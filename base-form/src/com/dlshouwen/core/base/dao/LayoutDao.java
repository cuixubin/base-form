package com.dlshouwen.core.base.dao;

import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.stereotype.Component;

import com.dlshouwen.core.announcement.model.Announcement;
import com.dlshouwen.core.base.extra.spring.mapper.ClassRowMapper;

/**
 * 跳转到正常布局页面
 * @author 刘精诚
 * @since 2013-7-16 18:59:36
 */
@Component("layoutDao")
public class LayoutDao extends BaseDao {
	
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
	 * @param request 请求对象
	 * @param response 响应对象
	 * @throws Exception 抛出全部异常
	 */
	public List<Announcement> getAnnouncementList() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.*, uc.user_name creator_name, ue.user_name editor_name ");
		sql.append("from core_announcement a ");
		sql.append("left join core_user uc on a.creator=uc.user_id ");
		sql.append("left join core_user ue on a.creator=ue.user_id ");
		sql.append("where a.status='1'");
		return this.queryForList(sql.toString(), new ClassRowMapper<Announcement>(Announcement.class));
	}
	
}
