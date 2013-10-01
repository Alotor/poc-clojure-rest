(ns rest.tasks.test.resources
  (:require [clojure.test :refer :all]
            [rest.tasks.resources :as res]
            [clojure.data.json :as json]))

(deftest test-tasks-resources
  (testing "User not logged in"
    (with-redefs [rest.auth.services/logged-in-user (fn [context] nil)]
      (let [response (res/tasks {:request-method :get :headers {"accept" "application/json"}})
            content-type ((response :headers) "Content-Type")]
        (is (= 401 (response :status)))
  )))

  (testing "List user tasks is empty"
    (with-redefs 
      [rest.auth.services/logged-in-user (fn [context] {:id 1})
       rest.tasks.services/tasks-belonging-to (fn [user] [])]
      (let [response (res/tasks {:request-method :get :headers {"accept" "application/json"}})
            content-type ((response :headers) "Content-Type")
            json-response (json/read-str (response :body))]
        (is (= 200 (response :status)))
        (is (= "application/json;charset=UTF-8" content-type))
        (is (empty? json-response))
  )))

  (testing "List user tasks"
    (with-redefs 
      [rest.auth.services/logged-in-user (fn [context] {:id 1})
       rest.tasks.services/tasks-belonging-to (fn [user] [{:id 1} {:id 2}])]
      (let [response (res/tasks {:request-method :get :headers {"accept" "application/json"}})
            content-type ((response :headers) "Content-Type")
            json-response (json/read-str (response :body))]
        (is (= 200 (response :status)))
        (is (= "application/json;charset=UTF-8" content-type))
        (is (= 2 (count json-response)))
  )))
)
