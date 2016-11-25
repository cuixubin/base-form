package com.dlshouwen.core.system.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.stereotype.Component;

import com.dlshouwen.core.base.dao.BaseDao;
import com.dlshouwen.core.base.extra.spring.mapper.ClassRowMapper;
import com.dlshouwen.core.base.utils.SqlUtils;
import com.dlshouwen.core.system.model.Dept;

/**
 * 部门
 * @author 大连首闻科技有限公司
 * @version 2013-08-17 08:04:29 144
 */
@Component("deptDao")
public class DeptDao extends BaseDao {

	/**
	 * 注入数据源
	 * @param dataSource 数据源对象
	 */
	@Resource(name="defaultDataSource")
	public void setDataSource(DataSource dataSource){
		super.setDataSource(dataSource);
	}

	/**
	 * 获取部门列表
	 * @return 部门列表
	 * @throws Exception 抛出全部异常
	 */
	public List<Map<String, Object>> getDeptList() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select d.* from core_dept d order by d.sort");
		return this.queryForList(sql.toString());
	}

	/**
	 * 获取部门名称列表
	 * @return 部门列表
	 * @throws Exception 抛出全部异常
	 */
	public List<String> getDeptNameList() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select CONCAT(d.dept_name,'____',d.dept_id) as deptName from core_dept d order by d.sort");
		return this.queryForList(sql.toString(), String.class);
	}

	/**
	 * 获取部门信息
	 * @param deptId 部门编号
	 * @return 部门信息
	 * @throws Exception 抛出全部异常
	 */
	public Dept getDeptById(String deptId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select d.*, uc.user_name creator_name, ue.user_name editor_name ");
		sql.append("from core_dept d ");
		sql.append("left join core_user uc on d.creator=uc.user_id ");
		sql.append("left join core_user ue on d.editor=ue.user_id ");
		sql.append("where d.dept_id=?");
		return this.queryForObject(sql.toString(), new ClassRowMapper<Dept>(Dept.class), deptId);
	}
	
	/**
	 * 新增部门
	 * @param dept 部门对象
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int insertDept(Dept dept) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into core_dept (dept_id, pre_dept_id, dept_name, phone, principal, principal_phone, ");
		sql.append("sort, remark, creator, create_time, editor, edit_time) ");
		sql.append("values (${dept_id }, ${pre_dept_id }, ${dept_name }, ${phone }, ${principal }, ${principal_phone }, ");
		sql.append("${sort }, ${remark }, ${creator }, ${create_time }, ${editor }, ${edit_time })");
		return this.updateObject(sql.toString(), dept);
	}

	/**
	 * 更新部门
	 * @param dept 部门对象
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int updateDept(Dept dept) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update core_dept set dept_name=${dept_name }, phone=${phone }, principal=${principal }, principal_phone=${principal_phone }, ");
		sql.append("sort=${sort }, remark=${remark }, editor=${editor }, edit_time=${edit_time } ");
		sql.append("where dept_id=${dept_id }");
		return this.updateObject(sql.toString(), dept);
	}
	
	/**
	 * 获取子菜单个数
	 * @param deptId 部门编号
	 * @return 子菜单个数
	 * @throws Exception 抛出全部异常
	 */
	public int getSubDeptCount(String deptId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(1) from core_dept o where o.pre_dept_id=?");
		return this.queryForInt(sql.toString(), deptId);
	}

	/**
	 * 删除部门
	 * @param deptIds 部门编号列表
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int deleteDept(String deptIds) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from core_dept where dept_id in (").append(SqlUtils.getArgsKey(deptIds, ",")).append(")");
		return this.update(sql.toString(), SqlUtils.getArgsValue(deptIds, ",").toArray());
	}

	/**
	 * 部门是否包含人员
	 * @param deptId 部门编号
	 * @return 是否包含人员
	 * @throws Exception 抛出全部异常
	 */
	public boolean getDeptIsHaveUser(String deptId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from core_user where dept_id=?");
		return this.queryForInt(sql.toString(), deptId)>0?true:false;
	}

}