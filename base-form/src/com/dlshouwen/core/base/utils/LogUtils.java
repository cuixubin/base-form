package com.dlshouwen.core.base.utils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.dlshouwen.core.base.config.CONFIG;
import com.dlshouwen.core.base.dao.BaseDao;
import com.dlshouwen.core.base.local.ProjectSessionThreadLocal;
import com.dlshouwen.core.base.local.SessionUserThreadLocal;
import com.dlshouwen.core.base.model.ProjectSession;
import com.dlshouwen.core.base.model.SessionUser;
import com.dlshouwen.core.log.model.LoginLog;
import com.dlshouwen.core.log.model.OperationLog;
import com.dlshouwen.core.log.model.SqlLog;

/**
 * 日志工具类
 * @author 大连首闻科技有限公司
 * @version 2014-11-10 10:26:59
 */
@SuppressWarnings("unchecked")
public class LogUtils {
	
	/**
	 * 记录登录日志
	 * <p>用于记录登录日志，登录日志由于包含某些登录控制是需要实时记录的。
	 * @param dao 数据操作对象基类
	 * @param loginLog 登录日志对象
	 * @throws Exception 抛出全部异常
	 */
	public static void insertLoginLog(BaseDao dao, LoginLog loginLog) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into core_login_log (log_id, session_id, login_user_id, login_user_name, login_user_dept_id, login_user_dept_name, login_time, ip, login_status, is_logout, logout_type, logout_time) ");
		sql.append("values (${log_id }, ${session_id }, ${login_user_id }, ${login_user_name }, ${login_user_dept_id }, ${login_user_dept_name }, ${login_time }, ${ip }, ${login_status }, ${is_logout }, ${logout_type }, ${logout_time })");
		try {
			dao.updateObjectNoLog(sql.toString(), loginLog);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Logger logger = Logger.getLogger(LoginLog.class);
			logger.info(loginLog.toString());
		}
	}
	
	/**
	 * 更新登录日志
	 * <p>用于更新登录日志，该操作在用户退出、Session失效、服务器启动处理未退出用户记录时执行
	 * @param dao 数据操作对象基类
	 * @param loginLog 登录日志对象
	 * @throws Exception 抛出全部异常
	 */
	public static void updateLoginLog(BaseDao dao, LoginLog loginLog) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update core_login_log set is_logout=${is_logout }, logout_type=${logout_type }, logout_time=${logout_time } ");
		sql.append("where log_id=${log_id }");
		try {
			dao.updateObjectNoLog(sql.toString(), loginLog);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Logger logger = Logger.getLogger(LoginLog.class);
			logger.info(loginLog.toString());
		}
	}
	
	/**
	 * 记录SQL日志
	 * <p>用于记录SQL日志，SQL日志缓存到Application域中，当缓存数量达到系统设置的最大值时批量向数据库中插入，提高执行效率
	 * @param dao 数据操作对象基类
	 * @param sqlLog SQL日志对象
	 * @throws Exception 抛出全部异常
	 */
	public synchronized static void insertSqlLog(BaseDao dao, SqlLog sqlLog) throws Exception {
//		获取本地线程缓存信息
		SessionUser sessionUser = SessionUserThreadLocal.getUser();
//		设置LogId、操作人、操作时间、操作IP
		sqlLog.setLog_id(new GUID().toString());
		if(sessionUser!=null){
			sqlLog.setOperator(sessionUser.getUser_id());
			sqlLog.setOperator_name(sessionUser.getUser_name());
			sqlLog.setOperator_dept_id(sessionUser.getDept_id());
			sqlLog.setOperator_dept_name(sessionUser.getDept_name());
			sqlLog.setIp(sessionUser.getIp());
		}else{
			sqlLog.setOperator("login / outter spaces");
			sqlLog.setOperator_name("登录/外网访问");
			sqlLog.setOperator_dept_id("login not complete");
			sqlLog.setOperator_dept_name("未登录完成");
//			获取本地线程中的Session信息
			ProjectSession projectSession = ProjectSessionThreadLocal.getSession();
			if(projectSession!=null){
				sqlLog.setIp(projectSession.getIp());
			}else{
				sqlLog.setIp("not access data");
			}
		}
//		设置耗时
		long start_time = sqlLog.getStart_time().getTime();
		long end_time = sqlLog.getEnd_time().getTime();
		sqlLog.setCost((int)(end_time-start_time));
//		获取调用位置
		String callSource = "unknow call source";
		int lineNo = -1;
		StackTraceElement[] stack = (new Throwable()).getStackTrace();
		if (stack != null) {
			for(int i=0; i<stack.length; i++){
				callSource = stack[i].getClassName();
				lineNo = stack[i].getLineNumber();
				if (!callSource.endsWith("com.dlshouwen.core.base.dao.BaseDao")
						&& !callSource.endsWith("org.springframework.jdbc.core.JdbcTemplate")
						&& !callSource.endsWith("com.dlshouwen.core.base.utils.LogUtils")
						&& !callSource.endsWith("com.dlshouwen.core.base.model.SqlLogger")) {
					break;
				}
			}
		}
		sqlLog.setCall_source(callSource);
		sqlLog.setLine_no(Integer.valueOf(lineNo));
//		获取SQL日志的缓冲数量
		String sql_log_buffer_size = AttributeUtils.getAttributeContent(CONFIG.SERVLET_CONTEXT, "sql_log_buffer_size");
		int bufferSize = Integer.parseInt(sql_log_buffer_size);
//		获取当前Application中SQL日志的内容
		List<SqlLog> sqlLogList = (List<SqlLog>)CONFIG.SERVLET_CONTEXT.getAttribute(CONFIG.SQL_LOG_APN);
//		判断是否为空，如果为空则重新处理
		if(sqlLogList==null){
			sqlLogList = new ArrayList<SqlLog>();
		}
//		将登录日志计入到Application中
		sqlLogList.add(sqlLog);
//		如果缓冲区超限则进行批量存储
		if(sqlLogList.size()>=bufferSize){
			try {
//				执行批量存储
				StringBuffer sql = new StringBuffer();
				sql.append("insert into core_sql_log (log_id, call_source, line_no, operation_sql, params, call_result, error_reason, execute_type, result_type, start_time, end_time, cost, operator, operator_name, operator_dept_id, operator_dept_name, ip) ");
				sql.append("values (${log_id }, ${call_source }, ${line_no }, ${operation_sql }, ${params }, ${call_result }, ${error_reason }, ${execute_type }, ${result_type }, ${start_time }, ${end_time }, ${cost }, ${operator }, ${operator_name }, ${operator_dept_id }, ${operator_dept_name }, ${ip })");
				List<Object[]> batchArgs = new ArrayList<Object[]>();
				for(SqlLog _sqlLog : sqlLogList){
					batchArgs.add(SqlUtils.getObjectArgs(sql.toString(), _sqlLog).toArray());
				}
				dao.batchUpdateNoLog(SqlUtils.getObjectSql(sql.toString()), batchArgs);
//				执行SQL日志后续操作
				String afterExecuteSql = AttributeUtils.getAttributeContent(CONFIG.SERVLET_CONTEXT, "sql_log_after_execute");
				dao.updateNoLog(afterExecuteSql);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				sqlLogList = new ArrayList<SqlLog>();
			}
		}
//		记录LOG
		Logger logger = Logger.getLogger(SqlLog.class);
		logger.info(sqlLog.toString());
//		重新设置
		CONFIG.SERVLET_CONTEXT.setAttribute(CONFIG.SQL_LOG_APN, sqlLogList);
	}
	
	/**
	 * 更新操作日志
	 * <p>用于更新操作日志，操作日志原类在SessionFilter中进行实例化，并传入到request请求对象中，
	 * 在业务方法中可通过此方法进行追加操作的详细描述信息
	 * @param operationLog 操作日志对象
	 * @param operationType 操作类别，可通过OperationType类调用静态属性，其中：UNKNOW-0-未知、VISIT-1-访问、SEARCH-2-查询、INSERT-3-新增、UPDATE-4-更新、DELETE-5-删除、LOGIN-6-登录、LOGOUT-7-登出
	 * @param operationDetail 操作详细信息
	 * @throws Exception 抛出全部异常
	 */
	public static void updateOperationLog(HttpServletRequest request, 
			String operationType, String operationDetail) throws Exception {
		OperationLog operationLog = (OperationLog)request.getAttribute(CONFIG.OPERATION_LOG);
		if(operationLog==null){
			return;
		}
		operationLog.setOperation_type(operationType);
		operationLog.setOperation_detail(operationDetail);
		request.setAttribute(CONFIG.OPERATION_LOG, operationLog);
	}
	
	/**
	 * 记录操作日志
	 * <p>用于记录操作日志，操作日志缓存到Application域中，当缓存数量达到系统设置的最大值时批量向数据库中插入，提高执行效率
	 * @param dao 数据操作对象基类
	 * @param operationLog 操作日志对象
	 * @throws Exception 抛出全部异常
	 */
	public synchronized static void insertOperationLog(BaseDao dao, OperationLog operationLog) throws Exception {
//		获取操作日志的缓冲数量
		String operation_log_buffer_size = AttributeUtils.getAttributeContent(CONFIG.SERVLET_CONTEXT, "operation_log_buffer_size");
		int bufferSize = Integer.parseInt(operation_log_buffer_size);
//		获取当前Application中操作日志的内容
		List<OperationLog> operationLogList = (List<OperationLog>)CONFIG.SERVLET_CONTEXT.getAttribute(CONFIG.OPERATION_LOG_APN);
//		判断是否为空，如果为空则重新处理
		if(operationLogList==null){
			operationLogList = new ArrayList<OperationLog>();
		}
//		将登录日志计入到Application中
		operationLogList.add(operationLog);
//		如果缓冲区超限则进行批量存储
		if(operationLogList.size()>=bufferSize){
			try {
//				执行批量存储
				StringBuffer sql = new StringBuffer();
				sql.append("insert into core_operation_log (log_id, operation_url, operation_type, operation_result, error_reason, ");
				sql.append("operation_detail, response_start, response_end, cost, operator, operator_name, operator_dept_id, operator_dept_name, ip) ");
				sql.append("values (${log_id }, ${operation_url }, ${operation_type }, ${operation_result }, ${error_reason }, ");
				sql.append("${operation_detail }, ${response_start }, ${response_end }, ${cost }, ${operator }, ${operator_name }, ${operator_dept_id }, ${operator_dept_name }, ${ip })");
				List<Object[]> batchArgs = new ArrayList<Object[]>();
				for(OperationLog _operationLog : operationLogList){
					batchArgs.add(SqlUtils.getObjectArgs(sql.toString(), _operationLog).toArray());
				}
				dao.batchUpdateNoLog(SqlUtils.getObjectSql(sql.toString()), batchArgs);
//				执行操作日志后续操作
				String afterExecuteSql = AttributeUtils.getAttributeContent(CONFIG.SERVLET_CONTEXT, "operation_log_after_execute");
				dao.updateNoLog(afterExecuteSql);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				operationLogList = new ArrayList<OperationLog>();
			}
		}
//		记录LOG
		Logger logger = Logger.getLogger(OperationLog.class);
		logger.info(operationLog.toString());
//		重新设置
		CONFIG.SERVLET_CONTEXT.setAttribute(CONFIG.OPERATION_LOG_APN, operationLogList);
	}
	
}
