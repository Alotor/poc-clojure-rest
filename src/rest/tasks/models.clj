(ns rest.tasks.models
  (:require
    [rest.db.config :as config]
    [lobos.core :as lobos]
    [lobos.schema :as s]
    [korma.core :as korma]))

(defn add-tasks-table []
  (lobos/create (s/table :tasks
     (s/integer :id :primary-key :auto-inc)
     (s/varchar :title 255 :not-null)
     (s/text :description)
     (s/boolean :completed :not-null (s/default false))
     (s/integer :owner-id [:refer :users :id :on-delete :set-null])
     (s/timestamp :date-created :not-null :time-zone (s/default (now)))
     (s/timestamp :date-updated :not-null :time-zone (s/default (now)))
  )))

(korma/defentity tasks
  (korma/pk :id)
  (korma/table :tasks)
  (korma/database config/database-spec)
  (korma/entity-fields :id :title :description :completed))
