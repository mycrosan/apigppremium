-- Migração simples para adicionar coluna usuario_id na tabela maquina_registro

-- Adicionar coluna usuario_id se não existir (MySQL 5.7+ syntax)
ALTER TABLE maquina_registro 
ADD COLUMN usuario_id BIGINT DEFAULT NULL;

-- Adicionar índice
CREATE INDEX idx_usuario_id ON maquina_registro(usuario_id);

-- Adicionar foreign key
ALTER TABLE maquina_registro 
ADD CONSTRAINT fk_maquina_registro_usuario 
FOREIGN KEY (usuario_id) REFERENCES usuario(id);