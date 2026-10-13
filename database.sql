-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: localhost    Database: farmer_advisory
-- ------------------------------------------------------
-- Server version	8.0.46

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
-- Table structure for table `crop_prices`
--

DROP TABLE IF EXISTS `crop_prices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `crop_prices` (
  `price_id` int NOT NULL AUTO_INCREMENT,
  `market_id` int NOT NULL,
  `crop_id` int NOT NULL,
  `price_per_unit` decimal(10,2) DEFAULT NULL,
  `unit` varchar(30) DEFAULT NULL,
  `date_updated` date DEFAULT NULL,
  PRIMARY KEY (`price_id`),
  KEY `market_id` (`market_id`),
  KEY `crop_id` (`crop_id`),
  CONSTRAINT `crop_prices_ibfk_1` FOREIGN KEY (`market_id`) REFERENCES `markets` (`market_id`),
  CONSTRAINT `crop_prices_ibfk_2` FOREIGN KEY (`crop_id`) REFERENCES `crops` (`crop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `crop_recommendations`
--

DROP TABLE IF EXISTS `crop_recommendations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `crop_recommendations` (
  `recommendation_id` int NOT NULL AUTO_INCREMENT,
  `farmer_id` int NOT NULL,
  `land_id` int NOT NULL,
  `crop_id` int NOT NULL,
  `suitability` varchar(50) DEFAULT NULL,
  `reason` varchar(500) DEFAULT NULL,
  `recommendation_date` date DEFAULT NULL,
  PRIMARY KEY (`recommendation_id`),
  KEY `farmer_id` (`farmer_id`),
  KEY `land_id` (`land_id`),
  KEY `crop_id` (`crop_id`),
  CONSTRAINT `crop_recommendations_ibfk_1` FOREIGN KEY (`farmer_id`) REFERENCES `farmers` (`farmer_id`),
  CONSTRAINT `crop_recommendations_ibfk_2` FOREIGN KEY (`land_id`) REFERENCES `lands` (`land_id`),
  CONSTRAINT `crop_recommendations_ibfk_3` FOREIGN KEY (`crop_id`) REFERENCES `crops` (`crop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `crops`
--

DROP TABLE IF EXISTS `crops`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `crops` (
  `crop_id` int NOT NULL AUTO_INCREMENT,
  `crop_name` varchar(100) NOT NULL,
  `season` varchar(50) DEFAULT NULL,
  `duration_days` int DEFAULT NULL,
  `expected_yield` decimal(10,2) DEFAULT NULL,
  `min_temperature` decimal(5,2) DEFAULT NULL,
  `max_temperature` decimal(5,2) DEFAULT NULL,
  PRIMARY KEY (`crop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cultivation`
--

DROP TABLE IF EXISTS `cultivation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cultivation` (
  `cultivation_id` int NOT NULL AUTO_INCREMENT,
  `crop_id` int NOT NULL,
  `cultivation_method` varchar(200) DEFAULT NULL,
  `pest_control` varchar(300) DEFAULT NULL,
  `harvest_duration` int DEFAULT NULL,
  PRIMARY KEY (`cultivation_id`),
  KEY `crop_id` (`crop_id`),
  CONSTRAINT `cultivation_ibfk_1` FOREIGN KEY (`crop_id`) REFERENCES `crops` (`crop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `farmers`
--

DROP TABLE IF EXISTS `farmers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `farmers` (
  `farmer_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `age` int DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `village` varchar(100) DEFAULT NULL,
  `district` varchar(100) DEFAULT NULL,
  `land_ownership` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`farmer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `fertilizers`
--

DROP TABLE IF EXISTS `fertilizers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fertilizers` (
  `fertilizer_id` int NOT NULL AUTO_INCREMENT,
  `fertilizer_name` varchar(100) NOT NULL,
  `quantity` varchar(50) DEFAULT NULL,
  `application_stage` varchar(100) DEFAULT NULL,
  `crop_id` int DEFAULT NULL,
  PRIMARY KEY (`fertilizer_id`),
  KEY `crop_id` (`crop_id`),
  CONSTRAINT `fertilizers_ibfk_1` FOREIGN KEY (`crop_id`) REFERENCES `crops` (`crop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `lands`
--

DROP TABLE IF EXISTS `lands`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lands` (
  `land_id` int NOT NULL AUTO_INCREMENT,
  `farmer_id` int NOT NULL,
  `area` decimal(10,2) DEFAULT NULL,
  `soil_type` varchar(100) DEFAULT NULL,
  `irrigation` varchar(100) DEFAULT NULL,
  `water_availability` varchar(100) DEFAULT NULL,
  `location` varchar(150) DEFAULT NULL,
  `temperature` decimal(5,2) DEFAULT NULL,
  PRIMARY KEY (`land_id`),
  KEY `farmer_id` (`farmer_id`),
  CONSTRAINT `lands_ibfk_1` FOREIGN KEY (`farmer_id`) REFERENCES `farmers` (`farmer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `markets`
--

DROP TABLE IF EXISTS `markets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `markets` (
  `market_id` int NOT NULL AUTO_INCREMENT,
  `market_name` varchar(100) NOT NULL,
  `location` varchar(150) DEFAULT NULL,
  `contact` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`market_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `previous_crops`
--

DROP TABLE IF EXISTS `previous_crops`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `previous_crops` (
  `record_id` int NOT NULL AUTO_INCREMENT,
  `farmer_id` int NOT NULL,
  `land_id` int NOT NULL,
  `crop_id` int NOT NULL,
  `season` varchar(50) DEFAULT NULL,
  `year` int DEFAULT NULL,
  `yield` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`record_id`),
  KEY `farmer_id` (`farmer_id`),
  KEY `land_id` (`land_id`),
  KEY `crop_id` (`crop_id`),
  CONSTRAINT `previous_crops_ibfk_1` FOREIGN KEY (`farmer_id`) REFERENCES `farmers` (`farmer_id`),
  CONSTRAINT `previous_crops_ibfk_2` FOREIGN KEY (`land_id`) REFERENCES `lands` (`land_id`),
  CONSTRAINT `previous_crops_ibfk_3` FOREIGN KEY (`crop_id`) REFERENCES `crops` (`crop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `soil_tests`
--

DROP TABLE IF EXISTS `soil_tests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `soil_tests` (
  `test_id` int NOT NULL AUTO_INCREMENT,
  `land_id` int NOT NULL,
  `nitrogen` decimal(10,2) DEFAULT NULL,
  `phosphorus` decimal(10,2) DEFAULT NULL,
  `potassium` decimal(10,2) DEFAULT NULL,
  `ph` decimal(4,2) DEFAULT NULL,
  `test_date` date DEFAULT NULL,
  PRIMARY KEY (`test_id`),
  KEY `land_id` (`land_id`),
  CONSTRAINT `soil_tests_ibfk_1` FOREIGN KEY (`land_id`) REFERENCES `lands` (`land_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-10-13 22:07:57
