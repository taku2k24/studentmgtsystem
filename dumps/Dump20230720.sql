CREATE DATABASE  IF NOT EXISTS `studentms` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `studentms`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: studentms
-- ------------------------------------------------------
-- Server version	8.0.34

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
-- Table structure for table `modules`
--

DROP TABLE IF EXISTS `modules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `modules` (
  `module_id` int NOT NULL AUTO_INCREMENT,
  `module_name` varchar(100) NOT NULL,
  `credits` int NOT NULL,
  `student_id` int NOT NULL,
  PRIMARY KEY (`module_id`),
  KEY `student_id` (`student_id`),
  CONSTRAINT `modules_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `modules`
--

LOCK TABLES `modules` WRITE;
/*!40000 ALTER TABLE `modules` DISABLE KEYS */;
INSERT INTO `modules` VALUES (1,'Introduction to Mathematics',4,1),(2,'Business Management',3,2),(3,'Art History',3,1);
/*!40000 ALTER TABLE `modules` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `student_id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `date_of_birth` date NOT NULL,
  `gender` varchar(10) NOT NULL,
  `address` varchar(100) NOT NULL,
  `contact_number` varchar(15) NOT NULL,
  `email` varchar(100) NOT NULL,
  `admission_date` date NOT NULL,
  `batch` varchar(20) NOT NULL,
  `course_name` enum('Bachelor of Arts (BA)','Bachelor of Science (BS)','Bachelor of Business Administration (BBA)','Bachelor of Computer Science (BCS)','Bachelor of Engineering (BE)','Bachelor of Education (BEd)','Bachelor of Fine Arts (BFA)','Bachelor of Commerce (BCom)','Bachelor of Nursing (BN)','Bachelor of Social Work (BSW)','Bachelor of Architecture (BArch)','Bachelor of Medicine and Bachelor of Surgery (MBBS)','Bachelor of Law (LLB)','Bachelor of Psychology (BPsych)','Bachelor of Environmental Science (BEnvSc)','Bachelor of Communication (BComm)','Bachelor of Media Studies (BMS)','Bachelor of Economics (BEcon)','Bachelor of Information Technology (BIT)','Bachelor of Music (BMus)','Bachelor of Public Health (BPH)','Bachelor of International Relations (BIR)','Bachelor of Science in Nursing (BSN)','Bachelor of Social Sciences (BSS)','Bachelor of Graphic Design (BDes)','Bachelor of Philosophy (BPhil)','Bachelor of Hospitality Management (BHM)','Bachelor of Sports Science (BSSc)','Bachelor of Environmental Engineering (BEnvE)','Bachelor of Health Sciences (BHSc)') NOT NULL,
  `username` varchar(50) NOT NULL,
  PRIMARY KEY (`student_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (1,'John','Doe','1995-08-15','Male','123 Main Street, City','+1234567890','john.doe@email.com','2022-09-01','2022 Batch','Bachelor of Science (BS)','thomas'),(2,'Jane','Smith','1998-03-20','Female','456 Elm Avenue, Town','+9876543210','jane.smith@email.com','2021-08-15','2021 Batch','Bachelor of Arts (BA)','john'),(3,'Michael','Johnson','1999-11-10','Male','789 Oak Street, Village','+5551234567','michael.johnson@email.com','2023-09-05','2023 Batch','Bachelor of Business Administration (BBA)','');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `userType` enum('student','staff','admin') NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'thomas','helloworld','staff'),(2,'john','heythere','student'),(3,'pinkman','yoloyolo','student');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-07-20 23:41:30
