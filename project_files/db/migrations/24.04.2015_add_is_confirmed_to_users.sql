USE test;

ALTER TABLE users ADD is_confirmed bit(1) NOT NULL;

COMMIT;