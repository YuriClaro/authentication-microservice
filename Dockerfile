FROM openjdk:21-jdk

WORKDIR /app

COPY build/libs/authentication_microservice-0.0.1-SNAPSHOT.jar /app/authentication_microservice.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "authentication_microservice.jar"]
