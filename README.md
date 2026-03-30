# PDF Tool MVP

## Features
- PDF Resize
- Async Processing (RabbitMQ)

## Tech Stack
- Spring Boot
- RabbitMQ
- PDFBox

## Run Locally

### 1. Start RabbitMQ
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management

### 2. Run Backend
mvn spring-boot:run

### 3. Open Frontend
open index.html
