package com.dlshouwen.core.base.extra.grid.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.sf.json.JSONObject;

import com.dlshouwen.core.base.extra.grid.model.Pager;
import com.dlshouwen.core.base.extra.grid.utils.ExportUtils;
import com.dlshouwen.core.base.extra.grid.utils.PagerPropertyUtils;

/**
 * Grid导出
 * @author 大连首闻科技有限公司
 * @version 1.0
 */
@Controller
@RequestMapping("/core/base/extra/grid/export")
public class ExportController {
	
	/**
	 * 执行导出
	 * @param gridPager Pager对象
	 * @param request 请求对象
	 * @param response 响应对象
	 * @throws Exception
	 */
	@RequestMapping(value="", method=RequestMethod.POST)
	public void export(String gridPager, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Pager pager = PagerPropertyUtils.copy(JSONObject.fromObject(gridPager));
		ExportUtils.export(request, response, pager);
	}

}