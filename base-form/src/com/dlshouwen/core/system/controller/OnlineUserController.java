package com.dlshouwen.core.system.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dlshouwen.core.base.config.CONFIG;
import com.dlshouwen.core.base.extra.grid.model.Pager;
import com.dlshouwen.core.base.extra.grid.utils.PagerPropertyUtils;
import com.dlshouwen.core.base.model.AjaxResponse;
import com.dlshouwen.core.base.model.OperationType;
import com.dlshouwen.core.base.model.SessionUser;
import com.dlshouwen.core.base.utils.LogUtils;
import com.dlshouwen.core.system.dao.OnlineUserDao;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * 在线用户
 *
 * @author 大连首闻科技有限公司
 * @version 2013-8-19 14:09:09
 */
@Controller
@RequestMapping("/core/system/online_user")
public class OnlineUserController {

    /**
     * 功能根路径
     */
    private String basePath = "core/system/onlineUser/";

    /**
     * 数据操作对象
     */
    private OnlineUserDao dao;

    /**
     * 注入数据操作对象
     *
     * @param dao 数据操作对象
     */
    @Resource(name = "coreOnlineUserDao")
    public void setDao(OnlineUserDao dao) {
        this.dao = dao;
    }

    /**
     * 跳转到获取在线用户列表页面
     *
     * @param request 请求对象
     * @return basePath + 'onlineUserList'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String goOnlineUserPage(HttpServletRequest request) throws Exception {
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问在线用户列表页面");
        return basePath + "onlineUserList";
    }

    /**
     * 获取在线用户列表
     *
     * @param gridPager 表格分页信息
     * @param request 请求对象
     * @param response 响应对象
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public void getOnlineUserList(String gridPager, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		映射Pager对象
        Pager pager = PagerPropertyUtils.copy(JSONObject.fromObject(gridPager));
//		获取在线用户列表数据
        dao.getOnlineUserList(pager, request, response);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.SEARCH, "获取在线用户列表数据");
    }

    /**
     * 执行强制下线
     *
     * @param system 系统对象
     * @param bindingResult 绑定结果
     * @param request 请求对象
     * @return ajax响应内容
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/force", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse forceUserOutLine(String logIds, HttpServletRequest request) throws Exception {
//		获取登录用户
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        String loginLogId = sessionUser.getLogin_id();
//		定义总数量、成功数量、因为是自己无法强制下线的数量
        int totalCount = 0;
        int successCount = 0;
        int isSelfCannotForceCount = 0;
//		获取总日志编号数量
        totalCount = logIds.split(",").length;
//		遍历执行强制下线
        for (String logId : logIds.split(",")) {
            if (loginLogId.equals(logId)) {
                isSelfCannotForceCount++;
                continue;
            }
            dao.forceUserOutLine(logId);
            successCount++;
        }
//		提示成功
        StringBuffer message = new StringBuffer();
        message.append("执行强制下线操作成功，本次共执行 ").append(totalCount).append(" 条记录，其中：<br /><br />");
        message.append("　　成功强制下线在线用户 ").append(successCount).append(" 人。<br />");
        if (isSelfCannotForceCount > 0) {
            message.append("　　由于被强制下线人是当前登录用户不能强制下线 ").append(isSelfCannotForceCount).append(" 人。<br />");
        }
//		反馈成功信息
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage(message.toString());
        //用‘URL’字段通知前段页面的跳转路径
        Map map = new HashMap();
        map.put("URL", "core/system/online_user");
        ajaxResponse.setExtParam(map);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.UPDATE, "强制下线，强制下线登录编号：" + logIds + "，强制信息：" + message.toString());
        return ajaxResponse;
    }

}
