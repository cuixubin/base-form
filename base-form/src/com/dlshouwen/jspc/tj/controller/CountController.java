/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlshouwen.jspc.tj.controller;

import com.dlshouwen.core.base.extra.grid.model.Pager;
import com.dlshouwen.core.base.extra.grid.utils.PagerPropertyUtils;
import com.dlshouwen.core.base.model.OperationType;
import com.dlshouwen.core.base.utils.LogUtils;
import com.dlshouwen.jspc.tj.dao.CountDao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统计业务类
 *
 * @author yangtong
 */
@Controller
@RequestMapping("/jspc/count/count")
public class CountController {

    /**
     * 功能根路径
     */
    private String basePath = "jspc/count/";

    /**
     * 数据操作对象
     */
    private CountDao dao;

    @Resource(name = "countDao")
    public void setDao(CountDao dao) {
        this.dao = dao;
    }

    //跳转到标签页面
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String goCountPage(HttpServletRequest request, Model model) throws Exception {
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问统计页面");
        //跳转到标签列表页面
        return basePath + "countList";
    }

    /**
     * 获取文件列表数据
     *
     * @param gridPager 表格分页信息
     * @param request 请求对象
     * @param response 响应对象
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public void getCountList(String gridPager, HttpServletRequest request, HttpServletResponse response) throws Exception {
//          映射Pager对象
        Pager pager = PagerPropertyUtils.copy(JSONObject.fromObject(gridPager));
//          获取文章列表数据
        dao.getCountList(pager, request, response);
//          记录操作日志
        LogUtils.updateOperationLog(request, OperationType.SEARCH, "获取统计数据");
    }

    /**
     * 获取文件上传统计图表数据
     *
     * @param request 请求对象
     * @return 操作日志图表数据
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/chart/data", method = RequestMethod.POST)
    @ResponseBody
    public List<Map<String, Object>> getCarCountChartData(HttpServletRequest request) throws Exception {
//		获取数据类型
        String dataType = request.getParameter("data_type");
        String sqlString = request.getParameter("sqlString");
        String params1 = request.getParameter("params");
//		获取查询参数
//		String sigmaGridParamsJSON = request.getParameter("sigmaGridParams");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("sqlString", sqlString);
        params.put("params", params1);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.SEARCH, "获取文件统计图表数据");
        return dao.getChartDataList(dataType, params);
    }
}
