/*
Navicat MySQL Data Transfer

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2018-01-17 01:28:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for authority
-- ----------------------------
DROP TABLE IF EXISTS `authority`;
CREATE TABLE `authority` (
  `authority_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `code` varchar(64) NOT NULL,
  `type` varchar(64) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`authority_id`),
  KEY `parent_id` (`parent_id`),
  CONSTRAINT `authority_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `authority` (`authority_id`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of authority
-- ----------------------------
INSERT INTO `authority` VALUES ('1', '用户权限', 'USER_AUTHORITY', 'MENU', '<a class=\"waves-effect\">\r\n  <i class=\"fa fa-user-secret\"></i>用户权限\r\n  <i class=\"expand-menu fa fa-caret-right\"></i>\r\n</a>', null, '2017-12-23 17:51:03');
INSERT INTO `authority` VALUES ('2', '用户管理', 'USER_MANAGE', 'PAGE', '<a class=\"waves-effect\" onclick=\"addTab(\'用户管理\', \'user.html\')\">\r\n  <i class=\"fa fa-users\"></i>用户管理\r\n</a>', '1', '2017-12-23 17:48:53');
INSERT INTO `authority` VALUES ('3', '用户新增', 'USER_ADD', 'ACTION', null, '2', '2017-12-23 17:49:27');
INSERT INTO `authority` VALUES ('4', '用户修改', 'USER_UPDATE', 'ACTION', null, '2', '2017-12-23 17:50:41');
INSERT INTO `authority` VALUES ('5', '用户删除', 'USER_DELETE', 'ACTION', null, '2', '2017-12-23 17:51:03');
INSERT INTO `authority` VALUES ('6', '角色权限', 'ROLE_AUTHORITY', 'PAGE', '<a class=\"waves-effect\" onclick=\"addTab(\'角色管理\', \'role.html\')\">\r\n  <i class=\"fa fa-user-circle\"></i>角色权限\r\n</a>', '1', '2017-12-23 17:48:53');
INSERT INTO `authority` VALUES ('7', '角色新增', 'ROLE_ADD', 'ACTION', null, '6', '2017-12-23 17:49:27');
INSERT INTO `authority` VALUES ('8', '角色修改', 'ROLE_UPDATE', 'ACTION', null, '6', '2017-12-23 17:50:41');
INSERT INTO `authority` VALUES ('9', '角色删除', 'ROLE_DELETE', 'ACTION', null, '6', '2017-12-23 17:51:03');
INSERT INTO `authority` VALUES ('10', '公共资源', 'COMMON_RESOURCE', 'MENU', '<a class=\"waves-effect\">\r\n  <i class=\"fa fa-th-large\"></i>公共资源\r\n  <i class=\"expand-menu fa fa-caret-right\"></i>\r\n</a>', null, '2018-01-16 01:25:55');
INSERT INTO `authority` VALUES ('11', '数据字典', 'DATA_DICT', 'PAGE', '<a class=\"waves-effect\" onclick=\"addTab(\'数据字典\', \'dict.html\')\">\r\n  <i class=\"fa fa-th-list\"></i>数据字典\r\n</a>', '10', '2018-01-16 01:26:55');
INSERT INTO `authority` VALUES ('12', '接口文档', 'API_DOCMENT', 'PAGE', '<a class=\"waves-effect\" href=\"swagger-ui.html\" target=\"_blank\">\r\n  <i class=\"fa fa-file-text\"></i>接口文档\r\n</a>', null, '2018-01-16 01:28:04');

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `permission_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL,
  `name` varchar(64) NOT NULL,
  `code` varchar(64) NOT NULL,
  `type` varchar(64) DEFAULT NULL,
  `target` varchar(512) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`permission_id`),
  KEY `permission_parent_id` (`parent_id`),
  CONSTRAINT `permission_parent_id` FOREIGN KEY (`parent_id`) REFERENCES `permission` (`permission_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permission
-- ----------------------------

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '用户管理', '2017-08-24 12:07:53');
INSERT INTO `role` VALUES ('2', '用户查看', '2017-12-23 16:25:58');

-- ----------------------------
-- Table structure for role_authority
-- ----------------------------
DROP TABLE IF EXISTS `role_authority`;
CREATE TABLE `role_authority` (
  `role_id` bigint(20) NOT NULL,
  `authority_id` bigint(20) NOT NULL,
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`role_id`,`authority_id`),
  KEY `authority_id` (`authority_id`),
  CONSTRAINT `role_authority_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `role_authority_ibfk_2` FOREIGN KEY (`authority_id`) REFERENCES `authority` (`authority_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_authority
-- ----------------------------
INSERT INTO `role_authority` VALUES ('1', '1', '2018-01-16 01:24:03');
INSERT INTO `role_authority` VALUES ('1', '2', '2017-12-23 17:51:16');
INSERT INTO `role_authority` VALUES ('1', '3', '2017-12-23 17:51:33');
INSERT INTO `role_authority` VALUES ('1', '4', '2017-12-23 17:51:40');
INSERT INTO `role_authority` VALUES ('1', '5', '2017-12-23 17:51:46');
INSERT INTO `role_authority` VALUES ('1', '6', '2018-01-16 01:24:23');
INSERT INTO `role_authority` VALUES ('1', '7', '2018-01-16 01:24:31');
INSERT INTO `role_authority` VALUES ('1', '8', '2018-01-16 01:24:40');
INSERT INTO `role_authority` VALUES ('1', '9', '2018-01-16 01:24:49');
INSERT INTO `role_authority` VALUES ('1', '10', '2018-01-16 01:28:38');
INSERT INTO `role_authority` VALUES ('1', '11', '2018-01-16 01:28:46');
INSERT INTO `role_authority` VALUES ('1', '12', '2018-01-16 01:28:55');
INSERT INTO `role_authority` VALUES ('2', '12', '2018-01-16 22:03:07');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `role_id` bigint(20) NOT NULL,
  `permission_id` bigint(20) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`role_id`,`permission_id`),
  KEY `FK_permission_role` (`permission_id`),
  CONSTRAINT `FK_permission_role` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`permission_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_role_permission` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_permission
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) NOT NULL,
  `password` varchar(32) NOT NULL,
  `nickname` varchar(64) NOT NULL,
  `gender` varchar(16) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'ewing', 'yb', '元宝', 'MALE', '2000-02-10', '2017-08-23 18:43:52');
INSERT INTO `user` VALUES ('2', 'rose', 'zx', '紫霞', 'FEMALE', '2002-05-20', '2017-08-24 12:06:02');

-- ----------------------------
-- Table structure for user_permission
-- ----------------------------
DROP TABLE IF EXISTS `user_permission`;
CREATE TABLE `user_permission` (
  `user_id` bigint(20) NOT NULL,
  `permission_id` bigint(20) NOT NULL,
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`,`permission_id`),
  KEY `FK_permission_user` (`permission_id`),
  CONSTRAINT `FK_permission_user` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`permission_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_user_permission` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_permission
-- ----------------------------

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `role_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`role_id`,`user_id`),
  KEY `FK_role_user` (`role_id`),
  KEY `FK_user_role` (`user_id`),
  CONSTRAINT `FK_role_user` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_user_role` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1', '2017-08-24 12:11:17');
INSERT INTO `user_role` VALUES ('2', '2', '2017-12-23 18:25:46');
