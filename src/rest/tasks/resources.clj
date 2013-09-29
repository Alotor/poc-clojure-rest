(ns rest.tasks.resources
  (:require
    [rest.tasks.views :as views]
    [rest.tasks.services :as services]
    [rest.tasks.models :as models]
    [liberator.core :refer [defresource]]
    [clojure.data.json :refer[JSONWriter]]
    ))

(defn- write-timestamp [x out]
  (.print out (str \" x \")))

(extend java.sql.Timestamp JSONWriter {:-write write-timestamp})

(defresource tasks-resources
  :available-media-types ["text/html" "application/json"]
  :handle-ok
  #(let [media-type (get-in % [:representation :media-type])]
     (condp = media-type
       "text/html" (views/tasks)
       "application/json" (services/search-tasks))))
