create table purchase(
    id BIGINT primary key,
    customer_id BIGINT,
    total_price BIGINT not null default 0,
    timestamp timestamp
);

create sequence purchase_seq increment by 1 start with 1;

create table purchase_event(
    id BIGINT primary key,
    product_list_id BIGINT,
    purchase_id BIGINT,
    quantity BIGINT,
    cook_id BIGINT,
    chef_name VARCHAR(255),
    recipe_listing_id BIGINT,
    recipe_name VARCHAR(255),
    unit_price REAL
);

create sequence purchase_event_seq increment by 1 start with 1;

