## BUILDER
FROM maven:3.8.3-openjdk-17 as builder
WORKDIR /app

# Maven download dependecies
COPY pom.xml ./
RUN /usr/local/bin/mvn-entrypoint.sh mvn dependency:resolve
COPY . ./

# Maven build
RUN mvn clean package -DskipTests=true
RUN cp target/contact.jar /app/backend.jar
RUN chmod a+x /app/backend.jar

## TOOLING
FROM alpine:latest as tooling
RUN apk --no-cache add unzip

## NewRelic agent
ARG NEWRELIC_AGENT_VERSION=7.11.0
RUN wget -O newrelic.jar https://download.newrelic.com/newrelic/java-agent/newrelic-agent/$NEWRELIC_AGENT_VERSION/newrelic-agent-$NEWRELIC_AGENT_VERSION.jar \
  && chmod a+x newrelic.jar

## MAIN
FROM gcr.io/distroless/java17

COPY --from=builder /app/backend.jar /usr/share/
COPY --from=tooling newrelic.jar /usr/share/newrelic/
COPY --from=builder /app/docker/newrelic.yml /usr/share/newrelic/
EXPOSE 8080
ENTRYPOINT ["java", "-javaagent:/usr/share/newrelic/newrelic.jar", "-XX:+PreserveFramePointer", "-jar", "/usr/share/backend.jar"]
