-- Migration V1.2.0: Remove unique constraint on celular_id to allow multiple configurations per mobile device
-- This enables the new functionality where a mobile device can have multiple matrix configurations
-- with the latest one being the active configuration

-- Idempotente: remove a unique constraint somente se o índice existir
SET @schema := DATABASE();
SET @idx_exists := (
    SELECT COUNT(*) FROM information_schema.statistics
    WHERE table_schema = @schema
      AND table_name = 'maquina_configuracao'
      AND index_name = 'uq_celular'
);
SET @sql := IF(@idx_exists > 0,
    'ALTER TABLE maquina_configuracao DROP INDEX uq_celular',
    'DO 0'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- Criar índice composto somente se não existir
SET @idx_perf_exists := (
    SELECT COUNT(*) FROM information_schema.statistics
    WHERE table_schema = @schema
      AND table_name = 'maquina_configuracao'
      AND index_name = 'idx_celular_dt_create'
);
SET @sql := IF(@idx_perf_exists = 0,
    'CREATE INDEX idx_celular_dt_create ON maquina_configuracao (celular_id, dt_create DESC)',
    'DO 0'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- Add comment to clarify the new behavior
ALTER TABLE maquina_configuracao COMMENT = 'Configurações de máquina por celular - permite múltiplas configurações, sendo a mais recente a ativa';