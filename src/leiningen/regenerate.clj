(ns leiningen.regenerate
  (:require
    [rest.db.migration :as migration]))

(defn regenerate [project & args]
  (migration/restore-database))
