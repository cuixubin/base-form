package com.dlshouwen.core.base.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dlshouwen.core.base.config.CONFIG;
import com.dlshouwen.core.base.model.SessionUser;
import com.dlshouwen.core.base.utils.LimitUtils;

/**
 * Controller基类
 * @author 大连首闻科技有限公司
 * @version 2013-8-18 19:03:12
 */
@Controller
@RequestMapping("/core/base")
public class BaseController {
	
	/**
	 * 执行设置用户信息
	 * @param user 被编辑的用户
	 * @param bindingResult 绑定的错误信息
	 * @param request 请求对象
	 * @return AJAX响应回执
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="/limit", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Boolean> limit(String urls, HttpServletRequest request) throws Exception {
//		获取SessionUser对象
		SessionUser sessionUser = (SessionUser)request.getSession().getAttribute(CONFIG.SESSION_USER);
//		判断权限
		Map<String, Boolean> result = new HashMap<String, Boolean>();
		for(String url : urls.split(",")){
			result.put(url, LimitUtils.checkLimit(url, sessionUser.getLimitInfo()));
		}
		return result;
	}
	
}