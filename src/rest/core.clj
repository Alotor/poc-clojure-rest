(ns rest.core
  (:require
    [ring.util.response :as resp]
    [compojure.handler :as handler]
    [compojure.core :refer [routes]]
    ; Auth
    [cemerick.friend :as friend]
    (cemerick.friend [credentials :as credentials]
                     [workflows :as workflows])
    [rest.auth.views :refer [unauthorized]]
    [rest.auth.services :refer [user]]
    ; Routes
    [rest.auth.routes :refer [auth-routes]]
    [rest.tasks.routes :refer [tasks-routes]]
    [rest.base.routes :refer [base-routes]]))

(def app-routes
 (routes auth-routes
         tasks-routes
         base-routes))

(def app
  (handler/site
    (friend/authenticate
      app-routes
      {:allow-anon? true
       :login-url "/login"
       :unauthorized-handler #(-> unauthorized
                                  resp/response
                                  (resp/status 401))
       :credential-fn #(credentials/bcrypt-credential-fn user %)
       :workflows [(workflows/interactive-form)]})))
