(ns rest.tasks.routes
  (:require
    [compojure.core :refer [defroutes ANY]]
    [compojure.route :as route]
    [rest.tasks.resources :as resources]
    [rest.tasks.layouts :as layouts]))

(defroutes tasks-routes
  (ANY "/tasks" [] resources/tasks-resources)
  (route/resources "/")
  (route/not-found layouts/not-found))
