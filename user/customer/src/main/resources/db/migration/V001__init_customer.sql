CREATE TABLE IF NOT EXISTS customer (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255) /* Assuming only one email */
);

CREATE SEQUENCE customer_seq INCREMENT BY 1 START WITH 1;
