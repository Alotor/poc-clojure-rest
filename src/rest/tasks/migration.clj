(ns rest.tasks.migration
  (:require [lobos.connectivity :refer :all]
            [lobos.core :refer :all]
            [lobos.schema :refer :all]))

(def db {:classname "org.sqlite.JDBC" :subprotocol "sqlite" :subname "tasks.db"})

(create
  (table :tasks
    (integer :id :unique)
    (varchar :title 255 :unique)
    (text :description)
    (check :completed)))
