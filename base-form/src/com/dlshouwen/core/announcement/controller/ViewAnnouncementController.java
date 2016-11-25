package com.dlshouwen.core.announcement.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dlshouwen.core.announcement.dao.ViewAnnouncementDao;
import com.dlshouwen.core.announcement.model.Announcement;
import com.dlshouwen.core.base.extra.grid.model.Pager;
import com.dlshouwen.core.base.extra.grid.utils.PagerPropertyUtils;
import com.dlshouwen.core.base.model.OperationType;
import com.dlshouwen.core.base.utils.LogUtils;

import net.sf.json.JSONObject;

/**
 * 公告查看
 * @author 大连首闻科技有限公司
 * @version 2013-08-17 08:04:27 144
 */
@Controller
@RequestMapping("/core/announcement/view_announcement")
public class ViewAnnouncementController {
	
	/** 功能根路径 */
	private String basePath = "core/announcement/viewAnnouncement/";

	/** 数据操作对象 */
	private ViewAnnouncementDao dao;

	/**
	 * 注入数据操作对象
	 * @param dao 数据操作对象
	 */
	@Resource(name="viewAnnouncementDao")
	public void setDao(ViewAnnouncementDao dao) {
		this.dao = dao;
	}
	
	/**
	 * 跳转到查看公告主页面
	 * @param request 请求对象
	 * @return basePath + 'announcementList'
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="", method=RequestMethod.GET)
	public String goAnnouncementPage(HttpServletRequest request) throws Exception {
//		记录操作日志
		LogUtils.updateOperationLog(request, OperationType.VISIT, "访问查看公告页面");
		return basePath+"announcementList";
	}
	
	/**
	 * 获取公告列表数据
	 * @param gridPager 表格分页信息
	 * @param request 请求对象
	 * @param response 响应对象
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="/list", method=RequestMethod.POST)
	public void getAnnouncementList(String gridPager, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		映射Pager对象
		Pager pager = PagerPropertyUtils.copy(JSONObject.fromObject(gridPager));
//		获取公告列表数据
		dao.getAnnouncementList(pager, request, response);
//		记录操作日志
		LogUtils.updateOperationLog(request, OperationType.SEARCH, "获取查看公告页面的公告列表数据");
	}
	
	/**
	 * 查看公告信息
	 * @param announcementId 公告编号
	 * @param request 请求对象
	 * @return basePath + 'viewAnnouncement'
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="/{announcementId}/view", method=RequestMethod.GET)
	public String viewAnnouncement(@PathVariable String announcementId, 
			Model model, HttpServletRequest request) throws Exception {
//		获取公告信息
		Announcement announcement = dao.getAnnouncementById(announcementId);
//		设置到反馈对象中
		model.addAttribute("announcement", announcement);
//		记录操作日志
		LogUtils.updateOperationLog(request, OperationType.VISIT, "查看公告，公告编号："+announcement.getAnnouncement_id()+"，公告标题："+announcement.getTitle());
//		执行跳转到查看页面
		return basePath + "viewAnnouncement";
	}

}