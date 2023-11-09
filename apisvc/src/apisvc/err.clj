(ns apisvc.err
  "Error handling."
  (:require [malli.core :as mc]))

(defmacro handle->
  "Monad like handler."
  [val & steps]
  (if (empty? steps)
    val
    (let [step (first steps)]
      `(let [result# ~val
             condition# ~(first step)
             action# ~(second step)]
         (if (condition# result#)
           (handle-> (action# result#) ~@(rest steps))
           result#)))))

(def error?
  (mc/schema [:map [:error :string]]))

(defn not-error?
  [slug]
  (not (mc/validate error? slug)))
