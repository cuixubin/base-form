package com.dlshouwen.core.base.extra.fastquery.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dlshouwen.core.base.extra.fastquery.dao.FastQueryDao;
import com.dlshouwen.core.base.model.OperationType;
import com.dlshouwen.core.base.utils.LogUtils;

/**
 * 快速查询的处理类
 * @author 大连首闻科技有限公司
 * @version 2013-8-7 17:19:18
 */
@Controller
@RequestMapping("/core/base/extra/fast_query")
public class FastQueryController {
	
	/** 功能根路径 */
	private String basePath = "core/base/extra/fastQuery/";
	
	/** 数据操作对象 */
	@SuppressWarnings("unused")
	private FastQueryDao dao;
	
	/**
	 * 注入数据操作对象
	 * @param dao 数据操作对象
	 */
	@Resource(name="fastQueryDao")
	public void setDao(FastQueryDao dao){
		this.dao = dao;
	}
	
	/**
	 * 跳转到快速查询主页面
	 * @param request 请求对象
	 * @return basePath + 'fastQuery'
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="", method=RequestMethod.GET)
	public String goFastQueryPage(HttpServletRequest request) throws Exception {
//		记录操作日志
		LogUtils.updateOperationLog(request, OperationType.VISIT, "访问快速查询页面");
		return basePath + "fastQuery";
	}
	
}
