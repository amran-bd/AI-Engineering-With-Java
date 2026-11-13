## Customer Management Service

This demo `customer-service` project used in the Spec-Driven Development
walkthrough.

I begin with **specifications, agent instructions, and prompts --- not
application code**.

## Project Structure
``` text
spec-driven-development-demo/
├── README.md
├── AGENTS.md
├── architecture/
│   ├── 1-system-context.md
│   ├── 2-domain-model.md
│   └── 3-technical-principles.md
├── business/
│   ├── 1-system-context.md
│   ├── 2-domain-model.md
│   └── 3-technical-principles.md
├── prompts/
│   ├── implement.md
│   ├── verify.md
│   └── change-request.md
├── specs/
│   ├── 1-requirements.md
│   ├── 2-system-specification.md
│   ├── 3-acceptance-criteria.md
│   ├── 4-technical-constraints.md
│   └── 5-openapi.yaml
└── customer-service/
    └── .gitkeep
```

## Folder and File Purpose
-   `AGENTS.md` tells the AI coding agent how to work with those
    specifications.
-   `customer-service/` is intentionally empty at the start. The AI coding
    agent generates the Spring Boot application here.

## This Workflow
> The `.gitkeep` file exists only so Git can preserve the initially
> empty `customer-service/` directory.

## Running the Project with Codex or else
Run all commands from the root of the repository.

### 1. Open the spec-driven-development-demo Directory
```bash
cd spec-driven-development-demo
```

### 2. Start Codex or else
If Codex is not installed globally:

```bash
npx @openai/codex
```

If Codex is already installed:
```bash
codex
```
---

## Implement from the Specification
The implementation prompt is stored in:
```text
prompts/implement.md
```
Run:
```bash
npx @openai/codex "$(cat prompts/implement.md)"
```

Or, if Codex is installed globally:
```bash
codex "$(cat prompts/implement.md)"
```

Codex should:
1. Read `AGENTS.md`.
2. Read all specification files under `specs/`.
3. Create an implementation plan.
4. Generate the Spring Boot application inside `customer-service/`.
5. Generate automated tests.
6. Run the test suite.
7. Fix implementation issues without modifying the specifications.

---

## Verify the Generated Application
After implementation, run the verification prompt:

```bash
npx @openai/codex "$(cat prompts/verify.md)"
```

Or:

```bash
codex "$(cat prompts/verify.md)"
```

The verification step checks the implementation against:
- Business requirements
- System specification
- Acceptance criteria
- Technical constraints
- OpenAPI contract
- Automated tests

---
## Handle a Requirement Change
When an existing business requirement changes, use:

```bash
npx @openai/codex "$(cat prompts/change-request.md)"
```

Or:

```bash
codex "$(cat prompts/change-request.md)"
```

The expected workflow is:
```text
Requirement Change
        ↓
Update Specification
        ↓
Impact Analysis
        ↓
Review Plan
        ↓
Update Implementation
        ↓
Update Tests
        ↓
Run Complete Test Suite
        ↓
Verify Against Specification
```

---
## Useful Codex Commands
Start Codex interactively:

```bash
npx @openai/codex
```

Start Codex using the implementation instructions:
```bash
npx @openai/codex "$(cat prompts/implement.md)"
```

Verify the implementation:
```bash
npx @openai/codex "$(cat prompts/verify.md)"
```

Process a requirement change:
```bash
npx @openai/codex "$(cat prompts/change-request.md)"
```

---
## SDD Workflow
```text
Business Requirements
        ↓
System Specification
        ↓
Acceptance Criteria
        ↓
Technical Constraints
        ↓
OpenAPI Contract
        ↓
AGENTS.md
        ↓
Implementation Prompt
        ↓
Codex
        ↓
Implementation Plan
        ↓
Generate /customer-service
        ↓
Automated Tests
        ↓
Verification
```

> The specifications are the source of truth. If the implementation conflicts with the specification, fix the implementation rather than changing the specification simply to make the code pass.