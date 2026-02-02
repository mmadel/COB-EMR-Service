-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: emr
-- ------------------------------------------------------
-- Server version	8.0.32

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
-- Table structure for table `consent_text_versions`
--

DROP TABLE IF EXISTS `consent_text_versions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `consent_text_versions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `active` bit(1) DEFAULT NULL,
  `consent_text` text NOT NULL,
  `consent_title` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `effective_from` datetime(6) NOT NULL,
  `effective_until` datetime(6) DEFAULT NULL,
  `text_hash` varchar(255) NOT NULL,
  `version_code` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_3ultyymdrv86dmlwrd2j2rcd4` (`version_code`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `consent_text_versions`
--

LOCK TABLES `consent_text_versions` WRITE;
/*!40000 ALTER TABLE `consent_text_versions` DISABLE KEYS */;
INSERT INTO `consent_text_versions` VALUES (1,_binary '','1. I consent to the use of an electronic signature to sign medical documents.\n\n2. I understand that my electronic signature has the same legal validity as a handwritten signature.\n\n3. I confirm that I am the authorized individual associated with this account and that no one else has access to my credentials.\n\n4. I acknowledge that all electronically signed documents will be stored securely and may be used as part of the official medical record.\n\n5. I understand that I may withdraw my consent to use electronic signatures at any time by contacting the system administrator.\n\n6. I agree that by completing the OTP verification process and acknowledging this consent, I am affixing my electronic signature to the associated document.','Electronic Signature Consent Agreement','2026-02-02 06:40:31.000000','system','2026-02-02 06:40:31.000000',NULL,'5291ff6cead8e453e8a3bbf2a73f07b6194fdb27bb2311ff9f6272a68f791bc4','V1.0');
/*!40000 ALTER TABLE `consent_text_versions` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-02-02 17:18:07
