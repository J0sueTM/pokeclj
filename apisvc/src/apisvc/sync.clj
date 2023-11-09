(ns apisvc.sync
  "Synchronization of large datasets through on-demand loading.
   Implements something in the lines of the Memento DP."
  (:require [apisvc.db :as db]
            [clj-http.client :as httpc]
            [clojure.data.json :as json]
            [environ.core :refer [env]]))

(defn gen-resource
  "Generates a synchronizable resource, which can be
   persisted on db through queries, and fetched through remotes."
  [name capacity table columns]
  {:capacity capacity
   :queries (db/gen-partial-queries table columns)
   :remotes {:all (format "%s?limit=%d" name capacity)
             :some (format "%s?offset=%%d&limit=%%d" name)
             :by-id (format "%s/%%d" name)
             :by-name (format "%s/%%s" name)}})

(def resources
  {:type (gen-resource "type" 20 :type_ [:id :name])
   :pokemon (gen-resource "pokemon" 1281
                          :pokemon [:id :name :description
                                    :weight :height :complete])})

(defonce ^:private base-url
  (or (env :sync-base-url)
      "https://pokeapi.co/api/v2/"))

(defn fetch
  "Ex: (fetch :all :pokemon)"
  [remote resource & args]
  (let [rmt (-> resources resource :remotes remote)
        frmt (format "%s/%s" base-url rmt)
        res (-> (partial format frmt)
                (apply args)
                httpc/get)
        body (json/read-str (:body res) :key-fn keyword)]
    (if (= (:status res) 200)
      body {:error body})))

(defn persist!
  "Ex: (persist! :update :type {:sets {:name \"fire\"} :condition [:= :id 4]})"
  [query resource replaces]
  (->> resources resource :queries query
       (replace replaces)
       (apply (partial db/gen-query query))
       db/execute!))
