(ns apisvc.handlers.pokemon
  (:require
   [apisvc.commons :as api-commons]
   [apisvc.err :as api-err]
   [apisvc.sync :as api-sync]
   [malli.core :as mc]))

;; dto
(def pokemon?
  (mc/schema
   [:map
    [:id :int]
    [:name :string]
    [:description :string]
    [:weight float?]
    [:height float?]
    [:complete :boolean]]))

(def get-all-200-resp?
  (mc/schema
   [:vector pokemon?]))

(defonce nloaded-yet-str "not loaded yet")

(defn fill-incomplete-resp [inc-pks]
  (map #(conj % nloaded-yet-str 0 0 false) inc-pks))

;; GET /pokemons
(defn get-all
  "Returns all pokemons."
  [_]
  (let [db-rsrcs (api-sync/persist! :read :pokemon {:condition []})
        empty-nerr? (partial api-commons/empty-nerr? db-rsrcs)
        result (api-err/handle->
                db-rsrcs
                [empty-nerr? (fn [_] (api-sync/fetch :all :pokemon))]
                [empty-nerr? api-commons/clear-named-response]
                [empty-nerr? fill-incomplete-resp]
                [empty-nerr? (fn [resp]
                               (api-sync/persist! :insert :pokemon {:values resp}))])]
    {:status (api-commons/get-status result)
     :body (->> (map api-commons/untag-map result)
                (into []))}))

(def get-all-spec
  "/pokemons specification"
  {:get {:summary "returns all pokemons"
         :handler get-all
         :responses {200 get-all-200-resp?
                     424 api-err/error?}}})

(defn nloaded-yet-nerr?
  "Validates if RSRC isn't loaded neither an error."
  [rsrc]
  (and (not= (:description rsrc) nloaded-yet-str)
       (api-err/not-error? rsrc)))

(defn get-by-name
  "Returns pokemon with given name."
  [name]
  (let [db-rsrcs (api-sync/persist! :read :pokemon {:condition [:= :name name]})
        result (api-err/handle->
                db-rsrcs
                [nloaded-yet-nerr? (fn [_] (api-sync/fetch :by-name :pokemon name))])]
    result))

(get-by-name "bulbasaur")

(defn get-by-id [id]
  {})

(defn get-by-name-or-id-handler
  [{{noid :name-or-id} :path-params}]
  (if (number? noid)
    (get-by-id noid)
    (get-by-name noid)))

(def get-by-name-or-id
  "/pokemons/:name-or-id specification"
  {:get {:summary "returns pokemons with given name or id"
         :handler get-by-name-or-id-handler}})
