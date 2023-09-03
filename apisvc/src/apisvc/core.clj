(ns apisvc.core
;;  (:require [apisvc.db :as db])
  (:gen-class))

(defn -main
  "I don't do a whole lot yet."
  []
  (.addShutdownHook
   (Runtime/getRuntime)
   (Thread. #(println "Shutting down"))))
