-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: paymybuddy
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `adresses`
--

DROP TABLE IF EXISTS `adresses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adresses` (
  `adresse_id` bigint NOT NULL AUTO_INCREMENT,
  `ville` varchar(255) DEFAULT NULL,
  `supplement_adresse` varchar(255) DEFAULT NULL,
  `numero` int DEFAULT NULL,
  `rue` varchar(255) DEFAULT NULL,
  `code_postal` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`adresse_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `comptes`
--

DROP TABLE IF EXISTS `comptes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comptes` (
  `compte_id` bigint NOT NULL AUTO_INCREMENT,
  `numero_compte` varchar(255) DEFAULT NULL,
  `compte_type` varchar(255) DEFAULT NULL,
  `solde` double DEFAULT NULL,
  `label` varchar(255) DEFAULT NULL,
  `utilisateur_id` bigint DEFAULT NULL,
  PRIMARY KEY (`compte_id`),
  UNIQUE KEY `UK_coswgxmsdb8j51fkqp4ncg27k` (`numero_compte`),
  KEY `FK1x2med8da6mnu9oqkuw17xvvc` (`utilisateur_id`),
  CONSTRAINT `FK1x2med8da6mnu9oqkuw17xvvc` FOREIGN KEY (`utilisateur_id`) REFERENCES `utilisateur` (`utilisateur_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `paiements`
--

DROP TABLE IF EXISTS `paiements`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `paiements` (
  `paiement_id` bigint NOT NULL AUTO_INCREMENT,
  `amount` double NOT NULL,
  `delivered` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `receveur_id` bigint DEFAULT NULL,
  `emetteur_id` bigint DEFAULT NULL,
  PRIMARY KEY (`paiement_id`),
  KEY `FKgwqvgaq3et3rslqab8jh21s48` (`receveur_id`),
  KEY `FKex5ai5effp7n9qgbfeng6vpiu` (`emetteur_id`),
  CONSTRAINT `FKex5ai5effp7n9qgbfeng6vpiu` FOREIGN KEY (`emetteur_id`) REFERENCES `utilisateur` (`utilisateur_id`),
  CONSTRAINT `FKgwqvgaq3et3rslqab8jh21s48` FOREIGN KEY (`receveur_id`) REFERENCES `utilisateur` (`utilisateur_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  KEY `FKrhfovtciq1l558cw6udg0h0d3` (`role_id`),
  KEY `FKfhg6g8mbl6gcwupy69tajpvjm` (`user_id`),
  CONSTRAINT `FKfhg6g8mbl6gcwupy69tajpvjm` FOREIGN KEY (`user_id`) REFERENCES `utilisateur` (`utilisateur_id`),
  CONSTRAINT `FKrhfovtciq1l558cw6udg0h0d3` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `utilisateur`
--

DROP TABLE IF EXISTS `utilisateur`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utilisateur` (
  `utilisateur_id` bigint NOT NULL AUTO_INCREMENT,
  `balance` double NOT NULL,
  `date_naissance` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `mot_de_passe` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`utilisateur_id`),
  UNIQUE KEY `UK_rma38wvnqfaf66vvmi57c71lo` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `utilisateurs_adresses`
--

DROP TABLE IF EXISTS `utilisateurs_adresses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utilisateurs_adresses` (
  `utilisateur_id` bigint NOT NULL,
  `adresse_id` bigint NOT NULL,
  KEY `FKthmyrq2ts1sfqypfmuqxaxcm4` (`adresse_id`),
  KEY `FKrv5xw0kh2wjc6xib807q6k7as` (`utilisateur_id`),
  CONSTRAINT `FKrv5xw0kh2wjc6xib807q6k7as` FOREIGN KEY (`utilisateur_id`) REFERENCES `utilisateur` (`utilisateur_id`),
  CONSTRAINT `FKthmyrq2ts1sfqypfmuqxaxcm4` FOREIGN KEY (`adresse_id`) REFERENCES `adresses` (`adresse_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `utilisateurs_contacts`
--

DROP TABLE IF EXISTS `utilisateurs_contacts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utilisateurs_contacts` (
  `utilisateur_id` bigint NOT NULL,
  `contact_id` bigint NOT NULL,
  KEY `FKm5sifobhil75kugbk7vifi315` (`contact_id`),
  KEY `FK223s9t4eoe1vaochcks6y75uf` (`utilisateur_id`),
  CONSTRAINT `FK223s9t4eoe1vaochcks6y75uf` FOREIGN KEY (`utilisateur_id`) REFERENCES `utilisateur` (`utilisateur_id`),
  CONSTRAINT `FKm5sifobhil75kugbk7vifi315` FOREIGN KEY (`contact_id`) REFERENCES `utilisateur` (`utilisateur_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping routines for database 'paymybuddy'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-03 10:00:57
