create table if not exists chef(
    id BIGINT primary key,
    name VARCHAR(255),
    email VARCHAR(255),
    phone_number VARCHAR(255)
);
create sequence chef_seq increment by 1 start with 1;

create table if not exists address(
    id BIGINT primary key,
    street VARCHAR(255),
    city VARCHAR(255),
    country VARCHAR(255),
    chef_id BIGINT,
    CONSTRAINT fk_address_chef FOREIGN KEY (chef_id) REFERENCES chef(id) ON DELETE CASCADE
);
create sequence address_seq increment by 1 start with 1;