-- Migração para adicionar coluna device_id na tabela maquina_registro caso não exista

-- Verificar se a coluna device_id existe e adicioná-la se necessário
SET @col_exists = 0;
SELECT COUNT(*) INTO @col_exists 
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_SCHEMA = DATABASE() 
  AND TABLE_NAME = 'maquina_registro' 
  AND COLUMN_NAME = 'device_id';

SET @sql = IF(@col_exists = 0, 
    'ALTER TABLE maquina_registro ADD COLUMN device_id VARCHAR(100) NOT NULL UNIQUE AFTER id', 
    'SELECT "Coluna device_id já existe" as message');

PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Adicionar índice se a coluna foi criada
SET @idx_exists = 0;
SELECT COUNT(*) INTO @idx_exists 
FROM INFORMATION_SCHEMA.STATISTICS 
WHERE TABLE_SCHEMA = DATABASE() 
  AND TABLE_NAME = 'maquina_registro' 
  AND INDEX_NAME = 'idx_device_id';

SET @sql_idx = IF(@idx_exists = 0, 
    'CREATE INDEX idx_device_id ON maquina_registro (device_id)', 
    'SELECT "Índice idx_device_id já existe" as message');

PREPARE stmt_idx FROM @sql_idx;
EXECUTE stmt_idx;
DEALLOCATE PREPARE stmt_idx;