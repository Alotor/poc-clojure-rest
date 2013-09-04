(ns rest.tasks.core
  (:require [liberator.core :refer [resource defresource]]
            [compojure.core :refer [defroutes ANY]]
            [korma.db :refer [defdb sqlite3]]
            [korma.core :refer :all]))

(defdb db (sqlite3 {:db "tasks.db"}))

(defentity tasks
  (pk :id)
  (table :tasks)
  (database db)
  (entity-fields :title :description :completed))

(defresource home-resource
  :available-media-types ["application/json"]
  :handle-ok 
    (fn [context]
      (do
        (println "OK")
        (select tasks))))

(defroutes app
  (ANY "/" [] home-resource))
