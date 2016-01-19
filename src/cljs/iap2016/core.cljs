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


            ;; web audio
            ;; warning: last commit > year ago
            [hum.core :as hum]


            [cljsjs.nvd3]
            [cljsjs.three]
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

(defonce audio-context (hum/create-context))
(defn play-freq
  [freq & {:keys [duration]
           :or {duration 1000}}]
  (let [audio-osc     (hum/create-osc audio-context :sine)
        audio-filter  (hum/create-biquad-filter audio-context)
        audio-out     (hum/create-gain audio-context)
        ]
    
    (hum/connect audio-osc audio-filter audio-out)
    (hum/start-osc audio-osc)
    (hum/connect-output audio-out)

    (hum/note-on audio-out
                 audio-osc
                 freq)

    (js/setTimeout
     (fn []
       (hum/note-off audio-out))
     duration)))

;; poor man scheduler
(comment
  (let [
        c 523.25
        a 440.00
        g 392.00
        e 329.63
        d 293.66
        C 261.63
        
        gap 0

        jingle1 [
                 C 2 C 1 C 2 d 1 e 2 d 1 g 2 e 1 c 2 a 1 g 2 e 1 g 6
                 C 2 C 1 C 2 d 1 e 2 g 1 a 2 a 1 e 3 e 2 d 1 C 6
                 c 2 a 1 c 2 a 1 g 2 e 1 g 2 e 1 g 3 c 3 c 6
                 ]
        jingle2 [
                 c 2 c 2 c 2 a 1 g 1 a 1 g 1 e 2 e 4
                 a 2 a 2 a 2 g 1 e 1 g 1 e 1 d 2 d 4
                 c 2 c 2 c 2 a 1 g 1 a 1 g 1 e 2 e 4
                 d 2 a 2 g 2 e 1 g 1 a 2 a 2 a 4
                 ]
        ]
    (loop [toffset 0
           note-seq (take 1000 (partition 2 jingle1))]
      (if (empty? note-seq)
        nil
        (let [[freq nbeat] (first note-seq)
              duration (* nbeat 200)]
          (dlog "HELLO" nbeat freq duration toffset)

          (js/setTimeout
           #(play-freq freq :duration (- duration gap))
           toffset)
          (recur (+ toffset duration)
                 (rest note-seq)))))))

(defn webaudio-component []
  (fn []
    [:div
     "fun with web audio"]
    ))




(defn render-nvd3 [el]
  ;; cf http://nvd3.org/examples/line.html;
  ;; look how trivially simple the data generator becomes in cljs!
  ;; NOTE: nvd3 line example is out of date wrt options
  ;; `transitionDuration` and `useInteractiveGuideline`
  (nv.addGraph
   (fn []
     (let [nv-chart (doto (nv.models.lineChart)
                      (.options (clj->js {:transitionDuration 300
                                          :useInteractiveGuideline true}))
                      (.margin (clj->js {:left 100}))
                      (.showLegend false) ;; leave this to react
                      (.showYAxis true)
                      (.showXAxis true))]
       (doto (.-xAxis nv-chart)
         (.axisLabel "time (s)")
         (.tickFormat (d3.format ",r")))
       (doto (.-yAxis nv-chart)
         (.axisLabel "amplitude")
         (.tickFormat (d3.format ".02f")))

       (nv.utils.windowResize #(.update nv-chart))

       (-> (d3.select el)
           (.datum (clj->js [{:values (map (fn [i]
                                             {:x i
                                              :y (Math/sin (/ i 10))})
                                           (range 100))}]))
           (.call nv-chart)
           )
       )))
  )

(defn plot-component []
  (fn []
    ;; this is tricky because we need to tell reagent
    ;; to tell react to not update the chart drawing area
    [(with-meta identity
       {:component-did-mount
        (fn [el]
          (render-nvd3 (reagent/dom-node el)))})
     [:svg
      {:id "testchart"
       :style {:width "100%"
               :height "500px"
               :border "2px solid brown"}}
      ]]
    ))


;; THREE render code

;; preload
;; wanted to do this:
;; (def tex-clojure (THREE.ImageUtils.loadTexture "https://upload.wikimedia.org/wikipedia/en/1/1d/Clojure_logo.gif"))
;; but blocked by CORS... so for our purposes you need to download it to
;; resources/img/Clogore_logo.gif; not adding it to repo
(comment
  (def tex-clojure (THREE.ImageUtils.loadTexture "http://localhost:3449/img/Clojure_logo.gif"))
  (aset tex-clojure "wrapS" THREE.RepeatWrapping)
  (aset tex-clojure "wrapT" THREE.RepeatWrapping)
  (aset tex-clojure "minFilter" THREE.LinearFilter)
  (aset tex-clojure "magFilter" THREE.LinearFilter)
  (aset tex-clojure "generateMipmaps" false)
  ((.. tex-clojure -repeat -set) 2 2)
  (aset tex-clojure "needsUpdate" true)
  
  (js/document.body.appendChild
   (doto (js/document.createElement "div")
     (.setAttribute "id" "foo"))))

(comment
 (let [W 400
       H 300

       rdr (THREE.WebGLRenderer.)
       cam (THREE.PerspectiveCamera.
            45                          ; view angle
            (/ W H)                     ; perspective
            0.1                         ; near
            1000                        ; far
            )

       scn (THREE.Scene.)

       ;; geom (THREE.SphereGeometry. 7 12 12)
       geom (THREE.CubeGeometry. 40 40 40)

       mat1 (THREE.MeshLambertMaterial. #js {:color "red"
                                             :transparent true
                                             :opacity 0.8})
       mat (THREE.MeshBasicMaterial. #js {:map tex-clojure
                                          :opacity true})

       mesh (THREE.Mesh. geom mat)
      
       light (THREE.DirectionalLight. 0xFFFF00 3)
       ]
   
   (doseq [i (range (aget (dom/getElement "foo") "childNodes" "length"))]
     (.removeChild
      (dom/getElement "foo")
      (aget (dom/getElement "foo") "childNodes" i)))
   
   
   (aset cam "position" "z" 100)

   (aset  "rotation" "z" (/ 2 Math/PI))

   (doto (.-position light)
     (.set 20 100 50)
     (.multiplyScalar 1.6))
   (aset light "castShadow" true)
   (aset light "shadowCameraVisible" true)
   (aset light "shadowMapWidth" 10)
   (aset light "shadowMapHeight" 10)
      
   (aset light "shadowCameraLeft" -200)
   (aset light "shadowCameraRight" 200)
   (aset light "shadowCameraTop" 200)
   (aset light "shadowCameraBottom" -200)

   (aset light "shadowCameraFar" 300)
   (aset light "shadowDarkness" 0.2)

   (doto scn
     (.add cam)
     (.add mesh)
     (.add (THREE.AmbientLight. 0x999999))
     (.add light)
     )
  
   (.setSize rdr W H)

   (.appendChild (dom/getElement "foo")
                 (.-domElement rdr))

   (letfn [(render []
             (aset mesh "rotation" "x"
                   (+ 0.01
                      (aget mesh "rotation" "x")))
             (aset mesh "rotation" "y"
                   (+ 0.005
                      (aget mesh "rotation" "y")))
             (.render rdr scn cam))
           (animate []
             (js/requestAnimationFrame animate)
             (render))]
     (animate))
   ))

(defn threejs-component []
  (fn []
    [:div
     "three js is cool"
     ]
    ))


(defn main-component []
  (fn []
    [:div
     [:h1 (:text @app-state)]

     ;; render "tabs"
     [:ul
      (for [tab-key (:available-section @app-state)]
        [:li
         {:style {:border (if (= tab-key (:selected-section @app-state))
                            "2px solid orange"
                            "2px solid gray")}
          :onClick (fn [_]
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
       [webaudio-component]

       :plot
       [plot-component]

       :threejs
       [threejs-component]

       (do
         ;; default
         [:div "quiet as a bear."]
         ))
     ]))







;; init app
(start-router!)

(reagent/render-component
 [main-component]
 (dom/getElement "app"))

;; (reload-from-server!)


