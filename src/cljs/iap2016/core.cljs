(ns iap2016.core
  (:require-macros
   [cljs.core.async.macros :as asyncm :refer (go go-loop)]
   )
  (:require [reagent.core :as reagent]

            [devtools.core :as devtools]
            
            [goog.dom :as dom]
            
            ;; transmission
            [cljs.core.async :as async :refer (<! >! put! chan)]
            [taoensso.sente  :as sente :refer (cb-success?)]
            
            ))

(devtools/enable-feature! :sanity-hints :dirac)
(devtools/install!)
(enable-console-print!)



;; === state decl ===
(defonce app-state
  (reagent/atom
   {:text "Hello Chestnut!"
    :username nil

    :selected-section :chatroom
    :available-section #{:chatroom
                         :audio
                         :plot
                         :threejs}
    }))

(defonce chatroom-state
  (reagent/atom
   {:title "title goes here"
    :user-map {}
    :message-history []
    }))





;; === utility ================================
(defn tnow
  "get current time"
  []
  (.getTime (js/Date.)))

;; save typing
(def dlog (js/console.log.bind js/console))

;; === transmission ===========================
;; sente setup is straight from the example:
;; https://github.com/ptaoussanis/sente/blob/master/example-project/src/example/my_app.cljx
(let [{:keys [chsk ch-recv send-fn state]}
      (sente/make-channel-socket! "/chsk" ; Note the same path as before
                                  {:type :auto ; e/o #{:auto :ajax :ws}
                                   })]
  (def chsk       chsk)
  (def ch-chsk    ch-recv) ; ChannelSocket's receive channel
  (def chsk-send! send-fn) ; ChannelSocket's send API fn
  (def chsk-state state)   ; Watchable, read-only atom
  )

(defmulti event-msg-handler :id) ; Dispatch on event-id
;; Wrap for logging, catching, etc.:
(defn     event-msg-handler* [{:as ev-msg :keys [id ?data event]}]
  (event-msg-handler ev-msg))

(do ; Client-side methods
  (defmethod event-msg-handler :default ; Fallback
    [{:as ev-msg :keys [event]}]
    (dlog (str "Unhandled event: " (pr-str event))))

  (defmethod event-msg-handler :chsk/state
    [{:as ev-msg :keys [?data]}]
    (if (= ?data {:first-open? true})
      (dlog "Channel socket successfully established!")
      (dlog (str "Channel socket state change: " (pr-str ?data)))))

  (declare handle-event)
  (defmethod event-msg-handler :chsk/recv
    [{:as ev-msg :keys [?data]}]
    (dlog "received data")
    (dlog ev-msg)
    (dlog ?data)
    (apply handle-event ?data))

  (defmethod event-msg-handler :chsk/handshake
    [{:as ev-msg :keys [?data]}]
    (let [[?uid ?csrf-token ?handshake-data] ?data]
      (dlog (str "%cHandshake: " (pr-str ?data))
            "color:cyan;")))

  

  )

(def router_ (atom nil))
(defn stop-router! []
  (when-let [stop-f @router_] (stop-f)))
(defn start-router! []
  (stop-router!)
  ;; start-chsk-router! returns a stopping function
  (reset! router_ (sente/start-chsk-router! ch-chsk event-msg-handler*)))






(defn post-message! [code]
  (chsk-send!
   [:server/post-message {:username (:username @app-state)
                          :content code}] 5000
   (fn [data]
     (dlog "response...")
     (dlog data))))




;; === chatroom ===============================
;; message structure
;; :username :content :timestamp
(declare reload-from-server!)
(defn chatroom-component []
  [:div
   {:style {:border "2px solid gray"
            :padding "2px"
            :width "600px"
            :height "100%"}}
   [:div
    "welcome to "
    [:span
     {:style {:font-weight "bold"}}
     (@chatroom-state :title)]]
   [:div
    "who's here:"
    (apply
     vector
     :ul
     {:style {:list-style "none"
              :background "beige"}}
     (for [user (vals (@chatroom-state :user-map))]
       [:li
        {:style {:border "1px solid orange"
                 :padding "2px"
                 :display "inline"}}
        (:username user)]))]
   ;; message log
   [:div
    (apply vector
           :div
           (for [msg (@chatroom-state :message-history)]
             [:div
              {:style {:fontSize "small"}}
              [:b
               (:username msg)]
              " said at "
              [:em
               (str (js/Date. (:timestamp msg)))]
              " :"
              [:textarea
               {:readOnly "readOnly"
                :style {:width "100%"
                        :height "4em"
                        }
                :value (:content msg)}]]))
    ]

   (let [my-code (reagent/atom "")]
    [:div
    
     "post a message or some code"
     [:textarea
      {:placeholder "enter your message"
       :style {:width "100%"}
       :value @my-code
       :onChange (fn [e] (reset! my-code (aget e "target" "value")))
       }
      ]
     [:button
      {:onClick (fn [_]
                  (if (< 0 (count @my-code))
                    (post-message! @my-code))
                  (reset! my-code ""))
       :type "button"}
      "submit"]
    
     [:button
      {:onClick (fn [_]
                  (reload-from-server!))
       :type "button"}
      "sync"]])
   ])


;; === handler ======================
(defn load-since!
  [timestamp]
  (chsk-send!
   [:server/load-snapshot {:since timestamp}] 5000
   (fn [data]
     (when (cb-success? data)
       (swap!
        chatroom-state
        assoc
        :user-map (:user-map data)
        :message-history
        (->>
         (concat (:message-history @chatroom-state)
                     (:message-history data))
         (map (fn [msg]
                [[(:timestamp msg)
                  (:username msg)
                  ] msg]))
         (into {})
         (vector)
         (sort)
         (map vals)
         (first)))))))

;; TODO turn this into a multimethod
(defn handle-event [id & [payload]]
  (case id
    :status/latest-message
    (do
      (let [latest-timestamp (apply max (map :timestamp (:message-history @chatroom-state)))]
        (if (> (:latest payload)
               latest-timestamp)
          (do
            (dlog "asking for refresh...")
            (load-since! latest-timestamp))
          (dlog "up to date"))
        ))))

(defn reload-from-server!
  "ignore local state and reload data from server as if we just joined"
  []
  (let []
    (load-since! -1)))

(defn server-test []
  (let []
    (chsk-send!
     [:server/test]
     5000
     (fn [data]
       (dlog "got...")
       (dlog data)))))

(defn query-data []
  (let []
    (chsk-send!
     [:server/query-data {:q '[:find :a]}]
     5000
     (fn [data]
       (dlog "got...")
       (dlog data)))))



(defn login!
  [username]
  (sente/ajax-call "/login"
                   {:method :post
                    :params {:username username
                             :csrf-token (:csrf-token @chsk-state)}}
                   (fn [ajax-resp]
                     (sente/chsk-reconnect! chsk)
                     (swap! app-state assoc :username username)
                     (swap! app-state assoc :text (str "logged in as " username))
                     (when-let [body (:?content ajax-resp)]
                       (dlog "%c + + + + + + + + + + "
                             "background:black;border:3px solid green;color:white;"))
                     )))

(defn main-component []
  (fn []
    [:div
     [:h1 (:text @app-state)]

     ;; render "tabs"
     [:ul
      (for [tab-key (:available-section @app-state)]
        [:li
         {:onClick (fn [_]
                     (swap! app-state assoc :selected-section tab-key))}
         (name tab-key)])
      ]
     
     (case (:selected-section @app-state)
       :chatroom
       (if (:username @app-state)
         [:div
          [chatroom-component]

          ]

         (let [inp-data (atom "")]
           [:div
            "enter name: "
            [:input
             {:type "text"
              :onChange (fn [e]
                          (reset! inp-data (aget e "target" "value")))
              }]
            [:button
             {:type "button"
              :onClick (fn [_]
                         (login! @inp-data))}
             "join"]]))

       :audio
       (do
         [:div
          "fun with web audio"]

         )

       (do
         ;; default
         [:div "quiet as a bear."]
         ))
     ]))







;; init app
(start-router!)

(reagent/render-component
 ;;main-component
 chatroom-component
 (dom/getElement "app"))

;; (reload-from-server!)


