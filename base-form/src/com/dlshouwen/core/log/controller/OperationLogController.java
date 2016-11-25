package com.dlshouwen.core.log.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import com.dlshouwen.core.base.config.CONFIG;
import com.dlshouwen.core.base.extra.grid.model.Condition;
import com.dlshouwen.core.base.extra.grid.model.Pager;
import com.dlshouwen.core.base.extra.grid.model.Sort;
import com.dlshouwen.core.base.extra.grid.utils.PagerPropertyUtils;
import com.dlshouwen.core.base.model.AjaxResponse;
import com.dlshouwen.core.base.model.OperationType;
import com.dlshouwen.core.base.utils.LogUtils;
import com.dlshouwen.core.log.dao.OperationLogDao;
import com.dlshouwen.core.log.model.OperationLog;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 操作日志统计
 * @author 大连首闻科技有限公司
 * @version 2014-11-10 13:50:10 932
 */
@Controller
@RequestMapping("/core/log/operation_log")
@SuppressWarnings({ "unchecked", "deprecation" })
public class OperationLogController {
	
	/** 功能根路径 */
	private String basePath = "core/log/operationLog/";

	/** 数据操作对象 */
	private OperationLogDao dao;

	/**
	 * 注入数据操作对象
	 * @param dao 数据操作对象
	 */
	@Resource(name="operationLogDao")
	public void setDao(OperationLogDao dao) {
		this.dao = dao;
	}

	/**
	 * 跳转到操作日志统计页面
	 * @param request 请求对象
	 * @return basePath + 'operationLogMain'
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="", method=RequestMethod.GET)
	public String goOperationLogMain(HttpServletRequest request) throws Exception {
//		记录操作日志
		LogUtils.updateOperationLog(request, OperationType.VISIT, "访问操作日志统计页面");
		return basePath + "operationLogMain";
	}
	
	/**
	 * 获取操作日志列表数据
	 * @param gridPager 表格分页信息
	 * @param request 请求对象
	 * @param response 响应对象
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="/list", method=RequestMethod.POST)
	public void getOperationLogList(String gridPager, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		映射Pager对象
		Pager pager = PagerPropertyUtils.copy(JSONObject.fromObject(gridPager));
//		获取操作日志列表数据
		dao.getOperationLogList(pager, request, response);
//		记录操作日志
		LogUtils.updateOperationLog(request, OperationType.SEARCH, "获取操作日志列表数据");
	}
	
	/**
	 * 查看操作日志
	 * @param logId 日志编号
	 * @param request 请求对象
	 * @param model 反馈对象
	 * @return basePath + 'viewOperationLog'
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="/{logId}/view", method=RequestMethod.GET)
	public String viewOperationLog(@PathVariable String logId, HttpServletRequest request, Model model) throws Exception {
//		根据日志编号获取日志详细信息
		OperationLog operationLog = dao.getOperationLogById(logId);
//		如果数据库中没有则查找缓存区
		if(operationLog==null){
//			从后往前找application中的内容
			List<OperationLog> applicationOperationLogList = (List<OperationLog>)request.getServletContext().getAttribute(CONFIG.OPERATION_LOG_APN);
//			查找时间匹配项目
			for(int i=0; i<applicationOperationLogList.size(); i++){
				OperationLog _operationLog = applicationOperationLogList.get(i);
//				如果匹配上了则设置
				if(_operationLog.getLog_id().equals(logId)){
					operationLog = _operationLog;
					break;
				}
			}
		}
//		如果未被收录
		if(operationLog==null){
			operationLog = new OperationLog();
			operationLog.setError_reason("日志未被收录");
		}else{
//			转义HTML代码
			operationLog.setError_reason(HtmlUtils.htmlEscape(operationLog.getError_reason()));
		}
//		传递到前台
		model.addAttribute("operationLog", operationLog);
//		记录操作日志
		LogUtils.updateOperationLog(request, OperationType.VISIT, "查看操作日志，日志编号："+logId);
		return basePath + "viewOperationLog";
	}
	
	/**
	 * 获取操作日志图表数据
	 * @param request 请求对象
	 * @return 操作日志图表数据
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="/chart/data", method=RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getOperationLogChartData(HttpServletRequest request) throws Exception {
//		获取数据类型
		String dataType = request.getParameter("data_type");
//		获取查询参数
		String fastQueryParametersJSON = request.getParameter("fastQueryParameters");
		String advanceQueryConditionsJSON = request.getParameter("advanceQueryConditions");
		String advanceQuerySortsJSON = request.getParameter("advanceQuerySorts");
		Map<String, Object> fastQueryParameters = (Map<String, Object>)JSONObject.toBean(JSONObject.fromObject(fastQueryParametersJSON), Map.class);
		List<Condition> advanceQueryConditions = JSONArray.toList(JSONArray.fromObject(advanceQueryConditionsJSON), Condition.class);
		List<Sort> advanceQuerySorts = JSONArray.toList(JSONArray.fromObject(advanceQuerySortsJSON), Sort.class);
//		记录操作日志
		LogUtils.updateOperationLog(request, OperationType.SEARCH, "获取操作日志图表数据");
		return dao.getChartDataList(dataType, fastQueryParameters, advanceQueryConditions, advanceQuerySorts);
	}
	
	/**
	 * 清空操作日志
	 * @return AJAX响应回执
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="/clear", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse clearOperationLog(HttpServletRequest request) throws Exception {
//		获取查询参数
		String fastQueryParametersJSON = request.getParameter("fastQueryParameters");
		String advanceQueryConditionsJSON = request.getParameter("advanceQueryConditions");
		String advanceQuerySortsJSON = request.getParameter("advanceQuerySorts");
		Map<String, Object> fastQueryParameters = (Map<String, Object>)JSONObject.toBean(JSONObject.fromObject(fastQueryParametersJSON), Map.class);
		List<Condition> advanceQueryConditions = JSONArray.toList(JSONArray.fromObject(advanceQueryConditionsJSON), Condition.class);
		List<Sort> advanceQuerySorts = JSONArray.toList(JSONArray.fromObject(advanceQuerySortsJSON), Sort.class);
//		执行清空操作日志
		dao.clearOperationLog(fastQueryParameters, advanceQueryConditions, advanceQuerySorts);
//		执行响应回执
		AjaxResponse ajaxResponse = new AjaxResponse();
		ajaxResponse.setSuccess(true);
		ajaxResponse.setSuccessMessage("操作日志清除成功！");
		return ajaxResponse;
	}

}