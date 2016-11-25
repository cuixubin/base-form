package com.dlshouwen.core.base.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dlshouwen.core.announcement.model.Announcement;
import com.dlshouwen.core.base.config.CONFIG;
import com.dlshouwen.core.base.dao.LayoutDao;
import com.dlshouwen.core.base.model.OperationType;
import com.dlshouwen.core.base.model.SessionUser;
import com.dlshouwen.core.base.utils.LogUtils;
import com.dlshouwen.core.system.model.Limit;

import net.sf.json.JSONArray;

/**
 * 跳转到正常布局页面
 * @author 大连首闻科技有限公司
 * @version 2013-7-16 18:59:36
 */
@Controller
@RequestMapping("/core/base/layout")
public class LayoutController {
	
	/** 功能根路径 */
	private String basePath = "core/base/layout/";
	
	/** 数据操作对象 */
	private LayoutDao dao;
	
	/**
	 * 注入数据操作对象
	 * @param dao 数据操作对象
	 */
	@Resource(name="layoutDao")
	public void setDao(LayoutDao dao) {
		this.dao = dao;
	}

	/**
	 * 跳转到Header页面
	 * @param request 请求对象
	 * @param model 反馈的数据对象
	 * @return basePath + 'header'
	 * @throws Exception
	 */
	@RequestMapping(value="/header")
	public String goHeaderPage(HttpServletRequest request, Model model) throws Exception {
//		获取公告列表到前台
		List<Announcement> announcementList = dao.getAnnouncementList();
		model.addAttribute("announcementList", announcementList);
//		获取皮肤列表传递到前台
		List<String> skinList = new ArrayList<String>();
		File skinFolder = new File(request.getSession().getServletContext().getRealPath("/")+"/" + CONFIG.SKIN_ROOT_PATH);
		for(File skin : skinFolder.listFiles()){
			if(!skin.isDirectory())
				skinList.add(skin.getName());
		}
		model.addAttribute("skinList", skinList);
//		获取根目录传递到前台
		model.addAttribute("skinRootPath", CONFIG.SKIN_ROOT_PATH);
//		记录操作日志
		LogUtils.updateOperationLog(request, OperationType.VISIT, "访问框架顶部页面");
		return basePath + "header";
	}
	
	/**
	 * 跳转到Left页面
	 * @param request 请求对象
	 * @param model 反馈对象
	 * @return basePath + 'left'
	 * @throws Exception
	 */
	@RequestMapping(value="/left")
	public String goLeftPage(HttpServletRequest request, Model model) throws Exception {
//		获取用户
		SessionUser sessionUser = (SessionUser)request.getSession().getAttribute(CONFIG.SESSION_USER);
//		获取所有有权限的菜单列表
		List<Limit> limitList = sessionUser.getLimitList();
		model.addAttribute("limitList", JSONArray.fromObject(limitList).toString());
//		记录操作日志
		LogUtils.updateOperationLog(request, OperationType.VISIT, "访问框架左侧页面");
//		跳转到Left页面
		return basePath + "left";
	}
	
	/**
	 * 跳转到快捷方式页面
	 * @param request 请求对象
	 * @param model 反馈的数据对象
	 * @return basePath + 'shortcut'
	 * @throws Exception
	 */
	@RequestMapping(value="/shortcut")
	public String goShortcutPage(HttpServletRequest request, Model model) throws Exception {
//		获取快捷方式列表传递到前台
		SessionUser sessionUser = (SessionUser)request.getSession().getAttribute(CONFIG.SESSION_USER);
		model.addAttribute("shortcutList", JSONArray.fromObject(sessionUser.getShortcutList()));
//		获取是否显示快捷方式
		model.addAttribute("is_show_shortcut", sessionUser.getUserAttr().getIs_show_shortcut());
//		记录操作日志
		LogUtils.updateOperationLog(request, OperationType.VISIT, "访问框架快捷方式页面");
		return basePath + "shortcut";
	}
	
}
