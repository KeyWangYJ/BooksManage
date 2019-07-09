/*
Navicat MySQL Data Transfer

Source Server         : travel
Source Server Version : 50546
Source Host           : localhost:3306
Source Database       : book

Target Server Type    : MYSQL
Target Server Version : 50546
File Encoding         : 65001

Date: 2019-04-27 14:46:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `adminname` varchar(50) DEFAULT NULL,
  `adminpassword` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('1', 'admin', '698d51a19d8a121ce581499d7b701668');
INSERT INTO `admin` VALUES ('2', 'sss', '698d51a19d8a121ce581499d7b701668');
INSERT INTO `admin` VALUES ('3', 'aaa', '698d51a19d8a121ce581499d7b701668');

-- ----------------------------
-- Table structure for admin_role
-- ----------------------------
DROP TABLE IF EXISTS `admin_role`;
CREATE TABLE `admin_role` (
  `adminId` int(11) NOT NULL,
  `roleId` int(11) NOT NULL,
  PRIMARY KEY (`adminId`,`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin_role
-- ----------------------------
INSERT INTO `admin_role` VALUES ('1', '3');
INSERT INTO `admin_role` VALUES ('2', '2');
INSERT INTO `admin_role` VALUES ('3', '1');

-- ----------------------------
-- Table structure for books
-- ----------------------------
DROP TABLE IF EXISTS `books`;
CREATE TABLE `books` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `picture` varchar(50) NOT NULL,
  `classification` varchar(30) NOT NULL,
  `totalnum` int(10) NOT NULL,
  `restnum` int(10) NOT NULL,
  `location` varchar(30) NOT NULL,
  `author` varchar(50) NOT NULL,
  `publisher` varchar(30) NOT NULL,
  `publishdate` date NOT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of books
-- ----------------------------
INSERT INTO `books` VALUES ('1', '小说', 'images/c441fb75-6003-479c-89f4-efc114266d17.jpg', '小说', '4', '0', 'B2A2', 'ww', '广东海洋大学文学出版社', '2001-02-19', '0');
INSERT INTO `books` VALUES ('2', 'www', '../images/book1.jpg', '科幻', '10', '10', 'B2A9', 'w', 'fdh', '0000-00-00', '0');
INSERT INTO `books` VALUES ('3', '小说', '../images/book1.jpg', '爱情', '4', '2', 'B2A3', 'ww', 'fdh', '1992-04-05', '0');
INSERT INTO `books` VALUES ('4', 'grsrg', '../images/book1.jpg', '冒险', '10', '10', 'B2A3', 'err', 'fdh', '0000-00-00', '0');
INSERT INTO `books` VALUES ('5', 'rrr', '../images/book1.jpg', '诗歌', '10', '10', 'B2A3', 'r', 'fdh', '0000-00-00', '0');
INSERT INTO `books` VALUES ('6', '3fv', '../images/book1.jpg', '教材', '10', '10', 'B2A3', 'hgfh', 'fdh', '0000-00-00', '0');
INSERT INTO `books` VALUES ('7', 'jjj', '../images/loginBackground.jpg', '小说', '10', '10', 'B2A3', '222', 'fdh', '0000-00-00', '0');
INSERT INTO `books` VALUES ('8', 'fd', 'images/0de33936-e09c-4e5f-916e-dfd89b954c18.jpg', 'xs', '12', '10', 'sd1', 'ss', 'sd', '2001-02-19', '0');
INSERT INTO `books` VALUES ('9', 'aa', 'images/c03047cf-b05e-4c8f-9c8e-711e38ab41de.jpg', 'xs', '12', '10', 'xx2', 'sd', 'sf', '2001-02-19', '0');
INSERT INTO `books` VALUES ('10', 'ssf', 'images/2ca03869-1921-4f24-802e-72b86ee04ceb.jpg', 'xs', '10', '10', 'df', 'sd', 'sf', '2001-02-19', '0');
INSERT INTO `books` VALUES ('11', 'aa', 'images/811b86e5-1a89-41e8-ad56-3033accb1464.jpg', '小说', '12', '10', 'B2A3', 'sd', 'sd', '2009-08-19', '0');
INSERT INTO `books` VALUES ('12', 'fd', 'images/26981e65-e0ad-412b-b4c4-a151a7eb6c1c.jpg', '小说', '12', '10', 'df', 'ss', 'fdh', '0000-00-00', '0');
INSERT INTO `books` VALUES ('13', 'ssf', 'images/d8f69d1a-9208-4463-93a2-ec32e51f6c7a.jpg', '小说', '12', '10', 'sd1', 'sd', 'sf', '2009-08-19', '0');
INSERT INTO `books` VALUES ('14', '3fv', '../images/book1.jpg', '教材', '10', '10', 'B2A3', 'hgfh', 'fdh', '0000-00-00', '0');
INSERT INTO `books` VALUES ('15', 'aa', 'images/c03047cf-b05e-4c8f-9c8e-711e38ab41de.jpg', 'xs', '12', '10', 'xx2', 'sd', 'sf', '2001-02-19', '0');
INSERT INTO `books` VALUES ('16', 'grsrg', '../images/book1.jpg', '冒险', '10', '10', 'B2A3', 'err', 'fdh', '0000-00-00', '0');
INSERT INTO `books` VALUES ('17', 'aa', 'images/c03047cf-b05e-4c8f-9c8e-711e38ab41de.jpg', 'xs', '12', '10', 'xx2', 'sd', 'sf', '2001-02-19', '0');
INSERT INTO `books` VALUES ('18', '小说', 'images/6db2a847-3fe4-4ca8-a4ce-e3bd5005fe84.jpg', '小说', '12', '10', 'B2A2', 'ss', 'sf', '2001-02-19', '0');
INSERT INTO `books` VALUES ('21', '小说', 'images/c81c92a2-c18e-4e7a-81f1-d85eeabcd728.jpg', '小说', '4', '1', 'B2A1', 'ww', 'sd', '2001-02-19', '0');
INSERT INTO `books` VALUES ('22', '小说', 'images/2bb9fb9d-1d08-4596-ab07-1e45bc2c6c32.jpg', '小说', '4', '2', 'B2A4', 'ww', 'sd', '2001-02-19', '0');

-- ----------------------------
-- Table structure for lend
-- ----------------------------
DROP TABLE IF EXISTS `lend`;
CREATE TABLE `lend` (
  `lendId` int(11) NOT NULL AUTO_INCREMENT,
  `bookId` int(11) NOT NULL,
  `bookName` varchar(50) NOT NULL,
  `userId` int(11) NOT NULL,
  `outTime` datetime NOT NULL,
  `deadline` datetime NOT NULL,
  `returnStatus` int(11) NOT NULL DEFAULT '0',
  `returnTime` datetime DEFAULT NULL,
  PRIMARY KEY (`lendId`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of lend
-- ----------------------------
INSERT INTO `lend` VALUES ('3', '2', 'www', '4', '2019-04-12 23:20:35', '2019-07-12 23:20:35', '1', '2019-04-13 22:38:06');
INSERT INTO `lend` VALUES ('9', '3', '小说', '4', '2019-04-12 23:56:07', '2019-07-12 23:56:07', '1', '2019-04-14 20:04:39');
INSERT INTO `lend` VALUES ('12', '1', '小说', '4', '2019-04-13 00:18:38', '2019-07-13 00:18:38', '1', '2019-04-19 16:44:17');
INSERT INTO `lend` VALUES ('13', '21', '小说', '4', '2019-04-13 21:34:27', '2019-07-13 21:34:27', '1', '2019-04-19 17:10:48');
INSERT INTO `lend` VALUES ('14', '1', '小说', '4', '2019-04-19 16:50:14', '2019-07-19 16:50:14', '1', '2019-04-19 16:54:46');

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `perid` int(11) NOT NULL AUTO_INCREMENT,
  `permissionname` varchar(30) NOT NULL,
  `perurl` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`perid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('1', 'manageBook', null);
INSERT INTO `permission` VALUES ('2', 'manageUser', null);
INSERT INTO `permission` VALUES ('3', 'manageAdmin', null);

-- ----------------------------
-- Table structure for permission_role
-- ----------------------------
DROP TABLE IF EXISTS `permission_role`;
CREATE TABLE `permission_role` (
  `perId` int(11) NOT NULL,
  `roleIdNum` int(11) NOT NULL,
  PRIMARY KEY (`perId`,`roleIdNum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permission_role
-- ----------------------------
INSERT INTO `permission_role` VALUES ('1', '1');
INSERT INTO `permission_role` VALUES ('1', '2');
INSERT INTO `permission_role` VALUES ('2', '1');
INSERT INTO `permission_role` VALUES ('2', '3');
INSERT INTO `permission_role` VALUES ('3', '1');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `roleid` int(11) NOT NULL AUTO_INCREMENT,
  `rolename` varchar(30) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`roleid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'superAdmin', null);
INSERT INTO `role` VALUES ('2', 'booksAdmin', null);
INSERT INTO `role` VALUES ('3', 'userAdmin', null);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `readername` varchar(30) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `identitycode` varchar(30) NOT NULL,
  `phonenum` varchar(30) NOT NULL,
  `email` varchar(30) NOT NULL,
  `description` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('4', 'www', 'com', 'e10adc3949ba59abbe56e057f20f883e', '12345678', '1234567', '111222qq.com', null);
INSERT INTO `user` VALUES ('5', 'sdsd', 'cn', 'e10adc3949ba59abbe56e057f20f883e', '12344454', '1322424', '32r23r32.com', null);
INSERT INTO `user` VALUES ('6', '王五', 'www', 'e10adc3949ba59abbe56e057f20f883e', '441322199708253', '13420120437', '785828852@qq.com', null);
INSERT INTO `user` VALUES ('8', '李四', 'sdf', 'e10adc3949ba59abbe56e057f20f883e', '123456789123456', '13246123493', '1584958984@qq.com', null);
INSERT INTO `user` VALUES ('12', '王五', 'qqq', 'e10adc3949ba59abbe56e057f20f883e', '123451234512345', '13420120437', '1584958984@qq.com', null);
