# RideLink - Account Service 
## Owner 
 
- **Member 1** - Primary owner of Account Service 
 
## Technology Stack 
 
- Java 24 
- Spring Boot 4.1.1 
- Spring Security with JWT 
- Spring Data MongoDB 
- Maven 
- JUnit 5 & Mockito 
 
## Prerequisites 
 
- Java 24 (JDK) 
- Maven (or use the included Maven wrapper mvnw) 
- MongoDB running on localhost:27017 
 
## How to Build 
 
cd account-service 
mvnw.cmd clean install 
 
## How to Run 
 
mvnw.cmd spring-boot:run 
 
## Running Tests 
 
mvnw.cmd test 
 
## API Endpoints 
 
| Method | Endpoint | Description | Auth Required | 
|--------|----------|-------------|---------------| 
| POST | /api/accounts/login | Authenticate and receive JWT | No | 
| GET | /api/accounts/profile | Get current user profile | Yes | 
| PUT | /api/accounts/profile | Update current user profile | Yes | 
