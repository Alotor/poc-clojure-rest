(ns rest.tasks.resources
  (:require 
    [rest.tasks.services :as services]
    [rest.tasks.models :as models]
    [liberator.core :refer [defresource]]))

(defresource tasks-resources
  :available-media-types ["application/json"]
  :handle-ok (fn [context] (services/search-tasks)))
