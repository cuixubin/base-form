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
import com.dlshouwen.core.base.utils.SqlUtils;

/**
 * 公告
 * @author 大连首闻科技有限公司
 * @version 2013-08-17 08:04:27 136
 */
@Component("announcementDao")
public class AnnouncementDao extends BaseDao {

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
		sql.append("left join core_user ue on a.editor=ue.user_id ");
		GridUtils.queryForGrid(this, sql.toString(), pager, request, response);
	}

	/**
	 * 新增公告
	 * @param announcement 公告对象
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int insertAnnouncement(Announcement announcement) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into core_announcement (announcement_id, title, content, status, creator, create_time, editor, edit_time) ");
		sql.append("values (${announcement_id }, ${title }, ${content }, ${status }, ${creator }, ${create_time }, ${editor }, ${edit_time })");
		return this.updateObject(sql.toString(), announcement);
	}

	/**
	 * 根据主键获取公告
	 * @param announcementId 公告主键
	 * @return 公告对象
	 * @throws Exception 抛出全部异常
	 */
	public Announcement getAnnouncementById(String announcementId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.* from core_announcement a where a.announcement_id=?");
		return this.queryForObject(sql.toString(), new ClassRowMapper<Announcement>(Announcement.class), announcementId);
	}

	/**
	 * 更新公告
	 * @param announcement 公告对象
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int updateAnnouncement(Announcement announcement) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update core_announcement set title=${title }, content=${content }, status=${status }, editor=${editor }, edit_time=${edit_time } ");
		sql.append("where announcement_id=${announcement_id }");
		return this.updateObject(sql.toString(), announcement);
	}

	/**
	 * 删除公告
	 * @param announcementIds 公告主键列表
	 * @throws Exception 抛出全部异常
	 */
	public int deleteAnnouncement(String announcementIds) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from core_announcement where announcement_id in (").append(SqlUtils.getArgsKey(announcementIds, ",")).append(")");
		return this.update(sql.toString(), SqlUtils.getArgsValue(announcementIds, ",").toArray());
	}

	/**
	 * 启用公告
	 * @param announcementIds 公告主键列表
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int openAnnouncement(String announcementIds) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update core_announcement set status='1' where announcement_id in (").append(SqlUtils.getArgsKey(announcementIds, ",")).append(")");
		return this.update(sql.toString(), SqlUtils.getArgsValue(announcementIds, ",").toArray());
	}

	/**
	 * 禁用公告
	 * @param announcementIds 公告主键列表
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int closeAnnouncement(String announcementIds) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update core_announcement set status='0' where announcement_id in (").append(SqlUtils.getArgsKey(announcementIds, ",")).append(")");
		return this.update(sql.toString(), SqlUtils.getArgsValue(announcementIds, ",").toArray());
	}

}