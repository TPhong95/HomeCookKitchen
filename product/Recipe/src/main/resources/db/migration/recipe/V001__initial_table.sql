CREATE TABLE IF NOT EXISTS recipe(
    recipe_id INT PRIMARY KEY,
    chef_id INT,
    name VARCHAR(255),
    active BOOLEAN,
    picture_id INT,
    instance_of INT,
    category VARCHAR(50),
    gluten BOOLEAN,
    crustaceans BOOLEAN,
    egg BOOLEAN,
    fish BOOLEAN,
    peanuts BOOLEAN,
    soybeans BOOLEAN,
    milk BOOLEAN,
    nuts BOOLEAN,
    celery BOOLEAN,
    mustard BOOLEAN,
    sesame BOOLEAN,
    sulfites BOOLEAN,
    lupin BOOLEAN,
    molluscs BOOLEAN
);

CREATE SEQUENCE IF NOT EXISTS recipe_seq INCREMENT BY 1 START WITH 1;
