(ns rest.tasks.resources
  (:require 
    [rest.tasks.services :as services]
    [rest.tasks.models :as models]
    [liberator.core :refer [defresource]]
    [clojure.data.json :refer[JSONWriter]]
    ))

(defn- write-timestamp [x out]
  (.print out (str \" x \")))

(extend java.sql.Timestamp JSONWriter {:-write write-timestamp})

(defresource tasks-resources
  :available-media-types ["application/json"]
  :handle-ok (fn [context] (services/search-tasks)))
