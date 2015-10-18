/*
Navicat MySQL Data Transfer

Source Server         : DB
Source Server Version : 50625
Source Host           : localhost:3306
Source Database       : stok

Target Server Type    : MYSQL
Target Server Version : 50625
File Encoding         : 65001

Date: 2015-08-18 12:24:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `musteriler`
-- ----------------------------
DROP TABLE IF EXISTS `musteriler`;
CREATE TABLE `musteriler` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mAdi` varchar(255) NOT NULL,
  `mFirma` varchar(255) NOT NULL,
  `mAdres` text NOT NULL,
  `mTel` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of musteriler
-- ----------------------------

-- ----------------------------
-- Table structure for `satislar`
-- ----------------------------
DROP TABLE IF EXISTS `satislar`;
CREATE TABLE `satislar` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sKime` varchar(255) NOT NULL,
  `sTuru` varchar(255) NOT NULL,
  `sMiktari` int(11) NOT NULL,
  `sTarihi` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of satislar
-- ----------------------------

-- ----------------------------
-- Table structure for `urunler`
-- ----------------------------
DROP TABLE IF EXISTS `urunler`;
CREATE TABLE `urunler` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uAd` varchar(255) NOT NULL,
  `uStok` double NOT NULL,
  `uFiyat` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of urunler
-- ----------------------------
INSERT INTO `urunler` VALUES ('1', 'biber', '3', '2.5');
INSERT INTO `urunler` VALUES ('2', 'domates', '0', '1.89');
