(ns rest.tasks.routes
  (:require
    [compojure.core :refer [defroutes ANY]]
    [rest.tasks.resources :as resources]))

(defroutes tasks-routes
  (ANY "/tasks" [] resources/tasks-resources))
