/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50552
Source Host           : localhost:3306
Source Database       : base_form

Target Server Type    : MYSQL
Target Server Version : 50552
File Encoding         : 65001

Date: 2016-11-25 16:46:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for core_advance_query
-- ----------------------------
DROP TABLE IF EXISTS `core_advance_query`;
CREATE TABLE `core_advance_query` (
  `advance_query_id` varchar(40) NOT NULL COMMENT '查询方案内码',
  `advance_query_name` varchar(40) NOT NULL COMMENT '查询方案名称',
  `function_code` varchar(40) NOT NULL COMMENT '功能编号',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `creator` varchar(40) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `editor` varchar(40) NOT NULL COMMENT '编辑人',
  `edit_time` datetime NOT NULL COMMENT '编辑时间',
  PRIMARY KEY (`advance_query_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='查询方案表';

-- ----------------------------
-- Records of core_advance_query
-- ----------------------------
INSERT INTO `core_advance_query` VALUES ('4d1389c882643d1a2f05ad403f13cc4d', 'TEST', 'CORE_ANNOUNCEMENT', 'TEST', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-21 15:06:47', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-21 15:06:47');
INSERT INTO `core_advance_query` VALUES ('d4ab72c0d0b274f9628d0bda56cf7ea5', 'TEST24', 'CORE_ANNOUNCEMENT', 'TEST', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-22 16:18:07', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-22 16:18:07');

-- ----------------------------
-- Table structure for core_advance_query_condition
-- ----------------------------
DROP TABLE IF EXISTS `core_advance_query_condition`;
CREATE TABLE `core_advance_query_condition` (
  `condition_id` varchar(40) NOT NULL COMMENT '条件内码',
  `advance_query_id` varchar(40) NOT NULL COMMENT '查询方案内码',
  `data_type` varchar(10) NOT NULL COMMENT '数据类别',
  `left_parentheses` varchar(10) DEFAULT NULL COMMENT '左括号',
  `condition_field` varchar(20) NOT NULL COMMENT '字段',
  `condition_type` varchar(2) NOT NULL COMMENT '条件',
  `condition_value` varchar(200) DEFAULT NULL COMMENT '值',
  `right_parentheses` varchar(10) DEFAULT NULL COMMENT '右括号',
  `logic` varchar(2) DEFAULT NULL COMMENT '逻辑',
  `sort` int(11) NOT NULL COMMENT '排序码',
  PRIMARY KEY (`condition_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='查询方案条件表';

-- ----------------------------
-- Records of core_advance_query_condition
-- ----------------------------
INSERT INTO `core_advance_query_condition` VALUES ('55dc5b0dbdfac8eab7b239f0224ab081', 'd4ab72c0d0b274f9628d0bda56cf7ea5', 'string', '', 'uname', '0', '张三', '', '', '0');
INSERT INTO `core_advance_query_condition` VALUES ('7c66db58212597bf37e7d2b96b679567', '4d1389c882643d1a2f05ad403f13cc4d', 'string', '', 'title', '0', '123123', '', '', '0');

-- ----------------------------
-- Table structure for core_advance_query_sort
-- ----------------------------
DROP TABLE IF EXISTS `core_advance_query_sort`;
CREATE TABLE `core_advance_query_sort` (
  `sort_id` varchar(40) NOT NULL COMMENT '排序内码',
  `advance_query_id` varchar(40) NOT NULL COMMENT '查询方案内码',
  `sort_field` varchar(20) NOT NULL COMMENT '字段',
  `sort_logic` varchar(2) NOT NULL COMMENT '排序逻辑',
  `sort` int(11) NOT NULL COMMENT '排序码',
  PRIMARY KEY (`sort_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='查询方案排序表';

-- ----------------------------
-- Records of core_advance_query_sort
-- ----------------------------

-- ----------------------------
-- Table structure for core_announcement
-- ----------------------------
DROP TABLE IF EXISTS `core_announcement`;
CREATE TABLE `core_announcement` (
  `announcement_id` varchar(40) NOT NULL COMMENT '公告内码',
  `title` varchar(200) NOT NULL COMMENT '公告标题',
  `content` text NOT NULL COMMENT '公告内容',
  `status` varchar(2) NOT NULL COMMENT '公告状态',
  `creator` varchar(40) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `editor` varchar(40) NOT NULL COMMENT '编辑人',
  `edit_time` datetime NOT NULL COMMENT '编辑时间',
  PRIMARY KEY (`announcement_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公告表';

-- ----------------------------
-- Records of core_announcement
-- ----------------------------

-- ----------------------------
-- Table structure for core_attr
-- ----------------------------
DROP TABLE IF EXISTS `core_attr`;
CREATE TABLE `core_attr` (
  `attr_id` varchar(40) NOT NULL COMMENT '参数内码',
  `attr_code` varchar(80) NOT NULL COMMENT '参数编号',
  `attr_name` varchar(40) NOT NULL COMMENT '参数名称',
  `type` varchar(2) NOT NULL COMMENT '参数类别',
  `content` text COMMENT '参数内容',
  `sort` int(11) NOT NULL COMMENT '排序码',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`attr_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='参数表';

-- ----------------------------
-- Records of core_attr
-- ----------------------------
INSERT INTO `core_attr` VALUES ('0224fe7163e7380fbc7d92983dff6f7c', 'base_role_ids', '框架基础角色', '1', '123456', '7', '框架基础角色');
INSERT INTO `core_attr` VALUES ('0829712de052d6e4eb3797a0efeb9e9d', 'login_page_system_title', '登录页面系统名称', '1', 'BASE-FORM1.0', '1', '登录页面系统名称');
INSERT INTO `core_attr` VALUES ('0a00ee3fb114dadb5a2726d5cbbd500f', 'login_page_copyright', '登录页面版权信息', '1', '<i class=\"fa fa-flag\"></i> base-form v1.0 · Created by <a href=\"http://blog.sina.com.cn/u/1978529257\">胜利路北13号</a>', '2', '登录页面版权信息');
INSERT INTO `core_attr` VALUES ('0c735b550d7e236c693019293116c19e', 'default_skin_info', '默认皮肤', '1', 'default.jpg', '17', '用户默认状态下使用的默认皮肤');
INSERT INTO `core_attr` VALUES ('11fb2963e45cee147ed533a707360a08', 'file_upload_webapp_interface', '文件上传应用接口地址', '1', 'http://192.168.2.6:8180/sourceManagement/UpFile', '0', '');
INSERT INTO `core_attr` VALUES ('1cc04aff145b76d4e88198eace9796e9', 'system_title', '系统标题', '1', 'BASE-FORM V1.0', '0', '系统标题');
INSERT INTO `core_attr` VALUES ('1e8396bfa7b623b36d268ef61606ab1c', 'show_button_when_no_limit', '无权限操作的按钮依然显示', '1', '1', '16', '无权限操作的按钮依然显示');
INSERT INTO `core_attr` VALUES ('1f8a120b8aebe5e118e2f637bddfc9f4', 'page_title', '默认页面标题', '1', 'base-form', '4', '默认页面标题');
INSERT INTO `core_attr` VALUES ('25acec4e48e01bb4cde5a542b5eadb08', 'default_background_float_speed', '默认背景漂浮速度', '1', '5', '20', '用户默认状态下背景漂浮速度');
INSERT INTO `core_attr` VALUES ('34ba96bc2c6d7f0fa4501aaf2dd942ff', 'base_system_ids', '框架基础子系统', '1', '12345', '5', '框架基础子系统');
INSERT INTO `core_attr` VALUES ('4482da7112af3c73cff0b47737997129', 'base_user_ids', '框架基础用户', '1', '240b59bf433b7701b58ba7adb63bfde0', '6', '框架基础用户');
INSERT INTO `core_attr` VALUES ('50953646f1a9c6c9409b0fa22f746a4d', 'online_login_user_single', '同一用户只能一处登录', '1', '0', '8', '同一用户只能一处登录');
INSERT INTO `core_attr` VALUES ('52af6b0df92f419b4cc2575ae60ace82', 'online_login_ip_single', '一个IP只能登录一个系统', '1', '0', '9', '一个IP只能登录一个系统');
INSERT INTO `core_attr` VALUES ('53e729659d318d909296989fbd61e7e2', 'default_is_show_shortcut', '默认是否显示快捷菜单', '1', '1', '18', '用户默认状态下是否显示快捷菜单');
INSERT INTO `core_attr` VALUES ('6d0a9a75157339d8b1b6ad4ba8690e77', 'exl_import_record_number', '导入excel数据允许的记录条数', '1', '100', '0', '');
INSERT INTO `core_attr` VALUES ('79db5b8bb7d5c10360071cfde917299c', 'initial_user_password', '新建用户的初始密码', '1', '123456', '10', '新建用户的初始密码');
INSERT INTO `core_attr` VALUES ('7f20abe89f1b96169c2cfada207c621f', 'advance_query_is_filter_user', '高级查询是否过滤登录用户', '1', '1', '11', '高级查询是否过滤登录用户');
INSERT INTO `core_attr` VALUES ('8e608aa58c4c91cc27bcf17485a7fcc3', 'sql_log_after_execute', 'SQL日志后续执行脚本', '1', 'delete from core_sql_log where cost<=300 and call_result=\'1\'', '15', 'SQL日志后续执行脚本');
INSERT INTO `core_attr` VALUES ('9ac6a8f745ab567d5f80a6f317b85a7d', 'default_is_background_float', '默认是否背景漂浮', '1', '0', '19', '用户默认状态下是否背景漂浮');
INSERT INTO `core_attr` VALUES ('b456d940d8469ef738d7fcef5b2bb6a6', 'operation_log_buffer_size', '操作日志缓冲池大小', '1', '10', '12', '操作日志缓冲池大小');
INSERT INTO `core_attr` VALUES ('bd1046b42d8c9ddbfa895dde10ec7323', 'sql_log_buffer_size', 'SQL日志缓冲池大小', '1', '10', '14', 'SQL日志缓冲池大小');
INSERT INTO `core_attr` VALUES ('cd7e2c0b35703c5c33c058b4bf3a9f4e', 'source_webapp_file_postion', '资源服务器资源地址', '1', 'http://127.0.0.1:8080/upload', '0', '');
INSERT INTO `core_attr` VALUES ('eb4dc6c1c5888c0ad14e63caef6ff00d', 'wzgl_reload_interface', '网站管理更新缓存接口', '1', 'http://localhost:8080/base-web/Index', '0', '更新门户网站页面');
INSERT INTO `core_attr` VALUES ('f14d6815aa7492b1869204653a49fa8f', 'operation_log_after_execute', '操作日志后续执行脚本', '1', 'delete from core_operation_log where cost<=300 and operation_result=\'1\'', '13', '操作日志后续执行脚本');

-- ----------------------------
-- Table structure for core_code_table
-- ----------------------------
DROP TABLE IF EXISTS `core_code_table`;
CREATE TABLE `core_code_table` (
  `c_type` varchar(40) NOT NULL COMMENT '码表类别编号',
  `c_key` varchar(40) NOT NULL COMMENT '码表键值',
  `c_value` varchar(200) NOT NULL COMMENT '码表内容',
  `sort` int(11) NOT NULL COMMENT '排序号',
  PRIMARY KEY (`c_type`,`c_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='码表表';

-- ----------------------------
-- Records of core_code_table
-- ----------------------------
INSERT INTO `core_code_table` VALUES ('album_flag', '1', '文章相册', '0');
INSERT INTO `core_code_table` VALUES ('album_flag', '2', '团队相册', '0');
INSERT INTO `core_code_table` VALUES ('articleType', '1', '通过', '0');
INSERT INTO `core_code_table` VALUES ('articleType', '2', '待评审', '1');
INSERT INTO `core_code_table` VALUES ('articleType', '3', '拒绝', '2');
INSERT INTO `core_code_table` VALUES ('article_limit', '1', '公开', '0');
INSERT INTO `core_code_table` VALUES ('article_limit', '2', '仅团队', '1');
INSERT INTO `core_code_table` VALUES ('attr_type', '1', '系统内置', '1');
INSERT INTO `core_code_table` VALUES ('attr_type', '2', '自定义参数', '2');
INSERT INTO `core_code_table` VALUES ('call_result', '0', '失败', '0');
INSERT INTO `core_code_table` VALUES ('call_result', '1', '成功', '1');
INSERT INTO `core_code_table` VALUES ('card_type', '1', '身份证', '1');
INSERT INTO `core_code_table` VALUES ('channel_type', '1', '网站栏目', '1');
INSERT INTO `core_code_table` VALUES ('channel_type', '2', '首页栏目', '2');
INSERT INTO `core_code_table` VALUES ('channel_type', '3', '团队栏目', '3');
INSERT INTO `core_code_table` VALUES ('degree', '1', '博士', '1');
INSERT INTO `core_code_table` VALUES ('degree', '10', '小学', '10');
INSERT INTO `core_code_table` VALUES ('degree', '11', '其他', '11');
INSERT INTO `core_code_table` VALUES ('degree', '2', '硕士', '2');
INSERT INTO `core_code_table` VALUES ('degree', '3', '大学', '3');
INSERT INTO `core_code_table` VALUES ('degree', '4', '大专', '4');
INSERT INTO `core_code_table` VALUES ('degree', '5', '中专中技', '5');
INSERT INTO `core_code_table` VALUES ('degree', '6', '技校', '6');
INSERT INTO `core_code_table` VALUES ('degree', '7', '高中', '7');
INSERT INTO `core_code_table` VALUES ('degree', '8', '职业高中', '8');
INSERT INTO `core_code_table` VALUES ('degree', '9', '初中', '9');
INSERT INTO `core_code_table` VALUES ('folk', '1', '汉族', '1');
INSERT INTO `core_code_table` VALUES ('folk', '10', '朝鲜族', '10');
INSERT INTO `core_code_table` VALUES ('folk', '11', '满族', '11');
INSERT INTO `core_code_table` VALUES ('folk', '12', '侗族', '12');
INSERT INTO `core_code_table` VALUES ('folk', '13', '瑶族', '13');
INSERT INTO `core_code_table` VALUES ('folk', '14', '白族', '14');
INSERT INTO `core_code_table` VALUES ('folk', '15', '土家族', '15');
INSERT INTO `core_code_table` VALUES ('folk', '16', '哈尼族', '16');
INSERT INTO `core_code_table` VALUES ('folk', '17', '哈萨克族', '17');
INSERT INTO `core_code_table` VALUES ('folk', '18', '傣族', '18');
INSERT INTO `core_code_table` VALUES ('folk', '19', '黎族', '19');
INSERT INTO `core_code_table` VALUES ('folk', '2', '蒙古族', '2');
INSERT INTO `core_code_table` VALUES ('folk', '20', '傈僳族', '20');
INSERT INTO `core_code_table` VALUES ('folk', '21', '佤族', '21');
INSERT INTO `core_code_table` VALUES ('folk', '22', '畲族', '22');
INSERT INTO `core_code_table` VALUES ('folk', '23', '高山族', '23');
INSERT INTO `core_code_table` VALUES ('folk', '24', '拉祜族', '24');
INSERT INTO `core_code_table` VALUES ('folk', '25', '水族', '25');
INSERT INTO `core_code_table` VALUES ('folk', '26', '东乡族', '26');
INSERT INTO `core_code_table` VALUES ('folk', '27', '纳西族', '27');
INSERT INTO `core_code_table` VALUES ('folk', '28', '景颇族', '28');
INSERT INTO `core_code_table` VALUES ('folk', '29', '柯尔克孜', '29');
INSERT INTO `core_code_table` VALUES ('folk', '3', '回族', '3');
INSERT INTO `core_code_table` VALUES ('folk', '30', '土族', '30');
INSERT INTO `core_code_table` VALUES ('folk', '31', '达斡尔族', '31');
INSERT INTO `core_code_table` VALUES ('folk', '32', '仫佬族', '32');
INSERT INTO `core_code_table` VALUES ('folk', '33', '羌族', '33');
INSERT INTO `core_code_table` VALUES ('folk', '34', '布朗族', '34');
INSERT INTO `core_code_table` VALUES ('folk', '35', '撒拉族', '35');
INSERT INTO `core_code_table` VALUES ('folk', '36', '毛南族', '36');
INSERT INTO `core_code_table` VALUES ('folk', '37', '仡佬族', '37');
INSERT INTO `core_code_table` VALUES ('folk', '38', '锡伯族', '38');
INSERT INTO `core_code_table` VALUES ('folk', '39', '阿昌族', '39');
INSERT INTO `core_code_table` VALUES ('folk', '4', '藏族', '4');
INSERT INTO `core_code_table` VALUES ('folk', '40', '普米族', '40');
INSERT INTO `core_code_table` VALUES ('folk', '41', '塔吉克族', '41');
INSERT INTO `core_code_table` VALUES ('folk', '42', '怒族', '42');
INSERT INTO `core_code_table` VALUES ('folk', '43', '乌孜别克', '43');
INSERT INTO `core_code_table` VALUES ('folk', '44', '俄罗斯族', '44');
INSERT INTO `core_code_table` VALUES ('folk', '45', '鄂温克族', '45');
INSERT INTO `core_code_table` VALUES ('folk', '46', '德昂族', '46');
INSERT INTO `core_code_table` VALUES ('folk', '47', '保安族', '47');
INSERT INTO `core_code_table` VALUES ('folk', '48', '裕固族', '48');
INSERT INTO `core_code_table` VALUES ('folk', '49', '京族', '49');
INSERT INTO `core_code_table` VALUES ('folk', '5', '维吾尔族', '5');
INSERT INTO `core_code_table` VALUES ('folk', '50', '塔塔尔族', '50');
INSERT INTO `core_code_table` VALUES ('folk', '51', '独龙族', '51');
INSERT INTO `core_code_table` VALUES ('folk', '52', '鄂伦春族', '52');
INSERT INTO `core_code_table` VALUES ('folk', '53', '赫哲族', '53');
INSERT INTO `core_code_table` VALUES ('folk', '54', '门巴族', '54');
INSERT INTO `core_code_table` VALUES ('folk', '55', '珞巴族', '55');
INSERT INTO `core_code_table` VALUES ('folk', '56', '基诺族', '56');
INSERT INTO `core_code_table` VALUES ('folk', '6', '苗族', '6');
INSERT INTO `core_code_table` VALUES ('folk', '7', '彝族', '7');
INSERT INTO `core_code_table` VALUES ('folk', '8', '壮族', '8');
INSERT INTO `core_code_table` VALUES ('folk', '9', '布依族', '9');
INSERT INTO `core_code_table` VALUES ('hjdj', '1', '一等', '1');
INSERT INTO `core_code_table` VALUES ('hjdj', '2', '二等', '2');
INSERT INTO `core_code_table` VALUES ('hjdj', '3', '三等', '3');
INSERT INTO `core_code_table` VALUES ('jszz', '0', '北京市骨干教师', '0');
INSERT INTO `core_code_table` VALUES ('jszz', '1', '北京市特级教师', '1');
INSERT INTO `core_code_table` VALUES ('jszz', '2', '北京市学科带头人', '2');
INSERT INTO `core_code_table` VALUES ('jszz', '3', '北京市正高级教师', '3');
INSERT INTO `core_code_table` VALUES ('jszz', '4', '区级骨干教师', '4');
INSERT INTO `core_code_table` VALUES ('jszz', '5', '区级学科带头人', '5');
INSERT INTO `core_code_table` VALUES ('jszz', '6', '区级优秀青年', '6');
INSERT INTO `core_code_table` VALUES ('limit_type', '1', '菜单', '1');
INSERT INTO `core_code_table` VALUES ('limit_type', '2', '按钮', '2');
INSERT INTO `core_code_table` VALUES ('login_status', '1', '登录成功', '1');
INSERT INTO `core_code_table` VALUES ('login_status', '2', '用户不存在', '2');
INSERT INTO `core_code_table` VALUES ('login_status', '3', '密码错误', '3');
INSERT INTO `core_code_table` VALUES ('login_status', '4', '用户无效', '4');
INSERT INTO `core_code_table` VALUES ('login_status', '5', '用户已在线', '5');
INSERT INTO `core_code_table` VALUES ('login_status', '6', 'IP已在线', '6');
INSERT INTO `core_code_table` VALUES ('logout_type', '1', '正常退出', '1');
INSERT INTO `core_code_table` VALUES ('logout_type', '2', 'SESSION失效退出', '2');
INSERT INTO `core_code_table` VALUES ('logout_type', '3', '服务启动退出', '3');
INSERT INTO `core_code_table` VALUES ('logout_type', '4', '强制下线', '4');
INSERT INTO `core_code_table` VALUES ('mail_delete_from', 'receive', '收件箱', '1');
INSERT INTO `core_code_table` VALUES ('mail_delete_from', 'send', '发件箱', '2');
INSERT INTO `core_code_table` VALUES ('mail_receive_status', '0', '未读', '0');
INSERT INTO `core_code_table` VALUES ('mail_receive_status', '1', '已读', '1');
INSERT INTO `core_code_table` VALUES ('mail_receive_status', '2', '已删除', '2');
INSERT INTO `core_code_table` VALUES ('mail_receive_status', '3', '已彻底删除', '3');
INSERT INTO `core_code_table` VALUES ('mail_send_status', '0', '草稿', '0');
INSERT INTO `core_code_table` VALUES ('mail_send_status', '1', '已发送', '1');
INSERT INTO `core_code_table` VALUES ('mail_send_status', '2', '已删除', '2');
INSERT INTO `core_code_table` VALUES ('mail_send_status', '3', '已彻底删除', '3');
INSERT INTO `core_code_table` VALUES ('open_close', '0', '禁用', '0');
INSERT INTO `core_code_table` VALUES ('open_close', '1', '启用', '1');
INSERT INTO `core_code_table` VALUES ('operation_result', '1', '访问成功', '1');
INSERT INTO `core_code_table` VALUES ('operation_result', '2', '会话超时', '2');
INSERT INTO `core_code_table` VALUES ('operation_result', '3', '无权访问', '3');
INSERT INTO `core_code_table` VALUES ('operation_result', '4', '响应错误', '4');
INSERT INTO `core_code_table` VALUES ('operation_type', '0', '未知', '0');
INSERT INTO `core_code_table` VALUES ('operation_type', '1', '访问', '1');
INSERT INTO `core_code_table` VALUES ('operation_type', '2', '查询', '2');
INSERT INTO `core_code_table` VALUES ('operation_type', '3', '新增', '3');
INSERT INTO `core_code_table` VALUES ('operation_type', '4', '编辑', '4');
INSERT INTO `core_code_table` VALUES ('operation_type', '5', '删除', '5');
INSERT INTO `core_code_table` VALUES ('operation_type', '6', '登录', '6');
INSERT INTO `core_code_table` VALUES ('operation_type', '7', '登出', '7');
INSERT INTO `core_code_table` VALUES ('operation_type', '8', '测试', '8');
INSERT INTO `core_code_table` VALUES ('pcx', 'JSYR', '教书育人', '0');
INSERT INTO `core_code_table` VALUES ('pcx', 'JXJYJL', '继续教育经历', '5');
INSERT INTO `core_code_table` VALUES ('pcx', 'JYJXJL', '教育教学经历', '4');
INSERT INTO `core_code_table` VALUES ('pcx', 'JYJXYJ', '教育教学研究', '2');
INSERT INTO `core_code_table` VALUES ('pcx', 'KCJX', '课程教学', '1');
INSERT INTO `core_code_table` VALUES ('pcx', 'QT', '其它', '7');
INSERT INTO `core_code_table` VALUES ('pcx', 'YXL', '影响力', '3');
INSERT INTO `core_code_table` VALUES ('pcx', 'ZYJSZC', '专业技术职称', '6');
INSERT INTO `core_code_table` VALUES ('picture_flag', '0', '自定义图片', '0');
INSERT INTO `core_code_table` VALUES ('picture_flag', '1', '文章图片', '1');
INSERT INTO `core_code_table` VALUES ('picture_flag', '2', '首页轮播图片', '2');
INSERT INTO `core_code_table` VALUES ('picture_flag', '3', '欢迎页轮播图片', '3');
INSERT INTO `core_code_table` VALUES ('picture_flag', '4', '图片集', '4');
INSERT INTO `core_code_table` VALUES ('picture_show', '0', '隐藏', '0');
INSERT INTO `core_code_table` VALUES ('picture_show', '1', '显示', '1');
INSERT INTO `core_code_table` VALUES ('review_status', '0', '待评审', '0');
INSERT INTO `core_code_table` VALUES ('review_status', '1', '通过', '1');
INSERT INTO `core_code_table` VALUES ('review_status', '2', '未通过', '2');
INSERT INTO `core_code_table` VALUES ('review_status', '3', '废除', '3');
INSERT INTO `core_code_table` VALUES ('review_status', '4', '未提交', '4');
INSERT INTO `core_code_table` VALUES ('review_status_zj', '0', '待评审', '0');
INSERT INTO `core_code_table` VALUES ('review_status_zj', '1', '通过', '0');
INSERT INTO `core_code_table` VALUES ('review_status_zj', '2', '未通过', '0');
INSERT INTO `core_code_table` VALUES ('ryjb', '0', '其他', '0');
INSERT INTO `core_code_table` VALUES ('ryjb', '1', '国家级', '1');
INSERT INTO `core_code_table` VALUES ('ryjb', '2', '省级', '2');
INSERT INTO `core_code_table` VALUES ('ryjb', '3', '市级', '3');
INSERT INTO `core_code_table` VALUES ('ryjb', '4', '区级', '4');
INSERT INTO `core_code_table` VALUES ('ryjb', '5', '校级', '5');
INSERT INTO `core_code_table` VALUES ('sex', '1', '男', '1');
INSERT INTO `core_code_table` VALUES ('sex', '2', '女', '2');
INSERT INTO `core_code_table` VALUES ('tdjs_articleType', '1', '通过', '0');
INSERT INTO `core_code_table` VALUES ('tdjs_articleType', '2', '待评审', '1');
INSERT INTO `core_code_table` VALUES ('tdjs_articleType', '3', '拒绝', '2');
INSERT INTO `core_code_table` VALUES ('tdjs_articleType', '4', '草稿', '3');
INSERT INTO `core_code_table` VALUES ('user_identity', '0', '普通用户', '0');
INSERT INTO `core_code_table` VALUES ('user_identity', '1', '管理者', '1');
INSERT INTO `core_code_table` VALUES ('valid_type', '0', '无效', '0');
INSERT INTO `core_code_table` VALUES ('valid_type', '1', '有效', '1');
INSERT INTO `core_code_table` VALUES ('video_status', '1', '转换中', '0');
INSERT INTO `core_code_table` VALUES ('video_status', '2', '已发布', '0');
INSERT INTO `core_code_table` VALUES ('video_status', '3', '上传成功', '0');
INSERT INTO `core_code_table` VALUES ('video_status', '4', '上传失败', '0');
INSERT INTO `core_code_table` VALUES ('video_status', '5', '上传中', '0');
INSERT INTO `core_code_table` VALUES ('zero_one', '0', '否', '0');
INSERT INTO `core_code_table` VALUES ('zero_one', '1', '是', '1');

-- ----------------------------
-- Table structure for core_code_table_type
-- ----------------------------
DROP TABLE IF EXISTS `core_code_table_type`;
CREATE TABLE `core_code_table_type` (
  `c_type` varchar(40) NOT NULL COMMENT '码表类别编号',
  `c_info` varchar(40) NOT NULL COMMENT '码表类别名称',
  PRIMARY KEY (`c_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='码表类别表';

-- ----------------------------
-- Records of core_code_table_type
-- ----------------------------
INSERT INTO `core_code_table_type` VALUES ('album_flag', '相册类型');
INSERT INTO `core_code_table_type` VALUES ('articleType', '评审文章类型');
INSERT INTO `core_code_table_type` VALUES ('article_limit', '文章权限');
INSERT INTO `core_code_table_type` VALUES ('attr_type', '参数类别');
INSERT INTO `core_code_table_type` VALUES ('call_result', '调用结果');
INSERT INTO `core_code_table_type` VALUES ('card_type', '证件类型');
INSERT INTO `core_code_table_type` VALUES ('channel_type', '栏目类型');
INSERT INTO `core_code_table_type` VALUES ('degree', '学历');
INSERT INTO `core_code_table_type` VALUES ('folk', '民族');
INSERT INTO `core_code_table_type` VALUES ('hjdj', '获奖等级');
INSERT INTO `core_code_table_type` VALUES ('jszz', '教师资质');
INSERT INTO `core_code_table_type` VALUES ('limit_type', '按钮类别');
INSERT INTO `core_code_table_type` VALUES ('login_status', '登录日志状态');
INSERT INTO `core_code_table_type` VALUES ('logout_type', '登出类型');
INSERT INTO `core_code_table_type` VALUES ('mail_delete_from', '邮件删除来源');
INSERT INTO `core_code_table_type` VALUES ('mail_receive_status', '邮件接收状态');
INSERT INTO `core_code_table_type` VALUES ('mail_send_status', '邮件发送状态');
INSERT INTO `core_code_table_type` VALUES ('open_close', '启用-禁用');
INSERT INTO `core_code_table_type` VALUES ('operation_result', '操作结果');
INSERT INTO `core_code_table_type` VALUES ('operation_type', '操作类别');
INSERT INTO `core_code_table_type` VALUES ('pcx', '评测项');
INSERT INTO `core_code_table_type` VALUES ('picture_flag', '图片类型');
INSERT INTO `core_code_table_type` VALUES ('picture_show', '图片是否展示');
INSERT INTO `core_code_table_type` VALUES ('review_status', '评审状态(管理员查看)');
INSERT INTO `core_code_table_type` VALUES ('review_status_zj', '评审状态(专家查看)');
INSERT INTO `core_code_table_type` VALUES ('ryjb', '荣誉级别');
INSERT INTO `core_code_table_type` VALUES ('sex', '性别');
INSERT INTO `core_code_table_type` VALUES ('tdjs_articleType', '团队建设文章类型');
INSERT INTO `core_code_table_type` VALUES ('user_identity', '用户身份');
INSERT INTO `core_code_table_type` VALUES ('valid_type', '有效标识');
INSERT INTO `core_code_table_type` VALUES ('video_status', '视频状态');
INSERT INTO `core_code_table_type` VALUES ('zero_one', '0-1是否');

-- ----------------------------
-- Table structure for core_default_shortcut_limit
-- ----------------------------
DROP TABLE IF EXISTS `core_default_shortcut_limit`;
CREATE TABLE `core_default_shortcut_limit` (
  `limit_id` varchar(40) NOT NULL COMMENT '功能内码',
  PRIMARY KEY (`limit_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='默认快捷功能表';

-- ----------------------------
-- Records of core_default_shortcut_limit
-- ----------------------------

-- ----------------------------
-- Table structure for core_dept
-- ----------------------------
DROP TABLE IF EXISTS `core_dept`;
CREATE TABLE `core_dept` (
  `dept_id` varchar(40) NOT NULL COMMENT '部门编号',
  `pre_dept_id` varchar(40) NOT NULL COMMENT '上级部门编号',
  `dept_name` varchar(40) NOT NULL COMMENT '部门名称',
  `phone` varchar(20) NOT NULL COMMENT '联系电话',
  `principal` varchar(40) NOT NULL COMMENT '负责人',
  `principal_phone` varchar(20) NOT NULL COMMENT '负责人电话',
  `sort` int(11) NOT NULL COMMENT '排序号',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `creator` varchar(40) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `editor` varchar(40) NOT NULL COMMENT '编辑人',
  `edit_time` datetime NOT NULL COMMENT '编辑时间',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门';

-- ----------------------------
-- Records of core_dept
-- ----------------------------

-- ----------------------------
-- Table structure for core_group
-- ----------------------------
DROP TABLE IF EXISTS `core_group`;
CREATE TABLE `core_group` (
  `group_id` varchar(40) NOT NULL COMMENT '用户组内码',
  `group_name` varchar(40) NOT NULL COMMENT '用户组名称',
  `group_type` varchar(2) NOT NULL COMMENT '用户组类型',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `creator` varchar(40) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `editor` varchar(40) NOT NULL COMMENT '编辑人',
  `edit_time` datetime NOT NULL COMMENT '编辑时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户组表';

-- ----------------------------
-- Records of core_group
-- ----------------------------

-- ----------------------------
-- Table structure for core_limit
-- ----------------------------
DROP TABLE IF EXISTS `core_limit`;
CREATE TABLE `core_limit` (
  `limit_id` varchar(40) NOT NULL COMMENT '功能内码',
  `pre_limit_id` varchar(40) NOT NULL COMMENT '上级功能内码',
  `system_id` varchar(40) NOT NULL COMMENT '系统内码',
  `limit_name` varchar(40) NOT NULL COMMENT '功能名称',
  `limit_type` varchar(2) NOT NULL COMMENT '功能类型',
  `ifAsynch` varchar(2) NOT NULL DEFAULT '0' COMMENT '链接请求是否异步',
  `url` varchar(120) NOT NULL COMMENT 'URL地址',
  `sort` int(11) NOT NULL COMMENT '排序码',
  `icon_fa` varchar(40) NOT NULL COMMENT '图标',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `creator` varchar(40) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `editor` varchar(40) NOT NULL COMMENT '编辑人',
  `edit_time` datetime NOT NULL COMMENT '编辑时间',
  PRIMARY KEY (`limit_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统功能表';

-- ----------------------------
-- Records of core_limit
-- ----------------------------
INSERT INTO `core_limit` VALUES ('000af26bda6a6adf90da9edb4bcf734e', 'e8240eae240368d3b81344acf5ef06ec', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '删除码表', '2', '0', 'core/system/code_table/*/code_table/delete', '9', 'trash-o', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-07 13:11:43');
INSERT INTO `core_limit` VALUES ('056b6f23abecbfd2a3caa4ec65f3980a', 'b5d40cd48bdb1a3877426c0c842144b1', '3892bfe623082aa570471b6cf177f45e', '文章管理', '1', '0', 'wzgl/article/article', '4', 'file-o', '文章管理', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2016-07-12 20:36:28', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-18 13:50:35');
INSERT INTO `core_limit` VALUES ('0bb86e97a94343bc78d7aed8082b8076', 'a8046c145134645c58398722ecd25e5f', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '编辑', '2', '0', 'core/task/task/*/edit', '3', 'edit', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-07 13:15:51');
INSERT INTO `core_limit` VALUES ('0bdf4ddbfc838992f9a133346275b46d', 'fa50872f12e1a3bf75f4548f9ff37461', '865f38e822e157fc2719b1933930ec03', '新增', '2', '0', 'tdjs/tdjsPicture/picture/add', '1', 'plus', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-27 18:35:07', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-28 10:10:20');
INSERT INTO `core_limit` VALUES ('0cbc84e12a1a65d3de109b278920d786', '11482385e77e8e9ccaa228ee3eb2c0bf', '3892bfe623082aa570471b6cf177f45e', '删除', '2', '0', 'wzgl/channel/channel/delete', '2', 'trash-o', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2016-07-13 09:01:54', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-14 17:12:45');
INSERT INTO `core_limit` VALUES ('0f81dd9d7dc2dcc57c35b5f8aae1cfe4', 'ad1ea16b08345f81eef8103efc1ba3cc', '865f38e822e157fc2719b1933930ec03', '启用', '2', '0', 'tdjs/tdjsVideo/video/open', '4', 'check', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-25 17:22:46', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-25 17:26:29');
INSERT INTO `core_limit` VALUES ('0fbc539a9ccd5f1d12c0d2124cf1f54d', '11482385e77e8e9ccaa228ee3eb2c0bf', '3892bfe623082aa570471b6cf177f45e', '新增', '2', '0', 'wzgl/channel/channel/add', '1', 'plus', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2016-07-11 20:38:49', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-14 17:12:15');
INSERT INTO `core_limit` VALUES ('10e86db0241d729a5cc2ebd59e2b040b', '11482385e77e8e9ccaa228ee3eb2c0bf', '3892bfe623082aa570471b6cf177f45e', '修改', '2', '0', 'wzgl/channel/channel/edit', '3', 'edit', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2016-07-13 09:02:40', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2016-07-13 09:02:40');
INSERT INTO `core_limit` VALUES ('11482385e77e8e9ccaa228ee3eb2c0bf', 'b5d40cd48bdb1a3877426c0c842144b1', '3892bfe623082aa570471b6cf177f45e', '栏目管理', '1', '0', 'wzgl/channel/channel', '1', 'navicon', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2016-07-11 20:37:41', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-14 11:08:29');
INSERT INTO `core_limit` VALUES ('118702e0ed598e0d3a5f376efda42bb0', '7697a17f9291534109351f7e41fd26cd', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '编辑', '2', '0', 'core/system/dept/*/edit', '2', 'edit', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-05 13:53:01', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-07 13:15:20');
INSERT INTO `core_limit` VALUES ('1302b6b76e5610c0c951e5b029bd1d92', '6ebad90faed981e0ca76131dd69a034a', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '公告管理', '1', '0', 'core/announcement/announcement', '1', 'bell-o', null, 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00');
INSERT INTO `core_limit` VALUES ('16cb992e02a44885d2a5d7a684905cb5', '50bdd77ddb05ccb5201506236c80a412', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '新增', '2', '0', 'core/system/user/add', '2', 'plus', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-07 13:12:26');
INSERT INTO `core_limit` VALUES ('16e0bca5286dcd3e49786b7b20e51483', '97e14f2fb96bf4602f7c0affe032ddad', '865f38e822e157fc2719b1933930ec03', '编辑', '2', '0', 'tdjs/channel/channel/edit', '2', 'pencil-square-o', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-25 11:25:13', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-25 11:25:13');
INSERT INTO `core_limit` VALUES ('199dcabe8410a81e2579c4faf97da1f6', '3e430f69d2c3cb4968424be37f2b197c', '865f38e822e157fc2719b1933930ec03', '启用', '2', '0', 'tdjs/tdjsArticle/article/open', '4', 'check', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-25 16:37:38', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-25 16:39:30');
INSERT INTO `core_limit` VALUES ('1a24f4d3d8ae31e4d301bae3a971ab36', 'e8240eae240368d3b81344acf5ef06ec', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '编辑码表', '2', '0', 'core/system/code_table/*/code_table/*/edit', '8', 'edit', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-07 13:11:27');
INSERT INTO `core_limit` VALUES ('1b302e17fd26a3d2a5a7a95ca78037d5', 'b5d40cd48bdb1a3877426c0c842144b1', '3892bfe623082aa570471b6cf177f45e', '相册管理', '1', '0', '/wzgl/album/album', '7', 'folder', '相册管理', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-27 13:27:27', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-27 13:28:04');
INSERT INTO `core_limit` VALUES ('1c2cf7be651103c6aed430adb06a5b3a', '7b63bbce0d9b93b106551c84dda6f126', '3892bfe623082aa570471b6cf177f45e', '启用', '2', '0', 'wzgl/email/email/show', '4', 'check', '', '240b59bf433b7701b58ba7adb63bfde0', '2016-11-10 13:35:06', '240b59bf433b7701b58ba7adb63bfde0', '2016-11-10 13:35:30');
INSERT INTO `core_limit` VALUES ('1de6b94ab9eaa3a71cf23d6dc3486e91', '50bdd77ddb05ccb5201506236c80a412', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '编辑', '2', '0', 'core/system/user/*/edit', '3', 'edit', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-07 13:12:34');
INSERT INTO `core_limit` VALUES ('1ded55f537c4d4bc8c0324852301b78c', '1302b6b76e5610c0c951e5b029bd1d92', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '删除', '2', '0', 'core/announcement/announcement/delete', '6', 'trash-o', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-07 13:16:53');
INSERT INTO `core_limit` VALUES ('21e6337ec810f5ee316b6fe6b8d4511a', '2836bdfe266dae1f801b9b96fd3e6048', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '新增', '2', '0', 'core/system/role/add', '2', 'plus', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-07 13:13:34');
INSERT INTO `core_limit` VALUES ('239286040e7fd22b27bc0eb9e5395ffe', 'e8240eae240368d3b81344acf5ef06ec', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '新建码表信息', '2', '0', 'core/system/code_table/code_table_type/add', '2', 'plus', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-07 13:10:15');
INSERT INTO `core_limit` VALUES ('263288db3e2a17b15f35d10f4e254fb1', 'acfba8514ef06c1427194af2932dcd02', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '任务查看', '1', '0', 'core/task/view_task', '2', 'eye', null, 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00');
INSERT INTO `core_limit` VALUES ('2836bdfe266dae1f801b9b96fd3e6048', 'b40cd5be82186ce3f5c21c3c966b35c9', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '角色管理', '1', '0', 'core/system/role', '5', 'lock', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-19 09:47:24');
INSERT INTO `core_limit` VALUES ('28fdd7b7b0369613b14d7d603e8b5353', '7b63bbce0d9b93b106551c84dda6f126', '3892bfe623082aa570471b6cf177f45e', '禁用', '2', '0', 'wzgl/email/email/hide', '5', 'times', '', '240b59bf433b7701b58ba7adb63bfde0', '2016-11-10 13:36:13', '240b59bf433b7701b58ba7adb63bfde0', '2016-11-10 13:36:13');
INSERT INTO `core_limit` VALUES ('2926b3eb2cddb8d6a5950c694eff9014', '8654ac15d6d46ead928e6f98cf37d7c1', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '编辑', '2', '0', 'core/system/system/*/edit', '3', 'edit', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-07 13:08:51');
INSERT INTO `core_limit` VALUES ('2b3bbaa8c8ff50a42b51e65ea2e0cc18', 'dedcdd22ed3d0632609cd1fd47c2d239', 'c461f41655d29e14f79e3a439260cb27', '评测查看', '1', '0', 'jspc/zwpc/zwpc/listPage', '3', 'eye', '查看已提交的评测信息', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-29 16:40:57', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-08-01 11:35:19');
INSERT INTO `core_limit` VALUES ('2be1f078671d0281c12882126f71bd7a', '5a9399d0fe2d49f39dc6725b63268efa', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '操作日志查询', '1', '0', 'core/log/operation_log', '4', 'table', null, 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00');
INSERT INTO `core_limit` VALUES ('2d0bce209e985a146241920258324c5c', '5a9399d0fe2d49f39dc6725b63268efa', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '登录日志查询', '1', '0', 'core/log/login_log', '2', 'table', null, 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00');
INSERT INTO `core_limit` VALUES ('2e246b2d9e556b3b1a95f677835216fb', 'b5d40cd48bdb1a3877426c0c842144b1', '3892bfe623082aa570471b6cf177f45e', '视频管理', '1', '0', 'wzgl/video/video', '5', 'video-camera', '视频管理', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-20 11:13:39', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-27 18:33:31');
INSERT INTO `core_limit` VALUES ('2fad67e7358cfa59ba1fa48b9f1770c3', '7fdc670ebe6f1c27b0ca5cfd7594f225', '865f38e822e157fc2719b1933930ec03', '文章审批', '1', '0', 'tdjs/tdjsArticle/articleCheck', '6', 'file-word-o', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-08-11 15:42:27', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-08-11 15:45:15');
INSERT INTO `core_limit` VALUES ('30114aba4c8d49173801b20feeddba3f', '7697a17f9291534109351f7e41fd26cd', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '新增', '2', '0', 'core/system/dept/add', '1', 'plus', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-05 13:52:43', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-07 13:15:12');
INSERT INTO `core_limit` VALUES ('339e71d24fe19d00b2448fb8d46906a6', 'a8046c145134645c58398722ecd25e5f', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '禁用', '2', '0', 'core/task/task/close', '5', 'times', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-07 13:16:04');
INSERT INTO `core_limit` VALUES ('35fb1eeab4bcb82825b63a244c005575', '2fad67e7358cfa59ba1fa48b9f1770c3', '865f38e822e157fc2719b1933930ec03', '文章审批', '2', '0', 'tdjs/tdjsArticle/articleCheck/review', '1', 'file-pdf-o', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-08-11 16:17:11', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-08-11 16:18:53');
INSERT INTO `core_limit` VALUES ('363c16bc637131d080e28935ace11ff1', 'ad1ea16b08345f81eef8103efc1ba3cc', '865f38e822e157fc2719b1933930ec03', '禁用', '2', '0', 'tdjs/tdjsVideo/video/close', '5', 'times', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-25 17:23:50', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-25 17:26:41');
INSERT INTO `core_limit` VALUES ('3b19e8915200e69be5395965ae81ecf8', 'dedcdd22ed3d0632609cd1fd47c2d239', 'c461f41655d29e14f79e3a439260cb27', '专家评测(管理员)', '1', '0', 'jspc/zjpc/zjpc/admin', '5', 'legal', '专家评测(管理员)', '240b59bf433b7701b58ba7adb63bfde0', '2016-08-16 14:51:17', '240b59bf433b7701b58ba7adb63bfde0', '2016-08-16 14:51:17');
INSERT INTO `core_limit` VALUES ('3e430f69d2c3cb4968424be37f2b197c', '7fdc670ebe6f1c27b0ca5cfd7594f225', '865f38e822e157fc2719b1933930ec03', '文章管理', '1', '0', 'tdjs/tdjsArticle/article', '2', 'file-o', '内容管理', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-25 16:33:28', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-25 16:34:04');
INSERT INTO `core_limit` VALUES ('3eab677934e8570a8e2a9db1a62d544e', 'b97193fb7871151e17775181b181e3a2', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '服务器管理', '1', '0', 'home', '3', 'folder-o', null, 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00');
INSERT INTO `core_limit` VALUES ('3f83c6527b78d3034039f6691dc9977c', 'a4b28e926583ed3b3977793d8ece5e54', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '删除', '2', '0', 'core/system/limit/delete', '4', 'trash-o', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-07 13:12:16');
INSERT INTO `core_limit` VALUES ('48f410e92c4c11693b7c5629926f56da', '97e14f2fb96bf4602f7c0affe032ddad', '865f38e822e157fc2719b1933930ec03', '删除', '2', '0', 'tdjs/channel/channel/delet', '3', 'trash-o', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-25 11:25:47', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-25 11:26:30');
INSERT INTO `core_limit` VALUES ('4a1413240c86aa8a34a8c5228131cfa0', 'e4738d2a3ce1f321e4d74b3607a8666b', '3892bfe623082aa570471b6cf177f45e', '编辑', '2', '0', 'wzgl/picture/picture/*/edit', '6', 'edit', '编辑', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-27 20:16:53', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-27 20:16:53');
INSERT INTO `core_limit` VALUES ('504960bfa845a54b383a74d010ab4e12', '5a9399d0fe2d49f39dc6725b63268efa', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'SQL日志查询', '1', '0', 'core/log/sql_log', '5', 'table', null, 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00');
INSERT INTO `core_limit` VALUES ('50bdd77ddb05ccb5201506236c80a412', 'b40cd5be82186ce3f5c21c3c966b35c9', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '用户管理', '1', '0', 'core/system/user', '4', 'user', null, 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00');
INSERT INTO `core_limit` VALUES ('512250f750d5106a84ea967598d67917', '9101e48e6097e5772b7c73ae1931be4e', '865f38e822e157fc2719b1933930ec03', '新增', '2', '0', 'tdjs/tdjsAlbum/album/add', '1', 'plus', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-28 10:37:04', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-28 11:11:27');
INSERT INTO `core_limit` VALUES ('538eb737a0f3a40c130799cde03b3681', '5a8ffd5083119437ef6f526a9a14a55c', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '编辑', '2', '0', 'core/team/*/edit', '2', 'edit', '编辑团队信息', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-11 19:08:30', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-12 10:00:24');
INSERT INTO `core_limit` VALUES ('545f282cf0b4c10cd19b9e3ed649f6f0', '50bdd77ddb05ccb5201506236c80a412', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '配置用户参数', '2', '0', 'core/system/user/*/set_user_attr', '7', 'gears', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2016-05-10 13:56:05', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2016-05-10 13:56:05');
INSERT INTO `core_limit` VALUES ('560a3cf481cf0de9eef61babdacc8a8d', '3eab677934e8570a8e2a9db1a62d544e', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '文件服务器', '1', '0', 'core/server/file', '2', 'file-o', null, 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00');
INSERT INTO `core_limit` VALUES ('5a8ffd5083119437ef6f526a9a14a55c', 'b40cd5be82186ce3f5c21c3c966b35c9', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '团队管理', '1', '0', 'core/team', '7', 'group', '团队管理', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-11 18:55:44', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-14 09:49:17');
INSERT INTO `core_limit` VALUES ('5a9399d0fe2d49f39dc6725b63268efa', 'b97193fb7871151e17775181b181e3a2', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '日志查询', '1', '0', 'home', '6', 'table', null, 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00');
INSERT INTO `core_limit` VALUES ('5c228adffe2439eaf7f95e4d2e702bf0', 'b97193fb7871151e17775181b181e3a2', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '系统工具', '1', '0', 'home', '7', 'puzzle-piece', null, 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00');
INSERT INTO `core_limit` VALUES ('5e912d9e49ee85d4b2e46be5608ffc66', 'top', '3892bfe623082aa570471b6cf177f45e', '网站管理系统', '1', '0', 'home', '3', 'paper-plane-o', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2016-07-11 17:27:26', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2016-07-11 19:59:38');
INSERT INTO `core_limit` VALUES ('6073c79d4d0cf92372f6bb65ae43eec2', '056b6f23abecbfd2a3caa4ec65f3980a', '3892bfe623082aa570471b6cf177f45e', '启用', '2', '0', 'wzgl/article/article/open', '3', 'check', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-13 11:39:02', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-18 13:42:34');
INSERT INTO `core_limit` VALUES ('6092bfcea87c613808e995bc41d35ab8', '056b6f23abecbfd2a3caa4ec65f3980a', '3892bfe623082aa570471b6cf177f45e', '禁用', '2', '0', 'wzgl/article/article/close', '4', 'times', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-13 11:39:47', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-18 13:42:46');
INSERT INTO `core_limit` VALUES ('6366c052bca1b4b11cc03e0c490bce8f', 'dedcdd22ed3d0632609cd1fd47c2d239', 'c461f41655d29e14f79e3a439260cb27', '自我评测', '1', '0', 'jspc/zwpc/zwpc', '1', 'male', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-19 09:57:01', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-27 13:15:44');
INSERT INTO `core_limit` VALUES ('64b29bc54416c87a24063067b0bf4824', 'ad1ea16b08345f81eef8103efc1ba3cc', '865f38e822e157fc2719b1933930ec03', '新增', '2', '0', 'tdjs/tdjsVideo/video/add', '1', 'plus', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-25 17:20:26', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-25 17:25:54');
INSERT INTO `core_limit` VALUES ('654b90ca9be11323f69ba28045e7454d', '3e430f69d2c3cb4968424be37f2b197c', '865f38e822e157fc2719b1933930ec03', '编辑', '2', '0', 'tdjs/tdjsArticle/article/edit', '2', 'pencil-square-o', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-25 16:35:57', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-25 16:35:57');
INSERT INTO `core_limit` VALUES ('687aad487e14da5ca70fbe7c996ec3c1', 'b139c82efd767c351650267ae400bba1', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '草稿箱', '1', '0', 'core/mail/draft', '2', 'edit', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-06 11:36:57');
INSERT INTO `core_limit` VALUES ('69ec0e48aa4dbe48ab8950cfa3ad53cc', '712b3978fd64ab2c6ea2e0cb6da40404', '3892bfe623082aa570471b6cf177f45e', '编辑', '2', '0', 'wzgl/tag/tag/edit', '2', 'edit', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-14 17:13:34', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-14 17:15:00');
INSERT INTO `core_limit` VALUES ('6b87e91df09bef4272ab4cb63303c16e', '8654ac15d6d46ead928e6f98cf37d7c1', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '删除', '2', '0', 'core/system/system/delete', '4', 'trash-o', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-07 13:09:01');
INSERT INTO `core_limit` VALUES ('6c5e4490c650d6884d1e8fa905358d85', 'a8046c145134645c58398722ecd25e5f', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '新增', '2', '0', 'core/task/task/add', '2', 'plus', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-07 13:15:42');
INSERT INTO `core_limit` VALUES ('6d535bbceee9ddc6d93109cb6f1d0243', '504960bfa845a54b383a74d010ab4e12', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '清空', '2', '0', 'core/log/sql_log/clear', '1', 'trash-o', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-10-27 16:01:24', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-10-27 16:03:27');
INSERT INTO `core_limit` VALUES ('6ebad90faed981e0ca76131dd69a034a', 'b97193fb7871151e17775181b181e3a2', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '公告平台', '1', '0', 'home', '4', 'bell-o', null, 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00');
INSERT INTO `core_limit` VALUES ('702dec278b1d43401237d892dc097532', 'e4738d2a3ce1f321e4d74b3607a8666b', '3892bfe623082aa570471b6cf177f45e', '展示', '2', '0', 'wzgl/picture/picture/show', '3', 'check', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-26 14:25:32', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-26 14:25:32');
INSERT INTO `core_limit` VALUES ('7057857f3bbb47b7cbb70fc3c0f9fe54', '9101e48e6097e5772b7c73ae1931be4e', '865f38e822e157fc2719b1933930ec03', '删除', '2', '0', 'tdjs/tdjsAlbum/album/delete', '2', 'trash-o', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-28 10:37:43', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-28 11:11:39');
INSERT INTO `core_limit` VALUES ('712b3978fd64ab2c6ea2e0cb6da40404', 'b5d40cd48bdb1a3877426c0c842144b1', '3892bfe623082aa570471b6cf177f45e', '标签管理', '1', '0', 'wzgl/tag/tag', '2', 'tag', '标签管理', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2016-07-12 20:32:22', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-14 17:10:48');
INSERT INTO `core_limit` VALUES ('7130f3161a59d7000cdfc37117fe756c', '5c228adffe2439eaf7f95e4d2e702bf0', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'JS编译器', '1', '0', 'core/tools/js_compile', '2', 'shield', null, 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00');
INSERT INTO `core_limit` VALUES ('73d9d0718aa83e5d374ae4226a6eea3a', '50bdd77ddb05ccb5201506236c80a412', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '删除', '2', '0', 'core/system/user/delete', '4', 'trash-o', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-07 13:12:41');
INSERT INTO `core_limit` VALUES ('7697a17f9291534109351f7e41fd26cd', 'b40cd5be82186ce3f5c21c3c966b35c9', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '部门管理', '1', '0', 'core/system/dept', '6', 'building', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-05 13:51:48', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-05 13:51:48');
INSERT INTO `core_limit` VALUES ('785eb202b652ef774ec4b3cce435024f', '7b63bbce0d9b93b106551c84dda6f126', '3892bfe623082aa570471b6cf177f45e', '回复', '2', '0', 'wzgl/email/email/edit', '2', 'edit', '', '240b59bf433b7701b58ba7adb63bfde0', '2016-11-10 09:38:57', '240b59bf433b7701b58ba7adb63bfde0', '2016-11-10 09:38:57');
INSERT INTO `core_limit` VALUES ('7b5a2f2fdff3edf7a07311e9eb6784d1', '5a8ffd5083119437ef6f526a9a14a55c', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '新增', '2', '0', 'core/team/add', '1', 'plus', '新增团队', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-11 18:57:40', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-12 10:00:10');
INSERT INTO `core_limit` VALUES ('7b63bbce0d9b93b106551c84dda6f126', 'b5d40cd48bdb1a3877426c0c842144b1', '3892bfe623082aa570471b6cf177f45e', '园长邮箱', '1', '0', 'wzgl/email/email', '8', 'envelope', '', '240b59bf433b7701b58ba7adb63bfde0', '2016-11-10 09:36:27', '240b59bf433b7701b58ba7adb63bfde0', '2016-11-10 09:36:46');
INSERT INTO `core_limit` VALUES ('7c0f35a345f293b46956338e5d47a6f6', '7b63bbce0d9b93b106551c84dda6f126', '3892bfe623082aa570471b6cf177f45e', '删除', '2', '0', 'wzgl/email/email/delete', '3', 'trash-o', '', '240b59bf433b7701b58ba7adb63bfde0', '2016-11-10 13:32:34', '240b59bf433b7701b58ba7adb63bfde0', '2016-11-10 13:33:29');
INSERT INTO `core_limit` VALUES ('7c217df8296186ea624067729beec477', '056b6f23abecbfd2a3caa4ec65f3980a', '3892bfe623082aa570471b6cf177f45e', '删除', '2', '0', 'wzgl/article/article/delete', '5', 'trash-o', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-13 11:40:31', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-18 13:43:02');
INSERT INTO `core_limit` VALUES ('7d1e47fb9e291f53dc8a3f29eab92334', '8654ac15d6d46ead928e6f98cf37d7c1', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '新增', '2', '0', 'core/system/system/add', '2', 'plus', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-07 13:08:35');
INSERT INTO `core_limit` VALUES ('7defa9d4a5c634d5923017c8ac1063b0', '056b6f23abecbfd2a3caa4ec65f3980a', '3892bfe623082aa570471b6cf177f45e', '新增', '2', '0', 'wzgl/article/article/add', '1', 'plus', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-13 11:36:45', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-18 13:42:11');
INSERT INTO `core_limit` VALUES ('7fdc670ebe6f1c27b0ca5cfd7594f225', '9e9b10840b4c1e4b314f5feeadba3deb', '865f38e822e157fc2719b1933930ec03', '基础管理', '1', '0', 'channel', '1', 'book', '团队建设基础管理', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-25 11:19:03', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-25 11:20:49');
INSERT INTO `core_limit` VALUES ('8654ac15d6d46ead928e6f98cf37d7c1', 'b40cd5be82186ce3f5c21c3c966b35c9', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '系统管理', '1', '0', 'core/system/system', '2', 'delicious', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-12 10:16:33');
INSERT INTO `core_limit` VALUES ('87399d83403f9acabae4899a258ec426', 'b139c82efd767c351650267ae400bba1', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '收件箱', '1', '0', 'core/mail/receive', '1', 'envelope-o', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-06 11:36:50');
INSERT INTO `core_limit` VALUES ('88d11bda15cb949ee440eb26bb9bb40e', '1302b6b76e5610c0c951e5b029bd1d92', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '编辑', '2', '0', 'core/announcement/announcement/*/edit', '3', 'edit', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-07 13:16:33');
INSERT INTO `core_limit` VALUES ('8e2abecd92ee46900ac56d433d3c25d7', 'ad1ea16b08345f81eef8103efc1ba3cc', '865f38e822e157fc2719b1933930ec03', '编辑', '2', '0', 'tdjs/tdjsVideo/video/edit', '2', 'pencil-square-o', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-25 17:20:58', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-25 17:26:05');
INSERT INTO `core_limit` VALUES ('8fd822aa9ec2c73433e450a467e00bed', '5c228adffe2439eaf7f95e4d2e702bf0', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '全局监控', '1', '0', 'core/tools/monitoring', '1', 'terminal', null, 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00');
INSERT INTO `core_limit` VALUES ('903fe4a10e97d297ac2f5f5059e9637e', 'b5d40cd48bdb1a3877426c0c842144b1', '3892bfe623082aa570471b6cf177f45e', '模板管理', '1', '0', 'wzgl/template/template', '3', 'file-text-o', '模板管理', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2016-07-12 20:34:03', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-18 15:24:02');
INSERT INTO `core_limit` VALUES ('9101e48e6097e5772b7c73ae1931be4e', '7fdc670ebe6f1c27b0ca5cfd7594f225', '865f38e822e157fc2719b1933930ec03', '相册管理', '1', '0', 'tdjs/tdjsAlbum/album', '5', 'folder', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-28 10:35:58', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-28 10:36:29');
INSERT INTO `core_limit` VALUES ('91e5133e19f1434321987f48d04a0578', '2836bdfe266dae1f801b9b96fd3e6048', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '删除', '2', '0', 'core/system/role/delete', '4', 'trash-o', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-07 13:13:46');
INSERT INTO `core_limit` VALUES ('925394b14891fee1197d678becc4bc6c', 'e4e9828177cde11540ce3cfd47c275dd', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '删除', '2', '0', 'core/system/attr/delete', '4', 'trash-o', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-07 13:09:32');
INSERT INTO `core_limit` VALUES ('97e14f2fb96bf4602f7c0affe032ddad', '7fdc670ebe6f1c27b0ca5cfd7594f225', '865f38e822e157fc2719b1933930ec03', '栏目管理', '1', '0', 'tdjs/tdjsChannel/channel', '1', 'navicon', '栏目管理', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-25 11:21:34', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-25 15:08:52');
INSERT INTO `core_limit` VALUES ('9a021bf9190b1d4db58c96dcd3dea232', '2e246b2d9e556b3b1a95f677835216fb', '3892bfe623082aa570471b6cf177f45e', '新增', '2', '0', 'wzgl/video/add', '2', 'plus', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-21 11:45:23', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-21 14:52:33');
INSERT INTO `core_limit` VALUES ('9c1762ac7773870b00e27308320a8987', 'e8240eae240368d3b81344acf5ef06ec', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '删除码表信息', '2', '0', 'core/system/code_table/code_table_type/delete', '4', 'trash-o', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-07 13:10:33');
INSERT INTO `core_limit` VALUES ('9c31fe252d7dd78bbb2c95b510f5e259', 'a8046c145134645c58398722ecd25e5f', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '删除', '2', '0', 'core/task/task/delete', '6', 'trash-o', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-07 13:16:11');
INSERT INTO `core_limit` VALUES ('9e9b10840b4c1e4b314f5feeadba3deb', 'top', '865f38e822e157fc2719b1933930ec03', '团队建设系统', '1', '0', 'home', '5', 'users', '团队建设系统', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-25 10:52:07', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-25 10:54:13');
INSERT INTO `core_limit` VALUES ('a0c15c78b22f95e1bc0a4c259b0c6b9b', '056b6f23abecbfd2a3caa4ec65f3980a', '3892bfe623082aa570471b6cf177f45e', '编辑', '2', '0', 'wzgl/article/article/*/edit', '2', 'edit', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-13 11:37:55', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-18 13:42:23');
INSERT INTO `core_limit` VALUES ('a115729eb9b27071332d7978ff9b0e86', 'f559d42f2eff60ae772cfa1f6fd4e26b', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '强制下线', '2', '0', 'core/system/online_user/force', '2', 'times-circle', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-07 13:14:54');
INSERT INTO `core_limit` VALUES ('a1315042ce37af5c6c3539219bb038c3', '056b6f23abecbfd2a3caa4ec65f3980a', '3892bfe623082aa570471b6cf177f45e', '编辑文章相册', '2', '0', 'wzgl/article/article/-/editArtAlbum', '7', 'pencil', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-08-09 17:55:53', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-08-09 18:20:55');
INSERT INTO `core_limit` VALUES ('a3a004e77df633828adc3f461036b59e', 'dedcdd22ed3d0632609cd1fd47c2d239', 'c461f41655d29e14f79e3a439260cb27', '统计', '1', '0', 'jspc/count/count', '4', 'bar-chart-o', '统计', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-08-03 16:25:18', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-08-03 16:25:18');
INSERT INTO `core_limit` VALUES ('a4b28e926583ed3b3977793d8ece5e54', 'b40cd5be82186ce3f5c21c3c966b35c9', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '功能管理', '1', '0', 'core/system/limit', '3', 'cubes', null, 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00');
INSERT INTO `core_limit` VALUES ('a70f6e2eb5f2c8a2b08db95a081b43f9', 'b139c82efd767c351650267ae400bba1', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '发件箱', '1', '0', 'core/mail/send', '3', 'paper-plane-o', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-06 11:37:03');
INSERT INTO `core_limit` VALUES ('a75ecb72da509e641b4d45b3628f446a', 'ad1ea16b08345f81eef8103efc1ba3cc', '865f38e822e157fc2719b1933930ec03', '删除', '2', '0', 'tdjs/tdjsVideo/video/delete', '3', 'trash-o', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-25 17:21:59', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-29 09:56:44');
INSERT INTO `core_limit` VALUES ('a8046c145134645c58398722ecd25e5f', 'acfba8514ef06c1427194af2932dcd02', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '任务管理', '1', '0', 'core/task/task', '1', 'tachometer', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-07 09:14:13');
INSERT INTO `core_limit` VALUES ('a82c9d98607fb4d0ed2d51812552da3c', 'e8240eae240368d3b81344acf5ef06ec', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '编辑码表信息', '2', '0', 'core/system/code_table/code_table_type/*/edit', '3', 'edit', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-07 13:10:25');
INSERT INTO `core_limit` VALUES ('a97760a6befc14da7178295e3fa8e5ca', '5a8ffd5083119437ef6f526a9a14a55c', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '删除', '2', '0', 'core/team/delete', '3', 'trash-o', '删除团队', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-11 19:09:35', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-12 10:00:41');
INSERT INTO `core_limit` VALUES ('ac7ed035ed7bb566871850f2fd568713', 'a4b28e926583ed3b3977793d8ece5e54', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '编辑', '2', '0', 'core/system/limit/*/edit', '3', 'edit', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-07 13:12:07');
INSERT INTO `core_limit` VALUES ('acfba8514ef06c1427194af2932dcd02', 'b97193fb7871151e17775181b181e3a2', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '任务管理', '1', '0', 'home', '5', 'tachometer', null, 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00');
INSERT INTO `core_limit` VALUES ('ad1ea16b08345f81eef8103efc1ba3cc', '7fdc670ebe6f1c27b0ca5cfd7594f225', '865f38e822e157fc2719b1933930ec03', '视频管理', '1', '0', 'tdjs/tdjsVideo/video', '3', 'video-camera', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-25 17:16:04', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-26 17:16:39');
INSERT INTO `core_limit` VALUES ('aea9385736876cf8225f9526cc9c855a', '1302b6b76e5610c0c951e5b029bd1d92', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '启用', '2', '0', 'core/announcement/announcement/open', '4', 'check', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-07 13:16:40');
INSERT INTO `core_limit` VALUES ('aed40f44f2ce7d1150f544fdaf6b3f6d', '2836bdfe266dae1f801b9b96fd3e6048', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '配置功能', '2', '0', 'core/system/role/*/set_limit', '5', 'cubes', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-07 13:14:09');
INSERT INTO `core_limit` VALUES ('b139c82efd767c351650267ae400bba1', 'b97193fb7871151e17775181b181e3a2', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '邮件系统', '1', '0', 'home', '2', 'envelope-o', null, 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00');
INSERT INTO `core_limit` VALUES ('b238b39b26167b16bd085fb2b9a17138', '50bdd77ddb05ccb5201506236c80a412', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '重置密码', '2', '0', 'core/system/user/reset_password', '6', 'key', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-07 13:13:23');
INSERT INTO `core_limit` VALUES ('b40cd5be82186ce3f5c21c3c966b35c9', 'b97193fb7871151e17775181b181e3a2', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '基础管理', '1', '0', 'home', '0', 'gears', null, 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00');
INSERT INTO `core_limit` VALUES ('b4c745bf441218c327d524cde7034c4d', '7697a17f9291534109351f7e41fd26cd', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '删除', '2', '0', 'core/system/dept/delete', '3', 'trash-o', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-05 13:53:14', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-07 13:15:26');
INSERT INTO `core_limit` VALUES ('b5d40cd48bdb1a3877426c0c842144b1', '5e912d9e49ee85d4b2e46be5608ffc66', '3892bfe623082aa570471b6cf177f45e', '基础管理', '1', '0', 'home', '2', 'book', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2016-07-11 20:36:34', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2016-07-11 20:36:34');
INSERT INTO `core_limit` VALUES ('b97193fb7871151e17775181b181e3a2', 'top', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '系统管理', '1', '0', 'core/base/home/core', '0', 'building-o', null, 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00');
INSERT INTO `core_limit` VALUES ('bc059cfcdf04aa858b525052530f2c37', '3eab677934e8570a8e2a9db1a62d544e', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '图片服务器', '1', '0', 'core/server/image', '1', 'image', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-05 14:25:16');
INSERT INTO `core_limit` VALUES ('bd1a677dc6360bfb1d11cabe4162c1ed', '5c228adffe2439eaf7f95e4d2e702bf0', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '网页截图', '1', '0', 'core/tools/screenshot', '3', 'camera', '网页截图', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-10-19 09:45:48', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-10-19 09:45:48');
INSERT INTO `core_limit` VALUES ('bd3efd01d2331b9b65c2c4a6a230c3ca', '3e430f69d2c3cb4968424be37f2b197c', '865f38e822e157fc2719b1933930ec03', '禁用', '2', '0', 'tdjs/tdjsArticle/article/close', '5', 'times', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-25 16:38:20', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-25 16:38:20');
INSERT INTO `core_limit` VALUES ('c0121e129ab8eaf1519e74e04494e8ed', '1302b6b76e5610c0c951e5b029bd1d92', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '禁用', '2', '0', 'core/announcement/announcement/close', '5', 'times', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-07 13:16:47');
INSERT INTO `core_limit` VALUES ('c10e4f8969283c62bedb1c045b1ac95a', '97e14f2fb96bf4602f7c0affe032ddad', '865f38e822e157fc2719b1933930ec03', '新增', '2', '0', 'tdjs/channel/channel/add', '1', 'plus', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-25 11:23:00', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-25 11:23:00');
INSERT INTO `core_limit` VALUES ('c2a9b6eb77f6b2190fae91dd55b8dda5', 'b139c82efd767c351650267ae400bba1', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '已删除邮件', '1', '0', 'core/mail/delete', '4', 'trash-o', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-06 11:37:10');
INSERT INTO `core_limit` VALUES ('c49930b80272d2bbf70d6c6796715729', '2d0bce209e985a146241920258324c5c', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '清空', '2', '0', 'core/log/login_log/clear', '1', 'trash-o', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-10-27 16:00:18', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-10-27 16:02:34');
INSERT INTO `core_limit` VALUES ('c6b3f6d65c2e010e46229eb096e4e1a2', 'e4e9828177cde11540ce3cfd47c275dd', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '配置默认快捷方式', '2', '0', 'core/system/attr/set_default_shortcut_limit', '5', 'magic', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2016-05-10 14:42:50', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2016-05-10 14:42:50');
INSERT INTO `core_limit` VALUES ('c76e3be3aabcf384a6a69618ab74aa60', 'b5d40cd48bdb1a3877426c0c842144b1', '3892bfe623082aa570471b6cf177f45e', '更新缓存', '1', '1', 'wzgl/reloadCash/reloadCash', '9', 'refresh', '', '240b59bf433b7701b58ba7adb63bfde0', '2016-08-16 14:16:17', '240b59bf433b7701b58ba7adb63bfde0', '2016-08-16 16:16:42');
INSERT INTO `core_limit` VALUES ('c809c922f09a07767c62c761f0809449', 'dedcdd22ed3d0632609cd1fd47c2d239', 'c461f41655d29e14f79e3a439260cb27', '专家评测', '1', '0', 'jspc/zjpc/zjpc', '2', 'legal', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-19 09:58:29', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-21 11:50:45');
INSERT INTO `core_limit` VALUES ('c9f5f333da7c50b369e5df912ac6e650', '2e246b2d9e556b3b1a95f677835216fb', '3892bfe623082aa570471b6cf177f45e', '编辑', '2', '0', 'wzgl/video/video/*/edit', '3', 'edit', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-21 11:46:33', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-21 14:54:43');
INSERT INTO `core_limit` VALUES ('cb2bfab17d96e7caf7af2fdf243fc37b', 'e4738d2a3ce1f321e4d74b3607a8666b', '3892bfe623082aa570471b6cf177f45e', '删除', '2', '0', 'wzgl/picture/picture/delete', '5', 'trash-o', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-26 14:26:32', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-27 18:51:14');
INSERT INTO `core_limit` VALUES ('cba4765d9f9a156b5546c36f4d660a60', '056b6f23abecbfd2a3caa4ec65f3980a', '3892bfe623082aa570471b6cf177f45e', '打开文章相册', '2', '0', 'wzgl/article/article/-/openArtAlbum', '6', 'file-photo-o', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-08-09 17:54:54', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-08-09 18:20:30');
INSERT INTO `core_limit` VALUES ('cc3960d5509d71fdb9e33f9aab13ec29', 'fa50872f12e1a3bf75f4548f9ff37461', '865f38e822e157fc2719b1933930ec03', '删除', '2', '0', 'tdjs/tdjsPicture/picture/delete', '2', 'trash-o', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-27 18:36:27', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-28 10:10:34');
INSERT INTO `core_limit` VALUES ('cca80983858e8f32cab3d9122d1d283f', '2836bdfe266dae1f801b9b96fd3e6048', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '编辑', '2', '0', 'core/system/role/*/edit', '3', 'edit', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-07 13:13:41');
INSERT INTO `core_limit` VALUES ('d17ff58b50f92049077729e6414f3da6', 'e4e9828177cde11540ce3cfd47c275dd', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '新增', '2', '0', 'core/system/attr/add', '2', 'plus', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-07 13:09:17');
INSERT INTO `core_limit` VALUES ('d3e851117e79b26af40c89bc7c02abe3', '2e246b2d9e556b3b1a95f677835216fb', '3892bfe623082aa570471b6cf177f45e', '启用', '2', '0', 'wzgl/video/video/open', '4', 'check', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-21 11:51:45', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-21 14:54:54');
INSERT INTO `core_limit` VALUES ('d464d14392d6413d95eeb9ed87f40499', 'fa50872f12e1a3bf75f4548f9ff37461', '865f38e822e157fc2719b1933930ec03', '编辑', '2', '0', 'tdjs/tdjsPicture/picture/*/edit', '3', 'edit', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-27 18:38:01', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-28 10:10:52');
INSERT INTO `core_limit` VALUES ('d46b922061a88bbd8cd8773ad3066a4f', '3e430f69d2c3cb4968424be37f2b197c', '865f38e822e157fc2719b1933930ec03', '删除', '2', '0', 'tdjs/tdjsArticle/article/delete', '3', 'trash-o', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-25 16:36:57', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-25 16:36:57');
INSERT INTO `core_limit` VALUES ('d67511fb7d40a86cf4cc006e0f82cd8e', 'a8046c145134645c58398722ecd25e5f', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '启用', '2', '0', 'core/task/task/open', '4', 'check', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-07 13:15:57');
INSERT INTO `core_limit` VALUES ('d7bc384e7c541d862ec4e8bdc243f58d', 'e4738d2a3ce1f321e4d74b3607a8666b', '3892bfe623082aa570471b6cf177f45e', '新建', '2', '0', 'wzgl/picture/picture/add', '1', 'plus', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-26 14:23:28', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-26 14:23:28');
INSERT INTO `core_limit` VALUES ('d7d0de61cef4d2da92e0743587d7ec12', 'e8240eae240368d3b81344acf5ef06ec', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '新建码表', '2', '0', 'core/system/code_table/*/code_table/add', '7', 'plus', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-07 13:11:06');
INSERT INTO `core_limit` VALUES ('d87ab53d6932fe8c851dc4ee55dbb159', '2e246b2d9e556b3b1a95f677835216fb', '3892bfe623082aa570471b6cf177f45e', '禁用', '2', '0', 'wzgl/video/video/close', '5', 'times', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-21 11:53:36', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-21 14:57:01');
INSERT INTO `core_limit` VALUES ('d89cb8876ae969a681580a398642a1ed', 'e4738d2a3ce1f321e4d74b3607a8666b', '3892bfe623082aa570471b6cf177f45e', '隐藏', '2', '0', 'wzgl/picture/picture/hide', '4', 'times', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-26 14:25:52', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-26 14:25:52');
INSERT INTO `core_limit` VALUES ('da627fa72362bc8ca2713a7b95356bd7', '712b3978fd64ab2c6ea2e0cb6da40404', '3892bfe623082aa570471b6cf177f45e', '新增', '2', '0', 'wzgl/tag/tag/add', '1', 'plus', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-14 17:11:45', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-14 17:13:08');
INSERT INTO `core_limit` VALUES ('dafc1c7760b517ee2dc981ade5622843', '712b3978fd64ab2c6ea2e0cb6da40404', '3892bfe623082aa570471b6cf177f45e', '删除', '2', '0', 'wzgl/tag/tag/delet', '3', 'trash-o', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-14 17:14:07', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-14 17:14:43');
INSERT INTO `core_limit` VALUES ('dedcdd22ed3d0632609cd1fd47c2d239', 'top', 'c461f41655d29e14f79e3a439260cb27', '教师评测', '1', '0', 'hello', '4', 'graduation-cap', '教师评测', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-19 09:54:30', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-19 09:54:30');
INSERT INTO `core_limit` VALUES ('df6bf9e702054dbd213fb9fa5cf3b0f5', '2836bdfe266dae1f801b9b96fd3e6048', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '配置用户', '2', '0', 'core/system/role/*/set_user', '7', 'user', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-07 13:14:31');
INSERT INTO `core_limit` VALUES ('e415234e836a99c240753fdc43835b11', 'a4b28e926583ed3b3977793d8ece5e54', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '新增', '2', '0', 'core/system/limit/add', '2', 'plus', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-07 13:11:59');
INSERT INTO `core_limit` VALUES ('e4738d2a3ce1f321e4d74b3607a8666b', 'b5d40cd48bdb1a3877426c0c842144b1', '3892bfe623082aa570471b6cf177f45e', '大图管理', '1', '0', 'wzgl/picture/picture', '6', 'file-image-o', '大图管理', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-20 11:14:23', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-25 13:24:34');
INSERT INTO `core_limit` VALUES ('e4e9828177cde11540ce3cfd47c275dd', 'b40cd5be82186ce3f5c21c3c966b35c9', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '参数管理', '1', '0', 'core/system/attr', '0', 'wrench', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-05 13:38:28');
INSERT INTO `core_limit` VALUES ('e8240eae240368d3b81344acf5ef06ec', 'b40cd5be82186ce3f5c21c3c966b35c9', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '码表管理', '1', '0', 'core/system/code_table', '1', 'table', null, 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00');
INSERT INTO `core_limit` VALUES ('e9a254f512f27173ab276472c41d80af', '2be1f078671d0281c12882126f71bd7a', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '清空', '2', '0', 'core/log/operation_log/clear', '1', 'trash-o', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-10-27 16:01:09', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-10-27 16:02:40');
INSERT INTO `core_limit` VALUES ('ea478d5cab19d0dbc4e7a60d968080b3', 'fa50872f12e1a3bf75f4548f9ff37461', '865f38e822e157fc2719b1933930ec03', '隐藏', '2', '0', 'tdjs/tdjsPicture/picture/hide', '5', 'times', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-27 18:46:35', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-28 10:11:27');
INSERT INTO `core_limit` VALUES ('ec57aeab7b5b3e45596f6fd4fa3bb41f', '9101e48e6097e5772b7c73ae1931be4e', '865f38e822e157fc2719b1933930ec03', '编辑', '2', '0', 'tdjs/tdjsAlbum/album/edit', '3', 'edit', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-28 10:38:58', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-28 11:11:51');
INSERT INTO `core_limit` VALUES ('f0d4ccec17a09e3e889a4570eb88dcea', '2e246b2d9e556b3b1a95f677835216fb', '3892bfe623082aa570471b6cf177f45e', '删除', '2', '0', 'wzgl/video/video/delete', '6', 'trash-o', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-21 11:54:20', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-21 14:55:12');
INSERT INTO `core_limit` VALUES ('f1b28d59c0debb32e923f86e0c424f5e', '3e430f69d2c3cb4968424be37f2b197c', '865f38e822e157fc2719b1933930ec03', '新增', '2', '0', 'tdjs/tdjsArticle/article/add', '1', 'plus', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-25 16:34:48', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-25 16:35:12');
INSERT INTO `core_limit` VALUES ('f52d86220afb98a242f42b4c22b44ad6', 'e4e9828177cde11540ce3cfd47c275dd', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '编辑', '2', '0', 'core/system/attr/*/edit', '3', 'edit', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-07 13:09:26');
INSERT INTO `core_limit` VALUES ('f559d42f2eff60ae772cfa1f6fd4e26b', 'b40cd5be82186ce3f5c21c3c966b35c9', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '在线用户管理', '1', '0', 'core/system/online_user', '9', 'male', null, 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00');
INSERT INTO `core_limit` VALUES ('f5b4239439cca06a8081d60ab8d25160', 'fa50872f12e1a3bf75f4548f9ff37461', '865f38e822e157fc2719b1933930ec03', '展示', '2', '0', 'tdjs/tdjsPicture/picture/show', '4', 'check', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-27 18:45:30', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-28 10:11:06');
INSERT INTO `core_limit` VALUES ('f7345fb4e96dfcff904d2e7248fa1b2f', '7b63bbce0d9b93b106551c84dda6f126', '3892bfe623082aa570471b6cf177f45e', '新增', '2', '0', 'wzgl/email/email/add', '1', 'plus', '', '240b59bf433b7701b58ba7adb63bfde0', '2016-11-10 09:37:35', '240b59bf433b7701b58ba7adb63bfde0', '2016-11-10 09:37:35');
INSERT INTO `core_limit` VALUES ('f897610c66b4ae623a38ed76b733f50e', '50bdd77ddb05ccb5201506236c80a412', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '配置角色', '2', '0', 'core/system/user/*/set_role', '5', 'users', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-07 13:13:04');
INSERT INTO `core_limit` VALUES ('fa50872f12e1a3bf75f4548f9ff37461', '7fdc670ebe6f1c27b0ca5cfd7594f225', '865f38e822e157fc2719b1933930ec03', '大图管理', '1', '0', 'tdjs/tdjsPicture/picture', '4', 'file-photo-o', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-27 18:34:28', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-28 10:09:59');
INSERT INTO `core_limit` VALUES ('fb29ad540140f51ac81d6ad25ad5cab5', 'c809c922f09a07767c62c761f0809449', 'c461f41655d29e14f79e3a439260cb27', '评审', '2', '0', 'jspc/zjpc/zjpc/review', '1', 'reorder', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-22 16:30:03', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-22 16:30:56');
INSERT INTO `core_limit` VALUES ('fd6bb3364f37c14f3abfe739a2f1c130', '1302b6b76e5610c0c951e5b029bd1d92', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '新增', '2', '0', 'core/announcement/announcement/add', '2', 'plus', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-07 13:16:26');
INSERT INTO `core_limit` VALUES ('ff3a4bc9819caf4211102d33a76c2b3e', '50bdd77ddb05ccb5201506236c80a412', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '配置快捷方式', '2', '0', 'core/system/user/*/set_user_shortcut_limit', '8', 'magic', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2016-05-10 13:56:26', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2016-05-10 13:56:51');
INSERT INTO `core_limit` VALUES ('ff56a3dd1f1af6a5df40e0be96dccdca', '6ebad90faed981e0ca76131dd69a034a', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '公告查看', '1', '0', 'core/announcement/view_announcement', '2', 'eye', null, 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00');

-- ----------------------------
-- Table structure for core_limit_role
-- ----------------------------
DROP TABLE IF EXISTS `core_limit_role`;
CREATE TABLE `core_limit_role` (
  `role_id` varchar(40) NOT NULL COMMENT '角色编号',
  `limit_id` varchar(40) NOT NULL COMMENT '功能编号',
  PRIMARY KEY (`role_id`,`limit_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='功能角色关系表';

-- ----------------------------
-- Records of core_limit_role
-- ----------------------------
INSERT INTO `core_limit_role` VALUES ('168c03dccce36563e4ceb3f58f84f117', '0bdf4ddbfc838992f9a133346275b46d');
INSERT INTO `core_limit_role` VALUES ('168c03dccce36563e4ceb3f58f84f117', '0f81dd9d7dc2dcc57c35b5f8aae1cfe4');
INSERT INTO `core_limit_role` VALUES ('168c03dccce36563e4ceb3f58f84f117', '199dcabe8410a81e2579c4faf97da1f6');
INSERT INTO `core_limit_role` VALUES ('168c03dccce36563e4ceb3f58f84f117', '363c16bc637131d080e28935ace11ff1');
INSERT INTO `core_limit_role` VALUES ('168c03dccce36563e4ceb3f58f84f117', '3e430f69d2c3cb4968424be37f2b197c');
INSERT INTO `core_limit_role` VALUES ('168c03dccce36563e4ceb3f58f84f117', '512250f750d5106a84ea967598d67917');
INSERT INTO `core_limit_role` VALUES ('168c03dccce36563e4ceb3f58f84f117', '64b29bc54416c87a24063067b0bf4824');
INSERT INTO `core_limit_role` VALUES ('168c03dccce36563e4ceb3f58f84f117', '654b90ca9be11323f69ba28045e7454d');
INSERT INTO `core_limit_role` VALUES ('168c03dccce36563e4ceb3f58f84f117', '7057857f3bbb47b7cbb70fc3c0f9fe54');
INSERT INTO `core_limit_role` VALUES ('168c03dccce36563e4ceb3f58f84f117', '7fdc670ebe6f1c27b0ca5cfd7594f225');
INSERT INTO `core_limit_role` VALUES ('168c03dccce36563e4ceb3f58f84f117', '8e2abecd92ee46900ac56d433d3c25d7');
INSERT INTO `core_limit_role` VALUES ('168c03dccce36563e4ceb3f58f84f117', '9101e48e6097e5772b7c73ae1931be4e');
INSERT INTO `core_limit_role` VALUES ('168c03dccce36563e4ceb3f58f84f117', '9e9b10840b4c1e4b314f5feeadba3deb');
INSERT INTO `core_limit_role` VALUES ('168c03dccce36563e4ceb3f58f84f117', 'a75ecb72da509e641b4d45b3628f446a');
INSERT INTO `core_limit_role` VALUES ('168c03dccce36563e4ceb3f58f84f117', 'ad1ea16b08345f81eef8103efc1ba3cc');
INSERT INTO `core_limit_role` VALUES ('168c03dccce36563e4ceb3f58f84f117', 'bd3efd01d2331b9b65c2c4a6a230c3ca');
INSERT INTO `core_limit_role` VALUES ('168c03dccce36563e4ceb3f58f84f117', 'cc3960d5509d71fdb9e33f9aab13ec29');
INSERT INTO `core_limit_role` VALUES ('168c03dccce36563e4ceb3f58f84f117', 'd464d14392d6413d95eeb9ed87f40499');
INSERT INTO `core_limit_role` VALUES ('168c03dccce36563e4ceb3f58f84f117', 'd46b922061a88bbd8cd8773ad3066a4f');
INSERT INTO `core_limit_role` VALUES ('168c03dccce36563e4ceb3f58f84f117', 'ea478d5cab19d0dbc4e7a60d968080b3');
INSERT INTO `core_limit_role` VALUES ('168c03dccce36563e4ceb3f58f84f117', 'ec57aeab7b5b3e45596f6fd4fa3bb41f');
INSERT INTO `core_limit_role` VALUES ('168c03dccce36563e4ceb3f58f84f117', 'f1b28d59c0debb32e923f86e0c424f5e');
INSERT INTO `core_limit_role` VALUES ('168c03dccce36563e4ceb3f58f84f117', 'f5b4239439cca06a8081d60ab8d25160');
INSERT INTO `core_limit_role` VALUES ('168c03dccce36563e4ceb3f58f84f117', 'fa50872f12e1a3bf75f4548f9ff37461');
INSERT INTO `core_limit_role` VALUES ('30b82933ec84c43047a295dee19b2280', '056b6f23abecbfd2a3caa4ec65f3980a');
INSERT INTO `core_limit_role` VALUES ('30b82933ec84c43047a295dee19b2280', '0cbc84e12a1a65d3de109b278920d786');
INSERT INTO `core_limit_role` VALUES ('30b82933ec84c43047a295dee19b2280', '0fbc539a9ccd5f1d12c0d2124cf1f54d');
INSERT INTO `core_limit_role` VALUES ('30b82933ec84c43047a295dee19b2280', '10e86db0241d729a5cc2ebd59e2b040b');
INSERT INTO `core_limit_role` VALUES ('30b82933ec84c43047a295dee19b2280', '11482385e77e8e9ccaa228ee3eb2c0bf');
INSERT INTO `core_limit_role` VALUES ('30b82933ec84c43047a295dee19b2280', '1b302e17fd26a3d2a5a7a95ca78037d5');
INSERT INTO `core_limit_role` VALUES ('30b82933ec84c43047a295dee19b2280', '1c2cf7be651103c6aed430adb06a5b3a');
INSERT INTO `core_limit_role` VALUES ('30b82933ec84c43047a295dee19b2280', '28fdd7b7b0369613b14d7d603e8b5353');
INSERT INTO `core_limit_role` VALUES ('30b82933ec84c43047a295dee19b2280', '2e246b2d9e556b3b1a95f677835216fb');
INSERT INTO `core_limit_role` VALUES ('30b82933ec84c43047a295dee19b2280', '4a1413240c86aa8a34a8c5228131cfa0');
INSERT INTO `core_limit_role` VALUES ('30b82933ec84c43047a295dee19b2280', '5e912d9e49ee85d4b2e46be5608ffc66');
INSERT INTO `core_limit_role` VALUES ('30b82933ec84c43047a295dee19b2280', '6073c79d4d0cf92372f6bb65ae43eec2');
INSERT INTO `core_limit_role` VALUES ('30b82933ec84c43047a295dee19b2280', '6092bfcea87c613808e995bc41d35ab8');
INSERT INTO `core_limit_role` VALUES ('30b82933ec84c43047a295dee19b2280', '702dec278b1d43401237d892dc097532');
INSERT INTO `core_limit_role` VALUES ('30b82933ec84c43047a295dee19b2280', '785eb202b652ef774ec4b3cce435024f');
INSERT INTO `core_limit_role` VALUES ('30b82933ec84c43047a295dee19b2280', '7b63bbce0d9b93b106551c84dda6f126');
INSERT INTO `core_limit_role` VALUES ('30b82933ec84c43047a295dee19b2280', '7c0f35a345f293b46956338e5d47a6f6');
INSERT INTO `core_limit_role` VALUES ('30b82933ec84c43047a295dee19b2280', '7c217df8296186ea624067729beec477');
INSERT INTO `core_limit_role` VALUES ('30b82933ec84c43047a295dee19b2280', '7defa9d4a5c634d5923017c8ac1063b0');
INSERT INTO `core_limit_role` VALUES ('30b82933ec84c43047a295dee19b2280', '9a021bf9190b1d4db58c96dcd3dea232');
INSERT INTO `core_limit_role` VALUES ('30b82933ec84c43047a295dee19b2280', 'a0c15c78b22f95e1bc0a4c259b0c6b9b');
INSERT INTO `core_limit_role` VALUES ('30b82933ec84c43047a295dee19b2280', 'a1315042ce37af5c6c3539219bb038c3');
INSERT INTO `core_limit_role` VALUES ('30b82933ec84c43047a295dee19b2280', 'b5d40cd48bdb1a3877426c0c842144b1');
INSERT INTO `core_limit_role` VALUES ('30b82933ec84c43047a295dee19b2280', 'c76e3be3aabcf384a6a69618ab74aa60');
INSERT INTO `core_limit_role` VALUES ('30b82933ec84c43047a295dee19b2280', 'c9f5f333da7c50b369e5df912ac6e650');
INSERT INTO `core_limit_role` VALUES ('30b82933ec84c43047a295dee19b2280', 'cb2bfab17d96e7caf7af2fdf243fc37b');
INSERT INTO `core_limit_role` VALUES ('30b82933ec84c43047a295dee19b2280', 'cba4765d9f9a156b5546c36f4d660a60');
INSERT INTO `core_limit_role` VALUES ('30b82933ec84c43047a295dee19b2280', 'd3e851117e79b26af40c89bc7c02abe3');
INSERT INTO `core_limit_role` VALUES ('30b82933ec84c43047a295dee19b2280', 'd7bc384e7c541d862ec4e8bdc243f58d');
INSERT INTO `core_limit_role` VALUES ('30b82933ec84c43047a295dee19b2280', 'd87ab53d6932fe8c851dc4ee55dbb159');
INSERT INTO `core_limit_role` VALUES ('30b82933ec84c43047a295dee19b2280', 'd89cb8876ae969a681580a398642a1ed');
INSERT INTO `core_limit_role` VALUES ('30b82933ec84c43047a295dee19b2280', 'e4738d2a3ce1f321e4d74b3607a8666b');
INSERT INTO `core_limit_role` VALUES ('30b82933ec84c43047a295dee19b2280', 'f0d4ccec17a09e3e889a4570eb88dcea');
INSERT INTO `core_limit_role` VALUES ('30b82933ec84c43047a295dee19b2280', 'f7345fb4e96dfcff904d2e7248fa1b2f');
INSERT INTO `core_limit_role` VALUES ('6ce375e4e4cf6500e78bcf5667dbeb48', '2b3bbaa8c8ff50a42b51e65ea2e0cc18');
INSERT INTO `core_limit_role` VALUES ('6ce375e4e4cf6500e78bcf5667dbeb48', '3b19e8915200e69be5395965ae81ecf8');
INSERT INTO `core_limit_role` VALUES ('6ce375e4e4cf6500e78bcf5667dbeb48', '6366c052bca1b4b11cc03e0c490bce8f');
INSERT INTO `core_limit_role` VALUES ('6ce375e4e4cf6500e78bcf5667dbeb48', 'a3a004e77df633828adc3f461036b59e');
INSERT INTO `core_limit_role` VALUES ('6ce375e4e4cf6500e78bcf5667dbeb48', 'c809c922f09a07767c62c761f0809449');
INSERT INTO `core_limit_role` VALUES ('6ce375e4e4cf6500e78bcf5667dbeb48', 'dedcdd22ed3d0632609cd1fd47c2d239');
INSERT INTO `core_limit_role` VALUES ('6ce375e4e4cf6500e78bcf5667dbeb48', 'fb29ad540140f51ac81d6ad25ad5cab5');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '000af26bda6a6adf90da9edb4bcf734e');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '0bb86e97a94343bc78d7aed8082b8076');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '0fc14f3064beca390759b608438affef');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '118702e0ed598e0d3a5f376efda42bb0');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '1302b6b76e5610c0c951e5b029bd1d92');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '16cb992e02a44885d2a5d7a684905cb5');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '1a24f4d3d8ae31e4d301bae3a971ab36');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '1de6b94ab9eaa3a71cf23d6dc3486e91');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '1ded55f537c4d4bc8c0324852301b78c');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '21e6337ec810f5ee316b6fe6b8d4511a');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '239286040e7fd22b27bc0eb9e5395ffe');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '263288db3e2a17b15f35d10f4e254fb1');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2836bdfe266dae1f801b9b96fd3e6048');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2926b3eb2cddb8d6a5950c694eff9014');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2be1f078671d0281c12882126f71bd7a');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2d0bce209e985a146241920258324c5c');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '30114aba4c8d49173801b20feeddba3f');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '339e71d24fe19d00b2448fb8d46906a6');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '3eab677934e8570a8e2a9db1a62d544e');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '3f83c6527b78d3034039f6691dc9977c');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '466f5359ae5368ccc0712fce9f57733b');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '504960bfa845a54b383a74d010ab4e12');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '50bdd77ddb05ccb5201506236c80a412');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '538eb737a0f3a40c130799cde03b3681');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '545f282cf0b4c10cd19b9e3ed649f6f0');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '560a3cf481cf0de9eef61babdacc8a8d');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '5a8ffd5083119437ef6f526a9a14a55c');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '5a9399d0fe2d49f39dc6725b63268efa');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '5c228adffe2439eaf7f95e4d2e702bf0');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '687aad487e14da5ca70fbe7c996ec3c1');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '6b87e91df09bef4272ab4cb63303c16e');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '6c5e4490c650d6884d1e8fa905358d85');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '6d535bbceee9ddc6d93109cb6f1d0243');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '6ebad90faed981e0ca76131dd69a034a');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '7130f3161a59d7000cdfc37117fe756c');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '722acbc35931054a518ec94d86f13266');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '73d9d0718aa83e5d374ae4226a6eea3a');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '7454df89813cd388ddc86c0d74d83b53');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '7697a17f9291534109351f7e41fd26cd');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '769f7b5f21303f8503002114bc1f0451');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '7b5a2f2fdff3edf7a07311e9eb6784d1');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '7d1e47fb9e291f53dc8a3f29eab92334');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '8654ac15d6d46ead928e6f98cf37d7c1');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '87399d83403f9acabae4899a258ec426');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '88d11bda15cb949ee440eb26bb9bb40e');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '8fd822aa9ec2c73433e450a467e00bed');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '91e5133e19f1434321987f48d04a0578');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '925394b14891fee1197d678becc4bc6c');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '9420b77b8b9cf46dbf89a54d0ccb8066');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '9c1762ac7773870b00e27308320a8987');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '9c31fe252d7dd78bbb2c95b510f5e259');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'a115729eb9b27071332d7978ff9b0e86');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'a4b28e926583ed3b3977793d8ece5e54');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'a70f6e2eb5f2c8a2b08db95a081b43f9');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'a8046c145134645c58398722ecd25e5f');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'a82c9d98607fb4d0ed2d51812552da3c');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'a97760a6befc14da7178295e3fa8e5ca');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'ac7ed035ed7bb566871850f2fd568713');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'acfba8514ef06c1427194af2932dcd02');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'aea9385736876cf8225f9526cc9c855a');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'aed40f44f2ce7d1150f544fdaf6b3f6d');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'b139c82efd767c351650267ae400bba1');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'b238b39b26167b16bd085fb2b9a17138');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'b40cd5be82186ce3f5c21c3c966b35c9');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'b4c745bf441218c327d524cde7034c4d');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'b97193fb7871151e17775181b181e3a2');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'bc059cfcdf04aa858b525052530f2c37');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'bd1a677dc6360bfb1d11cabe4162c1ed');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'c0121e129ab8eaf1519e74e04494e8ed');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'c2a9b6eb77f6b2190fae91dd55b8dda5');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'c49930b80272d2bbf70d6c6796715729');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'c6b3f6d65c2e010e46229eb096e4e1a2');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'cca80983858e8f32cab3d9122d1d283f');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'd17ff58b50f92049077729e6414f3da6');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'd67511fb7d40a86cf4cc006e0f82cd8e');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'd7d0de61cef4d2da92e0743587d7ec12');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'df6bf9e702054dbd213fb9fa5cf3b0f5');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'e415234e836a99c240753fdc43835b11');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'e4e9828177cde11540ce3cfd47c275dd');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'e8240eae240368d3b81344acf5ef06ec');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'e9a254f512f27173ab276472c41d80af');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'f52d86220afb98a242f42b4c22b44ad6');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'f559d42f2eff60ae772cfa1f6fd4e26b');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'f897610c66b4ae623a38ed76b733f50e');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'fd6bb3364f37c14f3abfe739a2f1c130');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'ff3a4bc9819caf4211102d33a76c2b3e');
INSERT INTO `core_limit_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'ff56a3dd1f1af6a5df40e0be96dccdca');
INSERT INTO `core_limit_role` VALUES ('fab1c3d6ccc1876abd0967e9ab551527', '0bdf4ddbfc838992f9a133346275b46d');
INSERT INTO `core_limit_role` VALUES ('fab1c3d6ccc1876abd0967e9ab551527', '0f81dd9d7dc2dcc57c35b5f8aae1cfe4');
INSERT INTO `core_limit_role` VALUES ('fab1c3d6ccc1876abd0967e9ab551527', '16e0bca5286dcd3e49786b7b20e51483');
INSERT INTO `core_limit_role` VALUES ('fab1c3d6ccc1876abd0967e9ab551527', '199dcabe8410a81e2579c4faf97da1f6');
INSERT INTO `core_limit_role` VALUES ('fab1c3d6ccc1876abd0967e9ab551527', '2fad67e7358cfa59ba1fa48b9f1770c3');
INSERT INTO `core_limit_role` VALUES ('fab1c3d6ccc1876abd0967e9ab551527', '35fb1eeab4bcb82825b63a244c005575');
INSERT INTO `core_limit_role` VALUES ('fab1c3d6ccc1876abd0967e9ab551527', '363c16bc637131d080e28935ace11ff1');
INSERT INTO `core_limit_role` VALUES ('fab1c3d6ccc1876abd0967e9ab551527', '3e430f69d2c3cb4968424be37f2b197c');
INSERT INTO `core_limit_role` VALUES ('fab1c3d6ccc1876abd0967e9ab551527', '48f410e92c4c11693b7c5629926f56da');
INSERT INTO `core_limit_role` VALUES ('fab1c3d6ccc1876abd0967e9ab551527', '512250f750d5106a84ea967598d67917');
INSERT INTO `core_limit_role` VALUES ('fab1c3d6ccc1876abd0967e9ab551527', '64b29bc54416c87a24063067b0bf4824');
INSERT INTO `core_limit_role` VALUES ('fab1c3d6ccc1876abd0967e9ab551527', '654b90ca9be11323f69ba28045e7454d');
INSERT INTO `core_limit_role` VALUES ('fab1c3d6ccc1876abd0967e9ab551527', '7057857f3bbb47b7cbb70fc3c0f9fe54');
INSERT INTO `core_limit_role` VALUES ('fab1c3d6ccc1876abd0967e9ab551527', '7fdc670ebe6f1c27b0ca5cfd7594f225');
INSERT INTO `core_limit_role` VALUES ('fab1c3d6ccc1876abd0967e9ab551527', '8e2abecd92ee46900ac56d433d3c25d7');
INSERT INTO `core_limit_role` VALUES ('fab1c3d6ccc1876abd0967e9ab551527', '9101e48e6097e5772b7c73ae1931be4e');
INSERT INTO `core_limit_role` VALUES ('fab1c3d6ccc1876abd0967e9ab551527', '97e14f2fb96bf4602f7c0affe032ddad');
INSERT INTO `core_limit_role` VALUES ('fab1c3d6ccc1876abd0967e9ab551527', '9e9b10840b4c1e4b314f5feeadba3deb');
INSERT INTO `core_limit_role` VALUES ('fab1c3d6ccc1876abd0967e9ab551527', 'a75ecb72da509e641b4d45b3628f446a');
INSERT INTO `core_limit_role` VALUES ('fab1c3d6ccc1876abd0967e9ab551527', 'ad1ea16b08345f81eef8103efc1ba3cc');
INSERT INTO `core_limit_role` VALUES ('fab1c3d6ccc1876abd0967e9ab551527', 'bd3efd01d2331b9b65c2c4a6a230c3ca');
INSERT INTO `core_limit_role` VALUES ('fab1c3d6ccc1876abd0967e9ab551527', 'c10e4f8969283c62bedb1c045b1ac95a');
INSERT INTO `core_limit_role` VALUES ('fab1c3d6ccc1876abd0967e9ab551527', 'cc3960d5509d71fdb9e33f9aab13ec29');
INSERT INTO `core_limit_role` VALUES ('fab1c3d6ccc1876abd0967e9ab551527', 'd464d14392d6413d95eeb9ed87f40499');
INSERT INTO `core_limit_role` VALUES ('fab1c3d6ccc1876abd0967e9ab551527', 'd46b922061a88bbd8cd8773ad3066a4f');
INSERT INTO `core_limit_role` VALUES ('fab1c3d6ccc1876abd0967e9ab551527', 'ea478d5cab19d0dbc4e7a60d968080b3');
INSERT INTO `core_limit_role` VALUES ('fab1c3d6ccc1876abd0967e9ab551527', 'ec57aeab7b5b3e45596f6fd4fa3bb41f');
INSERT INTO `core_limit_role` VALUES ('fab1c3d6ccc1876abd0967e9ab551527', 'f1b28d59c0debb32e923f86e0c424f5e');
INSERT INTO `core_limit_role` VALUES ('fab1c3d6ccc1876abd0967e9ab551527', 'f5b4239439cca06a8081d60ab8d25160');
INSERT INTO `core_limit_role` VALUES ('fab1c3d6ccc1876abd0967e9ab551527', 'fa50872f12e1a3bf75f4548f9ff37461');

-- ----------------------------
-- Table structure for core_login_log
-- ----------------------------
DROP TABLE IF EXISTS `core_login_log`;
CREATE TABLE `core_login_log` (
  `log_id` varchar(40) NOT NULL COMMENT '日志内码',
  `session_id` varchar(40) NOT NULL COMMENT 'SESSION编号',
  `login_user_id` varchar(40) NOT NULL COMMENT '登录用户内码',
  `login_user_name` varchar(40) NOT NULL COMMENT '登录用户名称',
  `login_user_dept_id` varchar(40) DEFAULT NULL COMMENT '登录用户所属部门',
  `login_user_dept_name` varchar(40) DEFAULT NULL COMMENT '登录用户所属部门名称',
  `login_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '登录时间',
  `ip` varchar(20) NOT NULL COMMENT '登录IP',
  `login_status` varchar(2) NOT NULL COMMENT '登录状态',
  `is_logout` varchar(2) NOT NULL COMMENT '是否登出',
  `logout_type` varchar(2) DEFAULT NULL COMMENT '登出状态',
  `logout_time` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '登出时间',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='登录日志表';

-- ----------------------------
-- Records of core_login_log
-- ----------------------------
INSERT INTO `core_login_log` VALUES ('03dc20d46bd2231673c37553e84ca780', '489C0EF9EF41BC9519C97B3EE626B5E1', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-19 09:41:09', '0:0:0:0:0:0:0:1', '1', '1', '1', '2016-11-19 09:41:09');
INSERT INTO `core_login_log` VALUES ('04ac1c6a334ab07492a7ba8b27dd38ff', 'D1A27387B91846714F6BF9B16B5D589C', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-08 16:47:22', '127.0.0.1', '1', '1', '3', '2016-11-08 16:47:22');
INSERT INTO `core_login_log` VALUES ('0a932fb665e2a0aa1fed4b8d216b9e09', 'A5E53A787A71D2D2DA3FB23FB0E7CDD3', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-25 16:32:12', '0:0:0:0:0:0:0:1', '1', '1', '3', '2016-11-25 16:32:12');
INSERT INTO `core_login_log` VALUES ('10c168a2f10c2b65ee4aa88982c9ef6b', 'EE5EE8B720CD5E7DD14A234F8C8D0833', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-25 16:25:55', '0:0:0:0:0:0:0:1', '1', '1', '3', '2016-11-25 16:25:55');
INSERT INTO `core_login_log` VALUES ('13b509b868b77c1f27ccbffef808e65d', 'F3EEAF5706451B751E2DA193040C2FAC', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-08 09:48:53', '127.0.0.1', '1', '1', '1', '2016-11-08 09:46:36');
INSERT INTO `core_login_log` VALUES ('1c7866c2436301ff89ca5008fb2507f8', '38516774E7AFAAE453F73B64E07073AD', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-11 11:17:02', '127.0.0.1', '1', '1', '3', '2016-11-11 11:17:02');
INSERT INTO `core_login_log` VALUES ('1dfca6e655c4f02e6fb2b59ee6caefea', '917B521F3A4FAA7EFFA27F53ACA74443', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-08 16:08:07', '127.0.0.1', '1', '1', '3', '2016-11-08 16:08:07');
INSERT INTO `core_login_log` VALUES ('204cdb639e6424343814d06cafc67768', '222325CF7C254F8D2DD7DA13B9DA1F65', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-10 13:41:47', '127.0.0.1', '1', '1', '1', '2016-11-10 13:38:41');
INSERT INTO `core_login_log` VALUES ('25240bd8c29ac03e0cd4c80e92573a0c', '7970A3702D0E5E7A09F12B490EF7BE2C', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-10 16:28:36', '127.0.0.1', '1', '1', '2', '2016-11-10 16:28:36');
INSERT INTO `core_login_log` VALUES ('266b27c63b5f99410a5d36ab02463311', 'FA7DCF1A917994793D9DD4A6F4B8561B', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-11 10:46:54', '127.0.0.1', '1', '1', '3', '2016-11-11 10:46:54');
INSERT INTO `core_login_log` VALUES ('2b1447a68ba51c64768193ce713d013b', '7E9617715EF364B3F5FD983E9A2B0865', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-10 15:56:39', '127.0.0.1', '1', '1', '3', '2016-11-10 15:56:39');
INSERT INTO `core_login_log` VALUES ('2bacffb7fb3ea350303f9acf93e5f773', 'BD8A21427CFBEAFB46124929CCB21F43', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-11 10:10:58', '127.0.0.1', '1', '1', '3', '2016-11-11 10:10:58');
INSERT INTO `core_login_log` VALUES ('2ccb1613ff69d5e506430881054201d6', 'FB83BA6E3ED702DAF6B90688BAF89FF0', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-10 17:06:50', '127.0.0.1', '1', '1', '2', '2016-11-10 17:06:50');
INSERT INTO `core_login_log` VALUES ('2fe0af7018b0636a8c05c432e4dcccf4', '127A5585EEC4B05340AA92CF14025291', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-10 17:32:20', '127.0.0.1', '1', '1', '3', '2016-11-10 17:32:20');
INSERT INTO `core_login_log` VALUES ('3e3157efb7eaeae8b630cae6ff1e7e78', 'A4DAAAD259858B92F802CFAEC37FF7E4', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-13 17:14:26', '0:0:0:0:0:0:0:1', '1', '1', '1', '2016-11-13 17:14:26');
INSERT INTO `core_login_log` VALUES ('3f06687ca58991d3fe8321d41ba5cfa1', 'EF04641E7DB6BAFB008CE2A00B959FB5', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-11 14:29:07', '127.0.0.1', '1', '1', '3', '2016-11-11 14:29:07');
INSERT INTO `core_login_log` VALUES ('41fce2a31738e0eb237d4dfea64df2b2', '222325CF7C254F8D2DD7DA13B9DA1F65', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-10 13:55:30', '127.0.0.1', '1', '1', '3', '2016-11-10 13:55:30');
INSERT INTO `core_login_log` VALUES ('43e78cf2cee494c02915579ab58f83af', 'A7DB9642CD3BB917FF6E52DA8AF1524C', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-08 10:52:04', '127.0.0.1', '1', '1', '3', '2016-11-08 10:52:04');
INSERT INTO `core_login_log` VALUES ('4b39209285b0af19bad9639bb8e40bde', 'A7DB9642CD3BB917FF6E52DA8AF1524C', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-08 10:10:07', '127.0.0.1', '1', '1', '1', '2016-11-08 10:07:50');
INSERT INTO `core_login_log` VALUES ('4faaabc22b627ae95169f5f3123f764d', 'A4DAAAD259858B92F802CFAEC37FF7E4', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-13 17:34:17', '0:0:0:0:0:0:0:1', '1', '1', '1', '2016-11-13 17:34:17');
INSERT INTO `core_login_log` VALUES ('50ce37754855543ab379e27cab9c2048', 'BE619F902B7BBBE1B3C2D639D3EB356F', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-10 14:41:18', '127.0.0.1', '1', '1', '3', '2016-11-10 14:41:18');
INSERT INTO `core_login_log` VALUES ('51f3d5ceb28a65f775c24650c917ae5c', '6FC67079B6F857FD996853B4C803B627', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-10 14:50:05', '127.0.0.1', '1', '1', '3', '2016-11-10 14:50:05');
INSERT INTO `core_login_log` VALUES ('51faf28f5a7485569941259fc3ee58d6', 'B88F17886EAE79B43B69502FA332F72C', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-10 09:52:15', '127.0.0.1', '1', '1', '3', '2016-11-10 09:52:15');
INSERT INTO `core_login_log` VALUES ('52a0b5e29f51b3d670827752f73748ca', '72AD18795497EE2A5EF78D0CEBB48F22', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-08 17:49:26', '127.0.0.1', '1', '1', '3', '2016-11-08 17:49:26');
INSERT INTO `core_login_log` VALUES ('5577c2a168a6743054799664fb5b9083', 'D84A135979155A60A153AFB5911258E0', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-10 14:22:18', '127.0.0.1', '1', '1', '3', '2016-11-10 14:22:18');
INSERT INTO `core_login_log` VALUES ('5d2fb32fe9348ae62fdb6b2f0d06f6d6', 'F3EEAF5706451B751E2DA193040C2FAC', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-08 10:00:26', '127.0.0.1', '1', '1', '1', '2016-11-08 09:58:09');
INSERT INTO `core_login_log` VALUES ('5d9cb4148a4e07947f859b07dcda7f16', 'A7DB9642CD3BB917FF6E52DA8AF1524C', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-08 10:05:43', '127.0.0.1', '1', '1', '1', '2016-11-08 10:03:26');
INSERT INTO `core_login_log` VALUES ('5e01a1f340bc738da2f0cbbb1eeecbe7', 'A7DB9642CD3BB917FF6E52DA8AF1524C', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-08 10:02:59', '127.0.0.1', '1', '1', '1', '2016-11-08 10:00:41');
INSERT INTO `core_login_log` VALUES ('5f80268fe10eb2fe81f0d805ea193c32', 'E672ED986239A4EA27F4DBEDBD69C5F2', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-11 13:44:00', '127.0.0.1', '1', '1', '3', '2016-11-11 13:44:00');
INSERT INTO `core_login_log` VALUES ('6125a34d359377a52f04e52337337493', '488419FD5E3EFD5D1D4941B38B17A48B', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-10 14:39:51', '127.0.0.1', '1', '1', '3', '2016-11-10 14:39:51');
INSERT INTO `core_login_log` VALUES ('61d38b2c390e7ac328d423e66300faf0', 'A4DAAAD259858B92F802CFAEC37FF7E4', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-13 17:35:43', '0:0:0:0:0:0:0:1', '1', '1', '1', '2016-11-13 17:35:43');
INSERT INTO `core_login_log` VALUES ('64b4ce1385a5f7d572693a21877c8c27', '3EF1FE274A6A8B68AD12439962F196E2', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-13 17:06:26', '0:0:0:0:0:0:0:1', '1', '1', '3', '2016-11-13 17:06:26');
INSERT INTO `core_login_log` VALUES ('68c653b2807b828fa4b851846bebf7c3', 'A4D941C05670E4ECC676D408C1C20DED', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-11 11:01:57', '127.0.0.1', '1', '1', '3', '2016-11-11 11:01:57');
INSERT INTO `core_login_log` VALUES ('68ca2fbcbd19b85b798b00b4cdfb5462', '6B8C7C072D840340D88B25ECE14CE83E', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-25 16:32:19', '0:0:0:0:0:0:0:1', '1', '0', null, null);
INSERT INTO `core_login_log` VALUES ('72a4f93daf334391cf6cbde561b2c71c', '36BA4A288DAA06749BABC004B49B7AB8', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-11 13:18:22', '127.0.0.1', '1', '1', '3', '2016-11-11 13:18:22');
INSERT INTO `core_login_log` VALUES ('7875cdd1743f4303285cdee18f9668bc', '720C84D63A868DE6BEAD2BA033DC955F', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-25 16:44:41', '0:0:0:0:0:0:0:1', '1', '0', null, null);
INSERT INTO `core_login_log` VALUES ('79a7aea8f5f84bea4a25bbc5aa76d4d8', '5E9F2B5CC314DE00C7EC9C3D5D39C4C4', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-11 10:59:50', '127.0.0.1', '1', '1', '3', '2016-11-11 10:59:50');
INSERT INTO `core_login_log` VALUES ('7ca6efb5b08e58de0783c0c43b95e969', '36D2826A9EF9DC7690C932E9D35AC2CD', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-08 09:44:56', '127.0.0.1', '1', '1', '3', '2016-11-08 09:44:56');
INSERT INTO `core_login_log` VALUES ('81b4e71764861f3eeddd1867b83efaca', '5AEEA7E902459DB155BFFAE0A56BAD99', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-08 13:42:29', '127.0.0.1', '1', '1', '3', '2016-11-08 13:42:29');
INSERT INTO `core_login_log` VALUES ('831ce90cd613bd538ff7fdb6b0c24174', '566F920C5CFB8054C0E631F6C18D5A65', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-11 13:45:34', '127.0.0.1', '1', '1', '3', '2016-11-11 13:45:34');
INSERT INTO `core_login_log` VALUES ('835af5b06aff8e28f4e63b4653881409', '489C0EF9EF41BC9519C97B3EE626B5E1', 'administrator', 'administrator', '-', '未知', '2016-11-19 08:48:08', '0:0:0:0:0:0:0:1', '2', '1', null, null);
INSERT INTO `core_login_log` VALUES ('87e7c63a142fb16ae7827a1d56d9b211', '6338A47B8EFB92E690DE5E3CAD543AD2', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-19 08:36:49', '0:0:0:0:0:0:0:1', '1', '1', '1', '2016-11-19 08:36:49');
INSERT INTO `core_login_log` VALUES ('896b24069aabdc72aa69534a9ca9f688', 'F18BA1ED7C80A6DB174563ECB5B9FF57', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-08 12:38:27', '127.0.0.1', '1', '1', '2', '2016-11-08 12:38:27');
INSERT INTO `core_login_log` VALUES ('89869c2bc171c6258da6b2642e31576a', '6338A47B8EFB92E690DE5E3CAD543AD2', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-19 08:36:20', '0:0:0:0:0:0:0:1', '1', '1', '1', '2016-11-19 08:36:20');
INSERT INTO `core_login_log` VALUES ('89ccd86520478ee078b752ee7a47e4f8', 'DD9FA0CE7A3F39528C223E5F98E70F91', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-11 14:11:25', '127.0.0.1', '1', '1', '3', '2016-11-11 14:11:25');
INSERT INTO `core_login_log` VALUES ('8c8d7c93fee452f87fd8f16ce018213e', 'A43FAAF96C7A60E8CDC516B7B0BEC281', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-10 14:45:49', '127.0.0.1', '1', '1', '3', '2016-11-10 14:45:49');
INSERT INTO `core_login_log` VALUES ('8d36e7c72da7d77798e7fd1258a75a64', '6338A47B8EFB92E690DE5E3CAD543AD2', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-25 14:52:40', '0:0:0:0:0:0:0:1', '1', '1', '3', '2016-11-25 14:52:40');
INSERT INTO `core_login_log` VALUES ('8d44e7f7c50472178220be93d5a2bf37', 'A7DB9642CD3BB917FF6E52DA8AF1524C', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-08 10:06:02', '127.0.0.1', '1', '1', '1', '2016-11-08 10:03:44');
INSERT INTO `core_login_log` VALUES ('90f2d1c46724d7ec1d6739dd589294d6', 'B0257D86673C78063CB0ADB04B6D83EF', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-10 14:00:55', '127.0.0.1', '1', '1', '3', '2016-11-10 14:00:55');
INSERT INTO `core_login_log` VALUES ('910c310bc592baa14201191f2e82c00a', '6338A47B8EFB92E690DE5E3CAD543AD2', 'admin', 'admin', '-', '未知', '2016-11-19 08:33:27', '0:0:0:0:0:0:0:1', '3', '1', null, null);
INSERT INTO `core_login_log` VALUES ('932a54b1068d62eccb9cbc57765f5402', '44C1BE7ECE9B07B3065ABDAA6F147DAC', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-11 10:40:43', '127.0.0.1', '1', '1', '3', '2016-11-11 10:40:43');
INSERT INTO `core_login_log` VALUES ('972bd165461857d89ea304eb9c80205e', 'F3A7493AEC73F82E32A34486CA5EE4FE', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-10 19:44:31', '127.0.0.1', '1', '1', '2', '2016-11-10 19:44:31');
INSERT INTO `core_login_log` VALUES ('9886b35da78e05b6f2a5e08ef7be82f3', '5E16EA103FED8DC20EB3828A49C649F4', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-09 10:15:05', '127.0.0.1', '1', '1', '3', '2016-11-09 10:15:05');
INSERT INTO `core_login_log` VALUES ('9c7ba8e1936f04d7edbabf134d278175', '6338A47B8EFB92E690DE5E3CAD543AD2', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-19 08:37:06', '0:0:0:0:0:0:0:1', '1', '1', '1', '2016-11-19 08:37:06');
INSERT INTO `core_login_log` VALUES ('9e52b878c26770921e5e6903b21c8fed', '592284C60447D3B6B6BF88FCCDEED9D7', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-09 17:17:04', '127.0.0.1', '1', '1', '2', '2016-11-09 17:17:04');
INSERT INTO `core_login_log` VALUES ('a1457d4d895ed70e35ea230b4eea1cdc', 'B88F17886EAE79B43B69502FA332F72C', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-10 09:52:15', '127.0.0.1', '1', '1', '3', '2016-11-10 09:52:15');
INSERT INTO `core_login_log` VALUES ('a54e06a3efe6b3fb1734a3f517e70d65', '23E3C8E630E454C1B130254F75531EB3', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-10 11:16:48', '127.0.0.1', '1', '1', '3', '2016-11-10 11:16:48');
INSERT INTO `core_login_log` VALUES ('acd38e7d7153e050eb678718f53e8b81', 'CE05D913F21B8339E30AC012E46BE484', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-08 13:42:29', '127.0.0.1', '1', '1', '3', '2016-11-08 13:42:29');
INSERT INTO `core_login_log` VALUES ('b1e1b54799630e73407aad6d86e51045', '36D2826A9EF9DC7690C932E9D35AC2CD', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-09-02 11:30:16', '127.0.0.1', '1', '1', '1', '2016-09-02 11:30:34');
INSERT INTO `core_login_log` VALUES ('b358cf8d271b0a7ecbe9f7ccb53eba6c', 'ED1CE97734BEB1F7E0CBC16FBEBE8217', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-10 13:04:49', '127.0.0.1', '1', '1', '2', '2016-11-10 13:04:49');
INSERT INTO `core_login_log` VALUES ('b42063e7b4d4f7794b23599023614b75', '916FE0A5EAC874F7C138FEFA80202EDA', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-11 10:34:52', '127.0.0.1', '1', '1', '3', '2016-11-11 10:34:52');
INSERT INTO `core_login_log` VALUES ('b5f9189cb347a4b85cde476cafad8a74', '567E269841B1B407185976F2DE14A35E', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-10 13:40:32', '127.0.0.1', '1', '1', '1', '2016-11-10 13:37:25');
INSERT INTO `core_login_log` VALUES ('b9237ef39d565f3c8b70401ae5ce5f7b', '01FC51158AFF95E776DA98FCDB998AED', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-10 14:38:12', '127.0.0.1', '1', '1', '3', '2016-11-10 14:38:12');
INSERT INTO `core_login_log` VALUES ('c1d5bceeae91a087442ff6cda0282836', '67C9413AE8EE56BEDCCD589A0FB6836F', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-10 14:19:18', '127.0.0.1', '1', '1', '3', '2016-11-10 14:19:18');
INSERT INTO `core_login_log` VALUES ('c5cdf1ecb33dc28290d4a56fd8c91139', '97F672BAF4B3291F5877656227F70EEA', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-11 11:04:46', '127.0.0.1', '1', '1', '3', '2016-11-11 11:04:46');
INSERT INTO `core_login_log` VALUES ('c767317167628df412a40a77fdedaba2', 'A354E6498804C067C39E8EBFF779B24B', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-11 09:18:55', '127.0.0.1', '1', '1', '3', '2016-11-11 09:18:55');
INSERT INTO `core_login_log` VALUES ('d02ee9393f8acde69ed3eb5fc9180eae', '9BA5AEE537C1FC9BB6339AFE4F25733E', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-10 09:37:25', '127.0.0.1', '1', '1', '3', '2016-11-10 09:37:25');
INSERT INTO `core_login_log` VALUES ('d1171adbd88c4ac2eb0dc43609d96c60', '489C0EF9EF41BC9519C97B3EE626B5E1', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-25 14:52:40', '0:0:0:0:0:0:0:1', '1', '1', '3', '2016-11-25 14:52:40');
INSERT INTO `core_login_log` VALUES ('d1d62103f01eb4f0e657fef1936daf6c', 'A7DB9642CD3BB917FF6E52DA8AF1524C', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-08 10:14:51', '127.0.0.1', '1', '1', '1', '2016-11-08 10:12:34');
INSERT INTO `core_login_log` VALUES ('d1e73afb0a6abac5e59a79ad87d9cc00', 'A4DAAAD259858B92F802CFAEC37FF7E4', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-13 17:34:54', '0:0:0:0:0:0:0:1', '1', '1', '1', '2016-11-13 17:34:54');
INSERT INTO `core_login_log` VALUES ('d4bcf7970cb80ce83fc58ad17c9d5c2a', '509EF545C1C2CC35AD4C9A9F941F19F6', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-10 11:13:43', '127.0.0.1', '1', '1', '3', '2016-11-10 11:13:43');
INSERT INTO `core_login_log` VALUES ('d6042b65c7f1aaec3de8b03be72948a3', 'ECBFB1F81782CCB94567FF947A40E1E4', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-10 11:18:46', '127.0.0.1', '1', '1', '3', '2016-11-10 11:18:46');
INSERT INTO `core_login_log` VALUES ('dc917eddedb231bc50caef8989c82136', 'A4DAAAD259858B92F802CFAEC37FF7E4', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-13 17:34:32', '0:0:0:0:0:0:0:1', '1', '1', '1', '2016-11-13 17:34:32');
INSERT INTO `core_login_log` VALUES ('dd5d585a70db5ee9233227dd4d39636c', 'A354E6498804C067C39E8EBFF779B24B', 'admin', 'admin', '-', '未知', '2016-11-11 08:35:35', '127.0.0.1', '3', '1', null, null);
INSERT INTO `core_login_log` VALUES ('de5b5ecece721969e629d69d72405336', 'D84A135979155A60A153AFB5911258E0', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-10 14:22:18', '127.0.0.1', '1', '1', '3', '2016-11-10 14:22:18');
INSERT INTO `core_login_log` VALUES ('df7f7740c02c90aae6daf52a2eb101bb', 'F61C0B747C8006F7C4ACC5D5B355B013', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-10 14:47:00', '127.0.0.1', '1', '1', '3', '2016-11-10 14:47:00');
INSERT INTO `core_login_log` VALUES ('e8cdb962efdcf250305d562898c784db', '24693EF5CF983AA13FC691825CACA974', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-08 14:44:23', '127.0.0.1', '1', '1', '2', '2016-11-08 14:44:23');
INSERT INTO `core_login_log` VALUES ('ed6bf45cc53331d0dc20e880bcd02de6', '509EF545C1C2CC35AD4C9A9F941F19F6', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-10 11:13:43', '127.0.0.1', '1', '1', '3', '2016-11-10 11:13:43');
INSERT INTO `core_login_log` VALUES ('ee9aea27abe1a68811b66cfe5047d887', '4CD3D813B3A887E1FCB8942AB7446FB1', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-11 10:08:23', '127.0.0.1', '1', '1', '3', '2016-11-11 10:08:23');
INSERT INTO `core_login_log` VALUES ('efca987a707702ada1aaca886cde14a9', '620CD57F598AE93BF44245028CCDCB98', 'admin', 'admin', '-', '未知', '2016-11-25 16:26:04', '0:0:0:0:0:0:0:1', '3', '1', null, null);
INSERT INTO `core_login_log` VALUES ('f4d741aa92665dbc6257a2191abacdca', 'E458744277757C59485762215636E7E2', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-11 13:49:31', '127.0.0.1', '1', '1', '3', '2016-11-11 13:49:31');
INSERT INTO `core_login_log` VALUES ('f6a3fab298480f724e7d78adcf8d2dcd', '341CE0453C7D4B648AA4848CDDB06872', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-10 16:05:52', '127.0.0.1', '1', '1', '3', '2016-11-10 16:05:52');
INSERT INTO `core_login_log` VALUES ('fe81ba5c3dfc4373723cc2766d8a50f2', '41F09FEDADF76361761DABA92C073EB5', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '2016-11-09 10:06:22', '127.0.0.1', '1', '1', '3', '2016-11-09 10:06:22');

-- ----------------------------
-- Table structure for core_mail
-- ----------------------------
DROP TABLE IF EXISTS `core_mail`;
CREATE TABLE `core_mail` (
  `mail_id` varchar(40) NOT NULL COMMENT '邮件内码',
  `title` varchar(200) NOT NULL COMMENT '邮件主题',
  `content` text NOT NULL COMMENT '邮件内容',
  `send_status` varchar(2) NOT NULL COMMENT '发送状态',
  `sender` varchar(40) NOT NULL COMMENT '发送人',
  `send_time` datetime DEFAULT NULL COMMENT '发送时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`mail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='邮件表';

-- ----------------------------
-- Records of core_mail
-- ----------------------------
INSERT INTO `core_mail` VALUES ('013c9413476bd65bca1e55c6f4424cb4', '你好', '<p>换个更好</p>', '2', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-08-10 18:34:02', '2016-08-10 18:34:03');
INSERT INTO `core_mail` VALUES ('083b92fc9cdca2ec9e44045e2f19d56b', '测试', '<p>测试内容</p>', '1', '240b59bf433b7701b58ba7adb63bfde0', '2016-08-24 15:42:02', '2016-08-24 15:42:03');
INSERT INTO `core_mail` VALUES ('0a1178e377d6a524b4e1b71bc71a9323', '大', '<p>大大大大<br/></p>', '2', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-26 18:21:45', '2016-07-26 18:21:45');
INSERT INTO `core_mail` VALUES ('12f44dba9bbb83df3e1876347d6dfd19', '', '<p>第三代</p>', '2', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-26 18:30:40', '2016-07-26 18:30:40');
INSERT INTO `core_mail` VALUES ('1d97e696fe5389a17eb88839571a1d8c', '大大大', '<p>大大大</p>', '1', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-26 18:21:19', '2016-07-26 18:21:19');
INSERT INTO `core_mail` VALUES ('243d6681689e1ab88e3b48449c51acbe', '学习', '<p>学习</p>', '1', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-08-10 18:46:40', '2016-08-10 18:46:41');
INSERT INTO `core_mail` VALUES ('39c9cfbe040b25d2798f47fa1393d366', '团队活动建设', '<p>多摄氏度的</p>', '2', '240b59bf433b7701b58ba7adb63bfde0', '2016-08-16 17:04:31', '2016-08-16 17:04:31');
INSERT INTO `core_mail` VALUES ('3e6f80db52ce54cbda9a2fb06471e835', '23', '<p>u</p>', '0', '259bfa4d8c86ea2b60e0c61f7b1c42e0', null, '2016-08-10 18:34:41');
INSERT INTO `core_mail` VALUES ('51a6448ec8efda548545a1cf2ff74c6c', '125', '<p>54</p>', '2', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-08-10 18:38:00', '2016-08-10 18:38:00');
INSERT INTO `core_mail` VALUES ('5989f6611e18ecfc46769eda36a45ba2', '转发：23', '<p>					<br/>----邮件原件----<br/>\n					发件人：超级管理人员<br/>\n					发送时间：2016-08-10 18:38:24.0<br/>\n					收件人：fdgdf<br/>\n					主题：23<br/></p><p>想</p><p>\n				</p>', '1', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-08-10 18:38:41', '2016-08-10 18:38:42');
INSERT INTO `core_mail` VALUES ('5ce8cf6c8ad034a605829f14fa2f2beb', 'DAS ', '<p>DSA&nbsp;</p>', '0', '259bfa4d8c86ea2b60e0c61f7b1c42e0', null, '2016-07-26 18:32:52');
INSERT INTO `core_mail` VALUES ('737320d9dd153e13a7ca017a14d71ec9', '23', '<p>想</p>', '2', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-08-10 18:38:24', '2016-08-10 18:38:24');
INSERT INTO `core_mail` VALUES ('a74ec683a3075be84052e3ff317678d1', '大', '<p>大大大大大大大</p>', '1', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-26 18:19:43', '2016-07-26 18:19:44');
INSERT INTO `core_mail` VALUES ('aebb5cd3d8cda3f854b97cbfd35c74fb', '找找', '', '0', '259bfa4d8c86ea2b60e0c61f7b1c42e0', null, '2016-08-10 18:35:13');
INSERT INTO `core_mail` VALUES ('cceab301eb816d7c415e77ddf08b28c7', '2', '<p>52</p>', '0', '259bfa4d8c86ea2b60e0c61f7b1c42e0', null, '2016-08-10 18:35:37');

-- ----------------------------
-- Table structure for core_mail_receive
-- ----------------------------
DROP TABLE IF EXISTS `core_mail_receive`;
CREATE TABLE `core_mail_receive` (
  `mail_id` varchar(40) NOT NULL COMMENT '邮件内码',
  `receiver` varchar(40) NOT NULL COMMENT '接收人',
  `receive_status` varchar(2) NOT NULL COMMENT '接收状态',
  PRIMARY KEY (`mail_id`,`receiver`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='邮件接收表';

-- ----------------------------
-- Records of core_mail_receive
-- ----------------------------
INSERT INTO `core_mail_receive` VALUES ('013c9413476bd65bca1e55c6f4424cb4', '108da062ed08596cb9ca9465a2976a09', '0');
INSERT INTO `core_mail_receive` VALUES ('083b92fc9cdca2ec9e44045e2f19d56b', '543352b804bc34a665eb65e6ce7359d8', '0');
INSERT INTO `core_mail_receive` VALUES ('0a1178e377d6a524b4e1b71bc71a9323', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2');
INSERT INTO `core_mail_receive` VALUES ('0a1178e377d6a524b4e1b71bc71a9323', '41d6a469478d402e442f31231ed08447', '0');
INSERT INTO `core_mail_receive` VALUES ('0a1178e377d6a524b4e1b71bc71a9323', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '0');
INSERT INTO `core_mail_receive` VALUES ('0a1178e377d6a524b4e1b71bc71a9323', 'demo', '0');
INSERT INTO `core_mail_receive` VALUES ('12f44dba9bbb83df3e1876347d6dfd19', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2');
INSERT INTO `core_mail_receive` VALUES ('12f44dba9bbb83df3e1876347d6dfd19', '41d6a469478d402e442f31231ed08447', '0');
INSERT INTO `core_mail_receive` VALUES ('12f44dba9bbb83df3e1876347d6dfd19', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '0');
INSERT INTO `core_mail_receive` VALUES ('12f44dba9bbb83df3e1876347d6dfd19', 'demo', '0');
INSERT INTO `core_mail_receive` VALUES ('1d97e696fe5389a17eb88839571a1d8c', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2');
INSERT INTO `core_mail_receive` VALUES ('1d97e696fe5389a17eb88839571a1d8c', '41d6a469478d402e442f31231ed08447', '0');
INSERT INTO `core_mail_receive` VALUES ('1d97e696fe5389a17eb88839571a1d8c', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '0');
INSERT INTO `core_mail_receive` VALUES ('1d97e696fe5389a17eb88839571a1d8c', 'demo', '0');
INSERT INTO `core_mail_receive` VALUES ('243d6681689e1ab88e3b48449c51acbe', '108da062ed08596cb9ca9465a2976a09', '0');
INSERT INTO `core_mail_receive` VALUES ('39c9cfbe040b25d2798f47fa1393d366', '543352b804bc34a665eb65e6ce7359d8', '0');
INSERT INTO `core_mail_receive` VALUES ('39c9cfbe040b25d2798f47fa1393d366', 'a9efec93fe86a12417a34e878502d2fc', '0');
INSERT INTO `core_mail_receive` VALUES ('3e6f80db52ce54cbda9a2fb06471e835', '108da062ed08596cb9ca9465a2976a09', '0');
INSERT INTO `core_mail_receive` VALUES ('51a6448ec8efda548545a1cf2ff74c6c', '6d01fdd1392958ec9d3988f799be0311', '0');
INSERT INTO `core_mail_receive` VALUES ('5989f6611e18ecfc46769eda36a45ba2', '6d01fdd1392958ec9d3988f799be0311', '0');
INSERT INTO `core_mail_receive` VALUES ('5ce8cf6c8ad034a605829f14fa2f2beb', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '1');
INSERT INTO `core_mail_receive` VALUES ('5ce8cf6c8ad034a605829f14fa2f2beb', '41d6a469478d402e442f31231ed08447', '0');
INSERT INTO `core_mail_receive` VALUES ('5ce8cf6c8ad034a605829f14fa2f2beb', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '0');
INSERT INTO `core_mail_receive` VALUES ('5ce8cf6c8ad034a605829f14fa2f2beb', 'demo', '0');
INSERT INTO `core_mail_receive` VALUES ('737320d9dd153e13a7ca017a14d71ec9', '108da062ed08596cb9ca9465a2976a09', '0');
INSERT INTO `core_mail_receive` VALUES ('a74ec683a3075be84052e3ff317678d1', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2');
INSERT INTO `core_mail_receive` VALUES ('a74ec683a3075be84052e3ff317678d1', '41d6a469478d402e442f31231ed08447', '0');
INSERT INTO `core_mail_receive` VALUES ('a74ec683a3075be84052e3ff317678d1', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '0');
INSERT INTO `core_mail_receive` VALUES ('a74ec683a3075be84052e3ff317678d1', 'demo', '0');
INSERT INTO `core_mail_receive` VALUES ('aebb5cd3d8cda3f854b97cbfd35c74fb', '6d01fdd1392958ec9d3988f799be0311', '0');
INSERT INTO `core_mail_receive` VALUES ('cceab301eb816d7c415e77ddf08b28c7', '6d01fdd1392958ec9d3988f799be0311', '0');

-- ----------------------------
-- Table structure for core_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `core_operation_log`;
CREATE TABLE `core_operation_log` (
  `log_id` varchar(40) NOT NULL COMMENT '日志内码',
  `operation_url` varchar(500) NOT NULL COMMENT '操作地址',
  `operation_type` varchar(2) NOT NULL COMMENT '操作类别',
  `operation_result` varchar(2) NOT NULL COMMENT '操作结果',
  `error_reason` varchar(2000) DEFAULT NULL COMMENT '错误原因',
  `operation_detail` varchar(2000) NOT NULL COMMENT '操作说明',
  `response_start` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '响应开始时间',
  `response_end` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '响应结束时间',
  `cost` int(11) NOT NULL COMMENT '耗时',
  `operator` varchar(40) NOT NULL COMMENT '操作人',
  `operator_name` varchar(40) NOT NULL COMMENT '操作人姓名',
  `operator_dept_id` varchar(40) NOT NULL COMMENT '操作人所属部门',
  `operator_dept_name` varchar(40) NOT NULL COMMENT '操作人所属部门名称',
  `ip` varchar(20) NOT NULL COMMENT 'IP地址',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作日志表';

-- ----------------------------
-- Records of core_operation_log
-- ----------------------------
INSERT INTO `core_operation_log` VALUES ('0087acae4bcc128480315f2d06a84aaa', 'core/system/code_table', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 13:16:55', '2016-11-11 13:16:56', '310', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('01a42edf4dbf1dda405491f54a43b457', 'wzgl/reloadCash/reloadCash', '0', '1', null, '-', '2016-11-10 10:45:21', '2016-11-10 10:45:21', '338', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('01eb177f89c6e009b17702ef8604144a', 'wzgl/picture/picture/ajaxAdd', '3', '1', null, '添加图片，图片编号：06cb5cacc1259ccb35c55f852b6fbc80', '2016-11-10 10:00:00', '2016-11-10 10:00:00', '770', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('020529f952d5865d45819ee417010c9a', 'wzgl/picture/picture/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-08 17:43:43', '2016-11-08 17:43:43', '607', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('025bf6bf8fd23e97a656998abdc1cd37', 'core/system/limit/1c2cf7be651103c6aed430adb06a5b3a/edit', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 13:35:20', '2016-11-10 13:35:21', '624', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('02a3b61c2a2f07dccf9711ded88d5d59', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-10 09:56:07', '2016-11-10 09:56:08', '596', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('030bac2bb7f9507de07b34dc9541abd2', 'core/base/home', '1', '1', null, '访问框架快捷方式页面', '2016-11-09 10:07:13', '2016-11-09 10:07:14', '956', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('03359925bedd2266eaea35135b3d5bb3', 'core/system/attr/0224fe7163e7380fbc7d92983dff6f7c/edit', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 10:13:41', '2016-11-08 10:13:42', '1221', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('0336ea5c30b422c3f134bc1793495f11', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-10 10:01:40', '2016-11-10 10:01:41', '442', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('033c50b0e14c29c8c827a45acaeff143', 'core/system/attr/add', '0', '2', null, '-', '2016-11-19 08:48:05', '2016-11-19 08:48:05', '0', '-', '未知', '-', '未知', '0:0:0:0:0:0:0:1');
INSERT INTO `core_operation_log` VALUES ('043d958bfaba458c14626ffa726759e3', 'wzgl/email/email/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-10 17:07:56', '2016-11-10 17:07:57', '677', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('046dcdf6e5fe6201017f8a40bee0a2f3', 'wzgl/article/article', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 13:52:22', '2016-11-11 13:52:23', '813', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('056fd7573830793bf08180a3923326ab', 'wzgl/email/email/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-10 17:34:33', '2016-11-10 17:41:41', '428191', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('0687a05f45fb864beaec96c534fc7999', 'core/system/attr/0224fe7163e7380fbc7d92983dff58tc/edit', '1', '1', null, '访问框架快捷方式页面', '2016-11-19 08:37:29', '2016-11-19 08:37:29', '467', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '0:0:0:0:0:0:0:1');
INSERT INTO `core_operation_log` VALUES ('068d3425dfaf2e814a81df7faed3e697', 'wzgl/email/email/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-11 13:21:29', '2016-11-11 13:21:29', '512', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('06b42920a10a28cab070b7f43a07cb42', 'wzgl/email/email/delete', '0', '4', 'Request processing failed; nested exception is org.springframework.jdbc.BadSqlGrammarException: PreparedStatementCallback; bad SQL grammar [delete from wzgl_email where emailIds in (?)]; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'emailIds\' in \'where clause\'', '-', '2016-11-11 13:28:00', '2016-11-11 13:28:05', '5351', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('06c409a6e48bb2bc902aec04b392c177', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-08 10:55:41', '2016-11-08 10:55:41', '538', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('07180b1c33ef36654936a07160aff39d', 'wzgl/email/email/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-10 17:22:44', '2016-11-10 17:22:46', '1888', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('0771c8837ca6288b6d795def09f95e45', 'wzgl/channel/channel/3ca97e31fca2abda3124f686f1efe69c/add', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 11:00:03', '2016-11-10 11:00:04', '580', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('07ccd7893b68a8bbf286da67cf8a4a8c', 'core/system/attr/edit', '4', '1', null, '编辑参数，参数内码：1f8a120b8aebe5e118e2f637bddfc9f4，参数编号：page_title，参数名称：默认页面标题', '2016-11-08 10:14:21', '2016-11-08 10:14:21', '706', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('07cfa67f52a11ff5d88e7bda1af59adb', 'wzgl/email/email', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 11:14:44', '2016-11-11 11:14:44', '508', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('08030b9b51f9928c8f68b59629609bbe', 'core/system/limit', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 09:38:14', '2016-11-10 09:38:14', '639', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('08d91504bc1eb92ca25632e717d6b3de', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-09 15:42:43', '2016-11-09 15:42:43', '452', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('08d9db436bd71936beacf1bff75e6720', 'core/base/home', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 11:14:24', '2016-11-11 11:14:25', '1070', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('097f8d27cac060f33aae805ca5cf0e35', 'wzgl/picture/picture/ajaxAdd', '3', '1', null, '添加图片，图片编号：dc703481b4fb79fd5e2afa96beb116df', '2016-11-08 13:15:01', '2016-11-08 13:15:02', '1523', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('0a0d7e5e03dca750abbc4a70182c46b7', 'wzgl/channel/channel/top/add', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 10:52:30', '2016-11-08 10:52:30', '523', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('0a2000999c95cdde16054a8b02873b7f', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-10 13:51:36', '2016-11-10 13:51:37', '611', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('0a528d3a042a7b07334ee15a77eb0a8a', 'wzgl/article/article/list', '2', '1', null, '获取文章管理页面中的文章列表数据', '2016-11-11 13:52:23', '2016-11-11 13:52:24', '497', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('0a6d5e0a10af4ab84000aac8a64eaee3', 'wzgl/email/email', '1', '1', null, '访问用户管理页面', '2016-11-10 13:56:17', '2016-11-10 13:56:41', '23526', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('0c3e61e11ddb387b43d64e4e2c41c6a3', 'wzgl/email/email/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-10 17:46:53', '2016-11-10 17:46:53', '514', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('0d132b933eb76dcb6f89ad6fae8e5ce4', 'wzgl/video/video/add', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 16:06:03', '2016-11-08 16:06:04', '571', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('0d5fdab23a37fc2a098e0d66f0ef13dd', 'wzgl/picture/picture/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-08 13:07:45', '2016-11-08 13:07:45', '311', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('0e3badfeee072c49ce385804a8e602e3', 'core/base/home', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 16:05:57', '2016-11-08 16:05:58', '1165', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('0ef5e1cfa2d845e2b71727ea201cb3ea', 'wzgl/picture/picture/ajaxAdd', '3', '1', null, '添加图片，图片编号：df28910593673d063b4b83384036eb6d', '2016-11-10 09:58:20', '2016-11-10 09:58:21', '454', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('0f446047fef67addef0c2998037e77e6', 'wzgl/picture/picture/ajaxAdd', '3', '1', null, '添加图片，图片编号：cb0ae76e107c1e25b402c4983d8c88ac', '2016-11-10 09:57:19', '2016-11-10 09:57:20', '841', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('1021c6094138ede9382fa42f433cadc9', 'wzgl/picture/picture/ajaxAdd', '3', '1', null, '添加图片，图片编号：b23ee50a652fc48dca5232bf539a6a50', '2016-11-10 09:58:06', '2016-11-10 09:58:07', '437', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('109c888ebd25f03d5131f45213c31598', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-10 11:08:04', '2016-11-10 11:08:05', '772', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('111d52862bfc930278e8d43cce12bcd6', 'wzgl/email/email', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 09:19:45', '2016-11-11 09:19:46', '1029', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('115986b00bc49e450117d6b2b66d1d7f', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-09 15:50:56', '2016-11-09 15:50:56', '464', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('116c309f9594de2a293628f8dff53aad', 'core/system/attr/list', '2', '1', null, '获取参数管理页面的参数列表数据', '2016-11-08 10:10:03', '2016-11-08 10:10:05', '1727', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('11c31c8852f2d6a34ec7e5031da10be0', 'wzgl/channel/channel', '1', '1', null, '访问框架快捷方式页面', '2016-11-09 15:44:20', '2016-11-09 15:44:21', '563', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('120cf9cabcc1b69e021303556701f2af', 'wzgl/picture/picture', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 10:00:00', '2016-11-10 10:00:01', '616', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('1212ea1ce7f61476d2705dfe17f2fb97', 'wzgl/email/email/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-10 17:47:15', '2016-11-10 17:47:16', '563', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('127ae8fe477006c6b98c6cdb91a19c48', 'wzgl/channel/channel/info', '1', '1', null, '获取栏目信息，栏目编号：3dab29df2dac9cefaa08f51450aa6111，栏目名称：新闻动态', '2016-11-09 15:50:35', '2016-11-09 15:50:36', '536', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('129f358e27feb315937a4d903c5cc808', 'wzgl/picture/picture', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 13:13:11', '2016-11-08 13:13:12', '853', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('12c21a590bc4ea9125a88b492c7a289c', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-11 13:19:02', '2016-11-11 13:19:03', '653', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('12e3d396ea7ab9ae463ee94752b2ecd3', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-10 13:38:24', '2016-11-10 13:38:25', '580', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('12e438413a08e2d5b8ecb4bc36e4151a', 'wzgl/picture/picture/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-10 10:00:15', '2016-11-10 10:00:16', '638', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('1404c44c8c45c589da948cf9603b43e0', 'wzgl/email/email/list', '0', '4', 'Request processing failed; nested exception is java.lang.NullPointerException', '-', '2016-11-10 14:21:19', '2016-11-10 14:21:20', '337', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('1425d4f330a328ba3f602959d310b475', 'core/base/home', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 10:02:45', '2016-11-08 10:02:45', '698', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('14b78c0310df8e73d3c58b59570e0052', '/wzgl/album/album', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 13:16:24', '2016-11-11 13:16:25', '358', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('1501793e86c8db16460df3c7a830235b', 'wzgl/picture/picture', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 09:57:03', '2016-11-10 09:57:04', '721', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('156c7fee762b9d55d93ad3d701877825', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-09-02 11:34:58', '2016-09-02 11:34:59', '513', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('1581f6aec3447f43eab8fe27d5a8b075', 'wzgl/email/email/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-10 16:02:32', '2016-11-10 16:02:32', '631', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('163f7049c7164af8a0d8259c86476cc5', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-10 17:48:14', '2016-11-10 17:48:15', '618', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('166e1cdf0e16d07a29df1d887df5c93e', 'wzgl/picture/picture/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-08 13:08:30', '2016-11-08 13:08:31', '768', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('168d4d28db41d8ea045ac6730030089c', 'core/system/code_table/album_flag/code_table', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 09:53:35', '2016-11-10 09:53:36', '732', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('16e75a156af0eaa8676649b9d61e772f', 'wzgl/email/email/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-10 18:00:30', '2016-11-10 18:00:30', '563', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('17234272fbc8d45fdd14a0b79ed1dcef', 'core/base/login', '0', '2', null, '-', '2016-11-13 17:34:54', '2016-11-13 17:34:54', '7', '-', '未知', '-', '未知', '0:0:0:0:0:0:0:1');
INSERT INTO `core_operation_log` VALUES ('176abf1694ca6266a53a6000b7fe8774', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-08 10:00:34', '2016-11-08 10:00:35', '621', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('17cff3b3e603df812fa333c713754198', 'core/system/system/3892bfe623082aa570471b6cf177f45e/edit', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 10:11:22', '2016-11-08 10:11:23', '852', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('18ac690dba1421381bb853ec1c055c27', 'wzgl/email/email/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-11 11:02:15', '2016-11-11 11:02:15', '438', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('1a5a4a2c7d35c815aa51e796d7698bd4', 'wzgl/email/email/show', '4', '1', null, '显示邮件，邮件编号列表：18a43a73cdee7908faa745d84c0ffcfa', '2016-11-11 11:21:28', '2016-11-11 11:21:35', '6848', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('1a6d682629ed880a70d5eb739701ea87', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-10 16:27:00', '2016-11-10 16:27:01', '573', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('1a9c4d93c6b6dcf91912b9847ba9318f', 'core/system/user', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 10:10:55', '2016-11-08 10:10:56', '505', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('1b5e694151b14264f53b080cb498dc1c', 'core/announcement/view_announcement', '1', '1', null, '访问框架快捷方式页面', '2016-11-25 16:43:26', '2016-11-25 16:43:26', '380', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '0:0:0:0:0:0:0:1');
INSERT INTO `core_operation_log` VALUES ('1bb391355de230169c7233099773b460', 'wzgl/picture/picture/ajaxAdd', '3', '1', null, '添加图片，图片编号：cbaae672dd862485e00e2262ac2d2872', '2016-11-10 09:59:45', '2016-11-10 09:59:46', '1113', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('1c1db74a5dcc33e43670d38a17fe2dfc', 'core/base/home', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 10:37:46', '2016-11-11 10:37:47', '1176', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('1c69b83920647927e1e1b17bce2a2213', 'core/system/role', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 13:36:52', '2016-11-10 13:36:52', '531', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('1c7cfb5f02374c072d58b2ff7b17b5e0', 'wzgl/channel/channel', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 14:47:49', '2016-11-10 14:47:50', '826', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('1cf733e7f96d8723320265e3b13738c8', 'core/base/login', '0', '2', null, '-', '2016-11-10 14:47:45', '2016-11-10 14:47:46', '1826', '-', '未知', '-', '未知', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('1d108ac5a390571b507609697882624f', 'wzgl/email/email/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-10 15:54:54', '2016-11-10 15:55:08', '13459', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('1da37b6741d6509eb99dc39e4a557f63', 'wzgl/email/email/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-10 16:06:12', '2016-11-10 16:06:12', '579', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('1dcc125225858f719ffeef98539144ec', 'core/base/home', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 15:53:52', '2016-11-10 15:53:53', '1327', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('1dd121d464527d5545fa3bad46309d72', '/wzgl/album/album', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 13:13:08', '2016-11-08 13:13:09', '614', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('1e6882e12c97fbd87b579ba10a1cdbad', 'tdjs/tdjsChannel/channel', '1', '1', null, '访问框架快捷方式页面', '2016-09-02 11:34:49', '2016-09-02 11:34:50', '612', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('1ef4988447c4494dc2e4858bc1338521', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-08 13:07:43', '2016-11-08 13:07:44', '789', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('1f60b813374cedecea048443d2a36be7', 'wzgl/picture/picture/ajaxAdd', '3', '1', null, '添加图片，图片编号：531eac33e9e078b3e653c8871c5f7a4c', '2016-11-10 09:59:07', '2016-11-10 09:59:08', '476', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('1fa027a27fd513e50e38fb7229fc3288', 'core/system/user/240b59bf433b7701b58ba7adb63bfde0/set_user_attr', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 10:09:10', '2016-11-08 10:09:10', '830', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('202d2fceaee15cac9555ccf3c2e2ccdd', 'core/system/limit', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 13:37:47', '2016-11-10 13:37:48', '572', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('20394fe27c16bc9fdc240207d69d70f7', 'wzgl/reloadCash/reloadCash', '0', '1', null, '-', '2016-11-10 10:44:36', '2016-11-10 10:44:37', '1093', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('206cee0ce97fee0b9e803c533d758679', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-10 17:07:56', '2016-11-10 17:07:57', '676', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('215ca9f8bfc058550548a354c194fd76', 'uploadPic', '0', '1', null, '-', '2016-11-10 10:44:31', '2016-11-10 10:44:31', '388', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('227bca9acfc74faab4650132c6e24655', 'wzgl/picture/picture/ajaxAdd', '3', '1', null, '添加图片，图片编号：17d90079060fff35965b9010c28ca964', '2016-11-10 10:01:02', '2016-11-10 10:01:03', '1195', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('22e12c86a27fe374bceef30207994f1d', 'wzgl/article/article/list', '2', '1', null, '获取文章管理页面中的文章列表数据', '2016-11-11 13:52:38', '2016-11-11 13:52:39', '643', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('238a55a9a1bd992c4bbf7fceafb7439e', 'wzgl/email/email/c1c5b3e1c50053f2efb6fa10593a2794/edit', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 10:37:51', '2016-11-11 10:37:52', '383', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('24880cf88af04c6c2d305317002298c5', 'wzgl/email/email/list', '0', '4', 'Request processing failed; nested exception is java.lang.NullPointerException', '-', '2016-11-10 14:18:11', '2016-11-10 14:18:11', '69', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('252312b3393c82be319635ed9f08a5aa', 'core/system/attr', '0', '2', null, '-', '2016-11-08 13:12:55', '2016-11-08 13:12:55', '0', '-', '未知', '-', '未知', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('253c8dce1744f784b884a2e0442ef279', 'wzgl/channel/channel', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 10:54:13', '2016-11-08 10:54:14', '564', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('25658dd79a2b635434ccfab7d643e628', 'core/log/operation_log/clear', '0', '1', null, '-', '2016-09-02 11:19:27', '2016-09-02 11:19:27', '404', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('26d46a429a00540ab2aaf09192a5245f', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-08 10:11:08', '2016-11-08 10:11:08', '618', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('2824bd9e308e4390a52d4884bbece28b', 'wzgl/email/email/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-11 11:07:23', '2016-11-11 11:07:24', '473', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('28460f334b279acdb14a2e549bd4bfe9', 'wzgl/email/email/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-10 17:41:37', '2016-11-10 17:41:42', '4122', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('287e7e0cd84b2c9147f25c67b50b4c95', 'wzgl/email/email/add', '1', '4', 'An exception occurred processing JSP page /WEB-INF/views/wzgl/email/addEmail.jsp at line 62\r\n\r\n59:                 <div class=\"panel-body\">\r\n60:                     <form:form id=\"emailForm\" cssClass=\"form-horizontal\" modelAttribute=\"email\" method=\"post\">\r\n61:                         <form:hidden path=\"channel_parent\" />\r\n62: \r\n63: \r\n64:                         <div class=\"form-group\">\r\n65:                             <label class=\"col-sm-2 control-label text-right\">閭欢鍚嶇О锛�/label>\r\n\r\n\r\nStacktrace:', '访问框架快捷方式页面', '2016-11-11 09:21:00', '2016-11-11 09:21:05', '5324', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('299323386002a822f0fc1ccceec8dac6', 'core/base/home', '1', '1', null, '访问框架快捷方式页面', '2016-11-09 15:33:01', '2016-11-09 15:33:03', '1276', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('29fa93d92e7569b52deccf7305840874', 'wzgl/picture/picture/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-10 14:06:46', '2016-11-10 14:06:47', '702', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('2a0165552e29e2324bf7fc39083440ff', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-08 10:03:33', '2016-11-08 10:03:33', '433', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('2a5eef28d209d224eecd4df8431a6270', 'core/system/user/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-11 10:38:37', '2016-11-11 10:38:38', '1087', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('2a8707f17b4801b199cd8cf3ec91795e', 'wzgl/email/email', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 14:18:10', '2016-11-10 14:18:11', '331', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('2aa5d58cec344da8da12c8963f2ce67b', 'core/base/home', '0', '2', null, '-', '2016-11-19 08:37:10', '2016-11-19 08:37:10', '0', '-', '未知', '-', '未知', '0:0:0:0:0:0:0:1');
INSERT INTO `core_operation_log` VALUES ('2ab1b7c356121733f20761b2dd032bd8', 'core/base/logout', '0', '2', null, '-', '2016-11-13 17:34:17', '2016-11-13 17:34:17', '0', '-', '未知', '-', '未知', '0:0:0:0:0:0:0:1');
INSERT INTO `core_operation_log` VALUES ('2b46aac443c9267ae81b831b3f182e99', 'jspc/zwpc/zwpc/list', '2', '1', null, '获取评测记录列表数据', '2016-09-02 11:31:08', '2016-09-02 11:31:08', '471', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('2be300ee5719d0c946ed5bb71e054024', 'core/tools/screenshot', '1', '1', null, '访问框架快捷方式页面', '2016-09-02 11:19:40', '2016-09-02 11:19:40', '464', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('2cfb103423d729f77f50e17217e93d14', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-11 13:08:28', '2016-11-11 13:08:28', '554', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('2d01e1644551b17a2c9f037351f1f0af', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-10 16:08:23', '2016-11-10 16:08:23', '380', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('2d50e9e2ad271a28fe6f457d1f0c8d0c', 'core/base/home', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 17:29:34', '2016-11-10 17:29:35', '1079', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('2d6df4084582bb42bbd7393153f5915c', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-11 13:29:27', '2016-11-11 13:29:28', '675', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('2d9d09c2b57aa6f5c3576b2490a9b01f', 'core/base/login', '0', '2', null, '-', '2016-11-19 09:41:09', '2016-11-19 09:41:09', '12', '-', '未知', '-', '未知', '0:0:0:0:0:0:0:1');
INSERT INTO `core_operation_log` VALUES ('2dec5de7f00882f78f31b0e7ab37f8c5', 'wzgl/email/email', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 11:03:42', '2016-11-11 11:03:43', '787', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('2e28c47877fb49fbf65807d3d4528961', 'core/base/home', '1', '1', null, '访问框架快捷方式页面', '2016-09-02 11:30:37', '2016-09-02 11:30:38', '671', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('2e8a4dd799c3280fa817822dfe56cb7f', 'wzgl/channel/channel', '1', '1', null, '访问框架快捷方式页面', '2016-11-09 15:41:56', '2016-11-09 15:41:56', '700', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('2edb548205f34e0ba049206832f18927', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-08 10:00:54', '2016-11-08 10:00:55', '460', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('2fc95a80fe72d63d35afa7e004ffc061', 'wzgl/email/email', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 17:12:39', '2016-11-10 17:12:40', '657', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('2ffc1cbff9f7978b216a5baedb53750e', 'wzgl/email/email/13/edit', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 11:14:41', '2016-11-11 11:14:42', '316', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('3145a10b51e0d8fab98104935ad99ac9', 'wzgl/album/album/list', '2', '1', null, '获取相册管理页面中的相册列表数据', '2016-09-02 11:30:59', '2016-09-02 11:31:00', '471', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('31b394a046e640fc068bdc108830e97d', 'core/system/role', '1', '1', null, '访问框架快捷方式页面', '2016-11-25 16:45:27', '2016-11-25 16:45:27', '320', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '0:0:0:0:0:0:0:1');
INSERT INTO `core_operation_log` VALUES ('32c2cd8fc94f94987bb040cd218fe7d1', 'wzgl/email/email/delete', '0', '4', 'Request processing failed; nested exception is org.springframework.jdbc.BadSqlGrammarException: PreparedStatementCallback; bad SQL grammar [delete from wzgl_email where emailIds in (?)]; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'emailIds\' in \'where clause\'', '-', '2016-11-11 13:33:54', '2016-11-11 13:33:58', '3928', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('32d7e506e8eb57dd03c851718882ab9e', 'core/base/home', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 13:46:33', '2016-11-11 13:46:34', '1053', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('33392031d46b9a4a9d657a4cd7ae63cd', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-10 14:06:52', '2016-11-10 14:06:53', '672', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('33875abe135a4bb589f37d69188b9004', 'core/system/code_table/open_close/code_table/list', '2', '1', null, '获取码表管理页面中的码表列表数据，码表类别编号open_close', '2016-11-11 13:17:00', '2016-11-11 13:17:01', '633', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('344c420c0b33c20599fb8a828d6d51ec', 'core/base/logout', '0', '2', null, '-', '2016-11-13 17:34:32', '2016-11-13 17:34:32', '0', '-', '未知', '-', '未知', '0:0:0:0:0:0:0:1');
INSERT INTO `core_operation_log` VALUES ('34578f2db41f48cdbd11067955eb8b6a', 'wzgl/picture/picture/ajaxAdd', '3', '1', null, '添加图片，图片编号：093395f9f959046c15931c3558a9c800', '2016-11-10 09:57:51', '2016-11-10 09:57:52', '992', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('3470ac373a29b955d29593ef40bed688', 'wzgl/article/article/add', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 15:02:50', '2016-11-08 15:02:50', '517', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('35a58417b77c241fdf795b450a7ae359', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-08 10:53:11', '2016-11-08 10:53:11', '452', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('36849b6560f01e8f6b068ca173cc89a6', 'wzgl/article/article/list', '2', '1', null, '获取文章管理页面中的文章列表数据', '2016-11-11 13:07:36', '2016-11-11 13:07:36', '548', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('368721e1ed995ebd4a8d616e338c2668', 'wzgl/picture/picture/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-08 10:12:44', '2016-11-08 10:12:44', '392', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('369309358f5371a534e0a8bbb3055d62', 'wzgl/article/article/add', '1', '1', null, '访问框架快捷方式页面', '2016-11-09 15:33:09', '2016-11-09 15:33:09', '449', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('36976e5b19497345920c61affb8fb119', 'core/system/attr', '1', '1', null, '访问框架快捷方式页面', '2016-11-19 08:33:40', '2016-11-19 08:33:40', '497', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '0:0:0:0:0:0:0:1');
INSERT INTO `core_operation_log` VALUES ('36dbc9354d3295c41783b3a08d8d83dd', 'wzgl/email/email/hide', '4', '1', null, '隐藏邮件，邮件编号列表：f7b903af2cfa8bdae45b6ec8f47ac4b0', '2016-11-11 13:58:02', '2016-11-11 13:58:04', '2580', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('36e743000d90522033c7976b250eef24', 'wzgl/email/email', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 13:58:59', '2016-11-11 13:59:00', '700', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('376ad63d7ea653f29146b512350da4e6', 'wzgl/channel/channel', '1', '1', null, '访问框架快捷方式页面', '2016-11-09 15:41:37', '2016-11-09 15:41:38', '808', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('383f8cbde57812dcfb0942da84e90e08', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-10 10:44:13', '2016-11-10 10:44:14', '857', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('38465637ce41b1c3b8593e296d553098', 'wzgl/email/email/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-11 13:26:48', '2016-11-11 13:26:53', '4795', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('391aa99aed97b549707f269234288575', 'wzgl/picture/picture/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-10 09:56:07', '2016-11-10 09:56:08', '609', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('398faf1219094815bbaa4dbf33e7cd06', 'wzgl/article/article/list', '2', '1', null, '获取文章管理页面中的文章列表数据', '2016-11-09 17:10:58', '2016-11-09 17:10:58', '647', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('39b86b097d74e15e35fa058b6c5327d0', 'wzgl/picture/picture/ajaxAdd', '0', '4', 'Request processing failed; nested exception is java.lang.NullPointerException', '-', '2016-11-08 13:13:23', '2016-11-08 13:13:24', '1159', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('39e99a37183a9416154fb36f17b80a13', 'wzgl/email/email/show', '4', '1', null, '显示邮件，邮件编号列表：f7b903af2cfa8bdae45b6ec8f47ac4b0', '2016-11-11 13:51:10', '2016-11-11 13:51:11', '736', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('39fd74f567f42bb1e65ef4ff5760e8d4', 'core/base/home', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 13:58:07', '2016-11-10 13:58:09', '1075', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('3b92eb0a0b7037f184b4b9e1d78acb61', 'core/system/attr/1cc04aff145b76d4e88198eace9796e9/edit', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 10:13:15', '2016-11-08 10:13:16', '616', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('3ba810faadebae97b3af65334db72308', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-11 11:21:48', '2016-11-11 11:21:49', '741', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('3bd57dcf4bf733617008e73f4cbaa62e', 'wzgl/email/email/add', '1', '4', 'An exception occurred processing JSP page /WEB-INF/views/wzgl/email/addEmail.jsp at line 62\r\n\r\n59:                 <div class=\"panel-body\">\r\n60:                     <form:form id=\"emailForm\" cssClass=\"form-horizontal\" modelAttribute=\"email\" method=\"post\">\r\n61:                         <form:hidden path=\"channel_parent\" />\r\n62: \r\n63: \r\n64:                         <div class=\"form-group\">\r\n65:                             <label class=\"col-sm-2 control-label text-right\">閭欢鍚嶇О锛�/label>\r\n\r\n\r\nStacktrace:', '访问框架快捷方式页面', '2016-11-11 09:22:34', '2016-11-11 09:22:35', '478', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('3becfe17cece8f9e85cb7d289061f72a', 'core/system/attr/1f8a120b8aebe5e118e2f637bddfc9f4/edit', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 10:09:52', '2016-11-08 10:09:53', '1412', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('3c12f1a7d1053b45b880cd5aba4e5422', 'wzgl/email/email/delete', '0', '4', 'Request processing failed; nested exception is org.springframework.jdbc.BadSqlGrammarException: PreparedStatementCallback; bad SQL grammar [delete from wzgl_email where emailIds in (?)]; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'emailIds\' in \'where clause\'', '-', '2016-11-11 13:33:45', '2016-11-11 13:33:48', '3214', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('3ca71b7a5fa582d64585fb537c1010cd', 'wzgl/channel/channel/9946e5db38fd8cc55ccdb02c210ce79e/edit', '1', '1', null, '访问框架快捷方式页面', '2016-11-09 15:39:12', '2016-11-09 15:39:13', '789', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('3cd32fcb4b35b393f9a0e054f3644346', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-11 13:17:22', '2016-11-11 13:17:22', '624', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('3db236fd8c1e1a1d325ccb59b6d8b54c', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-10 13:38:53', '2016-11-10 13:38:54', '612', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('3e5d36389ce1f4cb737478ff6493fea5', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-08 10:09:00', '2016-11-08 10:09:01', '565', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('3fafa5515931bedb40521ea4d9b59a7a', 'wzgl/picture/picture/cea7551020f1fee883b7e2da209e5998/ajaxMultipartAdd', '3', '1', null, '添加图片，图片编号：8cdfd05dba61dc354bfd02a818ec05c3', '2016-11-09 15:33:33', '2016-11-09 15:33:34', '1068', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('4006650f91847c24c36eb4da4452abb5', 'wzgl/reloadCash/reloadCash', '0', '1', null, '-', '2016-11-10 14:06:50', '2016-11-10 14:06:51', '454', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('40452c836958a7bd463e94fac5c23931', 'wzgl/email/email', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 11:02:03', '2016-11-11 11:02:04', '850', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('409045217b05dd87e5dbb35a5997efe5', 'wzgl/email/email/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-11 10:11:38', '2016-11-11 10:11:39', '1070', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('409b6e09871ca2bc78e416f087e29986', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-11 13:57:59', '2016-11-11 13:58:00', '721', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('40b11c4178fc46b5c9e88efdea0889a2', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-10 16:06:11', '2016-11-10 16:06:12', '453', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('40e76ef03b1480069405526cc4661a15', 'core/system/limit/', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 13:33:29', '2016-11-10 13:33:31', '1197', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('4122a7c6b853c6aeecbb6f753efc4adc', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-11 13:32:15', '2016-11-11 13:32:15', '589', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('41fb70b49abd6fd3c52f1e379322c438', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-10 16:02:32', '2016-11-10 16:02:32', '629', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('422b080e003c73c045a354b5a6547d27', 'core/system/limit', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 13:31:55', '2016-11-10 13:31:56', '1183', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('4288391b4d3e3c2644c549b6df707705', 'core/system/user/240b59bf433b7701b58ba7adb63bfde0/set_role/list', '2', '1', null, '获取设置用户关系角色页面中的角色列表数据，用户内码：240b59bf433b7701b58ba7adb63bfde0', '2016-11-08 10:01:43', '2016-11-08 10:01:43', '642', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('42dbfe26f69a709ad14b12db6b9130c3', 'core/announcement/announcement', '1', '1', null, '访问框架快捷方式页面', '2016-11-25 16:43:21', '2016-11-25 16:43:22', '494', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '0:0:0:0:0:0:0:1');
INSERT INTO `core_operation_log` VALUES ('4308ea935cf68100f22630b7fa318ae2', 'core/system/limit', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 10:10:27', '2016-11-08 10:10:27', '335', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('433afc6ebd868caa981abe8df7d44633', 'core/base/home', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 09:16:17', '2016-11-11 09:16:18', '1059', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('437bac34609826366038a83a13834fe2', 'wzgl/email/email', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 14:22:13', '2016-11-10 14:22:21', '7429', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('438b3bc1df6ec91b14e5a75aa237594d', 'wzgl/channel/channel/0f52c4016434b81ec035c75405cb747c/edit', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 10:52:56', '2016-11-08 10:52:57', '1105', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('43ad5f5106330abbef435b151e87f8e5', 'core/base/home', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 10:50:24', '2016-11-08 10:50:26', '1781', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('456a53445d6edcb6f1d5627a18c63065', 'wzgl/email/email', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 13:46:36', '2016-11-11 13:46:37', '970', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('45a097c9436eeaa414f363e6722d2514', 'wzgl/email/email/f7b903af2cfa8bdae45b6ec8f47ac4b0/edit', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 13:51:27', '2016-11-11 13:51:28', '934', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('45af26aa562abc67c9d5fdea8439b542', 'wzgl/email/email/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-11 08:35:51', '2016-11-11 08:35:52', '575', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('45d4a612d7c48299e59e57f01ef0bdb1', 'wzgl/email/email', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 13:15:43', '2016-11-11 13:15:44', '959', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('45e775176587db9b7c409112c4beca02', 'core/system/limit/7b63bbce0d9b93b106551c84dda6f126/add', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 13:34:01', '2016-11-10 13:34:02', '914', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('466a0f105bb432fb64542e01b3adeb10', 'wzgl/picture/picture/ajaxAdd', '3', '1', null, '添加图片，图片编号：bbd190d27d439961086c7a495474b8eb', '2016-11-10 10:00:28', '2016-11-10 10:00:29', '405', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('46da20d63d66101d7a138735e1baa22c', 'core/system/limit/', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 09:38:57', '2016-11-10 09:38:58', '790', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('4764cf412abca9254d814f60d641f52b', 'wzgl/album/album/add', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 09:55:23', '2016-11-10 09:55:24', '787', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('4959fe93af241afdcf8611b286a7b139', 'core/system/attr/', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 09:58:04', '2016-11-08 09:58:05', '1159', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('497bee26e936fdf7ef6592a0d2b9add1', 'wzgl/album/album/list', '2', '1', null, '获取相册管理页面中的相册列表数据', '2016-11-10 16:07:47', '2016-11-10 16:07:47', '746', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('4a2a9b0143355f328998e6ffc5f535ab', 'wzgl/picture/picture/95fd82add9086a4676c27e273be89656/ajaxMultipartAdd', '3', '1', null, '添加图片，图片编号：defa9b78a0ec0371f7cc8fc38e7585fe', '2016-11-08 15:05:51', '2016-11-08 15:05:52', '803', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('4b32b6ad93e19ec6c5c5f4389315c16c', 'wzgl/email/email/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-10 16:27:00', '2016-11-10 16:27:01', '575', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('4bbc1bd13e2e47dd46b2f5ee6405c82b', 'wzgl/email/email', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 14:44:43', '2016-11-10 14:44:44', '861', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('4bd785e27010c9f68a53ad1696d3bb9d', 'wzgl/email/email/edit', '4', '1', null, '编辑邮件（失败：数据验证错误，邮件编号：18a43a73cdee7908faa745d84c0ffcfa，邮件名称：我们的家）', '2016-11-11 11:06:56', '2016-11-11 11:07:05', '9203', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('4bf3a50e95589c75bd45d6b4577b31c9', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-08 10:54:32', '2016-11-08 10:54:33', '736', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('4c03ee835b579d519b9b0224006c1f01', 'jspc/zjpc/zjpc/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-09-02 11:28:40', '2016-09-02 11:28:41', '568', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('4c1f6989501ebf1ce7852f1219b53e0a', 'wzgl/email/email/add', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 09:23:24', '2016-11-11 09:23:25', '644', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('4c4e7d3189eb44d6d634749ec7ec4386', 'core/task/task', '1', '1', null, '访问框架快捷方式页面', '2016-11-25 16:43:16', '2016-11-25 16:43:17', '489', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '0:0:0:0:0:0:0:1');
INSERT INTO `core_operation_log` VALUES ('4e356e2864d7f51d53a77421f6a9a785', 'wzgl/email/email/list', '0', '4', 'Request processing failed; nested exception is java.lang.NullPointerException', '-', '2016-11-10 14:19:06', '2016-11-10 14:19:06', '79', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('4e4ddd455f7ca83f4dccf010d33802ea', 'wzgl/article/article', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 15:02:47', '2016-11-08 15:02:48', '1032', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('4ec05a67fc22408728575142988bb93a', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-08 09:53:37', '2016-11-08 09:53:38', '518', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('4ef34c037f21471e527a3f5f532ccbe0', 'core/base/login', '0', '2', null, '-', '2016-11-08 09:46:45', '2016-11-08 09:46:47', '1902', '-', '未知', '-', '未知', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('4f2e112363fe0a9a676462df57cda055', 'wzgl/picture/picture/ajaxAdd', '3', '1', null, '添加图片，图片编号：5eb2f7cfe0be3d1962bd8205d59fa4e9', '2016-11-10 09:56:30', '2016-11-10 09:56:30', '603', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('4fea8bf5a02eb048229d91fe6e6cef03', 'wzgl/email/email', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 11:14:31', '2016-11-11 11:14:31', '897', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('500e37d59727ec80a84121f12e814349', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-09 17:10:58', '2016-11-09 17:10:58', '639', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('502ae73fc3e404bec996435cd6d52094', 'wzgl/email/email/add', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 10:11:05', '2016-11-11 10:11:05', '310', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('511977303adc755a9895cc506f24969e', 'wzgl/email/email/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-11 13:21:36', '2016-11-11 13:21:36', '490', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('5139d4ac3b8fd0c8cee5dcad42d24392', 'core/base/login', '0', '2', null, '-', '2016-11-19 08:36:49', '2016-11-19 08:36:49', '9', '-', '未知', '-', '未知', '0:0:0:0:0:0:0:1');
INSERT INTO `core_operation_log` VALUES ('5156510c3aa20a3a675836e7e4b775e3', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-11 13:48:14', '2016-11-11 13:48:14', '514', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('51743145b231e7b565e64b36fcccbd76', 'wzgl/email/email/edit', '4', '1', null, '编辑邮件（失败：数据验证错误，邮件编号：13，邮件名称：987）', '2016-11-11 11:02:24', '2016-11-11 11:02:26', '2513', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('51fe5e882a770257a3afa1fcd647b392', 'core/system/code_table/code_table_type/list', '2', '1', null, '获取码表类别管理页面中的码表类别列表数据', '2016-11-08 10:00:34', '2016-11-08 10:00:35', '625', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('520155a4a3b380c6cb703141d725ab38', 'wzgl/email/email/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-10 15:56:04', '2016-11-10 15:56:08', '4246', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('5218ff4e27a0ba458e49115dcbd007d0', 'core/system/code_table/open_close/code_table/list', '2', '1', null, '获取码表管理页面中的码表列表数据，码表类别编号open_close', '2016-11-10 13:46:01', '2016-11-10 13:46:01', '483', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('521b2a52d11393104e1b6e72e50f0004', 'wzgl/picture/picture/ajaxAdd', '3', '1', null, '添加图片，图片编号：b49e3780e9ef4b66bfe5af4952b92ee4', '2016-11-10 09:56:46', '2016-11-10 09:56:47', '813', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('5292929fd5fcf5b50be4be1f7934a506', 'core/system/code_table/code_table_type/picture_flag/edit', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 17:42:36', '2016-11-08 17:42:37', '704', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('53096e1f6346b449a291ae8005ee75f7', 'wzgl/channel/channel/edit', '4', '1', null, '编辑栏目，栏目编号：15fdf33f6dc662d491e984206086ff85，栏目名称：null）', '2016-11-09 15:39:58', '2016-11-09 15:39:58', '309', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('532345ab929713337648584a3fdcbd0a', 'core/base/home', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 09:46:47', '2016-11-08 09:46:48', '626', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('53466c58ace702e3e075731e485f5b9a', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-10 10:01:16', '2016-11-10 10:01:16', '562', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('53dd2582c4edb2868a279b33dd54de43', 'wzgl/channel/channel/info', '1', '1', null, '获取栏目信息，栏目编号：9ce8b430b7c3cff0a4ed6ea0f2cbd975，栏目名称：保健宣传', '2016-11-09 15:43:55', '2016-11-09 15:43:55', '606', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('5446abf22dd13a0ee406936866796c5d', 'core/system/code_table/code_table_type/list', '2', '1', null, '获取码表类别管理页面中的码表类别列表数据', '2016-11-11 13:16:56', '2016-11-11 13:16:57', '701', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('5447dbf98b9096f454ec33cf69fd7f08', 'wzgl/email/email/list', '0', '4', 'Request processing failed; nested exception is java.lang.NullPointerException', '-', '2016-11-10 14:44:44', '2016-11-10 14:44:44', '126', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('54a7f10c8a75488aabc0afcdb71e4020', 'wzgl/email/email/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-11 13:50:21', '2016-11-11 13:50:22', '872', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('5507039c4109dc9dfc5b5bf21b1520b5', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-10 09:58:07', '2016-11-10 09:58:07', '492', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('550caa2a7d04294b54f6c9346167f949', 'core/base/login', '0', '2', null, '-', '2016-11-19 08:37:06', '2016-11-19 08:37:06', '13', '-', '未知', '-', '未知', '0:0:0:0:0:0:0:1');
INSERT INTO `core_operation_log` VALUES ('55aec1ebf466e8bb50f88899b0c2bd3e', 'wzgl/picture/picture/add', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 13:07:47', '2016-11-08 13:07:47', '357', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('566254e424745e2b714716a4c910a127', 'jspc/zwpc/zwpc', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 09:56:27', '2016-11-08 09:56:27', '482', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('56b2bf96919ec2f5241495defb04beae', 'wzgl/email/email/add', '3', '1', null, '新增邮件，邮件编号：c1c5b3e1c50053f2efb6fa10593a2794，邮件名称：多岁的方式', '2016-11-11 10:11:15', '2016-11-11 10:11:21', '5519', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('57663aae05097d0601ab8e41479bb141', 'core/system/attr/edit', '4', '1', null, '编辑参数，参数内码：1f8a120b8aebe5e118e2f637bddfc9f4，参数编号：page_title，参数名称：默认页面标题', '2016-11-08 10:13:29', '2016-11-08 10:13:30', '544', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('577f7f5318bd99de77a4b3c3131342d1', 'core/system/limit/info', '1', '1', null, '获取功能详细信息，功能编号：785eb202b652ef774ec4b3cce435024f，功能名称：回复', '2016-11-10 13:36:20', '2016-11-10 13:36:21', '747', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('57f6c9401d74cd448147b7c80596d7c0', 'core/system/limit/add', '3', '1', null, '新增功能，功能编号：7c0f35a345f293b46956338e5d47a6f6，功能名称：删除', '2016-11-10 13:32:34', '2016-11-10 13:32:35', '922', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('58a9aa0bc2d6020a05c8a3c6f1226c19', 'wzgl/email/email/delete', '0', '4', 'Request processing failed; nested exception is org.springframework.jdbc.BadSqlGrammarException: PreparedStatementCallback; bad SQL grammar [delete from wzgl_email where emailIds in (?)]; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'emailIds\' in \'where clause\'', '-', '2016-11-11 13:26:58', '2016-11-11 13:26:59', '877', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('59aa7a34b3e52d08d0a177259beff230', 'wzgl/email/email', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 17:29:37', '2016-11-10 17:29:38', '1294', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('59d987207d664acf4ea576e04a7af896', 'wzgl/email/email', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 14:18:56', '2016-11-10 14:19:05', '8985', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('59fd951105eaaaf0f1c42d1fa7bf64d6', 'wzgl/email/email/edit', '0', '4', 'Request processing failed; nested exception is org.springframework.jdbc.BadSqlGrammarException: PreparedStatementCallback; bad SQL grammar [update　wzgl_email set email_title=?,email_content=?,email_flag=? email_askDate=?,email_askPerson=?,email_replyContent=? email_replyUser=?,email_replyDate=? where email_id=?]; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'update　wzgl_email set email_title=\'1212\',email_content=\'分v的分的v得分v\' at line 1', '-', '2016-11-11 10:44:22', '2016-11-11 10:44:59', '36867', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('5ac384654d25fd137b0eccc3ee3dec2e', 'wzgl/email/email', '1', '1', null, '访问用户管理页面', '2016-11-10 13:52:35', '2016-11-10 13:52:39', '3548', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('5b3a8fc9236deccc0e9001a40d5d85cf', 'wzgl/email/email/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-11 13:23:59', '2016-11-11 13:24:00', '639', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('5b86a282073c5f31ee7d0c3208e004a6', 'core/base/home', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 15:02:44', '2016-11-08 15:02:45', '1183', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('5c944ab4264e7041e2f0addbf4559d42', 'core/system/system', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 09:58:50', '2016-11-08 09:58:51', '1098', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('5cd17b6a752f418d146f65418e8908ef', 'wzgl/picture/picture', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 13:07:45', '2016-11-08 13:07:45', '319', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('5cda329659f59e884e7e62a3b41da62a', 'core/system/code_table/code_table_type/list', '2', '1', null, '获取码表类别管理页面中的码表类别列表数据', '2016-11-10 11:08:04', '2016-11-10 11:08:05', '780', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('5d0a93c5b50cc79ae709be4169243c81', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-11 13:31:58', '2016-11-11 13:31:58', '795', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('5d5a45d3203443f1637500b32c214aea', 'wzgl/picture/picture/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-10 09:57:35', '2016-11-10 09:57:35', '520', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('5d64f85096e90b112285a9f6877bc063', 'core/system/code_table/open_close/code_table/list', '2', '1', null, '获取码表管理页面中的码表列表数据，码表类别编号open_close', '2016-11-10 13:51:36', '2016-11-10 13:51:37', '613', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('5f0c62120a15bf84ef8a282083b0ec1f', 'core/base/home', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 09:45:36', '2016-11-08 09:45:37', '1267', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('5fef1807a0168a8e834423f6d7e91d39', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-09-02 11:19:29', '2016-09-02 11:19:30', '438', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('6018c384f655505acc25ac48f72faa2b', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-09 15:45:25', '2016-11-09 15:45:26', '623', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('617c6e3e48580754eae2ed79597756c0', 'core/system/user/240b59bf433b7701b58ba7adb63bfde0/edit', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 10:38:46', '2016-11-11 10:38:47', '423', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('6192ef4e16aba4035114b34a33474df4', 'wzgl/email/email/delete', '0', '4', 'Request processing failed; nested exception is org.springframework.jdbc.BadSqlGrammarException: PreparedStatementCallback; bad SQL grammar [delete from wzgl_email where emailIds in (?)]; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'emailIds\' in \'where clause\'', '-', '2016-11-11 13:35:22', '2016-11-11 13:35:23', '181', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('61ec625d8c3b285475b938e1868bcfa4', 'wzgl/email/email/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-11 13:48:14', '2016-11-11 13:48:14', '522', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('62859e4c08a5d8698f6e20f412587079', 'core/system/limit', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 09:34:47', '2016-11-10 09:34:48', '426', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('62a268496d0769fb4e26e8d9b86b3066', 'core/system/system', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 09:38:10', '2016-11-10 09:38:10', '583', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('62f04e7e32344a600e6ebca0c00e1a94', 'core/base/home', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 14:21:14', '2016-11-10 14:21:15', '1186', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('63a0e12db7892175c2e5c1d952c0f5c6', 'core/base/home', '1', '1', null, '访问框架快捷方式页面', '2016-11-19 08:36:45', '2016-11-19 08:36:46', '404', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '0:0:0:0:0:0:0:1');
INSERT INTO `core_operation_log` VALUES ('641592fd70d3fc06a4b1c55f9d802ff6', 'core/system/user/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-10 13:36:34', '2016-11-10 13:36:35', '544', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('644ebc5323fd05f0e4b17708c308468d', 'wzgl/email/email/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-10 17:14:19', '2016-11-10 17:14:20', '613', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('648339a37fb79fb4fb6776c86787710c', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-10 17:38:59', '2016-11-10 17:38:59', '459', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('648749f486ab48d2bed09affb462de10', 'core/base/home', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 13:52:31', '2016-11-10 13:52:32', '1067', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('649a830c49194c5d198eb136cb09af20', 'wzgl/email/email', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 14:19:28', '2016-11-10 14:19:33', '5392', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('66ac22821b6d1b8a103fb007ca7073a0', 'wzgl/picture/picture/cea7551020f1fee883b7e2da209e5998/ajaxMultipartAdd', '3', '1', null, '添加图片，图片编号：1fe793aa9a2c9728dbe362aefdabacdf', '2016-11-09 15:33:33', '2016-11-09 15:33:34', '1056', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('670397bd57558d4dfbe41f9153e38dfd', 'wzgl/email/email', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 09:18:15', '2016-11-11 09:18:15', '407', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('678b227add7311e2205da1100b2a3960', 'wzgl/email/email/13/edit', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 11:02:05', '2016-11-11 11:02:06', '336', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('67c70046703e9e8d0587f1fb90ed3c15', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-08 09:46:08', '2016-11-08 09:46:09', '772', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('683ee5e3393cd78002598029dc4e3e06', 'core/system/user', '1', '1', null, '访问框架快捷方式页面', '2016-11-25 16:45:12', '2016-11-25 16:45:12', '429', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '0:0:0:0:0:0:0:1');
INSERT INTO `core_operation_log` VALUES ('6880c46afb51cd2c3e97e258bb7b980f', 'wzgl/email/email', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 10:44:13', '2016-11-11 10:44:14', '1169', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('68d7dcc5f68f4252d387c0ceec8d8dea', 'core/system/limit/b5d40cd48bdb1a3877426c0c842144b1/add', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 09:35:38', '2016-11-10 09:35:38', '798', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('69105a46c2a595b26f755bd279487efb', 'wzgl/email/email', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 14:21:15', '2016-11-10 14:21:19', '4503', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('6928a73c4d95d22c2811aa0876454635', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-08 09:47:01', '2016-11-08 09:47:01', '553', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('69619e4ff0861fe8de58e987fdb8d5e2', 'wzgl/email/email/add', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 13:47:10', '2016-11-11 13:47:10', '361', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('698413311e4d8d40e06c4e4fe6b562ee', 'core/system/role/list', '2', '1', null, '获取角色管理页面中的角色列表数据', '2016-11-08 10:12:25', '2016-11-08 10:12:26', '479', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('69e1f9bc0da545b546243b2719210d48', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-08 10:10:19', '2016-11-08 10:10:20', '774', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('6a75db2307d5341be2a549d4d216ddb1', 'core/log/sql_log/list', '2', '1', null, '获取SQL日志列表数据', '2016-09-02 11:19:30', '2016-09-02 11:19:30', '435', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('6a77a646c8c975099b62da9146f6c159', 'wzgl/album/album/list', '2', '1', null, '获取相册管理页面中的相册列表数据', '2016-11-10 14:09:35', '2016-11-10 14:09:35', '643', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('6ab67b693afc2895ffbe637a79b331bb', 'wzgl/email/email', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 16:05:08', '2016-11-10 16:05:09', '840', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('6c85f7d82c4b238c8c60f01e25bc2090', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-10 14:06:46', '2016-11-10 14:06:47', '682', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('6d95c6ea4951350de87b3d6faeccae36', 'wzgl/email/email/list', '0', '4', 'Request processing failed; nested exception is java.lang.NullPointerException', '-', '2016-11-10 14:24:36', '2016-11-10 14:24:36', '52', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('6df8d81a8d68cb3252f005ce4348ce4a', 'core/base/login', '0', '2', null, '-', '2016-11-13 17:34:58', '2016-11-13 17:34:59', '242', '-', '未知', '-', '未知', '0:0:0:0:0:0:0:1');
INSERT INTO `core_operation_log` VALUES ('6e04cb70eb64098ae64d84e7ae241a08', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-10 14:19:06', '2016-11-10 14:19:07', '1129', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('6e64936361b6ac13c0621171f0e0d9ce', 'wzgl/email/email', '0', '2', null, '-', '2016-11-10 14:44:35', '2016-11-10 14:44:35', '1', '-', '未知', '-', '未知', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('6e6a7bce5ce1230945ab73c62917d7dc', 'core/system/limit/', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 13:33:10', '2016-11-10 13:33:10', '642', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('6f227bf76045ea88bc46503e08cc367f', 'core/system/code_table/picture_flag/code_table', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 17:42:45', '2016-11-08 17:42:45', '704', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('6fb6c741f2f8730406c9bbc7dd3c2992', 'core/system/code_table', '1', '1', null, '访问框架快捷方式页面', '2016-11-25 16:44:57', '2016-11-25 16:44:58', '383', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '0:0:0:0:0:0:0:1');
INSERT INTO `core_operation_log` VALUES ('6fd17230c15426917fff93483a1427ff', 'wzgl/email/email', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 14:19:26', '2016-11-10 14:19:31', '4559', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('6fd3c60a4bbea43d37e93872b30cc983', 'core/system/code_table/open_close/code_table', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 13:46:00', '2016-11-10 13:46:01', '821', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('71e36e40011c971c9528f71bb656f81c', 'wzgl/email/email/show', '4', '1', null, '显示邮件，邮件编号列表：18a43a73cdee7908faa745d84c0ffcfa', '2016-11-11 12:29:54', '2016-11-11 12:29:55', '565', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('728eadfda93cbd32661023b0c16c8751', 'core/system/limit/7c0f35a345f293b46956338e5d47a6f6/edit', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 13:32:51', '2016-11-10 13:32:51', '309', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('72e0fa1c9c875e57d4cbf1f1d242ee9d', 'wzgl/picture/picture', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 10:12:43', '2016-11-08 10:12:43', '619', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('7317f21a166b5b2e14e9c97aa38e469a', 'core/system/limit/7b63bbce0d9b93b106551c84dda6f126/add', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 13:35:40', '2016-11-10 13:35:41', '576', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('733cbdd1f52674e53a49b30c7439db1d', 'tdjs/tdjsAlbum/album/list', '2', '1', null, '获取相册管理页面中的相册列表数据', '2016-09-02 11:34:58', '2016-09-02 11:34:59', '523', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('74062aef5d8b086dbc9493f43725e31c', 'wzgl/picture/picture', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 13:41:25', '2016-11-11 13:41:26', '849', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('74117cdfceb279d9c312da8ab6cc8fe8', 'core/system/attr/11fb2963e45cee147ed533a707360a08/edit', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 13:14:00', '2016-11-08 13:14:01', '967', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('74a931adcf3e1cd47fcc467c03f53ec5', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-10 09:58:21', '2016-11-10 09:58:21', '477', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('74d3b50bb6bd42d5360641ccbf2aa9e5', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-08 09:56:22', '2016-11-08 09:56:23', '537', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('75561ac70355e52c706cc69a76d11715', 'wzgl/picture/picture/de03fea0d9bf4b930342d011f3942607/edit', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 09:54:52', '2016-11-10 09:54:53', '885', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('75d9e4f4b35b7c0643e27acfc7e27412', 'wzgl/email/email', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 17:21:08', '2016-11-10 17:21:09', '850', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('76317b8289185b1d5602a7aa2f75fa10', 'wzgl/picture/picture/ajaxAdd', '0', '4', 'Request processing failed; nested exception is java.lang.NullPointerException', '-', '2016-11-08 13:08:12', '2016-11-08 13:08:15', '2105', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('7652bbf399d099508e673172d26cc7e6', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-10 09:37:01', '2016-11-10 09:37:01', '456', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('777fca3fec1ae1e51034ae0b350a7c27', 'wzgl/email/email/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-11 11:23:44', '2016-11-11 11:23:45', '658', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('780e635a486931dbe0c0bd1829338348', 'core/base/home', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 14:44:41', '2016-11-10 14:44:42', '954', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('78dc7002c9f35c18b06bc0154c4fe49a', 'core/base/home', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 10:32:15', '2016-11-11 10:32:16', '1019', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('78f379eace134209d605882a1e46cfbd', 'wzgl/email/email/add', '0', '4', 'Request processing failed; nested exception is org.springframework.jdbc.BadSqlGrammarException: PreparedStatementCallback; bad SQL grammar [insert wzgl_email (email_id,email_title,email_content,email_flag,email_askDate,email_askPerson,email_replyContent,email_replyUser,email_replyDate) values (?,?,?,?,?,?, )?,?,? ]; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \')\'好的\',\'240b59bf433b7701b58ba7adb63bfde0\',\'2016-11-11 10:06:09.88\'\' at line 1', '-', '2016-11-11 10:06:08', '2016-11-11 10:07:03', '54890', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('79c315f24805a29a695fae0650121988', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-08 10:10:40', '2016-11-08 10:10:41', '810', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('7b7793446b84dff86198708435ddc49d', 'wzgl/email/email/13/edit', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 10:44:15', '2016-11-11 10:44:16', '353', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('7bb94894cfb53c9361c0ae8b1df3ddb2', 'core/base/home', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 10:07:55', '2016-11-08 10:07:55', '564', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('7c398b9e6ed2cf28febbf531e1af6df9', 'wzgl/channel/channel', '1', '1', null, '访问框架快捷方式页面', '2016-11-09 15:46:32', '2016-11-09 15:46:33', '761', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('7cbaef73e0473580dacfefd17732e3a3', 'wzgl/picture/picture/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-10 09:58:07', '2016-11-10 09:58:07', '498', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('7cd06725a64d933c1b493157459f7cc1', 'core/system/role/list', '2', '1', null, '获取角色管理页面中的角色列表数据', '2016-11-08 10:12:01', '2016-11-08 10:12:02', '678', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('7ce8745915375b4d66b3a1be6b6c2ec1', 'wzgl/picture/picture/ajaxAdd', '3', '1', null, '添加图片，图片编号：d4ddc68a0fcc6b4a196f16ed2e7df1ca', '2016-11-10 09:56:06', '2016-11-10 09:56:07', '785', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('7d6857aea08b8d65ac1c24ccd1d9a624', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-08 16:36:20', '2016-11-08 16:36:20', '486', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('7f518db09e97ec7e78c49ed65e34a58f', 'core/system/attr/list', '2', '1', null, '获取参数管理页面的参数列表数据', '2016-11-08 09:56:30', '2016-11-08 09:56:31', '665', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('7f6be43776237e4de86ebe6a42098959', 'wzgl/picture/picture/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-10 09:57:20', '2016-11-10 09:57:21', '529', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('7f74cae99ff90b1f45762a4b38d673d7', 'core/system/limit', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 14:19:16', '2016-11-10 14:19:17', '565', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('8032d6cb7f1e4dd20595aa21207ade98', 'core/system/code_table', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 17:09:26', '2016-11-10 17:09:26', '402', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('807c0d2a96af39e7258f1c2b05c26a2b', 'core/base/home', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 17:42:15', '2016-11-08 17:42:17', '1161', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('80d506962c5f683f404fd14ccd60208c', 'core/base/login', '0', '2', null, '-', '2016-11-19 09:41:16', '2016-11-19 09:41:16', '467', '-', '未知', '-', '未知', '0:0:0:0:0:0:0:1');
INSERT INTO `core_operation_log` VALUES ('80fcb52f5c395fb3b0f787e8b386304e', 'wzgl/email/email/list', '0', '4', 'Request processing failed; nested exception is java.lang.NullPointerException', '-', '2016-11-10 14:25:31', '2016-11-10 14:25:31', '113', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('819050d408f7738afb188832f9617d9d', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-08 09:48:32', '2016-11-08 09:48:33', '633', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('81a0b3173b90be91044088c0164deb4c', 'core/system/attr', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 13:13:56', '2016-11-08 13:13:57', '391', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('823f2d11792182ad53ed181f0a0c08fa', 'core/system/attr/list', '2', '1', null, '获取参数管理页面的参数列表数据', '2016-11-08 09:46:08', '2016-11-08 09:46:09', '782', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('8298080cc1e753c1e6d5097fd2b2c003', 'core/base/home', '1', '1', null, '访问框架快捷方式页面', '2016-11-19 08:33:32', '2016-11-19 08:33:35', '2218', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '0:0:0:0:0:0:0:1');
INSERT INTO `core_operation_log` VALUES ('82da78d76cee671b05916e82146ebdf7', 'wzgl/channel/channel/add', '3', '1', null, '新增栏目，栏目编号：ef79efab70b25d9fba5edcbfea82f2f1，栏目名称：班级论坛', '2016-11-08 10:55:20', '2016-11-08 10:55:20', '615', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('8405ad76ae6f30f7114e1a79536ed124', 'wzgl/email/email/edit', '4', '1', null, '编辑邮件（失败：数据验证错误，邮件编号：13，邮件名称：987）', '2016-11-11 11:02:09', '2016-11-11 11:02:15', '5065', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('847d2464ba0bdb61f2f7614decb98501', 'wzgl/channel/channel', '1', '1', null, '访问框架快捷方式页面', '2016-11-09 15:40:45', '2016-11-09 15:40:46', '680', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('847fe15a66cd8b7e223132ce24b0be59', 'wzgl/picture/picture/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-10 10:00:29', '2016-11-10 10:00:30', '913', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('84903071b1e9b17c073cc95df2f74cf0', 'wzgl/channel/channel/ee73c789e3211f14f0b893fb962cc61a/edit', '1', '1', null, '访问框架快捷方式页面', '2016-11-09 15:40:26', '2016-11-09 15:40:27', '977', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('857cbd76083f1c78fafc1647869c83a4', 'core/base/login', '0', '2', null, '-', '2016-11-10 14:47:43', '2016-11-10 14:47:43', '155', '-', '未知', '-', '未知', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('85b934b9a93922d7fc8a788ab874a2de', 'wzgl/email/email/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-11 13:37:37', '2016-11-11 13:37:37', '543', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('868c909e2c788401840b0edbd980fd0b', 'core/base/home', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 10:10:58', '2016-11-11 10:10:59', '1128', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('870649766968a89309b3f536c5855655', 'wzgl/picture/picture/3fb6c79f9f5f28c796ab14b3ef343150/ajaxMultipartAdd', '3', '1', null, '添加图片，图片编号：d9f454a82932f8d9b0f3594e40b42f65', '2016-11-08 15:05:19', '2016-11-08 15:05:21', '1489', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('8845b2983fc70edc3edd2bf790159de6', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-10 17:14:19', '2016-11-10 17:14:20', '611', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('886c1277e3e0aeeff2cf0b5a1faba942', 'core/system/code_table', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 09:56:23', '2016-11-08 09:56:24', '464', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('889cd41ea0ce4f0168b0873af9262dff', 'wzgl/email/email/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-11 13:08:28', '2016-11-11 13:08:28', '562', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('896f614e364859e363ebb9dcbe48c680', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-09-02 11:30:59', '2016-09-02 11:31:00', '468', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('8a03938b7075d7daea577a0335c03125', 'core/base/home', '1', '1', null, '访问框架快捷方式页面', '2016-11-13 17:34:51', '2016-11-13 17:34:51', '304', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '0:0:0:0:0:0:0:1');
INSERT INTO `core_operation_log` VALUES ('8a2a067df8c098d9a5b457967c4ba75b', 'core/system/limit/info', '1', '1', null, '获取功能详细信息，功能编号：702dec278b1d43401237d892dc097532，功能名称：展示', '2016-11-10 09:37:46', '2016-11-10 09:37:47', '654', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('8b7c1f76267f42c4ae804efd0fccc663', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-09 15:40:05', '2016-11-09 15:40:05', '547', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('8b9cdd5045a3ecaa7be96c33a68f97b8', 'core/system/attr/add', '1', '1', null, '访问框架快捷方式页面', '2016-11-19 08:44:26', '2016-11-19 08:44:26', '357', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '0:0:0:0:0:0:0:1');
INSERT INTO `core_operation_log` VALUES ('8eaa810388cedcf023260b752bad5acd', 'wzgl/reloadCash/reloadCash', '0', '1', null, '-', '2016-11-09 16:03:32', '2016-11-09 16:03:32', '327', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('8fa305d374a51b53f7c49ade6eea9619', 'core/base/home', '1', '1', null, '访问框架快捷方式页面', '2016-11-25 16:32:19', '2016-11-25 16:32:20', '622', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '0:0:0:0:0:0:0:1');
INSERT INTO `core_operation_log` VALUES ('8fc25e0538ded3f97d8cf025bcc0ae8d', 'wzgl/channel/channel', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 10:53:32', '2016-11-08 10:53:33', '629', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('8feb71e3106fea8f2018245bbf5ffaa9', 'wzgl/channel/channel/top/add', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 10:50:38', '2016-11-08 10:50:38', '332', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('9050207d27499e7a69439a3879006da4', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-09 15:33:38', '2016-11-09 15:33:38', '801', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('90a5fbbb265b7acef45652c2a7ab26b7', 'core/base/home', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 13:31:49', '2016-11-10 13:31:50', '1210', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('9119ea3313c4953cc00cee70972da523', 'core/team/selectOneTeam', '1', '1', null, '访问选择团队页面', '2016-11-08 09:58:40', '2016-11-08 09:58:41', '1123', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('91f8f79a92cb55b00f976cd241e10ab2', 'core/system/limit/7b63bbce0d9b93b106551c84dda6f126/add', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 13:32:11', '2016-11-10 13:32:12', '314', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('92046cdadc0c8401f6668e7a981df8fd', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-08 10:12:55', '2016-11-08 10:12:56', '472', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('9243c9874969d34b6fe1673bfd4f15d5', 'core/base/home', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 09:34:43', '2016-11-10 09:34:44', '1134', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('9261c513940bd1fc279e9ab3959e8ca2', 'wzgl/picture/picture/cea7551020f1fee883b7e2da209e5998/ajaxMultipartAdd', '3', '1', null, '添加图片，图片编号：78f269ebd158856f87b9eb5b1adfb091', '2016-11-09 15:33:34', '2016-11-09 15:33:35', '803', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('92c957a61a8b7f79659301a830442a78', 'wzgl/email/email/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-11 13:08:18', '2016-11-11 13:08:18', '501', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('942e54aec14e8a12c7194c99acc23c8f', 'wzgl/email/email/hide', '4', '1', null, '隐藏邮件，邮件编号列表：18a43a73cdee7908faa745d84c0ffcfa', '2016-11-11 13:20:35', '2016-11-11 13:20:36', '679', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('949957a83c37c97936d245c0b6d483d2', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-08 10:11:51', '2016-11-08 10:11:52', '958', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('94d9d79883ad2a19a36784e1c5e2f7fb', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-10 13:56:15', '2016-11-10 13:56:15', '557', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('94e534d9a1c08fe8f557c2f1eec94185', 'core/system/attr/list', '2', '1', null, '获取参数管理页面的参数列表数据', '2016-11-08 10:12:55', '2016-11-08 10:12:56', '476', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('9660e555f3b5c218552d22c9eaeb8bd1', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-10 13:58:23', '2016-11-10 13:58:24', '779', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('97dc26c1c5a3adbe1307891a15384d58', 'wzgl/article/article', '1', '1', null, '访问框架快捷方式页面', '2016-11-09 15:33:05', '2016-11-09 15:33:07', '1451', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('97e7f5fc23d9c9e38c3fbc671751a9c7', 'wzgl/picture/picture/cea7551020f1fee883b7e2da209e5998/ajaxMultipartAdd', '3', '1', null, '添加图片，图片编号：d83a08a10576f448667235004bbf5cce', '2016-11-09 15:33:31', '2016-11-09 15:33:33', '1974', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('98482c0360681fe1cbb84b6d57db30f6', 'wzgl/article/article/add', '3', '1', null, '新增文章，文章编号：cea7551020f1fee883b7e2da209e5998，文章标题：哈哈', '2016-11-09 15:33:28', '2016-11-09 15:33:30', '2388', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('9863dc46d2e71c66d185e5b25bb8a3b3', 'wzgl/video/video', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 15:23:33', '2016-11-10 15:23:34', '1185', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('98962f28c47da4f932bf59f388f298be', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-08 10:12:47', '2016-11-08 10:12:48', '426', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('995c4d12e4b98ccaf75582752aeaa2e7', 'wzgl/picture/picture/ajaxAdd', '3', '1', null, '添加图片，图片编号：9f3b3f9dd0fb15a638a30e53cb78261a', '2016-11-10 10:01:40', '2016-11-10 10:01:40', '556', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('99fed45fe6d553f46b8afa1ffe0c31eb', 'core/system/attr/list', '2', '1', null, '获取参数管理页面的参数列表数据', '2016-11-08 10:09:34', '2016-11-08 10:09:34', '639', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('9a03e5d3a86204928ecea32164f6e705', 'wzgl/email/email', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 14:25:09', '2016-11-10 14:25:30', '21301', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('9a50b9c6b7babc7881a4c2670137f51d', 'core/base/home', '1', '1', null, '访问框架快捷方式页面', '2016-11-13 17:06:41', '2016-11-13 17:06:42', '1161', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '0:0:0:0:0:0:0:1');
INSERT INTO `core_operation_log` VALUES ('9a6cbaa8fdc27c27320f2de19dd0ad33', 'wzgl/email/email/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-11 13:25:50', '2016-11-11 13:25:54', '3986', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('9acea10bbbcb6ff3c2e92d5076b189b4', 'core/system/attr/cd7e2c0b35703c5c33c058b4bf3a9f4e/edit', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 13:19:55', '2016-11-08 13:19:56', '640', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('9b076a5f2c5c670c3bd439210cb470e7', 'core/base/extra/task/listener', '0', '2', null, '-', '2016-11-08 09:46:39', '2016-11-08 09:46:39', '0', '-', '未知', '-', '未知', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('9b0ab3e40d41911c70214152b1405413', 'wzgl/reloadCash/reloadCash', '0', '1', null, '-', '2016-11-09 15:54:03', '2016-11-09 15:54:04', '346', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('9ba1de3288d55669eaff69564eea6eb8', 'wzgl/email/email/show', '4', '1', null, '显示邮件，邮件编号列表：f7b903af2cfa8bdae45b6ec8f47ac4b0', '2016-11-11 13:48:40', '2016-11-11 13:48:41', '628', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('9c52bfcdf6089912f2ac12d2713c89d2', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-08 09:46:23', '2016-11-08 09:46:24', '909', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('9d66a6ac5e791dd54b3e31f519c6997c', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-10 17:46:53', '2016-11-10 17:46:53', '506', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('9df89fcdfde47d6a83816627b504bbf5', 'wzgl/picture/picture/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-10 09:59:08', '2016-11-10 09:59:09', '920', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('9e4c8ecd97ef3ce40832cc0701ceba67', 'core/system/attr/list', '2', '1', null, '获取参数管理页面的参数列表数据', '2016-11-08 10:11:51', '2016-11-08 10:11:52', '962', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('9e91e6d20ff56b1aa9c155b0b12afb6d', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-10 13:36:44', '2016-11-10 13:36:45', '804', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('9eaa1a5ea0a7d27eceda977fe7db3cfb', 'wzgl/email/email', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 10:05:31', '2016-11-11 10:05:32', '937', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('9f34e41133be6af10dac4982c19b0c0b', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-08 10:12:01', '2016-11-08 10:12:02', '670', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('9f8fc4a9a7485c21c8721253925a515a', 'wzgl/picture/picture/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-10 10:01:16', '2016-11-10 10:01:16', '570', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('9fbf8e6065522bc16affd14a44c8eafe', 'wzgl/email/email', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 13:43:43', '2016-11-11 13:43:44', '804', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('9fcf97d16c72072b9e7584b2bff2b602', 'core/base/home', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 10:07:46', '2016-11-08 10:07:47', '732', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('9fd38a289510ec60ef72c24439905704', 'core/system/role/list', '2', '1', null, '获取角色管理页面中的角色列表数据', '2016-11-10 13:36:52', '2016-11-10 13:36:53', '610', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('a012d18fd10db270c17a3ea48ef30666', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-08 10:53:52', '2016-11-08 10:53:53', '761', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('a0a1b698d485bdfae468fc35948dbbc6', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-08 10:03:00', '2016-11-08 10:03:01', '464', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('a0b124d6ddd48118b96181aaa76707aa', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-10 17:22:44', '2016-11-10 17:22:46', '1041', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('a0f4bf2fb44b3ff6bcf8642b5c33436a', 'wzgl/channel/channel/top/add', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 10:51:54', '2016-11-08 10:51:54', '591', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('a1a4ce3b9a8b24e949bc529e79b35cef', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-11 11:23:44', '2016-11-11 11:23:45', '1091', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('a1c10b35fc086ed9ac5e380fd95314c3', 'wzgl/picture/picture/cea7551020f1fee883b7e2da209e5998/ajaxMultipartAdd', '3', '1', null, '添加图片，图片编号：89ca504392b2344e7e06db7502590711', '2016-11-09 15:33:34', '2016-11-09 15:33:34', '415', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('a1e1a58c2f396edfcb33baaebb47f391', 'wzgl/picture/picture/ajaxAdd', '3', '1', null, '添加图片，图片编号：f0b26292d80af150254aab7cd3ae7b6d', '2016-11-10 09:59:26', '2016-11-10 09:59:27', '401', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('a1f5850819099d396828f2751a31c392', 'core/system/user', '1', '1', null, '访问框架快捷方式页面', '2016-11-19 08:46:03', '2016-11-19 08:46:03', '409', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '0:0:0:0:0:0:0:1');
INSERT INTO `core_operation_log` VALUES ('a1fa37c632859eae191e678aec476c08', 'wzgl/channel/channel/info', '1', '1', null, '获取栏目信息，栏目编号：3ca97e31fca2abda3124f686f1efe69c，栏目名称：友情链接', '2016-11-10 11:24:22', '2016-11-10 11:24:23', '701', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('a259b92cfa0f075498b83ca3471eb256', 'wzgl/album/album/list', '2', '1', null, '获取相册管理页面中的相册列表数据', '2016-11-10 17:12:12', '2016-11-10 17:12:13', '739', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('a25b4fb7ec15b70ea8fec2a5aaaf880a', 'wzgl/email/email/show', '4', '1', null, '显示邮件，邮件编号列表：c1c5b3e1c50053f2efb6fa10593a2794', '2016-11-11 13:05:52', '2016-11-11 13:05:53', '618', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('a2bdb3c93394f60f99bc6ac0c8fb8fae', 'wzgl/email/email/f7b903af2cfa8bdae45b6ec8f47ac4b0/edit', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 13:50:14', '2016-11-11 13:50:15', '856', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('a2c9475b220d4764b16b7ca16fc95514', 'wzgl/picture/picture/cea7551020f1fee883b7e2da209e5998/ajaxMultipartAdd', '3', '1', null, '添加图片，图片编号：1a70f49465028241f3c04342506374e0', '2016-11-09 15:33:33', '2016-11-09 15:33:34', '1055', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('a2f51ed180a50f97369b8ec7a86c97ed', 'wzgl/channel/channel/d435d9dc1c78e6e701c6aa34849eafa2/edit', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 11:23:18', '2016-11-10 11:23:19', '663', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('a361bb84401531d93690d4c8716988e4', 'core/system/limit/info', '1', '1', null, '获取功能详细信息，功能编号：7b63bbce0d9b93b106551c84dda6f126，功能名称：园长邮箱', '2016-11-10 09:37:07', '2016-11-10 09:37:07', '450', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('a385163ec7af0cc0a3cc7f1e30feb67c', 'wzgl/email/email/list', '0', '4', 'Request processing failed; nested exception is java.lang.NullPointerException', '-', '2016-11-10 14:22:23', '2016-11-10 14:22:23', '250', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('a47c320841a7cb2ab0663dfad2930fdc', 'wzgl/video/video', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 16:06:01', '2016-11-08 16:06:02', '1188', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('a49389d9d0ab67e68183626cd5128967', 'wzgl/picture/picture/cea7551020f1fee883b7e2da209e5998/ajaxMultipartAdd', '3', '1', null, '添加图片，图片编号：9e0d72370de457c15d3f52504a45c4e7', '2016-11-09 15:33:31', '2016-11-09 15:33:33', '1941', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('a4ceb4f208fc79c03a134fd710cc0dab', 'core/system/user/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-08 10:11:08', '2016-11-08 10:11:08', '621', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('a540d022ba6791b40b3fe7f3108ddd6c', 'wzgl/picture/picture/ajaxAdd', '3', '1', null, '添加图片，图片编号：08263004617e56761f9feec8d8c7bc00', '2016-11-10 09:57:34', '2016-11-10 09:57:34', '626', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('a5db2179ed1c23322ad67996718834d1', 'wzgl/email/email/add', '0', '4', 'Request processing failed; nested exception is org.springframework.jdbc.BadSqlGrammarException: PreparedStatementCallback; bad SQL grammar [insert wzgl_email (email_id,email_title,email_content,email_flag,email_askDate,email_askPerson,email_replyContent,email_replyUser,email_replyDate) values (?,?,?,?,?,?, )?,?,? ]; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \')\'好的\',\'240b59bf433b7701b58ba7adb63bfde0\',\'2016-11-11 10:05:51.862\'\' at line 1', '-', '2016-11-11 10:05:49', '2016-11-11 10:06:04', '14721', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('a5e6ff83a8bbbbcfb3ef51f2ca23488f', 'wzgl/channel/channel', '1', '1', null, '访问框架快捷方式页面', '2016-11-09 15:36:58', '2016-11-09 15:36:59', '1110', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('a61a887aac866c98c52a565bdb6caab5', 'core/system/code_table', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 09:53:31', '2016-11-10 09:53:32', '328', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('a62afa371b71d60cd328da15e2c2ba97', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-08 10:14:39', '2016-11-08 10:14:40', '668', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('a7145d1b757c087e9c5bffb26c718c61', 'wzgl/channel/channel', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 10:54:52', '2016-11-08 10:54:54', '1421', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('a7148821e3e11256f07056d71e6d79a0', 'wzgl/email/email/add', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 10:05:34', '2016-11-11 10:05:34', '311', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('a8a29be328a8a049b120a91da5e97b43', 'core/base/home', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 09:58:15', '2016-11-08 09:58:16', '679', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('a8bafa186fbc665d084eb6a0cfa4ebfb', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-08 09:56:30', '2016-11-08 09:56:30', '385', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('a97cb0f779bb44100a73cd0d844bef74', 'wzgl/channel/channel/info', '1', '1', null, '获取栏目信息，栏目编号：0f52c4016434b81ec035c75405cb747c，栏目名称：院所简介', '2016-11-09 15:38:59', '2016-11-09 15:39:00', '591', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('aad373a4a702933b120c3660221d52af', 'core/base/home', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 13:56:15', '2016-11-10 13:56:15', '319', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('ab67640c7f070621b0911b02ecb7b78a', 'wzgl/channel/channel', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 10:50:36', '2016-11-08 10:50:37', '1130', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('ab98059a9ec42de2e5c0f0ee8f3243d9', 'wzgl/email/email/delete', '5', '1', null, '删除邮件，邮件编号列表：c1c5b3e1c50053f2efb6fa10593a2794', '2016-11-11 13:46:49', '2016-11-11 13:46:54', '4990', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('abb2d28035376d008539d680a04a0c5f', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-09 15:37:53', '2016-11-09 15:37:53', '494', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('ae7554d69e09ec4399d1f077968efb27', 'wzgl/channel/channel/0f52c4016434b81ec035c75405cb747c/add', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 10:51:11', '2016-11-08 10:51:11', '587', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('aef9e3e48caa6f649c2babbbe7eaa005', 'core/system/code_table/open_close/code_table', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 17:09:32', '2016-11-10 17:09:32', '749', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('af01cbd8b744bd06197011f1363b7e02', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-08 17:43:12', '2016-11-08 17:43:13', '406', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('affafa7b45388079cee6dfcdedba38c4', 'wzgl/channel/channel/info', '0', '2', null, '-', '2016-11-10 14:47:41', '2016-11-10 14:47:41', '0', '-', '未知', '-', '未知', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('b0455127b2ff7dd7a7d1dcd03ca1344b', 'core/system/code_table/code_table_type/list', '2', '1', null, '获取码表类别管理页面中的码表类别列表数据', '2016-11-08 10:13:01', '2016-11-08 10:13:02', '396', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('b047ed5b240de662c44d17bce851bc37', 'core/system/attr/list', '2', '1', null, '获取参数管理页面的参数列表数据', '2016-11-09 15:51:10', '2016-11-09 15:51:11', '863', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('b29ae24ccf621da3a593be77a680a89d', 'wzgl/picture/picture/ajaxAdd', '3', '1', null, '添加图片，图片编号：e6c0596daa0128cc419ec4d689b9c490', '2016-11-08 13:15:41', '2016-11-08 13:15:42', '1173', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('b2c5ce14889cf22735808068b5a87416', 'wzgl/picture/picture/add', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 09:56:34', '2016-11-10 09:56:34', '690', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('b2c7bb677a879ef3eeced394d6e38cad', 'core/system/user/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-10 13:36:44', '2016-11-10 13:36:45', '821', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('b2f130443f0a0205432603f39769bd43', 'wzgl/email/email/delete', '0', '4', 'Request processing failed; nested exception is org.springframework.jdbc.BadSqlGrammarException: PreparedStatementCallback; bad SQL grammar [delete from wzgl_email where emailIds in (?)]; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'emailIds\' in \'where clause\'', '-', '2016-11-11 13:38:54', '2016-11-11 13:38:55', '110', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('b3e66e17cdbb44fe905b16f7fb2b4a0c', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-08 16:36:07', '2016-11-08 16:36:08', '663', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('b44f7c4b540d56edfd070762c2b06b09', 'core/base/login', '0', '2', null, '-', '2016-09-02 11:30:34', '2016-09-02 11:30:34', '10', '-', '未知', '-', '未知', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('b4f133bd33d24d0cee58189f2517cfe3', 'core/system/code_table/code_table_type/list', '2', '1', null, '获取码表类别管理页面中的码表类别列表数据', '2016-11-10 17:38:58', '2016-11-10 17:38:59', '666', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('b5de39878fa6cde3ae3274e6c8f6327e', 'core/system/user/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-08 10:09:00', '2016-11-08 10:09:01', '568', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('b70942146331f41f3c20d8e67cae81a2', 'wzgl/video/video/upload', '4', '1', null, '上传视频文件，视频文件名称：test.mp4', '2016-11-08 16:23:56', '2016-11-08 16:24:01', '4913', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('b73b936a801f2cb939d24dac5e808900', 'wzgl/video/video/add', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 16:08:52', '2016-11-08 16:08:53', '685', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('b74edccdc89797227bc76c4f23d96131', 'core/base/home', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 14:16:30', '2016-11-10 14:16:31', '1126', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('b77618718303b786709039ff3807a961', 'core/system/attr/list', '2', '1', null, '获取参数管理页面的参数列表数据', '2016-11-08 09:48:32', '2016-11-08 09:48:33', '635', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('b77cdef4a6809d62859f794758121ffd', 'wzgl/email/email/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-11 13:48:23', '2016-11-11 13:48:24', '486', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('b7de6b85f641bcbade65a3c6297b144f', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-11 13:37:21', '2016-11-11 13:37:22', '597', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('b84e2efc12d3d76a2a7c6bf1938b48d3', 'core/system/user', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 10:38:37', '2016-11-11 10:38:37', '330', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('b87e6e3b9cadf5b379334488f307d08c', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-10 13:36:34', '2016-11-10 13:36:35', '536', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('b8e6a06a428f6b0d1bfba74d09a4d473', 'core/base/home', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 13:41:20', '2016-11-11 13:41:21', '1061', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('b9035e3cae353c37c51b8325fec4cf0d', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-10 13:38:36', '2016-11-10 13:38:36', '785', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('b9e495ad6ee5d89cb0fe6e5bf42a2456', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-08 10:09:34', '2016-11-08 10:09:34', '623', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('ba6849ccd76729a84877511ef1d30b10', 'wzgl/email/email', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 13:21:27', '2016-11-11 13:21:28', '1036', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('baa3bf0fa9409b502ec5598a93c83a40', 'wzgl/email/email', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 14:31:14', '2016-11-10 14:31:24', '10555', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('bab1412c592581eeb1810bd5c0f95e97', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-10 09:58:24', '2016-11-10 09:58:24', '703', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('bad0519d1197270631893551d9469f1b', 'core/base/home', '1', '1', null, '访问框架快捷方式页面', '2016-11-13 17:34:12', '2016-11-13 17:34:13', '991', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '0:0:0:0:0:0:0:1');
INSERT INTO `core_operation_log` VALUES ('baf9a5781a25ab4ada72a7e76eec4cc8', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-10 17:12:12', '2016-11-10 17:12:13', '616', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('bb0689e5c994cb92d515227dd8b10c82', 'wzgl/email/email', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 17:07:22', '2016-11-10 17:07:23', '905', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('bb83abfe889207ca62449983476d1654', 'wzgl/article/article/list', '2', '1', null, '获取文章管理页面中的文章列表数据', '2016-11-08 10:12:47', '2016-11-08 10:12:48', '433', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('bccc6e3ea83be82c3ba2ccb5dd8faab1', 'wzgl/email/email/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-11 13:32:15', '2016-11-11 13:32:15', '593', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('bce83fd5bb495fca2e2eb55822650dec', 'wzgl/article/article/f81bd52d9cfa76054d6ca0802f802b3e/edit', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 10:45:12', '2016-11-10 10:45:13', '964', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('bd3913f1688ceccf03a4f8df22d2cf5b', 'wzgl/email/email', '1', '1', null, '访问用户管理页面', '2016-11-10 13:58:11', '2016-11-10 13:58:19', '8282', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('bd8c6227e141689a4348e487f76520b8', 'wzgl/email/email', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 14:32:56', '2016-11-10 14:33:01', '4778', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('bdc7d9df7bcbad7cb6ff95caf2206908', 'core/system/attr/list', '2', '1', null, '获取参数管理页面的参数列表数据', '2016-11-08 09:53:37', '2016-11-08 09:53:38', '523', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('bdf54903fa15e0147409879e1039dd43', 'wzgl/channel/channel/info', '1', '1', null, '获取栏目信息，栏目编号：3ca97e31fca2abda3124f686f1efe69c，栏目名称：友情链接', '2016-11-10 11:01:23', '2016-11-10 11:01:24', '851', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('c087f5d59ba263b015370e0e4ad703b1', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-08 10:00:47', '2016-11-08 10:00:48', '617', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('c0e4230290913f3dee1a3c7b41d5cff4', 'wzgl/channel/channel/info', '1', '1', null, '获取栏目信息，栏目编号：e8f1e52d3488def75936e7508f0d849f，栏目名称：中国教育网', '2016-11-10 11:23:10', '2016-11-10 11:23:11', '635', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('c2d18c3e29457bf6183eac290aee26da', 'wzgl/email/email', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 13:53:14', '2016-11-11 13:53:15', '581', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('c3a65936f798ced28a06f4d19c0d8a01', 'wzgl/picture/picture/ajaxAdd', '3', '1', null, '添加图片，图片编号：4cad551b86d512197558707f82b51302', '2016-11-10 10:00:14', '2016-11-10 10:00:15', '484', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('c423aae0c34ddd0f6b055423cffbf8bc', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-11 11:06:07', '2016-11-11 11:06:08', '911', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('c46da54970cc76458a808eb12a7c7f13', 'wzgl/channel/channel', '0', '2', null, '-', '2016-11-08 13:07:35', '2016-11-08 13:07:35', '0', '-', '未知', '-', '未知', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('c47216c178ab07be98741559dbeaa2b2', 'wzgl/email/email/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-11 09:08:01', '2016-11-11 09:08:01', '560', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('c4886e172c1165ffb5aef2ea8cd83f20', 'wzgl/email/email', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 15:53:54', '2016-11-10 15:53:55', '1214', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('c4ad7da2d5f3ada3d7f7d3ec8d8db83b', 'wzgl/email/email', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 16:24:34', '2016-11-10 16:24:34', '582', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('c4c1cfa879a4f78fde748d59002ff2a8', 'core/system/attr/list', '2', '1', null, '获取参数管理页面的参数列表数据', '2016-11-08 10:10:40', '2016-11-08 10:10:41', '813', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('c5cce7b1859ddac83adbf615064f97f7', 'wzgl/email/email/delete', '0', '4', 'Request processing failed; nested exception is org.springframework.jdbc.BadSqlGrammarException: PreparedStatementCallback; bad SQL grammar [delete from wzgl_email where emailIds in (?)]; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'emailIds\' in \'where clause\'', '-', '2016-11-11 13:27:03', '2016-11-11 13:27:03', '177', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('c66caeb1e9071769e964e7e7e3628589', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-10 17:21:19', '2016-11-10 17:21:19', '493', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('c75cbfe522056206e849ea84214f427a', 'wzgl/tag/tag/list', '2', '1', null, '获取标签管理页面中的标签列表数据', '2016-09-02 11:23:54', '2016-09-02 11:23:54', '426', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('c775cd5d82f78f441ab687607066f463', 'core/system/attr/list', '2', '1', null, '获取参数管理页面的参数列表数据', '2016-11-08 13:14:23', '2016-11-08 13:14:24', '1119', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('c83b685438409fefdee731ebae7acdbf', 'wzgl/email/email/list', '0', '4', 'Request processing failed; nested exception is java.lang.NullPointerException', '-', '2016-11-10 14:31:33', '2016-11-10 14:31:37', '4316', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('c855bc9ff90338868bab6f835536f710', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-10 09:59:08', '2016-11-10 09:59:09', '906', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('c9b1879f01008d5096780ab275faf571', 'wzgl/channel/channel/0626919cbb0fcc6d885de0babbe4f85d/edit', '1', '1', null, '访问框架快捷方式页面', '2016-11-09 15:44:34', '2016-11-09 15:44:35', '612', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('c9da49b966593fc7ade0a45da6d86fa0', 'core/system/user/240b59bf433b7701b58ba7adb63bfde0/set_role/list', '2', '1', null, '获取设置用户关系角色页面中的角色列表数据，用户内码：240b59bf433b7701b58ba7adb63bfde0', '2016-11-08 10:08:10', '2016-11-08 10:08:11', '657', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('ca9a4e3c83d12216d2430291bf48c7e2', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-11 11:07:23', '2016-11-11 11:07:24', '466', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('cac0d5ec6074a3df13d6f0216ca2538a', 'wzgl/email/email/c1c5b3e1c50053f2efb6fa10593a2794/edit', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 10:32:32', '2016-11-11 10:32:32', '302', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('cc2a7a988b1db62b699c27f0b8fca506', 'wzgl/email/email/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-10 17:48:14', '2016-11-10 17:48:15', '622', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('cc783b8c519e1e183b2799d39dbc41f7', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-09 10:07:14', '2016-11-09 10:07:15', '541', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('cca6c253a6235623ab29c8ddbff542ba', 'tdjs/tdjsVideo/video/list', '2', '1', null, '获取视频管理页面中的视频列表数据', '2016-09-02 11:34:54', '2016-09-02 11:34:54', '358', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('ccdb5c699b6551d8c200abd805db81d4', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-08 13:14:23', '2016-11-08 13:14:24', '606', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('cd8ee915c7d55594baae0c25b955da32', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-11 13:16:25', '2016-11-11 13:16:26', '803', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('ce0d5e99abf62a9913184e441d6c5f81', 'core/system/limit/b5d40cd48bdb1a3877426c0c842144b1/add', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 09:34:59', '2016-11-10 09:35:00', '812', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('ce33f8b6a1f884e96216cbea7a1fa126', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-10 09:36:46', '2016-11-10 09:36:46', '587', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('cef85c4938e01e1ba390394fe13e6ebb', 'core/base/login', '0', '2', null, '-', '2016-09-02 11:30:35', '2016-09-02 11:30:37', '1828', '-', '未知', '-', '未知', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('cf2af331a700626d7e55ecc730cce50c', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-09-02 11:30:51', '2016-09-02 11:30:52', '1014', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('d0265884ff720ead350fda25167df2d2', 'core/base/home', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 13:15:41', '2016-11-11 13:15:42', '1071', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('d0d64d2648f611730d19a984715da9ce', 'wzgl/reloadCash/reloadCash', '0', '1', null, '-', '2016-11-09 15:51:43', '2016-11-09 15:51:43', '321', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('d1224ba2bfa782539f5bc9dfec202b08', 'wzgl/picture/picture/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-08 13:14:45', '2016-11-08 13:14:46', '483', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('d15f731f750768290966e8f260a42654', 'wzgl/channel/channel', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 10:52:13', '2016-11-08 10:52:14', '692', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('d1e344bf489de0146723f5b8897d589b', 'wzgl/email/email/add', '1', '4', 'An exception occurred processing JSP page /WEB-INF/views/wzgl/email/addEmail.jsp at line 62\r\n\r\n59:                 <div class=\"panel-body\">\r\n60:                     <form:form id=\"emailForm\" cssClass=\"form-horizontal\" modelAttribute=\"email\" method=\"post\">\r\n61:                         <form:hidden path=\"channel_parent\" />\r\n62: \r\n63: \r\n64:                         <div class=\"form-group\">\r\n65:                             <label class=\"col-sm-2 control-label text-right\">閭欢鍚嶇О锛�/label>\r\n\r\n\r\nStacktrace:', '访问框架快捷方式页面', '2016-11-11 09:19:48', '2016-11-11 09:19:54', '6186', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('d1e65ae438eef44ee3cc2ed15d787766', 'core/base/home', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 09:53:27', '2016-11-10 09:53:27', '925', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('d1f8c04242cf0becce6a239a170f3ece', 'core/base/home', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 10:44:10', '2016-11-11 10:44:11', '1066', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('d23a80ae9b10c660dd8436532ce191b0', 'wzgl/email/email', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 10:37:49', '2016-11-11 10:37:49', '869', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('d2a3e20f8e771dd82366a7c77cc93d88', 'core/system/attr/list', '2', '1', null, '获取参数管理页面的参数列表数据', '2016-11-08 16:36:07', '2016-11-08 16:36:08', '668', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('d30b20bd720eb6a4fb1d7fe64f40ddd5', 'wzgl/email/email', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 14:06:20', '2016-11-10 14:06:24', '3981', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('d38cd2ddfb26d5aa90d4165c05d6b27c', 'wzgl/article/article/add', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 10:44:12', '2016-11-10 10:44:13', '658', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('d3bbfaea15fc5f2be5b8bee742a0f338', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-11 10:11:38', '2016-11-11 10:11:39', '1063', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('d491e2ccd7f273f9668b67e6e38f0832', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-11 13:16:56', '2016-11-11 13:16:57', '689', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('d4d5abbc78cd87a3892a13e02e4c9449', 'core/system/attr', '1', '1', null, '访问框架快捷方式页面', '2016-11-09 10:07:29', '2016-11-09 10:07:30', '900', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('d67b2c6482871042ac1c5f3c728bb073', 'wzgl/channel/channel', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 11:22:49', '2016-11-10 11:22:50', '713', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('d6a3e3d666abe900f7984ee2af17a9b7', 'wzgl/album/album/list', '2', '1', null, '获取相册管理页面中的相册列表数据', '2016-11-10 17:21:30', '2016-11-10 17:21:30', '428', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('d741ebee177e0e93ca6fd6cc6d9b8bdb', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-09-02 11:34:54', '2016-09-02 11:34:54', '349', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('d74ab71251de913aac0d62e5c97c2019', 'core/system/attr/', '1', '1', null, '访问框架快捷方式页面', '2016-11-09 15:51:41', '2016-11-09 15:51:41', '554', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('d75ee518a08fc91e65d4ed5f8a99c220', 'wzgl/channel/channel/info', '1', '1', null, '获取栏目信息，栏目编号：0f52c4016434b81ec035c75405cb747c，栏目名称：院所简介', '2016-11-08 10:55:28', '2016-11-08 10:55:29', '980', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('d77a232c73f25ebd76cd3b993b15e893', 'wzgl/picture/picture/ajaxAdd', '3', '1', null, '添加图片，图片编号：c6c2fbc3eda5327d33a1513bb76c5203', '2016-11-10 10:01:15', '2016-11-10 10:01:15', '379', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('d7979db852045570bab9fb26a0ef0ef1', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-09 15:45:02', '2016-11-09 15:45:03', '611', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('d7a7ea84e6678c0e78977e6c3427de26', 'core/base/login', '0', '2', null, '-', '2016-11-08 09:46:40', '2016-11-08 09:46:40', '12', '-', '未知', '-', '未知', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('d8d7571b76f91db326806b73574933e4', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-09 15:43:30', '2016-11-09 15:43:31', '1104', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('d8f54c3dcf7e8dc77b8593559aa4816c', 'core/system/code_table/code_table_type/add', '1', '1', null, '访问框架快捷方式页面', '2016-11-19 08:44:17', '2016-11-19 08:44:17', '360', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '0:0:0:0:0:0:0:1');
INSERT INTO `core_operation_log` VALUES ('d933ca4722066db40921a94cd42bfff1', 'wzgl/email/email/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-11 09:19:45', '2016-11-11 09:19:46', '1190', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('d963ac458eeee96ff6b0c9f08300b73f', 'wzgl/picture/picture/ajaxAdd', '3', '1', null, '添加图片，图片编号：8ec66dc73a728be0c70c502a71de05ad', '2016-11-10 09:58:38', '2016-11-10 09:58:39', '587', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('da761534d9c4940307166afa101a3acd', 'wzgl/email/email', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 09:16:22', '2016-11-11 09:16:23', '902', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('daa409fc5c232145626dda2d917d1211', 'wzgl/email/email/list', '0', '4', 'Request processing failed; nested exception is java.lang.NullPointerException', '-', '2016-11-10 14:33:01', '2016-11-10 14:33:09', '7428', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('dabbd98d251397c9c15a47d3afdaafdc', 'wzgl/picture/picture/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-11 09:07:45', '2016-11-11 09:07:49', '3866', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('db0ca1cb44e7a877a5cb9728ec22a808', 'wzgl/video/video/add', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 15:23:39', '2016-11-10 15:23:39', '399', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('dbb29f2040e9d41704bb30896542ffb0', 'wzgl/picture/picture', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 13:15:57', '2016-11-08 13:15:57', '572', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('dbb5984903ee09660b4a9d1b640a4825', 'core/system/limit', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 10:11:32', '2016-11-08 10:11:33', '750', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('dbd69443322c3466d92470e84ac9d71b', 'base-form', '0', '2', null, '-', '2016-11-19 08:36:21', '2016-11-19 08:36:21', '0', '-', '未知', '-', '未知', '0:0:0:0:0:0:0:1');
INSERT INTO `core_operation_log` VALUES ('dbd6ba6cc7d64490e894c795dc91185a', 'wzgl/email/email/add', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 13:08:08', '2016-11-11 13:08:09', '634', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('dbdde9ececd16198ad84933682851153', 'wzgl/article/article/list', '2', '1', null, '获取文章管理页面中的文章列表数据', '2016-11-10 17:21:19', '2016-11-10 17:21:20', '1024', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('dc5a88e942d228da2a5dd08e87491db3', 'core/system/attr', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 09:45:41', '2016-11-08 09:45:42', '1165', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('dcc113a5b90ad77dc500b2cdef7edd8d', 'core/system/code_table/picture_flag/code_table', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 09:53:59', '2016-11-10 09:54:00', '803', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('dcd88dc378be8435a1ac7a77e0e8b143', 'core/system/attr/list', '2', '1', null, '获取参数管理页面的参数列表数据', '2016-11-09 15:50:55', '2016-11-09 15:50:56', '700', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('dce2505c87235e89a8930b34873b086b', 'core/system/code_table/picture_flag/code_table/add', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 17:42:48', '2016-11-08 17:42:49', '747', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('dd14760ecb99eb1d22b229fad4fb76cd', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-11 09:07:46', '2016-11-11 09:07:47', '631', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('ddd815b5fa61964283de39a43ed90d11', 'wzgl/email/email/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-10 17:41:51', '2016-11-10 17:42:15', '23697', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('de8979383478ceac029f4c5c34453bf6', 'wzgl/picture/picture/ajaxAdd', '3', '1', null, '添加图片，图片编号：eb6eda8e4a9f3a4239e48721517e766d', '2016-11-10 09:57:03', '2016-11-10 09:57:03', '407', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('de967556f1d7b6dd5030fb5c74dd41a4', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-11 11:02:15', '2016-11-11 11:02:15', '428', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('dee6a91102b307aa37de36d5099756c1', 'core/system/code_table/picture_flag/code_table/list', '2', '1', null, '获取码表管理页面中的码表列表数据，码表类别编号picture_flag', '2016-11-08 17:43:35', '2016-11-08 17:43:36', '679', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('df2223c178e3e51b4f0db6214586a26d', '/wzgl/album/album', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 16:07:46', '2016-11-10 16:07:47', '864', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('df8a67ae5c6afb6af503a5298f303d43', 'wzgl/email/email', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 10:32:26', '2016-11-11 10:32:27', '1110', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('e0aed30a02065a982b9e027d88b3f653', 'wzgl/channel/channel', '1', '1', null, '访问框架快捷方式页面', '2016-11-25 16:32:33', '2016-11-25 16:32:34', '1532', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '0:0:0:0:0:0:0:1');
INSERT INTO `core_operation_log` VALUES ('e0caba82662887d8fd859dba82bd347c', 'wzgl/channel/channel', '1', '1', null, '访问框架快捷方式页面', '2016-11-09 15:46:09', '2016-11-09 15:46:10', '1115', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('e1a77152a9b8ebada5e5bcea7230a2c2', 'core/base/logout', '7', '1', null, '登出系统', '2016-11-13 17:34:17', '2016-11-13 17:34:17', '498', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '0:0:0:0:0:0:0:1');
INSERT INTO `core_operation_log` VALUES ('e2b91586381e9fe9bb53cb132ccec6b2', 'wzgl/picture/picture/add', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 09:59:29', '2016-11-10 09:59:31', '1328', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('e2e3370bd15aed7fd1090d191000b9b1', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-09 15:46:56', '2016-11-09 15:46:57', '854', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('e38d43807b4134f666757a1a9917e271', 'wzgl/channel/channel', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 10:58:48', '2016-11-10 10:58:49', '837', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('e3a5b96c69f8c68ad62ec579f84c45f4', 'wzgl/channel/channel/edit', '4', '1', null, '编辑栏目，栏目编号：ee73c789e3211f14f0b893fb962cc61a，栏目名称：null）', '2016-11-09 15:41:14', '2016-11-09 15:41:15', '1049', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('e412801bcba0545205d5a8c24e236e57', 'wzgl/channel/channel/15fdf33f6dc662d491e984206086ff85/edit', '1', '1', null, '访问框架快捷方式页面', '2016-11-09 15:39:41', '2016-11-09 15:39:42', '770', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('e56f8c1690b0d015b8107b907e6a8ba0', 'wzgl/email/email/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-11 13:29:27', '2016-11-11 13:29:28', '679', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('e68e394d6ef46c751c23f66b30c403a8', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-11 08:58:28', '2016-11-11 08:58:29', '367', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('e7015532cb7fb38119837d800e1d2057', 'core/base/home', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 14:24:30', '2016-11-10 14:24:31', '742', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('e7295a04715db138944a24f87ef84788', 'core/system/role/list', '2', '1', null, '获取角色管理页面中的角色列表数据', '2016-11-10 13:37:07', '2016-11-10 13:37:07', '558', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('e72c15ab494dd4ff19dc4f76906628fd', 'wzgl/email/email/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-11 13:24:40', '2016-11-11 13:25:24', '43819', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('e733efeefb9f98e3b314f1c37f04f256', 'core/announcement/announcement', '1', '4', 'Unable to compile class for JSP', '访问公告管理页面', '2016-11-25 16:43:03', '2016-11-25 16:43:03', '446', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '0:0:0:0:0:0:0:1');
INSERT INTO `core_operation_log` VALUES ('e76afbb7bf92169e2f7d58e6df7730a3', 'wzgl/email/email', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 14:32:38', '2016-11-10 14:32:46', '7373', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('e8172ac96f9a34e4dde03d14caf321e7', 'wzgl/email/email', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 12:30:08', '2016-11-11 12:30:09', '537', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('e81fded7497d09068e97136eab476f11', 'wzgl/channel/channel', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 08:58:24', '2016-11-11 08:58:25', '847', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('e823871ff366be3bbb4a06f5ad7c0caf', 'wzgl/channel/channel/select/radio', '1', '1', null, '访问选择栏目页面', '2016-11-08 15:05:30', '2016-11-08 15:05:31', '516', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('e82a19c4fce1d738e81df9776d832a15', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-08 10:12:25', '2016-11-08 10:12:26', '471', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('e98bd19b86deb8d212dd14d23c7805c3', 'wzgl/picture/picture/cea7551020f1fee883b7e2da209e5998/ajaxMultipartAdd', '3', '1', null, '添加图片，图片编号：4452465aeac0068a94aae2fb15172152', '2016-11-09 15:33:31', '2016-11-09 15:33:33', '1570', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('ea782af47ac066358b84f5cbe2edc0dc', 'wzgl/email/email/show', '4', '1', null, '显示邮件，邮件编号列表：c1c5b3e1c50053f2efb6fa10593a2794', '2016-11-11 11:26:25', '2016-11-11 11:26:25', '690', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('ea888f471d34c864775f7a9554b751cc', 'wzgl/email/email/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-10 17:22:50', '2016-11-10 17:22:51', '938', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('eaf1c3b297f415037fe99e4c41f6ff8e', 'wzgl/email/email/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-10 17:29:38', '2016-11-10 17:29:48', '9503', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('eb84e08e854af95edb8fccb522ca16ba', 'core/system/code_table', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 17:10:10', '2016-11-10 17:10:10', '665', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('ebb0bca4971ad7382109232bfde21b52', 'core/system/attr', '1', '1', null, '访问框架快捷方式页面', '2016-11-25 16:44:45', '2016-11-25 16:44:45', '404', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '0:0:0:0:0:0:0:1');
INSERT INTO `core_operation_log` VALUES ('ebb78e9f6c1b64f4be8c258a89f2102f', 'core/base/home', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 17:21:04', '2016-11-10 17:21:06', '1109', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('ec5f61e7f193220429d07cc838715081', 'core/system/code_table/picture_flag/code_table/list', '2', '1', null, '获取码表管理页面中的码表列表数据，码表类别编号picture_flag', '2016-11-08 17:43:12', '2016-11-08 17:43:13', '659', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('ec8c4762955ca8cda9c5e5e9683f95d7', 'core/system/limit', '1', '1', null, '访问框架快捷方式页面', '2016-11-13 17:06:53', '2016-11-13 17:06:53', '397', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '0:0:0:0:0:0:0:1');
INSERT INTO `core_operation_log` VALUES ('eccdec180e24c4619edc07883626702a', 'wzgl/channel/channel/edit', '4', '1', null, '编辑栏目，栏目编号：f73d02f7fc00e4b0d91768d2cb249dc9，栏目名称：null）', '2016-11-09 15:46:51', '2016-11-09 15:46:52', '970', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('ed4a572cc35db01b4e1bc3fbb65b18e3', 'wzgl/channel/channel', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 11:00:54', '2016-11-10 11:00:54', '695', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('ed77a071631e5444c5052dfae78ff1ef', 'wzgl/article/article', '0', '2', null, '-', '2016-11-11 13:43:34', '2016-11-11 13:43:34', '0', '-', '未知', '-', '未知', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('eddc1294fc78cbfe038202f768a21078', 'wzgl/email/email', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 10:11:03', '2016-11-11 10:11:04', '1095', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('ee59076e778839df42598120ee16c008', 'wzgl/email/email', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 15:55:43', '2016-11-10 15:55:44', '695', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('ee69199a7990e8924d5ac90fee0fc38e', 'core/system/user/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-08 10:01:21', '2016-11-08 10:01:22', '973', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('eec9ca3d54cb55913fca97211f3c7112', 'wzgl/picture/picture/e6c0596daa0128cc419ec4d689b9c490/edit', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 13:18:55', '2016-11-08 13:18:56', '1478', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('eed87aa97eb4c6600a73aefef956a607', 'wzgl/email/email/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-11 11:06:07', '2016-11-11 11:06:08', '461', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('ef2df2d819f724782521492253e1cfa4', 'wzgl/picture/picture/cea7551020f1fee883b7e2da209e5998/ajaxMultipartAdd', '3', '1', null, '添加图片，图片编号：7f444cbb27d4c2350be2cb826e0a2245', '2016-11-09 15:33:34', '2016-11-09 15:33:35', '887', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('efaca980814debad8ffb00e16352ffb9', 'wzgl/article/article', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 13:07:22', '2016-11-11 13:07:23', '735', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('f031e897c4f998dd68e67a30e3bca958', 'wzgl/email/email/list', '0', '4', 'Request processing failed; nested exception is java.lang.NullPointerException', '-', '2016-11-10 14:18:32', '2016-11-10 14:18:33', '100', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('f06cbff6df70159b0ca6d156d832a9bd', 'core/base/home', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 10:05:27', '2016-11-11 10:05:29', '1191', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('f3737d9cd0bdddc21cd2fea086012fed', 'core/system/user', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 10:01:20', '2016-11-08 10:01:21', '569', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('f479c9c36d35def7a4ee3b6a7813404c', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-08 13:16:05', '2016-11-08 13:16:06', '926', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('f488a6455c02f59ba8b5c2a384c64855', 'wzgl/channel/channel', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 10:51:39', '2016-11-08 10:51:39', '531', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('f4efeffd7dcc4e856abcf9b53de10183', 'wzgl/picture/picture', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 17:21:27', '2016-11-10 17:21:27', '373', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('f4fd732b24614a1b39908666a9a13ce3', 'wzgl/email/email/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-11 13:37:21', '2016-11-11 13:37:22', '606', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('f5f76bfe62a1ddf6b93a86bd3278fd78', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-11 11:23:49', '2016-11-11 11:23:50', '601', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('f606973a7906ce876287b5ca8d3591e7', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-09 15:43:08', '2016-11-09 15:43:08', '830', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('f62f2cecbc0e92bed97c8f43d7695cfc', 'core/base/home', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 13:43:40', '2016-11-11 13:43:41', '719', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('f842474eadd88f9d49277a287da07c04', 'wzgl/album/album/list', '2', '1', null, '获取相册管理页面中的相册列表数据', '2016-11-10 16:08:22', '2016-11-10 16:08:23', '694', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('f84bf0a434bd645e251418eb38d344ef', 'wzgl/email/email/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-11 13:19:02', '2016-11-11 13:19:03', '659', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('f89d6dc7abed5195168ccd05ae9a0412', 'core/system/user/add', '1', '1', null, '访问框架快捷方式页面', '2016-11-19 08:46:04', '2016-11-19 08:46:05', '474', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '0:0:0:0:0:0:0:1');
INSERT INTO `core_operation_log` VALUES ('f9027430b6908532c9e88d2a328e0320', 'wzgl/article/article/list', '2', '1', null, '获取文章管理页面中的文章列表数据', '2016-11-08 10:10:19', '2016-11-08 10:10:20', '778', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('f9624493fe9f5d6f014e6e99c33a6fcb', 'core/system/attr', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 17:42:21', '2016-11-08 17:42:22', '1080', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('f96fcc6f64a0376ae97d7f41ea95ab63', 'core/system/limit', '1', '1', null, '访问框架快捷方式页面', '2016-11-10 13:38:08', '2016-11-10 13:38:09', '809', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('fa990ee9f29fc86f503c6319b1207da3', 'core/system/limit/edit', '4', '1', null, '编辑功能，功能编号：7c0f35a345f293b46956338e5d47a6f6，功能名称：删除', '2016-11-10 13:32:56', '2016-11-10 13:32:56', '602', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('fab3a261e07d61a8587a4531397f2528', '/wzgl/album/album', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 13:31:11', '2016-11-11 13:31:11', '606', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('fb79b4739acec6704b481fea7646b0d0', 'core/system/attr/0829712de052d6e4eb3797a0efeb9e9d/edit', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 09:45:49', '2016-11-08 09:45:49', '318', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('fb8abe33f63547238a66f11d9e9b62b9', 'core/base/extra/task/listener', '2', '1', null, '获取系统任务列表', '2016-11-11 08:35:40', '2016-11-11 08:35:40', '582', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('fcc1ee39c0a407139e3fd0f1f1972bfd', 'core/log/operation_log/list', '2', '1', null, '获取操作日志列表数据', '2016-09-02 11:19:22', '2016-09-02 11:19:23', '403', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('fd162dff74f485c3099e3110ffa6c9c9', 'core/system/limit/info', '1', '1', null, '获取功能详细信息，功能编号：e4738d2a3ce1f321e4d74b3607a8666b，功能名称：大图管理', '2016-11-10 09:36:37', '2016-11-10 09:36:38', '674', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('fda25fbc4f4784fda45a6b5c5863cb88', 'core/system/attr', '1', '1', null, '访问框架快捷方式页面', '2016-11-08 10:14:31', '2016-11-08 10:14:32', '472', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('fdd1f13045f7ca22c09fe9094f0e4965', 'wzgl/email/email/list', '2', '1', null, '获取用户管理页面中的用户列表数据', '2016-11-11 11:06:19', '2016-11-11 11:06:20', '437', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('fe10a61afa03c9188f9d52ba5316cfce', 'wzgl/video/video', '0', '2', null, '-', '2016-11-10 15:23:15', '2016-11-10 15:23:15', '0', '-', '未知', '-', '未知', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('fe3b7397863a7b7e356f9ba0758d9432', 'wzgl/album/album/list', '2', '1', null, '获取相册管理页面中的相册列表数据', '2016-11-11 13:16:25', '2016-11-11 13:16:25', '477', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');
INSERT INTO `core_operation_log` VALUES ('ff5669dfdd68f031ec99e573a2bf8d28', 'core/system/attr', '1', '1', null, '访问框架快捷方式页面', '2016-11-19 08:36:15', '2016-11-19 08:36:15', '419', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '0:0:0:0:0:0:0:1');
INSERT INTO `core_operation_log` VALUES ('ffef9e8e5a602f52015baf49f6533e33', 'core/base/home', '1', '1', null, '访问框架快捷方式页面', '2016-11-11 11:02:00', '2016-11-11 11:02:01', '1139', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', '', '127.0.0.1');

-- ----------------------------
-- Table structure for core_role
-- ----------------------------
DROP TABLE IF EXISTS `core_role`;
CREATE TABLE `core_role` (
  `role_id` varchar(40) NOT NULL COMMENT '角色内码',
  `system_id` varchar(40) NOT NULL COMMENT '系统内码',
  `role_name` varchar(40) NOT NULL COMMENT '角色名称',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `creator` varchar(40) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `editor` varchar(40) NOT NULL COMMENT '编辑人',
  `edit_time` datetime NOT NULL COMMENT '编辑时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of core_role
-- ----------------------------
INSERT INTO `core_role` VALUES ('168c03dccce36563e4ceb3f58f84f117', '865f38e822e157fc2719b1933930ec03', '团队建设-普通用户', '团队普通成员角色', '240b59bf433b7701b58ba7adb63bfde0', '2016-08-18 11:41:17', '240b59bf433b7701b58ba7adb63bfde0', '2016-08-18 11:41:17');
INSERT INTO `core_role` VALUES ('30b82933ec84c43047a295dee19b2280', '3892bfe623082aa570471b6cf177f45e', '网站管理', '网站管理', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2016-07-11 15:56:54', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-20 10:47:23');
INSERT INTO `core_role` VALUES ('6ce375e4e4cf6500e78bcf5667dbeb48', 'c461f41655d29e14f79e3a439260cb27', '教师评测系统', '', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-19 16:30:40', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-19 16:30:40');
INSERT INTO `core_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '系统管理员角色', '系统管理员角色，请勿删除。', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00');
INSERT INTO `core_role` VALUES ('fab1c3d6ccc1876abd0967e9ab551527', '865f38e822e157fc2719b1933930ec03', '团队建设', '团队建设', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-25 11:28:21', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-25 11:28:21');

-- ----------------------------
-- Table structure for core_sql_log
-- ----------------------------
DROP TABLE IF EXISTS `core_sql_log`;
CREATE TABLE `core_sql_log` (
  `log_id` varchar(40) NOT NULL COMMENT '日志编号',
  `call_source` varchar(200) NOT NULL COMMENT '调用来源',
  `line_no` int(11) NOT NULL COMMENT '行号',
  `operation_sql` text NOT NULL COMMENT '操作SQL',
  `params` longtext NOT NULL COMMENT '参数',
  `call_result` varchar(2) NOT NULL COMMENT '执行结果',
  `error_reason` varchar(2000) DEFAULT NULL COMMENT '错误原因',
  `execute_type` varchar(20) NOT NULL COMMENT '执行类别',
  `result_type` varchar(80) NOT NULL COMMENT '结果类别',
  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '执行开始时间',
  `end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '执行结束时间',
  `cost` int(11) NOT NULL COMMENT '耗时',
  `operator` varchar(40) NOT NULL COMMENT '操作人',
  `operator_name` varchar(40) NOT NULL COMMENT '操作人名称',
  `operator_dept_id` varchar(40) DEFAULT NULL COMMENT '操作人所属部门',
  `operator_dept_name` varchar(40) DEFAULT NULL COMMENT '操作人所属部门名称',
  `ip` varchar(20) NOT NULL COMMENT 'IP地址',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='SQL日志表';

-- ----------------------------
-- Records of core_sql_log
-- ----------------------------
INSERT INTO `core_sql_log` VALUES ('0039f021cd780b777ad35811d6d0ff3d', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-11 10:37:44', '2016-11-11 10:37:46', '1324', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('0460ccd8868ef1361aca375b7c1f4167', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-10 17:21:03', '2016-11-10 17:21:04', '1366', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('0cf84b6d32a0b0de3ff8e5f6238b494a', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-11 10:10:57', '2016-11-11 10:10:58', '1389', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('0ff0a50b9e8516026b14f89fcdee8432', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-11 14:08:26', '2016-11-11 14:08:27', '1369', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('1b17fa8285738c564e9d15917473833f', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-10 15:23:28', '2016-11-10 15:23:30', '1497', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('1d4bbfc2bb47ffafd688fb329c7df901', 'com.dlshouwen.wzgl.email.dao.EmailDao', '91', 'delete from wzgl_email where emailIds in (?)', '18a43a73cdee7908faa745d84c0ffcfa', '0', 'PreparedStatementCallback; bad SQL grammar [delete from wzgl_email where emailIds in (?)]; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'emailIds\' in \'where clause\'', 'update', 'int', '2016-11-11 13:26:58', '2016-11-11 13:26:59', '59', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('1efc745d6c6bb7324a6ba13d400c1367', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-19 09:41:16', '2016-11-19 09:41:16', '420', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '0:0:0:0:0:0:0:1');
INSERT INTO `core_sql_log` VALUES ('21ae3ce77fad1b19d89a6dcf9b0d0b69', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-11 13:43:39', '2016-11-11 13:43:40', '1392', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('2ea96802c76c75361de84757c85d502d', 'com.dlshouwen.wzgl.email.dao.EmailDao', '58', 'update　wzgl_email set email_title=?,email_content=?,email_flag=? email_askDate=?,email_askPerson=?,email_replyContent=? email_replyUser=?,email_replyDate=? where email_id=?', '1212, 分v的分的v得分v个分的v得分v, 1, Mon Nov 28 00:00:00 CST 2016, 放到, 淡淡, com.dlshouwen.core.base.model.SessionUser@48c23efa, Fri Nov 11 10:44:21 CST 2016, 13', '0', 'PreparedStatementCallback; bad SQL grammar [update　wzgl_email set email_title=?,email_content=?,email_flag=? email_askDate=?,email_askPerson=?,email_replyContent=? email_replyUser=?,email_replyDate=? where email_id=?]; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'update　wzgl_email set email_title=\'1212\',email_content=\'分v的分的v得分v\' at line 1', 'update', 'int', '2016-11-11 10:44:24', '2016-11-11 10:44:24', '265', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('2ef2e998f18e89e85092ae4894c59cd1', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-10 14:47:45', '2016-11-10 14:47:46', '1495', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('2f19316a715206ab86e4067c8c140e08', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-08 13:07:41', '2016-11-08 13:07:42', '1222', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('2f9572df2300c5c1510c68d852140d1c', 'com.dlshouwen.wzgl.email.dao.EmailDao', '91', 'delete from wzgl_email where emailIds in (?)', '18a43a73cdee7908faa745d84c0ffcfa', '0', 'PreparedStatementCallback; bad SQL grammar [delete from wzgl_email where emailIds in (?)]; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'emailIds\' in \'where clause\'', 'update', 'int', '2016-11-11 13:28:03', '2016-11-11 13:28:03', '10', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('32c1e9cc4e7590d1d6f59e7fbf3c24fa', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-10 14:41:37', '2016-11-10 14:41:38', '1360', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('34a435101684f6bbe136c16d0dc448ca', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-11 10:32:13', '2016-11-11 10:32:15', '1338', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('34e0cb7f907011ac34e737a37a6dbaf4', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-11 08:35:38', '2016-11-11 08:35:39', '1326', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('365ec554e966c97aab2f085ba4583fd9', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-25 16:32:19', '2016-11-25 16:32:19', '537', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '0:0:0:0:0:0:0:1');
INSERT INTO `core_sql_log` VALUES ('36afaa74de3633a9d28c3522bbd445f3', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-08 09:58:13', '2016-11-08 09:58:15', '1715', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('378b9e7f862bbdaf432eb74708cb3e84', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-08 16:05:55', '2016-11-08 16:05:56', '1303', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('3be1ea13a9d70ccbc19da2bbe8ce42ef', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-11 09:16:15', '2016-11-11 09:16:16', '1347', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('3fd1e86b2cd7d8206a993bc5a01f3746', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-11 10:59:48', '2016-11-11 10:59:49', '1389', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('4125ed1228d9d15a80752e5cf70a39c8', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-10 15:46:17', '2016-11-10 15:46:19', '1355', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('415ba48c99ea2683408420948aef6cf4', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-19 08:36:59', '2016-11-19 08:37:00', '435', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '0:0:0:0:0:0:0:1');
INSERT INTO `core_sql_log` VALUES ('458b0d0c549bbaa02d3b0da243daa7a3', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-10 14:36:49', '2016-11-10 14:36:51', '1371', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('4654f7f9be32089f077e16b6b75cf836', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-11 13:15:39', '2016-11-11 13:15:41', '1360', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('4944e78730f6b3c83efc39de2d4465bf', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-13 17:06:40', '2016-11-13 17:06:41', '982', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '0:0:0:0:0:0:0:1');
INSERT INTO `core_sql_log` VALUES ('532faa3ee8eab427748b8d427c02d9c8', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-09 15:36:55', '2016-11-09 15:36:56', '1189', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('546a57e5ca077c16a5f742d1766f6b05', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-08 09:45:34', '2016-11-08 09:45:35', '1820', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('57597c041424a058de018bee23cd09aa', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-09 10:07:11', '2016-11-09 10:07:12', '1407', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('5ab52358b778196d13ede21b5ca94f28', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-10 09:34:40', '2016-11-10 09:34:41', '1312', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('5b4ea9b658f3708cf7e1ef24897a6ad0', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-10 13:37:28', '2016-11-10 13:37:30', '1286', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('5b585edeaa5cdd710c6db6b6b5f464d5', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-08 10:03:29', '2016-11-08 10:03:29', '383', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('5cc45d2df30aee9f9483690e63aac5f0', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-10 14:35:24', '2016-11-10 14:35:25', '1373', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('5e9d2d0a0171f5584db9ae9f7f81f556', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-08 10:03:48', '2016-11-08 10:03:48', '402', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('61268c8570d04c534e08f006f9b7ce07', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-11 11:14:22', '2016-11-11 11:14:23', '1374', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('657cace6bcb333a68dc5079b003f55ef', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-10 09:53:24', '2016-11-10 09:53:26', '1441', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('65e5b40b9e41679c192de03584b6e402', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-10 13:31:47', '2016-11-10 13:31:49', '1303', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('6bd29556481681a97ddce5710afc54f6', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-08 15:02:43', '2016-11-08 15:02:44', '1293', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('70ac2d7c26c34789693496c377e41390', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-11 13:41:19', '2016-11-11 13:41:20', '1366', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('729d537525a562a1c535d74cd4908f6d', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-10 14:21:12', '2016-11-10 14:21:14', '1324', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('73e02650b5ab30342d8d2f55bde101cb', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-10 17:29:32', '2016-11-10 17:29:33', '1338', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('76ef9350faa14943ea1b0fcc5d69f1ae', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-08 17:42:14', '2016-11-08 17:42:15', '1295', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('798facbe95c9c78b43d47a9e7d93d84f', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-11 13:46:32', '2016-11-11 13:46:33', '1359', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('7f63d54f2526db8d37740e72c8b4546e', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-10 09:53:24', '2016-11-10 09:53:26', '1456', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('83ffc2951ea6cdddd8875b828b430683', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-10 11:22:44', '2016-11-10 11:22:46', '1690', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('84b19f8a600fef27d40502ddbfa3f82b', 'com.dlshouwen.wzgl.email.dao.EmailDao', '91', 'delete from wzgl_email where emailIds in (?)', '18a43a73cdee7908faa745d84c0ffcfa', '0', 'PreparedStatementCallback; bad SQL grammar [delete from wzgl_email where emailIds in (?)]; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'emailIds\' in \'where clause\'', 'update', 'int', '2016-11-11 13:35:22', '2016-11-11 13:35:22', '8', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('897956f6dafa44c77ea578ec86266ab2', 'com.dlshouwen.wzgl.email.dao.EmailDao', '91', 'delete from wzgl_email where emailIds in (?)', '18a43a73cdee7908faa745d84c0ffcfa', '0', 'PreparedStatementCallback; bad SQL grammar [delete from wzgl_email where emailIds in (?)]; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'emailIds\' in \'where clause\'', 'update', 'int', '2016-11-11 13:27:03', '2016-11-11 13:27:03', '9', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('8a09a530ee458d81102f64d9c28e8bf3', 'com.dlshouwen.wzgl.email.dao.EmailDao', '91', 'delete from wzgl_email where emailIds in (?)', '18a43a73cdee7908faa745d84c0ffcfa', '0', 'PreparedStatementCallback; bad SQL grammar [delete from wzgl_email where emailIds in (?)]; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'emailIds\' in \'where clause\'', 'update', 'int', '2016-11-11 13:33:58', '2016-11-11 13:33:58', '7', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('9371535190a1eb53f388c188384559a0', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-11 10:57:04', '2016-11-11 10:57:05', '1348', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('9658202d3e3cf3d8732fa40b3e6343dc', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-10 14:16:28', '2016-11-10 14:16:29', '1350', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('96b3b7aac4664ad1944008e0d0b29148', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-25 16:44:41', '2016-11-25 16:44:41', '445', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '0:0:0:0:0:0:0:1');
INSERT INTO `core_sql_log` VALUES ('9a777ac4fdd16466c73823733a69c1e7', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-19 08:33:31', '2016-11-19 08:33:32', '589', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '0:0:0:0:0:0:0:1');
INSERT INTO `core_sql_log` VALUES ('9ad54658337674c09b545f7c7c4beda4', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-08 10:00:45', '2016-11-08 10:00:46', '1743', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('9de9592a8701bda3c3cbd146a2ab307f', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-11 11:01:58', '2016-11-11 11:01:59', '1364', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('9fa2fb51febd78e7fb820cbf8eeaeec5', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-08 10:50:22', '2016-11-08 10:50:24', '1330', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('aeb84c2f2df0569e3c4525b4d21ef026', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-09-02 11:30:36', '2016-09-02 11:30:37', '1685', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('af80cde99409dfeb1daca4cb0305f912', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-10 14:16:27', '2016-11-10 14:16:29', '1388', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('afd95db0d0cac1fd0056f0926d37d03f', 'com.dlshouwen.wzgl.email.dao.EmailDao', '91', 'delete from wzgl_email where emailIds in (?)', '18a43a73cdee7908faa745d84c0ffcfa', '0', 'PreparedStatementCallback; bad SQL grammar [delete from wzgl_email where emailIds in (?)]; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'emailIds\' in \'where clause\'', 'update', 'int', '2016-11-11 13:33:47', '2016-11-11 13:33:47', '8', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('ba29b7d681f62c10a372196a867899f5', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-08 13:13:00', '2016-11-08 13:13:01', '1252', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('bedb12287df8edcc7b4c9450d25f64eb', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-10 13:58:06', '2016-11-10 13:58:07', '1343', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('c0680c955f1f3b409e0aa42ef24266e9', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-10 14:44:40', '2016-11-10 14:44:41', '1370', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('c18939158baa9bd7036572f9ab46835e', 'com.dlshouwen.core.base.extra.task.dao.TaskListenerDao', '54', 'select t.*, l.limit_name, l.url, uc.user_name creator_name, ue.user_name editor_name from core_task t left join core_user uc on t.creator=uc.user_id left join core_user ue on t.editor=ue.user_id left join core_limit l on t.limit_id=l.limit_id where 1=1 and t.status=\'1\' and t.timing_time<=now() and (t.is_never_overdue=\'1\' or t.overdue_time>=now()) and (t.task_id in (select task_id from core_task_user where user_id=?) or t.task_id in (select task_id from core_task_role where role_id in (select role_id from core_user_role where user_id=?)) or t.is_all_user=\'1\')', '240b59bf433b7701b58ba7adb63bfde0, 240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<Map<String, Object>>', '2016-11-09 10:07:14', '2016-11-09 10:07:15', '493', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', '', null, '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('c2861064dc222ef4ec3ec36fad18c702', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-10 15:53:50', '2016-11-10 15:53:51', '1332', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('c97f738a6fd6281a5df23ed9da954b5b', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-08 09:46:45', '2016-11-08 09:46:47', '1731', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('cc73163b8acd5c75208b8722b170ba47', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-09 15:33:00', '2016-11-09 15:33:01', '1261', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('cff3a1fd6dd3cca2dc051f6ba36991fc', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-08 10:12:36', '2016-11-08 10:12:37', '1238', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('d27f1a301c044b396b2541a6636366a9', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-08 14:35:39', '2016-11-08 14:35:40', '1309', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('d426c379d39ad27ba5c1826b915c17dd', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-10 13:52:29', '2016-11-10 13:52:31', '1355', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('d9a0acdbb3dee1fc03627f126139e90a', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-19 08:36:45', '2016-11-19 08:36:45', '436', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '0:0:0:0:0:0:0:1');
INSERT INTO `core_sql_log` VALUES ('e6d86058311b182d216906988eba03fd', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-10 09:34:41', '2016-11-10 09:34:42', '1302', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('e9ec822bcd071c42fe571b30b81e3f55', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-11 10:44:09', '2016-11-11 10:44:10', '1365', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('ea4bdb7afeffe3abe9e43ce1708394d6', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-10 13:38:43', '2016-11-10 13:38:44', '1291', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('ec09ad936619119896c55c399df55627', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-19 08:37:16', '2016-11-19 08:37:17', '406', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '0:0:0:0:0:0:0:1');
INSERT INTO `core_sql_log` VALUES ('ecb2e91c4ae0c42c184622eea7a5c123', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-08 10:07:53', '2016-11-08 10:07:54', '1311', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('efbced9b0bb2c6d3b6247d376f377e48', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-10 14:42:59', '2016-11-10 14:43:00', '1383', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('f6b7747995ce5c79cad04f654f4b6bb8', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-19 08:48:16', '2016-11-19 08:48:17', '400', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '0:0:0:0:0:0:0:1');
INSERT INTO `core_sql_log` VALUES ('ff0a6245cb71809ca59465e7e0181ec6', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-11 10:05:26', '2016-11-11 10:05:27', '1420', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');
INSERT INTO `core_sql_log` VALUES ('ffae6750d61226168ead2c4442df3583', 'com.dlshouwen.core.base.dao.LoginDao', '126', 'select l.* from core_limit l where l.limit_id in (	select lr.limit_id from core_limit_role lr where lr.role_id in (		select ur.role_id from core_user_role ur where ur.user_id=?	)) order by l.sort', '240b59bf433b7701b58ba7adb63bfde0', '1', null, 'queryForList', 'List<[com.dlshouwen.core.system.model.Limit]>', '2016-11-10 11:13:52', '2016-11-10 11:13:53', '1234', 'login / outter spaces', '登录/外网访问', 'login not complete', '未登录完成', '127.0.0.1');

-- ----------------------------
-- Table structure for core_system
-- ----------------------------
DROP TABLE IF EXISTS `core_system`;
CREATE TABLE `core_system` (
  `system_id` varchar(40) NOT NULL COMMENT '系统内码',
  `system_code` varchar(40) NOT NULL COMMENT '系统编号',
  `system_name` varchar(40) NOT NULL COMMENT '系统名称',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `creator` varchar(40) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `editor` varchar(40) NOT NULL COMMENT '编辑人',
  `edit_time` datetime NOT NULL COMMENT '编辑时间',
  PRIMARY KEY (`system_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统表';

-- ----------------------------
-- Records of core_system
-- ----------------------------
INSERT INTO `core_system` VALUES ('3892bfe623082aa570471b6cf177f45e', 'WZGL', '网站管理', '网站管理', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2016-07-11 19:40:31', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2016-07-11 19:40:31');
INSERT INTO `core_system` VALUES ('865f38e822e157fc2719b1933930ec03', 'TDJS', '团队建设', '团队建设', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-25 10:50:44', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-25 10:50:44');
INSERT INTO `core_system` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'JCGL', '基础信息管理系统', '系统相关数据管理', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2015-08-01 00:00:00', '240b59bf433b7701b58ba7adb63bfde0', '2016-09-02 11:16:34');
INSERT INTO `core_system` VALUES ('c461f41655d29e14f79e3a439260cb27', 'JSPC', '教师评测系统', '教师评测系统', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-18 18:06:59', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-18 18:06:59');

-- ----------------------------
-- Table structure for core_task
-- ----------------------------
DROP TABLE IF EXISTS `core_task`;
CREATE TABLE `core_task` (
  `task_id` varchar(40) NOT NULL COMMENT '任务编号',
  `task_name` varchar(200) NOT NULL COMMENT '任务名称',
  `status` varchar(2) NOT NULL COMMENT '任务状态',
  `is_timing` varchar(2) NOT NULL COMMENT '是否定时提醒',
  `timing_time` datetime DEFAULT NULL COMMENT '定时提醒时间',
  `time_space` int(11) NOT NULL COMMENT '定时时间间隔',
  `is_never_overdue` varchar(2) NOT NULL COMMENT '是否永不过期',
  `overdue_time` datetime DEFAULT NULL COMMENT '过期时间',
  `message` varchar(2000) NOT NULL COMMENT '提示信息模板',
  `limit_id` varchar(40) NOT NULL COMMENT '响应功能',
  `detonate_sql` varchar(2000) NOT NULL COMMENT '触发SQL',
  `is_all_user` varchar(2) NOT NULL COMMENT '是否所有用户启用',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `creator` varchar(40) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `editor` varchar(40) NOT NULL COMMENT '编辑人',
  `edit_time` datetime NOT NULL COMMENT '编辑时间',
  PRIMARY KEY (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务表';

-- ----------------------------
-- Records of core_task
-- ----------------------------

-- ----------------------------
-- Table structure for core_task_attr
-- ----------------------------
DROP TABLE IF EXISTS `core_task_attr`;
CREATE TABLE `core_task_attr` (
  `attr_id` varchar(40) NOT NULL COMMENT '参数内码',
  `task_id` varchar(40) NOT NULL COMMENT '任务内码',
  `attr_sql` varchar(2000) NOT NULL COMMENT '参数SQL',
  `sort` int(11) NOT NULL COMMENT '排序码',
  PRIMARY KEY (`attr_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务参数表';

-- ----------------------------
-- Records of core_task_attr
-- ----------------------------

-- ----------------------------
-- Table structure for core_task_role
-- ----------------------------
DROP TABLE IF EXISTS `core_task_role`;
CREATE TABLE `core_task_role` (
  `task_id` varchar(40) NOT NULL COMMENT '任务编号',
  `role_id` varchar(40) NOT NULL COMMENT '角色编号',
  PRIMARY KEY (`task_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务角色关系表';

-- ----------------------------
-- Records of core_task_role
-- ----------------------------

-- ----------------------------
-- Table structure for core_task_user
-- ----------------------------
DROP TABLE IF EXISTS `core_task_user`;
CREATE TABLE `core_task_user` (
  `task_id` varchar(40) NOT NULL COMMENT '任务编号',
  `user_id` varchar(40) NOT NULL COMMENT '用户编号',
  PRIMARY KEY (`task_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务用户关系表';

-- ----------------------------
-- Records of core_task_user
-- ----------------------------

-- ----------------------------
-- Table structure for core_team
-- ----------------------------
DROP TABLE IF EXISTS `core_team`;
CREATE TABLE `core_team` (
  `team_id` varchar(40) NOT NULL,
  `pre_team_id` varchar(40) NOT NULL COMMENT '上级id',
  `team_name` varchar(40) NOT NULL COMMENT '团队名',
  `principal` varchar(20) DEFAULT NULL COMMENT '负责人',
  `principal_phone` varchar(20) DEFAULT NULL COMMENT '负责人电话',
  `sort` int(11) NOT NULL COMMENT '排序',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `creator` varchar(40) NOT NULL COMMENT '创建人id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `editor` varchar(40) NOT NULL COMMENT '最新一次编辑人id',
  `edit_time` datetime NOT NULL COMMENT '最新一次编辑时间',
  PRIMARY KEY (`team_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of core_team
-- ----------------------------
INSERT INTO `core_team` VALUES ('16056abbb0e9c5eb44574d4f2a001b93', '1', '未来之星团队（中）', '王主任', '100886', '0', '团队create', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-12 13:35:55', '240b59bf433b7701b58ba7adb63bfde0', '2016-08-15 17:56:25');
INSERT INTO `core_team` VALUES ('29790182f99ec9923877113d1265460d', '51c0a3c8d855d1484f2081078fb94220', '明日骨干团队（小）', '超级管理员', '13242234234', '0', '', '240b59bf433b7701b58ba7adb63bfde0', '2016-08-15 17:52:50', '240b59bf433b7701b58ba7adb63bfde0', '2016-08-15 17:56:59');
INSERT INTO `core_team` VALUES ('3db8a7069c2c247925a390786ce30d62', '1', '明日骨干团队（中）', '超级管理员', '13223423342', '0', '', '240b59bf433b7701b58ba7adb63bfde0', '2016-08-15 17:49:54', '240b59bf433b7701b58ba7adb63bfde0', '2016-08-15 17:56:35');
INSERT INTO `core_team` VALUES ('51c0a3c8d855d1484f2081078fb94220', 'top', '小学部', '张校长', '10086', '0', '新建', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-12 13:18:49', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '2016-07-12 13:18:49');
INSERT INTO `core_team` VALUES ('545294986cca97e038e71720d5431c45', '1', '翌日英才团队（中）', '超级管理员', '13423423342', '0', '', '240b59bf433b7701b58ba7adb63bfde0', '2016-08-15 17:50:54', '240b59bf433b7701b58ba7adb63bfde0', '2016-08-15 17:56:41');
INSERT INTO `core_team` VALUES ('67dffb6f441d1d38fa9b18594271db53', '51c0a3c8d855d1484f2081078fb94220', '校名师团队（小）', '超级管理员', '13654566554', '0', '', '240b59bf433b7701b58ba7adb63bfde0', '2016-08-15 17:53:41', '240b59bf433b7701b58ba7adb63bfde0', '2016-08-15 17:57:04');
INSERT INTO `core_team` VALUES ('cd8334849109a2f0c7aa8b174bdb02fa', '51c0a3c8d855d1484f2081078fb94220', '未来之星团队（小）', '超级管理员', '13543342234', '0', '', '240b59bf433b7701b58ba7adb63bfde0', '2016-08-15 17:52:21', '240b59bf433b7701b58ba7adb63bfde0', '2016-08-15 17:57:09');
INSERT INTO `core_team` VALUES ('ed0f5ea625a34f2a3a0c20658736ad62', '51c0a3c8d855d1484f2081078fb94220', '翌日英才团队（小）', '超级管理员', '13526897656', '0', '', '240b59bf433b7701b58ba7adb63bfde0', '2016-08-15 17:53:18', '240b59bf433b7701b58ba7adb63bfde0', '2016-08-15 17:57:14');
INSERT INTO `core_team` VALUES ('fff5b076963fe6b30123c953f319413b', '1', '校名师团队（中）', '超级管理员', '13244234423', '0', '', '240b59bf433b7701b58ba7adb63bfde0', '2016-08-15 17:51:39', '240b59bf433b7701b58ba7adb63bfde0', '2016-08-15 17:56:49');

-- ----------------------------
-- Table structure for core_team_user
-- ----------------------------
DROP TABLE IF EXISTS `core_team_user`;
CREATE TABLE `core_team_user` (
  `team_user_id` varchar(40) NOT NULL,
  `team_id` varchar(40) NOT NULL COMMENT '团队id',
  `user_id` varchar(40) NOT NULL COMMENT '用户id',
  `sort` int(11) NOT NULL COMMENT '排序号',
  PRIMARY KEY (`team_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of core_team_user
-- ----------------------------
INSERT INTO `core_team_user` VALUES ('1', '1', '1', '0');
INSERT INTO `core_team_user` VALUES ('2', '1', '2', '1');
INSERT INTO `core_team_user` VALUES ('3', '1', '3', '2');
INSERT INTO `core_team_user` VALUES ('4', '1', '259bfa4d8c86ea2b60e0c61f7b1c42e0', '3');

-- ----------------------------
-- Table structure for core_user
-- ----------------------------
DROP TABLE IF EXISTS `core_user`;
CREATE TABLE `core_user` (
  `user_id` varchar(40) NOT NULL COMMENT '用户内码',
  `dept_id` varchar(40) DEFAULT NULL COMMENT '所属部门',
  `team_id` varchar(40) DEFAULT NULL COMMENT '所属团队',
  `user_code` varchar(40) NOT NULL COMMENT '用户编号',
  `user_name` varchar(40) NOT NULL COMMENT '用户名称',
  `password` varchar(40) NOT NULL COMMENT '密码',
  `valid_type` varchar(2) NOT NULL COMMENT '有效状态',
  `identity` varchar(2) DEFAULT '0' COMMENT '用户身份',
  `sex` varchar(2) NOT NULL COMMENT '性别',
  `card_type` varchar(2) DEFAULT NULL COMMENT '证件类型',
  `card_id` varchar(20) DEFAULT NULL COMMENT '证件号码',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `work_date` date DEFAULT NULL COMMENT '工作日期',
  `imgpath` varchar(100) DEFAULT NULL COMMENT '个人照片路径',
  `folk` varchar(2) DEFAULT NULL COMMENT '民族',
  `degree` varchar(2) DEFAULT NULL COMMENT '学历',
  `qualified` varchar(2) DEFAULT NULL COMMENT '教师资质',
  `graduateSchool` varchar(40) DEFAULT NULL COMMENT '毕业院校',
  `phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(60) DEFAULT NULL COMMENT 'E-Mail',
  `address` varchar(200) DEFAULT NULL COMMENT '地址',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `creator` varchar(40) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `editor` varchar(40) NOT NULL COMMENT '编辑人',
  `edit_time` datetime NOT NULL COMMENT '编辑时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of core_user
-- ----------------------------
INSERT INTO `core_user` VALUES ('240b59bf433b7701b58ba7adb63bfde0', '', '', 'admin', '超级管理员', '0e9212587d373ca58e9bada0c15e6fe4', '1', '1', '1', '1', '130130199999999999', null, null, '/images/1471940782285.jpg', '', '', '1', 'test', '', '', '', '', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2016-08-15 15:36:03', '240b59bf433b7701b58ba7adb63bfde0', '2016-08-23 16:37:47');

-- ----------------------------
-- Table structure for core_user_attr
-- ----------------------------
DROP TABLE IF EXISTS `core_user_attr`;
CREATE TABLE `core_user_attr` (
  `user_id` varchar(40) NOT NULL COMMENT '用户编号',
  `skin_info` varchar(40) NOT NULL COMMENT '皮肤信息',
  `is_show_shortcut` varchar(2) NOT NULL COMMENT '是否显示快捷菜单',
  `is_background_float` varchar(2) NOT NULL COMMENT '是否背景漂浮',
  `background_float_speed` int(11) NOT NULL COMMENT '背景漂浮速度',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户参数表';

-- ----------------------------
-- Records of core_user_attr
-- ----------------------------
INSERT INTO `core_user_attr` VALUES ('032f6d8fd3bff1d06c8e00017ddeb0a2', 'default.jpg', '1', '0', '5');
INSERT INTO `core_user_attr` VALUES ('05968c92252274b6a6ddb0918ada207a', 'default.jpg', '1', '0', '5');
INSERT INTO `core_user_attr` VALUES ('0c616a2f51f080273da0a91c3d37fde7', 'default.jpg', '1', '0', '5');
INSERT INTO `core_user_attr` VALUES ('235c240fe88476bf0fac68c38ac8b066', 'default.jpg', '1', '0', '5');
INSERT INTO `core_user_attr` VALUES ('240b59bf433b7701b58ba7adb63bfde0', 'wave.jpg', '1', '0', '5');
INSERT INTO `core_user_attr` VALUES ('2410ebae1d0c11d55a613cad2d68010c', 'default.jpg', '1', '0', '5');
INSERT INTO `core_user_attr` VALUES ('26a0847d9f571a04451afc6da4dddefb', 'default.jpg', '1', '0', '5');
INSERT INTO `core_user_attr` VALUES ('3c4d35736acf8dc88fdb0b048c68ca4f', 'default.jpg', '1', '0', '5');
INSERT INTO `core_user_attr` VALUES ('49421945bd4a83607aea94cc8b4f4241', 'default.jpg', '1', '0', '5');
INSERT INTO `core_user_attr` VALUES ('4d12d1deb152146721836729f7a7510a', 'default.jpg', '1', '0', '5');
INSERT INTO `core_user_attr` VALUES ('543352b804bc34a665eb65e6ce7359d8', 'city.jpg', '1', '0', '5');
INSERT INTO `core_user_attr` VALUES ('682d17f8e9c09225dd0527f9a0388f9e', 'default.jpg', '1', '0', '5');
INSERT INTO `core_user_attr` VALUES ('6a28b9b41f01d3b72576c5e0f3cc9f05', 'default.jpg', '1', '0', '5');
INSERT INTO `core_user_attr` VALUES ('71c800835817f60d86faad3b4563eb0c', 'default.jpg', '1', '0', '5');
INSERT INTO `core_user_attr` VALUES ('93c925c5717aa78064692a10ae28d2fe', 'default.jpg', '1', '0', '5');
INSERT INTO `core_user_attr` VALUES ('9adedda07183a70cb591bee7fc67f74e', 'default.jpg', '1', '0', '5');
INSERT INTO `core_user_attr` VALUES ('a6c826a11818879b4832de03167c5fab', 'default.jpg', '1', '0', '5');
INSERT INTO `core_user_attr` VALUES ('a9efec93fe86a12417a34e878502d2fc', 'city.jpg', '1', '0', '5');
INSERT INTO `core_user_attr` VALUES ('c33cdcda2b55c31e06410acf139080f2', 'default.jpg', '1', '0', '5');
INSERT INTO `core_user_attr` VALUES ('d0aaf6159d8351e17ccb10db5d7d36a0', 'default.jpg', '1', '0', '5');
INSERT INTO `core_user_attr` VALUES ('d33189d9b7afa1db6f1d7bf689cc1a43', 'default.jpg', '1', '0', '5');
INSERT INTO `core_user_attr` VALUES ('da2391c1ac78682786363b76d1bf5398', 'default.jpg', '1', '0', '5');

-- ----------------------------
-- Table structure for core_user_role
-- ----------------------------
DROP TABLE IF EXISTS `core_user_role`;
CREATE TABLE `core_user_role` (
  `user_id` varchar(40) NOT NULL COMMENT '用户编号',
  `role_id` varchar(40) NOT NULL COMMENT '角色编号',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关系表';

-- ----------------------------
-- Records of core_user_role
-- ----------------------------
INSERT INTO `core_user_role` VALUES ('032f6d8fd3bff1d06c8e00017ddeb0a2', '168c03dccce36563e4ceb3f58f84f117');
INSERT INTO `core_user_role` VALUES ('0c616a2f51f080273da0a91c3d37fde7', '168c03dccce36563e4ceb3f58f84f117');
INSERT INTO `core_user_role` VALUES ('108da062ed08596cb9ca9465a2976a09', '30b82933ec84c43047a295dee19b2280');
INSERT INTO `core_user_role` VALUES ('108da062ed08596cb9ca9465a2976a09', '8b9e227bb9915cc30bfb6f4d6e78c461');
INSERT INTO `core_user_role` VALUES ('240b59bf433b7701b58ba7adb63bfde0', '30b82933ec84c43047a295dee19b2280');
INSERT INTO `core_user_role` VALUES ('240b59bf433b7701b58ba7adb63bfde0', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa');
INSERT INTO `core_user_role` VALUES ('259bfa4d8c86ea2b60e0c61f7b1c42e0', '30b82933ec84c43047a295dee19b2280');
INSERT INTO `core_user_role` VALUES ('259bfa4d8c86ea2b60e0c61f7b1c42e0', '6ce375e4e4cf6500e78bcf5667dbeb48');
INSERT INTO `core_user_role` VALUES ('259bfa4d8c86ea2b60e0c61f7b1c42e0', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa');
INSERT INTO `core_user_role` VALUES ('259bfa4d8c86ea2b60e0c61f7b1c42e0', 'demo');
INSERT INTO `core_user_role` VALUES ('259bfa4d8c86ea2b60e0c61f7b1c42e0', 'fab1c3d6ccc1876abd0967e9ab551527');
INSERT INTO `core_user_role` VALUES ('41d6a469478d402e442f31231ed08447', '30b82933ec84c43047a295dee19b2280');
INSERT INTO `core_user_role` VALUES ('41d6a469478d402e442f31231ed08447', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa');
INSERT INTO `core_user_role` VALUES ('41d6a469478d402e442f31231ed08447', 'demo');
INSERT INTO `core_user_role` VALUES ('543352b804bc34a665eb65e6ce7359d8', '30b82933ec84c43047a295dee19b2280');
INSERT INTO `core_user_role` VALUES ('543352b804bc34a665eb65e6ce7359d8', 'fab1c3d6ccc1876abd0967e9ab551527');
INSERT INTO `core_user_role` VALUES ('6d01fdd1392958ec9d3988f799be0311', '30b82933ec84c43047a295dee19b2280');
INSERT INTO `core_user_role` VALUES ('6d01fdd1392958ec9d3988f799be0311', '6ce375e4e4cf6500e78bcf5667dbeb48');
INSERT INTO `core_user_role` VALUES ('6d01fdd1392958ec9d3988f799be0311', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa');
INSERT INTO `core_user_role` VALUES ('6d01fdd1392958ec9d3988f799be0311', 'demo');
INSERT INTO `core_user_role` VALUES ('6d01fdd1392958ec9d3988f799be0311', 'fab1c3d6ccc1876abd0967e9ab551527');
INSERT INTO `core_user_role` VALUES ('a9efec93fe86a12417a34e878502d2fc', '6ce375e4e4cf6500e78bcf5667dbeb48');
INSERT INTO `core_user_role` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa');

-- ----------------------------
-- Table structure for core_user_shortcut_limit
-- ----------------------------
DROP TABLE IF EXISTS `core_user_shortcut_limit`;
CREATE TABLE `core_user_shortcut_limit` (
  `user_id` varchar(40) NOT NULL COMMENT '用户内码',
  `limit_id` varchar(40) NOT NULL COMMENT '功能内码',
  PRIMARY KEY (`user_id`,`limit_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户快捷方式关系表';

-- ----------------------------
-- Records of core_user_shortcut_limit
-- ----------------------------
INSERT INTO `core_user_shortcut_limit` VALUES ('032f6d8fd3bff1d06c8e00017ddeb0a2', '3e430f69d2c3cb4968424be37f2b197c');
INSERT INTO `core_user_shortcut_limit` VALUES ('032f6d8fd3bff1d06c8e00017ddeb0a2', '9101e48e6097e5772b7c73ae1931be4e');
INSERT INTO `core_user_shortcut_limit` VALUES ('0f8d59f1cab3274b1380394c8fe90590', '50bdd77ddb05ccb5201506236c80a412');
INSERT INTO `core_user_shortcut_limit` VALUES ('0f8d59f1cab3274b1380394c8fe90590', '7697a17f9291534109351f7e41fd26cd');
INSERT INTO `core_user_shortcut_limit` VALUES ('0f8d59f1cab3274b1380394c8fe90590', '8654ac15d6d46ead928e6f98cf37d7c1');
INSERT INTO `core_user_shortcut_limit` VALUES ('0f8d59f1cab3274b1380394c8fe90590', 'a4b28e926583ed3b3977793d8ece5e54');
INSERT INTO `core_user_shortcut_limit` VALUES ('0f8d59f1cab3274b1380394c8fe90590', 'e4e9828177cde11540ce3cfd47c275dd');
INSERT INTO `core_user_shortcut_limit` VALUES ('0f8d59f1cab3274b1380394c8fe90590', 'e8240eae240368d3b81344acf5ef06ec');
INSERT INTO `core_user_shortcut_limit` VALUES ('108da062ed08596cb9ca9465a2976a09', '5e912d9e49ee85d4b2e46be5608ffc66');
INSERT INTO `core_user_shortcut_limit` VALUES ('240b59bf433b7701b58ba7adb63bfde0', '50bdd77ddb05ccb5201506236c80a412');
INSERT INTO `core_user_shortcut_limit` VALUES ('240b59bf433b7701b58ba7adb63bfde0', '7697a17f9291534109351f7e41fd26cd');
INSERT INTO `core_user_shortcut_limit` VALUES ('240b59bf433b7701b58ba7adb63bfde0', '8654ac15d6d46ead928e6f98cf37d7c1');
INSERT INTO `core_user_shortcut_limit` VALUES ('240b59bf433b7701b58ba7adb63bfde0', 'a4b28e926583ed3b3977793d8ece5e54');
INSERT INTO `core_user_shortcut_limit` VALUES ('240b59bf433b7701b58ba7adb63bfde0', 'e4e9828177cde11540ce3cfd47c275dd');
INSERT INTO `core_user_shortcut_limit` VALUES ('240b59bf433b7701b58ba7adb63bfde0', 'e8240eae240368d3b81344acf5ef06ec');
INSERT INTO `core_user_shortcut_limit` VALUES ('259bfa4d8c86ea2b60e0c61f7b1c42e0', '2836bdfe266dae1f801b9b96fd3e6048');
INSERT INTO `core_user_shortcut_limit` VALUES ('259bfa4d8c86ea2b60e0c61f7b1c42e0', '50bdd77ddb05ccb5201506236c80a412');
INSERT INTO `core_user_shortcut_limit` VALUES ('259bfa4d8c86ea2b60e0c61f7b1c42e0', '8654ac15d6d46ead928e6f98cf37d7c1');
INSERT INTO `core_user_shortcut_limit` VALUES ('259bfa4d8c86ea2b60e0c61f7b1c42e0', 'a4b28e926583ed3b3977793d8ece5e54');
INSERT INTO `core_user_shortcut_limit` VALUES ('259bfa4d8c86ea2b60e0c61f7b1c42e0', 'e4e9828177cde11540ce3cfd47c275dd');
INSERT INTO `core_user_shortcut_limit` VALUES ('259bfa4d8c86ea2b60e0c61f7b1c42e0', 'e8240eae240368d3b81344acf5ef06ec');
INSERT INTO `core_user_shortcut_limit` VALUES ('260d20e616a7ebb39a37ee793638ef07', '50bdd77ddb05ccb5201506236c80a412');
INSERT INTO `core_user_shortcut_limit` VALUES ('260d20e616a7ebb39a37ee793638ef07', '8654ac15d6d46ead928e6f98cf37d7c1');
INSERT INTO `core_user_shortcut_limit` VALUES ('260d20e616a7ebb39a37ee793638ef07', 'a4b28e926583ed3b3977793d8ece5e54');
INSERT INTO `core_user_shortcut_limit` VALUES ('260d20e616a7ebb39a37ee793638ef07', 'e4e9828177cde11540ce3cfd47c275dd');
INSERT INTO `core_user_shortcut_limit` VALUES ('260d20e616a7ebb39a37ee793638ef07', 'e8240eae240368d3b81344acf5ef06ec');
INSERT INTO `core_user_shortcut_limit` VALUES ('3c4d35736acf8dc88fdb0b048c68ca4f', '50bdd77ddb05ccb5201506236c80a412');
INSERT INTO `core_user_shortcut_limit` VALUES ('3c4d35736acf8dc88fdb0b048c68ca4f', '7697a17f9291534109351f7e41fd26cd');
INSERT INTO `core_user_shortcut_limit` VALUES ('3c4d35736acf8dc88fdb0b048c68ca4f', '8654ac15d6d46ead928e6f98cf37d7c1');
INSERT INTO `core_user_shortcut_limit` VALUES ('3c4d35736acf8dc88fdb0b048c68ca4f', 'a4b28e926583ed3b3977793d8ece5e54');
INSERT INTO `core_user_shortcut_limit` VALUES ('3c4d35736acf8dc88fdb0b048c68ca4f', 'e4e9828177cde11540ce3cfd47c275dd');
INSERT INTO `core_user_shortcut_limit` VALUES ('3c4d35736acf8dc88fdb0b048c68ca4f', 'e8240eae240368d3b81344acf5ef06ec');
INSERT INTO `core_user_shortcut_limit` VALUES ('41acfd79b0b2e2485d1add28a1d2c5d7', '50bdd77ddb05ccb5201506236c80a412');
INSERT INTO `core_user_shortcut_limit` VALUES ('41acfd79b0b2e2485d1add28a1d2c5d7', '8654ac15d6d46ead928e6f98cf37d7c1');
INSERT INTO `core_user_shortcut_limit` VALUES ('41acfd79b0b2e2485d1add28a1d2c5d7', 'a4b28e926583ed3b3977793d8ece5e54');
INSERT INTO `core_user_shortcut_limit` VALUES ('41acfd79b0b2e2485d1add28a1d2c5d7', 'e4e9828177cde11540ce3cfd47c275dd');
INSERT INTO `core_user_shortcut_limit` VALUES ('41acfd79b0b2e2485d1add28a1d2c5d7', 'e8240eae240368d3b81344acf5ef06ec');
INSERT INTO `core_user_shortcut_limit` VALUES ('41d6a469478d402e442f31231ed08447', '50bdd77ddb05ccb5201506236c80a412');
INSERT INTO `core_user_shortcut_limit` VALUES ('41d6a469478d402e442f31231ed08447', '8654ac15d6d46ead928e6f98cf37d7c1');
INSERT INTO `core_user_shortcut_limit` VALUES ('41d6a469478d402e442f31231ed08447', 'a4b28e926583ed3b3977793d8ece5e54');
INSERT INTO `core_user_shortcut_limit` VALUES ('41d6a469478d402e442f31231ed08447', 'e4e9828177cde11540ce3cfd47c275dd');
INSERT INTO `core_user_shortcut_limit` VALUES ('41d6a469478d402e442f31231ed08447', 'e8240eae240368d3b81344acf5ef06ec');
INSERT INTO `core_user_shortcut_limit` VALUES ('48ac7ef5d1a3f61f7907517074c91fb3', '50bdd77ddb05ccb5201506236c80a412');
INSERT INTO `core_user_shortcut_limit` VALUES ('48ac7ef5d1a3f61f7907517074c91fb3', '7697a17f9291534109351f7e41fd26cd');
INSERT INTO `core_user_shortcut_limit` VALUES ('48ac7ef5d1a3f61f7907517074c91fb3', '8654ac15d6d46ead928e6f98cf37d7c1');
INSERT INTO `core_user_shortcut_limit` VALUES ('48ac7ef5d1a3f61f7907517074c91fb3', 'a4b28e926583ed3b3977793d8ece5e54');
INSERT INTO `core_user_shortcut_limit` VALUES ('48ac7ef5d1a3f61f7907517074c91fb3', 'e4e9828177cde11540ce3cfd47c275dd');
INSERT INTO `core_user_shortcut_limit` VALUES ('48ac7ef5d1a3f61f7907517074c91fb3', 'e8240eae240368d3b81344acf5ef06ec');
INSERT INTO `core_user_shortcut_limit` VALUES ('4d12d1deb152146721836729f7a7510a', '50bdd77ddb05ccb5201506236c80a412');
INSERT INTO `core_user_shortcut_limit` VALUES ('4d12d1deb152146721836729f7a7510a', '7697a17f9291534109351f7e41fd26cd');
INSERT INTO `core_user_shortcut_limit` VALUES ('4d12d1deb152146721836729f7a7510a', '8654ac15d6d46ead928e6f98cf37d7c1');
INSERT INTO `core_user_shortcut_limit` VALUES ('4d12d1deb152146721836729f7a7510a', 'a4b28e926583ed3b3977793d8ece5e54');
INSERT INTO `core_user_shortcut_limit` VALUES ('4d12d1deb152146721836729f7a7510a', 'e4e9828177cde11540ce3cfd47c275dd');
INSERT INTO `core_user_shortcut_limit` VALUES ('4d12d1deb152146721836729f7a7510a', 'e8240eae240368d3b81344acf5ef06ec');
INSERT INTO `core_user_shortcut_limit` VALUES ('543352b804bc34a665eb65e6ce7359d8', '2fad67e7358cfa59ba1fa48b9f1770c3');
INSERT INTO `core_user_shortcut_limit` VALUES ('543352b804bc34a665eb65e6ce7359d8', '3e430f69d2c3cb4968424be37f2b197c');
INSERT INTO `core_user_shortcut_limit` VALUES ('543352b804bc34a665eb65e6ce7359d8', '7fdc670ebe6f1c27b0ca5cfd7594f225');
INSERT INTO `core_user_shortcut_limit` VALUES ('543352b804bc34a665eb65e6ce7359d8', '9101e48e6097e5772b7c73ae1931be4e');
INSERT INTO `core_user_shortcut_limit` VALUES ('543352b804bc34a665eb65e6ce7359d8', '97e14f2fb96bf4602f7c0affe032ddad');
INSERT INTO `core_user_shortcut_limit` VALUES ('543352b804bc34a665eb65e6ce7359d8', '9e9b10840b4c1e4b314f5feeadba3deb');
INSERT INTO `core_user_shortcut_limit` VALUES ('543352b804bc34a665eb65e6ce7359d8', 'ad1ea16b08345f81eef8103efc1ba3cc');
INSERT INTO `core_user_shortcut_limit` VALUES ('543352b804bc34a665eb65e6ce7359d8', 'fa50872f12e1a3bf75f4548f9ff37461');
INSERT INTO `core_user_shortcut_limit` VALUES ('6079cd6f7e74e4ebb64ca28afab003c6', '50bdd77ddb05ccb5201506236c80a412');
INSERT INTO `core_user_shortcut_limit` VALUES ('6079cd6f7e74e4ebb64ca28afab003c6', '7697a17f9291534109351f7e41fd26cd');
INSERT INTO `core_user_shortcut_limit` VALUES ('6079cd6f7e74e4ebb64ca28afab003c6', '8654ac15d6d46ead928e6f98cf37d7c1');
INSERT INTO `core_user_shortcut_limit` VALUES ('6079cd6f7e74e4ebb64ca28afab003c6', 'a4b28e926583ed3b3977793d8ece5e54');
INSERT INTO `core_user_shortcut_limit` VALUES ('6079cd6f7e74e4ebb64ca28afab003c6', 'e4e9828177cde11540ce3cfd47c275dd');
INSERT INTO `core_user_shortcut_limit` VALUES ('6079cd6f7e74e4ebb64ca28afab003c6', 'e8240eae240368d3b81344acf5ef06ec');
INSERT INTO `core_user_shortcut_limit` VALUES ('615564c3450881c3a607958b6fa1846f', '50bdd77ddb05ccb5201506236c80a412');
INSERT INTO `core_user_shortcut_limit` VALUES ('615564c3450881c3a607958b6fa1846f', '7697a17f9291534109351f7e41fd26cd');
INSERT INTO `core_user_shortcut_limit` VALUES ('615564c3450881c3a607958b6fa1846f', '8654ac15d6d46ead928e6f98cf37d7c1');
INSERT INTO `core_user_shortcut_limit` VALUES ('615564c3450881c3a607958b6fa1846f', 'a4b28e926583ed3b3977793d8ece5e54');
INSERT INTO `core_user_shortcut_limit` VALUES ('615564c3450881c3a607958b6fa1846f', 'e4e9828177cde11540ce3cfd47c275dd');
INSERT INTO `core_user_shortcut_limit` VALUES ('615564c3450881c3a607958b6fa1846f', 'e8240eae240368d3b81344acf5ef06ec');
INSERT INTO `core_user_shortcut_limit` VALUES ('682d17f8e9c09225dd0527f9a0388f9e', '50bdd77ddb05ccb5201506236c80a412');
INSERT INTO `core_user_shortcut_limit` VALUES ('682d17f8e9c09225dd0527f9a0388f9e', '7697a17f9291534109351f7e41fd26cd');
INSERT INTO `core_user_shortcut_limit` VALUES ('682d17f8e9c09225dd0527f9a0388f9e', '8654ac15d6d46ead928e6f98cf37d7c1');
INSERT INTO `core_user_shortcut_limit` VALUES ('682d17f8e9c09225dd0527f9a0388f9e', 'a4b28e926583ed3b3977793d8ece5e54');
INSERT INTO `core_user_shortcut_limit` VALUES ('682d17f8e9c09225dd0527f9a0388f9e', 'e4e9828177cde11540ce3cfd47c275dd');
INSERT INTO `core_user_shortcut_limit` VALUES ('682d17f8e9c09225dd0527f9a0388f9e', 'e8240eae240368d3b81344acf5ef06ec');
INSERT INTO `core_user_shortcut_limit` VALUES ('9bfd8984b85912eb042221e2641a48a5', '50bdd77ddb05ccb5201506236c80a412');
INSERT INTO `core_user_shortcut_limit` VALUES ('9bfd8984b85912eb042221e2641a48a5', '7697a17f9291534109351f7e41fd26cd');
INSERT INTO `core_user_shortcut_limit` VALUES ('9bfd8984b85912eb042221e2641a48a5', '8654ac15d6d46ead928e6f98cf37d7c1');
INSERT INTO `core_user_shortcut_limit` VALUES ('9bfd8984b85912eb042221e2641a48a5', 'a4b28e926583ed3b3977793d8ece5e54');
INSERT INTO `core_user_shortcut_limit` VALUES ('9bfd8984b85912eb042221e2641a48a5', 'e4e9828177cde11540ce3cfd47c275dd');
INSERT INTO `core_user_shortcut_limit` VALUES ('9bfd8984b85912eb042221e2641a48a5', 'e8240eae240368d3b81344acf5ef06ec');
INSERT INTO `core_user_shortcut_limit` VALUES ('a6a196f2cc056a6ccf4dfb27135a002c', '50bdd77ddb05ccb5201506236c80a412');
INSERT INTO `core_user_shortcut_limit` VALUES ('a6a196f2cc056a6ccf4dfb27135a002c', '7697a17f9291534109351f7e41fd26cd');
INSERT INTO `core_user_shortcut_limit` VALUES ('a6a196f2cc056a6ccf4dfb27135a002c', '8654ac15d6d46ead928e6f98cf37d7c1');
INSERT INTO `core_user_shortcut_limit` VALUES ('a6a196f2cc056a6ccf4dfb27135a002c', 'a4b28e926583ed3b3977793d8ece5e54');
INSERT INTO `core_user_shortcut_limit` VALUES ('a6a196f2cc056a6ccf4dfb27135a002c', 'e4e9828177cde11540ce3cfd47c275dd');
INSERT INTO `core_user_shortcut_limit` VALUES ('a6a196f2cc056a6ccf4dfb27135a002c', 'e8240eae240368d3b81344acf5ef06ec');
INSERT INTO `core_user_shortcut_limit` VALUES ('a6c826a11818879b4832de03167c5fab', '50bdd77ddb05ccb5201506236c80a412');
INSERT INTO `core_user_shortcut_limit` VALUES ('a6c826a11818879b4832de03167c5fab', '7697a17f9291534109351f7e41fd26cd');
INSERT INTO `core_user_shortcut_limit` VALUES ('a6c826a11818879b4832de03167c5fab', '8654ac15d6d46ead928e6f98cf37d7c1');
INSERT INTO `core_user_shortcut_limit` VALUES ('a6c826a11818879b4832de03167c5fab', 'a4b28e926583ed3b3977793d8ece5e54');
INSERT INTO `core_user_shortcut_limit` VALUES ('a6c826a11818879b4832de03167c5fab', 'e4e9828177cde11540ce3cfd47c275dd');
INSERT INTO `core_user_shortcut_limit` VALUES ('a6c826a11818879b4832de03167c5fab', 'e8240eae240368d3b81344acf5ef06ec');
INSERT INTO `core_user_shortcut_limit` VALUES ('a9efec93fe86a12417a34e878502d2fc', '50bdd77ddb05ccb5201506236c80a412');
INSERT INTO `core_user_shortcut_limit` VALUES ('a9efec93fe86a12417a34e878502d2fc', '7697a17f9291534109351f7e41fd26cd');
INSERT INTO `core_user_shortcut_limit` VALUES ('a9efec93fe86a12417a34e878502d2fc', '8654ac15d6d46ead928e6f98cf37d7c1');
INSERT INTO `core_user_shortcut_limit` VALUES ('a9efec93fe86a12417a34e878502d2fc', 'a4b28e926583ed3b3977793d8ece5e54');
INSERT INTO `core_user_shortcut_limit` VALUES ('a9efec93fe86a12417a34e878502d2fc', 'e4e9828177cde11540ce3cfd47c275dd');
INSERT INTO `core_user_shortcut_limit` VALUES ('a9efec93fe86a12417a34e878502d2fc', 'e8240eae240368d3b81344acf5ef06ec');
INSERT INTO `core_user_shortcut_limit` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '8654ac15d6d46ead928e6f98cf37d7c1');
INSERT INTO `core_user_shortcut_limit` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '87399d83403f9acabae4899a258ec426');
INSERT INTO `core_user_shortcut_limit` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'a4b28e926583ed3b3977793d8ece5e54');
INSERT INTO `core_user_shortcut_limit` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'e4e9828177cde11540ce3cfd47c275dd');
INSERT INTO `core_user_shortcut_limit` VALUES ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'e8240eae240368d3b81344acf5ef06ec');
INSERT INTO `core_user_shortcut_limit` VALUES ('bbf19dacb58976e91b908131d1cad9ad', '50bdd77ddb05ccb5201506236c80a412');
INSERT INTO `core_user_shortcut_limit` VALUES ('bbf19dacb58976e91b908131d1cad9ad', '8654ac15d6d46ead928e6f98cf37d7c1');
INSERT INTO `core_user_shortcut_limit` VALUES ('bbf19dacb58976e91b908131d1cad9ad', 'a4b28e926583ed3b3977793d8ece5e54');
INSERT INTO `core_user_shortcut_limit` VALUES ('bbf19dacb58976e91b908131d1cad9ad', 'e4e9828177cde11540ce3cfd47c275dd');
INSERT INTO `core_user_shortcut_limit` VALUES ('bbf19dacb58976e91b908131d1cad9ad', 'e8240eae240368d3b81344acf5ef06ec');
INSERT INTO `core_user_shortcut_limit` VALUES ('be426623a662a66099adc78d22620467', '50bdd77ddb05ccb5201506236c80a412');
INSERT INTO `core_user_shortcut_limit` VALUES ('be426623a662a66099adc78d22620467', '7697a17f9291534109351f7e41fd26cd');
INSERT INTO `core_user_shortcut_limit` VALUES ('be426623a662a66099adc78d22620467', '8654ac15d6d46ead928e6f98cf37d7c1');
INSERT INTO `core_user_shortcut_limit` VALUES ('be426623a662a66099adc78d22620467', 'a4b28e926583ed3b3977793d8ece5e54');
INSERT INTO `core_user_shortcut_limit` VALUES ('be426623a662a66099adc78d22620467', 'e4e9828177cde11540ce3cfd47c275dd');
INSERT INTO `core_user_shortcut_limit` VALUES ('be426623a662a66099adc78d22620467', 'e8240eae240368d3b81344acf5ef06ec');
INSERT INTO `core_user_shortcut_limit` VALUES ('ca55d0bf4254e2a5bc7098dfb19d6bcf', '50bdd77ddb05ccb5201506236c80a412');
INSERT INTO `core_user_shortcut_limit` VALUES ('ca55d0bf4254e2a5bc7098dfb19d6bcf', '7697a17f9291534109351f7e41fd26cd');
INSERT INTO `core_user_shortcut_limit` VALUES ('ca55d0bf4254e2a5bc7098dfb19d6bcf', '8654ac15d6d46ead928e6f98cf37d7c1');
INSERT INTO `core_user_shortcut_limit` VALUES ('ca55d0bf4254e2a5bc7098dfb19d6bcf', 'a4b28e926583ed3b3977793d8ece5e54');
INSERT INTO `core_user_shortcut_limit` VALUES ('ca55d0bf4254e2a5bc7098dfb19d6bcf', 'e4e9828177cde11540ce3cfd47c275dd');
INSERT INTO `core_user_shortcut_limit` VALUES ('ca55d0bf4254e2a5bc7098dfb19d6bcf', 'e8240eae240368d3b81344acf5ef06ec');

-- ----------------------------
-- Table structure for jspc_evalitem
-- ----------------------------
DROP TABLE IF EXISTS `jspc_evalitem`;
CREATE TABLE `jspc_evalitem` (
  `evalItem_id` varchar(40) NOT NULL,
  `evalResult_id` varchar(40) DEFAULT NULL COMMENT '关联评审结果id',
  `name` varchar(100) NOT NULL COMMENT '称谓或荣誉名称',
  `type` varchar(10) NOT NULL COMMENT '所属分类',
  `description` text COMMENT '详情描述',
  `level` varchar(2) DEFAULT NULL COMMENT '荣誉级别(国家级；省级；市级...)',
  `grade` varchar(2) DEFAULT NULL COMMENT '荣誉等级(一等；二等；三等..)',
  `attach` varchar(100) DEFAULT NULL COMMENT '附件地址',
  `beginDate` date NOT NULL COMMENT '结束时间',
  `endDate` date DEFAULT NULL COMMENT '开始时间',
  `createDate` datetime NOT NULL COMMENT '创建时间',
  `editDate` datetime NOT NULL COMMENT '编辑时间',
  `creator` varchar(40) NOT NULL COMMENT '创建者id',
  `editor` varchar(40) NOT NULL COMMENT '修改者id',
  PRIMARY KEY (`evalItem_id`),
  KEY `jspc_evalitem_ibfk_1` (`evalResult_id`),
  CONSTRAINT `jspc_evalitem_ibfk_1` FOREIGN KEY (`evalResult_id`) REFERENCES `jspc_expreview` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='教师评测评测项表';

-- ----------------------------
-- Records of jspc_evalitem
-- ----------------------------

-- ----------------------------
-- Table structure for jspc_expreview
-- ----------------------------
DROP TABLE IF EXISTS `jspc_expreview`;
CREATE TABLE `jspc_expreview` (
  `id` varchar(40) NOT NULL COMMENT '编号',
  `user_id` varchar(40) NOT NULL COMMENT '教师编号',
  `user_name` varchar(40) NOT NULL COMMENT '教师姓名',
  `qualified` varchar(2) NOT NULL COMMENT '教师资质',
  `review_name` varchar(40) DEFAULT NULL COMMENT '评审人姓名',
  `status` varchar(2) NOT NULL COMMENT '0 未评审 1 通过 2 未通过 3 废除',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `review_date` datetime DEFAULT NULL COMMENT '评审时间',
  `note` varchar(200) DEFAULT NULL COMMENT '备注',
  `ext1` varchar(100) DEFAULT NULL,
  `ext2` varchar(100) DEFAULT NULL,
  `ext3` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jspc_expreview
-- ----------------------------

-- ----------------------------
-- Table structure for tdjs_album
-- ----------------------------
DROP TABLE IF EXISTS `tdjs_album`;
CREATE TABLE `tdjs_album` (
  `album_id` varchar(40) NOT NULL,
  `album_name` varchar(255) DEFAULT NULL,
  `album_description` varchar(255) DEFAULT NULL,
  `album_createdate` datetime DEFAULT NULL,
  `album_updatedate` datetime DEFAULT NULL,
  `album_createuser` varchar(255) DEFAULT NULL,
  `album_coverpath` varchar(255) DEFAULT NULL,
  `album_flag` varchar(255) DEFAULT NULL,
  `album_createuserbyid` varchar(40) DEFAULT NULL,
  `album_pic_total` int(11) DEFAULT NULL,
  `team_id` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`album_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tdjs_album
-- ----------------------------

-- ----------------------------
-- Table structure for tdjs_article
-- ----------------------------
DROP TABLE IF EXISTS `tdjs_article`;
CREATE TABLE `tdjs_article` (
  `article_id` varchar(40) NOT NULL,
  `title` varchar(100) NOT NULL COMMENT '内容标题',
  `tabloid` varchar(200) DEFAULT NULL COMMENT '内容摘要',
  `content` longtext COMMENT '主要内容',
  `status` varchar(2) NOT NULL COMMENT '启用禁用状态',
  `topset` varchar(2) DEFAULT NULL COMMENT '置顶标识',
  `channel_id` varchar(40) NOT NULL COMMENT '所属栏目',
  `templet` varchar(40) DEFAULT NULL COMMENT '所用模板',
  `provenance` varchar(40) NOT NULL COMMENT '内容来源',
  `publish_time` datetime NOT NULL COMMENT '发布时间',
  `publisher` varchar(20) NOT NULL COMMENT '发布者姓名',
  `creator` varchar(40) NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `editor` varchar(40) NOT NULL COMMENT '编辑者',
  `edit_time` datetime NOT NULL COMMENT '编辑时间',
  `article_type` varchar(3) NOT NULL COMMENT '1 通过 2 待审批 3 拒绝 4 草稿',
  `reviewer` varchar(40) DEFAULT NULL COMMENT '审核人',
  `review_time` datetime DEFAULT NULL COMMENT '审核时间',
  `team_id` varchar(40) DEFAULT NULL COMMENT '团队id',
  `article_limit` varchar(2) DEFAULT NULL COMMENT '文章权限 1公开，2 仅某个团队',
  `check_comment` varchar(200) DEFAULT NULL COMMENT '评审意见',
  PRIMARY KEY (`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='网站文章表';

-- ----------------------------
-- Records of tdjs_article
-- ----------------------------

-- ----------------------------
-- Table structure for tdjs_channel
-- ----------------------------
DROP TABLE IF EXISTS `tdjs_channel`;
CREATE TABLE `tdjs_channel` (
  `channel_id` varchar(40) NOT NULL COMMENT '栏目id',
  `channel_name` varchar(50) DEFAULT NULL COMMENT '栏目名称',
  `channel_description` varchar(2000) DEFAULT NULL,
  `channel_seq` int(40) DEFAULT NULL,
  `channel_parent` varchar(40) DEFAULT NULL,
  `channel_state` varchar(40) DEFAULT NULL,
  `channel_createuser` varchar(50) DEFAULT NULL,
  `channel_createdate` datetime DEFAULT NULL,
  `channel_updatedate` datetime DEFAULT NULL,
  `channel_url` varchar(255) DEFAULT NULL,
  `channel_type` varchar(255) DEFAULT NULL,
  `channel_picUrl` varchar(255) DEFAULT NULL,
  `channel_edituser` varchar(50) DEFAULT NULL,
  `team_id` varchar(40) DEFAULT NULL COMMENT '团队id',
  PRIMARY KEY (`channel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tdjs_channel
-- ----------------------------

-- ----------------------------
-- Table structure for tdjs_picture
-- ----------------------------
DROP TABLE IF EXISTS `tdjs_picture`;
CREATE TABLE `tdjs_picture` (
  `picture_id` varchar(40) NOT NULL COMMENT '图片ID',
  `picture_name` varchar(100) NOT NULL COMMENT '图片名称',
  `description` varchar(400) DEFAULT NULL COMMENT '图片描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `user_id` varchar(40) NOT NULL COMMENT '创建人编号',
  `user_name` varchar(40) NOT NULL COMMENT '创建人姓名',
  `album_id` varchar(40) DEFAULT NULL COMMENT '所属相册编号',
  `flag` varchar(2) NOT NULL COMMENT '标识 0 自定义图片 1 最新图片 2 首页轮播图片 3 欢迎页轮播图片',
  `path` varchar(500) NOT NULL COMMENT '路径',
  `order` int(3) DEFAULT NULL COMMENT '排序号',
  `show` varchar(2) NOT NULL COMMENT '是否显示 0 不显示 1 显示',
  `team_id` varchar(40) DEFAULT NULL COMMENT '团队id',
  PRIMARY KEY (`picture_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tdjs_picture
-- ----------------------------

-- ----------------------------
-- Table structure for tdjs_video
-- ----------------------------
DROP TABLE IF EXISTS `tdjs_video`;
CREATE TABLE `tdjs_video` (
  `vd_id` varchar(40) NOT NULL,
  `vd_name` varchar(50) DEFAULT NULL,
  `vd_description` varchar(500) DEFAULT NULL,
  `vd_num` int(10) DEFAULT NULL,
  `vd_like` int(10) DEFAULT NULL,
  `vd_savepath` varchar(100) DEFAULT NULL,
  `vd_type` varchar(2) DEFAULT NULL,
  `vd_thumbnailspath` varchar(100) DEFAULT NULL,
  `vd_tags` varchar(200) DEFAULT NULL,
  `vd_uploaduser` varchar(50) DEFAULT NULL,
  `vd_uploaddate` datetime DEFAULT NULL,
  `vd_updateuser` varchar(50) DEFAULT NULL,
  `vd_updatedate` datetime DEFAULT NULL,
  `vd_status` varchar(2) DEFAULT NULL,
  `vd_grade` varchar(2) DEFAULT NULL,
  `vd_heat` varchar(30) DEFAULT NULL,
  `vd_istop` varchar(2) DEFAULT NULL,
  `vd_isdisplay` varchar(2) DEFAULT NULL,
  `vd_time` varchar(50) DEFAULT NULL,
  `vd_order` int(11) NOT NULL DEFAULT '0',
  `team_id` varchar(40) DEFAULT NULL COMMENT '团队id',
  PRIMARY KEY (`vd_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='网站视频表';

-- ----------------------------
-- Records of tdjs_video
-- ----------------------------

-- ----------------------------
-- Table structure for test
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test` (
  `a` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of test
-- ----------------------------

-- ----------------------------
-- Table structure for wzgl_album
-- ----------------------------
DROP TABLE IF EXISTS `wzgl_album`;
CREATE TABLE `wzgl_album` (
  `album_id` varchar(40) NOT NULL,
  `album_name` varchar(255) DEFAULT NULL,
  `album_description` varchar(255) DEFAULT NULL,
  `album_createdate` datetime DEFAULT NULL,
  `album_updatedate` datetime DEFAULT NULL,
  `album_createuser` varchar(255) DEFAULT NULL,
  `album_coverpath` varchar(255) DEFAULT NULL,
  `album_flag` varchar(255) DEFAULT NULL,
  `album_createuserbyid` varchar(40) DEFAULT NULL,
  `album_pic_total` int(11) DEFAULT NULL,
  PRIMARY KEY (`album_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wzgl_album
-- ----------------------------

-- ----------------------------
-- Table structure for wzgl_article
-- ----------------------------
DROP TABLE IF EXISTS `wzgl_article`;
CREATE TABLE `wzgl_article` (
  `article_id` varchar(40) NOT NULL,
  `title` varchar(100) NOT NULL COMMENT '内容标题',
  `tabloid` varchar(200) DEFAULT NULL COMMENT '内容摘要',
  `content` longtext COMMENT '主要内容',
  `status` varchar(2) NOT NULL COMMENT '启用禁用状态',
  `topset` varchar(2) DEFAULT NULL COMMENT '置顶标识',
  `channel_id` varchar(40) NOT NULL COMMENT '所属栏目',
  `templet` varchar(40) DEFAULT NULL COMMENT '所用模板',
  `provenance` varchar(150) DEFAULT NULL COMMENT '内容来源',
  `publish_time` datetime NOT NULL COMMENT '发布时间',
  `publisher` varchar(20) NOT NULL COMMENT '发布者姓名',
  `creator` varchar(40) NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `editor` varchar(40) NOT NULL COMMENT '编辑者',
  `edit_time` datetime NOT NULL COMMENT '编辑时间',
  PRIMARY KEY (`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='网站文章表';

-- ----------------------------
-- Records of wzgl_article
-- ----------------------------
INSERT INTO `wzgl_article` VALUES ('3fb6c79f9f5f28c796ab14b3ef343150', 'notice1', '据台湾“中时电子报”7日报道，台湾“立法院”内政委员会7日邀请陆委会主委张小月、海基会副董事长张天钦及“内政部”、“经济部”、“交通部”等官员对“今年‘大选’以来两岸互动交流急冻，新政府因应作为及如何重启官方协商机制”进行专题报告，并备质询。国民党主席洪秀柱日前前往北京接受习近平总书记的会见，国民党政策会执行长蔡正元当时曾表示，国民党将以民间身份架构国共沟通机制。对此，张小月指出，“两岸和平发展论', '<p style=\"margin-top: 0px; margin-bottom: 0px; padding: 0px 0px 24px; text-indent: 2em; color: rgb(56, 56, 56); font-family: simsun; line-height: 29.6px; white-space: normal; background-color: rgb(255, 255, 255);\">据台湾“中时电子报”7日报道，台湾“立法院”内政委员会7日邀请陆委会主委张小月、海基会副董事长张天钦及“内政部”、“经济部”、“交通部”等官员对“今年‘大选’以来两岸互动交流急冻，新政府因应作为及如何重启官方协商机制”进行专题报告，并备质询。</p><p style=\"margin-top: 0px; margin-bottom: 0px; padding: 0px 0px 24px; text-indent: 2em; color: rgb(56, 56, 56); font-family: simsun; line-height: 29.6px; white-space: normal; background-color: rgb(255, 255, 255);\">国民党主席洪秀柱日前前往北京接受习近平总书记的会见，国民党政策会执行长蔡正元当时曾表示，国民党将以民间身份架构国共沟通机制。</p><p style=\"margin-top: 0px; margin-bottom: 0px; padding: 0px 0px 24px; text-indent: 2em; color: rgb(56, 56, 56); font-family: simsun; line-height: 29.6px; white-space: normal; background-color: rgb(255, 255, 255);\">对此，张小月指出，“两岸和平发展论坛”代表两岸政党交流，但两岸交流应该更全面，尤其涉及公权力事务，只有政府与政府授权的机构才有权处理，因此国共联系不可能取代官方沟通管道。</p><p><br/></p>', '1', '0', '3b8826540e278fcc0d53a849888db207', '', '', '2016-11-08 15:02:49', '超级管理员', '240b59bf433b7701b58ba7adb63bfde0', '2016-11-08 15:05:18', '240b59bf433b7701b58ba7adb63bfde0', '2016-11-08 15:05:18');
INSERT INTO `wzgl_article` VALUES ('95fd82add9086a4676c27e273be89656', 'news', '陈超明随后质询，并出题考张小月。陈超明先问七位中共中央常委分别是谁，张小月回答“习近平、李克强、俞正声、刘延东（刘延东不是七常委之一，还有张德江、刘云山、王岐山、张高丽——观察者网注）……”，后面就答不出来，转头向台下的陆委会同事求助。陈超明认定张小月答不出来，又问大陆四个直辖市市长的名字，但张小月一个也没回答出来。陈超明继续问张小月，大陆台商最多的省份是哪几个，张小月答出“福建、广东”，其他就答', '<p style=\"margin-top: 0px; margin-bottom: 0px; padding: 0px 0px 24px; text-indent: 2em; color: rgb(56, 56, 56); font-family: simsun; line-height: 29.6px; white-space: normal; background-color: rgb(255, 255, 255);\">陈超明随后质询，并出题考张小月。陈超明先问七位中共中央常委分别是谁，张小月回答“习近平、李克强、俞正声、刘延东（刘延东不是七常委之一，还有张德江、刘云山、王岐山、张高丽——观察者网注）……”，后面就答不出来，转头向台下的陆委会同事求助。</p><p style=\"margin-top: 0px; margin-bottom: 0px; padding: 0px 0px 24px; text-indent: 2em; color: rgb(56, 56, 56); font-family: simsun; line-height: 29.6px; white-space: normal; background-color: rgb(255, 255, 255);\">陈超明认定张小月答不出来，又问大陆四个直辖市市长的名字，但张小月一个也没回答出来。</p><p style=\"margin-top: 0px; margin-bottom: 0px; padding: 0px 0px 24px; text-indent: 2em; color: rgb(56, 56, 56); font-family: simsun; line-height: 29.6px; white-space: normal; background-color: rgb(255, 255, 255);\">陈超明继续问张小月，大陆台商最多的省份是哪几个，张小月答出“福建、广东”，其他就答不出了，陈超明直接喊错，说依序是江苏、广东、浙江、福建……但陈超明自己也忘记排序，被台下“绿委”嘲讽时，陈也不好意思的笑场，但仍不忘批张小月说“但你比我还差！”</p><p><br/></p>', '1', '0', '3dab29df2dac9cefaa08f51450aa6111', '', '', '2016-11-08 15:05:22', '超级管理员', '240b59bf433b7701b58ba7adb63bfde0', '2016-11-08 15:05:51', '240b59bf433b7701b58ba7adb63bfde0', '2016-11-08 15:05:51');
INSERT INTO `wzgl_article` VALUES ('cea7551020f1fee883b7e2da209e5998', '哈哈', '', '', '1', '0', '05d87eb2e1bd20cd5d1fb5fb341ad3d2', '', '', '2016-11-09 15:33:08', '超级管理员', '240b59bf433b7701b58ba7adb63bfde0', '2016-11-09 15:33:28', '240b59bf433b7701b58ba7adb63bfde0', '2016-11-09 15:33:28');
INSERT INTO `wzgl_article` VALUES ('f81bd52d9cfa76054d6ca0802f802b3e', '不能', '', '<p style=\"text-align: center;\"><img src=\"http://192.168.2.6:8180/upload/images/1478745206064.png\" title=\"bottom-img2.png\" alt=\"bottom-img2.png\"/></p>', '1', '0', '05d87eb2e1bd20cd5d1fb5fb341ad3d2', '', '', '2016-11-10 10:44:12', '超级管理员', '240b59bf433b7701b58ba7adb63bfde0', '2016-11-10 10:44:35', '240b59bf433b7701b58ba7adb63bfde0', '2016-11-10 10:45:20');

-- ----------------------------
-- Table structure for wzgl_channel
-- ----------------------------
DROP TABLE IF EXISTS `wzgl_channel`;
CREATE TABLE `wzgl_channel` (
  `channel_id` varchar(40) NOT NULL COMMENT '栏目id',
  `channel_name` varchar(50) DEFAULT NULL COMMENT '栏目名称',
  `channel_description` varchar(2000) DEFAULT NULL,
  `channel_seq` int(40) DEFAULT NULL,
  `channel_parent` varchar(40) DEFAULT NULL,
  `channel_state` varchar(40) DEFAULT NULL,
  `channel_createuser` varchar(50) DEFAULT NULL,
  `channel_createdate` datetime DEFAULT NULL,
  `channel_updatedate` datetime DEFAULT NULL,
  `channel_url` varchar(255) DEFAULT NULL,
  `channel_type` varchar(255) DEFAULT NULL COMMENT '栏目类型 1：网站栏目;2:首页栏目；3班级栏目 4社团栏目',
  `channel_picUrl` varchar(255) DEFAULT NULL,
  `channel_edituser` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`channel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of wzgl_channel
-- ----------------------------
INSERT INTO `wzgl_channel` VALUES ('05d87eb2e1bd20cd5d1fb5fb341ad3d2', '保健食谱', '', '0', '9d15dfb01ebec24f82e1bad2cbc4e562', '1', '240b59bf433b7701b58ba7adb63bfde0', '2016-11-08 10:53:18', '2016-11-09 15:43:29', 'front/list_05d87eb2e1bd20cd5d1fb5fb341ad3d2.html', '1', null, '240b59bf433b7701b58ba7adb63bfde0');
INSERT INTO `wzgl_channel` VALUES ('0626919cbb0fcc6d885de0babbe4f85d', '教师团队', '', '0', '27922412c8a2f7468a58054b5a27f6bb', '1', '240b59bf433b7701b58ba7adb63bfde0', '2016-11-08 10:54:01', '2016-11-09 15:44:49', 'front/list_0626919cbb0fcc6d885de0babbe4f85d.html', '1', null, '240b59bf433b7701b58ba7adb63bfde0');
INSERT INTO `wzgl_channel` VALUES ('0f52c4016434b81ec035c75405cb747c', '院所简介', '', '0', 'top', '1', '240b59bf433b7701b58ba7adb63bfde0', '2016-11-08 10:50:59', '2016-11-09 15:39:09', 'front/list_0f52c4016434b81ec035c75405cb747c.html', '1', null, '240b59bf433b7701b58ba7adb63bfde0');
INSERT INTO `wzgl_channel` VALUES ('1554fbe64b6ef180d82394a212732088', '育儿知识', '', '1', '2d4197c3b38d3a8f77710e66f4ef3968', '1', '240b59bf433b7701b58ba7adb63bfde0', '2016-11-08 10:54:52', '2016-11-09 15:46:32', 'front/list_1554fbe64b6ef180d82394a212732088.html', '1', null, '240b59bf433b7701b58ba7adb63bfde0');
INSERT INTO `wzgl_channel` VALUES ('15fdf33f6dc662d491e984206086ff85', '园所容貌', '', '1', '0f52c4016434b81ec035c75405cb747c', '1', '240b59bf433b7701b58ba7adb63bfde0', '2016-11-08 10:51:38', '2016-11-09 15:39:58', 'front/list_15fdf33f6dc662d491e984206086ff85.html', '1', null, '240b59bf433b7701b58ba7adb63bfde0');
INSERT INTO `wzgl_channel` VALUES ('27922412c8a2f7468a58054b5a27f6bb', '教育园地', '', '3', 'top', '1', '240b59bf433b7701b58ba7adb63bfde0', '2016-11-08 10:53:50', '2016-11-09 15:44:20', 'front/list_27922412c8a2f7468a58054b5a27f6bb.html', '1', null, '240b59bf433b7701b58ba7adb63bfde0');
INSERT INTO `wzgl_channel` VALUES ('2d4197c3b38d3a8f77710e66f4ef3968', '早教乐园', '', '4', 'top', '1', '240b59bf433b7701b58ba7adb63bfde0', '2016-11-08 10:54:28', '2016-11-09 15:45:47', 'front/list_2d4197c3b38d3a8f77710e66f4ef3968.html', '1', null, '240b59bf433b7701b58ba7adb63bfde0');
INSERT INTO `wzgl_channel` VALUES ('3b8826540e278fcc0d53a849888db207', '通知公告', '', '0', 'ee73c789e3211f14f0b893fb962cc61a', '1', '240b59bf433b7701b58ba7adb63bfde0', '2016-11-08 10:52:13', '2016-11-09 15:41:36', 'front/list_3b8826540e278fcc0d53a849888db207.html', '1', null, '240b59bf433b7701b58ba7adb63bfde0');
INSERT INTO `wzgl_channel` VALUES ('3ca97e31fca2abda3124f686f1efe69c', '友情链接', '友情链接分类', '0', 'top', '1', '240b59bf433b7701b58ba7adb63bfde0', '2016-11-10 10:59:31', '2016-11-10 10:59:31', '', '2', null, '240b59bf433b7701b58ba7adb63bfde0');
INSERT INTO `wzgl_channel` VALUES ('3dab29df2dac9cefaa08f51450aa6111', '新闻动态', '', '1', 'ee73c789e3211f14f0b893fb962cc61a', '1', '240b59bf433b7701b58ba7adb63bfde0', '2016-11-08 10:52:27', '2016-11-09 15:50:48', 'front/list_3dab29df2dac9cefaa08f51450aa6111.html', '1', null, '240b59bf433b7701b58ba7adb63bfde0');
INSERT INTO `wzgl_channel` VALUES ('4110ee8fa58233fe9f4163f11e0fbf6d', '海淀区教育局', '', '0', '3ca97e31fca2abda3124f686f1efe69c', '1', '240b59bf433b7701b58ba7adb63bfde0', '2016-11-10 11:00:30', '2016-11-10 11:24:31', 'http://www.ruyile.com/jiaoyuju.aspx?id=3952', '2', null, '240b59bf433b7701b58ba7adb63bfde0');
INSERT INTO `wzgl_channel` VALUES ('45a61dfef37dc42f1e08301ae93a031e', '社区活动', '', '0', '2d4197c3b38d3a8f77710e66f4ef3968', '1', '240b59bf433b7701b58ba7adb63bfde0', '2016-11-08 10:54:39', '2016-11-09 15:46:08', 'front/list_45a61dfef37dc42f1e08301ae93a031e.html', '1', null, '240b59bf433b7701b58ba7adb63bfde0');
INSERT INTO `wzgl_channel` VALUES ('4adf0fcc42756be4aed9693647177ca2', '园所荣誉', '', '2', '0f52c4016434b81ec035c75405cb747c', '1', '240b59bf433b7701b58ba7adb63bfde0', '2016-11-08 10:51:52', '2016-11-09 15:40:45', 'front/list_4adf0fcc42756be4aed9693647177ca2.html', '1', null, '240b59bf433b7701b58ba7adb63bfde0');
INSERT INTO `wzgl_channel` VALUES ('7047000646705a6b953c62d012684927', '教师随笔', '', '1', '27922412c8a2f7468a58054b5a27f6bb', '1', '240b59bf433b7701b58ba7adb63bfde0', '2016-11-08 10:54:13', '2016-11-09 15:45:16', 'front/list_7047000646705a6b953c62d012684927.html', '1', null, '240b59bf433b7701b58ba7adb63bfde0');
INSERT INTO `wzgl_channel` VALUES ('9946e5db38fd8cc55ccdb02c210ce79e', '园所简介', '', '0', '0f52c4016434b81ec035c75405cb747c', '1', '240b59bf433b7701b58ba7adb63bfde0', '2016-11-08 10:51:25', '2016-11-09 15:39:16', 'front/list_9946e5db38fd8cc55ccdb02c210ce79e.html', '1', null, '240b59bf433b7701b58ba7adb63bfde0');
INSERT INTO `wzgl_channel` VALUES ('9ce8b430b7c3cff0a4ed6ea0f2cbd975', '保健宣传', '', '1', '9d15dfb01ebec24f82e1bad2cbc4e562', '1', '240b59bf433b7701b58ba7adb63bfde0', '2016-11-08 10:53:32', '2016-11-09 15:43:52', 'front/list_9ce8b430b7c3cff0a4ed6ea0f2cbd975.html', '1', null, '240b59bf433b7701b58ba7adb63bfde0');
INSERT INTO `wzgl_channel` VALUES ('9d15dfb01ebec24f82e1bad2cbc4e562', '保健中心', '', '2', 'top', '1', '240b59bf433b7701b58ba7adb63bfde0', '2016-11-08 10:52:45', '2016-11-09 15:43:07', 'front/list_9d15dfb01ebec24f82e1bad2cbc4e562.html', '1', null, '240b59bf433b7701b58ba7adb63bfde0');
INSERT INTO `wzgl_channel` VALUES ('c3e9f2e63471bc505844b5e70d76cbee', '亲亲宝贝库洛库洛网', '', '2', '3ca97e31fca2abda3124f686f1efe69c', '1', '240b59bf433b7701b58ba7adb63bfde0', '2016-11-10 11:01:22', '2016-11-10 11:01:22', '#', '2', null, '240b59bf433b7701b58ba7adb63bfde0');
INSERT INTO `wzgl_channel` VALUES ('d435d9dc1c78e6e701c6aa34849eafa2', '幼儿教育网', '', '3', '3ca97e31fca2abda3124f686f1efe69c', '1', '240b59bf433b7701b58ba7adb63bfde0', '2016-11-10 11:01:41', '2016-11-10 11:23:23', '#', '2', null, '240b59bf433b7701b58ba7adb63bfde0');
INSERT INTO `wzgl_channel` VALUES ('e8f1e52d3488def75936e7508f0d849f', '中国教育网', '', '1', '3ca97e31fca2abda3124f686f1efe69c', '1', '240b59bf433b7701b58ba7adb63bfde0', '2016-11-10 11:00:54', '2016-11-10 11:00:54', '#', '2', null, '240b59bf433b7701b58ba7adb63bfde0');
INSERT INTO `wzgl_channel` VALUES ('ee73c789e3211f14f0b893fb962cc61a', '最新动态', '', '1', 'top', '1', '240b59bf433b7701b58ba7adb63bfde0', '2016-11-08 10:52:03', '2016-11-09 15:41:14', 'front/list_ee73c789e3211f14f0b893fb962cc61a.html', '1', null, '240b59bf433b7701b58ba7adb63bfde0');
INSERT INTO `wzgl_channel` VALUES ('ef79efab70b25d9fba5edcbfea82f2f1', '班级论坛', '', '6', 'top', '1', '240b59bf433b7701b58ba7adb63bfde0', '2016-11-08 10:55:20', '2016-11-09 15:47:19', 'front/list_ef79efab70b25d9fba5edcbfea82f2f1.html', '1', null, '240b59bf433b7701b58ba7adb63bfde0');
INSERT INTO `wzgl_channel` VALUES ('f73d02f7fc00e4b0d91768d2cb249dc9', '党群建设', '', '5', 'top', '1', '240b59bf433b7701b58ba7adb63bfde0', '2016-11-08 10:55:07', '2016-11-09 15:46:51', 'front/list_f73d02f7fc00e4b0d91768d2cb249dc9.html', '1', null, '240b59bf433b7701b58ba7adb63bfde0');

-- ----------------------------
-- Table structure for wzgl_email
-- ----------------------------
DROP TABLE IF EXISTS `wzgl_email`;
CREATE TABLE `wzgl_email` (
  `email_id` varchar(40) NOT NULL,
  `email_title` varchar(100) DEFAULT NULL COMMENT '邮件标题',
  `email_content` longtext COMMENT '邮件提问内容',
  `email_flag` varchar(2) DEFAULT NULL COMMENT '邮件是否显示',
  `email_askDate` date DEFAULT NULL COMMENT '邮件提问日期',
  `email_replyContent` varchar(200) DEFAULT NULL COMMENT '回复内容',
  `email_replyUser` varchar(50) DEFAULT NULL COMMENT '邮件回复人',
  `email_replydate` date DEFAULT NULL COMMENT '邮件回复日期',
  `email_askPerson` varchar(50) DEFAULT NULL COMMENT '提问人',
  PRIMARY KEY (`email_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wzgl_email
-- ----------------------------
INSERT INTO `wzgl_email` VALUES ('18a43a73cdee7908faa745d84c0ffcfa', '我们的家', '据韩联社10日报道，为应对特朗普当选美国总统，韩国9日召开部长会议。韩国国防部10日表示会照常推进韩国与奥巴马政府达成的协议事项。如驻韩美军部署“萨德”反导系统、商讨旨在提高对朝延伸威慑执行力的多种方案、有条件地收回作战指挥权等。\n尹炳世外交部长也表示，即使特朗普当选，重视韩美同盟的基调也不会发生变化。此外，外交部考虑派遣第一次官（副部长）林圣男或外长助理级的高官前往华盛顿与特朗普阵营协调对朝政策。外交部将强调韩美加强对朝威慑力和继续制裁朝鲜的必要性。', '1', '2016-11-08', '大大', '超级管理员', '2016-11-11', '老王');
INSERT INTO `wzgl_email` VALUES ('1a9dc17d9b27ea315cd18da525c21187', '测试的', '菜单', '1', '2016-11-07', '', '超级管理员', '2016-11-11', '咋航三');
INSERT INTO `wzgl_email` VALUES ('f7b903af2cfa8bdae45b6ec8f47ac4b0', '啊哈哈哈哈', '寒假什么时候考试', '1', '2016-11-16', '', '超级管理员', '2016-11-11', '王五');

-- ----------------------------
-- Table structure for wzgl_picture
-- ----------------------------
DROP TABLE IF EXISTS `wzgl_picture`;
CREATE TABLE `wzgl_picture` (
  `picture_id` varchar(40) NOT NULL COMMENT '图片ID',
  `picture_name` varchar(100) NOT NULL COMMENT '图片名称',
  `description` varchar(400) DEFAULT NULL COMMENT '图片描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `user_id` varchar(40) NOT NULL COMMENT '创建人编号',
  `user_name` varchar(40) NOT NULL COMMENT '创建人姓名',
  `album_id` varchar(40) DEFAULT NULL COMMENT '所属相册编号',
  `flag` varchar(2) NOT NULL COMMENT '标识 0 自定义图片 1 最新图片 2 首页轮播图片 3 欢迎页轮播图片',
  `path` varchar(500) NOT NULL COMMENT '路径',
  `order` int(3) DEFAULT NULL COMMENT '排序号',
  `show` varchar(2) NOT NULL COMMENT '是否显示 0 不显示 1 显示',
  PRIMARY KEY (`picture_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wzgl_picture
-- ----------------------------
INSERT INTO `wzgl_picture` VALUES ('06cb5cacc1259ccb35c55f852b6fbc80', '13', '呵呵', '2016-11-10 10:00:00', null, '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', null, '4', '/images/1478742356563.png', '13', '1');
INSERT INTO `wzgl_picture` VALUES ('08263004617e56761f9feec8d8c7bc00', '105', 'test', '2016-11-10 09:57:34', null, '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', null, '4', '/images/1478742210438.png', '5', '1');
INSERT INTO `wzgl_picture` VALUES ('093395f9f959046c15931c3558a9c800', '106', 'test', '2016-11-10 09:57:51', null, '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', null, '4', '/images/1478742227270.png', '6', '1');
INSERT INTO `wzgl_picture` VALUES ('17d90079060fff35965b9010c28ca964', '116', '是发给对方', '2016-11-10 10:01:02', null, '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', null, '4', '/images/1478742418714.png', '16', '1');
INSERT INTO `wzgl_picture` VALUES ('4cad551b86d512197558707f82b51302', '14', '个省份的', '2016-11-10 10:00:15', null, '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', null, '4', '/images/1478742371009.png', '14', '1');
INSERT INTO `wzgl_picture` VALUES ('531eac33e9e078b3e653c8871c5f7a4c', '110', 'test', '2016-11-10 09:59:08', null, '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', null, '4', '/images/1478742303835.png', '10', '1');
INSERT INTO `wzgl_picture` VALUES ('5eb2f7cfe0be3d1962bd8205d59fa4e9', '101', 'test', '2016-11-10 09:56:30', null, '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', null, '4', '/images/1478742146556.png', '1', '1');
INSERT INTO `wzgl_picture` VALUES ('8ec66dc73a728be0c70c502a71de05ad', '109', 'test', '2016-11-10 09:58:39', null, '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', null, '4', '/images/1478742275162.png', '9', '1');
INSERT INTO `wzgl_picture` VALUES ('9f3b3f9dd0fb15a638a30e53cb78261a', '118', '', '2016-11-10 10:01:40', null, '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', null, '0', '/images/1478742456310.jpg', '18', '1');
INSERT INTO `wzgl_picture` VALUES ('b23ee50a652fc48dca5232bf539a6a50', '107', 'test', '2016-11-10 09:58:06', null, '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', null, '4', '/images/1478742242761.png', '7', '1');
INSERT INTO `wzgl_picture` VALUES ('b49e3780e9ef4b66bfe5af4952b92ee4', '102', 'test', '2016-11-10 09:56:47', null, '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', null, '4', '/images/1478742162920.png', '2', '1');
INSERT INTO `wzgl_picture` VALUES ('bbd190d27d439961086c7a495474b8eb', '15', '地方', '2016-11-10 10:00:29', null, '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', null, '4', '/images/1478742385033.png', '15', '1');
INSERT INTO `wzgl_picture` VALUES ('c6c2fbc3eda5327d33a1513bb76c5203', '117', '多个', '2016-11-10 10:01:15', null, '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', null, '4', '/images/1478742431693.png', '17', '1');
INSERT INTO `wzgl_picture` VALUES ('cb0ae76e107c1e25b402c4983d8c88ac', '104', 'test', '2016-11-10 09:57:20', null, '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', null, '4', '/images/1478742196273.png', '4', '1');
INSERT INTO `wzgl_picture` VALUES ('cbaae672dd862485e00e2262ac2d2872', '112', '地方', '2016-11-10 09:59:46', null, '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', null, '4', '/images/1478742341915.png', '12', '1');
INSERT INTO `wzgl_picture` VALUES ('d4ddc68a0fcc6b4a196f16ed2e7df1ca', '100', 'test', '2016-11-10 09:56:07', null, '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', null, '4', '/images/1478742123358.png', '0', '1');
INSERT INTO `wzgl_picture` VALUES ('dc703481b4fb79fd5e2afa96beb116df', '首页轮播1', '', '2016-11-08 13:15:02', '2016-11-08 13:15:57', '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', null, '2', '/images/1478581436723.jpg', '0', '1');
INSERT INTO `wzgl_picture` VALUES ('de03fea0d9bf4b930342d011f3942607', '图片集', '', '2016-11-08 17:44:01', null, '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', null, '4', '/images/1478597576018.png', '0', '1');
INSERT INTO `wzgl_picture` VALUES ('df28910593673d063b4b83384036eb6d', '108', '8', '2016-11-10 09:58:21', null, '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', null, '4', '/images/1478742256973.png', '0', '1');
INSERT INTO `wzgl_picture` VALUES ('e6c0596daa0128cc419ec4d689b9c490', '首页轮播2', '', '2016-11-08 13:15:42', null, '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', null, '2', '/images/1478581476152.jpg', '0', '1');
INSERT INTO `wzgl_picture` VALUES ('eb6eda8e4a9f3a4239e48721517e766d', '103', '特色', '2016-11-10 09:57:03', null, '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', null, '4', '/images/1478742179175.png', '3', '1');
INSERT INTO `wzgl_picture` VALUES ('f0b26292d80af150254aab7cd3ae7b6d', '111', '热', '2016-11-10 09:59:27', null, '240b59bf433b7701b58ba7adb63bfde0', '超级管理员', null, '4', '/images/1478742322930.png', '11', '1');

-- ----------------------------
-- Table structure for wzgl_tag
-- ----------------------------
DROP TABLE IF EXISTS `wzgl_tag`;
CREATE TABLE `wzgl_tag` (
  `tag_id` varchar(255) NOT NULL,
  `tag_name` varchar(255) NOT NULL,
  `tag_styledescription` varchar(255) DEFAULT NULL,
  `tag_url` varchar(255) DEFAULT NULL,
  `tag_addtime` datetime DEFAULT NULL,
  `tag_adduser` varchar(255) DEFAULT NULL,
  `tag_edittime` datetime DEFAULT NULL,
  `tag_edituser` varchar(255) DEFAULT NULL,
  `tag_state` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wzgl_tag
-- ----------------------------

-- ----------------------------
-- Table structure for wzgl_video
-- ----------------------------
DROP TABLE IF EXISTS `wzgl_video`;
CREATE TABLE `wzgl_video` (
  `vd_id` varchar(40) NOT NULL,
  `vd_name` varchar(50) DEFAULT NULL,
  `vd_description` varchar(500) DEFAULT NULL,
  `vd_num` int(10) DEFAULT NULL,
  `vd_like` int(10) DEFAULT NULL,
  `vd_savepath` varchar(100) DEFAULT NULL,
  `vd_type` varchar(2) DEFAULT NULL,
  `vd_thumbnailspath` varchar(100) DEFAULT NULL,
  `vd_tags` varchar(200) DEFAULT NULL,
  `vd_uploaduser` varchar(50) DEFAULT NULL,
  `vd_uploaddate` datetime DEFAULT NULL,
  `vd_updateuser` varchar(50) DEFAULT NULL,
  `vd_updatedate` datetime DEFAULT NULL,
  `vd_status` varchar(2) DEFAULT NULL,
  `vd_grade` varchar(2) DEFAULT NULL,
  `vd_heat` varchar(30) DEFAULT NULL,
  `vd_istop` varchar(2) DEFAULT NULL,
  `vd_isdisplay` varchar(2) DEFAULT NULL,
  `vd_time` varchar(50) DEFAULT NULL,
  `vd_order` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`vd_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='网站视频表';

-- ----------------------------
-- Records of wzgl_video
-- ----------------------------
INSERT INTO `wzgl_video` VALUES ('de34356ea67049cf28a7bb7164a550c3', 'test', '测试用的', '0', '0', '/video/1478592771896.flv', null, '/video/1478592771896.jpg', null, '超级管理员', '2016-11-08 16:24:06', null, null, '2', null, null, null, '1', ' 00:02:24.10', '0');
