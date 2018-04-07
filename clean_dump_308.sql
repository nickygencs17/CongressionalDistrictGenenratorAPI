-- MySQL dump 10.13  Distrib 5.7.21, for osx10.13 (x86_64)
--
-- Host: 127.0.0.1    Database: database_308
-- ------------------------------------------------------
-- Server version	5.7.21

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
-- Table structure for table `app_user`
--

DROP TABLE IF EXISTS `app_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app_user` (
  `username` varchar(50) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `user_password` varchar(50) NOT NULL,
  `city` varchar(50) NOT NULL,
  `state_id` varchar(2) NOT NULL,
  `address` varchar(100) NOT NULL,
  `zip` int(11) NOT NULL,
  `role` varchar(50) NOT NULL,
  PRIMARY KEY (`username`),
  UNIQUE KEY `app_user_username_uindex` (`username`),
  KEY `app_user_us_state_state_id_fk` (`state_id`),
  CONSTRAINT `app_user_us_state_state_id_fk` FOREIGN KEY (`state_id`) REFERENCES `us_state` (`state_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_user`
--

LOCK TABLES `app_user` WRITE;
/*!40000 ALTER TABLE `app_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `app_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `blog_post`
--

DROP TABLE IF EXISTS `blog_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `blog_post` (
  `image_url` varchar(1024) NOT NULL,
  `post_text` longtext NOT NULL,
  `comment_ids` longtext,
  `post_id` int(11) NOT NULL AUTO_INCREMENT,
  `time_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `author` varchar(100) NOT NULL,
  PRIMARY KEY (`post_id`),
  UNIQUE KEY `blog_post_post_id_uindex` (`post_id`),
  KEY `blog_post_app_user_username_fk` (`author`),
  CONSTRAINT `blog_post_app_user_username_fk` FOREIGN KEY (`author`) REFERENCES `app_user` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blog_post`
--

LOCK TABLES `blog_post` WRITE;
/*!40000 ALTER TABLE `blog_post` DISABLE KEYS */;
/*!40000 ALTER TABLE `blog_post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `comment_text` longtext NOT NULL,
  `comment_id` int(11) NOT NULL AUTO_INCREMENT,
  `comment_time_date` int(11) NOT NULL,
  `author` varchar(50) NOT NULL,
  `post_id` int(11) NOT NULL,
  PRIMARY KEY (`comment_id`),
  UNIQUE KEY `comment_comment_id_uindex` (`comment_id`),
  KEY `comment_app_user_username_fk` (`author`),
  KEY `comment_blog_post_post_id_fk` (`post_id`),
  CONSTRAINT `comment_app_user_username_fk` FOREIGN KEY (`author`) REFERENCES `app_user` (`username`),
  CONSTRAINT `comment_blog_post_post_id_fk` FOREIGN KEY (`post_id`) REFERENCES `blog_post` (`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `congress_election_info`
--

DROP TABLE IF EXISTS `congress_election_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `congress_election_info` (
  `election_year` int(11) NOT NULL,
  `state_id` varchar(2) NOT NULL,
  `congress_id` varchar(10) NOT NULL,
  `party` varchar(50) NOT NULL,
  `percent_of_votes` float NOT NULL,
  `is_winner` tinyint(1) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  UNIQUE KEY `congress_election_info_id_uindex` (`id`),
  KEY `congress_election_info_congressional_districts_congress_id_fk` (`congress_id`),
  KEY `congress_election_info_us_state_state_id_fk` (`state_id`),
  CONSTRAINT `congress_election_info_congressional_districts_congress_id_fk` FOREIGN KEY (`congress_id`) REFERENCES `congressional_districts` (`congress_id`),
  CONSTRAINT `congress_election_info_us_state_state_id_fk` FOREIGN KEY (`state_id`) REFERENCES `us_state` (`state_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `congress_election_info`
--

LOCK TABLES `congress_election_info` WRITE;
/*!40000 ALTER TABLE `congress_election_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `congress_election_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `congressional_districts`
--

DROP TABLE IF EXISTS `congressional_districts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `congressional_districts` (
  `congress_id` varchar(10) NOT NULL,
  `voting_districts` longtext NOT NULL,
  `population` bigint(20) NOT NULL,
  `state_id` varchar(2) NOT NULL,
  `is_changed` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`congress_id`),
  UNIQUE KEY `congressional_districts_congress_id_uindex` (`congress_id`),
  KEY `congressional_districts_us_state_state_id_fk` (`state_id`),
  CONSTRAINT `congressional_districts_us_state_state_id_fk` FOREIGN KEY (`state_id`) REFERENCES `us_state` (`state_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `congressional_districts`
--

LOCK TABLES `congressional_districts` WRITE;
/*!40000 ALTER TABLE `congressional_districts` DISABLE KEYS */;
/*!40000 ALTER TABLE `congressional_districts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `current_officials`
--

DROP TABLE IF EXISTS `current_officials`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `current_officials` (
  `state_id` varchar(2) NOT NULL,
  `type_office` varchar(50) NOT NULL,
  `full_name` varchar(100) NOT NULL,
  `party` varchar(15) DEFAULT NULL,
  `district_id` varchar(10) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  UNIQUE KEY `current_officials_id_uindex` (`id`),
  KEY `current_officials_us_state_state_id_fk` (`state_id`),
  CONSTRAINT `current_officials_us_state_state_id_fk` FOREIGN KEY (`state_id`) REFERENCES `us_state` (`state_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `current_officials`
--

LOCK TABLES `current_officials` WRITE;
/*!40000 ALTER TABLE `current_officials` DISABLE KEYS */;
/*!40000 ALTER TABLE `current_officials` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `log`
--

DROP TABLE IF EXISTS `log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `log` (
  `time_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `log_id` int(11) NOT NULL AUTO_INCREMENT,
  `log_details` longtext NOT NULL,
  PRIMARY KEY (`log_id`),
  UNIQUE KEY `log_log_id_uindex` (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `log`
--

LOCK TABLES `log` WRITE;
/*!40000 ALTER TABLE `log` DISABLE KEYS */;
/*!40000 ALTER TABLE `log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `president_election_info`
--

DROP TABLE IF EXISTS `president_election_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `president_election_info` (
  `election_year` int(11) NOT NULL,
  `party` varchar(50) NOT NULL,
  `pres_name` varchar(100) NOT NULL,
  `vpres_name` varchar(100) NOT NULL,
  `votes_for` bigint(20) NOT NULL,
  `vote_percent` float NOT NULL,
  `ec_vote` int(11) NOT NULL,
  `state_id` varchar(2) NOT NULL,
  `is_winner` tinyint(1) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  UNIQUE KEY `president_election_info_id_uindex` (`id`),
  KEY `president_election_info_us_state_state_id_fk` (`state_id`),
  CONSTRAINT `president_election_info_us_state_state_id_fk` FOREIGN KEY (`state_id`) REFERENCES `us_state` (`state_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `president_election_info`
--

LOCK TABLES `president_election_info` WRITE;
/*!40000 ALTER TABLE `president_election_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `president_election_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `us_state`
--

DROP TABLE IF EXISTS `us_state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `us_state` (
  `voting_district_ids` longtext NOT NULL,
  `state_boundries` longtext NOT NULL,
  `lower_boundries` longtext NOT NULL,
  `upper_boundries` longtext NOT NULL,
  `congress_boundries` longtext,
  `number_of_congress_districts` int(11) DEFAULT NULL,
  `state_id` varchar(2) NOT NULL,
  PRIMARY KEY (`state_id`),
  UNIQUE KEY `us_state_state_id_uindex` (`state_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `us_state`
--

LOCK TABLES `us_state` WRITE;
/*!40000 ALTER TABLE `us_state` DISABLE KEYS */;
/*!40000 ALTER TABLE `us_state` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `voting_districts`
--

DROP TABLE IF EXISTS `voting_districts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `voting_districts` (
  `state_id` varchar(2) NOT NULL,
  `congress_id` varchar(10) NOT NULL,
  `vd_id` varchar(10) NOT NULL,
  `neighbor_vds` longtext,
  `d_leaning` float NOT NULL,
  `r_leaning` float NOT NULL,
  `population` bigint(20) NOT NULL,
  `vd_boundries` longtext,
  PRIMARY KEY (`vd_id`),
  UNIQUE KEY `voting_districts_vd_id_uindex` (`vd_id`),
  KEY `voting_districts_us_state_state_id_fk` (`state_id`),
  KEY `voting_districts_congressional_districts_congress_id_fk` (`congress_id`),
  CONSTRAINT `voting_districts_congressional_districts_congress_id_fk` FOREIGN KEY (`congress_id`) REFERENCES `congressional_districts` (`congress_id`),
  CONSTRAINT `voting_districts_us_state_state_id_fk` FOREIGN KEY (`state_id`) REFERENCES `us_state` (`state_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voting_districts`
--

LOCK TABLES `voting_districts` WRITE;
/*!40000 ALTER TABLE `voting_districts` DISABLE KEYS */;
/*!40000 ALTER TABLE `voting_districts` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-07 17:09:13
