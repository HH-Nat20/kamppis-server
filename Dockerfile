# Use Java 21 as base image
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy only the Gradle wrapper and configuration files
COPY gradlew build.gradle.kts settings.gradle.kts ./
COPY gradle gradle

# Ensure Gradle wrapper is executable
RUN chmod +x gradlew

# Copy the source code
COPY src src

# Expose the server port
EXPOSE 8080

# Build the application
RUN ./gradlew build

# Keep the container running with live-reload support
CMD ["./gradlew", "bootRun"]

# Use the following command to build the image
# docker build -t kamppis-server .

# Use the following command to run the container
# docker run --rm -p 8080:8080 -v "$(pwd)/src:/app/src" -v "$(pwd)/build:/app/build" kamppis-server
