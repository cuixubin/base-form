package com.dlshouwen.core.tools.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dlshouwen.core.base.config.CONFIG;
import com.dlshouwen.core.base.utils.DateUtils;
import com.dlshouwen.core.log.model.LoginLog;
import com.dlshouwen.core.log.model.OperationLog;
import com.dlshouwen.core.log.model.SqlLog;
import com.dlshouwen.core.tools.dao.MonitoringDao;

/**
 * 系统监控
 * @author 大连首闻科技有限公司
 * @version 2013-11-20 16:45:32
 */
@Controller
@RequestMapping("/core/tools/monitoring")
@SuppressWarnings("unchecked")
public class MonitoringController {
	
	/** 功能根路径 */
	private String basePath = "core/tools/monitoring/";

	/** 数据操作对象 */
	private MonitoringDao dao;

	/**
	 * 注入数据操作对象
	 * @param dao 数据操作对象
	 */
	@Resource(name="monitoringDao")
	public void setDao(MonitoringDao dao) {
		this.dao = dao;
	}
	
	/**
	 * 跳转到监控主页面
	 * @return basePath + 'monitoring'
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="", method=RequestMethod.GET)
	public String goMonitoringPage() throws Exception {
		return basePath + "monitoring";
	}
	
	/**
	 * 获取登录信息发送到前台
	 * @param lastTime 上次获取时间
	 * @return Ajax反馈数据
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="/login", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> loginData(String lastTime) throws Exception {
//		获取当前时间
		String nowTime = DateUtils.getNowTimeHaveMS();
//		如果上次获取时间为空则返回开始时间数据
		if("".equals(lastTime)){
			Map<String, Object> resultInfo = new HashMap<String, Object>();
			resultInfo.put("lastTime", nowTime);
			return resultInfo;
		}
//		获取登录、登出信息列表
		List<LoginLog> loginLogList = dao.getLoginLogList(lastTime, nowTime);
//		将开始时间信息、数据信息发送到前台
		Map<String, Object> resultInfo = new HashMap<String, Object>();
		resultInfo.put("lastTime", nowTime);
		resultInfo.put("loginLogList", loginLogList);
		return resultInfo;
	}
	
	/**
	 * 获取操作信息发送到前台
	 * @param lastTime 上次获取时间
	 * @return Ajax反馈数据
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="/operation", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> operationData(String lastTime, HttpServletRequest request) throws Exception {
//		获取当前时间
		String nowTime = DateUtils.getNowTimeHaveMS();
//		如果上次获取时间为空则返回开始时间数据
		if("".equals(lastTime)){
			Map<String, Object> resultInfo = new HashMap<String, Object>();
			resultInfo.put("lastTime", nowTime);
			return resultInfo;
		}
//		获取登录、登出信息列表
		List<OperationLog> operationLogList = dao.getOperationLogList(lastTime, nowTime);
//		从后往前找application中的内容
		List<OperationLog> applicationOperationLogList = (List<OperationLog>)request.getServletContext().getAttribute(CONFIG.OPERATION_LOG_APN);
//		定义时间格式化组件
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
//		查找时间匹配项目
		for(int i=applicationOperationLogList.size()-1; i>=0; i--){
			OperationLog operationLog = applicationOperationLogList.get(i);
//			如果匹配上了则追加到最前，否则直接跳出
			if(operationLog.getResponse_start().before(sdf.parse(nowTime))){
				operationLogList.add(0, operationLog);
			}else{
				break;
			}
		}
//		将开始时间信息、数据信息发送到前台
		Map<String, Object> resultInfo = new HashMap<String, Object>();
		resultInfo.put("lastTime", nowTime);
		resultInfo.put("operationLogList", operationLogList);
		return resultInfo;
	}
	
	/**
	 * 获取SQL信息发送到前台
	 * @param lastTime 上次获取时间
	 * @return Ajax反馈数据
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="/sql", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> sqlData(String lastTime, HttpServletRequest request) throws Exception {
//		获取当前时间
		String nowTime = DateUtils.getNowTimeHaveMS();
//		如果上次获取时间为空则返回开始时间数据
		if("".equals(lastTime)){
			Map<String, Object> resultInfo = new HashMap<String, Object>();
			resultInfo.put("lastTime", nowTime);
			return resultInfo;
		}
//		获取登录、登出信息列表
		List<SqlLog> sqlLogList = dao.getSqlLogList(lastTime, nowTime);
//		从后往前找application中的内容
		List<SqlLog> applicationSqlLogList = (List<SqlLog>)request.getServletContext().getAttribute(CONFIG.SQL_LOG_APN);
//		定义时间格式化组件
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
//		查找时间匹配项目
		for(int i=applicationSqlLogList.size()-1; i>=0; i--){
			SqlLog sqlLog = applicationSqlLogList.get(i);
//			如果匹配上了则追加到最前，否则直接跳出
			if(sqlLog.getStart_time().before(sdf.parse(nowTime))){
				sqlLogList.add(0, sqlLog);
			}else{
				break;
			}
		}
//		将开始时间信息、数据信息发送到前台
		Map<String, Object> resultInfo = new HashMap<String, Object>();
		resultInfo.put("lastTime", nowTime);
		resultInfo.put("sqlLogList", sqlLogList);
		return resultInfo;
	}
	
}