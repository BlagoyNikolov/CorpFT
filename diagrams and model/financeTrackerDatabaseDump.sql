-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: finance_tracker_hibernate
-- ------------------------------------------------------
-- Server version	5.7.19-log

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
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `accounts` (
  `account_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `amount` decimal(13,4) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`account_id`,`user_id`),
  UNIQUE KEY `account_id_UNIQUE` (`account_id`),
  KEY `fk_accounts_users1_idx` (`user_id`),
  CONSTRAINT `FKnjuop33mo69pd79ctplkck40n` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fk_accounts_users1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=147 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accounts`
--

LOCK TABLES `accounts` WRITE;
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
INSERT INTO `accounts` VALUES (93,'Bank',4658.0000,53),(94,'Cash',2000.0000,53),(95,'Credit card',1906.0000,53),(103,'para',-3.0000,54),(128,'Bank',2000.0000,67),(129,'Cash',1000.0000,67),(130,'smetki',41.2000,68),(131,'Cash',499999980.0000,68);
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `budgets`
--

DROP TABLE IF EXISTS `budgets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `budgets` (
  `budget_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `initial_amount` decimal(13,4) unsigned zerofill NOT NULL DEFAULT '000000000.0000',
  `amount` decimal(13,4) unsigned zerofill NOT NULL,
  `from_date` datetime NOT NULL,
  `to_date` datetime NOT NULL,
  `account_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  PRIMARY KEY (`budget_id`,`account_id`,`category_id`),
  UNIQUE KEY `budget_id_UNIQUE` (`budget_id`),
  KEY `fk_budgets_accounts1_idx` (`account_id`),
  KEY `fk_budgets_categories1_idx` (`category_id`),
  CONSTRAINT `FK8ejw00f6x180r8ks8d1bcu8ok` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`account_id`),
  CONSTRAINT `FKn7qib00712y8dwelmqfwis6ka` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`),
  CONSTRAINT `fk_budgets_accounts1` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`account_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_budgets_categories1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `budgets`
--

LOCK TABLES `budgets` WRITE;
/*!40000 ALTER TABLE `budgets` DISABLE KEYS */;
INSERT INTO `budgets` VALUES (53,'Car',000005600.0000,000003040.0000,'2017-12-01 00:00:00','2017-12-31 00:00:00',95,40),(62,'zxcvbnm',000001000.0000,000000854.0000,'2017-12-01 00:00:00','2018-01-26 00:00:00',94,46);
/*!40000 ALTER TABLE `budgets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `budgets_has_transactions`
--

DROP TABLE IF EXISTS `budgets_has_transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `budgets_has_transactions` (
  `budget_id` int(11) NOT NULL,
  `transaction_id` int(11) NOT NULL,
  PRIMARY KEY (`budget_id`,`transaction_id`),
  KEY `fk_budgets_has_transactions_transactions1_idx` (`transaction_id`),
  KEY `fk_budgets_has_transactions_budgets1_idx` (`budget_id`),
  CONSTRAINT `FKoa4510es2dh6xctgrr50vfr4g` FOREIGN KEY (`budget_id`) REFERENCES `budgets` (`budget_id`),
  CONSTRAINT `FKpweg5rc5pruii5mh51eu1py0o` FOREIGN KEY (`transaction_id`) REFERENCES `transactions` (`transaction_id`),
  CONSTRAINT `fk_budgets_has_transactions_budgets1` FOREIGN KEY (`budget_id`) REFERENCES `budgets` (`budget_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_budgets_has_transactions_transactions1` FOREIGN KEY (`transaction_id`) REFERENCES `transactions` (`transaction_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `budgets_has_transactions`
--

LOCK TABLES `budgets_has_transactions` WRITE;
/*!40000 ALTER TABLE `budgets_has_transactions` DISABLE KEYS */;
INSERT INTO `budgets_has_transactions` VALUES (53,87),(53,97),(53,104),(53,110),(53,111),(53,144),(62,164),(53,173),(62,207);
/*!40000 ALTER TABLE `budgets_has_transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categories` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `type` varchar(45) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `category_id_UNIQUE` (`category_id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  UNIQUE KEY `UK6b5di7puf2eyr06yangpd1rws` (`category_id`,`name`),
  KEY `FKghuylkwuedgl2qahxjt8g41kb` (`user_id`),
  CONSTRAINT `FKghuylkwuedgl2qahxjt8g41kb` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (39,'Transfer','TRANSFER',NULL),(40,'Car','EXPENSE',NULL),(41,'Taxes','EXPENSE',NULL),(42,'Cafe','EXPENSE',NULL),(43,'Salary','INCOME',NULL),(44,'Rent','INCOME',NULL),(45,'Lottery','INCOME',NULL),(46,'PC','EXPENSE',53),(47,'Accessories','EXPENSE',53),(49,'Zaplata','INCOME',53),(50,'TOTO','INCOME',53),(51,'Kuchki','EXPENSE',67),(53,'Computer','EXPENSE',53),(54,'Parno','EXPENSE',53);
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `planned_payments`
--

DROP TABLE IF EXISTS `planned_payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `planned_payments` (
  `planned_payment_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `type` varchar(45) NOT NULL,
  `from_date` datetime NOT NULL,
  `amount` decimal(13,4) unsigned zerofill NOT NULL,
  `description` varchar(45) NOT NULL,
  `account_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  `category_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`planned_payment_id`,`account_id`,`category_id`),
  UNIQUE KEY `planned_payment_id_UNIQUE` (`planned_payment_id`),
  KEY `fk_planned_payments_accounts1_idx` (`account_id`),
  KEY `fk_planned_payments_categories1_idx` (`category_id`),
  CONSTRAINT `FKi474lvg83wwrbjvb7a5hfykim` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`account_id`),
  CONSTRAINT `FKipiaffxud57rfvykuaefes3gg` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`),
  CONSTRAINT `fk_planned_payments_accounts1` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`account_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_planned_payments_categories1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `planned_payments`
--

LOCK TABLES `planned_payments` WRITE;
/*!40000 ALTER TABLE `planned_payments` DISABLE KEYS */;
INSERT INTO `planned_payments` VALUES (22,'tghjkl','EXPENSE','2018-01-20 00:00:00',000000654.0000,'rd',93,42,NULL),(24,'awd','EXPENSE','2018-01-19 00:00:00',000000213.0000,'sadaw',93,47,NULL),(25,'sad','EXPENSE','2018-01-26 00:00:00',000000313.0000,'fghjkl',93,47,NULL);
/*!40000 ALTER TABLE `planned_payments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transactions` (
  `transaction_id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL,
  `date` datetime NOT NULL,
  `amount` decimal(13,4) unsigned zerofill NOT NULL,
  `description` varchar(45) NOT NULL,
  `account_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  `category_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`transaction_id`,`account_id`,`category_id`),
  UNIQUE KEY `transaction_id_UNIQUE` (`transaction_id`),
  KEY `fk_transactions_accounts1_idx` (`account_id`),
  KEY `fk_transactions_categories1_idx` (`category_id`),
  CONSTRAINT `FK20w7wsg13u9srbq3bd7chfxdh` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`account_id`),
  CONSTRAINT `FKsqqi7sneo04kast0o138h19mv` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`),
  CONSTRAINT `fk_transactions_accounts1` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`account_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_transactions_categories1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=220 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transactions`
--

LOCK TABLES `transactions` WRITE;
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;
INSERT INTO `transactions` VALUES (81,'EXPENSE','2017-12-04 14:36:49',000000050.0000,'Transfer to account Credit card',93,39,'Transfer'),(82,'INCOME','2017-12-04 14:36:49',000000050.0000,'Transfer from account Bank',95,39,'Transfer'),(83,'INCOME','2017-12-05 00:00:00',000001000.0000,'Salary',95,43,'Salary'),(85,'EXPENSE','2017-12-04 15:26:23',000000500.0000,'da mi vurnat kolata asap',94,40,'Car'),(86,'EXPENSE','2017-12-02 00:00:00',000000097.0000,'parno',93,41,'Taxes'),(87,'EXPENSE','2017-12-03 00:00:00',000000450.0000,'nosa4a',95,40,'Car'),(88,'EXPENSE','2017-12-01 00:00:00',000001000.0000,'new rig',93,46,'PC'),(89,'EXPENSE','2017-12-04 16:13:19',000000950.0000,'Transfer to account Bank',95,39,'Transfer'),(90,'INCOME','2017-12-04 16:13:19',000000950.0000,'Transfer from account Credit card',93,39,'Transfer'),(92,'EXPENSE','2017-12-05 13:08:02',000000500.0000,'Transfer to account Cash',95,39,'Transfer'),(93,'INCOME','2017-12-05 13:08:02',000000500.0000,'Transfer from account Credit card',94,39,'Transfer'),(94,'EXPENSE','2017-12-05 13:55:47',000000065.0000,'Planned Payment Expense',93,40,'Car'),(96,'EXPENSE','2017-12-05 13:59:45',000000650.0000,'Planned Payment Expense',94,40,'Car'),(97,'EXPENSE','2017-12-06 10:40:45',000000200.0000,'gazovata',95,40,'Car'),(99,'INCOME','2017-12-06 00:00:00',000000300.0000,'naemaa',94,44,'Rent'),(100,'EXPENSE','2017-12-06 15:26:27',000000300.0000,'Transfer to account Bank',94,39,'Transfer'),(101,'INCOME','2017-12-06 15:26:27',000000300.0000,'Transfer from account Cash',93,39,'Transfer'),(104,'EXPENSE','2017-12-06 00:00:00',000000150.0000,'vodna pompa',95,40,'Car'),(106,'EXPENSE','2017-12-08 11:17:26',000000100.0000,'Transfer to account Bank',94,39,'Transfer'),(107,'INCOME','2017-12-08 11:17:26',000000100.0000,'Transfer from account Cash',93,39,'Transfer'),(110,'EXPENSE','2017-12-08 16:17:08',000000500.0000,'galvata',95,40,'Car'),(111,'EXPENSE','2017-12-08 17:23:44',000000200.0000,'gazovoto',95,40,'Car'),(112,'INCOME','2017-12-08 17:48:26',000000800.0000,'avans4e',93,43,'Salary'),(113,'INCOME','2017-12-11 10:27:48',000001000.0000,'payday',95,43,'Salary'),(114,'INCOME','2017-12-13 00:00:00',000001100.0000,'parenges',93,43,'Salary'),(116,'EXPENSE','2017-12-13 18:24:15',000000060.0000,'Transfer to account Cash',95,39,'Transfer'),(117,'INCOME','2017-12-13 18:24:15',000000060.0000,'Transfer from account Credit card',94,39,'Transfer'),(126,'EXPENSE','2017-12-15 13:25:03',000000123.0000,'dwas',103,41,'Taxes'),(127,'EXPENSE','2017-12-18 00:00:00',000000150.0000,'Planned Payment Expense',95,46,'PC'),(128,'EXPENSE','2017-12-19 09:47:57',000000080.0000,'Transfer to account Cash',95,39,'Transfer'),(129,'INCOME','2017-12-19 09:47:57',000000080.0000,'Transfer from account Credit card',94,39,'Transfer'),(131,'EXPENSE','2017-12-19 11:04:00',000000013.0000,'Transfer to account dafuq',94,39,'Transfer'),(139,'EXPENSE','2017-12-19 16:00:03',000000070.0000,'Transfer to account Cash',95,39,'Transfer'),(140,'INCOME','2017-12-19 16:00:03',000000070.0000,'Transfer from account Credit card',94,39,'Transfer'),(141,'INCOME','2017-12-19 00:00:00',000000789.0000,'fghjk',94,50,'TOTO'),(143,'INCOME','2017-12-20 13:05:48',000000987.0000,'dasf',94,43,'Salary'),(144,'EXPENSE','2017-12-20 00:00:00',000000300.0000,'remont',95,40,'Car'),(145,'EXPENSE','2017-12-20 17:00:09',000000250.0000,'asdfghjuki',94,41,'Taxes'),(148,'EXPENSE','2017-12-21 12:16:19',000000030.0000,'Transfer to account Credit card',94,39,'Transfer'),(149,'INCOME','2017-12-21 12:16:19',000000030.0000,'Transfer from account Cash',95,39,'Transfer'),(150,'INCOME','2017-12-21 13:10:28',000001000.0000,'Planned Payment Income',94,43,'Salary'),(151,'INCOME','2017-12-21 00:00:00',000002500.0000,'kjhgvfc',94,43,'Salary'),(153,'EXPENSE','2017-12-21 19:32:43',000001500.0000,'Transfer to account Credit card',94,39,'Transfer'),(154,'INCOME','2017-12-21 19:32:43',000001500.0000,'Transfer from account Cash',95,39,'Transfer'),(156,'INCOME','2017-12-21 19:42:07',000000500.0000,'Transfer from account dafuq',93,39,'Transfer'),(158,'INCOME','2017-12-21 19:43:20',000000200.0000,'Transfer from account dafuq',95,39,'Transfer'),(159,'EXPENSE','2017-12-22 10:14:12',000000314.0000,'plz work',94,47,'Accessories'),(164,'EXPENSE','2017-12-22 00:00:00',000000641.0000,'ADSDS',94,46,'PC'),(166,'INCOME','2017-12-22 17:06:22',000001000.0000,'Transfer from account dafuq',95,39,'Transfer'),(169,'EXPENSE','2017-12-28 12:10:36',000000400.0000,'Transfer to account Credit card',93,39,'Transfer'),(170,'INCOME','2017-12-28 12:10:36',000000400.0000,'Transfer from account Bank',95,39,'Transfer'),(173,'EXPENSE','2017-12-28 13:33:09',000001240.0000,'garnituri i kvo li oshte ne',95,40,'Car'),(174,'EXPENSE','2017-12-28 15:33:25',000001000.0000,'Az izgubih vsichko sega',128,40,'Car'),(175,'INCOME','2017-12-30 00:00:00',000000010.0000,'parichki ',131,43,NULL),(176,'EXPENSE','2017-12-28 15:50:02',000000040.0000,'Transfer to account smetki',131,39,'Transfer'),(177,'INCOME','2017-12-28 15:50:02',000000040.0000,'Transfer from account Cash',130,39,'Transfer'),(199,'INCOME','2018-01-08 22:39:34',000000500.0000,'dfghjk',93,43,'Salary'),(201,'EXPENSE','2018-01-09 21:39:53',000000020.0000,'xcvbnm',93,47,'Accessories'),(202,'INCOME','2018-01-09 21:48:49',000000200.0000,'cvbnm',93,50,'TOTO'),(203,'INCOME','2018-01-09 21:58:17',000000560.0000,'fghjk',93,43,'Salary'),(204,'INCOME','2018-01-09 21:58:39',000000200.0000,'tyui',93,50,'TOTO'),(205,'EXPENSE','2018-01-09 22:29:23',000000564.0000,'fghjkl',93,47,'Accessories'),(206,'EXPENSE','2018-01-10 23:23:00',000000154.0000,'Planned Payment Expense',93,47,'Accessories'),(207,'EXPENSE','2018-01-10 23:23:00',000000213.0000,'Planned Payment Expense',94,46,'PC'),(208,'EXPENSE','2018-01-10 23:23:00',000000160.0000,'Planned Payment Expense',95,40,'Car'),(209,'INCOME','2018-01-10 23:27:00',000001000.0000,'Planned Payment Income',93,43,'Salary'),(210,'INCOME','2018-01-10 23:27:00',000000200.0000,'Planned Payment Income',93,50,'TOTO'),(213,'INCOME','2018-01-13 00:00:00',000000350.0000,'dwadsz',95,44,'Rent'),(215,'EXPENSE','2018-01-13 15:56:46',000000008.0000,'Transfer to account Cash',95,39,'Transfer'),(216,'INCOME','2018-01-13 15:56:46',000000008.0000,'Transfer from account Credit card',94,39,'Transfer'),(217,'EXPENSE','2018-01-13 00:00:00',000000120.0000,'parnoto',95,54,'Parno'),(218,'INCOME','2017-12-13 00:00:00',000002000.0000,'xcvbnm',94,45,'Lottery'),(219,'EXPENSE','2017-12-14 00:00:00',000002000.0000,'xcvbnm',94,42,'Cafe');
/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(128) NOT NULL,
  `email` varchar(45) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `password_token` varchar(128) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`),
  UNIQUE KEY `user_name_UNIQUE` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `UKlkvjacpww3vdnd5b9qktoh4i5` (`user_id`,`username`,`email`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (53,'blagoy','cee7472a64b28f09ff70d24c2efd96d1200a800781119a10d53fb5d6cd64ef1642f72c6ff1b89d6ac11915929e6371ef0d5f9835249f44f6fb8145c5f351724e','nikolovblagoy@gmail.com','Blagoy','Nikolov','651b2de6efc1d9004d1ecb3ae99822ef200a410828e77e2c64477c535092f83d043aaa647171495ae890575f0b2aa526f542cba9c207d1cd32ddcdfe40e205a4'),(54,'minka','cee7472a64b28f09ff70d24c2efd96d1200a800781119a10d53fb5d6cd64ef1642f72c6ff1b89d6ac11915929e6371ef0d5f9835249f44f6fb8145c5f351724e','minka@min.ka','Minka','Petrova','27108703e2037ecc99df4b14e564311df50251e003ea7cf99e8f8f60564b465761cf38161a00c36331b835198494438eb6bc6dad76b7668381b87c888697cf49'),(56,'penka','cee7472a64b28f09ff70d24c2efd96d1200a800781119a10d53fb5d6cd64ef1642f72c6ff1b89d6ac11915929e6371ef0d5f9835249f44f6fb8145c5f351724e','penka@pen.ka','Penka','Petrova','4728156f983a3a2dfc65d81d2af916678aa3c2d8f956509bed4f8bd643f8498f4d49d0d0b4402f0ba93aebfcd889830fe0b941f946ff31ebb987b021f115e6a1'),(64,'zina','cee7472a64b28f09ff70d24c2efd96d1200a800781119a10d53fb5d6cd64ef1642f72c6ff1b89d6ac11915929e6371ef0d5f9835249f44f6fb8145c5f351724e','zin@kaa.za','Zinka','Jenatavoin','691363d7658d29858ec46628b0062e7f6ae0f6e1a99b9a756b8627390dedd9c2c4bfffdec4eb2b21fbae8ade0d7fdbf6d7ed5d702bde1fa0544af2ec8f252155'),(65,'maikati','5a9cdc5bc73189d3f2fbc90ab8d0c9fdaca66ff415651590f20f64e8cd048c8f821e272590ddc9aa5956e1338c783dc8522cffe944c31bb27be61d180203618c','maikati@maika.ti','Maika','Tiii','68526df020cb82d9cde5987753d29a67d2e03f1c7499908db6ca04702e4e91bf49e1b8ac1198eff4320b8231d17c3e9ed8d262e04192d3eb00330b7acd8d5604'),(67,'Martin','b75eb3e5f6d67e7d1387b27819e07ef339ee9f1825281c14eb56d55ec726f29608f9d48cb2fac77978019f98cbd27e60830d6c9c87cf27c169016e78e0d9b924','martokooo@abv.bg','Martin','Kostura','f0cf539e97e029587a72bf7f13fbec3003fed125db3825cb99f6620e05c79425555d0c62a5d2399c825cd97666288cd6f86d268c6d0382a0fd9801a6cd0a2060'),(68,'marmot','06971fc3d1b913c4fc6ceefd96ac16a27ef278900a96e0d035de4fc7c20771656bb57a5ad7128b715d5490a15a43094cf05d23b6b021e6ec944e7420dc26d8c3','dimanas@gmail.com','diman','diman','5c2330ddc8d77f26b48660ddd9128db3d06300a35cfa05634df13f4f90c846d5d6bbf4a7e8da34af5c10b47ffa3a4d2c1b49df9b2e1e95b55db048c32e1d4e2a');
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

-- Dump completed on 2018-01-18  0:27:36
