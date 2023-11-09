(ns apisvc.handlers.type
  (:require
   [apisvc.commons :as api-commons]
   [apisvc.err :as api-err]
   [apisvc.sync :as api-sync]
   [malli.core :as mc]))

;; dto
(def type?
  (mc/schema
   [:map
    [:id :int]
    [:name :string]]))

(def get-all-200-resp?
  (mc/schema
   [:vector type?]))

;; GET /types
(defn get-all
  [_]
  (let [db-rsrcs (api-sync/persist! :read :type {:condition []})
        empty-nerr? (partial api-commons/empty-nerr? db-rsrcs)
        result (api-err/handle->
                db-rsrcs
                [empty-nerr? (fn [_] (api-sync/fetch :all :type))]
                [empty-nerr? api-commons/clear-named-response]
                [empty-nerr? (fn [resp]
                               (api-sync/persist! :insert :type {:values resp}))])]
    {:status (api-commons/get-status result)
     :body (->> (map api-commons/untag-map result)
                (into []))}))

(def get-all-spec
  "/types specification"
  {:get {:summary "returns all types"
         :handler get-all
         :responses {200 {:body get-all-200-resp?}
                     424 {:body api-err/error?}}}})
