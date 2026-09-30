# Driver and Vehicle Service

This microservice manages driver and vehicle information for the RideLink platform.

## Technologies
- Java
- Spring Boot
- Spring Data MongoDB
- MongoDB
- Maven
- REST API
- Swagger / OpenAPI

## Service Port

The service runs on port 8081.

## API Documentation

Swagger UI: http://localhost:8081/swagger-ui.html

## Database

MongoDB database: driver_vehicle_db

## Main Features

### Driver Management
- Create a driver
- Get all drivers
- Get a driver by ID
- Update driver details
- Delete a driver

### Vehicle Management
- Create a vehicle
- Get all vehicles
- Get a vehicle by ID
- Update vehicle details
- Delete a vehicle
- Find vehicles by driver ID

## Project Structure

driver-vehicle-service/
+-- .mvn/
+-- postman/
+-- src/
+-- .gitattributes
+-- .gitignore
+-- mvnw
+-- mvnw.cmd
+-- pom.xml
+-- README.md
