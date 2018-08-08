/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : hteos4

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-08-02 13:35:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for hteos_access
-- ----------------------------
DROP TABLE IF EXISTS `hteos_access`;
CREATE TABLE `hteos_access` (
  `id` varchar(255) NOT NULL,
  `city` varchar(255) DEFAULT NULL,
  `day` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `month` varchar(255) DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  `time` varchar(255) DEFAULT NULL,
  `user` varchar(255) DEFAULT NULL,
  `year` varchar(255) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `referer` varchar(255) DEFAULT NULL,
  `duration` bigint(20) DEFAULT NULL,
  `browser` varchar(255) DEFAULT NULL,
  `device` varchar(255) DEFAULT NULL,
  `os` varchar(255) DEFAULT NULL,
  `access_count` bigint(20) DEFAULT NULL,
  `browser_version` varchar(255) DEFAULT NULL,
  `user_agent` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hteos_access
-- ----------------------------

-- ----------------------------
-- Table structure for hteos_app
-- ----------------------------
DROP TABLE IF EXISTS `hteos_app`;
CREATE TABLE `hteos_app` (
  `id` varchar(255) NOT NULL,
  `category` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `app_desc` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `provider` varchar(255) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `app_version` varchar(255) DEFAULT NULL,
  `install_count` int(11) DEFAULT NULL,
  `release_date` datetime DEFAULT NULL,
  `review_count` int(11) DEFAULT NULL,
  `is_default` bit(1) DEFAULT NULL,
  `is_native` bit(1) DEFAULT NULL,
  `tile_id` varchar(255) DEFAULT NULL,
  `task_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjny7q1dxb7ny7b6y8gqfnpwl3` (`tile_id`),
  KEY `FKt2k96sh2jxxu26g72b3a7lvw1` (`task_id`),
  CONSTRAINT `hteos_app_ibfk_1` FOREIGN KEY (`tile_id`) REFERENCES `hteos_app_tile` (`id`),
  CONSTRAINT `hteos_app_ibfk_2` FOREIGN KEY (`task_id`) REFERENCES `hteos_app_task` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hteos_app
-- ----------------------------
INSERT INTO `hteos_app` VALUES ('adf28bd452ff464a0152ff4ab9320000', '2', '', '腾讯网 - 图片新闻', 'http://asset.hteos.com/images/apps/icons/adf28bd452ff464a0152ff4ab9320000.png', 'http://asset.hteos.com/images/apps/images/adf28bd452ff464a0152ff4ab9320000.png', '图片新闻', null, '0', null, '腾讯网 - 图片新闻', 'v1.1', '0', null, '0', '', '', 'adf28bd452ff464a0152ff4ab9320000', 'adf28bd452ff464a0152ff4ab9320000');
INSERT INTO `hteos_app` VALUES ('adf28bd453021445015306eb38ce0000', '2', 'news', '新浪网 - 滚动新闻', 'http://asset.hteos.com/images/apps/icons/adf28bd453021445015306eb38ce0000.png', 'http://asset.hteos.com/images/apps/images/adf28bd453021445015306eb38ce0000.png', '新浪滚动新闻', null, '5', null, '新浪网 - 滚动新闻', 'v1.1', '1', null, '1', '', '', 'adf28bd453021445015306eb38ce0000', 'adf28bd453021445015306eb38ce0000');
INSERT INTO `hteos_app` VALUES ('adf28bd453021445015306ed2ee20001', '4', 'iqiyi', '爱奇艺', 'http://asset.hteos.com/images/apps/icons/adf28bd453021445015306ed2ee20001.png', 'http://asset.hteos.com/images/apps/images/adf28bd453021445015306ed2ee20001.png', '爱奇艺', null, '0', null, '爱奇艺', 'v1.1', '1', null, '0', '', '', 'adf28bd453021445015306ed2ee20001', 'adf28bd453021445015306ed2ee20001');
INSERT INTO `hteos_app` VALUES ('adf28bd453021445015306f095400002', '4', 'kugou', '酷狗音乐', 'http://asset.hteos.com/images/apps/icons/adf28bd453021445015306f095400002.png', 'http://asset.hteos.com/images/apps/images/adf28bd453021445015306f095400002.png', '酷狗音乐', null, '0', null, '酷狗音乐', 'v1.1', '1', null, '0', '', '', 'adf28bd453021445015306f095400002', 'adf28bd453021445015306f095400002');
INSERT INTO `hteos_app` VALUES ('adf28bd453021445015306f3fefa0003', '2', 'weather', '百度天气', 'http://asset.hteos.com/images/apps/icons/adf28bd453021445015306f3fefa0003.png', 'http://asset.hteos.com/images/apps/images/adf28bd453021445015306f3fefa0003.png', '天气', null, '0', null, '百度天气', 'v1.1', '3', null, '0', '', null, 'adf28bd453021445015306f3fefa0003', 'adf28bd453021445015306f3fefa0003');
INSERT INTO `hteos_app` VALUES ('adf28bd453021445015306f530b40004', '2', 'stock', '新浪股市', 'http://asset.hteos.com/images/apps/icons/adf28bd453021445015306f530b40004.png', 'http://asset.hteos.com/images/apps/images/adf28bd453021445015306f530b40004.png', '新浪股市', null, '0', null, '新浪股市', 'v1.1', '1', null, '0', '', '', 'adf28bd453021445015306f530b40004', 'adf28bd453021445015306f530b40004');
INSERT INTO `hteos_app` VALUES ('adf28bd453021445015306f7c5980005', '2', '', '淘宝网', 'http://asset.hteos.com/images/apps/icons/adf28bd453021445015306f7c5980005.png', 'http://asset.hteos.com/images/apps/images/adf28bd453021445015306f7c5980005.png', '淘宝网', null, '0', null, '淘宝网', 'v1.1', '1', null, '0', '', null, 'adf28bd453021445015306f7c5980005', 'adf28bd453021445015306f7c5980005');
INSERT INTO `hteos_app` VALUES ('adf28bd453021445015306fa7dc40006', '1', 'clock', '时钟', 'http://asset.hteos.com/images/apps/icons/adf28bd453021445015306fa7dc40006.png', 'http://asset.hteos.com/images/apps/images/adf28bd453021445015306fa7dc40006.png', '时钟', null, '0', null, '时钟', 'v1.1', '1', null, '0', '', null, 'adf28bd453021445015306fa7dc40006', 'adf28bd453021445015306fa7dc40006');
INSERT INTO `hteos_app` VALUES ('adf28bd453021445015306fc3a3b0007', '2', 'myhteos', '我的HteOS', 'http://asset.hteos.com/images/apps/icons/adf28bd453021445015306fc3a3b0007.png', 'http://asset.hteos.com/images/apps/images/adf28bd453021445015306fc3a3b0007.png', '我的HteOS', null, '0', null, '我的HteOS', 'v1.1', '1', null, '0', '', '', 'adf28bd453021445015306fc3a3b0007', 'adf28bd453021445015306fc3a3b0007');
INSERT INTO `hteos_app` VALUES ('adf28bd453021445015306ff2bc80008', '2', 'settings', '个性化设置', 'http://asset.hteos.com/images/apps/icons/adf28bd453021445015306ff2bc80008.png', 'http://asset.hteos.com/images/apps/images/adf28bd453021445015306ff2bc80008.png', '个性化设置', null, '0', null, '个性化设置', 'v1.1', '1', null, '0', '', '', 'adf28bd453021445015306ff2bc80008', 'adf28bd453021445015306ff2bc80008');
INSERT INTO `hteos_app` VALUES ('adf28bd4530214450153070163890009', '2', 'store', '应用市场', 'http://asset.hteos.com/images/apps/icons/adf28bd4530214450153070163890009.png', 'http://asset.hteos.com/images/apps/images/adf28bd4530214450153070163890009.png', '应用市场', null, '0', null, '应用市场', 'v1.1', '1', null, '0', '', '', 'adf28bd4530214450153070163890009', 'adf28bd4530214450153070163890009');
INSERT INTO `hteos_app` VALUES ('adf28bd453021445015307033d9a000c', '4', 'music', '9酷FM', 'http://asset.hteos.com//images/groups/icons/adf28bd453021445015307033d9a000c.png', 'http://asset.hteos.com//images/groups/images/adf28bd453021445015307033d9a000c.png', '9酷FM', null, '0', null, '9酷FM', 'v1.1', '1', null, '0', '', '', 'adf28bd453021445015307033d9a000c', 'adf28bd453021445015307033d9a000c');
INSERT INTO `hteos_app` VALUES ('adf28bd454a540dc0154a54c84550000', '8', 'kuaidi', '快递100', 'http://asset.hteos.com/images/apps/icons/adf28bd454a540dc0154a54c84550000.png', 'http://asset.hteos.com/images/apps/images/adf28bd454a540dc0154a54c84550000.png', '快递100', null, '0', null, '快递100', 'v1.1', '1', null, '0', '', '', 'adf28bd454a540dc0154a54c84550000', 'adf28bd454a540dc0154a54c84550000');
INSERT INTO `hteos_app` VALUES ('adf28bd454b9af650154bf2017b5004e', '2', '', '乐视网', 'http://asset.hteos.com/images/apps/icons/adf28bd454b9af650154bf2017b5004e.png', 'http://asset.hteos.com/images/apps/images/adf28bd454b9af650154bf2017b5004e.png', '乐视网', null, '0', null, '乐视网', '1', '-1', null, '0', '', null, 'adf28bd454b9af650154bf2017b5004e', 'adf28bd454b9af650154bf2017b5004e');
INSERT INTO `hteos_app` VALUES ('adf28bd454b9af650154bf20d086004f', '2', '', '网易新闻', 'http://asset.hteos.com/images/apps/icons/adf28bd454b9af650154bf20d086004f.png', 'http://asset.hteos.com/images/apps/images/adf28bd454b9af650154bf20d086004f.png', '网易新闻', null, '0', null, '网易新闻', '1', '-1', null, '0', '', null, 'adf28bd454b9af650154bf20d086004f', 'adf28bd454b9af650154bf20d086004f');
INSERT INTO `hteos_app` VALUES ('adf28bd454b9af650154bf224eca0050', '2', '', '知乎', 'http://asset.hteos.com/images/apps/icons/adf28bd454b9af650154bf224eca0050.png', 'http://asset.hteos.com/images/apps/images/adf28bd454b9af650154bf224eca0050.png', '知乎', null, '0', null, '知乎', '1', '1', null, '0', '', null, 'adf28bd454b9af650154bf224eca0050', 'adf28bd454b9af650154bf224eca0050');
INSERT INTO `hteos_app` VALUES ('adf28bd454b9af650154bf2431170051', '2', '', '携程旅游', 'http://asset.hteos.com/images/apps/icons/adf28bd454b9af650154bf2431170051.png', 'http://asset.hteos.com/', '携程旅游', null, '0', null, '携程旅游', '1', '1', null, '0', '', null, 'adf28bd454b9af650154bf2431170051', 'adf28bd454b9af650154bf2431170051');
INSERT INTO `hteos_app` VALUES ('adf28bd454b9af650154bf25bb810052', '2', '', '汽车之家', 'http://asset.hteos.com/images/apps/icons/adf28bd454b9af650154bf25bb810052.png', 'http://asset.hteos.com/images/apps/images/adf28bd454b9af650154bf25bb810052.png', '汽车之家', null, '0', null, '汽车之家', '1', '0', null, '0', '', null, 'adf28bd454b9af650154bf25bb810052', 'adf28bd454b9af650154bf25bb810052');
INSERT INTO `hteos_app` VALUES ('adf28bd454b9af650154bf26774f0053', '2', '', '有道词典', 'http://asset.hteos.com/images/apps/icons/adf28bd454b9af650154bf26774f0053.png', 'http://asset.hteos.com/images/apps/images/adf28bd454b9af650154bf26774f0053.png', '有道词典', null, '0', null, '有道词典', '1', '0', null, '0', '', null, 'adf28bd454b9af650154bf26774f0053', 'adf28bd454b9af650154bf26774f0053');
INSERT INTO `hteos_app` VALUES ('adf28bd454b9af650154bf2734850054', '2', '', '唱吧', 'http://asset.hteos.com/images/apps/icons/adf28bd454b9af650154bf2734850054.png', 'http://asset.hteos.com/images/apps/images/adf28bd454b9af650154bf2734850054.png', '唱吧', null, '0', null, '唱吧', '1', '0', null, '0', '', null, 'adf28bd454b9af650154bf2734850054', 'adf28bd454b9af650154bf2734850054');
INSERT INTO `hteos_app` VALUES ('adf28bd454b9af650154bf27be470055', '2', '', 'Youtube', 'http://asset.hteos.com/images/apps/icons/adf28bd454b9af650154bf27be470055.png', 'http://asset.hteos.com/images/apps/images/adf28bd454b9af650154bf27be470055.png', 'Youtube', null, '0', null, 'Youtube', '1', '2', null, '0', '', null, 'adf28bd454b9af650154bf27be470055', 'adf28bd454b9af650154bf27be470055');
INSERT INTO `hteos_app` VALUES ('adf28bd454b9af650154bf28b28b0056', '1', '', '百度地图', 'http://asset.hteos.com/images/apps/icons/adf28bd454b9af650154bf28b28b0056.png', 'http://asset.hteos.com/images/apps/images/adf28bd454b9af650154bf28b28b0056.png', '百度地图', 'HteOS官方', '0', null, '百度地图', '1', '1', null, '0', '', '\0', 'adf28bd454b9af650154bf28b28b0056', 'adf28bd454b9af650154bf28b28b0056');
INSERT INTO `hteos_app` VALUES ('adf28bd454b9af650154bf29e9b30057', '2', '', '美团外卖', 'http://asset.hteos.com/images/apps/icons/adf28bd454b9af650154bf29e9b30057.png', 'http://asset.hteos.com/images/apps/images/adf28bd454b9af650154bf29e9b30057.png', '美团外卖', null, '0', null, '美团外卖', '1', '1', null, '0', '', null, 'adf28bd454b9af650154bf29e9b30057', 'adf28bd454b9af650154bf29e9b30057');
INSERT INTO `hteos_app` VALUES ('adf28bd454b9af650154bf2ab07e0058', '1', '', '愤怒的小鸟', 'http://asset.hteos.com/images/apps/icons/adf28bd454b9af650154bf2ab07e0058.png', 'http://asset.hteos.com/images/apps/images/adf28bd454b9af650154bf2ab07e0058.png', '愤怒的小鸟', 'HteOS官方', '0', null, '愤怒的小鸟', '1', '1', null, '0', '', null, 'adf28bd454b9af650154bf2ab07e0058', 'adf28bd454b9af650154bf2ab07e0058');
INSERT INTO `hteos_app` VALUES ('adf28bd454b9af650154cbd1b2e50128', '8', '', 'ProcessOn', 'http://asset.hteos.com/images/apps/icons/adf28bd454b9af650154cbd1b2e50128.png', 'http://asset.hteos.com/images/apps/images/adf28bd454b9af650154cbd1b2e50128.png', 'ProcessOn', 'HteOS官方', '0', null, 'ProcessOn', '1', '2', null, '0', '', '', 'adf28bd454b9af650154cbd1b2e50128', 'adf28bd454b9af650154cbd1b2e50128');
INSERT INTO `hteos_app` VALUES ('adf28bd454b9af650154cbd4fb980146', '8', '', '有道云笔记', 'http://asset.hteos.com/images/apps/icons/adf28bd454b9af650154cbd4fb980146.png', 'http://asset.hteos.com/images/apps/images/adf28bd454b9af650154cbd4fb980146.png', '有道云笔记', 'HteOS官方', '0', null, '有道云笔记', '1', '2', null, '0', '', '\0', 'adf28bd454b9af650154cbd4fb980146', 'adf28bd454b9af650154cbd4fb980146');
INSERT INTO `hteos_app` VALUES ('adf28bd454b9af650154cbeb93de014a', '8', '', 'QQ日历', 'http://asset.hteos.com/images/apps/icons/adf28bd454b9af650154cbeb93de014a.png', 'http://asset.hteos.com/', 'QQ日历', 'HteOS官方', '0', null, 'QQ日历', '1', '1', null, '0', '', '\0', 'adf28bd454b9af650154cbeb93de014a', 'adf28bd454b9af650154cbeb93de014a');
INSERT INTO `hteos_app` VALUES ('adf28bd454b9af650154cd480f04015a', '8', '', '微云', 'http://asset.hteos.com/images/apps/icons/adf28bd454b9af650154cd480f04015a.png', 'http://asset.hteos.com/images/apps/images/adf28bd454b9af650154cd480f04015a.png', '微云', 'HteOS官方', '0', null, '微云', '1', '4', null, '0', '', '\0', 'adf28bd454b9af650154cd480f04015a', 'adf28bd454b9af650154cd480f04015a');

-- ----------------------------
-- Table structure for hteos_app_task
-- ----------------------------
DROP TABLE IF EXISTS `hteos_app_task`;
CREATE TABLE `hteos_app_task` (
  `id` varchar(255) NOT NULL,
  `bottom` int(11) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `height` int(11) DEFAULT NULL,
  `position_left` int(11) DEFAULT NULL,
  `maximizable` bit(1) DEFAULT NULL,
  `maximized` bit(1) DEFAULT NULL,
  `resizable` bit(1) DEFAULT NULL,
  `position_right` int(11) DEFAULT NULL,
  `script` longtext,
  `shell` varchar(255) DEFAULT NULL,
  `template` varchar(255) DEFAULT NULL,
  `template_url` varchar(255) DEFAULT NULL,
  `top` int(11) DEFAULT NULL,
  `width` int(11) DEFAULT NULL,
  `controller` varchar(255) DEFAULT NULL,
  `dependencies` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hteos_app_task
-- ----------------------------
INSERT INTO `hteos_app_task` VALUES ('297ee60864ac364e0164ac3a72600003', null, 'task', null, null, null, null, null, null, '', null, '', '', null, null, null, null);
INSERT INTO `hteos_app_task` VALUES ('297ee60864ad82570164ad86bdbe0003', null, 'task', null, null, null, null, null, null, '', null, '', '', null, null, null, null);
INSERT INTO `hteos_app_task` VALUES ('adf28bd452ff464a0152ff4ab9320000', '0', 'none', '0', '0', '', null, '', '0', '', 'window', '', '', '0', '0', '', null);
INSERT INTO `hteos_app_task` VALUES ('adf28bd453021445015306eb38ce0000', '0', 'none', '0', '0', '', null, '', '0', '', 'window', '', '', '0', '0', '', null);
INSERT INTO `hteos_app_task` VALUES ('adf28bd453021445015306ed2ee20001', '0', 'task', '0', '0', '', null, '', '0', '', 'window', '<iframe frameborder=\"0\" src=\"http://www.iqiyi.com\"></iframe>', '', '0', '0', '', null);
INSERT INTO `hteos_app_task` VALUES ('adf28bd453021445015306f095400002', '0', 'task', '540', '0', '', null, '', '0', '', 'window', '<iframe frameborder=\"0\" src=\"http://web.kugou.com\"></iframe>', '', '0', '745', '', null);
INSERT INTO `hteos_app_task` VALUES ('adf28bd453021445015306f3fefa0003', '0', 'none', '0', '0', '', null, '', '0', '', 'window', '', '', '0', '0', '', null);
INSERT INTO `hteos_app_task` VALUES ('adf28bd453021445015306f530b40004', '0', 'none', '0', '0', '', null, '', '0', '', 'window', '', '', '0', '0', '', null);
INSERT INTO `hteos_app_task` VALUES ('adf28bd453021445015306f7c5980005', '0', 'task', '0', '0', '', null, '', '0', '', 'window', '<iframe frameborder=\"0\" src=\"http://www.taobao.com\"></iframe>', '', '0', '0', '', null);
INSERT INTO `hteos_app_task` VALUES ('adf28bd453021445015306fa7dc40006', '0', 'task', '0', '0', '', null, '', '0', '', 'window', '', '', '0', '0', null, null);
INSERT INTO `hteos_app_task` VALUES ('adf28bd453021445015306fc3a3b0007', '0', 'task', '0', '0', '', null, '', '0', '', 'dock', '', 'tpl/profile.html', '0', '0', 'HteOS.controller.Profile', null);
INSERT INTO `hteos_app_task` VALUES ('adf28bd453021445015306ff2bc80008', '0', 'task', '0', '0', '', null, '', '0', '', 'dock', '', 'setting.html', '0', '0', 'HteOS.controller.Setting', null);
INSERT INTO `hteos_app_task` VALUES ('adf28bd4530214450153070163890009', '0', 'task', '600', '0', '', null, '', '0', '', 'window', '', 'tpl/store.html', '0', '1180', 'HteOS.controller.Store', null);
INSERT INTO `hteos_app_task` VALUES ('adf28bd453021445015307033d9a000c', '0', 'none', '0', '0', '', null, '', '0', '', 'window', '', '', '0', '0', '', null);
INSERT INTO `hteos_app_task` VALUES ('adf28bd454a540dc0154a54c84550000', '0', 'task', '450', '0', '\0', null, '\0', '0', '', 'window', '<iframe src=\"http://baidu.kuaidi100.com/index2.html\" ></iframe>', '', '0', '546', null, null);
INSERT INTO `hteos_app_task` VALUES ('adf28bd454b9af650154bf2017b5004e', '0', 'task', '0', '0', '', null, '', '0', '', 'window', '<iframe frameborder=\"0\" src=\"http://www.letv.com/\"></iframe>', '', '0', '0', '', null);
INSERT INTO `hteos_app_task` VALUES ('adf28bd454b9af650154bf20d086004f', '0', 'task', '0', '0', '', null, '', '0', '', 'window', '<iframe frameborder=\"0\" src=\"http://news.163.com\"></iframe>', '', '0', '0', '', null);
INSERT INTO `hteos_app_task` VALUES ('adf28bd454b9af650154bf224eca0050', '0', 'task', '0', '0', '', null, '', '0', '', 'window', '<iframe frameborder=\"0\" src=\"http://www.zhihu.com\"></iframe>', '', '0', '0', '', null);
INSERT INTO `hteos_app_task` VALUES ('adf28bd454b9af650154bf2431170051', '0', 'task', '0', '0', '', null, '', '0', '', 'window', '<iframe frameborder=\"0\" src=\"http://www.xiecheng.com/\"></iframe>', '', '0', '0', '', null);
INSERT INTO `hteos_app_task` VALUES ('adf28bd454b9af650154bf25bb810052', '0', 'task', '0', '0', '', null, '', '0', '', 'window', '<iframe frameborder=\"0\" src=\"http://www.autohome.com\"></iframe>', '', '0', '0', '', null);
INSERT INTO `hteos_app_task` VALUES ('adf28bd454b9af650154bf26774f0053', '0', 'task', '0', '0', '', null, '', '0', '', 'window', '<iframe frameborder=\"0\" src=\"http://www.youdao.com\"></iframe>', '', '0', '0', '', null);
INSERT INTO `hteos_app_task` VALUES ('adf28bd454b9af650154bf2734850054', '0', 'task', '0', '0', '', null, '', '0', '', 'window', '<iframe frameborder=\"0\" src=\"http://www.changba.com\"></iframe>', '', '0', '0', '', null);
INSERT INTO `hteos_app_task` VALUES ('adf28bd454b9af650154bf27be470055', '0', 'task', '0', '0', '', null, '', '0', '', 'window', '', 'http://www.youtube.com', '0', '0', '', null);
INSERT INTO `hteos_app_task` VALUES ('adf28bd454b9af650154bf28b28b0056', '0', 'task', '0', '0', '', null, '', '0', '', 'window', null, 'http://map.baidu.com', '0', '0', null, null);
INSERT INTO `hteos_app_task` VALUES ('adf28bd454b9af650154bf29e9b30057', '0', 'task', '0', '0', '', null, '', '0', '', 'window', '', 'http://waimai.meituan.com/', '0', '0', '', null);
INSERT INTO `hteos_app_task` VALUES ('adf28bd454b9af650154bf2ab07e0058', '0', 'task', '0', '0', '', null, '', '0', '', 'window', '', 'http://www.3366.com/flash/80774.shtml', '0', '0', null, null);
INSERT INTO `hteos_app_task` VALUES ('adf28bd454b9af650154cbd1b2e50128', '0', 'task', '0', '0', '', null, '', '0', '', 'window', null, 'https://www.processon.com/', '0', '0', null, null);
INSERT INTO `hteos_app_task` VALUES ('adf28bd454b9af650154cbd4fb980146', '0', 'task', '0', '0', '', null, '', '0', '', 'window', null, 'http://note.youdao.com/web', '0', '0', null, null);
INSERT INTO `hteos_app_task` VALUES ('adf28bd454b9af650154cbeb93de014a', '0', 'task', '0', '0', '', null, '', '0', '', 'window', null, 'http://calendar.snsapp.qq.com/cgi-bin/my_calendar_mainpage', '0', '0', null, null);
INSERT INTO `hteos_app_task` VALUES ('adf28bd454b9af650154cd480f04015a', '0', 'task', '0', '0', '', null, '', '0', '', 'window', null, 'https://www.weiyun.com/disk/index.html', '0', '0', null, null);

-- ----------------------------
-- Table structure for hteos_app_tile
-- ----------------------------
DROP TABLE IF EXISTS `hteos_app_tile`;
CREATE TABLE `hteos_app_tile` (
  `id` varchar(255) NOT NULL,
  `bgcolor` varchar(50) DEFAULT NULL,
  `color` varchar(50) DEFAULT NULL,
  `controller` varchar(100) DEFAULT NULL,
  `size` varchar(20) DEFAULT NULL,
  `template` longtext,
  `template_url` varchar(255) DEFAULT NULL,
  `ui` varchar(255) DEFAULT NULL,
  `data_url` varchar(255) DEFAULT NULL,
  `dependencies` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hteos_app_tile
-- ----------------------------
INSERT INTO `hteos_app_tile` VALUES ('297ee60864ac364e0164ac3a72600004', 'rgba(31,83,160,1)', 'rgb(255,255,255)', '', null, '', '', '1', null, null);
INSERT INTO `hteos_app_tile` VALUES ('adf28bd452ff464a0152ff4ab9320000', 'rgba(31,83,160,1)', 'rgb(255,255,255)', 'HteOS.controller.Photo', 'default', '', '', '1', '', null);
INSERT INTO `hteos_app_tile` VALUES ('adf28bd453021445015306eb38ce0000', 'rgba(31,83,160,1)', 'rgb(255,255,255)', 'HteOS.controller.News', 'default', '', '', '1', '', null);
INSERT INTO `hteos_app_tile` VALUES ('adf28bd453021445015306ed2ee20001', 'rgba(34,160,31,1)', 'rgb(255,255,255)', '', 'default', '<div class=\"hte-slide-container hte-slide-left  hte-tile-outline\"><div class=\"hte-slide-front\" style=\"background-color:<%=bgcolor%>\"><img src=\"<%=image%>\" class=\"hte-tile-image\" /></div><div class=\"hte-slide-back  hte-tile-title\" style=\"background-color:<%=bgcolor%>\"><%=name%></div></div>', '', '2', '', null);
INSERT INTO `hteos_app_tile` VALUES ('adf28bd453021445015306f095400002', 'rgba(77,150,204,1)', 'rgb(255,255,255)', '', 'mini', '<div class=\"hte-slide-container hte-slide-up hte-tile-outline\"><div class=\"hte-slide-front\" style=\"background-color:<%=bgcolor%>\"><img src=\"<%=icon%>\" class=\"hte-tile-icon\" /></div><div class=\"hte-slide-back  hte-tile-title\" style=\"background-color:<%=bgcolor%>\"><%=name%></div></div>', '', '1', '', null);
INSERT INTO `hteos_app_tile` VALUES ('adf28bd453021445015306f3fefa0003', 'rgba(31,83,160,1)', 'rgb(255,255,255)', 'HteOS.controller.Weather', 'default', '', '', '1', '', null);
INSERT INTO `hteos_app_tile` VALUES ('adf28bd453021445015306f530b40004', 'rgba(133,54,54,1)', 'rgb(255,255,255)', 'HteOS.controller.Stock', 'large', '', '', '1', '', null);
INSERT INTO `hteos_app_tile` VALUES ('adf28bd453021445015306f7c5980005', 'rgba(230,137,29,1)', 'rgb(255,255,255)', '', 'mini', '<div class=\"hte-slide-container hte-slide-right hte-tile-outline\"><div class=\"hte-slide-front\" style=\"background-color:<%=bgcolor%>\"><img src=\"<%=icon%>\" class=\"hte-tile-icon\" /></div><div class=\"hte-slide-back  hte-tile-title\" style=\"background-color:<%=bgcolor%>\"><%=name%></div></div>', '', '1', '', null);
INSERT INTO `hteos_app_tile` VALUES ('adf28bd453021445015306fa7dc40006', 'rgba(31,83,160,1)', 'rgb(255,255,255)', 'clock', 'default', '', '', '1', '', null);
INSERT INTO `hteos_app_tile` VALUES ('adf28bd453021445015306fc3a3b0007', 'rgba(31,83,160,1)', 'rgb(255,255,255)', '', 'mini', '', '', '2', '', null);
INSERT INTO `hteos_app_tile` VALUES ('adf28bd453021445015306ff2bc80008', 'rgba(31,83,160,1)', 'rgb(255,255,255)', '', 'default', '', '', '1', '', null);
INSERT INTO `hteos_app_tile` VALUES ('adf28bd4530214450153070163890009', 'rgba(31,83,160,1)', 'rgb(255,255,255)', '', 'mini', '', '', '1', '', null);
INSERT INTO `hteos_app_tile` VALUES ('adf28bd453021445015307033d9a000c', 'rgba(96,160,31,1)', 'rgb(255,255,255)', 'HteOS.controller.Music', 'large', '', '', '1', '', null);
INSERT INTO `hteos_app_tile` VALUES ('adf28bd454a540dc0154a54c84550000', 'rgba(31,83,160,1)', 'rgb(255,255,255)', '', 'mini', '', '', '1', '', null);
INSERT INTO `hteos_app_tile` VALUES ('adf28bd454b9af650154bf2017b5004e', 'rgba(196,0,0,1)', 'rgb(255,255,255)', '', 'mini', '<div class=\"hte-flip-container hte-flip-vertical\"><div class=\"hte-flip-front hte-tile-outline\" style=\"background-color:<%=bgcolor%>\"><img src=\"<%=icon%>\" class=\"hte-tile-icon\" /></div><div class=\"hte-flip-back hte-tile-outline hte-tile-title\" style=\"background-color:<%=bgcolor%>\"><%=name%></div></div>', '', '1', '', null);
INSERT INTO `hteos_app_tile` VALUES ('adf28bd454b9af650154bf20d086004f', 'rgba(176,15,15,1)', 'rgb(255,255,255)', '', 'large', '<div class=\"hte-slide-container hte-slide-up hte-tile-outline\"><div class=\"hte-slide-front\" style=\"background-color:<%=bgcolor%>\"><img src=\"<%=icon%>\" class=\"hte-tile-icon\" /></div><div class=\"hte-slide-back  hte-tile-title\" style=\"background-color:<%=bgcolor%>\"><%=name%></div></div>', '', '1', '', null);
INSERT INTO `hteos_app_tile` VALUES ('adf28bd454b9af650154bf224eca0050', 'rgba(39,73,194,1)', 'rgb(255,255,255)', '', 'mini', '', '', '1', '', null);
INSERT INTO `hteos_app_tile` VALUES ('adf28bd454b9af650154bf2431170051', 'rgba(31,75,160,1)', 'rgb(255,255,255)', '', 'mini', '', '', '1', '', null);
INSERT INTO `hteos_app_tile` VALUES ('adf28bd454b9af650154bf25bb810052', 'rgba(35,60,148,1)', 'rgb(255,255,255)', '', 'mini', '', '', '1', '', null);
INSERT INTO `hteos_app_tile` VALUES ('adf28bd454b9af650154bf26774f0053', 'rgba(217,18,54,1)', 'rgb(255,255,255)', '', 'mini', '', '', '1', '', null);
INSERT INTO `hteos_app_tile` VALUES ('adf28bd454b9af650154bf2734850054', 'rgba(240,0,0,1)', 'rgb(255,255,255)', '', 'default', '<div class=\"hte-slide-container hte-slide-down hte-tile-outline\"><div class=\"hte-slide-front\" style=\"background-color:<%=bgcolor%>\"><img src=\"<%=image%>\" class=\"hte-tile-image\" /></div><div class=\"hte-slide-back  hte-tile-title\" style=\"background-color:<%=bgcolor%>\"><%=name%></div></div>', '', '2', '', null);
INSERT INTO `hteos_app_tile` VALUES ('adf28bd454b9af650154bf27be470055', 'rgba(59,33,33,1)', 'rgb(255,255,255)', '', 'mini', '', '', '1', '', null);
INSERT INTO `hteos_app_tile` VALUES ('adf28bd454b9af650154bf28b28b0056', 'rgba(26,143,194,1)', 'rgb(255, 255, 255)', '', 'mini', '', null, '1', '', null);
INSERT INTO `hteos_app_tile` VALUES ('adf28bd454b9af650154bf29e9b30057', 'rgba(31,83,160,1)', 'rgb(255,255,255)', '', 'default', '<div class=\"hte-slide-container hte-slide-left  hte-tile-outline\"><div class=\"hte-slide-front\" style=\"background-color:<%=bgcolor%>\"><img src=\"<%=image%>\" class=\"hte-tile-image\" /></div><div class=\"hte-slide-back  hte-tile-title\" style=\"background-color:<%=bgcolor%>\"><%=name%></div></div>', '', '2', '', null);
INSERT INTO `hteos_app_tile` VALUES ('adf28bd454b9af650154bf2ab07e0058', 'rgba(171,33,165,1)', 'rgb(255,255,255)', '', 'mini', '', '', '1', '', null);
INSERT INTO `hteos_app_tile` VALUES ('adf28bd454b9af650154cbd1b2e50128', 'rgba(31, 83, 160,1)', 'rgb(255, 255, 255)', '', 'mini', '', null, '1', '', null);
INSERT INTO `hteos_app_tile` VALUES ('adf28bd454b9af650154cbd4fb980146', 'rgb(0,87,189)', 'rgb(255,255,255)', '', 'mini', '', null, '1', '', null);
INSERT INTO `hteos_app_tile` VALUES ('adf28bd454b9af650154cbeb93de014a', 'rgba(31, 83, 160,1)', 'rgb(255, 255, 255)', '', 'mini', '', null, '1', '', null);
INSERT INTO `hteos_app_tile` VALUES ('adf28bd454b9af650154cd480f04015a', 'rgb(3,0,166)', 'rgb(255, 255, 255)', '', 'mini', '', null, '1', '', null);

-- ----------------------------
-- Table structure for hteos_favorite
-- ----------------------------
DROP TABLE IF EXISTS `hteos_favorite`;
CREATE TABLE `hteos_favorite` (
  `id` varchar(255) NOT NULL,
  `favorite_index` int(11) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `app_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9gjtk36ylu9yjm74uf4sm8q3b` (`user_id`),
  KEY `FKhlajmkqiyuxkdl2hd62u84u29` (`app_id`),
  CONSTRAINT `hteos_favorite_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `hteos_user` (`id`),
  CONSTRAINT `hteos_favorite_ibfk_2` FOREIGN KEY (`app_id`) REFERENCES `hteos_app` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hteos_favorite
-- ----------------------------

-- ----------------------------
-- Table structure for hteos_feedback
-- ----------------------------
DROP TABLE IF EXISTS `hteos_feedback`;
CREATE TABLE `hteos_feedback` (
  `id` varchar(255) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `day` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hteos_feedback
-- ----------------------------

-- ----------------------------
-- Table structure for hteos_group
-- ----------------------------
DROP TABLE IF EXISTS `hteos_group`;
CREATE TABLE `hteos_group` (
  `id` varchar(255) NOT NULL,
  `group_index` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7708AD41473A48` (`user_id`),
  CONSTRAINT `hteos_group_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `hteos_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hteos_group
-- ----------------------------
INSERT INTO `hteos_group` VALUES ('adf28bd452ff464a0152ff65c9190002', '2', '默认分组', 'adf28bd452ff464a0152ff65c8fa0001');
INSERT INTO `hteos_group` VALUES ('adf28bd454a8d48f0154a902785d0001', '4', '新建分组', 'adf28bd452ff464a0152ff65c8fa0001');
INSERT INTO `hteos_group` VALUES ('adf28bd454b500020154b50126770000', '3', '新建分组', 'adf28bd452ff464a0152ff65c8fa0001');
INSERT INTO `hteos_group` VALUES ('adf28bd454b9af650154beec3b330049', '1', '新建分组', 'adf28bd452ff464a0152ff65c8fa0001');
INSERT INTO `hteos_group` VALUES ('adf28bd454b9af650154bf311f7a0065', '0', '新建分组', 'adf28bd452ff464a0152ff65c8fa0001');
INSERT INTO `hteos_group` VALUES ('adf28bd454b9af650154c66b98f900b4', '5', '新建分组12313', 'adf28bd452ff464a0152ff65c8fa0001');

-- ----------------------------
-- Table structure for hteos_installation
-- ----------------------------
DROP TABLE IF EXISTS `hteos_installation`;
CREATE TABLE `hteos_installation` (
  `id` varchar(255) NOT NULL,
  `hidden` varchar(255) DEFAULT NULL,
  `app_index` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `favorited` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) NOT NULL,
  `app_id` varchar(255) NOT NULL,
  `group_id` varchar(255) DEFAULT NULL,
  `size` varchar(255) DEFAULT NULL,
  `shortcutIndex` int(11) DEFAULT NULL,
  `install_date` datetime DEFAULT NULL,
  `favorite_index` int(11) DEFAULT NULL,
  `shortcut_index` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `account_id` (`user_id`,`app_id`),
  UNIQUE KEY `UK8fugk1gqvdudq0r25kjvfv5jv` (`user_id`,`app_id`),
  KEY `FKB9CBF963473A48` (`user_id`),
  KEY `FKB9CBF963F9F069BB` (`group_id`),
  KEY `FKB9CBF963891A2DC5` (`app_id`),
  CONSTRAINT `hteos_installation_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `hteos_user` (`id`),
  CONSTRAINT `hteos_installation_ibfk_2` FOREIGN KEY (`app_id`) REFERENCES `hteos_app` (`id`),
  CONSTRAINT `hteos_installation_ibfk_3` FOREIGN KEY (`group_id`) REFERENCES `hteos_group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hteos_installation
-- ----------------------------
INSERT INTO `hteos_installation` VALUES ('297ee60864e419600164e41aae320000', null, '4', null, null, 'adf28bd452ff464a0152ff65c8fa0001', 'adf28bd453021445015306f3fefa0003', 'adf28bd452ff464a0152ff65c9190002', null, null, '2018-07-29 11:35:11', null, null);
INSERT INTO `hteos_installation` VALUES ('adf28bd452ff464a0152ff65c9190003', '0', '1', null, '1', 'adf28bd452ff464a0152ff65c8fa0001', 'adf28bd452ff464a0152ff4ab9320000', 'adf28bd452ff464a0152ff65c9190002', 'default', '0', '2016-02-20 23:56:46', '3', null);
INSERT INTO `hteos_installation` VALUES ('adf28bd453021445015307020354000a', null, '1', null, '0', 'adf28bd452ff464a0152ff65c8fa0001', 'adf28bd4530214450153070163890009', 'adf28bd454b500020154b50126770000', null, '18', '2016-02-22 11:24:45', null, null);
INSERT INTO `hteos_installation` VALUES ('adf28bd45302144501530702403e000b', null, '0', null, '0', 'adf28bd452ff464a0152ff65c8fa0001', 'adf28bd453021445015306fc3a3b0007', 'adf28bd454b9af650154c66b98f900b4', 'large', '7', '2016-02-22 11:25:01', null, null);
INSERT INTO `hteos_installation` VALUES ('adf28bd4530214450153070913f2001c', '0', '3', null, '0', 'adf28bd452ff464a0152ff65c8fa0001', 'adf28bd453021445015306eb38ce0000', 'adf28bd452ff464a0152ff65c9190002', null, '3', '2016-02-22 11:32:28', null, null);
INSERT INTO `hteos_installation` VALUES ('adf28bd453021445015307091c7e001d', '0', '3', null, '0', 'adf28bd452ff464a0152ff65c8fa0001', 'adf28bd453021445015306ed2ee20001', 'adf28bd454b500020154b50126770000', 'default', '26', '2016-02-22 11:32:30', null, null);
INSERT INTO `hteos_installation` VALUES ('adf28bd45302144501530709297e001e', null, '4', null, '0', 'adf28bd452ff464a0152ff65c8fa0001', 'adf28bd453021445015306f095400002', 'adf28bd454b9af650154bf311f7a0065', null, '21', '2016-02-22 11:32:34', null, null);
INSERT INTO `hteos_installation` VALUES ('adf28bd4530214450153070942e20020', null, '2', null, '1', 'adf28bd452ff464a0152ff65c8fa0001', 'adf28bd453021445015306f530b40004', 'adf28bd454a8d48f0154a902785d0001', 'default', '12', '2016-02-22 11:32:40', '0', null);
INSERT INTO `hteos_installation` VALUES ('adf28bd453021445015307094c0a0021', null, '0', null, '0', 'adf28bd452ff464a0152ff65c8fa0001', 'adf28bd453021445015306f7c5980005', 'adf28bd454a8d48f0154a902785d0001', 'large', '9', '2016-02-22 11:32:43', null, null);
INSERT INTO `hteos_installation` VALUES ('adf28bd45302144501530709567a0022', null, '1', null, '1', 'adf28bd452ff464a0152ff65c8fa0001', 'adf28bd453021445015306fa7dc40006', 'adf28bd454a8d48f0154a902785d0001', 'mini', '4', '2016-02-22 11:32:45', '1', null);
INSERT INTO `hteos_installation` VALUES ('adf28bd4530214450153070a677d0023', '0', '4', null, '0', 'adf28bd452ff464a0152ff65c8fa0001', 'adf28bd453021445015306ff2bc80008', 'adf28bd454b9af650154c66b98f900b4', 'default', '23', '2016-02-22 11:33:55', null, null);
INSERT INTO `hteos_installation` VALUES ('adf28bd453021445015308cf75900035', '0', '2', null, '0', 'adf28bd452ff464a0152ff65c8fa0001', 'adf28bd453021445015307033d9a000c', 'adf28bd454b500020154b50126770000', 'default', '24', '2016-02-22 19:48:47', null, null);
INSERT INTO `hteos_installation` VALUES ('adf28bd454b9af650154bd71dd300033', '0', '3', null, '0', 'adf28bd452ff464a0152ff65c8fa0001', 'adf28bd454a540dc0154a54c84550000', 'adf28bd454b9af650154c66b98f900b4', null, null, '2016-05-17 14:40:36', null, null);
INSERT INTO `hteos_installation` VALUES ('adf28bd454b9af650154bf2e364b005a', null, '2', null, '0', 'adf28bd452ff464a0152ff65c8fa0001', 'adf28bd454b9af650154bf2ab07e0058', 'adf28bd452ff464a0152ff65c9190002', null, '8', '2016-05-17 22:45:57', null, null);
INSERT INTO `hteos_installation` VALUES ('adf28bd454b9af650154bf2e4177005b', null, '0', null, '0', 'adf28bd452ff464a0152ff65c8fa0001', 'adf28bd454b9af650154bf29e9b30057', 'adf28bd454b500020154b50126770000', 'default', '25', '2016-05-17 22:46:00', null, null);
INSERT INTO `hteos_installation` VALUES ('adf28bd454b9af650154bf2e50d8005c', null, '2', null, '1', 'adf28bd452ff464a0152ff65c8fa0001', 'adf28bd454b9af650154bf28b28b0056', 'adf28bd454b9af650154beec3b330049', 'large', '14', '2016-05-17 22:46:04', '4', null);
INSERT INTO `hteos_installation` VALUES ('adf28bd454b9af650154bf2e5bb5005d', null, '1', null, '0', 'adf28bd452ff464a0152ff65c8fa0001', 'adf28bd454b9af650154bf27be470055', 'adf28bd454b9af650154bf311f7a0065', null, '5', '2016-05-17 22:46:07', null, null);
INSERT INTO `hteos_installation` VALUES ('adf28bd454b9af650154bf2e6923005e', null, '3', null, '0', 'adf28bd452ff464a0152ff65c8fa0001', 'adf28bd454b9af650154bf2734850054', 'adf28bd454b9af650154bf311f7a0065', null, '13', '2016-05-17 22:46:10', null, null);
INSERT INTO `hteos_installation` VALUES ('adf28bd454b9af650154bf2e772d005f', null, '0', null, '0', 'adf28bd452ff464a0152ff65c8fa0001', 'adf28bd454b9af650154bf26774f0053', 'adf28bd452ff464a0152ff65c9190002', 'mini', '15', '2016-05-17 22:46:14', null, null);
INSERT INTO `hteos_installation` VALUES ('adf28bd454b9af650154bf2e82290060', null, '2', null, '0', 'adf28bd452ff464a0152ff65c8fa0001', 'adf28bd454b9af650154bf25bb810052', 'adf28bd454b9af650154c66b98f900b4', null, '17', '2016-05-17 22:46:16', null, null);
INSERT INTO `hteos_installation` VALUES ('adf28bd454b9af650154bf2e94690061', null, '0', null, '0', 'adf28bd452ff464a0152ff65c8fa0001', 'adf28bd454b9af650154bf2431170051', 'adf28bd454b9af650154beec3b330049', null, '19', '2016-05-17 22:46:21', null, null);
INSERT INTO `hteos_installation` VALUES ('adf28bd454b9af650154bf2f25fb0062', null, '4', null, '0', 'adf28bd452ff464a0152ff65c8fa0001', 'adf28bd454b9af650154bf2017b5004e', 'adf28bd454b500020154b50126770000', 'mini', '22', '2016-05-17 22:46:58', null, null);
INSERT INTO `hteos_installation` VALUES ('adf28bd454b9af650154bf2f30f70063', null, '1', null, '0', 'adf28bd452ff464a0152ff65c8fa0001', 'adf28bd454b9af650154bf20d086004f', 'adf28bd454b9af650154beec3b330049', 'default', '1', '2016-05-17 22:47:01', null, null);
INSERT INTO `hteos_installation` VALUES ('adf28bd454b9af650154bf2f3c810064', null, '1', null, '1', 'adf28bd452ff464a0152ff65c8fa0001', 'adf28bd454b9af650154bf224eca0050', 'adf28bd454b9af650154c66b98f900b4', null, '27', '2016-05-17 22:47:04', '2', null);
INSERT INTO `hteos_installation` VALUES ('adf28bd454b9af650154cbd219010144', null, '5', null, '0', 'adf28bd452ff464a0152ff65c8fa0001', 'adf28bd454b9af650154cbd1b2e50128', 'adf28bd452ff464a0152ff65c9190002', null, '2', '2016-05-20 09:40:24', null, null);
INSERT INTO `hteos_installation` VALUES ('adf28bd454b9af650154cbd5312e0147', null, '0', null, '0', 'adf28bd452ff464a0152ff65c8fa0001', 'adf28bd454b9af650154cbd4fb980146', 'adf28bd454b9af650154bf311f7a0065', 'large', '20', '2016-05-20 09:43:47', null, null);
INSERT INTO `hteos_installation` VALUES ('adf28bd454b9af650154cbebcfbe014b', null, '2', null, '0', 'adf28bd452ff464a0152ff65c8fa0001', 'adf28bd454b9af650154cbeb93de014a', 'adf28bd454b9af650154bf311f7a0065', null, '10', '2016-05-20 10:08:29', null, null);

-- ----------------------------
-- Table structure for hteos_review
-- ----------------------------
DROP TABLE IF EXISTS `hteos_review`;
CREATE TABLE `hteos_review` (
  `id` varchar(255) NOT NULL,
  `review` longtext,
  `score` int(11) DEFAULT NULL,
  `user_id` varchar(255) NOT NULL,
  `app_id` varchar(255) NOT NULL,
  `review_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4A070336473A48` (`user_id`),
  KEY `FK4A070336891A2DC5` (`app_id`),
  CONSTRAINT `hteos_review_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `hteos_user` (`id`),
  CONSTRAINT `hteos_review_ibfk_2` FOREIGN KEY (`app_id`) REFERENCES `hteos_app` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hteos_review
-- ----------------------------

-- ----------------------------
-- Table structure for hteos_statistics
-- ----------------------------
DROP TABLE IF EXISTS `hteos_statistics`;
CREATE TABLE `hteos_statistics` (
  `id` varchar(255) NOT NULL,
  `day` varchar(255) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `access_count` bigint(20) DEFAULT NULL,
  `ip_count` bigint(20) DEFAULT NULL,
  `resigter_count` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hteos_statistics
-- ----------------------------
INSERT INTO `hteos_statistics` VALUES ('297ee60864f421be0164f42bc79d0045', '2018-08-01', '2018-08-01 14:27:47', '0', '0', '3');

-- ----------------------------
-- Table structure for hteos_user
-- ----------------------------
DROP TABLE IF EXISTS `hteos_user`;
CREATE TABLE `hteos_user` (
  `id` varchar(255) NOT NULL,
  `username` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `face` varchar(255) DEFAULT NULL,
  `password` varchar(100) NOT NULL,
  `residence` varchar(255) DEFAULT NULL,
  `wallpaper` varchar(255) DEFAULT NULL,
  `theme` varchar(255) DEFAULT NULL,
  `mode` varchar(255) DEFAULT NULL,
  `qq_open_id` varchar(255) DEFAULT NULL,
  `register_date` datetime DEFAULT NULL,
  `shortcut_size` varchar(255) DEFAULT NULL,
  `nick_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `account` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hteos_user
-- ----------------------------
INSERT INTO `hteos_user` VALUES ('adf28bd452ff464a0152ff65c8fa0001', 'demo@hteos.com', 'demo@hteos.com', 'http://asset.honvay.com/images/account/face/adf28bd452ff464a0152ff65c8fa0001.png', '96e79218965eb72c92a549dd5a330112', '北京,北京,朝阳区', 'images/wallpaper/wallpaper.jpg', 'red', 'metro', null, null, 'medium', 'demo@hteos.com');
