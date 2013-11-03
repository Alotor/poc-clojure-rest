(ns rest.auth.routes
  (:require
    [compojure.core :refer [defroutes GET ANY]]
    [cemerick.friend :as friend]
    [rest.auth.resources :as resources]
    [ring.util.response :refer [redirect]]))

(defroutes auth-routes
  (GET "/token" request resources/get-token))
