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

  (defmethod event-msg-handler :chsk/recv
    [{:as ev-msg :keys [?data]}]
    (dlog "received data")
    (dlog ev-msg)
    )

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

;; === hello world ============================
(defonce app-state
  (reagent/atom
   {:text "Hello Chestnut!"
    }))

(defn main-component []
  [:div
   [:h1 (:text @app-state)]
   ])

;; === chatroom ===============================
;; message structure
;; :username :content :timestamp
(defonce chatroom-state
  (reagent/atom
   {:title "room of chatter"
    :user-list ["bot"] ;; unique
    :message-history [{:username "bot"
                       :content "hi everybody"
                       :timestamp (tnow)}]
    }))
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
     (for [username (@chatroom-state :user-list)]
       [:li
        {:style {:border "1px solid orange"
                 :padding "2px"
                 :display "inline"}}
        username]))]
   ;; message log
   [:div
    (apply vector
           :div
           (for [msg (@chatroom-state :message-history)]
             [:div
              (:username msg) ":"
              [:textarea
               {:readOnly "readOnly"
                :style {:width "100%"
                        :height "4em"
                        }}
               (:content msg)]]))
    ]
   ;; input area
   [:div
    ;; don't want to attach key listeners to this item, we will cheat
    [:textarea
     {:id "inp-chat-message"
      :placeholder "enter your message here"
      :style {:width "100%"}}]
    [:button
     {:onClick (fn [_]
                 (js/console.log
                  {:message (.-value (dom/getElement "inp-chat-message"))}))
      :type "button"}
     "submit"]]])

;; init app
(reagent/render-component
 ;;main-component
 chatroom-component
 (dom/getElement "app"))


