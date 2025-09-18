# Stage 1: Build the Spring Boot application with Maven
FROM eclipse-temurin:17-jdk-focal AS builder

WORKDIR /app

# Copy Maven wrapper
COPY mvnw .
COPY .mvn .mvn

# Copy pom.xml first (for dependency caching)
COPY pom.xml .

# Download dependencies (so Docker can cache this layer)
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src src

# Package the application (skip tests to speed up build)
RUN ./mvnw package -DskipTests

# Stage 2: Create the final lightweight runtime image
FROM eclipse-temurin:17-jre-focal

WORKDIR /app

# Copy the built JAR into the runtime image
COPY --from=builder /app/target/*.jar app.jar

# Expose the port (adjust if your app uses another port)
EXPOSE 9090

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
