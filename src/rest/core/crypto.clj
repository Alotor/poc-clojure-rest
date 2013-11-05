(ns rest.core.crypto
  (:require [clojure.string :refer [split]]
            [taoensso.nippy :as nippy]
            [clojure.string :as str])
  (:import (javax.crypto Mac)
           (javax.crypto.spec SecretKeySpec)
           (org.apache.commons.codec.binary Base64 Hex)
           (java.security MessageDigest)))

;; Global defined secret key.
;; WARN: must be changed on production code
(def ^:dynamic *secret-key* (atom nil))

(defn str->bytes
  "Convert string to java bytes array"
  ([^String s]
   (str->bytes s "UTF-8"))
  ([^String s, ^String encoding]
   (.getBytes s encoding)))

(defn bytes->str
  "Convert octets to String."
  ([data]
   (bytes->str data "UTF-8"))
  ([#^bytes data, ^String encoding]
   (String. data encoding)))

(defn bytes->hex
  "Convert a byte array to hex
  encoded string."
  [#^bytes data]
  (Hex/encodeHexString data))

(defn bytes->base64
  "Encode a bytes array to base64."
  [#^bytes data]
  (let [codec (Base64. true)]
    (str/trim (.encodeToString codec data))))

(defn base64->bytes
  "Decode from base64 to bytes."
  [^String s]
  (let [codec (Base64. true)
        data  (.decode codec s)]
    data))

(defn str->base64
  "Encode to urlsafe base64."
  [^String s]
  (let [codec (Base64. true)
        data  (str->bytes s)]
    (str/trim (.encodeAsString codec data))))

(defn base64->str
  "Decode from base64 to string."
  [^String s]
  (let [codec (Base64. true)
        data  (.decode codec s)]
    (bytes->str data)))

(defn make-keyspec
  "Create a new SecretKeySpec instance
  for some string."
  ([^String s, ^String algo]
   (SecretKeySpec. (str->bytes s) algo))
  ([^String s]
   (make-keyspec s "HmacSHA256")))

(defn make-keyspec-from-bytes
  "Create a new SecretKeySpec instance
  for some string."
  ([#^bytes s, ^String algo]
   (SecretKeySpec. s algo))
  ([#^bytes s]
   (make-keyspec-from-bytes s "HmacSHA256")))

(defn set-global-secret-key
  "Set globally secret key."
  ([^String s, ^String algo]
     (reset! *secret-key* (make-keyspec s algo)))
  ([^String s]
   (set-global-secret-key s "HmacSHA256")))

(defn salted-hmac
  "Returns a salted hmac."
  [^String salt, ^String value & {:keys [secret] :or {secret @*secret-key*}}]
  {:pre [(instance? SecretKeySpec secret)]}
  (let [md  (MessageDigest/getInstance "SHA-256")
        mac (Mac/getInstance (.getAlgorithm secret))]
    (.update md (.getEncoded secret))
    (.update md (str->bytes salt))
    (.init mac (make-keyspec-from-bytes (.digest md)))
    (let [final (.doFinal mac (str->bytes value))]
      (bytes->hex final))))

(defn make-signature
  "Low level method for make signature to string."
  [^String s, ^String salt]
  (salted-hmac salt s))

(defn make-timestamped-signature
  [^String s, ^String salt, ^String sep, ^String stamp]
  (let [signature (make-signature s salt)]
    (format "%s%s%s" signature sep stamp)))

(defn timestamp
  "Get current timestamp."
  []
  (quot (System/currentTimeMillis) 1000))

(defn sign
  "Sign string using a private key globally defined."
  [^String s & {:keys [sep salt] :or {sep ":" salt "clj"}}]
  (let [stamp     (str->base64 (str (timestamp)))
        signature (make-timestamped-signature s salt sep stamp)]
    (format "%s%s%s" s sep signature)))

(defn unsign
  "Unsign string using a private key globally defined."
  [^String s & {:keys [sep salt max-age] :or {sep ":" salt "clj" max-age nil}}]
  (let [[value sig stamp] (split s (re-pattern sep))]
    (when (= sig (make-signature value salt))
      (if-not (nil? max-age)
        (let [old-stamp-value (Integer/parseInt (base64->str stamp))
              age             (- (timestamp) old-stamp-value)]
          (if (> age max-age) nil value))
        value))))

(defn dumps
  "Sign a complex data strucutres using
  serialization as intermediate step."
  [data & args]
  (let [encoded (bytes->base64 (nippy/freeze data))]
    (apply sign (cons encoded (vec args)))))

(defn loads
  "Unsign data signed with dumps."
  [^String s & args]
  (let [unsigned (apply unsign (cons s (vec args)))]
    (nippy/thaw (base64->bytes unsigned))))

(reset! *secret-key* (make-keyspec "1234567890"))
