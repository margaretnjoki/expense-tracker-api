# Expense Tracker API
[![CI](https://github.com/margaretnjoki/expense-tracker-api/actions/workflows/ci.yml/badge.svg)](https://github.com/margaretnjoki/expense-tracker-api/actions/workflows/ci.yml)

A RESTful API for managing personal expenses and categories. Users can create categories, record expenses, filter expenses by date, and generate spending reports.

Built with: 
- Spring Boot 3.5
- PostgreSQL
- Spring Data JPA
- Flyway
- documented with
- SpringDoc OpenAPI (Swagger)

---

## Features

- Category CRUD operations
- Expense CRUD operations
- Pagination and filtering
- Monthly spending reports
- Category spending reports
- Summary reports
- Input validation
- Flyway database migrations
- Swagger/OpenAPI documentation

---

## Tech Stack

- Java 21
- Spring Boot 3.5
- Spring Data JPA
- PostgreSQL
- Flyway
- Spring Validation
- SpringDoc OpenAPI (Swagger)
- Maven

---

## Running the Project

### 1. Clone the repository

```bash
git clone https://github.com/margaretnjoki/expense-tracker-api.git
cd expense-tracker-api
```

### 2. Create a PostgreSQL database

```sql
CREATE DATABASE expense_db;
```

### 3. Configure the database

Edit:

```
src/main/resources/application.yml
```

Set your PostgreSQL username and password.

Example:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/expense_db
    username: postgres
    password: your_password
```

### 4. Run the application

Using Maven Wrapper:

```bash
./mvnw spring-boot:run
```

Or on Windows:

```bash
mvnw.cmd spring-boot:run
```

The application starts on:

```
http://localhost:8080
```

---

## API Documentation

Swagger UI:

```
http://localhost:8080/swagger-ui/index.html
```

OpenAPI JSON:

```
http://localhost:8080/v3/api-docs
```

---

## Endpoints

### Categories

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | `/api/v1/categories` | Create category |
| GET | `/api/v1/categories` | Get all categories |
| GET | `/api/v1/categories/{id}` | Get category by ID |
| PUT | `/api/v1/categories/{id}` | Update category |
| DELETE | `/api/v1/categories/{id}` | Delete category |

### Expenses

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | `/api/v1/expenses` | Create expense |
| GET | `/api/v1/expenses` | List expenses |
| GET | `/api/v1/expenses/{id}` | Get expense by ID |
| PUT | `/api/v1/expenses/{id}` | Update expense |
| DELETE | `/api/v1/expenses/{id}` | Delete expense |

### Reports

| Method | Endpoint | Description |
|---------|----------|-------------|
| GET | `/api/v1/reports/monthly?year=&month=` | Monthly report |
| GET | `/api/v1/reports/by-category?from=&to=` | Spending by category |
| GET | `/api/v1/reports/categories?min=` | Categories above spending threshold |
| GET | `/api/v1/reports/summary?from=&to=` | Summary report |

---

## Example Request

Create an expense

```bash
curl -X POST http://localhost:8080/api/v1/expenses \
-H "Content-Type: application/json" \
-d '{
  "amountKes":500,
  "description":"Lunch",
  "occurredOn":"2026-07-08",
  "categoryId":"YOUR_CATEGORY_ID"
}'
```

---

## Postman Collection

Import the file:

```
Expense-Tracker.postman_collection.json
```

into Postman to test all API endpoints.

---

## 🔐 JWT Authentication

This API uses **JSON Web Token (JWT)** authentication to secure protected endpoints.

### Features
- User registration with encrypted passwords using **BCrypt**
- User login with JWT token generation
- Stateless authentication
- Role-based authorization (e.g., USER, ADMIN)
- Protected endpoints using Spring Security
- Configurable token expiration
- JWT secret stored securely using environment variables

### Authentication Flow

1. Register a new user.
2. Login using your email and password.
3. Receive a JWT access token.
4. Include the token in every protected request:

```http
Authorization: Bearer <your-jwt-token>
```

5. Spring Security validates the token before granting access.

### Environment Variables

The following environment variables are required:

| Variable | Description |
|----------|-------------|
| `JWT_SECRET` | Secret key used to sign JWT tokens |
| `JWT_EXPIRATION_MS` | Token expiration time in milliseconds |

Example:

```bash
JWT_SECRET=your-super-secret-key-at-least-32-characters
JWT_EXPIRATION_MS=3600000
```

---

## 🐳 Docker Support

The application is fully containerized using **Docker** and can be run alongside PostgreSQL using **Docker Compose**.

### Services

- **expense-tracker-api** – Spring Boot application
- **postgres** – PostgreSQL database

### Start the application

```bash
docker compose up --build
```

### Run in detached mode

```bash
docker compose up -d
```

### Stop the containers

```bash
docker compose down
```

### Rebuild after making changes

```bash
docker compose up --build
```

### View logs

```bash
docker compose logs -f
```

### Container Ports

| Service | Port |
|----------|------|
| Spring Boot API | 8080 |
| PostgreSQL | 5432 |

### Environment Variables

Docker Compose passes the required configuration through environment variables, including:

- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `DB_PASSWORD`
- `JWT_SECRET`
- `SPRING_PROFILES_ACTIVE`

### Benefits of Docker

- Consistent development environment
- Easy application deployment
- Isolated PostgreSQL database
- One-command project startup
- Eliminates "works on my machine" issues

## 🌍 Live Demo

The application is deployed on **Render** and is publicly accessible.

### API Base URL

```
https://expense-tracker-api-di7u.onrender.com
```

### Swagger UI

Explore and test the API using Swagger UI:

```
https://expense-tracker-api-di7u.onrender.com/api/v1/swagger-ui/index.html
```



### Cold Starts

This project is hosted on the **Render Free** plan. If the service has been idle for a while, the first request may take **30–60 seconds** while the application starts up. After the initial request, subsequent requests should respond normally.

If Swagger or an API endpoint appears slow on the first request, please wait for the service to wake up and then refresh the page.


## Future Improvements
- Unit and Integration Tests
- CI/CD pipeline
- Cloud deployment (Render/AWS)


## Author

***Margaret Njoki***