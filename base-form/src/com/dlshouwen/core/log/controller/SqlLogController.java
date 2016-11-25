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
import com.dlshouwen.core.log.dao.SqlLogDao;
import com.dlshouwen.core.log.model.SqlLog;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * SQL日志统计
 * @author 大连首闻科技有限公司
 * @version 2014-11-10 13:50:10 942
 */
@Controller
@RequestMapping("/core/log/sql_log")
@SuppressWarnings({ "unchecked", "deprecation" })
public class SqlLogController {
	
	/** 功能根路径 */
	private String basePath = "core/log/sqlLog/";

	/** 数据操作对象 */
	private SqlLogDao dao;

	/**
	 * 注入数据操作对象
	 * @param dao 数据操作对象
	 */
	@Resource(name="sqlLogDao")
	public void setDao(SqlLogDao dao) {
		this.dao = dao;
	}
	
	/**
	 * 跳转到SQL日志统计页面
	 * @param request 请求对象
	 * @return basePath + 'sqlLogMain'
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="", method=RequestMethod.GET)
	public String goSqlLogMain(HttpServletRequest request) throws Exception {
//		记录操作日志
		LogUtils.updateOperationLog(request, OperationType.VISIT, "访问SQL日志统计页面");
		return basePath + "sqlLogMain";
	}
	
	/**
	 * 获取SQL日志列表数据
	 * @param gridPager 表格分页信息
	 * @param request 请求对象
	 * @param response 响应对象
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="/list", method=RequestMethod.POST)
	public void getSqlLogList(String gridPager, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		映射Pager对象
		Pager pager = PagerPropertyUtils.copy(JSONObject.fromObject(gridPager));
//		获取SQL日志列表数据
		dao.getSqlLogList(pager, request, response);
//		记录操作日志
		LogUtils.updateOperationLog(request, OperationType.SEARCH, "获取SQL日志列表数据");
	}
	
	/**
	 * 查看SQL日志
	 * @param logId 日志编号
	 * @param request 请求对象
	 * @param model 反馈对象
	 * @return basePath + 'viewSqlLog'
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="/{logId}/view", method=RequestMethod.GET)
	public String viewSqlLog(@PathVariable String logId, HttpServletRequest request, Model model) throws Exception {
//		根据日志编号获取日志详细信息
		SqlLog sqlLog = dao.getSqlLogById(logId);
//		如果数据库中没有则查找缓存区
		if(sqlLog==null){
//			从后往前找application中的内容
			List<SqlLog> applicationSqlLogList = (List<SqlLog>)request.getServletContext().getAttribute(CONFIG.SQL_LOG_APN);
//			查找时间匹配项目
			for(int i=0; i<applicationSqlLogList.size(); i++){
				SqlLog _sqlLog = applicationSqlLogList.get(i);
//				如果匹配上了则设置
				if(_sqlLog.getLog_id().equals(logId)){
					sqlLog = _sqlLog;
					break;
				}
			}
		}
//		如果日志未被收录
		if(sqlLog==null){
			sqlLog = new SqlLog();
			sqlLog.setError_reason("日志未被收录");
		}else{
//			转义HTML代码
			sqlLog.setResult_type(HtmlUtils.htmlEscape(sqlLog.getResult_type()));
		}
//		传递到前台
		model.addAttribute("sqlLog", sqlLog);
//		记录操作日志
		LogUtils.updateOperationLog(request, OperationType.VISIT, "查看SQL日志，日志编号："+logId);
		return basePath + "viewSqlLog";
	}
	
	/**
	 * 获取SQL日志图表数据
	 * @param request 请求对象
	 * @return 操作SQL图表数据
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="/chart/data", method=RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getSqlLogChartData(HttpServletRequest request) throws Exception {
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
		LogUtils.updateOperationLog(request, OperationType.SEARCH, "获取SQL日志图表数据");
		return dao.getChartDataList(dataType, fastQueryParameters, advanceQueryConditions, advanceQuerySorts);
	}
	
	/**
	 * 清空SQL日志
	 * @return AJAX响应回执
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="/clear", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse clearSqlLog(HttpServletRequest request) throws Exception {
//		获取查询参数
		String fastQueryParametersJSON = request.getParameter("fastQueryParameters");
		String advanceQueryConditionsJSON = request.getParameter("advanceQueryConditions");
		String advanceQuerySortsJSON = request.getParameter("advanceQuerySorts");
		Map<String, Object> fastQueryParameters = (Map<String, Object>)JSONObject.toBean(JSONObject.fromObject(fastQueryParametersJSON), Map.class);
		List<Condition> advanceQueryConditions = JSONArray.toList(JSONArray.fromObject(advanceQueryConditionsJSON), Condition.class);
		List<Sort> advanceQuerySorts = JSONArray.toList(JSONArray.fromObject(advanceQuerySortsJSON), Sort.class);
//		执行清空SQL日志
		dao.clearSqlLog(fastQueryParameters, advanceQueryConditions, advanceQuerySorts);
//		执行响应回执
		AjaxResponse ajaxResponse = new AjaxResponse();
		ajaxResponse.setSuccess(true);
		ajaxResponse.setSuccessMessage("SQL日志清除成功！");
		return ajaxResponse;
	}

}