package com.dlshouwen.core.system.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 码表类别
 * @author 大连首闻科技有限公司
 * @version 2015-07-28 14:00:39
 */
public class CodeTableType {

	@NotBlank(message="码表类别编号不能为空")
	@Length(min=0, max=40, message="码表类别编号长度必须在0-40之间")
	private String c_type;

	@NotBlank(message="码表类别名称不能为空")
	@Length(min=0, max=40, message="码表类别名称长度必须在0-40之间")
	private String c_info;

	public String getC_type() {
		return c_type;
	}

	public void setC_type(String c_type) {
		this.c_type = c_type;
	}

	public String getC_info() {
		return c_info;
	}

	public void setC_info(String c_info) {
		this.c_info = c_info;
	}

}