/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50126
Source Host           : localhost:3306
Source Database       : lxb

Target Server Type    : MYSQL
Target Server Version : 50126
File Encoding         : 65001

Date: 2017-11-01 17:31:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  `identify` varchar(100) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `icon` varchar(50) DEFAULT NULL,
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='目录表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键，自增',
  `name` varchar(50) NOT NULL COMMENT '用户名',
  `accout` varchar(50) NOT NULL COMMENT '登录账号',
  `password` varchar(50) NOT NULL COMMENT '登录密码',
  `email` varchar(50) NOT NULL COMMENT '邮件',
  `phone` varchar(20) NOT NULL COMMENT '联系电话',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) NOT NULL COMMENT '0 - 未删除；1 - 删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------