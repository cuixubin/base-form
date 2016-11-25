package com.dlshouwen.core.base.extra.task.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.stereotype.Component;

import com.dlshouwen.core.base.dao.BaseDao;

/**
 * 任务监听类
 * @author 大连首闻科技有限公司
 * @version 2013-11-20 9:45:26
 */
@Component("taskListenerDao")
public class TaskListenerDao extends BaseDao {

	/**
	 * 注入数据源
	 * @param dataSource 数据源对象
	 */
	@Resource(name="defaultDataSource")
	public void setDataSource(DataSource dataSource){
		super.setDataSource(dataSource);
	}
	
	/**
	 * 获取当前用户的监听列表
	 * @param userId 用户编号
	 * @return 监听列表
	 * @throws Exception 抛出全部异常
	 */
	public List<Map<String, Object>> getLoginUserTaskList(String userId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select t.*, l.limit_name, l.url, uc.user_name creator_name, ue.user_name editor_name ");
		sql.append("from core_task t ");
		sql.append("left join core_user uc on t.creator=uc.user_id ");
		sql.append("left join core_user ue on t.editor=ue.user_id ");
		sql.append("left join core_limit l on t.limit_id=l.limit_id ");
		sql.append("where 1=1 ");
//		过滤已启用的
		sql.append("and t.status='1' ");
//		过滤已开始的
		sql.append("and t.timing_time<=now() ");
//		过滤永不过期或未过期的
		sql.append("and (t.is_never_overdue='1' or t.overdue_time>=now()) ");
//		过滤是所有用户或当前用户已有的
		sql.append("and (t.task_id in (select task_id from core_task_user where user_id=?) ");
		sql.append("or t.task_id in (select task_id from core_task_role where role_id in (select role_id from core_user_role where user_id=?)) ");
		sql.append("or t.is_all_user='1')");
		return this.queryForList(sql.toString(), userId, userId);
	}
	
	/**
	 * 根据任务编号获取任务信息
	 * @param taskId 任务编号
	 * @return 任务信息
	 * @throws Exception 抛出全部异常
	 */
	public Map<String, Object> getTaskInfo(String taskId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select t.*, l.limit_name, l.url, uc.user_name creator_name, ue.user_name editor_name ");
		sql.append("from core_task t ");
		sql.append("left join core_user uc on t.creator=uc.user_id ");
		sql.append("left join core_user ue on t.editor=ue.user_id ");
		sql.append("left join core_limit l on t.limit_id=l.limit_id ");
		sql.append("where t.task_id=?");
		return this.queryForMap(sql.toString(), taskId);
	}
	
	
	/**
	 * 获取任务的参数列表
	 * @param taskId 任务编号
	 * @return 任务参数列表
	 * @throws Exception 抛出全部异常
	 */
	public List<Map<String, Object>> getTaskAttrList(String taskId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select ta.attr_id, ta.task_id, ta.attr_sql, ta.sort from core_task_attr ta ");
		sql.append("where ta.task_id=? order by ta.sort");
		return this.queryForList(sql.toString(), taskId);
	}

}
