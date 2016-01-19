(ns iap2016.server
  (:require [clojure.java.io :as io]
            [compojure.core :refer [ANY GET PUT POST DELETE defroutes]]
            [compojure.route :refer [resources]]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [ring.middleware.gzip :refer [wrap-gzip]]
            [ring.middleware.logger :refer [wrap-with-logger]]
            [environ.core :refer [env]]
            [ring.adapter.jetty :refer [run-jetty]]

            ;; see https://github.com/ptaoussanis/sente
            [taoensso.sente :as sente]
            [taoensso.timbre :as tm]
            
            ;; NOTE:
            ;; http://www.raynes.me/logs/irc.freenode.net/clojure/2015-06-05.txt
            ;; figwheel may not play nice with immutant web
            ;; [immutant.web :as web]
            ;; [taoensso.sente.server-adapters.immutant :refer (sente-web-server-adapter)]
            [taoensso.sente.server-adapters.http-kit      :refer (sente-web-server-adapter)]

            [datascript.core :as d]
            )
  (:gen-class))

;; write trace messages to stdout
(tm/merge-config! {:level :trace})

(let [{:keys [ch-recv send-fn ajax-post-fn ajax-get-or-ws-handshake-fn
              connected-uids]}
      (sente/make-channel-socket! sente-web-server-adapter {})]
  (def ring-ajax-post                ajax-post-fn)
  (def ring-ajax-get-or-ws-handshake ajax-get-or-ws-handshake-fn)
  (def ch-chsk                       ch-recv) ; ChannelSocket's receive channel
  (def chsk-send!                    send-fn) ; ChannelSocket's send API fn
  (def connected-uids                connected-uids) ; Watchable, read-only atom
  )



;; === utility ===============================
(defn tnow [] (System/currentTimeMillis))


;; === state management ===============================

;; for datascript
(def db-schema {:message-history []
                :user-map {}})
(defonce conn (d/create-conn db-schema))
;; yet unused

(def db
  (atom {:message-history [{:username "bot"
                            :content "hi everybody"
                            :timestamp (tnow)}
                           ]
         :user-map {:server {:username "bot"
                             :timestamp (tnow)}
                    }}))

;; persistence
(def DATA-FILE "data.edn")
(defn save-state! []
  (spit DATA-FILE (pr-str @db)))
(defn load-state! []
  (reset! db (read-string (slurp DATA-FILE))))

;; seed the db
;; (swap! db assoc-in [:user-map :server] {:username "bot" :timestamp (tnow)})

(defn get-base-data-status []
  {:latest (or (last (map :timestamp (:message-history @db)))
               (last (map :timestamp (vals (:user-map @db)))))
   :message-count (count (:message-history @db))})

;; transmission
(defn broadcast-last
  []
  (doseq [uid (:any @connected-uids)]
    (chsk-send! uid
                [:status/latest-message
                 (assoc (get-base-data-status)
                        :message (->>
                                  (:message-history @db)
                                  (sort-by :timestamp)
                                  (last)))])))

(defmulti event-msg-handler :id) ; Dispatch on event-id
;; Wrap for logging, catching, etc.:
(defn     event-msg-handler* [{:as ev-msg :keys [id ?data event]}]
  (tm/trace (format "Event: %s" event))
  (event-msg-handler ev-msg))

(do ; Server-side methods
  (defmethod event-msg-handler :default ; Fallback
    [{:as ev-msg :keys [event id ?data ring-req ?reply-fn send-fn]}]
    (let [session (:session ring-req)
          uid     (:uid     session)]
      (tm/debug (format "Unhandled event: %s" event))
      (when ?reply-fn
        (?reply-fn {:umatched-event event}))))

  (defmethod event-msg-handler :server/status
    [{:as ev-msg :keys [event id ?data ring-req ?reply-fn send-fn]}]
    (let []
      (when ?reply-fn
        (?reply-fn
         (assoc (get-base-data-status)
                :message-count (count (:message-history @db))
                :user-count (count (:user-map @db)))))))
  
  (defmethod event-msg-handler :server/load-snapshot
    [{:as ev-msg :keys [event id ?data ring-req ?reply-fn send-fn]}]
    (let [since (or (:since ?data)
                    -1)]
      (when ?reply-fn
        (?reply-fn
         {:status (get-base-data-status)
          :user-map (:user-map @db)
          :message-history
          (filter
           (fn [msg]
             (<= since (:timestamp msg)))
           (:message-history @db))
          }))))

  (defmethod event-msg-handler :server/post-message
    [{:as ev-msg :keys [event id ?data ring-req ?reply-fn send-fn]}]
    (let [{:keys [session params]} ring-req
          {:keys [username]} params]
      (swap!
       db
       update
       :message-history
       conj
       {:username (:client-id params)
        :content (:content ?data)
        :timestamp (tnow)})

      (broadcast-last)
      
      (when ?reply-fn
        (?reply-fn
         {:status (get-base-data-status)
          :user-map (:user-map @db)
          :message-history (:message-history @db)
          }))))
  
  (defmethod event-msg-handler :server/query-data
    [{:as ev-msg :keys [event id ?data ring-req ?reply-fn send-fn]}]
    (let [session (:session ring-req)
          uid     (:uid     session)
          query   (:q ?data)
          client-time (:time ?data)]
      ))
  )

(defonce router_ (atom nil))
(defn  stop-router! [] (when-let [stop-f @router_] (stop-f)))
(defn start-router! []
  (stop-router!)
  (reset! router_ (sente/start-chsk-router! ch-chsk event-msg-handler*)))
(defn restart-router! []
  (stop-router!)
  (start-router!))
;; (start-router!)


(defn login!
  "Here's where you'll add your server-side login/auth procedure (Friend, etc.).
  In our simplified example we'll just always successfully authenticate the user
  with whatever user-id they provided in the auth request."
  [ring-request]
  (let [{:keys [session params]} ring-request
        {:keys [username]} params]
    ;; use csrf-token (established on handshake)
    ;; as unique identifier. you shouldn't do this
    ;; for your own chat app
    (swap! db
           assoc-in
           [:user-map (:csrf-token params)]
           {:username (:username params)
            :timestamp (tnow)})
    {:status 200
     :session (assoc session :uid username)
     :body (pr-str (get-base-data-status))}))

(defroutes routes
  
  (GET  "/chsk" req (ring-ajax-get-or-ws-handshake req))
  (POST "/chsk" req (ring-ajax-post                req))

  (POST "/login" req (login! req))
  
  (GET "/" _
    {:status 200
     :headers {"Content-Type" "text/html; charset=utf-8"}
     :body (io/input-stream (io/resource "public/index.html"))})
  (resources "/"))

(def http-handler
  (-> (wrap-defaults
       routes
       (assoc-in
        ring.middleware.defaults/site-defaults
        [:security :anti-forgery]
        {:read-token (fn [req] (-> req :params :csrf-token))}))
      wrap-with-logger
      wrap-gzip))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 10555))]
    (run-jetty http-handler {:port port :join? false})
    ;; (web/run http-handler)
    ))

