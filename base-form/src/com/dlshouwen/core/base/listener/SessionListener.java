package com.dlshouwen.core.base.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.dlshouwen.core.base.config.CONFIG;
import com.dlshouwen.core.base.context.SessionContext;

/**
 * Session相关监听
 * @author 大连首闻科技有限公司
 * @version 2013-11-19 15:03:53
 */
public class SessionListener implements HttpSessionAttributeListener, HttpSessionListener, ServletContextListener {
	
	/** 定义Session上下文对象，主要用于上传文件的Session校验 */
	private SessionContext sessionContext = SessionContext.getInstance();

	/**
	 * 参数追加方法监听
	 * @param httpSessionBindingEvent HttpSession绑定事件
	 */
	public void attributeAdded(HttpSessionBindingEvent httpSessionBindingEvent) {
	}

	/**
	 * 参数移除方法监听
	 * @param httpSessionBindingEvent HttpSession绑定事件
	 */
	public void attributeRemoved(HttpSessionBindingEvent httpSessionBindingEvent) {
	}

	/**
	 * 参数替换方法监听
	 * @param httpSessionBindingEvent HttpSession绑定事件
	 */
	public void attributeReplaced(HttpSessionBindingEvent httpSessionBindingEvent) {
	}

	/**
	 * Session创建时执行的方法
	 * @param httpSessionEvent HttpSession事件
	 */
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		sessionContext.addSession(httpSessionEvent.getSession());
	}
	
	/**
	 * context初始化处理信息
	 * <p>对于当前数据库中未退出的用户执行强制退出，推出类别为服务器启动退出
	 * @param servletContextEvent servletContext事件
	 */
	public void contextInitialized(ServletContextEvent servletContextEvent) {
//		获取Spring的配置
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(CONFIG.SERVLET_CONTEXT);
//		获取数据源
		DataSource dataSource = context.getBean(CONFIG.LOGIN_LOG_DATA_SOURCE, DataSource.class);
//		定义JdbcTemplate对象
		JdbcTemplate jt = new JdbcTemplate(dataSource);
//		日志定义
		Logger logger = Logger.getLogger(SessionListener.class);
//		记录开始日志
		logger.info("正在处理登录信息，请稍后...");
//		更新登录信息
		StringBuffer sql = new StringBuffer();
		sql.append("update core_login_log set is_logout='1', logout_type='3', logout_time=now() where is_logout='0'");
		jt.update(sql.toString());
//		记录码表成功日志
		logger.info("登录信息处理完成。");
	}

	/**
	 * Session失效
	 * <p>执行用户退出操作，退出类型为Session失效退出
	 * @param servletContextEvent servletContext事件
	 */
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
//		获取Spring的配置
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(CONFIG.SERVLET_CONTEXT);
//		获取数据源
		DataSource dataSource = context.getBean(CONFIG.LOGIN_LOG_DATA_SOURCE, DataSource.class);
//		定义JdbcTemplate对象
		JdbcTemplate jt = new JdbcTemplate(dataSource);
//		日志定义
		Logger logger = Logger.getLogger(SessionListener.class);
//		获取SESSION_ID
		String sessionId = httpSessionEvent.getSession().getId();
//		更新登录信息
		StringBuffer sql = new StringBuffer();
		sql.append("update core_login_log set is_logout='1', logout_type='2', logout_time=now() where session_id=?");
		jt.update(sql.toString(), sessionId);
		logger.info("Session失效["+sessionId+"]");
//		去掉Session在上下文中的记录
		sessionContext.deleteSession(httpSessionEvent.getSession());
	}

	/**
	 * 监听失效方法
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
	}

}
