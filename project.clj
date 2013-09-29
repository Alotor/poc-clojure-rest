(defproject rest.tasks "0.1"
  :description "Proof of concept for REST/Backbone web stack"
  :dependencies
    [[org.clojure/clojure "1.5.1"]
     [liberator "0.9.0"]
     [compojure "1.1.5"]
     [korma "0.3.0-RC5"]
     [lobos "1.0.0-beta1"]
     [postgresql "9.1-901.jdbc4"]
     [hiccup "1.0.4"]]
  :plugins [[lein-ring "0.8.7"]]
  :ring {:handler rest.core/app}
  :eval-in-leiningen true)
