# Kotlin Spring Boot + Docker + PostgreSQL

This is a Kotlin-based Spring Boot application using PostgreSQL, packaged with Docker and orchestrated with Docker Compose.

---

## 🧰 Prerequisites

- Java 17+
- Maven
- Docker
- Docker Compose

---

## 🚀 Build and Run

Use the following commands to build and run everything:

```bash
docker-compose up -d postgres
mvn liquibase:update
mvn clean install
docker-compose up --build
