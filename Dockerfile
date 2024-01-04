# Use the official Maven/Java image to create a build environment
FROM maven:3.8.4-openjdk-17 AS builder

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml to the container
COPY pom.xml .

# Download dependencies
RUN mvn -B dependency:go-offline

# Copy the project source
COPY src ./src

# Build the application
RUN mvn -B package -DskipTests

# Use AdoptOpenJDK as the base image
FROM adoptopenjdk:17-jre-hotspot

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the builder stage to the current directory
COPY --from=builder /app/target/task-0.0.1-SNAPSHOT.jar ./app.jar

# Expose the port your app runs on
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "app.jar"]