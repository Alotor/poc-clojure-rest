(ns rest.core
  (:require
    [rest.tasks.routes :refer [tasks-routes]]
    [compojure.handler :as handler]))

(def app
  (handler/site tasks-routes))
