/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlshouwen.tdjs.video.controller;

import com.dlshouwen.core.base.config.CONFIG;
import com.dlshouwen.core.base.extra.grid.model.Pager;
import com.dlshouwen.core.base.extra.grid.utils.PagerPropertyUtils;
import com.dlshouwen.core.base.http.FileUploadClient;
import com.dlshouwen.core.base.model.AjaxResponse;
import com.dlshouwen.core.base.model.OperationType;
import com.dlshouwen.core.base.model.SessionUser;
import com.dlshouwen.core.base.utils.AttributeUtils;
import com.dlshouwen.core.base.utils.ConvertVideo;
import com.dlshouwen.core.base.utils.GUID;
import com.dlshouwen.core.base.utils.LogUtils;
import com.dlshouwen.core.base.utils.WebUtil;
import com.dlshouwen.core.system.dao.UserDao;
import com.dlshouwen.core.system.model.User;
import com.dlshouwen.tdjs.video.dao.TdjsVideoDao;
import com.dlshouwen.tdjs.video.model.Video;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 视频controller
 *
 * @author admin
 */
@Controller
@RequestMapping("/tdjs/tdjsVideo/video")
public class TdjsVideoController {

    //根路径
    private String basePath = "tdjs/video/";

    private TdjsVideoDao dao;
    private UserDao userDao;

    //注入dao
    @Resource(name = "tdjsVideoDao")
    public void setDao(TdjsVideoDao dao) {
        this.dao = dao;
    }

    @Resource(name = "userDao")
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    //跳转到视频页面
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String goVideolPage(HttpServletRequest request, Model model) throws Exception {
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问视频管理页面");
        //跳转到视频列表页面
        return basePath + "videoList";
    }

    /**
     * 获取视频列表数据
     *
     * @param gridPager 表格分页信息
     * @param request 请求对象
     * @param response 响应对象
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public void getVideoList(String gridPager, HttpServletRequest request, HttpServletResponse response) throws Exception {
//          获取当前用户
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        User user = userDao.getUserById(sessionUser.getUser_id());
        Pager pager = PagerPropertyUtils.copy(JSONObject.fromObject(gridPager));
        if (StringUtils.isNotEmpty(user.getIdentity()) && user.getIdentity().equals("1")) {
            if (StringUtils.isEmpty(user.getTeam_id())) {//超级管理员
                dao.getVideoList(pager, request, response);
            } else {//普通团队管理者
                dao.getVideoList(pager, request, response, user.getTeam_id(), null);
            }
        } else if (StringUtils.isNotEmpty(user.getIdentity()) && user.getIdentity().equals("0")
                && StringUtils.isNotEmpty(user.getTeam_id())) {//团队的普通用户
            dao.getVideoList(pager, request, response, user.getTeam_id(), user.getUser_id());
        }
//          记录操作日志
        LogUtils.updateOperationLog(request, OperationType.SEARCH, "获取视频管理页面中的视频列表数据");
    }

    /**
     * 跳转到新增视频页面
     *
     * @param model 反馈对象
     * @param request 请求对象
     * @return basePath + 'addVideo'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addVideo(Model model, HttpServletRequest request) throws Exception {
        Video video = new Video();
        video.setVd_isdisplay("0");
        model.addAttribute("video", video);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问新增视频页面");
        return basePath + "addVideo";
    }

    /**
     * 执行新增视频
     *
     * @param video 视频对象
     * @param bindingResult 绑定结果
     * @param request 请求对象
     * @return ajax响应内容
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse addVideo(@Valid Video video, BindingResult bindingResult,
            HttpServletRequest request) throws Exception {

        AjaxResponse ajaxResponse = new AjaxResponse();
        String localPath = WebUtil.getCookie(request, "videoPath");

        String videoTime = WebUtil.getCookie(request, "videoTime");
        String coverPath = WebUtil.getCookie(request, "coverPath");

        if (localPath == null || localPath.trim().length() == 0) {
            ajaxResponse.setWarning(true);
            ajaxResponse.setWarningMessage("验证出现错误：<br /><br />&nbsp;&nbsp;&nbsp;&nbsp;请上传一个视频后在保存");
            return ajaxResponse;
        }
        if (bindingResult.hasErrors()) {
            ajaxResponse.bindingResultHandler(bindingResult);
            LogUtils.updateOperationLog(request, OperationType.INSERT, "新增视频（新增失败，视频名称：" + video.getVd_name() + "，错误信息：" + AjaxResponse.getBindingResultMessage(bindingResult) + "）");
            return ajaxResponse;
        }
//		获取当前用户、当前时间
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        String userName = sessionUser.getUser_name();
        Date nowDate = new Date();
//		设置视频名称、创建人、创建时间、视频路径
        video.setVd_id(new GUID().toString());
        video.setVd_uploaduser(userName);
        video.setVd_uploaddate(nowDate);
        video.setVd_thumbnailspath(coverPath);
        video.setVd_savepath(localPath);
        video.setVd_status("1");
        video.setVd_time(videoTime);
//		执行新增视频
        dao.insertVideo(video);
        String toPath = localPath.substring(0, localPath.lastIndexOf("/"));
        Thread thread = new Thread(new SimpleThread(localPath, toPath, video.getVd_id()));
        thread.start();
//		反馈成功信息
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("视频新增成功！");
        //通知前端跳转路径
        Map map = new HashMap();
        map.put("URL", "tdjs/tdjsVideo/video");
        ajaxResponse.setExtParam(map);

//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.INSERT, "新增视频，视频id：" + video.getVd_id() + "，视频名称：" + video.getVd_name());
        return ajaxResponse;
    }

    /**
     * 跳转到编辑视频页面
     *
     * @param videoId 视频名称
     * @param model 反馈对象
     * @param request 请求对象
     * @return base + 'editVideo'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/{videoId}/edit", method = RequestMethod.GET)
    public String editVideo(@PathVariable String videoId,
            Model model, HttpServletRequest request) throws Exception {
        Video video = dao.getVideoById(videoId);
//		设置到反馈对象中
        model.addAttribute("video", video);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问编辑视频页面，视频id：" + video.getVd_id() + "，视频名称：" + video.getVd_name());
//		执行跳转到编辑页面
        return basePath + "editVideo";
    }

    /**
     * 执行编辑视频
     *
     * @param video 视频对象
     * @param bindingResult 绑定对象
     * @return ajax响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse editVideo(@Valid Video video, HttpServletRequest request,
            BindingResult bindingResult) throws Exception {
//		定义AJAX响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
//		如果发现错误则拼接错误信息执行回执
        if (bindingResult.hasErrors()) {
            ajaxResponse.bindingResultHandler(bindingResult);
//			记录操作日志
            LogUtils.updateOperationLog(request, OperationType.UPDATE, "编辑视频（编辑失败，视频id：" + video.getVd_id() + "，视频名称：" + video.getVd_name() + "，错误信息：" + AjaxResponse.getBindingResultMessage(bindingResult) + "）");
            return ajaxResponse;
        }
//		获取当前用户、当前时间
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        String userName = sessionUser.getUser_name();
        Date nowDate = new Date();
//		设置编辑人、编辑时间
        video.setVd_updatedate(nowDate);
        video.setVd_updateuser(userName);
//		执行编辑视频
        dao.updateVideo(video);
//		反馈成功信息
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("视频编辑成功！");
        //通知前端跳转路径
        Map map = new HashMap();
        map.put("URL", "tdjs/tdjsVideo/video");
        ajaxResponse.setExtParam(map);

//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.UPDATE, "编辑视频，视频id：" + video.getVd_id() + "，视频名称：" + video.getVd_name());
        return ajaxResponse;
    }

    /**
     * 执行删除视频
     *
     * @param videoIds 视频编号列表
     * @param request 请求对象
     * @return ajax响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse deleteVideo(String videoIds, HttpServletRequest request) throws Exception {

        dao.deleteVideo(videoIds);
//		执行响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("视频删除成功！");
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.DELETE, "删除视频，视频id：" + videoIds);
        return ajaxResponse;
    }

    //开启视频
    @RequestMapping(value = "/open", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse openVideo(String videoIds, HttpServletRequest request) throws Exception {
        dao.openVideo(videoIds);

//		执行响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("视频启用成功！");
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.UPDATE, "启用视频，视频编号列表：" + videoIds);
        return ajaxResponse;
    }

    //关闭视频
    @RequestMapping(value = "/close", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse closeVideo(String videoIds, HttpServletRequest request) throws Exception {
        dao.closeVideo(videoIds);
//		执行响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("视频启用成功！");
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.UPDATE, "关闭视频，视频编号列表：" + videoIds);
        return ajaxResponse;
    }

    /**
     * 上传视频
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse uploadVideo(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String path = "";
        String videoTime = "";
        String coverPath = "";
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartRequest.getFile("file");
        String fileName = multipartFile.getOriginalFilename();
        if (multipartFile != null && StringUtils.isNotEmpty(fileName)) {
            JSONObject jobj = FileUploadClient.upFile(request, fileName, multipartFile.getInputStream());
            if (jobj != null && jobj.getString("responseMessage").equals("OK")) {
                path = jobj.getString("fpath");
                //视频时长
                videoTime = jobj.getString("videoTime");
                coverPath = jobj.getString("videoCoverPath");
            }
        }
            WebUtil.addCookie(request, response, "videoPath", path, -1);
            WebUtil.addCookie(request, response, "videoTime", videoTime, -1);
            WebUtil.addCookie(request, response, "coverPath", coverPath, -1);

//		执行响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("视频启用成功！");
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.UPDATE, "上传视频文件，视频文件名称：" + fileName);
        return ajaxResponse;
    }

    /**
     * 跳转到视频预览页面
     *
     * @param model 反馈对象
     * @param request 请求对象
     * @return basePath + 'addVideo'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/preview", method = RequestMethod.GET)
    public String previewVideo(String videoId, HttpServletRequest request) throws Exception {
        Video video = dao.getVideoById(videoId);
        request.setAttribute("videoPath", video.getVd_savepath().replaceAll("\\\\", "/"));
        String videoName = "";
        if (video.getVd_savepath() != null) {
            videoName = video.getVd_savepath().substring(video.getVd_savepath().lastIndexOf("\\") + 1);
        }
        request.setAttribute("imagePath", video.getVd_thumbnailspath().replaceAll("\\\\", "/"));
        request.setAttribute("videoName", videoName);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问预览视频页面");
        return basePath + "preview";
    }
}
