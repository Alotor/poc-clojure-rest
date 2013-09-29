(ns rest.tasks.views
  (:require
    [hiccup.core :refer [html]]
    [hiccup.element :refer [ordered-list]]
    [hiccup.page :refer [include-js]]))


(defn tasks []
  (html [:h1 "Tasks"
         (ordered-list {:id "tasks"} [])
         (include-js "/js/vendor/require.js")
         (include-js "/js/config.js")
         (include-js "/js/main.js")]))

(defn not-found [_]
  (html [:h1 "Page not found"]))
