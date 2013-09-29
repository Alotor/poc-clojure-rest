(ns rest.base.routes
  (:require
    [compojure.core :refer [defroutes]]
    [compojure.route :as route]
    [rest.base.views :as views]))

(defroutes base-routes
  (route/resources "/")
  (route/not-found views/not-found))
