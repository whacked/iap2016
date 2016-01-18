(ns iap2016.core
  (:require [reagent.core :as reagent]
            ))

(enable-console-print!)

(defonce app-state (reagent/atom {:text "Hello Chestnut!"}))

(defn main-component []
  [:div
   [:h1 (:text @app-state)]
   ])

(reagent/render-component
 [main-component]
 (js/document.getElementById "app"))


