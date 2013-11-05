(ns rest.core.auth
  (:require [rest.core.crypto :as crypto])
  (:gen-class))

(defn unsign-bearer
  "Function that expect :token keyword on request
  and unsigns it value using a current stateless signing
  library."
  [request]
  (let [token (:token request)]
    (if (nil? token)
      (assoc request :auth nil)
      (assoc request :auth (crypto/loads token)))))

(defn parse-auth-token
  "Function that simply parses a authorization header,
  extract a bearer token from it and put them as :token
  keyword on request."
  [request]
  (let [headers-map (apply hash-map (:headers request))
        auth-header (get headers-map "authorization")]
    (if (nil? auth-header)
      (assoc request :token nil :token-type nil)
      (let [pattern (re-pattern "^Bearer (.+)$")
            matches (re-find pattern auth-header)]
        (if (nil? matches)
          (assoc request :token nil :token-type nil)
          (assoc request :token (get matches 1) :token-type "bearer"))))))

(defprotocol AuthBackend
  "Generic protocol for implement any authentication workflows."
  (parse-token [_ request] "Parse token and add its to request.")
  (authenticate [_ request] "Using a parsed data, attach user to request."))

;; Generic token based authentication that parses that simply
;; parses bearer token without any modification and delegate
;; any other work to extend functions that accept as parameters.
(defrecord TokenBackend [parsefn authfn]
  AuthBackend
  (parse-token [this request]
    (let [request (parse-auth-token request)
          parsefn (:parsefn this)]
      (if (nil? parsefn)
        request
        (-> request (parsefn)))))
  (authenticate [this request]
    (let [authfn (:authfn this)]
      (if (nil? authfn)
        request
        (authfn request)))))

;; Specialized Stateless token auth backend that
;; only delegates a auth process to external function.
(defrecord StatelessTokenBackend [authfn]
  AuthBackend
  (parse-token [this request]
    (-> request
        (parse-auth-token)
        (unsign-bearer)))
  (authenticate [this request]
    (let [authfn (:authfn this)]
      (if (nil? authfn)
        request
        (authfn request)))))

(defn wrap-auth
  "Ring middleware that parses autocontained (stateless) token
  and add its data to request."
  [handler backend]
  (fn [request]
    (->> request
         (parse-token backend)
         (authenticate backend)
         (handler))))
