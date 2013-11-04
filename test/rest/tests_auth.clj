(ns rest.tests-auth
  (:require [clojure.test :refer :all]
            [rest.core.crypto :as crypto]))

(deftest auth-tests
  (testing "Signing/Unsigning with default keys"
    (let [signed (crypto/sign "foo")]
      (is (= (crypto/sign "foo") signed))
      (is (= (crypto/unsign signed) "foo")))))

