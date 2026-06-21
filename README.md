# authApi

A Spring Boot authentication API using JWT, Spring Security, Spring Data JPA, and MySQL.

## Overview

This project provides basic user registration, login, and user management endpoints.

Key features:
- User registration (`POST /users/register`)
- User login with JWT token generation (`POST /users/login`)
- Protected user CRUD operations (`GET`, `PUT`, `DELETE /users/{id}`)
- Database persistence using Spring Data JPA and MySQL
- Password hashing with bcrypt
- Stateless session management with Spring Security

## Technologies

- Java 21
- Spring Boot 4.1.0
- Spring Web MVC
- Spring Security
- Spring Data JPA
- JWT (`io.jsonwebtoken`)
- MySQL Connector/J
- Lombok

## Prerequisites

- JDK 21
- Maven
- MySQL server

## Configuration

The application reads database settings from `src/main/resources/application.properties`.

Default configuration:

```properties
spring.application.name=authApi
server.port=8080
spring.datasource.url=jdbc:mysql://localhost:3306/auth_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

Update the `spring.datasource.username` and `spring.datasource.password` values to match your MySQL credentials.

## Build and Run

From the project root:

```bash
mvn clean package
mvn spring-boot:run
```

Or build and run the JAR:

```bash
mvn clean package
java -jar target/authApi-0.0.1-SNAPSHOT.jar
```

The application starts on:

- `http://localhost:8080`

## API Endpoints

### Register a new user

`POST /users/register`

Request body:

```json
{
  "email": "user@example.com",
  "password": "secret123",
  "role": "USER",
  "phoneNumber": "1234567890",
  "address": "123 Main Street"
}
```

Response includes user details.

### Login

`POST /users/login`

Request body:

```json
{
  "email": "user@example.com",
  "password": "secret123"
}
```

Response:

```json
{
  "token": "<jwt-token>",
  "msg": "login Success!"
}
```

### Get user by ID

`GET /users/{id}`

Requires authentication.

### Update user

`PUT /users/{id}`

Requires authentication.

Request body is the same format as registration.

### Delete user

`DELETE /users/{id}`

Requires authentication.

## Security

Spring Security allows unauthenticated access to:

- `/users/login`
- `/users/register`

All other requests require authentication.

## Notes

- The database is created automatically if it does not already exist.
- Passwords are stored hashed using bcrypt.
- JWT generation is handled by the `JwtService` in the security package.

## Helpful commands

```bash
mvn test
mvn spring-boot:run
```
