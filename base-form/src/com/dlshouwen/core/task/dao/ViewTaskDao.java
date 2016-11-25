package com.dlshouwen.core.task.dao;

import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.stereotype.Component;

import com.dlshouwen.core.base.dao.BaseDao;
import com.dlshouwen.core.base.extra.spring.mapper.ClassRowMapper;
import com.dlshouwen.core.task.model.Task;
import com.dlshouwen.core.task.model.TaskAttr;

/**
 * 任务查看
 * @author 大连首闻科技有限公司
 * @version 2013-08-17 08:04:29 326
 */
@Component("viewTaskDao")
public class ViewTaskDao extends BaseDao {

	/**
	 * 注入数据源
	 * @param dataSource 数据源对象
	 */
	@Resource(name="defaultDataSource")
	public void setDataSource(DataSource dataSource){
		super.setDataSource(dataSource);
	}

	/**
	 * 获取任务列表
	 * @param userId 用户编号
	 * @return 任务列表
	 * @throws Exception 抛出全部异常
	 */
	public List<Task> getTaskList(String userId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select t.*, l.limit_name, l.url from core_task t ");
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
		return this.queryForList(sql.toString(), new ClassRowMapper<Task>(Task.class), userId, userId);
	}

	/**
	 * 获取任务参数列表
	 * @param taskId 任务编号
	 * @return 任务参数列表
	 * @throws Exception 抛出全部异常
	 */
	public List<TaskAttr> getTaskAttrList(String taskId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select ta.* from core_task_attr ta where ta.task_id=? order by ta.sort");
		return this.queryForList(sql.toString(), new ClassRowMapper<TaskAttr>(TaskAttr.class), taskId);
	}

}