# Customer Management Service — Acceptance Criteria

## 1. Purpose

This document defines the acceptance criteria for the Customer Management Service.

The criteria are derived from the business requirements and system specification and provide objectively verifiable outcomes for the expected system behavior.

---

## 2. Create Customer

### AC-01 — Create Customer Successfully

**Given** valid customer information containing first name, last name, email, and phone number  
**When** a new customer is created  
**Then** the customer must be created successfully  
**And** a unique customer ID must be generated  
**And** the customer status must default to `ACTIVE`  
**And** Created At and Updated At must be generated.

### AC-02 — Reject Invalid Required Customer Information

**Given** customer information with a required value missing or blank  
**When** a new customer is created  
**Then** the operation must be rejected.

### AC-03 — Reject Invalid Email Format

**Given** customer information containing an invalid email format  
**When** a new customer is created  
**Then** the operation must be rejected.

### AC-04 — Reject Duplicate Email

**Given** an existing customer with email `customer@example.com`  
**When** another customer is created with email `Customer@Example.com`  
**Then** the operation must be rejected.

This verifies that email uniqueness is case-insensitive.

---

## 3. Retrieve Customer

### AC-05 — Retrieve Existing Customer

**Given** an existing customer  
**When** the customer is retrieved using their customer ID  
**Then** the customer's information must be returned.

### AC-06 — Retrieve Non-Existing Customer

**Given** a customer ID that does not exist  
**When** the customer is retrieved using that ID  
**Then** the system must produce a not-found outcome.

---

## 4. Retrieve All Customers

### AC-07 — Retrieve All Existing Customers

**Given** multiple customers exist  
**When** all customers are retrieved  
**Then** all existing customers must be returned.

### AC-08 — Retrieve Customers When No Customers Exist

**Given** no customers exist  
**When** all customers are retrieved  
**Then** an empty collection must be returned.

---

## 5. Update Customer

### AC-09 — Update Existing Customer Successfully

**Given** an existing customer  
**When** valid mutable customer information is updated  
**Then** the customer information must be updated successfully  
**And** the customer ID must remain unchanged  
**And** Created At must remain unchanged  
**And** Updated At must be refreshed.

### AC-10 — Retain Own Email During Update

**Given** an existing customer with email `customer@example.com`  
**When** the customer is updated while retaining the same email address  
**Then** the update must succeed.

### AC-11 — Reject Another Customer's Email During Update

**Given** two existing customers with different email addresses  
**When** one customer is updated using the email address belonging to the other customer  
**Then** the operation must be rejected.

### AC-12 — Reject Invalid Customer Data During Update

**Given** an existing customer  
**When** the customer is updated with missing, blank, or otherwise invalid required customer information  
**Then** the operation must be rejected.

### AC-13 — Update Customer Status

**Given** an existing customer with status `ACTIVE`  
**When** the customer status is updated to `INACTIVE`  
**Then** the update must succeed  
**And** the customer status must become `INACTIVE`.

### AC-14 — Reject Invalid Customer Status

**Given** an existing customer  
**When** the customer is updated with a status other than `ACTIVE` or `INACTIVE`  
**Then** the operation must be rejected.

### AC-15 — Update Non-Existing Customer

**Given** a customer ID that does not exist  
**When** an update is attempted for that customer  
**Then** the system must produce a not-found outcome.

---

## 6. Delete Customer

### AC-16 — Delete Existing Customer

**Given** an existing customer  
**When** the customer is deleted  
**Then** the customer must be deleted successfully.

### AC-17 — Delete Non-Existing Customer

**Given** a customer ID that does not exist  
**When** deletion is attempted for that customer  
**Then** the system must produce a not-found outcome.

---

## 7. Acceptance Criteria Outcome

These acceptance criteria provide verifiable conditions for the Customer Management Service.

They will later be used to guide automated test generation and to verify that the AI-generated implementation satisfies the specification.