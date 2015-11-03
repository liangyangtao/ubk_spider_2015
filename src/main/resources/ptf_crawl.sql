/*
Navicat MySQL Data Transfer

Source Server         : 10.0.0.50
Source Server Version : 50528
Source Host           : 10.0.0.50:3306
Source Database       : ubk_platform_2015

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2015-11-03 11:22:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ptf_crawl
-- ----------------------------
DROP TABLE IF EXISTS `ptf_crawl`;
CREATE TABLE `ptf_crawl` (
  `crawl_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `website_id` int(10) unsigned DEFAULT NULL,
  `crawl_title` varchar(500) NOT NULL DEFAULT '',
  `crawl_brief` varchar(140) DEFAULT NULL,
  `crawl_views` int(10) unsigned DEFAULT '0',
  `web_name` varchar(50) DEFAULT '',
  `url` varchar(255) NOT NULL DEFAULT '',
  `file_index` tinyint(1) DEFAULT '0',
  `news_time` datetime NOT NULL,
  `crawl_time` datetime NOT NULL,
  `task` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`crawl_id`),
  UNIQUE KEY `url_UNIQUE` (`url`),
  UNIQUE KEY `url` (`url`),
  KEY `fk_ptf_crawl_ptf_website_idx` (`website_id`),
  KEY `file_index_task_crawl_time_idx` (`file_index`,`task`,`crawl_time`),
  KEY `crawl_time_idx` (`crawl_time`)
) ENGINE=InnoDB AUTO_INCREMENT=29745467 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ptf_crawl_similar
-- ----------------------------
DROP TABLE IF EXISTS `ptf_crawl_similar`;
CREATE TABLE `ptf_crawl_similar` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `crawl_id` int(11) NOT NULL,
  `similar_id` int(11) NOT NULL,
  `webiste_id` int(11) NOT NULL,
  `web_name` varchar(255) NOT NULL,
  `classname` varchar(255) DEFAULT NULL,
  `hotnum` int(11) NOT NULL,
  `istask` int(11) NOT NULL,
  `crawl_time` datetime DEFAULT NULL,
  `news_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ptf_crawl_state
-- ----------------------------
DROP TABLE IF EXISTS `ptf_crawl_state`;
CREATE TABLE `ptf_crawl_state` (
  `crawl_id` int(11) NOT NULL DEFAULT '0',
  `maincrawl_id` int(11) DEFAULT NULL,
  `deviation` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`crawl_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ptf_crawl_text
-- ----------------------------
DROP TABLE IF EXISTS `ptf_crawl_text`;
CREATE TABLE `ptf_crawl_text` (
  `crawl_id` int(10) unsigned NOT NULL,
  `text` longtext,
  PRIMARY KEY (`crawl_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ptf_crawl_textrank_keyword
-- ----------------------------
DROP TABLE IF EXISTS `ptf_crawl_textrank_keyword`;
CREATE TABLE `ptf_crawl_textrank_keyword` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `crawl_id` int(11) NOT NULL,
  `keyword` varchar(255) NOT NULL,
  `score` float NOT NULL,
  PRIMARY KEY (`id`),
  KEY `keyword_crawl_id` (`crawl_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ptf_crawlid_docid
-- ----------------------------
DROP TABLE IF EXISTS `ptf_crawlid_docid`;
CREATE TABLE `ptf_crawlid_docid` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `crawl_id` int(11) NOT NULL,
  `doc_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `crawl_id_doc_id` (`doc_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=123431 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ptf_crawlid_similarid
-- ----------------------------
DROP TABLE IF EXISTS `ptf_crawlid_similarid`;
CREATE TABLE `ptf_crawlid_similarid` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `crawlid` int(11) NOT NULL,
  `similarid` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=259154 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ptf_day_num
-- ----------------------------
DROP TABLE IF EXISTS `ptf_day_num`;
CREATE TABLE `ptf_day_num` (
  `num_id` int(11) NOT NULL AUTO_INCREMENT,
  `crawl_time` varchar(50) DEFAULT NULL,
  `num` int(11) DEFAULT '1',
  PRIMARY KEY (`num_id`),
  UNIQUE KEY `crawl_time` (`crawl_time`)
) ENGINE=InnoDB AUTO_INCREMENT=18413877 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ptf_day_num_dup
-- ----------------------------
DROP TABLE IF EXISTS `ptf_day_num_dup`;
CREATE TABLE `ptf_day_num_dup` (
  `num_id` int(11) NOT NULL AUTO_INCREMENT,
  `crawl_time` varchar(50) DEFAULT NULL,
  `num` int(11) DEFAULT '1',
  PRIMARY KEY (`num_id`),
  UNIQUE KEY `crawl_time` (`crawl_time`)
) ENGINE=InnoDB AUTO_INCREMENT=6322690 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ptf_day_num_syn
-- ----------------------------
DROP TABLE IF EXISTS `ptf_day_num_syn`;
CREATE TABLE `ptf_day_num_syn` (
  `num_id` int(11) NOT NULL AUTO_INCREMENT,
  `crawl_time` varchar(50) DEFAULT NULL,
  `num` int(11) DEFAULT '1',
  PRIMARY KEY (`num_id`),
  UNIQUE KEY `crawl_time` (`crawl_time`)
) ENGINE=InnoDB AUTO_INCREMENT=5048029 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ptf_day_num_web
-- ----------------------------
DROP TABLE IF EXISTS `ptf_day_num_web`;
CREATE TABLE `ptf_day_num_web` (
  `num_id` int(11) NOT NULL AUTO_INCREMENT,
  `website_id` int(11) DEFAULT NULL,
  `crawl_time` date DEFAULT NULL,
  `num` int(11) DEFAULT '1',
  PRIMARY KEY (`num_id`),
  UNIQUE KEY `web_dayNum` (`crawl_time`,`website_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19030393 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ptf_day_num_web_dup
-- ----------------------------
DROP TABLE IF EXISTS `ptf_day_num_web_dup`;
CREATE TABLE `ptf_day_num_web_dup` (
  `num_id` int(11) NOT NULL AUTO_INCREMENT,
  `website_id` int(11) DEFAULT NULL,
  `crawl_time` varchar(50) DEFAULT NULL,
  `num` int(11) DEFAULT '1',
  PRIMARY KEY (`num_id`),
  UNIQUE KEY `web_dayNum` (`crawl_time`,`website_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6510026 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ptf_day_num_web_syn
-- ----------------------------
DROP TABLE IF EXISTS `ptf_day_num_web_syn`;
CREATE TABLE `ptf_day_num_web_syn` (
  `num_id` int(11) NOT NULL AUTO_INCREMENT,
  `website_id` int(11) DEFAULT NULL,
  `crawl_time` date DEFAULT NULL,
  `num` int(11) DEFAULT '1',
  PRIMARY KEY (`num_id`),
  UNIQUE KEY `web_dayNum` (`crawl_time`,`website_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5184831 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ptf_monitor_numerror
-- ----------------------------
DROP TABLE IF EXISTS `ptf_monitor_numerror`;
CREATE TABLE `ptf_monitor_numerror` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `websiteid` int(11) NOT NULL,
  `avage_num` int(11) NOT NULL,
  `pro_num` int(11) NOT NULL,
  `crawl_time` date NOT NULL,
  `status` int(11) NOT NULL,
  `url` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ptf_monitor_urlerror
-- ----------------------------
DROP TABLE IF EXISTS `ptf_monitor_urlerror`;
CREATE TABLE `ptf_monitor_urlerror` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `websiteid` int(10) NOT NULL,
  `url` varchar(500) DEFAULT NULL,
  `check_time` date DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `http_code` int(11) DEFAULT NULL,
  `error_info` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4449 DEFAULT CHARSET=utf8;
