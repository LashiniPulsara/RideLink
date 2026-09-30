# RideLink - Fare & Payment Service

## Owner

- **Member 4** - Primary owner of Fare & Payment Service

## Technology Stack

- Java 24
- Spring Boot 4.1.1
- Spring Data MongoDB
- Maven
- JUnit 5 & Mockito
- Swagger / OpenAPI
- Postman

## Prerequisites

- Java 24 (JDK)
- Maven (or use the included Maven wrapper mvnw)
- MongoDB running on localhost:27017

## How to Build

```bash
cd fare-payment-service
mvnw.cmd clean install
```

## How to Run

```bash
mvnw.cmd spring-boot:run
```

The service runs on:

```text
http://localhost:8080
```

## Running Tests

```bash
mvnw.cmd test
```

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/fares/estimate` | Calculate estimated fare |
| POST | `/api/fares/final` | Calculate final fare |
| POST | `/api/payments` | Create a simulated payment |
| PUT | `/api/payments/{paymentId}/status` | Update payment status |
| GET | `/api/payments/{paymentId}/receipt` | Get payment receipt |

## Fare Calculation

The fare is calculated using the base fare, distance, and rate per kilometre.

Example:

```text
Base Fare = 200
Rate per KM = 100
Distance = 5 KM

Total Fare = 200 + (5 × 100)
           = 700
```

## Example API Requests

### Fare Estimation

```text
POST /api/fares/estimate?rideId=RIDE001&distance=5
```

### Final Fare

```text
POST /api/fares/final?rideId=RIDE001&distance=5
```

### Create Payment

```text
POST /api/payments?rideId=RIDE001&amount=700&paymentMethod=CARD
```

### Update Payment Status

```text
PUT /api/payments/{paymentId}/status?status=COMPLETED
```

### Get Payment Receipt

```text
GET /api/payments/{paymentId}/receipt
```

## Postman Collection

The Postman collection is available at:

```text
postman/RideLink-Fare-Payment.postman_collection.json
```

The collection includes:

- Fare estimation
- Final fare calculation
- Payment creation
- Payment status update
- Payment receipt retrieval
- Invalid fare distance test
- Invalid payment ID test

## Swagger / OpenAPI

Swagger/OpenAPI documentation is available at:

```text
http://localhost:8080/swagger-ui.html
```
