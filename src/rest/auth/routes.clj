(ns rest.auth.routes
  (:require
    [compojure.core :refer [defroutes GET ANY]]
    [cemerick.friend :as friend]
    [ring.util.response :refer [redirect]]
    [rest.auth.views :refer [login-form bye]]))

(defroutes auth-routes
  (GET "/" [] (redirect "/tasks"))
  (GET "/login" [] login-form)
  (friend/logout (ANY "/logout" [] (redirect "/logout/bye")))
  (GET "/logout/bye" [] bye))
