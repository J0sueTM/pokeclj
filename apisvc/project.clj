(defproject apisvc "0.1.0"
  :description "Backend"
  :url "https://github.com/j0suetm/pokeclj/apisvc"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :plugins [[lein-environ "1.2.0"]]
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [org.postgresql/postgresql "42.6.0"]
                 [com.github.seancorfield/next.jdbc "1.3.883"]
                 [com.github.seancorfield/honeysql "2.4.1066"]
                 [hikari-cp "3.0.1"]
                 [environ "1.2.0"]
                 [clj-http "3.12.3"]
                 [org.clojure/data.json "2.4.0"]
                 [com.github.seancorfield/expectations "2.0.165"]
                 [metosin/reitit "0.7.0-alpha5"]
                 [metosin/muuntaja "0.6.8"]
                 [metosin/malli "0.12.0"]
                 [metosin/ring-swagger-ui "5.0.0-alpha.0"]                 
                 [ring/ring-jetty-adapter "1.11.0-alpha1"]]
  :main ^:skip-aot apisvc.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}
             :dev [:project/dev :profiles/dev]
             :test [:project/test :profiles/test]
             :profiles/dev {}
             :profiles/test {}
             :project/dev {:source-paths ["src" "tool-src"]
                           :dependencies []
                           plugins [[lein-auto "0.1.3"]]}
             :project/test {}})
