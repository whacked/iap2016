(ns iap2016.core
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

;; === transmission ===========================
(let [{:keys [chsk ch-recv send-fn state]}
      (sente/make-channel-socket! "/chsk" ; Note the same path as before
                                  {:type :auto ; e/o #{:auto :ajax :ws}
                                   })]
  (def chsk       chsk)
  (def ch-chsk    ch-recv) ; ChannelSocket's receive channel
  (def chsk-send! send-fn) ; ChannelSocket's send API fn
  (def chsk-state state)   ; Watchable, read-only atom
  )

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


