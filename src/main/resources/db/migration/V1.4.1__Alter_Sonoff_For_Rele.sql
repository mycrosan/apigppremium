-- Migração idempotente: aplicar alterações somente se a tabela 'sonoff' existir.
-- Se a tabela já foi renomeada para 'rele' (via V1.4.2), esta migração não faz nada.

SET @schema := DATABASE();

-- Verificar se tabela sonoff existe
SET @tbl_exists := (
    SELECT COUNT(*) FROM information_schema.tables
    WHERE table_schema = @schema AND table_name = 'sonoff'
);

-- Sair se não existir (já renomeada ou removida)
SET @sql := IF(@tbl_exists = 0, 'DO 0', 'DO 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- Se existir, aplicar alterações de forma segura
-- 1) Remover coluna 'nome' se existir
SET @col_nome_exists := (
    SELECT COUNT(*) FROM information_schema.columns
    WHERE table_schema = @schema AND table_name = 'sonoff' AND column_name = 'nome'
);
SET @sql := IF(@col_nome_exists > 0,
    'ALTER TABLE sonoff DROP COLUMN nome',
    'DO 0'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- 2) Remover coluna 'maquina' se existir
SET @col_maquina_exists := (
    SELECT COUNT(*) FROM information_schema.columns
    WHERE table_schema = @schema AND table_name = 'sonoff' AND column_name = 'maquina'
);
SET @sql := IF(@col_maquina_exists > 0,
    'ALTER TABLE sonoff DROP COLUMN maquina',
    'DO 0'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- 3) Adicionar coluna 'celular_id' se não existir
SET @col_celular_exists := (
    SELECT COUNT(*) FROM information_schema.columns
    WHERE table_schema = @schema AND table_name = 'sonoff' AND column_name = 'celular_id'
);
SET @sql := IF(@col_celular_exists = 0,
    'ALTER TABLE sonoff ADD COLUMN celular_id VARCHAR(100) NOT NULL AFTER ip',
    'DO 0'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- 4) Adicionar coluna 'maquina_id' se não existir, alinhando tipo ao de maquina_registro.id
SET @mr_id_type := (
    SELECT COLUMN_TYPE FROM information_schema.columns
    WHERE table_schema = @schema AND table_name = 'maquina_registro' AND column_name = 'id' LIMIT 1
);
SET @col_mrid_exists := (
    SELECT COUNT(*) FROM information_schema.columns
    WHERE table_schema = @schema AND table_name = 'sonoff' AND column_name = 'maquina_id'
);
SET @sql := IF(@col_mrid_exists = 0,
    CONCAT('ALTER TABLE sonoff ADD COLUMN maquina_id ', COALESCE(@mr_id_type, 'INT(11)'), ' NOT NULL AFTER celular_id'),
    'DO 0'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- 5) Ajustar tipo de maquina_id se diferente
SET @sonoff_maquina_type := (
    SELECT COLUMN_TYPE FROM information_schema.columns
    WHERE table_schema = @schema AND table_name = 'sonoff' AND column_name = 'maquina_id' LIMIT 1
);
SET @need_modify := (
    SELECT CASE WHEN @sonoff_maquina_type IS NOT NULL AND @mr_id_type IS NOT NULL AND @sonoff_maquina_type <> @mr_id_type THEN 1 ELSE 0 END
);
SET @sql := IF(@need_modify = 1,
    CONCAT('ALTER TABLE sonoff MODIFY COLUMN maquina_id ', @mr_id_type, ' NOT NULL'),
    'DO 0'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- 6) Adicionar FK somente se não existir
SET @fk_exists := (
    SELECT COUNT(*) FROM information_schema.KEY_COLUMN_USAGE
    WHERE table_schema = @schema AND table_name = 'sonoff' AND column_name = 'maquina_id'
      AND referenced_table_name = 'maquina_registro' AND referenced_column_name = 'id'
);
SET @sql := IF(@fk_exists = 0,
    'ALTER TABLE sonoff ADD CONSTRAINT fk_sonoff_maquina FOREIGN KEY (maquina_id) REFERENCES maquina_registro (id)',
    'DO 0'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;