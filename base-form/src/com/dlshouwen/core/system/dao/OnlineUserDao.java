package com.dlshouwen.core.system.dao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.stereotype.Component;

import com.dlshouwen.core.base.dao.BaseDao;
import com.dlshouwen.core.base.extra.grid.model.Pager;
import com.dlshouwen.core.base.extra.grid.utils.GridUtils;

/**
 * 在线用户
 * @author 大连首闻科技有限公司
 * @version 2013-8-19 14:09:09
 */
@Component("coreOnlineUserDao")
public class OnlineUserDao extends BaseDao {

	/**
	 * 注入数据源
	 * @param dataSource 数据源对象
	 */
	@Resource(name="defaultDataSource")
	public void setDataSource(DataSource dataSource){
		super.setDataSource(dataSource);
	}

	/**
	 * 获取在线用户列表
	 * @param pager 分页对象
	 * @param request 请求对象
	 * @param response 响应对象
	 * @throws Exception 抛出全部异常
	 */
	public void getOnlineUserList(Pager pager, HttpServletRequest request, HttpServletResponse response) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select l.* from core_login_log l ");
		sql.append("where l.is_logout='0' ");
		sql.append("order by l.login_time desc");
		GridUtils.queryForGrid(this, sql.toString(), pager, request, response);
	}

	/**
	 * 执行强制下线
	 * @param logId 登录日志编号
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int forceUserOutLine(String logId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update core_login_log set is_logout=?, logout_type=?, logout_time=now() where log_id=?");
		return this.update(sql.toString(), "1", "4", logId);
	}
	
}