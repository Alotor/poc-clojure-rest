(ns rest.main
  ;; (:import rest.core.auth.StatelessTokenBackend)
  (:require [compojure.handler :as handler]
            [compojure.core :refer [defroutes, routes, context]]
            [compojure.route :as route]
            [rest.core.auth :as auth]
            ; Routes
            [rest.auth.routes :refer [auth-routes]]
            [rest.tasks.routes :refer [tasks-routes]]
            [rest.base.routes :refer [base-routes]]
            [ring.adapter.jetty :as jetty])
  (:gen-class))

(defroutes api-routes
  ; Additional API Routes
  (context "/api" []
    (routes tasks-routes))
  (routes base-routes))

(defn make-handler
  "Create a main handler."
  (handler/api api-routes))

;; (defn make-handler
;;   "Create a main handler."
;;   (let [backend (auth/->StatelessTokenBackend nil)]
;;     (-> (auth/wrap-auth backend api-routes)
;;         (handler/api))))

(def app (make-handler))

(defn -main
  [& args]
  (println "Now listening on: http://127.0.0.1:9090/")
  (jetty/run-jetty #'app {:port 9090}))

