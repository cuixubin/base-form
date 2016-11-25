package com.dlshouwen.core.task.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dlshouwen.core.base.config.CONFIG;
import com.dlshouwen.core.base.controller.BaseController;
import com.dlshouwen.core.base.extra.task.utils.TaskUtils;
import com.dlshouwen.core.base.model.OperationType;
import com.dlshouwen.core.base.model.SessionUser;
import com.dlshouwen.core.base.utils.LogUtils;
import com.dlshouwen.core.task.dao.ViewTaskDao;
import com.dlshouwen.core.task.model.Task;
import com.dlshouwen.core.task.model.TaskAttr;

import net.sf.json.JSONArray;

/**
 * 任务查看
 * @author 大连首闻科技有限公司
 * @version 2013-08-17 08:04:29 322
 */
@Controller
@RequestMapping("/core/task/view_task")
public class ViewTaskController extends BaseController {
	
	/** 功能根路径 */
	private String basePath = "core/task/viewTask/";

	/** 数据操作对象 */
	private ViewTaskDao dao;

	/**
	 * 注入数据操作对象
	 * @param dao 数据操作对象
	 */
	@Resource(name="viewTaskDao")
	public void setDao(ViewTaskDao dao) {
		this.dao = dao;
	}
	
	/**
	 * 跳转到查看任务页面
	 * @param request 请求对象
	 * @return basePath + 'viewTaskList'
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="", method=RequestMethod.GET)
	public String goViewTaskPage(HttpServletRequest request) throws Exception {
//		记录操作日志
		LogUtils.updateOperationLog(request, OperationType.VISIT, "访问查看任务页面");
		return basePath + "viewTaskList";
	}
	
	/**
	 * 获取任务数据
	 * @param gridPager 表格分页信息
	 * @param request 请求对象
	 * @param response 响应对象
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="/list", method=RequestMethod.POST)
	public void getViewTaskData(String gridPager, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		定义数据结果集
		List<Map<String, Object>> recordList = new ArrayList<Map<String, Object>>();
//		获取用户编号
		SessionUser sessionUser = (SessionUser)request.getSession().getAttribute(CONFIG.SESSION_USER);
		String userId = sessionUser.getUser_id();
//		获取当前用户的监听列表
		List<Task> taskList = dao.getTaskList(userId);
//		遍历获取监听列表
		for(Task task : taskList){
			Map<String, Object> taskInfo = new HashMap<String, Object>();
			String taskId = task.getTask_id();
//			定义是否显示，如果出错了也不显示
			String isShow = "1";
			try{
//				判断此任务是否触发
				String detonateSql = task.getDetonate_sql();
				detonateSql = TaskUtils.getTaskSqlByTemplate(detonateSql, sessionUser);
				boolean isDetonate = dao.queryForInt(detonateSql)>0?true:false;
				if(isDetonate){
//					通过正则表达式赋值参数
					String message = task.getMessage();
					message = message.replaceAll("\\{key\\}", "<span class='taskKey'>");
					message = message.replaceAll("\\{/key\\}", "</span>");
					List<TaskAttr> taskAttrList = dao.getTaskAttrList(taskId);
					Matcher matcher = Pattern.compile("\\{[1-9][0-9]*\\}").matcher(message);
					while(matcher.find()){
						String attr = matcher.group();
						int seq = Integer.parseInt(attr.replace("{", "").replace("}", "").trim());
						String attrSql = taskAttrList.get(seq-1).getAttr_sql();
						String attrValue = dao.queryForObject(TaskUtils.getTaskSqlByTemplate(attrSql, sessionUser), String.class);
						message = message.replaceAll("\\{"+seq+"\\}", "<span class='text-danger'><strong>"+attrValue+"</strong></span>");
					}
					taskInfo.put("task_id", task.getTask_id());
					taskInfo.put("limit_id", task.getLimit_id());
					taskInfo.put("limit_name", task.getLimit_name());
					taskInfo.put("url", task.getUrl());
					taskInfo.put("message", message);
					taskInfo.put("isShow", isShow);
					recordList.add(taskInfo);
				}else{
					isShow = "0";
				}
			}catch(Exception e){
				isShow = "0";
			}
			taskInfo.put("isShow", isShow);
		}
//		转换为JSON数据格式
		JSONArray recordDatas = JSONArray.fromObject(recordList);
//		回写数据
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(recordDatas.toString());
//		记录操作日志
		LogUtils.updateOperationLog(request, OperationType.SEARCH, "获取查看任务页面中需要处理的任务列表");
	}

}