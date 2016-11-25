/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlshouwen.tdjs.picture.controller;

import com.dlshouwen.core.base.config.CONFIG;
import com.dlshouwen.core.base.extra.grid.model.Pager;
import com.dlshouwen.core.base.extra.grid.utils.PagerPropertyUtils;
import com.dlshouwen.core.base.http.FileUploadClient;
import com.dlshouwen.core.base.model.AjaxResponse;
import com.dlshouwen.core.base.model.OperationType;
import com.dlshouwen.core.base.model.SessionUser;
import com.dlshouwen.core.base.utils.GUID;
import com.dlshouwen.core.base.utils.LogUtils;
import com.dlshouwen.core.system.dao.UserDao;
import com.dlshouwen.core.system.model.User;
import com.dlshouwen.tdjs.album.dao.AlbumDao;
import com.dlshouwen.tdjs.album.model.Album;
import com.dlshouwen.tdjs.picture.dao.PictureDao;
import com.dlshouwen.tdjs.picture.model.Picture;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
 * 大图管理
 *
 * @author xlli
 */
@Controller
@RequestMapping("/tdjs/tdjsPicture/picture")
public class TdjsPictureController {

    /**
     * 功能根路径
     */
    private String basePath = "tdjs/picture/";

    /**
     * 数据操作对象
     */
    private PictureDao dao;
    private UserDao userDao;
    private AlbumDao albumDao;

    /**
     * 注入数据操作对象
     *
     * @param dao 数据操作对象
     */
    @Resource(name = "TdjsPictureDao")
    public void setDao(PictureDao dao) {
        this.dao = dao;
    }

    @Resource(name = "userDao")
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Resource(name = "TdjsAlbumDao")
    public void setAlbumDao(AlbumDao albumDao) {
        this.albumDao = albumDao;
    }

    /**
     * 跳转到大图管理
     *
     * @param request 请求对象
     * @return basePath + "pictureList"
     * @throws Exception
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String goPicturePage(HttpServletRequest request) throws Exception {
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问用户管理页面");
        return basePath + "pictureList";
    }

    /**
     * 获取图片列表数据
     *
     * @param gridPager 表格分页信息
     * @param request 请求对象
     * @param response 响应对象
     * @throws Exception 抛出所有异常
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public void getPictureList(String gridPager, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		获取当前用户
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        User user = userDao.getUserById(sessionUser.getUser_id());
//		映射Pager对象
        Pager pager = PagerPropertyUtils.copy(JSONObject.fromObject(gridPager));
//		获取用户列表数据
        if (StringUtils.isNotEmpty(user.getIdentity()) && user.getIdentity().equals("1")) {
            if (StringUtils.isEmpty(user.getTeam_id())) {//超级管理员
                dao.getPictureList(pager, request, response);
            } else {//普通团队管理者
                dao.getPictureList(pager, request, response, user.getTeam_id(), null);
            }
        } else if (StringUtils.isNotEmpty(user.getIdentity()) && user.getIdentity().equals("0")
                && StringUtils.isNotEmpty(user.getTeam_id())) {//团队的普通用户
            dao.getPictureList(pager, request, response, user.getTeam_id(), user.getUser_id());
        }

//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.SEARCH, "获取用户管理页面中的用户列表数据");
    }

    /**
     * 跳转到图片添加页面
     *
     * @param request
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addPicture(HttpServletRequest request, Model model) throws Exception {
        //		新增用户对象
        Picture pic = new Picture();
        //这是默认显示
        pic.setShow("1");
        //传递图片对象到前台
        model.addAttribute("picture", pic);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问新增用户页面");
        return basePath + "addPicture";
    }

    /**
     * 执行添加图片
     *
     * @param picture 图片对象
     * @param bindingResult 绑定的错误信息
     * @param request 请求对象
     * @return ajax响应结果
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/ajaxAdd", method = RequestMethod.POST)
    @ResponseBody
    public void ajaxAddPicture(@Valid Picture picture, BindingResult bindingResult,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
//		定义AJAX响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
//		如果发现错误则拼接错误信息执行回执zz
        if (bindingResult.hasErrors()) {
            ajaxResponse.bindingResultHandler(bindingResult);
//			记录操作日志
            LogUtils.updateOperationLog(request, OperationType.INSERT, "新增图片（失败：数据验证错误，详细信息：" + AjaxResponse.getBindingResultMessage(bindingResult) + "）");

            response.setContentType("text/html;charset=utf-8");
            JSONObject obj = JSONObject.fromObject(ajaxResponse);
            response.getWriter().write(obj.toString());
            return;

        }
        String path = "";
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartRequest.getFile("picture");
        if (multipartFile != null && StringUtils.isNotEmpty(multipartFile.getOriginalFilename())) {
            //调用向文件服务器上传文件
            JSONObject jobj = FileUploadClient.upFile(request, multipartFile.getOriginalFilename(), multipartFile.getInputStream());
            if (null != jobj && jobj.getString("responseMessage").equals("OK")) {
                path = jobj.getString("fpath");
            }

        }

//		获取当前用户、当前时间
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        String userId = sessionUser.getUser_id();
        String userName = sessionUser.getUser_name();
        Date nowDate = new Date();

//		设置图片编号 创建时间 创建人 图片路径
        picture.setPicture_id(new GUID().toString());
        picture.setCreate_time(nowDate);
        picture.setUser_id(userId);
        picture.setUser_name(userName);
        path = path.replaceAll("\\\\", "/");
        picture.setPath(path);

//		执行添加图片
        dao.insertPicture(picture);
//		反馈成功信息
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("图片添加成功！");
        //通知前端跳转路径
        Map map = new HashMap();
        map.put("URL", "tdjs/tdjsPicture/picture");
        ajaxResponse.setExtParam(map);

//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.INSERT, "添加图片，图片编号：" + picture.getPicture_id());

        response.setContentType("text/html;charset=utf-8");
        JSONObject obj = JSONObject.fromObject(ajaxResponse);
        response.getWriter().write(obj.toString());

    }

    /**
     * 跳转到图片编辑页面
     *
     * @param pictureId 图片ID
     * @param request 请求对象
     * @param model 反馈对象
     * @return basePath + "editPicture"
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/{pictureId}/edit", method = RequestMethod.GET)
    public String editPicture(@PathVariable String pictureId, HttpServletRequest request, Model model) throws Exception {
//		获取用户详细信息
        Picture pic = dao.getPictureById(pictureId);
//		将用户放置在反馈对象中
        model.addAttribute("picture", pic);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问编辑图片页面，图片编号：" + pic.getPicture_id() + "，图片名称：" + pic.getPicture_name());
        return basePath + "editPicture";
    }

    /**
     * 执行更新图片
     *
     * @param picture 图片对象
     * @param bindingResult 绑定的错误信息
     * @param request 请求对象
     * @return ajax响应对象
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/ajaxEdit", method = RequestMethod.POST)
    @ResponseBody
    public void ajaxEditPicture(@Valid Picture picture, HttpServletRequest request,
            BindingResult bindingResult, HttpServletResponse response) throws Exception {
//          定义AJAX响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
//		如果发现错误则拼接错误信息执行回执
        if (bindingResult.hasErrors()) {
            ajaxResponse.bindingResultHandler(bindingResult);
//			记录操作日志
            LogUtils.updateOperationLog(request, OperationType.INSERT, "更新图片（失败：数据验证错误，详细信息：" + AjaxResponse.getBindingResultMessage(bindingResult) + "）");

            response.setContentType("text/html;charset=utf-8");
            JSONObject obj = JSONObject.fromObject(ajaxResponse);
            response.getWriter().write(obj.toString());
            return;

        }

        String path = null;
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartRequest.getFile("picture");
        String orgFilename = multipartFile.getOriginalFilename();
        if (multipartFile != null && StringUtils.isNotEmpty(orgFilename)) {
            JSONObject jobj = FileUploadClient.upFile(request, orgFilename, multipartFile.getInputStream());
            if (jobj != null && jobj.getString("responseMessage").equals("OK")) {
                path = jobj.getString("fpath");
            }
        }
        
        Picture oldPicture = dao.getPictureById(picture.getPicture_id());
        oldPicture.setPicture_name(picture.getPicture_name());
        oldPicture.setDescription(picture.getDescription());
        oldPicture.setFlag(picture.getFlag());
        oldPicture.setShow(picture.getShow());
        oldPicture.setTeam_id(picture.getTeam_id());
        if(StringUtils.isNotEmpty(path)) {
            oldPicture.setPath(path);
        }

//		获取当前时间
        Date nowDate = new Date();

//		设置图片更新时间 路径
        oldPicture.setUpdate_time(nowDate);

//		执行更新图片
        dao.updatePicture(oldPicture);
//		反馈成功信息
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("图片更新成功！");
        //通知前端跳转路径
        Map map = new HashMap();
        map.put("URL", "tdjs/tdjsPicture/picture");
        ajaxResponse.setExtParam(map);

//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.INSERT, "更新图片，图片编号：" + picture.getPicture_id());

        response.setContentType("text/html;charset=utf-8");
        JSONObject obj = JSONObject.fromObject(ajaxResponse);
        response.getWriter().write(obj.toString());
    }

    /**
     * 删除图片
     *
     * @param pictureIds 图片列表编号
     * @param request 请求对象
     * @return ajax响应对象
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse deletePicture(String pictureIds,
            HttpServletRequest request) throws Exception {
//		执行删除图片
        dao.deletePicture(pictureIds);
//		执行响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("图片删除成功！");
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.DELETE, "删除图片，图片编号列表：" + pictureIds);
        return ajaxResponse;
    }

    /**
     * 显示图片
     *
     * @param pictureIds 图片编号列表
     * @param request 请求对象
     * @return ajax响应对象
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/show", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse showPicture(String pictureIds, HttpServletRequest request) throws Exception {
//		执行图片显示
        dao.showPicture(pictureIds);
//		执行响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("图片显示成功！");
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.UPDATE, "显示图片，图片编号列表：" + pictureIds);
        return ajaxResponse;
    }

    /**
     * 隐藏图片
     *
     * @param pictureIds 图片编号列表
     * @param request 请求对象
     * @return ajax响应对象
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/hide", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse hidePicture(String pictureIds, HttpServletRequest request) throws Exception {
//		执行图片隐藏
        int i = dao.hidePicture(pictureIds);
//		执行响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("图片隐藏成功！");
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.UPDATE, "隐藏图片，图片编号列表：" + pictureIds);
        return ajaxResponse;
    }

    /**
     * 跳转到多图片添加页面
     *
     * @param request 请求对象
     * @param Id 相册的id
     * @param model
     * @return basePath + "pictureList"
     * @throws Exception
     */
    @RequestMapping(value = "/{albumId}/batchAdd", method = RequestMethod.GET)
    public String batchAdd(HttpServletRequest request, @PathVariable String albumId, Model model) throws Exception {
        //将相册的id放域中
        model.addAttribute("albumId", albumId);

//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问图片管理页面");
        return basePath + "batchAdd";
    }

    /**
     * 执行添加多图片
     *
     * @param picture 图片对象
     * @param bindingResult 绑定的错误信息
     * @param request 请求对象
     * @return ajax响应结果
     */
    @RequestMapping(value = "/{albumId}/ajaxMultipartAdd", method = RequestMethod.POST)
    @ResponseBody
    public void ajaxAddMultipartPictureAl(@Valid Picture picture, BindingResult bindingResult,
            HttpServletRequest request, HttpServletResponse response, @PathVariable String albumId) throws Exception {
//		定义AJAX响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        String flag = request.getParameter("flag");
        String path = "";
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartRequest.getFile("file");
        String sname = multipartFile.getOriginalFilename();
        if (multipartFile != null && StringUtils.isNotEmpty(sname)) {
            JSONObject jobj = FileUploadClient.upFile(request, sname, multipartFile.getInputStream());
            if (jobj != null && jobj.getString("responseMessage").equals("OK")) {
                path = jobj.getString("fpath");
            }
        }

        //获得图片的名称
        picture.setPicture_name(sname);

//		获取当前用户、当前时间
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        String userId = sessionUser.getUser_id();
        String userName = sessionUser.getUser_name();
        Date nowDate = new Date();

//		设置图片编号 创建时间 创建人 图片路径
        picture.setPicture_id(new GUID().toString());
        picture.setCreate_time(nowDate);
        picture.setUser_id(userId);
        picture.setUser_name(userName);
        //   path = path.replaceAll("\\\\", "/");
        picture.setPath(path);
        picture.setAlbum_id(albumId);
        picture.setFlag("1");
        picture.setShow("1");
        
        //查询相册
        Album album = albumDao.getAlbumById(albumId);
        String team_id = album.getTeam_id();
        String team_name = album.getTeam_name();
        picture.setTeam_id(team_id);
        picture.setTeam_name(team_name);
        
        picture.setUpdate_time(new Date());
        

//		执行添加图片
        dao.insertPicture(picture);

        //设置相册的封面,如果相册没有封面，默认第一张为相册的封面
        if (StringUtils.isNotEmpty(flag)&&flag.equals("article")) {
            if (StringUtils.isEmpty(album.getAlbum_coverpath())) {
                albumDao.setAlbCover(albumId, picture.getPath());
            }
        }

//		反馈成功信息
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("图片添加成功！");

//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.INSERT, "添加图片，图片编号：" + picture.getPicture_id());

        response.setContentType("text/html;charset=utf-8");
        JSONObject obj = JSONObject.fromObject(ajaxResponse);
        response.getWriter().write(obj.toString());
        return;
    }

    /**
     * 相册中图片的编辑页面
     *
     * @param pictureId 图片id
     * @param request
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{pictureId}/editAlbPic", method = RequestMethod.GET)
    public String editAlbumPicture(@PathVariable String pictureId, HttpServletRequest request, Model model) throws Exception {
//		获取用户详细信息
        Picture pic = dao.getPictureById(pictureId);
//		将图片放置在反馈对象中
        model.addAttribute("pic", pic);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问编辑图片页面，图片编号：" + pic.getPicture_id() + "，图片名称：" + pic.getPicture_name());
        return basePath + "editAlbPic";
    }

    @RequestMapping(value = "/editDescription", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse editDescription(HttpServletRequest request) throws Exception {
//		执行图片显示
        String picId = request.getParameter("picId");
        String pic_description = request.getParameter("pic_description");
        dao.updatePicDescription(picId, pic_description);
//		执行响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("图片描述更新成功！");
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.UPDATE, "图片描述更新，图片编号列表：" + picId);
        return ajaxResponse;
    }

    //图片排序
    @RequestMapping(value = "/updatePicOrder", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse updatePicOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //定义AJAX响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        String picIds = request.getParameter("picIds");
        String parms = request.getParameter("order");
        //遍历
        if (picIds != null && !"".equals(picIds)) {
            String[] picId = picIds.split(",");
            String[] ord = parms.split(",");

            List<Object[]> list = new ArrayList();
            for (int i = 0; i < ord.length; i++) {
                Object[] objs = {ord[i], picId[i]};
                list.add(objs);
            }
            dao.updatePicOrder(list);
        }
        //		反馈成功信息
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("图片更新成功！");
        //		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.INSERT, "更新图片，图片编号：" + picIds);

        response.setContentType("text/html;charset=utf-8");
        JSONObject obj = JSONObject.fromObject(ajaxResponse);
        return ajaxResponse;
    }

}
