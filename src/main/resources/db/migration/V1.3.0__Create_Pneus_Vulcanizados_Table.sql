-- Migração idempotente: cria a tabela somente se não existir e ajusta índices/FKs conforme necessário

SET @schema := DATABASE();

-- 1) Criar tabela somente se não existir
SET @tbl_exists := (
    SELECT COUNT(*) FROM information_schema.tables
    WHERE table_schema = @schema AND table_name = 'pneus_vulcanizados'
);
SET @sql := IF(@tbl_exists = 0,
    'CREATE TABLE pneus_vulcanizados (\n        id BIGINT AUTO_INCREMENT PRIMARY KEY,\n        usuario_id BIGINT NOT NULL,\n        producao_id BIGINT NOT NULL,\n        status ENUM(\'INICIADO\', \'FINALIZADO\') NOT NULL DEFAULT \'INICIADO\',\n        dt_create DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,\n        dt_update DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,\n        dt_delete DATETIME DEFAULT NULL\n    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci',
    'DO 0'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- 2) Garantir índices existentes ou criar se faltar
SET @idx_usuario := (
    SELECT COUNT(*) FROM information_schema.statistics
    WHERE table_schema = @schema AND table_name = 'pneus_vulcanizados' AND index_name = 'idx_usuario_id'
);
SET @sql := IF(@idx_usuario = 0,
    'CREATE INDEX idx_usuario_id ON pneus_vulcanizados(usuario_id)',
    'DO 0'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @idx_producao := (
    SELECT COUNT(*) FROM information_schema.statistics
    WHERE table_schema = @schema AND table_name = 'pneus_vulcanizados' AND index_name = 'idx_producao_id'
);
SET @sql := IF(@idx_producao = 0,
    'CREATE INDEX idx_producao_id ON pneus_vulcanizados(producao_id)',
    'DO 0'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @idx_status := (
    SELECT COUNT(*) FROM information_schema.statistics
    WHERE table_schema = @schema AND table_name = 'pneus_vulcanizados' AND index_name = 'idx_status'
);
SET @sql := IF(@idx_status = 0,
    'CREATE INDEX idx_status ON pneus_vulcanizados(status)',
    'DO 0'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @idx_dt_create := (
    SELECT COUNT(*) FROM information_schema.statistics
    WHERE table_schema = @schema AND table_name = 'pneus_vulcanizados' AND index_name = 'idx_dt_create'
);
SET @sql := IF(@idx_dt_create = 0,
    'CREATE INDEX idx_dt_create ON pneus_vulcanizados(dt_create)',
    'DO 0'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @idx_dt_delete := (
    SELECT COUNT(*) FROM information_schema.statistics
    WHERE table_schema = @schema AND table_name = 'pneus_vulcanizados' AND index_name = 'idx_dt_delete'
);
SET @sql := IF(@idx_dt_delete = 0,
    'CREATE INDEX idx_dt_delete ON pneus_vulcanizados(dt_delete)',
    'DO 0'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- 3) Garantir tipos de colunas compatíveis com chaves estrangeiras
-- Ajustar usuario_id para combinar com tipo de usuario.id, se necessário
SET @usuario_id_type := (
    SELECT COLUMN_TYPE FROM information_schema.columns
    WHERE table_schema = @schema AND table_name = 'usuario' AND column_name = 'id' LIMIT 1
);
SET @pv_usuario_type := (
    SELECT COLUMN_TYPE FROM information_schema.columns
    WHERE table_schema = @schema AND table_name = 'pneus_vulcanizados' AND column_name = 'usuario_id' LIMIT 1
);
SET @sql := IF(@pv_usuario_type IS NOT NULL AND @usuario_id_type IS NOT NULL AND @pv_usuario_type <> @usuario_id_type,
    CONCAT('ALTER TABLE pneus_vulcanizados MODIFY COLUMN usuario_id ', @usuario_id_type, ' NOT NULL'),
    'DO 0'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- Ajustar producao_id para combinar com tipo de producao.id, se necessário
SET @producao_id_type := (
    SELECT COLUMN_TYPE FROM information_schema.columns
    WHERE table_schema = @schema AND table_name = 'producao' AND column_name = 'id' LIMIT 1
);
SET @pv_producao_type := (
    SELECT COLUMN_TYPE FROM information_schema.columns
    WHERE table_schema = @schema AND table_name = 'pneus_vulcanizados' AND column_name = 'producao_id' LIMIT 1
);
SET @sql := IF(@pv_producao_type IS NOT NULL AND @producao_id_type IS NOT NULL AND @pv_producao_type <> @producao_id_type,
    CONCAT('ALTER TABLE pneus_vulcanizados MODIFY COLUMN producao_id ', @producao_id_type, ' NOT NULL'),
    'DO 0'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- 4) Garantir FKs existem, criar se faltarem
SET @fk_usuario_exists := (
    SELECT COUNT(*) FROM information_schema.KEY_COLUMN_USAGE
    WHERE table_schema = @schema AND table_name = 'pneus_vulcanizados'
      AND column_name = 'usuario_id' AND referenced_table_name = 'usuario' AND referenced_column_name = 'id'
);
SET @sql := IF(@fk_usuario_exists = 0,
    'ALTER TABLE pneus_vulcanizados ADD CONSTRAINT fk_pneus_vulcanizados_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id)',
    'DO 0'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @fk_producao_exists := (
    SELECT COUNT(*) FROM information_schema.KEY_COLUMN_USAGE
    WHERE table_schema = @schema AND table_name = 'pneus_vulcanizados'
      AND column_name = 'producao_id' AND referenced_table_name = 'producao' AND referenced_column_name = 'id'
);
SET @sql := IF(@fk_producao_exists = 0,
    'ALTER TABLE pneus_vulcanizados ADD CONSTRAINT fk_pneus_vulcanizados_producao FOREIGN KEY (producao_id) REFERENCES producao(id)',
    'DO 0'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- Comentários para documentação
ALTER TABLE pneus_vulcanizados 
    COMMENT = 'Tabela para controle de pneus vulcanizados por usuário e produção';

-- Observação: evitar alterações de tipo com MODIFY para não conflitar com FKs existentes.
-- Caso precise adicionar comentários nas colunas, fazer em migração futura usando o tipo atual da coluna via information_schema.