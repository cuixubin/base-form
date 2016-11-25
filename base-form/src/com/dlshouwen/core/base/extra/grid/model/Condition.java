package com.dlshouwen.core.base.extra.grid.model;

public class Condition {
	
	/**
	 * 左括号
	 */
	private String left_parentheses;
	
	/**
	 * 字段名称
	 */
	private String condition_field;
	
	/**
	 * 条件
	 */
	private String condition_type;
	
	/**
	 * 值
	 */
	private String condition_value;
	
	/**
	 * 右括号
	 */
	private String right_parentheses;
	
	/**
	 * 查询逻辑
	 */
	private String logic;

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

}
