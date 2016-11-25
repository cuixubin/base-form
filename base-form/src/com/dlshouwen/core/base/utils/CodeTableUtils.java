package com.dlshouwen.core.base.utils;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspWriter;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;

import com.dlshouwen.core.base.config.CONFIG;

/**
 * 码表工具类
 * @author 大连首闻科技有限公司
 * @version 2013-7-23 20:18:14
 */
@SuppressWarnings("unchecked")
public class CodeTableUtils {
	
	/**
	 * JSP页面中输出码表映射的JS代码
	 * <p>用于JSP页面中需要引用码表的情况，通过Java代码调用该方法即可输入JS代码，
	 * 代码将处理两项操作：追加JS中的code_table_info对象值，key为码表类别编号，value为该码表类别的码表信息JSON数据，JSON数据格式为Object，key-码表编号、value-码表值；
	 * 定义方法“sigmaGridCodeTable_码表类别编号”，根据传入的value参数获取码表具体值，通常用于列表回显。
	 * @param context Servlet上下文
	 * @param out JSP输出对象
	 * @param type 码表类别编号
	 * @throws Exception 抛出全部异常
	 */
	public static void createCodeTableJS(ServletContext context, JspWriter out, String type) throws Exception{
//		获取码表的数据
		LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>)((Map<String, Object>)context.getAttribute(CONFIG.CODE_TABLE)).get(type);
//		生成JS程序
		StringBuffer result = new StringBuffer();
		result.append("<script type='text/javascript'>");
		result.append("code_table_info['").append(type).append("'] = ").append(JSONObject.fromObject(map).toString()).append(";");
		result.append("function sigmaGridCodeTable_"+type+"(value ,record,columnObj,grid,colNo,rowNo){");
		result.append("var result = value;");
		if(map!=null){
			for(String key : map.keySet()){
				result.append("if(value==").append("'").append(key).append("')")
						.append("result=").append("'").append(MapUtils.getString(map, key)).append("';");
			}
		}
		result.append("return result;");
		result.append("}");
		result.append("</script>");
//		执行写入
		out.println(result);
	}
	
	/**
	 * 获取码表值
	 * <p>用于获取码表值，需要提供码表类别编号及码表编号信息
	 * @param context Servlet上下文
	 * @param type 码表类别编号
	 * @param key 码表编号
	 * @return 码表值
	 */
	public static String getValue(ServletContext context, String type, String key){
//		获取码表的数据
		LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>)((Map<String, Object>)context.getAttribute(CONFIG.CODE_TABLE)).get(type);
		return MapUtils.getString(map, key);
	}
	
	/**
	 * 根据码表类别编号获取码所有表值
	 * <p>用于获取码表值，需要提供码表类别编号及码表编号信息
	 * @param context Servlet上下文
	 * @param type 码表类别编号
	 * @return 码表值
	 */
	public static Map<String, Object> getValues(ServletContext context, String type){
//		获取码表的数据
		LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>)((Map<String, Object>)context.getAttribute(CONFIG.CODE_TABLE)).get(type);
		return map;
	}
	
}
