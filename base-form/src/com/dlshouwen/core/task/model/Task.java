package com.dlshouwen.core.task.model;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 任务
 * @author 大连首闻科技有限公司
 * @version 2013-08-17 12:13:03
 */
public class Task {

	private String task_id;

	@NotBlank(message="任务名称不能为空")
	@Length(min=0, max=200, message="任务名称长度必须在0-200之间")
	private String task_name;

	@NotBlank(message="状态不能为空")
	@Length(min=0, max=2, message="状态长度必须在0-2之间")
	private String status;

	@NotBlank(message="是否定时提示不能为空")
	@Length(min=0, max=2, message="是否定时提示长度必须在0-2之间")
	private String is_timing;

	@NotNull(message="定时提醒时间不能为空")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date timing_time;

	@NotNull(message="定时时间间隔不能为空")
	private int time_space;

	@NotBlank(message="是否永不过期不能为空")
	@Length(min=0, max=2, message="是否永不过期长度必须在0-2之间")
	private String is_never_overdue;

	@NotNull(message="过期时间不能为空")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date overdue_time;

	@NotBlank(message="提示信息模板不能为空")
	@Length(min=0, max=2000, message="提示信息模板长度必须在0-2000之间")
	private String message;

	@NotBlank(message="响应功能不能为空")
	@Length(min=0, max=40, message="响应功能长度必须在0-40之间")
	private String limit_id;
	
	private String limit_name;
	
	private String url;

	@NotBlank(message="触发SQL不能为空")
	@Length(min=0, max=2000, message="触发SQL长度必须在0-2000之间")
	private String detonate_sql;

	@NotBlank(message="是否所有用户启用不能为空")
	@Length(min=0, max=2, message="是否所有用户启用长度必须在0-2之间")
	private String is_all_user;

	@Length(min=0, max=40, message="创建人长度必须在0-40之间")
	private String creator;

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date create_time;
	
	@Length(min=0, max=40, message="编辑人长度必须在0-40之间")
	private String editor;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date edit_time;
	
	private String userIds;
	
	private String roleIds;
	
	@Valid
	private List<TaskAttr> taskAttrList;

	public String getTask_id() {
		return task_id;
	}

	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}

	public String getTask_name() {
		return task_name;
	}

	public void setTask_name(String task_name) {
		this.task_name = task_name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIs_timing() {
		return is_timing;
	}

	public void setIs_timing(String is_timing) {
		this.is_timing = is_timing;
	}

	public Date getTiming_time() {
		return timing_time;
	}

	public void setTiming_time(Date timing_time) {
		this.timing_time = timing_time;
	}

	public int getTime_space() {
		return time_space;
	}

	public void setTime_space(int time_space) {
		this.time_space = time_space;
	}

	public String getIs_never_overdue() {
		return is_never_overdue;
	}

	public void setIs_never_overdue(String is_never_overdue) {
		this.is_never_overdue = is_never_overdue;
	}

	public Date getOverdue_time() {
		return overdue_time;
	}

	public void setOverdue_time(Date overdue_time) {
		this.overdue_time = overdue_time;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLimit_id() {
		return limit_id;
	}

	public void setLimit_id(String limit_id) {
		this.limit_id = limit_id;
	}
	
	public String getLimit_name() {
		return limit_name;
	}

	public void setLimit_name(String limit_name) {
		this.limit_name = limit_name;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDetonate_sql() {
		return detonate_sql;
	}

	public void setDetonate_sql(String detonate_sql) {
		this.detonate_sql = detonate_sql;
	}

	public String getIs_all_user() {
		return is_all_user;
	}

	public void setIs_all_user(String is_all_user) {
		this.is_all_user = is_all_user;
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
	
	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	
	public List<TaskAttr> getTaskAttrList() {
		return taskAttrList;
	}

	public void setTaskAttrList(List<TaskAttr> taskAttrList) {
		this.taskAttrList = taskAttrList;
	}

}