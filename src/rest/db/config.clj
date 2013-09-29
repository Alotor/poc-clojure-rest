; Module to store application configuration across modules
(ns rest.db.config)

; Database definition
(def database-spec {
  :classname "org.postgresql.Driver"
  :subprotocol "postgresql"
  :user "tasks"
  :subname "//localhost:5432/tasks"})
