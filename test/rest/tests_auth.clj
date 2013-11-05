(ns rest.tests-auth
  (:require [clojure.test :refer :all]
            [rest.core.crypto :as crypto]))

(deftest auth-tests
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
