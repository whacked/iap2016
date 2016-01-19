;; gorilla-repl.fileformat = 1

;; **
;;; # Gorilla REPL
;;; 
;;; Welcome to gorilla :-)
;;; 
;;; Shift + enter evaluates code. Hit alt+g twice in quick succession or click the menu icon (upper-right corner) for more commands ...
;;; 
;;; It's a good habit to run each worksheet in its own namespace: feel free to use the declaration we've provided below if you'd like.
;; **

;; @@
(ns lush-stream
  (:require [gorilla-plot.core :as plot]
            [iap2016.wp :as wp]
            [clojure.java.io :as io]
            [clojure.data.csv :as csv]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(wp/query-wikipedia "Clojure")
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:batchcomplete</span>","value":":batchcomplete"},{"type":"html","content":"<span class='clj-string'>&quot;&quot;</span>","value":"\"\""}],"value":"[:batchcomplete \"\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:query</span>","value":":query"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:pages</span>","value":":pages"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:16561990</span>","value":":16561990"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:pageid</span>","value":":pageid"},{"type":"html","content":"<span class='clj-long'>16561990</span>","value":"16561990"}],"value":"[:pageid 16561990]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:ns</span>","value":":ns"},{"type":"html","content":"<span class='clj-long'>0</span>","value":"0"}],"value":"[:ns 0]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:title</span>","value":":title"},{"type":"html","content":"<span class='clj-string'>&quot;Clojure&quot;</span>","value":"\"Clojure\""}],"value":"[:title \"Clojure\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:extract</span>","value":":extract"},{"type":"html","content":"<span class='clj-string'>&quot;Clojure (pronunciation: /ˈkloʊʒɜːr/, like \\&quot;closure\\&quot;) is a dialect of the Lisp programming language created by Rich Hickey. Clojure is a general-purpose programming language with an emphasis on functional programming. It runs on the Java Virtual Machine, Common Language Runtime, and JavaScript engines. Like other Lisps, Clojure treats code as data and has a macro system. The current development process is community-driven, overseen by Rich Hickey as its BDFL.\\nClojure takes a modern stance on programming by encouraging immutability and immutable data structures. While its type system is entirely dynamic, recent efforts have also sought the implementation of gradual typing. Clojure encourages programmers to be explicit about managing state and identity. This focus on programming with immutable values and explicit progression-of-time constructs are intended to facilitate the development of more robust programs, particularly multithreaded ones.\\nClojure is successfully used in industry by companies such as Walmart, Puppet Labs, and other large software companies. Commercial support for Clojure is provided by Cognitect. Annual Clojure conferences are organised every year across the globe, the most famous of them being Clojure/conj (US east coast), Clojure/West (US west coast), and EuroClojure (Europe).\\nThe latest stable version of Clojure is 1.7, released on June 30, 2015. The first stable release was version 1.0, released on May 4, 2009. Clojure is free software released under the Eclipse Public License.&quot;</span>","value":"\"Clojure (pronunciation: /ˈkloʊʒɜːr/, like \\\"closure\\\") is a dialect of the Lisp programming language created by Rich Hickey. Clojure is a general-purpose programming language with an emphasis on functional programming. It runs on the Java Virtual Machine, Common Language Runtime, and JavaScript engines. Like other Lisps, Clojure treats code as data and has a macro system. The current development process is community-driven, overseen by Rich Hickey as its BDFL.\\nClojure takes a modern stance on programming by encouraging immutability and immutable data structures. While its type system is entirely dynamic, recent efforts have also sought the implementation of gradual typing. Clojure encourages programmers to be explicit about managing state and identity. This focus on programming with immutable values and explicit progression-of-time constructs are intended to facilitate the development of more robust programs, particularly multithreaded ones.\\nClojure is successfully used in industry by companies such as Walmart, Puppet Labs, and other large software companies. Commercial support for Clojure is provided by Cognitect. Annual Clojure conferences are organised every year across the globe, the most famous of them being Clojure/conj (US east coast), Clojure/West (US west coast), and EuroClojure (Europe).\\nThe latest stable version of Clojure is 1.7, released on June 30, 2015. The first stable release was version 1.0, released on May 4, 2009. Clojure is free software released under the Eclipse Public License.\""}],"value":"[:extract \"Clojure (pronunciation: /ˈkloʊʒɜːr/, like \\\"closure\\\") is a dialect of the Lisp programming language created by Rich Hickey. Clojure is a general-purpose programming language with an emphasis on functional programming. It runs on the Java Virtual Machine, Common Language Runtime, and JavaScript engines. Like other Lisps, Clojure treats code as data and has a macro system. The current development process is community-driven, overseen by Rich Hickey as its BDFL.\\nClojure takes a modern stance on programming by encouraging immutability and immutable data structures. While its type system is entirely dynamic, recent efforts have also sought the implementation of gradual typing. Clojure encourages programmers to be explicit about managing state and identity. This focus on programming with immutable values and explicit progression-of-time constructs are intended to facilitate the development of more robust programs, particularly multithreaded ones.\\nClojure is successfully used in industry by companies such as Walmart, Puppet Labs, and other large software companies. Commercial support for Clojure is provided by Cognitect. Annual Clojure conferences are organised every year across the globe, the most famous of them being Clojure/conj (US east coast), Clojure/West (US west coast), and EuroClojure (Europe).\\nThe latest stable version of Clojure is 1.7, released on June 30, 2015. The first stable release was version 1.0, released on May 4, 2009. Clojure is free software released under the Eclipse Public License.\"]"}],"value":"{:pageid 16561990, :ns 0, :title \"Clojure\", :extract \"Clojure (pronunciation: /ˈkloʊʒɜːr/, like \\\"closure\\\") is a dialect of the Lisp programming language created by Rich Hickey. Clojure is a general-purpose programming language with an emphasis on functional programming. It runs on the Java Virtual Machine, Common Language Runtime, and JavaScript engines. Like other Lisps, Clojure treats code as data and has a macro system. The current development process is community-driven, overseen by Rich Hickey as its BDFL.\\nClojure takes a modern stance on programming by encouraging immutability and immutable data structures. While its type system is entirely dynamic, recent efforts have also sought the implementation of gradual typing. Clojure encourages programmers to be explicit about managing state and identity. This focus on programming with immutable values and explicit progression-of-time constructs are intended to facilitate the development of more robust programs, particularly multithreaded ones.\\nClojure is successfully used in industry by companies such as Walmart, Puppet Labs, and other large software companies. Commercial support for Clojure is provided by Cognitect. Annual Clojure conferences are organised every year across the globe, the most famous of them being Clojure/conj (US east coast), Clojure/West (US west coast), and EuroClojure (Europe).\\nThe latest stable version of Clojure is 1.7, released on June 30, 2015. The first stable release was version 1.0, released on May 4, 2009. Clojure is free software released under the Eclipse Public License.\"}"}],"value":"[:16561990 {:pageid 16561990, :ns 0, :title \"Clojure\", :extract \"Clojure (pronunciation: /ˈkloʊʒɜːr/, like \\\"closure\\\") is a dialect of the Lisp programming language created by Rich Hickey. Clojure is a general-purpose programming language with an emphasis on functional programming. It runs on the Java Virtual Machine, Common Language Runtime, and JavaScript engines. Like other Lisps, Clojure treats code as data and has a macro system. The current development process is community-driven, overseen by Rich Hickey as its BDFL.\\nClojure takes a modern stance on programming by encouraging immutability and immutable data structures. While its type system is entirely dynamic, recent efforts have also sought the implementation of gradual typing. Clojure encourages programmers to be explicit about managing state and identity. This focus on programming with immutable values and explicit progression-of-time constructs are intended to facilitate the development of more robust programs, particularly multithreaded ones.\\nClojure is successfully used in industry by companies such as Walmart, Puppet Labs, and other large software companies. Commercial support for Clojure is provided by Cognitect. Annual Clojure conferences are organised every year across the globe, the most famous of them being Clojure/conj (US east coast), Clojure/West (US west coast), and EuroClojure (Europe).\\nThe latest stable version of Clojure is 1.7, released on June 30, 2015. The first stable release was version 1.0, released on May 4, 2009. Clojure is free software released under the Eclipse Public License.\"}]"}],"value":"{:16561990 {:pageid 16561990, :ns 0, :title \"Clojure\", :extract \"Clojure (pronunciation: /ˈkloʊʒɜːr/, like \\\"closure\\\") is a dialect of the Lisp programming language created by Rich Hickey. Clojure is a general-purpose programming language with an emphasis on functional programming. It runs on the Java Virtual Machine, Common Language Runtime, and JavaScript engines. Like other Lisps, Clojure treats code as data and has a macro system. The current development process is community-driven, overseen by Rich Hickey as its BDFL.\\nClojure takes a modern stance on programming by encouraging immutability and immutable data structures. While its type system is entirely dynamic, recent efforts have also sought the implementation of gradual typing. Clojure encourages programmers to be explicit about managing state and identity. This focus on programming with immutable values and explicit progression-of-time constructs are intended to facilitate the development of more robust programs, particularly multithreaded ones.\\nClojure is successfully used in industry by companies such as Walmart, Puppet Labs, and other large software companies. Commercial support for Clojure is provided by Cognitect. Annual Clojure conferences are organised every year across the globe, the most famous of them being Clojure/conj (US east coast), Clojure/West (US west coast), and EuroClojure (Europe).\\nThe latest stable version of Clojure is 1.7, released on June 30, 2015. The first stable release was version 1.0, released on May 4, 2009. Clojure is free software released under the Eclipse Public License.\"}}"}],"value":"[:pages {:16561990 {:pageid 16561990, :ns 0, :title \"Clojure\", :extract \"Clojure (pronunciation: /ˈkloʊʒɜːr/, like \\\"closure\\\") is a dialect of the Lisp programming language created by Rich Hickey. Clojure is a general-purpose programming language with an emphasis on functional programming. It runs on the Java Virtual Machine, Common Language Runtime, and JavaScript engines. Like other Lisps, Clojure treats code as data and has a macro system. The current development process is community-driven, overseen by Rich Hickey as its BDFL.\\nClojure takes a modern stance on programming by encouraging immutability and immutable data structures. While its type system is entirely dynamic, recent efforts have also sought the implementation of gradual typing. Clojure encourages programmers to be explicit about managing state and identity. This focus on programming with immutable values and explicit progression-of-time constructs are intended to facilitate the development of more robust programs, particularly multithreaded ones.\\nClojure is successfully used in industry by companies such as Walmart, Puppet Labs, and other large software companies. Commercial support for Clojure is provided by Cognitect. Annual Clojure conferences are organised every year across the globe, the most famous of them being Clojure/conj (US east coast), Clojure/West (US west coast), and EuroClojure (Europe).\\nThe latest stable version of Clojure is 1.7, released on June 30, 2015. The first stable release was version 1.0, released on May 4, 2009. Clojure is free software released under the Eclipse Public License.\"}}]"}],"value":"{:pages {:16561990 {:pageid 16561990, :ns 0, :title \"Clojure\", :extract \"Clojure (pronunciation: /ˈkloʊʒɜːr/, like \\\"closure\\\") is a dialect of the Lisp programming language created by Rich Hickey. Clojure is a general-purpose programming language with an emphasis on functional programming. It runs on the Java Virtual Machine, Common Language Runtime, and JavaScript engines. Like other Lisps, Clojure treats code as data and has a macro system. The current development process is community-driven, overseen by Rich Hickey as its BDFL.\\nClojure takes a modern stance on programming by encouraging immutability and immutable data structures. While its type system is entirely dynamic, recent efforts have also sought the implementation of gradual typing. Clojure encourages programmers to be explicit about managing state and identity. This focus on programming with immutable values and explicit progression-of-time constructs are intended to facilitate the development of more robust programs, particularly multithreaded ones.\\nClojure is successfully used in industry by companies such as Walmart, Puppet Labs, and other large software companies. Commercial support for Clojure is provided by Cognitect. Annual Clojure conferences are organised every year across the globe, the most famous of them being Clojure/conj (US east coast), Clojure/West (US west coast), and EuroClojure (Europe).\\nThe latest stable version of Clojure is 1.7, released on June 30, 2015. The first stable release was version 1.0, released on May 4, 2009. Clojure is free software released under the Eclipse Public License.\"}}}"}],"value":"[:query {:pages {:16561990 {:pageid 16561990, :ns 0, :title \"Clojure\", :extract \"Clojure (pronunciation: /ˈkloʊʒɜːr/, like \\\"closure\\\") is a dialect of the Lisp programming language created by Rich Hickey. Clojure is a general-purpose programming language with an emphasis on functional programming. It runs on the Java Virtual Machine, Common Language Runtime, and JavaScript engines. Like other Lisps, Clojure treats code as data and has a macro system. The current development process is community-driven, overseen by Rich Hickey as its BDFL.\\nClojure takes a modern stance on programming by encouraging immutability and immutable data structures. While its type system is entirely dynamic, recent efforts have also sought the implementation of gradual typing. Clojure encourages programmers to be explicit about managing state and identity. This focus on programming with immutable values and explicit progression-of-time constructs are intended to facilitate the development of more robust programs, particularly multithreaded ones.\\nClojure is successfully used in industry by companies such as Walmart, Puppet Labs, and other large software companies. Commercial support for Clojure is provided by Cognitect. Annual Clojure conferences are organised every year across the globe, the most famous of them being Clojure/conj (US east coast), Clojure/West (US west coast), and EuroClojure (Europe).\\nThe latest stable version of Clojure is 1.7, released on June 30, 2015. The first stable release was version 1.0, released on May 4, 2009. Clojure is free software released under the Eclipse Public License.\"}}}]"}],"value":"{:batchcomplete \"\", :query {:pages {:16561990 {:pageid 16561990, :ns 0, :title \"Clojure\", :extract \"Clojure (pronunciation: /ˈkloʊʒɜːr/, like \\\"closure\\\") is a dialect of the Lisp programming language created by Rich Hickey. Clojure is a general-purpose programming language with an emphasis on functional programming. It runs on the Java Virtual Machine, Common Language Runtime, and JavaScript engines. Like other Lisps, Clojure treats code as data and has a macro system. The current development process is community-driven, overseen by Rich Hickey as its BDFL.\\nClojure takes a modern stance on programming by encouraging immutability and immutable data structures. While its type system is entirely dynamic, recent efforts have also sought the implementation of gradual typing. Clojure encourages programmers to be explicit about managing state and identity. This focus on programming with immutable values and explicit progression-of-time constructs are intended to facilitate the development of more robust programs, particularly multithreaded ones.\\nClojure is successfully used in industry by companies such as Walmart, Puppet Labs, and other large software companies. Commercial support for Clojure is provided by Cognitect. Annual Clojure conferences are organised every year across the globe, the most famous of them being Clojure/conj (US east coast), Clojure/West (US west coast), and EuroClojure (Europe).\\nThe latest stable version of Clojure is 1.7, released on June 30, 2015. The first stable release was version 1.0, released on May 4, 2009. Clojure is free software released under the Eclipse Public License.\"}}}}"}
;; <=

;; @@
;; go to http://www.modelingonlineauctions.com/datasets
;; and get an xbox auction data set

(def data-csv "Xbox 7-day auctions.csv")
(if-not (.exists (io/as-file data-csv))
  (println "download it please")
  (let [data (slurp data-csv)]
    (count data)))

;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>88020</span>","value":"88020"}
;; <=

;; @@
;; doall because read-csv is lazy
;; and if we attempt to read data-seq after the with-open,
;; the stream will have closed
(with-open [in-file (io/reader data-csv)]
  (def data-seq
    (doall (csv/read-csv in-file))))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;lush-stream/data-seq</span>","value":"#'lush-stream/data-seq"}
;; <=

;; @@
(first data-seq)
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;auctionid&quot;</span>","value":"\"auctionid\""},{"type":"html","content":"<span class='clj-string'>&quot;bid&quot;</span>","value":"\"bid\""},{"type":"html","content":"<span class='clj-string'>&quot;bidtime&quot;</span>","value":"\"bidtime\""},{"type":"html","content":"<span class='clj-string'>&quot;bidder&quot;</span>","value":"\"bidder\""},{"type":"html","content":"<span class='clj-string'>&quot;bidderrate&quot;</span>","value":"\"bidderrate\""},{"type":"html","content":"<span class='clj-string'>&quot;openbid&quot;</span>","value":"\"openbid\""},{"type":"html","content":"<span class='clj-string'>&quot;price&quot;</span>","value":"\"price\""}],"value":"[\"auctionid\" \"bid\" \"bidtime\" \"bidder\" \"bidderrate\" \"openbid\" \"price\"]"}
;; <=

;; @@
(second data-seq)
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;8211480551&quot;</span>","value":"\"8211480551\""},{"type":"html","content":"<span class='clj-string'>&quot;52.99&quot;</span>","value":"\"52.99\""},{"type":"html","content":"<span class='clj-string'>&quot;1.201505&quot;</span>","value":"\"1.201505\""},{"type":"html","content":"<span class='clj-string'>&quot;hanna1104&quot;</span>","value":"\"hanna1104\""},{"type":"html","content":"<span class='clj-string'>&quot;94&quot;</span>","value":"\"94\""},{"type":"html","content":"<span class='clj-string'>&quot;49.99&quot;</span>","value":"\"49.99\""},{"type":"html","content":"<span class='clj-string'>&quot;311.6&quot;</span>","value":"\"311.6\""}],"value":"[\"8211480551\" \"52.99\" \"1.201505\" \"hanna1104\" \"94\" \"49.99\" \"311.6\"]"}
;; <=

;; @@
(zipmap [:a :b] [1 "x"])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:a</span>","value":":a"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[:a 1]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:b</span>","value":":b"},{"type":"html","content":"<span class='clj-string'>&quot;x&quot;</span>","value":"\"x\""}],"value":"[:b \"x\"]"}],"value":"{:a 1, :b \"x\"}"}
;; <=

;; @@
;; make type converter based on header
(Float/parseFloat "52.99")
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>52.99</span>","value":"52.99"}
;; <=

;; @@
(def type-converter-map
  {:auctionid #(Long/parseLong %)
   :bid #(Float/parseFloat %)
   :bidtime #(.longValue (* 1e12 (Float/parseFloat %)))
   :bidder identity
   :bidderrate #(Float/parseFloat %)
   :openbid #(Float/parseFloat %)
   :price #(Float/parseFloat %)})
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;lush-stream/type-converter-map</span>","value":"#'lush-stream/type-converter-map"}
;; <=

;; @@
(def rec-seq
  (let [hdr (map keyword (first data-seq))]
    (->> (rest data-seq)
         (map (fn [row]
                (zipmap hdr row))))))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;lush-stream/rec-seq</span>","value":"#'lush-stream/rec-seq"}
;; <=

;; @@
(first rec-seq)
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:auctionid</span>","value":":auctionid"},{"type":"html","content":"<span class='clj-string'>&quot;8211480551&quot;</span>","value":"\"8211480551\""}],"value":"[:auctionid \"8211480551\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:bid</span>","value":":bid"},{"type":"html","content":"<span class='clj-string'>&quot;52.99&quot;</span>","value":"\"52.99\""}],"value":"[:bid \"52.99\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:bidtime</span>","value":":bidtime"},{"type":"html","content":"<span class='clj-string'>&quot;1.201505&quot;</span>","value":"\"1.201505\""}],"value":"[:bidtime \"1.201505\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:bidder</span>","value":":bidder"},{"type":"html","content":"<span class='clj-string'>&quot;hanna1104&quot;</span>","value":"\"hanna1104\""}],"value":"[:bidder \"hanna1104\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:bidderrate</span>","value":":bidderrate"},{"type":"html","content":"<span class='clj-string'>&quot;94&quot;</span>","value":"\"94\""}],"value":"[:bidderrate \"94\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:openbid</span>","value":":openbid"},{"type":"html","content":"<span class='clj-string'>&quot;49.99&quot;</span>","value":"\"49.99\""}],"value":"[:openbid \"49.99\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:price</span>","value":":price"},{"type":"html","content":"<span class='clj-string'>&quot;311.6&quot;</span>","value":"\"311.6\""}],"value":"[:price \"311.6\"]"}],"value":"{:auctionid \"8211480551\", :bid \"52.99\", :bidtime \"1.201505\", :bidder \"hanna1104\", :bidderrate \"94\", :openbid \"49.99\", :price \"311.6\"}"}
;; <=

;; @@
;; how would you make this better?

(map (fn [val conv] (conv val)) [1 2 3] [inc dec identity])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>3</span>","value":"3"}],"value":"(2 1 3)"}
;; <=

;; @@
;; make a converted version
(def rec-seq
  (let [hdr (map keyword (first data-seq))
        hdr-conv (map type-converter-map hdr)
        convert-row (fn [row]
                      (map (fn [val conv]
                             (conv val))
                           row
                           hdr-conv)
                      )]
    (->> (rest data-seq)
         (map (fn [row] (zipmap hdr (convert-row row)))))))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;lush-stream/rec-seq</span>","value":"#'lush-stream/rec-seq"}
;; <=

;; @@
(take 1 rec-seq)
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:auctionid</span>","value":":auctionid"},{"type":"html","content":"<span class='clj-long'>8211480551</span>","value":"8211480551"}],"value":"[:auctionid 8211480551]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:bid</span>","value":":bid"},{"type":"html","content":"<span class='clj-unkown'>52.99</span>","value":"52.99"}],"value":"[:bid 52.99]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:bidtime</span>","value":":bidtime"},{"type":"html","content":"<span class='clj-long'>1201504945755</span>","value":"1201504945755"}],"value":"[:bidtime 1201504945755]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:bidder</span>","value":":bidder"},{"type":"html","content":"<span class='clj-string'>&quot;hanna1104&quot;</span>","value":"\"hanna1104\""}],"value":"[:bidder \"hanna1104\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:bidderrate</span>","value":":bidderrate"},{"type":"html","content":"<span class='clj-unkown'>94.0</span>","value":"94.0"}],"value":"[:bidderrate 94.0]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:openbid</span>","value":":openbid"},{"type":"html","content":"<span class='clj-unkown'>49.99</span>","value":"49.99"}],"value":"[:openbid 49.99]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:price</span>","value":":price"},{"type":"html","content":"<span class='clj-unkown'>311.6</span>","value":"311.6"}],"value":"[:price 311.6]"}],"value":"{:auctionid 8211480551, :bid 52.99, :bidtime 1201504945755, :bidder \"hanna1104\", :bidderrate 94.0, :openbid 49.99, :price 311.6}"}],"value":"({:auctionid 8211480551, :bid 52.99, :bidtime 1201504945755, :bidder \"hanna1104\", :bidderrate 94.0, :openbid 49.99, :price 311.6})"}
;; <=

;; @@
(plot/list-plot (map :bid rec-seq))
;; @@
;; =>
;;; {"type":"vega","content":{"width":400,"height":247.2187957763672,"padding":{"top":10,"left":50,"bottom":20,"right":10},"data":[{"name":"b9c90c8f-f83a-46cd-9b53-32cd3ab5e965","values":[{"x":0,"y":52.9900016784668},{"x":1,"y":50.9900016784668},{"x":2,"y":101.98999786376953},{"x":3,"y":57.0},{"x":4,"y":144.47999572753906},{"x":5,"y":170.0},{"x":6,"y":189.0},{"x":7,"y":298.4800109863281},{"x":8,"y":200.0},{"x":9,"y":250.0},{"x":10,"y":306.6000061035156},{"x":11,"y":311.6000061035156},{"x":12,"y":10.0},{"x":13,"y":11.0},{"x":14,"y":11.0},{"x":15,"y":13.369999885559082},{"x":16,"y":21.0},{"x":17,"y":15.0},{"x":18,"y":18.0},{"x":19,"y":20.0},{"x":20,"y":25.0},{"x":21,"y":35.0},{"x":22,"y":30.0},{"x":23,"y":40.0},{"x":24,"y":37.5},{"x":25,"y":75.0},{"x":26,"y":42.0},{"x":27,"y":45.0},{"x":28,"y":82.5},{"x":29,"y":99.0},{"x":30,"y":85.0},{"x":31,"y":87.0},{"x":32,"y":89.0},{"x":33,"y":95.0},{"x":34,"y":100.0},{"x":35,"y":110.0},{"x":36,"y":118.5},{"x":37,"y":127.75},{"x":38,"y":115.0},{"x":39,"y":125.0},{"x":40,"y":136.8000030517578},{"x":41,"y":130.0},{"x":42,"y":139.3000030517578},{"x":43,"y":55.0},{"x":44,"y":51.0},{"x":45,"y":54.0},{"x":46,"y":59.65999984741211},{"x":47,"y":57.0},{"x":48,"y":60.0},{"x":49,"y":65.0},{"x":50,"y":65.0},{"x":51,"y":80.0},{"x":52,"y":67.0},{"x":53,"y":69.01000213623047},{"x":54,"y":71.01000213623047},{"x":55,"y":73.01000213623047},{"x":56,"y":80.0},{"x":57,"y":85.0},{"x":58,"y":100.0},{"x":59,"y":90.0},{"x":60,"y":99.0},{"x":61,"y":120.0},{"x":62,"y":115.0},{"x":63,"y":140.0},{"x":64,"y":141.0},{"x":65,"y":130.0},{"x":66,"y":140.0},{"x":67,"y":150.0},{"x":68,"y":150.0},{"x":69,"y":160.0},{"x":70,"y":161.0},{"x":71,"y":15.0},{"x":72,"y":1.25},{"x":73,"y":2.0},{"x":74,"y":5.0},{"x":75,"y":7.510000228881836},{"x":76,"y":30.0},{"x":77,"y":20.0},{"x":78,"y":25.0},{"x":79,"y":30.0},{"x":80,"y":31.0},{"x":81,"y":35.0},{"x":82,"y":40.0},{"x":83,"y":44.0},{"x":84,"y":43.0},{"x":85,"y":45.0},{"x":86,"y":46.0},{"x":87,"y":48.0},{"x":88,"y":50.0},{"x":89,"y":51.0},{"x":90,"y":52.0},{"x":91,"y":53.0},{"x":92,"y":56.0},{"x":93,"y":55.0},{"x":94,"y":61.0},{"x":95,"y":58.0},{"x":96,"y":60.0},{"x":97,"y":61.0},{"x":98,"y":50.0},{"x":99,"y":100.0},{"x":100,"y":51.0},{"x":101,"y":95.0},{"x":102,"y":125.0},{"x":103,"y":105.0},{"x":104,"y":110.0},{"x":105,"y":115.0},{"x":106,"y":120.0},{"x":107,"y":167.5},{"x":108,"y":130.0},{"x":109,"y":135.0},{"x":110,"y":140.0},{"x":111,"y":145.0},{"x":112,"y":150.0},{"x":113,"y":167.5},{"x":114,"y":155.0},{"x":115,"y":160.0},{"x":116,"y":165.0},{"x":117,"y":75.0},{"x":118,"y":100.0},{"x":119,"y":80.0},{"x":120,"y":88.87999725341797},{"x":121,"y":95.0},{"x":122,"y":100.0},{"x":123,"y":30.0},{"x":124,"y":25.0},{"x":125,"y":30.0},{"x":126,"y":31.5},{"x":127,"y":50.0},{"x":128,"y":33.5},{"x":129,"y":40.0},{"x":130,"y":45.0},{"x":131,"y":50.0},{"x":132,"y":55.0},{"x":133,"y":60.0},{"x":134,"y":60.0},{"x":135,"y":63.0},{"x":136,"y":62.0},{"x":137,"y":75.0},{"x":138,"y":70.0},{"x":139,"y":80.0},{"x":140,"y":82.0},{"x":141,"y":125.0},{"x":142,"y":90.0},{"x":143,"y":101.0},{"x":144,"y":106.0},{"x":145,"y":111.0},{"x":146,"y":116.0},{"x":147,"y":121.0},{"x":148,"y":126.0},{"x":149,"y":20.0},{"x":150,"y":20.0},{"x":151,"y":20.5},{"x":152,"y":30.0},{"x":153,"y":31.0},{"x":154,"y":25.0},{"x":155,"y":30.0},{"x":156,"y":35.0},{"x":157,"y":40.0},{"x":158,"y":60.0},{"x":159,"y":70.0},{"x":160,"y":75.0},{"x":161,"y":76.0},{"x":162,"y":75.0},{"x":163,"y":85.0},{"x":164,"y":90.0},{"x":165,"y":95.0},{"x":166,"y":80.0},{"x":167,"y":82.0},{"x":168,"y":90.0},{"x":169,"y":92.0},{"x":170,"y":100.0},{"x":171,"y":100.0},{"x":172,"y":110.0},{"x":173,"y":115.0},{"x":174,"y":120.0},{"x":175,"y":120.0},{"x":176,"y":130.0},{"x":177,"y":135.0},{"x":178,"y":130.0}]}],"marks":[{"type":"symbol","from":{"data":"b9c90c8f-f83a-46cd-9b53-32cd3ab5e965"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"y":{"scale":"y","field":"data.y"},"fill":{"value":"steelblue"},"fillOpacity":{"value":1}},"update":{"shape":"circle","size":{"value":70},"stroke":{"value":"transparent"}},"hover":{"size":{"value":210},"stroke":{"value":"white"}}}}],"scales":[{"name":"x","type":"linear","range":"width","zero":false,"domain":{"data":"b9c90c8f-f83a-46cd-9b53-32cd3ab5e965","field":"data.x"}},{"name":"y","type":"linear","range":"height","nice":true,"zero":false,"domain":{"data":"b9c90c8f-f83a-46cd-9b53-32cd3ab5e965","field":"data.y"}}],"axes":[{"type":"x","scale":"x"},{"type":"y","scale":"y"}]},"value":"#gorilla_repl.vega.VegaView{:content {:width 400, :height 247.2188, :padding {:top 10, :left 50, :bottom 20, :right 10}, :data [{:name \"b9c90c8f-f83a-46cd-9b53-32cd3ab5e965\", :values ({:x 0, :y 52.99} {:x 1, :y 50.99} {:x 2, :y 101.99} {:x 3, :y 57.0} {:x 4, :y 144.48} {:x 5, :y 170.0} {:x 6, :y 189.0} {:x 7, :y 298.48} {:x 8, :y 200.0} {:x 9, :y 250.0} {:x 10, :y 306.6} {:x 11, :y 311.6} {:x 12, :y 10.0} {:x 13, :y 11.0} {:x 14, :y 11.0} {:x 15, :y 13.37} {:x 16, :y 21.0} {:x 17, :y 15.0} {:x 18, :y 18.0} {:x 19, :y 20.0} {:x 20, :y 25.0} {:x 21, :y 35.0} {:x 22, :y 30.0} {:x 23, :y 40.0} {:x 24, :y 37.5} {:x 25, :y 75.0} {:x 26, :y 42.0} {:x 27, :y 45.0} {:x 28, :y 82.5} {:x 29, :y 99.0} {:x 30, :y 85.0} {:x 31, :y 87.0} {:x 32, :y 89.0} {:x 33, :y 95.0} {:x 34, :y 100.0} {:x 35, :y 110.0} {:x 36, :y 118.5} {:x 37, :y 127.75} {:x 38, :y 115.0} {:x 39, :y 125.0} {:x 40, :y 136.8} {:x 41, :y 130.0} {:x 42, :y 139.3} {:x 43, :y 55.0} {:x 44, :y 51.0} {:x 45, :y 54.0} {:x 46, :y 59.66} {:x 47, :y 57.0} {:x 48, :y 60.0} {:x 49, :y 65.0} {:x 50, :y 65.0} {:x 51, :y 80.0} {:x 52, :y 67.0} {:x 53, :y 69.01} {:x 54, :y 71.01} {:x 55, :y 73.01} {:x 56, :y 80.0} {:x 57, :y 85.0} {:x 58, :y 100.0} {:x 59, :y 90.0} {:x 60, :y 99.0} {:x 61, :y 120.0} {:x 62, :y 115.0} {:x 63, :y 140.0} {:x 64, :y 141.0} {:x 65, :y 130.0} {:x 66, :y 140.0} {:x 67, :y 150.0} {:x 68, :y 150.0} {:x 69, :y 160.0} {:x 70, :y 161.0} {:x 71, :y 15.0} {:x 72, :y 1.25} {:x 73, :y 2.0} {:x 74, :y 5.0} {:x 75, :y 7.51} {:x 76, :y 30.0} {:x 77, :y 20.0} {:x 78, :y 25.0} {:x 79, :y 30.0} {:x 80, :y 31.0} {:x 81, :y 35.0} {:x 82, :y 40.0} {:x 83, :y 44.0} {:x 84, :y 43.0} {:x 85, :y 45.0} {:x 86, :y 46.0} {:x 87, :y 48.0} {:x 88, :y 50.0} {:x 89, :y 51.0} {:x 90, :y 52.0} {:x 91, :y 53.0} {:x 92, :y 56.0} {:x 93, :y 55.0} {:x 94, :y 61.0} {:x 95, :y 58.0} {:x 96, :y 60.0} {:x 97, :y 61.0} {:x 98, :y 50.0} {:x 99, :y 100.0} {:x 100, :y 51.0} {:x 101, :y 95.0} {:x 102, :y 125.0} {:x 103, :y 105.0} {:x 104, :y 110.0} {:x 105, :y 115.0} {:x 106, :y 120.0} {:x 107, :y 167.5} {:x 108, :y 130.0} {:x 109, :y 135.0} {:x 110, :y 140.0} {:x 111, :y 145.0} {:x 112, :y 150.0} {:x 113, :y 167.5} {:x 114, :y 155.0} {:x 115, :y 160.0} {:x 116, :y 165.0} {:x 117, :y 75.0} {:x 118, :y 100.0} {:x 119, :y 80.0} {:x 120, :y 88.88} {:x 121, :y 95.0} {:x 122, :y 100.0} {:x 123, :y 30.0} {:x 124, :y 25.0} {:x 125, :y 30.0} {:x 126, :y 31.5} {:x 127, :y 50.0} {:x 128, :y 33.5} {:x 129, :y 40.0} {:x 130, :y 45.0} {:x 131, :y 50.0} {:x 132, :y 55.0} {:x 133, :y 60.0} {:x 134, :y 60.0} {:x 135, :y 63.0} {:x 136, :y 62.0} {:x 137, :y 75.0} {:x 138, :y 70.0} {:x 139, :y 80.0} {:x 140, :y 82.0} {:x 141, :y 125.0} {:x 142, :y 90.0} {:x 143, :y 101.0} {:x 144, :y 106.0} {:x 145, :y 111.0} {:x 146, :y 116.0} {:x 147, :y 121.0} {:x 148, :y 126.0} {:x 149, :y 20.0} {:x 150, :y 20.0} {:x 151, :y 20.5} {:x 152, :y 30.0} {:x 153, :y 31.0} {:x 154, :y 25.0} {:x 155, :y 30.0} {:x 156, :y 35.0} {:x 157, :y 40.0} {:x 158, :y 60.0} {:x 159, :y 70.0} {:x 160, :y 75.0} {:x 161, :y 76.0} {:x 162, :y 75.0} {:x 163, :y 85.0} {:x 164, :y 90.0} {:x 165, :y 95.0} {:x 166, :y 80.0} {:x 167, :y 82.0} {:x 168, :y 90.0} {:x 169, :y 92.0} {:x 170, :y 100.0} {:x 171, :y 100.0} {:x 172, :y 110.0} {:x 173, :y 115.0} {:x 174, :y 120.0} {:x 175, :y 120.0} {:x 176, :y 130.0} {:x 177, :y 135.0} {:x 178, :y 130.0})}], :marks [{:type \"symbol\", :from {:data \"b9c90c8f-f83a-46cd-9b53-32cd3ab5e965\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :y {:scale \"y\", :field \"data.y\"}, :fill {:value \"steelblue\"}, :fillOpacity {:value 1}}, :update {:shape \"circle\", :size {:value 70}, :stroke {:value \"transparent\"}}, :hover {:size {:value 210}, :stroke {:value \"white\"}}}}], :scales [{:name \"x\", :type \"linear\", :range \"width\", :zero false, :domain {:data \"b9c90c8f-f83a-46cd-9b53-32cd3ab5e965\", :field \"data.x\"}} {:name \"y\", :type \"linear\", :range \"height\", :nice true, :zero false, :domain {:data \"b9c90c8f-f83a-46cd-9b53-32cd3ab5e965\", :field \"data.y\"}}], :axes [{:type \"x\", :scale \"x\"} {:type \"y\", :scale \"y\"}]}}"}
;; <=

;; @@
(plot/histogram (map :price rec-seq))
;; @@
;; =>
;;; {"type":"vega","content":{"width":400,"height":247.2187957763672,"padding":{"top":10,"left":50,"bottom":20,"right":10},"data":[{"name":"cdaa2cdb-21c8-452c-8413-0bce406c121f","values":[{"x":61.0,"y":0},{"x":88.84444512261285,"y":27.0},{"x":116.6888902452257,"y":6.0},{"x":144.53333536783856,"y":57.0},{"x":172.3777804904514,"y":47.0},{"x":200.22222561306427,"y":0.0},{"x":228.06667073567712,"y":0.0},{"x":255.91111585828997,"y":0.0},{"x":283.7555609809028,"y":0.0},{"x":311.6000061035157,"y":42.0},{"x":339.44445122612854,"y":0}]}],"marks":[{"type":"line","from":{"data":"cdaa2cdb-21c8-452c-8413-0bce406c121f"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"y":{"scale":"y","field":"data.y"},"interpolate":{"value":"step-before"},"fill":{"value":"steelblue"},"fillOpacity":{"value":0.4},"stroke":{"value":"steelblue"},"strokeWidth":{"value":2},"strokeOpacity":{"value":1}}}}],"scales":[{"name":"x","type":"linear","range":"width","zero":false,"domain":{"data":"cdaa2cdb-21c8-452c-8413-0bce406c121f","field":"data.x"}},{"name":"y","type":"linear","range":"height","nice":true,"zero":false,"domain":{"data":"cdaa2cdb-21c8-452c-8413-0bce406c121f","field":"data.y"}}],"axes":[{"type":"x","scale":"x"},{"type":"y","scale":"y"}]},"value":"#gorilla_repl.vega.VegaView{:content {:width 400, :height 247.2188, :padding {:top 10, :left 50, :bottom 20, :right 10}, :data [{:name \"cdaa2cdb-21c8-452c-8413-0bce406c121f\", :values ({:x 61.0, :y 0} {:x 88.84444512261285, :y 27.0} {:x 116.6888902452257, :y 6.0} {:x 144.53333536783856, :y 57.0} {:x 172.3777804904514, :y 47.0} {:x 200.22222561306427, :y 0.0} {:x 228.06667073567712, :y 0.0} {:x 255.91111585828997, :y 0.0} {:x 283.7555609809028, :y 0.0} {:x 311.6000061035157, :y 42.0} {:x 339.44445122612854, :y 0})}], :marks [{:type \"line\", :from {:data \"cdaa2cdb-21c8-452c-8413-0bce406c121f\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :y {:scale \"y\", :field \"data.y\"}, :interpolate {:value \"step-before\"}, :fill {:value \"steelblue\"}, :fillOpacity {:value 0.4}, :stroke {:value \"steelblue\"}, :strokeWidth {:value 2}, :strokeOpacity {:value 1}}}}], :scales [{:name \"x\", :type \"linear\", :range \"width\", :zero false, :domain {:data \"cdaa2cdb-21c8-452c-8413-0bce406c121f\", :field \"data.x\"}} {:name \"y\", :type \"linear\", :range \"height\", :nice true, :zero false, :domain {:data \"cdaa2cdb-21c8-452c-8413-0bce406c121f\", :field \"data.y\"}}], :axes [{:type \"x\", :scale \"x\"} {:type \"y\", :scale \"y\"}]}}"}
;; <=

;; **
;;; # zipper demo
;;; 
;;; see a nice bit of code for zipping with maps
;;; 
;;; https://clojuredocs.org/clojure.zip/zipper
;;; 
;;; this is a heterogenous structure which makes this not a good example of a zipper, but anyway:
;; **

;; @@
(require '(clojure [zip :as z]))
;; Adds zip support for maps.
;; (Source: http://stackoverflow.com/a/15020649/42188)
(defn map-zipper [m]
  (z/zipper 
    (fn [x] (or (map? x) (map? (nth x 1))))
    (fn [x] (seq (if (map? x) x (nth x 1))))
    (fn [x children] 
      (if (map? x) 
        (into {} children) 
        (assoc x 1 (into {} children))))
    m))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;lush-stream/map-zipper</span>","value":"#'lush-stream/map-zipper"}
;; <=

;; @@
(def rec-zip (map-zipper rec-seq))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;lush-stream/rec-zip</span>","value":"#'lush-stream/rec-zip"}
;; <=

;; @@
(-> rec-zip
    z/down
    z/right
    z/right
    z/right
    z/right
    z/node)
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:bidderrate</span>","value":":bidderrate"},{"type":"html","content":"<span class='clj-unkown'>90.0</span>","value":"90.0"}],"value":"[:bidderrate 90.0]"}
;; <=

;; @@
(-> rec-zip
    z/down
    z/right
    z/right
    z/right
    z/right
    (z/edit (fn [[k v]] k))
    z/node)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-keyword'>:bidderrate</span>","value":":bidderrate"}
;; <=

;; @@
(-> rec-zip
    z/down
    z/right
    z/right
    z/right
    z/right
    (z/edit (fn [[k v]] [k (* 150 v)]))
    z/node)
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:bidderrate</span>","value":":bidderrate"},{"type":"html","content":"<span class='clj-double'>13500.0</span>","value":"13500.0"}],"value":"[:bidderrate 13500.0]"}
;; <=

;; @@
(-> rec-zip
    z/down
    z/right
    z/right
    z/right
    z/right
    (z/edit (fn [[k v]] [k (* 150 v)]))
    z/up) ;; consequences of mixing up the type
;; @@

;; **
;;; ## alternative method / fix
;; **

;; @@
(nth rec-seq 10)
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:auctionid</span>","value":":auctionid"},{"type":"html","content":"<span class='clj-long'>8211480551</span>","value":"8211480551"}],"value":"[:auctionid 8211480551]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:bid</span>","value":":bid"},{"type":"html","content":"<span class='clj-unkown'>306.6</span>","value":"306.6"}],"value":"[:bid 306.6]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:bidtime</span>","value":":bidtime"},{"type":"html","content":"<span class='clj-long'>6633934974670</span>","value":"6633934974670"}],"value":"[:bidtime 6633934974670]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:bidder</span>","value":":bidder"},{"type":"html","content":"<span class='clj-string'>&quot;pkfury&quot;</span>","value":"\"pkfury\""}],"value":"[:bidder \"pkfury\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:bidderrate</span>","value":":bidderrate"},{"type":"html","content":"<span class='clj-unkown'>11.0</span>","value":"11.0"}],"value":"[:bidderrate 11.0]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:openbid</span>","value":":openbid"},{"type":"html","content":"<span class='clj-unkown'>49.99</span>","value":"49.99"}],"value":"[:openbid 49.99]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:price</span>","value":":price"},{"type":"html","content":"<span class='clj-unkown'>311.6</span>","value":"311.6"}],"value":"[:price 311.6]"}],"value":"{:auctionid 8211480551, :bid 306.6, :bidtime 6633934974670, :bidder \"pkfury\", :bidderrate 11.0, :openbid 49.99, :price 311.6}"}
;; <=

;; @@
;; better method
(get-in (nth rec-seq 10)
        [:openbid])
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>49.99</span>","value":"49.99"}
;; <=

;; @@
;; or use the homogeneous structure
(-> (z/vector-zip (vec data-seq))
    z/down
    z/right
    z/node)
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;8211480551&quot;</span>","value":"\"8211480551\""},{"type":"html","content":"<span class='clj-string'>&quot;52.99&quot;</span>","value":"\"52.99\""},{"type":"html","content":"<span class='clj-string'>&quot;1.201505&quot;</span>","value":"\"1.201505\""},{"type":"html","content":"<span class='clj-string'>&quot;hanna1104&quot;</span>","value":"\"hanna1104\""},{"type":"html","content":"<span class='clj-string'>&quot;94&quot;</span>","value":"\"94\""},{"type":"html","content":"<span class='clj-string'>&quot;49.99&quot;</span>","value":"\"49.99\""},{"type":"html","content":"<span class='clj-string'>&quot;311.6&quot;</span>","value":"\"311.6\""}],"value":"[\"8211480551\" \"52.99\" \"1.201505\" \"hanna1104\" \"94\" \"49.99\" \"311.6\"]"}
;; <=

;; @@
(-> (z/vector-zip (vec data-seq))
    z/down
    z/right
    z/right
    z/right
    z/right
    z/down
    z/right
    z/right
    z/right
    z/right
    z/up
    z/node)
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;8211480551&quot;</span>","value":"\"8211480551\""},{"type":"html","content":"<span class='clj-string'>&quot;57&quot;</span>","value":"\"57\""},{"type":"html","content":"<span class='clj-string'>&quot;1.708437&quot;</span>","value":"\"1.708437\""},{"type":"html","content":"<span class='clj-string'>&quot;newberryhwt&quot;</span>","value":"\"newberryhwt\""},{"type":"html","content":"<span class='clj-string'>&quot;14&quot;</span>","value":"\"14\""},{"type":"html","content":"<span class='clj-string'>&quot;49.99&quot;</span>","value":"\"49.99\""},{"type":"html","content":"<span class='clj-string'>&quot;311.6&quot;</span>","value":"\"311.6\""}],"value":"[\"8211480551\" \"57\" \"1.708437\" \"newberryhwt\" \"14\" \"49.99\" \"311.6\"]"}
;; <=

;; @@
(-> (z/vector-zip (vec data-seq))
    z/down
    z/right
    z/right
    z/right
    z/right
    z/down
    z/right
    z/right
    z/right
    z/right
    z/right
    (z/edit (fn [openbid-str]
              (* 3 (Float/parseFloat openbid-str))))
    z/up
    z/node)
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;8211480551&quot;</span>","value":"\"8211480551\""},{"type":"html","content":"<span class='clj-string'>&quot;57&quot;</span>","value":"\"57\""},{"type":"html","content":"<span class='clj-string'>&quot;1.708437&quot;</span>","value":"\"1.708437\""},{"type":"html","content":"<span class='clj-string'>&quot;newberryhwt&quot;</span>","value":"\"newberryhwt\""},{"type":"html","content":"<span class='clj-string'>&quot;14&quot;</span>","value":"\"14\""},{"type":"html","content":"<span class='clj-double'>149.9700050354004</span>","value":"149.9700050354004"},{"type":"html","content":"<span class='clj-string'>&quot;311.6&quot;</span>","value":"\"311.6\""}],"value":"[\"8211480551\" \"57\" \"1.708437\" \"newberryhwt\" \"14\" 149.9700050354004 \"311.6\"]"}
;; <=

;; @@
(-> (z/vector-zip (vec data-seq))
    z/down
    z/right
    z/right
    z/right
    z/right
    z/down
    z/right
    z/right
    z/right
    z/right
    z/right
    (z/edit (fn [openbid-str]
              (* 3 (Float/parseFloat openbid-str))))
    z/up
    z/up
    z/node
    (#(take 10 %)))
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;auctionid&quot;</span>","value":"\"auctionid\""},{"type":"html","content":"<span class='clj-string'>&quot;bid&quot;</span>","value":"\"bid\""},{"type":"html","content":"<span class='clj-string'>&quot;bidtime&quot;</span>","value":"\"bidtime\""},{"type":"html","content":"<span class='clj-string'>&quot;bidder&quot;</span>","value":"\"bidder\""},{"type":"html","content":"<span class='clj-string'>&quot;bidderrate&quot;</span>","value":"\"bidderrate\""},{"type":"html","content":"<span class='clj-string'>&quot;openbid&quot;</span>","value":"\"openbid\""},{"type":"html","content":"<span class='clj-string'>&quot;price&quot;</span>","value":"\"price\""}],"value":"[\"auctionid\" \"bid\" \"bidtime\" \"bidder\" \"bidderrate\" \"openbid\" \"price\"]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;8211480551&quot;</span>","value":"\"8211480551\""},{"type":"html","content":"<span class='clj-string'>&quot;52.99&quot;</span>","value":"\"52.99\""},{"type":"html","content":"<span class='clj-string'>&quot;1.201505&quot;</span>","value":"\"1.201505\""},{"type":"html","content":"<span class='clj-string'>&quot;hanna1104&quot;</span>","value":"\"hanna1104\""},{"type":"html","content":"<span class='clj-string'>&quot;94&quot;</span>","value":"\"94\""},{"type":"html","content":"<span class='clj-string'>&quot;49.99&quot;</span>","value":"\"49.99\""},{"type":"html","content":"<span class='clj-string'>&quot;311.6&quot;</span>","value":"\"311.6\""}],"value":"[\"8211480551\" \"52.99\" \"1.201505\" \"hanna1104\" \"94\" \"49.99\" \"311.6\"]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;8211480551&quot;</span>","value":"\"8211480551\""},{"type":"html","content":"<span class='clj-string'>&quot;50.99&quot;</span>","value":"\"50.99\""},{"type":"html","content":"<span class='clj-string'>&quot;1.203843&quot;</span>","value":"\"1.203843\""},{"type":"html","content":"<span class='clj-string'>&quot;wrufai1&quot;</span>","value":"\"wrufai1\""},{"type":"html","content":"<span class='clj-string'>&quot;90&quot;</span>","value":"\"90\""},{"type":"html","content":"<span class='clj-string'>&quot;49.99&quot;</span>","value":"\"49.99\""},{"type":"html","content":"<span class='clj-string'>&quot;311.6&quot;</span>","value":"\"311.6\""}],"value":"[\"8211480551\" \"50.99\" \"1.203843\" \"wrufai1\" \"90\" \"49.99\" \"311.6\"]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;8211480551&quot;</span>","value":"\"8211480551\""},{"type":"html","content":"<span class='clj-string'>&quot;101.99&quot;</span>","value":"\"101.99\""},{"type":"html","content":"<span class='clj-string'>&quot;1.204433&quot;</span>","value":"\"1.204433\""},{"type":"html","content":"<span class='clj-string'>&quot;wrufai1&quot;</span>","value":"\"wrufai1\""},{"type":"html","content":"<span class='clj-string'>&quot;90&quot;</span>","value":"\"90\""},{"type":"html","content":"<span class='clj-string'>&quot;49.99&quot;</span>","value":"\"49.99\""},{"type":"html","content":"<span class='clj-string'>&quot;311.6&quot;</span>","value":"\"311.6\""}],"value":"[\"8211480551\" \"101.99\" \"1.204433\" \"wrufai1\" \"90\" \"49.99\" \"311.6\"]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;8211480551&quot;</span>","value":"\"8211480551\""},{"type":"html","content":"<span class='clj-string'>&quot;57&quot;</span>","value":"\"57\""},{"type":"html","content":"<span class='clj-string'>&quot;1.708437&quot;</span>","value":"\"1.708437\""},{"type":"html","content":"<span class='clj-string'>&quot;newberryhwt&quot;</span>","value":"\"newberryhwt\""},{"type":"html","content":"<span class='clj-string'>&quot;14&quot;</span>","value":"\"14\""},{"type":"html","content":"<span class='clj-double'>149.9700050354004</span>","value":"149.9700050354004"},{"type":"html","content":"<span class='clj-string'>&quot;311.6&quot;</span>","value":"\"311.6\""}],"value":"[\"8211480551\" \"57\" \"1.708437\" \"newberryhwt\" \"14\" 149.9700050354004 \"311.6\"]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;8211480551&quot;</span>","value":"\"8211480551\""},{"type":"html","content":"<span class='clj-string'>&quot;144.48&quot;</span>","value":"\"144.48\""},{"type":"html","content":"<span class='clj-string'>&quot;3.089711&quot;</span>","value":"\"3.089711\""},{"type":"html","content":"<span class='clj-string'>&quot;miloo2005&quot;</span>","value":"\"miloo2005\""},{"type":"html","content":"<span class='clj-string'>&quot;3&quot;</span>","value":"\"3\""},{"type":"html","content":"<span class='clj-string'>&quot;49.99&quot;</span>","value":"\"49.99\""},{"type":"html","content":"<span class='clj-string'>&quot;311.6&quot;</span>","value":"\"311.6\""}],"value":"[\"8211480551\" \"144.48\" \"3.089711\" \"miloo2005\" \"3\" \"49.99\" \"311.6\"]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;8211480551&quot;</span>","value":"\"8211480551\""},{"type":"html","content":"<span class='clj-string'>&quot;170&quot;</span>","value":"\"170\""},{"type":"html","content":"<span class='clj-string'>&quot;4.187963&quot;</span>","value":"\"4.187963\""},{"type":"html","content":"<span class='clj-string'>&quot;fzakhour&quot;</span>","value":"\"fzakhour\""},{"type":"html","content":"<span class='clj-string'>&quot;5&quot;</span>","value":"\"5\""},{"type":"html","content":"<span class='clj-string'>&quot;49.99&quot;</span>","value":"\"49.99\""},{"type":"html","content":"<span class='clj-string'>&quot;311.6&quot;</span>","value":"\"311.6\""}],"value":"[\"8211480551\" \"170\" \"4.187963\" \"fzakhour\" \"5\" \"49.99\" \"311.6\"]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;8211480551&quot;</span>","value":"\"8211480551\""},{"type":"html","content":"<span class='clj-string'>&quot;189&quot;</span>","value":"\"189\""},{"type":"html","content":"<span class='clj-string'>&quot;5.24581&quot;</span>","value":"\"5.24581\""},{"type":"html","content":"<span class='clj-string'>&quot;bbcboi2007&quot;</span>","value":"\"bbcboi2007\""},{"type":"html","content":"<span class='clj-string'>&quot;1&quot;</span>","value":"\"1\""},{"type":"html","content":"<span class='clj-string'>&quot;49.99&quot;</span>","value":"\"49.99\""},{"type":"html","content":"<span class='clj-string'>&quot;311.6&quot;</span>","value":"\"311.6\""}],"value":"[\"8211480551\" \"189\" \"5.24581\" \"bbcboi2007\" \"1\" \"49.99\" \"311.6\"]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;8211480551&quot;</span>","value":"\"8211480551\""},{"type":"html","content":"<span class='clj-string'>&quot;298.48&quot;</span>","value":"\"298.48\""},{"type":"html","content":"<span class='clj-string'>&quot;5.863021&quot;</span>","value":"\"5.863021\""},{"type":"html","content":"<span class='clj-string'>&quot;miloo2005&quot;</span>","value":"\"miloo2005\""},{"type":"html","content":"<span class='clj-string'>&quot;3&quot;</span>","value":"\"3\""},{"type":"html","content":"<span class='clj-string'>&quot;49.99&quot;</span>","value":"\"49.99\""},{"type":"html","content":"<span class='clj-string'>&quot;311.6&quot;</span>","value":"\"311.6\""}],"value":"[\"8211480551\" \"298.48\" \"5.863021\" \"miloo2005\" \"3\" \"49.99\" \"311.6\"]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;8211480551&quot;</span>","value":"\"8211480551\""},{"type":"html","content":"<span class='clj-string'>&quot;200&quot;</span>","value":"\"200\""},{"type":"html","content":"<span class='clj-string'>&quot;6.334815&quot;</span>","value":"\"6.334815\""},{"type":"html","content":"<span class='clj-string'>&quot;laxwarrior07&quot;</span>","value":"\"laxwarrior07\""},{"type":"html","content":"<span class='clj-string'>&quot;12&quot;</span>","value":"\"12\""},{"type":"html","content":"<span class='clj-string'>&quot;49.99&quot;</span>","value":"\"49.99\""},{"type":"html","content":"<span class='clj-string'>&quot;311.6&quot;</span>","value":"\"311.6\""}],"value":"[\"8211480551\" \"200\" \"6.334815\" \"laxwarrior07\" \"12\" \"49.99\" \"311.6\"]"}],"value":"([\"auctionid\" \"bid\" \"bidtime\" \"bidder\" \"bidderrate\" \"openbid\" \"price\"] [\"8211480551\" \"52.99\" \"1.201505\" \"hanna1104\" \"94\" \"49.99\" \"311.6\"] [\"8211480551\" \"50.99\" \"1.203843\" \"wrufai1\" \"90\" \"49.99\" \"311.6\"] [\"8211480551\" \"101.99\" \"1.204433\" \"wrufai1\" \"90\" \"49.99\" \"311.6\"] [\"8211480551\" \"57\" \"1.708437\" \"newberryhwt\" \"14\" 149.9700050354004 \"311.6\"] [\"8211480551\" \"144.48\" \"3.089711\" \"miloo2005\" \"3\" \"49.99\" \"311.6\"] [\"8211480551\" \"170\" \"4.187963\" \"fzakhour\" \"5\" \"49.99\" \"311.6\"] [\"8211480551\" \"189\" \"5.24581\" \"bbcboi2007\" \"1\" \"49.99\" \"311.6\"] [\"8211480551\" \"298.48\" \"5.863021\" \"miloo2005\" \"3\" \"49.99\" \"311.6\"] [\"8211480551\" \"200\" \"6.334815\" \"laxwarrior07\" \"12\" \"49.99\" \"311.6\"])"}
;; <=

;; **
;;; # Mark's puzzle (from August meetup)
;;; 
;;; Your job is to answer the following questions:
;;; 
;;; - What's the format?
;;; - How is it encoded below?
;;; - What do you get when you decode it?
;;; - What's the secret encoded in the format?  (this will be obvious after answering the questions above)
;;; 
;;; 
;;; 
;; **

;; @@
(def bosclj-data
  [[:b 7 2 1 2 2 2 1 2 1 2 7]
   [:b 1 5 1 3 3 1 3 2 2 1 1 5 1]
   [:b 1 1 3 1 1 1 3 1 3 1 1 3 1 1 1 1 3 1 1]
   [:b 1 1 3 1 1 2 3 1 3 2 2 2 1 1 3 1 1]
   [:b 1 1 3 1 1 3 1 3 1 2 3 2 1 1 3 1 1]
   [:b 1 5 1 2 1 3 1 3 1 1 1 2 1 5 1]
   [:b 7 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 7]
   [:w 8 3 1 3 1 2 1 1 9]
   [:b 3 1 5 1 2 2 4 1 4 3 1 2]
   [:w 4 1 2 1 2 2 2 2 1 2 1 1 1 1 2 1 2 1]
   [:b 1 1 1 1 1 1 4 3 1 2 1 4 2 1 1 1 3]
   [:b 1 1 1 5 1 2 1 5 1 2 2 5 1 1]
   [:b 2 1 4 1 1 3 1 2 3 1 5 1 1 1 2]
   [:b 1 1 2 1 1 1 1 3 3 2 2 3 2 2 1 2 1]
   [:w 1 4 1 3 2 2 1 1 1 1 1 1 2 2 3 1 2]
   [:b 1 1 2 1 1 4 1 1 9 2 3 1 1 1]
   [:w 1 1 1 1 1 4 2 1 2 4 1 4 2 1 1 2]
   [:w 1 1 2 2 1 1 1 3 2 1 1 2 2 1 1 1 2 2 1 1]
   [:b 1 1 1 1 4 5 1 3 1 4 2 3 2]
   [:w 1 1 3 1 2 1 2 1 5 2 1 3 2 1 1 1 1]
   [:b 1 2 4 5 1 3 9 4]
   [:w 8 2 1 3 2 1 2 2 3 1 1 3]
   [:b 7 1 2 1 2 1 1 2 1 1 2 1 1 1 2 1 2]
   [:b 1 5 1 1 3 1 3 2 2 1 1 3 2 1 2]
   [:b 1 1 3 1 1 1 1 2 1 2 1 2 1 2 5 3 1]
   [:b 1 1 3 1 1 4 1 2 1 1 2 2 2 2 1 1 3]
   [:b 1 1 3 1 1 1 1 1 1 2 1 3 2 1 1 2 3 2 1]
   [:b 1 5 1 1 1 2 1 4 2 1 2 1 2 3 1 1]
   [:b 7 1 1 1 1 1 1 2 3 1 2 2 3 1 2]])
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;lush-stream/bosclj-data</span>","value":"#'lush-stream/bosclj-data"}
;; <=

;; **
;;; ### group 1
;; **

;; @@
(defn bw-seq [color]
  (cons color (lazy-seq (bw-seq (case color
                                  :b :w
                                  :w :b)))))
(defn make-pixels [[color count]]
  (repeat count (case color
                  :w "0 "
                  :b "1 ")))                                  
;; more elegant
(defn bw-seq [color]
  (cycle (color {:b [:b :w] :w [:w :b]}))) 

(defn make-pixels [[color count]]
  (repeat count (color {:w "0 " :b "1 "})))

(defn zip-row [row]
  (map vector (bw-seq (first row)) (rest row)))

(defn make-row [row]
  (clojure.string/join (flatten (map make-pixels (zip-row row)))))

(defn make-image [data]
  (clojure.string/join
   ["P1\n29 29\n"
    (clojure.string/join "\n" (map make-row data))]))

(defn make-image2 [data]
  (clojure.string/join
   (conj (interpose "\n" (map make-row bosclj-data))
         (format "P1\n%1s %2s\n"
                 (apply + (rest (first data)))
                 (count data)))))

(spit "/tmp/test.pbm" (make-image2 bosclj-data))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; ### group 2
;; **

;; @@
(def color-map {:b \u2588 :w " "})

(defn pair-decode
  [k p]
  (let [s1 (repeat (first p) k)
        s2 (if (second p)
             (repeat (second p) ({:b :w :w :b} k))
             '())]
    (into s2 s1)))

(defn line-decode
  [l]
  (let [k (first l)
        data (partition-all 2 (rest l))]
    (apply str (map color-map (mapcat #(pair-decode k %) data)))))

(defn image-decode
  [data]
  (->> bosclj-data
       (map line-decode)
       (clojure.string/join \newline)
       print))

;; ------------- alternate solution - less verbose
(defn color-seq
  [k]
  (iterate #({:b :w :w :b} %) k))

(defn line-decode
  [[k & vals]] 
  (mapcat #(repeat (first %) (second %)) 
          (map vector vals (color-seq k))))

;; simplified
(defn line-decode 
  [[k & vals]] 
  (mapcat repeat vals (color-seq k)))

(defn image-decode
  [data]
  (let [decoded-data (for [r data]
                       (reduce #(str %1 ({:b \u2588 :w " "} %2)) 
                               \newline 
                               (line-decode r)))]
    (print (apply str decoded-data))))

(defn line-decode 
  [[k & vals]] 
  (mapcat repeat vals (cycle [k ({:b :w :w :b} k)])))

(defn image-decode
  [data]
  (map #(apply str (map {:b 1 :w 0} (line-decode %1))) data))

;; view in ascii  
(println (interpose \newline (image-decode bosclj-data)))

;; @@
;; ->
;;; (11111110010011001001001111111 
;;;  10000010001110111001101000001 
;;;  10111010111011101000101011101 
;;;  10111010011101110011001011101 
;;;  10111010001000100111001011101 
;;;  10000010010001000101001000001 
;;;  11111110101010101010101111111 
;;;  00000000111011101101000000000 
;;;  11101111101100111101111000100 
;;;  00001001001100110110101001001 
;;;  10101011110001001000011010111 
;;;  10100000100100000100110000010 
;;;  11011110100010011101111101011 
;;;  10110101000111001100011001001 
;;;  01111011100110101010011000100 
;;;  10110100001011111111100111010 
;;;  01010111100100111101111001011 
;;;  01001101011100101100101001101 
;;;  10101111000001000100001100011 
;;;  01000100100100000110111001010 
;;;  10011110000010001111111110000 
;;;  00000000110111001001100010111 
;;;  11111110110110100101101011011 
;;;  10000010111011100110100011011 
;;;  10111010100100100100111110001 
;;;  10111010000100101100110010111 
;;;  10111010101001000110100111001 
;;;  10000010100100001101101100010 
;;;  11111110101010011101100111011)
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; ## challenge: make a visualizer right here
;; **

;; @@

;; @@
