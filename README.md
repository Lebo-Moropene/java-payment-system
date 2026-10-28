# Java Payment System

A Java project for practising object-oriented programming (OOP), unit testing, and Test-Driven Development (TDD). This project models payments and explores how to store and retrieve payment records using SQLite.

## Technology Stack

- **Java 21** — application development
- **Maven** — dependency management and build automation
- **JUnit 5** — unit testing
- **SQLite** — persistent payment storage
- **JDBC** — database connectivity
- **Git and GitHub** — version control

## Current Features

- **Payment model:** Stores a payment ID, amount, payment method, and status.
- **Payment methods:** Credit card, debit card, and EFT.
- **Payment statuses:** Pending, successful, and failed.
- **Input validation:** Validates required payment details and prevents invalid status assignments.
- **In-memory repository:** Stores payment records using Java collections.
- **SQLite persistence:** Saves payment records in a database.
- **Database connectivity:** Opens SQLite database connections using JDBC.
- **Payment retrieval:** Finds stored payments by payment ID.
- **Duplicate protection:** Prevents duplicate payment IDs when saving records.
- **Unit testing:** Tests payment models, enums, repositories, database connectivity, and payment-service behaviour.

## Project Structure

```text
src/
├── main/
│   └── java/za/co/clemzito/
│       ├── Main.java
│       ├── database/
│       │   └── DatabaseConnection.java
│       └── payments/
│           ├── Payment.java
│           ├── PaymentMethod.java
│           ├── PaymentRepository.java
│           ├── PaymentService.java
│           ├── PaymentStatus.java
│           └── SqlitePaymentRepository.java
└── test/
    └── java/za/co/clemzito/
        ├── database/
        │   └── DatabaseConnectionTest.java
        └── payments/
            ├── PaymentMethodTest.java
            ├── PaymentRepositoryTest.java
            ├── PaymentServiceTest.java
            ├── PaymentStatusTest.java
            ├── PaymentTest.java
            └── SqlitePaymentRepositoryTest.java
```

## Getting Started

### Prerequisites

Ensure that you have the following installed:

- Java Development Kit (JDK) 21
- Apache Maven
- Git

### Run the Tests

Run all tests and verify that the project builds successfully:

```bash
mvn clean test
```

### Build the Project

Package the application into a JAR file:

```bash
mvn package
```

## Test Results

The latest test run completed successfully:

- **Tests run:** 26
- **Failures:** 0
- **Errors:** 0
- **Build status:** SUCCESS

## Test-Driven Development

This project follows the Red-Green-Refactor cycle:

1. **RED** — Write a test and confirm that it fails for the expected reason.
2. **GREEN** — Implement the minimum code needed to make the test pass.
3. **REFACTOR** — Improve the code while keeping the tests passing.

## Planned Improvements

- Add tests for restoring successful and failed payment statuses from SQLite.
- Improve database exception handling.
- Introduce a common repository interface for in-memory and SQLite storage.
- Strengthen payment validation and status-transition rules.
- Expand payment-processing functionality.
- Explore REST API integration.
- Add PostgreSQL and Spring Boot as future learning milestones.
- Introduce Docker and continuous integration.

## Author

**Lebo Moropene**

GitHub: [Lebo-Moropene](https://github.com/Lebo-Moropene)
