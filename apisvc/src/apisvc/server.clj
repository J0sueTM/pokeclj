(ns apisvc.server
  (:require
   [apisvc.handlers.ping :as api-pingh]
   [apisvc.handlers.pokemon :as api-pokemonh]
   [apisvc.handlers.type :as api-typeh]
   [environ.core :refer [env]]
   [muuntaja.core :as mtj]
   [reitit.coercion.malli :as rtt-mc]
   [reitit.openapi :as rtt-openapi]
   [reitit.ring :as rtt-ring]
   [reitit.ring.coercion :as rtt-rc]
   [reitit.ring.middleware.muuntaja :as rtt-mtj]
   [reitit.swagger-ui :as rtt-swagger-ui]
   [ring.adapter.jetty :as ring-jetty]))

(def routes
  [""
   ["/openapi.json"
    {:get {:no-doc true
           :openapi {:info {:title "ApiSvc"
                            :description "PokeCLJ main backend service"
                            :version "0.0.1"}
                     :tags [{:name "misc"}
                            {:name "types"}
                            {:name "pokemons"}]}
           :handler (rtt-openapi/create-openapi-handler)}}]
   ["/docs/*"
    {:get {:no-doc true
           :handler (rtt-swagger-ui/create-swagger-ui-handler
                     {:url "/openapi.json"})}}]
   ["/misc" {:tags ["misc"]}
    ["/ping" api-pingh/spec]]
   ["/types" {:tags ["types"]}
    ["" api-typeh/get-all-spec]]
   ["/pokemons" {:tags ["pokemons"]}
    ["" api-pokemonh/get-all-spec]
    ["/:name-or-id" api-pokemonh/get-by-name-or-id]]])

;; [":name-or-id" api-pokemonh/get-by-name-or-id]

(def app-spec
  {:data {:muuntaja mtj/instance
          :coercion rtt-mc/coercion
          :middleware [rtt-mtj/format-middleware
                       rtt-rc/coerce-exceptions-middleware
                       rtt-rc/coerce-request-middleware
                       rtt-rc/coerce-response-middleware]}})

(def app
  (rtt-ring/ring-handler
   (rtt-ring/router routes app-spec)
   (rtt-ring/routes
    (rtt-ring/create-default-handler))))

(defonce server (atom nil))

(def ^:private port
  (or (env :server-port)
      4499))

(defn- restart [srv]
  (when srv (.stop srv))
  (ring-jetty/run-jetty app {:port port
                             :join? false}))

(defn start []
  (swap! server restart)
  (println (format "server running on port %d" port)))
