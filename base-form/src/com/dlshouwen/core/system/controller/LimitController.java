package com.dlshouwen.core.system.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dlshouwen.core.base.config.CONFIG;
import com.dlshouwen.core.base.model.AjaxResponse;
import com.dlshouwen.core.base.model.OperationType;
import com.dlshouwen.core.base.model.SessionUser;
import com.dlshouwen.core.base.utils.GUID;
import com.dlshouwen.core.base.utils.LogUtils;
import com.dlshouwen.core.system.dao.LimitDao;
import com.dlshouwen.core.system.model.Limit;
import com.dlshouwen.core.system.model.System;
import java.util.HashMap;

/**
 * 功能
 *
 * @author 大连首闻科技有限公司
 * @version 2013-08-17 08:04:29 131
 */
@Controller
@RequestMapping("/core/system/limit")
public class LimitController {

    /**
     * 功能根路径
     */
    private String basePath = "core/system/limit/";

    /**
     * 数据操作对象
     */
    private LimitDao dao;

    /**
     * 注入数据操作对象
     *
     * @param dao 数据操作对象
     */
    @Resource(name = "limitDao")
    public void setDao(LimitDao dao) {
        this.dao = dao;
    }

    /**
     * 跳转到功能主页面
     *
     * @param request 请求对象
     * @param model 反馈对象
     * @return basePath + 'limitMain'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String goLimitMainPage(HttpServletRequest request, Model model) throws Exception {
//		获取功能列表传递到前台
        List<Map<String, Object>> limitList = dao.getLimitList();
        model.addAttribute("limitTree", JSONArray.fromObject(limitList).toString());
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问功能管理页面");
        return basePath + "limitMain";
    }

    /**
     * 获取功能信息
     *
     * @param limitId 功能编号
     * @param request 请求对象
     * @return limit 功能信息
     * @throws Exception
     */
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    @ResponseBody
    public Limit getLimitInfo(String limitId, HttpServletRequest request) throws Exception {
//		获取功能信息
        Limit limit = dao.getLimitById(limitId);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "获取功能详细信息，功能编号：" + limitId + "，功能名称：" + limit.getLimit_name());
        return limit;
    }

    /**
     * 跳转到新增功能页面
     *
     * @param preLimitId 上级功能编号
     * @param request 请求对象
     * @param model 反馈对象
     * @return basePath + 'addLimit'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/{preLimitId}/add", method = RequestMethod.GET)
    public String addLimit(@PathVariable String preLimitId, HttpServletRequest request, Model model) throws Exception {
//		定义新的功能对象
        Limit limit = new Limit();
//		设置上级功能编号、默认菜单、不异步
        limit.setPre_limit_id(preLimitId);
        limit.setLimit_type("1");
        limit.setIfAsynch("0");
//		设置系统编号
        if (!"top".equals(preLimitId)) {
//			如果不是顶级节点则子系统编号为当前编号
            Limit preLimit = dao.getLimitById(preLimitId);
            limit.setSystem_id(preLimit.getSystem_id());
        }
//		传递新的功能到前台
        model.addAttribute("limit", limit);
//		传递所有的子系统列表到前台
        List<System> systemList = dao.getSystemList();
        model.addAttribute("systemList", systemList);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问新增功能页面，上级功能编号：" + preLimitId);
        return basePath + "addLimit";
    }

    /**
     * 执行新增功能
     *
     * @param limit 功能对象
     * @param bindingResult 绑定结果
     * @param request 请求对象
     * @return ajax响应内容
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse addLimit(@Valid Limit limit, BindingResult bindingResult,
            HttpServletRequest request) throws Exception {
//		定义AJAX响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
//		如果发现错误则拼接错误信息执行回执
        if (bindingResult.hasErrors()) {
            ajaxResponse.bindingResultHandler(bindingResult);
//			记录操作日志
            LogUtils.updateOperationLog(request, OperationType.INSERT, "新增功能（失败：数据验证错误，功能编号：" + limit.getLimit_id() + "，功能名称：" + limit.getLimit_name() + "）");
            return ajaxResponse;
        }
//		获取最大的排序码
        int maxSort = dao.getMaxSort(limit.getPre_limit_id());
        limit.setSort(maxSort + 1);
//		设置功能编号、创建人、创建时间、编辑人、编辑时间
        limit.setLimit_id(new GUID().toString());
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        limit.setCreator(sessionUser.getUser_id());
        limit.setCreate_time(new Date());
        limit.setEditor(sessionUser.getUser_id());
        limit.setEdit_time(new Date());
//		执行新增功能
        dao.insertLimit(limit);
//		反馈成功信息
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("功能新增成功！");
        ajaxResponse.setData(limit);
        //用‘URL’字段通知前段页面的跳转路径
        Map map = new HashMap();
        map.put("URL", basePath);
        ajaxResponse.setExtParam(map);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.INSERT, "新增功能，功能编号：" + limit.getLimit_id() + "，功能名称：" + limit.getLimit_name());
        return ajaxResponse;
    }

    /**
     * 跳转到编辑功能页面
     *
     * @param limitId 功能编号
     * @param request 请求对象
     * @param model 反馈对象
     * @return base + 'editLimit'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/{limitId}/edit", method = RequestMethod.GET)
    public String editLimit(@PathVariable String limitId, HttpServletRequest request, Model model) throws Exception {
//		获取功能信息
        Limit limit = dao.getLimitById(limitId);
//		设置到反馈对象中
        model.addAttribute("limit", limit);
//		传递所有的子系统列表到前台
        List<System> systemList = dao.getSystemList();
        model.addAttribute("systemList", systemList);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问编辑功能页面，功能编号：" + limit.getLimit_id() + "，功能名称：" + limit.getLimit_name());
//		执行跳转到编辑页面
        return basePath + "editLimit";
    }

    /**
     * 执行编辑功能
     *
     * @param limit 功能对象
     * @param bindingResult 绑定对象
     * @param request 请求对象
     * @return ajax响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse editLimit(@Valid Limit limit, BindingResult bindingResult,
            HttpServletRequest request) throws Exception {
//		定义AJAX响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
//		如果发现错误则拼接错误信息执行回执
        if (bindingResult.hasErrors()) {
            ajaxResponse.bindingResultHandler(bindingResult);
//			记录操作日志
            LogUtils.updateOperationLog(request, OperationType.UPDATE, "编辑功能（失败：数据验证错误，功能编号：" + limit.getLimit_id() + "，功能名称：" + limit.getLimit_name() + "）");
            return ajaxResponse;
        }
//		设置编辑人、编辑时间
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        limit.setEditor(sessionUser.getUser_id());
        limit.setEdit_time(new Date());
//		执行编辑功能
        dao.updateLimit(limit);
//		反馈成功信息
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("功能编辑成功！");
        ajaxResponse.setData(limit);
        //用‘URL’字段通知前段页面的跳转路径
        Map map = new HashMap();
        map.put("URL", basePath);
        ajaxResponse.setExtParam(map);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.UPDATE, "编辑功能，功能编号：" + limit.getLimit_id() + "，功能名称：" + limit.getLimit_name());
        return ajaxResponse;
    }

    /**
     * 执行删除功能
     *
     * @param limitId 功能编号
     * @param request 请求对象
     * @return ajax响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse deleteLimit(String limitIds, HttpServletRequest request) throws Exception {
//		定义最终需要删除的功能列表
        StringBuffer deleteLimitIds = new StringBuffer();
//		定义各个数量
        int totalCount = limitIds.split(",").length;
        int hasLimitCount = 0;
//		删除成功、失败的纪录编号
        StringBuffer successIds = new StringBuffer();
        StringBuffer failureIds = new StringBuffer();
//		遍历判断
        for (String limitId : limitIds.split(",")) {
//			系统是否包含功能
            boolean isHaveSubLimit = dao.getSubLimitCount(limitId) > 0 ? true : false;
            if (isHaveSubLimit) {
                hasLimitCount++;
                failureIds.append(limitId).append(",");
                continue;
            }
            successIds.append(limitId).append(",");
//			拼接删除功能编号
            deleteLimitIds.append(limitId).append(",");
        }
        if (deleteLimitIds.length() > 0) {
            deleteLimitIds.deleteCharAt(deleteLimitIds.length() - 1);
//			执行删除功能
            dao.deleteLimit(deleteLimitIds.toString());
        }
//		处理响应信息
        StringBuffer message = new StringBuffer();
        message.append("本次共执行").append(totalCount).append("条记录，其中：<br /><br />");
        message.append("　　成功删除功能").append(totalCount - hasLimitCount).append("条。<br />");
        if (hasLimitCount > 0) {
            message.append("　　因为包含子功能无法删除的记录共").append(hasLimitCount).append("条。<br />");
        }
//		执行响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage(message.toString());
        ajaxResponse.setData(deleteLimitIds.toString().replaceAll("'", ""));

//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.DELETE,
                "删除功能，成功删除功能：" + successIds.toString() + "；因为包含子功能无法删除的功能：" + failureIds.toString());
        return ajaxResponse;
    }

    /**
     * 拖拽功能
     *
     * @param limitIds 功能编号列表
     * @param targetLimitId 目标功能编号
     * @param moveType 移动类别
     * @param request 请求对象
     * @return ajax响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/drag", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public AjaxResponse dragLimit(String limitIds, String targetLimitId, String moveType, HttpServletRequest request) throws Exception {
//		获取目标功能对象
        Limit targetLimit = dao.getLimitById(targetLimitId);
//		遍历功能编号列表
        for (String limitId : limitIds.split(",")) {
//			获取功能对象
            Limit limit = dao.getLimitById(limitId);
//			如果是移动到目标功能内部
            if ("inner".equals(moveType)) {
//				设置上级编号、系统编号
                limit.setPre_limit_id(targetLimit.getLimit_id());
//				设置排序号为最大的sort号+1
                int sort = dao.getMaxSort(targetLimitId);
                limit.setSort(sort + 1);
//				更新功能
                dao.updateLimit(limit);
            }
//			如果是移动到目标功能前
            if ("prev".equals(moveType)) {
//				设置上级编号、系统编号
                limit.setPre_limit_id(targetLimit.getPre_limit_id());
//				设置排序号为当前的排序号
                limit.setSort(targetLimit.getSort());
//				将所有大于此排序号的节点序号自增1
                dao.updateLimitSortAdd1(targetLimit.getPre_limit_id(), targetLimit.getSort(), limit.getLimit_id(), true);
//				更新功能
                dao.updateLimit(limit);
            }
//			如果是移动到目标功能后
            if ("next".equals(moveType)) {
//				设置上级编号、系统编号
                limit.setPre_limit_id(targetLimit.getPre_limit_id());
//				设置排序号为当前的排序号加1
                limit.setSort(targetLimit.getSort() + 1);
//				将所有大于此排序号的节点序号自增1
                dao.updateLimitSortAdd1(targetLimit.getPre_limit_id(), targetLimit.getSort(), limit.getLimit_id(), false);
//				更新功能
                dao.updateLimit(limit);
            }
        }
//		执行响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("功能移动成功！");
        //用‘URL’字段通知前段页面的跳转路径
        Map map = new HashMap();
        map.put("URL", basePath);
        ajaxResponse.setExtParam(map);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.DELETE,
                "移动功能，被移动的功能：" + limitIds + "，目标功能：" + targetLimitId + "，移动类型：" + moveType);
        return ajaxResponse;
    }

    /**
     * 选择功能
     *
     * @param type 选择类别
     * @param request 请求对象
     * @param model 反馈对象
     * @return basePath + 'selectLimit'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/select/{type}", method = RequestMethod.GET)
    public String selectLimit(@PathVariable String type, HttpServletRequest request, Model model) throws Exception {
//		类型传递到前台
        model.addAttribute("type", type);
//		获取功能树传递到前台
        List<Map<String, Object>> limitList = dao.getLimitListOnlyMenu();
        model.addAttribute("limitTree", JSONArray.fromObject(limitList));
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问选择功能页面，选择类型：" + type);
        return basePath + "selectLimit";
    }

    /**
     * 选择功能（包含用户权限）
     *
     * @param userId 用户编号
     * @param type 选择类别
     * @param request 请求对象
     * @param model 反馈对象
     * @return basePath + 'selectLimit'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/select/{userId}/{type}", method = RequestMethod.GET)
    public String selectLimit(@PathVariable String userId, @PathVariable String type,
            HttpServletRequest request, Model model) throws Exception {
//		类型传递到前台
        model.addAttribute("type", type);
//		获取功能树传递到前台
        List<Map<String, Object>> limitList = dao.getLimitListOnlyMenu(userId);
        model.addAttribute("limitTree", JSONArray.fromObject(limitList));
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问选择功能页面（包含用户权限），权限用户内码：" + userId + "，选择类型：" + type);
        return basePath + "selectLimit";
    }

}
