FROM openjdk:11-jre-slim
WORKDIR /app
COPY target/gateway-0.0.1-SNAPSHOT.jar /gateway-0.0.1-SNAPSHOT.jar
EXPOSE 9191
ENTRYPOINT ["java", "-jar", "/gateway-0.0.1-SNAPSHOT.jar"]