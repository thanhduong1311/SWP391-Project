-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: swp391homemate
-- ------------------------------------------------------
-- Server version	8.1.0

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
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `id` int NOT NULL AUTO_INCREMENT,
  `password` varchar(255) DEFAULT NULL,
  `role` smallint DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_gfn44sntic2k93auag97juyij` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (7,'0e7517141fb53f21ee439b355b5a1d0a',0,'AdminHomemate01'),(8,'0e7517141fb53f21ee439b355b5a1d0a',0,'AdminHomemate02');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `customer_id` int NOT NULL AUTO_INCREMENT,
  `account_status` smallint DEFAULT NULL,
  `address_detail` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `balance` double NOT NULL,
  `city` varchar(255) DEFAULT NULL,
  `create_at` datetime(6) DEFAULT NULL,
  `district` varchar(255) DEFAULT NULL,
  `dob` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `role` smallint DEFAULT NULL,
  `total_spend` double NOT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`customer_id`),
  UNIQUE KEY `UK_dwk6cx0afu8bs9o4t536v1j5v` (`email`),
  UNIQUE KEY `UK_o3uty20c6csmx5y4uk2tc5r4m` (`phone`),
  UNIQUE KEY `UK_irnrrncatp2fvw52vp45j7rlw` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,0,'Hem 51, duong 3 thang 2, Hung Loi','/assets/images/customer/AVTCV.png',0,'Can Tho','2023-11-05 11:46:01.555000','Ninh Kieu','2002-11-13 00:00:00.000000','thanhduongjnguyen@gmail.com','Nguyen Thanh Duong',NULL,'d8db8aa001bb5df03e525ef15ddfc050','0813993149',1,23,'2023-11-05 11:46:01.555000','thanhduong01'),(2,0,'Trần Hoàng Na, Hưng Lợi','/assets/images/customer/MRV_20220531_16_08_00.jpg',12.63,'Cần Thơ','2023-11-05 13:03:45.121000','Ninh Kiều','2000-01-01 00:00:00.000000','giahuysangtao@gmail.com','Huy Tran',NULL,'04b93e6985938ba6fef3a74161af20d3','0813559756',1,2.5,'2023-11-05 13:03:45.121000','giahuy123'),(3,0,'160 Tam Vu, Hung Loi','/assets/images/customer/HomeMate.png',0,'Cần Thơ','2023-11-05 13:05:54.758000','Ninh Kiều','2003-01-01 00:00:00.000000','Minhnhut01@email.com','Tran Minh Nhut',NULL,'0d3166837389ca6387f1740d12e16bfa','0945887682',1,0,'2023-11-05 13:05:54.758000','Minhnhut01'),(4,0,' 36, Nguyễn Đệ, Hưng Lợi','/assets/images/customer/f0a83150fd76a056aad42ae690bfd45b.jpg',0,'Cần Thơ','2023-11-05 13:07:15.947000','Ninh Kiều','2000-01-01 00:00:00.000000','Kimngan01@gmail.com','Kim Ngan To Cao',NULL,'71f18b0e42c593bfbac98aeb25bdb656','0945668897',1,0,'2023-11-05 13:07:15.947000','Kimngan01'),(8,1,'7 Le Van So, An Thoi','/assets/images/defaultUser.png',0,'Can Tho','2023-10-26 22:09:14.375000','Binh Thuy','1999-06-07 00:00:00.000000','tgiahan076@gmail.com','Truong Gia Han',NULL,'0c436d017d3912713062ae0c51607546','0879519765',1,0,'2023-10-26 22:09:14.375000','tghan'),(9,1,'379 Ap Thoi, Dong Hoa','/assets/images/defaultUser.png',0,'Tien Giang','2023-10-27 00:06:21.898000','Chau Thanh','1996-06-06 00:00:00.000000','linhchi6629@gmail.com','Linh Chi  Dang',NULL,'e68464da238ccebe72440d78121ea553','0896634012',1,0,'2023-10-27 00:06:21.898000','linhchi6629'),(10,0,'216 Tây Thạnh','/assets/images/defaultUser.png',0,'Hồ Chí Minh','2023-10-27 00:14:17.141000','Tân Phú','2000-06-04 00:00:00.000000','lgbao6400@gmail.com','Gia Bảo Lưu ',NULL,'4add0b20e05583e99f60fa549771081e','0879145865',1,0,'2023-10-27 00:14:17.141000','lgbao6400'),(11,0,'2 Lưu Hữu Phước, Mỹ Đình','/assets/images/defaultUser.png',0,'Hà Nội','2023-10-27 00:28:01.136000','Từ Liêm','1996-08-15 00:00:00.000000','bichngoc2586@gmail.com','Trịnh Bích Ngọc',NULL,'6860185ea9ebf30ccdc36157f7e7d6b4','0377301814',1,0,'2023-10-27 00:28:01.136000','bichngoc2586'),(12,0,'15 Chi Lăng, Đông Hồ','/assets/images/defaultUser.png',0,'Kiên Giang','2023-10-27 10:01:18.910000','Hà Tiên','2003-03-03 00:00:00.000000','kim333huyen@gmail.com','Đỗ Kim Huyền',NULL,'0e35dfc923e748610b4e98275222feaf','0927897351',1,0,'2023-10-27 10:01:18.910000','k.huyen'),(13,0,'6 Đặng Thùy Trâm, Hòa Thuận Nam','/assets/images/defaultUser.png',0,'Đà Nẵng','2023-10-27 11:19:19.914000','Hải Châu','1980-04-18 00:00:00.000000','xcao1804@gmail.com','Cao Xuân Mai',NULL,'cc68e06238661d5ebe05ba71022b57b4','0385418890',1,0,'2023-10-27 11:19:19.914000','xcao'),(14,0,'197 Nguyễn Trãi','/assets/images/defaultUser.png',0,'Sóc Trăng','2023-10-27 15:40:48.387000','Ngã Năm','2004-05-25 00:00:00.000000','hvanh2505@gmail.com','Hoàng Việt Anh',NULL,'e9b28a2c98433d4be08477dd802ef40e','0775331999',1,0,'2023-10-27 15:40:48.387000','hvanh'),(15,0,'11 Đường Số 14, Phường 5','/assets/images/defaultUser.png',0,'Bạc Liêu','2023-10-28 13:31:42.416000','Bạc Liêu','2002-02-21 00:00:00.000000','vhdang0201@gmail.com','Võ Hải Đăng',NULL,'f170f68485a4967cffb11a0bab638953','0568808351',1,0,'2023-10-28 13:31:42.416000','vhd'),(16,0,'333 Nguyễn Văn Linh, An Khánh','/assets/images/defaultUser.png',0,'Cần Thơ','2023-10-28 13:52:12.027000','Ninh Kiều','2001-10-15 00:00:00.000000','danhtieuhuyen@gmail.com','Danh Tiểu Huyền',NULL,'5e777b75170d5b3855f80dea3df55421','0975944344',1,0,'2023-10-28 13:52:12.027000','huyendt'),(17,0,'110a Phạm Văn Đồng, Phường 3','/assets/images/defaultUser.png',0,'Hồ Chí Minh','2023-10-28 13:55:49.056000','Gò Vấp','1995-12-07 00:00:00.000000','hbtuyen7@gmail.com','Hứa Bích Tuyền',NULL,'7e599d8c781ffa0b9d3734e2ee85e57f','0868686819',1,0,'2023-10-28 13:55:49.056000','tuyenhb');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `employee_id` int NOT NULL AUTO_INCREMENT,
  `account_status` smallint DEFAULT NULL,
  `address_detail` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `balance` double NOT NULL,
  `city` varchar(255) DEFAULT NULL,
  `create_at` datetime(6) DEFAULT NULL,
  `district` varchar(255) DEFAULT NULL,
  `dob` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `id_card_number` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `role` smallint DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `work_place` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`employee_id`),
  UNIQUE KEY `UK_fopic1oh5oln2khj8eat6ino0` (`email`),
  UNIQUE KEY `UK_im8flsuftl52etbhgnr62d6wh` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,0,'8A, Lý Thái Tổ, Hưng Phú','/assets/images/employee/hinh-anh-nen-con-meo-cute.jpg',9.5,'Cần Thơ','2023-11-05 13:13:00.917000','Cái Răng','2002-01-01 00:00:00.000000','pkhoi@email.com','Phạm Khôi','male','372099513','712a1210470e8fd782a9e2976c42eec1','0936552789',2,'2023-11-06 16:40:21.131000','KhoiP01','Cần Thơ'),(2,0,'160, 30/4, Hưng Lợi','/assets/images/defaultUser.png',0,'Cần Thơ','2023-11-05 13:17:40.507000','Ninh Kieu','2001-01-01 00:00:00.000000','pvu@email.com','Vũ Phạm','male','848565778','69a4a570298a3f351e33271612f86e5e','0845665731',2,'2023-11-05 13:17:40.507000','VuP01','Cần Thơ'),(12,1,'Hem 216, Tam Vu, Hung Loi','/assets/images/defaultUser.png',11.3726,'Can Tho','2023-10-26 22:20:03.760000','Ninh Kieu','2004-01-09 00:00:00.000000','giahuy01@gmail.com','Tran Ha Gia Huy','male','372536119','ec5d79520da53321e578f302fb2b6c9c','0973988349',2,'2023-10-26 22:20:03.760000','giahuy01','Can Tho'),(14,2,'Hòn Đất','/assets/images/defaultUser.png',0,'Kiên Giang','2023-10-27 00:47:25.927000','Ninh Kieu','2000-12-01 00:00:00.000000','duongjnguyen@gmail.com','Dương Nguyễn','male','225668445','d8db8aa001bb5df03e525ef15ddfc050','0813222249',2,'2023-10-27 00:47:25.927000','thanhduong01333','Can Tho'),(15,0,'7 Le Van So, An Thoi','/assets/images/defaultUser.png',0,'Can Tho','2023-10-27 00:50:18.298000','Binh Thuy','1998-01-12 00:00:00.000000','duongngocmaianh1@gmail.com','Ngọc Mai Anh Dương','female','091303001588','0c195af5cab6171342e88ae5f39642f5','0395692400',2,'2023-10-27 00:50:18.298000','dnmaianh','Can Tho'),(16,0,'Cô Bắc','/assets/images/defaultUser.png',0,'Kiên Giang','2023-10-27 10:06:14.184000','Rạch Giá ','1995-05-05 00:00:00.000000','nnthiennhi55@gmail.com','Nguyễn Ngọc Thiên Nhi','female','091303045288','d89198f27c7827b6f4a49fcb06df9378','0876648423',2,'2023-10-27 10:06:14.184000','nntnhi','Kiên Giang'),(17,0,'Vĩnh Kim','/assets/images/defaultUser.png',0,'Tiền Giang','2023-10-27 10:11:55.630000','Châu Thành','1998-02-23 00:00:00.000000','bdmylinh@gmail.com','Bùi Đặng Mỹ Linh','female','091303007428','0df8c4cd48dbbdf2815940ae676583fc','0363800518',2,'2023-10-27 10:11:55.630000','mlinh','Tiền Giang'),(18,0,'Tây Thạnh','/assets/images/defaultUser.png',0,'Hồ Chí Minh','2023-10-27 11:23:43.556000','Tân Phú','2002-01-24 00:00:00.000000','duykhanh24102@gmail.com','Dương Duy Khánh','male','091303007142','d2ed4d6fc3b3578153a02014b10855b7','0898568758',2,'2023-10-27 11:23:43.556000','dkhanh','Hồ Chí Minh'),(19,0,'36 Nguyễn Hoàng, Mỹ Đình','/assets/images/defaultUser.png',0,'Hà Nội','2023-10-27 14:29:25.469000','Nam Từ Liên','1996-08-08 00:00:00.000000','ghnam8896@gmail.com','Giang Hải Nam','male','091302355288','345eae2ca29839f28b0d9bf109d50e21','0587818864',2,'2023-10-27 14:29:25.469000','ghnam','Hà Nội'),(20,0,'85 Duy Tân, Hòa Thuận Nam','/assets/images/defaultUser.png',0,'Đà Nẵng','2023-10-27 14:36:10.699000','Hải Châu','1999-03-12 00:00:00.000000','lngocchau99@gmail.com','Lâm Ngọc Châu','female','091303003452','6108378ec0f238df53284618fb7f4682','0777779312',2,'2023-10-27 14:36:10.699000','lnchau','Đà Nẵng'),(21,0,'Thuận Hưng','/assets/images/defaultUser.png',0,'Sóc Trăng','2023-10-27 15:39:30.005000','Mỹ Tú','1993-06-28 00:00:00.000000','hangnt280693@gmail.com','Ngô Thúy Hằng','female','091301231588','b31949e53c494f52c5c05e834537ea78','0796416888',2,'2023-10-27 15:39:30.005000','hangnt','Sóc Trăng'),(22,2,'Châu Hưng','/assets/images/defaultUser.png',0,'Bạc Liêu','2023-10-27 15:45:00.274000','Vĩnh Lợi','1990-01-01 00:00:00.000000','mtct11@gmail.com','Mai Thị Cẩm Tú','female','091301231875','f126ea15e5ad5db6eac130fca5ec0743','0783223344',2,'2023-10-27 15:45:00.274000','mtct','Bạc Liêu'),(23,2,'516f Trương Vĩnh Nguyên, Thường Thạnh','/assets/images/defaultUser.png',0,'Cần Thơ','2023-10-28 14:40:31.357000','Cái Răng','1998-08-08 00:00:00.000000','tuyetvydo@gmail.com','Đỗ Tuyết Vy ','female','0847836886','41894f9dc6beb32c3ff5878310ccab33','0847836886',2,'2023-10-28 14:40:31.357000','vyvy','Cần Thơ'),(24,2,'04 Lương Văn Nho, Phường 9','/assets/images/defaultUser.png',0,'Bà Rịa - Vũng Tàu','2023-10-28 14:44:49.711000','Vũng Tàu','1993-11-18 00:00:00.000000','haiyen181193@gmail.com','Đặng Hải Yến','female','091303043142','f22203868abc5614ff5d26a9d55eaa64','0974408880',2,'2023-10-28 14:44:49.711000','yendh','Bà Rịa - Vũng Tàu');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee_request`
--

DROP TABLE IF EXISTS `employee_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee_request` (
  `request_id` int NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `decision_at` datetime(6) DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `status` smallint DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `employee_id` int DEFAULT NULL,
  `job_id` int DEFAULT NULL,
  PRIMARY KEY (`request_id`),
  KEY `FKe10ncko4by0w1w1aac42yao4d` (`employee_id`),
  KEY `FK1cycbo3x3wxngfgfjmi8yy52h` (`job_id`),
  CONSTRAINT `FK1cycbo3x3wxngfgfjmi8yy52h` FOREIGN KEY (`job_id`) REFERENCES `job` (`job_id`),
  CONSTRAINT `FKe10ncko4by0w1w1aac42yao4d` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`employee_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee_request`
--

LOCK TABLES `employee_request` WRITE;
/*!40000 ALTER TABLE `employee_request` DISABLE KEYS */;
INSERT INTO `employee_request` VALUES (5,'2023-11-06 16:25:54.284000',NULL,'Xa quá em không có xe đi được mà lỡ nhận rồi cho em hủy được không ạ. Cảm ơn ad rất nhiều.',0,'2023-11-06 16:25:54.284000',1,7),(6,'2023-11-06 17:23:48.530000','2023-11-06 17:24:04.596000','Em muốn hủy job, này ạ.',1,'2023-11-06 17:23:48.530000',2,8);
/*!40000 ALTER TABLE `employee_request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedback` (
  `feedback_id` int NOT NULL AUTO_INCREMENT,
  `detail` varchar(255) DEFAULT NULL,
  `point` int NOT NULL,
  `customer_id` int DEFAULT NULL,
  `job_id` int DEFAULT NULL,
  PRIMARY KEY (`feedback_id`),
  KEY `FKpi2y2j7n01ypo49fone3knjry` (`customer_id`),
  KEY `FKeae0989o3xxvyx6esb3dnadbb` (`job_id`),
  CONSTRAINT `FKeae0989o3xxvyx6esb3dnadbb` FOREIGN KEY (`job_id`) REFERENCES `job` (`job_id`),
  CONSTRAINT `FKpi2y2j7n01ypo49fone3knjry` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback`
--

LOCK TABLES `feedback` WRITE;
/*!40000 ALTER TABLE `feedback` DISABLE KEYS */;
INSERT INTO `feedback` VALUES (4,'Veru good man',5,1,1),(5,'So good',5,1,2);
/*!40000 ALTER TABLE `feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `income`
--

DROP TABLE IF EXISTS `income`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `income` (
  `income_id` int NOT NULL AUTO_INCREMENT,
  `amount` double DEFAULT NULL,
  `create_at` datetime(6) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `employee_id` int DEFAULT NULL,
  `job_id` int DEFAULT NULL,
  PRIMARY KEY (`income_id`),
  KEY `FKevjfnwv5rp70ajrfda6417iyc` (`employee_id`),
  KEY `FK5wm2rgonc8m7tx3qirmflye3k` (`job_id`),
  CONSTRAINT `FK5wm2rgonc8m7tx3qirmflye3k` FOREIGN KEY (`job_id`) REFERENCES `job` (`job_id`),
  CONSTRAINT `FKevjfnwv5rp70ajrfda6417iyc` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`employee_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `income`
--

LOCK TABLES `income` WRITE;
/*!40000 ALTER TABLE `income` DISABLE KEYS */;
INSERT INTO `income` VALUES (5,1.715,'2023-11-06 16:28:47.868000',NULL,'2023-11-06 16:28:47.868000',1,1),(6,-0.15,'2023-11-06 16:29:22.972000',NULL,'2023-11-06 16:29:22.972000',1,3);
/*!40000 ALTER TABLE `income` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job`
--

DROP TABLE IF EXISTS `job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job` (
  `job_id` int NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `end` datetime(6) DEFAULT NULL,
  `job_address` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `payment_type` smallint DEFAULT NULL,
  `start` datetime(6) DEFAULT NULL,
  `status` smallint DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `customer_id` int DEFAULT NULL,
  `employee_id` int DEFAULT NULL,
  `service_id` int DEFAULT NULL,
  PRIMARY KEY (`job_id`),
  KEY `FKrvifgogqkmjsw51g2t3j7t0r3` (`customer_id`),
  KEY `FKkbfwqadwjxuh6vqgis77q7c7f` (`employee_id`),
  KEY `FKex4smot1h3p88v8gqmxpjij2` (`service_id`),
  CONSTRAINT `FKex4smot1h3p88v8gqmxpjij2` FOREIGN KEY (`service_id`) REFERENCES `service` (`service_id`),
  CONSTRAINT `FKkbfwqadwjxuh6vqgis77q7c7f` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`employee_id`),
  CONSTRAINT `FKrvifgogqkmjsw51g2t3j7t0r3` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job`
--

LOCK TABLES `job` WRITE;
/*!40000 ALTER TABLE `job` DISABLE KEYS */;
INSERT INTO `job` VALUES (1,'2023-11-05 13:32:41.847000','Cleaning house and garden','2023-11-01 15:30:00.000000',NULL,'10.0084,105.7493',0,'2023-11-01 15:00:00.000000',2,'2023-11-05 13:32:41.847000',1,1,2),(2,'2023-11-05 13:35:07.412000','Giữ bé cho anh đi công việc có người ở nhà nhưng không rảnh để trông bé','2023-11-01 10:05:00.000000',NULL,'10.0377,105.7875',0,'2023-11-01 08:35:00.000000',1,'2023-11-05 13:35:07.412000',1,1,1),(3,'2023-11-05 13:37:00.013000','Giặc quần áo cũ','2023-11-02 09:10:00.000000',NULL,'10.0587,105.7116',1,'2023-11-02 06:40:00.000000',2,'2023-11-05 13:37:00.013000',1,1,6),(4,'2023-11-05 13:38:33.117000','Tổng vệ sinh nhà bếp','2023-11-03 11:40:00.000000',NULL,'10.0553,105.7775',1,'2023-11-03 07:40:00.000000',1,'2023-11-05 13:38:33.117000',1,1,2),(5,'2023-11-05 13:41:35.883000','Giữ bé chị đi chợ','2023-11-04 20:00:00.000000',NULL,'9.9913,105.7508',0,'2023-11-04 19:00:00.000000',1,'2023-11-05 13:41:35.883000',2,1,1),(6,'2023-11-05 13:43:55.887000','Vệ sinh 3 cái máy lạnh ','2023-11-05 16:46:00.000000',NULL,'10.0504,105.8307',0,'2023-11-05 15:46:00.000000',3,'2023-11-05 13:43:55.887000',2,1,3),(7,'2023-11-05 13:45:14.325000','Giặt đệm','2023-11-17 17:15:00.000000',NULL,'10.0504,105.8307',1,'2023-11-17 15:45:00.000000',1,'2023-11-05 13:45:14.325000',2,1,6),(8,'2023-11-05 13:49:24.966000','Chuyển nhà sang cái răng','2023-11-18 13:30:00.000000',NULL,'10.0084,105.7493',0,'2023-11-18 13:00:00.000000',3,'2023-11-05 13:49:24.966000',2,2,5),(9,'2023-11-05 13:55:36.246000','Dọn nhà chuẩn bị đón sinh nhật','2023-11-13 15:30:00.000000',NULL,'10.0377,105.7875',0,'2023-11-13 15:00:00.000000',3,'2023-11-05 13:55:36.246000',2,1,2),(10,'2023-11-05 13:58:56.352000','','2023-11-05 14:30:00.000000',NULL,'10.0377,105.7875',0,'2023-11-05 14:00:00.000000',2,'2023-11-05 13:58:56.352000',2,1,3),(11,'2023-11-05 15:02:24.004000','','2023-11-12 09:30:00.000000',NULL,'10.0309,105.7533',0,'2023-11-12 07:00:00.000000',1,'2023-11-05 15:02:24.004000',2,1,7),(12,'2023-11-05 15:12:14.061000','','2023-11-05 08:50:00.000000',NULL,'10.0406,105.792',0,'2023-11-05 07:20:00.000000',1,'2023-11-05 15:12:14.061000',3,1,2),(13,'2023-11-05 15:13:26.609000','','2023-11-07 22:15:00.000000',NULL,'10.1043,105.672',0,'2023-11-07 16:15:00.000000',1,'2023-11-05 15:13:26.609000',3,1,1),(14,'2023-11-05 15:14:35.859000','','2023-11-08 17:45:00.000000',NULL,'10.0275,105.7694',1,'2023-11-08 15:15:00.000000',1,'2023-11-05 15:14:35.859000',3,1,6),(15,'2023-11-05 15:15:47.182000','','2023-11-08 09:20:00.000000',NULL,'9.9251,105.7266',0,'2023-11-08 08:20:00.000000',1,'2023-11-05 15:15:47.182000',3,1,3),(16,'2023-11-05 15:18:07.488000','','2023-11-09 06:50:00.000000',NULL,'10.0256,105.6127',1,'2023-11-09 06:20:00.000000',1,'2023-11-05 15:18:07.488000',3,1,6),(17,'2023-11-05 15:18:56.492000','','2023-11-10 20:50:00.000000',NULL,'10.0714,105.7548',0,'2023-11-10 20:20:00.000000',1,'2023-11-05 15:18:56.492000',3,1,7),(18,'2023-11-05 15:20:11.397000','','2023-11-11 19:00:00.000000',NULL,'10.0142,105.783',0,'2023-11-11 17:00:00.000000',1,'2023-11-05 15:20:11.397000',3,1,3),(19,'2023-11-05 15:31:19.629000','','2023-11-09 17:01:00.000000',NULL,'9.9973,105.7398',0,'2023-11-09 15:31:00.000000',0,'2023-11-05 15:31:19.629000',4,NULL,1),(20,'2023-11-05 15:34:29.399000','','2023-11-10 13:35:00.000000',NULL,'10.0208,105.7524',0,'2023-11-10 07:35:00.000000',0,'2023-11-05 15:34:29.399000',4,NULL,1),(21,'2023-11-05 15:36:18.420000','','2023-11-16 21:08:00.000000',NULL,'10.0118,105.7298',1,'2023-11-16 20:38:00.000000',0,'2023-11-05 15:36:18.420000',4,NULL,1),(22,'2023-11-05 15:37:36.501000','','2023-11-20 08:40:00.000000',NULL,'9.9763,105.8283',0,'2023-11-20 06:40:00.000000',0,'2023-11-05 15:37:36.501000',4,NULL,3),(23,'2023-11-05 15:39:58.738000','','2023-11-09 17:09:00.000000',NULL,'10.0121,105.7317',1,'2023-11-09 15:39:00.000000',0,'2023-11-05 15:39:58.738000',4,NULL,1),(24,'2023-11-05 15:40:45.454000','','2023-11-15 19:12:00.000000',NULL,'10.0246,105.7439',0,'2023-11-15 18:42:00.000000',0,'2023-11-05 15:40:45.454000',3,NULL,4),(25,'2023-11-05 15:43:39.907000','','2023-11-18 16:13:00.000000',NULL,'10.0262,105.771',1,'2023-11-18 15:43:00.000000',0,'2023-11-05 15:43:39.907000',3,NULL,3),(26,'2023-11-06 15:58:24.229000','Cham soc nguoi cao tuoi, hoi kho tinh','2023-11-09 11:30:00.000000',NULL,'10.0247,105.755',0,'2023-11-09 10:00:00.000000',1,'2023-11-06 15:58:24.229000',1,2,4),(27,'2023-11-06 16:14:56.250000','Mua đồ ăn sáng và đút cho cụ','2023-11-07 06:50:00.000000',NULL,'10.0304,105.7941',1,'2023-11-07 06:20:00.000000',0,'2023-11-06 16:14:56.250000',3,NULL,4),(28,'2023-11-06 16:16:53.291000','Giặt mềm','2023-11-16 09:45:00.000000',NULL,'10.0413,105.7227',1,'2023-11-16 08:15:00.000000',0,'2023-11-06 16:16:53.291000',4,NULL,6),(29,'2023-11-06 16:17:39.318000','Chăm bé','2023-11-16 09:50:00.000000',NULL,'10.0385,105.7234',1,'2023-11-16 09:20:00.000000',0,'2023-11-06 16:17:39.318000',4,NULL,2),(30,'2023-11-06 16:49:03.319000','','2023-11-30 07:20:00.000000',NULL,'10.042,105.7909',1,'2023-11-30 06:50:00.000000',0,'2023-11-06 16:49:03.319000',4,NULL,4),(31,'2023-11-06 16:49:05.979000','','2023-11-30 07:20:00.000000',NULL,'10.042,105.7909',1,'2023-11-30 06:50:00.000000',0,'2023-11-06 16:49:05.979000',4,NULL,4),(32,'2023-11-06 16:49:42.321000','','2023-11-30 17:19:00.000000',NULL,'10.0403,105.7321',0,'2023-11-30 16:49:00.000000',0,'2023-11-06 16:49:42.321000',4,NULL,8),(33,'2023-11-06 17:50:11.732000','','2023-11-12 16:20:00.000000',NULL,'10.0322,105.7809',0,'2023-11-12 07:50:00.000000',1,'2023-11-06 17:50:11.732000',2,2,3),(34,'2023-11-06 18:17:27.769000','','2023-11-08 08:50:00.000000',NULL,'10.0159,105.7321',1,'2023-11-08 08:20:00.000000',0,'2023-11-06 18:17:27.769000',2,NULL,1),(35,'2023-11-06 18:34:56.456000','','2023-11-15 21:04:00.000000',NULL,'10.066,105.6566',0,'2023-11-15 18:34:00.000000',1,'2023-11-06 18:34:56.456000',2,1,2),(36,'2023-11-06 18:35:42.189000','','2023-11-15 16:05:00.000000',NULL,'10.0798,105.6645',1,'2023-11-15 14:35:00.000000',0,'2023-11-06 18:35:42.189000',2,NULL,1),(37,'2023-11-06 18:35:44.925000','','2023-11-15 16:05:00.000000',NULL,'10.0798,105.6645',1,'2023-11-15 14:35:00.000000',1,'2023-11-06 18:35:44.925000',2,1,1),(38,'2023-11-06 18:38:56.305000','','2023-11-07 10:10:00.000000',NULL,'10.148,105.5971',1,'2023-11-07 09:40:00.000000',0,'2023-11-06 18:38:56.305000',2,NULL,3);
/*!40000 ALTER TABLE `job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `major`
--

DROP TABLE IF EXISTS `major`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `major` (
  `employee_id` int NOT NULL,
  `service_id` int NOT NULL,
  KEY `FKbufs6vx0edxftmvix09qkgpoq` (`service_id`),
  KEY `FK2j2siisl0rm4od5u41ir3vyjq` (`employee_id`),
  CONSTRAINT `FK2j2siisl0rm4od5u41ir3vyjq` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`employee_id`),
  CONSTRAINT `FKbufs6vx0edxftmvix09qkgpoq` FOREIGN KEY (`service_id`) REFERENCES `service` (`service_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `major`
--

LOCK TABLES `major` WRITE;
/*!40000 ALTER TABLE `major` DISABLE KEYS */;
INSERT INTO `major` VALUES (2,1);
/*!40000 ALTER TABLE `major` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `customer_id` int NOT NULL,
  `rank_id` int NOT NULL,
  `up_rank_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`customer_id`,`rank_id`),
  KEY `FKl5bqhjvu3gos32ygabdu2a4ua` (`rank_id`),
  CONSTRAINT `FKbaqvakoxfn8079onqo5kaxcp9` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`),
  CONSTRAINT `FKl5bqhjvu3gos32ygabdu2a4ua` FOREIGN KEY (`rank_id`) REFERENCES `ranking` (`rank_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES (1,1,'2023-11-05 11:47:33.101000'),(2,1,'2023-11-05 13:40:49.528000'),(3,1,'2023-11-06 16:13:29.077000'),(4,1,'2023-11-05 15:30:40.909000'),(9,1,'2023-11-06 16:58:25.001000');
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ranking`
--

DROP TABLE IF EXISTS `ranking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ranking` (
  `rank_id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `discount` double NOT NULL,
  `min_spend` double NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`rank_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ranking`
--

LOCK TABLES `ranking` WRITE;
/*!40000 ALTER TABLE `ranking` DISABLE KEYS */;
INSERT INTO `ranking` VALUES (1,'Your Journey to Greater Rewards Starts Here!',0,0,'Bronze'),(2,'Upgrade to Silver for a 2% discount.',2,50,'Silver'),(3,'Elevate to Gold for a 3% discount.',3,100,'Gold'),(4,'Experience the top-tier benefits with a 5% discount.',5,200,'Platinum'),(5,'Reach the pinnacle of rewards with an exclusive 7% discount.',7,500,'Diamond');
/*!40000 ALTER TABLE `ranking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `report`
--

DROP TABLE IF EXISTS `report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `report` (
  `report_id` int NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `customer_id` int DEFAULT NULL,
  `job_id` int DEFAULT NULL,
  PRIMARY KEY (`report_id`),
  KEY `FK9gdgejq43wjsko8rtiuks82a4` (`customer_id`),
  KEY `FKg5pev9qjkdahxi4bxikpvmn7o` (`job_id`),
  CONSTRAINT `FK9gdgejq43wjsko8rtiuks82a4` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`),
  CONSTRAINT `FKg5pev9qjkdahxi4bxikpvmn7o` FOREIGN KEY (`job_id`) REFERENCES `job` (`job_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report`
--

LOCK TABLES `report` WRITE;
/*!40000 ALTER TABLE `report` DISABLE KEYS */;
INSERT INTO `report` VALUES (3,'2023-11-06 16:02:33.501000','I think him have a little lazy',NULL,1,3),(4,'2023-11-06 16:13:13.434000','Nhân viên này hơi ngơ ngơ.',NULL,3,12);
/*!40000 ALTER TABLE `report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service`
--

DROP TABLE IF EXISTS `service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service` (
  `service_id` int NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `description` text,
  `discount` double DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `price` double DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`service_id`),
  UNIQUE KEY `UK_adgojnrwwx9c3y3qa2q08uuqp` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service`
--

LOCK TABLES `service` WRITE;
/*!40000 ALTER TABLE `service` DISABLE KEYS */;
INSERT INTO `service` VALUES (1,'2023-10-26 17:23:41.667000','Discover the comfort of personalized in-home childcare, where your child\'s safety and development are our top priorities.>>>>>Our Homemate Childcare Service offer a nurturing and educational haven for your child, right in the comfort of your own home, ensuring their safety and early development. With highly trained caregivers and flexible scheduling, we provide a personalized childcare experience tailored to your family\'s unique needs.>>>>>Personalized Attention: In-home childcare allows for one-on-one care and tailored activities, ensuring your child\'s individual needs and preferences are met.\r###Familiar Environment: Your child remains in the comfort of their own home, which can reduce stress and separation anxiety, creating a more secure and content atmosphere.\r###Flexible Scheduling: With flexible scheduling options, you can arrange childcare to fit your family\'s specific routine and needs, providing convenience for working parents.\r###Enhanced Safety: In-home childcare minimizes exposure to potential health risks by reducing contact with larger groups of children and staff, creating a safer and controlled environment.',0,'/assets/images/services/347781358_609914051085736_103361847478288778_n.jpg','Child Care',3,'2023-10-26 17:23:41.667000'),(2,'2023-10-27 23:21:29.529000',' Experience the delight of coming home to a spotless and refreshing living space with Homemate Cleaning Service.>>>>>Our professional cleaning team offers a range of services from regular house cleaning to deep cleaning, tailored to your preferences and schedule, ensuring a healthy and inviting environment for you and your family.>>>>>Customized Cleaning: We adapt to your unique cleaning needs and preferences, ensuring your home is cleaned to your satisfaction.\r###Professional Expertise: Our experienced cleaners use the latest techniques and tools, delivering the highest level of cleanliness.\r###Convenience: We provide in-home cleaning, making it effortless for you to maintain a clean and healthy living space.\r###Safety and Hygiene: Our rigorous cleaning protocols create a safe and sanitized environment, promoting the well-being of your family.',0,'/assets/images/services/Main-Benefits-of-Residential-Cleaning-Services.jpg','Cleaning',3.5,'2023-10-27 23:21:29.529000'),(3,'2023-10-30 17:49:29.000000','Keep your indoor comfort at its best with Hommate AC Clean and Repair Service.>>>>>Our expert technicians provide a comprehensive range of air conditioning services, including maintenance, repairs, and installation, ensuring your AC system operates efficiently and keeps your home cool and comfortable.>>>>>Professional Expertise: Our certified technicians have the knowledge and skills to diagnose and fix AC issues, ensuring optimal performance.\r###Preventive Maintenance: Regular AC maintenance extends the lifespan of your system, reduces energy costs, and prevents costly breakdowns.\r###Custom Solutions: We tailor our services to your specific needs, whether it\'s a simple tune-up, emergency repairs, or a new installation.\r###Year-round Comfort: Our services help you maintain a comfortable indoor climate in all seasons, ensuring a relaxed and enjoyable living space.',0,'/assets/images/services/ac-coil-cleaning-by-day-night-hvac-1.jpg','A/C Clean and Repair',5,'2023-10-30 17:49:57.000000'),(4,'2023-10-30 17:50:15.000000','Provide compassionate and professional care for your loved ones in the golden years with Homemate Elderly Care Services.>>>>>Our dedicated caregivers offer a range of elderly care services, including companionship, personal care, medication management, and more, ensuring your senior family members receive the support and attention they need in the comfort of their own home.>>>>>Compassionate Care: Our caregivers are trained to provide empathetic, respectful, and patient care for seniors, promoting their well-being.\r###Personalized Support: We create individual care plans to address specific needs, ensuring a customized and tailored approach to care.\r###Safety and Peace of Mind: With our services, you can rest assured that your elderly family members are in safe and capable hands.\r###Familiar Environment: Seniors can age in place, surrounded by the familiarity and comfort of their own home, which can enhance their quality of life and emotional well-being.',0,'/assets/images/services/growing-up-to-rapidly-rising-demand-for-nursing-homes.jpg','Elderly Care',4,'2023-10-30 17:50:12.000000'),(5,'2023-11-04 15:33:30.034000','Enjoy hassle-free and convenient transportation solutions with Homemate Home Transportation Services.>>>>>Our reliable transportation service offers a range of options, from airport transfers to everyday chauffeur services, ensuring you get to your destination safely and on time while adding a touch of luxury to your journeys.>>>>>Effortless Travel: Our professional drivers take care of the logistics, making transportation a breeze, whether it\'s for work, travel, or special occasions.\r###Time Savings: Avoid the stress of driving and parking, allowing you to focus on other tasks while en route.\r###Safety and Reliability: Our drivers are experienced and safety-focused, providing peace of mind during your travels.\r###Comfort and Style: Enjoy a comfortable and stylish ride that adds a touch of luxury to your transportation needs, making your journeys more enjoyable.',0,'/assets/images/services/dantri-2-docx-1584608553114.jpg','Home Transportation ',3,'2023-11-04 15:33:30.034000'),(6,'2023-11-05 08:11:36.480000','Experience the convenience of clean and neatly folded laundry without the hassle with our expert laundry team.>>>>>Our laundry service takes care of washing, drying, and folding your clothes and linens, ensuring fresh, crisp, and neatly organized laundry. Say goodbye to the laundry room and enjoy more time for yourself.>>>>>Time Efficiency: Reclaim hours of your day by delegating the time-consuming task of laundry to our professionals, allowing you to focus on more important aspects of your life.\r###Expert Care: Our team is experienced in handling various fabrics and laundry requirements, ensuring your clothes and linens are treated with care and precision.\r###Always Fresh: Enjoy the luxury of having freshly laundered and neatly folded clothes and linens, giving you that crisp and clean feeling.\r###Customized Solutions: We offer a range of laundry options, including regular laundry service, dry cleaning, and special fabric treatments, tailored to your specific laundry needs, making laundry a breeze.',0,'/assets/images/services/photo1655282684745-16552826851001094747241.webp','Laundry ',3,'2023-11-05 08:11:36.480000'),(7,'2023-11-05 08:13:56.961000','Revitalize your furniture and home environment with our professional upholstery cleaning service, designed to bring back the luster and freshness to your living space.>>>>>Our expert cleaning team specializes in the deep cleaning of upholstery, including sofas, chairs, and other furniture. We use advanced cleaning techniques to remove stains, dirt, and allergens, ensuring your furniture looks and feels as good as new.>>>>>Furniture Renewal: Our upholstery cleaning service extends the life of your furniture, restoring its original beauty and comfort.\r###Improved Indoor Air Quality: By removing allergens, dust, and debris from your upholstery, we help create a healthier living space, benefiting the well-being of your family.\r###Stain Removal: We tackle stubborn stains, spills, and dirt, leaving your upholstery looking fresh and spotless.\r###Convenience and Time Savings: Avoid the hassle of DIY cleaning. Our experts take care of the entire process, saving you time and effort, so you can enjoy your clean and comfortable furniture hassle-free.',0,'/assets/images/services/Upholstery-Cleaning-tips.jpeg','Upholstery Cleaning',3.25,'2023-11-05 08:13:56.961000'),(8,'2023-11-06 15:49:43.386000','ok>>>>>Something>>>>>ok',0,'/assets/images/services/pic02.jpg','Deep Cleaning Service',1,'2023-11-06 15:49:43.386000');
/*!40000 ALTER TABLE `service` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-06 18:42:03
