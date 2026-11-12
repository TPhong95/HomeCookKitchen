
CREATE TABLE IF NOT EXISTS product_listing(
    pl_id BIGINT PRIMARY KEY,
    recipe_id BIGINT,
    recipe_name VARCHAR(255),
    recipe_url VARCHAR(255),
    recipe_description VARCHAR(512),
    address_id BIGINT,
    composite_address VARCHAR(255),
    chef_id BIGINT,
    chef_name VARCHAR(255),
    active BOOLEAN,
    quantity_offered INT NOT NULL DEFAULT 0,
    quantity_purchased INT NOT NULL DEFAULT 0,
    unit_price FLOAT,
    created_at_time TIMESTAMPTZ, /* Timestamp w/TimeZone */
    purchase_deadline TIMESTAMPTZ,
    ready_by TIMESTAMPTZ
);

CREATE SEQUENCE IF NOT EXISTS prod_listing_seq INCREMENT BY 1 START WITH 1;