CREATE TABLE dungeon (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    required_level INTEGER DEFAULT 1,
    image_url VARCHAR(255)
);

CREATE TABLE dungeon_floor (
    id SERIAL PRIMARY KEY,
    dungeon_id INTEGER REFERENCES dungeon(id),
    monster_id INTEGER REFERENCES monster(id),
    floor_number INTEGER NOT NULL,
    description TEXT,
    UNIQUE(dungeon_id, floor_number)
);

CREATE TABLE hero_dungeon_progress (
    id SERIAL PRIMARY KEY,
    hero_id INTEGER REFERENCES hero(id),
    dungeon_id INTEGER REFERENCES dungeon(id),
    completed_floors INTEGER DEFAULT 0,
    is_completed BOOLEAN DEFAULT FALSE,
    UNIQUE(hero_id, dungeon_id)
);