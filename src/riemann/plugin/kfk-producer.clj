(ns riemann.plugin.kfk-producer
  (:import [org.apache.kafka.clients.producer Producer KafkaProducer ProducerRecord]
           [java.util List Properties]))

(defn as-properties
  [m]
  (let [props (Properties. )]
    (doseq [[n v] m] (.setProperty props n v))
    props))

(defn producer
  "Create a Producer from conf."
  [conf]
  (^Producer KafkaProducer. (as-properties conf)))

(defn send-message
  [^Producer producer ^ProducerRecord message]
  (.send producer message))

(defn send-messages
  [^Producer producer ^List messages]
  (.send producer messages))

(defn kfk-message
  ([topic value] (kfk-message topic nil value))
  ([topic key value] (ProducerRecord. topic key value)))
