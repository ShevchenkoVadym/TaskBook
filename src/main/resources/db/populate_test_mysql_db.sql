USE test;

SET FOREIGN_KEY_CHECKS = 0; -- отключить проверку внешних ключей
SET AUTOCOMMIT = 0; -- отключить автокоммит
START TRANSACTION; -- начать транзакцию

--
-- Clear the users table
--
-- DELETE FROM user_role;
DELETE FROM users;
DELETE FROM roles;

ALTER TABLE users AUTO_INCREMENT = 100000;

--
-- users dump for testing
--
INSERT INTO users (username, password, is_enabled, isConfirmed, is_non_read_only, creation_date, email, /*role,*/ country, image_url, rating, tasks_solved, sign_in_provider) VALUES
('ONE',   '$2a$10$zoEDFVz0ANfe6nNPBgmBq.ngG3l09wMhRxNLT0WuBGKsSA/rrAOCe', 1, 1, 1, NOW(), 'ONE@mail.ru',   /*'ADMIN',*/ 'Russian Federation', 'img/user_photo_male.png', 5.55, 24, NULL ),
('TWO',   '$2a$10$nki82pz0NkFre1niB0mKN.hKAXDrXwfeF2Y.NHHfVtq0O7M.LjNba', 1, 1, 1, NOW(), 'TWO@mail.ru',   /*'USER',*/ 'Canada', 'img/user_photo_male.png', 3.11, 20, NULL ),
('THREE', '$2a$10$2rlbkKyv9SErPH8h4zQlA.IxE3R.8n.1ufjY08sxGMB6OZtGM0SUy', 1, 1, 1, NOW(), 'THREE@mail.ru', /*'MODERATOR',*/ 'China', 'img/user_photo_male.png', 2.22, 23, NULL );

-- passwords encoded by BCrypt
-- ONE's password:   12345
-- TWO's password:   123
-- THREE's password: 234



-- INSERT INTO roles (role) VALUES
--   ('ADMIN'),
--   ('USER'),
--   ('MODERATOR');
--
-- INSERT INTO user_role (user_id, role_id) VALUES
--   ((SELECT id FROM users WHERE username = 'ONE'),(SELECT role_id FROM roles WHERE role = 'ADMIN')),
--   ((SELECT id FROM users WHERE username = 'TWO'),(SELECT role_id FROM roles WHERE role = 'USER')),
--   ((SELECT id FROM users WHERE username = 'THREE'),(SELECT role_id FROM roles WHERE role = 'MODERATOR'));



INSERT INTO roles (user_id, role_id) VALUES
  ((SELECT id FROM users WHERE username = 'ONE'),0),
  ((SELECT id FROM users WHERE username = 'TWO'),2),
  ((SELECT id FROM users WHERE username = 'THREE'),1);


SET FOREIGN_KEY_CHECKS=1; -- включить проверку внешних ключей
COMMIT;  -- сделать коммит