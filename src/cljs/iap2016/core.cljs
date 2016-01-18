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
                       :timestamp (t.now)}]
    }))
(defn chatroom-component []
  [:div
   [:div
    "welcome to "
    [:span
     {:style {:font-weight "bold"}}
     (@chatroom-state :title)]]
   (apply
    vector
    :ul
    {:style {:list-style "none"
             :background "beige"}}
    (for [username (@chatroom-state :user-list)]
      [:li
       {:style {:border "1px solid orange"
                :padding "2px"
                :float "left"}}
       username]))])

;; init app
(reagent/render-component
 ;;main-component
 chatroom-component
 (js/document.getElementById "app"))


