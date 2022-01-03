FROM openjdk:17
WORKDIR /opt/server
COPY ./target/service-identity-0.0.1-SNAPSHOT.jar server.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "server.jar"]