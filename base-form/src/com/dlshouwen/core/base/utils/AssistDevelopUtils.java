package com.dlshouwen.core.base.utils;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.sql.DataSource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import com.dlshouwen.core.base.config.CONFIG;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 辅助开发工具类
 * @author 大连首闻科技有限公司
 * @version 2016-5-25 08:57:32
 */
@SuppressWarnings("unchecked")
public class AssistDevelopUtils {
	
	/**
	 * 获取数据源
	 * <p>根据驱动类地址、链接地址、用户名及密码获取数据源信息，使用C3P0数据连接池
	 * @param driverClass 驱动类地址
	 * @param jdbcUrl 链接地址
	 * @param username 用户名
	 * @param password 密码
	 * @return 数据源对象
	 * @throws Exception 抛出全部异常
	 */
	public static DataSource getDataSource(String driverClass, String jdbcUrl, 
			String username, String password) throws Exception {
//		初始化数据源
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		try {
			dataSource.setDriverClass(driverClass);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		dataSource.setJdbcUrl(jdbcUrl);
		dataSource.setUser(username);
		dataSource.setPassword(password);
		return dataSource;
	}
	
	/**
	 * 获取对应SQL
	 * <p>根据数据源及表名获取常用SQL，输入SQL包含：insert、update、select，可控制输出类型
	 * @param dataSource 连接数据源
	 * @param tableName 表名
	 * @param aliasName 表别名
	 * @param type 输入类型，0-${*}格式输出、1-?输出
	 * @throws Exception 抛出全部异常
	 */
	public static void getSQL(DataSource dataSource, 
			String tableName, String aliasName, String type) throws Exception{
//		获取JDBCTemplate对象
		JdbcTemplate jt = new JdbcTemplate(dataSource);
//		获取列数据列表
		StringBuffer sql = new StringBuffer();
		sql.append("desc "+tableName);
		List<Map<String, Object>> columnList = jt.queryForList(sql.toString());
//		获取insert语句
		StringBuffer insertSql = new StringBuffer();
		insertSql.append("insert into ").append(tableName).append(" (");
		for(Map<String, Object> columnInfo : columnList)
			insertSql.append(MapUtils.getString(columnInfo, "Field").toLowerCase()).append(", ");
		insertSql.deleteCharAt(insertSql.length()-1).deleteCharAt(insertSql.length()-1).append(") values (");
		for(Map<String, Object> columnInfo : columnList)
			if("0".equals(type)){
				insertSql.append("${").append(MapUtils.getString(columnInfo, "Field").toLowerCase()).append(" }, ");
			}else if("1".equals(type)){
				insertSql.append("?, ");
			}
		insertSql.deleteCharAt(insertSql.length()-1).deleteCharAt(insertSql.length()-1).append(")");
//		获取update语句
		StringBuffer updateSql = new StringBuffer();
		updateSql.append("update ").append(tableName).append(" set ");
		for(Map<String, Object> columnInfo : columnList){
			updateSql.append(MapUtils.getString(columnInfo, "Field").toLowerCase()).append("=");
			if("0".equals(type)){
				updateSql.append("${").append(MapUtils.getString(columnInfo, "Field").toLowerCase()).append(" }");
			}else if("1".equals(type)){
				updateSql.append("?");
			}
			updateSql.append(", ");
		}
		updateSql.deleteCharAt(updateSql.length()-1).deleteCharAt(updateSql.length()-1);
//		获取select语句
		StringBuffer selectSql = new StringBuffer();
		selectSql.append("select ");
		for(Map<String, Object> columnInfo : columnList)
			selectSql.append(aliasName).append(".").append(MapUtils.getString(columnInfo, "Field").toLowerCase()).append(", ");
		selectSql.deleteCharAt(selectSql.length()-1).deleteCharAt(selectSql.length()-1).append(" from ");
		selectSql.append(tableName).append(" ").append(aliasName);
//		输出语句
		System.out.println("========================================================================");
		System.out.println(insertSql.toString());
		System.out.println(updateSql.toString());
		System.out.println(selectSql.toString());
		System.out.println("========================================================================");
	}
	
	/**
	 * 通过表名新增实体对象
	 * <p>通过表名查询数据库表信息，根据表基础信息生成实体对象，生成的对象包含验证信息，
	 * 验证信息依据数据库中是否必填及长度信息生成，验证名称依据字段备注说明信息生成。
	 * 由于主键、创建人（creator）、创建时间（create_time）、编辑人（editor）、
	 * 编辑时间（edit_time）、是否删除（is_delete）字段通过系统生成，故不作必填限制。
	 * @param dataSource 连接数据源
	 * @param srcPath src根节点路径
	 * @param packageName 包名
	 * @param className 类名
	 * @param tableName 表名
	 * @param name 对象名
	 * @throws Exception 抛出全部异常
	 */
	public static void createModel(DataSource dataSource, String srcPath, String packageName, 
			String className, String tableName, String name) throws Exception {
//		获取JDBCTemplate对象
		JdbcTemplate jt = new JdbcTemplate(dataSource);
//		设置不处理判断空值的内容
		List<String> notNullFields = new ArrayList<String>();
		notNullFields.add("creator");
		notNullFields.add("create_time");
		notNullFields.add("editor");
		notNullFields.add("edit_time");
		notNullFields.add("is_delete");
//		定义签名
		String author = "大连首闻科技有限公司";
//		获取文档真实路径
		StringBuffer classPath = new StringBuffer();
		classPath.append(srcPath).append(File.separator).append(packageName.replaceAll("\\.", "\\"+File.separator))
		.append(File.separator).append("model").append(File.separator).append(className).append(".java");
//		定义是否需要引入NotBlank、NotNull、DateTimeFormat、Date、Length
		boolean isNotBlank = false;
		boolean isNotNull = false;
		boolean isDateTimeFormat = false;
		boolean isDate = false;
		boolean isLength = false;
//		定义对应引入的包
		String importNotBlank = "import org.hibernate.validator.constraints.NotBlank;";
		String importNotNull = "import javax.validation.constraints.NotNull;";
		String importDateTimeFormat = "import org.springframework.format.annotation.DateTimeFormat;";
		String importDate = "import java.util.Date;";
		String importLength = "import org.hibernate.validator.constraints.Length;";
//		获取文档
		File classFile = new File(classPath.toString());
//		删除原文档
		classFile.delete();
//		写入属性列表及方法列表
		StringBuffer properties = new StringBuffer();
		StringBuffer methods = new StringBuffer();
		List<Map<String, Object>> columnList = jt.queryForList("show full columns from "+tableName.toLowerCase()+"");
		for(Map<String, Object> columnInfo : columnList){
//			获取字段名、字段类别、是否可为空、是否主键、注释
			String field = MapUtils.getString(columnInfo, "Field").toLowerCase();
			String type = MapUtils.getString(columnInfo, "Type").toLowerCase();
			String isNull = MapUtils.getString(columnInfo, "Null").toLowerCase();
			String key = MapUtils.getString(columnInfo, "Key");
			String comment = MapUtils.getString(columnInfo, "Comment").toLowerCase();
			String length = type.replaceAll("\\D", "");
//			如果是主键则不作任何注解
			if(!"PRI".equalsIgnoreCase(key)){
//				varchar/char/text/tinytext/mediumtext/longtext类型
				if(type.toLowerCase().startsWith("varchar")
						||type.toLowerCase().startsWith("char")
						||type.toLowerCase().startsWith("text")
						||type.toLowerCase().startsWith("tinytext")
						||type.toLowerCase().startsWith("mediumtext")
						||type.toLowerCase().startsWith("longtext")){
//					如果不能为空，则加入NotBlank的参数
					if("no".equalsIgnoreCase(isNull.toLowerCase())&&!notNullFields.contains(field)){
						properties.append("\t@NotBlank(message=\"").append(comment).append("不能为空\")").append("\n");
						isNotBlank = true;
					}
//					获取长度
					if(!"".equals(length.trim())){
						properties.append("\t@Length(min=0, max=").append(length).append(", message=\"").append(comment).append("长度必须在0-").append(length).append("之间\")").append("\n");
						isLength = true;
					}
				}
//				int类型
				if(type.toLowerCase().startsWith("int")){
//					如果不能为空，则加入NotNull的参数
					if("no".equalsIgnoreCase(isNull.toLowerCase())&&!notNullFields.contains(field)){
						properties.append("\t@NotNull(message=\"").append(comment).append("不能为空\")").append("\n");
						isNotNull = true;
					}
				}
//				date/datetime类型
				if(type.toLowerCase().startsWith("date")||type.toLowerCase().startsWith("datetime")){
//					设置date的引入为ture
					isDate = true;
					isDateTimeFormat = true;
//					如果不能为空，则加入NotNull的参数
					if("no".toLowerCase().equalsIgnoreCase(isNull)&&!notNullFields.contains(field)){
						properties.append("\t@NotNull(message=\"").append(comment).append("不能为空\")").append("\n");
						isNotNull = true;
					}
//					如果是date则格式为yyyy-MM-dd，datetime则是yyyy-MM-dd HH:mm:ss
					if("date".toLowerCase().equalsIgnoreCase(type)){
						properties.append("\t@DateTimeFormat(pattern=\"yyyy-MM-dd\")").append("\n");
					}else{
						properties.append("\t@DateTimeFormat(pattern=\"yyyy-MM-dd HH:mm:ss\")").append("\n");
					}
				}
			}
			String attrType = "String";
			if(type.toLowerCase().startsWith("date")||type.toLowerCase().startsWith("timestmp")) attrType = "Date";
			if(type.toLowerCase().startsWith("int")) attrType = "int";
			properties.append("\tprivate ").append(attrType).append(" ").append(field).append(";").append("\n").append("\n");
			methods.append("\tpublic ").append(attrType).append(" get").append(StringUtils.capitalize(field)).append("() {").append("\n");
			methods.append("\t\treturn ").append(field).append(";").append("\n");
			methods.append("\t}").append("\n").append("\n");
			methods.append("\tpublic void set").append(StringUtils.capitalize(field)).append("(").append(attrType).append(" ").append(field).append(") {").append("\n");
			methods.append("\t\tthis.").append(field).append(" = ").append(field).append(";").append("\n");
			methods.append("\t}").append("\n").append("\n");
		}
//		定义所有内容的参数并写入
		StringBuffer content = new StringBuffer();
		content.append("package ").append(packageName).append(".model").append(";").append("\n");
		if(isDate||isNotNull||isLength||isNotBlank||isDateTimeFormat)
			content.append("\n");
		if(isDate) content.append(importDate).append("\n");
		if(isNotNull) content.append(importNotNull).append("\n");
		if(isLength) content.append(importLength).append("\n");
		if(isNotBlank) content.append(importNotBlank).append("\n");
		if(isDateTimeFormat) content.append(importDateTimeFormat).append("\n");
		content.append("\n");
		content.append("/**").append("\n");
		content.append(" * ").append(name).append("\n");
		content.append(" * ").append("@author ").append(author).append("\n");
		content.append(" * ").append("@since ").append(DateUtils.getNowTime()).append("\n");
		content.append(" */").append("\n");
		content.append("public class ").append(className).append(" {").append("\n").append("\n");
//		写入属性及方法
		content.append(properties).append(methods);
//		完成写入
		content.append("}");
//		写入到文档
		FileUtils.writeStringToFile(classFile, content.toString(), CONFIG.ENCODING);
//		新增文档
		classFile.createNewFile();
	}
	
	/**
	 * 新增Java文件
	 * <p>用于生成新的Java未见，需要制定位置及包名、类名、业务名信息，生成的Java文件包含Controller及Dao套件文件，
	 * 文件内容包含一些基础引用的对象及代码
	 * @param srcPath src根绝对路径
	 * @param packageName 包名称
	 * @param className 类名称
	 * @param name 业务名称
	 * @throws Exception 抛出全部异常
	 */
	public static void createJavaFile(String srcPath, String packageName, String className, String name) throws Exception {
//		定义签名
		String author = "大连首闻科技有限公司";
//		获取文档真实路径
		StringBuffer classPath = new StringBuffer();
		classPath.append(srcPath).append(File.separator)
			.append(packageName.replaceAll("\\.", "\\"+File.separator)).append(File.separator);
//		新增Controller
		File controllerFile = new File(classPath.toString()+File.separator+"controller"+File.separator+className+"Controller.java");
		StringBuffer controllerContent = new StringBuffer();
		controllerContent.append("package ").append(packageName).append(".controller;").append("\n").append("\n");
		controllerContent.append("import javax.annotation.Resource;").append("\n").append("\n");
		controllerContent.append("import org.springframework.stereotype.Controller;").append("\n");
		controllerContent.append("import org.springframework.web.bind.annotation.RequestMapping;").append("\n").append("\n");
		controllerContent.append("import ").append(packageName).append(".dao.").append(className).append("Dao;").append("\n").append("\n");
		controllerContent.append("/**").append("\n");
		controllerContent.append(" * ").append(name).append("\n");
		controllerContent.append(" * @author ").append(author).append("\n");
		controllerContent.append(" * @since ").append(DateUtils.getNowTimeHaveMS()).append("\n");
		controllerContent.append(" */").append("\n");
		controllerContent.append("@Controller").append("\n");
		controllerContent.append("@RequestMapping(\"/something...\")").append("\n");
		controllerContent.append("public class ").append(className).append("Controller {").append("\n").append("\n");
		controllerContent.append("\t@SuppressWarnings(\"unused\")").append("\n");
		controllerContent.append("\tprivate String basePath = ").append("\"\"").append(";").append("\n").append("\n");
		controllerContent.append("\t@SuppressWarnings(\"unused\")").append("\n");
		controllerContent.append("\tprivate ").append(className).append("Dao dao;").append("\n").append("\n");
		controllerContent.append("\t@Resource(name=\"").append(StringUtils.capitalize(className)).append("Dao\")").append("\n");
		controllerContent.append("\tpublic void setDao(").append(className).append("Dao dao) {").append("\n");
		controllerContent.append("\t\tthis.dao = dao;").append("\n");
		controllerContent.append("\t}").append("\n").append("\n");
		controllerContent.append("}");
		FileUtils.writeStringToFile(controllerFile, controllerContent.toString(), CONFIG.ENCODING);
		controllerFile.createNewFile();
//		新增Dao实现
		File daoFile = new File(classPath.toString()+"dao"+File.separator+className+"Dao.java");
		StringBuffer daoContent = new StringBuffer();
		daoContent.append("package ").append(packageName).append(".dao;").append("\n").append("\n");
		daoContent.append("import javax.annotation.Resource;").append("\n");
		daoContent.append("import javax.sql.DataSource;").append("\n").append("\n");
		daoContent.append("import org.springframework.stereotype.Component;").append("\n").append("\n");
		daoContent.append("import ").append(packageName).append(".dao.").append(className).append("Dao;").append("\n");
		daoContent.append("import com.dlshouwen.core.base.dao.BaseDao;").append("\n").append("\n");
		daoContent.append("/**").append("\n");
		daoContent.append(" * ").append(name).append("\n");
		daoContent.append(" * @author ").append(author).append("\n");
		daoContent.append(" * @since ").append(DateUtils.getNowTimeHaveMS()).append("\n");
		daoContent.append(" */").append("\n");
		daoContent.append("@Component(\"").append(StringUtils.capitalize(className)).append("Dao\")").append("\n");
		daoContent.append("public class ").append(className).append("Dao extends BaseDao {").append("\n").append("\n");
		daoContent.append("\t/**").append("\n");
		daoContent.append("\t * 注入数据源\n");
		daoContent.append("\t * @param dataSource 数据源对象\n");
		daoContent.append("\t */").append("\n");
		daoContent.append("\t@Resource(name=\"defaultDataSource\")").append("\n");
		daoContent.append("\tpublic void setDataSource(DataSource dataSource){").append("\n");
		daoContent.append("\t\tsuper.setDataSource(dataSource);").append("\n");
		daoContent.append("\t}").append("\n").append("\n");
		daoContent.append("}");
		FileUtils.writeStringToFile(daoFile, daoContent.toString(), CONFIG.ENCODING);
		daoFile.createNewFile();
	}
	
	/**
	 * 生成Grid对应属性
	 * <p>根据表名生成前台使用的Grid表格属性
	 * @param dataSource 连接数据源
	 * @param tableName 表名
	 * @throws Exception 抛出全部异常
	 */
	public static void constructGridDate(DataSource dataSource, String tableName) throws Exception {
//		获取JDBCTemplate对象
		JdbcTemplate jt = new JdbcTemplate(dataSource);
//		获取列数据列表
		StringBuffer sql = new StringBuffer();
		sql.append("show full columns from "+tableName.toLowerCase());
		List<Map<String, Object>> columnList = jt.queryForList(sql.toString());
//		定义fields
		StringBuffer fields = new StringBuffer();
//		遍历所有列信息
		for(Map<String, Object> columnInfo : columnList){
//			获取字段名、字段类别、是否可为空、是否主键、注释
			String field = MapUtils.getString(columnInfo, "Field").toLowerCase();
			String type = MapUtils.getString(columnInfo, "Type").toLowerCase();
			String comment = MapUtils.getString(columnInfo, "Comment").toLowerCase();
			String length = type.replaceAll("\\D", "");
//			{id:'user_code', title:'用户编号', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq' },
//			处理编号、名称
			fields.append("{");
			fields.append("id:'").append(field).append("', ");
			fields.append("title:'").append(comment).append("', ");
//			处理列数据类型
			String columnType = "string";
			if(type.toLowerCase().startsWith("date")||type.toLowerCase().startsWith("time")) columnType = "date";
			if(type.toLowerCase().startsWith("double")||type.toLowerCase().startsWith("int")) columnType = "number";
			fields.append("type:'").append(columnType).append("', ");
//			处理日期格式化
			if("date".equals(columnType)){
				fields.append("format:'yyyy-MM-dd hh:mm:ss', ");
			}
//			处理码表类型
			boolean isCodeTable = ("string".equals(columnType)&&"2".equals(length))?true:false;
			if(isCodeTable){
				fields.append("codeTable:code_table_info['").append(field).append("'], ");
			}
//			处理居中、隐藏方式、是否快速查询
			fields.append("columnClass:'text-center', ");
			fields.append("hideType:'xs|sm|md|lg', ");
			fields.append("fastQuery:true, ");
//			处理快速查询方式
			String fastQueryType = "lk";
			if(columnType.equals("date")||columnType.equals("number")) fastQueryType = "range";
			if(isCodeTable) fastQueryType = "eq";
			fields.append("fastQueryType:'").append(fastQueryType).append("' ");
			fields.append("},");
			fields.append("\n");
			if(field.equalsIgnoreCase("creator")||field.equalsIgnoreCase("editor")){
				fields.append("{");
				fields.append("id:'").append(field).append("_name").append("', ");
				fields.append("title:'").append(comment).append("', ");
				fields.append("type:'date', ");
				fields.append("format:'yyyy-MM-dd hh:mm:ss', ");
				fields.append("columnClass:'text-center', ");
				fields.append("hideType:'xs|sm|md|lg', ");
				fields.append("fastQuery:true, ");
				fields.append("fastQueryType:'lk' ");
				fields.append("},");
				fields.append("\n");
			}
		}
		System.out.println(fields.toString());
	}
	
	/**
	 * 生成表单对应属性
	 * <p>根据表名生成表单信息，顺序依据字段顺序生成，表单元素的验证信息使用字段的必填、长度、格式等限制
	 * @param dataSource 连接数据源
	 * @param tableName 表名称
	 * @throws Exception 抛出全部异常
	 */
	public static void constructInputData(DataSource dataSource, String tableName) throws Exception {
//		获取JDBCTemplate对象
		JdbcTemplate jt = new JdbcTemplate(dataSource);
//		获取列数据列表
		StringBuffer sql = new StringBuffer();
		sql.append("show full columns from "+tableName.toLowerCase());
		List<Map<String, Object>> columnList = jt.queryForList(sql.toString());
//		定义fields
		StringBuffer fields = new StringBuffer();
//		遍历所有列信息
		for(Map<String, Object> columnInfo : columnList){
//			获取字段名、字段类别、是否可为空、是否主键、注释
			String field = MapUtils.getString(columnInfo, "Field").toLowerCase();
			String type = MapUtils.getString(columnInfo, "Type").toLowerCase();
			String isNull = MapUtils.getString(columnInfo, "Null").toLowerCase();
//			String key = MapUtils.getString(columnInfo, "Key");
			String comment = MapUtils.getString(columnInfo, "Comment").toLowerCase();
			String length = type.replaceAll("\\D", "");
//			判断是否是码表
			boolean isCodeTable = ("2".equals(length))?true:false;
			fields.append("<div class=\"form-group\">").append("\n");
			fields.append("\t<label class=\"col-sm-2 control-label text-right\">").append(comment).append("：</label>").append("\n");
			fields.append("\t<div class=\"col-sm-4\">").append("\n");
			fields.append("\t\t");
			if(isCodeTable){
				fields.append("<sw:select path=\"").append(field).append("\" items=\"${applicationScope.__CODE_TABLE__.").append(field)
					.append(" }\" emptyText=\"请选择...\" cssClass=\"form-control\" valid=\"r\" validTitle=\"").append(comment)
					.append("\" validInfoArea=\"").append(field).append("_info_area\" />").append("\n");
			}else{
				if(type.startsWith("int")){
					fields.append("<sw:input path=\"").append(field).append("\" cssClass=\"form-control\" placeholder=\"请输入").append(comment).append("\" valid=\"")
						.append("no".equalsIgnoreCase(isNull)?"r|":"").append("integer\" validTitle=\"")
						.append(comment).append("\" validInfoArea=\"").append(field).append("_info_area\"/>").append("\n");
				} else if(type.startsWith("date")&&!type.startsWith("datetime")) {
					fields.append("<div class=\"input-group\">").append("\n");
					fields.append("\t\t\t<sw:input path=\"").append(field).append("\" cssClass=\"form-control\" placeholder=\"请输入").append(comment).append("\" valid=\"")
						.append("no".equalsIgnoreCase(isNull)?"r":"").append("\" validTitle=\"")
						.append(comment).append("\" validInfoArea=\"").append(field).append("_info_area\" onclick=\"WdatePicker();\"/>").append("\n");
					fields.append("\t\t\t<span class=\"input-group-addon\"><i class=\"fa fa-calendar\"></i></span>").append("\n");
					fields.append("\t\t</div>").append("\n");
				} else if(type.startsWith("datetime")||type.startsWith("time")) {
					fields.append("<div class=\"input-group\">").append("\n");
					fields.append("\t\t\t<sw:input path=\"").append(field).append("\" cssClass=\"form-control\" placeholder=\"请输入").append(comment).append("\" valid=\"")
						.append("no".equalsIgnoreCase(isNull)?"r":"").append("\" validTitle=\"")
						.append(comment).append("\" validInfoArea=\"").append(field).append("_info_area\" onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});\"/>").append("\n");
					fields.append("\t\t\t<span class=\"input-group-addon\"><i class=\"fa fa-calendar\"></i></span>").append("\n");
					fields.append("\t\t</div>").append("\n");
				} else {
					fields.append("<sw:input path=\"").append(field).append("\" cssClass=\"form-control\" placeholder=\"请输入").append(comment).append("\" valid=\"")
					.append("no".equalsIgnoreCase(isNull)?"r|":"").append("l-l").append(length).append("\" validTitle=\"")
					.append(comment).append("\" validInfoArea=\"").append(field).append("_info_area\"/>").append("\n");
				}
			}
			fields.append("\t</div>").append("\n");
			fields.append("\t<div class=\"col-sm-6\"><p class=\"help-block\" id=\"").append(field).append("_info_area\"></p></div>").append("\n");
			fields.append("</div>").append("\n");
		}
		System.out.println(fields.toString());
	}
	
	/**
	 * 生成字段描述信息
	 * <p>根据表名称生成所有字段描述信息，用于文档编写
	 * @param dataSource 连接数据源
	 * @param tableName 表名称
	 * @param tableComment 表注释信息
	 * @throws Exception 抛出全部异常
	 */
	public static void constructFiledDescription(DataSource dataSource, 
			String tableName, String tableComment) throws Exception {
//		获取JDBCTemplate对象
		JdbcTemplate jt = new JdbcTemplate(dataSource);
//		获取列数据列表
		StringBuffer sql = new StringBuffer();
		sql.append("show full columns from "+tableName.toLowerCase());
		List<Map<String, Object>> columnList = jt.queryForList(sql.toString());
//		定义fields
		StringBuffer description = new StringBuffer();
//		遍历所有列信息
		for(Map<String, Object> columnInfo : columnList){
//			获取字段名、字段类别、是否可为空、是否主键、注释
			String field = MapUtils.getString(columnInfo, "Field").toLowerCase();
			String type = MapUtils.getString(columnInfo, "Type").toLowerCase();
			String isNull = MapUtils.getString(columnInfo, "Null").toLowerCase();
//			String key = MapUtils.getString(columnInfo, "Key");
			String comment = MapUtils.getString(columnInfo, "Comment").toLowerCase();
			String length = type.replaceAll("\\D", "");
			description.append(comment).append("：用于设置").append(tableComment).append("的").append(comment).append("；");
			description.append("对应表字段：").append(tableName).append("[").append(field).append("]").append("；");
			if("no".equalsIgnoreCase(isNull)){
				description.append("必填项；");
			}else{
				description.append("非必填项；");
			}
			description.append("长度限制为").append(length).append("个字符。").append("\n");
		}
		System.out.println(description.toString());
	}
	
	/**
	 * 生成任务可用参数
	 * <p>反射某对象信息，并根据某表信息匹配该对象属性，匹配上则生成说明信息。
	 * 该方法主要用于编写任务的可用参数说明，通常不会用到。
	 * @param dataSource 连接数据源
	 * @param z 类
	 * @param tableName 表名称
	 * @param tableComment 表注释信息
	 * @throws Exception 抛出全部异常
	 */
	public static void constructTaskAttrDescription(DataSource dataSource, Class<?> z, 
			String tableName, String tableComment) throws Exception {
//		获取JDBCTemplate对象
		JdbcTemplate jt = new JdbcTemplate(dataSource);
//		获取列数据列表
		StringBuffer sql = new StringBuffer();
		sql.append("show full columns from "+tableName.toLowerCase());
		List<Map<String, Object>> columnList = jt.queryForList(sql.toString());
//		定义fields
		StringBuffer description = new StringBuffer();
//		通过反射从对象中获取参数
		Field[] fields = z.getDeclaredFields();
		for(Field field : fields){
			String fieldName = field.getName();
//			遍历所有列信息
			boolean isFind = false;
			for(Map<String, Object> columnInfo : columnList){
//				获取字段名、字段类别、是否可为空、是否主键、注释
				String _field = MapUtils.getString(columnInfo, "Field").toLowerCase();
				if(_field.equalsIgnoreCase(fieldName)){
					isFind = true;
					String comment = MapUtils.getString(columnInfo, "Comment").toLowerCase();
					description.append(fieldName).append("：调用方式为${").append(StringUtils.uncapitalize(z.getSimpleName())).append(".").append(fieldName).append("}，");
					description.append("可以获取").append(tableComment).append("的").append(comment).append("数据。").append("\n");
				}
			}
			if(!isFind){
				description.append(fieldName).append("：调用方式为${").append(StringUtils.uncapitalize(z.getSimpleName())).append(".").append(fieldName).append("}，");
				description.append("可以获取").append(tableComment).append("的").append("").append("数据。").append("\n");
			}
		}
		System.out.println(description.toString());
	}
	
	/**
	 * 生成Java接口文档
	 * <p>用于生成大连首闻核心系统Classic版本Java文件接口文档
	 * @param path 类文件所在路径
	 * @param z 文件类对象
	 * @throws Exception 抛出全部异常
	 */
	public static void constructJavaAPI(String path, Class<?> z) throws Exception {
//		读取文件
		File file = new File(path + File.separator + z.getSimpleName() + ".java");
		List<String> lines = FileUtils.readLines(file);
		/**
		 * 定义类文档对象，结构如下：
		 * {
		 * 		package			:	包名
		 * 		className		:	类名称
		 * 		classFullName	:	类全名
		 * 		classDescript	:	类描述
		 * 		author			: 	编写者
		 * 		version			:	版本
		 * 		methods			:	方法列表
		 * 		[
		 * 			{
		 * 				methodName		:	方法名称
		 * 				methodFullName	:	方法全名
		 * 				methodInfo		:	方法简述
		 * 				methodDescript	:	方法描述
		 * 				params			:	参数列表
		 * 				[
		 * 					{
		 * 						paramTypeName		:	参数类型
		 * 						paramFullTypeName	:	参数类型全称
		 * 						paramName			:	参数名称
		 * 						paramDescript		:	参数描述
		 * 					}
		 * 				]
		 * 				return			:	返回值
		 * 				exception		:	异常信息
		 * 			}
		 * 		]
		 * }
		 */
		Map<String, Object> classInfo = new HashMap<String, Object>();
//		获取类基础信息：包名、类名、类全名
		classInfo.put("package", z.getPackage().getName());
		classInfo.put("className", z.getSimpleName());
		classInfo.put("classFullName", z.getName());
//		获取类注释信息
		List<String> classAnnotationLines = new ArrayList<String>();
		for(int i=0; i<lines.size(); i++){
			String line = lines.get(i);
			if(line.indexOf("class")!=-1){
				String defined = new String(line);
				if(line.indexOf("{")==-1){
					for(int l=i+1; l<lines.size(); l++){
						String lline = lines.get(l).trim();
						defined += " " + lline;
						if(lline.indexOf("{")!=-1){
							break;
						}
					}
				}
				if(defined.indexOf(z.getSimpleName())!=-1){
					break;
				}
			}
			if(line.trim().startsWith("@")){
				continue;
			}
			if(line.indexOf("/**")!=-1){
				classAnnotationLines = new ArrayList<String>();
			}
			classAnnotationLines.add(line);
			if(line.indexOf(" */")!=-1){
				continue;
			}
		}
//		获取类说明、类作者、类版本信息
		String classDescript = "";
		String author = "";
		String version = "";
		for(String line : classAnnotationLines){
			line = line.trim().replaceAll("/\\*\\*", "").replaceAll(" *\\*/", "").replaceAll("\\* ", "");
			if("".equals(line)){
				continue;
			}
			if(!line.startsWith("@")){
				classDescript += line.trim();
				continue;
			}
			if(line.startsWith("@author")){
				author = line.replaceAll("@author", "").trim();
				continue;
			}
			if(line.startsWith("@version")){
				version = line.replaceAll("@version", "").trim();
				continue;
			}
		}
		classInfo.put("classDescript", classDescript);
		classInfo.put("author", author);
		classInfo.put("version", version);
//		定义方法列表
		List<Map<String, Object>> methods = new ArrayList<Map<String,Object>>();
//		获取所有方法
		Method[] ms = z.getDeclaredMethods();
		for(Method m : ms){
			if(m.getName().equals("main")){
				continue;
			}
//			获取方法注释信息
			List<String> methodAnnotationLines = new ArrayList<String>();
			for(int i=0; i<lines.size(); i++){
				String line = lines.get(i);
				if(line.replaceAll("synchronized", "").replaceAll("\\s", "").indexOf(Modifier.toString(m.getModifiers()).replaceAll("synchronized", "").replaceAll("transient", "").replaceAll("\\s", ""))!=-1){
					String defined = line;
					int methodLineNo = i;
					if(line.indexOf("{")==-1){
						for(int j=i+1; j<lines.size(); j++){
							defined += lines.get(j);
							if(lines.get(j).indexOf("{")!=-1){
								break;
							}
						}
					}
					String regex = m.getName()+"\\s*\\(";
					if(Pattern.compile(regex).matcher(defined).find()){
						String paramsStr = defined.replaceAll(".*\\(", "").replaceAll("\\).*", "").trim();
						paramsStr = paramsStr.replaceAll("<[^>]*>", "");
						String[] params = "".equals(paramsStr)?new String[]{}:paramsStr.split("\\s*,\\s*");
						if(params.length!=m.getParameterTypes().length){
							continue;
						}
						boolean isThisMethod = true;
						for(int j=0; j<params.length; j++){
							String param = params[j];
							String paramTypeName = m.getParameterTypes()[j].getSimpleName();
							param = param.replace("final", "").trim();
							if(j!=params.length-1){
								if(!param.startsWith(paramTypeName)){
									isThisMethod = false;
									break;
								}
							}else{
								String paramTypeName2 = paramTypeName.replace("[]", "...");
								if(!param.startsWith(paramTypeName)&&!param.replaceAll("\\s", "").startsWith(paramTypeName2)){
									isThisMethod = false;
									break;
								}
							}
						}
						if(isThisMethod){
							for(int j=methodLineNo-1; j>=0; j--){
								if(lines.get(j).trim().startsWith("@")){
									continue;
								}
								if(lines.get(j).indexOf(" */")!=-1){
									methodAnnotationLines = new ArrayList<String>();
								}
								if(lines.get(j).indexOf("/**")!=-1){
									break;
								}
								methodAnnotationLines.add(0, lines.get(j));
							}
						}
					}
				}
			}
//			定义方法对象
			Map<String, Object> method = new HashMap<String, Object>();
//			获取方法代码、方法名称、方法描述
			String methodName = m.getName();
			String methodInfo = "";
			String methodDescript = "";
			String _return = "无返回值";
			String exception = "无异常抛出";
//			获取方法全名
			StringBuffer methodFullName = new StringBuffer();
			methodFullName.append(Modifier.toString(m.getModifiers())).append(" ");
			methodFullName.append(m.getReturnType().getSimpleName()).append(" ");
			methodFullName.append(m.getName()).append(" (");
			for(Class<?> p : m.getParameterTypes()){
				methodFullName.append(p.getCanonicalName()).append(", ");
			}
			if(m.getParameterTypes().length>0){
				methodFullName.delete(methodFullName.lastIndexOf(","), methodFullName.length());
			}
			methodFullName.append(")");
			if(m.getExceptionTypes().length>0){
				methodFullName.append(" throws ");
				for(Class<?> e : m.getExceptionTypes()){
					methodFullName.append(e.getCanonicalName()).append(", ");
				}
				methodFullName.delete(methodFullName.lastIndexOf(","), methodFullName.length());
			}
//			获取其他信息
			boolean methodInfoComplete = false;
			for(int i=0; i<methodAnnotationLines.size(); i++){
				String line = methodAnnotationLines.get(i);
				line = line.trim().replaceAll("/\\*\\*", "").replaceAll(" *\\*/", "").replaceAll("\\* ", "");
				if("".equals(line)){
					continue;
				}
				if(!line.startsWith("@")&&!line.startsWith("<p>")){
					if(!methodInfoComplete){
						methodInfo = line.trim();
						methodInfoComplete = true;
						continue;
					}
				}
				if(line.startsWith("<p>")){
					methodDescript = line.replaceAll("<p>", "").trim();
					for(int l=i+1; l<methodAnnotationLines.size(); l++){
						String lline = methodAnnotationLines.get(l);
						lline = lline.trim().replaceAll("/\\*\\*", "").replaceAll(" *\\*/", "").replaceAll("\\* ", "");
						if(lline.startsWith("@")){
							break;
						}
						methodDescript += lline;
					}
					continue;
				}
				if(line.startsWith("@return")){
					_return = line.replaceAll("@return", "").trim();
					continue;
				}
				if(line.startsWith("@throws")){
					exception = line.replaceAll("@throws", "").trim();
					continue;
				}
			}
//			放置方法对象
			method.put("methodName", methodName);
			method.put("methodFullName", methodFullName.toString());
			method.put("methodInfo", methodInfo);
			method.put("methodDescript", methodDescript);
			method.put("return", _return);
			method.put("returnType", m.getReturnType().getCanonicalName());
			method.put("exception", exception);
//			定义参数列表
			List<Map<String, Object>> params = new ArrayList<Map<String,Object>>();
//			获取所有参数列表
			Class<?>[] ps = m.getParameterTypes();
			int paramNo = 0;
			for(int i=0; i<methodAnnotationLines.size(); i++){
				Map<String, Object> param = new HashMap<String, Object>();
				String paramTypeName = "";
				String paramFullTypeName = "";
				String paramName = "";
				String paramDescript = "";
				String line = methodAnnotationLines.get(i);
				line = line.trim().replaceAll("/\\*\\*", "").replaceAll(" *\\*/", "").replaceAll("\\* ", "");
				if(line.startsWith("@param")){
					String paramInfo = line.replaceAll("@param", "").trim();
					paramName = paramInfo.split(" ")[0];
					paramDescript = paramInfo.split(" ")[1];
					Class<?> p = ps[paramNo];
					paramTypeName = p.getSimpleName();
					paramFullTypeName = p.getCanonicalName();
					param.put("paramTypeName", paramTypeName);
					param.put("paramFullTypeName", paramFullTypeName);
					param.put("paramName", paramName);
					param.put("paramDescript", paramDescript);
					params.add(param);
					paramNo++;
				}
			}
			method.put("params", params);
			methods.add(method);
		}
		classInfo.put("methods", methods);
//		输出
		System.out.println("包路径："+MapUtils.getString(classInfo, "package"));
		System.out.println("类名称："+MapUtils.getString(classInfo, "className"));
		System.out.println("类全名："+MapUtils.getString(classInfo, "classFullName"));
		System.out.println("类说明："+MapUtils.getString(classInfo, "classDescript"));
		System.out.println("作者："+MapUtils.getString(classInfo, "author"));
		System.out.println("版本："+MapUtils.getString(classInfo, "version"));
		List<Map<String, Object>> _methods = (List<Map<String, Object>>)classInfo.get("methods");
		for(Map<String, Object> method : _methods){
			System.out.println(MapUtils.getString(method, "methodName"));
			System.out.println("方法名称："+MapUtils.getString(method, "methodName"));
			System.out.println("方法全名："+MapUtils.getString(method, "methodFullName"));
			System.out.println("方法描述："+MapUtils.getString(method, "methodInfo"));
			System.out.println("方法说明："+MapUtils.getString(method, "methodDescript"));
			List<Map<String, Object>> _params = (List<Map<String, Object>>)method.get("params");
			if(_params!=null&&_params.size()>0){
				System.out.println("参数信息：");
				for(Map<String, Object> param : _params){
					StringBuffer paramInfo = new StringBuffer();
					paramInfo.append(MapUtils.getString(param, "paramFullTypeName")).append(" ");
					paramInfo.append(MapUtils.getString(param, "paramName")).append("：");
					paramInfo.append(MapUtils.getString(param, "paramDescript")).append("。");
					System.out.println(paramInfo);
				}
			}else{
				System.out.println("参数信息：无");
			}
			System.out.println("返回值："+MapUtils.getString(method, "return")+" ["+MapUtils.getString(method, "returnType")+"]");
			System.out.println("异常信息："+MapUtils.getString(method, "exception"));
		}
	}
	
	/**
	 * 生成JavaScript接口文档
	 * <p>用于生成大连首闻核心系统Classic版本JavaScript文件接口文档
	 * @param path 文件路径
	 * @throws Exception 抛出全部异常
	 */
	public static void constructJavaScriptAPI(String path) throws Exception {
//		读取文件
		File file = new File(path);
		List<String> lines = FileUtils.readLines(file);
		/**
		 * 定义类文档对象，结构如下：
		 * 	[
		 * 		{
		 * 			methodName		:	方法名称
		 * 			methodDescript	:	方法描述
		 * 			params			:	参数列表
		 * 			[
		 * 				{
		 * 					paramType			:	参数类型
		 * 					paramName			:	参数名称
		 * 					paramDescript		:	参数描述
		 * 				}
		 * 			]
		 * 			returnType		:	返回值类型
		 * 			return			:	返回值
		 * 			example			:	示例
		 * 		}
		 * 	]
		 */
//		定义方法列表
		List<Map<String, Object>> methods = new ArrayList<Map<String,Object>>();
//		定义方法
		Map<String, Object> m = null;
//		获取所有的方法注释
		for(int i=0; i<lines.size(); i++){
			String line = lines.get(i).trim();
			if(line.startsWith("/**")){
				if(m==null){
					m = new HashMap<String, Object>();
				}
				if(line.endsWith("*/")){
					m.put("methodName", lines.get(i+1).replaceAll("var ", "").replaceAll("=.*", "").trim());
					m.put("methodDescript", line.replaceAll("/\\*\\*", "").replaceAll("\\*/", "").trim());
					methods.add(m);
					m = null;
				}
				continue;
			}
			if(line.startsWith("* ")){
				if(m==null){
					continue;
				}
				line = line.replaceAll("\\* ", "");
				if(line.startsWith("@param")){
					line = line.replaceAll("@param", "").trim();
					String[] paramInfo = line.split(" ");
					List<Map<String, Object>> params = (List<Map<String, Object>>)m.get("params");
					if(params==null){
						params = new ArrayList<Map<String,Object>>();
					}
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("paramType", paramInfo[0].replace("{", "").replace("}", ""));
					param.put("paramName", paramInfo[1]);
					param.put("paramDescript", paramInfo[2]);
					params.add(param);
					m.put("params", params);
					continue;
				}
				if(line.startsWith("@returns")){
					line = line.replaceAll("@returns", "").trim();
					String[] returnInfo = line.split(" ");
					m.put("returnType", returnInfo[0].replace("{", "").replace("}", ""));
					m.put("return", returnInfo[1]);
					continue;
				}
				if(line.startsWith("@example")){
					line = line.replaceAll("@example", "").trim();
					m.put("example", line);
					continue;
				}
				m.put("methodDescript", line.replaceFirst("\\* ", "").trim());
				continue;
			}
			if(line.endsWith("*/")){
				if(m==null){
					continue;
				}
				String methodName = lines.get(i+1);
				methodName = methodName.replaceAll("var ", "");
				methodName = methodName.replaceAll("DLShouWen.prototype.", "");
				methodName = methodName.replaceAll("function ", "");
				methodName = methodName.replaceAll("Date.prototype.", "");
				methodName = methodName.replaceAll("String.prototype.", "");
				methodName = methodName.replaceAll("\\(.*", "");
				methodName = methodName.replaceAll("=.*", "");
				methodName = methodName.trim();
				m.put("methodName", methodName);
				methods.add(m);
				m = new HashMap<String, Object>();
			}
		}
//		输出
		for(Map<String, Object> method : methods){
			System.out.println(MapUtils.getString(method, "methodName"));
			System.out.println("方法名称："+MapUtils.getString(method, "methodName"));
			System.out.println("方法说明："+MapUtils.getString(method, "methodDescript"));
			List<Map<String, Object>> _params = (List<Map<String, Object>>)method.get("params");
			if(_params!=null&&_params.size()>0){
				System.out.println("参数信息：");
				for(Map<String, Object> param : _params){
					StringBuffer paramInfo = new StringBuffer();
					paramInfo.append(MapUtils.getString(param, "paramType")).append(" ");
					paramInfo.append(MapUtils.getString(param, "paramName")).append("：");
					paramInfo.append(MapUtils.getString(param, "paramDescript")).append("。");
					System.out.println(paramInfo);
				}
			}else{
				System.out.println("参数信息：无参数");
			}
			String _return = MapUtils.getString(method, "return");
			_return = _return==null?"无返回值":(_return+" ["+MapUtils.getString(method, "returnType")+"]");
			System.out.println("返回值："+_return);
			System.out.println("调用示例："+MapUtils.getString(method, "example"));
		}
	}
	
	/**
	 * 生成Css接口文档
	 * <p>用于生成大连首闻核心系统Classic版本Css文件接口文档
	 * @param path 文件路径
	 * @throws Exception 抛出全部异常
	 */
	public static void constructCssAPI(String path) throws Exception {
//		读取文件
		File file = new File(path);
		List<String> lines = FileUtils.readLines(file);
		/**
		 * 定义类文档对象，结构如下：
		 * 	[
		 * 		{
		 * 			cssName		:	Css名称
		 * 			cssDescript	:	Css描述
		 * 		}
		 * 	]
		 */
//		定义方法列表
		List<Map<String, Object>> cssList = new ArrayList<Map<String,Object>>();
//		定义方法
		Map<String, Object> cssInfo = null;
//		获取所有的方法注释
		for(int i=0; i<lines.size(); i++){
			String line = lines.get(i).trim();
			if(line.startsWith("/*")){
				if(cssInfo==null){
					cssInfo = new HashMap<String, Object>();
				}
				cssInfo = new HashMap<String, Object>();
				cssInfo.put("cssName", lines.get(i+1).replaceAll("\\.", "").replaceAll(" *\\{", "").trim());
				cssInfo.put("cssDescript", line.replaceAll("/\\*", "").replaceAll("\\*/", "").trim());
				cssList.add(cssInfo);
			}
		}
//		输出
		for(Map<String, Object> css : cssList){
			System.out.println(MapUtils.getString(css, "cssName"));
			System.out.println("样式名称："+MapUtils.getString(css, "cssName"));
			System.out.println("样式说明："+MapUtils.getString(css, "cssDescript"));
		}
	}
	
	/**
	 * 图片裁剪
	 * <p>用于图片裁剪，可控制裁剪类型
	 * @param sourceFolder 源文件路径
	 * @param targetFolder 目标文件路径
	 * @param type 裁剪类型
	 * 			1：不按比率裁剪，宽度、高度设置的值单位为像素
	 * 			2：不按比率裁剪，宽度、高度设置的值单位为百分比
	 * 			3：按比率裁剪，以宽度为准，单位为像素
	 * 			4：按比率裁剪，以宽度为准，单位为百分比
	 * 			5：按比率裁剪，以高度为准，单位为像素
	 * 			6：按比率裁剪，以高度为准，单位为百分比
	 * @param width 裁剪宽度
	 * @param height 裁剪高度
	 * @throws Exception 抛出全部异常
	 */
	public static void imageClipping(String sourceFolder, String targetFolder, String type, 
			double width, double height) throws Exception {
//		遍历源文件夹图片
		File[] files = new File(sourceFolder).listFiles();
		for(File file : files){
//			过滤非png文件
			if(!file.getName().endsWith(".png")){
				continue;
			}
//			获取图片对象
			Iterator<ImageReader> iterator = ImageIO.getImageReadersByFormatName("png");
			ImageReader reader = (ImageReader) iterator.next();
			InputStream in = new FileInputStream(file);
			ImageInputStream iis = ImageIO.createImageInputStream(in);
			reader.setInput(iis, true);
//			处理剪裁后的宽高
			int sourceImageWidth = reader.getWidth(0);
			int sourceImageHeight = reader.getHeight(0);
			int targetImageWidth = 0;
			int targetImageHeight = 0;
			if("1".equals(type)){
				targetImageWidth = sourceImageWidth>width?(int)width:sourceImageWidth;
				targetImageHeight = sourceImageHeight>height?(int)height:sourceImageHeight;
			}
			if("2".equals(type)){
				targetImageWidth = (int)(sourceImageWidth*width);
				targetImageHeight = (int)(sourceImageHeight*height);
			}
			if("3".equals(type)){
				if(sourceImageWidth>width){
					targetImageWidth = (int)width;
					targetImageHeight = (int)((double)sourceImageHeight / (double)sourceImageWidth * (double)targetImageWidth);
				}else{
					targetImageWidth = sourceImageWidth;
					targetImageHeight = sourceImageHeight;
				}
			}
			if("4".equals(type)){
				targetImageWidth = (int)(sourceImageWidth*width);
				targetImageHeight = (int)((double)sourceImageHeight / (double)sourceImageWidth * (double)targetImageWidth);
			}
			if("5".equals(type)){
				if(sourceImageHeight>height){
					targetImageHeight = (int)height;
					targetImageWidth = (int)((double)sourceImageWidth / (double)sourceImageHeight * (double)targetImageHeight);
				}else{
					targetImageWidth = sourceImageWidth;
					targetImageHeight = sourceImageHeight;
				}
			}
			if("6".equals(type)){
				targetImageHeight = (int)(sourceImageHeight*height);
				targetImageWidth = (int)((double)sourceImageWidth / (double)sourceImageHeight * (double)targetImageHeight);
			}
//			生成图片文件
			BufferedImage bufImg = ImageIO.read(file);
			double wr = targetImageWidth * 1.0 / bufImg.getWidth();
			double hr = targetImageHeight * 1.0 / bufImg.getHeight();
			Image Itemp = bufImg.getScaledInstance(targetImageWidth, targetImageHeight, BufferedImage.SCALE_SMOOTH);
			AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);
			Itemp = ato.filter(bufImg, null);
			ImageIO.write((BufferedImage) Itemp, "png", new File(targetFolder+File.separator+file.getName()));
//			输出
			System.out.println("文件："+file.getName()+"裁剪完成，裁剪后大小："+targetImageWidth+"px * "+targetImageHeight+"px。");
		}
	}
	
	/**
	 * 生成测试数据
	 * <p>用于生成大连首闻核心系统Classic版本测试数据
	 * @param dataSource 连接数据源
	 * @throws Exception 抛出全部异常
	 */
	public static void constructTestDatas(DataSource dataSource) throws Exception {
//		获取JDBCTemplate对象
		JdbcTemplate jt = new JdbcTemplate(dataSource);
//		生成部门测试数据
		String[] deptNames = new String[]{"总裁办公室", "财务筹资部", "人力资源部", "质量监督部", "经营销售部", "产品研发部", "后勤部", "采购部"};
		String deptSQL = "insert into core_dept (dept_id, pre_dept_id, dept_name, phone, principal, principal_phone, sort, remark, creator, create_time, editor, edit_time) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		List<Object[]> deptArgs = new ArrayList<Object[]>();
		int i=2;
		for(String deptName : deptNames){
			Object[] dept = new Object[12];
			dept[0] = new GUID().toString();
			dept[1] = "top";
			dept[2] = deptName;
			dept[3] = "0411-8372"+DemoUtils.getRandomNum(1000, 9999);
			dept[4] = DemoUtils.getChineseName();
			dept[5] = DemoUtils.getTelephone();
			dept[6] = i;
			dept[7] = "测试数据";
			dept[8] = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
			dept[9] = new Date();
			dept[10] = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
			dept[11] = new Date();
			deptArgs.add(dept);
			i++;
		}
		jt.batchUpdate(deptSQL, deptArgs);
		System.out.println("部门测试数据创建完成，共创建 "+deptArgs.size()+" 条数据。");
//		生成角色测试数据
		String[] roleNames = new String[]{"基础管理角色", "邮件系统角色", "服务器管理员角色", "公告角色", "任务角色", "日志查看角色", "系统工具角色"};
		String roleSQL = "insert into core_role (role_id, system_id, role_name, remark, creator, create_time, editor, edit_time) values (?, ?, ?, ?, ?, ?, ?, ?)";
		List<Object[]> roleArgs = new ArrayList<Object[]>();
		i=0;
		for(String roleName : roleNames){
			Object[] role = new Object[8];
			role[0] = new GUID().toString();
			role[1] = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
			role[2] = roleName;
			role[3] = "测试数据";
			role[4] = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
			role[5] = new Date();
			role[6] = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
			role[7] = new Date();
			roleArgs.add(role);
			i++;
		}
		jt.batchUpdate(roleSQL, roleArgs);
		System.out.println("角色测试数据创建完成，共创建 "+roleArgs.size()+" 条数据。");
//		生成用户测试数据
		String userSQL = "insert into core_user (user_id, dept_id, user_code, user_name, password, valid_type, sex, card_type, card_id, birthday, work_date, folk, degree, phone, email, address, remark, creator, create_time, editor, edit_time) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		List<Object[]> userArgs = new ArrayList<Object[]>();
		List<Map<String, Object>> deptList = jt.queryForList("select * from core_dept");
		for(i=0; i<300; i++){
			String userName = DemoUtils.getChineseName();
			String cardId = new IdCardGenerator().generate();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Object[] user = new Object[21];
			user[0] = new GUID().toString();
			user[1] = MapUtils.getString(deptList.get(DemoUtils.getRandomNum(0, deptList.size()-1)), "dept_id");
			user[2] = DemoUtils.getFullSpell(userName);
			user[3] = userName;
			user[4] = SecurityUtils.getMD5("123456");
			user[5] = DemoUtils.getRandomNum(0, 1);
			user[6] = "M".equals(IdCardUtils.getGenderByIdCard(cardId))?"1":"2";
			user[7] = DemoUtils.getRandomNum(1, 1);
			user[8] = cardId;
			user[9] = sdf.parse(IdCardUtils.getBirthByIdCard(cardId));
			user[10] = sdf.parseObject("20100701");
			user[11] = DemoUtils.getRandomNum(1, 56);
			user[12] = DemoUtils.getRandomNum(1, 11);
			user[13] = DemoUtils.getTelephone();
			user[14] = DemoUtils.getFullSpell(userName)+"@dlshouwen.com";
			user[15] = "辽宁省大连市甘井子区××大厦×××室";
			user[16] = "测试数据";
			user[17] = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
			user[18] = new Date();
			user[19] = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
			user[20] = new Date();
			userArgs.add(user);
		}
		jt.batchUpdate(userSQL, userArgs);
		System.out.println("用户测试数据创建完成，共创建 "+userArgs.size()+" 条数据。");
//		创建用户参数
		String userAttrSQL = "insert into core_user_attr (user_id, skin_info, is_show_shortcut, is_background_float, background_float_speed) values (?, ?, ?, ?, ?)";
		List<Object[]> userAttrArgs = new ArrayList<Object[]>();
		List<Map<String, Object>> userList = jt.queryForList("select * from core_user where user_id<>'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa' and user_id<>'demo'");
		for(Map<String, Object> user : userList){
			Object[] userAttr = new Object[5];
			userAttr[0] = MapUtils.getString(user, "user_id");
			userAttr[1] = "default.jpg";
			userAttr[2] = "1";
			userAttr[3] = "1";
			userAttr[4] = 5;
			userAttrArgs.add(userAttr);
		}
		jt.batchUpdate(userAttrSQL, userAttrArgs);
		System.out.println("用户参数测试数据创建完成，共创建 "+userAttrArgs.size()+" 条数据。");
//		创建用户角色关系数据
		String userRoleSQL = "insert into core_user_role (user_id, role_id) values (?, ?)";
		List<Object[]> userRoleArgs = new ArrayList<Object[]>();
		List<Map<String, Object>> roleList = jt.queryForList("select * from core_role where role_id<>'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa' and role_id<>'demo'");
		for(Map<String, Object> user : userList){
			int count = DemoUtils.getRandomNum(1, roleList.size());
			List<Map<String, Object>> tempRoleList = new ArrayList<Map<String,Object>>();
			tempRoleList.addAll(roleList);
			Map<String, Object> role = new HashMap<String, Object>();
			for(i=0; i<count; i++){
				int index = DemoUtils.getRandomNum(0, tempRoleList.size()-1);
				role = tempRoleList.get(index);
				Object[] userRole = new Object[2];
				userRole[0] = MapUtils.getString(user, "user_id");
				userRole[1] = MapUtils.getString(role, "role_id");
				userRoleArgs.add(userRole);
				tempRoleList.remove(index);
			}
		}
		jt.batchUpdate(userRoleSQL, userRoleArgs);
		System.out.println("用户角色关系测试数据创建完成，共创建 "+userRoleArgs.size()+" 条数据。");
//		生成邮件测试数据
		String mailSQL = "insert into core_mail (mail_id, title, content, send_status, sender, send_time, create_time) values (?, ?, ?, ?, ?, ?, ?)";
		String mailReceiveSQL = "insert into core_mail_receive (mail_id, receiver, receive_status) values (?, ?, ?)";
		List<Object[]> mailArgs = new ArrayList<Object[]>();
		List<Object[]> mailReceiveArgs = new ArrayList<Object[]>();
		userList = jt.queryForList("select * from core_user");
		for(int mailCount=0; mailCount<300; mailCount++){
			Object[] mail = new Object[7];
			mail[0] = new GUID().toString();
			mail[1] = "测试编号："+mailCount+"，这是一封测试邮件的标题";
			mail[2] = "测试编号："+mailCount+"，这是一封测试邮件的内容";
			mail[3] = DemoUtils.getRandomNum(0, 3);
			mail[4] = MapUtils.getString(userList.get(DemoUtils.getRandomNum(0, userList.size()-1)), "user_id");
			mail[5] = new Date();
			mail[6] = new Date();
			mailArgs.add(mail);
			List<Map<String, Object>> tempUserList = new ArrayList<Map<String,Object>>();
			tempUserList.addAll(userList);
			for(int receviceCount=0; receviceCount<DemoUtils.getRandomNum(1, userList.size()-1); receviceCount++){
				int index = DemoUtils.getRandomNum(0, tempUserList.size()-1);
				Map<String, Object> user = tempUserList.get(index);
				Object[] mailReceive = new Object[3];
				mailReceive[0] = mail[0];
				mailReceive[1] = MapUtils.getString(user, "user_id");
				mailReceive[2] = DemoUtils.getRandomNum(0, 3);
				mailReceiveArgs.add(mailReceive);
				tempUserList.remove(index);
			}
		}
		jt.batchUpdate(mailSQL, mailArgs);
		jt.batchUpdate(mailReceiveSQL, mailReceiveArgs);
		System.out.println("邮件测试数据创建完成，共创建 "+mailArgs.size()+" 条数据。");
//		生成公告测试数据
		String announcementSQL = "insert into core_announcement (announcement_id, title, content, status, creator, create_time, editor, edit_time) values (?, ?, ?, ?, ?, ?, ?, ?)";
		List<Object[]> announcementArgs = new ArrayList<Object[]>();
		userList = jt.queryForList("select * from core_user");
		for(i=0; i<300; i++){
			Object[] announcement = new Object[8];
			announcement[0] = new GUID().toString();
			announcement[1] = "测试编号："+i+"，这是一封测试公告的标题";
			announcement[2] = "测试编号："+i+"，这是一封测试公告的内容";
			announcement[3] = DemoUtils.getRandomNum(0, 1);
			announcement[4] = MapUtils.getString(userList.get(DemoUtils.getRandomNum(0, userList.size()-1)), "user_id");
			announcement[5] = new Date();
			announcement[6] = MapUtils.getString(userList.get(DemoUtils.getRandomNum(0, userList.size()-1)), "user_id");
			announcement[7] = new Date();
			announcementArgs.add(announcement);
		}
		jt.batchUpdate(announcementSQL, announcementArgs);
		System.out.println("公告测试数据创建完成，共创建 "+announcementArgs.size()+" 条数据。");
	}
	
	/**
	 * 清除测试数据
	 * <p>用于清除大连首闻核心系统Classic版本测试数据
	 * @param dataSource 连接数据源
	 * @throws Exception 抛出全部异常
	 */
	public static void clearTestDatas(DataSource dataSource) throws Exception {
//		获取JDBCTemplate对象
		JdbcTemplate jt = new JdbcTemplate(dataSource);
//		清除所有部门、角色、用户、功能角色关系、用户角色关系、快捷方式、用户参数
		jt.update("delete from core_dept where dept_id<>'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa' and dept_id<>'demo'");
		jt.update("delete from core_limit_role where role_id in (select role_id from core_role where role_id<>'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa' and role_id<>'demo')");
		jt.update("delete from core_user_role where role_id in (select role_id from core_role where role_id<>'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa' and role_id<>'demo')");
		jt.update("delete from core_user_shortcut_limit where user_id in (select user_id from core_user where user_id<>'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa' and user_id<>'demo')");
		jt.update("delete from core_role where role_id<>'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa' and role_id<>'demo'");
		jt.update("delete from core_user where user_id<>'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa' and user_id<>'demo'");
		jt.update("delete from core_user_attr where user_id<>'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa' and user_id<>'demo'");
//		清除所有高级查询
		jt.update("delete from core_advance_query");
		jt.update("delete from core_advance_query_condition");
		jt.update("delete from core_advance_query_sort");
//		清除所有公告
		jt.update("delete from core_announcement");
//		清除所有非系统内置参数
		jt.update("delete from core_attr where type<>'1'");
//		清除所有邮件内容
		jt.update("delete from core_mail");
		jt.update("delete from core_mail_receive");
//		清除所有日志内容
		jt.update("delete from core_login_log");
		jt.update("delete from core_operation_log");
		jt.update("delete from core_sql_log");
	}
	
	/**
	 * 主方法
	 * @param args 参数列表
	 */
	public static void main(String[] args) throws Exception {
//		初始化数据源
//		DataSource dataSource = getDataSource("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/core_responsive", "root", "root");
		
//		测试SQL
//		getSQL(dataSource, "demo_car", "c", "0");
		
//		测试创建对象
//		createModel(dataSource, "C:\\Files\\Workspace_dlshouwen\\core-responsive\\src", "com.dlshouwen.demo.demo", "Car", "demo_car", "汽车");
		
//		测试创建Java文件
//		createJavaFile("C:\\Files\\Workspace_dlshouwen\\core-responsive\\src", "com.dlshouwen.demo.demo", "Car", "汽车管理");
		
//		测试生成Grid配置数据
//		constructGridDate(dataSource, "demo_car");
		
//		测试生成表单对应属性
//		constructInputData(dataSource, "demo_car");
		
//		测试生成表字段属性说明
//		constructFiledDescription(dataSource, "demo_car", "汽车");
		
//		测试生成任务可用参数
//		constructTaskAttrDescription(dataSource, SessionUser.class, "core_user", "用户");
		
//		测试生成图片
//		imageClipping("C:\\Files\\Workspace_dlshouwen\\core-responsive\\WebContent\\resources\\document\\images", "C:\\Temp\\images", "1", 300, 200);
//		imageClipping("C:\\Files\\Workspace_dlshouwen\\core-responsive\\WebContent\\resources\\document\\images", "C:\\Temp\\images", "2", 0.5, 0.5);
//		imageClipping("C:\\Files\\Workspace_dlshouwen\\core-responsive\\WebContent\\resources\\document\\images", "C:\\Temp\\images", "3", 300, 200);
//		imageClipping("C:\\Files\\Workspace_dlshouwen\\core-responsive\\WebContent\\resources\\document\\images", "C:\\Temp\\images", "4", 0.5, 0.5);
//		imageClipping("C:\\Files\\Workspace_dlshouwen\\core-responsive\\WebContent\\resources\\document\\images", "C:\\Temp\\images", "5", 300, 120);
//		imageClipping("C:\\Files\\Workspace_dlshouwen\\core-responsive\\WebContent\\resources\\document\\images", "C:\\Temp\\images", "6", 0.5, 0.5);
		
//		测试生成基础数据
//		constructTestDatas(dataSource);
		
//		测试删除基础数据
//		clearTestDatas(dataSource);
		
//		测试生成JavaAPI
//		constructJavaAPI("C:\\Files\\Workspace_dlshouwen\\core-responsive\\src\\com\\dlshouwen\\core\\base\\utils", AjaxUtils.class);
//		constructJavaAPI("C:\\Files\\Workspace_dlshouwen\\core-responsive\\src\\com\\dlshouwen\\core\\base\\utils", AssistDevelopUtils.class);
//		constructJavaAPI("C:\\Files\\Workspace_dlshouwen\\core-responsive\\src\\com\\dlshouwen\\core\\base\\utils", AttributeUtils.class);
//		constructJavaAPI("C:\\Files\\Workspace_dlshouwen\\core-responsive\\src\\com\\dlshouwen\\core\\base\\utils", CodeTableUtils.class);
//		constructJavaAPI("C:\\Files\\Workspace_dlshouwen\\core-responsive\\src\\com\\dlshouwen\\core\\base\\utils", DateUtils.class);
//		constructJavaAPI("C:\\Files\\Workspace_dlshouwen\\core-responsive\\src\\com\\dlshouwen\\core\\base\\utils", DemoUtils.class);
//		constructJavaAPI("C:\\Files\\Workspace_dlshouwen\\core-responsive\\src\\com\\dlshouwen\\core\\base\\utils", GUID.class);
//		constructJavaAPI("C:\\Files\\Workspace_dlshouwen\\core-responsive\\src\\com\\dlshouwen\\core\\base\\utils", IdCardGenerator.class);
//		constructJavaAPI("C:\\Files\\Workspace_dlshouwen\\core-responsive\\src\\com\\dlshouwen\\core\\base\\utils", IdCardUtils.class);
//		constructJavaAPI("C:\\Files\\Workspace_dlshouwen\\core-responsive\\src\\com\\dlshouwen\\core\\base\\utils", LimitUtils.class);
//		constructJavaAPI("C:\\Files\\Workspace_dlshouwen\\core-responsive\\src\\com\\dlshouwen\\core\\base\\utils", LogUtils.class);
//		constructJavaAPI("C:\\Files\\Workspace_dlshouwen\\core-responsive\\src\\com\\dlshouwen\\core\\base\\utils", QuartzUtils.class);
//		constructJavaAPI("C:\\Files\\Workspace_dlshouwen\\core-responsive\\src\\com\\dlshouwen\\core\\base\\utils", SecurityUtils.class);
//		constructJavaAPI("C:\\Files\\Workspace_dlshouwen\\core-responsive\\src\\com\\dlshouwen\\core\\base\\utils", SpringUtils.class);
//		constructJavaAPI("C:\\Files\\Workspace_dlshouwen\\core-responsive\\src\\com\\dlshouwen\\core\\base\\utils", SqlUtils.class);
//		constructJavaAPI("C:\\Files\\Workspace_dlshouwen\\core-responsive\\src\\com\\dlshouwen\\core\\base\\utils", TagUtils.class);
//		constructJavaAPI("C:\\Files\\Workspace_dlshouwen\\core-responsive\\src\\com\\dlshouwen\\core\\base\\utils", ValidateUtils.class);
//		constructJavaAPI("C:\\Files\\Workspace_dlshouwen\\core-responsive\\src\\com\\dlshouwen\\core\\base\\extra\\task\\utils", TaskUtils.class);
//		constructJavaAPI("C:\\Files\\Workspace_dlshouwen\\core-responsive\\src\\com\\dlshouwen\\core\\base\\extra\\unique\\utils", UniqueUtils.class);
//		constructJavaAPI("C:\\Files\\Workspace_dlshouwen\\core-responsive\\src\\com\\dlshouwen\\core\\base\\dao", BaseDao.class);

//		测试生成JavaScriptAPI
//		constructJavaScriptAPI("C:\\Files\\Workspace_dlshouwen\\core-responsive\\WebContent\\resources\\js\\utils.js");
//		constructJavaScriptAPI("C:\\Files\\Workspace_dlshouwen\\core-responsive\\WebContent\\resources\\js\\dlshouwen.js");

//		测试生成CssAPI
//		constructCssAPI("C:\\Files\\Workspace_dlshouwen\\core-responsive\\WebContent\\resources\\css\\global.css");
	}
	
}
