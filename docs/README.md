# Blog Service

This is the backend service for the blog application, responsible for managing posts, comments, and user interactions.

## Getting Started

### Prerequisites
- Docker and Docker Compose
- Java 17 or higher
- Maven

### Build and Run
1. Start the infrastructure (Keycloak, Nginx, Eureka Server):
   ```bash
   docker-compose up -d
   ```
2. Navigate to the `blog-service` directory.
3. Run `mvnw.cmd clean install` to build the project.
4. Run `java -jar target/blog-0.0.1-SNAPSHOT.jar` to start the service.

## API Documentation
Refer to `docs/API.md` for detailed API endpoints and usage.

## Project Structure
Refer to `docs/ARCHITECTURE.md` for an overview of the system architecture.
