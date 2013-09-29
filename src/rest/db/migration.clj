; Module to store database migrations
(ns rest.db.migration
  (:require
    [rest.auth.services :refer [encrypt-password]]
    [rest.db.config :as config]
    [rest.auth.models :refer [users add-users-table]]
    [rest.tasks.models :refer [tasks add-tasks-table]]
    [lobos.connectivity :as conn]
    [lobos.core :as lobos]
    [lobos.schema :as s]
    [lobos.migration :as migration]
    [korma.core :as korma]))

(defn insert-test-users []
  (korma/insert users
    (korma/values [
      {:username "admin" :email "admin@test.com" :password (encrypt-password "admin")}
      {:username "user" :email "user@test.com"  :password (encrypt-password "user")}])))

; TODO
(defn insert-test-tasks []
  nil)

(migration/defmigration add-users-table-migration
  (migration/up []
    (do
       (add-users-table)
       (insert-test-users)))
  (migration/down []
    (lobos/drop (s/table :users))))

(migration/defmigration add-tasks-table-migration
  (migration/up []
    (add-tasks-table)
    (insert-test-tasks))
  (migration/down []
    (lobos/drop (s/table :tasks))))

(conn/open-global config/database-spec)
(defn execute-migration []
  (binding [migration/*migrations-namespace* 'rest.db.migration
            migration/*reload-migrations* false]
    (do
      (println ">> Migration start")
      (lobos/migrate config/database-spec)
      (println "<< Migration end"))))

(defn restore-database []
  (binding [migration/*migrations-namespace* 'rest.db.migration
            migration/*reload-migrations* false]
    (do
      (println ">> Rollback all migrations")
      (lobos/rollback config/database-spec :all)
      (println "<< Rollback finished")
      (println ">> Migration start")
      (lobos/migrate config/database-spec)
      (println "<< Migration end"))))
