USE test;

ALTER TABLE users ADD last_visit timestamp;
UPDATE users SET last_visit = NOW();

COMMIT;