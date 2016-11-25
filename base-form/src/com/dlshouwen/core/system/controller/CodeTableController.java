package com.dlshouwen.core.system.controller;

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
import com.dlshouwen.core.base.loader.CodeTableLoader;
import com.dlshouwen.core.base.model.AjaxResponse;
import com.dlshouwen.core.base.model.OperationType;
import com.dlshouwen.core.base.utils.LogUtils;
import com.dlshouwen.core.system.dao.CodeTableDao;
import com.dlshouwen.core.system.model.CodeTable;
import com.dlshouwen.core.system.model.CodeTableType;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * 码表
 *
 * @author 大连首闻科技有限公司
 * @version 2013-08-17 08:04:28 759
 */
@Controller
@RequestMapping("/core/system/code_table")
public class CodeTableController {

    /**
     * 功能根路径
     */
    private String basePath = "core/system/codeTable/";

    /**
     * 数据操作对象
     */
    private CodeTableDao dao;

    /**
     * 注入数据操作对象
     *
     * @param dao 数据操作对象
     */
    @Resource(name = "codeTableDao")
    public void setDao(CodeTableDao dao) {
        this.dao = dao;
    }

    /**
     * 跳转到码表类别页面
     *
     * @param request 请求对象
     * @return basePath + 'codeTableTypeList'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String goCodeTableTypePage(HttpServletRequest request) throws Exception {
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问码表类别管理页面");
        return basePath + "codeTableTypeList";
    }

    /**
     * 获取码表类别列表数据
     *
     * @param gridPager 表格分页信息
     * @param request 请求对象
     * @param response 响应对象
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/code_table_type/list", method = RequestMethod.POST)
    public void getCodeTableTypeList(String gridPager, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		映射Pager对象
        Pager pager = PagerPropertyUtils.copy(JSONObject.fromObject(gridPager));
//		获取码表类别列表数据
        dao.getCodeTableTypeList(pager, request, response);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.SEARCH, "获取码表类别管理页面中的码表类别列表数据");
    }

    /**
     * 跳转到新增码表类别页面
     *
     * @param request 请求对象
     * @param model 反馈对象
     * @return basePath + 'addCodeTableType'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/code_table_type/add", method = RequestMethod.GET)
    public String addCodeTableType(HttpServletRequest request, Model model) throws Exception {
        model.addAttribute("codeTableType", new CodeTableType());
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问新增码表类别页面");
        return basePath + "addCodeTableType";
    }

    /**
     * 执行新增码表类别
     *
     * @param codeTableType 码表类别对象
     * @param bindingResult 绑定结果
     * @param request 请求对象
     * @return ajax响应内容
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/code_table_type/add", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse addCodeTableType(@Valid CodeTableType codeTableType, BindingResult bindingResult,
            HttpServletRequest request) throws Exception {
//		定义AJAX响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
//		校验唯一
        if (!UniqueUtils.unique(dao, ajaxResponse, "码表类别编号已经被使用，请重新填写。", "CODE_TABLE_C_TYPE_UNIQUE_FOR_ADD", codeTableType.getC_type())) {
//			记录操作日志
            LogUtils.updateOperationLog(request, OperationType.INSERT, "新增码表类别（失败：码表类别编号已被使用，码表类别编号：" + codeTableType.getC_type() + "）");
            return ajaxResponse;
        }
//		如果发现错误则拼接错误信息执行回执
        if (bindingResult.hasErrors()) {
            ajaxResponse.bindingResultHandler(bindingResult);
//			记录操作日志
            LogUtils.updateOperationLog(request, OperationType.INSERT, "新增码表类别（失败：数据验证错误，码表类别编号：" + codeTableType.getC_type() + "）");
            return ajaxResponse;
        }
//		执行新增码表类别
        dao.insertCodeTableType(codeTableType);
//		重新加载码表
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(CONFIG.SERVLET_CONTEXT);
        DataSource dataSource = context.getBean(CONFIG.CODE_TABLE_DATA_SOURCE, DataSource.class);
        CodeTableLoader.load(dataSource);
//		反馈成功信息
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("码表类别新增成功！");
        //通知前端跳转路径
        Map map = new HashMap();
        map.put("URL","/core/system/code_table");
        ajaxResponse.setExtParam(map);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.INSERT, "新增码表类别，码表类别编号：" + codeTableType.getC_type());
        return ajaxResponse;
    }

    /**
     * 跳转到编辑码表类别页面
     *
     * @param c_type 码表类别编号
     * @param request 请求对象
     * @param model 反馈对象
     * @return basePath + 'editCodeTableType'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/code_table_type/{c_type}/edit", method = RequestMethod.GET)
    public String editCodeTableType(@PathVariable String c_type, HttpServletRequest request, Model model) throws Exception {
//		获取码表类别信息
        CodeTableType codeTableType = dao.getCodeTableTypeByType(c_type);
//		设置到反馈对象中
        model.addAttribute("codeTableType", codeTableType);
//		设置原编号到反馈对象中
        model.addAttribute("oldType", c_type);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问编辑码表类别页面，码表类别编号：" + c_type);
//		执行跳转到编辑页面
        return basePath + "editCodeTableType";
    }

    /**
     * 执行编辑码表类别
     *
     * @param codeTableType 码表类别对象
     * @param oldType 原始码表类别编号
     * @param request 请求对象
     * @param bindingResult 绑定信息
     * @return ajax响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/code_table_type/edit", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse editCodeTableType(@Valid CodeTableType codeTableType, String oldType, HttpServletRequest request,
            BindingResult bindingResult) throws Exception {
//		定义AJAX响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
//		校验唯一
        if (!UniqueUtils.unique(dao, ajaxResponse, "码表类别编号已经被使用，请重新填写。",
                "CODE_TABLE_C_TYPE_UNIQUE_FOR_EDIT", codeTableType.getC_type(), oldType)) {
//			记录操作日志
            LogUtils.updateOperationLog(request, OperationType.UPDATE, "编辑码表类别（失败：码表类别编号已被使用），码表类别编号：" + codeTableType.getC_type());
            return ajaxResponse;
        }
//		如果发现错误则拼接错误信息执行回执
        if (bindingResult.hasErrors()) {
            ajaxResponse.bindingResultHandler(bindingResult);
//			记录操作日志
            LogUtils.updateOperationLog(request, OperationType.UPDATE, "编辑码表类别（失败：数据验证错误），码表类别编号：" + codeTableType.getC_type());
            return ajaxResponse;
        }
//		执行编辑码表类别
        dao.updateCodeTableType(codeTableType, oldType);
//		执行编辑码表下的所有码表类别
        dao.updateCodeTable(codeTableType, oldType);
//		重新加载码表
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(CONFIG.SERVLET_CONTEXT);
        DataSource dataSource = context.getBean(CONFIG.CODE_TABLE_DATA_SOURCE, DataSource.class);
        CodeTableLoader.load(dataSource);
//		反馈成功信息
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("码表类别编辑成功！");
                //通知前端跳转路径
        Map map = new HashMap();
        map.put("URL", "/core/system/code_table");
        ajaxResponse.setExtParam(map);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.UPDATE, "编辑码表类别，码表类别编号：" + codeTableType.getC_type());
        return ajaxResponse;
    }

    /**
     * 执行删除码表类别
     *
     * @param codeTableTypeIds 码表类别编号列表
     * @param request 请求对象
     * @return ajax响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/code_table_type/delete", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse deleteCodeTableType(String c_types, HttpServletRequest request) throws Exception {
//		定义最终需要删除的码表类别列表
        StringBuffer deleteC_types = new StringBuffer();
//		定义各个数量
        int totalCount = c_types.split(",").length;
        int hasCodeTable = 0;
//		删除成功、失败的纪录编号
        StringBuffer successIds = new StringBuffer();
        StringBuffer failureIds = new StringBuffer();
//		遍历判断
        for (String c_type : c_types.split(",")) {
//			码表类别是否包含码表
            boolean codeTableTypeIsHaveCodeTable = dao.getCodeTableTypeIsHaveCodeTable(c_type);
            if (codeTableTypeIsHaveCodeTable) {
                hasCodeTable++;
                failureIds.append(c_type).append(",");
                continue;
            }
            successIds.append(c_type).append(",");
//			拼接删除系统编号
            deleteC_types.append(c_type).append(",");
        }
        if (deleteC_types.length() > 0) {
            deleteC_types.deleteCharAt(deleteC_types.length() - 1);
//			执行删除码表类别
            dao.deleteCodeTableType(deleteC_types.toString());
        }
//		处理响应信息
        StringBuffer message = new StringBuffer();
        message.append("本次共执行").append(totalCount).append("条记录，其中：<br /><br />");
        message.append("　　成功删除码表类别").append(totalCount - hasCodeTable).append("条。<br />");
        if (hasCodeTable > 0) {
            message.append("　　因为系统下包含码表无法删除的记录共").append(hasCodeTable).append("条。<br />");
        }
//		重新加载码表
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(CONFIG.SERVLET_CONTEXT);
        DataSource dataSource = context.getBean(CONFIG.CODE_TABLE_DATA_SOURCE, DataSource.class);
        CodeTableLoader.load(dataSource);
//		执行响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage(message.toString());
        ajaxResponse.setData(deleteC_types.toString().replaceAll("'", ""));

//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.DELETE,
                "删除码表，成功删除码表：" + successIds.toString() + "；因为系统下包含码表无法删除的码表：" + failureIds.toString());
        return ajaxResponse;
    }

    /**
     * 跳转到码表主页面
     *
     * @param c_type 码表类别编号
     * @param request 请求对象
     * @param model 反馈对象
     * @return basePath + 'codeTableList'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/{c_type}/code_table", method = RequestMethod.GET)
    public String goCodeTablePage(@PathVariable String c_type, HttpServletRequest request, Model model) throws Exception {
//		传递码表类型到前台
        model.addAttribute("c_type", c_type);
//		获取码表类别信息传递到前台
        CodeTableType codeTableType = dao.getCodeTableTypeByType(c_type);
        model.addAttribute("codeTableType", codeTableType);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问码表管理页面，码表类别编号：" + c_type);
        return basePath + "codeTableList";
    }

    /**
     * 获取码表列表数据
     *
     * @param gridPager 表格分页信息
     * @param request 请求对象
     * @param response 响应对象
     * @param c_type 码表类别
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/{c_type}/code_table/list", method = RequestMethod.POST)
    public void getCodeTableList(@PathVariable String c_type, String gridPager, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		映射Pager对象
        Pager pager = PagerPropertyUtils.copy(JSONObject.fromObject(gridPager));
//		获取码表列表数据
        dao.getCodeTableList(pager, request, response, c_type);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.SEARCH, "获取码表管理页面中的码表列表数据，码表类别编号" + c_type);
    }

    /**
     * 跳转到新增码表页面
     *
     * @param c_type 码表类别
     * @param request 请求对象
     * @param model 反馈对象
     * @return basePath + 'addCodeTable'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/{c_type}/code_table/add", method = RequestMethod.GET)
    public String addCodeTable(@PathVariable String c_type, HttpServletRequest request, Model model) throws Exception {
//		创建码表对象，设置码表类别
        CodeTable codeTable = new CodeTable();
        codeTable.setC_type(c_type);
        model.addAttribute("codeTable", codeTable);
//		获取码表类别信息传递到前台
        CodeTableType codeTableType = dao.getCodeTableTypeByType(c_type);
        model.addAttribute("codeTableType", codeTableType);
        
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问新增码表页面，码表类别编号：" + c_type);
        return basePath + "addCodeTable";
    }

    /**
     * 执行新增码表
     *
     * @param codeTable 码表对象
     * @param bindingResult 绑定结果
     * @param request 请求对象
     * @return ajax响应内容
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/code_table/add", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse addCodeTable(@Valid CodeTable codeTable, BindingResult bindingResult,
            HttpServletRequest request) throws Exception {
//		定义AJAX响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
//		校验唯一
        if (!UniqueUtils.unique(dao, ajaxResponse, "码表编号已经被使用，请重新填写。",
                "CODE_TABLE_C_KEY_UNIQUE_FOR_ADD", codeTable.getC_type(), codeTable.getC_type())) {
//			记录操作日志
            LogUtils.updateOperationLog(request, OperationType.INSERT, "新增码表（失败：码表编号已被使用，码表类别编号：" + codeTable.getC_type() + "，码表编号：" + codeTable.getC_key() + "）");
            return ajaxResponse;
        }
//		如果发现错误则拼接错误信息执行回执
        if (bindingResult.hasErrors()) {
            ajaxResponse.bindingResultHandler(bindingResult);
//			记录操作日志
            LogUtils.updateOperationLog(request, OperationType.INSERT, "新增码表（失败：数据验证错误，码表类别编号：" + codeTable.getC_type() + "，码表编号：" + codeTable.getC_key() + "）");
            return ajaxResponse;
        }
//		执行新增码表
        dao.insertCodeTable(codeTable);
//		重新加载码表
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(CONFIG.SERVLET_CONTEXT);
        DataSource dataSource = context.getBean(CONFIG.CODE_TABLE_DATA_SOURCE, DataSource.class);
        CodeTableLoader.load(dataSource);
//		反馈成功信息
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("码表新增成功！");
        //通知前端跳转路径
        Map map = new HashMap();
        map.put("URL", "core/system/code_table/"+codeTable.getC_type()+"/code_table");
        ajaxResponse.setExtParam(map);

//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.INSERT, "新增码表，码表类别编号：" + codeTable.getC_type() + "，码表编号：" + codeTable.getC_key());
        return ajaxResponse;
    }

    /**
     * 跳转到编辑码表页面
     *
     * @param c_type
     * @param c_key
     * @param request 请求对象
     * @param model
     * @return
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/{c_type}/code_table/{c_key}/edit", method = RequestMethod.GET)
    public String editCodeTable(@PathVariable String c_type, @PathVariable String c_key,
            HttpServletRequest request, Model model) throws Exception {
//		获取码表信息
        CodeTable codeTable = dao.getCodeTableByTypeAndKey(c_type, c_key);
//		设置到反馈对象中
        model.addAttribute("codeTable", codeTable);
//		将原key设置到反馈对象中
        model.addAttribute("oldKey", c_key);
//		获取码表类别信息传递到前台
        CodeTableType codeTableType = dao.getCodeTableTypeByType(c_type);
        model.addAttribute("codeTableType", codeTableType);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问编辑码表页面，码表类别编号：" + c_type + "，码表编号：" + c_key);
//		执行跳转到编辑页面
        return basePath + "editCodeTable";
    }

    /**
     * 执行编辑码表
     *
     * @param codeTable 码表对象
     * @param oldKey 原码表key值
     * @param request 请求对象
     * @param bindingResult 绑定的信息
     * @return ajax响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/code_table/edit", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse editCodeTable(@Valid CodeTable codeTable, String oldKey,
            HttpServletRequest request, BindingResult bindingResult) throws Exception {
//		定义AJAX响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
//		校验唯一
        if (!UniqueUtils.unique(dao, ajaxResponse, "码表编号已经被使用，请重新填写。", "CODE_TABLE_C_KEY_UNIQUE_FOR_EDIT",
                codeTable.getC_key(), codeTable.getC_type(), oldKey)) {
//			记录操作日志
            LogUtils.updateOperationLog(request, OperationType.UPDATE, "编辑码表（失败：码表编号已被使用），码表类别编号：" + codeTable.getC_type() + "，码表编号：" + codeTable.getC_key());
            return ajaxResponse;
        }
//		如果发现错误则拼接错误信息执行回执
        if (bindingResult.hasErrors()) {
            ajaxResponse.bindingResultHandler(bindingResult);
//			记录操作日志
            LogUtils.updateOperationLog(request, OperationType.UPDATE, "编辑码表（失败：数据验证错误），码表类别编号：" + codeTable.getC_type() + "，码表编号：" + codeTable.getC_key());
            return ajaxResponse;
        }
//		执行编辑码表
        dao.updateCodeTable(codeTable, oldKey);
//		重新加载码表
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(CONFIG.SERVLET_CONTEXT);
        DataSource dataSource = context.getBean(CONFIG.CODE_TABLE_DATA_SOURCE, DataSource.class);
        CodeTableLoader.load(dataSource);
//		反馈成功信息
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("码表编辑成功！");
                //通知前端跳转路径
        Map map = new HashMap();
        map.put("URL", "core/system/code_table/"+codeTable.getC_type()+"/code_table");
        ajaxResponse.setExtParam(map);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.UPDATE, "编辑码表，码表类别编号：" + codeTable.getC_type() + "，码表编号：" + codeTable.getC_key());
        return ajaxResponse;
    }

    /**
     * 执行删除码表
     *
     * @param c_type 码表类别编号
     * @param c_keys 码表Key列表
     * @param request 请求对象
     * @return ajax响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/{c_type}/code_table/delete", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse deleteCodeTable(@PathVariable String c_type, String c_keys, HttpServletRequest request) throws Exception {
//		执行删除码表
        dao.deleteCodeTable(c_type, c_keys);
//		重新加载码表
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(CONFIG.SERVLET_CONTEXT);
        DataSource dataSource = context.getBean(CONFIG.CODE_TABLE_DATA_SOURCE, DataSource.class);
        CodeTableLoader.load(dataSource);
//		执行响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("码表删除成功！");
  
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.DELETE, "删除码表，码表类别编号：" + c_type + "，码表编号列表：" + c_keys);
        return ajaxResponse;
    }

}
