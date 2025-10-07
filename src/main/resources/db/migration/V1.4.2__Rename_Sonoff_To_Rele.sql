-- Migração idempotente: renomeia sonoff -> rele apenas se necessário e ajusta coluna/FK condicionalmente

SET @schema := DATABASE();

-- Desabilitar checagem de FKs temporariamente
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS; SET FOREIGN_KEY_CHECKS = 0;

-- 1) Verificar existência das tabelas
SET @sonoff_exists := (
    SELECT COUNT(*) FROM information_schema.tables
    WHERE table_schema = @schema AND table_name = 'sonoff'
);
SET @rele_exists := (
    SELECT COUNT(*) FROM information_schema.tables
    WHERE table_schema = @schema AND table_name = 'rele'
);

-- 2) Se sonoff existe e rele não, preparar para renomear
-- 2a) Remover FK antiga fk_sonoff_maquina se existir
SET @fk_sonoff_exists := (
    SELECT COUNT(*) FROM information_schema.TABLE_CONSTRAINTS
    WHERE CONSTRAINT_SCHEMA = @schema AND TABLE_NAME = 'sonoff'
      AND CONSTRAINT_NAME = 'fk_sonoff_maquina' AND CONSTRAINT_TYPE = 'FOREIGN KEY'
);
SET @sql := IF(@sonoff_exists > 0 AND @rele_exists = 0 AND @fk_sonoff_exists > 0,
    'ALTER TABLE sonoff DROP FOREIGN KEY fk_sonoff_maquina',
    'DO 0'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- 2b) Renomear tabela sonoff -> rele somente se aplicável
SET @sql := IF(@sonoff_exists > 0 AND @rele_exists = 0,
    'RENAME TABLE sonoff TO rele',
    'DO 0'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- 2c) Se por ordem de execução V1.4.1 não rodou, remover colunas obsoletas na tabela 'rele'
-- Remover coluna 'nome' se existir (era NOT NULL em V1.4.0)
SET @col_nome_rele_exists := (
    SELECT COUNT(*) FROM information_schema.columns
    WHERE table_schema = @schema AND table_name = 'rele' AND column_name = 'nome'
);
SET @sql := IF(@col_nome_rele_exists > 0,
    'ALTER TABLE rele DROP COLUMN nome',
    'DO 0'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- Remover coluna 'maquina' se existir (substituída por maquina_registro_id)
SET @col_maquina_rele_exists := (
    SELECT COUNT(*) FROM information_schema.columns
    WHERE table_schema = @schema AND table_name = 'rele' AND column_name = 'maquina'
);
SET @sql := IF(@col_maquina_rele_exists > 0,
    'ALTER TABLE rele DROP COLUMN maquina',
    'DO 0'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- 3) Ajustar coluna de FK na tabela rele
-- Se a coluna maquina_id existe, renomeá-la para maquina_registro_id com o mesmo tipo de maquina_registro.id
SET @mr_id_type := (
    SELECT COLUMN_TYPE FROM information_schema.columns
    WHERE table_schema = @schema AND table_name = 'maquina_registro' AND column_name = 'id' LIMIT 1
);
SET @col_maquina_id_exists := (
    SELECT COUNT(*) FROM information_schema.columns
    WHERE table_schema = @schema AND table_name = 'rele' AND column_name = 'maquina_id'
);
SET @sql := IF(@col_maquina_id_exists > 0,
    CONCAT('ALTER TABLE rele CHANGE COLUMN maquina_id maquina_registro_id ', COALESCE(@mr_id_type, 'INT(11)'), ' NOT NULL'),
    'DO 0'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- 3b) Se a coluna já está com nome maquina_registro_id mas tipo difere, ajustar tipo
SET @rele_mr_type := (
    SELECT COLUMN_TYPE FROM information_schema.columns
    WHERE table_schema = @schema AND table_name = 'rele' AND column_name = 'maquina_registro_id' LIMIT 1
);
SET @need_modify := (
    SELECT CASE WHEN @rele_mr_type IS NOT NULL AND @mr_id_type IS NOT NULL AND @rele_mr_type <> @mr_id_type THEN 1 ELSE 0 END
);
SET @sql := IF(@need_modify = 1,
    CONCAT('ALTER TABLE rele MODIFY COLUMN maquina_registro_id ', @mr_id_type, ' NOT NULL'),
    'DO 0'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- 4) Recriar FK em rele, somente se não existir
SET @fk_rele_exists := (
    SELECT COUNT(*) FROM information_schema.TABLE_CONSTRAINTS
    WHERE CONSTRAINT_SCHEMA = @schema AND TABLE_NAME = 'rele'
      AND CONSTRAINT_NAME = 'fk_rele_maquina_registro' AND CONSTRAINT_TYPE = 'FOREIGN KEY'
);
SET @sql := IF(@fk_rele_exists = 0,
    'ALTER TABLE rele ADD CONSTRAINT fk_rele_maquina_registro FOREIGN KEY (maquina_registro_id) REFERENCES maquina_registro(id)',
    'DO 0'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- Reabilitar checagem de FKs
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;