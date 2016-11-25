package com.dlshouwen.core.base.utils;

import org.springframework.web.context.ContextLoader;

/**
 * Spring工具类
 * @author 大连首闻科技有限公司
 * @version 2015-12-1 10:11:44
 */
public class SpringUtils {
	
	/**
	 * 取得对应的bean实例
	 * <p>通过beanName参数获取对应的bean实例对象，若传递的实例名称在定义的配置中未找到则返回空
	 * @param beanName bean名称
	 * @return 对应的bean实例
	 * @throws Exception 抛出全部异常
	 */
	public static Object getBean(String beanName) throws Exception {
		return ContextLoader.getCurrentWebApplicationContext().getBean(beanName);
	}
        public static Object getBean(Class cls) throws Exception {
		return ContextLoader.getCurrentWebApplicationContext().getBean(cls);
	}
}
