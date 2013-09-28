(ns rest.tasks.layouts
  (:require
    [hiccup.core :refer [html]]))


(defn tasks []
  (html [:h1 "Tasks"]))
