/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : love_init

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2015-08-04 12:10:58
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for love_blog_article
-- ----------------------------
DROP TABLE IF EXISTS `love_blog_article`;
CREATE TABLE `love_blog_article` (
  `id` varchar(32) NOT NULL,
  `type_id` varchar(32) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  `subtitle` varchar(50) DEFAULT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `content` text,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `isValid` varchar(10) DEFAULT NULL,
  `file_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of love_blog_article
-- ----------------------------

-- ----------------------------
-- Table structure for love_blog_article_type
-- ----------------------------
DROP TABLE IF EXISTS `love_blog_article_type`;
CREATE TABLE `love_blog_article_type` (
  `id` varchar(32) NOT NULL,
  `code` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `isValid` varchar(10) DEFAULT NULL,
  `isshow` varchar(10) DEFAULT NULL,
  `display` int(5) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of love_blog_article_type
-- ----------------------------

-- ----------------------------
-- Table structure for love_blog_board
-- ----------------------------
DROP TABLE IF EXISTS `love_blog_board`;
CREATE TABLE `love_blog_board` (
  `id` varchar(32) NOT NULL,
  `content` text,
  `visitor_id` varchar(32) DEFAULT NULL,
  `reply_content` text,
  `user_id` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `reply_time` datetime DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `isValid` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of love_blog_board
-- ----------------------------

-- ----------------------------
-- Table structure for love_blog_media
-- ----------------------------
DROP TABLE IF EXISTS `love_blog_media`;
CREATE TABLE `love_blog_media` (
  `id` varchar(32) NOT NULL,
  `group_id` varchar(32) DEFAULT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `file_id` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `isValid` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of love_blog_media
-- ----------------------------

-- ----------------------------
-- Table structure for love_blog_media_group
-- ----------------------------
DROP TABLE IF EXISTS `love_blog_media_group`;
CREATE TABLE `love_blog_media_group` (
  `id` varchar(32) NOT NULL,
  `type_id` varchar(32) DEFAULT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `file_id` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `isValid` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of love_blog_media_group
-- ----------------------------

-- ----------------------------
-- Table structure for love_blog_media_type
-- ----------------------------
DROP TABLE IF EXISTS `love_blog_media_type`;
CREATE TABLE `love_blog_media_type` (
  `id` varchar(32) NOT NULL,
  `code` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `isValid` varchar(10) DEFAULT NULL,
  `isshow` varchar(10) DEFAULT NULL,
  `display` int(5) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of love_blog_media_type
-- ----------------------------

-- ----------------------------
-- Table structure for love_blog_visitor
-- ----------------------------
DROP TABLE IF EXISTS `love_blog_visitor`;
CREATE TABLE `love_blog_visitor` (
  `id` varchar(32) NOT NULL,
  `ip` varchar(15) DEFAULT NULL,
  `ip_address` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `login_time` datetime DEFAULT NULL,
  `login_count` varchar(10) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `forbid_start` datetime DEFAULT NULL,
  `forbid_end` datetime DEFAULT NULL,
  `forbid_day` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of love_blog_visitor
-- ----------------------------

-- ----------------------------
-- Table structure for love_system_auth
-- ----------------------------
DROP TABLE IF EXISTS `love_system_auth`;
CREATE TABLE `love_system_auth` (
  `id` varchar(32) NOT NULL,
  `name` varchar(50) NOT NULL,
  `code` varchar(50) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `isValid` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of love_system_auth
-- ----------------------------
INSERT INTO `love_system_auth` VALUES ('0350de3377754371b2e3d09d38a7b5e1', '角色管理', 'MENU_SYSTEM_ROLE', '2015-07-27 17:17:45', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('0aaa42214eff4ed59886f7b523f1905e', '媒体文件管理', 'MENU_BLOG_MEDIA', '2015-07-31 09:17:57', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('0c455fd416f2404c99e50d98ae1983e4', '添加权限', 'BTN_SYSTEM_AUTH_ADD,BTN_SYSTEM_AUTH_ADD', '2015-07-28 10:00:57', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('0f3f2e4e37f64e1c9a054ab7a02c9fdf', '文章管理', 'MENU_BLOG_ARTICLE', '2015-07-31 00:42:21', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('100982cf1b654bc480aff18bb9e08d59', '移出黑名单', 'BTN__BLOG_VISITOR_OUTBLACK', '2015-08-04 11:20:13', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('12f10fc1d83a4779b780d6a05cc42be5', '启停文章类型', 'BTN_BLOG_ARTICLE_TYPE_RUN', '2015-08-04 11:12:48', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('15bd06d00f03405a9eb6193f8c143a26', '修改媒体类型', 'BTN_BLOG_MEDIA_TYPE_EDIT', '2015-08-04 11:16:12', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('171696905cb54cbe8c76fa03d9fd7a8f', '启停文章', 'BTN_BLOG_ARTICLE_RUN', '2015-08-04 11:13:49', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('2163da0a4c404a9d93c77b5ce0be0337', '查看禁言名单', 'BTN__BLOG_VISITOR_VIEWFORBID', '2015-08-04 11:19:51', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('217882d2a12b473ba773d2410271ca32', '修改文章', 'BTN_BLOG_ARTICLE_EDIT', '2015-08-04 11:13:09', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('312625fd799c4fd1a73a1aa103de5b6a', '删除用户', 'BTN_SYSTEM_USER_REMOVE', '2015-07-28 10:00:48', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('3276a76294b9448a97b31a42036b69ad', '启停角色', 'BTN_SYSTEM_USER_RUN', '2015-07-30 15:40:45', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('374ebdaeb4704c3aad3df7b421f991ac', '禁言访客', 'BTN__BLOG_VISITOR_FORBID', '2015-08-04 11:19:31', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('394e1031dadc400686d78e062630f5a6', '修改角色', 'BTN_SYSTEM_ROLE_EDIT', '2015-07-28 10:00:53', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('42734368ca364ce9b2d5ffbf0c1dede0', '启停媒体类型', 'BTN_BLOG_MEDIA_TYPE_RUN', '2015-08-04 11:16:47', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('4607edc2b86e4be28d5e4addaddb415f', '媒体组管理', 'MENU_BLOG_MEDIA_GROUP', '2015-08-04 11:09:54', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('4cd76b97e2664f9c8869df2d87d6208b', '启停媒体文件', 'BTN__BLOG_MEDIA_RUN', '2015-08-04 11:18:50', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('505dfa3105974fa3bb74c786da21d6c7', '修改权限', 'BTN_SYSTEM_AUTH_EDIT', '2015-07-28 10:01:00', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('53966031829e4e24bed8c58046cd3a9d', '修改用户', 'BTN_SYSTEM_USER_EDIT', '2015-07-28 10:00:44', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('53c4e4c4345e4f79b8643bdb18fd519e', '启停角色', 'BTN_SYSTEM_ROLE_RUN', '2015-07-30 15:40:26', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('55d9dda66fbb4e31b88774144704e0f6', '删除媒体类型', 'BTN_BLOG_MEDIA_TYPE_REMOVE', '2015-08-04 11:16:33', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('57c74cd59e2b463c812af9c42472e940', '删除媒体文件', 'BTN__BLOG_MEDIA_REMOVE', '2015-08-04 11:18:40', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('59c4a3679a484a9087525f85a66a133e', '删除文章类型', 'BTN_BLOG_ARTICLE_TYPE_REMOVE', '2015-08-04 11:12:29', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('5fc91d22b8ee43188ecd43c102750609', '添加文章类型', 'BTN_BLOG_ARTICLE_TYPE_ADD', '2015-08-04 11:10:21', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('5ff16b10fff54858a70145669c249590', '添加角色', 'BTN_SYSTEM_ROLE_ADD', '2015-07-28 10:00:51', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('60cf447451af4f39bb01485659d5bddb', '重置用户密码', 'BTN_SYSTEM_USER_RESETPASSWORD', '2015-07-31 00:41:50', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('79d5e0b1cac447e9b8eef4464e0e30bf', '用户管理', 'MENU_SYSTEM_USER', '2015-07-27 17:17:19', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('7c54257f3cab4848a5cbbe9b974672d6', '修改媒体组', 'BTN_BLOG_MEDIA_GROUP_EDIT', '2015-08-04 11:17:11', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('7de590365a2b4d03b930d9ed624b58fd', '删除角色', 'BTN_SYSTEM_ROLE_REMOVE', '2015-07-28 10:00:55', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('7e623fcec7a94d02b853590d232bcfc1', '文章类型管理', 'MENU_BLOG_ARTICLE_TYPE', '2015-07-31 00:42:12', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('803c7c3309a7413ca6f168ebb8ec2abb', '添加文章', 'BTN_BLOG_ARTICLE_ADD', '2015-08-04 11:13:00', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('80bfad325bb34bb99635030d246d0b87', '博客管理', 'MENU_BLOG', '2015-07-31 00:42:02', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('85cc034702a94e9d96d4a113f070d3ae', '添加用户', 'BTN_SYSTEM_USER_ADD', '2015-07-28 10:00:41', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('861a2361699e4e93a35d374600741040', '访客管理', 'MENU_BLOG_VISITOR', '2015-07-31 09:18:16', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('90acd9d8eb4e4ba6b6e772e8bb8a5cce', '添加媒体组', 'BTN_BLOG_MEDIA_GROUP_ADD', '2015-08-04 11:17:03', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('942eae29881140f2a3dd1c5e3bb21a74', '媒体类型管理', 'MENU_BLOG_MEDIA_TYPE', '2015-07-31 09:17:40', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('9b781da27edf47cb9a2d3defe412eb6c', '启停媒体组', 'BTN_BLOG_MEDIA_GROUP_RUN', '2015-08-04 11:17:27', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('aa3ed655f55147db8c81b9cb61edfb72', '设置媒体文件分组', 'BTN__BLOG_MEDIA_SETGROUP', '2015-08-04 11:18:27', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('ad7b718fcefd4a2cb1aa793083a65bd5', '设置媒体文件名称', 'BTN__BLOG_MEDIA_SETNAME', '2015-08-04 11:18:10', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('b1c1de5252f64cd79a4a25568f053522', '上传媒体文件', 'BTN__BLOG_MEDIA_UPLOAD', '2015-08-04 11:17:59', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('b2dbe4207eae444aad479c046a03c2e6', '删除留言', 'BTN__BLOG_BOARD_REMOVE', '2015-08-04 11:19:12', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('b387791b043845eba66438b60cf8111d', '用户分配角色', 'BTN_SYSTEM_USER_GRANT_ROLE', '2015-07-30 15:41:22', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('bfd99212f50848db83c0550590d9cc0f', '拉黑访客', 'BTN__BLOG_VISITOR_BLACK', '2015-08-04 11:20:01', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('c351df4e3b244318992edd825bbcc4c3', '启停权限', 'BTN_SYSTEM_AUTH_RUN', '2015-07-29 14:44:25', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('c4f3fa1203e3405484631974ea3145c0', '删除文章', 'BTN_BLOG_ARTICLE_REMOVE', '2015-08-04 11:13:20', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('cd82d04f9ba04728bb9d762874819a2a', '权限管理', 'MENU_SYSTEM_AUTH', '2015-07-27 17:18:13', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('d041c997a49242a1985e38d854baf9d0', '删除媒体组', 'BTN_BLOG_MEDIA_GROUP_REMOVE', '2015-08-04 11:17:19', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('d39f3069ea1246059aa01491c11e46fc', '解除访客禁言', 'BTN__BLOG_VISITOR_OUTFORBID', '2015-08-04 11:19:42', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('d7b1e792413b4382b44237d6b9e9572b', '留言板管理', 'MENU_BLOG_BOARD', '2015-07-31 09:18:07', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('d7e2285d81304d75b7e168644e85c2c4', '查看黑名单', 'BTN__BLOG_VISITOR_VIEWBLACK', '2015-08-04 11:20:26', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('dfd8c30d5b7348be91d03ae76e01539d', '添加媒体类型', 'BTN_BLOG_MEDIA_TYPE_ADD', '2015-08-04 11:15:44', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('f34bff06caaf48a48965eafce9807289', '用户分配权限', 'BTN_SYSTEM_USER_GRANT_AUTH', '2015-07-30 15:41:32', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('f3e3e297187b4e8eb8b5ff1d36f4227c', '回复留言', 'BTN__BLOG_BOARD_REPLY', '2015-08-04 11:19:05', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('f84c9ea0dd8741019591a410bac15602', '删除权限', 'BTN_SYSTEM_AUTH_REMOVE', '2015-07-28 10:01:02', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('fac55066a65544e2abe2fe39f1fe46b3', '角色分配权限', 'BTN_SYSTEM_ROLE_GRANT_AUTH', '2015-07-30 15:41:04', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('fba14cc65b7f4cb08a7ae5d4c5708dba', '设置媒体组封面', 'BTN_BLOG_MEDIA_GROUP_SETCOVER', '2015-08-04 11:17:42', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('fcec34e8c68a431590c40737beea2c22', '系统管理', 'MENU_SYSTEM', '2015-07-27 17:15:24', null, 'DEFAULT', 'Y');
INSERT INTO `love_system_auth` VALUES ('fea7743791794fc9b64fecbca8406281', '修改文章类型', 'BTN_BLOG_ARTICLE_TYPE_EDIT', '2015-08-04 11:10:36', null, 'DEFAULT', 'Y');

-- ----------------------------
-- Table structure for love_system_file
-- ----------------------------
DROP TABLE IF EXISTS `love_system_file`;
CREATE TABLE `love_system_file` (
  `ID` varchar(32) NOT NULL,
  `URL` varchar(1024) CHARACTER SET gbk DEFAULT NULL,
  `SAVE_PATH` varchar(64) CHARACTER SET gbk DEFAULT NULL,
  `SAVE_NAME` varchar(64) CHARACTER SET gbk DEFAULT NULL,
  `FILE_NAME` varchar(500) CHARACTER SET gbk DEFAULT NULL,
  `CONTENT_TYPE` varchar(500) CHARACTER SET gbk DEFAULT NULL,
  `ENTITY_ID` varchar(32) CHARACTER SET gbk DEFAULT NULL,
  `ENTITY_TYPE` varchar(500) CHARACTER SET gbk DEFAULT NULL,
  `UPLOAD_USER_ID` varchar(32) CHARACTER SET gbk DEFAULT NULL,
  `UPLOAD_USER_NAME` varchar(50) CHARACTER SET gbk DEFAULT NULL,
  `UPLOAD_TIME` datetime DEFAULT NULL,
  `size` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of love_system_file
-- ----------------------------

-- ----------------------------
-- Table structure for love_system_menu
-- ----------------------------
DROP TABLE IF EXISTS `love_system_menu`;
CREATE TABLE `love_system_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT NULL,
  `code` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `isValid` varchar(10) DEFAULT NULL,
  `type` varchar(10) DEFAULT NULL,
  `actions` varchar(100) DEFAULT NULL,
  `auth_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of love_system_menu
-- ----------------------------
INSERT INTO `love_system_menu` VALUES ('1', null, 'MENU_SYSTEM', '系统管理', '', '2015-07-27 13:55:59', null, 'DEFAULT', 'Y', 'ADMIN', null, 'fcec34e8c68a431590c40737beea2c22');
INSERT INTO `love_system_menu` VALUES ('2', '1', 'MENU_SYSTEM_USER', '用户管理', '/system/user/query.s', '2015-07-27 13:57:27', null, 'DEFAULT', 'Y', 'ADMIN', 'dataList.s', '79d5e0b1cac447e9b8eef4464e0e30bf');
INSERT INTO `love_system_menu` VALUES ('3', '1', 'MENU_SYSTEM_ROLE', '角色管理', '/system/role/query.s', '2015-07-27 13:57:54', null, 'DEFAULT', 'Y', 'ADMIN', 'dataList.s', '0350de3377754371b2e3d09d38a7b5e1');
INSERT INTO `love_system_menu` VALUES ('4', '1', 'MENU_SYSTEM_AUTH', '权限管理', '/system/auth/query.s', '2015-07-27 13:58:18', null, 'DEFAULT', 'Y', 'ADMIN', 'dataList.s', 'cd82d04f9ba04728bb9d762874819a2a');
INSERT INTO `love_system_menu` VALUES ('5', null, 'MENU_BLOG', '博客管理', null, '2015-07-31 00:33:49', null, 'DEFAULT', 'Y', 'ADMIN', null, '80bfad325bb34bb99635030d246d0b87');
INSERT INTO `love_system_menu` VALUES ('6', '5', 'MENU_BLOG_ARTICLE_TYPE', '文章类型管理', '/blog/articleType/query.s', '2015-07-31 00:37:33', null, 'DEFAULT', 'Y', 'ADMIN', 'dataList.s', '7e623fcec7a94d02b853590d232bcfc1');
INSERT INTO `love_system_menu` VALUES ('7', '5', 'MENU_BLOG_ARTICLE', '文章管理', '/blog/article/query.s', '2015-07-31 00:39:35', null, 'DEFAULT', 'Y', 'ADMIN', 'dataList.s', '0f3f2e4e37f64e1c9a054ab7a02c9fdf');
INSERT INTO `love_system_menu` VALUES ('8', '5', 'MENU_BLOG_MEDIA_TYPE', '媒体类型管理', '/blog/mediaType/query.s', '2015-07-31 09:08:54', null, 'DEFAULT', 'Y', 'ADMIN', 'dataList.s', '942eae29881140f2a3dd1c5e3bb21a74');
INSERT INTO `love_system_menu` VALUES ('9', '5', 'MENU_BLOG_MEDIA_GROUP', '媒体组管理', '/blog/mediaGroup/query.s', '2015-07-31 09:08:54', null, 'DEFAULT', 'Y', 'ADMIN', 'dataList.s', '4607edc2b86e4be28d5e4addaddb415f');
INSERT INTO `love_system_menu` VALUES ('10', '5', 'MENU_BLOG_MEDIA', '媒体文件管理', '/blog/media/query.s', '2015-07-31 09:09:58', null, 'DEFAULT', 'Y', 'ADMIN', 'dataList.s', '0aaa42214eff4ed59886f7b523f1905e');
INSERT INTO `love_system_menu` VALUES ('11', '5', 'MENU_BLOG_BOARD', '留言板管理', '/blog/board/query.s', '2015-07-31 09:12:58', null, 'DEFAULT', 'Y', 'ADMIN', 'dataList.s', 'd7b1e792413b4382b44237d6b9e9572b');
INSERT INTO `love_system_menu` VALUES ('12', '5', 'MENU_BLOG_VISITOR', '访客管理', '/blog/visitor/query.s', '2015-07-31 09:14:55', null, 'DEFAULT', 'Y', 'ADMIN', 'dataList.s', '861a2361699e4e93a35d374600741040');

-- ----------------------------
-- Table structure for love_system_menu_btn
-- ----------------------------
DROP TABLE IF EXISTS `love_system_menu_btn`;
CREATE TABLE `love_system_menu_btn` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_id` int(11) DEFAULT NULL,
  `code` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `isValid` varchar(10) DEFAULT NULL,
  `auth_id` varchar(32) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of love_system_menu_btn
-- ----------------------------
INSERT INTO `love_system_menu_btn` VALUES ('1', '2', 'BTN_SYSTEM_USER_ADD', '添加', 'save.s', '2015-07-28 09:43:18', null, 'DEFAULT', 'Y', '85cc034702a94e9d96d4a113f070d3ae', 'add');
INSERT INTO `love_system_menu_btn` VALUES ('2', '2', 'BTN_SYSTEM_USER_EDIT', '修改', 'view.s|save.s', '2015-07-28 09:46:36', null, 'DEFAULT', 'Y', '53966031829e4e24bed8c58046cd3a9d', 'edit');
INSERT INTO `love_system_menu_btn` VALUES ('3', '2', 'BTN_SYSTEM_USER_REMOVE', '删除', 'remove.s', '2015-07-28 09:46:39', null, 'DEFAULT', 'Y', '312625fd799c4fd1a73a1aa103de5b6a', 'remove');
INSERT INTO `love_system_menu_btn` VALUES ('4', '3', 'BTN_SYSTEM_ROLE_ADD', '添加', 'save.s', '2015-07-28 09:46:42', null, 'DEFAULT', 'Y', '5ff16b10fff54858a70145669c249590', 'add');
INSERT INTO `love_system_menu_btn` VALUES ('5', '3', 'BTN_SYSTEM_ROLE_EDIT', '修改', 'view.s|save.s', '2015-07-28 09:46:45', null, 'DEFAULT', 'Y', '394e1031dadc400686d78e062630f5a6', 'edit');
INSERT INTO `love_system_menu_btn` VALUES ('6', '3', 'BTN_SYSTEM_ROLE_REMOVE', '删除', 'remove.s', '2015-07-28 09:46:48', null, 'DEFAULT', 'Y', '7de590365a2b4d03b930d9ed624b58fd', 'remove');
INSERT INTO `love_system_menu_btn` VALUES ('7', '4', 'BTN_SYSTEM_AUTH_ADD', '添加', 'save.s', '2015-07-28 09:46:51', null, 'DEFAULT', 'Y', '0c455fd416f2404c99e50d98ae1983e4', 'add');
INSERT INTO `love_system_menu_btn` VALUES ('8', '4', 'BTN_SYSTEM_AUTH_EDIT', '修改', 'view.s|save.s', '2015-07-28 09:46:53', null, 'DEFAULT', 'Y', '505dfa3105974fa3bb74c786da21d6c7', 'edit');
INSERT INTO `love_system_menu_btn` VALUES ('9', '4', 'BTN_SYSTEM_AUTH_REMOVE', '删除', 'remove.s', '2015-07-28 09:46:55', null, 'DEFAULT', 'Y', 'f84c9ea0dd8741019591a410bac15602', 'remove');
INSERT INTO `love_system_menu_btn` VALUES ('10', '4', 'BTN_SYSTEM_AUTH_RUN', '启停', 'run.s', '2015-07-29 13:24:29', null, 'DEFAULT', 'Y', 'c351df4e3b244318992edd825bbcc4c3', 'run');
INSERT INTO `love_system_menu_btn` VALUES ('11', '3', 'BTN_SYSTEM_ROLE_RUN', '启停', 'run.s', '2015-07-30 15:30:02', null, 'DEFAULT', 'Y', '53c4e4c4345e4f79b8643bdb18fd519e', 'run');
INSERT INTO `love_system_menu_btn` VALUES ('12', '2', 'BTN_SYSTEM_USER_RUN', '启停', 'run.s', '2015-07-30 15:31:33', null, 'DEFAULT', 'Y', '3276a76294b9448a97b31a42036b69ad', 'run');
INSERT INTO `love_system_menu_btn` VALUES ('13', '3', 'BTN_SYSTEM_ROLE_GRANT_AUTH', '分配权限', 'authDataList.s|getAuthIdsByRole.s|grantAuth.s', '2015-07-30 15:33:58', null, 'DEFAULT', 'Y', 'fac55066a65544e2abe2fe39f1fe46b3', 'grantAutn');
INSERT INTO `love_system_menu_btn` VALUES ('14', '2', 'BTN_SYSTEM_USER_GRANT_ROLE', '分配角色', 'roleDataList.s|getRoleIdsByUser.s|grantRole.s', '2015-07-30 15:36:53', null, 'DEFAULT', 'Y', 'b387791b043845eba66438b60cf8111d', 'grantRole');
INSERT INTO `love_system_menu_btn` VALUES ('15', '2', 'BTN_SYSTEM_USER_GRANT_AUTH', '分配权限', 'authDataList.s|getAuthIdsByUser.s|grantAuth.s', '2015-07-30 15:38:22', null, 'DEFAULT', 'Y', 'f34bff06caaf48a48965eafce9807289', 'grantAutn');
INSERT INTO `love_system_menu_btn` VALUES ('16', '2', 'BTN_SYSTEM_USER_RESETPASSWORD', '重置用户密码', 'resetPassword.s', '2015-07-31 00:32:11', null, 'DEFAULT', 'Y', '60cf447451af4f39bb01485659d5bddb', 'resetPassword');
INSERT INTO `love_system_menu_btn` VALUES ('17', '6', 'BTN_BLOG_ARTICLE_TYPE_ADD', '添加', 'save.s', '2015-08-04 10:39:34', null, 'DEFAULT', 'Y', '5fc91d22b8ee43188ecd43c102750609', 'add');
INSERT INTO `love_system_menu_btn` VALUES ('18', '6', 'BTN_BLOG_ARTICLE_TYPE_EDIT', '修改', 'view.s|save.s', '2015-08-04 10:40:29', null, 'DEFAULT', 'Y', 'fea7743791794fc9b64fecbca8406281', 'edit');
INSERT INTO `love_system_menu_btn` VALUES ('19', '6', 'BTN_BLOG_ARTICLE_TYPE_REMOVE', '删除', 'remove.s', '2015-08-04 10:40:29', null, 'DEFAULT', 'Y', '59c4a3679a484a9087525f85a66a133e', 'remove');
INSERT INTO `love_system_menu_btn` VALUES ('20', '6', 'BTN_BLOG_ARTICLE_TYPE_RUN', '启停', 'run.s', '2015-08-04 10:40:29', null, 'DEFAULT', 'Y', '12f10fc1d83a4779b780d6a05cc42be5', 'run');
INSERT INTO `love_system_menu_btn` VALUES ('21', '7', 'BTN_BLOG_ARTICLE_ADD', '添加', 'save.s', '2015-08-04 10:40:29', null, 'DEFAULT', 'Y', '803c7c3309a7413ca6f168ebb8ec2abb', 'add');
INSERT INTO `love_system_menu_btn` VALUES ('22', '7', 'BTN_BLOG_ARTICLE_EDIT', '修改', 'view.s|save.s', '2015-08-04 10:40:29', null, 'DEFAULT', 'Y', '217882d2a12b473ba773d2410271ca32', 'edit');
INSERT INTO `love_system_menu_btn` VALUES ('23', '7', 'BTN_BLOG_ARTICLE_REMOVE', '删除', 'remove.s', '2015-08-04 10:40:29', null, 'DEFAULT', 'Y', 'c4f3fa1203e3405484631974ea3145c0', 'remove');
INSERT INTO `love_system_menu_btn` VALUES ('24', '7', 'BTN_BLOG_ARTICLE_RUN', '启停', 'run.s', '2015-08-04 10:40:29', null, 'DEFAULT', 'Y', '171696905cb54cbe8c76fa03d9fd7a8f', 'run');
INSERT INTO `love_system_menu_btn` VALUES ('25', '8', 'BTN_BLOG_MEDIA_TYPE_ADD', '添加', 'save.s', '2015-08-04 10:40:29', null, 'DEFAULT', 'Y', 'dfd8c30d5b7348be91d03ae76e01539d', 'add');
INSERT INTO `love_system_menu_btn` VALUES ('26', '8', 'BTN_BLOG_MEDIA_TYPE_EDIT', '修改', 'view.s|save.s', '2015-08-04 10:40:29', null, 'DEFAULT', 'Y', '15bd06d00f03405a9eb6193f8c143a26', 'edit');
INSERT INTO `love_system_menu_btn` VALUES ('27', '8', 'BTN_BLOG_MEDIA_TYPE_REMOVE', '删除', 'remove.s', '2015-08-04 10:40:29', null, 'DEFAULT', 'Y', '55d9dda66fbb4e31b88774144704e0f6', 'remove');
INSERT INTO `love_system_menu_btn` VALUES ('28', '8', 'BTN_BLOG_MEDIA_TYPE_RUN', '启停', 'run.s', '2015-08-04 10:40:29', null, 'DEFAULT', 'Y', '42734368ca364ce9b2d5ffbf0c1dede0', 'run');
INSERT INTO `love_system_menu_btn` VALUES ('29', '9', 'BTN_BLOG_MEDIA_GROUP_ADD', '添加', 'save.s|selectType.s', '2015-08-04 10:40:29', null, 'DEFAULT', 'Y', '90acd9d8eb4e4ba6b6e772e8bb8a5cce', 'add');
INSERT INTO `love_system_menu_btn` VALUES ('30', '9', 'BTN_BLOG_MEDIA_GROUP_EDIT', '修改', 'view.s|save.s|selectType.s', '2015-08-04 10:40:29', null, 'DEFAULT', 'Y', '7c54257f3cab4848a5cbbe9b974672d6', 'edit');
INSERT INTO `love_system_menu_btn` VALUES ('31', '9', 'BTN_BLOG_MEDIA_GROUP_REMOVE', '删除', 'remove.s', '2015-08-04 10:40:29', null, 'DEFAULT', 'Y', 'd041c997a49242a1985e38d854baf9d0', 'remove');
INSERT INTO `love_system_menu_btn` VALUES ('32', '9', 'BTN_BLOG_MEDIA_GROUP_RUN', '启停', 'run.s', '2015-08-04 10:40:29', null, 'DEFAULT', 'Y', '9b781da27edf47cb9a2d3defe412eb6c', 'run');
INSERT INTO `love_system_menu_btn` VALUES ('33', '9', 'BTN_BLOG_MEDIA_GROUP_SETCOVER', '设置封面', 'view.s|coverUpload.s', '2015-08-04 10:40:29', null, 'DEFAULT', 'Y', 'fba14cc65b7f4cb08a7ae5d4c5708dba', 'setCover');
INSERT INTO `love_system_menu_btn` VALUES ('34', '10', 'BTN__BLOG_MEDIA_UPLOAD', '上传', 'save.s|fileUpload.s', '2015-08-04 10:40:29', null, 'DEFAULT', 'Y', 'b1c1de5252f64cd79a4a25568f053522', 'addFile');
INSERT INTO `love_system_menu_btn` VALUES ('35', '10', 'BTN__BLOG_MEDIA_SETNAME', '设置名称', 'view.s|setName.s', '2015-08-04 10:40:29', null, 'DEFAULT', 'Y', 'ad7b718fcefd4a2cb1aa793083a65bd5', 'setName');
INSERT INTO `love_system_menu_btn` VALUES ('36', '10', 'BTN__BLOG_MEDIA_SETGROUP', '设置分组', 'view.s|setGroup.s|selectGroup.s', '2015-08-04 10:40:29', null, 'DEFAULT', 'Y', 'aa3ed655f55147db8c81b9cb61edfb72', 'setGroup');
INSERT INTO `love_system_menu_btn` VALUES ('37', '10', 'BTN__BLOG_MEDIA_REMOVE', '删除', 'remove.s', '2015-08-04 10:40:29', null, 'DEFAULT', 'Y', '57c74cd59e2b463c812af9c42472e940', 'remove');
INSERT INTO `love_system_menu_btn` VALUES ('38', '10', 'BTN__BLOG_MEDIA_RUN', '启停', 'run.s', '2015-08-04 10:40:29', null, 'DEFAULT', 'Y', '4cd76b97e2664f9c8869df2d87d6208b', 'run');
INSERT INTO `love_system_menu_btn` VALUES ('39', '11', 'BTN__BLOG_BOARD_REPLY', '回复', 'view.s|save.s', '2015-08-04 10:40:29', null, 'DEFAULT', 'Y', 'f3e3e297187b4e8eb8b5ff1d36f4227c', 'edit');
INSERT INTO `love_system_menu_btn` VALUES ('40', '11', 'BTN__BLOG_BOARD_REMOVE', '删除', 'remove.s', '2015-08-04 10:40:29', null, 'DEFAULT', 'Y', 'b2dbe4207eae444aad479c046a03c2e6', 'remove');
INSERT INTO `love_system_menu_btn` VALUES ('41', '12', 'BTN__BLOG_VISITOR_FORBID', '禁言', 'view.s|save.s', '2015-08-04 10:40:29', null, 'DEFAULT', 'Y', '374ebdaeb4704c3aad3df7b421f991ac', 'edit');
INSERT INTO `love_system_menu_btn` VALUES ('42', '12', 'BTN__BLOG_VISITOR_OUTFORBID', '解除禁言', 'outForbid.', '2015-08-04 10:40:29', null, 'DEFAULT', 'Y', 'd39f3069ea1246059aa01491c11e46fc', 'outforbid');
INSERT INTO `love_system_menu_btn` VALUES ('43', '12', 'BTN__BLOG_VISITOR_VIEWFORBID', '查看禁言名单', 'viewByStatus.s', '2015-08-04 10:40:29', null, 'DEFAULT', 'Y', '2163da0a4c404a9d93c77b5ce0be0337', 'viewforbid');
INSERT INTO `love_system_menu_btn` VALUES ('44', '12', 'BTN__BLOG_VISITOR_BLACK', '拉黑', 'inBlack.s', '2015-08-04 10:40:29', null, 'DEFAULT', 'Y', 'bfd99212f50848db83c0550590d9cc0f', 'inblack');
INSERT INTO `love_system_menu_btn` VALUES ('45', '12', 'BTN__BLOG_VISITOR_OUTBLACK', '移出黑名单', 'outBlack.s', '2015-08-04 10:40:29', null, 'DEFAULT', 'Y', '100982cf1b654bc480aff18bb9e08d59', 'outblack');
INSERT INTO `love_system_menu_btn` VALUES ('46', '12', 'BTN__BLOG_VISITOR_VIEWBLACK', '查看黑名单', 'viewByStatus.s', '2015-08-04 10:40:29', null, 'DEFAULT', 'Y', 'd7e2285d81304d75b7e168644e85c2c4', 'viewblack');

-- ----------------------------
-- Table structure for love_system_role
-- ----------------------------
DROP TABLE IF EXISTS `love_system_role`;
CREATE TABLE `love_system_role` (
  `id` varchar(32) NOT NULL,
  `name` varchar(50) NOT NULL,
  `code` varchar(50) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `isValid` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of love_system_role
-- ----------------------------
INSERT INTO `love_system_role` VALUES ('f83e5e76bee74b42add71eeec19bb46d', '超级管理员', 'ROLE_ADMIN', '2015-07-27 15:24:11', null, 'DEFAULT', 'Y');

-- ----------------------------
-- Table structure for love_system_r_role_auth
-- ----------------------------
DROP TABLE IF EXISTS `love_system_r_role_auth`;
CREATE TABLE `love_system_r_role_auth` (
  `id` varchar(32) NOT NULL,
  `role_id` varchar(32) DEFAULT NULL,
  `auth_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of love_system_r_role_auth
-- ----------------------------

-- ----------------------------
-- Table structure for love_system_r_user_auth
-- ----------------------------
DROP TABLE IF EXISTS `love_system_r_user_auth`;
CREATE TABLE `love_system_r_user_auth` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `auth_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of love_system_r_user_auth
-- ----------------------------

-- ----------------------------
-- Table structure for love_system_r_user_role
-- ----------------------------
DROP TABLE IF EXISTS `love_system_r_user_role`;
CREATE TABLE `love_system_r_user_role` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `role_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of love_system_r_user_role
-- ----------------------------
INSERT INTO `love_system_r_user_role` VALUES ('033f0af0e5624267b76a377f9191c67b', '6f60af2d074141ccb4e835906fdefe66', 'f83e5e76bee74b42add71eeec19bb46d');

-- ----------------------------
-- Table structure for love_system_user
-- ----------------------------
DROP TABLE IF EXISTS `love_system_user`;
CREATE TABLE `love_system_user` (
  `id` varchar(32) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `nickname` varchar(50) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `isValid` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of love_system_user
-- ----------------------------
INSERT INTO `love_system_user` VALUES ('6f60af2d074141ccb4e835906fdefe66', 'admin', '4392c4f26c8de6034f758c61331d6e60', '超级管理员', '2015-07-27 14:08:12', null, 'DEFAULT', 'Y');
