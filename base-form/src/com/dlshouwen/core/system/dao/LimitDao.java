package com.dlshouwen.core.system.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.stereotype.Component;

import com.dlshouwen.core.base.dao.BaseDao;
import com.dlshouwen.core.base.extra.spring.mapper.ClassRowMapper;
import com.dlshouwen.core.base.utils.SqlUtils;
import com.dlshouwen.core.system.model.Limit;
import com.dlshouwen.core.system.model.System;

/**
 * 功能
 * @author 大连首闻科技有限公司
 * @version 2013-08-17 08:04:29 144
 */
@Component("limitDao")
public class LimitDao extends BaseDao {

	/**
	 * 注入数据源
	 * @param dataSource 数据源对象
	 */
	@Resource(name="defaultDataSource")
	public void setDataSource(DataSource dataSource){
		super.setDataSource(dataSource);
	}

	/**
	 * 获取功能列表
	 * @return 功能列表
	 * @throws Exception 抛出全部异常
	 */
	public List<Map<String, Object>> getLimitList() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select l.* from core_limit l order by l.sort");
		return this.queryForList(sql.toString());
	}

	/**
	 * 获取功能信息
	 * @param limitId 功能编号
	 * @return 功能信息
	 * @throws Exception 抛出全部异常
	 */
	public Limit getLimitById(String limitId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select l.*, s.system_name, uc.user_name creator_name, ue.user_name editor_name ");
		sql.append("from core_limit l ");
		sql.append("left join core_user uc on l.creator=uc.user_id ");
		sql.append("left join core_user ue on l.editor=ue.user_id ");
		sql.append("left join core_system s on l.system_id=s.system_id ");
		sql.append("where l.limit_id=?");
		return this.queryForObject(sql.toString(), new ClassRowMapper<Limit>(Limit.class), limitId);
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
	 * 新增功能
	 * @param limit 功能对象
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int insertLimit(Limit limit) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into core_limit (limit_id, pre_limit_id, system_id, limit_name, limit_type, ifAsynch, url, sort, icon_fa, remark, creator, create_time, editor, edit_time) ");
		sql.append("values (${limit_id }, ${pre_limit_id }, ${system_id }, ${limit_name }, ${limit_type }, ${ifAsynch}, ${url }, ${sort }, ${icon_fa }, ${remark }, ${creator }, ${create_time }, ${editor }, ${edit_time })");
		return this.updateObject(sql.toString(), limit);
	}

	/**
	 * 获取最大的排序码
	 * @param preLimitId 上级功能编号
	 * @return 下属功能的最大排序号
	 * @throws Exception 抛出全部异常
	 */
	public int getMaxSort(String preLimitId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select max(s.sort) from core_limit s where s.pre_limit_id=?");
		return this.queryForInt(sql.toString(), preLimitId);
	}

	/**
	 * 更新功能
	 * @param limit 功能对象
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int updateLimit(Limit limit) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update core_limit set pre_limit_id=${pre_limit_id }, limit_name=${limit_name }, ");
		sql.append("system_id=${system_id }, limit_type=${limit_type }, ifAsynch=${ifAsynch}, url=${url }, sort=${sort }, ");
		sql.append("icon_fa=${icon_fa }, remark=${remark }, editor=${editor }, edit_time=${edit_time } ");
		sql.append("where limit_id=${limit_id }");
		return this.updateObject(sql.toString(), limit);
	}
	
	/**
	 * 获取子菜单个数
	 * @param limitId 功能编号
	 * @return 子菜单个数
	 * @throws Exception 抛出全部异常
	 */
	public int getSubLimitCount(String limitId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from core_limit s where s.pre_limit_id=?");
		return this.queryForInt(sql.toString(), limitId);
	}

	/**
	 * 删除功能
	 * @param limitIds 功能编号列表
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int deleteLimit(String limitIds) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from core_limit where limit_id in (").append(SqlUtils.getArgsKey(limitIds, ",")).append(")");
		return this.update(sql.toString(), SqlUtils.getArgsValue(limitIds, ",").toArray());
	}

	/**
	 * 将所有大于此排序号的节点序号自增1
	 * @param preLimitId 上级功能编号
	 * @param sort 排序码
	 * @param notInLimitId 不处理的功能编号
	 * @param includeSelf 本身是否自增
	 * @return 响应的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int updateLimitSortAdd1(String preLimitId, int sort, String notInLimitId, boolean includeSelf) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update core_limit set sort=sort+1 where pre_limit_id=? and limit_id<>? and sort").append(includeSelf?">=":">").append("?");
		return this.update(sql.toString(), preLimitId, notInLimitId, sort);
	}

	/**
	 * 获取功能列表，只获取菜单
	 * @return 功能列表
	 * @throws Exception 抛出全部异常
	 */
	public List<Map<String, Object>> getLimitListOnlyMenu() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select l.limit_id id, l.pre_limit_id pid, l.limit_name name, l.* ");
		sql.append("from core_limit l where l.limit_type='1' order by l.sort");
		return this.queryForList(sql.toString());
	}

	/**
	 * 获取功能列表，只获取菜单（包含用户权限）
	 * @return 功能列表
	 * @throws Exception 抛出全部异常
	 */
	public List<Map<String, Object>> getLimitListOnlyMenu(String userId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select l.* ");
		sql.append("from core_limit l ");
		sql.append("where l.limit_type='1' and l.limit_id in (");
		sql.append("	select lr.limit_id from core_limit_role lr where lr.role_id in (");
		sql.append("		select ur.role_id from core_user_role ur where ur.user_id=? ");
		sql.append(")) order by l.sort");
		return this.queryForList(sql.toString(), userId);
	}

}