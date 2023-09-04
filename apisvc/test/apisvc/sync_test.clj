(ns apisvc.sync-test
  (:require [expectations.clojure.test :refer [defexpect expect]]
            [apisvc.sync :as sync]))

(defexpect fetch
  (expect 1281 (:count (sync/fetch :all :pokemon)))
  (expect '("charmeleon" "charizard" "squirtle")
          (map :name (:results (sync/fetch :some :pokemon 4 3))))
  (expect {:id 4 :height 6}
          (select-keys (sync/fetch :by-name :pokemon "charmander") [:id :height]))
  (expect {:height 6 :name "charmander"}
          (select-keys (sync/fetch :by-id :pokemon 4) [:height :name])))
