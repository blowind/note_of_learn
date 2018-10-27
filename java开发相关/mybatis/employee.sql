/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : employee

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-10-27 18:52:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_employee
-- ----------------------------
DROP TABLE IF EXISTS `t_employee`;
CREATE TABLE `t_employee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `real_name` varchar(60) NOT NULL,
  `sex` int(2) NOT NULL,
  `birthday` datetime NOT NULL,
  `mobile` varchar(20) NOT NULL,
  `email` varchar(60) NOT NULL,
  `position` varchar(20) NOT NULL,
  `note` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='雇员表\r\n在t_work_card和t_employee_task带外键引用到本表的情况下，\r\n在employee表对应的POJO Employee中会带有WorkCard和EmployeeTask两个类型的属性\r\nprivate WorkCard workCard;\r\nprivate List<EmployeeTask> employeeTaskList; 反应一对多的关系\r\n外部Employee的子类MaleEmployee进一步包含属性\r\nprivate MaleHealthForm maleHealthForm来关联到t_male_health_form表';

-- ----------------------------
-- Records of t_employee
-- ----------------------------

-- ----------------------------
-- Table structure for t_employee_task
-- ----------------------------
DROP TABLE IF EXISTS `t_employee_task`;
CREATE TABLE `t_employee_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `emp_id` int(11) NOT NULL,
  `task_id` int(11) NOT NULL COMMENT '在外键关联的情况下，对应的POJO里面此处存储的是task对象',
  `task_name` varchar(60) NOT NULL,
  `note` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Reference_4` (`emp_id`),
  KEY `FK_Reference_8` (`task_id`),
  CONSTRAINT `FK_Reference_4` FOREIGN KEY (`emp_id`) REFERENCES `t_employee` (`id`),
  CONSTRAINT `FK_Reference_8` FOREIGN KEY (`task_id`) REFERENCES `t_task` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='雇员任务表';

-- ----------------------------
-- Records of t_employee_task
-- ----------------------------

-- ----------------------------
-- Table structure for t_female_health_form
-- ----------------------------
DROP TABLE IF EXISTS `t_female_health_form`;
CREATE TABLE `t_female_health_form` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `emp_id` int(11) NOT NULL,
  `heart` varchar(64) NOT NULL,
  `liver` varchar(64) NOT NULL,
  `spleen` varchar(64) NOT NULL,
  `lung` varchar(64) NOT NULL,
  `kidney` varchar(64) NOT NULL,
  `uterus` varchar(64) NOT NULL,
  `note` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Reference_5` (`emp_id`),
  CONSTRAINT `FK_Reference_5` FOREIGN KEY (`emp_id`) REFERENCES `t_employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='女性体检表';

-- ----------------------------
-- Records of t_female_health_form
-- ----------------------------

-- ----------------------------
-- Table structure for t_male_health_form
-- ----------------------------
DROP TABLE IF EXISTS `t_male_health_form`;
CREATE TABLE `t_male_health_form` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `emp_id` int(11) NOT NULL,
  `heart` varchar(64) NOT NULL,
  `liver` varchar(64) NOT NULL,
  `spleen` varchar(64) NOT NULL,
  `lung` varchar(64) NOT NULL,
  `kidney` varchar(64) NOT NULL,
  `prostate` varchar(64) NOT NULL,
  `note` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Reference_6` (`emp_id`),
  CONSTRAINT `FK_Reference_6` FOREIGN KEY (`emp_id`) REFERENCES `t_employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='男性体检表';

-- ----------------------------
-- Records of t_male_health_form
-- ----------------------------

-- ----------------------------
-- Table structure for t_task
-- ----------------------------
DROP TABLE IF EXISTS `t_task`;
CREATE TABLE `t_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(60) NOT NULL,
  `context` varchar(255) NOT NULL,
  `note` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务表';

-- ----------------------------
-- Records of t_task
-- ----------------------------

-- ----------------------------
-- Table structure for t_work_card
-- ----------------------------
DROP TABLE IF EXISTS `t_work_card`;
CREATE TABLE `t_work_card` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `emp_id` int(11) NOT NULL,
  `real_name` varchar(60) NOT NULL,
  `department` varchar(20) NOT NULL,
  `mobile` varchar(20) NOT NULL,
  `position` varchar(30) NOT NULL,
  `note` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Reference_7` (`emp_id`),
  CONSTRAINT `FK_Reference_7` FOREIGN KEY (`emp_id`) REFERENCES `t_employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工牌表';

-- ----------------------------
-- Records of t_work_card
-- ----------------------------
