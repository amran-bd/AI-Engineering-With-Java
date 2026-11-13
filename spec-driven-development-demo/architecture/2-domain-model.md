# Core Domain Model

## Customer
Customer represents onboarding in the system which is one-to-one relationship with user table.

Initial attributes:
- `custId` — UUID
- `firstName`
- `lastName`
- `phoneNumber` - default null
- `dob` - date
- `occupation` - default null
- `email` - unique
- `status` - default Active
- `createdAt` - timestamp
- `updatedAt` - timestamp

## User
User represents the platform identity which is store username, email as unique.

Initial attributes:
- `userId` — UUID
- `custId` — UUID
- `username` - unique
- `status` - default Active
- `createdAt` - timestamp
- `updatedAt` - timestamp

## Role
Role represents as lookup table.

Initial attributes:
- `roleId` - UUID
- `roleName` - unique
- `description`
- `status` - default Active
- `createdAt` - timestamp
- `updatedAt` - timestamp

## Role and User Map
This mapper table will be use for user has multiple role purpose.

Initial attributes:
- `roleId` - UUID
- `userId` — UUID
- `createdAt` - timestamp
- `updatedAt` - timestamp