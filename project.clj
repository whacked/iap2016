(defproject iap2016 "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.228" :scope "provided"]
                 [ring "1.4.0"]
                 [ring/ring-defaults "0.1.5"]
                 [bk/ring-gzip "0.1.1"]
                 [ring.middleware.logger "0.5.0"]
                 [compojure "1.4.0"]

                 ;; better cljs debugging
                 [binaryage/devtools "0.5.0"]

                 
                 ;; chestnut default; preferring reagent
                 ;; [org.omcljs/om "1.0.0-alpha28"]
                 
                 [environ "1.0.1"]


                 [reagent "0.6.0-alpha" :exclusions [cljsjs/react]]
                 [cljsjs/react-dom "0.14.3-1"]
                 [cljsjs/react-dom-server "0.14.3-0"]
                 [cljsjs/react-with-addons "0.14.3-0"]
                 
                 [cljsjs/nvd3 "1.8.1-0"]
                 [cljsjs/phaser "2.4.2-0"]

                 [cljsjs/three "0.0.72-0"]

                 [prismatic/schema "1.0.4"]
                 ;; utility
                 [prismatic/plumbing "0.5.2"]

                 ;; flat file db
                 ;; [com.taoensso/nippy "2.10.0"]
                 [datascript "0.15.0"]
                 
                 [com.taoensso/sente "1.7.0" :exclusions [com.taoensso/encore]] ;; websocket comm
                 [com.taoensso/timbre "4.2.1"] ;; logging
                 [org.clojure/core.async "0.2.374"]

                 [cljsjs/codemirror "5.10.0-0"]


                 [org.immutant/web "2.1.2"]
                 [org.immutant/caching "2.1.2"]
                 [org.immutant/messaging "2.1.2"]
                 [org.immutant/scheduling "2.1.2"]
                 [org.immutant/transactions "2.1.2"]


                 ;; ;; wolfram alpha
                 ;; [self/wolframalpha "1.1"]

                 ;; ;; WA deps
                 ;; [org.apache.httpcomponents/httpclient "4.5.1"]
                 ;; [org.apache.httpcomponents/httpcore "4.4.4"]
                 ;; [commons-codec/commons-codec "1.10"]
                 ;; [commons-logging/commons-logging "1.2"]

                 [com.cemerick/url "0.1.1"]

                 [org.clojure/data.json "0.2.6"]
                 [org.clojure/data.csv "0.1.3"]
                 ]

  :plugins [[lein-cljsbuild "1.1.1"]
            [lein-environ "1.0.1"]]

  :min-lein-version "2.5.3"

  :source-paths ["src/clj" "src/cljs" "dev"]

  :test-paths ["test/clj"]

  :clean-targets ^{:protect false} [:target-path :compile-path "resources/public/js"]

  :uberjar-name "iap2016.jar"

  ;; Use `lein run` if you just want to start a HTTP server, without figwheel
  ;; :main iap2016.server
  :main ^:skip-aot gorilla-test.core

  ;; for immutant;
  ;; see http://immutant.org/builds/1214/html-docs/initialization.html
  :ring {:handler iap2016.server/http-handler}
  :immutant {:init iap2016.server/immutant-start}

  ;; nREPL by default starts in the :main namespace, we want to start in `user`
  ;; because that's where our development helper functions like (run) and
  ;; (browser-repl) live.
  :repl-options {:init-ns user}

  :cljsbuild {:builds
              {:app
               {:source-paths ["src/cljs"]

                :figwheel true
                ;; Alternatively, you can configure a function to run every time figwheel reloads.
                ;; :figwheel {:on-jsload "iap2016.core/on-figwheel-reload"}

                :compiler {:main iap2016.core
                           :asset-path "js/compiled/out"
                           :output-to "resources/public/js/compiled/iap2016.js"
                           :output-dir "resources/public/js/compiled/out"
                           :source-map-timestamp true}}}}

  ;; When running figwheel from nREPL, figwheel will read this configuration
  ;; stanza, but it will read it without passing through leiningen's profile
  ;; merging. So don't put a :figwheel section under the :dev profile, it will
  ;; not be picked up, instead configure figwheel here on the top level.

  :figwheel {;; :http-server-root "public"       ;; serve static assets from resources/public/
             ;; :server-port 3449                ;; default
             ;; :server-ip "127.0.0.1"           ;; default
             :css-dirs ["resources/public/css"]  ;; watch and update CSS

             ;; Instead of booting a separate server on its own port, we embed
             ;; the server ring handler inside figwheel's http-kit server, so
             ;; assets and API endpoints can all be accessed on the same host
             ;; and port. If you prefer a separate server process then take this
             ;; out and start the server with `lein run`.
             :ring-handler user/http-handler

             ;; Start an nREPL server into the running figwheel process. We
             ;; don't do this, instead we do the opposite, running figwheel from
             ;; an nREPL process, see
             ;; https://github.com/bhauman/lein-figwheel/wiki/Using-the-Figwheel-REPL-within-NRepl
             ;; :nrepl-port 7888

             ;; To be able to open files in your editor from the heads up display
             ;; you will need to put a script on your path.
             ;; that script will have to take a file path and a line number
             ;; ie. in  ~/bin/myfile-opener
             ;; #! /bin/sh
             ;; emacsclient -n +$2 $1
             ;;
             ;; :open-file-command "myfile-opener"

             :server-logfile "log/figwheel.log"}

  :doo {:build "test"}

  :profiles {:dev
             {:dependencies [[figwheel "0.5.0-3"]
                             [figwheel-sidecar "0.5.0-3"]
                             [com.cemerick/piggieback "0.2.1"]
                             [org.clojure/tools.nrepl "0.2.12"]

                             ;; for dependency reload
                             [alembic "0.3.2"]
                             ]

              :plugins [[lein-figwheel "0.5.0-2"]
                        [lein-doo "0.1.6"]
                        [lein-gorilla "0.3.5"]]

              :cljsbuild {:builds
                          {:test
                           {:source-paths ["src/cljs" "test/cljs"]
                            :compiler {:output-to "resources/public/js/compiled/testable.js"
                                       :main iap2016.test-runner
                                       :optimizations :none}}}}}

             :uberjar
             {:source-paths ^:replace ["src/clj"]
              :hooks [leiningen.cljsbuild]
              :omit-source true
              :aot :all
              :cljsbuild {:builds
                          {:app
                           {:source-paths ^:replace ["src/cljs"]
                            :compiler
                            {:optimizations :advanced
                             :pretty-print false}}}}}})
