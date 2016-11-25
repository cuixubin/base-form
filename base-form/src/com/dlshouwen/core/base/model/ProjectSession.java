package com.dlshouwen.core.base.model;

/**
 * 系统回话对象，线程使用
 * @author 大连首闻科技有限公司
 * @version 2015-12-2 14:11:42
 */
public class ProjectSession {
	
	/** Session编号 */
	private String session_id;

	/** 登录机IP地址 */
	private String ip;
	
	public String getSession_id() {
		return session_id;
	}

	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
