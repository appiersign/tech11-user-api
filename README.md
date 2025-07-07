# Users API

A Jakarta EE-based RESTful API for user management with secure password handling and data validation.

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- Jakarta EE 9.1 compatible application server (e.g., WildFly, Payara, or GlassFish)

## Technologies

- Jakarta EE 9.1
- Hibernate 6.4.4
- H2 Database
- JBCrypt for password hashing
- JUnit 5 for testing
- SLF4J & Logback for logging

## Features

- User management (CRUD operations)
- Secure password hashing using BCrypt
- Input validation using Jakarta Bean Validation
- H2 Database integration
- RESTful API endpoints
- Partial updates support
- Custom email validation

## Building the Project

To build the project, run:
`mvn clean package`

This will create a WAR file in the `target` directory named `users-api.war`.

## Deployment

Deploy the generated WAR file to your Jakarta EE compatible application server. For example:

1. Copy `target/users-api.war` to your server's deployment directory
2. Start your application server
3. Access the API at `http://localhost:8080/users-api/`

## Configuration

The application uses H2 database by default. Database configuration can be modified in the `persistence.xml` file.

## API Endpoints

- `GET /api/users` - List all users
- `GET /api/users/{uuid}` - Get a specific user
- `POST /api/users` - Create a new user
- `PATCH /api/users/{uuid}` - Update user (partial updates supported)

## Development

To run the tests:
`mvn test`

## Dependencies

- Jakarta EE Web API 9.1.0
- H2 Database 2.1.214
- Hibernate Core 6.4.4.Final
- JBCrypt 0.4
- Commons BeanUtils 1.9.4
- SLF4J API 2.0.7
- Logback Classic 1.4.8

## Author

[Solomon APPIER-SIGN]