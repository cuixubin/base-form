package com.dlshouwen.core.task.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dlshouwen.core.base.config.CONFIG;
import com.dlshouwen.core.base.extra.grid.model.Pager;
import com.dlshouwen.core.base.extra.grid.utils.PagerPropertyUtils;
import com.dlshouwen.core.base.model.AjaxResponse;
import com.dlshouwen.core.base.model.OperationType;
import com.dlshouwen.core.base.model.SessionUser;
import com.dlshouwen.core.base.utils.GUID;
import com.dlshouwen.core.base.utils.LogUtils;
import com.dlshouwen.core.task.dao.TaskDao;
import com.dlshouwen.core.task.model.Task;
import com.dlshouwen.core.task.model.TaskAttr;
import java.util.HashMap;

/**
 * 任务
 *
 * @author 大连首闻科技有限公司
 * @version 2013-08-17 08:04:29 316
 */
@Controller
@RequestMapping("/core/task/task")
public class TaskController {

    /**
     * 功能根路径
     */
    private String basePath = "core/task/task/";

    /**
     * 数据操作对象
     */
    private TaskDao dao;

    /**
     * 注入数据操作对象
     *
     * @param dao 数据操作对象
     */
    @Resource(name = "taskDao")
    public void setDao(TaskDao dao) {
        this.dao = dao;
    }

    /**
     * 跳转到任务主页面
     *
     * @param request 请求对象
     * @return basePath + 'taskList'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String goTaskPage(HttpServletRequest request) throws Exception {
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问任务管理页面");
        return basePath + "taskList";
    }

    /**
     * 获取任务列表数据
     *
     * @param gridPager 表格分页信息
     * @param request 请求对象
     * @param response 响应对象
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public void getTaskList(String gridPager, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		映射Pager对象
        Pager pager = PagerPropertyUtils.copy(JSONObject.fromObject(gridPager));
//		获取任务列表数据
        dao.getTaskList(pager, request, response);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.SEARCH, "获取任务管理页面任务列表数据");
    }

    /**
     * 跳转到新增任务页面
     *
     * @param model 反馈对象
     * @param request 请求对象
     * @return basePath + 'addTask'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addTask(Model model, HttpServletRequest request) throws Exception {
//		新增新的任务对象
        Task task = new Task();
//		设置默认启用、不是定时提醒、从不过期为是、全用户触发为否
        task.setStatus("1");
        task.setIs_timing("0");
        task.setIs_never_overdue("1");
        task.setIs_all_user("0");
//		传递任务对象
        model.addAttribute("task", task);
//		获取关联的用户列表映射为树传递到前台
        List<Map<String, Object>> userList = dao.getUserList();
        model.addAttribute("userTree", JSONArray.fromObject(userList));
//		获取关联的角色列表映射为树传递到前台
        List<Map<String, Object>> roleList = dao.getRoleList();
        model.addAttribute("roleTree", JSONArray.fromObject(roleList));
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问新增任务页面");
        return basePath + "addTask";
    }

    /**
     * 执行新增任务
     *
     * @param task 任务对象
     * @param bindingResult 绑定结果
     * @param request 请求对象
     * @return ajax响应内容
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public AjaxResponse addTask(@RequestBody @Valid Task task, BindingResult bindingResult,
            HttpServletRequest request) throws Exception {
//		定义AJAX响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
//		如果发现错误则拼接错误信息执行回执
        if (bindingResult.hasErrors()) {
            ajaxResponse.bindingResultHandler(bindingResult);
//			记录操作日志
            LogUtils.updateOperationLog(request, OperationType.INSERT, "新增任务（新增错误，任务名称：" + task.getTask_name() + "，错误信息：" + AjaxResponse.getBindingResultMessage(bindingResult) + "）");
            return ajaxResponse;
        }
//		验证提醒时间
        if ("1".equals(task.getIs_timing()) && task.getTiming_time() == null) {
//			反馈成功信息
            ajaxResponse.setWarning(true);
            ajaxResponse.setWarningMessage("提醒时间必须填写。");
//			记录操作日志
            LogUtils.updateOperationLog(request, OperationType.INSERT, "新增任务（新增错误，没有填写提醒时间）");
            return ajaxResponse;
        }
//		验证过期时间
        if ("1".equals(task.getIs_never_overdue()) && task.getOverdue_time() == null) {
//			反馈成功信息
            ajaxResponse.setWarning(true);
            ajaxResponse.setWarningMessage("过期时间必须填写。");
//			记录操作日志
            LogUtils.updateOperationLog(request, OperationType.INSERT, "新增任务（新增错误，没有填写过期时间）");
            return ajaxResponse;
        }
//		获取当前用户、当前时间
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        String userId = sessionUser.getUser_id();
        Date nowDate = new Date();
//		设置任务编号、创建人、创建时间、编辑人、编辑时间
        task.setTask_id(new GUID().toString());
        task.setCreator(userId);
        task.setCreate_time(nowDate);
        task.setEditor(userId);
        task.setEdit_time(nowDate);
//		执行新增任务
        dao.insertTask(task);
//		遍历任务参数
        List<TaskAttr> taskAttrList = task.getTaskAttrList();
        for (TaskAttr taskAttr : taskAttrList) {
            taskAttr.setTask_id(task.getTask_id());
            taskAttr.setAttr_id(new GUID().toString());
//			执行新增任务参数
            dao.insertTaskAttr(taskAttr);
        }
//		新增用户关系
        dao.insertTaskUser(task.getTask_id(), task.getUserIds());
//		新增角色关系
        dao.insertTaskRole(task.getTask_id(), task.getRoleIds());
//		反馈成功信息
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("任务新增成功！");
        //用‘URL’字段通知前段页面的跳转路径
        Map map = new HashMap();
        map.put("URL", basePath);
        ajaxResponse.setExtParam(map);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.INSERT, "新增任务，任务内码：" + task.getTask_id() + "，任务名称：" + task.getTask_name());
        return ajaxResponse;
    }

    /**
     * 跳转到编辑任务页面
     *
     * @param taskId 任务编号
     * @param model 反馈对象
     * @param request 请求对象
     * @return base + 'editTask'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/{taskId}/edit", method = RequestMethod.GET)
    public String editTask(@PathVariable String taskId,
            Model model, HttpServletRequest request) throws Exception {
//		获取任务信息
        Task task = dao.getTaskById(taskId);
//		获取任务参数列表
        List<TaskAttr> taskAttrList = dao.getTaskAttrListById(taskId);
        task.setTaskAttrList(taskAttrList);
//		设置到反馈对象中
        model.addAttribute("task", task);
//		获取关联的用户列表映射为树传递到前台
        List<Map<String, Object>> userList = dao.getUserList(taskId);
        model.addAttribute("userTree", JSONArray.fromObject(userList));
//		获取关联的角色列表映射为树传递到前台
        List<Map<String, Object>> roleList = dao.getRoleList(taskId);
        model.addAttribute("roleTree", JSONArray.fromObject(roleList));
//		参数转为JSON传递到前台
        model.addAttribute("taskAttrList", JSONArray.fromObject(taskAttrList).toString());
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "编辑任务，任务内码：" + task.getTask_id() + "，任务名称：" + task.getTask_name());
//		执行跳转到编辑页面
        return basePath + "editTask";
    }

    /**
     * 执行编辑任务
     *
     * @param task 任务对象
     * @param bindingResult 绑定对象
     * @param request 请求对象
     * @return ajax响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public AjaxResponse editTask(@RequestBody @Valid Task task,
            BindingResult bindingResult, HttpServletRequest request) throws Exception {
//		定义AJAX响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
//		如果发现错误则拼接错误信息执行回执
        if (bindingResult.hasErrors()) {
            ajaxResponse.bindingResultHandler(bindingResult);
//			记录操作日志
            LogUtils.updateOperationLog(request, OperationType.UPDATE, "编辑任务，任务内码：" + task.getTask_id() + "，任务名称：" + task.getTask_name() + "，错误信息：" + AjaxResponse.getBindingResultMessage(bindingResult));
            return ajaxResponse;
        }
//		验证提醒时间
        if ("1".equals(task.getIs_timing()) && task.getTiming_time() == null) {
//			反馈成功信息
            ajaxResponse.setWarning(true);
            ajaxResponse.setWarningMessage("提醒时间必须填写。");
//			记录操作日志
            LogUtils.updateOperationLog(request, OperationType.INSERT, "新增任务（新增错误，没有填写提醒时间）");
            return ajaxResponse;
        }
//		验证过期时间
        if ("1".equals(task.getIs_never_overdue()) && task.getOverdue_time() == null) {
//			反馈成功信息
            ajaxResponse.setWarning(true);
            ajaxResponse.setWarningMessage("过期时间必须填写。");
//			记录操作日志
            LogUtils.updateOperationLog(request, OperationType.INSERT, "新增任务（新增错误，没有填写过期时间）");
            return ajaxResponse;
        }
//		获取当前用户、当前时间
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        String userId = sessionUser.getUser_id();
        Date nowDate = new Date();
//		设置编辑人、编辑时间
        task.setEditor(userId);
        task.setEdit_time(nowDate);
//		执行编辑任务
        dao.updateTask(task);
//		清空所有参数
        dao.clearTaskAttr(task.getTask_id());
//		遍历任务参数
        List<TaskAttr> taskAttrList = task.getTaskAttrList();
        for (TaskAttr taskAttr : taskAttrList) {
            taskAttr.setTask_id(task.getTask_id());
            taskAttr.setAttr_id(new GUID().toString());
//			执行新增任务参数
            dao.insertTaskAttr(taskAttr);
        }
//		清空用户关系
        dao.clearTaskUser(task.getTask_id());
//		新增用户关系
        dao.insertTaskUser(task.getTask_id(), task.getUserIds());
//		清空角色关系
        dao.clearTaskRole(task.getTask_id());
//		新增角色关系
        dao.insertTaskRole(task.getTask_id(), task.getRoleIds());
//		反馈成功信息
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("任务编辑成功！");
        //用‘URL’字段通知前段页面的跳转路径
        Map map = new HashMap();
        map.put("URL", basePath);
        ajaxResponse.setExtParam(map);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.UPDATE, "编辑任务，任务内码：" + task.getTask_id() + "，任务名称：" + task.getTask_name());
        return ajaxResponse;
    }

    /**
     * 执行删除任务
     *
     * @param taskIds 任务编号列表
     * @param request 请求对象
     * @return ajax响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public AjaxResponse deleteTask(String taskIds, HttpServletRequest request) throws Exception {
//		执行删除任务
        dao.deleteTask(taskIds);
//		执行响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("任务删除成功！");

//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.DELETE, "删除任务，任务内码列表：" + taskIds);
        return ajaxResponse;
    }

    /**
     * 执行启用任务
     *
     * @param taskIds 任务编号列表
     * @param request 请求对象
     * @return ajax响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/open", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse openTask(String taskIds, HttpServletRequest request) throws Exception {
//		执行启用任务
        dao.openTask(taskIds);
//		执行响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("任务启用成功！");
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.UPDATE, "启用任务，任务内码列表：" + taskIds);
        return ajaxResponse;
    }

    /**
     * 执行禁用任务
     *
     * @param taskIds 任务编号列表
     * @param request 请求对象
     * @return ajax响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/close", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse closeTask(String taskIds, HttpServletRequest request) throws Exception {
//		执行禁用任务
        dao.closeTask(taskIds);
//		执行响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("任务禁用成功！");
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.UPDATE, "禁用任务，任务内码列表：" + taskIds);
        return ajaxResponse;
    }

}
