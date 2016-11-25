package com.dlshouwen.core.system.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dlshouwen.core.base.config.CONFIG;
import com.dlshouwen.core.base.extra.grid.model.Pager;
import com.dlshouwen.core.base.extra.grid.utils.PagerPropertyUtils;
import com.dlshouwen.core.base.model.AjaxResponse;
import com.dlshouwen.core.base.model.OperationType;
import com.dlshouwen.core.base.model.SessionUser;
import com.dlshouwen.core.base.utils.AttributeUtils;
import com.dlshouwen.core.base.utils.GUID;
import com.dlshouwen.core.base.utils.LogUtils;
import com.dlshouwen.core.system.dao.RoleDao;
import com.dlshouwen.core.system.model.Role;
import com.dlshouwen.core.system.model.System;
import java.util.HashMap;

/**
 * 角色
 *
 * @author 大连首闻科技有限公司
 * @version 2013-08-17 08:04:29 236
 */
@Controller
@RequestMapping("/core/system/role")
public class RoleController {

    /**
     * 功能根路径
     */
    private String basePath = "core/system/role/";

    /**
     * 数据操作对象
     */
    private RoleDao dao;

    /**
     * 注入数据操作对象
     *
     * @param dao 数据操作对象
     */
    @javax.annotation.Resource(name = "roleDao")
    public void setDao(RoleDao dao) {
        this.dao = dao;
    }

    /**
     * 跳转到角色主页面
     *
     * @param request 请求对象
     * @return basePath + 'roleList'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String goRolePage(HttpServletRequest request) throws Exception {
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问角色管理页面");
        return basePath + "roleList";
    }

    /**
     * 获取角色列表数据
     *
     * @param gridPager 表格分页信息
     * @param request 请求对象
     * @param response 响应对象
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public void getRoleList(String gridPager, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		映射Pager对象
        Pager pager = PagerPropertyUtils.copy(JSONObject.fromObject(gridPager));
//		获取角色列表数据
        dao.getRoleList(pager, request, response);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.SEARCH, "获取角色管理页面中的角色列表数据");
    }

    /**
     * 跳转到新增角色页面
     *
     * @param request 请求对象
     * @param model 反馈对象
     * @return basePath + 'addRole'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addRole(HttpServletRequest request, Model model) throws Exception {
        Role role = new Role();
        model.addAttribute("role", role);
//		传递所有的子系统列表到前台
        List<System> systemList = dao.getSystemList();
        model.addAttribute("systemList", systemList);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问新增角色页面");
        return basePath + "addRole";
    }

    /**
     * 执行新增角色
     *
     * @param role 角色对象
     * @param bindingResult 绑定结果
     * @param request 请求对象
     * @return ajax响应内容
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse addRole(@Valid Role role, BindingResult bindingResult,
            HttpServletRequest request) throws Exception {
//		定义AJAX响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
//		如果发现错误则拼接错误信息执行回执
        if (bindingResult.hasErrors()) {
            ajaxResponse.bindingResultHandler(bindingResult);
//			记录操作日志
            LogUtils.updateOperationLog(request, OperationType.INSERT, "新增角色（失败：数据验证错误，角色内码：" + role.getRole_id() + "，角色名称：" + role.getRole_name() + "）");
            return ajaxResponse;
        }
//		设置角色编号、创建人、创建时间、编辑人、编辑时间
        role.setRole_id(new GUID().toString());
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        role.setCreator(sessionUser.getUser_id());
        role.setCreate_time(new Date());
        role.setEditor(sessionUser.getUser_id());
        role.setEdit_time(new Date());
//		执行新增角色
        dao.insertRole(role);
//		反馈成功信息
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("角色新增成功！");
        //用‘URL’字段通知前段页面的跳转路径
        Map map = new HashMap();
        map.put("URL", basePath);
        ajaxResponse.setExtParam(map);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.INSERT, "新增角色，角色内码：" + role.getRole_id() + "，角色名称：" + role.getRole_name());
        return ajaxResponse;
    }

    /**
     * 跳转到编辑角色页面
     *
     * @param roleId 角色编号
     * @param request 请求对象
     * @param model 反馈对象
     * @return base + 'editRole'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/{roleId}/edit", method = RequestMethod.GET)
    public String editRole(@PathVariable String roleId, HttpServletRequest request, Model model) throws Exception {
//		获取角色信息
        Role role = dao.getRoleById(roleId);
//		设置到反馈对象中
        model.addAttribute("role", role);
//		传递所有的子系统列表到前台
        List<System> systemList = dao.getSystemList();
        model.addAttribute("systemList", systemList);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问编辑角色页面，角色内码：" + role.getRole_id() + "，角色名称：" + role.getRole_name());
//		执行跳转到编辑页面
        return basePath + "editRole";
    }

    /**
     * 执行编辑角色
     *
     * @param role 角色对象
     * @param bindingResult 绑定对象
     * @param request 请求对象
     * @return ajax响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse editRole(@Valid Role role, BindingResult bindingResult,
            HttpServletRequest request) throws Exception {
//		定义AJAX响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
//		如果发现错误则拼接错误信息执行回执
        if (bindingResult.hasErrors()) {
            ajaxResponse.bindingResultHandler(bindingResult);
//			记录操作日志
            LogUtils.updateOperationLog(request, OperationType.UPDATE, "编辑角色（失败：数据验证错误，角色内码：" + role.getRole_id() + "，角色名称：" + role.getRole_name() + "）");
            return ajaxResponse;
        }
//		设置编辑人、编辑时间
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        role.setEditor(sessionUser.getUser_id());
        role.setEdit_time(new Date());
//		执行编辑角色
        dao.updateRole(role);
//		反馈成功信息
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("角色编辑成功！");
        //用‘URL’字段通知前段页面的跳转路径
        Map map = new HashMap();
        map.put("URL", basePath);
        ajaxResponse.setExtParam(map);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.UPDATE, "编辑角色，角色内码：" + role.getRole_id() + "，角色名称：" + role.getRole_name());
        return ajaxResponse;
    }

    /**
     * 执行删除角色
     *
     * @param roleIds 角色编号列表
     * @param request 请求对象
     * @return ajax响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse deleteRole(String roleIds, HttpServletRequest request) throws Exception {
//		定义新的角色列表
        StringBuffer deleteRoleIds = new StringBuffer();
//		映射为数组类型
        int totalCount = roleIds.split(",").length;
        int isBaseRoleCount = 0;
//		获取基础角色的编号列表
        String base_role_ids = AttributeUtils.getAttributeContent(request.getServletContext(), "base_role_ids");
        List<String> baseRoleIdList = Arrays.asList(base_role_ids.split(","));
//		遍历数组
        for (String roleId : roleIds.split(",")) {
//			是否为内置角色
            if (baseRoleIdList.contains(roleId)) {
                isBaseRoleCount++;
                continue;
            }
//			如果不为基本角色则添加
            deleteRoleIds.append(roleId).append(",");
        }
//		执行删除角色
        dao.deleteRole(deleteRoleIds.toString());
//		提示成功
//		处理响应信息
        StringBuffer message = new StringBuffer();
        message.append("本次共执行").append(totalCount).append("条记录，其中：<br /><br />");
        message.append("　　成功删除角色").append(totalCount - isBaseRoleCount).append("条。<br />");
        if (isBaseRoleCount > 0) {
            message.append("　　因为是系统内置角色无法删除的记录共").append(isBaseRoleCount).append("条。<br />");
        }
//		执行响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage(message.toString());

//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.DELETE, "删除角色，删除的角色：" + deleteRoleIds.toString() + "，提示信息：" + message.toString());
        return ajaxResponse;
    }

    /**
     * 跳转到设置功能权限页面
     *
     * @param roleId 角色编号
     * @param request 请求对象
     * @param model 反馈对象
     * @return basePath + 'setLimit'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/{roleId}/set_limit", method = RequestMethod.GET)
    public String setLimit(@PathVariable String roleId, HttpServletRequest request, Model model) throws Exception {
//		传递角色编号到前台
        model.addAttribute("roleId", roleId);
//		传递角色到前台
        Role role = dao.getRoleById(roleId);
        model.addAttribute("role", role);
//		获取功能列表
        List<Map<String, Object>> limitList = dao.getLimitList(roleId, role.getSystem_id());
        model.addAttribute("limitTree", JSONArray.fromObject(limitList).toString());
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问设置角色功能权限页面，角色内码：" + role.getRole_id() + "，角色名称：" + role.getRole_name());
        return basePath + "setLimit";
    }

    /**
     * 执行设置功能权限
     *
     * @param roleId 角色编号
     * @param limitIds 功能编号列表
     * @param request 请求对象
     * @return ajax响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/set_limit", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse setLimit(String roleId, String limitIds, HttpServletRequest request) throws Exception {
//		清除角色以前的权限列表
        dao.clearRoleLimitRelation(roleId);
//		设置权限
        dao.setRoleLimitRelation(roleId, limitIds);
//		执行响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("配置功能成功！");
          //用‘URL’字段通知前段页面的跳转路径
        Map map = new HashMap();
        map.put("URL", basePath);
        ajaxResponse.setExtParam(map);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.UPDATE, "设置角色功能权限，角色：" + roleId + "，功能：" + limitIds);
        return ajaxResponse;
    }

    /**
     * 跳转到设置用户关系页面
     *
     * @param roleId 角色编号
     * @param request 请求对象
     * @param model 反馈对象
     * @return basePath + "setUser"
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/{roleId}/set_user", method = RequestMethod.GET)
    public String setUser(@PathVariable String roleId, HttpServletRequest request, Model model) throws Exception {
//		角色编号传递到前台
        model.addAttribute("roleId", roleId);
//		传递角色到前台
        Role role = dao.getRoleById(roleId);
        model.addAttribute("role", role);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问设置角色用户关系页面，角色：" + roleId);
        return basePath + "setUser";
    }

    /**
     * 获取人员列表数据
     *
     * @param gridPager 表格分页信息
     * @param roleId 角色编号
     * @param request 请求对象
     * @param response 响应对象
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/{roleId}/set_user/list", method = RequestMethod.POST)
    public void getRoleUserList(@PathVariable String roleId, String gridPager, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		映射Pager对象
        Pager pager = PagerPropertyUtils.copy(JSONObject.fromObject(gridPager));
//		获取角色用户关系中的用户列表数据
        dao.getRoleUserList(pager, request, response, roleId);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.SEARCH, "获取设置角色用户关系页面中的用户列表，角色：" + roleId);
    }

    /**
     * 执行设置用户关系
     *
     * @param roleId 角色编号
     * @param addUserIds 添加的用户编号列表
     * @param deleteUserIds 删除的用户编号列表
     * @param request 请求对象
     * @return ajax响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/set_user", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public AjaxResponse setUser(String roleId, String addUserIds, String deleteUserIds, HttpServletRequest request) throws Exception {
//		如果添加的用户列表不为空
        if (addUserIds != null && !"".equals(addUserIds)) {
//			删除关系
            dao.deleteRoleUserRelation(roleId, addUserIds);
//			添加关系
            dao.insertRoleUserRelation(roleId, addUserIds);
        }
//		如果删除的用户列表不为空
        if (deleteUserIds != null && !"".equals(deleteUserIds)) {
//			删除关系
            dao.deleteRoleUserRelation(roleId, deleteUserIds);
        }
//		执行响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("配置用户成功！");
        //用‘URL’字段通知前段页面的跳转路径
        Map map = new HashMap();
        map.put("URL", basePath);
        ajaxResponse.setExtParam(map);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.UPDATE, "配置角色用户关系，角色：" + roleId + "，添加用户：" + addUserIds + "，删除用户：" + deleteUserIds);
        return ajaxResponse;
    }

}
