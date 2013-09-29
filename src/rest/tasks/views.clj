(ns rest.tasks.views
  (:require
    [rest.base.views :refer [layout]]
    [hiccup.element :refer [ordered-list]]
    [hiccup.form :refer [submit-button]]
    [hiccup.page :refer [include-js]]))

(defn tasks []
  (layout [:h1 "Tasks"
          [:section {:id "tasks"}
           (ordered-list {:id "task-list"} [])
           [:form {}
            [:label "New task" [:input {:type "text"
                                        :name "task-title"
                                        :placeholder "Buy some milk"}]]
            (submit-button {:id "new-task"} "Add")]]
         (include-js "/js/vendor/require.js")
         (include-js "/js/config.js")
         (include-js "/js/main.js")]))
