
CREATE TABLE IF NOT EXISTS product_listing(
    pl_id BIGINT PRIMARY KEY,
    recipe_id BIGINT,
    chef_id BIGINT,
    active BOOLEAN,
    quantity INT,
    unit_price FLOAT,
    created_at_time TIMESTAMPTZ, /* Timestamp w/TimeZone */
    purchase_deadline TIMESTAMPTZ,
    ready_by TIMESTAMPTZ
);

CREATE SEQUENCE IF NOT EXISTS prod_listing_seq INCREMENT BY 1 START WITH 1;