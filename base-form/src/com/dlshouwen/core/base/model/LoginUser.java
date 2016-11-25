package com.dlshouwen.core.base.model;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 登录用户
 * @author 大连首闻科技有限公司
 * @version 2013-7-12 15:22:04
 */
public class LoginUser {
	
	@NotBlank(message="用户名必须填写。")
	private String user_code;
	
	@NotBlank(message="密码必须填写。")
	private String password;

	public String getUser_code() {
		return user_code;
	}

	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
