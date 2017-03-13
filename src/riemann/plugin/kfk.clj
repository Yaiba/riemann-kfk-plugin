(ns riemann.plugin.kfk
  "A riemann plugin for kfk."
  (:import [org.apache.kafka.clients.producer Producer KafkaProducer ProducerRecord]
           [java.util List Properties])
  (:require [clojure.tools.logging :refer [debug info error]]))

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

(defn gen-message
  ([topic value] (gen-message topic nil value))
  ([topic key value] (ProducerRecord. topic key value)))

(defn stringify
  "Converted a (keyword as key) map to normal map."
  [props]
  (let [input (dissoc props :topic :decoder :encoder :commit.per.msg)
        skeys (map (juxt (comp name key) val) input)]
    (reduce merge {} skeys)))

(defn kafka-producer
  "Return a kafka producer"
  [{:keys [topic] :as config}]
  (let [p (producer (stringify config))]
    (fn [event]
      (let [events (if (sequential? event) event [event])
            encoder (:encoder config)]
        (send-message p (gen-message topic (encoder events)))))))
