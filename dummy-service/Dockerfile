FROM openjdk:11-jre-slim
WORKDIR /app
COPY target/dummy-service-1.0.0-SNAPSHOT.jar /dummy-service-1.0.0-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/dummy-service-1.0.0-SNAPSHOT.jar"]