# Blog Service Architecture

This document describes the high-level architecture of the Blog Service.

## Overview
The Blog Service is a Spring Boot application that provides RESTful APIs for managing blog posts. It interacts with an H2 in-memory database for data persistence and registers itself with a Eureka Server for service discovery.

## Components

### 1. Controller Layer (`com.example.blog.controller`)
- Handles incoming HTTP requests.
- Maps requests to appropriate service methods.
- Returns HTTP responses.

### 2. Service Layer (`com.example.blog.service`)
- Contains business logic.
- Interacts with the repository layer to perform CRUD operations.
- Orchestrates data flow.

### 3. Repository Layer (`com.example.blog.repository`)
- Provides an abstraction over the data storage.
- Uses Spring Data JPA for database interactions.

### 4. Entity Layer (`com.example.blog.entity`)
- Defines the data model (e.g., `Post` entity).
- Mapped to database tables.

### 5. DTO Layer (`com.example.blog.dto`)
- Data Transfer Objects used for request and response payloads.
- Decouples the internal data model from the API.

### 6. Exception Handling (`com.example.blog.exception`)
- Global exception handler to provide consistent error responses.

## External Integrations

### Keycloak
- The Blog Service is integrated with Keycloak for user authentication and authorization.
- It acts as an OAuth2 Resource Server, validating JWTs issued by Keycloak.

### Nginx
- Nginx is used as a reverse proxy in front of the application.
- It routes requests to the appropriate services (blog-service or Keycloak).

### Eureka Server
- The Blog Service registers itself with the Eureka Server for service discovery.
- Other microservices can discover and communicate with the Blog Service via Eureka.

### H2 Database
- An in-memory database used for development and testing.
- Can be replaced with a persistent database (e.g., PostgreSQL, MySQL) in production.

## Data Flow Example (Get All Posts)
1. A client sends a `GET /api/posts` request to the Blog Service.
2. `PostController` receives the request and calls `PostService.getAllPosts()`.
3. `PostService` calls `PostRepository.findAll()`.
4. `PostRepository` retrieves data from the H2 database.
5. Data is mapped to `PostDto` objects.
6. `PostController` returns the list of `PostDto` objects as a JSON response.
