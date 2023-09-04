(ns apisvc.db-test
  (:require [expectations.clojure.test :refer [defexpect expect]]
            [apisvc.db :as db]))

(defexpect gen-query
  (expect ["INSERT INTO pokemon (id, name) VALUES (?, ?)" 4 "bulbasaur"]
          (db/gen-query :insert :pokemon [:id :name] [[4 "bulbasaur"]]))
  (expect ["SELECT name, description FROM pokemon WHERE id >= ?" 4]
          (db/gen-query :read [:name :description] :pokemon [:>= :id 4]))
  (expect ["UPDATE pokemon SET name = ?, description = ? WHERE id >= ?"
           "poke1234" "missing" 4]
          (db/gen-query :update :pokemon {:name "poke1234"
                                          :description "missing"}
                        [:>= :id 4]))
  (expect ["DELETE FROM pokemon WHERE (id = ?) AND (name LIKE ?)" 4 "%1234"]
          (db/gen-query :delete :pokemon [:and [:= :id 4] [:like :name "%1234"]])))

;; WARNING: db/execute! isn't being tested for now since its more
;; of an integration test rather than an unit test.
