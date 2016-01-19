(ns iap2016.wa
  (:import [com.wolfram.alpha WAEngine]))

;; conversion of the sample program
(def app-id "YOUR APP ID")
(def wae (WAEngine.))

(doto wae
  (.setAppID app-id)
  (.addFormat "plaintext"))

(comment
 (def q (doto (.createQuery wae)
          (.setInput "pi")))
 (def res (.performQuery wae q))

 (.isSuccess res) ;; => true

 (def pod-seq (.getPods res))

 (doseq [pod pod-seq]
   (println "=============")
   (println (.getTitle pod))
   (doseq [subpod (.getSubpods pod)]
     (doseq [elem (.getContents subpod)]
       (prn (.getText elem))))))

