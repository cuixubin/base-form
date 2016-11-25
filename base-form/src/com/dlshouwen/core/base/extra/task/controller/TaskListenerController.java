package com.dlshouwen.core.base.extra.task.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dlshouwen.core.base.config.CONFIG;
import com.dlshouwen.core.base.extra.task.dao.TaskListenerDao;
import com.dlshouwen.core.base.extra.task.utils.TaskUtils;
import com.dlshouwen.core.base.model.OperationType;
import com.dlshouwen.core.base.model.SessionUser;
import com.dlshouwen.core.base.utils.LogUtils;

/**
 * 任务监听类
 * @author 大连首闻科技有限公司
 * @version 2013-11-20 9:45:26
 */
@Controller
@RequestMapping("/core/base/extra/task")
public class TaskListenerController {
	
	/** 功能根路径 */
	private TaskListenerDao dao;
	
	/**
	 * 注入数据操作对象
	 * @param dao 数据操作对象
	 */
	@Resource(name="taskListenerDao")
	public void setDao(TaskListenerDao dao){
		this.dao = dao;
	}
	
	/**
	 * 处理系统任务监听
	 * @param request 请求对象
	 * @return 监听列表
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="/listener", method=RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> listener(HttpServletRequest request) throws Exception {
//		获取用户编号
		SessionUser sessionUser = (SessionUser)request.getSession().getAttribute(CONFIG.SESSION_USER);
		String userId = sessionUser.getUser_id();
//		获取监听列表
		List<Map<String, Object>> taskList = dao.getLoginUserTaskList(userId);
//		记录操作日志
		LogUtils.updateOperationLog(request, OperationType.SEARCH, "获取系统任务列表");
		return taskList;
	}
	
	/**
	 * 获取任务信息
	 * @param taskId 任务编号
	 * @param request 请求对象
	 * @return 监听列表
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="/listener/{taskId}/info", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> taskInfo(@PathVariable String taskId, HttpServletRequest request) throws Exception {
//		根据任务编号获取任务信息
		Map<String, Object> taskInfo = dao.getTaskInfo(taskId);
//		如果要是设置了本次不显示则将参数放置进去
		String isHideOnThisSession = "0";
		SessionUser sessionUser = (SessionUser)request.getSession().getAttribute(CONFIG.SESSION_USER);
		Map<String, Object> hideOnThisSessionTasks = sessionUser.getHideOnThisSessionTasks();
		if(hideOnThisSessionTasks!=null&&hideOnThisSessionTasks.containsKey(taskId)){
			isHideOnThisSession = "1";
		}
		taskInfo.put("isHideOnThisSession", isHideOnThisSession);
//		定义是否显示，如果出错了也不显示
		String isShow = "1";
		try{
//			判断此任务是否触发
			String detonateSql = MapUtils.getString(taskInfo, "detonate_sql");
			detonateSql = TaskUtils.getTaskSqlByTemplate(detonateSql, sessionUser);
			boolean isDetonate = dao.queryForInt(detonateSql)>0?true:false;
			if(isDetonate){
//				通过正则表达式赋值参数
				String message = MapUtils.getString(taskInfo, "message");
				message = message.replaceAll("\\{key\\}", "<span class='highlight'>");
				message = message.replaceAll("\\{/key\\}", "</span>");
				List<Map<String, Object>> taskAttrList = dao.getTaskAttrList(taskId);
				Matcher matcher = Pattern.compile("\\{[1-9][0-9]*\\}").matcher(message);
				while(matcher.find()){
					String attr = matcher.group();
					int seq = Integer.parseInt(attr.replace("{", "").replace("}", "").trim());
					String attrSql = MapUtils.getString((Map<String, Object>)taskAttrList.get(seq-1), "attr_sql");
					String attrValue = dao.queryForObject(TaskUtils.getTaskSqlByTemplate(attrSql, sessionUser), String.class);
					message = message.replaceAll("\\{"+seq+"\\}", "<span class='text-danger'><strong>&nbsp;"+attrValue+"&nbsp;</strong></span>");
				}
				taskInfo.put("message", message);
			}else{
				isShow = "0";
			}
		}catch(Exception e){
			isShow = "0";
			e.printStackTrace();
		}
		taskInfo.put("isShow", isShow);
//		记录操作日志
		LogUtils.updateOperationLog(request, OperationType.SEARCH, "获取任务信息，任务编号："+taskId);
		return taskInfo;
	}
	
	/**
	 * 设置本次回话不再提示
	 * @param taskId 任务编号
	 * @param request 请求对象
	 * @return null
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="/listener/{taskId}/hide", method=RequestMethod.POST)
	@ResponseBody
	public String hideOnThisSession(@PathVariable String taskId, HttpServletRequest request) throws Exception {
		SessionUser sessionUser = (SessionUser)request.getSession().getAttribute(CONFIG.SESSION_USER);
		Map<String, Object> hideOnThisSessionTasks = sessionUser.getHideOnThisSessionTasks();
		hideOnThisSessionTasks = hideOnThisSessionTasks==null?new HashMap<String, Object>():hideOnThisSessionTasks;
		hideOnThisSessionTasks.put(taskId, "true");
		sessionUser.setHideOnThisSessionTasks(hideOnThisSessionTasks);
		request.getSession().setAttribute(CONFIG.SESSION_USER, sessionUser);
		return null;
	}
	
}
