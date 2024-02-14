-- Database
CREATE DATABASE IF NOT EXISTS `Splitwise`;

USE `Splitwise`;

-- User Table
CREATE TABLE `user` (
    `user_id` INTEGER AUTO_INCREMENT PRIMARY KEY,
    `username` VARCHAR(255) NOT NULL UNIQUE,
    `email` VARCHAR(255) UNIQUE,
    `phone_country_code` VARCHAR(5) NOT NULL,
    `phone_number` INTEGER NOT NULL,
    CONSTRAINT `unique_phone_number_with_country_code` UNIQUE (`phone_country_code`, `phone_number`),
    `password` VARCHAR(255) NOT NULL
);
-- Group Table
CREATE TABLE `group_table` (
    `group_id` INTEGER AUTO_INCREMENT PRIMARY KEY,
    `group_name` VARCHAR(255) NOT NULL,
    `user_id` INTEGER,
    FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`) ON DELETE CASCADE
);

-- Expense Table
CREATE TABLE `expense` (
    `expense_id` INTEGER AUTO_INCREMENT PRIMARY KEY,
    `group_id` INTEGER NOT NULL,
    `payer_id` INTEGER NOT NULL,
    `amount` DECIMAL(10, 2) NOT NULL,
    `description` VARCHAR(255),
    `date` DATE NOT NULL,
    FOREIGN KEY (`group_id`) REFERENCES `group_table`(`group_id`) ON DELETE CASCADE,
    FOREIGN KEY (`payer_id`) REFERENCES `user`(`user_id`) ON DELETE CASCADE
);

-- Expense_Share Table
CREATE TABLE `expense_share` (
    `sharing_expense_id` INTEGER AUTO_INCREMENT PRIMARY KEY,
    `expense_id` INTEGER,
    `user_id` INTEGER,
    `share` DECIMAL(10, 2) NOT NULL,
    CONSTRAINT `user_expense_id` UNIQUE (`expense_id`, `user_id`),
    FOREIGN KEY (`expense_id`) REFERENCES `expense`(`expense_id`),
    FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`)
);

-- Settlement Table
CREATE TABLE `settlement` (
    `settlement_id` INTEGER AUTO_INCREMENT PRIMARY KEY,
    `payer_id` INTEGER NOT NULL,
    `payee_id` INTEGER NOT NULL,
    `amount` DECIMAL(10, 2) NOT NULL,
    `description` VARCHAR(255),
    `date` DATE,
    FOREIGN KEY (`payer_id`) REFERENCES `user`(`user_id`) ON DELETE CASCADE,
    FOREIGN KEY (`payee_id`) REFERENCES `user`(`user_id`) ON DELETE CASCADE
);
