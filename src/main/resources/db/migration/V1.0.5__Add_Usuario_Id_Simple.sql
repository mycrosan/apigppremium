-- Migração idempotente para adicionar coluna usuario_id na tabela maquina_registro
-- Usa information_schema para checar existência antes de alterar a tabela

SET @schema := DATABASE();

-- Descobrir o tipo da coluna usuario.id para garantir compatibilidade
SET @ref_col_type := (
    SELECT COLUMN_TYPE FROM information_schema.columns
    WHERE table_schema = @schema AND table_name = 'usuario' AND column_name = 'id'
    LIMIT 1
);

-- Adicionar coluna usuario_id somente se não existir, com mesmo tipo de usuario.id
SET @col_exists := (
    SELECT COUNT(*) FROM information_schema.columns
    WHERE table_schema = @schema AND table_name = 'maquina_registro' AND column_name = 'usuario_id'
);
SET @sql := IF(@col_exists = 0,
    CONCAT('ALTER TABLE maquina_registro ADD COLUMN usuario_id ', @ref_col_type, ' DEFAULT NULL'),
    'DO 0'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- Se a coluna já existe mas o tipo é diferente, ajustar para combinar com usuario.id
SET @mr_col_type := (
    SELECT COLUMN_TYPE FROM information_schema.columns
    WHERE table_schema = @schema AND table_name = 'maquina_registro' AND column_name = 'usuario_id'
    LIMIT 1
);
SET @need_modify := (
    SELECT CASE WHEN @mr_col_type IS NOT NULL AND @ref_col_type IS NOT NULL AND @mr_col_type <> @ref_col_type THEN 1 ELSE 0 END
);
SET @sql := IF(@need_modify = 1,
    CONCAT('ALTER TABLE maquina_registro MODIFY COLUMN usuario_id ', @ref_col_type, ' NULL'),
    'DO 0'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- Criar índice idx_usuario_id somente se não existir
SET @idx_exists := (
    SELECT COUNT(*) FROM information_schema.statistics
    WHERE table_schema = @schema
      AND table_name = 'maquina_registro'
      AND index_name = 'idx_usuario_id'
);
SET @sql := IF(@idx_exists = 0,
    'CREATE INDEX idx_usuario_id ON maquina_registro(usuario_id)',
    'DO 0'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- Adicionar FK para usuario(id) somente se não existir
SET @fk_exists := (
    SELECT COUNT(*)
    FROM information_schema.KEY_COLUMN_USAGE
    WHERE table_schema = @schema AND table_name = 'maquina_registro'
      AND column_name = 'usuario_id'
      AND referenced_table_name = 'usuario'
      AND referenced_column_name = 'id'
);

-- Garantir que não existam FKs conflitantes antes de adicionar (opcional)
-- Desabilitar checagem de FKs durante a criação para evitar erros transitórios
SET @OLD_FOREIGN_KEY_CHECKS := @@FOREIGN_KEY_CHECKS;
SET FOREIGN_KEY_CHECKS = 0;

SET @sql := IF(@fk_exists = 0,
    'ALTER TABLE maquina_registro ADD CONSTRAINT fk_maquina_registro_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id)',
    'DO 0'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;