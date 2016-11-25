package com.dlshouwen.core.base.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.dlshouwen.core.system.model.Limit;
import com.dlshouwen.core.system.model.UserAttr;

/**
 * 用户对象
 * @author 大连首闻科技有限公司
 * @version 2015-12-2 14:11:39
 */
public class SessionUser {
	
	/** 登录编号 */
	private String login_id;
	
	/** Session编号 */
	private String session_id;
	
	/** 登录机IP地址 */
	private String ip;
	
	/** 登录时间 */
	private String login_time;

	/** 用户内码 */
	private String user_id;
	
	/** 用户所属机构编号 */
	private String dept_id;
	
	/** 用户所属机构名称 */
	private String dept_name;
	
	/** 用户编号 */
	private String user_code;
	
	/** 用户名称 */
	private String user_name;
	
	/** 密码 */
	private String password;
	
	/** 有效标识 */
	private String valid_type;
	
	/** 性别 */
	private String sex;
	
	/** 证件类别 */
	private String card_type;
	
	/** 证件编号 */
	private String card_id;
	
	/** 出生日期 */
	private Date birthday;
	
	/** 工作日期 */
	private Date work_date;
	
	/** 民族 */
	private String folk;
	
	/** 学历 */
	private String degree;
	
	/** 联系电话 */
	private String phone;
	
	/** Email */
	private String email;
	
	/** 联系地址 */
	private String address;
	
	/** 备注 */
	private String remark;
	
	/** 创建人 */
	private String creator;
	
	/** 创建时间 */
	private Date create_time;
	
	/** 编辑人 */
	private String editor;
	
	/** 编辑时间 */
	private Date edit_time;
	
	/** 用户功能列表数据 */
	private List<Limit> limitList;
	
	/** 用户功能信息数据 */
	private Map<String, String> limitInfo;
	
	/** 快捷方式列表数据 */
	private List<Limit> shortcutList;
	
	/** 用户参数 */
	private UserAttr userAttr;
	
	/** 当前会话中不再提示的任务 */
	private Map<String, Object> hideOnThisSessionTasks;

	public String getLogin_id() {
		return login_id;
	}

	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}

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

	public String getLogin_time() {
		return login_time;
	}

	public void setLogin_time(String login_time) {
		this.login_time = login_time;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getDept_id() {
		return dept_id;
	}

	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getUser_code() {
		return user_code;
	}

	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getValid_type() {
		return valid_type;
	}

	public void setValid_type(String valid_type) {
		this.valid_type = valid_type;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCard_type() {
		return card_type;
	}

	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}

	public String getCard_id() {
		return card_id;
	}

	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getWork_date() {
		return work_date;
	}

	public void setWork_date(Date work_date) {
		this.work_date = work_date;
	}

	public String getFolk() {
		return folk;
	}

	public void setFolk(String folk) {
		this.folk = folk;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public Date getEdit_time() {
		return edit_time;
	}

	public void setEdit_time(Date edit_time) {
		this.edit_time = edit_time;
	}

	public List<Limit> getLimitList() {
		return limitList;
	}

	public void setLimitList(List<Limit> limitList) {
		this.limitList = limitList;
	}

	public Map<String, String> getLimitInfo() {
		return limitInfo;
	}

	public void setLimitInfo(Map<String, String> limitInfo) {
		this.limitInfo = limitInfo;
	}

	public List<Limit> getShortcutList() {
		return shortcutList;
	}

	public void setShortcutList(List<Limit> shortcutList) {
		this.shortcutList = shortcutList;
	}

	public UserAttr getUserAttr() {
		return userAttr;
	}

	public void setUserAttr(UserAttr userAttr) {
		this.userAttr = userAttr;
	}

	public Map<String, Object> getHideOnThisSessionTasks() {
		return hideOnThisSessionTasks;
	}

	public void setHideOnThisSessionTasks(Map<String, Object> hideOnThisSessionTasks) {
		this.hideOnThisSessionTasks = hideOnThisSessionTasks;
	}
	
}