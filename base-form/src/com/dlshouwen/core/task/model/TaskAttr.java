package com.dlshouwen.core.task.model;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 任务参数
 * @author 大连首闻科技有限公司
 * @version 2013-08-17 12:13:03
 */
public class TaskAttr {

	private String attr_id;

	private String task_id;

	@NotBlank(message="参数SQL不能为空")
	@Length(min=0, max=2000, message="参数SQL长度必须在0-2000之间")
	private String attr_sql;

	@NotNull(message="排序码不能为空")
	private int sort;

	public String getAttr_id() {
		return attr_id;
	}

	public void setAttr_id(String attr_id) {
		this.attr_id = attr_id;
	}

	public String getTask_id() {
		return task_id;
	}

	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}

	public String getAttr_sql() {
		return attr_sql;
	}

	public void setAttr_sql(String attr_sql) {
		this.attr_sql = attr_sql;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

}