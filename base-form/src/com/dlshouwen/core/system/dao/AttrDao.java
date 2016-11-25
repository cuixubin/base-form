package com.dlshouwen.core.system.dao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.stereotype.Component;

import com.dlshouwen.core.base.dao.BaseDao;
import com.dlshouwen.core.base.extra.grid.model.Pager;
import com.dlshouwen.core.base.extra.grid.utils.GridUtils;
import com.dlshouwen.core.base.extra.spring.mapper.ClassRowMapper;
import com.dlshouwen.core.base.utils.SqlUtils;
import com.dlshouwen.core.system.model.Attr;

/**
 * 参数
 * @author 大连首闻科技有限公司
 * @version 2013-08-17 08:04:27 994
 */
@Component("coreAttrDao")
public class AttrDao extends BaseDao {

	/**
	 * 注入数据源
	 * @param dataSource 数据源对象
	 */
	@Resource(name="defaultDataSource")
	public void setDataSource(DataSource dataSource){
		super.setDataSource(dataSource);
	}
	
	/**
	 * 获取参数列表
	 * @param pager 分页对象
	 * @param request 请求对象
	 * @param response 响应对象
	 * @throws Exception 抛出全部异常
	 */
	public void getAttrList(Pager pager, HttpServletRequest request, HttpServletResponse response) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.* from core_attr a order by a.sort ");
		GridUtils.queryForGrid(this, sql.toString(), pager, request, response);
	}

	/**
	 * 新增参数
	 * @param attr 参数对象
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int insertAttr(Attr attr) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into core_attr (attr_id, attr_code, attr_name, type, content, remark, sort) ");
		sql.append("values (${attr_id }, ${attr_code }, ${attr_name }, ${type }, ${content }, ${remark }, ${sort })");
		return this.updateObject(sql.toString(), attr);
	}

	/**
	 * 根据主键获取参数
	 * @param attrId 参数主键
	 * @return 参数对象
	 * @throws Exception 抛出全部异常
	 */
	public Attr getAttrById(String attrId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.* from core_attr a where a.attr_id=?");
		return this.queryForObject(sql.toString(), new ClassRowMapper<Attr>(Attr.class), attrId);
	}

	/**
	 * 更新参数
	 * @param attr 参数对象
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int updateAttr(Attr attr) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update core_attr set attr_code=${attr_code }, attr_name=${attr_name }, ");
		sql.append("type=${type }, content=${content }, remark=${remark }, sort=${sort } ");
		sql.append("where attr_id=${attr_id }");
		return this.updateObject(sql.toString(), attr);
	}

	/**
	 * 删除参数
	 * @param attrIds 参数主键列表
	 * @throws Exception 抛出全部异常
	 */
	public int deleteAttr(String attrIds) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from core_attr where attr_id in (").append(SqlUtils.getArgsKey(attrIds, ",")).append(")");
		return this.update(sql.toString(), SqlUtils.getArgsValue(attrIds, ",").toArray());
	}
	
	/**
	 * 获取默认快捷方式菜单
	 * @return 快捷方式菜单列表
	 * @throws Exception
	 */
	public List<Map<String, Object>> getDefaultShortcutLimitList() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select l.*, case when dsl.limit_id is not null then true else false end checked ");
		sql.append("from core_limit l ");
		sql.append("left join core_default_shortcut_limit dsl on l.limit_id=dsl.limit_id ");
		sql.append("where l.limit_type='1' ");
		sql.append("order by l.sort");
		return this.queryForList(sql.toString());
	}
	
	/**
	 * 更新默认快捷方式菜单
	 * @param shortcutLimitIds 快捷方式菜单列表
	 * @throws Exception
	 */
	public void updateDefaultShortcutLimit(String shortcutLimitIds) throws Exception {
//		删除原有快捷方式菜单
		StringBuffer sql = new StringBuffer();
		sql.append("delete from core_default_shortcut_limit");
		this.update(sql.toString());
//		新建快捷方式菜单
		sql = new StringBuffer();
		sql.append("insert into core_default_shortcut_limit (limit_id) ");
		sql.append("(select limit_id from core_limit l where l.limit_id in (").append(SqlUtils.getArgsKey(shortcutLimitIds, ",")).append("))");
		List<Object> args = SqlUtils.getArgsValue(shortcutLimitIds, ",");
		this.update(sql.toString(), args.toArray());
	}
	
}