-- This is initial population script.
-- Every time the application is initialized it will be executed, cleaning up any previously stored data.

-- A table to store users
DROP TABLE IF EXISTS USERS;
CREATE TABLE IF NOT EXISTS USERS (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    fullname VARCHAR(64) NOT NULL,
    username VARCHAR(64) NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- A table to store user roles
DROP TABLE IF EXISTS ROLES;
CREATE TABLE IF NOT EXISTS ROLES (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(64) NOT NULL
);

-- Linking Users and Roles
DROP TABLE IF EXISTS USER_ROLES;
CREATE TABLE IF NOT EXISTS USER_ROLES (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,

	FOREIGN KEY (user_id) REFERENCES USERS(id) ON DELETE CASCADE,
	FOREIGN KEY (role_id) REFERENCES ROLES(id) ON DELETE CASCADE
);


-- Initial database population
INSERT INTO USERS( fullname, username, password )
  VALUES
    ( 'John', 'john', 'john' ),
    ( 'Paul', 'paul', 'paul' ),
    ( 'Mary', 'mary', 'mary' ),
    ( 'Lucy', 'lucy', 'lucy' )
  ;

INSERT INTO ROLES (name) VALUES ( 'CHAT-ROOM' );

INSERT INTO USER_ROLES VALUES
 ( 1, 1 ), ( 2, 1 ), ( 3, 1 ), ( 3, 1 );
