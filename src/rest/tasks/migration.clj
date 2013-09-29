; Module to store database migrations
(ns rest.tasks.migration
  (:require
    [rest.tasks.config :as config]
    [rest.tasks.models :as models]
    [lobos.connectivity :as conn]
    [lobos.core :as lobos]
    [lobos.schema :as s]
    [lobos.migration :as migration]
    [korma.core :as korma]))

(defn insert-test-users []
  (korma/insert models/users
    (korma/values [
      {:email "admin@test.com" :password "admin"}
      {:email "user@test.com"  :password "user"}])))

(migration/defmigration add-users-table-migration
  (migration/up []
    (do
       (models/add-users-table)
       (insert-test-users)))
  (migration/down []
    (lobos/drop (s/table :users))))

(migration/defmigration add-tasks-table-migration
  (migration/up []
    (models/add-tasks-table))
  (migration/down []
    (lobos/drop (s/table :tasks))))

(conn/open-global config/database-spec)
(defn execute-migration []
  (binding [migration/*migrations-namespace* 'rest.tasks.migration
            migration/*reload-migrations* false]
    (do
      (println ">> Migration start")
      (lobos/migrate config/database-spec)
      (println "<< Migration end"))))

(defn restore-database []
  (binding [migration/*migrations-namespace* 'rest.tasks.migration
            migration/*reload-migrations* false]
    (do
      (println ">> Rollback all migrations")
      (lobos/rollback config/database-spec :all)
      (println "<< Rollback finished")
      (println ">> Migration start")
      (lobos/migrate config/database-spec)
      (println "<< Migration end"))))
