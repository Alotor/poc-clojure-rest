(ns leiningen.migrate
  (:require
    [rest.tasks.migration :as migration]))

(defn migrate [project & args]
  (migration/execute-migration))
