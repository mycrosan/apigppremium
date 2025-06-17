ALTER TABLE controle_qualidade
DROP FOREIGN KEY fk_controle_qualidade_tipo_classificacao1,
DROP FOREIGN KEY fk_controle_qualidade_tipo_observacao1;

ALTER TABLE carcaca
ADD COLUMN status_carcaca_id INT(11) NULL DEFAULT NULL AFTER pais_id,
ADD INDEX fk_carcaca_status_carcaca1_idx (status_carcaca_id ASC) VISIBLE;
;

ALTER TABLE controle_qualidade
CHANGE COLUMN tipo_classificacao_id tipo_classificacao_id INT(11) NOT NULL COMMENT 'NET\nLOJA\nLOJA C\nD\nESCARIAR\nDESCARTE' ,
CHANGE COLUMN tipo_observacao_id tipo_observacao_id INT(11) NOT NULL COMMENT 'BOLHA POR DENTRO\nFALHA NA ANTIQUEBRA\nTALAO FROUXO\nBOLHA EXTERNA QDO ESTAVA QUENTE' ;

CREATE TABLE IF NOT EXISTS status_carcaca (
  id INT(11) NOT NULL AUTO_INCREMENT,
  descricao VARCHAR(45) NOT NULL,
  PRIMARY KEY (id))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

ALTER TABLE carcaca
ADD CONSTRAINT fk_carcaca_status_carcaca1
  FOREIGN KEY (status_carcaca_id)
  REFERENCES status_carcaca (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE controle_qualidade
ADD CONSTRAINT fk_controle_qualidade_tipo_classificacao1
  FOREIGN KEY (tipo_classificacao_id)
  REFERENCES tipo_classificacao (id),
ADD CONSTRAINT fk_controle_qualidade_tipo_observacao1
  FOREIGN KEY (tipo_observacao_id)
  REFERENCES tipo_observacao (id);