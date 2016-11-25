package com.dlshouwen.core.tools.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dlshouwen.core.tools.dao.JsCompileDao;

/**
 * JS编译器
 * @author 大连首闻科技有限公司
 * @version 2013-11-20 16:45:32
 */
@Controller
@RequestMapping("/core/tools/js_compile")
public class JsCompileController {
	
	/** 功能根路径 */
	private String basePath = "core/tools/jsCompile/";

	/** 数据操作对象 */
	@SuppressWarnings("unused")
	private JsCompileDao dao;

	/**
	 * 注入数据操作对象
	 * @param dao 数据操作对象
	 */
	@Resource(name="jsCompileDao")
	public void setDao(JsCompileDao dao) {
		this.dao = dao;
	}
	
	/**
	 * 跳转到JS编译页面
	 * @return basePath + 'monitoring'
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="", method=RequestMethod.GET)
	public String main() throws Exception {
		return basePath + "jsCompile";
	}
	
}