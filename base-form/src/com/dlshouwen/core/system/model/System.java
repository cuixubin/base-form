package com.dlshouwen.core.system.model;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 系统
 * @author 大连首闻科技有限公司
 * @version 2013-08-17 12:13:03
 */
public class System {

	private String system_id;

	@NotBlank(message="系统编号不能为空")
	@Length(min=0, max=40, message="系统编号长度必须在0-40之间")
	private String system_code;

	@NotBlank(message="系统名称不能为空")
	@Length(min=0, max=40, message="系统名称长度必须在0-40之间")
	private String system_name;
	
	@Length(min=0, max=200, message="备注长度必须在0-200之间")
	private String remark;

	@Length(min=0, max=40, message="创建人长度必须在0-40之间")
	private String creator;

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date create_time;
	
	@Length(min=0, max=40, message="编辑人长度必须在0-40之间")
	private String editor;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date edit_time;

	public String getSystem_id() {
		return system_id;
	}

	public void setSystem_id(String system_id) {
		this.system_id = system_id;
	}

	public String getSystem_code() {
		return system_code;
	}

	public void setSystem_code(String system_code) {
		this.system_code = system_code;
	}

	public String getSystem_name() {
		return system_name;
	}

	public void setSystem_name(String system_name) {
		this.system_name = system_name;
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

}