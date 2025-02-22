# Use a base image with Java 17 (or your desired version)
FROM openjdk:21-jdk-slim

# Copy the Gradle wrapper files
COPY gradlew .
COPY gradle ./gradle

# Set executable permission for gradlew
RUN chmod +x ./gradlew

# Build the application
COPY . .
RUN ./gradlew bootJar

# Expose the port your Spring Boot application runs on
EXPOSE 8082

# Run the application
CMD ["java", "-jar", "build/libs/*.jar"]