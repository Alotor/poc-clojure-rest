(ns rest.base.routes
  (:require
    [ring.util.response :as resp]
    [compojure.core :refer [defroutes, GET]]
    [compojure.route :as route]
    [rest.base.views :as views]))

(defroutes base-routes
  ; Redirect the root to the index
  (GET "/" [] (resp/redirect "/index.html"))
  ; Route static resources
  (route/resources "/")
  ; Error pages
  (route/not-found "/error/400.html") (route/resources "/"))
