-- MariaDB dump 10.17  Distrib 10.4.10-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: sog_rigel
-- ------------------------------------------------------
-- Server version	10.4.10-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `sog_adjunto`
--

DROP TABLE IF EXISTS `sog_adjunto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sog_adjunto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ruta` varchar(200) COLLATE utf8_spanish_ci DEFAULT NULL,
  `tamano` varchar(45) COLLATE utf8_spanish_ci DEFAULT NULL,
  `tipoArchivo` varchar(45) COLLATE utf8_spanish_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sog_adjunto`
--

LOCK TABLES `sog_adjunto` WRITE;
/*!40000 ALTER TABLE `sog_adjunto` DISABLE KEYS */;
/*!40000 ALTER TABLE `sog_adjunto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sog_estado`
--

DROP TABLE IF EXISTS `sog_estado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sog_estado` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sog_estado`
--

LOCK TABLES `sog_estado` WRITE;
/*!40000 ALTER TABLE `sog_estado` DISABLE KEYS */;
INSERT INTO `sog_estado` VALUES (1,'Gestión '),(2,'Aprobado '),(3,'Rechazado'),(4,'Pendiente ');
/*!40000 ALTER TABLE `sog_estado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sog_festivos`
--

DROP TABLE IF EXISTS `sog_festivos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sog_festivos` (
  `id` int(11) NOT NULL,
  `nombre` varchar(45) DEFAULT NULL,
  `dia` int(11) DEFAULT NULL,
  `mes` int(11) DEFAULT NULL,
  `diaSemana` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Tabla de festivos Colombia';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sog_festivos`
--

LOCK TABLES `sog_festivos` WRITE;
/*!40000 ALTER TABLE `sog_festivos` DISABLE KEYS */;
INSERT INTO `sog_festivos` VALUES (1,'Año Nuevo',1,1,3),(2,'Día de los Reyes Magos',6,1,1),(3,'Día de San José',23,3,1),(4,'Jueves Santo',9,4,4),(5,'Viernes Santo',10,4,5),(6,'Día del Trabajo',1,5,5),(7,'Día de la Ascensión',25,5,1),(8,'Corpus Christi',15,6,1),(9,'Sagrado Corazón',22,6,1),(10,'San Pedro y San Pablo',29,6,1),(11,'Día de la Independencia',20,7,1),(12,'Batalla de Boyacá',7,8,5),(13,'Día de la Asunción',17,8,1),(14,'Celebración del Día de la Raza',12,10,1),(15,'Día de todos los Santos',2,11,1),(16,'Independencia de Cartagena',16,11,1),(17,'Inmaculada Concepción',8,12,2),(18,'Navidad',25,12,5);
/*!40000 ALTER TABLE `sog_festivos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sog_motivosolicitud`
--

DROP TABLE IF EXISTS `sog_motivosolicitud`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sog_motivosolicitud` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) COLLATE utf8_spanish_ci DEFAULT NULL,
  `prioridad` int(2) DEFAULT NULL,
  `estado` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sog_motivosolicitud`
--

LOCK TABLES `sog_motivosolicitud` WRITE;
/*!40000 ALTER TABLE `sog_motivosolicitud` DISABLE KEYS */;
INSERT INTO `sog_motivosolicitud` VALUES (1,'Médicos',2,1),(2,'Personales',3,1),(3,'Familiares',3,1),(4,'Escolares',3,1),(5,'Judiciales',2,1),(6,'Cirugía Programada',1,1),(7,'Procedimiento Odontológico',1,1),(8,'No Aplica',3,1);
/*!40000 ALTER TABLE `sog_motivosolicitud` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sog_reglas`
--

DROP TABLE IF EXISTS `sog_reglas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sog_reglas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `renderedPanel` tinyint(4) DEFAULT NULL,
  `renderedFechaFinal` tinyint(4) DEFAULT NULL,
  `aplicaHoraInicio` tinyint(4) DEFAULT NULL,
  `aplicaHoraFinal` tinyint(4) DEFAULT NULL,
  `horaInicial` int(2) DEFAULT NULL,
  `horaFinal` int(2) DEFAULT NULL,
  `rompeZona` tinyint(4) DEFAULT NULL,
  `adjuntoObligatorio` tinyint(4) DEFAULT NULL,
  `renderedAdjunto` tinyint(4) DEFAULT NULL,
  `laboraFestivo` tinyint(4) DEFAULT NULL,
  `idTipoSolicitud` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COMMENT='Creación de Reglas Dinámicas ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sog_reglas`
--

LOCK TABLES `sog_reglas` WRITE;
/*!40000 ALTER TABLE `sog_reglas` DISABLE KEYS */;
INSERT INTO `sog_reglas` VALUES (1,1,0,0,0,0,0,0,0,0,0,1),(2,1,1,0,0,0,0,0,0,0,0,2),(3,1,0,0,1,0,1,1,0,0,0,3),(4,1,0,1,0,1,0,1,0,0,0,4),(5,1,1,0,0,0,0,0,1,1,0,5),(6,1,0,0,0,0,0,1,0,0,1,6);
/*!40000 ALTER TABLE `sog_reglas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sog_respuesta`
--

DROP TABLE IF EXISTS `sog_respuesta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sog_respuesta` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fechaCreacion` datetime DEFAULT NULL,
  `idUsuarioCreacion` int(11) DEFAULT NULL,
  `valor` blob DEFAULT NULL,
  `esRespuestaOperador` tinyint(4) DEFAULT NULL,
  `idSolicitud` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sog_respuesta`
--

LOCK TABLES `sog_respuesta` WRITE;
/*!40000 ALTER TABLE `sog_respuesta` DISABLE KEYS */;
/*!40000 ALTER TABLE `sog_respuesta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sog_solicitud`
--

DROP TABLE IF EXISTS `sog_solicitud`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sog_solicitud` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `guid` varchar(45) COLLATE utf8_spanish_ci DEFAULT NULL,
  `idEstado` int(2) DEFAULT NULL,
  `fechaCreacion` datetime DEFAULT NULL,
  `fechaModificacion` datetime DEFAULT NULL,
  `horaInicio` time DEFAULT NULL,
  `horaFinal` time DEFAULT NULL,
  `fechaSolicitud` datetime DEFAULT NULL,
  `fechaSolicitudFinal` datetime DEFAULT NULL,
  `idOperador` int(11) DEFAULT NULL,
  `idUsuarioCreacion` int(11) DEFAULT NULL,
  `idUsuarioModificacion` int(11) DEFAULT NULL,
  `idMotivoSolicitud` int(11) DEFAULT NULL,
  `idTipoSolicitud` int(11) DEFAULT NULL,
  `puntoInicio` varchar(45) COLLATE utf8_spanish_ci DEFAULT NULL,
  `puntoFin` varchar(45) COLLATE utf8_spanish_ci DEFAULT NULL,
  `observaciones` varchar(255) COLLATE utf8_spanish_ci DEFAULT NULL,
  `solicitarDocumentoAdjunto` tinyint(4) DEFAULT NULL,
  `esSolicitudSistema` tinyint(4) DEFAULT NULL,
  `prioridad` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sog_solicitud`
--

LOCK TABLES `sog_solicitud` WRITE;
/*!40000 ALTER TABLE `sog_solicitud` DISABLE KEYS */;
INSERT INTO `sog_solicitud` VALUES (1,'SOL1JKUI1',1,'2019-12-18 00:00:00','2019-12-18 00:00:00',NULL,NULL,'2019-12-18 00:00:00',NULL,1,1,1,8,1,NULL,NULL,'Prueba',1,1,3),(2,'SOL1YWE2',2,'2019-12-18 00:00:00','2019-12-18 00:00:00','11:00:00','23:00:00','2019-12-18 00:00:00',NULL,1,1,1,8,1,NULL,NULL,'Prueba con hora',1,1,1);
/*!40000 ALTER TABLE `sog_solicitud` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sog_tipo_motivo`
--

DROP TABLE IF EXISTS `sog_tipo_motivo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sog_tipo_motivo` (
  `id` int(11) NOT NULL,
  `idTipo` int(11) DEFAULT NULL,
  `idMotivo` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sog_tipo_motivo`
--

LOCK TABLES `sog_tipo_motivo` WRITE;
/*!40000 ALTER TABLE `sog_tipo_motivo` DISABLE KEYS */;
INSERT INTO `sog_tipo_motivo` VALUES (1,1,8),(2,2,1),(3,2,2),(4,2,3),(5,2,4),(6,2,5),(7,3,1),(8,3,2),(9,3,3),(10,3,4),(11,3,5),(12,4,1),(13,4,2),(14,4,3),(15,4,4),(16,4,5),(17,5,6),(18,5,7),(19,6,8);
/*!40000 ALTER TABLE `sog_tipo_motivo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sog_tiposolicitud`
--

DROP TABLE IF EXISTS `sog_tiposolicitud`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sog_tiposolicitud` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) COLLATE utf8_spanish_ci DEFAULT NULL,
  `estado` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sog_tiposolicitud`
--

LOCK TABLES `sog_tiposolicitud` WRITE;
/*!40000 ALTER TABLE `sog_tiposolicitud` DISABLE KEYS */;
INSERT INTO `sog_tiposolicitud` VALUES (1,'Descanso ',1),(2,'Licencia no remunerada',1),(3,'Sale antes de ',1),(4,'Entra despues de ',1),(5,'Incapacidad',1),(6,'Trabajar festivo',1);
/*!40000 ALTER TABLE `sog_tiposolicitud` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-01-02 18:24:48
