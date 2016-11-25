package com.dlshouwen.core.base.utils;

import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.collections.MapUtils;

import com.dlshouwen.core.base.config.CONFIG;

/**
 * 系统参数工具类
 * @author 大连首闻科技有限公司
 * @version 2013-7-12 9:24:32
 */
@SuppressWarnings("unchecked")
public class AttributeUtils {
	
	/**
	 * 获取参数内容
	 * <p>用于获取参数值，需要提供参数编号
	 * @param context Servlet上下文
	 * @param attrCode 参数编号
	 * @return 参数内容
	 */
	public static String getAttributeContent(ServletContext context, String attrCode){
		Map<String, Object> systemAttributes = (Map<String, Object>)context.getAttribute(CONFIG.SYSTEM_ATTRIBUTE);
		return MapUtils.getString(MapUtils.getMap(systemAttributes, attrCode), "content");
	}
	
}
