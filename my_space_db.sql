/*
 Navicat Premium Data Transfer

 Source Server         : 1
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : localhost:3306
 Source Schema         : my_space_db

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 01/07/2022 17:36:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_admin_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_admin_user`;
CREATE TABLE `tb_admin_user`  (
  `admin_user_id` int NOT NULL AUTO_INCREMENT COMMENT '管理员id',
  `login_user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员登陆名称',
  `login_password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员登陆密码',
  `real_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员显示昵称',
  `locked` tinyint NULL DEFAULT 0 COMMENT '是否锁定 0未锁定 1已锁定无法登陆',
  PRIMARY KEY (`admin_user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_admin_user
-- ----------------------------
INSERT INTO `tb_admin_user` VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', '广寒璃', 0);

-- ----------------------------
-- Table structure for tb_blog
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog`;
CREATE TABLE `tb_blog`  (
  `blog_id` bigint NOT NULL AUTO_INCREMENT COMMENT '博客表主键id',
  `blog_title` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '博客标题',
  `blog_sub_url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '博客自定义路径url',
  `blog_cover_image` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '博客封面图',
  `blog_content` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '博客内容',
  `blog_category_id` int NOT NULL COMMENT '博客分类id',
  `blog_category_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '博客分类(冗余字段)',
  `blog_tags` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '博客标签',
  `blog_status` tinyint NOT NULL DEFAULT 0 COMMENT '0-草稿 1-发布',
  `blog_views` bigint NOT NULL DEFAULT 0 COMMENT '阅读量',
  `enable_comment` tinyint NOT NULL DEFAULT 0 COMMENT '0-允许评论 1-不允许评论',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除 0=否 1=是',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`blog_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_blog
-- ----------------------------
INSERT INTO `tb_blog` VALUES (1, '自我介绍', 'about', 'http://localhost:8081/admin/dist/img/rand/33.jpg', '## About me\n\n学习是一件极其枯燥而无聊的过程，甚至有时候显得很无助，我也想告诉你，成长就是这样一件残酷的事情，任何成功都不是一蹴而就，需要坚持、需要付出、需要你的毅力，短期可能看不到收获，因为破茧成蝶所耗费的时间不是一天。', 32, '随笔', '个人', 1, 35, 0, 0, '2022-07-01 09:34:19', '2022-07-01 09:34:19');
INSERT INTO `tb_blog` VALUES (14, 'hello world', '', 'http://localhost:8081/admin/dist/img/rand/10.jpg', 'helloworld', 32, '随笔', 'hello', 1, 0, 0, 1, '2022-07-01 14:53:47', '2022-07-01 14:53:47');
INSERT INTO `tb_blog` VALUES (15, 'hello cat', '', 'http://localhost:8081/admin/dist/img/rand/36.jpg', 'hellocat', 32, '随笔', 'hello', 1, 0, 0, 1, '2022-07-01 14:54:08', '2022-07-01 14:54:08');
INSERT INTO `tb_blog` VALUES (16, 'hello cat', '', 'http://localhost:8081/admin/dist/img/rand/16.jpg', '213', 32, '随笔', 'hello', 1, 0, 0, 1, '2022-07-01 14:56:21', '2022-07-01 14:56:21');
INSERT INTO `tb_blog` VALUES (17, 'hello world', '', 'http://localhost:8081/admin/dist/img/rand/34.jpg', 'enjoy it', 32, '随笔', 'hello', 1, 0, 0, 1, '2022-07-01 14:56:46', '2022-07-01 14:56:46');
INSERT INTO `tb_blog` VALUES (18, 'hello what', '', 'http://localhost:8081/admin/dist/img/rand/10.jpg', 'hello', 32, '随笔', 'hello', 1, 0, 0, 1, '2022-07-01 15:01:31', '2022-07-01 15:01:31');
INSERT INTO `tb_blog` VALUES (19, 'hello kid', '', 'http://localhost:8081/admin/dist/img/rand/3.jpg', 'hello', 32, '随笔', 'hello', 1, 0, 0, 1, '2022-07-01 15:02:36', '2022-07-01 15:02:36');
INSERT INTO `tb_blog` VALUES (20, 'hello nit', '', 'http://localhost:8081/admin/dist/img/rand/4.jpg', 'adc', 32, '随笔', 'hello', 1, 0, 0, 1, '2022-07-01 15:04:04', '2022-07-01 15:04:04');
INSERT INTO `tb_blog` VALUES (21, 'hello nit', '', 'http://localhost:8081/admin/dist/img/rand/4.jpg', 'adc', 32, '随笔', 'hello', 1, 0, 0, 1, '2022-07-01 15:04:16', '2022-07-01 15:04:16');
INSERT INTO `tb_blog` VALUES (22, 'hello what', '', 'http://localhost:8081/admin/dist/img/rand/12.jpg', '123456', 32, '随笔', 'hello', 1, 0, 0, 1, '2022-07-01 15:04:48', '2022-07-01 15:04:48');
INSERT INTO `tb_blog` VALUES (23, 'hello what', '', 'http://localhost:8081/admin/dist/img/rand/12.jpg', '123456', 32, '随笔', 'hello', 1, 0, 0, 1, '2022-07-01 15:04:52', '2022-07-01 15:04:52');
INSERT INTO `tb_blog` VALUES (24, 'hello haha', '', 'http://localhost:8081/admin/dist/img/rand/40.jpg', 'hello haha', 32, '随笔', 'hello', 1, 0, 0, 1, '2022-07-01 15:05:58', '2022-07-01 15:05:58');
INSERT INTO `tb_blog` VALUES (25, 'hello haha', '', 'http://localhost:8081/admin/dist/img/rand/9.jpg', 'kid', 32, '随笔', 'hello', 1, 0, 0, 1, '2022-07-01 15:06:59', '2022-07-01 15:06:59');
INSERT INTO `tb_blog` VALUES (26, 'helloa', '', 'http://localhost:8081/admin/dist/img/rand/38.jpg', '2', 32, '随笔', 'hello,a', 1, 0, 0, 1, '2022-07-01 15:09:48', '2022-07-01 15:09:48');
INSERT INTO `tb_blog` VALUES (27, 'hello f', '', 'http://localhost:8081/admin/dist/img/rand/33.jpg', '123456', 32, '随笔', 'hello', 1, 0, 0, 1, '2022-07-01 15:10:06', '2022-07-01 15:10:06');
INSERT INTO `tb_blog` VALUES (28, 'hello world', '', 'http://localhost:8081/admin/dist/img/rand/28.jpg', 'hello', 32, '随笔', 'hello', 1, 0, 0, 1, '2022-07-01 15:33:50', '2022-07-01 15:33:50');
INSERT INTO `tb_blog` VALUES (29, 'hello kid', '', 'http://localhost:8081/admin/dist/img/rand/9.jpg', 'hello kid', 32, '随笔', 'hello', 1, 0, 0, 1, '2022-07-01 15:34:16', '2022-07-01 15:34:16');
INSERT INTO `tb_blog` VALUES (30, 'hello aa', '', 'http://localhost:8081/admin/dist/img/rand/30.jpg', 'hello', 32, '随笔', 'hello', 1, 0, 0, 1, '2022-07-01 16:29:50', '2022-07-01 16:29:50');
INSERT INTO `tb_blog` VALUES (31, 'kk', '', 'http://localhost:8081/admin/dist/img/rand/6.jpg', '123', 32, '随笔', 'kk', 1, 0, 0, 1, '2022-07-01 16:33:27', '2022-07-01 16:33:27');
INSERT INTO `tb_blog` VALUES (32, 'ghl', '', 'http://localhost:8081/admin/dist/img/rand/12.jpg', '22', 32, '随笔', 'ghl', 1, 0, 0, 1, '2022-07-01 16:33:57', '2022-07-01 16:33:57');
INSERT INTO `tb_blog` VALUES (33, 'hello world', '', 'http://localhost:8081/admin/dist/img/rand/2.jpg', '123', 32, '随笔', 'hello', 1, 0, 0, 1, '2022-07-01 16:39:38', '2022-07-01 16:39:38');
INSERT INTO `tb_blog` VALUES (34, 'hello baby', '', 'http://localhost:8081/admin/dist/img/rand/16.jpg', 'kid', 32, '随笔', 'hello', 1, 0, 0, 1, '2022-07-01 16:39:55', '2022-07-01 16:39:55');
INSERT INTO `tb_blog` VALUES (35, 'hello', '', 'http://localhost:8081/admin/dist/img/rand/16.jpg', 'hello', 32, '随笔', 'hello', 1, 0, 0, 1, '2022-07-01 16:41:09', '2022-07-01 16:41:09');
INSERT INTO `tb_blog` VALUES (36, 'hello w', '', 'http://localhost:8081/admin/dist/img/rand/36.jpg', 'ghl', 32, '随笔', 'hello', 1, 0, 0, 1, '2022-07-01 16:43:22', '2022-07-01 16:43:22');

-- ----------------------------
-- Table structure for tb_blog_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog_category`;
CREATE TABLE `tb_blog_category`  (
  `category_id` int NOT NULL AUTO_INCREMENT COMMENT '分类表主键',
  `category_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分类的名称',
  `category_icon` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分类的图标',
  `category_rank` int NOT NULL DEFAULT 1 COMMENT '分类的排序值 被使用的越多数值越大',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除 0=否 1=是',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_blog_category
-- ----------------------------
INSERT INTO `tb_blog_category` VALUES (26, 'database', '/admin/dist/img/category/04.png', 1, 0, '2022-07-01 09:04:39');
INSERT INTO `tb_blog_category` VALUES (27, 'linux', '/admin/dist/img/category/13.png', 1, 0, '2022-07-01 09:04:53');
INSERT INTO `tb_blog_category` VALUES (28, 'java', '/admin/dist/img/category/02.png', 1, 0, '2022-07-01 09:05:00');
INSERT INTO `tb_blog_category` VALUES (29, 'html', '/admin/dist/img/category/16.png', 1, 0, '2022-07-01 09:05:12');
INSERT INTO `tb_blog_category` VALUES (30, 'android', '/admin/dist/img/category/03.png', 2, 0, '2022-07-01 09:05:26');
INSERT INTO `tb_blog_category` VALUES (32, '随笔', '/admin/dist/img/category/06.png', 15, 0, '2022-07-01 09:06:08');

-- ----------------------------
-- Table structure for tb_blog_comment
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog_comment`;
CREATE TABLE `tb_blog_comment`  (
  `comment_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `blog_id` bigint NOT NULL DEFAULT 0 COMMENT '关联的blog主键',
  `commentator` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '评论者名称',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '评论人的邮箱',
  `website_url` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '网址',
  `comment_body` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '评论内容',
  `comment_create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评论提交时间',
  `commentator_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '评论时的ip地址',
  `reply_body` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '回复内容',
  `reply_create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '回复时间',
  `comment_status` tinyint NOT NULL DEFAULT 0 COMMENT '是否审核通过 0-未审核 1-审核通过',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '是否删除 0-未删除 1-已删除',
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_blog_comment
-- ----------------------------
INSERT INTO `tb_blog_comment` VALUES (27, 1, 'ghl', '1519469195@qq.com', '', '加油', '2022-07-01 16:46:23', '', '好啊', '2022-07-01 08:53:29', 1, 0);
INSERT INTO `tb_blog_comment` VALUES (28, 1, '111', '25164@163.com', '', 'loser', '2022-07-01 16:47:05', '', '', '2022-07-01 16:47:05', 0, 1);

-- ----------------------------
-- Table structure for tb_blog_tag
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog_tag`;
CREATE TABLE `tb_blog_tag`  (
  `tag_id` int NOT NULL AUTO_INCREMENT COMMENT '标签表主键id',
  `tag_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标签名称',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除 0=否 1=是',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 149 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_blog_tag
-- ----------------------------
INSERT INTO `tb_blog_tag` VALUES (135, '自我介绍', 1, '2022-07-01 09:23:07');
INSERT INTO `tb_blog_tag` VALUES (136, '润学', 1, '2022-07-01 09:23:24');
INSERT INTO `tb_blog_tag` VALUES (137, '延寿指南', 1, '2022-07-01 09:23:57');
INSERT INTO `tb_blog_tag` VALUES (138, '做饭指南', 1, '2022-07-01 09:24:07');
INSERT INTO `tb_blog_tag` VALUES (139, '个人', 1, '2022-07-01 09:32:20');
INSERT INTO `tb_blog_tag` VALUES (140, '润学', 1, '2022-07-01 09:32:28');
INSERT INTO `tb_blog_tag` VALUES (141, '1', 1, '2022-07-01 10:56:59');
INSERT INTO `tb_blog_tag` VALUES (142, '123', 1, '2022-07-01 10:59:41');
INSERT INTO `tb_blog_tag` VALUES (143, '12', 1, '2022-07-01 12:24:10');
INSERT INTO `tb_blog_tag` VALUES (144, 'hello', 1, '2022-07-01 14:53:47');
INSERT INTO `tb_blog_tag` VALUES (145, '个人', 0, '2022-07-01 14:56:02');
INSERT INTO `tb_blog_tag` VALUES (146, 'hello', 1, '2022-07-01 14:56:21');
INSERT INTO `tb_blog_tag` VALUES (147, 'kid', 1, '2022-07-01 15:09:12');
INSERT INTO `tb_blog_tag` VALUES (148, 'a', 1, '2022-07-01 15:09:48');
INSERT INTO `tb_blog_tag` VALUES (149, 'hello', 1, '2022-07-01 15:33:50');
INSERT INTO `tb_blog_tag` VALUES (150, 'kk', 1, '2022-07-01 16:33:27');
INSERT INTO `tb_blog_tag` VALUES (151, 'ghl', 1, '2022-07-01 16:33:57');
INSERT INTO `tb_blog_tag` VALUES (152, 'hello', 1, '2022-07-01 16:39:38');

-- ----------------------------
-- Table structure for tb_blog_tag_relation
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog_tag_relation`;
CREATE TABLE `tb_blog_tag_relation`  (
  `relation_id` bigint NOT NULL AUTO_INCREMENT COMMENT '关系表id',
  `blog_id` bigint NOT NULL COMMENT '博客id',
  `tag_id` int NOT NULL COMMENT '标签id',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`relation_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 297 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_blog_tag_relation
-- ----------------------------
INSERT INTO `tb_blog_tag_relation` VALUES (291, 1, 145, '2022-07-01 14:56:02');

-- ----------------------------
-- Table structure for tb_config
-- ----------------------------
DROP TABLE IF EXISTS `tb_config`;
CREATE TABLE `tb_config`  (
  `config_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '配置项的名称',
  `config_value` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '配置项的值',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`config_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_config
-- ----------------------------
INSERT INTO `tb_config` VALUES ('footerAbout', 'Hello world!', '2018-11-11 20:33:23', '2018-11-12 11:58:06');
INSERT INTO `tb_config` VALUES ('footerCopyRight', '广寒璃', '2018-11-11 20:33:31', '2018-11-12 11:58:06');
INSERT INTO `tb_config` VALUES ('footerICP', '鄂ICP备 xxxxxx-x号', '2018-11-11 20:33:27', '2018-11-12 11:58:06');
INSERT INTO `tb_config` VALUES ('footerPoweredBy', 'https://github.com/violet-cai', '2018-11-11 20:33:36', '2018-11-12 11:58:06');
INSERT INTO `tb_config` VALUES ('footerPoweredByURL', 'https://github.com/violet-cai', '2018-11-11 20:33:39', '2018-11-12 11:58:06');
INSERT INTO `tb_config` VALUES ('websiteDescription', 'This is your blog.', '2018-11-11 20:33:04', '2018-11-11 22:05:14');
INSERT INTO `tb_config` VALUES ('websiteIcon', '/admin/dist/img/favicon.png', '2018-11-11 20:33:11', '2018-11-11 22:05:14');
INSERT INTO `tb_config` VALUES ('websiteLogo', '/admin/dist/img/logo2.png', '2018-11-11 20:33:08', '2018-11-11 22:05:14');
INSERT INTO `tb_config` VALUES ('websiteName', 'personal blog', '2018-11-11 20:33:01', '2018-11-11 22:05:14');
INSERT INTO `tb_config` VALUES ('yourAvatar', '/admin/dist/img/ghl.png', '2018-11-11 20:33:14', '2019-05-07 21:56:23');
INSERT INTO `tb_config` VALUES ('yourEmail', '1519469195@qq.com', '2018-11-11 20:33:17', '2019-05-07 21:56:23');
INSERT INTO `tb_config` VALUES ('yourName', '广寒璃', '2018-11-11 20:33:20', '2019-05-07 21:56:23');

-- ----------------------------
-- Table structure for tb_link
-- ----------------------------
DROP TABLE IF EXISTS `tb_link`;
CREATE TABLE `tb_link`  (
  `link_id` int NOT NULL AUTO_INCREMENT COMMENT '友链表主键id',
  `link_type` tinyint NOT NULL DEFAULT 0 COMMENT '友链类别 0-友链 1-推荐 2-个人网站',
  `link_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '网站名称',
  `link_url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '网站链接',
  `link_description` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '网站描述',
  `link_rank` int NOT NULL DEFAULT 0 COMMENT '用于列表排序',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除 0-未删除 1-已删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`link_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_link
-- ----------------------------
INSERT INTO `tb_link` VALUES (1, 0, 'tqr', 'rqwe', 'rqw', 0, 1, '2018-10-22 18:57:52');
INSERT INTO `tb_link` VALUES (2, 2, '十三的GitHub', 'https://github.com/ZHENFENG13', '十三分享代码的地方', 1, 1, '2018-10-22 19:41:04');
INSERT INTO `tb_link` VALUES (3, 2, '十三的博客', 'http://13blog.site', '个人独立博客13blog', 14, 1, '2018-10-22 19:53:34');
INSERT INTO `tb_link` VALUES (4, 1, 'CSDN 图文课', 'https://gitchat.csdn.net', 'IT优质内容平台', 6, 1, '2018-10-22 19:55:55');
INSERT INTO `tb_link` VALUES (5, 2, '十三的博客园', 'https://www.cnblogs.com/han-1034683568', '最开始写博客的地方', 17, 1, '2018-10-22 19:56:14');
INSERT INTO `tb_link` VALUES (6, 1, 'CSDN', 'https://www.csdn.net/', 'CSDN-专业IT技术社区官网', 4, 1, '2018-10-22 19:56:47');
INSERT INTO `tb_link` VALUES (7, 0, '梁桂钊的博客', 'http://blog.720ui.com', '后端攻城狮', 1, 1, '2018-10-22 20:01:38');
INSERT INTO `tb_link` VALUES (8, 0, '猿天地', 'http://cxytiandi.com', '一个综合性的网站,以程序猿用户为主,提供各种开发相关的内容', 12, 1, '2018-10-22 20:02:41');
INSERT INTO `tb_link` VALUES (9, 0, 'Giraffe Home', 'https://yemengying.com/', 'Giraffe Home', 0, 1, '2018-10-22 20:27:04');
INSERT INTO `tb_link` VALUES (10, 0, '纯洁的微笑', 'http://www.ityouknow.com', '分享技术，分享生活', 3, 1, '2018-10-22 20:27:16');
INSERT INTO `tb_link` VALUES (11, 0, 'afsdf', 'http://localhost:28080/admin/links', 'fad', 0, 1, '2018-10-22 20:27:26');
INSERT INTO `tb_link` VALUES (12, 1, 'afsdf', 'http://localhost', 'fad1', 0, 1, '2018-10-24 14:04:18');
INSERT INTO `tb_link` VALUES (13, 0, '郭赵晖', 'http://guozh.net/', '老郭三分地', 0, 1, '2019-04-24 15:30:19');
INSERT INTO `tb_link` VALUES (14, 0, 'dalaoyang', 'https://www.dalaoyang.cn/', 'dalaoyang', 0, 1, '2019-04-24 15:31:50');
INSERT INTO `tb_link` VALUES (15, 0, 'mushblog', 'https://www.sansani.cn', '穆世明博客', 0, 1, '2019-04-24 15:32:19');
INSERT INTO `tb_link` VALUES (16, 1, '实验楼', 'https://www.shiyanlou.com/', '一家专注于IT技术的在线实训平台', 17, 1, '2019-04-24 16:03:48');
INSERT INTO `tb_link` VALUES (17, 2, '《SSM 搭建精美实用的管理系统》', 'https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9', 'Spring+SpringMVC+MyBatis实战课程', 18, 1, '2019-04-24 16:06:52');
INSERT INTO `tb_link` VALUES (18, 2, '《Spring Boot 入门及前后端分离项目实践》', 'https://www.shiyanlou.com/courses/1244', 'SpringBoot实战课程', 19, 1, '2019-04-24 16:07:27');
INSERT INTO `tb_link` VALUES (19, 2, '《玩转Spring Boot 系列》', 'https://www.shiyanlou.com/courses/1274', 'SpringBoot实战课程', 20, 1, '2019-04-24 16:10:30');
INSERT INTO `tb_link` VALUES (20, 1, 'github', 'https://github.com/', '全球最大的社交编程及代码托管网站', 1, 0, '2022-07-01 09:16:27');
INSERT INTO `tb_link` VALUES (21, 1, 'b站', 'https://www.bilibili.com/', '大学生真正的学习网站', 2, 0, '2022-07-01 09:16:51');
INSERT INTO `tb_link` VALUES (22, 1, 'leetcode', 'https://leetcode.cn/', '刷题必备网站', 3, 0, '2022-07-01 09:17:31');
INSERT INTO `tb_link` VALUES (23, 0, 'youtube', 'https://www.youtube.com/', '油管', 4, 0, '2022-07-01 09:18:29');
INSERT INTO `tb_link` VALUES (24, 0, '京东', 'https://www.jd.com/', '购物', 5, 0, '2022-07-01 09:18:55');
INSERT INTO `tb_link` VALUES (25, 2, 'violet-cai', 'https://github.com/violet-cai', '个人网址', 0, 0, '2022-07-01 09:19:31');

SET FOREIGN_KEY_CHECKS = 1;
