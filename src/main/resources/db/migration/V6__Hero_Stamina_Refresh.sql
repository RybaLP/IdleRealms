ALTER TABLE hero ADD COLUMN last_energy_update TIMESTAMP WITHOUT TIME ZONE;

UPDATE hero SET last_energy_update = NOW() WHERE last_energy_update IS NULL;