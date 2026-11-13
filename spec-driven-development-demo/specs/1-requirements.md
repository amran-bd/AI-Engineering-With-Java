# Customer Management Service — Business Requirements

## 1. Objective

The system should allow customer information to be created, retrieved, updated, and deleted.

---

## 2. Customer Information

Each customer must contain the following information:

- Customer ID
- First Name
- Last Name
- Email
- Phone Number
- Status

---

## 3. Functional Requirements

### FR-01 — Create Customer

The system must allow a new customer to be created.

### FR-02 — Retrieve Customer

The system must allow an existing customer to be retrieved using the customer ID.

### FR-03 — Retrieve All Customers

The system must allow all existing customers to be retrieved.

### FR-04 — Update Customer

The system must allow an existing customer's information to be updated.

### FR-05 — Delete Customer

The system must allow an existing customer to be deleted.

---

## 4. Business Rules

### BR-01 — Unique Email

Each customer must have a unique email address.

### BR-02 — Required Customer Information

Required customer information must be provided when creating or updating a customer.

### BR-03 — Customer Status

A customer can have one of the following statuses:

- ACTIVE
- INACTIVE

### BR-04 — Non-Existing Customer

Operations involving a customer that does not exist must be handled appropriately.

---

## 5. Scope

This version of the Customer Management Service covers:

- Customer creation
- Customer retrieval
- Customer listing
- Customer updates
- Customer deletion
- Basic customer information validation
- Customer status management

---

## 6. Out of Scope

The following capabilities are outside the scope of this version:

- Authentication and authorization
- Audit history
- External system integrations
