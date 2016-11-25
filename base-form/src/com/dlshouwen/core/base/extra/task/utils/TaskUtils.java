package com.dlshouwen.core.base.extra.task.utils;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dlshouwen.core.base.model.SessionUser;

/**
 * 任务工具类
 * @author 大连首闻科技有限公司
 * @version 2013-7-17 14:13:23
 */
public class TaskUtils {
	
	/**
	 * 处理模板SQL
	 * <p>通过反射处理模板SQL，将SQL中的参数转换为SessionUser对象实例中具体的值
	 * @param templateSql 模板SQL
	 * @param sessionUser 登录用户对象
	 * @return 经过处理后的SQL
	 * @throws Exception 抛出全部异常
	 */
	public static String getTaskSqlByTemplate(String templateSql, SessionUser sessionUser) throws Exception {
//		获取新的SQL
		String resultSql = templateSql;
//		匹配是否有sessionUser的项目
		Matcher matcher = Pattern.compile("\\$\\{sessionUser\\.[\\S&&[^\\}]]+\\}").matcher(templateSql);
		while(matcher.find()){
			String attr = matcher.group();
			String attrName = attr.replace("${sessionUser.", "").replace("}", "").trim();
//			通过反射从sessionUser中获取此参数
			Method method = SessionUser.class.getMethod("get"+attrName.toUpperCase().substring(0, 1)+attrName.substring(1, attrName.length()));
			String value = (String)method.invoke(sessionUser);
			resultSql = resultSql.replaceAll("\\$\\{sessionUser\\."+attrName+"\\}", value);
		}
		return resultSql;
	}
	
}
