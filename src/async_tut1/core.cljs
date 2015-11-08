(ns async-tut1.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [goog.dom :as dom]
            [goog.events :as events]
            [cljs.core.async :refer [put! chan <!]]))

(enable-console-print!)

(println "Hello world!")

(defn listen [e1 type]
  (let [out (chan)]
    ;; Listen to events 
    (events/listen e1 type
                   (fn [e]
                     (put! out e)))
    out))

;; Test the `listen` function.
(let [clicks (listen (dom/getElement "search") "click")]
  (go (while true
        (println (<! clicks)))))
