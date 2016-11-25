package com.dlshouwen.core.base.local;

import com.dlshouwen.core.base.model.ProjectSession;

/**
 * 项目SESSION的本地资源缓存
 * @author 大连首闻科技有限公司
 * @version 2013-11-13 10:28:04
 */
public class ProjectSessionThreadLocal {
	
	/** 定义本地缓存资源 */
	private static ThreadLocal<ProjectSession> _PROJECT_SESSION_ = new ThreadLocal<ProjectSession>();
	
	/**
	 * 获取缓存的项目Session信息
	 * @return 缓存的项目Session信息
	 */
	public static ProjectSession getSession() {
		return _PROJECT_SESSION_.get();
	}
	
	/**
	 * 设置缓存的项目Session信息
	 * @param projectSession 项目Session信息
	 */
	public static void setSession(ProjectSession projectSession){
		_PROJECT_SESSION_.set(projectSession);
	}

}
