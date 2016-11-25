package com.dlshouwen.core.announcement.controller;

import java.util.Date;

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

import com.dlshouwen.core.announcement.dao.AnnouncementDao;
import com.dlshouwen.core.announcement.model.Announcement;
import com.dlshouwen.core.base.config.CONFIG;
import com.dlshouwen.core.base.extra.grid.model.Pager;
import com.dlshouwen.core.base.extra.grid.utils.PagerPropertyUtils;
import com.dlshouwen.core.base.model.AjaxResponse;
import com.dlshouwen.core.base.model.OperationType;
import com.dlshouwen.core.base.model.SessionUser;
import com.dlshouwen.core.base.utils.GUID;
import com.dlshouwen.core.base.utils.LogUtils;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * 公告
 *
 * @author 大连首闻科技有限公司
 * @version 2013-08-17 08:04:25 847
 */
@Controller
@RequestMapping("/core/announcement/announcement")
public class AnnouncementController {

    /**
     * 功能根路径
     */
    private String basePath = "core/announcement/announcement/";

    /**
     * 数据操作对象
     */
    private AnnouncementDao dao;

    /**
     * 注入数据操作对象
     *
     * @param dao 数据操作对象
     */
    @Resource(name = "announcementDao")
    public void setDao(AnnouncementDao dao) {
        this.dao = dao;
    }

    /**
     * 跳转到公告主页面
     *
     * @param request 请求对象
     * @return basePath + 'announcementList'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String goAnnouncementPage(HttpServletRequest request) throws Exception {
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问公告管理页面");
        return basePath + "announcementList";
    }

    /**
     * 获取公告列表数据
     *
     * @param gridPager 表格分页信息
     * @param request 请求对象
     * @param response 响应对象
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public void getAnnouncementList(String gridPager, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		映射Pager对象
        Pager pager = PagerPropertyUtils.copy(JSONObject.fromObject(gridPager));
//		获取公告列表数据
        dao.getAnnouncementList(pager, request, response);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.SEARCH, "获取公告管理页面中的公告列表数据");
    }

    /**
     * 跳转到新增公告页面
     *
     * @param model 反馈对象
     * @param request 请求对象
     * @return basePath + 'addAnnouncement'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addAnnouncement(Model model, HttpServletRequest request) throws Exception {
//		新增公告对象
        Announcement announcement = new Announcement();
//		设置默认启用
        announcement.setStatus("1");
//		传递公告对象到前台
        model.addAttribute("announcement", announcement);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问新增公告页面");
        return basePath + "addAnnouncement";
    }

    /**
     * 执行新增公告
     *
     * @param announcement 公告对象
     * @param bindingResult 绑定结果
     * @param request 请求对象
     * @return ajax响应内容
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse addAnnouncement(@Valid Announcement announcement, BindingResult bindingResult,
            HttpServletRequest request) throws Exception {
//		定义AJAX响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
//		如果发现错误则拼接错误信息执行回执
        if (bindingResult.hasErrors()) {
            ajaxResponse.bindingResultHandler(bindingResult);
//			记录操作日志
            LogUtils.updateOperationLog(request, OperationType.INSERT, "新增公告（失败：数据验证错误，详细信息：" + AjaxResponse.getBindingResultMessage(bindingResult) + "）");
            return ajaxResponse;
        }
//		获取当前用户、当前时间
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        String userId = sessionUser.getUser_id();
        Date nowDate = new Date();
//		设置公告编号、创建人、创建时间、编辑人、编辑时间
        announcement.setAnnouncement_id(new GUID().toString());
        announcement.setCreator(userId);
        announcement.setCreate_time(nowDate);
        announcement.setEditor(userId);
        announcement.setEdit_time(nowDate);
//		执行新增公告
        dao.insertAnnouncement(announcement);
//		反馈成功信息
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("公告新增成功！");
        //用‘URL’字段通知前段页面的跳转路径
        Map map = new HashMap();
        map.put("URL", basePath);
        ajaxResponse.setExtParam(map);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.INSERT, "新增公告，公告编号：" + announcement.getAnnouncement_id() + "，公告标题：" + announcement.getTitle());
        return ajaxResponse;
    }

    /**
     * 跳转到编辑公告页面
     *
     * @param announcementId 公告编号
     * @param model 反馈对象
     * @param request 请求对象
     * @return base + 'editAnnouncement'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/{announcementId}/edit", method = RequestMethod.GET)
    public String editAnnouncement(@PathVariable String announcementId,
            Model model, HttpServletRequest request) throws Exception {
//		获取公告信息
        Announcement announcement = dao.getAnnouncementById(announcementId);
//		设置到反馈对象中
        model.addAttribute("announcement", announcement);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问编辑公告页面");
//		执行跳转到编辑页面
        return basePath + "editAnnouncement";
    }

    /**
     * 执行编辑公告
     *
     * @param announcement 公告对象
     * @param bindingResult 绑定对象
     * @param request 请求对象
     * @return ajax响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse editAnnouncement(@Valid Announcement announcement,
            BindingResult bindingResult, HttpServletRequest request) throws Exception {
//		定义AJAX响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
//		如果发现错误则拼接错误信息执行回执
        if (bindingResult.hasErrors()) {
            ajaxResponse.bindingResultHandler(bindingResult);
//			记录操作日志
            LogUtils.updateOperationLog(request, OperationType.UPDATE, "编辑公告（失败：数据验证错误，详细信息：" + AjaxResponse.getBindingResultMessage(bindingResult) + "）");
            return ajaxResponse;
        }
//		获取当前用户、当前时间
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        String userId = sessionUser.getUser_id();
        Date nowDate = new Date();
//		设置编辑人、编辑时间
        announcement.setEditor(userId);
        announcement.setEdit_time(nowDate);
//		执行编辑公告
        dao.updateAnnouncement(announcement);
//		反馈成功信息
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("公告编辑成功！");
        //用‘URL’字段通知前段页面的跳转路径
        Map map = new HashMap();
        map.put("URL", basePath);
        ajaxResponse.setExtParam(map);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.UPDATE, "编辑公告，公告编号：" + announcement.getAnnouncement_id() + "，公告标题：" + announcement.getTitle());
        return ajaxResponse;
    }

    /**
     * 执行删除公告
     *
     * @param announcementIds 公告编号列表
     * @param request 请求对象
     * @return ajax响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse deleteAnnouncement(String announcementIds,
            HttpServletRequest request) throws Exception {
//		执行删除公告
        dao.deleteAnnouncement(announcementIds);
//		执行响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("公告删除成功！");
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.DELETE, "删除公告，公告编号列表：" + announcementIds);
        return ajaxResponse;
    }

    /**
     * 执行启用公告
     *
     * @param announcementIds 公告编号列表
     * @param request 请求对象
     * @return ajax响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/open", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse openAnnouncement(String announcementIds, HttpServletRequest request) throws Exception {
//		执行启用公告
        dao.openAnnouncement(announcementIds);
//		执行响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("公告启用成功！");
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.UPDATE, "启用公告，公告编号列表：" + announcementIds);
        return ajaxResponse;
    }

    /**
     * 执行禁用公告
     *
     * @param announcementIds 公告编号列表
     * @param request 请求对象
     * @return ajax响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/close", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse closeAnnouncement(String announcementIds, HttpServletRequest request) throws Exception {
//		执行禁用公告
        dao.closeAnnouncement(announcementIds);
//		执行响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("公告禁用成功！");
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.UPDATE, "禁用公告，公告编号列表：" + announcementIds);
        return ajaxResponse;
    }

}
