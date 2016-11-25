package com.dlshouwen.core.base.utils;

import java.util.Map;

import org.apache.commons.collections.MapUtils;

/**
 * AJAX操作响应头的工具类
 * @author 大连首闻科技有限公司
 * @version 2013-7-12 9:24:32
 */
public class LimitUtils {
	
	/**
	 * 判断地址是否有权限
	 * <p>判断地址是否包含权限，主要应用于button标签及SessionFilter中的地址权限判断，可匹配通配符
	 * @param url URL地址
	 * @param limitInfo Application中存储的权限信息
	 * @return 是否有权限
	 * @throws Exception 抛出全部异常
	 */
	public static boolean checkLimit(String url, Map<String, String> limitInfo) throws Exception {
//		循环判断是否包含
		for(String limit : limitInfo.keySet()){
//			判断URL是否匹配，如果匹配则有权限
			String regex = limit.replaceAll("\\*", "[^/]\\*");
			if(url.matches(regex)&&"0".equals(MapUtils.getString(limitInfo, limit))){
				return false;
			}
		}
		return true;
	}
	
}
