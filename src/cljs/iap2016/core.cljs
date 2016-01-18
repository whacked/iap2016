(ns iap2016.core
  (:require [reagent.core :as reagent]
            
            ))

(enable-console-print!)

;; === utility ================================
(defn tnow
  "get current time"
  []
  (.getTime (js/Date.)))

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
    [:textarea
     {:placeholder "enter your message here"
      :style {:width "100%"}}]
    [:button
     {:onClick (fn [_]
                 (js/console.log "clicked"))
      :type "button"}
     "submit"]]])

;; init app
(reagent/render-component
 ;;main-component
 chatroom-component
 (js/document.getElementById "app"))


