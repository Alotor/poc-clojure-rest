(ns rest.auth.services
  (:require
    [korma.core :refer :all]
    [rest.auth.models :refer :all]))

(defn all-users []
  (select users))

(defn is-user? [credentials]
  (boolean
    (select users
      (where {:email (:email credentials)
              :password (:password credentials)}))))
