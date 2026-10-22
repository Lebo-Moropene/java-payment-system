# Java Payment System

A Java project for practising object-oriented programming, unit testing, and Test-Driven Development (TDD).

## Technology Stack

- Java 21
- Maven
- JUnit 5
- Git and GitHub

## Current Features

- Payment methods: credit card, debit card, and EFT.
- Payment statuses: pending, successful, and failed.
- Payment model containing a payment ID, amount, payment method, and initial status.
- Unit tests for payment methods, statuses, and initial payment status.

## Run the Tests

Ensure Java 21 and Maven are installed.

Run the tests:

```bash
mvn clean test
```

Build the project:

```bash
mvn package
```

## Test-Driven Development

1. RED — write a test and confirm it fails.
2. GREEN — implement the minimum code needed to pass.
3. REFACTOR — improve the code while keeping tests passing.

## Planned Improvements

- Validate payment IDs and amounts.
- Implement payment processing in `PaymentService`.
- Add payment-status transitions.
- Expand unit-test coverage.
- Explore API integration and persistent storage.

## Author

Lebo Moropene

GitHub: [Lebo-Moropene](https://github.com/Lebo-Moropene)
