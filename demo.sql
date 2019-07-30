/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50725
Source Host           : localhost:3306
Source Database       : demo

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2019-05-17 16:33:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_admin
-- ----------------------------
DROP TABLE IF EXISTS `tb_admin`;
CREATE TABLE `tb_admin` (
  `id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  `imageUrl` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_admin
-- ----------------------------
INSERT INTO `tb_admin` VALUES ('2', 'superAdmin', '12345', 'laughing', 'superAdmin', 'http://localhost:8080/image/article/b7cc3066603d4490896dcbb0ea03ed16.jpg');
INSERT INTO `tb_admin` VALUES ('3', 'admin1', '12345', 'en', 'admin', 'http://localhost:8080/image/article/b7cc3066603d4490896dcbb0ea03ed16.jpg');
INSERT INTO `tb_admin` VALUES ('4', '123', '12345', '11', 'admin', 'http://localhost:8080/image/article/b7cc3066603d4490896dcbb0ea03ed16.jpg');
INSERT INTO `tb_admin` VALUES ('5', '1234', '12345', '321', 'admin', 'http://localhost:8080/image/article/b7cc3066603d4490896dcbb0ea03ed16.jpg');
INSERT INTO `tb_admin` VALUES ('6', '12345', '12345', '222', 'admin', 'http://localhost:8080/image/article/b7cc3066603d4490896dcbb0ea03ed16.jpg');
INSERT INTO `tb_admin` VALUES ('7', '321', '12345', '333', 'admin', 'http://localhost:8080/image/article/b7cc3066603d4490896dcbb0ea03ed16.jpg');
INSERT INTO `tb_admin` VALUES ('8', '4321', '12345', '2222', 'admin', 'http://localhost:8080/image/article/b7cc3066603d4490896dcbb0ea03ed16.jpg');
INSERT INTO `tb_admin` VALUES ('9', '54321', '12345', '666', 'admin', 'http://localhost:8080/image/article/b7cc3066603d4490896dcbb0ea03ed16.jpg');
INSERT INTO `tb_admin` VALUES ('10', '11111', '12345', '777', 'admin', 'http://localhost:8080/image/article/b7cc3066603d4490896dcbb0ea03ed16.jpg');
INSERT INTO `tb_admin` VALUES ('11', 'super', 'super', '111', 'superAdmin', 'http://localhost:8080/image/article/b7cc3066603d4490896dcbb0ea03ed16.jpg');

-- ----------------------------
-- Table structure for tb_article
-- ----------------------------
DROP TABLE IF EXISTS `tb_article`;
CREATE TABLE `tb_article` (
  `article_id` int(4) NOT NULL AUTO_INCREMENT,
  `article_title` varchar(255) NOT NULL,
  `article_desc` varchar(255) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `article_content` longtext NOT NULL,
  `article_classify` varchar(255) NOT NULL,
  `collect_count` int(20) DEFAULT NULL,
  PRIMARY KEY (`article_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_article
-- ----------------------------
INSERT INTO `tb_article` VALUES ('23', '123213', '123', '2019-05-11 10:59:40', 'http://localhost:8080/image/article/8388ad85c6aa4114ae4e91be98dc5dd3.jpg', '<p>2132131123</p>', '#科技', '0');
INSERT INTO `tb_article` VALUES ('24', '213', '123', '2019-05-11 11:01:14', 'http://localhost:8080/image/article/99253f0e6448460fbc226b1c6528bfed.jpg', '<p>1232131</p>', '#科技', '0');
INSERT INTO `tb_article` VALUES ('25', '123', '12321', '2019-05-11 11:01:35', 'http://localhost:8080/image/article/4b802fc2507346f5a693b2fad7721bb7.jpg', '<p>123213</p>', '#游戏', '0');
INSERT INTO `tb_article` VALUES ('26', '如果你累了，就去旅行吧', '风景可以治愈人的心灵', '2019-05-11 11:02:50', 'http://localhost:8080/image/article/2ac0494fda7f4efb9cef78824a874b01.png', '<p><span style=\"color: rgb(25, 25, 25);\">风景可以治愈人的心灵，当生活总被各种琐事填满，内心总是闷闷不乐的时候你是不是该出去走走了。</span></p><p><br></p><p><span style=\"color: rgb(25, 25, 25);\">一次真正的旅行，不是去到某一个景点留下到此一游的纪念照。而是融入那里，用心体会自己生活圈子之外的精彩。</span></p><p><br></p><p><span style=\"color: rgb(25, 25, 25);\">把自己放空，来到崭新的地方，也许走着走着便释怀了，许多烦恼和压力也都烟消云散。</span></p><p><br></p><p><span style=\"color: rgb(25, 25, 25);\">一次纯粹的旅行，是在新的地方新的环境下，重拾希望，获得一个崭新的自己。</span></p>', '#旅行', '0');
INSERT INTO `tb_article` VALUES ('27', '滴滴将在全国增加2000名司机服务顾问', '滴滴出行联合创始人、CTO张博做客《北大汇丰创讲堂》', '2019-05-11 11:04:26', 'http://localhost:8080/image/article/af324390bc8346ed8653c7177a5409ce.png', '<p><span style=\"color: rgb(51, 51, 51);\">张博在分享中回忆了加入滴滴的“那些事”。2012年，有两个重要的决策改变了自己的人生轨迹。一个是决定从百度离开，开始创业；第二个是加入滴滴。</span></p><p><br></p><p><span style=\"color: rgb(51, 51, 51);\">2012年，张博坚信，智能手机和移动互联网网络的重大发展，一定会带来上层应用的繁荣，这也让他下定决心离开百度转而寻求合伙人一起创业。</span></p><p><br></p><p><span style=\"color: rgb(51, 51, 51);\">创业的第一个项目失败后，张博一个人背着书包到威海闭关了一周。期间张博一直在思考两个问题：接下来的路该怎么走？如果要创业该做什么？</span></p><p><span style=\"color: rgb(51, 51, 51);\">大众、高频、刚需和有用户口碑传播的场景，是张博当时最看重的四个关键要素。张博认为，目标用户群是大众的需求还是小众的需求，决定了创业天花板的高低；用户需求是否高频决定了用户的留存；而刚需的定义是企业的产品或者服务创造的价值是可衡量、可持续、可防御；当企业的产品或者服务真正触及用户痛点的时候，用户口碑传播能起到的效果会比任何广告都管用。</span></p>', '#社会', '1');
INSERT INTO `tb_article` VALUES ('28', '耗资千万却不求盈利 《隐形守护者》这么做真值得吗？', '一款真人实拍的剧情交互游戏《隐形守护者》走进了玩家们的视野', '2019-05-11 11:05:31', 'http://localhost:8080/image/article/33b60bad99364b95b86a42b22aa1087c.png', '<p><span style=\"color: rgb(67, 74, 84);\">最近，一款真人实拍的剧情交互游戏《隐形守护者》走进了玩家们的视野，它讲述的是发生在民国时期的一场惊心动魄的谍战故事。由于这款游戏剧情紧密，演员表演出色，因此深受玩家好评，迄今Steam的好评率一直稳居在90%以上，在影视爱好者聚集的豆瓣也获得了9.3分。</span></p>', '#游戏', '1');

-- ----------------------------
-- Table structure for tb_classify
-- ----------------------------
DROP TABLE IF EXISTS `tb_classify`;
CREATE TABLE `tb_classify` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `classify_name` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_classify
-- ----------------------------
INSERT INTO `tb_classify` VALUES ('3', '#社会', 'http://localhost:8080/image/classify/c4b57093e7ac40c7a214b2e24926892d.jpg');
INSERT INTO `tb_classify` VALUES ('9', '#科技', 'http://localhost:8080/image/classify/ad6286a585b746ff869042bcfae25884.jpg');
INSERT INTO `tb_classify` VALUES ('13', '#游戏', 'http://localhost:8080/image/classify//86ad5dc587154675bbaaec660d26c508.jpg');
INSERT INTO `tb_classify` VALUES ('14', '#旅行', 'http://localhost:8080/image/classify//446e1fa939e54807a3aceff2c3c308b4.jpg');

-- ----------------------------
-- Table structure for tb_comment
-- ----------------------------
DROP TABLE IF EXISTS `tb_comment`;
CREATE TABLE `tb_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `wx_name` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `article_id` int(11) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `points_count` int(20) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `wx_head` varchar(255) DEFAULT NULL,
  `open_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_comment
-- ----------------------------
INSERT INTO `tb_comment` VALUES ('30', '许演杰?', '28', '阿瑟东', '0', '2019-05-11 11:27:30', 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJicJOgXTJ2xjPv4jfa67AXrFh7ysOrhSBHY9eibAktH9Vib2Oqth6cEnqc5KQlQ6cOaHO3uYceZQwHQ/132', 'oDSvi5NBQeeHzqcdSR4KYJiy7lQk');
INSERT INTO `tb_comment` VALUES ('31', '许演杰?', '28', '***', '1', '2019-05-11 11:27:37', 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJicJOgXTJ2xjPv4jfa67AXrFh7ysOrhSBHY9eibAktH9Vib2Oqth6cEnqc5KQlQ6cOaHO3uYceZQwHQ/132', 'oDSvi5NBQeeHzqcdSR4KYJiy7lQk');
INSERT INTO `tb_comment` VALUES ('32', '许演杰?', '27', '213', '0', '2019-05-11 11:54:48', 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJicJOgXTJ2xjPv4jfa67AXrFh7ysOrhSBHY9eibAktH9Vib2Oqth6cEnqc5KQlQ6cOaHO3uYceZQwHQ/132', 'oDSvi5NBQeeHzqcdSR4KYJiy7lQk');
INSERT INTO `tb_comment` VALUES ('33', '许演杰?', '27', '***', '1', '2019-05-11 11:55:05', 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJicJOgXTJ2xjPv4jfa67AXrFh7ysOrhSBHY9eibAktH9Vib2Oqth6cEnqc5KQlQ6cOaHO3uYceZQwHQ/132', 'oDSvi5NBQeeHzqcdSR4KYJiy7lQk');
INSERT INTO `tb_comment` VALUES ('34', '许演杰?', '24', '***', '0', '2019-05-11 11:57:36', 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJicJOgXTJ2xjPv4jfa67AXrFh7ysOrhSBHY9eibAktH9Vib2Oqth6cEnqc5KQlQ6cOaHO3uYceZQwHQ/132', 'oDSvi5NBQeeHzqcdSR4KYJiy7lQk');

-- ----------------------------
-- Table structure for tb_history
-- ----------------------------
DROP TABLE IF EXISTS `tb_history`;
CREATE TABLE `tb_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `history_title` varchar(255) NOT NULL,
  `history_desc` varchar(255) NOT NULL,
  `year` int(5) NOT NULL,
  `history_url` varchar(255) DEFAULT NULL,
  `history_content` varchar(255) NOT NULL,
  `month` int(5) NOT NULL,
  `day` int(5) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_history
-- ----------------------------
INSERT INTO `tb_history` VALUES ('1', '222', '222', '2019', '3', '12', '3', '5');
INSERT INTO `tb_history` VALUES ('2', '222', '222', '2019', '3', '12', '4', '30');

-- ----------------------------
-- Table structure for tb_permission
-- ----------------------------
DROP TABLE IF EXISTS `tb_permission`;
CREATE TABLE `tb_permission` (
  `id` int(11) NOT NULL,
  `key` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `permission` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_permission
-- ----------------------------

-- ----------------------------
-- Table structure for tb_sensitive
-- ----------------------------
DROP TABLE IF EXISTS `tb_sensitive`;
CREATE TABLE `tb_sensitive` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `word` varchar(255) NOT NULL,
  `time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_sensitive
-- ----------------------------
INSERT INTO `tb_sensitive` VALUES ('2', '去你的', '2019-05-06 13:27:13');
INSERT INTO `tb_sensitive` VALUES ('5', '法轮功', '2019-05-06 13:27:02');
INSERT INTO `tb_sensitive` VALUES ('6', '苏大强', '2019-05-11 11:57:18');

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `WeChat_sex` varchar(255) DEFAULT NULL,
  `WeChat_name` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `WeChat_head` varchar(255) DEFAULT NULL,
  `WeChat_openid` varchar(255) NOT NULL,
  `comment_praise` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `WeChat_openid` (`WeChat_openid`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('1', '3', '2', '1', '12345', '');
INSERT INTO `tb_user` VALUES ('9', '112', '222', '333', '', null);
INSERT INTO `tb_user` VALUES ('8', '男性', '许演杰?', 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJicJOgXTJ2xjPv4jfa67AXrFh7ysOrhSBHY9eibAktH9Vib2Oqth6cEnqc5KQlQ6cOaHO3uYceZQwHQ/132', 'oDSvi5NBQeeHzqcdSR4KYJiy7lQk', '31,,33');
