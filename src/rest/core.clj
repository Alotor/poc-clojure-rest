(ns rest.core
  (:require
    [compojure.handler :as handler]
    [compojure.core :refer [routes]]
    [rest.tasks.routes :refer [tasks-routes]]
    [rest.base.routes :refer [base-routes]]))

(def app
  (handler/site (routes tasks-routes
                        base-routes)))
