(require '[cljs.build.api :as b])

(let [start (System/nanoTime)]
  (b/watch "src"
           {:main 'async-tut1.core
            :output-to "out/async_tut1.js"
            :output-dir "out"
            :verbose true})
  (println "... done. Elapsed" (/ (- (System/nanoTime) start) 1e9) "secondss"))
