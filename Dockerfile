# Use OpenJDK 21 as base image
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy Maven wrapper and project files
COPY mvnw pom.xml ./
COPY .mvn .mvn
COPY src ./src

# Ensure Maven wrapper is executable
RUN chmod +x mvnw

# Build the application
RUN ./mvnw clean package -DskipTests

# Expose the server port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/*.jar"]
