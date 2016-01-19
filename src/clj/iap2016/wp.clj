(ns iap2016.wp
  (:require [cemerick.url :refer (url url-encode)]
            [clojure.data.json :as json]
            ))

(defn query-wikipedia
  [title-matcher]
  (->
   (url "https://en.wikipedia.org/w/api.php")
   (assoc :query
          {:action "query"
           :format "json"
           :prop "extracts"
           :exintro ""
           :explaintext ""
           :titles title-matcher
           })
   str
   slurp
   (json/read-str :key-fn keyword)))

;; (keys (query-wikipedia "Clojure"))




