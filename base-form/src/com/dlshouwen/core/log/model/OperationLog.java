package com.dlshouwen.core.log.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.dlshouwen.core.base.config.CONFIG;
import com.dlshouwen.core.base.utils.CodeTableUtils;
import com.dlshouwen.core.base.utils.DateUtils;

/**
 * 操作日志
 * @author 大连首闻科技有限公司
 * @version 2015-07-28 14:31:05
 */
public class OperationLog {

	@NotBlank(message="日志内码不能为空")
	@Length(min=0, max=40, message="日志内码长度必须在0-40之间")
	private String log_id;

	@NotBlank(message="操作地址不能为空")
	@Length(min=0, max=500, message="操作地址长度必须在0-500之间")
	private String operation_url;

	@NotBlank(message="操作类别不能为空")
	@Length(min=0, max=2, message="操作类别长度必须在0-2之间")
	private String operation_type;

	@NotBlank(message="操作结果不能为空")
	@Length(min=0, max=2, message="操作结果长度必须在0-2之间")
	private String operation_result;

	@Length(min=0, max=2000, message="错误原因长度必须在0-2000之间")
	private String error_reason;

	@NotBlank(message="操作说明不能为空")
	@Length(min=0, max=2000, message="操作说明长度必须在0-2000之间")
	private String operation_detail;

	private Timestamp response_start;

	private Timestamp response_end;

	@NotNull(message="耗时不能为空")
	private int cost;

	@NotBlank(message="操作人不能为空")
	@Length(min=0, max=40, message="操作人长度必须在0-40之间")
	private String operator;

	@NotBlank(message="操作人姓名不能为空")
	@Length(min=0, max=40, message="操作人姓名长度必须在0-40之间")
	private String operator_name;

	@NotBlank(message="操作人所属部门不能为空")
	@Length(min=0, max=40, message="操作人所属部门长度必须在0-40之间")
	private String operator_dept_id;

	@NotBlank(message="操作人所属部门名称不能为空")
	@Length(min=0, max=40, message="操作人所属部门名称长度必须在0-40之间")
	private String operator_dept_name;

	@NotBlank(message="ip地址不能为空")
	@Length(min=0, max=20, message="ip地址长度必须在0-20之间")
	private String ip;

	public String getLog_id() {
		return log_id;
	}

	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}

	public String getOperation_url() {
		return operation_url;
	}

	public void setOperation_url(String operation_url) {
		this.operation_url = operation_url;
	}

	public String getOperation_type() {
		return operation_type;
	}

	public void setOperation_type(String operation_type) {
		this.operation_type = operation_type;
	}

	public String getOperation_result() {
		return operation_result;
	}

	public void setOperation_result(String operation_result) {
		this.operation_result = operation_result;
	}

	public String getError_reason() {
		return error_reason;
	}

	public void setError_reason(String error_reason) {
		this.error_reason = error_reason;
	}

	public String getOperation_detail() {
		return operation_detail;
	}

	public void setOperation_detail(String operation_detail) {
		this.operation_detail = operation_detail;
	}

	public Timestamp getResponse_start() {
		return response_start;
	}

	public void setResponse_start(Timestamp response_start) {
		this.response_start = response_start;
	}

	public Timestamp getResponse_end() {
		return response_end;
	}

	public void setResponse_end(Timestamp response_end) {
		this.response_end = response_end;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperator_name() {
		return operator_name;
	}

	public void setOperator_name(String operator_name) {
		this.operator_name = operator_name;
	}

	public String getOperator_dept_id() {
		return operator_dept_id;
	}

	public void setOperator_dept_id(String operator_dept_id) {
		this.operator_dept_id = operator_dept_id;
	}

	public String getOperator_dept_name() {
		return operator_dept_name;
	}

	public void setOperator_dept_name(String operator_dept_name) {
		this.operator_dept_name = operator_dept_name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	@Override
	public String toString(){
		SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer result = new StringBuffer();
		result.append("操作地址：").append(this.getOperation_url()).append("; ");
		result.append("操作类型：").append(CodeTableUtils.getValue(CONFIG.SERVLET_CONTEXT, "operation_type", this.getOperation_type())).append("; ");
		result.append("操作结果：").append(CodeTableUtils.getValue(CONFIG.SERVLET_CONTEXT, "operation_result", this.getOperation_result())).append("; ");
		result.append("错误原因：").append(this.getError_reason()).append("; ");
		result.append("操作说明：").append(this.getOperation_detail()).append("; ");
		result.append("执行开始时间：").append(timeFormat.format(this.getResponse_start())).append("; ");
		result.append("执行结束时间：").append(timeFormat.format(this.getResponse_end())).append("; ");
		result.append("响应耗时：").append(this.getCost()).append("; ");
		result.append("操作机IP：").append(this.getIp()).append("; ");
		result.append("输出时间：").append(DateUtils.getNowTimeHaveMS()).append("");
		return result.toString();
	}
	
//	========================================================
//	辅助属性
//	========================================================
	
	private String operation_type_name;
	
	private String operation_result_name;

	public String getOperation_type_name() {
		return operation_type_name;
	}

	public void setOperation_type_name(String operation_type_name) {
		this.operation_type_name = operation_type_name;
	}

	public String getOperation_result_name() {
		return operation_result_name;
	}

	public void setOperation_result_name(String operation_result_name) {
		this.operation_result_name = operation_result_name;
	}
	
}