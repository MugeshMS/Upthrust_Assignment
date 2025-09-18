# Stage 1: Build the Spring Boot application with Maven
FROM eclipse-temurin:17-jdk-focal AS builder

WORKDIR /app

# Copy Maven wrapper and configuration
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Make Maven wrapper executable (this fixes your error)
RUN chmod +x mvnw

# Download dependencies (cached if pom.xml hasn’t changed)
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src src

# Build the application (skip tests for faster build)
RUN ./mvnw package -DskipTests

# Stage 2: Create the final lightweight runtime image
FROM eclipse-temurin:17-jre-focal

WORKDIR /app

# Copy the built JAR into the runtime image
COPY --from=builder /app/target/*.jar app.jar

# Expose the app port (Render sets PORT env var dynamically)
EXPOSE 9090

# Run Spring Boot app (Render overrides PORT automatically)
ENTRYPOINT ["java", "-jar", "app.jar"]
