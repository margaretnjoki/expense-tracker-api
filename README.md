# Expense Tracker API

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

## Future Improvements

- JWT Authentication
- Docker support
- Unit and Integration Tests
- CI/CD pipeline
- Cloud deployment (Render/AWS)

---

## Author

***Margaret Njoki***