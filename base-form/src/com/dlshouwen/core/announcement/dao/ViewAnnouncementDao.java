package com.dlshouwen.core.announcement.dao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.stereotype.Component;

import com.dlshouwen.core.announcement.model.Announcement;
import com.dlshouwen.core.base.dao.BaseDao;
import com.dlshouwen.core.base.extra.grid.model.Pager;
import com.dlshouwen.core.base.extra.grid.utils.GridUtils;
import com.dlshouwen.core.base.extra.spring.mapper.ClassRowMapper;

/**
 * 公告查看
 * @author 大连首闻科技有限公司
 * @version 2013-08-17 08:04:27 165
 */
@Component("viewAnnouncementDao")
public class ViewAnnouncementDao extends BaseDao {

	/**
	 * 注入数据源
	 * @param dataSource 数据源对象
	 */
	@Resource(name="defaultDataSource")
	public void setDataSource(DataSource dataSource){
		super.setDataSource(dataSource);
	}
	
	/**
	 * 获取公告列表
	 * @param pager 分页对象
	 * @param request 请求对象
	 * @param response 响应对象
	 * @throws Exception 抛出全部异常
	 */
	public void getAnnouncementList(Pager pager, HttpServletRequest request, HttpServletResponse response) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.*, uc.user_name creator_name, ue.user_name editor_name ");
		sql.append("from core_announcement a ");
		sql.append("left join core_user uc on a.creator=uc.user_id ");
		sql.append("left join core_user ue on a.creator=ue.user_id ");
		sql.append("where a.status='1'");
		GridUtils.queryForGrid(this, sql.toString(), pager, request, response);
	}
	
	/**
	 * 根据主键获取公告
	 * @param announcementId 公告主键
	 * @return 公告对象
	 * @throws Exception 抛出全部异常
	 */
	public Announcement getAnnouncementById(String announcementId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.*, uc.user_name creator_name, ue.user_name editor_name ");
		sql.append("from core_announcement a ");
		sql.append("left join core_user uc on a.creator=uc.user_id ");
		sql.append("left join core_user ue on a.creator=ue.user_id ");
		sql.append("where a.announcement_id=?");
		return this.queryForObject(sql.toString(), new ClassRowMapper<Announcement>(Announcement.class), announcementId);
	}

}