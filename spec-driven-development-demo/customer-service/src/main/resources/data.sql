INSERT INTO customers (
    cust_id,
    first_name,
    last_name,
    email,
    email_normalized,
    phone_number,
    status,
    created_at,
    updated_at
) VALUES (
    '11111111-1111-1111-1111-111111111111',
    'Sample',
    'Customer',
    'sample@example.com',
    'sample@example.com',
    '5550100',
    'ACTIVE',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);
