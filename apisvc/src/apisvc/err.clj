(ns apisvc.err
  "Error handling.")

(defn error?
  "Checks if given obj is an error."
  [obj]
  (contains? obj :error))
