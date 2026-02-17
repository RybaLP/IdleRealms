INSERT INTO dungeon (name, description, required_level)
VALUES ('Ancient Forest', 'A dark, enchanted woodland where nature has turned against mankind.', 1);

INSERT INTO monster (name, level, monster_class, monster_type, strength, dexterity, intelligence, constitution, luck, gold_reward, exp_reward, image_url, total_armor) VALUES
('Shadow Rat', 10, 'WARRIOR', 'DUNGEON', 25, 15, 10, 30, 10, 150, 600, 'https://res.cloudinary.com/dfo147x05/image/upload/v1771320385/9_biraj4.jpg', 50),
('Cursed Wolf', 11, 'SCOUT', 'DUNGEON', 20, 35, 10, 25, 15, 180, 750, 'https://res.cloudinary.com/dfo147x05/image/upload/v1771320385/3_fxxgmu.jpg', 40),
('Restless Phantom', 12, 'MAGE', 'DUNGEON', 10, 15, 45, 20, 20, 220, 950, 'https://res.cloudinary.com/dfo147x05/image/upload/v1771320386/10_tymvuq.jpg', 20),
('Corrupted Stag', 13, 'WARRIOR', 'DUNGEON', 35, 20, 10, 40, 12, 270, 1200, 'https://res.cloudinary.com/dfo147x05/image/upload/v1771320386/8_b62zde.jpg', 70),
('Goblin Skirmisher', 14, 'WARRIOR', 'DUNGEON', 40, 25, 15, 35, 15, 330, 1500, 'https://res.cloudinary.com/dfo147x05/image/upload/v1771320385/goblin_jadww9.jpg', 60),
('Dread Bat', 15, 'SCOUT', 'DUNGEON', 25, 50, 15, 30, 25, 400, 1900, 'https://res.cloudinary.com/dfo147x05/image/upload/v1771320385/1_z5snuz.jpg', 45),
('Corrupted Elf Ranger', 16, 'SCOUT', 'DUNGEON', 30, 60, 20, 35, 30, 500, 2400, 'https://res.cloudinary.com/dfo147x05/image/upload/v1771320385/4_hpo7ak.jpg', 55),
('Ancient Treant', 17, 'WARRIOR', 'DUNGEON', 60, 20, 20, 80, 10, 650, 3100, 'https://res.cloudinary.com/dfo147x05/image/upload/v1771320385/5_whqtfj.jpg', 120),
('Forgotten Guardian', 18, 'WARRIOR', 'DUNGEON', 70, 35, 20, 70, 20, 850, 4000, 'https://res.cloudinary.com/dfo147x05/image/upload/v1771320385/7_iruvec.jpg', 150),
('Corrupted Archdruid (BOSS)', 20, 'MAGE', 'DUNGEON', 30, 40, 100, 60, 40, 2000, 10000, 'https://res.cloudinary.com/dfo147x05/image/upload/v1771320387/6_uigtln.jpg', 80);

INSERT INTO dungeon_floor (dungeon_id, monster_id, floor_number, description) VALUES
(1, (SELECT id FROM monster WHERE name = 'Shadow Rat'), 1, 'The entrance is choked with thorny vines. A pair of glowing red eyes watches you from the shadows.'),
(1, (SELECT id FROM monster WHERE name = 'Cursed Wolf'), 2, 'The smell of wet fur and decay fills the air. Low growls echo through the twisted trees.'),
(1, (SELECT id FROM monster WHERE name = 'Restless Phantom'), 3, 'The temperature drops suddenly. A pale, flickering light drifts between the blackened trunks.'),
(1, (SELECT id FROM monster WHERE name = 'Corrupted Stag'), 4, 'The ground is trampled and stained. A majestic beast, now twisted by malice, blocks your path.'),
(1, (SELECT id FROM monster WHERE name = 'Goblin Skirmisher'), 5, 'You hear high-pitched cackling. A small, cruel figure leaps from the canopy with a rusty blade.'),
(1, (SELECT id FROM monster WHERE name = 'Dread Bat'), 6, 'The canopy is so thick it is pitch black. A sudden rush of wind suggests something large is swooping down.'),
(1, (SELECT id FROM monster WHERE name = 'Corrupted Elf Ranger'), 7, 'An arrow thuds into a tree inches from your head. A fallen guardian of the woods stalks you.'),
(1, (SELECT id FROM monster WHERE name = 'Ancient Treant'), 8, 'The very forest seems to come alive. A massive tree uproots itself with a deafening groan.'),
(1, (SELECT id FROM monster WHERE name = 'Forgotten Guardian'), 9, 'You reach an ancient stone clearing. A silent sentinel of iron and rock slowly awakens.'),
(1, (SELECT id FROM monster WHERE name = 'Corrupted Archdruid (BOSS)'), 10, 'The heart of the corruption. The Archdruid awaits, surrounded by swirling dark energy. This is it!');