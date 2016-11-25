/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlshouwen.tdjs.content.controller;

import com.dlshouwen.core.base.config.CONFIG;
import com.dlshouwen.core.base.extra.grid.model.Pager;
import com.dlshouwen.core.base.extra.grid.utils.PagerPropertyUtils;
import com.dlshouwen.core.base.model.AjaxResponse;
import com.dlshouwen.core.base.model.OperationType;
import com.dlshouwen.core.base.model.SessionUser;
import com.dlshouwen.core.base.utils.AttributeUtils;
import com.dlshouwen.core.base.utils.LogUtils;
import com.dlshouwen.core.system.dao.UserDao;
import com.dlshouwen.core.system.model.User;
import com.dlshouwen.core.team.dao.TeamDao;
import com.dlshouwen.core.team.model.Team;
import com.dlshouwen.tdjs.channel.dao.ChannelDao;
import com.dlshouwen.tdjs.channel.model.Channel;
import com.dlshouwen.tdjs.content.dao.ArticleDao;
import com.dlshouwen.tdjs.content.model.Article;
import com.dlshouwen.tdjs.picture.dao.PictureDao;
import com.dlshouwen.tdjs.picture.model.Picture;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author admin
 */
@Controller
@RequestMapping("/tdjs/tdjsArticle/articleCheck")
public class TdjsArtCheckController {

    /**
     * 功能根路径
     */
    private String basePath = "tdjs/article/";

    private ArticleDao dao;
    
    @Resource(name = "tdjsArticleDao")
    public void setDao(ArticleDao dao) {
        this.dao = dao;
    }
    
    private PictureDao pictureDao;

    @Resource(name = "TdjsPictureDao")
    public void setPictureDao(PictureDao pictureDao) {
        this.pictureDao = pictureDao;
    }
    
    
    private ChannelDao channelDao;
    /**
     * 数据操作对象
     */
    @Resource(name = "TdjsChannelDao")
    public void setChannelDao(ChannelDao channelDao) {
        this.channelDao = channelDao;
    }
    
    private TeamDao teamDao;
    @Resource(name = "teamDao")
    public void setTeamDao(TeamDao teamDao) {
        this.teamDao = teamDao;
    }
    
    private UserDao userDao;
    @Resource(name = "userDao")
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    
    /**
     * 跳转到审查页面
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String goArticlePage(HttpServletRequest request) throws Exception {

//	    记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问文章管理页面");
        return basePath + "artCheckList";
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
//          获取当前用户
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        User user = userDao.getUserById(sessionUser.getUser_id());
//          映射Pager对象
        Pager pager = PagerPropertyUtils.copy(JSONObject.fromObject(gridPager));
//          获取文章列表数据(管理者视图)
        if(StringUtils.isNotEmpty(user.getIdentity()) && user.getIdentity().equals("1")) {
            if(StringUtils.isEmpty(user.getTeam_id())) {//超级管理员
                dao.getCheckArticleList(pager, request, response);
            }else {//普通团队管理者
                dao.getCheckArticleList(pager, request, response, true, user.getTeam_id(), user.getUser_id());
            }
        }else if(StringUtils.isNotEmpty(user.getIdentity()) && user.getIdentity().equals("0")
                && StringUtils.isNotEmpty(user.getTeam_id())) {//团队的普通用户
            dao.getCheckArticleList(pager, request, response, false, user.getTeam_id(), user.getUser_id());
        }
        
//          记录操作日志
        LogUtils.updateOperationLog(request, OperationType.SEARCH, "获取文章管理页面中的文章列表数据");
    }
    
    /**
     * 跳转到审查编辑页面
     *
     * @param articleId 文章编号
     * @param request 请求对象
     * @return base + 'editAnnouncement'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/{articleId}/check", method = RequestMethod.GET)
    public ModelAndView checkArticle(@PathVariable String articleId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView view = new ModelAndView("/tdjs/content/articleDetail.btl");
        String sourcePath = AttributeUtils.getAttributeContent(request.getServletContext(), "source_webapp_file_postion");
//	获取文章信息
        Article article = dao.getArticleById(articleId);
        
        String channel_id = article.getChannel_id();
        Channel channelNow = channelDao.getChannelById(channel_id);
        
        String teamId = article.getTeam_id();
        Team teamNow = teamDao.getTeamById(teamId);
        
        List<Map<String, Object>> allChannel = channelDao.getChannelListByTeamId(teamId);
        List<Picture> pictureList = pictureDao.getPictureByAlbumId(articleId);
        if(null == pictureList || pictureList.size() == 0) {
            pictureList = null;
        }
        view.addObject("teamNow", teamNow);
        view.addObject("allChannel", allChannel);
        view.addObject("article", article);
        view.addObject("channelNow", channelNow);
        view.addObject("pictureList", pictureList);
        view.addObject("SOURCEPATH", sourcePath);

//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "预览文章页面");
        return view;
    }

    /**
     * 文章评审通过
     */
    @RequestMapping(value = "/{articleId}/pass", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse checkArticlePass(@PathVariable String articleId, HttpServletRequest request) throws Exception {
//		定义AJAX响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        //獲得當前的用戶
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        String user_name = sessionUser.getUser_name();
        Date date = new Date();
        String check_comment = request.getParameter("check_comment");
        
        String aid[] = articleId.split(",");
        for(String atcId:aid) {
            dao.ArtTypePass(user_name, date, atcId, check_comment);
        }
//		反馈成功信息
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("文章通过评审！");

//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问列表页面");
        return ajaxResponse;
    }

    /**
     * 文章评审未通过
     *
     */
    @RequestMapping(value = "/{articleId}/refuse", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse checkArticleRefuse(@PathVariable String articleId, HttpServletRequest request) throws Exception {
        AjaxResponse ajaxResponse = new AjaxResponse();
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        String user_name = sessionUser.getUser_name();
        Date date = new Date();
        String check_comment = request.getParameter("check_comment");
        String aid[] = articleId.split(",");
        for(String atcId:aid) {
            dao.ArtTypereRefuse(user_name, date, atcId, check_comment);
        }
        
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("文章未通过评审！");

//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问列表页面");
        return ajaxResponse;
    }

    //文章评审意见页面
    @RequestMapping(value = "/note", method = RequestMethod.GET)
    public String openNotePage(HttpServletRequest request) throws Exception {
        LogUtils.updateOperationLog(request, OperationType.UPDATE, "文章评审页面");
        return basePath + "note";
    }

}
