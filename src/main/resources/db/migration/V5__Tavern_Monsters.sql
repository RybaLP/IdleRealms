INSERT INTO monster (
    name, monster_type, monster_class, level, strength,
    dexterity, intelligence, constitution, luck, total_armor,
    exp_reward, gold_reward, image_url
)
VALUES
(
    'Werewolf Shaman', 'TAVERN', 'MAGE', 1, 5,
    10, 20, 12, 8, 2,
    0, 0, 'https://res.cloudinary.com/dfo147x05/image/upload/v1771536027/warwick_v3hhdt.jpg'
),
(
    'Cursed Knight', 'TAVERN', 'WARRIOR', 1, 20,
    8, 5, 15, 5, 10,
    0, 0, 'https://res.cloudinary.com/dfo147x05/image/upload/v1771536027/knight_hximyj.jpg'
),
(
    'Swamp Drowner', 'TAVERN', 'SCOUT', 1, 8,
    20, 4, 10, 12, 1,
    0, 0, 'https://res.cloudinary.com/dfo147x05/image/upload/v1771536027/utopiec_f6dbut.jpg'
),
(
    'Highway Bandit', 'TAVERN', 'WARRIOR', 1, 18,
    12, 2, 12, 6, 5,
    0, 0, 'https://res.cloudinary.com/dfo147x05/image/upload/v1771536027/bandit_oanvct.jpg'
);