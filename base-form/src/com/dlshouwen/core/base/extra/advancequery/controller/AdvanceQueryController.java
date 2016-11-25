package com.dlshouwen.core.base.extra.advancequery.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dlshouwen.core.base.config.CONFIG;
import com.dlshouwen.core.base.extra.advancequery.dao.AdvanceQueryDao;
import com.dlshouwen.core.base.extra.advancequery.model.AdvanceQuery;
import com.dlshouwen.core.base.extra.advancequery.model.AdvanceQueryCondition;
import com.dlshouwen.core.base.extra.advancequery.model.AdvanceQuerySort;
import com.dlshouwen.core.base.model.AjaxResponse;
import com.dlshouwen.core.base.model.SessionUser;
import com.dlshouwen.core.base.utils.AttributeUtils;
import com.dlshouwen.core.base.utils.GUID;

/**
 * 高级查询的处理类
 * @author 大连首闻科技有限公司
 * @version 2013-8-7 17:19:18
 */
@Controller
@RequestMapping("/core/base/extra/advance_query")
@SuppressWarnings("unchecked")
public class AdvanceQueryController {
	
	/** 数据操作对象 */
	private AdvanceQueryDao dao;
	
	/**
	 * 注入数据操作对象
	 * @param dao 数据操作对象
	 */
	@Resource(name="advanceQueryDao")
	public void setDao(AdvanceQueryDao dao){
		this.dao = dao;
	}
	
	/**
	 * 获取方案数据
	 * @param request 请求对象
	 * @param functionCode 功能代码
	 * @param model 反馈对象
	 * @return 方案数据
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="/list", method=RequestMethod.POST)
	@ResponseBody
	public List<AdvanceQuery> getAdvanceQuery(HttpServletRequest request, String functionCode, Model model) throws Exception {
//		获取当前用户编号
		SessionUser sessionUser = (SessionUser)request.getSession().getAttribute(CONFIG.SESSION_USER);
		String userId = sessionUser.getUser_id();
//		获取高级查询是否过滤登录用户
		String advance_query_is_filter_user = AttributeUtils.getAttributeContent(request.getServletContext(), "advance_query_is_filter_user");
//		查询所有的方案列表映射为String发送到前台
		List<AdvanceQuery> advanceQueryList = dao.getAdvanceQueryList(functionCode, userId, advance_query_is_filter_user);
//		跳转到对应页面
		return advanceQueryList;
	}
	
	/**
	 * 获取高级查询数据
	 * @param advanceQueryId 方案编号
	 * @return 高级查询数据
	 */
	@RequestMapping(value="/info/data", method=RequestMethod.POST)
	@ResponseBody
	public AdvanceQuery getAdvanceQueryConditionList(String advanceQueryId) throws Exception{
//		根据方案编号获取方案信息
		AdvanceQuery advanceQuery = dao.getAdvanceQuery(advanceQueryId);
//		根据方案编号获取高级查询条件列表数据
		List<AdvanceQueryCondition> advanceQueryConditionList = dao.getAdvanceQueryConditionList(advanceQueryId);
		advanceQuery.setAdvanceQueryConditionList(advanceQueryConditionList);
//		根据方案编号获取高级查询排序列表数据
		List<AdvanceQuerySort> advanceQuerySortList = dao.getAdvanceQuerySortList(advanceQueryId);
		advanceQuery.setAdvanceQuerySortList(advanceQuerySortList);
		return advanceQuery;
	}
	
	/**
	 * 新增高级查询方案
	 * @param advanceQuery 方案对象
	 * @param bindingResult 方案绑定的结果信息
	 * @param request 请求对象
	 * @return ajax响应反馈
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="/add", method=RequestMethod.POST)
	@ResponseBody
	@Transactional
	public AjaxResponse addAdvanceQuery(@RequestBody @Valid AdvanceQuery advanceQuery, BindingResult bindingResult,
			HttpServletRequest request) throws Exception {
//		定义ajax响应对象
		AjaxResponse ajaxResponse = new AjaxResponse();
//		如果发现错误则拼接错误信息执行回执
		if(bindingResult.hasErrors()){
			ajaxResponse.bindingResultHandler(bindingResult);
			return ajaxResponse;
		}
//		获取当前用户、当前时间
		SessionUser sessionUser = (SessionUser)request.getSession().getAttribute(CONFIG.SESSION_USER);
		String userId = sessionUser.getUser_id();
		Date nowDate = new Date();
//		获取设置方案号、创建人、创建时间、编辑人、编辑时间
		String advanceQueryId = new GUID().toString();
		advanceQuery.setAdvance_query_id(advanceQueryId);
		advanceQuery.setCreator(userId);
		advanceQuery.setCreate_time(nowDate);
		advanceQuery.setEditor(userId);
		advanceQuery.setEdit_time(nowDate);
//		插入快速查询方案
		dao.insertAdvanceQuery(advanceQuery);
//		定义排序码
		int conditionSort = 0;
//		遍历列表依次插入快速查询条件
		List<AdvanceQueryCondition> conditionList = advanceQuery.getAdvanceQueryConditionList();
		for(AdvanceQueryCondition condition : conditionList){
//			获取设置查询编号、方案编号、排序号
			String conditionId = new GUID().toString();
			condition.setCondition_id(conditionId);
			condition.setAdvance_query_id(advanceQueryId);
			condition.setSort(conditionSort);
//			插入快速查询数据
			dao.insertAdvanceQueryCondition(condition);
//			排序自增
			conditionSort++;
		}
//		定义排序码
		int sortSort = 0;
//		遍历列表依次插入快速查询排序
		List<AdvanceQuerySort> sortList = advanceQuery.getAdvanceQuerySortList();
		for(AdvanceQuerySort sort : sortList){
//			获取设置条件编号、方案编号、排序号
			String sortId = new GUID().toString();
			sort.setSort_id(sortId);
			sort.setAdvance_query_id(advanceQueryId);
			sort.setSort(sortSort);
//			插入快速查询数据
			dao.insertAdvanceQuerySort(sort);
//			排序自增
			sortSort++;
		}
//		设置成功信息
		ajaxResponse.setSuccess(true);
		ajaxResponse.setSuccessMessage("新增查询方案成功！");
		return ajaxResponse;
	}
	
	/**
	 * 编辑高级查询方案
	 * @param advanceQuery 方案对象
	 * @param bindingResult 方案绑定的结果信息
	 * @param request 请求对象
	 * @return ajax响应反馈
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	@ResponseBody
	@Transactional
	public AjaxResponse editAdvanceQuery(@RequestBody @Valid AdvanceQuery advanceQuery, BindingResult bindingResult,
			HttpServletRequest request) throws Exception {
//		定义ajax响应对象
		AjaxResponse ajaxResponse = new AjaxResponse();
//		如果发现错误则拼接错误信息执行回执
		if(bindingResult.hasErrors()){
			ajaxResponse.bindingResultHandler(bindingResult);
			return ajaxResponse;
		}
//		获取当前用户、当前时间
		SessionUser sessionUser = (SessionUser)request.getSession().getAttribute(CONFIG.SESSION_USER);
		String userId = sessionUser.getUser_id();
		Date nowDate = new Date();
//		获取设置方案号、创建人、创建时间、编辑人、编辑时间
		advanceQuery.setEditor(userId);
		advanceQuery.setEdit_time(nowDate);
//		编辑快速查询方案
		dao.updateAdvanceQuery(advanceQuery);
//		删除所有的快速查询条件记录
		dao.deleteAllAdvanceQueryCondition(advanceQuery.getAdvance_query_id());
//		删除所有的快速查询排序记录
		dao.deleteAllAdvanceQuerySort(advanceQuery.getAdvance_query_id());
//		定义排序码
		int conditionSort = 0;
//		遍历列表依次插入快速查询条件
		List<AdvanceQueryCondition> conditionList = advanceQuery.getAdvanceQueryConditionList();
		for(AdvanceQueryCondition condition : conditionList){
//			获取设置查询编号、方案编号、排序号
			String conditionId = new GUID().toString();
			condition.setCondition_id(conditionId);
			condition.setAdvance_query_id(advanceQuery.getAdvance_query_id());
			condition.setSort(conditionSort);
//			插入快速查询数据
			dao.insertAdvanceQueryCondition(condition);
//			排序自增
			conditionSort++;
		}
//		定义排序码
		int sortSort = 0;
//		遍历列表依次插入快速查询排序
		List<AdvanceQuerySort> sortList = advanceQuery.getAdvanceQuerySortList();
		for(AdvanceQuerySort sort : sortList){
//			获取设置条件编号、方案编号、排序号
			String sortId = new GUID().toString();
			sort.setSort_id(sortId);
			sort.setAdvance_query_id(advanceQuery.getAdvance_query_id());
			sort.setSort(sortSort);
//			插入快速查询数据
			dao.insertAdvanceQuerySort(sort);
//			排序自增
			sortSort++;
		}
//		设置成功信息
		ajaxResponse.setSuccess(true);
		ajaxResponse.setSuccessMessage("查询方案编辑成功！");
		return ajaxResponse;
	}
	
	/**
	 * 删除高级查询方案
	 * @param advanceQueryId 方案编号
	 * @return ajax响应反馈
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	@ResponseBody
	@Transactional
	public AjaxResponse deleteAdvanceQuery(String advanceQueryId) throws Exception {
//		定义ajax响应对象
		AjaxResponse ajaxResponse = new AjaxResponse();
//		删除快速查询方案
		dao.deleteAdvanceQuery(advanceQueryId);
//		删除所有的快速查询条件记录
		dao.deleteAllAdvanceQueryCondition(advanceQueryId);
//		删除所有的快速查询排序记录
		dao.deleteAllAdvanceQuerySort(advanceQueryId);
//		设置成功信息
		ajaxResponse.setSuccess(true);
		ajaxResponse.setSuccessMessage("查询方案删除成功！");
		return ajaxResponse;
	}
	
	/**
	 * 复制高级查询方案
	 * @param request 请求对象
	 * @param advanceQueryId 方案编号
	 * @return ajax响应反馈
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="/copy", method=RequestMethod.POST)
	@ResponseBody
	@Transactional
	public AjaxResponse copyAdvanceQuery(HttpServletRequest request, String advanceQueryId) throws Exception {
//		定义ajax响应对象
		AjaxResponse ajaxResponse = new AjaxResponse();
//		获取登录用户
		SessionUser sessionUser = (SessionUser)request.getSession().getAttribute(CONFIG.SESSION_USER);
//		获取新的方案编号、创建人、创建时间
		String newAdvanceQueryId = new GUID().toString();
		String userId = sessionUser.getUser_id();
		Date nowDate = new Date();
//		复制快速查询方案
		dao.copyAdvanceQuery(advanceQueryId, newAdvanceQueryId, userId, nowDate, userId, nowDate);
//		根据方案编号获取高级查询条件列表数据
		List<AdvanceQueryCondition> conditionList = dao.getAdvanceQueryConditionList(advanceQueryId);
//		遍历查询条件列表数据
		for(AdvanceQueryCondition condition : conditionList){
//			获取查询编号、新的查询编号
			String conditionId = condition.getCondition_id();
			String newConditionId = new GUID().toString();
//			复制快速查询条件数据
			dao.copyAdvanceQueryCondition(conditionId, newConditionId, newAdvanceQueryId);
		}
//		根据方案编号获取高级查询排序列表数据
		List<AdvanceQuerySort> sortList = dao.getAdvanceQuerySortList(advanceQueryId);
//		遍历查询排序列表数据
		for(AdvanceQuerySort sort : sortList){
//			获取查询编号、新的查询编号
			String sortId = sort.getSort_id();
			String newSortId = new GUID().toString();
//			复制快速查询排序数据
			dao.copyAdvanceQuerySort(sortId, newSortId, newAdvanceQueryId);
		}
//		设置成功信息
		ajaxResponse.setSuccess(true);
		ajaxResponse.setSuccessMessage("复制查询方案成功！");
		return ajaxResponse;
	}
	
	/**
	 * 获取Codetable的内容
	 * @param context 上下文
	 * @param key 键值
	 * @return 对应的codetable内容
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="/get_code_table", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getCodeTable(HttpServletRequest request, String key) throws Exception {
//		定义返回的codetable内容
		Map<String, Object> codeTableInfo = new TreeMap<String, Object>();
//		根据Key获取codetable
		codeTableInfo = (Map<String, Object>)((Map<String, Object>)request.getServletContext().getAttribute(CONFIG.CODE_TABLE)).get(key);
		return codeTableInfo;
	}
	
}
