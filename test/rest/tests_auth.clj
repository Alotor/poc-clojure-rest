(ns rest.tests-auth
  (:require [clojure.test :refer :all]
            [rest.core.crypto :as crypto]
            [rest.core.auth :as auth]))

(deftest sign-tests
  (testing "Signing/Unsigning with default keys"
    (let [signed (crypto/sign "foo")]
      (Thread/sleep 1000)
      (is (not= (crypto/sign "foo") signed))
      (is (= (crypto/unsign signed) "foo"))))
  (testing "Signing/Unsigning with new key"
    (let [default-key @crypto/*secret-key*]
      (crypto/set-global-secret-key "foobar")
      (let [signed (crypto/sign "foo")]
        (Thread/sleep 1000)
        (is (not= (crypto/sign "foo") signed))
        (is (= (crypto/unsign signed) "foo")))
      ;; Restore old key
      (reset! crypto/*secret-key* default-key)))
  (testing "Signing/Unsigning timestamped"
    (let [signed (crypto/sign "foo")]
      (Thread/sleep 1000)
      ;; (is (= nil (crypto/unsign signed :max-age 2)))
      (is (= "foo" (crypto/unsign signed :max-age 20)))))
  (testing "Signing/Unsigning complex clojure data"
    (let [signed (crypto/dumps {:foo 2 :bar 1})]
      (Thread/sleep 1000)
      (is (= {:foo 2 :bar 1} (crypto/loads signed))))))

(defrecord TestBackend []
  auth/AuthBackend
  (parse-token [_ request]
    (-> request
        (auth/parse-auth-token)
        (auth/unsign-bearer)))
  (authenticate [_ request] request))

(deftest auth-test
  (testing "Token parsing from authorization header"
    (let [backend (TestBackend.)
          request {:request-method :get
                   :uri "/my-uri"
                   :headers ["authorization" (str "Bearer " (crypto/dumps {:userid 1}))]
                   :params {:x "foo" :y "bar" :z "baz" :w "qux"}}
          result  (atom nil)]
     (let [handler (fn [request]
                    (reset! result (:auth request)))
           handler (auth/wrap-auth handler backend)]
       (handler request)
       (is (= @result {:userid 1}))))))
