-- MySQL Workbench Synchronization
-- Generated: 2022-04-03 00:59
-- Model: New Model
-- Version: 1.0
-- Project: Name of the project
-- Author: Sandy

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

ALTER TABLE `gppremium`.`carcaca`
ADD COLUMN `status_carcaca_id` INT(11) NOT NULL AFTER `fotos`,
ADD INDEX `fk_carcaca_status_carcaca1_idx` (`status_carcaca_id` ASC) VISIBLE;
;

CREATE TABLE IF NOT EXISTS `gppremium`.`status_carcaca` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `descricao` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

ALTER TABLE `gppremium`.`carcaca`
ADD CONSTRAINT `fk_carcaca_status_carcaca1`
  FOREIGN KEY (`status_carcaca_id`)
  REFERENCES `gppremium`.`status_carcaca` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
