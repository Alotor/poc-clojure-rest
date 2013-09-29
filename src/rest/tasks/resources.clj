(ns rest.tasks.resources
  (:require
    [rest.tasks.views :as views]
    [rest.tasks.services :as services]
    [rest.tasks.models :as models]
    [rest.auth.services :refer [logged-in-user]]
    [liberator.core :refer [defresource]]
    [clojure.data.json :refer [read-str JSONWriter]]))

; JSON representation

(defn- write-timestamp [x out]
  (.print out (str \" x \")))

(extend java.sql.Timestamp JSONWriter {:-write write-timestamp})

; Resources

(defresource tasks
  :allowed-methods [:get :post]
  :available-media-types ["text/html" "application/json"]
  :authorized? logged-in-user
  :handle-ok
  #(let [media-type (get-in % [:representation :media-type])
         user (logged-in-user %)]
     (condp = media-type
       "text/html" (views/tasks)
       "application/json" (services/tasks-belonging-to user)))
  :post! (fn [context]
           (let [body (slurp (get-in context [:request :body]))
                 user (logged-in-user context)]
             (services/add-task (read-str body :key-fn keyword) user))))

(defresource task
  :allowed-methods [:get :put :delete]
  :available-media-types ["application/json"]
  :authorized? logged-in-user
  :put! (fn [context]
           (let [body (slurp (get-in context [:request :body]))
                 id (services/update-task (read-str body :key-fn keyword))]
             {::id id})))
