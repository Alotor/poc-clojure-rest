(ns leiningen.regenerate
  (:require
    [rest.tasks.migration :as migration]))

(defn regenerate [project & args]
  (migration/restore-database))
