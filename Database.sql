

--  BELOW (CREATE TABLE) DDL's SHOULD BE APPLIED IN BELOW OREDER
--  BECAUSE THERE IS A DEPEDENCY AMONST TABLES BECAUSE OF FOREIGHN KEY CONSTRAINT

CREATE TABLE `ADDRESS` (
  `houseNumber` int NOT NULL,
  `postcode` varchar(30) NOT NULL,
  `roadName` varchar(100) DEFAULT NULL,
  `cityName` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`houseNumber`,`postcode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `USERS` (
  `userID` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `passwordHash` varchar(64) NOT NULL,
  `forename` varchar(50) DEFAULT NULL,
  `surname` varchar(50) DEFAULT NULL,
  'last_login TIMESTAMP' DEFAULT CURRENT_TIMESTAMP,  -- Timestamp of the last login (default to the current timestamp)
  `failedLoginAttempts` int DEFAULT '0',
  'account_locked' BOOLEAN DEFAULT FALSE      -- Flag to indicate if the account is locked (default to false)
  `houseNumber` int NOT NULL,
  `postcode` varchar(30) NOT NULL,
  PRIMARY KEY (`userID`),
  KEY `houseNumber_idx` (`houseNumber`) /*!80000 INVISIBLE */,
  KEY `postcode_idx` (`postcode`),
  CONSTRAINT `houseNumberpostcode` FOREIGN KEY (`houseNumber`, `postcode` ) REFERENCES `ADDRESS` (`houseNumber`, `postcode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci


CREATE TABLE `ROLES` (
  `userID` varchar(50) NOT NULL,
  `role` enum('Customer','Staff','Manager') NOT NULL,
  PRIMARY KEY (`userID`, `role`),
  CONSTRAINT `Roles_userID` FOREIGN KEY (`userID`) REFERENCES `USERS` (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci


CREATE TABLE `BANK_DETAILS` (
  `userID` varchar(50) NOT NULL,
  `bankCardName` varchar(100) DEFAULT NULL,
  `cardHolderName` varchar(100) DEFAULT NULL,
  `cardNumber` varchar(30) DEFAULT NULL,
  `cardExpiryDate` date DEFAULT NULL,
  `securityCode` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`userID`),
  CONSTRAINT `Bank_Details_userID` FOREIGN KEY (`userID`) REFERENCES `USERS` (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci


CREATE TABLE `ORDERS` (
  `orderNumber` int NOT NULL AUTO_INCREMENT,
  `orderDate` date DEFAULT NULL,
  `orderStatus` enum('Pending','Confirmed','Fulfilled') DEFAULT NULL,
  `userID` varchar(50) NOT NULL,
  PRIMARY KEY (`orderNumber`),
  KEY `userID_idx` (`userID`),
  CONSTRAINT `fk_userID` FOREIGN KEY (`userID`) REFERENCES `USERS` (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `PRODUCTS` (
  `productCode` varchar(10) NOT NULL,
  `brandName` varchar(60) NOT NULL,
  `productName` varchar(200) NOT NULL,
  `retailPrice` decimal(8,2) NOT NULL,
  `stock` int NOT NULL,
  `gauge` varchar(9) DEFAULT NULL,
  `eraCode` varchar(15) DEFAULT NULL,
  `dccCode` varchar(50) DEFAULT NULL,
  `productType` char(1) NOT NULL,
  PRIMARY KEY (`productCode`),
  UNIQUE KEY `productCode_UNIQUE` (`productCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci


CREATE TABLE `ORDER_LINES` (
  `orderNumber` int NOT NULL,
  `orderLineNumber` int NOT NULL,
  `quantity` int DEFAULT NULL,
  `lineCost` float DEFAULT NULL,
  `productCode` varchar(10) NOT NULL,
  PRIMARY KEY (`orderNumber`,`orderLineNumber`),
  KEY `fk_productCode_idx` (`productCode`),
  CONSTRAINT `fk_orderNumber` FOREIGN KEY (`orderNumber`) REFERENCES `ORDERS` (`orderNumber`),
  CONSTRAINT `fk_productCode` FOREIGN KEY (`productCode`) REFERENCES `PRODUCTS` (`productCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci


CREATE TABLE `SETS` (
  `setCode` varchar(10) NOT NULL,
  `productCode` varchar(10) NOT NULL,
  PRIMARY KEY (`setCode`,`productCode`),
  KEY `productCode_idx` (`productCode`),
  CONSTRAINT `productCode` FOREIGN KEY (`productCode`) REFERENCES `PRODUCTS` (`productCode`) ON DELETE CASCADE,
  CONSTRAINT `setCode` FOREIGN KEY (`setCode`) REFERENCES `PRODUCTS` (`productCode`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `TRACK_PACK` (
  `trackPackCode` varchar(10) NOT NULL,
  `productCode` varchar(10) NOT NULL,
  PRIMARY KEY (`trackPackCode`,`productCode`),
  KEY `productCode_idx` (`productCode`),
  CONSTRAINT `trackPackCode` FOREIGN KEY (`trackPackCode`) REFERENCES `PRODUCTS` (`productCode`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci




-- Insert Manager data into the 'User' table
INSERT INTO USERS (email, forename, surname , password_hash)
VALUES
    ( 'manager@example.com',  'manager', 'manager', '423e16e053d0121774ce4e0a42556837fbfe0d9f74dcd4ef3966a5e5194ceceb') ;  -- User: Manager

-- Insert sample data into the 'Roles' table to associate users with roles
INSERT INTO ROLES (userId, role)
VALUES
    ('<User ID from Users table>', 'Manager');   -- Manager

--turn a user into a manager
INSERT INTO ROLES (userId, role)
VALUES
    ('1998c6f7-cf0b-41e2-9958-000065a9270e', 'Manager');


INSERT INTO ROLES (userId, role)
VALUES
    ('1', 'Staff');   --

-- Select all rows from the 'User' table
SELECT * FROM USERS;
