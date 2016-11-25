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
import com.dlshouwen.core.log.model.SqlLog;

/**
 * SQL日志统计
 * @author 大连首闻科技有限公司
 * @version 2014-11-10 13:50:10 942
 */
@Component("sqlLogDao")
public class SqlLogDao extends BaseDao {

	/**
	 * 注入数据源
	 * @param dataSource 数据源对象
	 */
	@Resource(name="defaultDataSource")
	public void setDataSource(DataSource dataSource){
		super.setDataSource(dataSource);
	}
	
	/**
	 * 获取SQL日志列表
	 * @param pager 分页对象
	 * @param request 请求对象
	 * @param response 响应对象
	 * @throws Exception 抛出全部异常
	 */
	public void getSqlLogList(Pager pager, HttpServletRequest request, HttpServletResponse response) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select l.* from core_sql_log l order by l.start_time desc ");
		GridUtils.queryForGrid(this, sql.toString(), pager, request, response);
	}
	
	/**
	 * 根据编号获取SQL日志信息
	 * @param logId 日志编号
	 * @return 日志信息
	 * @throws Exception 抛出全部异常
	 */
	public SqlLog getSqlLogById(String logId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select l.*, ct1.c_value call_result_name ");
		sql.append("from core_sql_log l ");
		sql.append("left join core_code_table ct1 on l.call_result=ct1.c_key and ct1.c_type='call_result' ");
		sql.append("where l.log_id=?");
		return this.queryForObject(sql.toString(), new ClassRowMapper<SqlLog>(SqlLog.class), logId);
	}
	
	/**
	 * 获取SQL日志图表数据
	 * @param dataType 统计类别
	 * @param fastQueryParameters 快速查询参数
	 * @param advanceQueryConditions 高级查询条件
	 * @param advanceQuerySorts 高级查询排序
	 * @return SQL日志图表数据
	 * @throws Exception 抛出全部异常
	 */
	public List<Map<String, Object>> getChartDataList(String dataType,
			Map<String, Object> fastQueryParameters, List<Condition> advanceQueryConditions, List<Sort> advanceQuerySorts) throws Exception {
		StringBuffer sql = new StringBuffer();
		List<Object> args = new ArrayList<Object>();
		if("dept".equals(dataType)){
			sql.append("select l.operator_dept_name category_field_name, count(*) sql_count ");
			sql.append("from (");
			sql.append("	select t.* ");
			sql.append("	from core_sql_log t ");
			sql.append("	where 1=1 ");
			sql.append("	").append(QueryUtils.getFastQuerySql(fastQueryParameters, args));
			sql.append("	").append(QueryUtils.getAdvanceQueryConditionSql(advanceQueryConditions, args));
			sql.append("	").append(QueryUtils.getAdvanceQuerySortSql(advanceQuerySorts));
			sql.append(") l ");
			sql.append("group by l.operator_dept_name");
		}
		if("operator".equals(dataType)){
			sql.append("select l.operator_name category_field_name, count(*) sql_count ");
			sql.append("from (");
			sql.append("	select t.* ");
			sql.append("	from core_sql_log t ");
			sql.append("	where 1=1 ");
			sql.append("	").append(QueryUtils.getFastQuerySql(fastQueryParameters, args));
			sql.append("	").append(QueryUtils.getAdvanceQueryConditionSql(advanceQueryConditions, args));
			sql.append("	").append(QueryUtils.getAdvanceQuerySortSql(advanceQuerySorts));
			sql.append(") l ");
			sql.append("group by l.operator_name");
		}
		if("ip".equals(dataType)){
			sql.append("select l.ip category_field_name, count(*) sql_count ");
			sql.append("from (");
			sql.append("	select t.* ");
			sql.append("	from core_sql_log t ");
			sql.append("	where 1=1 ");
			sql.append("	").append(QueryUtils.getFastQuerySql(fastQueryParameters, args));
			sql.append("	").append(QueryUtils.getAdvanceQueryConditionSql(advanceQueryConditions, args));
			sql.append("	").append(QueryUtils.getAdvanceQuerySortSql(advanceQuerySorts));
			sql.append(") l ");
			sql.append("group by l.ip");
		}
		if("cost".equals(dataType)){
			sql.append("select '0~100毫秒' category_field_name, count(*) sql_count, '1' as num from (");
			sql.append("	select t.* ");
			sql.append("	from core_sql_log t ");
			sql.append("	where 1=1 ");
			sql.append("	").append(QueryUtils.getFastQuerySql(fastQueryParameters, args));
			sql.append("	").append(QueryUtils.getAdvanceQueryConditionSql(advanceQueryConditions, args));
			sql.append("	").append(QueryUtils.getAdvanceQuerySortSql(advanceQuerySorts));
			sql.append(") l1 ");
			sql.append("where l1.cost <=100 ");
			sql.append("union ");
			sql.append("select '100~500毫秒' category_field_name, count(*)sql_count, '2' as num  from (");
			sql.append("	select t.* ");
			sql.append("	from core_sql_log t ");
			sql.append("	where 1=1 ");
			sql.append("	").append(QueryUtils.getFastQuerySql(fastQueryParameters, args));
			sql.append("	").append(QueryUtils.getAdvanceQueryConditionSql(advanceQueryConditions, args));
			sql.append("	").append(QueryUtils.getAdvanceQuerySortSql(advanceQuerySorts));
			sql.append(") l2 ");
			sql.append("where l2.cost>100 and l2.cost<=500 ");
			sql.append("union ");
			sql.append("select '500毫秒~1秒' category_field_name, count(*)sql_count, '3' as num  from (");
			sql.append("	select t.* ");
			sql.append("	from core_sql_log t ");
			sql.append("	where 1=1 ");
			sql.append("	").append(QueryUtils.getFastQuerySql(fastQueryParameters, args));
			sql.append("	").append(QueryUtils.getAdvanceQueryConditionSql(advanceQueryConditions, args));
			sql.append("	").append(QueryUtils.getAdvanceQuerySortSql(advanceQuerySorts));
			sql.append(") l3 ");
			sql.append("where l3.cost>500 and l3.cost<=1000 ");
			sql.append("union ");
			sql.append("select '1秒~5秒' category_field_name, count(*)sql_count, '4' as num  from (");
			sql.append("	select t.* ");
			sql.append("	from core_sql_log t ");
			sql.append("	where 1=1 ");
			sql.append("	").append(QueryUtils.getFastQuerySql(fastQueryParameters, args));
			sql.append("	").append(QueryUtils.getAdvanceQueryConditionSql(advanceQueryConditions, args));
			sql.append("	").append(QueryUtils.getAdvanceQuerySortSql(advanceQuerySorts));
			sql.append(") l4 ");
			sql.append("where l4.cost>1000 and l4.cost<=5000 ");
			sql.append("union ");
			sql.append("select '5秒以上' category_field_name, count(*)sql_count, '5' as num  from (");
			sql.append("	select t.* ");
			sql.append("	from core_sql_log t ");
			sql.append("	where 1=1 ");
			sql.append("	").append(QueryUtils.getFastQuerySql(fastQueryParameters, args));
			sql.append("	").append(QueryUtils.getAdvanceQueryConditionSql(advanceQueryConditions, args));
			sql.append("	").append(QueryUtils.getAdvanceQuerySortSql(advanceQuerySorts));
			sql.append(") l5 ");
			sql.append("where l5.cost>5000 ");
		}
		if("call_result".equals(dataType)){
			sql.append("select ct1.c_value category_field_name, count(*) sql_count ");
			sql.append("from (");
			sql.append("	select t.* ");
			sql.append("	from core_sql_log t ");
			sql.append("	where 1=1 ");
			sql.append("	").append(QueryUtils.getFastQuerySql(fastQueryParameters, args));
			sql.append("	").append(QueryUtils.getAdvanceQueryConditionSql(advanceQueryConditions, args));
			sql.append("	").append(QueryUtils.getAdvanceQuerySortSql(advanceQuerySorts));
			sql.append(") l ");
			sql.append("left join core_code_table ct1 on l.call_result=ct1.c_key and ct1.c_type='zero_one' ");
			sql.append("group by l.call_result, ct1.c_value ");
		}
		if("date".equals(dataType)){
			sql.append("select date_format(l.start_time, '%Y-%m-%d') category_field_name, count(*) sql_count ");
			sql.append("from (");
			sql.append("	select t.* ");
			sql.append("	from core_sql_log t ");
			sql.append("	where 1=1 ");
			sql.append("	").append(QueryUtils.getFastQuerySql(fastQueryParameters, args));
			sql.append("	").append(QueryUtils.getAdvanceQueryConditionSql(advanceQueryConditions, args));
			sql.append("	").append(QueryUtils.getAdvanceQuerySortSql(advanceQuerySorts));
			sql.append(") l ");
			sql.append("group by date_format(l.start_time, '%Y-%m-%d') order by date_format(l.start_time, '%Y-%m-%d')");
		}
		if("month".equals(dataType)){
			sql.append("select date_format(l.start_time, '%Y-%m') category_field_name, count(*) sql_count ");
			sql.append("from (");
			sql.append("	select t.* ");
			sql.append("	from core_sql_log t ");
			sql.append("	where 1=1 ");
			sql.append("	").append(QueryUtils.getFastQuerySql(fastQueryParameters, args));
			sql.append("	").append(QueryUtils.getAdvanceQueryConditionSql(advanceQueryConditions, args));
			sql.append("	").append(QueryUtils.getAdvanceQuerySortSql(advanceQuerySorts));
			sql.append(") l ");
			sql.append("group by date_format(l.start_time, '%Y-%m') order by date_format(l.start_time, '%Y-%m')");
		}
		if("year".equals(dataType)){
			sql.append("select date_format(l.start_time, '%Y') category_field_name, count(*) sql_count ");
			sql.append("from (");
			sql.append("	select t.* ");
			sql.append("	from core_sql_log t ");
			sql.append("	where 1=1 ");
			sql.append("	").append(QueryUtils.getFastQuerySql(fastQueryParameters, args));
			sql.append("	").append(QueryUtils.getAdvanceQueryConditionSql(advanceQueryConditions, args));
			sql.append("	").append(QueryUtils.getAdvanceQuerySortSql(advanceQuerySorts));
			sql.append(") l ");
			sql.append("group by date_format(l.start_time, '%Y') order by date_format(l.start_time, '%Y')");
		}
		return this.queryForList(sql.toString(), args.toArray());
	}
	
	/**
	 * 执行清空SQL日志
	 * @param fastQueryParameters 快速查询参数
	 * @param advanceQueryConditions 高级查询条件
	 * @param advanceQuerySorts 高级查询排序
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int clearSqlLog(Map<String, Object> fastQueryParameters, List<Condition> advanceQueryConditions, List<Sort> advanceQuerySorts) throws Exception {
		StringBuffer sql = new StringBuffer();
		List<Object> args = new ArrayList<Object>();
		sql.append("delete from core_sql_log where 1=1 ");
		sql.append(QueryUtils.getFastQuerySql(fastQueryParameters, args));
		sql.append(QueryUtils.getAdvanceQueryConditionSql(advanceQueryConditions, args));
		sql.append(QueryUtils.getAdvanceQuerySortSql(advanceQuerySorts));
		return this.update(sql.toString(), args.toArray());
	}

}