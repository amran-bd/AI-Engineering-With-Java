# Change Request Workflow

Use this prompt when an existing requirement changes after the
customer-service has already been generated.

## Instructions

1.  Read `AGENTS.md`.
2.  Read all files under `/specs`.
3.  Inspect the existing implementation under `/customer-service`.
4.  Identify the specification files affected by the requested business
    change.
5.  Update the specification first.
6.  Perform an impact analysis before modifying application code.
7.  Present the implementation plan for review.
8.  After approval, update the customer-service.
9.  Update or add automated tests for the changed behavior.
10. Run the complete test suite and fix implementation failures.

## Rules

-   Do not silently change unrelated requirements.
-   Do not change the API contract unless the requirement requires an
    API change.
-   Preserve existing behavior that is not affected by the change.
-   Do not modify specifications merely to make existing code pass.
-   The updated specification remains the source of truth.