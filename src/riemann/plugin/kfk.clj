(ns riemann.plugin.kfk
  "A riemann plugin for kfk."
  (:import com.aphyr.riemann.Proto$Msg)
  (:require [riemann.plugin.kfk-producer :refer [producer send-message kfk-message]]
            [clojure.tools.logging :refer [debug info error]]))

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
            encoder (or (:encoder config) encode)]
        (send-message p (kfk-message topic (encoder events)))))))
