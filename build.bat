@echo off
mvn clean install -DskipTests & ^
docker build -t keycloak:21.1.0 -f ./keycloak/Dockerfile ./keycloak & ^
docker build -t dummy-service:0.0.1-SNAPSHOT -f ./dummy-service/Dockerfile ./dummy-service & ^
docker build -t eureka-server:0.0.1-SNAPSHOT -f ./eureka-server/Dockerfile ./eureka-server & ^
docker build -t gateway:0.0.1-SNAPSHOT -f ./gateway/Dockerfile ./gateway