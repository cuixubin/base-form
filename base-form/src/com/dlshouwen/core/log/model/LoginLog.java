package com.dlshouwen.core.log.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.dlshouwen.core.base.config.CONFIG;
import com.dlshouwen.core.base.utils.CodeTableUtils;
import com.dlshouwen.core.base.utils.DateUtils;

/**
 * 登录日志
 * @author 大连首闻科技有限公司
 * @version 2015-07-28 14:31:05
 */
public class LoginLog {

	@NotBlank(message="日志内码不能为空")
	@Length(min=0, max=40, message="日志内码长度必须在0-40之间")
	private String log_id;

	@NotBlank(message="session编号不能为空")
	@Length(min=0, max=40, message="session编号长度必须在0-40之间")
	private String session_id;

	@NotBlank(message="登录用户内码不能为空")
	@Length(min=0, max=40, message="登录用户内码长度必须在0-40之间")
	private String login_user_id;

	@NotBlank(message="登录用户名称不能为空")
	@Length(min=0, max=40, message="登录用户名称长度必须在0-40之间")
	private String login_user_name;

	@NotBlank(message="登录用户所属部门不能为空")
	@Length(min=0, max=40, message="登录用户所属部门长度必须在0-40之间")
	private String login_user_dept_id;

	@NotBlank(message="登录用户所属部门名称不能为空")
	@Length(min=0, max=40, message="登录用户所属部门名称长度必须在0-40之间")
	private String login_user_dept_name;

	private Timestamp login_time;

	@NotBlank(message="登录ip不能为空")
	@Length(min=0, max=20, message="登录ip长度必须在0-20之间")
	private String ip;

	@NotBlank(message="登录状态不能为空")
	@Length(min=0, max=2, message="登录状态长度必须在0-2之间")
	private String login_status;

	@NotBlank(message="是否登出不能为空")
	@Length(min=0, max=2, message="是否登出长度必须在0-2之间")
	private String is_logout;

	@Length(min=0, max=2, message="登出状态长度必须在0-2之间")
	private String logout_type;

	private Timestamp logout_time;

	public String getLog_id() {
		return log_id;
	}

	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}

	public String getSession_id() {
		return session_id;
	}

	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}

	public String getLogin_user_id() {
		return login_user_id;
	}

	public void setLogin_user_id(String login_user_id) {
		this.login_user_id = login_user_id;
	}

	public String getLogin_user_name() {
		return login_user_name;
	}

	public void setLogin_user_name(String login_user_name) {
		this.login_user_name = login_user_name;
	}

	public String getLogin_user_dept_id() {
		return login_user_dept_id;
	}

	public void setLogin_user_dept_id(String login_user_dept_id) {
		this.login_user_dept_id = login_user_dept_id;
	}

	public String getLogin_user_dept_name() {
		return login_user_dept_name;
	}

	public void setLogin_user_dept_name(String login_user_dept_name) {
		this.login_user_dept_name = login_user_dept_name;
	}

	public Timestamp getLogin_time() {
		return login_time;
	}

	public void setLogin_time(Timestamp login_time) {
		this.login_time = login_time;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getLogin_status() {
		return login_status;
	}

	public void setLogin_status(String login_status) {
		this.login_status = login_status;
	}

	public String getIs_logout() {
		return is_logout;
	}

	public void setIs_logout(String is_logout) {
		this.is_logout = is_logout;
	}

	public String getLogout_type() {
		return logout_type;
	}

	public void setLogout_type(String logout_type) {
		this.logout_type = logout_type;
	}

	public Timestamp getLogout_time() {
		return logout_time;
	}

	public void setLogout_time(Timestamp logout_time) {
		this.logout_time = logout_time;
	}
	
	@Override
	public String toString(){
		SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer result = new StringBuffer();
		result.append("SESSION编号：").append(this.getSession_id()).append("; ");
		result.append("登录用户编号：").append(this.getLogin_user_id()).append("; ");
		result.append("登录用户名称：").append(this.getLogin_user_name()).append("; ");
		result.append("登录用户部门编号：").append(this.getLogin_user_dept_id()).append("; ");
		result.append("登录用户部门名称：").append(this.getLogin_user_dept_name()).append("; ");
		result.append("登录时间：").append(this.getLogin_time()==null?"-":timeFormat.format(this.getLogin_time())).append("; ");
		result.append("登录IP：").append(this.getIp()).append("; ");
		result.append("登录状态：").append(CodeTableUtils.getValue(CONFIG.SERVLET_CONTEXT, "login_status", this.getLogin_status())).append("; ");
		result.append("是否登出：").append(CodeTableUtils.getValue(CONFIG.SERVLET_CONTEXT, "zero_one", this.getIs_logout())).append("; ");
		result.append("登出状态：").append(CodeTableUtils.getValue(CONFIG.SERVLET_CONTEXT, "logout_type", this.getLogout_type())).append("; ");
		result.append("登出时间：").append(this.getLogout_time()==null?"-":timeFormat.format(this.getLogout_time())).append("; ");
		result.append("输出时间：").append(DateUtils.getNowTimeHaveMS()).append("");
		return result.toString();
	}
	
//	========================================
//	辅助字段
//	========================================
	
	private String logout_type_name;
	
	private String is_logout_name;
	
	private String login_status_name;
	
	private String data_type;
	
	private Timestamp donate_time;

	public String getLogout_type_name() {
		return logout_type_name;
	}

	public void setLogout_type_name(String logout_type_name) {
		this.logout_type_name = logout_type_name;
	}

	public String getIs_logout_name() {
		return is_logout_name;
	}

	public void setIs_logout_name(String is_logout_name) {
		this.is_logout_name = is_logout_name;
	}

	public String getLogin_status_name() {
		return login_status_name;
	}

	public void setLogin_status_name(String login_status_name) {
		this.login_status_name = login_status_name;
	}

	public String getData_type() {
		return data_type;
	}

	public void setData_type(String data_type) {
		this.data_type = data_type;
	}

	public Timestamp getDonate_time() {
		return donate_time;
	}

	public void setDonate_time(Timestamp donate_time) {
		this.donate_time = donate_time;
	}

}