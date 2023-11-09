(ns apisvc.handlers.ping
  (:require [apisvc.commons :as api-commons]))

(defn handler
  "A simple pong message."
  [_]
  {:status 200
   :body {:message "pong"}})

(def spec
  "/ping specification"
  {:get {:summary "returns a simple pong message"
         :handler handler
         :responses {200 {:body api-commons/message?}}}})
