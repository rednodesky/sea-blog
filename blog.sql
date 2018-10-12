/*
Navicat MySQL Data Transfer

Source Server         : blog
Source Server Version : 50721
Source Host           : 127.0.0.1:3306
Source Database       : blog

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-09-05 15:33:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for banner
-- ----------------------------
DROP TABLE IF EXISTS `banner`;
CREATE TABLE `banner` (
  `BANNER_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TYPE` tinyint(4) DEFAULT NULL,
  `SRC` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `TARGET` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  `TITLE` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `LINK` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `CONTENT` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`BANNER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='banner表';

-- ----------------------------
-- Table structure for blog
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog` (
  `BLOG_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CATEGORY_LEVEL` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '分类',
  `PIC` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '预览图',
  `TITLE` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '标题',
  `CONTENT` mediumtext COLLATE utf8_bin COMMENT '内容',
  `VIEW_COUNT` bigint(20) DEFAULT NULL COMMENT '浏览次数',
  `PRISE_COUNT` bigint(20) DEFAULT NULL COMMENT '点赞数',
  `STICK` tinyint(4) DEFAULT NULL COMMENT '置顶：0:不置顶 1:置顶',
  `SORT` int(11) DEFAULT NULL COMMENT '排序',
  `STATUS` tinyint(4) DEFAULT NULL COMMENT '状态:  0:草稿 1:发表 ',
  `SECRET` tinyint(4) DEFAULT NULL COMMENT '私密:  0:公开  1:私密',
  `COMMENT_COUNT` bigint(20) DEFAULT NULL COMMENT '评论次数',
  `CREATE_TIME` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `AUTHOR` bigint(20) DEFAULT NULL COMMENT '作者',
  `UPDATE_TIME` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `DELETED` tinyint(4) DEFAULT NULL COMMENT '删除  1:删除 0:未删除',
  PRIMARY KEY (`BLOG_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='博客表';

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `CATEGORY_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '分类名称',
  `IMAGE` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `MEMO` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `DELETED` tinyint(4) DEFAULT NULL,
  `BLOG_COUNT` int(11) DEFAULT NULL,
  PRIMARY KEY (`CATEGORY_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `COMMENT_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CONTENT` text COLLATE utf8_bin COMMENT '内容',
  `BLOG_ID` bigint(20) DEFAULT NULL COMMENT '博客ID',
  `PARENT_ID` bigint(20) DEFAULT NULL COMMENT '评论ID',
  `USER` bigint(20) DEFAULT NULL COMMENT '评论用户',
  PRIMARY KEY (`COMMENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for hibernate_
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_`;
CREATE TABLE `hibernate_` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `MESSAGE_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CONTENT` text COLLATE utf8_bin,
  `EMAIL` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `NAME` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`MESSAGE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PERMISSION` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ROLE_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ROLE_NAME` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `USER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `USER_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `LOGIN_NAME` varchar(16) COLLATE utf8_bin DEFAULT NULL COMMENT '登录名',
  `PASSWORD` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '密码',
  `NICK_NAME` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '昵称',
  `TRUE_NAME` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '真实姓名',
  `PERSON_SIGNATURE` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '个性签名',
  `ICON` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '头像',
  `BLOG_COUNT` int(11) DEFAULT NULL COMMENT '博客数量',
  `EMAIL_ADDRESS` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱地址',
  `DELETED` tinyint(4) DEFAULT NULL,
  `UPDATE_TIME` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_TIME` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户表';
