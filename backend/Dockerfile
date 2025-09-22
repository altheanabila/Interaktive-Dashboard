# Use working Maven image
FROM maven:3.8.6-eclipse-temurin-17 AS build

# Set working directory
WORKDIR /app

# Copy the Dockerfile's directory (backend) to /app
COPY . .

# Build the application
RUN mvn clean package -DskipTests

# Create runtime image
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy the built JAR from build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar", "--server.port=8080"]