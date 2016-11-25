/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlshouwen.wzgl.album.controller;

import com.dlshouwen.core.base.config.CONFIG;
import com.dlshouwen.core.base.extra.grid.model.Pager;
import com.dlshouwen.core.base.extra.grid.utils.PagerPropertyUtils;
import com.dlshouwen.core.base.http.FileUploadClient;
import com.dlshouwen.core.base.model.AjaxResponse;
import com.dlshouwen.core.base.model.OperationType;
import com.dlshouwen.core.base.model.SessionUser;
import com.dlshouwen.core.base.utils.GUID;
import com.dlshouwen.core.base.utils.LogUtils;
import com.dlshouwen.wzgl.album.dao.AlbumDao;
import com.dlshouwen.wzgl.album.model.Album;
import com.dlshouwen.wzgl.picture.dao.PictureDao;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
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
 * 相册controller
 *
 * @author admin
 */
@Controller
@RequestMapping("/wzgl/album/album")
public class AlbumController {

    //根路径
    private String basePath = "wzgl/album/";

    private AlbumDao dao;

    //注入dao
    @Resource(name = "albumDao")
    public void setDao(AlbumDao dao) {
        this.dao = dao;
    }

    //跳转到相册页面
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String goAlbumlPage(HttpServletRequest request, Model model) throws Exception {
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问相册管理页面");
        //跳转到相册列表页面
        return basePath + "albumList";
    }

    /**
     * 获取相册列表数据
     *
     * @param gridPager 表格分页信息
     * @param request 请求对象
     * @param response 响应对象
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public void getAlbumList(String gridPager, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Pager pager = PagerPropertyUtils.copy(JSONObject.fromObject(gridPager));
        dao.getAlbumList(pager, request, response);
//          记录操作日志
        LogUtils.updateOperationLog(request, OperationType.SEARCH, "获取相册管理页面中的相册列表数据");
    }

    /**
     * 跳转到新增相册页面
     *
     * @param model 反馈对象
     * @param request 请求对象
     * @return basePath + 'addAlbum'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addAlbum(Model model, HttpServletRequest request) throws Exception {
        Album album = new Album();
        model.addAttribute("album", album);
        
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问新增相册页面");
        return basePath + "addAlbum";
    }

    /**
     * 执行新增相册
     *
     * @param album 相册对象
     * @param bindingResult 绑定结果
     * @param request 请求对象
     * @return ajax响应内容
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void addAlbum(@Valid Album album, BindingResult bindingResult,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        AjaxResponse ajaxResponse = new AjaxResponse();

        if (bindingResult.hasErrors()) {
            ajaxResponse.bindingResultHandler(bindingResult);
            LogUtils.updateOperationLog(request, OperationType.INSERT, "新增相册（新增失败，相册名称：" + album.getAlbum_name() + "，错误信息：" + AjaxResponse.getBindingResultMessage(bindingResult) + "）");
            response.setContentType("text/html;charset=utf-8");
            JSONObject obj = JSONObject.fromObject(ajaxResponse);
            response.getWriter().write(obj.toString());
            return;
        }
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartRequest.getFile("picture");
        String fileName = multipartFile.getOriginalFilename();
        //获取用户上传照片
        String path = null;
        if(null != multipartFile && StringUtils.isNotEmpty(multipartFile.getOriginalFilename())) {
            JSONObject jobj = FileUploadClient.upFile(request, fileName, multipartFile.getInputStream());
            if(jobj.getString("responseMessage").equals("OK")) {
                path = jobj.getString("fpath");
            }
        }
        /*
        if (fileName.trim().length() > 0) {
            int pos = fileName.lastIndexOf(".");
            fileName = fileName.substring(pos);
            String fileDirPath = request.getSession().getServletContext().getRealPath("/");
            String path = fileDirPath.substring(0, fileDirPath.lastIndexOf(File.separator)) + CONFIG.UPLOAD_PIC_PATH;
            Date date = new Date();
            fileName = String.valueOf(date.getTime()) + fileName;
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            file = new File(path + "/" + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            path = path + "/" + fileName;
            FileOutputStream fos = null;
            InputStream s = null;
            try {
                fos = new FileOutputStream(file);
                s = multipartFile.getInputStream();
                byte[] buffer = new byte[1024];
                int read = 0;
                while ((read = s.read(buffer)) != -1) {
                    fos.write(buffer, 0, read);
                }
                fos.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    fos.close();
                }
                if (s != null) {
                    s.close();
                }
            }
            path = path.replaceAll("\\\\", "/");
            album.setAlbum_coverpath(path);
        }
        */
        if(null != path) {
            album.setAlbum_coverpath(path);
        }
//		获取当前用户、当前时间
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        String userName = sessionUser.getUser_name();
        String userId = sessionUser.getUser_id();
        Date nowDate = new Date();
//		设置相册名称、创建人、创建时间、相册路径
        album.setAlbum_id(new GUID().toString());
        album.setAlbum_createuser(userName);
        album.setAlbum_createuserbyid(userId);
        album.setAlbum_createdate(nowDate);
        
//		执行新增相册
        dao.insertAlbum(album);
//		反馈成功信息
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("相册新增成功！");
                //通知前端跳转路径
        Map map = new HashMap();
        map.put("URL", basePath + "album");
        ajaxResponse.setExtParam(map);
        
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.INSERT, "新增相册，相册id：" + album.getAlbum_id() + "，相册名称：" + album.getAlbum_name());
        response.setContentType("text/html;charset=utf-8");
        JSONObject obj = JSONObject.fromObject(ajaxResponse);
        response.getWriter().write(obj.toString());
        return;
    }

    /**
     * 跳转到编辑相册页面
     *
     * @param albumId 相册名称
     * @param model 反馈对象
     * @param request 请求对象
     * @return base + 'editAlbum'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/{albumId}/edit", method = RequestMethod.GET)
    public String editAlbumPage(@PathVariable String albumId,
            Model model, HttpServletRequest request) throws Exception {
        Album album = dao.getAlbumById(albumId);
//		设置到反馈对象中
        model.addAttribute("album", album);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问编辑相册页面，相册id：" + album.getAlbum_id() + "，相册名称：" + album.getAlbum_name());
//		执行跳转到编辑页面
        return basePath + "editAlbum";
    }

    /**
     * 执行编辑相册
     *
     * @param album 相册对象
     * @param bindingResult 绑定对象
     * @return ajax响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public void editAlbum(@Valid Album album, HttpServletRequest request,
            BindingResult bindingResult, HttpServletResponse response) throws Exception {
//		定义AJAX响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
//		如果发现错误则拼接错误信息执行回执
        if (bindingResult.hasErrors()) {
            ajaxResponse.bindingResultHandler(bindingResult);
//	        记录操作日志
            LogUtils.updateOperationLog(request, OperationType.UPDATE, "编辑相册（编辑失败，相册id：" + album.getAlbum_id() + "，相册名称：" + album.getAlbum_name() + "，错误信息：" + AjaxResponse.getBindingResultMessage(bindingResult) + "）");
            response.setContentType("text/html;charset=utf-8");
            JSONObject obj = JSONObject.fromObject(ajaxResponse);
            response.getWriter().write(obj.toString());
            return;
        }
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartRequest.getFile("picture");
        String fileName = multipartFile.getOriginalFilename();
        //获取用户上传照片
        String path = null;
        if(null != multipartFile && StringUtils.isNotEmpty(multipartFile.getOriginalFilename())) {
            JSONObject jobj = FileUploadClient.upFile(request, fileName, multipartFile.getInputStream());
            if(jobj.getString("responseMessage").equals("OK")) {
                path = jobj.getString("fpath");
            }
        }
        /*
        if (fileName.trim().length() > 0) {
            int pos = fileName.lastIndexOf(".");
            fileName = fileName.substring(pos);
            String fileDirPath = request.getSession().getServletContext().getRealPath("/");
            String path = fileDirPath.substring(0, fileDirPath.lastIndexOf(File.separator)) + CONFIG.UPLOAD_PIC_PATH;
            Date date = new Date();
            fileName = String.valueOf(date.getTime()) + fileName;
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            file = new File(path + "/" + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            path = path + "/" + fileName;
            FileOutputStream fos = null;
            InputStream s = null;
            try {
                fos = new FileOutputStream(file);
                s = multipartFile.getInputStream();
                byte[] buffer = new byte[1024];
                int read = 0;
                while ((read = s.read(buffer)) != -1) {
                    fos.write(buffer, 0, read);
                }
                fos.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    fos.close();
                }
                if (s != null) {
                    s.close();
                }
            }
            path = path.replaceAll("\\\\", "/");
            album.setAlbum_coverpath(path);
        }
        */
        if(null != path) {
            album.setAlbum_coverpath(path);
        }
//		获取当前用户、当前时间
        Date nowDate = new Date();
//		设置编辑人、编辑时间
        album.setAlbum_updatedate(nowDate);
        album.setAlbum_flag("1");
//		执行编辑相册
        dao.updateAlbum(album);
//		反馈成功信息
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("相册编辑成功！");
                //通知前端跳转路径
        Map map = new HashMap();
        map.put("URL", basePath + "album");
        ajaxResponse.setExtParam(map);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.UPDATE, "编辑相册，相册id：" + album.getAlbum_id() + "，相册名称：" + album.getAlbum_name());
        response.setContentType("text/html;charset=utf-8");
        JSONObject obj = JSONObject.fromObject(ajaxResponse);
        response.getWriter().write(obj.toString());
    }

    /**
     * 执行删除相册，并删除相册下的图片
     *
     * @param albumIds 相册编号列表 数组
     * @param request 请求对象
     * @return ajax响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse deleteAlbum(String[] albumIds, HttpServletRequest request) throws Exception {
        for(int i =0;i<albumIds.length;i++){
            //根据相册id 删除相册下边的图片
             dao.deletePicByAlbId(albumIds[i]);
            //删除相册
            dao.deleteAlbum(albumIds[i]);
        }
//		执行响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("相册删除成功！");
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.DELETE, "删除相册，相册id：" + albumIds);
        return ajaxResponse;
    }

    /**
     * 打开相册
     *
     * @param albumId
     * @param model
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{albumId}/openAlbum", method = RequestMethod.GET)
    public String openAlbum(@PathVariable String albumId,
            Model model, HttpServletRequest request) throws Exception {
        Album album = dao.getAlbumById(albumId);
        List pic = dao.findPicByAlbumId(albumId);
        model.addAttribute("pic", pic);
//		设置到反馈对象中
        model.addAttribute("album", album);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问打开相册页面，相册id：" + album.getAlbum_id() + "，相册名称：" + album.getAlbum_name());
//		执行跳转到编辑页面
        return basePath + "openAlbum";
    }

    /**
     * 设置相册封面
     *
     * @param albumId 相册id
     * @param albCoverpath 封面路径
     * @param request
     */
    @RequestMapping(value = "/updateAlbCover", method = RequestMethod.POST)
    public void updateAlbCover(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String albumId = request.getParameter("albumId");
        String albCoverPath = request.getParameter("albCoverPath");
        dao.setAlbCover(albumId, albCoverPath);
        //		执行响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("设置成功！");
        
        //		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.DELETE, "删除相册，相册id：" + albumId + ",相册封面路径：" + albCoverPath);
        response.setContentType("text/html;charset=utf-8");
        JSONObject obj = JSONObject.fromObject(ajaxResponse);
        response.getWriter().write(obj.toString());
    }

}
