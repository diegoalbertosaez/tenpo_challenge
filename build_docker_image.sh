#!/bin/bash

# generando jar

./mvnw clean install

# construyendo imagen

./mvnw spring-boot:build-image -DskipTests=true

# ejecutando docker-compose
cd ./docker-compose

docker-compose up