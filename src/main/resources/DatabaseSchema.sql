-- -----------------------------------------------------
-- Schema escape_room_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `escape_room_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `escape_room_db` ;

-- -----------------------------------------------------
-- Table `escape_room_db`.`client`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `escape_room_db`.`client` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `is_subscribed` TINYINT(1) NOT NULL DEFAULT '0',
  `created_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email` (`email` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `escape_room_db`.`escape_room`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `escape_room_db`.`escape_room` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `created_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `escape_room_db`.`room`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `escape_room_db`.`room` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `escape_room_id` INT NULL DEFAULT NULL,
  `name` VARCHAR(255) NOT NULL,
  `difficulty_level` ENUM('EASY', 'MEDIUM', 'HARD') NOT NULL,
  `price` DOUBLE NOT NULL,
  `created_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `fk_room_escape_room_id` (`escape_room_id` ASC) VISIBLE,
  CONSTRAINT `fk_room_escape_room_id`
    FOREIGN KEY (`escape_room_id`)
    REFERENCES `escape_room_db`.`escape_room` (`id`)
    ON DELETE SET NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `escape_room_db`.`decoration`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `escape_room_db`.`decoration` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `room_id` INT NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `material` VARCHAR(255) NULL DEFAULT NULL,
  `price` DOUBLE NOT NULL,
  `created_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `room_id` (`room_id` ASC) VISIBLE,
  CONSTRAINT `decoration_ibfk_1`
    FOREIGN KEY (`room_id`)
    REFERENCES `escape_room_db`.`room` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `escape_room_db`.`hint`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `escape_room_db`.`hint` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `room_id` INT NOT NULL,
  `description` VARCHAR(255) NOT NULL,
  `price` DOUBLE NOT NULL,
  `created_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `room_id` (`room_id` ASC) VISIBLE,
  CONSTRAINT `hint_ibfk_1`
    FOREIGN KEY (`room_id`)
    REFERENCES `escape_room_db`.`room` (`id`)
    ON DELETE CASCADE
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `escape_room_db`.`room_client`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `escape_room_db`.`room_client` (
  `client_id` INT NOT NULL,
  `room_id` INT NOT NULL,
  `start_time` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `end_time` DATETIME NULL DEFAULT NULL,
  `completed` TINYINT(1) NULL DEFAULT '0',
  PRIMARY KEY (`client_id`, `room_id`),
  INDEX `room_id` (`room_id` ASC) VISIBLE,
  CONSTRAINT `room_client_ibfk_1`
    FOREIGN KEY (`client_id`)
    REFERENCES `escape_room_db`.`client` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `room_client_ibfk_2`
    FOREIGN KEY (`room_id`)
    REFERENCES `escape_room_db`.`room` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `escape_room_db`.`ticket`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `escape_room_db`.`ticket` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `client_id` INT NOT NULL,
  `room_id` INT NOT NULL,
  `total_price` DOUBLE NOT NULL,
  `purchase_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `client_id` (`client_id` ASC) VISIBLE,
  INDEX `room_id` (`room_id` ASC) VISIBLE,
  CONSTRAINT `ticket_ibfk_1`
    FOREIGN KEY (`client_id`)
    REFERENCES `escape_room_db`.`client` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `ticket_ibfk_2`
    FOREIGN KEY (`room_id`)
    REFERENCES `escape_room_db`.`room` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;