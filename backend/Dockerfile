# Use working Maven image
FROM maven:3.8.6-eclipse-temurin-17 AS build

# Set working directory
WORKDIR /app

# Copy the backend files (from the backend directory context)
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Create runtime image
FROM eclipse-temurin:17-jre

# Set working directory
WORKDIR /app

# Copy the built JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar", "--server.port=8080"]