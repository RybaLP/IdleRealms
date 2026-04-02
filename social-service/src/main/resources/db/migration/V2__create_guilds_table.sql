CREATE TABLE guilds (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    owner_id UUID,
    gold_vault BIGINT DEFAULT 0,
    instructor_lvl INT DEFAULT 1
);