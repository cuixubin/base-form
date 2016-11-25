package com.dlshouwen.core.system.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dlshouwen.core.base.config.CONFIG;
import com.dlshouwen.core.base.model.AjaxResponse;
import com.dlshouwen.core.base.model.OperationType;
import com.dlshouwen.core.base.model.SessionUser;
import com.dlshouwen.core.base.utils.GUID;
import com.dlshouwen.core.base.utils.LogUtils;
import com.dlshouwen.core.system.dao.DeptDao;
import com.dlshouwen.core.system.model.Dept;
import java.util.HashMap;

/**
 * 部门
 *
 * @author 大连首闻科技有限公司
 * @version 2013-08-17 08:04:29 131
 */
@Controller
@RequestMapping("/core/system/dept")
public class DeptController {

    /**
     * 功能根路径
     */
    private String basePath = "core/system/dept/";

    /**
     * 数据操作对象
     */
    private DeptDao dao;

    /**
     * 注入数据操作对象
     *
     * @param dao 数据操作对象
     */
    @Resource(name = "deptDao")
    public void setDao(DeptDao dao) {
        this.dao = dao;
    }

    /**
     * 跳转到部门主页面
     *
     * @param request 请求对象
     * @param model 反馈对象
     * @return basePath + 'deptMain'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String goDeptMainPage(HttpServletRequest request, Model model) throws Exception {
//		获取部门列表传递到前台
        List<Map<String, Object>> deptList = dao.getDeptList();
        model.addAttribute("deptTree", JSONArray.fromObject(deptList).toString());
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问部门管理页面");
        return basePath + "deptMain";
    }

    /**
     * 获取部门信息
     *
     * @param deptId 部门编号
     * @param request 请求对象
     * @param model 反馈对象
     * @return 部门信息
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    @ResponseBody
    public Dept viewDept(String deptId, HttpServletRequest request, Model model) throws Exception {
//		获取部门信息
        Dept dept = dao.getDeptById(deptId);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "获取部门信息，部门编号：" + dept.getDept_id() + "，部门名称：" + dept.getDept_name());
        return dept;
    }

    /**
     * 跳转到新增部门页面
     *
     * @param preDeptId 上级部门编号
     * @param request 请求对象
     * @param model 反馈对象
     * @return basePath + 'addDept'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/{preDeptId}/add", method = RequestMethod.GET)
    public String addDept(@PathVariable String preDeptId, HttpServletRequest request, Model model) throws Exception {
//		定义新的部门对象
        Dept dept = new Dept();
//		设置上级部门编号
        dept.setPre_dept_id(preDeptId);
//		传递新的部门到前台
        model.addAttribute("dept", dept);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问新增部门页面，上级部门编号：" + preDeptId);
        return basePath + "addDept";
    }

    /**
     * 执行新增部门
     *
     * @param dept 部门对象
     * @param bindingResult 绑定结果
     * @param request 请求对象
     * @return ajax响应内容
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse addDept(@Valid Dept dept, BindingResult bindingResult,
            HttpServletRequest request) throws Exception {
//		定义AJAX响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
//		如果发现错误则拼接错误信息执行回执
        if (bindingResult.hasErrors()) {
            ajaxResponse.bindingResultHandler(bindingResult);
//			记录操作日志
            LogUtils.updateOperationLog(request, OperationType.INSERT, "新增部门（失败：数据验证错误，部门编号：" + dept.getDept_id() + "，部门名称：" + dept.getDept_name() + "）");
            return ajaxResponse;
        }
//		设置部门编号、创建人、创建时间、编辑人、编辑时间
        dept.setDept_id(new GUID().toString());
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        dept.setCreator(sessionUser.getUser_id());
        dept.setCreate_time(new Date());
        dept.setEditor(sessionUser.getUser_id());
        dept.setEdit_time(new Date());
//		执行新增部门
        dao.insertDept(dept);
//		反馈成功信息
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("部门新增成功！");
        ajaxResponse.setData(dept);

        //用‘URL’字段通知前段页面的跳转路径
        Map map = new HashMap();
        map.put("URL", basePath);
        ajaxResponse.setExtParam(map);

//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.INSERT, "新增部门，部门编号：" + dept.getDept_id() + "，部门名称：" + dept.getDept_name());
        return ajaxResponse;
    }

    /**
     * 跳转到编辑部门页面
     *
     * @param deptId 部门编号
     * @param request 请求对象
     * @param model 反馈对象
     * @return base + 'editDept'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/{deptId}/edit", method = RequestMethod.GET)
    public String editDept(@PathVariable String deptId, HttpServletRequest request, Model model) throws Exception {
//		获取部门信息
        Dept dept = dao.getDeptById(deptId);
//		设置到反馈对象中
        model.addAttribute("dept", dept);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问编辑部门页面，部门编号：" + dept.getDept_id() + "，部门名称：" + dept.getDept_name());
//		执行跳转到编辑页面
        return basePath + "editDept";
    }

    /**
     * 执行编辑部门
     *
     * @param dept 部门对象
     * @param bindingResult 绑定对象
     * @param request 请求对象
     * @return ajax响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse editDept(@Valid Dept dept, BindingResult bindingResult,
            HttpServletRequest request) throws Exception {
//		定义AJAX响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
//		如果发现错误则拼接错误信息执行回执
        if (bindingResult.hasErrors()) {
            ajaxResponse.bindingResultHandler(bindingResult);
//			记录操作日志
            LogUtils.updateOperationLog(request, OperationType.UPDATE, "编辑部门（失败：数据验证错误，部门编号：" + dept.getDept_id() + "，部门名称：" + dept.getDept_name() + "）");
            return ajaxResponse;
        }
//		设置编辑人、编辑时间
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        dept.setEditor(sessionUser.getUser_id());
        dept.setEdit_time(new Date());
//		执行编辑部门
        dao.updateDept(dept);
//		反馈成功信息
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("部门编辑成功！");
        ajaxResponse.setData(dept);
          //用‘URL’字段通知前段页面的跳转路径
        Map map = new HashMap();
        map.put("URL", basePath);
        ajaxResponse.setExtParam(map);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.UPDATE, "编辑部门，部门编号：" + dept.getDept_id() + "，部门名称：" + dept.getDept_name());
        return ajaxResponse;
    }

    /**
     * 执行删除部门
     *
     * @param deptId 部门编号
     * @param request 请求对象
     * @return ajax响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse deleteDept(String deptIds, HttpServletRequest request) throws Exception {
//		定义最终需要删除的部门列表
        StringBuffer deleteDeptIds = new StringBuffer();
//		定义各个数量
        int totalCount = deptIds.split(",").length;
        int hasDeptCount = 0;
        int hasUserCount = 0;
//		删除成功、失败的纪录编号
        StringBuffer successIds = new StringBuffer();
        StringBuffer failureIds = new StringBuffer();
//		遍历判断
        for (String deptId : deptIds.split(",")) {
//			系统是否包含部门
            boolean isHaveSubDept = dao.getSubDeptCount(deptId) > 0 ? true : false;
            if (isHaveSubDept) {
                hasDeptCount++;
                failureIds.append(deptId).append(",");
                continue;
            }
//			部门是否包含人员
            boolean isHaveUser = dao.getDeptIsHaveUser(deptId);
            if (isHaveUser) {
                hasUserCount++;
                failureIds.append(deptId).append(",");
                continue;
            }
            successIds.append(deptId).append(",");
//			拼接删除部门编号
            deleteDeptIds.append(deptId).append(",");
        }
        if (deleteDeptIds.length() > 0) {
            deleteDeptIds.deleteCharAt(deleteDeptIds.length() - 1);
//			执行删除部门
            dao.deleteDept(deleteDeptIds.toString());
        }
//		处理响应信息
        StringBuffer message = new StringBuffer();
        message.append("本次共执行").append(totalCount).append("条记录，其中：<br /><br />");
        message.append("　　成功删除部门").append(totalCount - hasDeptCount - hasUserCount).append("条。<br />");
        if (hasDeptCount > 0) {
            message.append("　　因为包含子部门无法删除的记录共").append(hasDeptCount).append("条。<br />");
        }
        if (hasUserCount > 0) {
            message.append("　　因为包含用户无法删除的记录共").append(hasUserCount).append("条。<br />");
        }
//		执行响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage(message.toString());
        ajaxResponse.setData(deleteDeptIds.toString().replaceAll("'", ""));

//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.DELETE,
                "删除部门，成功删除部门：" + successIds.toString() + "；因为包含子部门无法删除的部门：" + failureIds.toString());
        return ajaxResponse;
    }

    /**
     * 选择部门
     *
     * @param type 选择类别
     * @param request 请求对象
     * @param model 反馈对象
     * @return basePath + 'selectDept'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/select/{type}", method = RequestMethod.GET)
    public String selectDept(@PathVariable String type,
            HttpServletRequest request, Model model) throws Exception {
        String deptId = request.getParameter("deptId");
//		传递选择类别到前台
        model.addAttribute("type", type);
//		获取部门树传递到前台
        List<Map<String, Object>> deptList = dao.getDeptList();
        if (deptId != null && deptId.trim().length() > 0) {
            for(Map obj : deptList){
                String deptId1 = (String) obj.get("dept_id");
                if(deptId1.equals(deptId)){
                    obj.put("checked", "true");
                    break;
                }
            }
        }
        model.addAttribute("deptTree", JSONArray.fromObject(deptList));
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问选择部门页面");
        return basePath + "selectDept";
    }

}
