-- Create a table named 'User' to store user information

CREATE TABLE USER (
    userID int NOT NULL AUTO_INCREMENT,  -- Unique user identifier
    email varchar(100) NOT NULL,               -- User's email address
    password  varchar(64) NOT NULL,        -- Securely hashed password
    forename varchar(50) ,
    surname varchar(50) ,
    houseNumber int  ,
    postcode varchar(30) ,
    PRIMARY KEY (userId)

)


--  ADD CONSTRAINT FK_housenumpostal
--     FOREIGN KEY (houseNumber) REFERENCES USERS_ADDRESS(houseNumber)
--     FOREIGN KEY (postcode) REFERENCES USERS_ADDRESS(postcode)
-- CREATE TABLE `USER` (
--   `userID` int NOT NULL AUTO_INCREMENT,
--   `email` varchar(100) DEFAULT NULL,
--   `password` varchar(100) DEFAULT NULL,
--   `forename` varchar(50) DEFAULT NULL,
--   `surname` varchar(50) DEFAULT NULL,
--   `houseNumber` int NOT NULL,
--   `postcode` varchar(30) NOT NULL,
--   PRIMARY KEY (`userID`),
--   KEY `postcode_idx` (`postcode`),
--   KEY `houseNumber_idx` (`houseNumber`),
--   KEY `fk_housenumber_idx` (`houseNumber`),
--   KEY `fk_postcode_idx` (`postcode`),
--   KEY `postcode_idx1` (`houseNumber`,`postcode`)
-- )
-- --  FOREIGN KEY (houseNumber) REFERENCES USERS_ADDRESS(houseNumber) ,  -- Foreign key relationship with Address
-- FOREIGN KEY (postcode) REFERENCES USERS_ADDRESS(postcode)

--  KEY `fk_postcode_idx` (`postcode`),
-- KEY `postcode_idx1` (`houseNumber`,`postcode`)

CREATE TABLE ADDRESS (
  housenumber int NOT NULL,
  postcode varchar(30) NOT NULL,
  roadName varchar(100) DEFAULT NULL,
  cityName varchar(50) DEFAULT NULL,
  PRIMARY KEY (housenumber,postcode)
)

-- Create a table named 'Roles' to store user roles for each user
CREATE TABLE ROLES (
   userID int NOT NULL,
   role ENUM('Manager', 'Staff', 'Customer') NOT NULL,
   PRIMARY KEY (userId, role),
   FOREIGN KEY (userId) REFERENCES User(userId)
);

-- CREATE TABLE Users (
--       userId VARCHAR(50) NOT NULL PRIMARY KEY,  -- Unique user identifier
--       email VARCHAR(100) NOT NULL,               -- User's email address
--       username VARCHAR(50) NOT NULL,             -- User's username
--       password_hash VARCHAR(64) NOT NULL,        -- Securely hashed password
--       last_login TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- Timestamp of the last login (default to the current timestamp)
--       failed_login_attempts INT DEFAULT 0,       -- Number of failed login attempts (default to 0)
--       account_locked BOOLEAN DEFAULT FALSE      -- Flag to indicate if the account is locked (default to false)
-- );

-- Insert sample data into the 'User' table
INSERT INTO User (email, forename, surname , password_hash)
VALUES
    ( 'manager@example.com',  'manager', 'manager', '423e16e053d0121774ce4e0a42556837fbfe0d9f74dcd4ef3966a5e5194ceceb') ;  -- User: Manager


-- Insert sample data into the 'Roles' table to associate users with roles
INSERT INTO Roles (userId, role)
VALUES
    ('1', 'Manager');   -- Manager

INSERT INTO Roles (userId, role)
VALUES
    ('1', 'Staff');   --

INSERT INTO Roles (userId, role)
VALUES
    ('1', 'Customer') ;  --

-- Select all rows from the 'User' table
SELECT * FROM User;
