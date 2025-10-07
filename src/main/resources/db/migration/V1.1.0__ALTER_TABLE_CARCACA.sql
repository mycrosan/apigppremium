-- Migração idempotente: adiciona coluna uuid em carcaca apenas se não existir
-- e ajusta restrição de chave estrangeira somente quando necessário

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS; SET UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS; SET FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE; SET SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

SET @schema := DATABASE();

-- 1) Adicionar coluna uuid somente se não existir (usar NULL inicialmente para evitar falhas em tabelas com dados)
SET @col_exists := (
    SELECT COUNT(*) FROM information_schema.columns
    WHERE table_schema = @schema AND table_name = 'carcaca' AND column_name = 'uuid'
);
SET @sql := IF(@col_exists = 0,
    'ALTER TABLE `gppremium`.`carcaca` ADD COLUMN `uuid` BINARY(16) NULL AFTER `dt_delete`',
    'DO 0'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- 2) Preencher uuid para registros nulos
UPDATE `gppremium`.`carcaca` SET `uuid` = UUID_TO_BIN(UUID()) WHERE `uuid` IS NULL;

-- 3) Tornar uuid NOT NULL somente se ainda for anulável
SET @is_nullable := (
    SELECT CASE WHEN UPPER(COALESCE(IS_NULLABLE,'YES'))='YES' THEN 1 ELSE 0 END
    FROM information_schema.columns
    WHERE table_schema = @schema AND table_name = 'carcaca' AND column_name = 'uuid'
    LIMIT 1
);
SET @sql := IF(@is_nullable = 1,
    'ALTER TABLE `gppremium`.`carcaca` MODIFY COLUMN `uuid` BINARY(16) NOT NULL',
    'DO 0'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- 4) Ajustar FK fk_carcaca_status_carcaca1 de forma idempotente
SET @fk_exists := (
    SELECT COUNT(*)
    FROM information_schema.TABLE_CONSTRAINTS
    WHERE CONSTRAINT_SCHEMA = @schema AND TABLE_NAME = 'carcaca'
      AND CONSTRAINT_NAME = 'fk_carcaca_status_carcaca1'
      AND CONSTRAINT_TYPE = 'FOREIGN KEY'
);

-- Drop somente se existir
SET @sql := IF(@fk_exists > 0,
    'ALTER TABLE `gppremium`.`carcaca` DROP FOREIGN KEY `fk_carcaca_status_carcaca1`',
    'DO 0'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- Recriar FK somente se não existir
SET @fk_exists := (
    SELECT COUNT(*)
    FROM information_schema.TABLE_CONSTRAINTS
    WHERE CONSTRAINT_SCHEMA = @schema AND TABLE_NAME = 'carcaca'
      AND CONSTRAINT_NAME = 'fk_carcaca_status_carcaca1'
      AND CONSTRAINT_TYPE = 'FOREIGN KEY'
);
SET @sql := IF(@fk_exists = 0,
    'ALTER TABLE `gppremium`.`carcaca` ADD CONSTRAINT `fk_carcaca_status_carcaca1` FOREIGN KEY (`status_carcaca_id`) REFERENCES `gppremium`.`status_carcaca` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION',
    'DO 0'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
