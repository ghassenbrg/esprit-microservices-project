FROM openjdk:11-jre-slim
WORKDIR /app
COPY target/order-processing-service-1.0.0-SNAPSHOT.jar /order-processing-service-1.0.0-SNAPSHOT.jar
EXPOSE 8989
ENTRYPOINT ["java", "-jar", "/order-processing-service-1.0.0-SNAPSHOT.jar"]