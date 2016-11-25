package com.dlshouwen.core.base.utils;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.dlshouwen.core.base.patch.DateJsonValueProcessor;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * AJAX操作响应的工具类
 * @author 大连首闻科技有限公司
 * @version 2013-7-12 9:24:32
 */
@SuppressWarnings({ "rawtypes" })
public class AjaxUtils {
	
	/**
	 * Ajax回写内容
	 * <p>用于回写response信息，由于Spring MVC可以直接通过return响应，该方法几乎不会用到
	 * @param response 响应对象
	 * @param value 需要写入的内容
	 * @throws Exception 抛出全部异常
	 */
	public static void ajaxPut(HttpServletResponse response, String value) throws Exception {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(value);
	}
	
	/**
	 * Ajax回写Map对象，回写过程中将会把Map对象转为JSON
	 * <p>用于回写response信息，回写过程中将会把Map对象转为JSON，由于Spring MVC可以直接通过return响应，该方法几乎不会用到
	 * @param response 响应对象
	 * @param params 需要写入的Map对象
	 * @throws Exception 抛出全部异常
	 */
	public static void ajaxPut(HttpServletResponse response, Map<String, Object> params) throws Exception {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
//		json-lib 日期补丁
		JsonValueProcessor jsonProcessor = new DateJsonValueProcessor();
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, jsonProcessor);
		JSONObject content = JSONObject.fromObject(params, jsonConfig);
		response.getWriter().write(content.toString());
	}
	
	/**
	 * AJAX回写List对象，回写过程中将会把List对象转为JSON
	 * <p>用于回写response信息，回写过程中将会把List对象转为JSON，由于Spring MVC可以直接通过return响应，该方法几乎不会用到
	 * @param response 响应对象
	 * @param list 需要写入的List对象
	 * @throws Exception 抛出全部异常
	 */
	public static void ajaxPut(HttpServletResponse response, List list) throws Exception {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
//		json-lib 日期补丁
		JsonValueProcessor jsonProcessor = new DateJsonValueProcessor();
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, jsonProcessor);
		JSONArray content = JSONArray.fromObject(list, jsonConfig);
		response.getWriter().write(content.toString());
	}
	
	/**
	 * Ajax回写Object对象，回写过程中将会把Object对象转为JSON
	 * <p>用于回写response信息，回写过程中将会把Object对象转为JSON，由于Spring MVC可以直接通过return相应，该方法几乎不会用到
	 * @param response 响应对象
	 * @param object 需要写入的对象
	 * @throws Exception 抛出全部异常
	 */
	public static void ajaxPut(HttpServletResponse response, Object object) throws Exception {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
//		json-lib 日期补丁
		JsonValueProcessor jsonProcessor = new DateJsonValueProcessor();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, jsonProcessor);
		JSONObject content = JSONObject.fromObject(object, jsonConfig);
		response.getWriter().write(content.toString());
	}
	
}
