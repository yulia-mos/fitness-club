FROM openjdk:17
WORKDIR /opt/server
COPY ./target/service-abonnement-0.0.1-SNAPSHOT.jar server.jar

EXPOSE 8083
ENTRYPOINT ["java", "-jar", "server.jar"]