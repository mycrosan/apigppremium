-- Migração Baseline - Estrutura completa do banco de dados
-- Versão: 1.0.0
-- Data: 2025-01-27
-- Descrição: Baseline com todas as tabelas existentes e necessárias

-- Tabela antiquebra
CREATE TABLE IF NOT EXISTS `antiquebra` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Tabela camelback
CREATE TABLE IF NOT EXISTS `camelback` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Tabela pais
CREATE TABLE IF NOT EXISTS `pais` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Tabela marca
CREATE TABLE IF NOT EXISTS `marca` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Tabela modelo
CREATE TABLE IF NOT EXISTS `modelo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(45) NOT NULL,
  `marca_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_modelo_marca1_idx` (`marca_id`),
  CONSTRAINT `fk_modelo_marca1` FOREIGN KEY (`marca_id`) REFERENCES `marca` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Tabela medida
CREATE TABLE IF NOT EXISTS `medida` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Tabela matriz
CREATE TABLE IF NOT EXISTS `matriz` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Tabela perfil
CREATE TABLE IF NOT EXISTS `perfil` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Tabela usuario
CREATE TABLE IF NOT EXISTS `usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) DEFAULT NULL,
  `login` varchar(45) DEFAULT NULL,
  `senha` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Tabela usuario_perfil
CREATE TABLE IF NOT EXISTS `usuario_perfil` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `perfil_id` int(11) NOT NULL,
  `usuario_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_usuario_perfil_perfil1_idx` (`perfil_id`),
  KEY `fk_usuario_perfil_usuario1_idx` (`usuario_id`),
  CONSTRAINT `fk_usuario_perfil_perfil1` FOREIGN KEY (`perfil_id`) REFERENCES `perfil` (`id`),
  CONSTRAINT `fk_usuario_perfil_usuario1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Tabela status_carcaca
CREATE TABLE IF NOT EXISTS `status_carcaca` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Tabela carcaca
CREATE TABLE IF NOT EXISTS `carcaca` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `numero_etiqueta` varchar(45) NOT NULL,
  `dot` char(10) NOT NULL COMMENT 'DOT= Department of Transportation',
  `dados` json NOT NULL,
  `modelo_id` int(11) NOT NULL,
  `medida_id` int(11) NOT NULL,
  `pais_id` int(11) NOT NULL,
  `fotos` json DEFAULT NULL,
  `status_carcaca_id` int(11) DEFAULT NULL,
  `status` varchar(45) NOT NULL DEFAULT 'start',
  `dt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `dt_update` datetime DEFAULT NULL,
  `dt_delete` datetime DEFAULT NULL,
  `uuid` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_pneu_modelo1_idx` (`modelo_id`),
  KEY `fk_pneu_medida1_idx` (`medida_id`),
  KEY `fk_pneu_pais1_idx` (`pais_id`),
  KEY `fk_carcaca_status_carcaca1_idx` (`status_carcaca_id`),
  CONSTRAINT `fk_carcaca_status_carcaca1` FOREIGN KEY (`status_carcaca_id`) REFERENCES `status_carcaca` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_pneu_medida1` FOREIGN KEY (`medida_id`) REFERENCES `medida` (`id`),
  CONSTRAINT `fk_pneu_modelo1` FOREIGN KEY (`modelo_id`) REFERENCES `modelo` (`id`),
  CONSTRAINT `fk_pneu_pais1` FOREIGN KEY (`pais_id`) REFERENCES `pais` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Tabela regra
CREATE TABLE IF NOT EXISTS `regra` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(45) NOT NULL,
  `medida_id` int(11) NOT NULL,
  `camelback_id` int(11) NOT NULL,
  `antiquebra_id` int(11) NOT NULL,
  `espessuramento_id` int(11) NOT NULL,
  `correcao_id` int(11) NOT NULL,
  `matriz_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_regra_medida1_idx` (`medida_id`),
  KEY `fk_regra_camelback1_idx` (`camelback_id`),
  KEY `fk_regra_antiquebra1_idx` (`antiquebra_id`),
  KEY `fk_regra_espessuramento1_idx` (`espessuramento_id`),
  KEY `fk_regra_correcao1_idx` (`correcao_id`),
  KEY `fk_regra_matriz1_idx` (`matriz_id`),
  CONSTRAINT `fk_regra_antiquebra1` FOREIGN KEY (`antiquebra_id`) REFERENCES `antiquebra` (`id`),
  CONSTRAINT `fk_regra_camelback1` FOREIGN KEY (`camelback_id`) REFERENCES `camelback` (`id`),
  CONSTRAINT `fk_regra_correcao1` FOREIGN KEY (`correcao_id`) REFERENCES `correcao` (`id`),
  CONSTRAINT `fk_regra_espessuramento1` FOREIGN KEY (`espessuramento_id`) REFERENCES `espessuramento` (`id`),
  CONSTRAINT `fk_regra_matriz1` FOREIGN KEY (`matriz_id`) REFERENCES `matriz` (`id`),
  CONSTRAINT `fk_regra_medida1` FOREIGN KEY (`medida_id`) REFERENCES `medida` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Tabela producao
CREATE TABLE IF NOT EXISTS `producao` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `carcaca_id` int(11) NOT NULL,
  `medida_pneu_raspado` decimal(4,3) NOT NULL,
  `dados` json NOT NULL,
  `regra_id` int(11) NOT NULL,
  `fotos` json DEFAULT NULL,
  `dt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `dt_update` datetime DEFAULT NULL,
  `dt_delete` datetime DEFAULT NULL,
  `uuid` binary(16) DEFAULT NULL,
  `usuario_id` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `fk_producao_pneu1_idx` (`carcaca_id`),
  KEY `fk_producao_regra1_idx` (`regra_id`),
  KEY `fk_producao_usuario1_idx` (`usuario_id`),
  CONSTRAINT `fk_producao_pneu1` FOREIGN KEY (`carcaca_id`) REFERENCES `carcaca` (`id`),
  CONSTRAINT `fk_producao_regra1` FOREIGN KEY (`regra_id`) REFERENCES `regra` (`id`),
  CONSTRAINT `fk_producao_usuario1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Tabela tipo_classificacao
CREATE TABLE IF NOT EXISTS `tipo_classificacao` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Tabela tipo_observacao
CREATE TABLE IF NOT EXISTS `tipo_observacao` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Tabela controle_qualidade
CREATE TABLE IF NOT EXISTS `controle_qualidade` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `producao_id` int(11) NOT NULL,
  `observacao` varchar(45) DEFAULT NULL,
  `tipo_classificacao_id` int(11) NOT NULL,
  `fotos` json NOT NULL,
  `tipo_observacao_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_controle_qualidade_producao1_idx` (`producao_id`),
  KEY `fk_controle_qualidade_tipo_classificacao1_idx` (`tipo_classificacao_id`),
  KEY `fk_controle_qualidade_tipo_observacao1_idx` (`tipo_observacao_id`),
  CONSTRAINT `fk_controle_qualidade_producao1` FOREIGN KEY (`producao_id`) REFERENCES `producao` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Tabela correcao
CREATE TABLE IF NOT EXISTS `correcao` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `peso_antes` json NOT NULL,
  `peso_depois` json NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Tabela credenciados
CREATE TABLE IF NOT EXISTS `credenciados` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Tabela espessuramento
CREATE TABLE IF NOT EXISTS `espessuramento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Tabela maquina_registro (nova tabela necessária)
CREATE TABLE IF NOT EXISTS `maquina_registro` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `nome` VARCHAR(255) NOT NULL,
    `descricao` TEXT,
    `usuario_id` BIGINT,
    `status` ENUM('ATIVA', 'INATIVA', 'MANUTENCAO') NOT NULL DEFAULT 'ATIVA',
    `ip_address` VARCHAR(45),
    `localizacao` VARCHAR(255),
    `dt_create` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `dt_update` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    `dt_delete` DATETIME DEFAULT NULL,
    PRIMARY KEY (`id`),
    INDEX `idx_device_id` (`device_id`),
    INDEX `idx_status` (`status`),
    INDEX `idx_matriz_id` (`matriz_id`),
    INDEX `idx_usuario_id` (`usuario_id`),
    INDEX `idx_dt_delete` (`dt_delete`),
    CONSTRAINT `fk_maquina_registro_matriz` 
        FOREIGN KEY (`matriz_id`) REFERENCES `matriz` (`id`) 
        ON DELETE SET NULL ON UPDATE CASCADE,
    CONSTRAINT `fk_maquina_registro_usuario` 
        FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`) 
        ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela maquina_configuracao (nova tabela necessária)
CREATE TABLE IF NOT EXISTS `maquina_configuracao` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `registro_maquina_id` BIGINT NOT NULL,
    `nome_configuracao` VARCHAR(255) NOT NULL,
    `tipo_configuracao` ENUM('SISTEMA', 'PRODUCAO', 'QUALIDADE', 'MANUTENCAO', 'PERSONALIZADA') NOT NULL,
    `chave_configuracao` VARCHAR(255) NOT NULL,
    `valor_configuracao` TEXT NOT NULL,
    `descricao` TEXT,
    `ativo` BOOLEAN NOT NULL DEFAULT TRUE,
    `usuario_criacao_id` BIGINT,
    `usuario_atualizacao_id` BIGINT,
    `dt_create` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `dt_update` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    `dt_delete` DATETIME DEFAULT NULL,
    PRIMARY KEY (`id`),
    INDEX `idx_registro_maquina_id` (`registro_maquina_id`),
    INDEX `idx_tipo_configuracao` (`tipo_configuracao`),
    INDEX `idx_chave_configuracao` (`chave_configuracao`),
    INDEX `idx_ativo` (`ativo`),
    INDEX `idx_dt_delete` (`dt_delete`),
    UNIQUE KEY `uk_maquina_chave` (`registro_maquina_id`, `chave_configuracao`, `dt_delete`),
    CONSTRAINT `fk_maquina_configuracao_registro` 
        FOREIGN KEY (`registro_maquina_id`) REFERENCES `maquina_registro` (`id`) 
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `fk_maquina_configuracao_usuario_criacao` 
        FOREIGN KEY (`usuario_criacao_id`) REFERENCES `usuario` (`id`) 
        ON DELETE SET NULL ON UPDATE CASCADE,
    CONSTRAINT `fk_maquina_configuracao_usuario_atualizacao` 
        FOREIGN KEY (`usuario_atualizacao_id`) REFERENCES `usuario` (`id`) 
        ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;