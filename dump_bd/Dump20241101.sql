CREATE DATABASE  IF NOT EXISTS `db_barbearia` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `db_barbearia`;
-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: db_barbearia
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.32-MariaDB

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
-- Table structure for table `tb_barbeiros`
--

DROP TABLE IF EXISTS `tb_barbeiros`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_barbeiros` (
  `id_barbeiro` int(11) NOT NULL AUTO_INCREMENT,
  `nome_barbeiro` varchar(50) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `tel` varchar(50) NOT NULL,
  `login` varchar(15) NOT NULL,
  `senha` varchar(15) NOT NULL,
  `perfil` enum('admin','user') NOT NULL DEFAULT 'user',
  PRIMARY KEY (`id_barbeiro`),
  UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_barbeiros`
--

LOCK TABLES `tb_barbeiros` WRITE;
/*!40000 ALTER TABLE `tb_barbeiros` DISABLE KEYS */;
INSERT INTO `tb_barbeiros` VALUES (1,'Guilherme','guilherme@gmail.com','(21) 99999 - 9999','guilherme','123','admin'),(2,'Sergyo','sergyo@gmail.com','(21) 99999 - 9999','sergyo','123','user'),(11,'Júlio','julio@gmail.com','(21) 99999 - 9999','julio','123','user'),(12,'Marcio','marcio@gmail.com','(21) 99999 - 9999','marcio','123','user'),(13,'Pedro','pedro@gmail.com','(21) 99999 - 9999','pedro','123','user');
/*!40000 ALTER TABLE `tb_barbeiros` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_clientes`
--

DROP TABLE IF EXISTS `tb_clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_clientes` (
  `id_cliente` int(11) NOT NULL AUTO_INCREMENT,
  `id_barbeiro` int(11) DEFAULT NULL,
  `nome_cliente` varchar(50) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `tel` varchar(50) NOT NULL,
  `servico` varchar(100) DEFAULT NULL,
  `valor` decimal(10,2) DEFAULT NULL,
  `marcacao` timestamp NOT NULL DEFAULT current_timestamp(),
  `data_c` date DEFAULT NULL,
  `hora` time DEFAULT NULL,
  PRIMARY KEY (`id_cliente`),
  KEY `id_barbeiro` (`id_barbeiro`),
  CONSTRAINT `tb_clientes_ibfk_1` FOREIGN KEY (`id_barbeiro`) REFERENCES `tb_barbeiros` (`id_barbeiro`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_clientes`
--

LOCK TABLES `tb_clientes` WRITE;
/*!40000 ALTER TABLE `tb_clientes` DISABLE KEYS */;
INSERT INTO `tb_clientes` VALUES (3,1,'Pedro','pedro@gmail.com','(21) 99999-9999','Cabelo',25.00,'2024-10-22 19:24:08','2024-10-26','17:00:00'),(4,2,'Vinicius','vinicius@gmail.com','(21) 99999-9999','Barba',20.00,'2024-10-22 19:25:05','2024-10-24','15:00:00'),(5,11,'Antonio','antonio@gmail.com','(21) 99999-9999','Cabelo e Barba',40.00,'2024-10-22 19:25:57','2024-10-23','17:00:00'),(8,1,'Pedro','pedro@gmail.com','(21) 99999-9999','Cabelo',25.00,'2024-11-01 16:41:44','2024-11-02','17:30:00');
/*!40000 ALTER TABLE `tb_clientes` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-01 15:03:12
