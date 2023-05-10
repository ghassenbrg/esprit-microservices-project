FROM openjdk:11-jre-slim
WORKDIR /app
COPY target/user-management-service-1.0.0-SNAPSHOT.jar /user-management-service-1.0.0-SNAPSHOT.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "/user-management-service-1.0.0-SNAPSHOT.jar"]