package com.dlshouwen.core.tools.dao;

import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.stereotype.Component;

import com.dlshouwen.core.base.dao.BaseDao;
import com.dlshouwen.core.base.extra.spring.mapper.ClassRowMapper;
import com.dlshouwen.core.log.model.LoginLog;
import com.dlshouwen.core.log.model.OperationLog;
import com.dlshouwen.core.log.model.SqlLog;

/**
 * 系统监控
 * @author 大连首闻科技有限公司
 * @version 2013-08-17 08:04:29 320
 */
@Component("monitoringDao")
public class MonitoringDao extends BaseDao {

	/**
	 * 注入数据源
	 * @param dataSource 数据源对象
	 */
	@Resource(name="defaultDataSource")
	public void setDataSource(DataSource dataSource){
		super.setDataSource(dataSource);
	}
	
	/**
	 * 获取登录信息列表
	 * @param lastTime 上次获取时间
	 * @param nowTime 当前时间
	 * @return 登录信息列表
	 */
	public List<LoginLog> getLoginLogList(String lastTime, String nowTime) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (");
		sql.append("(select l.*, c.c_value logout_type_name, l.login_time donate_time, 'login' data_type ");
		sql.append("from core_login_log l ");
		sql.append("left join core_code_table c on l.logout_type=c.c_key and c.c_type='logout_type' ");
		sql.append("where l.is_logout<>'1' and l.login_time>=? and l.login_time<?) ");
		sql.append("union all ");
		sql.append("(select l.*, c.c_value logout_type_value, l.logout_time donate_time, 'logout' data_type ");
		sql.append("from core_login_log l ");
		sql.append("left join core_code_table c on l.logout_type=c.c_key and c.c_type='logout_type' ");
		sql.append("where l.is_logout='1' and l.logout_time>=? and l.logout_time<?) ");
		sql.append(") t order by t.donate_time");
		return this.queryForListNoLog(sql.toString(), new ClassRowMapper<LoginLog>(LoginLog.class), lastTime, nowTime, lastTime, nowTime);
	}

	/**
	 * 获取操作信息列表
	 * @param lastTime 上次获取时间
	 * @param nowTime 当前时间
	 * @return 操作信息列表
	 */
	public List<OperationLog> getOperationLogList(String lastTime, String nowTime) {
		StringBuffer sql = new StringBuffer();
		sql.append("select l.* from core_operation_log l ");
		sql.append("where l.response_start>=? and l.response_start<? ");
		sql.append("order by l.response_start desc");
		return this.queryForListNoLog(sql.toString(), new ClassRowMapper<OperationLog>(OperationLog.class), lastTime, nowTime);
	}
	
	/**
	 * 获取SQL信息列表
	 * @param lastTime 上次获取时间
	 * @param nowTime 当前时间
	 * @return SQL信息列表
	 */
	public List<SqlLog> getSqlLogList(String lastTime, String nowTime) {
		StringBuffer sql = new StringBuffer();
		sql.append("select l.* from core_sql_log l ");
		sql.append("where l.start_time>=? and l.start_time<? ");
		sql.append("order by l.start_time desc");
		return this.queryForListNoLog(sql.toString(), new ClassRowMapper<SqlLog>(SqlLog.class), lastTime, nowTime);
	}

}