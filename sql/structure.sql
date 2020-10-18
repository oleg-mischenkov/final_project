DROP TABLE IF EXISTS `items`;

CREATE TABLE `items` (
  `item_id` int NOT NULL AUTO_INCREMENT,
  `tariff_id` int NOT NULL,
  `order_id` int NOT NULL,
  PRIMARY KEY (`item_id`),
  UNIQUE KEY `item_id_UNIQUE` (`item_id`),
  KEY `fk_items_tariffs1_idx` (`tariff_id`),
  KEY `fk_items_orders1_idx` (`order_id`),
  CONSTRAINT `fk_items_orders1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_items_tariffs1` FOREIGN KEY (`tariff_id`) REFERENCES `tariffs` (`tariff_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `lang_serv`;

CREATE TABLE `lang_serv` (
  `lang_id` int DEFAULT NULL,
  `serv_id` int DEFAULT NULL,
  `title` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `short_description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci,
  UNIQUE KEY `index3` (`lang_id`,`serv_id`),
  KEY `ind_lang_id` (`lang_id`) /*!80000 INVISIBLE */,
  KEY `ind_serv_id` (`serv_id`) /*!80000 INVISIBLE */,
  CONSTRAINT `lang_id` FOREIGN KEY (`lang_id`) REFERENCES `languages` (`lang_id`),
  CONSTRAINT `serv_id` FOREIGN KEY (`serv_id`) REFERENCES `services` (`service_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `lang_tariffs`;

CREATE TABLE `lang_tariffs` (
  `lang_id` int DEFAULT NULL,
  `trf_id` int DEFAULT NULL,
  `title` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `short_description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci,
  UNIQUE KEY `index_tar_id_lang_id` (`lang_id`,`trf_id`) /*!80000 INVISIBLE */,
  KEY `ind_lang_id` (`lang_id`) /*!80000 INVISIBLE */,
  KEY `ind_tariff_id` (`trf_id`) /*!80000 INVISIBLE */,
  CONSTRAINT `fk_lang_id` FOREIGN KEY (`lang_id`) REFERENCES `languages` (`lang_id`),
  CONSTRAINT `fk_tariff_id` FOREIGN KEY (`trf_id`) REFERENCES `tariffs` (`tariff_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `languages`;

CREATE TABLE `languages` (
  `lang_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(16) NOT NULL,
  `active` tinyint(1) NOT NULL DEFAULT '0',
  `iso-639-1` varchar(2) DEFAULT NULL,
  `iso-639-2` varchar(3) DEFAULT NULL,
  `iso-639-3` varchar(3) DEFAULT NULL,
  `code` smallint NOT NULL,
  PRIMARY KEY (`lang_id`),
  UNIQUE KEY `title_UNIQUE` (`title`),
  UNIQUE KEY `code_UNIQUE` (`code`),
  UNIQUE KEY `alpha2_UNIQUE` (`iso-639-1`),
  UNIQUE KEY `alpha3_UNIQUE` (`iso-639-2`),
  UNIQUE KEY `iso-639-3_UNIQUE` (`iso-639-3`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `money_cards`;

CREATE TABLE `money_cards` (
  `card_id` int NOT NULL AUTO_INCREMENT,
  `money` int unsigned NOT NULL,
  `card_code` int unsigned NOT NULL,
  PRIMARY KEY (`card_id`),
  UNIQUE KEY `card_code_UNIQUE` (`card_code`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `total` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`order_id`),
  KEY `fk_orders_users1_idx` (`user_id`),
  CONSTRAINT `fk_orders_users1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(45) NOT NULL,
  `special` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `role_name_UNIQUE` (`role_name`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `role_permission`;

CREATE TABLE `role_permission` (
  `perm_id` int NOT NULL AUTO_INCREMENT,
  `admin_access` tinyint(1) NOT NULL DEFAULT '0',
  `card_access` tinyint(1) NOT NULL DEFAULT '1',
  `role_id` int NOT NULL,
  PRIMARY KEY (`perm_id`),
  KEY `fk_role_permission_role_idx` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `services`;

CREATE TABLE `services` (
  `service_id` int NOT NULL AUTO_INCREMENT,
  `active` tinyint(1) NOT NULL DEFAULT '1',
  `start_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `end_date` datetime DEFAULT NULL,
  PRIMARY KEY (`service_id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `tariffs`;

CREATE TABLE `tariffs` (
  `tariff_id` int NOT NULL AUTO_INCREMENT,
  `active` tinyint(1) NOT NULL DEFAULT '1',
  `price` int unsigned DEFAULT '0',
  `start_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `end_date` datetime DEFAULT NULL,
  `service_id` int NOT NULL,
  PRIMARY KEY (`tariff_id`),
  KEY `service_id_idx` (`service_id`),
  CONSTRAINT `fk_service_id` FOREIGN KEY (`service_id`) REFERENCES `services` (`service_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `active` tinyint(1) NOT NULL DEFAULT '1',
  `login` varchar(16) NOT NULL,
  `email` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `first_name` varchar(16) NOT NULL,
  `last_name` varchar(16) NOT NULL,
  `gender` enum('M','F') NOT NULL DEFAULT 'M',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `role_id` int NOT NULL DEFAULT '3',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `login_UNIQUE` (`login`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `fk_users_2_role1_idx` (`role_id`),
  CONSTRAINT `fk_users_2_role1` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `wallets`;

CREATE TABLE `wallets` (
  `wallet_id` int NOT NULL AUTO_INCREMENT,
  `money` int unsigned DEFAULT '0',
  `blocked_money` int unsigned DEFAULT '0',
  `users_user_id` int NOT NULL,
  PRIMARY KEY (`wallet_id`),
  UNIQUE KEY `wallet_id_UNIQUE` (`wallet_id`),
  KEY `fk_wallets_users1_idx` (`users_user_id`),
  CONSTRAINT `fk_wallets_users1` FOREIGN KEY (`users_user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;