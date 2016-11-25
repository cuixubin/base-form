package com.dlshouwen.core.mail.controller;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
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
import com.dlshouwen.core.base.utils.DateUtils;
import com.dlshouwen.core.base.utils.GUID;
import com.dlshouwen.core.base.utils.LogUtils;
import com.dlshouwen.core.mail.dao.MailDao;
import com.dlshouwen.core.mail.model.Mail;
import java.util.HashMap;

import net.sf.json.JSONObject;

/**
 * 邮件
 *
 * @author 大连首闻科技有限公司
 * @version 2013-08-17 12:13:03 198
 */
@Controller
@RequestMapping("/core/mail")
public class MailController {

    /**
     * 功能根路径
     */
    private String basePath = "core/mail/";

    /**
     * 数据操作对象
     */
    private MailDao dao;

    /**
     * 注入数据操作对象
     *
     * @param dao 数据操作对象
     */
    @Resource(name = "mailDao")
    public void setDao(MailDao dao) {
        this.dao = dao;
    }

    /**
     * 跳转到收件箱页面
     *
     * @param request 请求对象
     * @return basePath + 'receiveMailList'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/receive", method = RequestMethod.GET)
    public String goReceiveMailPage(HttpServletRequest request) throws Exception {
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问收件箱页面");
        return basePath + "receiveMailList";
    }

    /**
     * 获取收件箱列表
     *
     * @param gridPager 表格分页信息
     * @param request 请求对象
     * @param response 响应对象
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/receive/list", method = RequestMethod.POST)
    public void getReceiveMailList(String gridPager, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		映射Pager对象
        Pager pager = PagerPropertyUtils.copy(JSONObject.fromObject(gridPager));
//		获取登录用户编号
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        String userId = sessionUser.getUser_id();
//		获取收件箱列表
        dao.getReceiveMailList(pager, request, response, userId);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.SEARCH, "获取收件箱页面邮件列表");
    }

    /**
     * 跳转到发件箱页面
     *
     * @param request 请求对象
     * @return basePath + 'sendMailList'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public String goSendMailPage(HttpServletRequest request) throws Exception {
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问发件箱页面 ");
        return basePath + "sendMailList";
    }

    /**
     * 获取发件箱列表
     *
     * @param gridPager 表格分页信息
     * @param request 请求对象
     * @param response 响应对象
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/send/list", method = RequestMethod.POST)
    public void getSendMailList(String gridPager, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		映射Pager对象
        Pager pager = PagerPropertyUtils.copy(JSONObject.fromObject(gridPager));
//		获取登录用户编号
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        String userId = sessionUser.getUser_id();
//		获取发件箱列表
        dao.getSendMailList(pager, request, response, userId);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.SEARCH, "获取收件箱邮件列表数据");
    }

    /**
     * 跳转到草稿箱页面
     *
     * @param request 请求对象
     * @return basePath + 'draftMailList'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/draft", method = RequestMethod.GET)
    public String goDraftMailPage(HttpServletRequest request) throws Exception {
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问草稿箱页面");
        return basePath + "draftMailList";
    }

    /**
     * 获取草稿箱列表
     *
     * @param gridPager 表格分页信息
     * @param request 请求对象
     * @param response 响应对象
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/draft/list", method = RequestMethod.POST)
    public void getDraftMailList(String gridPager, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		映射Pager对象
        Pager pager = PagerPropertyUtils.copy(JSONObject.fromObject(gridPager));
//		获取登录用户编号
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        String userId = sessionUser.getUser_id();
//		获取草稿箱列表
        dao.getDraftMailList(pager, request, response, userId);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.SEARCH, "获取草稿箱邮件列表数据");
    }

    /**
     * 跳转到已删除邮件页面
     *
     * @param request 请求对象
     * @return basePath + 'draftMailList'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String goDeleteMailPage(HttpServletRequest request) throws Exception {
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问已删除邮件页面");
        return basePath + "deleteMailList";
    }

    /**
     * 获取已删除邮件列表
     *
     * @param gridPager 表格分页信息
     * @param request 请求对象
     * @param response 响应对象
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/delete/list", method = RequestMethod.POST)
    public void getDeleteMailList(String gridPager, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		映射Pager对象
        Pager pager = PagerPropertyUtils.copy(JSONObject.fromObject(gridPager));
//		获取登录用户编号
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        String userId = sessionUser.getUser_id();
//		获取已删除邮件列表
        dao.getDeleteMailList(pager, request, response, userId);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.SEARCH, "获取已删除邮件列表数据");
    }

    /**
     * 跳转到查看邮件页面
     *
     * @param mailId 邮件编号
     * @param model 反馈对象
     * @param request 请求对象
     * @return basePath + 'viewMail'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/{mailId}/view", method = RequestMethod.GET)
    public String viewMail(@PathVariable String mailId, Model model, HttpServletRequest request) throws Exception {
//		根据邮件编号获取邮件详细信息并传递到前台
        Map<String, Object> messageInfo = dao.getMailInfoById(mailId);
        model.addAttribute("messageInfo", messageInfo);
//		获取查看信息
        String receiverRead = request.getParameter("receiverRead");
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        String userId = sessionUser.getUser_id();
        if ("true".equals(receiverRead)) {
//			变更查看标识
            dao.receiverViewMail(mailId, userId);
        }
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "查看邮件，邮件编号：" + MapUtils.getString(messageInfo, "mail_id") + "，邮件主题：" + MapUtils.getString(messageInfo, "title"));
        return basePath + "viewMail";
    }

    /**
     * 跳转到新增邮件页面
     *
     * @param model 反馈对象
     * @param request 请求对象
     * @return basePath + 'addMail'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addMail(Model model, HttpServletRequest request) throws Exception {
//		定义新的邮件对象
        Mail mail = new Mail();
//		设置一个临时的邮件编号用于存储草稿
        mail.setMail_id(new GUID().toString());
//		传递到前台
        model.addAttribute("mail", mail);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问新增邮件页面");
        return basePath + "addMail";
    }

    /**
     * 跳转到编辑草稿页面
     *
     * @param mailId 邮件编号
     * @param model 反馈对象
     * @param request 请求对象
     * @return basePath + 'editDraftMail'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/draft/{mailId}/edit", method = RequestMethod.GET)
    public String editDraftMail(@PathVariable String mailId,
            Model model, HttpServletRequest request) throws Exception {
//		获取邮件对象
        Mail mail = dao.getMailById(mailId);
//		传递到前台
        model.addAttribute("mail", mail);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问编辑邮件草稿页面，邮件编号：" + mail.getMail_id() + "，邮件主题：" + mail.getTitle());
        return basePath + "editDraftMail";
    }

    /**
     * 保存草稿
     *
     * @param mail 邮件对象
     * @param receivers 收件人列表
     * @param request 请求对象
     * @return ajax响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/draft/save", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public AjaxResponse saveDraftMail(Mail mail, String receivers, HttpServletRequest request) throws Exception {
//		获取邮件编号
        String mailId = mail.getMail_id();
//		设置邮件的发送类型为草稿，设置发信人、创建时间
        mail.setSend_status("0");
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        mail.setSender(sessionUser.getUser_id());
        mail.setCreate_time(new Date());
//		首先清空当前邮件信息
        dao.deleteTempMail(mailId);
//		添加邮件
        dao.insertMail(mail);
//		添加收件人列表
        dao.insertMailReceivers(mailId, receivers);
//		处理回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("草稿保存成功！");
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.UPDATE, "保存草稿，邮件编号：" + mail.getMail_id() + "，邮件主题：" + mail.getTitle());
        return ajaxResponse;
    }

    /**
     * 发送邮件
     *
     * @param mail 邮件对象
     * @param receivers 收件人列表
     * @param request 请求对象
     * @return ajax响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public AjaxResponse sendMail(@Valid Mail mail, String receivers, HttpServletRequest request) throws Exception {
//		获取邮件编号
        String mailId = mail.getMail_id();
//		获取是否来自草稿
        boolean isFromDraft = dao.getMailIsFromDraft(mailId);
//		如果不是，则重新启用保存
        if (!isFromDraft) {
//			首先清空当前邮件信息
            dao.deleteTempMail(mailId);
//			设置邮件的发送类型为草稿，设置发信人、创建时间
            mail.setSend_status("0");
            SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
            mail.setSender(sessionUser.getUser_id());
            mail.setCreate_time(new Date());
//			添加邮件本体
            dao.insertMail(mail);
//			添加收件人列表
            dao.insertMailReceivers(mailId, receivers);
        }
        String sendTime = DateUtils.getNowTime();
//		执行发送功能
        dao.sendMail(mailId, sendTime);
//		执行ajax回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("邮件发送成功！");
        
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.UPDATE, "发送邮件，邮件编号：" + mail.getMail_id() + "，邮件主题：" + mail.getTitle());
        return ajaxResponse;
    }

    /**
     * 跳转到答复页面
     *
     * @param mailId 邮件编号
     * @param model 反馈对象
     * @param request 请求对象
     * @return basePath + 'replyMail'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/{mailId}/reply", method = RequestMethod.GET)
    public String replyMail(@PathVariable String mailId,
            Model model, HttpServletRequest request) throws Exception {
//		获取原邮件信息
        Mail replyMail = dao.getMailById(mailId);
        model.addAttribute("replyMail", replyMail);
//		获取新的邮件对象发送到前台
        Mail mail = new Mail();
        mail.setMail_id(new GUID().toString());
        model.addAttribute("mail", mail);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问答复邮件页面，答复邮件编号：" + replyMail.getMail_id() + "，答复邮件主题：" + replyMail.getTitle());
        return basePath + "replyMail";
    }

    /**
     * 跳转到转发页面
     *
     * @param mailId 邮件编号
     * @param model 反馈对象
     * @param request 请求对象
     * @return basePath + 'replyMail'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/{mailId}/transmit", method = RequestMethod.GET)
    public String transmitMail(@PathVariable String mailId,
            Model model, HttpServletRequest request) throws Exception {
//		获取原邮件信息
        Mail transmitMail = dao.getMailById(mailId);
        model.addAttribute("transmitMail", transmitMail);
//		获取新的邮件对象发送到前台
        Mail mail = new Mail();
        mail.setMail_id(new GUID().toString());
        model.addAttribute("mail", mail);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问转发邮件页面，转发邮件编号：" + transmitMail.getMail_id() + "，转发邮件主题：" + transmitMail.getTitle());
        return basePath + "transmitMail";
    }

    /**
     * 发件人删除
     *
     * @param mailIds 邮件编号列表
     * @param request 请求对象
     * @return ajax响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/delete/send", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse deleteMailForSend(String mailIds, HttpServletRequest request) throws Exception {
//		获取登录用户编号
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        String userId = sessionUser.getUser_id();
//		拼接为字符串格式执行删除
        dao.deleteMailForSend(mailIds, userId);
//		执行响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("邮件删除成功！");

//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.DELETE, "发件人删除，删除邮件编号列表：" + mailIds);
        return ajaxResponse;
    }

    /**
     * 收件人删除
     *
     * @param mailIds 邮件编号列表
     * @param request 请求对象
     * @return ajax响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/delete/receive", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse deleteMailForReceive(String mailIds, HttpServletRequest request) throws Exception {
//		获取登录用户编号
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        String userId = sessionUser.getUser_id();
//		拼接为字符串格式执行删除
        dao.deleteMailForReceive(mailIds, userId);
//		执行响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("邮件删除成功！");
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.DELETE, "收件人删除，删除邮件编号列表：" + mailIds);
        return ajaxResponse;
    }

    /**
     * 草稿删除
     *
     * @param mailIds 邮件编号列表
     * @param request 请求对象
     * @return ajax响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/delete/draft", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse deleteMailForDraft(String mailIds, HttpServletRequest request) throws Exception {
//		获取登录用户编号
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        String userId = sessionUser.getUser_id();
//		拼接为字符串格式执行删除
        dao.deleteMailForDraft(mailIds, userId);
//		执行响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("邮件删除成功！");

//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.DELETE, "删除草稿箱邮件，邮件编号列表：" + mailIds);
        return ajaxResponse;
    }

    /**
     * 彻底删除
     *
     * @param mailIds 邮件编号列表
     * @param request 请求对象
     * @return ajax响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public AjaxResponse deleteMail(String mailIds, HttpServletRequest request) throws Exception {
//		获取登录用户编号
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        String userId = sessionUser.getUser_id();
//		遍历邮件编号执行删除
        for (String mailId : mailIds.split(",")) {
//			获取邮件删除来源
            String deleteFrom = request.getParameter("delete_from_" + mailId);
//			执行删除
            dao.deleteMail(mailId, userId, deleteFrom);
        }
//		执行响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("邮件删除成功！");

//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.DELETE, "已删除邮件列表中彻底删除邮件，邮件编号列表：" + mailIds);
        return ajaxResponse;
    }

}
