
CREATE TABLE IF NOT EXISTS product_listing(
    pl_id INT PRIMARY KEY,
    recipe_id INT,
    chef_id INT,
    active BOOLEAN,
    quantity INT,
    unit_price FLOAT,
    created_at_time TIMESTAMPTZ, /* Timestamp w/TimeZone */
    purchase_deadline TIMESTAMPTZ,
    ready_by TIMESTAMPTZ
);

CREATE SEQUENCE IF NOT EXISTS prod_listing_seq INCREMENT BY 1 START WITH 1;