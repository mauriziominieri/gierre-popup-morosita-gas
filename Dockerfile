ARG VERSION=11

FROM openjdk:11-jdk as BUILD
COPY . /src
WORKDIR /src
RUN ./gradlew build -x test --stacktrace

FROM openjdk:${VERSION}-jre
COPY --from=BUILD /src/build/libs/gierre-project-webservice-0.0.1.jar /bin/runner/gierre-project-webservice-0.0.1.jar
WORKDIR /bin/runner
EXPOSE 8080
CMD ["java","-Djdk.httpclient.allowRestrictedHeaders=host","-jar","gierre-project-webservice-0.0.1.jar"]