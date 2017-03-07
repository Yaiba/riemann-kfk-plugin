(ns riemann.plugin.kfk
  "A riemann plugin for kfk."
  (:require [riemann.core :refer [stream!]]
            [riemann.common :refer [decode-msg encode]]
            [riemann.service :refer [Service ServiceEquiv]]
            [riemann.config :refer [service!]]
            [clojure.tools.logging :refer [debug info error]]))
