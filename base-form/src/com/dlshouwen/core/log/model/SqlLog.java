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
 * SQL日志
 * @author 大连首闻科技有限公司
 * @version 2015-07-28 14:31:06
 */
public class SqlLog {

	@NotBlank(message="日志编号不能为空")
	@Length(min=0, max=40, message="日志编号长度必须在0-40之间")
	private String log_id;

	@NotBlank(message="调用来源不能为空")
	@Length(min=0, max=200, message="调用来源长度必须在0-200之间")
	private String call_source;

	@NotNull(message="行号不能为空")
	private int line_no;

	@NotBlank(message="操作sql不能为空")
	private String operation_sql;

	@NotBlank(message="参数不能为空")
	private String params;

	@NotBlank(message="执行结果不能为空")
	@Length(min=0, max=2, message="执行结果长度必须在0-2之间")
	private String call_result;

	@Length(min=0, max=2000, message="错误原因长度必须在0-2000之间")
	private String error_reason;

	@NotBlank(message="执行类别不能为空")
	@Length(min=0, max=20, message="执行类别长度必须在0-20之间")
	private String execute_type;

	@NotBlank(message="结果类别不能为空")
	@Length(min=0, max=80, message="结果类别长度必须在0-80之间")
	private String result_type;

	private Timestamp start_time;

	private Timestamp end_time;

	@NotNull(message="耗时不能为空")
	private int cost;

	@NotBlank(message="操作人不能为空")
	@Length(min=0, max=40, message="操作人长度必须在0-40之间")
	private String operator;

	@NotBlank(message="操作人名称不能为空")
	@Length(min=0, max=40, message="操作人名称长度必须在0-40之间")
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

	public String getCall_source() {
		return call_source;
	}

	public void setCall_source(String call_source) {
		this.call_source = call_source;
	}

	public int getLine_no() {
		return line_no;
	}

	public void setLine_no(int line_no) {
		this.line_no = line_no;
	}

	public String getOperation_sql() {
		return operation_sql;
	}

	public void setOperation_sql(String operation_sql) {
		this.operation_sql = operation_sql;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getCall_result() {
		return call_result;
	}

	public void setCall_result(String call_result) {
		this.call_result = call_result;
	}

	public String getError_reason() {
		return error_reason;
	}

	public void setError_reason(String error_reason) {
		this.error_reason = error_reason;
	}

	public String getExecute_type() {
		return execute_type;
	}

	public void setExecute_type(String execute_type) {
		this.execute_type = execute_type;
	}

	public String getResult_type() {
		return result_type;
	}

	public void setResult_type(String result_type) {
		this.result_type = result_type;
	}

	public Timestamp getStart_time() {
		return start_time;
	}

	public void setStart_time(Timestamp start_time) {
		this.start_time = start_time;
	}

	public Timestamp getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Timestamp end_time) {
		this.end_time = end_time;
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
	
	public SqlLog(){
	}
	
	/**
	 * 初始构造方法
	 * @param operation_sql
	 * @param params
	 * @param success
	 * @param execute_type
	 * @param result_type
	 */
	public SqlLog(String operation_sql, String params, String call_result, 
			String execute_type, String result_type) {
		super();
		this.call_result = call_result;
		this.operation_sql = operation_sql;
		this.params = params;
		this.execute_type = execute_type;
		this.result_type = result_type;
	}
	
	@Override
	public String toString(){
		SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer result = new StringBuffer();
		result.append("执行SQL：").append(this.getOperation_sql()).append("; ");
		result.append("执行参数：").append(this.getParams()).append("; ");
		result.append("执行结果：").append(CodeTableUtils.getValue(CONFIG.SERVLET_CONTEXT, "call_result", this.getCall_result())).append("; ");
		result.append("错误原因：").append("0".equals(this.getCall_result())?this.getError_reason():"无错误").append("; ");
		result.append("执行类别：").append(this.getExecute_type()).append("; ");
		result.append("结果类别：").append(this.getResult_type()).append("; ");
		result.append("调用来源：").append(this.getCall_source()).append("; ");
		result.append("来源文档行号：").append(this.getLine_no()).append("; ");
		result.append("执行开始时间：").append(timeFormat.format(this.getStart_time())).append("; ");
		result.append("执行结束时间：").append(timeFormat.format(this.getEnd_time())).append("; ");
		result.append("响应耗时：").append(this.getCost()).append("; ");
		result.append("操作机IP：").append(this.getIp()).append("; ");
		result.append("输出时间：").append(DateUtils.getNowTimeHaveMS()).append("");
		return result.toString();
	}
	
//	===================================
//	辅助属性
//	===================================
	
	private String call_result_name;

	public String getCall_result_name() {
		return call_result_name;
	}

	public void setCall_result_name(String call_result_name) {
		this.call_result_name = call_result_name;
	}
	
}