/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : questionnaire

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 29/02/2020 23:04:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '题目id',
  `question_type` int(4) NOT NULL COMMENT '题目类型{1：单选，2：多选，3：文本，4：附件上传}',
  `required` int(2) NOT NULL COMMENT '是否必填：{0:可选，1：必填}',
  `title` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '题目标题',
  `body` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '题目题干',
  `question_bank` int(255) NOT NULL COMMENT '是否作为题库，1：不作为，2放入题库',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of question
-- ----------------------------
INSERT INTO `question` VALUES (3, 1, 1, '你最喜欢哪种颜色', '[{\"code\": \"A\",\"content\": \"紫色\",\"desc\": \"\",\"pic\": \"http://xxxxxx\"},{\"code\": \"B\",\"content\": \"绿色\",\"desc\": \"\",\"pic\": \"http://xxxxxx\"},{\"code\": \"C\",\"content\": \"蓝色\",\"desc\": \"\",\"pic\": \"http://xxxxxx\"}]', 1);
INSERT INTO `question` VALUES (4, 2, 1, '喜欢什么动物', '[{\"code\": \"A\",\"content\": \"cat\",\"desc\": \"\",\"pic\": \"http://xxxxxx\"},{\"code\": \"B\",\"content\": \"dog\",\"desc\": \"\",\"pic\": \"http://xxxxxx\"},{\"code\": \"C\",\"content\": \"bird\",\"desc\": \"\",\"pic\": \"http://xxxxxx\"}]', 1);
INSERT INTO `question` VALUES (5, 3, 1, '你是什么星座', '', 1);

-- ----------------------------
-- Table structure for questionnaire
-- ----------------------------
DROP TABLE IF EXISTS `questionnaire`;
CREATE TABLE `questionnaire`  (
  `id` int(24) NOT NULL COMMENT '问卷id,由自增算法实现,用于标识问卷',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '问卷标题',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '问卷说明',
  `status` int(2) NOT NULL COMMENT '问卷状态{1:已创建未发布 2：已发布}',
  `createTime` datetime(0) NULL DEFAULT NULL,
  `startTime` datetime(0) NULL DEFAULT NULL,
  `endTime` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of questionnaire
-- ----------------------------
INSERT INTO `questionnaire` VALUES (11111111, 'Quize测试', '测试个人喜好', 1, '2020-02-26 17:43:40', '2020-02-26 17:43:44', '2020-02-28 17:43:47');

-- ----------------------------
-- Table structure for questionnaire_to_question
-- ----------------------------
DROP TABLE IF EXISTS `questionnaire_to_question`;
CREATE TABLE `questionnaire_to_question`  (
  `id` int(11) NOT NULL,
  `questionnaireId` int(11) NULL DEFAULT NULL,
  `questionId` int(11) NULL DEFAULT NULL,
  `statistics` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `order` int(10) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record`  (
  `id` int(11) NOT NULL,
  `questionnaire_id` int(11) NOT NULL COMMENT '问卷id',
  `question_id` int(11) NOT NULL COMMENT '问题id',
  `answer` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '问卷回答结果',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
