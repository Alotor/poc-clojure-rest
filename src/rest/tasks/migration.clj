; Module to store database migrations
(ns rest.tasks.migration
  (:refer-clojure :exclude [alter drop time boolean float char bigint double complement])
  (:require 
    [rest.tasks.config :as config]
    [lobos.connectivity :refer :all]
    [lobos.core :refer :all]
    [lobos.schema :refer :all]
    [lobos.migration :refer :all]
    [korma.core :as korma]
    [rest.tasks.models :refer :all]))

(defn insert-test-users []
  (korma/insert users 
    (korma/values [
      {:id 1 :email "test@test.com"}
      {:id 2 :email "asdf@asdf.com"}])))

(defmigration add-users-table-migration
  (up [] (do (add-users-table)(insert-test-users)))
  (down [] (drop (table :users))))

(defmigration add-tasks-table-migration
  (up [] (add-tasks-table))
  (down [] (drop (table :tasks))))

(open-global config/database-spec)
(defn execute-migration []
  (binding [lobos.migration/*migrations-namespace* 'rest.tasks.migration
            lobos.migration/*reload-migrations* false]
    (do 
      (println ">> Migration start")
      (migrate config/database-spec)
      (println "<< Migration end"))))

(defn restore-database []
  (binding [lobos.migration/*migrations-namespace* 'rest.tasks.migration
            lobos.migration/*reload-migrations* false]
    (do 
      (println ">> Rollback all migrations")
      (rollback config/database-spec :all)
      (println "<< Rollback finished")
      (println ">> Migration start")
      (migrate config/database-spec)
      (println "<< Migration end"))))

