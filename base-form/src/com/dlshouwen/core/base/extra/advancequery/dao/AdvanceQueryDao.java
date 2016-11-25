package com.dlshouwen.core.base.extra.advancequery.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.dlshouwen.core.base.dao.BaseDao;
import com.dlshouwen.core.base.extra.advancequery.model.AdvanceQuery;
import com.dlshouwen.core.base.extra.advancequery.model.AdvanceQueryCondition;
import com.dlshouwen.core.base.extra.advancequery.model.AdvanceQuerySort;
import com.dlshouwen.core.base.extra.spring.mapper.ClassRowMapper;

/**
 * 高级查询
 * @author 大连首闻科技有限公司
 * @version 2013-8-8 17:15:11
 */
@Component("advanceQueryDao")
public class AdvanceQueryDao extends BaseDao {

	/**
	 * 注入数据源
	 * @param dataSource 数据源对象
	 */
	@Resource(name="defaultDataSource")
	public void setDataSource(DataSource dataSource){
		super.setDataSource(dataSource);
	}

	/**
	 * 查询所有的方案列表
	 * @param functionCode 功能代码
	 * @param userId 用户编号
	 * @param advance_query_is_filter_user 高级查询是否过滤登录用户
	 * @return 对应的高级查询方案列表
	 * @throws Exception 抛出全部异常
	 */
	public List<AdvanceQuery> getAdvanceQueryList(String functionCode, String userId, String advance_query_is_filter_user) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select aq.* from core_advance_query aq ");
		sql.append("where aq.function_code=? ");
		if("1".equals(advance_query_is_filter_user)){
			sql.append("and aq.creator=? ");
		}
		sql.append("order by aq.create_time");
		return this.queryForList(sql.toString(), new ClassRowMapper<AdvanceQuery>(AdvanceQuery.class), functionCode, userId);
	}

	/**
	 * 根据方案编号获取方案信息
	 * @param advanceQueryId 方案编号
	 * @return 方案信息
	 * @throws Exception 抛出全部异常
	 */
	public AdvanceQuery getAdvanceQuery(String advanceQueryId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select aq.* from core_advance_query aq where aq.advance_query_id=? ");
		return this.queryForObject(sql.toString(), new BeanPropertyRowMapper<AdvanceQuery>(AdvanceQuery.class), advanceQueryId);
	}

	/**
	 * 根据方案编号获取高级查询条件列表数据
	 * @param advanceQueryId 方案编号
	 * @return 高级查询列表数据
	 * @throws Exception 抛出全部异常
	 */
	public List<AdvanceQueryCondition> getAdvanceQueryConditionList(String advanceQueryId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select c.* from core_advance_query_condition c where c.advance_query_id=? order by c.sort");
		return this.queryForList(sql.toString(), new ClassRowMapper<AdvanceQueryCondition>(AdvanceQueryCondition.class), advanceQueryId);
	}
	
	/**
	 * 根据方案编号获取高级查询排序列表数据
	 * @param advanceQueryId 方案编号
	 * @return 高级查询列表数据
	 * @throws Exception 抛出全部异常
	 */
	public List<AdvanceQuerySort> getAdvanceQuerySortList(String advanceQueryId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select s.* from core_advance_query_sort s where s.advance_query_id=? order by s.sort");
		return this.queryForList(sql.toString(), new ClassRowMapper<AdvanceQuerySort>(AdvanceQuerySort.class), advanceQueryId);
	}

	/**
	 * 插入快速查询方案
	 * @param advanceQuery 快速查询方案对象
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int insertAdvanceQuery(AdvanceQuery advanceQuery) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into core_advance_query (advance_query_id, advance_query_name, function_code, remark, creator, create_time, editor, edit_time) ");
		sql.append("values (${advance_query_id }, ${advance_query_name }, ${function_code }, ${remark }, ${creator }, ${create_time }, ${editor }, ${edit_time })");
		return this.updateObject(sql.toString(), advanceQuery);
	}

	/**
	 * 插入快速查询条件数据
	 * @param advanceQueryCondition 快速查询条件数据对象
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int insertAdvanceQueryCondition(AdvanceQueryCondition advanceQueryCondition) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into core_advance_query_condition (condition_id, advance_query_id, data_type, ");
		sql.append("left_parentheses, condition_field, condition_type, condition_value, right_parentheses, logic, sort) ");
		sql.append("values (${condition_id }, ${advance_query_id }, ${data_type }, ");
		sql.append("${left_parentheses }, ${condition_field }, ${condition_type }, ${condition_value }, ${right_parentheses }, ${logic }, ${sort })");
		return this.updateObject(sql.toString(), advanceQueryCondition);
	}
	
	/**
	 * 插入快速查询排序数据
	 * @param advanceQuerySort 快速查询排序数据对象
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int insertAdvanceQuerySort(AdvanceQuerySort advanceQuerySort) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into core_advance_query_sort (sort_id, advance_query_id, sort_field, sort_logic, sort) ");
		sql.append("values (${sort_id }, ${advance_query_id }, ${sort_field }, ${sort_logic }, ${sort })");
		return this.updateObject(sql.toString(), advanceQuerySort);
	}

	/**
	 * 编辑快速查询方案
	 * @param advanceQuery 快速查询方案
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int updateAdvanceQuery(AdvanceQuery advanceQuery) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update core_advance_query set advance_query_name=${advance_query_name }, function_code=${function_code }, remark=${remark }, ");
		sql.append("editor=${editor }, edit_time=${edit_time } where advance_query_id=${advance_query_id }");
		return this.updateObject(sql.toString(), advanceQuery);
	}

	/**
	 * 删除快速查询方案
	 * @param advanceQueryId 方案编号
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int deleteAdvanceQuery(String advanceQueryId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from core_advance_query where advance_query_id=?");
		return this.update(sql.toString(), advanceQueryId);
	}

	/**
	 * 删除所有的快速查询条件记录
	 * @param advanceQueryId 查询方案编号
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int deleteAllAdvanceQueryCondition(String advanceQueryId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from core_advance_query_condition where advance_query_id=?");
		return this.update(sql.toString(), advanceQueryId);
	}
	
	/**
	 * 删除所有的快速查询排序记录
	 * @param advanceQueryId 查询方案编号
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int deleteAllAdvanceQuerySort(String advanceQueryId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from core_advance_query_sort where advance_query_id=?");
		return this.update(sql.toString(), advanceQueryId);
	}
	
	/**
	 * 复制快速查询方案
	 * @param advanceQueryId 方案编号
	 * @param newAdvanceQueryId 新的方案编号
	 * @param creator 创建人
	 * @param createTime 创建时间
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int copyAdvanceQuery(String advanceQueryId, String newAdvanceQueryId, 
			String creator, Date createTime, String editor, Date editTime) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into core_advance_query (advance_query_id, advance_query_name, ");
		sql.append("function_code, remark, creator, create_time, editor, edit_time) ");
		sql.append("(select ?, concat(advance_query_name, '_副本'), function_code, remark, ?, ?, ?, ? ");
		sql.append("from core_advance_query where advance_query_id=?)");
		return this.update(sql.toString(), newAdvanceQueryId, creator, createTime, editor, editTime, advanceQueryId);
	}

	/**
	 * 复制快速查询条件数据
	 * @param advanceQueryConditionId 查询编号
	 * @param newAdvanceQueryConditionId 新的查询编号
	 * @param newAdvanceQueryId 新的方案编号
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int copyAdvanceQueryCondition(String advanceQueryConditionId, String newAdvanceQueryConditionId,
			String newAdvanceQueryId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into core_advance_query_condition (condition_id, advance_query_id, data_type, ");
		sql.append("left_parentheses, condition_field, condition_type, condition_value, right_parentheses, logic, sort) ");
		sql.append("(select ?, ?, data_type, left_parentheses, condition_field, condition_type, condition_value, ");
		sql.append("right_parentheses, logic, sort from core_advance_query_condition where condition_id=?)");
		return this.update(sql.toString(), newAdvanceQueryConditionId, newAdvanceQueryId, advanceQueryConditionId);
	}
	
	/**
	 * 复制快速查询排序数据
	 * @param advanceQuerySortId 查询编号
	 * @param newAdvanceQuerySortId 新的查询编号
	 * @param newAdvanceQueryId 新的方案编号
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int copyAdvanceQuerySort(String advanceQuerySortId, String newAdvanceQuerySortId,
			String newAdvanceQueryId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into core_advance_query_sort (sort_id, advance_query_id, sort_field, sort_logic, sort) ");
		sql.append("(select ?, ?, sort_field, sort_logic, sort from core_advance_query_sort where sort_id=?)");
		return this.update(sql.toString(), newAdvanceQuerySortId, newAdvanceQueryId, advanceQuerySortId);
	}

}
