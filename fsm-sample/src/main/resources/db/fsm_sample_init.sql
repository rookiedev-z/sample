/*

 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : 127.0.0.1:3306
 Source Schema         : fsm_sample

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 28/07/2020 18:58:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for fsm_issues
-- ----------------------------
DROP TABLE IF EXISTS `fsm_issues`;
CREATE TABLE `fsm_issues` (
                              `id` bigint NOT NULL AUTO_INCREMENT,
                              `project_id` bigint DEFAULT NULL COMMENT '项目 id',
                              `num` varchar(100) DEFAULT NULL COMMENT 'issue 编号',
                              `summary` varchar(255) DEFAULT NULL COMMENT '摘要',
                              `status_id` bigint DEFAULT NULL COMMENT '状态 id',
                              `assignee_id` bigint DEFAULT NULL COMMENT '经办人 id',
                              `description` varchar(255) DEFAULT NULL COMMENT 'issue 描述',
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fsm_issues
-- ----------------------------
BEGIN;
INSERT INTO `fsm_issues` VALUES (1, 1, 'Issue-01', 'issue 摘要', 3, NULL, 'issue 描述信息');
COMMIT;

-- ----------------------------
-- Table structure for fsm_state_machine_nodes
-- ----------------------------
DROP TABLE IF EXISTS `fsm_state_machine_nodes`;
CREATE TABLE `fsm_state_machine_nodes` (
                                           `id` bigint NOT NULL AUTO_INCREMENT,
                                           `state_machine_id` bigint NOT NULL COMMENT '状态机 id',
                                           `status_id` bigint NOT NULL COMMENT '状态 id',
                                           `type` int NOT NULL COMMENT '节点类型, 0:start 1:init 2:custom',
                                           `all_transform_id` bigint DEFAULT NULL COMMENT '全部转换 id',
                                           `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '描述',
                                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fsm_state_machine_nodes
-- ----------------------------
BEGIN;
INSERT INTO `fsm_state_machine_nodes` VALUES (0, 1, 0, 0, NULL, 'start node');
INSERT INTO `fsm_state_machine_nodes` VALUES (1, 1, 1, 1, NULL, 'todo init node');
INSERT INTO `fsm_state_machine_nodes` VALUES (2, 1, 2, 2, NULL, 'groomed node');
INSERT INTO `fsm_state_machine_nodes` VALUES (3, 1, 3, 2, NULL, 'in progress node');
INSERT INTO `fsm_state_machine_nodes` VALUES (4, 1, 4, 2, NULL, 'blocked node');
INSERT INTO `fsm_state_machine_nodes` VALUES (5, 1, 5, 2, NULL, 'needs qa node');
INSERT INTO `fsm_state_machine_nodes` VALUES (6, 1, 6, 2, NULL, 'qa approved node');
INSERT INTO `fsm_state_machine_nodes` VALUES (7, 1, 7, 2, NULL, 'ready for production node');
INSERT INTO `fsm_state_machine_nodes` VALUES (8, 1, 8, 2, NULL, 'done node');
COMMIT;

-- ----------------------------
-- Table structure for fsm_state_machine_transforms
-- ----------------------------
DROP TABLE IF EXISTS `fsm_state_machine_transforms`;
CREATE TABLE `fsm_state_machine_transforms` (
                                                `id` bigint NOT NULL AUTO_INCREMENT,
                                                `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '转换名称',
                                                `state_machine_id` bigint NOT NULL COMMENT '状态机 id',
                                                `start_node_id` bigint NOT NULL COMMENT '开始节点 id',
                                                `end_node_id` bigint NOT NULL COMMENT '结束节点 id',
                                                `type` int DEFAULT NULL COMMENT '转换类型，0:transform_init， 1:transform_all， 2:transform_custom',
                                                `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '描述',
                                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fsm_state_machine_transforms
-- ----------------------------
BEGIN;
INSERT INTO `fsm_state_machine_transforms` VALUES (2, 'init', 1, 0, 1, 0, '初始化');
INSERT INTO `fsm_state_machine_transforms` VALUES (3, 'Start working', 1, 1, 3, 2, 'start working, todo -> in progress');
INSERT INTO `fsm_state_machine_transforms` VALUES (4, 'Won\'t Do', 1, 1, 8, 2, 'wont\'t do, to -> done');
INSERT INTO `fsm_state_machine_transforms` VALUES (5, 'Get Blocked', 1, 3, 4, 2, 'get blocked, in progress -> blocked');
INSERT INTO `fsm_state_machine_transforms` VALUES (6, 'Deploy to Stage', 1, 3, 5, 2, 'deploy to stage, in progress -> needs qa');
INSERT INTO `fsm_state_machine_transforms` VALUES (7, 'Start Development', 1, 4, 3, 2, 'start development, blocked -> in progress');
INSERT INTO `fsm_state_machine_transforms` VALUES (8, 'Reject', 1, 5, 3, 2, 'reject, needs qa -> in progress');
INSERT INTO `fsm_state_machine_transforms` VALUES (9, 'Approve', 1, 5, 7, 2, 'approved, needs qa -> qa approved');
INSERT INTO `fsm_state_machine_transforms` VALUES (10, 'Reject', 1, 6, 3, 2, 'reject, qa approved -> in progress');
INSERT INTO `fsm_state_machine_transforms` VALUES (11, 'Merge and Tag', 1, 6, 7, 2, 'merge and tag, qa approved -> ready for production');
INSERT INTO `fsm_state_machine_transforms` VALUES (12, 'Deploy to Production', 1, 7, 8, 2, 'deploy to production, ready for production -> done');
INSERT INTO `fsm_state_machine_transforms` VALUES (13, 'Reopen', 1, 8, 1, 2, 'reopen, done -> todo');
COMMIT;

-- ----------------------------
-- Table structure for fsm_state_machines
-- ----------------------------
DROP TABLE IF EXISTS `fsm_state_machines`;
CREATE TABLE `fsm_state_machines` (
                                      `id` bigint NOT NULL AUTO_INCREMENT,
                                      `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态机名称',
                                      `status` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '0:草稿 1: 创建 2: 已发布',
                                      `project_id` bigint DEFAULT NULL COMMENT '项目 id',
                                      `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '描述',
                                      PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fsm_state_machines
-- ----------------------------
BEGIN;
INSERT INTO `fsm_state_machines` VALUES (1, 'issue 状态机', '2', 1, 'issue 状态机');
COMMIT;

-- ----------------------------
-- Table structure for fsm_status
-- ----------------------------
DROP TABLE IF EXISTS `fsm_status`;
CREATE TABLE `fsm_status` (
                              `id` bigint NOT NULL AUTO_INCREMENT,
                              `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态名称',
                              `type` int DEFAULT NULL COMMENT '状态类型，0:待处理 1:处理中 2:待测试 3: 待发布 4: 已完成',
                              `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '描述',
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fsm_status
-- ----------------------------
BEGIN;
INSERT INTO `fsm_status` VALUES (1, 'TO DO', 0, '待处理');
INSERT INTO `fsm_status` VALUES (2, 'GROMMED', 1, '讨论中');
INSERT INTO `fsm_status` VALUES (3, 'IN PROGRESS', 1, '处理中');
INSERT INTO `fsm_status` VALUES (4, 'BLOCKED', 1, '处理中');
INSERT INTO `fsm_status` VALUES (5, 'NEEDS QA', 2, '待测试');
INSERT INTO `fsm_status` VALUES (6, 'QA APPROVED', 3, '测试通过');
INSERT INTO `fsm_status` VALUES (7, 'READY FOR PRODUCTION', 3, '待发布');
INSERT INTO `fsm_status` VALUES (8, 'DONE', 4, '已完成');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;