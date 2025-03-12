# Stage 1: Build the application
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Copy Gradle-related files first (cache dependencies)
COPY gradlew build.gradle.kts settings.gradle.kts ./
COPY gradle gradle
RUN chmod +x gradlew

# Download dependencies to improve caching
RUN ./gradlew dependencies

# Copy the source code
COPY src src

# Build the application (skip tests for faster builds)
RUN ./gradlew bootJar -x test

# Stage 2: Run the application
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the server port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

# Use the following command to build the image
# docker build -t kamppis-server .

# Use the following command to run the container
# docker run --rm -p 8080:8080 -v "$(pwd)/src:/app/src" -v "$(pwd)/build:/app/build" kamppis-server
