(ns rest.auth.models
  (:require
    [rest.db.config :as config]
    [lobos.core :as lobos]
    [lobos.schema :as s]
    [korma.core :as korma]))

(defn add-users-table []
  (lobos/create (s/table :users
     (s/integer :id :primary-key :auto-inc)
     (s/varchar :username 255 :unique :not-null)
     (s/varchar :email 255 :unique :not-null) ; @@@: can we use Postgres' email datatype?
     (s/varchar :password 255 :not-null) ; TODO: max size of a good hashing algorithm?
     (s/timestamp :date-created :not-null :time-zone (s/default (now)))
     (s/timestamp :date-updated :not-null :time-zone (s/default (now)))
  )))

(korma/defentity users
  (korma/pk :id)
  (korma/table :users)
  (korma/database config/database-spec)
  (korma/entity-fields :id :email :password :date-created :date-updated))
