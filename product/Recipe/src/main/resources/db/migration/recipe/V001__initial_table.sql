CREATE TABLE IF NOT EXISTS recipe(
    recipe_id BIGINT PRIMARY KEY,
    chef_id BIGINT,
    name VARCHAR(255),
    invalidated BOOLEAN NOT NULL DEFAULT FALSE,
    active BOOLEAN NOT NULL DEFAULT FALSE,
    picture_url VARCHAR(255),
    description VARCHAR(255),
    instance_of BIGINT,
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
