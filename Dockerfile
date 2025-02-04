# Use Java 21 as base image
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy only the Maven wrapper and configuration files
COPY mvnw pom.xml ./
COPY .mvn .mvn

# Ensure Maven wrapper is executable
RUN chmod +x mvnw

# Expose the server port
EXPOSE 8080

# Keep the container running with live-reload support
CMD ["./mvnw", "spring-boot:run"]

# Use the following command to build the image
# docker build -t kamppis-server .

# Use the following command to run the container
# docker run --rm -p 8080:8080 -v "$(pwd)/src:/app/src" -v "$(pwd)/target:/app/target" kamppis-server
