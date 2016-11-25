package com.dlshouwen.core.base.controller;

import com.dlshouwen.core.base.config.CONFIG;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dlshouwen.core.base.dao.HomeDao;
import com.dlshouwen.core.base.model.OperationType;
import com.dlshouwen.core.base.model.SessionUser;
import com.dlshouwen.core.base.utils.AttributeUtils;
import com.dlshouwen.core.base.utils.LogUtils;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;

/**
 * 框架首页
 *
 * @author 大连首闻科技有限公司
 * @version 2016-4-29 08:47:37
 */
@Controller
@RequestMapping("/core/base/home")
public class HomeController {

    /**
     * 功能根路径
     */
    private String basePath = "core/base/home/";

    /**
     * 数据操作对象
     */
    @SuppressWarnings("unused")
    private HomeDao dao;

    /**
     * 注入数据操作对象
     *
     * @param dao 数据操作对象
     */
    @Resource(name = "homeDao")
    public void setDao(HomeDao dao) {
        this.dao = dao;
    }

    /**
     * 跳转到Home页面
     *
     * @param request 请求对象
     * @param model 反馈的数据对象
     * @return basePath + 'home'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String goHomePage(HttpServletRequest request, Model model) throws Exception {
        //获得快捷列表数据 
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        model.addAttribute("shortcutList", JSONArray.fromObject(sessionUser.getShortcutList()));

        //获取团队建设里边的最新新闻
        List<Map<String, Object>> listNews = dao.getNewArtForHome();
        model.addAttribute("news", listNews);

        //获取网站管理里边的最新新闻
        String newsNumber = AttributeUtils.getAttributeContent(request.getServletContext(), "wzgl_news_showNumber");
        if(StringUtils.isEmpty(newsNumber)) {
            newsNumber = "5";
        }
        List<Map<String, Object>> wzglNews = dao.getContentArtForHome(newsNumber);
        model.addAttribute("wzglNews", wzglNews);

//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问首页");
        return basePath + "home";
    }

  

}
