# Use OpenJDK 17 as base image
FROM openjdk:17-jdk-slim

# Set work directory
WORKDIR /app

# Copy built JAR file
COPY target/task-0.0.1-SNAPSHOT.jar app.jar

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]