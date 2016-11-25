package com.dlshouwen.core.base.extra.advancequery.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 高级查询
 * @author 大连首闻科技有限公司
 * @version 2013-08-09 10:45:07
 */
public class AdvanceQuery {

	private String advance_query_id;

	@NotEmpty(message="高级查询方案名称不能为空")
	@Length(min=0, max=40, message="高级查询方案名称长度仅限40个字符")
	private String advance_query_name;

	@NotEmpty(message="功能编号不能为空")
	@Length(min=0, max=40, message="功能编号长度仅限40个字符")
	private String function_code;

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
	
	@Valid
	private List<AdvanceQueryCondition> advanceQueryConditionList = new ArrayList<AdvanceQueryCondition>();
	
	@Valid
	private List<AdvanceQuerySort> advanceQuerySortList = new ArrayList<AdvanceQuerySort>();

	public String getAdvance_query_id() {
		return advance_query_id;
	}

	public void setAdvance_query_id(String advance_query_id) {
		this.advance_query_id = advance_query_id;
	}

	public String getAdvance_query_name() {
		return advance_query_name;
	}

	public void setAdvance_query_name(String advance_query_name) {
		this.advance_query_name = advance_query_name;
	}

	public String getFunction_code() {
		return function_code;
	}

	public void setFunction_code(String function_code) {
		this.function_code = function_code;
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

	public List<AdvanceQueryCondition> getAdvanceQueryConditionList() {
		return advanceQueryConditionList;
	}

	public void setAdvanceQueryConditionList(List<AdvanceQueryCondition> advanceQueryConditionList) {
		this.advanceQueryConditionList = advanceQueryConditionList;
	}

	public List<AdvanceQuerySort> getAdvanceQuerySortList() {
		return advanceQuerySortList;
	}

	public void setAdvanceQuerySortList(List<AdvanceQuerySort> advanceQuerySortList) {
		this.advanceQuerySortList = advanceQuerySortList;
	}

}