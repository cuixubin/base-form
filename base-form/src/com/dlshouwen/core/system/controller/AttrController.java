package com.dlshouwen.core.system.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.dlshouwen.core.base.config.CONFIG;
import com.dlshouwen.core.base.extra.grid.model.Pager;
import com.dlshouwen.core.base.extra.grid.utils.PagerPropertyUtils;
import com.dlshouwen.core.base.extra.unique.utils.UniqueUtils;
import com.dlshouwen.core.base.loader.SystemAttributeLoader;
import com.dlshouwen.core.base.model.AjaxResponse;
import com.dlshouwen.core.base.model.OperationType;
import com.dlshouwen.core.base.utils.GUID;
import com.dlshouwen.core.base.utils.LogUtils;
import com.dlshouwen.core.system.dao.AttrDao;
import com.dlshouwen.core.system.model.Attr;
import java.util.HashMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 参数
 *
 * @author 大连首闻科技有限公司
 * @version 2013-08-17 08:04:27 965
 */
@Controller
@RequestMapping("/core/system/attr")
public class AttrController {

    /**
     * 功能根路径
     */
    private String basePath = "core/system/attr/";

    /**
     * 数据操作对象
     */
    private AttrDao dao;

    /**
     * 注入数据操作对象
     *
     * @param dao 数据操作对象
     */
    @Resource(name = "coreAttrDao")
    public void setDao(AttrDao dao) {
        this.dao = dao;
    }

    /**
     * 跳转到参数主页面
     *
     * @param request 请求对象
     * @return basePath + 'attrList'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String goAttrPage(HttpServletRequest request) throws Exception {
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问参数管理页面");
        return basePath + "attrList";
    }

    /**
     * 获取参数列表数据
     *
     * @param gridPager 表格分页信息
     * @param request 请求对象
     * @param response 响应对象
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public void getAttrList(String gridPager, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		映射Pager对象
        Pager pager = PagerPropertyUtils.copy(JSONObject.fromObject(gridPager));
//		获取参数列表
        dao.getAttrList(pager, request, response);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.SEARCH, "获取参数管理页面的参数列表数据");
    }

    /**
     * 跳转到新增参数页面
     *
     * @param model 反馈对象
     * @param request 请求对象
     * @return basePath + 'addAttr'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addAttr(Model model, HttpServletRequest request) throws Exception {
        model.addAttribute("attr", new Attr());
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问新增参数页面");
        return basePath + "addAttr";
    }

    /**
     * 执行新增参数
     *
     * @param attr 参数对象
     * @param bindingResult 绑定结果
     * @param request 请求对象
     * @return ajax响应内容
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse addAttr(@Valid Attr attr, BindingResult bindingResult,
            HttpServletRequest request) throws Exception {
//		定义AJAX响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
//		校验唯一
        if (!UniqueUtils.unique(dao, ajaxResponse, "参数编号已经被使用，请重新填写。", "ATTR_CODE_UNIQUE_FOR_ADD", attr.getAttr_code())) {
//			记录操作日志
            LogUtils.updateOperationLog(request, OperationType.INSERT, "新增参数（失败：参数编号已经被使用，参数编号：" + attr.getAttr_code() + "）");
            return ajaxResponse;
        }
//		如果发现错误则拼接错误信息执行回执
        if (bindingResult.hasErrors()) {
            ajaxResponse.bindingResultHandler(bindingResult);
//			记录操作日志
            LogUtils.updateOperationLog(request, OperationType.INSERT, "新增参数（失败：数据验证错误，详细信息：" + AjaxResponse.getBindingResultMessage(bindingResult) + "）");
            return ajaxResponse;
        }
//		设置参数编号
        attr.setAttr_id(new GUID().toString());
//		执行新增参数
        dao.insertAttr(attr);
//		参数重新加载
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(CONFIG.SERVLET_CONTEXT);
        DataSource dataSource = context.getBean(CONFIG.SYSTEM_ATTRIBUTE_DATA_SOURCE, DataSource.class);
        SystemAttributeLoader.load(dataSource);
//		反馈成功信息
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("参数新增成功！");
        
        //通知前端跳转路径
        Map map = new HashMap();
        map.put("URL", basePath);
        ajaxResponse.setExtParam(map);

//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.INSERT, "新增参数，参数内码：" + attr.getAttr_id() + "，参数编号：" + attr.getAttr_code() + "，参数名称：" + attr.getAttr_name());
        return ajaxResponse;
    }

    /**
     * 跳转到编辑参数页面
     *
     * @param attrId 参数编号
     * @param model 反馈对象
     * @param request 请求对象
     * @return basePath + 'editAttr'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/{attrId}/edit", method = RequestMethod.GET)
    public String editAttr(@PathVariable String attrId, Model model, HttpServletRequest request) throws Exception {
//		获取参数信息
        Attr attr = dao.getAttrById(attrId);
//		设置到反馈对象中
        model.addAttribute("attr", attr);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问编辑参数页面，参数内码：" + attr.getAttr_id() + "，参数编号：" + attr.getAttr_code() + "，参数名称：" + attr.getAttr_name());
//		执行跳转到编辑页面
        return basePath + "editAttr";
    }

    /**
     * 执行编辑参数
     *
     * @param attr 参数对象
     * @param bindingResult 绑定信息
     * @param request 请求对象
     * @return ajax响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse editAttr(@Valid Attr attr, BindingResult bindingResult,
            HttpServletRequest request) throws Exception {
//		定义AJAX响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
//		校验唯一
        if (!UniqueUtils.unique(dao, ajaxResponse, "参数编号已经被使用，请重新填写。", "ATTR_CODE_UNIQUE_FOR_EDIT", attr.getAttr_code(), attr.getAttr_id())) {
//			记录操作日志
            LogUtils.updateOperationLog(request, OperationType.UPDATE, "编辑参数（失败：参数编号已经被使用，参数编号：" + attr.getAttr_code() + "）");
            return ajaxResponse;
        }
//		如果发现错误则拼接错误信息执行回执
        if (bindingResult.hasErrors()) {
            ajaxResponse.bindingResultHandler(bindingResult);
//			记录操作日志
            LogUtils.updateOperationLog(request, OperationType.UPDATE, "编辑参数，（失败：数据验证错误，详细信息：" + AjaxResponse.getBindingResultMessage(bindingResult) + "）");
            return ajaxResponse;
        }
//		执行编辑参数
        dao.updateAttr(attr);
//		参数重新加载
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(CONFIG.SERVLET_CONTEXT);
        DataSource dataSource = context.getBean(CONFIG.SYSTEM_ATTRIBUTE_DATA_SOURCE, DataSource.class);
        SystemAttributeLoader.load(dataSource);
//		反馈成功信息
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("参数编辑成功！");
        //通知前端跳转路径
        Map map = new HashMap();
        map.put("URL", basePath);
        ajaxResponse.setExtParam(map);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.UPDATE, "编辑参数，参数内码：" + attr.getAttr_id() + "，参数编号：" + attr.getAttr_code() + "，参数名称：" + attr.getAttr_name());
        return ajaxResponse;
    }

    /**
     * 执行删除参数
     *
     * @param attrIds 参数编号列表
     * @param request 请求对象
     * @return ajax响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse deleteAttr(String attrIds, HttpServletRequest request) throws Exception {
//		定义最终需要删除的子系统列表
        StringBuffer deleteAttrIds = new StringBuffer();
//		定义各个数量
        int totalCount = 0;
        int isSystemAttrCount = 0;
//		遍历判断
        for (String attrId : attrIds.split(",")) {
            totalCount++;
//			获取参数对象
            Attr attr = dao.getAttrById(attrId);
//			判断参数是否为系统参数
            if ("1".equals(attr.getType())) {
                isSystemAttrCount++;
                continue;
            }
//			拼接删除参数编号
            deleteAttrIds.append(attrId).append(",");
        }
        if (deleteAttrIds.length() > 0) {
            deleteAttrIds.deleteCharAt(deleteAttrIds.length() - 1);
//			执行删除系统
            dao.deleteAttr(deleteAttrIds.toString());
        }
//		参数重新加载
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(CONFIG.SERVLET_CONTEXT);
        DataSource dataSource = context.getBean(CONFIG.SYSTEM_ATTRIBUTE_DATA_SOURCE, DataSource.class);
        SystemAttributeLoader.load(dataSource);
//		处理响应信息
        StringBuffer message = new StringBuffer();
        message.append("本次共执行").append(totalCount).append("条记录，其中：<br /><br />");
        message.append("　　成功删除系统").append(totalCount - isSystemAttrCount).append("条。<br />");
        if (isSystemAttrCount > 0) {
            message.append("　　因为是系统内置参数无法删除的记录共").append(isSystemAttrCount).append("条。<br />");
        }
//		执行响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage(message.toString());
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.DELETE, "删除参数，删除参数内码列表：" + attrIds + "，删除提示信息：" + message.toString());
         return ajaxResponse;
    }

    /**
     * 跳转到设置默认快捷方式页面
     *
     * @param request 请求对象
     * @param model 反馈对象
     * @return basePath + 'setDefaultShortcutLimit'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/set_default_shortcut_limit", method = RequestMethod.GET)
    public String setDefaultShortcutLimit(HttpServletRequest request, Model model) throws Exception {
//		获取用户的快捷方式菜单
        List<Map<String, Object>> shortcutLimitList = dao.getDefaultShortcutLimitList();
        model.addAttribute("shortcutLimitList", JSONArray.fromObject(shortcutLimitList).toString());
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "跳转到设置默认快捷方式页面");
        return basePath + "setDefaultShortcutLimit";
    }

    /**
     * 执行默认快捷方式设置
     *
     * @param request 请求对象
     * @param shortcutLimitIds 快捷方式菜单列表
     * @return AJAX的响应信息
     * @throws Exception
     */
    @RequestMapping(value = "/set_default_shortcut_limit", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse setDefaultShortcutLimit(HttpServletRequest request, String shortcutLimitIds) throws Exception {
//		定义AJAX响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
//		更新默认快捷方式菜单
        dao.updateDefaultShortcutLimit(shortcutLimitIds);
//		反馈AJAX响应
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("默认快捷方式设置成功！");
        
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.UPDATE, "设置默认快捷方式");
        return ajaxResponse;
    }

}
