FROM openjdk:11-jre-slim
WORKDIR /app
COPY target/notification-service-1.0.0-SNAPSHOT.jar /notification-service-1.0.0-SNAPSHOT.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "/notification-service-1.0.0-SNAPSHOT.jar"]