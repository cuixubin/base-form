package com.dlshouwen.core.system.model;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 码表
 * @author 大连首闻科技有限公司
 * @version 2015-07-28 14:00:39
 */
public class CodeTable {

	@NotBlank(message="码表类别编号不能为空")
	@Length(min=0, max=40, message="码表类别编号长度必须在0-40之间")
	private String c_type;

	@NotBlank(message="码表键值不能为空")
	@Length(min=0, max=40, message="码表键值长度必须在0-40之间")
	private String c_key;

	@NotBlank(message="码表内容不能为空")
	@Length(min=0, max=200, message="码表内容长度必须在0-200之间")
	private String c_value;

	@NotNull(message="排序号不能为空")
	private int sort;

	public String getC_type() {
		return c_type;
	}

	public void setC_type(String c_type) {
		this.c_type = c_type;
	}

	public String getC_key() {
		return c_key;
	}

	public void setC_key(String c_key) {
		this.c_key = c_key;
	}

	public String getC_value() {
		return c_value;
	}

	public void setC_value(String c_value) {
		this.c_value = c_value;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

}