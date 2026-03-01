ALTER TABLE hero ADD COLUMN social_id UUID;

UPDATE hero SET social_id = gen_random_uuid() WHERE social_id IS NULL;

ALTER TABLE hero ALTER COLUMN social_id SET NOT NULL;
ALTER TABLE hero ADD CONSTRAINT uk_hero_social_id UNIQUE (social_id);