(ns apisvc.core
  (:require
   [apisvc.server :as api-server])
  (:gen-class))

(defn -main [_]
  (.addShutdownHook
   (Runtime/getRuntime)
   (Thread. #(println "Shutting down")))
  (api-server/start))
