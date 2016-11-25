package com.dlshouwen.core.system.model;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 参数
 * @author 大连首闻科技有限公司
 * @version 2013-08-17 12:13:01
 */
public class Attr {

	private String attr_id;

	@NotBlank(message="参数编号不能为空")
	@Length(min=0, max=80, message="参数编号长度必须在0-80之间")
	private String attr_code;

	@Length(min=0, max=40, message="参数名称长度必须在0-40之间")
	private String attr_name;

	@Length(min=0, max=2, message="参数类别长度必须在0-2之间")
	private String type;

	private String content;

	@Length(min=0, max=200, message="备注长度必须在0-200之间")
	private String remark;

	@NotNull(message="排序不能为空")
	private int sort;

	public String getAttr_id() {
		return attr_id;
	}

	public void setAttr_id(String attr_id) {
		this.attr_id = attr_id;
	}

	public String getAttr_code() {
		return attr_code;
	}

	public void setAttr_code(String attr_code) {
		this.attr_code = attr_code;
	}

	public String getAttr_name() {
		return attr_name;
	}

	public void setAttr_name(String attr_name) {
		this.attr_name = attr_name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

}