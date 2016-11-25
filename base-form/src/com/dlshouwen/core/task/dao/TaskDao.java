package com.dlshouwen.core.task.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.stereotype.Component;

import com.dlshouwen.core.base.dao.BaseDao;
import com.dlshouwen.core.base.extra.grid.model.Pager;
import com.dlshouwen.core.base.extra.grid.utils.GridUtils;
import com.dlshouwen.core.base.extra.spring.mapper.ClassRowMapper;
import com.dlshouwen.core.base.utils.SqlUtils;
import com.dlshouwen.core.task.model.Task;
import com.dlshouwen.core.task.model.TaskAttr;

/**
 * 任务
 * @author 大连首闻科技有限公司
 * @version 2013-08-17 08:04:29 320
 */
@Component("taskDao")
public class TaskDao extends BaseDao {

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
	 * @param pager 分页对象
	 * @param request 请求对象
	 * @param response 响应对象
	 * @throws Exception 抛出全部异常
	 */
	public void getTaskList(Pager pager, HttpServletRequest request, HttpServletResponse response) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select t.*, l.limit_name, uc.user_name creator_name, ue.user_name editor_name ");
		sql.append("from core_task t ");
		sql.append("left join core_limit l on t.limit_id=l.limit_id ");
		sql.append("left join core_user uc on t.creator=uc.user_id ");
		sql.append("left join core_user ue on t.editor=ue.user_id ");
		GridUtils.queryForGrid(this, sql.toString(), pager, request, response);
	}

	/**
	 * 新增任务
	 * @param task 任务对象
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int insertTask(Task task) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into core_task (task_id, task_name, status, is_timing, timing_time, time_space, ");
		sql.append("is_never_overdue, overdue_time, message, limit_id, detonate_sql, is_all_user, creator, create_time, editor, edit_time) ");
		sql.append("values (${task_id }, ${task_name }, ${status }, ${is_timing }, ${timing_time }, ${time_space }, ${is_never_overdue }, ");
		sql.append("${overdue_time }, ${message }, ${limit_id }, ${detonate_sql }, ${is_all_user }, ${creator }, ${create_time }, ${editor }, ${edit_time })");
		return this.updateObject(sql.toString(), task);
	}
	
	/**
	 * 新增任务参数
	 * @param taskAttr 任务参数对象
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int insertTaskAttr(TaskAttr taskAttr) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into core_task_attr (attr_id, task_id, attr_sql, sort) ");
		sql.append("values (${attr_id }, ${task_id }, ${attr_sql }, ${sort })");
		return this.updateObject(sql.toString(), taskAttr);
	}

	/**
	 * 根据主键获取任务
	 * @param taskId 任务主键
	 * @return 任务对象
	 * @throws Exception 抛出全部异常
	 */
	public Task getTaskById(String taskId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select t.*, l.limit_name from core_task t ");
		sql.append("left join core_limit l on t.limit_id=l.limit_id ");
		sql.append("where t.task_id=?");
		return this.queryForObject(sql.toString(), new ClassRowMapper<Task>(Task.class), taskId);
	}
	
	/**
	 * 根据主键获取任务参数列表
	 * @param taskId 任务编号
	 * @return 任务参数列表
	 * @throws Exception 抛出全部异常
	 */
	public List<TaskAttr> getTaskAttrListById(String taskId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select ta.* from core_task_attr ta where ta.task_id=? order by ta.sort");
		return this.queryForList(sql.toString(), new ClassRowMapper<TaskAttr>(TaskAttr.class), taskId);
	}

	/**
	 * 更新任务
	 * @param task 任务对象
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int updateTask(Task task) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update core_task set task_name=${task_name }, status=${status }, is_timing=${is_timing }, ");
		sql.append("timing_time=${timing_time }, time_space=${time_space }, is_never_overdue=${is_never_overdue }, ");
		sql.append("overdue_time=${overdue_time }, message=${message }, limit_id=${limit_id }, ");
		sql.append("detonate_sql=${detonate_sql }, is_all_user=${is_all_user }, editor=${editor }, edit_time=${edit_time } ");
		sql.append("where task_id=${task_id }");
		return this.updateObject(sql.toString(), task);
	}
	
	/**
	 * 清空所有参数
	 * @param taskId 任务编号
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int clearTaskAttr(String taskId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from core_task_attr where task_id=?");
		return this.update(sql.toString(), taskId);
	}

	/**
	 * 删除任务
	 * @param taskIds 任务主键列表
	 * @throws Exception 抛出全部异常
	 */
	public void deleteTask(String taskIds) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from core_task where task_id in (").append(SqlUtils.getArgsKey(taskIds, ",")).append(")");
		this.update(sql.toString(), SqlUtils.getArgsValue(taskIds, ",").toArray());
		sql = new StringBuffer();
		sql.append("delete from core_task_attr where task_id in (").append(SqlUtils.getArgsKey(taskIds, ",")).append(")");
		this.update(sql.toString(), SqlUtils.getArgsValue(taskIds, ",").toArray());
		sql = new StringBuffer();
		sql.append("delete from core_task_role where task_id in (").append(SqlUtils.getArgsKey(taskIds, ",")).append(")");
		this.update(sql.toString(), SqlUtils.getArgsValue(taskIds, ",").toArray());
		sql = new StringBuffer();
		sql.append("delete from core_task_user where task_id in (").append(SqlUtils.getArgsKey(taskIds, ",")).append(")");
		this.update(sql.toString(), SqlUtils.getArgsValue(taskIds, ",").toArray());
	}

	/**
	 * 启用任务
	 * @param taskIds 任务主键列表
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int openTask(String taskIds) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update core_task set status='1' where task_id in (").append(SqlUtils.getArgsKey(taskIds, ",")).append(")");
		return this.update(sql.toString(), SqlUtils.getArgsValue(taskIds, ",").toArray());
	}

	/**
	 * 禁用任务
	 * @param taskIds 任务主键列表
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int closeTask(String taskIds) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update core_task set status='0' where task_id in (").append(SqlUtils.getArgsKey(taskIds, ",")).append(")");
		return this.update(sql.toString(), SqlUtils.getArgsValue(taskIds, ",").toArray());
	}

	/**
	 * 清空任务用户关系
	 * @param taskId 任务编号
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int clearTaskUser(String taskId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from core_task_user where task_id=?");
		return this.update(sql.toString(), taskId);
	}

	/**
	 * 插入任务用户关系
	 * @param taskId 任务编号
	 * @param userIds 用户
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int insertTaskUser(String taskId, String userIds) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into core_task_user (task_id, user_id) ");
		sql.append("select ?, user_id from core_user where user_id in (").append(SqlUtils.getArgsKey(userIds, ",")).append(")");
		List<Object> args = SqlUtils.getArgsValue(userIds, ",");
		args.add(0, taskId);
		return this.update(sql.toString(), args.toArray());
	}

	/**
	 * 清空任务角色关系
	 * @param taskId 任务编号
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int clearTaskRole(String taskId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from core_task_role where task_id=?");
		return this.update(sql.toString(), taskId);
	}

	/**
	 * 插入任务角色关系
	 * @param taskId 任务编号
	 * @param roleIds 角色编号列表
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int insertTaskRole(String taskId, String roleIds) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into core_task_role (task_id, role_id) ");
		sql.append("select ?, role_id from core_role where role_id in (").append(SqlUtils.getArgsKey(roleIds, ",")).append(")");
		List<Object> args = SqlUtils.getArgsValue(roleIds, ",");
		args.add(0, taskId);
		return this.update(sql.toString(), args.toArray());
	}

	/**
	 * 获取用户列表
	 * @return 用户列表
	 * @throws Exception 抛出全部异常
	 */
	public List<Map<String, Object>> getUserList() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select u.user_id id, u.user_name name, 'top' pid from core_user u");
		return this.queryForList(sql.toString());
	}

	/**
	 * 获取角色列表
	 * @return 角色列表
	 * @throws Exception 抛出全部异常
	 */
	public List<Map<String, Object>> getRoleList() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select r.role_id id, r.role_name name, 'top' pid from core_role r");
		return this.queryForList(sql.toString());
	}

	/**
	 * 获取用户列表
	 * @param taskId 任务编号
	 * @return 用户列表
	 * @throws Exception 抛出全部异常
	 */
	public List<Map<String, Object>> getUserList(String taskId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select u.user_id id, u.user_name name, 'top' pid, ");
		sql.append("case when tu.task_id is not null then true else false end checked ");
		sql.append("from core_user u ");
		sql.append("left join core_task_user tu on u.user_id=tu.user_id and tu.task_id=? ");
		return this.queryForList(sql.toString(), taskId);
	}

	/**
	 * 获取角色列表
	 * @param taskId 任务编号
	 * @return 角色列表
	 * @throws Exception 抛出全部异常
	 */
	public List<Map<String, Object>> getRoleList(String taskId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select r.role_id id, r.role_name name, 'top' pid, ");
		sql.append("case when tr.task_id is not null then true else false end checked ");
		sql.append("from core_role r ");
		sql.append("left join core_task_role tr on r.role_id=tr.role_id and tr.task_id=? ");
		return this.queryForList(sql.toString(), taskId);
	}

}