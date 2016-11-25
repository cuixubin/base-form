/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlshouwen.wzgl.channel.controller;

import com.dlshouwen.core.base.config.CONFIG;
import com.dlshouwen.core.base.model.AjaxResponse;
import com.dlshouwen.core.base.model.OperationType;
import com.dlshouwen.core.base.model.SessionUser;
import com.dlshouwen.core.base.utils.GUID;
import com.dlshouwen.core.base.utils.LogUtils;
import com.dlshouwen.wzgl.channel.dao.ChannelDao;
import com.dlshouwen.wzgl.channel.model.Channel;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import net.sf.json.JSONArray;
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
@RequestMapping("/wzgl/channel/channel")
public class ChannelController {

    //根路径
    private String basePath = "wzgl/channel/";

    private ChannelDao dao;

    //注入DAO
    @Resource(name = "ChannelDao")
    public void setDao(ChannelDao dao) {
        this.dao = dao;
    }

    //跳转到栏目页面
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String goChannelPage(HttpServletRequest request, Model model) throws Exception {
        List<Map<String, Object>> channelList = dao.getChannelList();
        model.addAttribute("channelTree", JSONArray.fromObject(channelList).toString());
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问栏目管理页面");
        return basePath + "channelList";
    }

    //获取栏目信息
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    @ResponseBody
    public Channel viewChannel(String channelId, HttpServletRequest request, Model model) throws Exception {
        Channel channel = dao.getChannelById(channelId);
        LogUtils.updateOperationLog(request, OperationType.VISIT, "获取栏目信息，栏目编号：" + channel.getChannel_id() + "，栏目名称：" + channel.getChannel_name());
        return channel;
    }

    //跳转到添加页面  perChannelId 代表的是上一级栏目的Id  
    @RequestMapping(value = "/{preChannelId}/add", method = RequestMethod.GET)
    public String addChannelPage(@PathVariable String preChannelId, HttpServletRequest request, Model model) throws Exception {
        Channel channel = new Channel();
        channel.setChannel_parent(preChannelId);
        //默认为网站栏目
        channel.setChannel_type("1");
        //默认为启用状态
        channel.setChannel_state("1");
        model.addAttribute("channel", channel);
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问新增栏目页面，上级栏目编号：" + preChannelId);
        return basePath + "addChannel";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse addChannel(@Valid Channel channel, BindingResult bindingResult,
            HttpServletRequest request) throws Exception {
        AjaxResponse ajaxResponse = new AjaxResponse();
        if (bindingResult.hasErrors()) {
            ajaxResponse.bindingResultHandler(bindingResult);
            LogUtils.updateOperationLog(request, OperationType.INSERT, "新增栏目（失败：数据验证错误，栏目编号：" + channel.getChannel_id() + "，栏目名称：" + channel.getChannel_name() + "）");
            return ajaxResponse;
        }
        channel.setChannel_id(new GUID().toString());
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        //创建人
        channel.setChannel_createUser(sessionUser.getUser_id());
        channel.setChannel_createDate(new Date());
        channel.setChannel_edituser(sessionUser.getUser_id());
        channel.setChannel_updateDate(new Date());

        dao.insertChannel(channel);
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("栏目新增成功！");
        ajaxResponse.setData(channel);
        //通知前端跳转路径
         Map map = new HashMap();
        map.put("URL", basePath + "channel");
        ajaxResponse.setExtParam(map);
        
        
        LogUtils.updateOperationLog(request, OperationType.INSERT, "新增栏目，栏目编号：" + channel.getChannel_id() + "，栏目名称：" + channel.getChannel_name());
        return ajaxResponse;
    }

    /**
     * 跳转到编辑栏目页面
     *
     * @param channelId 栏目id
     * @param request 请求对象
     * @param model 反馈对象
     * @return base + 'editChannel'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/{channelId}/edit", method = RequestMethod.GET)
    public String editChannelPage(@PathVariable String channelId, HttpServletRequest request, Model model) throws Exception {
        Channel channel = dao.getChannelById(channelId);
        model.addAttribute("channel", channel);
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问编辑栏目页面，栏目编号：" + channel.getChannel_id() + "，栏目名称：" + channel.getChannel_name());
//		执行跳转到编辑页面
        return basePath + "editChannel";
    }

    /**
     * 执行编辑栏目
     *
     * @param channel 栏目对象
     * @param bindingResult 绑定对象
     * @param request 请求对象
     * @return ajax响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse editChannel(@Valid Channel channel, BindingResult bindingResult,
            HttpServletRequest request) throws Exception {
        AjaxResponse ajaxResponse = new AjaxResponse();
        if (bindingResult.hasErrors()) {
            ajaxResponse.bindingResultHandler(bindingResult);
            LogUtils.updateOperationLog(request, OperationType.UPDATE, "编辑栏目（失败：数据验证错误，栏目编号：" + channel.getChannel_id() + "，栏目名称：" + channel.getCreator_name() + "）");
            return ajaxResponse;
        }
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        channel.setChannel_edituser(sessionUser.getUser_id());
        channel.setChannel_updateDate(new Date());
//		执行编辑
        dao.updateChannel(channel);
//		反馈成功信息
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("栏目编辑成功！");
        ajaxResponse.setData(channel);
        
         //通知前端跳转路径
         Map map = new HashMap();
        map.put("URL", basePath + "channel");
        ajaxResponse.setExtParam(map);
        
        LogUtils.updateOperationLog(request, OperationType.UPDATE, "编辑栏目，栏目编号：" + channel.getChannel_id() + "，栏目名称：" + channel.getCreator_name() + "）");
        return ajaxResponse;
    }

    /**
     * 执行删除栏目
     *
     * @param channelId 栏目编号
     * @param request 请求对象
     * @return ajax响应回执
     * @throws Exception 抛出全部异常 删除的时候需要判断栏目下边是否有内容
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse deleteChannel(String channelIds, HttpServletRequest request) throws Exception {
        StringBuffer deleteChannelIds = new StringBuffer();
        int totalCount = channelIds.split(",").length;
        int hasChannelCount = 0;
        int hasChannelArticle = 0;
        StringBuffer successIds = new StringBuffer();
        StringBuffer failureIds = new StringBuffer();
//		遍历判断
        for (String channelId : channelIds.split(",")) {
//			系统是否包含栏目
            boolean isHaveSubDept = dao.getSubChannelCount(channelId) > 0 ? true : false;
            if (isHaveSubDept) {
                hasChannelCount++;
                failureIds.append(channelId).append(",");
                continue;
            }
            //           栏目是否包含内容
            boolean isChannelArticle = dao.getChannelArticle(channelId);
            if (isChannelArticle) {
                hasChannelArticle++;
                failureIds.append(channelId).append(",");
                continue;
            }

            successIds.append(channelId).append(",");
//			拼接删除栏目编号
            deleteChannelIds.append(channelId).append(",");
        }
        if (deleteChannelIds.length() > 0) {
            deleteChannelIds.deleteCharAt(deleteChannelIds.length() - 1);
//			执行删除栏目
            dao.deleteChannel(deleteChannelIds.toString());
        }
//		处理响应信息
        StringBuffer message = new StringBuffer();
        message.append("本次共执行").append(totalCount).append("条记录，其中：<br /><br />");
        message.append("　　成功删除栏目").append(totalCount - hasChannelCount - hasChannelArticle).append("条。<br />");
        if (hasChannelCount > 0) {
            message.append("　　因为包含子栏目无法删除的记录共").append(hasChannelCount).append("条。<br />");
        }
        if (hasChannelArticle > 0) {
            message.append("　　因为栏目包含内容无法删除的记录共").append(hasChannelArticle).append("条。<br />");
        }
//		执行响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage(message.toString());
        ajaxResponse.setData(deleteChannelIds.toString().replaceAll("'", ""));
        
         //通知前端跳转路径
         Map map = new HashMap();
        map.put("URL", basePath + "channel");
        ajaxResponse.setExtParam(map);
        
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.DELETE,
                "删除栏目，成功删除栏目：" + successIds.toString() + "；因为包含子栏目无法删除的栏目：" + failureIds.toString());
        return ajaxResponse;
    }

    /**
     * 选择栏目
     *
     * @param type 选择类别
     * @param request 请求对象
     * @param model 反馈对象
     * @return basePath + 'selectChannel'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/select/{type}", method = RequestMethod.GET)
    public String selectChannel(@PathVariable String type,
            HttpServletRequest request, Model model) throws Exception {
        String channelId = request.getParameter("channelId");
//		传递选择类别到前台
        model.addAttribute("type", type);
//		获取栏目树传递到前台
        List<Map<String, Object>> channelList = dao.getChannelList();
        if (channelId != null && channelId.trim().length() > 0) {
            for (Map obj : channelList) {
                String channelId1 = (String) obj.get("channel_id");
                if (channelId.equals(channelId1)) {
                    obj.put("checked", "true");
                    break;
                }
            }
        }
        model.addAttribute("channelTree", JSONArray.fromObject(channelList));
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问选择栏目页面");
        return basePath + "selectChannel";
    }

}
