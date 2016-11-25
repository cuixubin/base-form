package com.dlshouwen.core.base.extra.advancequery.model;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 高级查询条件
 * @author 大连首闻科技有限公司
 * @version 2015-07-30 16:40:02
 */
public class AdvanceQueryCondition {

	@Length(min=0, max=40, message="条件内码长度必须在0-40之间")
	private String condition_id;

	@Length(min=0, max=40, message="查询方案内码长度必须在0-40之间")
	private String advance_query_id;

	@NotBlank(message="数据类别不能为空")
	@Length(min=0, max=10, message="数据类别长度必须在0-10之间")
	private String data_type;

	@Length(min=0, max=10, message="左括号长度必须在0-10之间")
	private String left_parentheses;

	@NotBlank(message="字段不能为空")
	@Length(min=0, max=20, message="字段长度必须在0-20之间")
	private String condition_field;

	@NotBlank(message="条件不能为空")
	@Length(min=0, max=2, message="条件长度必须在0-2之间")
	private String condition_type;

	@Length(min=0, max=200, message="值长度必须在0-200之间")
	private String condition_value;

	@Length(min=0, max=10, message="右括号长度必须在0-10之间")
	private String right_parentheses;

	@Length(min=0, max=2, message="逻辑长度必须在0-2之间")
	private String logic;

	@NotNull(message="排序码不能为空")
	private int sort;

	public String getCondition_id() {
		return condition_id;
	}

	public void setCondition_id(String condition_id) {
		this.condition_id = condition_id;
	}

	public String getAdvance_query_id() {
		return advance_query_id;
	}

	public void setAdvance_query_id(String advance_query_id) {
		this.advance_query_id = advance_query_id;
	}

	public String getData_type() {
		return data_type;
	}

	public void setData_type(String data_type) {
		this.data_type = data_type;
	}

	public String getLeft_parentheses() {
		return left_parentheses;
	}

	public void setLeft_parentheses(String left_parentheses) {
		this.left_parentheses = left_parentheses;
	}

	public String getCondition_field() {
		return condition_field;
	}

	public void setCondition_field(String condition_field) {
		this.condition_field = condition_field;
	}

	public String getCondition_type() {
		return condition_type;
	}

	public void setCondition_type(String condition_type) {
		this.condition_type = condition_type;
	}

	public String getCondition_value() {
		return condition_value;
	}

	public void setCondition_value(String condition_value) {
		this.condition_value = condition_value;
	}

	public String getRight_parentheses() {
		return right_parentheses;
	}

	public void setRight_parentheses(String right_parentheses) {
		this.right_parentheses = right_parentheses;
	}

	public String getLogic() {
		return logic;
	}

	public void setLogic(String logic) {
		this.logic = logic;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

}