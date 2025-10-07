-- Ajuste idempotente pós-renomeação: garante que a tabela 'rele' esteja consistente com o modelo
-- Remove colunas obsoletas e garante coluna/FK de maquina_registro

SET @schema := DATABASE();

-- Desabilitar checagem de FKs temporariamente
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS; SET FOREIGN_KEY_CHECKS = 0;

-- Verificar se a tabela 'rele' existe
SET @rele_exists := (
    SELECT COUNT(*) FROM information_schema.tables
    WHERE table_schema = @schema AND table_name = 'rele'
);

-- Obter tipo de maquina_registro.id
SET @mr_id_type := (
    SELECT COLUMN_TYPE FROM information_schema.columns
    WHERE table_schema = @schema AND table_name = 'maquina_registro' AND column_name = 'id' LIMIT 1
);

-- 1) Remover coluna 'nome' se existir (legado de V1.4.0)
SET @col_nome_exists := (
    SELECT COUNT(*) FROM information_schema.columns
    WHERE table_schema = @schema AND table_name = 'rele' AND column_name = 'nome'
);
SET @sql := IF(@rele_exists > 0 AND @col_nome_exists > 0,
    'ALTER TABLE rele DROP COLUMN nome',
    'DO 0'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- 2) Remover coluna 'maquina' se existir (legado)
SET @col_maquina_exists := (
    SELECT COUNT(*) FROM information_schema.columns
    WHERE table_schema = @schema AND table_name = 'rele' AND column_name = 'maquina'
);
SET @sql := IF(@rele_exists > 0 AND @col_maquina_exists > 0,
    'ALTER TABLE rele DROP COLUMN maquina',
    'DO 0'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- 3) Garantir coluna maquina_registro_id
SET @col_mr_exists := (
    SELECT COUNT(*) FROM information_schema.columns
    WHERE table_schema = @schema AND table_name = 'rele' AND column_name = 'maquina_registro_id'
);
SET @col_maquina_id_exists := (
    SELECT COUNT(*) FROM information_schema.columns
    WHERE table_schema = @schema AND table_name = 'rele' AND column_name = 'maquina_id'
);

-- 3a) Se maquina_registro_id não existe e maquina_id existe, renomear com tipo correto
SET @sql := IF(@rele_exists > 0 AND @col_mr_exists = 0 AND @col_maquina_id_exists > 0,
    CONCAT('ALTER TABLE rele CHANGE COLUMN maquina_id maquina_registro_id ', COALESCE(@mr_id_type, 'INT(11)'), ' NOT NULL'),
    'DO 0'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- 3b) Se nenhuma das duas existe, adicionar maquina_registro_id
SET @sql := IF(@rele_exists > 0 AND @col_mr_exists = 0 AND @col_maquina_id_exists = 0,
    CONCAT('ALTER TABLE rele ADD COLUMN maquina_registro_id ', COALESCE(@mr_id_type, 'INT(11)'), ' NOT NULL'),
    'DO 0'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- 3c) Ajustar tipo se diferente do de maquina_registro.id
SET @rele_mr_type := (
    SELECT COLUMN_TYPE FROM information_schema.columns
    WHERE table_schema = @schema AND table_name = 'rele' AND column_name = 'maquina_registro_id' LIMIT 1
);
SET @need_modify := (
    SELECT CASE WHEN @rele_mr_type IS NOT NULL AND @mr_id_type IS NOT NULL AND @rele_mr_type <> @mr_id_type THEN 1 ELSE 0 END
);
SET @sql := IF(@rele_exists > 0 AND @need_modify = 1,
    CONCAT('ALTER TABLE rele MODIFY COLUMN maquina_registro_id ', @mr_id_type, ' NOT NULL'),
    'DO 0'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- 4) Garantir FK fk_rele_maquina_registro
SET @fk_rele_exists := (
    SELECT COUNT(*) FROM information_schema.TABLE_CONSTRAINTS
    WHERE CONSTRAINT_SCHEMA = @schema AND TABLE_NAME = 'rele'
      AND CONSTRAINT_NAME = 'fk_rele_maquina_registro' AND CONSTRAINT_TYPE = 'FOREIGN KEY'
);
SET @sql := IF(@rele_exists > 0 AND @fk_rele_exists = 0,
    'ALTER TABLE rele ADD CONSTRAINT fk_rele_maquina_registro FOREIGN KEY (maquina_registro_id) REFERENCES maquina_registro(id)',
    'DO 0'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- Reabilitar checagem de FKs
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;