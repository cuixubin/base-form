package com.dlshouwen.core.base.controller;

import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dlshouwen.core.base.config.CONFIG;
import com.dlshouwen.core.base.dao.SkinDao;
import com.dlshouwen.core.base.model.AjaxResponse;
import com.dlshouwen.core.base.model.OperationType;
import com.dlshouwen.core.base.model.SessionUser;
import com.dlshouwen.core.base.utils.LogUtils;

/**
 * 皮肤功能
 * @author 大连首闻科技有限公司
 * @version 2016-5-11 15:03:33
 */
@Controller("skinController")
@RequestMapping("/core/base/skin")
public class SkinController {
	
	@SuppressWarnings("unused")
	private String basePath = "core/base/skin/";
	
	private SkinDao dao;
	
	@Resource(name="skinDao")
	public void setDao(SkinDao dao) {
		this.dao = dao;
	}
	
	/**
	 * 存储皮肤信息
	 * @param skinInfo 皮肤信息
	 * @param request 请求对象
	 * @param response 响应对象
	 * @param model 反馈对象
	 * @return AJAX的响应信息
	 * @throws Exception
	 */
	@RequestMapping(value="/set_user_skin_info", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse saveUserSkinInfo(String skinInfo, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
//		定义AJAX响应回执
		AjaxResponse ajaxResponse = new AjaxResponse();
//		获取登录用户编号
		SessionUser sessionUser = (SessionUser)request.getSession().getAttribute(CONFIG.SESSION_USER);
		String userId = sessionUser.getUser_id();
//		设置用户皮肤信息
		dao.setUserSkinInfo(userId, skinInfo);
//		设置Session中的用户皮肤信息
		sessionUser.getUserAttr().setSkin_info(skinInfo);
		request.getSession().setAttribute(CONFIG.SESSION_USER, sessionUser);
//		设置皮肤缓存
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("skin_info")) {
					cookie.setValue(URLEncoder.encode(skinInfo, "utf-8"));
					cookie.setPath("/");
					try{
						cookie.setMaxAge(60 * 60 * 24 * CONFIG.SYSTEM_LOGIN_USER_SKIN_INFO_COOKIE_AVAILABLE_DAY);
					}catch(Exception e){
						cookie.setMaxAge(60 * 60 * 24 * 30);
					}
					response.addCookie(cookie);
					break;
				}
			}
		}
//		反馈AJAX响应
		ajaxResponse.setSuccess(true);
		ajaxResponse.setSuccessMessage("用户皮肤信息设置成功！");
//		记录操作日志
		LogUtils.updateOperationLog(request, OperationType.UPDATE, "设置用户皮肤信息，用户内码："+userId);
		return ajaxResponse;
	}
	
	/**
	 * 生成皮肤信息
	 * @param request 请求对象
	 * @param model 反馈对象
	 * @return 皮肤信息
	 * @throws Exception
	 */
	@RequestMapping(value="", method=RequestMethod.GET)
	@ResponseBody
	public String skin(HttpServletRequest request, Model model) throws Exception {
//		获取登录用户
		SessionUser sessionUser = (SessionUser)request.getSession().getAttribute(CONFIG.SESSION_USER);
		if(sessionUser==null){
			return "";
		}
//		定义样式表
		StringBuffer content = new StringBuffer();
		content.append("body{background-image:url('").append(request.getContextPath())
			.append("/").append(CONFIG.SKIN_ROOT_PATH).append("/").append(sessionUser.getUserAttr().getSkin_info()).append("');background-size:100% 100%}");
		return content.toString();
	}
	
}
