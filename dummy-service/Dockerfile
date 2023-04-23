FROM openjdk:11-jre-slim
WORKDIR /app
COPY target/dummy-service-0.0.1-SNAPSHOT.jar /dummy-service-0.0.1-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/dummy-service-0.0.1-SNAPSHOT.jar"]