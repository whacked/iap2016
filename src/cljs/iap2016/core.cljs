(ns iap2016.core
  (:require [reagent.core :as reagent]
            
            ))

(enable-console-print!)

(defonce app-state
  (reagent/atom
   {:text "Hello Chestnut!"
    }))

(defn main-component []
  [:div
   [:h1 (:text @app-state)]
   ])


;; chatroom
(defonce chatroom-state
  (reagent/atom
   {:user-list ["bot"]
    :title "room of chatter"
    }))
(defn chatroom-component []
  [:div
   "hi all"
   (apply
    vector
    :ul
    (for [username (@chatroom-state :user-list)]
      [:li username]))])

;; init app
(reagent/render-component
 ;;main-component
 chatroom-component
 (js/document.getElementById "app"))


