create table purchase(
    id INTEGER primary key,
    customer_id INTEGER,
    total_price INTEGER not null default 0,
    timestamp timestamp
);

create sequence purchase_seq increment by 1 start with 1;

create table purchase_event(
    id INTEGER primary key,
    product_list_id INTEGER,
    purchase_id INTEGER,
    quantity INTEGER
);

create sequence purchase_event_seq increment by 1 start with 1;