CREATE TABLE IF NOT EXISTS customer (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255)
);

CREATE SEQUENCE customer_seq INCREMENT BY 1 START WITH 1;

create  table if not exists customer_notification(
    id BIGINT primary key,
    customer_id BIGINT,
    message VARCHAR(100),
    time TIMESTAMPTZ,
    is_read BOOLEAN
);
create sequence customer_notification_seq increment by 1 start with 1;
