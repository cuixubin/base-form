package com.dlshouwen.core.system.dao;

import java.util.List;

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
import com.dlshouwen.core.system.model.CodeTable;
import com.dlshouwen.core.system.model.CodeTableType;

/**
 * 码表
 * @author 大连首闻科技有限公司
 * @version 2013-08-17 08:04:28 763
 */
@Component("codeTableDao")
public class CodeTableDao extends BaseDao {

	/**
	 * 注入数据源
	 * @param dataSource 数据源对象
	 */
	@Resource(name="defaultDataSource")
	public void setDataSource(DataSource dataSource){
		super.setDataSource(dataSource);
	}

	/**
	 * 获取码表类别列表
	 * @param pager 分页对象
	 * @param request 请求对象
	 * @param response 响应对象
	 * @throws Exception 抛出全部异常
	 */
	public void getCodeTableTypeList(Pager pager, HttpServletRequest request, HttpServletResponse response) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select ctt.*, ");
		sql.append("(select count(*) from core_code_table ct where ct.c_type=ctt.c_type) ct_count ");
		sql.append("from core_code_table_type ctt ");
		GridUtils.queryForGrid(this, sql.toString(), pager, request, response);
	}

	/**
	 * 新增码表类别
	 * @param codeTableType 码表类别对象
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int insertCodeTableType(CodeTableType codeTableType) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into core_code_table_type (c_type, c_info) values (${c_type }, ${c_info })");
		return this.updateObject(sql.toString(), codeTableType);
	}

	/**
	 * 根据类别编号获取码表类别
	 * @param c_type 类别编号
	 * @return 码表类别
	 * @throws Exception 抛出全部异常
	 */
	public CodeTableType getCodeTableTypeByType(String c_type) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select ctt.* from core_code_table_type ctt where ctt.c_type=?");
		return this.queryForObject(sql.toString(), new ClassRowMapper<CodeTableType>(CodeTableType.class), c_type);
	}

	/**
	 * 更新码表类别
	 * @param codeTableType 码表类别
	 * @param oldType 原码表类别编号
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int updateCodeTableType(CodeTableType codeTableType, String oldType) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update core_code_table_type set c_type=${c_type }, c_info=${c_info } where c_type=${oldType }");
		return this.updateObject(sql.toString(), codeTableType, oldType);
	}
	
	/**
	 * 执行编辑码表下的所有码表类别
	 * @param codeTableType 码表类别编号
	 * @param oldType 原码表类别编号
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int updateCodeTable(CodeTableType codeTableType, String oldType) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update core_code_table set c_type=${c_type } where c_type=${oldType }");
		return this.updateObject(sql.toString(), codeTableType, oldType);
	}
	
	/**
	 * 码表类别是否包含码表
	 * @param c_type 码表类别编号
	 * @return 是否包含码表
	 * @throws Exception 抛出全部异常
	 */
	public boolean getCodeTableTypeIsHaveCodeTable(String c_type) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from core_code_table ct where ct.c_type=?");
		return this.queryForInt(sql.toString(), c_type)>0?true:false;
	}

	/**
	 * 删除码表类别
	 * @param c_types 码表类别编号列表
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int deleteCodeTableType(String c_types) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from core_code_table_type where c_type in ("+SqlUtils.getArgsKey(c_types, ",")+")");
		return this.update(sql.toString(), SqlUtils.getArgsValue(c_types, ",").toArray());
	}

	/**
	 * 获取码表列表
	 * @param pager 分页对象
	 * @param request 请求对象
	 * @param response 响应对象
	 * @param c_type 类别编号
	 * @throws Exception 抛出全部异常
	 */
	public void getCodeTableList(Pager pager, HttpServletRequest request, HttpServletResponse response, String c_type) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select ct.* from core_code_table ct where ct.c_type=? order by ct.sort");
		GridUtils.queryForGrid(this, sql.toString(), pager, request, response, c_type);
	}

	/**
	 * 新增码表
	 * @param codeTable 码表对象
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int insertCodeTable(CodeTable codeTable) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into core_code_table (c_type, c_key, c_value, sort) values (${c_type }, ${c_key }, ${c_value }, ${sort })");
		return this.updateObject(sql.toString(), codeTable);
	}

	/**
	 * 根据类别编号和key获取码表对象
	 * @param c_type 类别编号
	 * @param c_key 码表key
	 * @return 码表对象
	 * @throws Exception 抛出全部异常
	 */
	public CodeTable getCodeTableByTypeAndKey(String c_type, String c_key) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select ct.* from core_code_table ct where ct.c_type=? and ct.c_key=?");
		return this.queryForObject(sql.toString(), new ClassRowMapper<CodeTable>(CodeTable.class), c_type, c_key);
	}

	/**
	 * 更新码表
	 * @param codeTable 码表对象
	 * @param oldKey 原码表Key
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int updateCodeTable(CodeTable codeTable, String oldKey) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update core_code_table set c_key=${c_key }, c_value=${c_value }, sort=${sort } where c_type=${c_type } and c_key=${oldKey }");
		return this.updateObject(sql.toString(), codeTable, oldKey);
	}

	/**
	 * 删除码表
	 * @param c_type 码表类别编号
	 * @param c_keys 码表Key
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int deleteCodeTable(String c_type, String c_keys) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from core_code_table where c_type=? and c_key in ("+SqlUtils.getArgsKey(c_keys, ",")+")");
		List<Object> args = SqlUtils.getArgsValue(c_keys, ",");
		args.add(0, c_type);
		return this.update(sql.toString(), args.toArray());
	}

}