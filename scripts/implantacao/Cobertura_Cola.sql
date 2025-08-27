-- MySQL Workbench Synchronization
-- Generated: 2025-08-27 00:27
-- Model: New Model
-- Version: 1.0
-- Project: Name of the project
-- Author: my

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

ALTER TABLE `gppremium`.`cobertura` 
DROP FOREIGN KEY `fk_cobertura_producao1`;

ALTER TABLE `gppremium`.`cobertura` 
ADD COLUMN `cola_id` INT(11) NOT NULL AFTER `usuario_id`,
CHANGE COLUMN `producao_id` `producao_id` INT(11) NULL DEFAULT NULL ,
ADD INDEX `fk_cobertura_cola1_idx` (`cola_id` ASC) VISIBLE;
;

ALTER TABLE `gppremium`.`cobertura` 
ADD CONSTRAINT `fk_cobertura_producao1`
  FOREIGN KEY (`producao_id`)
  REFERENCES `gppremium`.`producao` (`id`),
ADD CONSTRAINT `fk_cobertura_cola1`
  FOREIGN KEY (`cola_id`)
  REFERENCES `gppremium`.`cola` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- Atualizar cobertura com as colas
UPDATE cobertura c
JOIN cola co ON c.producao_id = co.producao_id
SET c.cola_id = co.id
WHERE c.cola_id = 0;


select * from cobertura where cola_id = 0

SET SQL_SAFE_UPDATES = 0;