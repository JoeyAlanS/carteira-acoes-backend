(defproject carteira-acoes-backend "0.1.0-SNAPSHOT"
            :description "API de carteira de ações - Backend Compojure"
            :min-lein-version "2.0.0"
            :dependencies [[org.clojure/clojure "1.10.0"]
                           [compojure "1.6.1"]
                           [ring/ring-defaults "0.3.2"]
                           [ring/ring-json "0.5.1"]
                           [clj-http "3.12.3"]
                           [environ "1.2.0"]
                           ]
            :plugins [[lein-ring "0.12.5"]
                      [lein-environ "1.2.0"]]
            :main carteira-acoes-backend.core
            :ring {:handler carteira-acoes-backend.handler/app}
            :profiles
            {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                                  [ring/ring-mock "0.3.2"]]}})
