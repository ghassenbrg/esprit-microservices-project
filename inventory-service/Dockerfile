FROM openjdk:11-jre-slim
WORKDIR /app
COPY target/inventory-service-1.0.0-SNAPSHOT.jar /inventory-service-1.0.0-SNAPSHOT.jar
EXPOSE 8989
ENTRYPOINT ["java", "-jar", "/inventory-service-1.0.0-SNAPSHOT.jar"]