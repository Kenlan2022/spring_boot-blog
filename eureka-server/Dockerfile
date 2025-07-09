# Stage 1: Build the application
FROM maven:3.8.5-openjdk-17 AS builder
WORKDIR /app
# Copy all the source code
COPY . .
# Build only the eureka-server module
RUN mvn -pl eureka-server -am clean package -DskipTests

# Stage 2: Create the final, small image
FROM openjdk:17-jdk-slim
WORKDIR /app
# Copy the JAR from the builder stage
COPY --from=builder /app/eureka-server/target/eureka-server-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8761
CMD ["java", "-jar", "app.jar"]
