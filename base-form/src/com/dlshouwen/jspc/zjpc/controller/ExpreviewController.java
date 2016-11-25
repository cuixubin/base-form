/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlshouwen.jspc.zjpc.controller;

import com.dlshouwen.core.base.config.CONFIG;
import com.dlshouwen.core.base.extra.grid.model.Pager;
import com.dlshouwen.core.base.extra.grid.utils.PagerPropertyUtils;
import com.dlshouwen.core.base.model.AjaxResponse;
import com.dlshouwen.core.base.model.OperationType;
import com.dlshouwen.core.base.model.SessionUser;
import com.dlshouwen.core.base.utils.CodeTableUtils;
import com.dlshouwen.core.base.utils.DateUtils;
import com.dlshouwen.core.base.utils.GUID;
import com.dlshouwen.core.base.utils.LogUtils;
import com.dlshouwen.jspc.zjpc.dao.ExpreviewDao;
import com.dlshouwen.jspc.zjpc.model.Expreview;
import com.dlshouwen.core.system.dao.UserDao;
import com.dlshouwen.core.system.model.User;
import com.dlshouwen.core.system.model.UserVO;
import com.dlshouwen.jspc.zwpc.dao.EvalItemDao;
import com.dlshouwen.jspc.zwpc.model.EvalItem;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 专家评审
 *
 * @author xlli
 */
@Controller
@RequestMapping("/jspc/zjpc/zjpc")
public class ExpreviewController {

    /**
     * 功能根路径
     */
    private String basePath = "jspc/zjpc/";

    /**
     * 数据操作对象
     */
    private ExpreviewDao dao;

    /**
     * 用户数据操作对象
     */
    private UserDao userDao;

    /**
     * 评测项数据操作对象
     */
    private EvalItemDao evalItemDao;

    /**
     * 注入数据库操作对象
     *
     * @param dao
     */
    @Resource(name = "expreviewDao")
    public void setDao(ExpreviewDao dao) {
        this.dao = dao;
    }

    /**
     * 注入数据操作对象
     *
     * @param dao 数据操作对象
     */
    @Resource(name = "userDao")
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * 注入数据操作对象
     *
     * @param dao 数据操作对象
     */
    @Resource(name = "evalItemDao")
    public void setDao(EvalItemDao evalItemDao) {
        this.evalItemDao = evalItemDao;
    }

    /**
     * 跳转到专家评审页面
     *
     * @param request 请求对象
     * @return basePath + "expreviewList"
     * @throws Exception
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String goExpreviewPage(HttpServletRequest request) throws Exception {
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问专家评审页面");
        return basePath + "expreviewList";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String goExpreviewPageForAdmin(HttpServletRequest request) throws Exception {
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问专家评审(管理员)页面");
        return basePath + "expreviewList_admin";
    }

    /**
     * 获取专家评审列表数据
     *
     * @param gridPager 表格分页信息
     * @param request 请求对象
     * @param response 响应对象
     * @throws Exception 抛出所有异常
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public void getExpreviewList(String gridPager, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		映射Pager对象
        Pager pager = PagerPropertyUtils.copy(JSONObject.fromObject(gridPager));
//		获取用户列表数据
        dao.getExpreviewList(pager, request, response);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.SEARCH, "获取用户管理页面中的用户列表数据");
    }

    /**
     * 获取专家评审列表数据
     *
     * @param gridPager 表格分页信息
     * @param request 请求对象
     * @param response 响应对象
     * @throws Exception 抛出所有异常
     */
    @RequestMapping(value = "/admin/list", method = RequestMethod.POST)
    @ResponseBody
    public void getExpreviewListForAdmin(String gridPager, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		映射Pager对象
        Pager pager = PagerPropertyUtils.copy(JSONObject.fromObject(gridPager));
//		获取用户列表数据
        dao.getExpreviewListForAdmin(pager, request, response);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.SEARCH, "获取用户管理页面中的用户列表数据");
    }

    /**
     * 跳转到评审页面
     *
     * @param id 评审编号
     * @param model 反馈对象
     * @param request 请求对象
     * @return basePath + "editExpreview"
     * @throws Exception 抛出所有异常
     */
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String editExpreview(@PathVariable String id,
            Model model, HttpServletRequest request) throws Exception {
//		获取评审对象
        Expreview expreview = dao.getExpreviewById(id);
        //获取被评审人
        User user = userDao.getUserById(expreview.getUser_id());
        //初始化传输对象
        UserVO UVO = new UserVO(user, expreview);
        //设置页面显示标识
        request.setAttribute("editalble", "editalble");

        model.addAttribute("UVO", UVO);

//              记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问评审页面");
//              执行跳转到评审页面
        return basePath + "reviewDetail";
    }

    @RequestMapping(value = "/{id}/adminEdit", method = RequestMethod.GET)
    public String editExpreviewForAdmin(@PathVariable String id,
            Model model, HttpServletRequest request) throws Exception {
//		获取评审对象
        Expreview expreview = dao.getExpreviewById(id);
        //获取被评审人
        User user = userDao.getUserById(expreview.getUser_id());
        //初始化传输对象
        UserVO UVO = new UserVO(user, expreview);
        //设置页面显示标识
        request.setAttribute("editalble", "editalble");

        model.addAttribute("UVO", UVO);

//              记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问评审页面");
//              执行跳转到评审页面
        return basePath + "reviewDetail_admin";
    }

    /**
     * 获取所有评测项数据
     *
     * @param expId
     * @param request 请求对象
     * @return AJAX响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/{expId}/getAllItems", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public AjaxResponse getAllItems(@PathVariable String expId, HttpServletRequest request) throws Exception {
//          定义AJAX响应回执
        AjaxResponse aresp = new AjaxResponse();

        //          获取用户评测项数据
        List<EvalItem> evalItems = new ArrayList<EvalItem>();
        evalItems = evalItemDao.getAllByExpId(expId);

//          具体评测项
        List ET[] = new ArrayList[8];

        for (int i = 0; i < ET.length; i++) {
            ET[i] = new ArrayList<EvalItem>();
        }

        for (EvalItem item : evalItems) {
            if (item.getType().equals("JSYR")) {//教书育人
                ET[0].add(item);
            } else if (item.getType().equals("KCJX")) {//课程教学
                ET[1].add(item);
            } else if (item.getType().equals("JYJXYJ")) {//教育教学研究
                ET[2].add(item);
            } else if (item.getType().equals("YXL")) {//影响了
                ET[3].add(item);
            } else if (item.getType().equals("JYJXJL")) {//教育教学经理
                ET[4].add(item);
            } else if (item.getType().equals("JXJYJL")) {//继续教育经历
                ET[5].add(item);
            } else if (item.getType().equals("ZYJSZC")) {//专业技术职称
                ET[6].add(item);
            } else if (item.getType().equals("QT")) {//其他
                ET[7].add(item);
            }
        }

        Map map = new HashMap();
        map.put("JSYR", getItemForHtml(ET[0], request.getServletContext()));
        map.put("KCJX", getItemForHtml(ET[1], request.getServletContext()));
        map.put("JYJXYJ", getItemForHtml(ET[2], request.getServletContext()));
        map.put("YXL", getItemForHtml(ET[3], request.getServletContext()));
        map.put("JYJXJL", getItemForHtml(ET[4], request.getServletContext()));
        map.put("JXJYJL", getItemForHtml(ET[5], request.getServletContext()));
        map.put("ZYJSZC", getItemForHtml(ET[6], request.getServletContext()));
        map.put("QT", getItemForHtml(ET[7], request.getServletContext()));
        aresp.setExtParam(map);
        aresp.setSuccess(true);
        aresp.setSuccessMessage("加载完成");
        return aresp;
    }

    /**
     * 评审操作
     *
     * @param expreview 评审对象
     * @param bindingResult 绑定结果
     * @param request 请求对象
     * @return ajax响应内容
     * @throws Exception
     */
    @RequestMapping(value = "/ajaxReview", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse ajaxExpertReview(@Valid Expreview expreview,
            BindingResult bindingResult, HttpServletRequest request) throws Exception {

        String id = request.getParameter("id");
        String status = request.getParameter("status");
        String notes = request.getParameter("notes");
//		定义AJAX响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
//		如果发现错误则拼接错误信息执行回执
        if (bindingResult.hasErrors()) {
            ajaxResponse.bindingResultHandler(bindingResult);
//			记录操作日志
            LogUtils.updateOperationLog(request, OperationType.UPDATE, "专家评审（失败：数据验证错误，详细信息：" + AjaxResponse.getBindingResultMessage(bindingResult) + "）");
            return ajaxResponse;
        }
//		获取当前时间
        Date nowDate = new Date();
        //获取当前用户
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        String review_name = sessionUser.getUser_name();
//		设置vo
        expreview.setReview_date(nowDate);
        expreview.setId(id);
        expreview.setStatus(status);
        expreview.setReview_name(review_name);
        expreview.setNote(notes);
//		执行评审
        dao.updateExpreview(expreview);
        //如果评审通过  则修改用户资质
        if ("1".equals(status)) {
            expreview = dao.getExpreviewById(id);
            User user = new User();
            user.setUser_id(expreview.getUser_id());
            user.setQualified(expreview.getQualified());
            userDao.updateUser(user);
        }

//		反馈成功信息
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("专家评审成功！");

        //用‘URL’字段通知前段页面的跳转路径
        Map map = new HashMap();
        map.put("URL", basePath + "zjpc");
        ajaxResponse.setExtParam(map);

//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.UPDATE, "专家评审，评审编号：" + expreview.getId());
        return ajaxResponse;
    }

    /**
     * 评审操作（管理员）
     *
     * @param expreview 评审对象
     * @param bindingResult 绑定结果
     * @param request 请求对象
     * @return ajax响应内容
     * @throws Exception
     */
    @RequestMapping(value = "/ajaxReviewAdmin", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse ajaxExpertReviewForAdmin(@Valid Expreview expreview,
            BindingResult bindingResult, HttpServletRequest request) throws Exception {

        String id = request.getParameter("id");
        String status = request.getParameter("status");
        String notes = request.getParameter("notes");
//		定义AJAX响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
//		如果发现错误则拼接错误信息执行回执
        if (bindingResult.hasErrors()) {
            ajaxResponse.bindingResultHandler(bindingResult);
//			记录操作日志
            LogUtils.updateOperationLog(request, OperationType.UPDATE, "专家评审（失败：数据验证错误，详细信息：" + AjaxResponse.getBindingResultMessage(bindingResult) + "）");
            return ajaxResponse;
        }
//		获取当前时间
        Date nowDate = new Date();
        //获取当前用户
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        String review_name = sessionUser.getUser_name();
//		设置vo
        expreview.setReview_date(nowDate);
        expreview.setId(id);
        expreview.setStatus(status);
        expreview.setReview_name(review_name);
        expreview.setNote(notes);
//		执行评审
        dao.updateExpreview(expreview);
        //如果评审通过  则修改用户资质
        if ("1".equals(status)) {
            expreview = dao.getExpreviewById(id);
            User user = new User();
            user.setUser_id(expreview.getUser_id());
            user.setQualified(expreview.getQualified());
            userDao.updateUser(user);
        }

//		反馈成功信息
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("专家评审成功！");

        //用‘URL’字段通知前段页面的跳转路径
        Map map = new HashMap();
        map.put("URL", basePath + "zjpc/admin");
        ajaxResponse.setExtParam(map);

//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.UPDATE, "专家评审，评审编号：" + expreview.getId());
        return ajaxResponse;
    }

    /**
     * 跳转到确认评审页
     *
     * @param request 请求对象
     * @return 评审确认页面
     * @throws Exception
     */
    @RequestMapping(value = "note", method = RequestMethod.GET)
    public String goNotePage(HttpServletRequest request) throws Exception {
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问专家评审意见页面");
        return basePath + "note";
    }

    /**
     * 删除评审
     *
     * @param ids 评审编号列表
     * @param request 请求对象
     * @return ajax响应对象
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse deleteExpreview(String ids,
            HttpServletRequest request) throws Exception {
//		执行删除图片
        dao.deleteExpreview(ids);
//		执行响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("评审删除成功！");
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.DELETE, "删除评审，评审编号列表：" + ids);
        return ajaxResponse;
    }

    /**
     * 添加评审
     *
     * @param expreview 评审对象
     * @param bindingResult 绑定的错误信息
     * @param request 请求对象
     * @return ajax响应对象
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/ajaxAdd", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse ajaxAddExpreview(@Valid Expreview expreview, BindingResult bindingResult,
            HttpServletRequest request) throws Exception {
//		定义AJAX响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
//		如果发现错误则拼接错误信息执行回执
        if (bindingResult.hasErrors()) {
            ajaxResponse.bindingResultHandler(bindingResult);
//			记录操作日志
            LogUtils.updateOperationLog(request, OperationType.INSERT, "新增图片（失败：数据验证错误，详细信息：" + AjaxResponse.getBindingResultMessage(bindingResult) + "）");
            return ajaxResponse;
        }

//		获取当前用户、当前时间
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        String userId = sessionUser.getUser_id();
        String userName = sessionUser.getUser_id();
        Date nowDate = new Date();

//		设置图片编号 创建时间 创建人
        expreview.setId(new GUID().toString());
        expreview.setCreate_date(nowDate);
        expreview.setUser_id(userId);
        expreview.setUser_name(userName);
        expreview.setStatus("0");

//		执行添加图片
        dao.insertExpreview(expreview);
//		反馈成功信息
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("评审添加成功！");
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.INSERT, "添加评审，评审编号：" + expreview.getId());
        return ajaxResponse;
    }

    private String getItemForHtml(List<EvalItem> items, ServletContext sc) {
        String result = "", preHtml, sufHtml;

        sufHtml = "</tr>"
                + "</table>"
                + "</div>"
                + "</li>";
        if (!items.equals(null) && items.size() > 0) {
            for (int i = 0; i < items.size(); i++) {
                preHtml = "<li class='item-table-list-li'>"
                        + "<div class='item-table-list-box'>"
                        //                        + "<i class='fa fa-edit' style='display:none;' onclick=\"editItem('"+items.get(i).getEvalItem_id()+"','"+items.get(i).getType()+"')\"></i>"
                        //                        + "<i class='fa fa-times' style='display:none;' id='"+items.get(i).getEvalItem_id()+"' onclick=\"deleteItem('"+items.get(i).getEvalItem_id()+"')\"></i>"
                        + "<table class='table  table-responsive item-table-list' width='100%' border='0' cellspacing='0' cellpadding='0'>"
                        + "<tr>";
                String bDate = DateUtils.getYearMonth(items.get(i).getBeginDate());
                String eDate = DateUtils.getYearMonth(items.get(i).getEndDate());
                String level = CodeTableUtils.getValue(sc, "ryjb", items.get(i).getLevel());
                String grade = CodeTableUtils.getValue(sc, "hjdj", items.get(i).getGrade());
                level = StringUtils.isEmpty(level) ? "" : level;
                grade = StringUtils.isEmpty(grade) ? "" : grade;
                result += preHtml;
                result += "<td class='first_td'>" + bDate + "-" + eDate + "</td>";
                result += "<td>" + items.get(i).getName() + "</td>";
                result += "<td>" + level + grade + "</td>";
                result += "<td><a href='" + sc.getContextPath() + "/jspc/zwpc/zwpc/downloadFile?itemId=" + items.get(i).getEvalItem_id() + "' class='table-tc-fj-open'  >附件</a></td>";
                result += sufHtml;
            }
        } else {
            result = "<div class='div_zanwu'>暂无</div>";
        }
        return result;
    }
}
