(defproject rest.tasks "0.1"
  :description "Proof of concept for REST/Backbone web stack"
  :dependencies 
    [[org.clojure/clojure "1.5.1"]
     [liberator "0.9.0"]
     [compojure "1.1.3"]
     [korma "0.3.0-RC5"]
     [lobos "1.0.0-beta1"]
;     [com.h2database/h2 "1.3.170"]] 
     [postgresql "9.1-901.jdbc4"]]

  :plugins [[lein-ring "0.8.3"]]
  :ring {:handler rest.tasks.routes/tasks-routes}
  :eval-in-leiningen true)
