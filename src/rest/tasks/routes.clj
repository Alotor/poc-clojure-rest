(ns rest.tasks.routes
  (:require
    [compojure.core :refer [defroutes GET]]
    [rest.tasks.resources :as resources]))

(defroutes tasks-routes
  (GET "/tasks" [] resources/tasks-resources))
