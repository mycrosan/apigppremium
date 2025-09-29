-- Migração para adicionar coluna dt_delete na tabela maquina_registro caso não exista

-- Verificar se a coluna dt_delete existe e adicioná-la se necessário
SET @col_exists = 0;
SELECT COUNT(*) INTO @col_exists 
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_SCHEMA = DATABASE() 
  AND TABLE_NAME = 'maquina_registro' 
  AND COLUMN_NAME = 'dt_delete';

SET @sql = IF(@col_exists = 0, 
    'ALTER TABLE maquina_registro ADD COLUMN dt_delete DATETIME DEFAULT NULL', 
    'SELECT "Coluna dt_delete já existe" as message');

PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Adicionar índice se a coluna foi criada
SET @idx_exists = 0;
SELECT COUNT(*) INTO @idx_exists 
FROM INFORMATION_SCHEMA.STATISTICS 
WHERE TABLE_SCHEMA = DATABASE() 
  AND TABLE_NAME = 'maquina_registro' 
  AND INDEX_NAME = 'idx_dt_delete';

SET @sql_idx = IF(@idx_exists = 0, 
    'CREATE INDEX idx_dt_delete ON maquina_registro (dt_delete)', 
    'SELECT "Índice idx_dt_delete já existe" as message');

PREPARE stmt_idx FROM @sql_idx;
EXECUTE stmt_idx;
DEALLOCATE PREPARE stmt_idx;