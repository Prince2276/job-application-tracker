# Job Application Tracker

A RESTful backend application built with **Java, Spring Boot, Spring Data JPA, Hibernate, and MySQL** to manage and track job applications throughout the recruitment process.

The project demonstrates core backend development concepts, including layered architecture, CRUD operations, DTO-based data transfer, request validation, custom exception handling, and database integration.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Application Workflow](#application-workflow)
- [Data Model](#data-model)
- [API Documentation](#api-documentation)
- [Validation and Error Handling](#validation-and-error-handling)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [Testing the API](#testing-the-api)
- [Project Structure](#project-structure)
- [Future Improvements](#future-improvements)
- [Author](#author)
- [Live Deployment](#live-deployment)

## Overview

Managing multiple job applications can become difficult when application details, job links, and recruitment statuses are scattered across different platforms.

The **Job Application Tracker** provides a centralized REST API for recording job applications, retrieving saved records, updating application information, filtering applications, and deleting records when required.

The application follows a layered architecture to separate HTTP request handling, business logic, database access, and data representation.

This project is being developed as a practical backend engineering project to strengthen Java and Spring Boot skills.

## Live Deployment

**Live API Base URL:** https://job-application-tracker-3458.onrender.com/api/applications

The backend is deployed on Render and connected to a cloud-hosted MySQL database on Aiven.

**Deployment details:**
- Backend hosting: Render
- Cloud database: Aiven MySQL
- Configuration: Environment variables
- API testing: Postman

> **Note:** The application uses a free Render instance, which may spin down after inactivity. The first request after a period of inactivity can take some time to respond.

### Try the API

- **Retrieve applications:** [GET /api/applications](https://job-application-tracker-3458.onrender.com/api/applications)
- **Base URL (live):**

`https://job-application-tracker-3458.onrender.com/api/applications`

  Use Postman to test the POST, PUT, and DELETE endpoints.


## Features

- **Create Applications:** Store job application details through REST API requests.
- **Retrieve All Applications:** Fetch all saved job application records.
- **Retrieve by ID:** Find a specific application using its unique identifier.
- **Update Applications:** Modify an existing application's details.
- **Delete Applications:** Remove an application by its ID.
- **Filter by Status:** Retrieve applications based on their recruitment status.
- **Filter by Job Role:** Find applications associated with a particular role.
- **DTO-Based Responses:** Separate API request and response objects from the database entity.
- **Input Validation:** Validate required fields, string lengths, and allowed field values.
- **Centralized Exception Handling:** Return structured error responses for validation failures and missing resources.
- **Automatic Application Date:** Set the application date automatically when a new application is created.
- **MySQL Persistence:** Store application records in a relational database.

## Tech Stack

| Technology | Purpose |
|---|---|
| Java 21 | Programming language |
| Spring Boot | Application framework |
| Spring Web | REST API development |
| Spring Data JPA | Repository and database operations |
| Hibernate | ORM and entity persistence |
| MySQL | Relational database |
| Maven | Dependency and build management |
| Bean Validation | Request validation |
| Postman | API testing |
| Git and GitHub | Version control and source code hosting |
| Render | Backend hosting and deployment |
| Aiven MySQL | Cloud database hosting |

## Architecture

The application follows a layered architecture.

```text
Client (Postman / Frontend)
            |
            v
      REST Controller
            |
            v
        Service Layer
            |
            v
      Repository Layer
            |
            v
        MySQL Database
```

### Layer Responsibilities

**1. Controller Layer**

Handles HTTP requests, maps API endpoints, accepts request DTOs, and returns appropriate HTTP responses.

**2. Service Layer**

Contains the business logic, processes requests, coordinates repository operations, and maps entities to response DTOs.

**3. Repository Layer**

Uses Spring Data JPA to perform database operations without requiring repetitive SQL for standard CRUD functionality.

**4. Entity Layer**

Defines the `JobApplication` entity and its mapping to the database table.

**5. DTO Layer**

Contains request and response objects, keeping the API data contract separate from the persistence model.

**6. Exception Layer**

Handles application exceptions and provides structured responses for errors such as missing resources and invalid request fields.

## Application Workflow

For a typical create request:

1. The client sends a JSON request to the REST API.
2. The controller receives the request and triggers validation.
3. The service layer processes the request and applies the required business logic.
4. The repository saves the entity through Spring Data JPA.
5. Hibernate persists the record in MySQL.
6. The service maps the saved entity to a response DTO.
7. The controller returns the response to the client.

## Data Model

The main entity is `JobApplication`.

| Field | Description |
|---|---|
| `id` | Unique application identifier, generated automatically |
| `companyName` | Name of the company |
| `jobRole` | Position applied for |
| `jobType` | Employment type |
| `applicationDate` | Date of application |
| `status` | Current recruitment status |
| `jobUrl` | Link to the job posting |
| `notes` | Additional application details |

### Example Application

```json
{
  "companyName": "Google",
  "jobRole": "Java Developer",
  "jobType": "FULL_TIME",
  "status": "APPLIED",
  "jobUrl": "https://example.com/google-job",
  "notes": "Applied through company career portal"
}
```

The application date is generated automatically by the backend, so it does not need to be supplied in the create request.

## API Documentation

**Base URL (local):**

```text
http://localhost:8080/api/applications
```

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/applications` | Create a job application |
| GET | `/api/applications` | Retrieve all applications |
| GET | `/api/applications/{id}` | Retrieve an application by ID |
| PUT | `/api/applications/{id}` | Update an existing application |
| DELETE | `/api/applications/{id}` | Delete an application |
| GET | `/api/applications/role/{jobRole}` | Filter applications by job role |

The status-filtering endpoint is also implemented. Confirm its exact route in the controller before publishing that specific URL here.

### Create an Application

**Request**

```http
POST /api/applications
Content-Type: application/json
```

```json
{
  "companyName": "TCS",
  "jobRole": "Java Developer",
  "jobType": "FULL_TIME",
  "status": "APPLIED",
  "jobUrl": "https://example.com/tcs-job",
  "notes": "Applied through company career portal"
}
```

**Expected result:** A successful response containing the saved application details, generated ID, application date, and any configured response message.

### Retrieve All Applications

```http
GET /api/applications
```

Returns a list of saved job applications.

### Retrieve an Application by ID

```http
GET /api/applications/1
```

Returns the matching application or an appropriate not-found response when the ID does not exist.

### Update an Application

```http
PUT /api/applications/1
Content-Type: application/json
```

Send the updated fields using the request format expected by the API.

### Delete an Application

```http
DELETE /api/applications/1
```

Deletes the specified application if it exists. A missing application returns an appropriate error response.

### Filter by Job Role

```http
GET /api/applications/role/Java%20Developer
```

Returns applications matching the requested job role. URL-encode spaces and other special characters when necessary.

## Validation and Error Handling

The application uses Bean Validation annotations to reject invalid request data.

Examples include:

- Required company name, job role, job type, status, and job URL.
- Minimum and maximum string-length constraints.
- Pattern-based validation for allowed job types and statuses.
- Validation error responses containing field-specific messages.
- Custom handling for resources that do not exist.
- Centralized exception handling for consistent API errors.

### Example Validation Error

```json
{
  "statusCode": 400,
  "error": "Bad Request",
  "message": "Validation Failed",
  "path": "/api/applications",
  "fieldErrors": {
    "companyName": "Company name must be within 2 to 50 characters long",
    "jobRole": "Job role cannot be empty/blank or null"
  }
}
```

The exact error fields and messages depend on the application's exception handler and validation configuration.

## Getting Started

### Prerequisites

Install the following before running the project:

- JDK 21
- MySQL Server
- Git
- An IDE such as Eclipse or IntelliJ IDEA
- Postman or another REST API client

### 1. Clone the Repository

```bash
git clone https://github.com/Prince2276/job-application-tracker.git
cd job-application-tracker
```

### 2. Create the Database

Open MySQL and execute:

```sql
CREATE DATABASE job_application_tracker;
```

Hibernate can create or update the entity schema when the application starts, according to the configured JPA settings.

### 3. Configure Environment Variables

The application reads its database connection details from environment variables.

The `src/main/resources/application.properties` file should contain:

```properties
spring.application.name=JobApplicationTracker

spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

Set these environment variables on your machine:

| Variable | Example value |
|---|---|
| `DB_URL` | `jdbc:mysql://localhost:3306/job_application_tracker` |
| `DB_USERNAME` | `root` |
| `DB_PASSWORD` | Your local MySQL password |

**Security note:** Never commit actual database passwords, API keys, or other secrets to GitHub. Use environment variables for local development and deployment.

For a temporary Windows Command Prompt session:

```cmd
set DB_URL=jdbc:mysql://localhost:3306/job_application_tracker
set DB_USERNAME=root
set DB_PASSWORD=your_actual_password
```

Replace the example password with your actual local MySQL password. These variables remain available only in that Command Prompt session.

### 4. Run the Application

Using the Maven Wrapper on Windows:

```cmd
mvnw.cmd spring-boot:run
```

Alternatively, import the project into your IDE and run the main class annotated with `@SpringBootApplication`.

The API will be available at:

```text
http://localhost:8080
```

provided the application starts successfully and port 8080 is available.

## Testing the API

Use Postman to test the implemented endpoints.

Recommended testing sequence:

1. Create a valid application using `POST`.
2. Retrieve all records using `GET`.
3. Retrieve a specific record using its ID.
4. Filter records by job role and status.
5. Update an existing record using `PUT`.
6. Test deletion using `DELETE`.
7. Submit invalid request data to verify validation responses.
8. Request a non-existent ID to verify not-found handling.

Check the HTTP status codes, response body, and database records for each operation.

## Project Structure

```text
job-application-tracker/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/example/JobApplicationTracker/
│   │   │       ├── controller/
│   │   │       │   └── JobApplicationController.java
│   │   │       ├── dto/
│   │   │       │   ├── ExceptionResponseDto.java
│   │   │       │   ├── JobApplicationRequest.java
│   │   │       │   ├── JobApplicationResp.java
│   │   │       │   ├── UpdateReqDto.java
│   │   │       │   ├── UpdateRespDto.java
│   │   │       │   └── ValidationExceptionResponseDto.java
│   │   │       ├── entity/
│   │   │       │   └── JobApplication.java
│   │   │       ├── exception/
│   │   │       │   ├── GlobalExceptionHandler.java
│   │   │       │   └── ResourceNotFoundException.java
│   │   │       ├── repository/
│   │   │       │   └── JobApplicationRepository.java
│   │   │       ├── service/
│   │   │       │   └── JobApplicationService.java
│   │   │       └── JobApplicationTrackerApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── .gitignore
├── mvnw
├── mvnw.cmd
└── pom.xml
├── Dockerfile
```

## Future Improvements

Potential enhancements for future versions include:

- Pagination and sorting for application lists.
- More advanced search and filtering.
- Improved API documentation with Swagger/OpenAPI.
- Automated unit and integration tests.
- Authentication and authorization.
- Deployment with a managed production database.
- Continuous integration and automated deployment.
- A frontend dashboard for managing applications visually.

These are planned improvements, not claims that the features are already implemented.

## Author

**Prince Burnwal**

B.Tech — Computer Science and Engineering

GitHub: [Prince2276](https://github.com/Prince2276)

Project Repository: [Job Application Tracker](https://github.com/Prince2276/job-application-tracker)

---

*Built as a hands-on project to practice REST API development, Java, Spring Boot, and relational database integration.*
