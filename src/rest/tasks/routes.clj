(ns rest.tasks.routes
  (:require
    [compojure.core :refer [defroutes ANY context]]
    [cemerick.friend :as friend]
    [rest.tasks.resources :as resources]))

(defroutes tasks-routes
  (context "/tasks" []
    (ANY "/" request (friend/authenticated (fn [context] (resources/tasks context))))
    (ANY "/:id" request (friend/authenticated (fn [context] (resources/task context))))
  ))
