(ns rest.tasks.models
  (:refer-clojure :exclude [alter drop time boolean float char bigint double complement])
  (:require 
    [rest.tasks.config :as config]
    [lobos.connectivity :refer :all]
    [lobos.core :refer :all]
    [lobos.schema :refer :all]
    [korma.core :as korma]))

(defn add-users-table []
  (create (table :users 
     (integer :id :primary-key)
     (varchar :email 255 :unique)
     (varchar :password 255)
     (time :date-created)
     (time :date-updated))))

(korma/defentity users
  (korma/pk :id)
  (korma/table :users)
  (korma/database config/database-spec)
  (korma/entity-fields :id :email :password :date-created :date-updated))

(defn add-tasks-table []
  (create (table :tasks
     (integer :id :primary-key)
     (varchar :title 255 :unique)
     (text :description)
     (boolean :completed))))
  
(korma/defentity tasks
  (korma/pk :id)
  (korma/table :tasks)
  (korma/database config/database-spec)
  (korma/entity-fields :id :title :description :completed))

