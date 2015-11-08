(ns async-tut1.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [goog.dom :as dom]
            [goog.events :as events]
            [cljs.core.async :refer [put! chan <!]])
  (:import [goog.net Jsonp]
           [goog Uri]))

(enable-console-print!)

(println "Hello world!")

(def wiki-search-url "http://en.wikipedia.org/w/api.php?action=opensearch&format=json&search=")

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

(defn jsonp [uri]
  (let [out (chan)
        req (Jsonp. (Uri. uri))]
    (.send req nil (fn [res] (put! out res)))
    out))

(defn query-url [q]
  (str wiki-search-url q))

(go (println (<! (jsonp (query-url "cats")))))
