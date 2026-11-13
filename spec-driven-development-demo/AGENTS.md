# AI Coding Agent Instructions

# Agent Instructions

## Read before coding
Read and understand, in this order:
1. `business/1-business-requirements.md`
2. `architecture/1-system-context.md`
3. `architecture/2-domain-model.md`
4. `architecture/3-technical-principles.md`

## Engineering rules
- Implement only the current approved increment.
- Do not implement future capabilities merely because they appear in the product vision.
- Keep domain/capability boundaries explicit.
- Do not expose JPA entities directly through REST.
- Use separate API DTOs and persistence models with explicit mapping.
- Keep controllers thin; business behavior belongs in the service layer.
- Use consistent structured error responses.
- IDs and audit timestamps are server controlled.
- Add automated tests for required behavior.
- Run the complete test suite before declaring the increment complete.
- Never change specifications merely to make implementation or tests pass.
- If specifications conflict or are ambiguous, report the issue rather than inventing business behavior.
---

## 2. Read the Specification First

Before implementation:

1. Read and understand every file under `/specs`.
2. Treat `specs/5-openapi.yaml` as the authoritative REST API contract.
3. Treat `specs/3-acceptance-criteria.md` as the required verification criteria.
4. Follow the technical boundaries defined in `specs/4-technical-constraints.md`.
5. Ensure the implementation remains consistent with `specs/1-requirements.md` and `specs/2-system-specification.md`.

Do not begin implementation until the specification has been reviewed.

---

## 3. Implementation Workflow

## Working lifecycle
Read → Understand → Plan → Implement → Build → Test → Fix → Verify.

Before modifying the application:

1. Inspect the repository and all specification files.
2. Create an implementation plan.
3. Map the planned implementation to the requirements and acceptance criteria.
4. Implement the smallest coherent solution that satisfies the specification.
5. Generate automated tests covering the acceptance criteria.
6. Run the complete test suite.
7. Fix implementation failures.
8. Re-run the tests until the implementation satisfies the specification.

---

## 4. API Contract

The REST API must follow `specs/5-openapi.yaml`.

Do not independently change:

- API paths
- HTTP methods
- Request models
- Response models
- Field names
- HTTP status codes
- Defined error responses

If the implementation differs from the OpenAPI contract, change the implementation.

---

## 5. Technical Constraints

Follow all constraints defined in `specs/4-technical-constraints.md`.

In particular:

- Use the approved technology stack.
- Follow the defined layered architecture.
- Do not place business logic in controllers.
- Controllers must not access repositories directly.
- Do not expose JPA entities as external API models.
- Use DTOs for API requests and responses.
- Use Jakarta Validation for input validation.
- Generate unit and integration tests using JUnit 5.
- Keep the implementation modular and maintainable.

---

## 6. Acceptance Criteria

All acceptance criteria in `specs/3-acceptance-criteria.md` are mandatory.

Automated tests should provide evidence that the implementation satisfies these criteria.

A successful build alone does not mean the specification has been satisfied.

---

## 7. Specification Protection

Do not modify specification files merely to make the implementation or tests pass.

The files under `/specs` represent human-defined intent and constraints.

If the implementation conflicts with the specification:

**Change the implementation — not the specification.**

---

## 8. Conflict Handling

If specification documents conflict with each other:

1. Stop implementation of the conflicting behavior.
2. Clearly identify the conflicting specification statements.
3. Report the conflict.
4. Do not invent or assume a resolution.

Implementation may continue only after the conflict has been resolved in the specification.

---

## 9. Scope Control

Do not introduce functionality that is not required by the specification.

Avoid unnecessary frameworks, abstractions, integrations, or features.

Implementation decisions not explicitly constrained by the specification may be made by the coding agent, provided they remain consistent with the requirements and technical constraints.

---

## 10. Completion Criteria

Implementation is complete only when:

- The application builds successfully.
- The REST API matches the OpenAPI contract.
- All acceptance criteria are satisfied.
- Automated tests pass.
- Technical constraints are respected.
- No specification files were changed to accommodate implementation behavior.