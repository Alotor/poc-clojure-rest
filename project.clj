(defproject rest.tasks "0.1"
  :description "Proof of concept for REST/Backbone web stack"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 ; Database
                 [korma "0.3.0-RC5"]
                 [lobos "1.0.0-beta1"]
                 [postgresql "9.1-901.jdbc4"]
                 ; HTTP routing
                 [ring/ring-core "1.2.0-beta1"]
                 [compojure "1.1.5"]
                 ; RESTful resources
                 [liberator "0.9.0"]
                 ; Templating
                 [hiccup "1.0.4"]
                 ; Authentication and authorization
                 [com.cemerick/friend "0.1.5"]
                 [commons-codec/commons-codec "1.8"]

                 ; Serialization
                 [com.taoensso/nippy "2.4.1"]]
  :plugins [[lein-ring "0.8.7"]]
  :ring {:handler rest.core/app}
  :eval-in-leiningen true)
