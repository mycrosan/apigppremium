-- Ajustes na tabela sonoff para serviço Rele
-- Remove coluna 'nome', troca 'maquina' por FK, adiciona 'celular_id'

ALTER TABLE sonoff
  DROP COLUMN nome,
  DROP COLUMN maquina,
  ADD COLUMN celular_id VARCHAR(100) NOT NULL AFTER ip,
  ADD COLUMN maquina_id BIGINT NOT NULL AFTER celular_id,
  ADD CONSTRAINT fk_sonoff_maquina FOREIGN KEY (maquina_id) REFERENCES maquina_registro (id);