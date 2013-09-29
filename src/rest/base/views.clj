(ns rest.base.views
  (:require
    [hiccup.core :refer [html]]))

(defn not-found [_]
  (html [:h1 "Page not found"]))
