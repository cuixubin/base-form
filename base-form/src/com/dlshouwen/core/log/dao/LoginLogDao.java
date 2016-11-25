package com.dlshouwen.core.log.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.stereotype.Component;

import com.dlshouwen.core.base.dao.BaseDao;
import com.dlshouwen.core.base.extra.grid.model.Condition;
import com.dlshouwen.core.base.extra.grid.model.Pager;
import com.dlshouwen.core.base.extra.grid.model.Sort;
import com.dlshouwen.core.base.extra.grid.utils.GridUtils;
import com.dlshouwen.core.base.extra.grid.utils.QueryUtils;
import com.dlshouwen.core.base.extra.spring.mapper.ClassRowMapper;
import com.dlshouwen.core.log.model.LoginLog;

/**
 * 登录日志统计
 * @author 大连首闻科技有限公司
 * @version 2014-11-10 13:50:10 932
 */
@Component("loginLogDao")
public class LoginLogDao extends BaseDao {

	/**
	 * 注入数据源
	 * @param dataSource 数据源对象
	 */
	@Resource(name="defaultDataSource")
	public void setDataSource(DataSource dataSource){
		super.setDataSource(dataSource);
	}
	
	/**
	 * 获取登录日志列表
	 * @param pager 分页对象
	 * @param request 请求对象
	 * @param response 响应对象
	 * @throws Exception 抛出全部异常
	 */
	public void getLoginLogList(Pager pager, HttpServletRequest request, HttpServletResponse response) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select l.* from core_login_log l order by l.login_time desc");
		GridUtils.queryForGrid(this, sql.toString(), pager, request, response);
	}
	
	/**
	 * 根据编号获取登录日志信息
	 * @param logId 日志编号
	 * @return 日志信息
	 * @throws Exception 抛出全部异常
	 */
	public LoginLog getLoginLogById(String logId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select l.*, ct1.c_value logout_type_name, ct2.c_value is_logout_name, ct3.c_value login_status_name ");
		sql.append("from core_login_log l ");
		sql.append("left join core_code_table ct1 on l.logout_type=ct1.c_key and ct1.c_type='logout_type' ");
		sql.append("left join core_code_table ct2 on l.is_logout=ct2.c_key and ct2.c_type='zero_one' ");
		sql.append("left join core_code_table ct3 on l.login_status=ct3.c_key and ct3.c_type='login_status' ");
		sql.append("where l.log_id=?");
		return this.queryForObject(sql.toString(), new ClassRowMapper<LoginLog>(LoginLog.class), logId);
	}
	
	/**
	 * 获取登录日志图表数据
	 * @param dataType 统计类别
	 * @param fastQueryParameters 快速查询参数
	 * @param advanceQueryConditions 高级查询条件
	 * @param advanceQuerySorts 高级查询排序
	 * @return 登录日志图表数据
	 * @throws Exception 抛出全部异常
	 */
	public List<Map<String, Object>> getChartDataList(String dataType, 
			Map<String, Object> fastQueryParameters, List<Condition> advanceQueryConditions, List<Sort> advanceQuerySorts) throws Exception {
		StringBuffer sql = new StringBuffer();
		List<Object> args = new ArrayList<Object>();
		if("dept".equals(dataType)){
			sql.append("select l.login_user_dept_name category_field_name, count(*) login_count ");
			sql.append("from (");
			sql.append("	select t.* ");
			sql.append("	from core_login_log t ");
			sql.append("	where 1=1 ");
			sql.append("	").append(QueryUtils.getFastQuerySql(fastQueryParameters, args));
			sql.append("	").append(QueryUtils.getAdvanceQueryConditionSql(advanceQueryConditions, args));
			sql.append("	").append(QueryUtils.getAdvanceQuerySortSql(advanceQuerySorts));
			sql.append(") l ");
			sql.append("group by l.login_user_dept_name");
		}
		if("login_user".equals(dataType)){
			sql.append("select l.login_user_name category_field_name, count(*) login_count ");
			sql.append("from (");
			sql.append("	select t.* ");
			sql.append("	from core_login_log t ");
			sql.append("	where 1=1 ");
			sql.append("	").append(QueryUtils.getFastQuerySql(fastQueryParameters, args));
			sql.append("	").append(QueryUtils.getAdvanceQueryConditionSql(advanceQueryConditions, args));
			sql.append("	").append(QueryUtils.getAdvanceQuerySortSql(advanceQuerySorts));
			sql.append(") l ");
			sql.append("group by l.login_user_name");
		}
		if("login_status".equals(dataType)){
			sql.append("select ct1.c_value category_field_name, count(*) login_count ");
			sql.append("from (");
			sql.append("	select t.* ");
			sql.append("	from core_login_log t ");
			sql.append("	where 1=1 ");
			sql.append("	").append(QueryUtils.getFastQuerySql(fastQueryParameters, args));
			sql.append("	").append(QueryUtils.getAdvanceQueryConditionSql(advanceQueryConditions, args));
			sql.append("	").append(QueryUtils.getAdvanceQuerySortSql(advanceQuerySorts));
			sql.append(") l ");
			sql.append("left join core_code_table ct1 on l.login_status=ct1.c_key and ct1.c_type='login_status' ");
			sql.append("group by l.login_status, ct1.c_value");
		}
		if("date".equals(dataType)){
			sql.append("select date_format(l.login_time, '%Y-%m-%d') category_field_name, count(*) login_count ");
			sql.append("from (");
			sql.append("	select t.* ");
			sql.append("	from core_login_log t ");
			sql.append("	where 1=1 ");
			sql.append("	").append(QueryUtils.getFastQuerySql(fastQueryParameters, args));
			sql.append("	").append(QueryUtils.getAdvanceQueryConditionSql(advanceQueryConditions, args));
			sql.append("	").append(QueryUtils.getAdvanceQuerySortSql(advanceQuerySorts));
			sql.append(") l ");
			sql.append("group by date_format(l.login_time, '%Y-%m-%d') order by date_format(l.login_time, '%Y-%m-%d')");
		}
		if("month".equals(dataType)){
			sql.append("select date_format(l.login_time, '%Y-%m') category_field_name, count(*) login_count ");
			sql.append("from (");
			sql.append("	select t.* ");
			sql.append("	from core_login_log t ");
			sql.append("	where 1=1 ");
			sql.append("	").append(QueryUtils.getFastQuerySql(fastQueryParameters, args));
			sql.append("	").append(QueryUtils.getAdvanceQueryConditionSql(advanceQueryConditions, args));
			sql.append("	").append(QueryUtils.getAdvanceQuerySortSql(advanceQuerySorts));
			sql.append(") l ");
			sql.append("group by date_format(l.login_time, '%Y-%m') order by date_format(l.login_time, '%Y-%m')");
		}
		if("year".equals(dataType)){
			sql.append("select date_format(l.login_time, '%Y') category_field_name, count(*) login_count ");
			sql.append("from (");
			sql.append("	select t.* ");
			sql.append("	from core_login_log t ");
			sql.append("	where 1=1 ");
			sql.append("	").append(QueryUtils.getFastQuerySql(fastQueryParameters, args));
			sql.append("	").append(QueryUtils.getAdvanceQueryConditionSql(advanceQueryConditions, args));
			sql.append("	").append(QueryUtils.getAdvanceQuerySortSql(advanceQuerySorts));
			sql.append(") l ");
			sql.append("group by date_format(l.login_time, '%Y') order by date_format(l.login_time, '%Y')");
		}
		if("ip".equals(dataType)){
			sql.append("select l.ip category_field_name, count(*) login_count ");
			sql.append("from (");
			sql.append("	select t.* ");
			sql.append("	from core_login_log t ");
			sql.append("	where 1=1 ");
			sql.append("	").append(QueryUtils.getFastQuerySql(fastQueryParameters, args));
			sql.append("	").append(QueryUtils.getAdvanceQueryConditionSql(advanceQueryConditions, args));
			sql.append("	").append(QueryUtils.getAdvanceQuerySortSql(advanceQuerySorts));
			sql.append(") l ");
			sql.append("group by l.ip");
		}
		if("online".equals(dataType)){
			sql.append("(select '在线' category_field_name, count(*) login_count from (");
			sql.append("	select t.* ");
			sql.append("	from core_login_log t ");
			sql.append("	where 1=1 ");
			sql.append("	").append(QueryUtils.getFastQuerySql(fastQueryParameters, args));
			sql.append("	").append(QueryUtils.getAdvanceQueryConditionSql(advanceQueryConditions, args));
			sql.append("	").append(QueryUtils.getAdvanceQuerySortSql(advanceQuerySorts));
			sql.append(") l where l.is_logout='0' ");
//			sql.append(this.getFastQuerySql(params, args));
			sql.append("group by l.login_user_id) union ");
			sql.append("(select '离线' category_field_name, count(*) login_count from core_user u where u.user_id not in (");
			sql.append("select l.login_user_id from (");
			sql.append("	select t.* ");
			sql.append("	from core_login_log t ");
			sql.append("	where 1=1 ");
			sql.append("	").append(QueryUtils.getFastQuerySql(fastQueryParameters, args));
			sql.append("	").append(QueryUtils.getAdvanceQueryConditionSql(advanceQueryConditions, args));
			sql.append("	").append(QueryUtils.getAdvanceQuerySortSql(advanceQuerySorts));
			sql.append(") l where is_logout='0' ");
			sql.append(")) ");
		}
		if("logout_type".equals(dataType)){
			sql.append("(select '在线' category_field_name, count(*) login_count from (");
			sql.append("	select t.* ");
			sql.append("	from core_login_log t ");
			sql.append("	where 1=1 ");
			sql.append("	").append(QueryUtils.getFastQuerySql(fastQueryParameters, args));
			sql.append("	").append(QueryUtils.getAdvanceQueryConditionSql(advanceQueryConditions, args));
			sql.append("	").append(QueryUtils.getAdvanceQuerySortSql(advanceQuerySorts));
			sql.append(") l where l.logout_type is null ");
			sql.append(") union ");
			sql.append("(select ct1.c_value category_field_name, count(*) login_count from (");
			sql.append("	select t.* ");
			sql.append("	from core_login_log t ");
			sql.append("	where 1=1 ");
			sql.append("	").append(QueryUtils.getFastQuerySql(fastQueryParameters, args));
			sql.append("	").append(QueryUtils.getAdvanceQueryConditionSql(advanceQueryConditions, args));
			sql.append("	").append(QueryUtils.getAdvanceQuerySortSql(advanceQuerySorts));
			sql.append(") l ");
			sql.append("left join core_code_table ct1 on l.logout_type=ct1.c_key and ct1.c_type='logout_type' ");
			sql.append("where l.is_logout='1' and l.logout_type is not null ");
			sql.append("group by l.logout_type, ct1.c_value)");
		}
		return this.queryForList(sql.toString(), args.toArray());
	}

	/**
	 * 执行清空登录日志
	 * @param fastQueryParameters 快速查询参数
	 * @param advanceQueryConditions 高级查询条件
	 * @param advanceQuerySorts 高级查询排序
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int clearLoginLog(Map<String, Object> fastQueryParameters, List<Condition> advanceQueryConditions, List<Sort> advanceQuerySorts) throws Exception {
		StringBuffer sql = new StringBuffer();
		List<Object> args = new ArrayList<Object>();
		sql.append("delete from core_login_log where is_logout='1' ");
		sql.append(QueryUtils.getFastQuerySql(fastQueryParameters, args));
		sql.append(QueryUtils.getAdvanceQueryConditionSql(advanceQueryConditions, args));
		sql.append(QueryUtils.getAdvanceQuerySortSql(advanceQuerySorts));
		return this.update(sql.toString(), args.toArray());
	}

}