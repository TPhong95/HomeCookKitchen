
CREATE TABLE IF NOT EXISTS recipe(
    r_id INT PRIMARY KEY,
    co_id INT,
    name VARCHAR(255),
    active BOOLEAN
);

CREATE SEQUENCE IF NOT EXISTS recipe_seq INCREMENT BY 1 START WITH 1;

CREATE TABLE IF NOT EXISTS product_listing(
    pl_id INT PRIMARY KEY,
    r_id INT,
    quantity INT,
    unit_price FLOAT
);

CREATE SEQUENCE IF NOT EXISTS prod_listing_seq INCREMENT BY 1 START WITH 1;