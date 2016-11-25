/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlshouwen.wzgl.tag.controller;

import com.dlshouwen.core.base.config.CONFIG;
import com.dlshouwen.core.base.extra.grid.model.Pager;
import com.dlshouwen.core.base.extra.grid.utils.PagerPropertyUtils;
import com.dlshouwen.core.base.extra.unique.utils.UniqueUtils;
import com.dlshouwen.core.base.model.AjaxResponse;
import com.dlshouwen.core.base.model.OperationType;
import com.dlshouwen.core.base.model.SessionUser;
import com.dlshouwen.core.base.utils.GUID;
import com.dlshouwen.core.base.utils.LogUtils;
import com.dlshouwen.wzgl.tag.dao.TagDao;
import com.dlshouwen.wzgl.tag.model.Tag;
import java.util.Date;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 *
 * @author admin
 */
@Controller
@RequestMapping("/wzgl/tag/tag")
public class TagController {

    //根路径
    private String basePath = "wzgl/tag/";

    private TagDao dao;

    //注入dao
    @Resource(name = "TagDao")
    public void setDao(TagDao dao) {
        this.dao = dao;
    }

    //跳转到标签页面
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String goTaglPage(HttpServletRequest request, Model model) throws Exception {
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问标签管理页面");
        //跳转到标签列表页面
        return basePath + "tagList";
    }

    /**
     * 获取标签列表数据
     *
     * @param gridPager 表格分页信息
     * @param request 请求对象
     * @param response 响应对象
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public void getArticleList(String gridPager, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Pager pager = PagerPropertyUtils.copy(JSONObject.fromObject(gridPager));
        dao.getTagList(pager, request, response);
//          记录操作日志
        LogUtils.updateOperationLog(request, OperationType.SEARCH, "获取标签管理页面中的标签列表数据");
    }

    /**
     * 跳转到新增标签页面
     *
     * @param model 反馈对象
     * @param request 请求对象
     * @return basePath + 'addTag'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addTag(Model model, HttpServletRequest request) throws Exception {
        Tag tag = new Tag();
        tag.setTag_state("1");
        model.addAttribute("tag", tag);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问新增标签页面");
        return basePath + "addTag";
    }

    /**
     * 执行新增标签
     *
     * @param tag 标签对象
     * @param bindingResult 绑定结果
     * @param request 请求对象
     * @return ajax响应内容
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse addTag(@Valid Tag tag, BindingResult bindingResult,
            HttpServletRequest request) throws Exception {
        AjaxResponse ajaxResponse = new AjaxResponse();
//		校验唯一 标签
        if (!UniqueUtils.unique(dao, ajaxResponse, "标签已经被使用，请重新填写。", "TAG_NAME_UNIQUE_FOR_ADD", tag.getTag_name())) {
            LogUtils.updateOperationLog(request, OperationType.INSERT, "新增标签（新增失败，标签名称已经被使用，标签名称：" + tag.getTag_name() + "）");
            return ajaxResponse;
        }
        if (bindingResult.hasErrors()) {
            ajaxResponse.bindingResultHandler(bindingResult);
            LogUtils.updateOperationLog(request, OperationType.INSERT, "新增标签（新增失败，标签名称：" + tag.getTag_name() + "，错误信息：" + AjaxResponse.getBindingResultMessage(bindingResult) + "）");
            return ajaxResponse;
        }
//		获取当前用户、当前时间
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        String userName = sessionUser.getUser_name();
        Date nowDate = new Date();
//		设置标签名称、创建人、创建时间、编辑人、编辑时间
        tag.setTag_id(new GUID().toString());
        tag.setTag_adduser(userName);
        tag.setTag_addtime(nowDate);
        tag.setTag_edituser(userName);
        tag.setTag_edittime(nowDate);
//		执行新增标签
        dao.insertTag(tag);
//		反馈成功信息
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("标签新增成功！");
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.INSERT, "新增标签，标签id：" + tag.getTag_id() + "，标签名称：" + tag.getTag_name());
        return ajaxResponse;
    }

    /**
     * 跳转到编辑标签页面
     *
     * @param tagId 标签名称
     * @param model 反馈对象
     * @param request 请求对象
     * @return base + 'editTag'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/{tagId}/edit", method = RequestMethod.GET)
    public String editTag(@PathVariable String tagId,
            Model model, HttpServletRequest request) throws Exception {
        Tag tag = dao.getTagById(tagId);
//		设置到反馈对象中
        model.addAttribute("tag", tag);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问编辑标签页面，标签id：" + tag.getTag_id() + "，标签名称：" + tag.getTag_name());
//		执行跳转到编辑页面
        return basePath + "editTag";
    }

    /**
     * 执行编辑标签
     *
     * @param tag 标签对象
     * @param bindingResult 绑定对象
     * @return ajax响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse editTag(@Valid Tag tag, HttpServletRequest request,
            BindingResult bindingResult) throws Exception {
//		定义AJAX响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
//		校验唯一
        if (!UniqueUtils.unique(dao, ajaxResponse, "标签名称已经被使用，请重新填写。", "TAG_NAME_UNIQUE_FOR_EDIT",
                tag.getTag_name(), tag.getTag_id())) {
//			记录操作日志
            LogUtils.updateOperationLog(request, OperationType.UPDATE, "编辑标签（编辑失败，标签名称已经被使用，标签id：" + tag.getTag_id() + "，标签名称：" + tag.getTag_name() + "）");
            return ajaxResponse;
        }
//		如果发现错误则拼接错误信息执行回执
        if (bindingResult.hasErrors()) {
            ajaxResponse.bindingResultHandler(bindingResult);
//			记录操作日志
            LogUtils.updateOperationLog(request, OperationType.UPDATE, "编辑标签（编辑失败，标签id：" + tag.getTag_id() + "，标签名称：" + tag.getTag_name() + "，错误信息：" + AjaxResponse.getBindingResultMessage(bindingResult) + "）");
            return ajaxResponse;
        }
//		获取当前用户、当前时间
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        String userId = sessionUser.getUser_id();
        Date nowDate = new Date();
//		设置编辑人、编辑时间
        tag.setTag_edittime(nowDate);
        tag.setTag_edituser(userId);
//		执行编辑标签
        dao.updateTag(tag);
//		反馈成功信息
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("标签编辑成功！");
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.UPDATE, "编辑标签，标签id：" + tag.getTag_id() + "，标签名称：" + tag.getTag_name());
        return ajaxResponse;
    }

    /**
     * 执行删除标签
     *
     * @param tagIds 标签编号列表
     * @param request 请求对象
     * @return ajax响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse deleteTag(String tagIds, HttpServletRequest request) throws Exception {

        dao.deleteTag(tagIds);
//		执行响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("标签删除成功！");
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.DELETE, "删除标签，标签id：" + tagIds);
        return ajaxResponse;
    }

    //开启标签
    @RequestMapping(value = "/open", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse openTag(String tagIds, HttpServletRequest request) throws Exception {
        dao.openTag(tagIds);

//		执行响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("标签启用成功！");
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.UPDATE, "启用标签，标签编号列表：" + tagIds);
        return ajaxResponse;
    }

    //关闭标签
    @RequestMapping(value = "/close", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse closeTag(String tagIds, HttpServletRequest request) throws Exception {
        dao.closeTag(tagIds);
//		执行响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("标签启用成功！");
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.UPDATE, "关闭标签，标签编号列表：" + tagIds);
        return ajaxResponse;
    }

}
