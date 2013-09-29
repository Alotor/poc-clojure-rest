(ns rest.tasks.services
  (:require
    [korma.core :refer :all]
    [rest.tasks.models :refer :all]))

; Task services
(defn all-tasks []
  (select tasks))

(defn update-task [task]
  (update tasks
    (set-fields {:title (:title task)
                 :completed (:completed task)})
    (where {:id (:id task)})))

(defn tasks-belonging-to [user]
  (select tasks
    (where {:owner-id (:id user)
            :completed false})))

(defn add-task [task owner]
  (insert tasks
    (values
      (into task {:owner-id (:id owner)}))))

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
