-- Migration V1.2.0: Remove unique constraint on celular_id to allow multiple configurations per mobile device
-- This enables the new functionality where a mobile device can have multiple matrix configurations
-- with the latest one being the active configuration

-- Remove the unique constraint on celular_id
ALTER TABLE maquina_configuracao DROP INDEX uq_celular;

-- Add index for better performance when querying by celular_id and dt_create
CREATE INDEX idx_celular_dt_create ON maquina_configuracao (celular_id, dt_create DESC);

-- Add comment to clarify the new behavior
ALTER TABLE maquina_configuracao COMMENT = 'Configurações de máquina por celular - permite múltiplas configurações, sendo a mais recente a ativa';