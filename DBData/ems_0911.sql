-- MySQL dump 10.13  Distrib 5.5.40, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: ems
-- ------------------------------------------------------
-- Server version	5.5.40-0ubuntu0.14.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `COURSEMODULE`
--

DROP TABLE IF EXISTS `COURSEMODULE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `COURSEMODULE` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `DATEOFEXAM` date DEFAULT NULL,
  `DURATION` int(11) DEFAULT NULL,
  `LOCATION` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `STARTTIME` time DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `COURSEMODULE`
--

LOCK TABLES `COURSEMODULE` WRITE;
/*!40000 ALTER TABLE `COURSEMODULE` DISABLE KEYS */;
INSERT INTO `COURSEMODULE` VALUES (1,'2014-11-17',3,'MPSH','Object Oriented Analysis and Design','19:00:00'),(2,'2014-11-18',3,'MPSH','Object Oriented Design Patterns','19:00:00'),(3,'2014-11-19',3,'MPSH','Advanced Estimation','19:00:00'),(4,'2014-11-20',3,'MPSH','Entreprise Java','19:00:00'),(5,'2014-11-21',3,'MPSH','Human Computer Interface','19:00:00'),(6,'2014-11-22',3,'MPSH','Software Quality Management','19:00:00');
/*!40000 ALTER TABLE `COURSEMODULE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EXAMPAPER`
--

DROP TABLE IF EXISTS `EXAMPAPER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EXAMPAPER` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CREATEDON` date DEFAULT NULL,
  `MODULE_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_EXAMPAPER_MODULE_ID` (`MODULE_ID`),
  CONSTRAINT `FK_EXAMPAPER_MODULE_ID` FOREIGN KEY (`MODULE_ID`) REFERENCES `COURSEMODULE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EXAMPAPER`
--

LOCK TABLES `EXAMPAPER` WRITE;
/*!40000 ALTER TABLE `EXAMPAPER` DISABLE KEYS */;
/*!40000 ALTER TABLE `EXAMPAPER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EXAMSESSION`
--

DROP TABLE IF EXISTS `EXAMSESSION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EXAMSESSION` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ISACTIVESESSION` tinyint(1) DEFAULT '0',
  `EXAMPAPER_ID` bigint(20) DEFAULT NULL,
  `STUDENT_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_EXAMSESSION_EXAMPAPER_ID` (`EXAMPAPER_ID`),
  KEY `FK_EXAMSESSION_STUDENT_ID` (`STUDENT_ID`),
  CONSTRAINT `FK_EXAMSESSION_EXAMPAPER_ID` FOREIGN KEY (`EXAMPAPER_ID`) REFERENCES `EXAMPAPER` (`ID`),
  CONSTRAINT `FK_EXAMSESSION_STUDENT_ID` FOREIGN KEY (`STUDENT_ID`) REFERENCES `STUDENT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EXAMSESSION`
--

LOCK TABLES `EXAMSESSION` WRITE;
/*!40000 ALTER TABLE `EXAMSESSION` DISABLE KEYS */;
/*!40000 ALTER TABLE `EXAMSESSION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QUESTION`
--

DROP TABLE IF EXISTS `QUESTION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QUESTION` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `QuestionType` varchar(31) DEFAULT NULL,
  `MARKS` int(11) DEFAULT NULL,
  `TEXT` varchar(255) DEFAULT NULL,
  `TYPEOFQUESTION` varchar(255) DEFAULT NULL,
  `COURSEMODULE_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_QUESTION_COURSEMODULE_ID` (`COURSEMODULE_ID`),
  CONSTRAINT `FK_QUESTION_COURSEMODULE_ID` FOREIGN KEY (`COURSEMODULE_ID`) REFERENCES `COURSEMODULE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QUESTION`
--

LOCK TABLES `QUESTION` WRITE;
/*!40000 ALTER TABLE `QUESTION` DISABLE KEYS */;
/*!40000 ALTER TABLE `QUESTION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QUESTIONMULTIANSWER`
--

DROP TABLE IF EXISTS `QUESTIONMULTIANSWER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QUESTIONMULTIANSWER` (
  `id` bigint(20) NOT NULL,
  `CHOICES` longblob,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_QUESTIONMULTIANSWER_id` FOREIGN KEY (`id`) REFERENCES `QUESTION` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QUESTIONMULTIANSWER`
--

LOCK TABLES `QUESTIONMULTIANSWER` WRITE;
/*!40000 ALTER TABLE `QUESTIONMULTIANSWER` DISABLE KEYS */;
/*!40000 ALTER TABLE `QUESTIONMULTIANSWER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QUESTIONMULTICHOICE`
--

DROP TABLE IF EXISTS `QUESTIONMULTICHOICE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QUESTIONMULTICHOICE` (
  `id` bigint(20) NOT NULL,
  `CHOICES` longblob,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_QUESTIONMULTICHOICE_id` FOREIGN KEY (`id`) REFERENCES `QUESTION` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QUESTIONMULTICHOICE`
--

LOCK TABLES `QUESTIONMULTICHOICE` WRITE;
/*!40000 ALTER TABLE `QUESTIONMULTICHOICE` DISABLE KEYS */;
/*!40000 ALTER TABLE `QUESTIONMULTICHOICE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QUESTION_SUBJECT`
--

DROP TABLE IF EXISTS `QUESTION_SUBJECT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QUESTION_SUBJECT` (
  `SUBJECT_ID` bigint(20) NOT NULL,
  `QUESTION_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`SUBJECT_ID`,`QUESTION_ID`),
  KEY `FK_QUESTION_SUBJECT_QUESTION_ID` (`QUESTION_ID`),
  CONSTRAINT `FK_QUESTION_SUBJECT_QUESTION_ID` FOREIGN KEY (`QUESTION_ID`) REFERENCES `QUESTION` (`ID`),
  CONSTRAINT `FK_QUESTION_SUBJECT_SUBJECT_ID` FOREIGN KEY (`SUBJECT_ID`) REFERENCES `SUBJECT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QUESTION_SUBJECT`
--

LOCK TABLES `QUESTION_SUBJECT` WRITE;
/*!40000 ALTER TABLE `QUESTION_SUBJECT` DISABLE KEYS */;
/*!40000 ALTER TABLE `QUESTION_SUBJECT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QuestionMultiAnswer_CHOICES`
--

DROP TABLE IF EXISTS `QuestionMultiAnswer_CHOICES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QuestionMultiAnswer_CHOICES` (
  `QuestionMultiAnswer_ID` bigint(20) DEFAULT NULL,
  `CHOICES` varchar(255) DEFAULT NULL,
  KEY `QuestionMultiAnswer_CHOICES_QuestionMultiAnswer_ID` (`QuestionMultiAnswer_ID`),
  CONSTRAINT `QuestionMultiAnswer_CHOICES_QuestionMultiAnswer_ID` FOREIGN KEY (`QuestionMultiAnswer_ID`) REFERENCES `QUESTION` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QuestionMultiAnswer_CHOICES`
--

LOCK TABLES `QuestionMultiAnswer_CHOICES` WRITE;
/*!40000 ALTER TABLE `QuestionMultiAnswer_CHOICES` DISABLE KEYS */;
/*!40000 ALTER TABLE `QuestionMultiAnswer_CHOICES` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QuestionMultiChoice_CHOICES`
--

DROP TABLE IF EXISTS `QuestionMultiChoice_CHOICES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QuestionMultiChoice_CHOICES` (
  `QuestionMultiChoice_ID` bigint(20) DEFAULT NULL,
  `CHOICES` varchar(255) DEFAULT NULL,
  KEY `QuestionMultiChoice_CHOICES_QuestionMultiChoice_ID` (`QuestionMultiChoice_ID`),
  CONSTRAINT `QuestionMultiChoice_CHOICES_QuestionMultiChoice_ID` FOREIGN KEY (`QuestionMultiChoice_ID`) REFERENCES `QUESTION` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QuestionMultiChoice_CHOICES`
--

LOCK TABLES `QuestionMultiChoice_CHOICES` WRITE;
/*!40000 ALTER TABLE `QuestionMultiChoice_CHOICES` DISABLE KEYS */;
/*!40000 ALTER TABLE `QuestionMultiChoice_CHOICES` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SECTION`
--

DROP TABLE IF EXISTS `SECTION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SECTION` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `SECTIONNAME` varchar(255) DEFAULT NULL,
  `SECTIONTOTALMARKS` bigint(20) DEFAULT NULL,
  `SECTIONTYPE` varchar(255) DEFAULT NULL,
  `EXAMPAPER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_SECTION_EXAMPAPER_ID` (`EXAMPAPER_ID`),
  CONSTRAINT `FK_SECTION_EXAMPAPER_ID` FOREIGN KEY (`EXAMPAPER_ID`) REFERENCES `EXAMPAPER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SECTION`
--

LOCK TABLES `SECTION` WRITE;
/*!40000 ALTER TABLE `SECTION` DISABLE KEYS */;
/*!40000 ALTER TABLE `SECTION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `STUDENT`
--

DROP TABLE IF EXISTS `STUDENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `STUDENT` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) DEFAULT NULL,
  `USER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_STUDENT_USER_ID` (`USER_ID`),
  CONSTRAINT `FK_STUDENT_USER_ID` FOREIGN KEY (`USER_ID`) REFERENCES `USER` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `STUDENT`
--

LOCK TABLES `STUDENT` WRITE;
/*!40000 ALTER TABLE `STUDENT` DISABLE KEYS */;
INSERT INTO `STUDENT` VALUES (1,'Abhinav Gupta',1),(2,'Nisith Singh',2),(4,'Sharique Azam',4);
/*!40000 ALTER TABLE `STUDENT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `STUDENT_COURSEMODULE`
--

DROP TABLE IF EXISTS `STUDENT_COURSEMODULE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `STUDENT_COURSEMODULE` (
  `Student_ID` bigint(20) NOT NULL,
  `enrolledModules_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`Student_ID`,`enrolledModules_ID`),
  KEY `FK_STUDENT_COURSEMODULE_enrolledModules_ID` (`enrolledModules_ID`),
  CONSTRAINT `FK_STUDENT_COURSEMODULE_enrolledModules_ID` FOREIGN KEY (`enrolledModules_ID`) REFERENCES `COURSEMODULE` (`ID`),
  CONSTRAINT `FK_STUDENT_COURSEMODULE_Student_ID` FOREIGN KEY (`Student_ID`) REFERENCES `STUDENT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `STUDENT_COURSEMODULE`
--

LOCK TABLES `STUDENT_COURSEMODULE` WRITE;
/*!40000 ALTER TABLE `STUDENT_COURSEMODULE` DISABLE KEYS */;
INSERT INTO `STUDENT_COURSEMODULE` VALUES (1,1),(2,1),(4,1),(1,2),(2,2),(4,2),(1,3),(2,3),(4,4);
/*!40000 ALTER TABLE `STUDENT_COURSEMODULE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SUBJECT`
--

DROP TABLE IF EXISTS `SUBJECT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SUBJECT` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TAGNAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SUBJECT`
--

LOCK TABLES `SUBJECT` WRITE;
/*!40000 ALTER TABLE `SUBJECT` DISABLE KEYS */;
/*!40000 ALTER TABLE `SUBJECT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER`
--

DROP TABLE IF EXISTS `USER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ISFIRSTLOGIN` varchar(255) DEFAULT NULL,
  `LOGINID` varchar(255) DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `ROLE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER`
--

LOCK TABLES `USER` WRITE;
/*!40000 ALTER TABLE `USER` DISABLE KEYS */;
INSERT INTO `USER` VALUES (1,'Y','abhinav','Abhinav','password','student'),(2,'Y','nisith','Nisith','password','student'),(3,'N','dilbert','Dilbert','password','lecturer'),(4,'Y','sharique','Sharique','password','student');
/*!40000 ALTER TABLE `USER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USERACCESS`
--

DROP TABLE IF EXISTS `USERACCESS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USERACCESS` (
  `USERID` varchar(255) NOT NULL,
  `PASSWORD` varchar(255) NOT NULL,
  `ISFIRSTLOGIN` char(1) NOT NULL DEFAULT 'Y',
  PRIMARY KEY (`USERID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USERACCESS`
--

LOCK TABLES `USERACCESS` WRITE;
/*!40000 ALTER TABLE `USERACCESS` DISABLE KEYS */;
INSERT INTO `USERACCESS` VALUES ('abhinav','password','Y'),('dilbert','password','N'),('nisith','password','Y'),('sharique','password','Y');
/*!40000 ALTER TABLE `USERACCESS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USERACCESS_groups`
--

DROP TABLE IF EXISTS `USERACCESS_GROUPS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USERACCESS_GROUPS` (
  `GROUPID` varchar(255) NOT NULL,
  `USERID` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USERACCESS_groups`
--

LOCK TABLES `USERACCESS_GROUPS` WRITE;
/*!40000 ALTER TABLE `USERACCESS_groups` DISABLE KEYS */;
INSERT INTO `USERACCESS_GROUPS` VALUES ('student','abhinav'),('student','nisith'),('lecturer','dilbert'),('student','sharique');
/*!40000 ALTER TABLE `USERACCESS_groups` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-11-09 17:36:00