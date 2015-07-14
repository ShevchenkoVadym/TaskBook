-- MySQL dump 10.13  Distrib 5.7.4-m14, for Linux (x86_64)
--
-- Host: localhost    Database: test
-- ------------------------------------------------------
-- Server version	5.7.4-m14

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
-- Table structure for table `UserConnection`
--

DROP TABLE IF EXISTS `UserConnection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserConnection` (
  `userId` varchar(255) NOT NULL,
  `providerId` varchar(255) NOT NULL,
  `providerUserId` varchar(255) NOT NULL,
  `rank` int(11) NOT NULL,
  `displayName` varchar(255) DEFAULT NULL,
  `profileUrl` varchar(512) DEFAULT NULL,
  `imageUrl` varchar(512) DEFAULT NULL,
  `accessToken` varchar(255) NOT NULL,
  `secret` varchar(255) DEFAULT NULL,
  `refreshToken` varchar(255) DEFAULT NULL,
  `expireTime` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`userId`,`providerId`,`providerUserId`),
  UNIQUE KEY `UserConnectionRank` (`userId`,`providerId`,`rank`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserConnection`
--

LOCK TABLES `UserConnection` WRITE;
/*!40000 ALTER TABLE `UserConnection` DISABLE KEYS */;
INSERT INTO `UserConnection` VALUES ('aaa-rusl@yandex.ru','facebook','948279095205987',1,NULL,'http://facebook.com/profile.php?id=948279095205987','http://graph.facebook.com/948279095205987/picture','CAALaVWEcZBG0BAMGTsZAhimO2tB8zkcv8aLfFhtCXzLEsng8IZCbJNZBCdNQZAVXV4w4JqWcVtO4tblW0Mv1MjiCL8Wsvn9tRXGOizcjSZCXDEVZCjdCx6OTEGuGtreTyy9ma6ZA5ZApBHQb4P8rnzvMhM689tY5enbMTTRrhGnZB5HJkFQ9ivJQgk6eg6bUMVnH4TRKDRGINPO8X0LbFHtZCTU',NULL,NULL,1432835051313),('andrievskiy.r@gmail.com','google','106421442264476627630',1,'Ruslan Andrievskiy','https://plus.google.com/106421442264476627630','https://lh6.googleusercontent.com/-JR4O-d9Gs_Y/AAAAAAAAAAI/AAAAAAAAAGI/uNJa-2_TFrI/photo.jpg?sz=50','ya29.RwGR4xHSN6vLtKD1j-oBxWXmt8dEngu0M9KkHYqMIbcLasqJFZnPPKd7kx0hUD2KpS44gY0nwKmzTA',NULL,NULL,1427808572452),('dushen.alexey@gmail.com','google','110306962912057233880',1,'Алексей Дюшен','https://plus.google.com/110306962912057233880','https://lh4.googleusercontent.com/-Ao34wzdyAkk/AAAAAAAAAAI/AAAAAAAAAD8/65n2ooi8-OU/photo.jpg?sz=50','ya29.SQEvmwteOkVOptHZvSDAm4XTWSMF5HzpwH6FpviosdqYp6-Opt0_OoWpRp-_HyYxsHHVGe3NxsUSCQ',NULL,NULL,1427961642688),('j_jordisson@yandex.by','vkontakte','37689824',1,'Александр Титов','http://vk.com/id37689824','http://cs302407.vk.me/u37689824/e_6a93325b.jpg','74aff84d8041213734039631e262be791e7b5f66cb0c83f7a46742b5917a63320361d99f8f3e1d752136c',NULL,NULL,1427739271448),('larsulrich9999@gmail.com','google','109829763401445122895',1,'Александр Титов','https://plus.google.com/109829763401445122895','https://lh3.googleusercontent.com/-XdUIqdMkCWA/AAAAAAAAAAI/AAAAAAAAAAA/4252rscbv5M/photo.jpg?sz=50','ya29.SAFAC0F_LfP7DKgwmCUkDPnHbh9xI9Q6x6KQr5FPmU5pVX8twD4jVd1-BC1xAJrVKmMaeO4fmS4hlw',NULL,NULL,1427921800451),('lay_o@yandex.by','vkontakte','8632738',1,'Руслан Андриевский','http://vk.com/id8632738','http://cs425924.vk.me/v425924738/66e3/ZEvYabmBcCU.jpg','4fdbd11008165a5a0395bcbe3f7563f2f2180aa24453e6ca1405e5d257f6fc190ea13c23e37658db75814',NULL,NULL,1427739261681),('letitbe400@gmail.com','google','109193114791459820056',1,'Полярный Лис','https://plus.google.com/109193114791459820056','https://lh4.googleusercontent.com/-kraObzCwZwU/AAAAAAAAAAI/AAAAAAAAADo/zMF9ngbD_rM/photo.jpg?sz=50','ya29.RQGllOSZA13puq3-XZ7amt0ShPTWZCiuFsGd1LbMya_GT45h9SsE-aNw3l9EmRg5ZB3zRUZ1JAbadw',NULL,NULL,1427656272302),('serezhkaserezhkavich@yandex.by','vkontakte','5637692',1,'Сережка Серёжкавич','http://vk.com/id5637692','http://cs606128.vk.me/v606128692/1cfd/kJ9hLHk6BCk.jpg','670f62ea61857416a8e8e5744af62a0439e225557eabe9a09dd40c8b2df84be4d281bc51815005dc358f2',NULL,NULL,1427604826634),('spawnkiev@yandex.by','vkontakte','8185302',1,'Вадим Шевченко','http://vk.com/id8185302','http://cs617523.vk.me/v617523302/16694/xRHTXP1zElk.jpg','c02d3c721bf9f74376befd76fcfe6e52e3bf9d6f756b9de691adeb9e0f8ed62c08160bd63b0cd132a49ca',NULL,NULL,1427295207798),('stanislavponomarev@yandex.by','vkontakte','65971588',1,'Станислав Пономарёв','http://vk.com/id65971588','http://cs625229.vk.me/v625229588/e679/tjGYRMW1feM.jpg','d71ffd073fd0f650c0ae607944093ee401d4ad57a4bf8d34179c5fe0b9899c2cfc045b83b7c3f9eb4cd71',NULL,NULL,1427353649010);
/*!40000 ALTER TABLE `UserConnection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `user_id` int(10) NOT NULL,
  `role_id` int(10) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `USER` (`user_id`),
  CONSTRAINT `USER` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (100000,0),(100001,0),(100002,2),(100003,1),(100004,0),(100019,2),(100020,2),(100021,2),(100022,2),(100023,0),(100023,1),(100023,2),(100024,2),(100025,0),(100025,1),(100025,2),(100028,2),(100029,2),(100030,2),(100031,2),(100032,2),(100033,2);
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_tasks`
--

DROP TABLE IF EXISTS `user_tasks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_tasks` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL,
  `task_id` varchar(100) NOT NULL,
  `tryies_count` int(10) DEFAULT '0',
  `date_solve` timestamp NULL DEFAULT NULL,
  `status` bit(1) DEFAULT b'0',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `task_id` (`task_id`),
  CONSTRAINT `tasks_ibfk1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_tasks`
--

LOCK TABLES `user_tasks` WRITE;
/*!40000 ALTER TABLE `user_tasks` DISABLE KEYS */;
INSERT INTO `user_tasks` VALUES (1,100019,'550fea49975a14227fc74d45',3,NULL,'\0'),(2,100020,'550feee6975a172bf993631d',1,NULL,'\0'),(3,100020,'550fea49975a14227fc74d45',1,NULL,'\0'),(4,100019,'551134fc975accd1f04e8609',2,NULL,'\0'),(5,100021,'550fea49975a14227fc74d3f',1,'2015-03-25 07:13:31',''),(6,100023,'55107f3444ae143d89176e8b',1,'2015-03-27 22:43:49',''),(7,100023,'55107f3444ae143d89176e95',1,NULL,'\0'),(8,100023,'55107f3444ae143d89176e8c',1,'2015-03-27 22:45:25',''),(9,100023,'55107f3444ae143d89176e8d',1,'2015-03-27 22:45:42',''),(10,100023,'55107f3444ae143d89176e8e',2,NULL,'\0'),(11,100023,'55107f3444ae143d89176e94',1,'2015-03-27 22:46:37',''),(12,100023,'55107f3444ae143d89176e8f',1,'2015-03-27 22:46:49',''),(13,100023,'55107f3444ae143d89176e90',1,'2015-03-27 22:47:30',''),(14,100023,'55107f3444ae143d89176e91',1,'2015-03-27 22:47:58',''),(15,100023,'55107f3444ae143d89176e96',1,'2015-03-27 22:48:21',''),(16,100023,'55107f3444ae143d89176e93',2,'2015-03-27 22:48:47',''),(17,100025,'55107f3444ae143d89176e95',16,'2015-03-29 13:10:32',''),(18,100025,'55107f3444ae143d89176e8c',2,'2015-03-28 21:13:33',''),(19,100025,'55107f3444ae143d89176e8d',2,'2015-03-28 21:14:09',''),(20,100025,'55107f3444ae143d89176e8e',4,'2015-03-29 13:15:08',''),(21,100025,'55107f3444ae143d89176e93',3,'2015-03-29 13:02:28',''),(22,100025,'55107f3444ae143d89176e8b',3,'2015-03-29 12:48:02',''),(23,100025,'55107f3444ae143d89176e94',3,'2015-03-29 12:57:25',''),(24,100025,'55107f3444ae143d89176e96',2,'2015-03-29 12:59:15',''),(25,100025,'55107f3444ae143d89176e8f',3,'2015-03-29 13:03:59',''),(26,100025,'55107f3444ae143d89176e90',1,'2015-03-29 13:04:43',''),(27,100000,'55107f3444ae143d89176e95',2,'2015-03-29 13:10:06',''),(28,100025,'55107f3444ae143d89176e91',3,'2015-03-29 13:13:08',''),(29,100000,'55107f3444ae143d89176e8c',1,'2015-03-29 16:12:48',''),(30,100000,'55107f3444ae143d89176e8b',8,'2015-03-29 17:26:18',''),(31,100000,'55107f3444ae143d89176e8f',1,'2015-03-29 18:38:21',''),(32,100003,'55107f3444ae143d89176e95',1,'2015-03-30 20:41:25',''),(33,100000,'5519b5de975a4b934e2a9db8',2,'2015-03-31 08:20:05',''),(34,100029,'55107f3444ae143d89176e95',1,'2015-03-31 12:30:54',''),(35,100000,'55107f3444ae143d89176e93',4,'2015-04-01 19:26:23',''),(36,100003,'55107f3444ae143d89176e93',7,'2015-04-01 19:30:53',''),(38,100032,'55107f3444ae143d89176e93',5,'2015-04-01 19:58:44',''),(39,100003,'5519b5de975a4b934e2a9db8',2,'2015-04-01 20:04:00',''),(40,100000,'551819d544aeaa7d70c87732',2,'2015-04-01 21:26:44',''),(41,100003,'55107f3444ae143d89176e8b',1,'2015-04-02 08:24:21',''),(42,100000,'55107f3444ae143d89176e96',1,'2015-04-02 08:48:59',''),(43,100000,'55107f3444ae143d89176e94',1,'2015-04-02 19:28:41',''),(44,100000,'55107f3444ae143d89176e8d',1,'2015-04-02 19:29:10',''),(45,100000,'55107f3444ae143d89176e90',3,'2015-04-02 19:32:51',''),(46,100000,'55107f3444ae143d89176e91',2,'2015-04-02 19:50:35','');
/*!40000 ALTER TABLE `user_tasks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(150) NOT NULL,
  `is_enabled` bit(1) NOT NULL,
  `is_confirmed` bit(1) NOT NULL,
  `is_non_read_only` bit(1) NOT NULL,
  `creation_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `email` varchar(50) NOT NULL,
  `country` varchar(100) NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `rating` double DEFAULT '0',
  `tasks_solved` int(10) DEFAULT '0',
  `sign_in_provider` varchar(50) DEFAULT NULL,
  `token_confirmation` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=100034 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (100000,'alex','$2a$10$D2PalsJX68JeMPh0DVfnneugKjHGJMTLRIenDWjI5WsnJqman3w8O','','','\0','2015-04-02 19:24:42','letitbe400@gmail.com','Russian Federation','',101.12,12,NULL,NULL),(100001,'Brynne','$2a$10$hwoWc2RZtgUUihDrFSvffu6mG.Gaw78UR7vnP.ZQRlGg6DVdbY2RO','','','2015-03-29 16:23:10','sashok@mail.ru','Russian Federation','',14,0,NULL,NULL),(100002,'Vivian','$2a$10$C2b3XAQHI6UwB.bEMIvGT.O7zP7L1kzf8OuSktBEEX4EAdP1M3f2W','\0','\0','2015-03-29 16:23:10','beast@mail.ru','United States of America(USA)','',13,0,NULL,NULL),(100003,'mary','$2a$10$PYaAiEwiMLVIQKmns0wrPOyFM/rrwWUTeMFuD/MJBSDv8qlRD4AWK','','\0','2015-04-02 19:26:27','mary@mail.ru','Germany','',12.47,4,NULL,NULL),(100004,'Christopher','$2a$10$G0fwKDq8xe913b8aitEKC.Ykeu/RizvhGMccT1QKxLL7RHJxlCMbu','','\0','2015-03-29 16:23:10','christopher@mail.ru','France','',11,0,NULL,NULL),(100005,'Thor','$2a$10$G0fwKDq8xe913b8aitEKC.Ykeu/RizvhGMccT1QKxLL7RHJxlCMbu','','\0','2015-03-29 16:23:10','quam@at.edu','Portugal','',13,0,NULL,NULL),(100006,'Jasmine','$2a$10$G0fwKDq8xe913b8aitEKC.Ykeu/RizvhGMccT1QKxLL7RHJxlCMbu','','\0','2015-03-29 16:23:10','natoque.penatibus@cursusdiam.edu','Tanzania','',19,0,NULL,NULL),(100007,'Olympia','$2a$10$G0fwKDq8xe913b8aitEKC.Ykeu/RizvhGMccT1QKxLL7RHJxlCMbu','','\0','2015-03-29 16:23:10','a@elit.org','Indonesia','',16,0,NULL,NULL),(100008,'Cecilia','$2a$10$G0fwKDq8xe913b8aitEKC.Ykeu/RizvhGMccT1QKxLL7RHJxlCMbu','','\0','2015-03-29 16:23:10','diam@vehiculaaliquetlibero.co.uk','Taiwan','',15,0,NULL,NULL),(100009,'Dillon','$2a$10$G0fwKDq8xe913b8aitEKC.Ykeu/RizvhGMccT1QKxLL7RHJxlCMbu','','\0','2015-03-29 16:23:10','gravida.Praesent@aliquetProin.edu','Monaco','',18,0,NULL,NULL),(100010,'Merritt','$2a$10$G0fwKDq8xe913b8aitEKC.Ykeu/RizvhGMccT1QKxLL7RHJxlCMbu','','\0','2015-03-29 16:23:10','In.scelerisque@sitametornare.com','Armenia','',14,0,NULL,NULL),(100011,'Duncan','$2a$10$G0fwKDq8xe913b8aitEKC.Ykeu/RizvhGMccT1QKxLL7RHJxlCMbu','\0','\0','2015-03-29 16:23:10','Donec.feugiat@dui.net','China','',19,0,NULL,NULL),(100012,'Roth','$2a$10$G0fwKDq8xe913b8aitEKC.Ykeu/RizvhGMccT1QKxLL7RHJxlCMbu','','\0','2015-03-29 16:23:10','turpis@cubilia.com','Burundi','',12,0,NULL,NULL),(100013,'Benjamin','$2a$10$G0fwKDq8xe913b8aitEKC.Ykeu/RizvhGMccT1QKxLL7RHJxlCMbu','','\0','2015-03-29 16:23:10','nascetur.ridiculus.mus@necleoMorbi.org','Romania','',13,0,NULL,NULL),(100014,'Rooney','$2a$10$G0fwKDq8xe913b8aitEKC.Ykeu/RizvhGMccT1QKxLL7RHJxlCMbu','','\0','2015-03-29 16:23:10','porta@arcuimperdiet.co.uk','Greenland','',12,0,NULL,NULL),(100015,'Davis','$2a$10$G0fwKDq8xe913b8aitEKC.Ykeu/RizvhGMccT1QKxLL7RHJxlCMbu','','\0','2015-03-29 16:23:10','In.nec.orci@ac.net','Palau','',18,0,NULL,NULL),(100016,'Macon','$2a$10$G0fwKDq8xe913b8aitEKC.Ykeu/RizvhGMccT1QKxLL7RHJxlCMbu','','\0','2015-03-29 16:23:10','quis@estNunc.edu','Spain','',16,0,NULL,NULL),(100017,'Timothy','$2a$10$G0fwKDq8xe913b8aitEKC.Ykeu/RizvhGMccT1QKxLL7RHJxlCMbu','','\0','2015-03-29 16:23:10','eu.nulla.at@Nunc.org','Panama','',16,0,NULL,NULL),(100018,'Cedric','$2a$10$G0fwKDq8xe913b8aitEKC.Ykeu/RizvhGMccT1QKxLL7RHJxlCMbu','','\0','2015-03-29 16:23:10','nunc@accumsansed.edu','Norway','',11,0,NULL,NULL),(100019,'Valnistuy','$2a$10$dGpNqKjlSRKueQgSy2AVkuanEcY11uqUh7O32wqtkZIwSROke4CZ6','','\0','2015-04-02 19:26:33','valnistuy@gmail.ru','Russian Federation','',0,2,NULL,NULL),(100020,'spawnkiev','c02d3c721bf9f74376befd76fcfe6e52e3bf9d6f756b9de691adeb9e0f8ed62c08160bd63b0cd132a49ca','','\0','2015-04-02 19:26:33','spawnkiev@yandex.by','','http://cs617523.vk.me/v617523302/16694/xRHTXP1zElk.jpg',0,2,'VKONTAKTE',NULL),(100021,'shaman','$2a$10$L9gVHLmQeV2WPRBYU9ZgRu0a3bGgBy.a7GSHU5ysbocXth..5zcA.','','\0','2015-04-02 19:25:47','ambassador00@bk.ru','','',0.05,1,NULL,NULL),(100022,'stanislavponomarev','d71ffd073fd0f650c0ae607944093ee401d4ad57a4bf8d34179c5fe0b9899c2cfc045b83b7c3f9eb4cd71','','\0','2015-03-25 07:07:29','stanislavponomarev@yandex.by','','http://cs625229.vk.me/v625229588/e679/tjGYRMW1feM.jpg',0,0,'VKONTAKTE',NULL),(100023,'newcomer','$2a$10$gjXoOatv39Vfn2ProzRTa.lb5MomiOzv/r2fCopR2sVA07CHwp3jK','','\0','2015-04-02 19:25:18','test@example.com','Colombia','http://www.deviantsart.com/3dfqruk.jpg',0.95,11,NULL,NULL),(100024,'serezhkaserezhkavich','670f62ea61857416a8e8e5744af62a0439e225557eabe9a09dd40c8b2df84be4d281bc51815005dc358f2','','\0','2015-03-28 04:53:50','serezhkaserezhkavich@yandex.by','','http://cs606128.vk.me/v606128692/1cfd/kJ9hLHk6BCk.jpg',0,0,'VKONTAKTE',NULL),(100025,'dushenalexey','ya29.RAGTIzSZ9MAbqry8J29HoytXHGFE4lZQ8aFzBOhr2pmE3hfTBerFpOs0e9zH4_uToXX3Ug6Z_h6pBQ','','\0','2015-04-02 19:25:18','dushen.alexey@gmail.com','Russian Federation','https://lh4.googleusercontent.com/-Ao34wzdyAkk/AAAAAAAAAAI/AAAAAAAAAD8/65n2ooi8-OU/photo.jpg?sz=50',1.03,11,'GOOGLE',NULL),(100028,'aaarusl','CAALaVWEcZBG0BAPeWv8TfEDZAixhFz2eZAU0hm2nP3cCX9gZC8o1oCcF3eF81bdIsSPiS2wQB9ePQuKUvPD0yDmGQZCPYcdZAZCNWSWPA0YDIkZBRmcxDMG9r75ey5lNZCENl4HjPRL','','\0','2015-03-29 17:44:19','aaa-rusl@yandex.ru','','http://graph.facebook.com/948279095205987/picture',0,0,'FACEBOOK',NULL),(100029,'andrievskiyr','ya29.RQFoVwt2oKfWLtG0Ry9631Oi1XBsjDifJ-uqVuYuuVe1UlrkU21E9jJvsBKl_mvyyNezTpEzm_K8WQ','','\0','2015-04-02 19:24:55','andrievskiy.r@gmail.com','','https://lh6.googleusercontent.com/-JR4O-d9Gs_Y/AAAAAAAAAAI/AAAAAAAAAGI/uNJa-2_TFrI/photo.jpg?sz=50',0.15,1,'GOOGLE',NULL),(100030,'layo','$2a$10$NdtnH5yj7QkkM6P0aPvzNOzq.25kC64bUBIixG2rXoIIIq6KLNeTS','','\0','2015-03-29 17:50:13','layoua@gmail.com','','',0,0,NULL,NULL),(100031,'layo4','f1b74db4e7411102b923b8dfcb60093fa7d91c78f11769e88847b03981d6fc544fc027ad9a2e4586beb18','','\0','2015-03-29 17:56:08','lay_o@yandex.by','','http://cs425924.vk.me/v425924738/66e3/ZEvYabmBcCU.jpg',0,0,'VKONTAKTE',NULL),(100032,'larsulrich9999','$2a$10$y6abWmpbhPRW8iarvlCxP.3sMe2FkU9.tiM6dqcdLI2ZlPh58Qlre','','\0','2015-04-02 19:25:47','larsulrich9999@gmail.com','','',0.05,1,NULL,NULL),(100033,'alex111','$2a$10$coHUOrz2paU14zlGQ1vWkeVxkIpf90AlKnND.p4xD06gjD02ZxQAK','','\0','2015-04-02 06:36:56','erty_90@inbox.ru','','',0,0,NULL,NULL);
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

-- Dump completed on 2015-04-02 19:52:17
