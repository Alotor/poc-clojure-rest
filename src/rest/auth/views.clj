(ns rest.auth.views
  (:require
    [rest.base.views :refer [layout]]
    [hiccup.element :refer [ordered-list]]
    [hiccup.page :refer [include-js]]))

(def login-form
  (layout
    [:div {:class "row"}
     [:div {:class "columns small-12"}
      [:h3 "Login"]
      [:div {:class "row"}
       [:form {:method "POST" :action "login" :class "columns small-4"}
        [:div "Username" [:input {:type "text" :name "username"}]]
        [:div "Password" [:input {:type "password" :name "password"}]]
        [:div [:input {:type "submit" :class "button" :value "Login"}]]]]]]))

(def unauthorized
  (layout [:h1 "You do not have privileges to access this page."]))

(def bye
  (layout [:h2 "Bye!"]))
