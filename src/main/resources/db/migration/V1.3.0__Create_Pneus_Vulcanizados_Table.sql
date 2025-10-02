-- Criação da tabela para controle de pneus vulcanizados
CREATE TABLE pneus_vulcanizados (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    producao_id BIGINT NOT NULL,
    status ENUM('INICIADO', 'FINALIZADO') NOT NULL DEFAULT 'INICIADO',
    dt_create DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    dt_update DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    dt_delete DATETIME DEFAULT NULL,
    
    -- Índices para melhor performance
    INDEX idx_usuario_id (usuario_id),
    INDEX idx_producao_id (producao_id),
    INDEX idx_status (status),
    INDEX idx_dt_create (dt_create),
    INDEX idx_dt_delete (dt_delete),
    
    -- Chaves estrangeiras (assumindo que existem tabelas usuario e producao)
    CONSTRAINT fk_pneus_vulcanizados_usuario 
        FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    CONSTRAINT fk_pneus_vulcanizados_producao 
        FOREIGN KEY (producao_id) REFERENCES producao(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Comentários para documentação
ALTER TABLE pneus_vulcanizados 
    COMMENT = 'Tabela para controle de pneus vulcanizados por usuário e produção';

ALTER TABLE pneus_vulcanizados 
    MODIFY COLUMN id BIGINT AUTO_INCREMENT COMMENT 'Identificador único do registro',
    MODIFY COLUMN usuario_id BIGINT NOT NULL COMMENT 'ID do usuário responsável pela vulcanização',
    MODIFY COLUMN producao_id BIGINT NOT NULL COMMENT 'ID da produção relacionada',
    MODIFY COLUMN status ENUM('INICIADO', 'FINALIZADO') NOT NULL DEFAULT 'INICIADO' 
        COMMENT 'Status da vulcanização: INICIADO (padrão) ou FINALIZADO',
    MODIFY COLUMN dt_create DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP 
        COMMENT 'Data e hora de criação do registro',
    MODIFY COLUMN dt_update DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP 
        COMMENT 'Data e hora da última atualização',
    MODIFY COLUMN dt_delete DATETIME DEFAULT NULL 
        COMMENT 'Data e hora de exclusão lógica (soft delete)';