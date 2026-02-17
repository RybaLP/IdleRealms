CREATE TABLE item_template (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    image_url VARCHAR(255),
    type VARCHAR(50),
    hero_class VARCHAR(50)
);

CREATE TABLE quest_template (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255),
    description VARCHAR(500),
    image_url VARCHAR(255)
);

CREATE TABLE hero (
    id BIGSERIAL PRIMARY KEY,
    nickname VARCHAR(255) NOT NULL UNIQUE,
    hero_class VARCHAR(50),
    level INT DEFAULT 1,
    experience BIGINT DEFAULT 0,
    gold INT DEFAULT 1000,

    strength INT DEFAULT 10,
    dexterity INT DEFAULT 10,
    intelligence INT DEFAULT 10,
    constitution INT DEFAULT 10,
    luck INT DEFAULT 10,

    visual_config VARCHAR(255) DEFAULT '0;0;0;0;0',

    bonus_strength INT DEFAULT 0,
    bonus_dexterity INT DEFAULT 0,
    bonus_intelligence INT DEFAULT 0,
    bonus_constitution INT DEFAULT 0,
    bonus_luck INT DEFAULT 0,
    total_armor INT DEFAULT 0,

    energy INT DEFAULT 100,
    quest_finish_time TIMESTAMP,

    weapon_id BIGINT,
    helmet_id BIGINT,
    armor_id BIGINT,
    gloves_id BIGINT,
    boots_id BIGINT
);

CREATE TABLE item (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    image_url VARCHAR(255),
    hero_class VARCHAR(50),
    item_type VARCHAR(50),
    strength_bonus INT DEFAULT 0,
    dexterity_bonus INT DEFAULT 0,
    intelligence_bonus INT DEFAULT 0,
    constitution_bonus INT DEFAULT 0,
    luck_bonus INT DEFAULT 0,
    power INT DEFAULT 0,
    price INT DEFAULT 0,
    required_level INT DEFAULT 0,
    hero_id BIGINT REFERENCES hero(id),
    shop_id BIGINT,
    item_order INT
);

CREATE TABLE account (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    nickname VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50),
    hero_id BIGINT UNIQUE REFERENCES hero(id)
);

CREATE TABLE monster (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    image_url VARCHAR(255),
    level INT,
    strength INT,
    dexterity INT,
    intelligence INT,
    constitution INT,
    luck INT,
    total_armor INT,
    exp_reward INT,
    gold_reward INT,
    monster_class VARCHAR(50),
    monster_type VARCHAR(50),
    weapon_id BIGINT REFERENCES item(id)
);

CREATE TABLE quest (
    id BIGSERIAL PRIMARY KEY,
    hero_id BIGINT REFERENCES hero(id),
    monster_id BIGINT,
    title VARCHAR(255),
    description TEXT,
    image_url VARCHAR(255),
    gold_reward INT,
    exp_reward INT,
    duration_in_seconds INT,
    difficulty DOUBLE PRECISION,
    energy_cost INT
);

CREATE TABLE active_quest (
    id BIGSERIAL PRIMARY KEY,
    hero_id BIGINT UNIQUE REFERENCES hero(id),
    monster_id BIGINT,
    difficulty_multiplier DOUBLE PRECISION,
    gold_reward INT,
    exp_reward INT,
    start_time TIMESTAMP,
    finish_time TIMESTAMP,
    completed BOOLEAN DEFAULT FALSE,
    rewards_claimed BOOLEAN DEFAULT FALSE,
    image_url VARCHAR(255),
    title VARCHAR(255)
);

CREATE TABLE shop (
    id BIGSERIAL PRIMARY KEY,
    hero_id BIGINT UNIQUE REFERENCES hero(id),
    amount_of_items INT DEFAULT 6,
    last_refresh TIMESTAMP
);

ALTER TABLE item ADD CONSTRAINT fk_item_shop FOREIGN KEY (shop_id) REFERENCES shop(id);





