# Backend Documentation for CRUD Application (Departments and Employees)

## Overview

This is the backend of a CRUD application built using **Spring Boot** with **H2** as the embedded database. It provides a RESTful API for managing **Departments** and **Employees**, including authentication using **JWT tokens**.

### Tech Stack:
- **Spring Boot** (RESTful APIs)
- **Spring Data JPA** (ORM)
- **H2 Database** (In-memory)
- **Spring Security** (JWT Authentication)
- **Hibernate** (JPA Implementation)
- **Maven** (Build tool)

## Endpoints

### Authentication
- **POST /authenticate**
    - **Description**: Authenticates the user and returns a JWT token.
    - **Request Body**:
      ```json
      {
        "username": "admin",
        "password": "password"
      }
      ```
    - **Response**:
      ```json
      {
        "token": "your-jwt-token"
      }
      ```

### Departments API

#### 1. Get All Departments (Paginated)
- **GET /api/departments**
    - **Description**: Fetches all departments in a paginated format.
    - **Query Params**:
        - `page`: Page number (default 0)
        - `size`: Page size (default 10)
    - **Authorization**: Requires JWT token in the `Authorization` header.
    - **Response**:
      ```json
      {
        "content": [
          {
            "id": 1,
            "name": "HR"
          }
        ],
        "totalPages": 1,
        "totalElements": 1,
        "size": 10,
        "number": 0
      }
      ```

#### 2. Get Department by ID
- **GET /api/departments/{id}**
    - **Description**: Fetches a specific department by its ID.
    - **Path Param**: `id` (Department ID)
    - **Authorization**: Requires JWT token in the `Authorization` header.
    - **Response**:
      ```json
      {
        "id": 1,
        "name": "HR"
      }
      ```
    - **404 Not Found**: Returned if the department does not exist.

#### 3. Create a New Department
- **POST /api/departments**
    - **Description**: Creates a new department.
    - **Request Body**:
      ```json
      {
        "name": "Finance"
      }
      ```
    - **Authorization**: Requires JWT token in the `Authorization` header.
    - **Response**:
      ```json
      {
        "id": 2,
        "name": "Finance"
      }
      ```

#### 4. Update a Department
- **PUT /api/departments/{id}**
    - **Description**: Updates an existing department by ID.
    - **Path Param**: `id` (Department ID)
    - **Request Body**:
      ```json
      {
        "name": "Updated Department"
      }
      ```
    - **Authorization**: Requires JWT token in the `Authorization` header.
    - **Response**:
      ```json
      {
        "id": 1,
        "name": "Updated Department"
      }
      ```

#### 5. Delete a Department
- **DELETE /api/departments/{id}**
    - **Description**: Deletes a department by ID.
    - **Path Param**: `id` (Department ID)
    - **Authorization**: Requires JWT token in the `Authorization` header.
    - **Response**:
      ```json
      {
        "deleted": true
      }
      ```

## Database

### H2 In-Memory Database Configuration
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: (leave blank unless configured)

### Accessing the H2 Console
- **URL**: `http://localhost:8080/h2-console`
- **Username**: `sa`
- **Password**: (leave blank unless configured)

The database schema is automatically generated and updated based on the entities (`Department`, `Employee`) using Hibernate’s **DDL auto-generation**.

## Error Handling

- **404 Not Found**: Returned if a requested department or employee does not exist.
    - Example:
      ```json
      {
        "error": "Department not found for this id: 10"
      }
      ```
- **400 Bad Request**: Returned for validation errors (e.g., missing required fields).
    - Example:
      ```json
      {
        "error": "Department name should have at least 2 characters"
      }
      ```

### Running the Project

1. Go to the [Releases](https://github.com/your-repo-name/releases) page.
2. Download the latest `casestudy-0.0.1-SNAPSHOT.jar`.
3. Run the project with the following command:

```bash
java -jar your-project-name.jar
```

