package com.dlshouwen.core.base.local;

import com.dlshouwen.core.base.model.SessionUser;

/**
 * SESSION用户的本地资源缓存
 * @author 大连首闻科技有限公司
 * @version 2013-11-12 14:58:17
 */
public class SessionUserThreadLocal {
	
	/** 定义本地缓存资源 */
	private static ThreadLocal<SessionUser> _SESSION_USER_ = new ThreadLocal<SessionUser>();
	
	/**
	 * 获取缓存的Session用户信息
	 * @return 缓存的Session用户信息
	 */
	public static SessionUser getUser() {
		return _SESSION_USER_.get();
	}
	
	/**
	 * 设置缓存的Session用户信息
	 * @param sessionUser Session用户信息
	 */
	public static void setUser(SessionUser sessionUser){
		_SESSION_USER_.set(sessionUser);
	}

}
