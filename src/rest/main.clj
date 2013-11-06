(ns rest.main
  (:require
    [compojure.handler :as handler]
    [compojure.core :refer [defroutes, routes, context]]
    [compojure.route :as route]

    ; Auth
    [rest.auth.services :as auth]
    [rest.auth.token :as token]
    [cemerick.friend :as friend]
    [cemerick.friend.credentials :as credentials]
    [cemerick.friend.workflows :as workflows]

    ; Routes
    [rest.auth.routes :refer [auth-routes]]
    [rest.tasks.routes :refer [tasks-routes]]
    [rest.base.routes :refer [base-routes]]))

(defroutes api-routes
  ; Additional API Routes
  (context "/api" []
    (routes tasks-routes))
  (routes base-routes))

(def secret-key "MYSECRET")
(def app
  (handler/api
    (friend/authenticate
      api-routes
      { :allow-anon? true
        ;:unauthenticated-handler #(workflows/http-basic-deny api-realm %)
        :workflows [(token/token-auth :secret secret-key)]
      })))
;(def app
;  (handler/api
;    (friend/authenticate
;      api-routes
;      { :allow-anon? true
;        :unauthenticated-handler #(workflows/http-basic-deny api-realm %)
;        :workflows [(workflows/http-basic
;                     :credential-fn (partial credentials/bcrypt-credential-fn auth/get-user)
;                     :realm api-realm)]
;      })))
