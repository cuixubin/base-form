/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlshouwen.wzgl.content.controller;

import com.dlshouwen.core.base.config.CONFIG;
import com.dlshouwen.core.base.extra.grid.model.Pager;
import com.dlshouwen.core.base.extra.grid.utils.PagerPropertyUtils;
import com.dlshouwen.core.base.http.FileUploadClient;
import com.dlshouwen.core.base.model.AjaxResponse;
import com.dlshouwen.core.base.model.OperationType;
import com.dlshouwen.core.base.model.SessionUser;
import com.dlshouwen.core.base.utils.AttributeUtils;
import com.dlshouwen.core.base.utils.GUID;
import com.dlshouwen.core.base.utils.LogUtils;
import com.dlshouwen.wzgl.album.dao.AlbumDao;
import com.dlshouwen.wzgl.album.model.Album;
import com.dlshouwen.wzgl.channel.dao.ChannelDao;
import com.dlshouwen.wzgl.channel.model.Channel;
import com.dlshouwen.wzgl.content.dao.ArticleDao;
import com.dlshouwen.wzgl.content.model.Article;
import com.dlshouwen.wzgl.picture.model.Picture;
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
import org.springframework.web.servlet.ModelAndView;

/**
 * 文章控制类
 *
 * @author cui
 */
@Controller
@RequestMapping("/wzgl/article/article")
public class ArticleController {

    /**
     * 功能根路径
     */
    private String basePath = "wzgl/article/";

    /**
     * 数据操作对象
     */
    private ArticleDao dao;

    /**
     * 数据操作对象
     */
    private AlbumDao albumDao;

    /**
     * 注入数据操作对象
     *
     * @param dao 数据操作对象
     */
    @Resource(name = "articleDao")
    public void setDao(ArticleDao dao) {
        this.dao = dao;
    }
    
      /**
     * 数据操作对象
     */
    @Resource(name = "ChannelDao")
    private ChannelDao channelDao;

    public void setChannelDao(ChannelDao channelDao) {
        this.channelDao = channelDao;
    }

    /**
     * 注入数据操作对象
     *
     * @param dao 数据操作对象
     */
    @Resource(name = "albumDao")
    public void setAlbumDao(AlbumDao albumDao) {
        this.albumDao = albumDao;
    }

    /**
     * 跳转到文章管理主页面
     *
     * @param request 请求对象
     * @return basePath + 'articleList'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String goArticlePage(HttpServletRequest request) throws Exception {
        
                //执行查询栏目     
        List<Map<String, Object>> channelList = channelDao.getChannelList();
        Map map1 = new HashMap();
        for (Map map : channelList) {
            String channel_id = (String) map.get("channel_id");
            String channel_name = (String) map.get("channel_name");
            map1.put(channel_id, channel_name);
        }
        request.setAttribute("channelList", JSONObject.fromObject(map1).toString());
         String selChannelId = request.getParameter("selChannelId");
        request.setAttribute("selChannelId", selChannelId);
////	    记录操作日志
//        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问文章管理页面");
        return basePath + "articleList";
    }

    /**
     * 获取文章列表数据
     *
     * @param gridPager 表格分页信息
     * @param request 请求对象
     * @param response 响应对象
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public void getArticleList(String gridPager, HttpServletRequest request, HttpServletResponse response) throws Exception {
//          映射Pager对象
        Pager pager = PagerPropertyUtils.copy(JSONObject.fromObject(gridPager));
//          获取文章列表数据
        dao.getArticleList(pager, request, response);
//          记录操作日志
        LogUtils.updateOperationLog(request, OperationType.SEARCH, "获取文章管理页面中的文章列表数据");
    }

    /**
     * 跳转到新增文章页面
     *
     * @param model 反馈对象
     * @param request 请求对象
     * @return basePath + 'addArticle'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addArticle(Model model, HttpServletRequest request) throws Exception {
//          新增文章对象
        Article article = new Article();

//          获取当前用户
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        String publisher = sessionUser == null ? "" : sessionUser.getUser_name();

        article.setArticle_id(new GUID().toString());
//          设置默认启用
//          启用
        article.setStatus("1");
//          不置顶
        article.setTopset("0");
//          发布人姓名
        article.setPublisher(publisher);
//            发布时间
        article.setPublish_time(new Date());

//          传递文章对象到前台
        model.addAttribute("article", article);

//          记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问新增文章页面");
        return basePath + "addArticle";
    }

    /**
     * 执行新增文章
     *
     * @param article 文章对象
     * @param bindingResult 绑定结果
     * @param request 请求对象
     * @return ajax响应内容
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse addArticle(@Valid Article article, BindingResult bindingResult,
            HttpServletRequest request) throws Exception {
//		定义AJAX响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
//		如果发现错误则拼接错误信息执行回执
        if (bindingResult.hasErrors()) {
            ajaxResponse.bindingResultHandler(bindingResult);
//			记录操作日志
            LogUtils.updateOperationLog(request, OperationType.INSERT, "新增文章（失败：数据验证错误，详细信息：" + AjaxResponse.getBindingResultMessage(bindingResult) + "）");
            return ajaxResponse;
        }
//		获取当前用户、当前时间
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        String userId = sessionUser.getUser_id();
        Date nowDate = new Date();
//		设置文章 编号、创建人、创建时间、编辑人、编辑时间
        if (null == article.getPublish_time()) {
            article.setPublish_time(nowDate);
        }
        article.setCreator(userId);
        article.setCreate_time(nowDate);
        article.setEditor(userId);
        article.setEdit_time(nowDate);
//		执行新增文章
        dao.insertArticle(article);

//              配置文章包含的相册信息
        Album album = new Album();
        album.setAlbum_id(article.getArticle_id());
        album.setAlbum_name(article.getTitle());
        album.setAlbum_createuserbyid(userId);
        album.setAlbum_flag("1");//
        album.setAlbum_description("文章相册");
        album.setAlbum_createuser(sessionUser.getUser_name());
        album.setAlbum_createdate(nowDate);
        album.setAlbum_updatedate(nowDate);
//              执行文章相册的创建
        albumDao.insertAlbum(album);

//		反馈成功信息
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("文章新增成功！");
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.INSERT, "新增文章，文章编号：" + article.getArticle_id() + "，文章标题：" + article.getTitle());
        return ajaxResponse;
    }

    /**
     * 跳转到编辑文章页面
     *
     * @param articleId 文章编号
     * @param model 反馈对象
     * @param request 请求对象
     * @return base + 'editAnnouncement'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/{articleId}/edit", method = RequestMethod.GET)
    public String editArticle(@PathVariable String articleId,
            Model model, HttpServletRequest request) throws Exception {
//		获取文章信息
        Article article = dao.getArticleById(articleId);
//		设置到反馈对象中
        model.addAttribute("article", article);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问编辑文章页面");
//		执行跳转到编辑页面
        return basePath + "editArticle";
    }

    /**
     * 执行编辑文章
     *
     * @param article 文章对象
     * @param bindingResult 绑定对象
     * @param request 请求对象
     * @return ajax响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse editArticle(@Valid Article article,
            BindingResult bindingResult, HttpServletRequest request) throws Exception {
//		定义AJAX响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
//		如果发现错误则拼接错误信息执行回执
        if (bindingResult.hasErrors()) {
            ajaxResponse.bindingResultHandler(bindingResult);
//			记录操作日志
            LogUtils.updateOperationLog(request, OperationType.UPDATE, "编辑文章（失败：数据验证错误，详细信息：" + AjaxResponse.getBindingResultMessage(bindingResult) + "）");
            return ajaxResponse;
        }
//		获取当前用户、当前时间
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        String userId = sessionUser.getUser_id();
        Date nowDate = new Date();
//		设置编辑人、编辑时间
        article.setEditor(userId);
        article.setEdit_time(nowDate);
//		执行编辑文章
        dao.updateArticle(article);
//		反馈成功信息
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("文章编辑成功！");
        //用‘URL’字段通知前段页面的跳转路径
        Map map = new HashMap();
        map.put("URL", "wzgl/article/article");
        ajaxResponse.setExtParam(map);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.UPDATE, "编辑文章，文章编号：" + article.getArticle_id() + "，文章标题：" + article.getTitle());
        return ajaxResponse;
    }

    /**
     * 执行删除文章
     *
     * @param articleIds 文章编号列表
     * @param request 请求对象
     * @return ajax响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse deleteArticle(String articleIds,
            HttpServletRequest request) throws Exception {
//		执行删除文章
        dao.deleteArticel(articleIds);
//              删除文章相册内的图片
        albumDao.deletePicByAlbId(articleIds);
//              删除文章的相册
        albumDao.deleteAlbum(articleIds);
//		执行响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("文章删除成功！");
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.DELETE, "删除文章，文章编号列表：" + articleIds);
        return ajaxResponse;
    }

    /**
     * 执行启用文章
     *
     * @param articleIds 文章编号列表
     * @param request 请求对象
     * @return ajax响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/open", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse openArticle(String articleIds, HttpServletRequest request) throws Exception {
//		执行启用文章
        dao.openArticle(articleIds);
//		执行响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("文章启用成功！");
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.UPDATE, "启用文章，文章编号列表：" + articleIds);
        return ajaxResponse;
    }

    /**
     * 执行禁用文章
     *
     * @param articleIds 文章编号列表
     * @param request 请求对象
     * @return ajax响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/close", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse closeArticle(String articleIds, HttpServletRequest request) throws Exception {
//		执行禁用文章
        dao.closeArticle(articleIds);
//		执行响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("文章禁用成功！");
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.UPDATE, "禁用文章，文章编号列表：" + articleIds);
        return ajaxResponse;
    }

    /**
     * 打开文章的相册
     *
     * @param articleId
     * @param model
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{articleId}/openArtAlubm", method = RequestMethod.GET)
    public String openArtAlubm(@PathVariable String articleId, Model model, HttpServletRequest request) throws Exception {
        //执行查询方法
        Album album = albumDao.getAlbumById(articleId);
        List<Picture> pic = albumDao.findPicByAlbumId(articleId);
        //将对象传递给页面
        model.addAttribute("pic", pic);
        model.addAttribute("album", album);
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问打开相册页面，相册id：" + articleId);
        return basePath + "openArtAlbum";
    }

    /**
     * 打开编辑页面
     *
     * @param articleId
     * @param model
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{articleId}/editArtAlubm", method = RequestMethod.GET)
    public String editArtAlubm(@PathVariable String articleId, Model model, HttpServletRequest request) throws Exception {
        //获得相册
        Album album = albumDao.getAlbumById(articleId);
        model.addAttribute("album", album);
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问打开相册编辑页面，相册id：" + articleId);
        return basePath + "editArtAlbum";
    }
    
    /**
     * 跳转到审核页面
     *
     * @param articleId 文章编号
     * @param model 反馈对象
     * @param request 请求对象
     * @return base + 'editAnnouncement'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/{articleId}/check", method = RequestMethod.GET)
    public ModelAndView checkArticle(@PathVariable String articleId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView view = new ModelAndView("/wzgl/content/articleDetail.btl");
        String sourcePath = AttributeUtils.getAttributeContent(request.getServletContext(), "source_webapp_file_postion");
//	获取文章信息
        Article article = dao.getArticleById(articleId);
        String channel_id = article.getChannel_id();
        Channel channelNow = channelDao.getChannelById(channel_id);
        List<Map<String, Object>> secChannelList = null;
        if(channelNow.getChannel_parent().equals("top")) {
            secChannelList = channelDao.getSecChannelList(channelNow.getChannel_id());
            view.addObject("topChannelNow", channelNow);
            view.addObject("channelNow", null);
            view.addObject("secChannelList", secChannelList);
        }else {
            Channel channelTopNow = channelDao.getChannelById(channelNow.getChannel_parent());
            secChannelList = channelDao.getSecChannelList(channelTopNow.getChannel_id());
            view.addObject("topChannelNow", channelTopNow);
            view.addObject("channelNow", channelNow);
            view.addObject("secChannelList", secChannelList);
        }
        List<Map<String, Object>> allChannel = channelDao.getFirstChannel();
        List<Picture> pics = albumDao.findPicByAlbumId(articleId);
        if(null == pics || pics.size() == 0) {
            pics = null;
        }
        view.addObject("allChannel", allChannel);
        view.addObject("article", article);
        view.addObject("pictureList", pics);
        view.addObject("SOURCEPATH", sourcePath);

//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "预览文章页面");
        return view;
    }

    
    /**
     * 执行编辑相册
     *
     * @param album 相册对象
     * @param bindingResult 绑定对象
     * @return ajax响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/editAlbum", method = RequestMethod.POST)
    public void editArtAlbum(@Valid Album album, HttpServletRequest request,
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
            JSONObject jobj = FileUploadClient.upFile(request, multipartFile.getOriginalFilename(), multipartFile.getInputStream());
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
        albumDao.updateAlbum(album);
//		反馈成功信息
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("相册编辑成功！");
        
        //用‘URL’字段通知前段页面的跳转路径
        Map map = new HashMap();
        map.put("URL", "wzgl/article/article");
        ajaxResponse.setExtParam(map);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.UPDATE, "编辑相册，相册id：" + album.getAlbum_id() + "，相册名称：" + album.getAlbum_name());
        response.setContentType("text/html;charset=utf-8");
        JSONObject obj = JSONObject.fromObject(ajaxResponse);
        response.getWriter().write(obj.toString());
    }
    
    
}
