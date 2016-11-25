/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlshouwen.jspc.zwpc.controller;

import com.dlshouwen.core.base.config.CONFIG;
import com.dlshouwen.core.base.extra.grid.model.Pager;
import com.dlshouwen.core.base.extra.grid.utils.PagerPropertyUtils;
import com.dlshouwen.core.base.http.FileUploadClient;
import com.dlshouwen.core.base.model.AjaxResponse;
import com.dlshouwen.core.base.model.OperationType;
import com.dlshouwen.core.base.model.SessionUser;
import com.dlshouwen.core.base.utils.AttributeUtils;
import com.dlshouwen.core.base.utils.CodeTableUtils;
import com.dlshouwen.core.base.utils.DateUtils;
import com.dlshouwen.core.base.utils.GUID;
import com.dlshouwen.core.base.utils.LogUtils;
import com.dlshouwen.core.system.dao.UserDao;
import com.dlshouwen.core.system.model.User;
import com.dlshouwen.core.system.model.UserVO;
import com.dlshouwen.jspc.zjpc.dao.ExpreviewDao;
import com.dlshouwen.jspc.zjpc.model.Expreview;
import com.dlshouwen.jspc.zwpc.dao.EvalItemDao;
import com.dlshouwen.jspc.zwpc.model.EvalItem;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 自我评测管理
 *
 * @author cuixubin
 */
@Controller
@RequestMapping("/jspc/zwpc/zwpc")
public class SelfEvaluateController {

    /**
     * 功能根路径
     */
    private String basePath = "jspc/zwpc/";

    /**
     * 评测项数据操作对象
     */
    private EvalItemDao dao;
    /**
     * 用户数据操作对象
     */
    private UserDao userDao;
    /**
     * 专家评审数据操作对象
     */
    private ExpreviewDao expDao;

    /**
     * 注入数据操作对象
     *
     * @param dao 数据操作对象
     */
    @Resource(name = "evalItemDao")
    public void setDao(EvalItemDao dao) {
        this.dao = dao;
    }

    /**
     * 注入数据操作对象
     *
     * @param dao 数据操作对象
     */
    @Resource(name = "userDao")
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * 注入数据操作对象
     *
     * @param expDao
     */
    @Resource(name = "expreviewDao")
    public void setExpDao(ExpreviewDao expDao) {
        this.expDao = expDao;
    }

    /**
     * 跳转到自我评测声明页面
     *
     * @param model 数据模型
     * @param request 请求对象
     * @param expectation 申请意愿信息
     * @return basePath + 'main'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String toDeclarationPage(Model model, HttpServletRequest request, String expectation) throws Exception {

//          初始化一条评审记录 
        Expreview exp = new Expreview();

        model.addAttribute("expreview", exp);

//	    记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问评测申请页面");
        return basePath + "declaration";
    }

    /**
     * 跳转到评测历史记录管理页
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listPage", method = RequestMethod.GET)
    public String toEvalItemListPage(HttpServletRequest request) throws Exception {
//	    记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问评测记录页面");
        return basePath + "evalList";
    }

    /**
     * 获取评测记录列表数据
     *
     * @param gridPager 表格分页信息
     * @param request 请求对象
     * @param response 响应对象
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public void getEvalList(String gridPager, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SessionUser sUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
//          映射Pager对象
        Pager pager = PagerPropertyUtils.copy(JSONObject.fromObject(gridPager));
//          获取评测数据
        expDao.getExpreviewByUserId(sUser.getUser_id(), pager, request, response);
//          记录操作日志
        LogUtils.updateOperationLog(request, OperationType.SEARCH, "获取评测记录列表数据");
    }

    /**
     * 跳转到自我评测申请页面
     *
     * @param qualified 所选意愿id
     * @param model 数据模型
     * @param request 请求对象
     * @return basePath + 'main'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/{qualified}/application", method = RequestMethod.GET)
    public String toApplyPage(@PathVariable String qualified, Model model, HttpServletRequest request) throws Exception {
//          从session中获取当前用户
        SessionUser sUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        User user = userDao.getUserById(sUser.getUser_id());

//          初始化一条评审记录 
        Expreview exp = new Expreview();
        exp.setQualified(qualified);
        exp.setId(new GUID().toString());
        exp.setUser_id(user.getUser_id());
        exp.setUser_name(user.getUser_name());
        exp.setStatus("4");//默认状态 4：未提交
        exp.setCreate_date(new Date());
        expDao.insertExpreview(exp);

//          初始化数据传输对象
        UserVO UVO = new UserVO(user, exp);

        model.addAttribute("UVO", UVO);

//	    记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问评测申请页面");
        return basePath + "application";
    }

    /**
     * 执行新增评测项
     *
     * @param evalItem 被新增的评测项
     * @param request 请求对象
     * @param bindingResult 绑定的错误信息
     * @param response
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/addEvalItem", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public void addEvalItem(@Valid EvalItem evalItem, BindingResult bindingResult,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
//          定义AJAX响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();

        if (bindingResult.hasErrors()) {
            ajaxResponse.bindingResultHandler(bindingResult);
            LogUtils.updateOperationLog(request, OperationType.INSERT, "新增评测项资料（新增失败，错误信息：" + AjaxResponse.getBindingResultMessage(bindingResult) + "）");
            response.setContentType("text/html;charset=utf-8");
            JSONObject obj = JSONObject.fromObject(ajaxResponse);
            response.getWriter().write(obj.toString());
            return;
        }

        if (StringUtils.isNotEmpty(evalItem.getEvalResult_id()) && StringUtils.isNotEmpty(evalItem.getType())) {
            SessionUser sUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
            evalItem.setCreator(sUser.getUser_id());
            evalItem.setEditor(sUser.getUser_id());
            String attachName = uploadFile(request, "upfile");
            evalItem.setAttach(attachName);
            evalItem.setCreateDate(new Date());
            evalItem.setEditDate(new Date());
            dao.insertItem(evalItem);
        }
//          执行AJAX响应回执
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("新增资料成功！");
        response.setContentType("text/html;charset=utf-8");
        JSONObject obj = JSONObject.fromObject(ajaxResponse);
        response.getWriter().write(obj.toString());
    }

    /**
     * 执行更新评测项
     *
     * @param evalItem 被更新的评测项
     * @param request 请求对象
     * @param bindingResult 绑定的错误信息
     * @return AJAX响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/updateEvalItem", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public void updateEvalItem(@Valid EvalItem evalItem, BindingResult bindingResult,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
//          定义AJAX响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();

        if (bindingResult.hasErrors()) {
            ajaxResponse.bindingResultHandler(bindingResult);
            LogUtils.updateOperationLog(request, OperationType.INSERT, "更新评测项资料（更新失败，错误信息：" + AjaxResponse.getBindingResultMessage(bindingResult) + "）");
            response.setContentType("text/html;charset=utf-8");
            JSONObject obj = JSONObject.fromObject(ajaxResponse);
            response.getWriter().write(obj.toString());
            return;
        }

        if (StringUtils.isNotEmpty(evalItem.getEvalResult_id()) && StringUtils.isNotEmpty(evalItem.getType())) {
            SessionUser sUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
            evalItem.setEditor(sUser.getUser_id());
            String attachName = uploadFile(request, "upfile");
            evalItem.setAttach(attachName);
            evalItem.setEditDate(new Date());
            dao.updateEvalItem(evalItem);
        }
//          执行AJAX响应回执
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("更新资料成功！");
        response.setContentType("text/html;charset=utf-8");
        JSONObject obj = JSONObject.fromObject(ajaxResponse);
        response.getWriter().write(obj.toString());
    }

    /**
     * 刷新评测项数据
     *
     * @param expId_itemType
     * @param request 请求对象
     * @return AJAX响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/{expId_itemType}/refreshItems", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public AjaxResponse refreshItems(@PathVariable String expId_itemType, HttpServletRequest request) throws Exception {
//          定义AJAX响应回执
        AjaxResponse aresp = new AjaxResponse();

        String expId = null;
        String itemType = null;
        if (StringUtils.isNotEmpty(expId_itemType)) {
            expId = expId_itemType.split("_")[0];
            itemType = expId_itemType.split("_")[1];
        }

        List<EvalItem> etList = new ArrayList<EvalItem>();
        if (StringUtils.isNotEmpty(expId) && StringUtils.isNotEmpty(itemType)) {
            etList = dao.getByExpIdAndItemType(expId, itemType);
        }

        String html = getItemForHtml(etList, request.getServletContext());

        Map map = new HashMap();
        map.put("ITEM_DATE", html);
        map.put("ITEM_TYPE", itemType);
        aresp.setSuccess(true);
        aresp.setSuccessMessage("添加资料成功！");
        aresp.setExtParam(map);
        return aresp;
    }

    /**
     * 获取所有评测项数据
     *
     * @param expId
     * @param request 请求对象
     * @return AJAX响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/{expId}/getAllItems", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public AjaxResponse getAllItems(@PathVariable String expId, HttpServletRequest request) throws Exception {
//          定义AJAX响应回执
        AjaxResponse aresp = new AjaxResponse();

        //          获取用户评测项数据
        List<EvalItem> evalItems = new ArrayList<EvalItem>();
        evalItems = dao.getAllByExpId(expId);

//          具体评测项
        List ET[] = new ArrayList[8];

        for (int i = 0; i < ET.length; i++) {
            ET[i] = new ArrayList<EvalItem>();
        }

        for (EvalItem item : evalItems) {
            if (item.getType().equals("JSYR")) {//教书育人
                ET[0].add(item);
            } else if (item.getType().equals("KCJX")) {//课程教学
                ET[1].add(item);
            } else if (item.getType().equals("JYJXYJ")) {//教育教学研究
                ET[2].add(item);
            } else if (item.getType().equals("YXL")) {//影响了
                ET[3].add(item);
            } else if (item.getType().equals("JYJXJL")) {//教育教学经理
                ET[4].add(item);
            } else if (item.getType().equals("JXJYJL")) {//继续教育经历
                ET[5].add(item);
            } else if (item.getType().equals("ZYJSZC")) {//专业技术职称
                ET[6].add(item);
            } else if (item.getType().equals("QT")) {//其他
                ET[7].add(item);
            }
        }

        Map map = new HashMap();
        map.put("JSYR", getItemForHtml(ET[0], request.getServletContext()));
        map.put("KCJX", getItemForHtml(ET[1], request.getServletContext()));
        map.put("JYJXYJ", getItemForHtml(ET[2], request.getServletContext()));
        map.put("YXL", getItemForHtml(ET[3], request.getServletContext()));
        map.put("JYJXJL", getItemForHtml(ET[4], request.getServletContext()));
        map.put("JXJYJL", getItemForHtml(ET[5], request.getServletContext()));
        map.put("ZYJSZC", getItemForHtml(ET[6], request.getServletContext()));
        map.put("QT", getItemForHtml(ET[7], request.getServletContext()));
        aresp.setExtParam(map);
        aresp.setSuccess(true);
        aresp.setSuccessMessage("加载完成");
        return aresp;
    }

    /**
     * 跳转到自我评测申请记录详情页面(可修改记录)
     *
     * @param expId 评审项id
     * @param model 数据模型
     * @param request 请求对象
     * @return basePath + 'applyDetail'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/{expId}/toDetailPage", method = RequestMethod.GET)
    public String toApplyDetailPage(@PathVariable String expId, Model model, HttpServletRequest request) throws Exception {

        String userid = null;
        if (StringUtils.isNotEmpty(expId)) {
            userid = expDao.getExpreviewById(expId).getUser_id();
        }

//          初始化数据传输对象
        User temUser = userDao.getUserById(userid);
        Expreview temExp = expDao.getExpreviewById(expId);
        UserVO UVO = new UserVO(temUser, temExp);

        model.addAttribute("UVO", UVO);
//	    记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问自我评测详情页面");
        return basePath + "applyDetail";
    }

    /**
     * 跳转到自我评测申请记录详情页面
     *
     * @param expId 评审项id
     * @param model 数据模型
     * @param request 请求对象
     * @return basePath + 'applyDetail'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/{expId}/toResultPage", method = RequestMethod.GET)
    public String toResultPage(@PathVariable String expId, Model model, HttpServletRequest request) throws Exception {

        String userid = null;
        if (StringUtils.isNotEmpty(expId)) {
            userid = expDao.getExpreviewById(expId).getUser_id();
        }

//          初始化数据传输对象
        User temUser = userDao.getUserById(userid);
        Expreview temExp = expDao.getExpreviewById(expId);
        UserVO UVO = new UserVO(temUser, temExp);

        model.addAttribute("UVO", UVO);
//	    记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问自我评测详情页面");
        return basePath + "applyDetail";
    }

    /**
     * *
     * 根据id删除一个item
     *
     * @param itemId
     * @param request
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/deleteItem", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse deleteItem(String itemId, HttpServletRequest request) throws Exception {

        AjaxResponse ajaxResponse = new AjaxResponse();
//		记录操作日志
        if (StringUtils.isNotEmpty(itemId)) {
            dao.deleteItem(itemId);
        }
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("删除成功");
        return ajaxResponse;
    }

    /**
     * *
     * 提交评审
     *
     * @param expId
     * @param expStatu
     * @param request
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/submitItems", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse submitItems(String expId, String expStatu, HttpServletRequest request) throws Exception {

        AjaxResponse ajaxResponse = new AjaxResponse();
//		记录操作日志
        if (StringUtils.isNotEmpty(expId)) {
            Expreview exp = expDao.getExpreviewById(expId);
            exp.setStatus("0");//设置待评审状态
            expDao.updateExpreview(exp);
        }
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("提交评审成功");
        //用‘URL’字段通知前段页面的跳转路径
        Map map = new HashMap();
        if ("notNumber".equals(expStatu)) {
            map.put("URL", basePath + "zwpc");
        } else {
            map.put("URL", basePath + "zwpc/listPage");
        }
        ajaxResponse.setExtParam(map);

        return ajaxResponse;
    }

    /**
     * 跳转至新增评价项弹出页
     *
     * @param expId_itemType 评审记录id
     * @param request 请求对象
     * @return basePath + 'toAddEvalItemPage'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/{expId_itemType}/toAddEvalItemPage", method = RequestMethod.GET)
    public String toAddEvalItemPage(@PathVariable String expId_itemType, HttpServletRequest request, Model model) throws Exception {
        EvalItem evalItem = null;
        String jspName = "addEvalItem";

        if (expId_itemType.contains("_")) {
            evalItem = new EvalItem();
            evalItem.setEvalItem_id(new GUID().toString());
            String expId = null;
            String itemType = null;
            if (StringUtils.isNotEmpty(expId_itemType)) {
                expId = expId_itemType.split("_")[0];
                itemType = expId_itemType.split("_")[1];
            }
            evalItem.setEvalResult_id(expId);
            evalItem.setType(itemType);
        } else {
            evalItem = dao.getEvalItemById(expId_itemType);
            jspName = "updateEvalItem";
        }

        model.addAttribute("evalItem", evalItem);
//	    记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问评测项添加弹出页");
        return basePath + jspName;
    }

    /**
     * 跳转至本人信息页面
     *
     * @param request 请求对象
     * @return basePath + 'toUserInfoPage'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/toUserInfoPage", method = RequestMethod.GET)
    public String toUserInfoPage(HttpServletRequest request) throws Exception {
//	    记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问本人信息弹出页");
        return basePath + "userInfo";
    }

    /**
     * 执行删除申请
     *
     * @param applyIds 申请编号列表
     * @param request 请求对象
     * @return ajax响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/deleteApply", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse deleteApply(String applyIds,
            HttpServletRequest request) throws Exception {
//          定义最终需要删除的申请列表
        StringBuffer deleteApplayIds = new StringBuffer();

//          定义各个数量
        int totalCount = applyIds.split(",").length;
        int expNowCount = 0;

//          删除成功、失败的纪录编号
        StringBuffer successIds = new StringBuffer();
        StringBuffer failureIds = new StringBuffer();

//          遍历判断
        for (String applyId : applyIds.split(",")) {
//              是否处于评审中状态
            boolean isNowExp = expDao.getExpreviewById(applyId).getStatus().equals("0");
            if (isNowExp) {
                expNowCount++;
                failureIds.append(applyId).append(",");
                continue;
            }

            successIds.append(applyId).append(",");
//              拼接删除申请编号
            deleteApplayIds.append(applyId).append(",");
        }
        if (deleteApplayIds.length() > 0) {
            deleteApplayIds.deleteCharAt(deleteApplayIds.length() - 1);
//              执行删除申请
            expDao.deleteExpreview(deleteApplayIds.toString());
        }
//          处理响应信息
        StringBuffer message = new StringBuffer();
        message.append("本次共执行").append(totalCount).append("条记录，其中：<br /><br />");
        message.append("　　成功删除申请").append(totalCount - expNowCount).append("条。<br />");
        if (expNowCount > 0) {
            message.append("　　因为正在评审中而无法删除的记录共").append(expNowCount).append("条。<br />");
        }
//          执行响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage(message.toString());
        ajaxResponse.setData(deleteApplayIds.toString().replaceAll("'", ""));
//          记录操作日志
        LogUtils.updateOperationLog(request, OperationType.DELETE,
                "删除申请，成功删除申请：" + successIds.toString() + "；因为正处于评审中无法被删除的申请：" + failureIds.toString());
        return ajaxResponse;
    }

     //下载附件
    @RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
    public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        String itemId = request.getParameter("itemId");
        String path = dao.getEvalItemById(itemId).getAttach();
        String fileName = path.substring(path.lastIndexOf("/") + 1);
        File f = new File(path);
        if (f.exists()) {
            FileInputStream filein = new FileInputStream(f);
            byte[] b = new byte[1024];
            try {
                int len = filein.read(b);
                if (fileName != null) {
                    fileName = new String(fileName.getBytes(), "ISO8859-1");
                    if (fileName != "") {
                        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
                    }
                }
                while (len > 0) {
                    response.getOutputStream().write(b, 0, len);
                    len = filein.read(b);
                }
            } catch (Exception e) {
                System.out.println(e.getStackTrace() + e.getMessage());
            } finally {
                response.getOutputStream().flush();
                response.getOutputStream().close();
                filein.close();
            }
        }else {
            response.getWriter().print("下载失败！文件已损坏或者已被删除。");
        }
    }

    private String uploadFile(HttpServletRequest request, String fname) throws IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartRequest.getFile(fname);
        String fileName = multipartFile.getOriginalFilename();
        String filePath = "";
        if(null != multipartFile && StringUtils.isNotEmpty(multipartFile.getOriginalFilename())) {
            JSONObject jobj = FileUploadClient.upFile(request, fileName, multipartFile.getInputStream());
            if(jobj.getString("responseMessage").equals("OK")) {
                filePath = jobj.getString("fpath");
            }
        }
        /*
        if (fileName.trim().length() > 0) {
            int pos = fileName.lastIndexOf(".");
            fileName = fileName.substring(pos);
            String fileDirPath = request.getSession().getServletContext().getRealPath("/");
            String path = fileDirPath.substring(0, fileDirPath.lastIndexOf(File.separator)) + CONFIG.UPLOAD_FILE_PATH;
            Date date = new Date();
            fileName = String.valueOf(date.getTime()) + fileName;
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            file = new File(path + "/" + fileName);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
                try {
                    if (fos != null) {
                        fos.close();
                    }
                    if (s != null) {
                        s.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            path = path.replaceAll("\\\\", "/");
            filePath = path;
        }
        */
        return filePath;
    }

    private String getItemForHtml(List<EvalItem> items, ServletContext sc) {
        String result = "", preHtml;
        String beizhu = "无";
        if (!items.equals(null) && items.size() > 0) {
            for (int i = 0; i < items.size(); i++) {
                preHtml = "<li class='item-table-list-li'>"
                        + "<div class='item-table-list-box'>"
                        + "<i class='fa fa-edit' style='display:none;' onclick=\'editItem(\"" + items.get(i).getEvalItem_id() + "\",\"" + items.get(i).getType() + "\")\'></i>"
                        + "<i class='fa fa-times' style='display:none;' id='" + items.get(i).getEvalItem_id() + "' onclick=\'deleteItem(\"" + items.get(i).getEvalItem_id() + "\")\'></i>"
                        + "<table class='table  table-responsive item-table-list' width='100%' border='0' cellspacing='0' cellpadding='0'>"
                        + "<tr>";
                String bDate = DateUtils.getYearMonth(items.get(i).getBeginDate());
                String eDate = DateUtils.getYearMonth(items.get(i).getEndDate());
                String level = CodeTableUtils.getValue(sc, "ryjb", items.get(i).getLevel());
                String grade = CodeTableUtils.getValue(sc, "hjdj", items.get(i).getGrade());
                level = StringUtils.isEmpty(level) ? "" : level;
                grade = StringUtils.isEmpty(grade) ? "" : grade;
                result += preHtml;
                result += "<td class='first_td'>" + bDate + "-" + eDate + "</td>";
                result += "<td>" + items.get(i).getName() + "</td>";
                result += "<td>" + level + grade + "</td>";
//                result += "<td><a target='_blank' href='" + sc.getContextPath() + "/jspc/zwpc/zwpc/downloadFile?itemId=" + items.get(i).getEvalItem_id() + "' class='table-tc-fj-open'  >附件</a></td>";
                if(StringUtils.isNotEmpty(items.get(i).getAttach())) {
                    String sourceURL = AttributeUtils.getAttributeContent(sc, "source_webapp_file_postion");
                    result += "<td><a target='_blank' href='" + sourceURL + items.get(i).getAttach() + "' class='table-tc-fj-open'  >附件</a></td>";
                }else {
                    result += "<td>&nbsp;&nbsp;</td>";
                }
                beizhu = items.get(i).getDescription();
                beizhu = StringUtils.isEmpty(beizhu) ? "无" : beizhu;
                if ("无".equals(beizhu)) {
                    result += "</tr></table><div class='exp_description'>备注：" + beizhu + "</div></li>";
                } else {
                    result += "</tr></table><div class='exp_description' title='" + beizhu + "'>备注：" + beizhu + "</div></li>";
                }
            }
        } else {
            result = "<div class='div_zanwu'>暂无</div>";
        }
        return result;
    }
}
