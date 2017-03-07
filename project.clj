(defproject riemann-kfk-plugin "0.1.0-SNAPSHOT"
  :description "A riemann plugin for kafka 0.10.*"
  :url "https://github.com/yaiba/riemann-kfk-plugin"
  :license {:name "MIT License"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [riemann "0.2.11"]
                 [kafka-clj "4.0.0"
                  :exclusions [org.slf4j/slf4j-log4j12
                               log4j/log4j]]])
