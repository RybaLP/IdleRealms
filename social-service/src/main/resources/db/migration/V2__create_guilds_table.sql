CREATE TABLE guilds (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    owner_id UUID NOT NULL,
    total_gold INT DEFAULT 0,
    coach_level INT DEFAULT 0
);

CREATE TABLE guild_members (
    guild_id UUID NOT NULL,
    player_social_id UUID NOT NULL,
    CONSTRAINT fk_guild FOREIGN KEY (guild_id) REFERENCES guilds (id) ON DELETE CASCADE
);