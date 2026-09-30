# RideLink - Ride Management Service

## Owner

- Member 3 - Primary owner of Ride Management Service

## Technology Stack

- Java 24
- Spring Boot
- Spring Data MongoDB
- MongoDB
- Maven
- JUnit 5 & Mockito
- Swagger / OpenAPI
- Postman

## Prerequisites

- Java 24 (JDK)
- Maven (or use the included Maven wrapper)
- MongoDB running locally

## How to Build

From the RideLink repository root:

```powershell
cd ride-management-service
.\mvnw.cmd clean install
```

## How to Run

From the `ride-management-service` directory:

```powershell
.\mvnw.cmd spring-boot:run
```

## Running Tests

From the `ride-management-service` directory:

```powershell
.\mvnw.cmd test
```

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/rides` | Create a new ride request |
| GET | `/api/rides` | Retrieve all rides |
| GET | `/api/rides/{id}` | Retrieve a ride by ID |
| PUT | `/api/rides/{id}/assign` | Assign a ride |
| PUT | `/api/rides/{id}/accept` | Accept an assigned ride |
| PUT | `/api/rides/{id}/start` | Start a ride |
| PUT | `/api/rides/{id}/complete` | Complete a ride |
| PUT | `/api/rides/{id}/cancel` | Cancel a ride |

## Ride Lifecycle

The Ride Management Service manages the following ride lifecycle:

```text
REQUESTED
    |
    v
ASSIGNED
    |
    v
ACCEPTED
    |
    v
IN_PROGRESS
    |
    v
COMPLETED
```

A ride can also move to `CANCELLED` according to the valid ride lifecycle transition rules.

Invalid ride status transitions are rejected by the service.

## Postman Collection

The Postman collection for the Ride Management Service is located at:

```text
postman/RideLink-Ride-Management.postman_collection.json
```

The collection can be imported into Postman to test and demonstrate the Ride Management Service API.

It includes requests for the ride lifecycle and negative testing scenarios.

## Swagger / OpenAPI

Swagger / OpenAPI documentation is available when the Ride Management Service is running.

Swagger UI can be used to view and test the REST API endpoints exposed by the service.

## Testing

The Ride Management Service includes unit tests for ride business logic and lifecycle behaviour.

Testing is performed using:

- JUnit 5
- Mockito
- Postman
- Swagger / OpenAPI

The Postman collection can be used to demonstrate successful API operations and negative scenarios such as invalid ride status transitions.

## Data Ownership

The Ride Management Service manages its own ride data using MongoDB.

The service maintains its own persistence boundary and does not directly access or modify another microservice's database.

## Project Structure

```text
ride-management-service/
├── postman/
│   └── RideLink-Ride-Management.postman_collection.json
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   └── test/
├── pom.xml
└── README.md
```