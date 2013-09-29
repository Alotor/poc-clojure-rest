(ns rest.tasks.services
  (:require
    [korma.core :refer :all]
    [rest.tasks.models :refer :all]))

; Task services
(defn all-tasks []
  (select tasks))

(defn add-task [task]
  (insert tasks (values task)))

(defn mark-as-completed [id]
  (update tasks
    (set-fields {:completed true})
    (where {:id id})))

(defn mark-as-pending [id]
  (update tasks
    (set-fields {:completed false})
    (where {:id id})))

(defn delete-task [id]
  (delete tasks
    (where {:id id})))
