# Customer Management Service Implementation Plan

## Scope and requirements mapping

| Work | Contract / acceptance criteria |
|---|---|
| Spring Boot 4, Java 25, Maven app under `com.sdd.demo`; layered `controller`, `service` interface/implementation, `repository`, `entity`, `exception`, and `model` packages | Technical constraints; requested package layout and service abstraction |
| Customer JPA entity, H2 persistence, UUID identity, status enum, and server-managed create/update timestamps | System specification sections 2 and 9; AC-01, AC-09 |
| Record request/response DTOs, explicit MapStruct mapping, Jakarta validation | OpenAPI schemas; technical principles and constraints |
| CRUD controller rooted at `/api/v1/customers`, thin delegation, entry logging; Springdoc docs | OpenAPI paths and methods; user request for Swagger |
| Service rules for default ACTIVE status, case-insensitive email uniqueness, self-email update, missing customer handling, timestamps | System specification sections 3–8; AC-01–AC-17 |
| Structured error DTO and exception handling with contract codes/statuses | OpenAPI `ErrorResponse`; AC-02–04, AC-06, AC-11–12, AC-14–15, AC-17 |
| JUnit 5 service and API integration coverage | All acceptance criteria AC-01–AC-17 |
| Dockerfile and Docker Compose app startup; Kubernetes-style `application.yml` | Explicit user request; technical principles |
| GitHub Actions verification workflow running format checks and Maven verify | Technical principles CI/CD requirement |

## Implementation sequence

1. Create the Maven/Spring Boot project skeleton and runtime configuration.
2. Implement entity, repository, DTOs, mapper, service, controller, and exception handling.
3. Add focused unit and integration tests for all acceptance behavior.
4. Add container build and Compose configuration plus Springdoc configuration.
5. Format Java and `pom.xml`, run the complete Maven test suite, inspect API/configuration consistency, and confirm specification files are unchanged.

## Contract decisions

- The current Customer Management specification and OpenAPI contract define phone number as required and customer status as uppercase `ACTIVE`/`INACTIVE`; these are used for this increment.
- API field `id` maps explicitly to persisted `custId`.
- Invalid status values are rejected as `INVALID_CUSTOMER_STATUS` with HTTP 400; other request validation errors use `VALIDATION_ERROR`.
- Email uniqueness is enforced case-insensitively in service checks and by a normalized persistence key to protect against concurrent requests.
