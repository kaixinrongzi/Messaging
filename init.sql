CREATE DATABASE `messaging_test`;

USE `messaging_test`;

CREATE USER 'messaging'@'localhost' IDENTIFIED BY 'messaging2021';
GRANT ALL PRIVILEGES ON messaging_test.* TO 'messaging'@'localhost';

CREATE TABLE `user` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `username` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                        `nickname` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                        `password` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                        `login_token` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                        `register_time` datetime DEFAULT NULL,
                        `last_login_time` datetime DEFAULT NULL,
                        `gender` varchar(128) DEFAULT NULL,
                        `email` varchar(128) DEFAULT NULL,
                        `address` varchar(128) DEFAULT NULL,
                        `is_valid` tinyint(1) DEFAULT NULL,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `username_uniq_index` (`username`),
                        UNIQUE KEY `email_uniq_index` (`email`),
                        KEY `nickname_index` (`nickname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `user_validation_code` (
                                        `id` int NOT NULL AUTO_INCREMENT,
                                        `user_id` int DEFAULT NULL,
                                        `validation_code` varchar(8) DEFAULT NULL,
                                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

