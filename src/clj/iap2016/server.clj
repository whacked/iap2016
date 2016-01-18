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
            
            ;; NOTE:
            ;; http://www.raynes.me/logs/irc.freenode.net/clojure/2015-06-05.txt
            ;; figwheel may not play nice with immutant web
            ;; [immutant.web :as web]
            ;; [taoensso.sente.server-adapters.immutant :refer (sente-web-server-adapter)]
            [taoensso.sente.server-adapters.http-kit      :refer (sente-web-server-adapter)]
            )
  (:gen-class))

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

(def http-handler
  (-> routes
      (wrap-defaults api-defaults)
      ring.middleware.keyword-params/wrap-keyword-params
      ring.middleware.params/wrap-params
      wrap-with-logger
      wrap-gzip))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 10555))]
    (run-jetty http-handler {:port port :join? false})
    ;; (web/run http-handler)
    ))

