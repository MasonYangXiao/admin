/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 80027
Source Host           : localhost:3306
Source Database       : admin

Target Server Type    : MYSQL
Target Server Version : 80027
File Encoding         : 65001

Date: 2022-04-01 11:19:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `disabled` bit(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `sort` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '超级管理员', '\0', 'admin', '0');
INSERT INTO `role` VALUES ('3', '管理员', '\0', '管理员', '1');

-- ----------------------------
-- Table structure for role_sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_sys_menu`;
CREATE TABLE `role_sys_menu` (
  `roles_id` bigint NOT NULL,
  `sys_menu_id` int NOT NULL,
  PRIMARY KEY (`roles_id`,`sys_menu_id`),
  KEY `FKo9r80xwb6uxhnwwbignnk8x8t` (`sys_menu_id`),
  CONSTRAINT `FKo9r80xwb6uxhnwwbignnk8x8t` FOREIGN KEY (`sys_menu_id`) REFERENCES `system_menu` (`id`),
  CONSTRAINT `FKqytlcibkadym4xsblhqsiqbgc` FOREIGN KEY (`roles_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of role_sys_menu
-- ----------------------------
INSERT INTO `role_sys_menu` VALUES ('1', '1');
INSERT INTO `role_sys_menu` VALUES ('3', '1');
INSERT INTO `role_sys_menu` VALUES ('1', '2');
INSERT INTO `role_sys_menu` VALUES ('3', '2');
INSERT INTO `role_sys_menu` VALUES ('1', '3');
INSERT INTO `role_sys_menu` VALUES ('3', '3');
INSERT INTO `role_sys_menu` VALUES ('1', '6');
INSERT INTO `role_sys_menu` VALUES ('3', '6');
INSERT INTO `role_sys_menu` VALUES ('1', '7');
INSERT INTO `role_sys_menu` VALUES ('3', '7');

-- ----------------------------
-- Table structure for role_sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `role_sys_resource`;
CREATE TABLE `role_sys_resource` (
  `roles_id` bigint NOT NULL,
  `sys_resource_id` bigint NOT NULL,
  PRIMARY KEY (`roles_id`,`sys_resource_id`),
  KEY `FKt3kkraamu17f0dss8sqpu4u64` (`sys_resource_id`),
  CONSTRAINT `FKfwd4rq4shhsp42tamssswhgi7` FOREIGN KEY (`roles_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FKt3kkraamu17f0dss8sqpu4u64` FOREIGN KEY (`sys_resource_id`) REFERENCES `sys_resource` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of role_sys_resource
-- ----------------------------

-- ----------------------------
-- Table structure for system_menu
-- ----------------------------
DROP TABLE IF EXISTS `system_menu`;
CREATE TABLE `system_menu` (
  `id` int NOT NULL AUTO_INCREMENT,
  `create_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `href` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `pid` int DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `sort` int DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `target` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `update_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of system_menu
-- ----------------------------
INSERT INTO `system_menu` VALUES ('1', '2022-03-30 03:57:17', null, '', 'fa fa-window-maximize', '0', '系统模块', '0', '', '_self', '系统模块', '2022-03-30 03:59:26');
INSERT INTO `system_menu` VALUES ('2', '2022-03-30 04:01:19', null, 'menu', 'fa fa-window-maximize', '1', '菜单管理', '1', '', '_self', '菜单管理', '2022-03-30 04:13:02');
INSERT INTO `system_menu` VALUES ('3', '2022-03-30 04:10:56', null, 'role', 'fa fa-bars', '1', '角色管理', '2', '', '_self', '角色管理', '2022-03-30 04:13:05');
INSERT INTO `system_menu` VALUES ('6', '2022-03-31 01:14:52', null, 'user', 'fa fa-users', '1', '用户管理', '3', '', '_self', '用户管理', '2022-03-31 18:06:26');
INSERT INTO `system_menu` VALUES ('7', '2022-03-31 23:09:55', null, 'resource', 'fa fa-window-restore', '1', '资源管理', '4', '', '_self', '资源管理', '2022-04-01 01:44:14');

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `disabled` bit(1) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
INSERT INTO `sys_resource` VALUES ('1', '菜单接口', '\0', '菜单', '/menu/**');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(64) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `disabled` bit(1) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `last_time` datetime DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '2022-03-30 04:16:37', '\0', '15100310109@163.com', '2022-03-30 04:16:51', '{bcrypt}$2a$10$.hfQewARcjaUJodE1HCNoO1evRgoZPgjaOD7yC40QylpO9CWNjFtK', 'root');
INSERT INTO `user` VALUES ('d3910935-a066-4a2a-bb80-0bd2c5d8cc72', '2022-03-31 23:05:28', '\0', '619510281@qq.com', null, '{bcrypt}$2a$10$MnAyjq.3sRAlI9O1VCjvbOnUYqu0Q/NB.3QfSMmsALs8QmpIAWD6C', 'mason');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` varchar(64) NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1');
INSERT INTO `user_role` VALUES ('d3910935-a066-4a2a-bb80-0bd2c5d8cc72', '3');
