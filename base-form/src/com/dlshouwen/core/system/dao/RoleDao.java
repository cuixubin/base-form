package com.dlshouwen.core.system.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.stereotype.Component;

import com.dlshouwen.core.base.dao.BaseDao;
import com.dlshouwen.core.base.extra.grid.model.Pager;
import com.dlshouwen.core.base.extra.grid.utils.GridUtils;
import com.dlshouwen.core.base.extra.spring.mapper.ClassRowMapper;
import com.dlshouwen.core.base.utils.SqlUtils;
import com.dlshouwen.core.system.model.Role;
import com.dlshouwen.core.system.model.System;

/**
 * 角色
 * @author 大连首闻科技有限公司
 * @version 2013-08-17 08:04:29 249
 */
@Component("roleDao")
public class RoleDao extends BaseDao {

	/**
	 * 注入数据源
	 * @param dataSource 数据源对象
	 */
	@Resource(name="defaultDataSource")
	public void setDataSource(DataSource dataSource){
		super.setDataSource(dataSource);
	}
	
	/**
	 * 获取子系统列表
	 * @return 子系统列表
	 * @throws Exception 抛出全部异常
	 */
	public List<System> getSystemList() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select s.* from core_system s");
		return this.queryForList(sql.toString(), new ClassRowMapper<System>(System.class));
	}
	
	/**
	 * 获取角色列表
	 * @param pager 分页对象
	 * @param request 请求对象
	 * @param response 响应对象
	 * @throws Exception 抛出全部异常
	 */
	public void getRoleList(Pager pager, HttpServletRequest request, HttpServletResponse response) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select r.*, s.system_name, uc.user_name creator_name, ue.user_name editor_name ");
		sql.append("from core_role r ");
		sql.append("left join core_system s on r.system_id=s.system_id ");
		sql.append("left join core_user uc on r.creator=uc.user_id ");
		sql.append("left join core_user ue on r.editor=ue.user_id ");
		GridUtils.queryForGrid(this, sql.toString(), pager, request, response);
	}
	
	/**
	 * 新增角色
	 * @param role 角色对象
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int insertRole(Role role) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into core_role (role_id, system_id, role_name, remark, creator, create_time, editor, edit_time) ");
		sql.append("values (${role_id }, ${system_id }, ${role_name }, ${remark }, ${creator }, ${create_time }, ${editor }, ${edit_time })");
		return this.updateObject(sql.toString(), role);
	}

	/**
	 * 根据主键获取角色
	 * @param roleId 角色主键
	 * @return 角色对象
	 * @throws Exception 抛出全部异常
	 */
	public Role getRoleById(String roleId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select r.* from core_role r where r.role_id=?");
		return this.queryForObject(sql.toString(), new ClassRowMapper<Role>(Role.class), roleId);
	}

	/**
	 * 更新角色
	 * @param role 角色对象
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int updateRole(Role role) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update core_role set system_id=${system_id }, role_name=${role_name }, remark=${remark }, editor=${editor }, edit_time=${edit_time } ");
		sql.append("where role_id=${role_id }");
		return this.updateObject(sql.toString(), role);
	}

	/**
	 * 删除角色
	 * @param roleIds 角色主键列表
	 * @throws Exception 抛出全部异常
	 */
	public int deleteRole(String roleIds) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from core_role where role_id in (").append(SqlUtils.getArgsKey(roleIds, ",")).append(")");
		return this.update(sql.toString(), SqlUtils.getArgsValue(roleIds, ",").toArray());
	}

	/**
	 * 获取功能列表
	 * @param roleId 角色编号
	 * @return 功能列表
	 * @throws Exception 抛出全部异常
	 */
	public List<Map<String, Object>> getLimitList(String roleId, String systemId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select l.*, case when (");
		sql.append("	select count(*) ");
		sql.append("	from core_limit_role lr ");
		sql.append("	where lr.role_id=? and lr.limit_id=l.limit_id");
		sql.append(")>0 then 'true' else 'false' end checked ");
		sql.append("from core_limit l ");
		sql.append("where l.system_id=? ");
		sql.append("order by l.sort");
		return this.queryForList(sql.toString(), roleId, systemId);
	}

	/**
	 * 清空角色功能关系
	 * @param roleId 角色编号
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int clearRoleLimitRelation(String roleId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from core_limit_role where role_id=?");
		return this.update(sql.toString(), roleId);
	}

	/**
	 * 设置角色功能关系
	 * @param roleId 角色编号
	 * @param limitIds 功能编号列表
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int setRoleLimitRelation(String roleId, String limitIds) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into core_limit_role (role_id, limit_id) select ?, limit_id from core_limit where limit_id in ("+SqlUtils.getArgsKey(limitIds, ",")+")");
		List<Object> args = SqlUtils.getArgsValue(limitIds, ",");
		args.add(0, roleId);
		return this.update(sql.toString(), args.toArray());
	}

	/**
	 * 获取用户列表
	 * @param pager 分页对象
	 * @param request 请求对象
	 * @param response 响应对象
	 * @param roleId 角色编号
	 * @throws Exception 抛出全部异常
	 */
	public void getRoleUserList(Pager pager, HttpServletRequest request, HttpServletResponse response, String roleId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select u.*, d.dept_name, uc.user_name creator_name, ue.user_name editor_name, case when (");
		sql.append("	select count(*) from core_user_role ur where ur.user_id=u.user_id and ur.role_id=?");
		sql.append(")>0 then '1' else '0' end checked ");
		sql.append("from core_user u ");
		sql.append("left join core_dept d on u.dept_id=d.dept_id ");
		sql.append("left join core_user uc on u.creator=uc.user_id ");
		sql.append("left join core_user ue on u.editor=ue.user_id ");
		GridUtils.queryForGrid(this, sql.toString(), pager, request, response, roleId);
	}

	/**
	 * 删除角色用户关系
	 * @param roleId 角色编号
	 * @param userIds 用户编号列表
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int deleteRoleUserRelation(String roleId, String userIds) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from core_user_role where role_id=? and user_id in ("+SqlUtils.getArgsKey(userIds, ",")+")");
		List<Object> args = SqlUtils.getArgsValue(userIds, ",");
		args.add(0, roleId);
		return this.update(sql.toString(), args.toArray());
	}

	/**
	 * 新增角色用户关系
	 * @param roleId 角色编号
	 * @param userIds 用户编号列表
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int insertRoleUserRelation(String roleId, String userIds) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into core_user_role (role_id, user_id) select ?, user_id from core_user where user_id in ("+SqlUtils.getArgsKey(userIds, ",")+")");
		List<Object> args = SqlUtils.getArgsValue(userIds, ",");
		args.add(0, roleId);
		return this.update(sql.toString(), args.toArray());
	}

}