package com.dlshouwen.core.system.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 功能
 * @author 大连首闻科技有限公司
 * @version 2015-07-28 14:00:39
 */
public class Limit {

	@Length(min=0, max=40, message="功能内码长度必须在0-40之间")
	private String limit_id;

	@NotBlank(message="上级功能内码不能为空")
	@Length(min=0, max=40, message="上级功能内码长度必须在0-40之间")
	private String pre_limit_id;

	@NotBlank(message="系统内码不能为空")
	@Length(min=0, max=40, message="系统内码长度必须在0-40之间")
	private String system_id;

	@NotBlank(message="功能名称不能为空")
	@Length(min=0, max=40, message="功能名称长度必须在0-40之间")
	private String limit_name;

	@NotBlank(message="功能类型不能为空")
	@Length(min=0, max=2, message="功能类型长度必须在0-2之间")
	private String limit_type;

	@NotBlank(message="是否异步不能为空")
	@Length(min=0, max=2, message="是否异步长度必须在0-2之间")
	private String ifAsynch;

	@NotBlank(message="url地址不能为空")
	@Length(min=0, max=120, message="url地址长度必须在0-120之间")
	private String url;

	@NotNull(message="排序码不能为空")
	private int sort;

	@NotBlank(message="图标不能为空")
	@Length(min=0, max=40, message="图标长度必须在0-40之间")
	private String icon_fa;

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

	public String getLimit_id() {
		return limit_id;
	}

	public void setLimit_id(String limit_id) {
		this.limit_id = limit_id;
	}

	public String getPre_limit_id() {
		return pre_limit_id;
	}

	public void setPre_limit_id(String pre_limit_id) {
		this.pre_limit_id = pre_limit_id;
	}

	public String getSystem_id() {
		return system_id;
	}

	public void setSystem_id(String system_id) {
		this.system_id = system_id;
	}

	public String getLimit_name() {
		return limit_name;
	}

	public void setLimit_name(String limit_name) {
		this.limit_name = limit_name;
	}

	public String getLimit_type() {
		return limit_type;
	}

	public void setLimit_type(String limit_type) {
		this.limit_type = limit_type;
	}

        public String getIfAsynch() {
            return ifAsynch;
        }

        public void setIfAsynch(String ifAsynch) {
            this.ifAsynch = ifAsynch;
        }
        
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getIcon_fa() {
		return icon_fa;
	}

	public void setIcon_fa(String icon_fa) {
		this.icon_fa = icon_fa;
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
	
	private String system_name;
	
	private String creator_name;
	
	private String editor_name;

	public String getSystem_name() {
		return system_name;
	}

	public void setSystem_name(String system_name) {
		this.system_name = system_name;
	}

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