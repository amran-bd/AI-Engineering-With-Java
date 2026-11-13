# Customer Management Service — Technical Constraints

## 1. Purpose

This document defines the technical boundaries within which the Customer Management Service must be implemented.

The AI coding agent may make implementation decisions as needed, provided they remain within these constraints and satisfy the requirements, system specification, and acceptance criteria.

---

## 2. Approved Technology Stack

The application must use the following technologies:

| Technology         | Purpose |
|--------------------|---|
| Java 25            | Programming language |
| Spring Boot 4.x    | Application framework |
| Maven 3.9+         | Build tool |
| Spring Web         | REST API development |
| Spring Data JPA    | Data access |
| H2 Database        | In-memory database for development |
| Jakarta Validation | Request and DTO validation |
| JUnit 5            | Unit and integration testing |
| OpenAPI 3.x        | API documentation and contract |

---

## 3. Architectural Constraints

The application must follow a layered architecture.

### 3.1 REST API Layer

The REST API layer must:

- Handle HTTP requests and responses.
- Use DTOs for external request and response models.
- Delegate business operations to the business layer.
- Not contain business logic.
- Not access repositories directly.

### 3.2 Business Layer

The business layer must:

- Implement business logic.
- Apply business and domain rules.
- Coordinate validation beyond basic request validation.
- Interact with the persistence layer.
- Use interfaces where appropriate.

### 3.3 Persistence Layer

The persistence layer must:

- Use Spring Data JPA repositories.
- Handle database interaction.
- Keep persistence concerns isolated from the REST API layer.

---

## 4. Implementation Constraints

The implementation must follow these constraints:

1. Use only the approved technology stack.
2. Follow RESTful principles for API design.
3. Controllers must not access repositories directly.
4. JPA entities must not be exposed as API request or response models.
5. Use DTOs for external communication.
6. Use Jakarta Validation for input validation.
7. Handle application errors consistently with meaningful responses.
8. Include unit and integration tests using JUnit 5.
9. Provide API documentation using OpenAPI 3.x.
10. Keep the implementation modular, clean, and maintainable.

---

## 5. Database Constraint

H2 must be used as the database for this demo implementation.

Persistence logic should remain isolated so that the database can be replaced in the future without changing the external API contract or business behavior.

---

## 6. Implementation Freedom

The specification intentionally does not prescribe:

- Exact package names
- Exact class names
- Exact method names
- Internal file organization
- Detailed implementation algorithms

The AI coding agent may determine these implementation details while respecting the technical constraints defined in this document.

---

## 7. Constraint Outcome

These constraints define the technical boundaries for implementation without prescribing every internal design decision.

The AI coding agent must generate the application within these boundaries while satisfying the complete specification and acceptance criteria.