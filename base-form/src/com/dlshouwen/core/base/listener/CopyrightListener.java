package com.dlshouwen.core.base.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

/**
 * 服务启动监听加载版权信息
 * @author 大连首闻科技有限公司
 * @version 2013-12-2 13:32:15
 */
public class CopyrightListener implements ServletContextListener{
	
	/** 日志记录对象 */
	private Logger logger = Logger.getLogger(CopyrightListener.class);
	
	/**
	 * 监听启动方法
	 * <p>输出版权信息
	 * @param servletContextEvent Servlet上下文事件
	 */
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		logger.info("**********************************************************");
		logger.info("*                                                        *");
		logger.info("*              DLSHOUWEN CORE RESPONSIVE                 *");
		logger.info("*                                                        *");
		logger.info("*           Copyright (c) 2015-2016 DLSHOUWEN            *");
		logger.info("*                                                        *");
		logger.info("*       Company : http://www.dlshouwen.com               *");
		logger.info("*       WebSite : http://core.dlshouwen.com/responsive   *");
		logger.info("*         Email : service@dlshouwen.com                  *");
		logger.info("*           BBS : http://bbs.dlshouwen.com               *");
		logger.info("*    QQ(WeiXin) ：1739881799                             *");
		logger.info("*         WeiBo ：@大连首闻科技有限公司                                                 *");
		logger.info("* WeiXin Public ：dlshouwen                              *");
		logger.info("*                                                        *");
		logger.info("**********************************************************");
	}
	
	/**
	 * 监听失效方法
	 * @param servletContextEvent Servlet上下文事件
	 */
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
	}

}
