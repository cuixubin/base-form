package com.dlshouwen.core.base.utils;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SQL工具类
 * @author 大连首闻科技有限公司
 * @version 2015-12-1 09:30:17
 */
public class SqlUtils {
	
	/**
	 * 获取Preparestatement方式SQL的Key值
	 * <p>用于获取Preparestatement方式SQL的key值，传入值后将会依据内容个数生成对应的?占位符
	 * @param values 值列表，将依据该值列表决定生成对应的?占位符数量
	 * @param splitChar 分隔符，值列表的分割符号，通常为英文逗号,
	 * @return Preparestatement方式SQL的Key值
	 * @throws Exception 抛出全部异常
	 */
	public static String getArgsKey(String values, String splitChar) throws Exception {
		if(values==null||"".equals(values.trim())){
			return "?";
		}
		StringBuffer attrs = new StringBuffer();
		for(int i=0; i<values.split(splitChar).length; i++){
			attrs.append("?").append(",");
		}
		if(attrs.length()==0){
			return "?";
		}else{
			attrs.deleteCharAt(attrs.length()-1);
		}
		return attrs.toString();
	}
	
	/**
	 * 获取Preparestatement方式SQL的值列表
	 * <p>用于获取Preparestatement方式SQL的值列表，传入值后将会依据内容生成List<Object>列表
	 * @param values 值列表，将依据该值列表决定生成List<Object>类型的值列表内容及顺序
	 * @param splitChar 分隔符，值列表的分割符号，通常为英文逗号,
	 * @return Preparestatement方式SQL的值列表
	 * @throws Exception 抛出全部异常
	 */
	public static List<Object> getArgsValue(String values, String splitChar) throws Exception {
		List<Object> list = new ArrayList<Object>();
		if(values==null||"".equals(values.trim())){
			list.add("");
			return list;
		}
		for(String value : values.split(splitChar)){
			list.add(value);
		}
		if(list.size()==0){
			list.add("");
		}
		return list;
	}
	
	/**
	 * 获取对象操作的SQL
	 * <p>用于获取对象操作的SQL，对象的参数格式采用${参数名}的方式进行匹配，该方法可以替换改格式参数为?占位符
	 * @param sql 原对象操作SQL
	 * @return 经过替换占位符处理的SQL
	 * @throws Exception 抛出全部异常
	 */
	public static String getObjectSql(String sql) throws Exception {
		return sql.replaceAll("\\$\\{\\s*[\\S&&[^\\}]]+\\s*\\}", "?");
	}
	
	/**
	 * 获取对象操作参数列表
	 * <p>用于获取对象操作的参数列表，对象的参数格式采用${参数名}的方式进行匹配，
	 * 该方法根据传入的SQL内容匹配SQL中所有符合条件的参数格式，获取参数名后通过对象反射一一查找object对象内匹配的属性，
	 * 若匹配上，则该位置将填入对象参数值，若不能匹配，则从此节点顺序开始查找对应的追加参数，
	 * 若查找到，则追加到参数列表中，若长度溢出，则抛出异常。
	 * @param sql 原对象操作SQL
	 * @param object 通过反射查找对应属性的对象
	 * @param args 若对象未能匹配到属性则通过该对象数据顺次追加参数值
	 * @return 对象操作的参数列表
	 * @throws Exception 抛出全部异常
	 */
	public static List<Object> getObjectArgs(String sql, Object object, Object...args) throws Exception {
//		Match匹配SQL的参数内容，使用${}方式Match
		Matcher matcher = Pattern.compile("\\$\\{\\s*[\\S&&[^\\}]]+\\s*\\}").matcher(sql);
//		定义参数列表
		List<Object> params = new ArrayList<Object>();
//		获取对象的类
		Class<?> objectClass = object.getClass();
//		获取属性描述
		PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(objectClass).getPropertyDescriptors();
//		定义可变参数下标
		int i=0;
		while(matcher.find()){
//			获取实际参数
			String attr = matcher.group().replace("${", "").replace("}", "").trim();
//			设置是否找到
			boolean isFind = false;
//			内循环查找对应属性
			for(PropertyDescriptor propertyDescriptor : propertyDescriptors){
				if(attr.equalsIgnoreCase(propertyDescriptor.getName())){
//					映射方法并通过反射获取参数
					Method method = propertyDescriptor.getReadMethod();
					Object attrObject = method.invoke(object);
//					参数赋值并跳出循环继续Match
					params.add(attrObject);
					isFind = true;
					break;
				}
			}
			if(!isFind){
//				如果没找到对应参数，则添加args的参数
				params.add(args[i]);
				i++;
			}
		}
		return params;
	}

}
