(ns apisvc.db
  "Persistence"
  (:require
   [environ.core :refer [env]]
   [hikari-cp.core :as hkr]
   [honey.sql :as sql]
   [next.jdbc :as jdbc]))

(defonce ^:private pool-ds
  (-> {:auto-commit        true
       :read-only          false
       :connection-timeout 3000
       :validation-timeout 5000
       :idle-timeout       21600000
       :max-lifetime       1800000
       :minimum-idle       10
       :maximum-pool-size  10
       :pool-name          "pokeclj-db-pool"
       :adapter            "postgresql"
       :username           (env :postgres-user)
       :password           (env :postgres-password)
       :database-name      (env :postgres-db)
       :server-name        (env :postgres-host)
       :port-number        (env :postgres-port)
       :register-mbeans    false
       :connection-test-query "SELECT 1"}
      hkr/make-datasource))

(defn gen-insert [table columns values]
  {:insert-into table :columns columns :values values})

(defn gen-read [selects table condition]
  {:select selects :from table :where condition})

(defn gen-update [table sets condition]
  {:update table :set sets :where condition})

(defn gen-delete [table condition]
  {:delete-from table :where condition})

(def query-gens
  {:insert gen-insert
   :read gen-read
   :update gen-update
   :delete gen-delete})

(defn gen-query [gen & args]
  (sql/format (apply (gen query-gens) args)))

(defn gen-partial-queries [table columns]
  {:insert [table columns :values]
   :read [columns table :condition]
   :update [table :sets :condition]
   :delete [table :condition]})

(defn execute!
  "Executes QUERY on persistent db.
   <https://github.com/tomekw/hikari-cp#postgresql-example>."
  [query]
  (with-open [conn (jdbc/get-connection {:datasource pool-ds})]
    (try
      (jdbc/execute! conn query)
      (catch Exception e {:error (.getMessage e)}))))
