-- MySQL dump 10.13  Distrib 8.0.26, for macos11 (x86_64)
--
-- Host: localhost    Database: gppremium
-- ------------------------------------------------------
-- Server version	8.0.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `antiquebra`
--

DROP TABLE IF EXISTS `antiquebra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `antiquebra` (
  `id` int NOT NULL AUTO_INCREMENT,
  `descricao` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `antiquebra`
--

LOCK TABLES `antiquebra` WRITE;
/*!40000 ALTER TABLE `antiquebra` DISABLE KEYS */;
INSERT INTO `antiquebra` VALUES (2,'antiquebra2');
/*!40000 ALTER TABLE `antiquebra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `camelback`
--

DROP TABLE IF EXISTS `camelback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `camelback` (
  `id` int NOT NULL AUTO_INCREMENT,
  `descricao` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `camelback`
--

LOCK TABLES `camelback` WRITE;
/*!40000 ALTER TABLE `camelback` DISABLE KEYS */;
INSERT INTO `camelback` VALUES (1,'42.62.08'),(2,'44.66.08'),(3,'50.66.09'),(4,'50.70.08'),(5,'50.70.09'),(6,'50.70.10'),(7,'52.72.08'),(8,'54.74.08'),(9,'54.74.09'),(10,'54.74.10'),(11,'56.76.08'),(12,'56.76.10'),(13,'60.74.09'),(14,'66.76.08'),(15,'66.76.10'),(16,'64.80.09'),(17,'64.84.08'),(18,'70.84.10'),(19,'70.90.08'),(20,'70.90.10'),(21,'70.90.12'),(22,'74.90.10'),(23,'80.90.12'),(24,'80.110.12'),(25,'90.120.12');
/*!40000 ALTER TABLE `camelback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `carcaca`
--

DROP TABLE IF EXISTS `carcaca`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `carcaca` (
  `id` int NOT NULL AUTO_INCREMENT,
  `numero_etiqueta` varchar(45) NOT NULL,
  `dot` char(10) NOT NULL COMMENT 'DOT= Department of Transportation, informa que o pneu está em conformidade com os regulamentos DOT dos Estados Unidos. O código começa com as letras DOT seguido por duas letras e o número que representa a fábrica onde foi produzido.',
  `dados` json NOT NULL,
  `modelo_id` int NOT NULL,
  `medida_id` int NOT NULL,
  `pais_id` int NOT NULL,
  `fotos` json DEFAULT NULL,
  `status` varchar(45) NOT NULL DEFAULT 'start',
  `dt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `dt_update` datetime DEFAULT NULL,
  `dt_delete` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_pneu_modelo1_idx` (`modelo_id`),
  KEY `fk_pneu_medida1_idx` (`medida_id`),
  KEY `fk_pneu_pais1_idx` (`pais_id`),
  CONSTRAINT `fk_pneu_medida1` FOREIGN KEY (`medida_id`) REFERENCES `medida` (`id`),
  CONSTRAINT `fk_pneu_modelo1` FOREIGN KEY (`modelo_id`) REFERENCES `modelo` (`id`),
  CONSTRAINT `fk_pneu_pais1` FOREIGN KEY (`pais_id`) REFERENCES `pais` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carcaca`
--

LOCK TABLES `carcaca` WRITE;
/*!40000 ALTER TABLE `carcaca` DISABLE KEYS */;
INSERT INTO `carcaca` VALUES (7,'123456','1234','null',1,1,1,'null','start','2021-10-07 22:53:36',NULL,NULL);
/*!40000 ALTER TABLE `carcaca` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `controle_qualidade`
--

DROP TABLE IF EXISTS `controle_qualidade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `controle_qualidade` (
  `id` int NOT NULL AUTO_INCREMENT,
  `producao_id` int NOT NULL,
  `observacao` varchar(45) DEFAULT NULL,
  `tipo_classificacao_id` int NOT NULL,
  `fotos` json NOT NULL,
  `tipo_observacao_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_controle_qualidade_producao1_idx` (`producao_id`),
  KEY `fk_controle_qualidade_tipo_classificacao1_idx` (`tipo_classificacao_id`),
  KEY `fk_controle_qualidade_tipo_observacao1_idx` (`tipo_observacao_id`),
  CONSTRAINT `fk_controle_qualidade_producao1` FOREIGN KEY (`producao_id`) REFERENCES `producao` (`id`),
  CONSTRAINT `fk_controle_qualidade_tipo_classificacao1` FOREIGN KEY (`tipo_classificacao_id`) REFERENCES `tipo_classificacao` (`id`),
  CONSTRAINT `fk_controle_qualidade_tipo_observacao1` FOREIGN KEY (`tipo_observacao_id`) REFERENCES `tipo_observacao` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `controle_qualidade`
--

LOCK TABLES `controle_qualidade` WRITE;
/*!40000 ALTER TABLE `controle_qualidade` DISABLE KEYS */;
/*!40000 ALTER TABLE `controle_qualidade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `correcao`
--

DROP TABLE IF EXISTS `correcao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `correcao` (
  `id` int NOT NULL AUTO_INCREMENT,
  `peso_antes` json NOT NULL,
  `peso_depois` json NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `correcao`
--

LOCK TABLES `correcao` WRITE;
/*!40000 ALTER TABLE `correcao` DISABLE KEYS */;
/*!40000 ALTER TABLE `correcao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `credenciados`
--

DROP TABLE IF EXISTS `credenciados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `credenciados` (
  `id` int NOT NULL AUTO_INCREMENT,
  `contrato_social` varchar(45) NOT NULL,
  `fotos` json NOT NULL,
  `cnpj` char(14) NOT NULL,
  `nm_fantasia` varchar(45) NOT NULL,
  `nm_gerente_loja` varchar(45) NOT NULL,
  `celular_gerente_loja` varchar(45) NOT NULL,
  `email_loja` varchar(45) NOT NULL,
  `telefone_loja` varchar(45) DEFAULT NULL,
  `site` varchar(45) DEFAULT NULL,
  `logradouro` varchar(45) DEFAULT NULL,
  `numero` varchar(45) DEFAULT NULL,
  `complemento` varchar(45) DEFAULT NULL,
  `bairro` varchar(45) DEFAULT NULL,
  `cidade` varchar(45) DEFAULT NULL,
  `uf` varchar(45) DEFAULT NULL,
  `cep` char(8) DEFAULT NULL,
  `lat` varchar(45) DEFAULT NULL,
  `log` varchar(45) DEFAULT NULL,
  `status` varchar(10) NOT NULL DEFAULT 'PENDENTE',
  `dados_adicionais` json DEFAULT NULL,
  `dt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `dt_update` datetime DEFAULT NULL,
  `dt_delete` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `credenciados`
--

LOCK TABLES `credenciados` WRITE;
/*!40000 ALTER TABLE `credenciados` DISABLE KEYS */;
INSERT INTO `credenciados` VALUES (1,'exemplo-de-pdf.pdf','\"[\\\"e910fed7-909b-4c52-b7b5-1c94ce79375f.jpeg\\\",\\\"b27755ff-196e-42d9-9621-3ecf277ff16c.jpeg\\\",\\\"98b632db-34a4-4c1d-a48f-3a6d46148893.pdf\\\"]\"','15094205000150','SANDY S SANTOS','SANDY S SANTOS','(12) 34123-4312','sandysantosjo316@gmail.com','(61) 99668-9482','asdfadsfads','Rua 05','17','Q29 lote 17 ','Parque Estrela Dalva VII','Luziânia','GO','72830060',NULL,NULL,'pendente','\"\\\"\\\\\\\"{\\\\\\\\\\\\\\\"quantidade_boxes\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"4\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"monta_pneu_aro\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"17\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"altura_maxima_veiculo\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"4 metros\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"executa_concerto_roda\\\\\\\\\\\\\\\":true,\\\\\\\\\\\\\\\"executa_montagem_pneu_blindado\\\\\\\\\\\\\\\":true,\\\\\\\\\\\\\\\"executa_montagem_pneus_runflat\\\\\\\\\\\\\\\":true,\\\\\\\\\\\\\\\"horario_atendimento\\\\\\\\\\\\\\\":{\\\\\\\\\\\\\\\"h_inicio_seg_sex\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"12:00\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"h_fim_seg_sex\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"12:00\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"h_inicio_sabado\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"12:00\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"h_fim_sabado\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"12:00\\\\\\\\\\\\\\\"}}\\\\\\\"\\\"\"','2021-11-11 18:01:55',NULL,NULL),(2,'exemplo-de-pdf.pdf','\"[\\\"0c26e379-700a-40dc-802c-f83e45218db6.jpeg\\\",\\\"af9666ac-64b8-46f1-8882-23f0b294eb63.jpeg\\\",\\\"353789a2-30c3-47b5-936e-01d89f3e1590.pdf\\\"]\"','15094205000150','SANDY S SANTOS','SANDY S SANTOS','(32) 31234-1234','sandysantosjo316@gmail.com','(61) 99668-9482','asdfasdfasdfas','Rua 05','17','Q29 lote 17 ','Parque Estrela Dalva VII','Luziânia','GO','72830060',NULL,NULL,'pendente','\"{\\\"quantidade_boxes\\\":\\\"4\\\",\\\"monta_pneu_aro\\\":\\\"16\\\",\\\"altura_maxima_veiculo\\\":\\\"5 metros\\\",\\\"executa_concerto_roda\\\":true,\\\"executa_montagem_pneu_blindado\\\":true,\\\"executa_montagem_pneus_runflat\\\":true,\\\"horario_atendimento\\\":{\\\"h_inicio_seg_sex\\\":\\\"12:00\\\",\\\"h_fim_seg_sex\\\":\\\"12:00\\\",\\\"h_inicio_sabado\\\":\\\"12:00\\\",\\\"h_fim_sabado\\\":\\\"12:00\\\"}}\"','2021-11-11 18:14:17',NULL,NULL),(3,'exemplo-de-pdf.pdf','\"[\\\"220021ac-5b03-4101-9e26-fe1072b569ca.jpeg\\\",\\\"f085e81e-1f5d-4dc0-97dd-688436f68095.jpeg\\\",\\\"b6d7105f-d0b4-452b-accb-70ffae430c59.pdf\\\"]\"','15094205000150','SANDY S SANTOS','SANDY S SANTOS','(32) 41234-1234','sandysantosjo316@gmail.com','(61) 99668-9482','asdfasdfasdf','Rua 05','17','Q29 lote 17 ','Parque Estrela Dalva VII','Luziânia','GO','72830060',NULL,NULL,'pendente','\"{\\\"quantidade_boxes\\\":\\\"4\\\",\\\"monta_pneu_aro\\\":\\\"16\\\",\\\"altura_maxima_veiculo\\\":\\\"4 metros\\\",\\\"executa_concerto_roda\\\":true,\\\"executa_montagem_pneu_blindado\\\":true,\\\"executa_montagem_pneus_runflat\\\":true,\\\"horario_atendimento\\\":{\\\"h_inicio_seg_sex\\\":\\\"12:00\\\",\\\"h_fim_seg_sex\\\":\\\"12:00\\\",\\\"h_inicio_sabado\\\":\\\"12:00\\\",\\\"h_fim_sabado\\\":\\\"12:00\\\"}}\"','2021-11-11 18:41:01',NULL,NULL),(4,'exemplo-de-pdf.pdf','\"[]\"','15094205000150','SANDY S SANTOS','SANDY S SANTOS','(12) 32131-2312','sandysantosjo316@gmail.com','(61) 99668-9482','asfasdfasd','Rua 05','17','Q29 lote 17 ','Parque Estrela Dalva VII','Luziânia','GO','72830060',NULL,NULL,'pendente','\"{\\\"quantidade_boxes\\\":\\\"4\\\",\\\"monta_pneu_aro\\\":\\\"15\\\",\\\"altura_maxima_veiculo\\\":\\\"4 metros\\\",\\\"executa_concerto_roda\\\":true,\\\"executa_montagem_pneu_blindado\\\":true,\\\"executa_montagem_pneus_runflat\\\":true,\\\"horario_atendimento\\\":{\\\"h_inicio_seg_sex\\\":\\\"12:00\\\",\\\"h_fim_seg_sex\\\":\\\"12:00\\\",\\\"h_inicio_sabado\\\":\\\"12:00\\\",\\\"h_fim_sabado\\\":\\\"12:00\\\"}}\"','2021-11-16 09:33:21',NULL,NULL),(5,'exemplo-de-pdf.pdf','\"[\\\"dffd725d-67ac-4252-8b7f-4ffec5dee3c4.jpeg\\\",\\\"c5a2349c-71a6-46bf-ab63-ac8e0229e655.jpeg\\\",\\\"346083b6-6b4a-4138-a7ac-189a47d4ec01.pdf\\\"]\"','15094205000150','SANDY S SANTOS','SANDY S SANTOS','(32) 4234-123','sandysantosjo316@gmail.com','(61) 99668-9482','','Rua 05','17','Q29 lote 17 ','Parque Estrela Dalva VII','Luziânia','GO','72830060',NULL,NULL,'pendente','\"{\\\"quantidade_boxes\\\":\\\"4\\\",\\\"monta_pneu_aro\\\":\\\"16\\\",\\\"altura_maxima_veiculo\\\":\\\"4 metros\\\",\\\"executa_concerto_roda\\\":true,\\\"executa_montagem_pneu_blindado\\\":true,\\\"executa_montagem_pneus_runflat\\\":true,\\\"horario_atendimento\\\":{\\\"h_inicio_seg_sex\\\":\\\"12:00\\\",\\\"h_fim_seg_sex\\\":\\\"12:00\\\",\\\"h_inicio_sabado\\\":\\\"12:00\\\",\\\"h_fim_sabado\\\":\\\"12:00\\\"}}\"','2021-11-17 23:25:13',NULL,NULL),(6,'exemplo-de-pdf.pdf','\"[\\\"9440e1ab-0e12-4994-94b6-e343d117319a.jpeg\\\",\\\"23283cab-a6f2-4e09-955d-eb756a99065e.jpeg\\\",\\\"dcb14867-9891-4128-b985-57ea06513500.pdf\\\"]\"','15094205000150','SANDY S SANTOS','SANDY S SANTOS','(23) 41234-1234','sandysantosjo316@gmail.com','(61) 99668-9482','asdfasdfasdf','Rua 05','17','Q29 lote 17 ','Parque Estrela Dalva VII','Luziânia','GO','72830060',NULL,NULL,'pendente','\"{\\\"quantidade_boxes\\\":\\\"3\\\",\\\"monta_pneu_aro\\\":\\\"16\\\",\\\"altura_maxima_veiculo\\\":\\\"4 metros\\\",\\\"executa_concerto_roda\\\":true,\\\"executa_montagem_pneu_blindado\\\":true,\\\"executa_montagem_pneus_runflat\\\":true,\\\"horario_atendimento\\\":{\\\"h_inicio_seg_sex\\\":\\\"12:00\\\",\\\"h_fim_seg_sex\\\":\\\"12:00\\\",\\\"h_inicio_sabado\\\":\\\"12:00\\\",\\\"h_fim_sabado\\\":\\\"12:00\\\"}}\"','2021-11-19 23:08:21',NULL,NULL),(7,'exemplo-de-pdf.pdf','\"[\\\"1da9d9ba-dd74-40ef-b421-bce96966508b.jpeg\\\",\\\"f73c05cc-66ad-439c-ae8c-8c9c85807094.jpeg\\\",\\\"2a9763b9-9402-4fab-a471-5cc6a7c25084.pdf\\\"]\"','15094205000150','SANDY S SANTOS','SANDY S SANTOS','(23) 41234-1234','sandysantosjo316@gmail.com','(61) 99668-9482','asdfasdfasdf','Rua 05','17','Q29 lote 17 ','Parque Estrela Dalva VII','Luziânia','GO','72830060',NULL,NULL,'pendente','\"{\\\"quantidade_boxes\\\":\\\"3\\\",\\\"monta_pneu_aro\\\":\\\"15\\\",\\\"altura_maxima_veiculo\\\":\\\"3 metros\\\",\\\"executa_concerto_roda\\\":true,\\\"executa_montagem_pneu_blindado\\\":true,\\\"executa_montagem_pneus_runflat\\\":true,\\\"horario_atendimento\\\":{\\\"h_inicio_seg_sex\\\":\\\"12:00\\\",\\\"h_fim_seg_sex\\\":\\\"12:00\\\",\\\"h_inicio_sabado\\\":\\\"12:00\\\",\\\"h_fim_sabado\\\":\\\"12:00\\\"}}\"','2021-11-20 15:25:44',NULL,NULL),(8,'01 - Prova - Introdução Bíblica.pdf','\"[]\"','15094205000150','SANDY S SANTOS','SANDY S SANTOS','(34) 1234-1234','sandysantosjo316@gmail.com','(61) 99668-9482','','Rua 05','17','Q29 lote 17 ','Parque Estrela Dalva VII','Luziânia','GO','72830060',NULL,NULL,'pendente','\"{\\\"quantidade_boxes\\\":\\\"3\\\",\\\"monta_pneu_aro\\\":\\\"16\\\",\\\"altura_maxima_veiculo\\\":\\\"3 metros\\\",\\\"executa_concerto_roda\\\":true,\\\"executa_montagem_pneu_blindado\\\":true,\\\"executa_montagem_pneus_runflat\\\":true,\\\"horario_atendimento\\\":{\\\"h_inicio_seg_sex\\\":\\\"12:00\\\",\\\"h_fim_seg_sex\\\":\\\"12:00\\\",\\\"h_inicio_sabado\\\":\\\"12:00\\\",\\\"h_fim_sabado\\\":\\\"12:00\\\"}}\"','2021-12-02 17:11:27',NULL,NULL),(9,'exemplo-de-pdf.pdf','\"[]\"','15094205000150','SANDY S SANTOS','SANDY S SANTOS','(34) 54353-4524','sandysantosjo316@gmail.com','(61) 99668-9482','sdfasdfasdfasdf','Rua 05','17','Q29 lote 17 ','Parque Estrela Dalva VII','Luziânia','GO','72830060',NULL,NULL,'pendente','\"{\\\"quantidade_boxes\\\":\\\"2\\\",\\\"monta_pneu_aro\\\":\\\"15\\\",\\\"altura_maxima_veiculo\\\":\\\"4 metros\\\",\\\"executa_concerto_roda\\\":true,\\\"executa_montagem_pneu_blindado\\\":true,\\\"executa_montagem_pneus_runflat\\\":true,\\\"horario_atendimento\\\":{\\\"h_inicio_seg_sex\\\":\\\"12:00\\\",\\\"h_fim_seg_sex\\\":\\\"12:00\\\",\\\"h_inicio_sabado\\\":\\\"12:00\\\",\\\"h_fim_sabado\\\":\\\"12:00\\\"}}\"','2021-12-02 17:13:45',NULL,NULL),(10,'exemplo-de-pdf.pdf','\"[]\"','15094205000150','SANDY S SANTOS','SANDY S SANTOS','(34) 23413-2413','sandysantosjo316@gmail.com','(61) 99668-9482','efsdfsdfasdf','Rua 05','17','Q29 lote 17 ','Parque Estrela Dalva VII','Luziânia','GO','72830060',NULL,NULL,'pendente','\"{\\\"quantidade_boxes\\\":\\\"3\\\",\\\"monta_pneu_aro\\\":\\\"14\\\",\\\"altura_maxima_veiculo\\\":\\\"3 metros\\\",\\\"executa_concerto_roda\\\":true,\\\"executa_montagem_pneu_blindado\\\":true,\\\"executa_montagem_pneus_runflat\\\":true,\\\"horario_atendimento\\\":{\\\"h_inicio_seg_sex\\\":\\\"12:00\\\",\\\"h_fim_seg_sex\\\":\\\"12:00\\\",\\\"h_inicio_sabado\\\":\\\"12:00\\\",\\\"h_fim_sabado\\\":\\\"12:00\\\"}}\"','2021-12-02 17:19:56',NULL,NULL),(11,'exemplo-de-pdf.pdf','\"[]\"','15094205000150','SANDY S SANTOS','SANDY S SANTOS','(23) 12312-3123','sandysantosjo316@gmail.com','(61) 99668-9482','fasdfasdfasdfasd','Rua 05','17','Q29 lote 17 ','Parque Estrela Dalva VII','Luziânia','GO','72830060',NULL,NULL,'pendente','\"{\\\"quantidade_boxes\\\":\\\"3\\\",\\\"monta_pneu_aro\\\":\\\"15\\\",\\\"altura_maxima_veiculo\\\":\\\"4 metros\\\",\\\"executa_concerto_roda\\\":true,\\\"executa_montagem_pneu_blindado\\\":true,\\\"executa_montagem_pneus_runflat\\\":true,\\\"horario_atendimento\\\":{\\\"h_inicio_seg_sex\\\":\\\"12:00\\\",\\\"h_fim_seg_sex\\\":\\\"12:00\\\",\\\"h_inicio_sabado\\\":\\\"12:00\\\",\\\"h_fim_sabado\\\":\\\"12:00\\\"}}\"','2021-12-02 23:37:02',NULL,NULL),(12,'exemplo-de-pdf.pdf','\"[\\\"71df11d1-512d-4932-b8e2-f079168b6321.jpeg\\\",\\\"9666d740-f3a5-45f3-abc0-2e749242d50b.jpeg\\\",\\\"62543e38-3858-46f3-95cb-1af360e15fdf.pdf\\\"]\"','15094205000150','SANDY S SANTOS','SANDY S SANTOS','(23) 12312-3123','sandysantosjo316@gmail.com','(61) 99668-9482','fasdfasdfasdfasd','Rua 05','17','Q29 lote 17 ','Parque Estrela Dalva VII','Luziânia','GO','72830060',NULL,NULL,'pendente','\"\\\"{\\\\\\\"quantidade_boxes\\\\\\\":\\\\\\\"3\\\\\\\",\\\\\\\"monta_pneu_aro\\\\\\\":\\\\\\\"15\\\\\\\",\\\\\\\"altura_maxima_veiculo\\\\\\\":\\\\\\\"4 metros\\\\\\\",\\\\\\\"executa_concerto_roda\\\\\\\":true,\\\\\\\"executa_montagem_pneu_blindado\\\\\\\":true,\\\\\\\"executa_montagem_pneus_runflat\\\\\\\":true,\\\\\\\"horario_atendimento\\\\\\\":{\\\\\\\"h_inicio_seg_sex\\\\\\\":\\\\\\\"12:00\\\\\\\",\\\\\\\"h_fim_seg_sex\\\\\\\":\\\\\\\"12:00\\\\\\\",\\\\\\\"h_inicio_sabado\\\\\\\":\\\\\\\"12:00\\\\\\\",\\\\\\\"h_fim_sabado\\\\\\\":\\\\\\\"12:00\\\\\\\"}}\\\"\"','2021-12-02 23:41:12',NULL,NULL),(13,'exemplo-de-pdf.pdf','\"[\\\"ab1ac58a-a827-43a3-a255-0bd580de4b51.jpeg\\\",\\\"9cbd5eb9-d302-4a38-9dba-3a1e1d3e6470.jpeg\\\",\\\"084cadc9-c0ac-491f-a1cb-aecd7e067858.pdf\\\"]\"','15094205000150','SANDY S SANTOS','SANDY S SANTOS','(32) 44134-1234','sandysantosjo316@gmail.com','(61) 99668-9482','asdfasdfasdfad','Rua 05','17','Q29 lote 17 ','Parque Estrela Dalva VII','Luziânia','GO','72830060',NULL,NULL,'pendente','\"{\\\"quantidade_boxes\\\":\\\"3\\\",\\\"monta_pneu_aro\\\":\\\"16\\\",\\\"altura_maxima_veiculo\\\":\\\"3 metros\\\",\\\"executa_concerto_roda\\\":true,\\\"executa_montagem_pneu_blindado\\\":true,\\\"executa_montagem_pneus_runflat\\\":true,\\\"horario_atendimento\\\":{\\\"h_inicio_seg_sex\\\":\\\"12:00\\\",\\\"h_fim_seg_sex\\\":\\\"12:00\\\",\\\"h_inicio_sabado\\\":\\\"12:00\\\",\\\"h_fim_sabado\\\":\\\"12:00\\\"}}\"','2021-12-04 12:03:58',NULL,NULL);
/*!40000 ALTER TABLE `credenciados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `espessuramento`
--

DROP TABLE IF EXISTS `espessuramento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `espessuramento` (
  `id` int NOT NULL AUTO_INCREMENT,
  `descricao` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `espessuramento`
--

LOCK TABLES `espessuramento` WRITE;
/*!40000 ALTER TABLE `espessuramento` DISABLE KEYS */;
INSERT INTO `espessuramento` VALUES (1,'espessuramento');
/*!40000 ALTER TABLE `espessuramento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `marca`
--

DROP TABLE IF EXISTS `marca`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `marca` (
  `id` int NOT NULL AUTO_INCREMENT,
  `descricao` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `marca`
--

LOCK TABLES `marca` WRITE;
/*!40000 ALTER TABLE `marca` DISABLE KEYS */;
INSERT INTO `marca` VALUES (1,'ACHILES'),(2,'AELUS'),(3,'APOLLO'),(4,'BARUM'),(5,'BF GOODRICH'),(6,'BOLEX'),(7,'BRIDGESTONE'),(8,'CONTINENTAL'),(9,'COOPER'),(10,'COSTANCY'),(11,'DUNLOP'),(12,'EUROMETRIC'),(13,'EUZKADI'),(14,'FATE'),(15,'FEDERAL'),(16,'FIRESTONE'),(17,'FORMULA'),(18,'FUZION'),(19,'GENERAL'),(20,'GO FORM'),(21,'GOODRIDE'),(22,'GOODYEAR'),(23,'GT RADIAL'),(24,'HANKOOK'),(25,'HIFLY'),(26,'JK TYRE'),(27,'KELLY'),(28,'KENDA '),(29,'KUMHO'),(30,'LANDSAIL'),(31,'LING'),(32,'MASTER'),(33,'MAXXIS'),(34,'MICHELIN'),(35,'MINNEL'),(36,'NEXEN'),(37,'PIRELLI'),(38,'PREMIORRI'),(39,'PRIMEWHEEL'),(40,'ROSAVA'),(41,'SAILUN'),(42,'SEIBERLING'),(43,'SEMPERITE'),(44,'SUMITOMO'),(45,'SUNNY'),(46,'TORNADO'),(47,'TORNEL'),(48,'TOYO'),(49,'TRIANGLE'),(50,'TYRE'),(51,'WANLI'),(52,'WEST'),(53,'YOKOHAMA');
/*!40000 ALTER TABLE `marca` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `matriz`
--

DROP TABLE IF EXISTS `matriz`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `matriz` (
  `id` int NOT NULL AUTO_INCREMENT,
  `descricao` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `matriz`
--

LOCK TABLES `matriz` WRITE;
/*!40000 ALTER TABLE `matriz` DISABLE KEYS */;
INSERT INTO `matriz` VALUES (1,'165/70 R13 GP-7'),(2,'175/70 R13 GP-1'),(3,'175/70 R13 GP-7'),(4,'175/70 R13 GP-400'),(5,'165/70 R14 GP-7'),(6,'175/65 R14 GP-1'),(7,'175/65 R14 GP-7'),(8,'175/70 R14 GP-1'),(9,'175/70 R14 GP-7'),(10,'175/70 R14 ATR'),(11,'185/60 R14 GP-1'),(12,'185/65 R14 GP-1'),(13,'185/65 R14 GP-7'),(14,'185/70 R14 GP-1'),(15,'185/70 R14 GP-7'),(16,'185/60 R15 GP-1'),(17,'185/65 R15 GP-1'),(18,'195/55 R15 GP-1'),(19,'195/55 R15 GP-7'),(20,'195/60 R15 GP-1'),(21,'195/60 R15 GP-7'),(22,'195/65 R15 GP-1'),(23,'195/65 R15 GP-7'),(24,'205/60 R15 GP-1'),(25,'205/60 R15 ATR'),(26,'205/65 R15 ATR'),(27,'205/70 R15 A/T'),(28,'205/70 R15 ATR'),(29,'195/55 R16 GP-7'),(30,'205/55 R16 GP-1'),(31,'205/55 R16 GP-4'),(32,'205/60 R16 ATR'),(33,'215/50 R17 GP-4'),(34,'215/55 R17 GP-4'),(35,'225/45 R17 GP-7'),(36,'225/50 R17 GP-7'),(37,'215/65 R16 ADVENTURE VERDE'),(38,'235/60 R16 ADVENTURE VERDE'),(39,'225/65 R17 ADVENTURE VERDE'),(40,'225/55 R18 ADVENTURE VERDE'),(41,'235/60 R18 ADVENTURE VERDE'),(42,'195/70 R15 CARGO DURAMAX'),(43,'205/70 R15 CARGO DURAMAX'),(44,'205/75 R16 CARGO DURAMAX'),(45,'225/65 R16 CARGO DURAMAX'),(46,'225/75 R16 CARGO DURAMAX'),(47,'235/75 R15 ADVENTURE A/T'),(48,'235/70 R16 ADVENTURE ATR'),(49,'245/70 R16 ADVENTURE ATR'),(50,'265/70 R16 ADVENTURE ATR'),(51,'265/70 R16 ADVENTURE H/T'),(52,'265/65 R17 ADVENTURE ATR'),(53,'265/65 R17 ADVENTURE H/T'),(54,'265/60 R18 ADVENTURE H/T');
/*!40000 ALTER TABLE `matriz` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medida`
--

DROP TABLE IF EXISTS `medida`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medida` (
  `id` int NOT NULL AUTO_INCREMENT,
  `descricao` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medida`
--

LOCK TABLES `medida` WRITE;
/*!40000 ALTER TABLE `medida` DISABLE KEYS */;
INSERT INTO `medida` VALUES (1,'165/70 R13'),(2,'175/70 R13'),(3,'165/70 R14'),(4,'175/65 R14'),(5,'175/70 R14'),(6,'185/60 R14'),(7,'185/65 R14'),(8,'185/70 R14'),(9,'185/60 R15'),(10,'185/65 R15'),(11,'195/55 R15'),(12,'195/60 R15'),(13,'195/65 R15'),(14,'195/70 R15'),(15,'205/60 R15'),(16,'205/65 R15'),(17,'205/70 R15'),(18,'235/75 R15'),(19,'195/55 R16'),(20,'205/55 R16'),(21,'205/60 R16'),(22,'205/75 R16'),(23,'215/65 R16'),(24,'225/65 R16'),(25,'225/75 R16'),(26,'235/60 R16'),(27,'235/70 R16'),(28,'245/70 R16'),(29,'265/70 R16'),(30,'215/50 R17'),(31,'215/55 R17'),(32,'225/45 R17'),(33,'225/50 R17'),(34,'225/65 R17'),(35,'265/65 R17'),(36,'225/55 R18'),(37,'235/60 R18'),(38,'265/60 R18');
/*!40000 ALTER TABLE `medida` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `modelo`
--

DROP TABLE IF EXISTS `modelo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `modelo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `descricao` varchar(45) NOT NULL,
  `marca_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_modelo_marca1_idx` (`marca_id`),
  CONSTRAINT `fk_modelo_marca1` FOREIGN KEY (`marca_id`) REFERENCES `marca` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=236 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `modelo`
--

LOCK TABLES `modelo` WRITE;
/*!40000 ALTER TABLE `modelo` DISABLE KEYS */;
INSERT INTO `modelo` VALUES (1,'ACHILES ATR SPORT',1),(2,'ACHILLES ATR-K ECONOMIST',1),(3,'ACHILLES DESERT HAWK M/T',1),(4,'ACHILLES DESERT HAWK UHP',1),(5,'AELUS PRECISIONACE',2),(6,'APOLLO AMAZER 3G',3),(7,'APOLLO AMAZER 3G MAXX',3),(8,'BARUM BRAVURIS',4),(9,'BARUM BRAVURIS 3HM',4),(10,'BARUM BRILLANTIS  2',4),(11,'BF GOODRICH ALL TERRAIN KO-0',5),(12,'BF GOODRICH ALL TERRAIN KO-2',5),(13,'BF GOODRICH MUD TERRAIN T/A KM3',5),(14,'BF GOODRICH TOURING',5),(15,'BOLEX AW 2016',6),(16,'BRIDGESTONE B-250',7),(17,'BRIDGESTONE DUELER A/T REVO2',7),(18,'BRIDGESTONE DUELER AT 693',7),(19,'BRIDGESTONE DUELER HT 684',7),(20,'BRIDGESTONE DURAVIS R630',7),(21,'BRIDGESTONE ECOPIA EP150',7),(22,'BRIDGESTONE POTENZA G III',7),(23,'BRIDGESTONE TURANZA ER30',7),(24,'BRIDGESTONE TURANZA ER300',7),(25,'CONTINENTAL  CONTACT   3',8),(26,'CONTINENTAL CONTI CROSS CONTACT',8),(27,'CONTINENTAL CONTI ECO CONTACT',8),(28,'CONTINENTAL CONTI POWER CONTACT ECO PLUS',8),(29,'COOPER CHENGSHAN SPORTCAR CSC',9),(30,'COOPER CLASSIC TOUR',9),(31,'COOPER COBRA RADIAL',9),(32,'COOPER COURSE',9),(33,'COOPER CS1',9),(34,'COOPER DISCOVERY ATR',9),(35,'COSTANCY L4688',10),(36,'DUNLOP  SP LT-30',11),(37,'DUNLOP  TOURING R1',11),(38,'DUNLOP  TOURING T1',11),(39,'DUNLOP DIREZZA DZ-101',11),(40,'DUNLOP DIREZZA DZ-102',11),(41,'DUNLOP ENASAVE EC-300',11),(42,'DUNLOP GRANDTREK AT-25',11),(43,'DUNLOP GRANDTREK AT-3',11),(44,'DUNLOP GRANDTREK MT-1',11),(45,'DUNLOP GRANDTREK PT-3',11),(46,'DUNLOP LT30',11),(47,'DUNLOP SP FM-800',11),(48,'DUNLOP SPORT GT1',11),(49,'DUNLOP SPORT LM704',11),(50,'EUROMETRIC',12),(51,'EUZKADI EURO DRIVE 2',13),(52,'FATE  AR-410',14),(53,'FATE AR-300 ADVANCE',14),(54,'FATE AR-35 ADVANCE',14),(55,'FATE AR-360 ADVANCE',14),(56,'FATE AR-550 ADVANCE',14),(57,'FATE EXIMINIA PININFARINA',14),(58,'FATE LY688 CONSTANCY',14),(59,'FATE PRESTIVA',14),(60,'FATE SENTIVA AR 300',14),(61,'FATE SR-200',14),(62,'FEDERAL COURAGIA FX',15),(63,'FEDERAL FORMOZA FD2',15),(64,'FEDERAL Ss-595',15),(65,'FIRESTONE  F-580',16),(66,'FIRESTONE  F-600',16),(67,'FIRESTONE  FS-400',16),(68,'FIRESTONE CV-5000',16),(69,'FIRESTONE DESTINATION',16),(70,'FIRESTONE DESTINATION AT',16),(71,'FIRESTONE F-700',16),(72,'FIRESTONE F-77',16),(73,'FIRESTONE MULTIHAWK',16),(74,'FORMULA ENERGY',17),(75,'FORMULA SPIDER',17),(76,'FUZION SUV',18),(77,'FUZION TOURING',18),(78,'FUZION UHP SPORT',18),(79,'GENERAL ALTIMAX',19),(80,'GENERAL EVERTREK',19),(81,'GENERAL GRABBER AT2',19),(82,'GENERAL TIRE ALTIMAX HP',19),(83,'GO FORM G-520',20),(84,'GOODRIDE RADIAL RP 78',21),(85,'GOODYEAR ASSURANCE',22),(86,'GOODYEAR ASSURANCE TOURING',22),(87,'GOODYEAR DIRECTION',22),(88,'GOODYEAR DIRECTION SPORT',22),(89,'GOODYEAR DIRECTION TOURING',22),(90,'GOODYEAR EAGLE EXCELENCE',22),(91,'GOODYEAR EAGLE SPORT',22),(92,'GOODYEAR EFICIENTE GRIP PERFORMANCE',22),(93,'GOODYEAR FORTERA',22),(94,'GOODYEAR G-32 CARGO',22),(95,'GOODYEAR GPS DURAPLUS',22),(96,'GOODYEAR GPS-2',22),(97,'GOODYEAR GPS-3 SPORT',22),(98,'GOODYEAR GT-2',22),(99,'GOODYEAR NCT-5',22),(100,'GOODYEAR WRANGLER',22),(101,'GT RADIAL ADVENTURE M/T',23),(102,'GT RADIAL CHAMPIRO 128',23),(103,'GT RADIAL CHAMPIRO 70',23),(104,'GT RADIAL CHAMPIRO GTX-65',23),(105,'GT RADIAL CHAMPIRO SPORT ACTIVE',23),(106,'GT RADIAL MAXMILER-X',23),(107,'HANKOOK DYNAMIC RA03',24),(108,'HANKOOK DYNAPRO ATM',24),(109,'HANKOOK OPTIMO H425',24),(110,'HANKOOK OPTIMO ME02',24),(111,'HANKOOK VENTUS',24),(112,'HANKOOK VENTUS PRIME',24),(113,'HIFLY HF  201',25),(114,'JK TYRE ULTIMA SPORT',26),(115,'JK TYRE VECTRA',26),(116,'KELLY EDGE TOURING',27),(117,'KENDA RADIAL KN3',28),(118,'KENDA RADIAL SPT',28),(119,'KUMHO CRUGEN',29),(120,'KUMHO ECO SOLUS',29),(121,'KUMHO ECOWING',29),(122,'KUMHO ECSTA KH11',29),(123,'KUMHO ROAD VENTURE',29),(124,'KUMHO SOLUS HS51',29),(125,'KUMHO SOLUS SENSE',29),(126,'LANDSAIL  LS 388',30),(127,'LING LONG GREEEN',31),(128,'MASTER CRALT  AST',32),(129,'MAXXIS  MT-762',33),(130,'MAXXIS 110S AT-980',33),(131,'MAXXIS 116T - AT-771',33),(132,'MAXXIS AT-771 A/T',33),(133,'MAXXIS HP-600',33),(134,'MAXXIS HT-750',33),(135,'MAXXIS M-8060',33),(136,'MAXXIS M-8090 - CREEPY CRAWLER',33),(137,'MAXXIS MA 202',33),(138,'MAXXIS MA-701',33),(139,'MAXXIS MAP1',33),(140,'MAXXIS PRESA',33),(141,'MAXXIS VICTRA MA-Z4S',33),(142,'MICHELIN AGILLIS',34),(143,'MICHELIN ENERGY XM2',34),(144,'MICHELIN G-GRIP',34),(145,'MICHELIN LTX FORCE',34),(146,'MICHELIN PAJERO',34),(147,'MICHELIN PILOT SPORT',34),(148,'MICHELIN PRIMACY 3',34),(149,'MICHELIN PRIMACY 4',34),(150,'MICHELIN PRIMACY HP',34),(151,'MINNEL RADIAL',35),(152,'NEXEN CP-661',36),(153,'PIRELLI  CHRONO',37),(154,'PIRELLI CINTURATO P1',37),(155,'PIRELLI CINTURATO P1 PLUS',37),(156,'PIRELLI CINTURATO P-7',37),(157,'PIRELLI CT-52 CENTAURO',37),(158,'PIRELLI CT-65',37),(159,'PIRELLI P-2000',37),(160,'PIRELLI P-4 CINTURATO',37),(161,'PIRELLI P-400',37),(162,'PIRELLI P-6',37),(163,'PIRELLI P-6000',37),(164,'PIRELLI PHANTOM',37),(165,'PIRELLI SCORPION ATR',37),(166,'PIRELLI SCORPION MUD',37),(167,'PIRELLI SCORPION STR',37),(168,'PREMIORRI SOLAZO',38),(169,'PRIMEWHEEL LAERA H/T',39),(170,'PRIMEWHEEL PS880',39),(171,'PRIMEWHEEL SORENTO VITARA',39),(172,'PRIMEWHEEL SPORT',39),(173,'ROSAVA ORBITA',40),(174,'SAILUN  ATREZA 15-700',41),(175,'SAILUN ATREZO SH-402',41),(176,'SAILUN SH 406',41),(177,'SEIBERLING S-500',42),(178,'SEMPERITE LIFE 2',43),(179,'SUMITOMO BC-10',44),(180,'SUMITOMO BC-100',44),(181,'SUMITOMO BC-20',44),(182,'SUNNY  SN-3606',45),(183,'SUNNY SN-223C',45),(184,'SUNNY SN-3630',45),(185,'SUNNY SN-3870',45),(186,'SUNNY SN-3970',45),(187,'SUNNY SN-600',45),(188,'TORNADO ALFA',46),(189,'TORNADO JK TYRE',46),(190,'TORNADO LANDER TENERE',46),(191,'TORNEL CLASIC',47),(192,'TORNEL REAL',47),(193,'TOYO DRB',48),(194,'TOYO OPEN COUNTRY',48),(195,'TOYO PROXES C-1s',48),(196,'TOYO PROXES R-888',48),(197,'TOYO PROXES SPORT',48),(198,'TOYO PROXES ST',48),(199,'TRIANGLE ALL TERRAIN',49),(200,'TRIANGLE TALON GLS',49),(201,'TRIANGLE TC 101',49),(202,'TRIANGLE TE 301',49),(203,'TRIANGLE TH 201',49),(204,'TRIANGLE TR 257',49),(205,'TRIANGLE TR 257 HT',49),(206,'TRIANGLE TR 292',49),(207,'TRIANGLE TR 645',49),(208,'TRIANGLE TR 652',49),(209,'TRIANGLE TR 968',49),(210,'TRIANGLE TR 978',49),(211,'TRIANGLE TSH 11',49),(212,'TYRE VECTRA',50),(213,'WANLI AS-028',51),(214,'WANLI H-220',51),(215,'WANLI S-1023',51),(216,'WANLI S-1063',51),(217,'WANLI S-1088',51),(218,'WANLI SA302',51),(219,'WANLI SP-118',51),(220,'WANLI SU-006 A/T',51),(221,'WEST LAKE  RP-18',52),(222,'WEST LAKE H-188',52),(223,'WEST LAKE RP-28',52),(224,'WEST LAKE SL-369 A/T',52),(225,'WEST LAKE SU-318',52),(226,'YOKOHAMA A. DRIVE R1',53),(227,'YOKOHAMA A349A',53),(228,'YOKOHAMA ADVAN FLEVA',53),(229,'YOKOHAMA ADVAN NEOVA',53),(230,'YOKOHAMA ADVAN SPORT',53),(231,'YOKOHAMA BLUEARTH ES32',53),(232,'YOKOHAMA C.DRIVE2',53),(233,'YOKOHAMA GEOLANDAR A/T-S G012',53),(234,'YOKOHAMA GEOLANDAR AT G015',53),(235,'YOKOHAMA S-306',53);
/*!40000 ALTER TABLE `modelo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pais`
--

DROP TABLE IF EXISTS `pais`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pais` (
  `id` int NOT NULL AUTO_INCREMENT,
  `descricao` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pais`
--

LOCK TABLES `pais` WRITE;
/*!40000 ALTER TABLE `pais` DISABLE KEYS */;
INSERT INTO `pais` VALUES (1,'Brasil');
/*!40000 ALTER TABLE `pais` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `perfil`
--

DROP TABLE IF EXISTS `perfil`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `perfil` (
  `id` int NOT NULL AUTO_INCREMENT,
  `descricao` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `perfil`
--

LOCK TABLES `perfil` WRITE;
/*!40000 ALTER TABLE `perfil` DISABLE KEYS */;
INSERT INTO `perfil` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_FUNCIONARIO');
/*!40000 ALTER TABLE `perfil` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `producao`
--

DROP TABLE IF EXISTS `producao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `producao` (
  `id` int NOT NULL AUTO_INCREMENT,
  `carcaca_id` int NOT NULL,
  `medida_pneu_raspado` decimal(4,3) NOT NULL,
  `dados` json NOT NULL,
  `regra_id` int NOT NULL,
  `dt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `dt_update` datetime DEFAULT NULL,
  `dt_delete` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_producao_pneu1_idx` (`carcaca_id`),
  KEY `fk_producao_regra1_idx` (`regra_id`),
  CONSTRAINT `fk_producao_pneu1` FOREIGN KEY (`carcaca_id`) REFERENCES `carcaca` (`id`),
  CONSTRAINT `fk_producao_regra1` FOREIGN KEY (`regra_id`) REFERENCES `regra` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producao`
--

LOCK TABLES `producao` WRITE;
/*!40000 ALTER TABLE `producao` DISABLE KEYS */;
/*!40000 ALTER TABLE `producao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `regra`
--

DROP TABLE IF EXISTS `regra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `regra` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tamanho_min` decimal(4,3) NOT NULL,
  `tamanho_max` decimal(4,3) NOT NULL,
  `tempo` varchar(45) DEFAULT NULL,
  `matriz_id` int NOT NULL,
  `medida_id` int NOT NULL,
  `pais_id` int NOT NULL,
  `modelo_id` int NOT NULL,
  `camelback_id` int NOT NULL,
  `espessuramento_id` int DEFAULT NULL,
  `antiquebra1_id` int NOT NULL,
  `antiquebra2_id` int DEFAULT NULL,
  `antiquebra3_id` int DEFAULT NULL,
  `dt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `dt_update` datetime DEFAULT NULL,
  `dt_delete` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_regra_matriz1_idx` (`matriz_id`),
  KEY `fk_regra_medida1_idx` (`medida_id`),
  KEY `fk_regra_pais1_idx` (`pais_id`),
  KEY `fk_regra_modelo1_idx` (`modelo_id`),
  KEY `fk_regra_camelback1_idx` (`camelback_id`),
  KEY `fk_regra_espessuramento1_idx` (`espessuramento_id`),
  KEY `fk_regra_antiquebra1_idx` (`antiquebra1_id`),
  KEY `fk_regra_antiquebra2_idx` (`antiquebra2_id`),
  KEY `fk_regra_antiquebra3_idx` (`antiquebra3_id`),
  CONSTRAINT `fk_regra_antiquebra1` FOREIGN KEY (`antiquebra1_id`) REFERENCES `antiquebra` (`id`),
  CONSTRAINT `fk_regra_antiquebra2` FOREIGN KEY (`antiquebra2_id`) REFERENCES `antiquebra` (`id`),
  CONSTRAINT `fk_regra_antiquebra3` FOREIGN KEY (`antiquebra3_id`) REFERENCES `antiquebra` (`id`),
  CONSTRAINT `fk_regra_camelback1` FOREIGN KEY (`camelback_id`) REFERENCES `camelback` (`id`),
  CONSTRAINT `fk_regra_espessuramento1` FOREIGN KEY (`espessuramento_id`) REFERENCES `espessuramento` (`id`),
  CONSTRAINT `fk_regra_matriz1` FOREIGN KEY (`matriz_id`) REFERENCES `matriz` (`id`),
  CONSTRAINT `fk_regra_medida1` FOREIGN KEY (`medida_id`) REFERENCES `medida` (`id`),
  CONSTRAINT `fk_regra_modelo1` FOREIGN KEY (`modelo_id`) REFERENCES `modelo` (`id`),
  CONSTRAINT `fk_regra_pais1` FOREIGN KEY (`pais_id`) REFERENCES `pais` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `regra`
--

LOCK TABLES `regra` WRITE;
/*!40000 ALTER TABLE `regra` DISABLE KEYS */;
INSERT INTO `regra` VALUES (1,1.000,2.000,'10',1,1,1,1,1,1,2,2,NULL,'2021-10-07 22:55:33',NULL,NULL);
/*!40000 ALTER TABLE `regra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_classificacao`
--

DROP TABLE IF EXISTS `tipo_classificacao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_classificacao` (
  `id` int NOT NULL AUTO_INCREMENT,
  `descricao` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_classificacao`
--

LOCK TABLES `tipo_classificacao` WRITE;
/*!40000 ALTER TABLE `tipo_classificacao` DISABLE KEYS */;
/*!40000 ALTER TABLE `tipo_classificacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_observacao`
--

DROP TABLE IF EXISTS `tipo_observacao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_observacao` (
  `id` int NOT NULL AUTO_INCREMENT,
  `descricao` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_observacao`
--

LOCK TABLES `tipo_observacao` WRITE;
/*!40000 ALTER TABLE `tipo_observacao` DISABLE KEYS */;
/*!40000 ALTER TABLE `tipo_observacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) DEFAULT NULL,
  `login` varchar(45) DEFAULT NULL,
  `senha` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'Sandy','admin','$2y$12$TsFjfCJYLGe0Bw08E6aQ9uu9NZTf5yQ4TvhKb0UNVwKHWlDRZwdia');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario_perfil`
--

DROP TABLE IF EXISTS `usuario_perfil`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario_perfil` (
  `id` int NOT NULL AUTO_INCREMENT,
  `perfil_id` int NOT NULL,
  `usuario_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_usuario_perfil_perfil1_idx` (`perfil_id`),
  KEY `fk_usuario_perfil_usuario1_idx` (`usuario_id`),
  CONSTRAINT `fk_usuario_perfil_perfil1` FOREIGN KEY (`perfil_id`) REFERENCES `perfil` (`id`),
  CONSTRAINT `fk_usuario_perfil_usuario1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario_perfil`
--

LOCK TABLES `usuario_perfil` WRITE;
/*!40000 ALTER TABLE `usuario_perfil` DISABLE KEYS */;
INSERT INTO `usuario_perfil` VALUES (1,1,1);
/*!40000 ALTER TABLE `usuario_perfil` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-01-10 21:35:56
