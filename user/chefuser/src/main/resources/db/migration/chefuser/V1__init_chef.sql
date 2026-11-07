create table if not exists chef(
    id BIGINT primary key,
    name VARCHAR(255),
    email VARCHAR(255),
    phone_number VARCHAR(255),
    active BOOLEAN DEFAULT TRUE
);
create sequence chef_seq increment by 1 start with 1;

create table if not exists address(
    id BIGINT primary key,
    chef_id BIGINT,
    street VARCHAR(255),
    postal_code CHAR(4),
    city VARCHAR(255),
    active BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_address_chef FOREIGN KEY (chef_id) REFERENCES chef(id) ON DELETE CASCADE
);
create sequence address_seq increment by 1 start with 1;

create table if not exists home_vibe(
    id BIGINT primary key,
    address_id BIGINT NOT NULL,
    description VARCHAR(500),
    music VARCHAR(50),
    pets BOOLEAN DEFAULT FALSE,
    outdoor BOOLEAN DEFAULT FALSE,
    family_friendly BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_home_vibe_address FOREIGN KEY (address_id) REFERENCES address(id) ON DELETE CASCADE,
    CONSTRAINT uq_home_vibe_address UNIQUE (address_id)
    );
create sequence home_vibe_seq increment by 1 start with 1;