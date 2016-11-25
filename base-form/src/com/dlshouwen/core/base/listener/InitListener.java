package com.dlshouwen.core.base.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.dlshouwen.core.base.config.CONFIG;
import com.dlshouwen.core.base.loader.CodeTableLoader;
import com.dlshouwen.core.base.loader.SystemAttributeLoader;

/**
 * 服务启动监听
 * @author 大连首闻科技有限公司
 * @version 2013-7-12 10:41:08
 */
public class InitListener implements ServletContextListener{
	
	/**
	 * 监听启动方法
	 * <p>加载CONFIG对象，加载码表，加载系统参数
	 * @param servletContextEvent Servlet上下文事件
	 */
	public void contextInitialized(ServletContextEvent servletContextEvent) {
//		初始化CONFIG
		CONFIG.getInstance();
//		ServletContext赋值
		CONFIG.SERVLET_CONTEXT = servletContextEvent.getServletContext();
//		获取Spring的配置
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(CONFIG.SERVLET_CONTEXT);
//		定义数据源
		DataSource dataSource = null;
		/**
		 * 加载码表
		 */
//		获取加载码表的数据源
		dataSource = context.getBean(CONFIG.CODE_TABLE_DATA_SOURCE, DataSource.class);
//		加载码表信息
		try {
			CodeTableLoader.load(dataSource);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		/**
		 * 加载系统参数
		 */
//		获取加载系统参数的数据源
		dataSource = context.getBean(CONFIG.SYSTEM_ATTRIBUTE_DATA_SOURCE, DataSource.class);
//		加载码表信息
		try {
			SystemAttributeLoader.load(dataSource);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 监听失效方法
	 */
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
	}

}
