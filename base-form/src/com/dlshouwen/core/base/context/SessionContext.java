package com.dlshouwen.core.base.context;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

/**
 * 服务启动监听
 * @author 大连首闻科技有限公司
 * @version 2013-7-12 10:41:08
 */
public class SessionContext {

	/** 私有化Session上下文对象 */
	private static SessionContext instance;

	/** 定义Session信息 */
	private HashMap<String, HttpSession> sessionInfo;

	/**
	 * 私有化构造方法，并在构造方法中实例化Session信息
	 */
	private SessionContext() {
		sessionInfo = new HashMap<String, HttpSession>();
	}

	/**
	 * 获取Session上下文对象
	 * @return Session上下文对象
	 */
	public static SessionContext getInstance() {
		if (instance == null)
			instance = new SessionContext();
		return instance;
	}

	/**
	 * 添加Session
	 * @param session session对象
	 */
	public synchronized void addSession(HttpSession session) {
		if (session != null) {
			sessionInfo.put(session.getId(), session);
		}
	}

	/**
	 * 删除Session
	 * @param session session对象
	 */
	public synchronized void deleteSession(HttpSession session) {
		if (session != null) {
			sessionInfo.remove(session.getId());
		}
	}

	/**
	 * 获取Session对象
	 * @param sessionId session编号
	 * @return session对象
	 */
	public synchronized HttpSession getSession(String sessionId) {
		if (sessionId == null)
			return null;
		return (HttpSession) sessionInfo.get(sessionId);
	}
	
}
