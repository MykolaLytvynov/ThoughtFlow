# ThoughtFlow Application

A Spring Boot project using MongoDB for posts and comments management, implementing JWT-based user authentication.

## Features

- User registration and authentication with JWT.
- CRUD operations for posts and comments.
- Filtering posts by topics.
- MongoDB as the database.

## Technologies

- Spring Boot (Spring Security, Spring Data MongoDB)
- JWT for authentication
- MongoDB as the database
- Lombok for reducing boilerplate code

## Configure MongoDB
You can either download MongoDB locally or run it through Docker.

### Run MongoDB through Docker
To quickly run MongoDB in Docker, create a MongoDB container using the following command:
   ```
    docker run -d --name mongodb -p 27017:27017 -e MONGO_INITDB_ROOT_USERNAME=admin -e MONGO_INITDB_ROOT_PASSWORD=1234 mongo:latest
   ```
### Install MongoDB Locally
Alternatively, you can download and install MongoDB from the MongoDB Download page.

## API Endpoints

### Register a new user

**POST** `/api/auth/register`

**Request body:**

```json
{
  "username": "MykolaPR",
  "password": "password123",
  "favoriteTopics": ["TECHNOLOGY", "SCIENCE"]
}
```

**Response:**

```json
{
  "accessToken": "your-access-token",
  "refreshToken": "your-refresh-token"
}
```

### Login

**POST** `/api/auth/login`

**Request body:**

```json
{
  "username": "MykolaPR",
  "password": "password123"
}
```

**Response:**

```json
{
  "accessToken": "your-access-token",
  "refreshToken": "your-refresh-token"
}
```

### Refresh Access Token

**POST** `/api/auth/refresh`

This endpoint is used to refresh access token. When the access token expires, you can use the refresh token to obtain a new access token.

**Request body:**

```json
{
  "refreshToken": "your-refresh-token"
}
```

**Response:**

```json
{
  "accessToken": "your-new-access-token",
  "refreshToken": "your-refresh-token"
}
```

### Create a Post

**POST** `/api/posts`

Authorization: Bearer `your-access-token`

**Request body:**

```json
{
  "title": "MongoDB with Spring Boot",
  "content": "In this post, we will explore how to integrate MongoDB with Spring Boot.",
  "topic": "TECHNOLOGY"
}
```

**Response:**

```json
{
   "id": "50a5806f-26ee-419e-9ae6-6be17ca83da8",
   "title": "MongoDB with Spring Boot",
   "content": "In this post, we will explore how to integrate MongoDB with Spring Boot.",
   "author": {
       "username": "MykolaPR"
   },
   "topic": "TECHNOLOGY",
   "comments": null,
   "createdAt": "2025-01-29T17:15:43.869"
}
```

### Get Posts by User's Favorite Topics

**GET** `/api/posts`

Authorization: Bearer `your-access-token`

The request retrieves posts based on the favorite topics of the logged-in user.

**Response example:**

```json
{
    "page": 0,
    "content": [
        {
            "id": "d0266c52-e460-4009-bcbf-575156576e9d",
            "title": "Why should I use a refresh token?",
            "content": "I used a token, and as I understood, developers can also use a second token.",
            "author": {
                "username": "UserHost"
            },
            "topic": "TECHNOLOGY",
            "comments": null,
            "createdAt": "2025-01-30T12:44:05.999"
        },
        {
            "id": "50a5806f-26ee-419e-9ae6-6be17ca83da8",
            "title": "MongoDB with Spring Boot",
            "content": "In this post, we will explore how to integrate MongoDB with Spring Boot.",
            "author": {
                "username": "MykolaPR"
            },
            "topic": "TECHNOLOGY",
            "comments": [
                {
                    "username": "Mykola123",
                    "content": "Pretty good!",
                    "createdAt": "2025-01-29T18:17:29.354"
                }
            ],
            "createdAt": "2025-01-29T17:15:43.869"
        }
    ],
    "size": 100,
    "totalElements": 2,
    "totalPages": 1
}
```

### Add a Comment to a Post

**POST** `/api/posts/{postId}/comments`

Authorization: Bearer `your-access-token`

**Request body:**

```json
{
  "comment": "Pretty good!"
}
```

**Response:**

```json
{
  "id": "50a5806f-26ee-419e-9ae6-6be17ca83da8",
  "title": "MongoDB with Spring Boot",
  "content": "In this post, we will explore how to integrate MongoDB with Spring Boot.",
  "author": {
      "username": "MykolaPR"
  },
  "topic": "TECHNOLOGY",
  "comments": [
      {
          "username": "Mykola123",
          "content": "Pretty good!",
          "createdAt": "2025-01-29T18:17:29.354"
      }
  ],
  "createdAt": "2025-01-29T17:15:43.869"
}
```

## Dependencies

- Spring Boot (Security, Data MongoDB, Web)
- Lombok
- JWT (jjwt)

## Author

Mykola Lytvynov
