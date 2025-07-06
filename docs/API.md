# Blog Service API Documentation

This document outlines the RESTful API endpoints for the Blog Service.

## Base URL
`http://localhost:8080` (or the configured port)

## Endpoints

### Posts

#### `GET /api/posts`
Retrieves a list of all blog posts.

- **Response:** `200 OK` with an array of `PostDto` objects.

#### `GET /api/posts/{id}`
Retrieves a single blog post by its ID.

- **Parameters:**
  - `id` (path): The ID of the post.
- **Response:** `200 OK` with a `PostDto` object, or `404 Not Found` if the post does not exist.

#### `POST /api/posts`
Creates a new blog post.

- **Request Body:** `CreatePostRequest` object.
- **Response:** `201 Created` with the newly created `PostDto` object.

#### `PUT /api/posts/{id}`
Updates an existing blog post.

- **Parameters:**
  - `id` (path): The ID of the post to update.
- **Request Body:** `UpdatePostRequest` object.
- **Response:** `200 OK` with the updated `PostDto` object, or `404 Not Found` if the post does not exist.

#### `DELETE /api/posts/{id}`
Deletes a blog post by its ID.

- **Parameters:**
  - `id` (path): The ID of the post to delete.
- **Response:** `204 No Content` on successful deletion, or `404 Not Found` if the post does not exist.

## Data Transfer Objects (DTOs)

### `PostDto`
```json
{
  "id": "string",
  "title": "string",
  "content": "string",
  "author": "string",
  "createdAt": "2025-07-06T12:00:00Z"
}
```

### `CreatePostRequest`
```json
{
  "title": "string",
  "content": "string",
  "author": "string"
}
```

### `UpdatePostRequest`
```json
{
  "title": "string",
  "content": "string"
}
```
