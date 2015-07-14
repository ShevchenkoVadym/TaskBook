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
DELETE FROM user_tasks;

ALTER TABLE users AUTO_INCREMENT = 100000;

--
-- users dump for preview
--
INSERT INTO users (username, password, is_enabled, is_confirmed, is_non_read_only, creation_date, email, /*role,*/ country, image_url, rating, tasks_solved, sign_in_provider, token_confirmation) VALUES
('alex',   '$2a$10$D2PalsJX68JeMPh0DVfnneugKjHGJMTLRIenDWjI5WsnJqman3w8O', 1, 1, 0, NOW(), 'letitbe400@gmail.com',   /*'ADMIN',*/ 'Russian Federation', '', 100, 0, NULL, NULL ),
('Brynne', '$2a$10$hwoWc2RZtgUUihDrFSvffu6mG.Gaw78UR7vnP.ZQRlGg6DVdbY2RO', 1, 1, 1, NOW(), 'sashok@mail.ru', /*'ADMIN',*/ 'Russian Federation', '', FLOOR(RAND() * 10) + 10, 0, NULL, NULL ),
('Vivian',  '$2a$10$C2b3XAQHI6UwB.bEMIvGT.O7zP7L1kzf8OuSktBEEX4EAdP1M3f2W', 0, 1, 0, NOW(), 'beast@mail.ru',  /*'USER',*/  'United States of America(USA)', '', FLOOR(RAND() * 10) + 10, 0, NULL, NULL ),
('mary',   '$2a$10$PYaAiEwiMLVIQKmns0wrPOyFM/rrwWUTeMFuD/MJBSDv8qlRD4AWK', 1, 1, 0, NOW(), 'mary@mail.ru',   /*'MODERATOR',*/ 'Germany', '', FLOOR(RAND() * 10) + 10, 0, NULL, NULL ),
('Christopher',  '$2a$10$G0fwKDq8xe913b8aitEKC.Ykeu/RizvhGMccT1QKxLL7RHJxlCMbu', 1, 1, 0, NOW(), 'christopher@mail.ru',  /*'ADMIN',*/ 'France', '', FLOOR(RAND() * 10) + 10, 0, NULL, NULL ),
('Thor',  '$2a$10$G0fwKDq8xe913b8aitEKC.Ykeu/RizvhGMccT1QKxLL7RHJxlCMbu', 1, 1, 0, NOW(), 'quam@at.edu',  /*'USER',*/ 'Portugal', '', FLOOR(RAND() * 10) + 10,0, NULL, NULL ),
('Jasmine',  '$2a$10$G0fwKDq8xe913b8aitEKC.Ykeu/RizvhGMccT1QKxLL7RHJxlCMbu', 1, 1, 0, NOW(), 'natoque.penatibus@cursusdiam.edu',  /*'USER',*/ 'Tanzania', '', FLOOR(RAND() * 10) + 10, 0, NULL, NULL ),
('Olympia',  '$2a$10$G0fwKDq8xe913b8aitEKC.Ykeu/RizvhGMccT1QKxLL7RHJxlCMbu', 1, 1, 0, NOW(), 'a@elit.org',  /*'USER',*/ 'Indonesia', '', FLOOR(RAND() * 10) + 10, 0, NULL, NULL ),
('Cecilia',  '$2a$10$G0fwKDq8xe913b8aitEKC.Ykeu/RizvhGMccT1QKxLL7RHJxlCMbu', 1, 1, 0, NOW(), 'diam@vehiculaaliquetlibero.co.uk',  /*'USER',*/ 'Taiwan', '', FLOOR(RAND() * 10) + 10, 0, NULL, NULL ),
('Dillon',  '$2a$10$G0fwKDq8xe913b8aitEKC.Ykeu/RizvhGMccT1QKxLL7RHJxlCMbu', 1, 1, 0, NOW(), 'gravida.Praesent@aliquetProin.edu',  /*'USER',*/ 'Monaco', '', FLOOR(RAND() * 10) + 10, 0, NULL, NULL ),
('Merritt',  '$2a$10$G0fwKDq8xe913b8aitEKC.Ykeu/RizvhGMccT1QKxLL7RHJxlCMbu', 1, 1, 0, NOW(), 'In.scelerisque@sitametornare.com',  /*'USER',*/ 'Armenia', '', FLOOR(RAND() * 10) + 10, 0, NULL, NULL ),
('Duncan',  '$2a$10$G0fwKDq8xe913b8aitEKC.Ykeu/RizvhGMccT1QKxLL7RHJxlCMbu', 1, 1, 0, NOW(), 'Donec.feugiat@dui.net',  /*'USER',*/ 'China', '', FLOOR(RAND() * 10) + 10, 0, NULL, NULL ),
('Roth',  '$2a$10$G0fwKDq8xe913b8aitEKC.Ykeu/RizvhGMccT1QKxLL7RHJxlCMbu', 1, 1, 0, NOW(), 'turpis@cubilia.com',  /*'USER',*/ 'Burundi', '', FLOOR(RAND() * 10) + 10, 0, NULL, NULL ),
('Benjamin',  '$2a$10$G0fwKDq8xe913b8aitEKC.Ykeu/RizvhGMccT1QKxLL7RHJxlCMbu', 1, 1, 0, NOW(), 'nascetur.ridiculus.mus@necleoMorbi.org',  /*'USER',*/ 'Romania', '', FLOOR(RAND() * 10) + 10, 0, NULL, NULL ),
('Rooney',  '$2a$10$G0fwKDq8xe913b8aitEKC.Ykeu/RizvhGMccT1QKxLL7RHJxlCMbu', 1, 1, 0, NOW(), 'porta@arcuimperdiet.co.uk',  /*'USER',*/ 'Greenland', '', FLOOR(RAND() * 10) + 10, 0, NULL, NULL ),
('Davis',  '$2a$10$G0fwKDq8xe913b8aitEKC.Ykeu/RizvhGMccT1QKxLL7RHJxlCMbu', 1, 1, 0, NOW(), 'In.nec.orci@ac.net',  /*'USER',*/ 'Palau', '', FLOOR(RAND() * 10) + 10, 0, NULL, NULL ),
('Macon',  '$2a$10$G0fwKDq8xe913b8aitEKC.Ykeu/RizvhGMccT1QKxLL7RHJxlCMbu', 1, 1, 0, NOW(), 'quis@estNunc.edu',  /*'USER',*/ 'Spain', '', FLOOR(RAND() * 10) + 10, 0, NULL, NULL ),
('Timothy',  '$2a$10$G0fwKDq8xe913b8aitEKC.Ykeu/RizvhGMccT1QKxLL7RHJxlCMbu', 1, 1, 0, NOW(), 'eu.nulla.at@Nunc.org',  /*'USER',*/ 'Panama', '', FLOOR(RAND() * 10) + 10, 0, NULL, NULL ),
('Cedric',  '$2a$10$G0fwKDq8xe913b8aitEKC.Ykeu/RizvhGMccT1QKxLL7RHJxlCMbu', 1, 1, 0, NOW(), 'nunc@accumsansed.edu',  /*'USER',*/ 'Norway', '', FLOOR(RAND() * 10) + 10, 0, NULL, NULL );


-- passwords encoded by BCrypt
-- alex's password: 12345
-- sashok's password: 123
-- beast's password: 234
-- mary's password: 456
-- vitek's password: 2345

-- INSERT INTO roles (role) VALUES
--   ('ADMIN'),
--   ('USER'),
--   ('MODERATOR');
-- 
-- INSERT INTO user_role (user_id, role_id) VALUES
--   ((SELECT id FROM users WHERE username = 'alex'),(SELECT role_id FROM roles WHERE role = 'ADMIN')),
--   ((SELECT id FROM users WHERE username = 'sashok'),(SELECT role_id FROM roles WHERE role = 'ADMIN')),
--   ((SELECT id FROM users WHERE username = 'beast'),(SELECT role_id FROM roles WHERE role = 'USER')),
--   ((SELECT id FROM users WHERE username = 'mary'),(SELECT role_id FROM roles WHERE role = 'MODERATOR')),
--   ((SELECT id FROM users WHERE username = 'vitek'),(SELECT role_id FROM roles WHERE role = 'ADMIN'));

INSERT INTO roles (user_id, role_id) VALUES
  ((SELECT id FROM users WHERE username = 'alex'), 0),
  ((SELECT id FROM users WHERE username = 'Brynne'), 0),
  ((SELECT id FROM users WHERE username = 'Vivian'), 2),
  ((SELECT id FROM users WHERE username = 'mary'), 1),
  ((SELECT id FROM users WHERE username = 'Christopher'), 0);


SET FOREIGN_KEY_CHECKS=1; -- включить проверку внешних ключей
COMMIT;  -- сделать коммит