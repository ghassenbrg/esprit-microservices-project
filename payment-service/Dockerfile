FROM openjdk:11-jre-slim
WORKDIR /app
COPY target/payment-service-1.0.0-SNAPSHOT.jar /payment-service-1.0.0-SNAPSHOT.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "/payment-service-1.0.0-SNAPSHOT.jar"]