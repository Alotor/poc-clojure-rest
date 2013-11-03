(ns rest.auth.resources
  (:require
    [rest.auth.services :as services]
    [liberator.core :refer [defresource]]))

(defresource get-token
  :allowed-methods [:post]
  :available-media-types ["application/json"]
  :post! (fn [context] {:token "XXX"}))

