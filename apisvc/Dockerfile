FROM clojure:latest

RUN mkdir -p /var/pokeclj/apisvc
WORKDIR /var/pokeclj/apisvc

COPY project.clj /var/pokeclj/apisvc
RUN lein deps

COPY . /var/pokeclj/apisvc

RUN mv "$(lein uberjar | sed -n 's/^Created \(.*standalone\.jar\)/\1/p')" apisvc-standalone.jar

ENTRYPOINT ["java", "-jar", "apisvc-standalone.jar"]
