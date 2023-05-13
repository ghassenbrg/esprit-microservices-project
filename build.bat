@echo off
mvn clean install -DskipTests & ^
docker build -t user-management-service:1.0.0-SNAPSHOT -f ./user-management-service/Dockerfile ./user-management-service & ^
docker build -t inventory-service:1.0.0-SNAPSHOT -f ./inventory-service/Dockerfile ./inventory-service & ^
docker build -t product-catalog-service:1.0.0-SNAPSHOT -f ./product-catalog-service/Dockerfile ./product-catalog-service & ^
docker build -t order-processing-service:1.0.0-SNAPSHOT -f ./order-processing-service/Dockerfile ./order-processing-service & ^
docker build -t config-server:1.0.0-SNAPSHOT -f ./config-server/Dockerfile ./config-server & ^
docker build -t payment-service:1.0.0-SNAPSHOT -f ./payment-service/Dockerfile ./payment-service & ^
docker build -t notification-service:1.0.0-SNAPSHOT -f ./notification-service/Dockerfile ./notification-service & ^
docker build -t dummy-service:1.0.0-SNAPSHOT -f ./dummy-service/Dockerfile ./dummy-service & ^
docker build -t eureka-server:1.0.0-SNAPSHOT -f ./eureka-server/Dockerfile ./eureka-server & ^
docker build -t gateway:1.0.0-SNAPSHOT -f ./gateway/Dockerfile ./gateway