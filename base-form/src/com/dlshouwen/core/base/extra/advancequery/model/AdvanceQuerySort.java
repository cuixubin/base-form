package com.dlshouwen.core.base.extra.advancequery.model;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 高级查询排序
 * @author 大连首闻科技有限公司
 * @version 2015-07-30 16:40:02
 */
public class AdvanceQuerySort {

	@Length(min=0, max=40, message="排序内码长度必须在0-40之间")
	private String sort_id;

	@Length(min=0, max=40, message="查询方案内码长度必须在0-40之间")
	private String advance_query_id;

	@NotBlank(message="字段不能为空")
	@Length(min=0, max=20, message="字段长度必须在0-20之间")
	private String sort_field;

	@NotBlank(message="排序逻辑不能为空")
	@Length(min=0, max=2, message="排序逻辑长度必须在0-2之间")
	private String sort_logic;

	@NotNull(message="排序码不能为空")
	private int sort;

	public String getSort_id() {
		return sort_id;
	}

	public void setSort_id(String sort_id) {
		this.sort_id = sort_id;
	}

	public String getAdvance_query_id() {
		return advance_query_id;
	}

	public void setAdvance_query_id(String advance_query_id) {
		this.advance_query_id = advance_query_id;
	}

	public String getSort_field() {
		return sort_field;
	}

	public void setSort_field(String sort_field) {
		this.sort_field = sort_field;
	}

	public String getSort_logic() {
		return sort_logic;
	}

	public void setSort_logic(String sort_logic) {
		this.sort_logic = sort_logic;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

}