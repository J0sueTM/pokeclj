(ns apisvc.commons
  (:require
   [apisvc.err :as api-err]
   [malli.core :as mc]))

(def message?
  (mc/schema [:map [:message :string]]))

(defn untag-map [m]
  (->> (for [[k v] m] [(keyword (name k)) v])
       (into {})))

(defn empty-nerr?
  "Validates if INITIAL-RSRC not empty and CUR-RSRC not an error."
  [initial-rsrc cur-rsrc]
  (and (empty? initial-rsrc) (api-err/not-error? cur-rsrc)))

(defn clear-named-response [resp]
  (->> (:results resp)
       (map :name)
       (map-indexed vector)
       (into [])))

(defn get-status [result]
  (if (mc/validate api-err/error? result) 424 200))
