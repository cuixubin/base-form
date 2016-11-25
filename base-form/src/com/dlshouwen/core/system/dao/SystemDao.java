package com.dlshouwen.core.system.dao;

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
import com.dlshouwen.core.system.model.System;

/**
 * 系统
 * @author 大连首闻科技有限公司
 * @version 2013-08-17 08:04:29 210
 */
@Component("coreSystemDao")
public class SystemDao extends BaseDao {

	/**
	 * 注入数据源
	 * @param dataSource 数据源对象
	 */
	@Resource(name="defaultDataSource")
	public void setDataSource(DataSource dataSource){
		super.setDataSource(dataSource);
	}
	
	/**
	 * 获取系统列表
	 * @param pager 分页对象
	 * @param request 请求对象
	 * @param response 响应对象
	 * @throws Exception 抛出全部异常
	 */
	public void getSystemList(Pager pager, HttpServletRequest request, HttpServletResponse response) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select s.*, uc.user_name creator_name, ue.user_name editor_name ");
		sql.append("from core_system s ");
		sql.append("left join core_user uc on s.creator=uc.user_id ");
		sql.append("left join core_user ue on s.editor=ue.user_id ");
		GridUtils.queryForGrid(this, sql.toString(), pager, request, response);
	}

	/**
	 * 新增系统
	 * @param system 系统对象
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int insertSystem(System system) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into core_system (system_id, system_code, system_name, creator, create_time, editor, edit_time, remark) ");
		sql.append("values (${system_id }, ${system_code }, ${system_name }, ${creator }, ${create_time }, ${editor }, ${edit_time }, ${remark })");
		return this.updateObject(sql.toString(), system);
	}

	/**
	 * 根据主键获取系统
	 * @param systemId 系统主键
	 * @return 系统对象
	 * @throws Exception 抛出全部异常
	 */
	public System getSystemById(String systemId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select s.* from core_system s where s.system_id=?");
		return this.queryForObject(sql.toString(), new ClassRowMapper<System>(System.class), systemId);
	}

	/**
	 * 更新系统
	 * @param system 系统对象
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int updateSystem(System system) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update core_system set system_code=${system_code }, system_name=${system_name }, remark=${remark }, editor=${editor }, edit_time=${edit_time } where system_id=${system_id }");
		return this.updateObject(sql.toString(), system);
	}

	/**
	 * 删除系统
	 * @param systemIds 系统主键列表
	 * @throws Exception 抛出全部异常
	 */
	public int deleteSystem(String systemIds) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from core_system where system_id in (").append(SqlUtils.getArgsKey(systemIds, ",")).append(")");
		return this.update(sql.toString(), SqlUtils.getArgsValue(systemIds, ",").toArray());
	}

	/**
	 * 判断系统是否包含功能
	 * @param systemId 系统编号
	 * @return 判断结果
	 * @throws Exception 抛出全部异常
	 */
	public boolean getSystemIsHasLimit(String systemId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from core_limit l where l.system_id=?");
		return this.queryForInt(sql.toString(), systemId)>0?true:false;
	}

	/**
	 * 判断系统是否包含角色
	 * @param systemId 系统编号
	 * @return 判断结果
	 * @throws Exception 抛出全部异常
	 */
	public boolean getSystemIsHasRole(String systemId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from core_role r where r.system_id=?");
		return this.queryForInt(sql.toString(), systemId)>0?true:false;
	}

}