/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlshouwen.tdjs.content.controller;

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
import com.dlshouwen.tdjs.content.dao.ArticleDao;
import com.dlshouwen.tdjs.content.model.Article;
import com.dlshouwen.tdjs.album.dao.AlbumDao;
import com.dlshouwen.tdjs.album.model.Album;
import com.dlshouwen.tdjs.channel.dao.ChannelDao;
import com.dlshouwen.tdjs.channel.model.Channel;
import com.dlshouwen.tdjs.picture.model.Picture;
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
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 文章控制类
 *
 * @author cui
 */
@Controller
@RequestMapping("/tdjs/tdjsArticle/article")
public class TdjsArticleController {

    /**
     * 功能根路径
     */
    private String basePath = "tdjs/article/";

    private ArticleDao dao;

    private AlbumDao albumDao;

    private UserDao userDao;

    private ChannelDao channelDao;

    /**
     * 注入数据操作对象
     *
     * @param dao 数据操作对象
     */
    @Resource(name = "tdjsArticleDao")
    public void setDao(ArticleDao dao) {
        this.dao = dao;
    }

    @Resource(name = "TdjsAlbumDao")
    public void setAlbumDao(AlbumDao albumDao) {
        this.albumDao = albumDao;
    }

    @Resource(name = "userDao")
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Resource(name = "TdjsChannelDao")
    public void setChannelDao(ChannelDao channelDao) {
        this.channelDao = channelDao;
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
//	    记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问文章管理页面");
        

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
        return basePath + "articleList";
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

        String path = "";
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartRequest.getFile("picture");
        String orgFileName = multipartFile.getOriginalFilename();
        if (multipartFile != null && StringUtils.isNotEmpty(orgFileName)) {
            JSONObject jobj = FileUploadClient.upFile(request, orgFileName, multipartFile.getInputStream());
            if (jobj != null && jobj.getString("responseMessage").equals("OK")) {
                path = jobj.getString("fpath");
            }
        }
        album.setAlbum_coverpath(path);
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
        map.put("URL", "tdjs/tdjsArticle/article");
        ajaxResponse.setExtParam(map);

//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.UPDATE, "编辑相册，相册id：" + album.getAlbum_id() + "，相册名称：" + album.getAlbum_name());
        response.setContentType("text/html;charset=utf-8");
        JSONObject obj = JSONObject.fromObject(ajaxResponse);
        response.getWriter().write(obj.toString());
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
        String selChannelId = request.getParameter("selChannelId");
//          获取当前用户
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        User user = userDao.getUserById(sessionUser.getUser_id());
//          映射Pager对象
        Pager pager = PagerPropertyUtils.copy(JSONObject.fromObject(gridPager));
//          获取文章列表数据(管理者视图)
        if (StringUtils.isNotEmpty(user.getIdentity()) && user.getIdentity().equals("1")) {
            if (StringUtils.isEmpty(user.getTeam_id())) {//超级管理员
                dao.getArticleList(pager, request, response, selChannelId);
            } else {//普通团队管理者
                dao.getArticleList(pager, request, response, true, user.getTeam_id(), user.getUser_id(), selChannelId);
            }
        } else if (StringUtils.isNotEmpty(user.getIdentity()) && user.getIdentity().equals("0")
                && StringUtils.isNotEmpty(user.getTeam_id())) {//团队的普通用户
            dao.getArticleList(pager, request, response, false, user.getTeam_id(), user.getUser_id(), selChannelId);
        }

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
    public String addArticlePage(Model model, HttpServletRequest request) throws Exception {
        
//          新增文章对象
        Article article = new Article();
        
        //获得栏目的id
        String channelId = request.getParameter("selChannelId");
        
        //如果channleid 不为空的话，获得栏目下的所属团队
        if(channelId !=null && channelId !=""){
            Channel channel = channelDao.getChannelById(channelId);
            String channel_name = channel.getChannel_name();
            String team_id = channel.getTeam_id();
            String team_name = channel.getTeam_name();
            article.setTeam_id(team_id);
            article.setTeam_name(team_name);
           article.setChannel_name(channel_name);
           article.setChannel_id(channelId);
        }
        

//          获取当前用户
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        String publisher = sessionUser == null ? "" : sessionUser.getUser_name();

        article.setArticle_id(new GUID().toString());

        article.setArticle_limit("2");

//          发布人姓名
        article.setPublisher(publisher);
//            发布时间
        article.setPublish_time(new Date());
        //初始化的时候也赋予编辑时间，页面按照编辑时间排序
        article.setEdit_time(new Date());
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
        //获取ServletContext
        ServletContext context = request.getSession().getServletContext();

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

        article.setArticle_type("2");
//		执行新增文章
        dao.insertArticle(article);

//              配置文章包含的相册信息
        Album album = new Album();
        album.setAlbum_id(article.getArticle_id());
        album.setAlbum_name(article.getTitle());
        album.setAlbum_createuserbyid(userId);
        //设置相册的类型 1为文章相册 2为团队相册
        album.setAlbum_flag("1");//
        album.setAlbum_description("文章相册");
        album.setAlbum_createuser(sessionUser.getUser_name());
        album.setAlbum_createdate(nowDate);
        album.setAlbum_updatedate(nowDate);
        //添加文章的时候把teamId 也添加到相册中
        album.setTeam_id(article.getTeam_id());
        album.setTeam_name(article.getTeam_name());
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
    public AjaxResponse editArticlePag(@Valid Article article,
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

        //文章编辑的时候，将文章的状态修改为待评审状态 2
        article.setArticle_type("2");

//		执行编辑文章
        dao.updateArticle(article);
//		反馈成功信息
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("文章编辑成功！");
        //用‘URL’字段通知前段页面的跳转路径
        Map map = new HashMap();
        map.put("URL", "tdjs/tdjsArticle/article");
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
    public String editArtAlubmPag(@PathVariable String articleId, Model model, HttpServletRequest request) throws Exception {
        //获得相册
        Album album = albumDao.getAlbumById(articleId);
        model.addAttribute("album", album);
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问打开相册编辑页面，相册id：" + articleId);
        return basePath + "editArtAlbum";
    }

    /**
     * 保存为草稿
     *
     * @param article
     * @param bindingResult
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveDraft", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse saveDraft(@Valid Article article, BindingResult bindingResult,
            HttpServletRequest request) throws Exception {
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

        //文章编辑的时候，将文章的状态修改为待评审状态 2
        article.setArticle_type("4");

//		执行编辑文章
        dao.updateArticle(article);
//		反馈成功信息
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("存为草稿成功！");
        //用‘URL’字段通知前段页面的跳转路径
        Map map = new HashMap();
        map.put("URL", "tdjs/tdjsArticle/article");
        ajaxResponse.setExtParam(map);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.UPDATE, "编辑文章，文章编号：" + article.getArticle_id() + "，文章标题：" + article.getTitle());
        return ajaxResponse;
    }

}
