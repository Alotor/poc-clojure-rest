(ns leiningen.migrate
  (:require
    [rest.db.migration :as migration]))

(defn migrate [project & args]
  (migration/execute-migration))
