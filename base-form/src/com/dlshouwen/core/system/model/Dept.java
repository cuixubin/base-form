package com.dlshouwen.core.system.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 部门
 * @author 大连首闻科技有限公司
 * @version 2015-07-28 14:00:39
 */
public class Dept {

	@Length(min=0, max=40, message="部门编号长度必须在0-40之间")
	private String dept_id;
	
	@NotBlank(message="上级部门编号不能为空")
	@Length(min=0, max=40, message="上级部门编号长度必须在0-40之间")
	private String pre_dept_id;

	@NotBlank(message="部门名称不能为空")
	@Length(min=0, max=40, message="部门名称长度必须在0-40之间")
	private String dept_name;

	@NotBlank(message="联系电话不能为空")
	@Length(min=0, max=20, message="联系电话长度必须在0-20之间")
	private String phone;

	@NotBlank(message="负责人不能为空")
	@Length(min=0, max=40, message="负责人长度必须在0-40之间")
	private String principal;

	@NotBlank(message="负责人电话不能为空")
	@Length(min=0, max=20, message="负责人电话长度必须在0-20之间")
	private String principal_phone;

	@NotNull(message="排序号不能为空")
	private int sort;

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

	public String getDept_id() {
		return dept_id;
	}

	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}

	public String getPre_dept_id() {
		return pre_dept_id;
	}

	public void setPre_dept_id(String pre_dept_id) {
		this.pre_dept_id = pre_dept_id;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getPrincipal_phone() {
		return principal_phone;
	}

	public void setPrincipal_phone(String principal_phone) {
		this.principal_phone = principal_phone;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
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
	
//	===================================
//	附属字段
//	===================================
	
	private String creator_name;
	
	private String editor_name;

	public String getCreator_name() {
		return creator_name;
	}

	public void setCreator_name(String creator_name) {
		this.creator_name = creator_name;
	}

	public String getEditor_name() {
		return editor_name;
	}

	public void setEditor_name(String editor_name) {
		this.editor_name = editor_name;
	}

}