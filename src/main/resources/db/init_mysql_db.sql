CREATE DATABASE IF NOT EXISTS test DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

-- GRANT ALL privileges ON test.* TO jr_taskbook@localhost;

USE test;

-- http://dev.mysql.com/doc/refman/5.7/en/commit.html
-- http://dev.mysql.com/doc/refman/5.7/en/set-statement.html
-- http://dev.mysql.com/doc/refman/5.7/en/storage-engines.html
-- http://dev.mysql.com/doc/refman/5.7/en/glossary.html#glos_acid
-- http://dev.mysql.com/doc/refman/5.7/en/innodb-storage-engine.html

SET FOREIGN_KEY_CHECKS = 0; -- отключить проверку внешних ключей
SET AUTOCOMMIT = 0; -- отключить автокоммит
START TRANSACTION; -- начать транзакцию


DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS UserConnection;
DROP TABLE IF EXISTS user_tasks;


CREATE TABLE users (
  id int(10) NOT NULL AUTO_INCREMENT,
  username varchar(100) NOT NULL,
  full_name varchar(100) NULL,
  password varchar(150) NOT NULL,
  is_enabled bit(1) NOT NULL,
  is_confirmed bit(1) NOT NULL,
  is_non_read_only bit(1) NOT NULL,
  creation_date timestamp NOT NULL,
  email varchar(50) NOT NULL,
  country varchar(100) NOT NULL,
  image_url varchar(255) NULL,
  rating DOUBLE DEFAULT 0,
  tasks_solved int(10) DEFAULT 0,
  sign_in_provider varchar(50),
  token_confirmation varchar(255) NULL,
  last_visit timestamp NULL,
  PRIMARY KEY (id),
  UNIQUE KEY username (username),
  UNIQUE KEY email(email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table UserConnection (userId varchar(255) not null,
                             providerId varchar(255) not null,
                             providerUserId varchar(255),
                             rank int not null,
                             displayName varchar(255),
                             profileUrl varchar(512),
                             imageUrl varchar(512),
                             accessToken varchar(255) not null,
                             secret varchar(255),
                             refreshToken varchar(255),
                             expireTime bigint,
  primary key (userId, providerId, providerUserId));
create unique index UserConnectionRank on UserConnection(userId, providerId, rank);


# CREATE TABLE IF NOT EXISTS roles (
#   role_id int(10) NOT NULL AUTO_INCREMENT,
#   role varchar(50) NOT NULL,
#   PRIMARY KEY (role_id)
# ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
#
# CREATE TABLE user_role (
#   user_id int(6) NOT NULL,
#   role_id int(6) NOT NULL,
#   KEY USER (user_id),
#   KEY ROLE (role_id),
#   CONSTRAINT USER FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
#   CONSTRAINT ROLE FOREIGN KEY (role_id) REFERENCES roles (role_id) ON DELETE CASCADE ON UPDATE CASCADE
# ) COLLATE='utf8_general_ci' ENGINE=InnoDB;


CREATE TABLE roles (
  user_id int(10) NOT NULL,
  role_id int(10) NOT NULL, -- THE ORDINAL NUMBER OF ENUM VALUE
  PRIMARY KEY (user_id, role_id),
  KEY USER (user_id),
  CONSTRAINT USER FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;;

CREATE TABLE IF NOT EXISTS user_tasks (
  id int(10) NOT NULL AUTO_INCREMENT,
  user_id int(10) NOT NULL,
  task_id varchar(100) NOT NULL,
  tryies_count int(10) DEFAULT 0,
  date_solve TIMESTAMP NULL,
  status bit(1) DEFAULT 0,
  PRIMARY KEY (id),
  KEY user_id (user_id),
  KEY task_id (task_id),
  CONSTRAINT tasks_ibfk1 FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;;



SET FOREIGN_KEY_CHECKS=1; -- включить проверку внешних ключей
COMMIT;  -- сделать коммит
