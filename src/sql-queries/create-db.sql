-- Database
CREATE DATABASE IF NOT EXISTS `Splitwise`;

USE `Splitwise`;

-- User Table
CREATE TABLE `User` (
    `user_id` INTEGER AUTO_INCREMENT PRIMARY KEY,
    `username` VARCHAR(255) NOT NULL UNIQUE,
    `email` VARCHAR(255) UNIQUE,
    `phone_country_code` VARCHAR(5) NOT NULL,
    `phone_number` INTEGER NOT NULL,
    CONSTRAINT `unique_phone_number_with_country_code` UNIQUE (`phone_country_code`, `phone_number`),
    `password` VARCHAR(255) NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Group Table
CREATE TABLE `Group` (
    `group_id` INTEGER AUTO_INCREMENT PRIMARY KEY,
    `group_name` VARCHAR(255) NOT NULL,
    `user_id` INTEGER,
    FOREIGN KEY (`user_id`) REFERENCES `User`(`user_id`) ON DELETE CASCADE
);

-- Expense Table
CREATE TABLE `Expense` (
    `expense_id` INTEGER AUTO_INCREMENT PRIMARY KEY,
    `group_id` INTEGER NOT NULL,
    `payer_id` INTEGER NOT NULL,
    `amount` DECIMAL(10, 2) NOT NULL,
    `description` VARCHAR(255),
    `date` DATE NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`group_id`) REFERENCES `Group`(`group_id`) ON DELETE CASCADE,
    FOREIGN KEY (`payer_id`) REFERENCES `User`(`user_id`) ON DELETE CASCADE
);

-- Expense_Share Table
CREATE TABLE `Expense_Share` (
    `sharing_expense_id` INTEGER AUTO_INCREMENT PRIMARY KEY,
    `expense_id` INTEGER,
    `user_id` INTEGER,
    `share` DECIMAL(10, 2) NOT NULL,
    CONSTRAINT `user_expense_id` UNIQUE (`expense_id`, `user_id`),
    FOREIGN KEY (`expense_id`) REFERENCES `Expense`(`expense_id`),
    FOREIGN KEY (`user_id`) REFERENCES `User`(`user_id`)
);

-- Settlement Table
CREATE TABLE `Settlement` (
    `settlement_id` INTEGER AUTO_INCREMENT PRIMARY KEY,
    `payer_id` INTEGER NOT NULL,
    `payee_id` INTEGER NOT NULL,
    `amount` DECIMAL(10, 2) NOT NULL,
    `description` VARCHAR(255),
    `date` DATE,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`payer_id`) REFERENCES `User`(`user_id`) ON DELETE CASCADE,
    FOREIGN KEY (`payee_id`) REFERENCES `User`(`user_id`) ON DELETE CASCADE
);
