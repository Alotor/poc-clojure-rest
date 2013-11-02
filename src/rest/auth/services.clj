(ns rest.auth.services
  (:require
    [korma.core :refer :all]
    [cemerick.friend :as friend]
    [cemerick.friend.credentials :refer [hash-bcrypt]]
    [rest.auth.models :refer :all]))

(def encrypt-password hash-bcrypt)

(defn all-users []
  (select users))

(defn get-user [username]
  (let [user (select users (where {:username username}))]
    (if (empty? user)
      nil
     (assoc (first user) :roles #{::user}))))

(defn logged-in-user [context]
  (friend/current-authentication (:request context)))

