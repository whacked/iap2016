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

(defroutes routes
  
  (GET  "/chsk" req (ring-ajax-get-or-ws-handshake req))
  (POST "/chsk" req (ring-ajax-post                req))
  
  (GET "/" _
    {:status 200
     :headers {"Content-Type" "text/html; charset=utf-8"}
     :body (io/input-stream (io/resource "public/index.html"))})
  (resources "/"))



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
         :user-map {"bot" {}}}))

;; persistence
(def DATA-FILE "data.edn")
(defn save-state! []
  (spit DATA-FILE (pr-str @db)))
(defn load-state! []
  (reset! db (read-string (slurp DATA-FILE))))

;; seed the db
;; (swap! db assoc-in [:user-map :bot] {:address "server"})

(defn get-base-data-status []
  {:timestamp (tnow)
   :latest (or (last (map :timestamp (:message-history @db)))
               (last (map :timestamp (vals (:user-map @db)))))
   :message-count (count (:message-history @db))})

;; transmission
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
  (defmethod event-msg-handler :server/load-snapshot
    [{:as ev-msg :keys [event id ?data ring-req ?reply-fn send-fn]}]
    (let []
      (when ?reply-fn
        (?reply-fn
         {:status (get-base-data-status)
          :user-map (:user-map @db)
          :message-history (:message-history @db)
          }))))

(defonce router_ (atom nil))
(defn  stop-router! [] (when-let [stop-f @router_] (stop-f)))
(defn start-router! []
  (stop-router!)
  (reset! router_ (sente/start-chsk-router! ch-chsk event-msg-handler*)))

;; (start-router!)



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

