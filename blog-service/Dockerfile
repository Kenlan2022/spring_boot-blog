# Stage 1: Build the application using a Maven image
# This stage will download dependencies and build the JAR
FROM maven:3.8.5-openjdk-17 AS builder

# Set the working directory in the container
WORKDIR /app

# Copy the entire project context (requires build context to be project root)
COPY . .

# Run Maven to build only the blog-service module and its dependencies (-am)
# -DskipTests skips running tests to speed up the build
RUN mvn -pl blog-service -am clean package -DskipTests

# Stage 2: Create the final, lean image
# This stage will contain only the JRE and the application JAR
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file from the builder stage to the final image
COPY --from=builder /app/blog-service/target/blog-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application runs on
EXPOSE 8081

# The command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]