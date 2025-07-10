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

#### `GET /api/posts/tags/{tagName}`
Retrieves all posts associated with a specific tag.

- **Parameters:**
  - `tagName` (path): The name of the tag.
- **Response:** `200 OK` with an array of `PostDto` objects.

### Tags

#### `GET /api/tags`
Retrieves a list of all tags.

- **Response:** `200 OK` with an array of `TagDto` objects.

#### `POST /api/tags`
Creates a new tag.

- **Request Body:** `CreateTagRequest` object.
- **Response:** `201 Created` with the newly created `TagDto` object.

## Data Transfer Objects (DTOs)

### `PostDto`
```json
{
  "id": "string",
  "title": "string",
  "content": "string",
  "author": "string",
  "createdAt": "2025-07-06T12:00:00Z",
  "tags": [
    {
      "id": "string",
      "name": "string"
    }
  ]
}
```

### `CreatePostRequest`
```json
{
  "title": "string",
  "content": "string",
  "author": "string",
  "tags": ["string"]
}
```

### `UpdatePostRequest`
```json
{
  "title": "string",
  "content": "string",
  "tags": ["string"]
}
```

### `TagDto`
```json
{
  "id": "string",
  "name": "string"
}
```

### `CreateTagRequest`
```json
{
  "name": "string"
}
```
