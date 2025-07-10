# Database Schema

This document outlines the database schema for the Blog Service.

## Tables

### `posts`

| Column | Type | Constraints |
|---|---|---|
| `id` | `BIGINT` | Primary Key, Auto-increment |
| `title` | `VARCHAR(255)` | Not Null |
| `content` | `TEXT` | |

### `tags`

| Column | Type | Constraints |
|---|---|---|
| `id` | `BIGINT` | Primary Key, Auto-increment |
| `name` | `VARCHAR(255)` | Not Null, Unique |

### `post_tags` (Join Table)

| Column | Type | Constraints |
|---|---|---|
| `post_id` | `BIGINT` | Foreign Key to `posts(id)` |
| `tag_id` | `BIGINT` | Foreign Key to `tags(id)` |
