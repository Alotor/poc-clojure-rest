(ns rest.base.views
  (:require
    [hiccup.page :refer [include-css include-js]]
    [hiccup.core :refer [html]]))

(def head
  [:head (include-css "/css/normalize.css")
         (include-css "/css/foundation.css")
         ;(include-js "/js/foundation.min.js")
          ])

(defn body
  [& content]
  [:body {:class "row"}
   (into [:div {:class "columns small-12"}] content)])

(defn layout [& content]
  (html
    head
    (apply body content)))

(defn not-found [_]
  (layout [:h1 "Page not found"]))
