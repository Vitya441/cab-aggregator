# cab-aggregator

## 📖 Introduction
Cab Aggregator is a microservices-based application for managing a taxi aggregator system. The project is built using Spring Boot and incorporates Kafka, Keycloak, Stripe, ELK and others.

## ✅ Before running
1. Make sure that your computer ports 8080 - 8088, 8090, 8761, 5434, 9091, 9092, 9200, 27017, 22181, 5044, 5601, 9090, 3000, 9411 are not busy
2. Docker and docker-compose installed on your system

## 🚀 Running the project
Main branch (all in the containers):
1. `git clone https://github.com/Vitya441/cab-aggregator`
2. `mvn clean package -DskipTests`
3. `docker compose up`

Dev branch (microservices locally):
1. `git clone https://github.com/Vitya441/cab-aggregator -b dev`
2. `cd cab-aggregator`
3. `docker compose up -f docker-compose-dev.yml`
4. Run spring boot microservices locally with your favourite IDE

## 👁️ Observability
Eureka - `http://localhost:8761` 

Kibana - `http://localhost:5601`

Grafana - `http://localhost:3000`

Zipkin - `http://localhost:9411`

KafkaUI - `http://localhost:8090`

