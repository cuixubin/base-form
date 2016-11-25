package com.dlshouwen.core.system.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dlshouwen.core.base.config.CONFIG;
import com.dlshouwen.core.base.extra.grid.model.Pager;
import com.dlshouwen.core.base.extra.grid.utils.PagerPropertyUtils;
import com.dlshouwen.core.base.extra.unique.utils.UniqueUtils;
import com.dlshouwen.core.base.model.AjaxResponse;
import com.dlshouwen.core.base.model.OperationType;
import com.dlshouwen.core.base.model.SessionUser;
import com.dlshouwen.core.base.utils.AttributeUtils;
import com.dlshouwen.core.base.utils.GUID;
import com.dlshouwen.core.base.utils.LogUtils;
import com.dlshouwen.core.system.dao.SystemDao;
import com.dlshouwen.core.system.model.System;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * 系统
 *
 * @author 大连首闻科技有限公司
 * @version 2013-08-17 08:04:29 198
 */
@Controller
@RequestMapping("/core/system/system")
public class SystemController {

    /**
     * 功能根路径
     */
    private String basePath = "core/system/system/";

    /**
     * 数据操作对象
     */
    private SystemDao dao;

    /**
     * 注入数据操作对象
     *
     * @param dao 数据操作对象
     */
    @Resource(name = "coreSystemDao")
    public void setDao(SystemDao dao) {
        this.dao = dao;
    }

    /**
     * 跳转到系统主页面
     *
     * @param request 请求对象
     * @return basePath + 'systemList'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String goSystemPage(HttpServletRequest request) throws Exception {
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问系统管理页面");
        return basePath + "systemList";
    }

    /**
     * 获取系统列表数据
     *
     * @param gridPager 表格分页信息
     * @param request 请求对象
     * @param response 响应对象
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public void getSystemList(String gridPager, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		映射Pager对象
        Pager pager = PagerPropertyUtils.copy(JSONObject.fromObject(gridPager));
//		获取系统列表数据
        dao.getSystemList(pager, request, response);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.SEARCH, "获取系统管理的系统列表数据");
    }

    /**
     * 跳转到新增系统页面
     *
     * @param model 反馈对象
     * @param request 请求对象
     * @return basePath + 'addSystem'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addSystem(Model model, HttpServletRequest request) throws Exception {
        model.addAttribute("system", new System());
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问新增系统页面");
        return basePath + "addSystem";
    }

    /**
     * 执行新增系统
     *
     * @param system 系统对象
     * @param bindingResult 绑定结果
     * @param request 请求对象
     * @return ajax响应内容
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse addSystem(@Valid System system, BindingResult bindingResult,
            HttpServletRequest request) throws Exception {
//		定义AJAX响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
//		校验唯一
        if (!UniqueUtils.unique(dao, ajaxResponse, "系统编号已经被使用，请重新填写。", "SYSTEM_CODE_UNIQUE_FOR_ADD", system.getSystem_code())) {
//			记录操作日志
            LogUtils.updateOperationLog(request, OperationType.INSERT, "新增系统（新增失败，系统编号已经被使用，系统编号：" + system.getSystem_code() + "）");
            return ajaxResponse;
        }
//		如果发现错误则拼接错误信息执行回执
        if (bindingResult.hasErrors()) {
            ajaxResponse.bindingResultHandler(bindingResult);
//			记录操作日志
            LogUtils.updateOperationLog(request, OperationType.INSERT, "新增系统（新增失败，系统编号：" + system.getSystem_code() + "，错误信息：" + AjaxResponse.getBindingResultMessage(bindingResult) + "）");
            return ajaxResponse;
        }
//		获取当前用户、当前时间
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        String userId = sessionUser.getUser_id();
        Date nowDate = new Date();
//		设置系统编号、创建人、创建时间、编辑人、编辑时间
        system.setSystem_id(new GUID().toString());
        system.setCreator(userId);
        system.setCreate_time(nowDate);
        system.setEditor(userId);
        system.setEdit_time(nowDate);
//		执行新增系统
        dao.insertSystem(system);
//		反馈成功信息
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("系统新增成功！");
        //用‘URL’字段通知前段页面的跳转路径
        Map map = new HashMap();
        map.put("URL", basePath);
        ajaxResponse.setExtParam(map);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.INSERT, "新增系统，系统内码：" + system.getSystem_id() + "，系统编号：" + system.getSystem_code() + "，系统名称：" + system.getSystem_name());
        return ajaxResponse;
    }

    /**
     * 跳转到编辑系统页面
     *
     * @param systemId 系统编号
     * @param model 反馈对象
     * @param request 请求对象
     * @return base + 'editSystem'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/{systemId}/edit", method = RequestMethod.GET)
    public String editSystem(@PathVariable String systemId,
            Model model, HttpServletRequest request) throws Exception {
//		获取系统信息
        System system = dao.getSystemById(systemId);
//		设置到反馈对象中
        model.addAttribute("system", system);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问编辑系统页面，系统内码：" + system.getSystem_id() + "，系统编号：" + system.getSystem_code() + "，系统名称：" + system.getSystem_name());
//		执行跳转到编辑页面
        return basePath + "editSystem";
    }

    /**
     * 执行编辑系统
     *
     * @param system 系统对象
     * @param bindingResult 绑定对象
     * @return ajax响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse editSystem(@Valid System system, HttpServletRequest request,
            BindingResult bindingResult) throws Exception {
//		定义AJAX响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
//		校验唯一
        if (!UniqueUtils.unique(dao, ajaxResponse, "系统编号已经被使用，请重新填写。", "SYSTEM_CODE_UNIQUE_FOR_EDIT",
                system.getSystem_code(), system.getSystem_id())) {
//			记录操作日志
            LogUtils.updateOperationLog(request, OperationType.UPDATE, "编辑系统（编辑失败，系统编号已经被使用，系统内码：" + system.getSystem_id() + "，系统编号：" + system.getSystem_code() + "）");
            return ajaxResponse;
        }
//		如果发现错误则拼接错误信息执行回执
        if (bindingResult.hasErrors()) {
            ajaxResponse.bindingResultHandler(bindingResult);
//			记录操作日志
            LogUtils.updateOperationLog(request, OperationType.UPDATE, "编辑系统（编辑失败，系统内码：" + system.getSystem_id() + "，系统编号：" + system.getSystem_code() + "，错误信息：" + AjaxResponse.getBindingResultMessage(bindingResult) + "）");
            return ajaxResponse;
        }
//		获取当前用户、当前时间
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        String userId = sessionUser.getUser_id();
        Date nowDate = new Date();
//		设置编辑人、编辑时间
        system.setEditor(userId);
        system.setEdit_time(nowDate);
//		执行编辑系统
        dao.updateSystem(system);
//		反馈成功信息
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("系统编辑成功！");
        //用‘URL’字段通知前段页面的跳转路径
        Map map = new HashMap();
        map.put("URL", basePath);
        ajaxResponse.setExtParam(map);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.UPDATE, "编辑系统，系统内码：" + system.getSystem_id() + "，系统编号：" + system.getSystem_code() + "，系统名称：" + system.getSystem_name());
        return ajaxResponse;
    }

    /**
     * 执行删除系统
     *
     * @param systemIds 系统编号列表
     * @param request 请求对象
     * @return ajax响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse deleteSystem(String systemIds, HttpServletRequest request) throws Exception {
//		定义最终需要删除的子系统列表
        StringBuffer deleteSystemIds = new StringBuffer();
//		定义各个数量
        int totalCount = systemIds.split(",").length;
        int isBaseSystemCount = 0;
        int hasLimitCount = 0;
        int hasRoleCount = 0;
//		获取基础系统的编号列表
        String base_system_ids = AttributeUtils.getAttributeContent(request.getServletContext(), "base_system_ids");
        List<String> baseSystemIdList = Arrays.asList(base_system_ids.split(","));
//		遍历判断
        for (String systemId : systemIds.split(",")) {
//			系统是否为内置系统
            if (baseSystemIdList.contains(systemId)) {
                isBaseSystemCount++;
                continue;
            }
//			系统是否包含功能
            boolean systemIsHasLimit = dao.getSystemIsHasLimit(systemId);
            if (systemIsHasLimit) {
                hasLimitCount++;
                continue;
            }
//			系统是否包含角色
            boolean systemIsHasRole = dao.getSystemIsHasRole(systemId);
            if (systemIsHasRole) {
                hasRoleCount++;
                continue;
            }
//			拼接删除系统编号
            deleteSystemIds.append(systemId).append(",");
        }
        if (deleteSystemIds.length() > 0) {
            deleteSystemIds.deleteCharAt(deleteSystemIds.length() - 1);
//			执行删除系统
            dao.deleteSystem(deleteSystemIds.toString());
        }
//		处理响应信息
        StringBuffer message = new StringBuffer();
        message.append("本次共执行").append(totalCount).append("条记录，其中：<br /><br />");
        message.append("　　成功删除系统").append(totalCount - isBaseSystemCount - hasLimitCount - hasRoleCount).append("条。<br />");
        if (isBaseSystemCount > 0) {
            message.append("　　因为是系统内置系统无法删除的记录共").append(isBaseSystemCount).append("条。<br />");
        }
        if (hasLimitCount > 0) {
            message.append("　　因为系统下挂载功能无法删除的记录共").append(hasLimitCount).append("条。<br />");
        }
        if (hasRoleCount > 0) {
            message.append("　　因为系统下挂载角色无法删除的记录共").append(hasRoleCount).append("条。<br />");
        }
//		执行响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage(message.toString());

//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.DELETE, "删除系统，提示信息：" + message.toString());
        return ajaxResponse;
    }

}
