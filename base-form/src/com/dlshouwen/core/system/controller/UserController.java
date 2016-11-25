package com.dlshouwen.core.system.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dlshouwen.core.base.config.CONFIG;
import com.dlshouwen.core.base.extra.grid.model.Pager;
import com.dlshouwen.core.base.extra.grid.utils.PagerPropertyUtils;
import com.dlshouwen.core.base.extra.unique.utils.UniqueUtils;
import com.dlshouwen.core.base.http.FileUploadClient;
import com.dlshouwen.core.base.model.AjaxResponse;
import com.dlshouwen.core.base.model.OperationType;
import com.dlshouwen.core.base.model.SessionUser;
import com.dlshouwen.core.base.patch.DateJsonValueProcessor;
import com.dlshouwen.core.base.utils.AttributeUtils;
import com.dlshouwen.core.base.utils.CodeTableUtils;
import com.dlshouwen.core.base.utils.ExcelUtils;
import com.dlshouwen.core.base.utils.GUID;
import com.dlshouwen.core.base.utils.LogUtils;
import com.dlshouwen.core.base.utils.MyFileUtils;
import com.dlshouwen.core.base.utils.SecurityUtils;
import com.dlshouwen.core.system.dao.SecUserDao;
import com.dlshouwen.core.system.dao.UserDao;
import com.dlshouwen.core.system.model.User;
import com.dlshouwen.core.system.model.UserAttr;
import java.util.HashMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 用户管理
 *
 * @author 大连首闻科技有限公司
 * @version 2013-7-23 13:29:27
 */
@Controller
@RequestMapping("/core/system/user")
public class UserController {

    /**
     * 功能根路径
     */
    private String basePath = "core/system/user/";

    /**
     * 数据操作对象
     */
    private UserDao dao;
    
    /**
     * 注入数据操作对象
     *
     * @param dao 数据操作对象
     */
    @Resource(name = "userDao")
    public void setDao(UserDao dao) {
        this.dao = dao;
    }
    
    private SecUserDao secUserDao;
    
    @Resource(name = "secUserDao")
    public void setSecUserDao(SecUserDao secUserDao) {
        this.secUserDao = secUserDao;
    }
    
    /**
     * 跳转到用户管理页面
     *
     * @param request 请求对象
     * @return basePath + 'userList'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String goUserPage(HttpServletRequest request) throws Exception {
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问用户管理页面");
        return basePath + "userList";
    }

    /**
     * 获取用户列表数据
     *
     * @param gridPager 表格分页信息
     * @param request 请求对象
     * @param response 响应对象
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public void getUserList(String gridPager, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		映射Pager对象
        Pager pager = PagerPropertyUtils.copy(JSONObject.fromObject(gridPager));
//		获取用户列表数据
        dao.getUserList(pager, request, response);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.SEARCH, "获取用户管理页面中的用户列表数据");
    }

    /**
     * 跳转到添加用户页面
     *
     * @param request 请求对象
     * @param model 反馈对象
     * @return basePath + 'addUser'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addUser(HttpServletRequest request, Model model) throws Exception {
//		新增用户对象
        User user = new User();
//		设置默认男、默认有效、普通用户
        user.setSex("1");
        user.setValid_type("1");
        user.setIdentity("0");
        model.addAttribute("user", user);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问新增用户页面");
        return basePath + "addUser";
    }

    /**
     * 执行新增用户
     *
     * @param user 被新增的用户
     * @param request 请求对象
     * @param bindingResult 绑定的错误信息
     * @param response
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void addUser(@Valid User user, BindingResult bindingResult,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
//		定义AJAX响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
//		校验唯一
        if (!UniqueUtils.unique(dao, ajaxResponse, "用户编号已经被使用，请重新填写。", "USER_CODE_UNIQUE_FOR_ADD", user.getUser_code())) {
//			记录操作日志
            LogUtils.updateOperationLog(request, OperationType.INSERT, "新增用户（失败：用户编号已被使用，用户编号：" + user.getUser_code() + "，用户名称：" + user.getUser_name() + "）");
            response.setContentType("text/html;charset=utf-8");
            JSONObject obj = JSONObject.fromObject(ajaxResponse);
            response.getWriter().write(obj.toString());
        }
//		如果发现错误则拼接错误信息执行回执
        if (bindingResult.hasErrors()) {
            ajaxResponse.bindingResultHandler(bindingResult);
//			记录操作日志
            LogUtils.updateOperationLog(request, OperationType.INSERT, "新增用户（失败：数据验证错误，用户编号：" + user.getUser_code() + "，用户名称：" + user.getUser_name() + "）");
            response.setContentType("text/html;charset=utf-8");
            JSONObject obj = JSONObject.fromObject(ajaxResponse);
            response.getWriter().write(obj.toString());
        }

        //获取用户上传照片
        String path = null;
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartRequest.getFile("picture");
        if(null != multipartFile && StringUtils.isNotEmpty(multipartFile.getOriginalFilename())) {
            JSONObject jobj = FileUploadClient.upFile(request, multipartFile.getOriginalFilename(), multipartFile.getInputStream());
            if(jobj.getString("responseMessage").equals("OK")) {
                path = jobj.getString("fpath");
            }
        }
        
        /*应用服务器上存储图片方式
        if (null != multipartFile) {
            String fileName = multipartFile.getOriginalFilename();
            if (!"".equals(fileName)) {
                int pos = fileName.lastIndexOf(".");
                fileName = fileName.substring(pos);
                String fileDirPath = request.getSession().getServletContext().getRealPath("/");
                path = fileDirPath.substring(0, fileDirPath.lastIndexOf(File.separator)) + CONFIG.UPLOAD_PIC_PATH;
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
            }
        }
        */

//              添加用户图片
        user.setImgpath(path);
//		添加用户的编号
        user.setUser_id(new GUID().toString());
//		获取初始密码
        String initial_user_password = AttributeUtils.getAttributeContent(request.getServletContext(), "initial_user_password");
//		初始密码加密
        String password = SecurityUtils.getMD5(initial_user_password);
//		设置初始密码
        user.setPassword(password);
//		获取登录用户
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
//		设置创建人、创建时间、编辑人、编辑时间
        user.setCreator(sessionUser.getUser_id());
        user.setCreate_time(new Date());
        user.setEditor(sessionUser.getUser_id());
        user.setEdit_time(new Date());
//		执行新增用户
        dao.insertUser(user);
//		设置用户默认参数
        UserAttr userAttr = new UserAttr();
        userAttr.setUser_id(user.getUser_id());
        userAttr.setSkin_info(AttributeUtils.getAttributeContent(request.getServletContext(), "default_skin_info"));
        userAttr.setIs_show_shortcut(AttributeUtils.getAttributeContent(request.getServletContext(), "default_is_show_shortcut"));
        userAttr.setIs_background_float(AttributeUtils.getAttributeContent(request.getServletContext(), "default_is_background_float"));
        userAttr.setBackground_float_speed(Integer.parseInt(AttributeUtils.getAttributeContent(request.getServletContext(), "default_background_float_speed")));
        dao.insertUserAttr(userAttr);
//		设置用户默认快捷方式
        dao.setUserDefaultShortcutLimit(user.getUser_id());
//		执行AJAX响应回执
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("新增用户成功！");
        Map map = new HashMap();
        map.put("URL", "/core/system/user");
        ajaxResponse.setExtParam(map);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.INSERT, "新增用户，用户内码：" + user.getUser_id() + "，用户编号：" + user.getUser_code() + "，用户名称：" + user.getUser_name());
        response.setContentType("text/html;charset=utf-8");
        JSONObject obj = JSONObject.fromObject(ajaxResponse);
        response.getWriter().write(obj.toString());
    }

    /**
     * 跳转到编辑用户页面
     *
     * @param userId 用户编号
     * @param request 请求对象
     * @param model 反馈对象
     * @return basePath + 'editUser'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/{userId}/edit", method = RequestMethod.GET)
    public String editUser(@PathVariable String userId, HttpServletRequest request, Model model) throws Exception {
//		获取用户详细信息
        User user = dao.getUserById(userId);
//		将用户放置在反馈对象中
        model.addAttribute("user", user);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问编辑用户页面，用户编号：" + user.getUser_code() + "，用户名称：" + user.getUser_name());
        return basePath + "editUser";
    }

    /**
     * 执行编辑用户
     *
     * @param user 被编辑的用户
     * @param bindingResult 绑定的错误信息
     * @param request 请求对象
     * @return AJAX响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public void editUser(@Valid User user, BindingResult bindingResult,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
//		定义AJAX响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
//		校验唯一
        if (!UniqueUtils.unique(dao, ajaxResponse, "用户编号已经被使用，请重新填写。", "USER_CODE_UNIQUE_FOR_EDIT", user.getUser_code(), user.getUser_id())) {
//			记录操作日志
            LogUtils.updateOperationLog(request, OperationType.UPDATE, "编辑用户（失败：用户编号已被使用，用户内码：" + user.getUser_id() + "，用户编号：" + user.getUser_code() + "，用户名称：" + user.getUser_name() + "）");
            response.setContentType("text/html;charset=utf-8");
            JSONObject obj = JSONObject.fromObject(ajaxResponse);
            response.getWriter().write(obj.toString());
        }
//		如果发现错误则拼接错误信息执行回执
        if (bindingResult.hasErrors()) {
            ajaxResponse.bindingResultHandler(bindingResult);
//			记录操作日志
            LogUtils.updateOperationLog(request, OperationType.UPDATE, "编辑用户（失败：数据验证错误，用户内码：" + user.getUser_id() + "，用户编号：" + user.getUser_code() + "，用户名称：" + user.getUser_name() + "）");
            response.setContentType("text/html;charset=utf-8");
            JSONObject obj = JSONObject.fromObject(ajaxResponse);
            response.getWriter().write(obj.toString());
        }
//		获取登录用户
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
//		设置编辑人、编辑时间
        user.setEditor(sessionUser.getUser_id());
        user.setEdit_time(new Date());

        //获取用户上传照片
        String path = null;
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartRequest.getFile("picture");
        if(null != multipartFile && StringUtils.isNotEmpty(multipartFile.getOriginalFilename())) {
            JSONObject jobj = FileUploadClient.upFile(request, multipartFile.getOriginalFilename(), multipartFile.getInputStream());
            if(null != jobj && jobj.getString("responseMessage").equals("OK")) {
                path = jobj.getString("fpath");
            }
        }
        /*
        if (null != multipartFile) {
            String fileName = multipartFile.getOriginalFilename();
            if (!"".equals(fileName)) {
                int pos = fileName.lastIndexOf(".");
                fileName = fileName.substring(pos);
                String fileDirPath = request.getSession().getServletContext().getRealPath("/");
                path = fileDirPath.substring(0, fileDirPath.lastIndexOf(File.separator)) + CONFIG.UPLOAD_PIC_PATH;
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
            }
        }
        */
        if (null != path) {
            user.setImgpath(path);
        }

//		执行更新用户
        dao.updateUser(user);
//		反馈AJAX响应
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("编辑用户成功！");
        Map map = new HashMap();
        map.put("URL", "/core/system/user");
        ajaxResponse.setExtParam(map);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.UPDATE, "编辑用户，用户内码：" + user.getUser_id() + "，用户编号：" + user.getUser_code() + "，用户名称：" + user.getUser_name());
        response.setContentType("text/html;charset=utf-8");
        JSONObject obj = JSONObject.fromObject(ajaxResponse);
        response.getWriter().write(obj.toString());
    }

    /**
     * 执行删除用户
     *
     * @param userIds 要被删除的用户编号列表
     * @param request 请求对象
     * @return AJAX响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse deleteUser(String userIds, HttpServletRequest request) throws Exception {
//		定义新的用户列表
        StringBuffer deleteUserIds = new StringBuffer();
//		映射为数组类型
        int totalCount = userIds.split(",").length;
        int isBaseUserCount = 0;
//		获取基础用户的编号列表
        String base_user_ids = AttributeUtils.getAttributeContent(request.getServletContext(), "base_user_ids");
        List<String> baseUserIdList = Arrays.asList(base_user_ids.split(","));
//		遍历数组
        for (String userId : userIds.split(",")) {
//			是否为内置用户
            if (baseUserIdList.contains(userId)) {
                isBaseUserCount++;
                continue;
            }
//			如果不为基本用户则添加
            deleteUserIds.append(userId).append(",");
        }
//		执行删除用户
        dao.deleteUser(deleteUserIds.toString());
//		提示成功
//		处理响应信息
        StringBuffer message = new StringBuffer();
        message.append("本次共执行").append(totalCount).append("条记录，其中：<br /><br />");
        message.append("　　成功删除用户").append(totalCount - isBaseUserCount).append("条。<br />");
        if (isBaseUserCount > 0) {
            message.append("　　因为是系统内置用户无法删除的记录共").append(isBaseUserCount).append("条。<br />");
        }
//		执行响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage(message.toString());

//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.DELETE, "删除用户，删除的用户：" + deleteUserIds.toString() + "，提示信息：" + message.toString());
        return ajaxResponse;
    }

    /**
     * 执行编辑用户密码
     *
     * @param userId 要被编辑的用户编号
     * @param request 请求对象
     * @return AJAX响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/reset_password", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse resetPassword(String userId, HttpServletRequest request) throws Exception {
//		获取初始密码
        String initial_user_password = AttributeUtils.getAttributeContent(request.getServletContext(), "initial_user_password");
//		初始密码加密
        String password = SecurityUtils.getMD5(initial_user_password);
//		重置用户密码
        dao.resetPassword(userId, password);
//		执行响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("密码重置成功！");
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.UPDATE, "重置用户密码，用户内码：" + userId);
        return ajaxResponse;
    }

    /**
     * 跳转到设置角色页面
     *
     * @param userId 需要被设置角色的用户编号
     * @param request 请求对象
     * @param model 反馈对象
     * @return basePath + 'setRole'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/{userId}/set_role", method = RequestMethod.GET)
    public String setRole(@PathVariable String userId, HttpServletRequest request, Model model) throws Exception {
//		将用户编号设置到反馈对象
        model.addAttribute("userId", userId);
//		获取用户对象传递到前台
        User user = dao.getUserById(userId);
        model.addAttribute("user", user);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "跳转到设置用户关系角色页面，用户内码：" + userId);
        return basePath + "setRole";
    }

    /**
     * 获取用户关系的角色数据
     *
     * @param gridPager 表格分页信息
     * @param userId 用户编号
     * @param request 请求对象
     * @param response 响应对象
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/{userId}/set_role/list", method = RequestMethod.POST)
    public void getUserRoleList(@PathVariable String userId, String gridPager,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
//		映射Pager对象
        Pager pager = PagerPropertyUtils.copy(JSONObject.fromObject(gridPager));
//		获取用户关系角色中的角色列表数据
        dao.getUserRoleList(pager, request, response, userId);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.SEARCH, "获取设置用户关系角色页面中的角色列表数据，用户内码：" + userId);
    }

    /**
     * 执行设置角色
     *
     * @param userId 要被设置的用户编号
     * @param roleIds 需要被设置的角色编号列表
     * @param request 请求对象
     * @return AJAX响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/set_role", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public AjaxResponse setRole(String userId, String addRoleIds, String deleteRoleIds, HttpServletRequest request) throws Exception {
//		如果添加的用户列表不为空
        if (addRoleIds != null && !"".equals(addRoleIds)) {
//			删除关系
            dao.deleteUserRoleRelation(userId, addRoleIds);
//			添加关系
            dao.insertUserRoleRelation(userId, addRoleIds);
        }
//		如果删除的用户列表不为空
        if (deleteRoleIds != null && !"".equals(deleteRoleIds)) {
//			删除关系
            dao.deleteUserRoleRelation(userId, deleteRoleIds);
        }
//		执行响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("配置角色成功！");
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.UPDATE, "设置用户关系角色，用户内码：" + userId);
        return ajaxResponse;
    }

    /**
     * 跳转到设置用户参数页面
     *
     * @param userId 需要被设置用户参数的用户编号
     * @param request 请求对象
     * @param model 反馈对象
     * @return basePath + 'setUserAttr'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/{userId}/set_user_attr", method = RequestMethod.GET)
    public String setUserAttr(@PathVariable String userId, HttpServletRequest request, Model model) throws Exception {
//		将用户编号设置到反馈对象
        model.addAttribute("userId", userId);
//		获取用户参数信息并传递到前台
        UserAttr userAttr = dao.getUserAttrById(userId);
        if (userAttr == null) {
            userAttr = new UserAttr();
            userAttr.setUser_id(userId);
        }
        model.addAttribute("userAttr", userAttr);
//		获取皮肤列表传递到前台
        List<String> skinList = new ArrayList<String>();
        File skinFolder = new File(request.getSession().getServletContext().getRealPath("/") + "/" + CONFIG.SKIN_ROOT_PATH);
        for (File skin : skinFolder.listFiles()) {
            if (!skin.isDirectory()) {
                skinList.add(skin.getName());
            }
        }
        model.addAttribute("skinList", skinList);
//		获取根目录传递到前台
        model.addAttribute("skinRootPath", CONFIG.SKIN_ROOT_PATH);
//		获取用户对象传递到前台
        User user = dao.getUserById(userId);
        model.addAttribute("user", user);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "跳转到设置用户参数页面，用户内码：" + userId);
        return basePath + "setUserAttr";
    }

    /**
     * 执行设置用户参数
     *
     * @param request 请求对象
     * @param userAttr 用户参数
     * @param bindingResult 绑定的错误信息
     * @return AJAX响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/set_user_attr", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse setUserAttr(HttpServletRequest request,
            @Valid UserAttr userAttr, BindingResult bindingResult) throws Exception {
//		定义AJAX响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
//		如果发现错误则拼接错误信息执行回执
        if (bindingResult.hasErrors()) {
            ajaxResponse.bindingResultHandler(bindingResult);
//			记录操作日志
            LogUtils.updateOperationLog(request, OperationType.UPDATE, "设置用户参数（失败：数据验证错误，详细信息：" + AjaxResponse.getBindingResultMessage(bindingResult) + "）");
            return ajaxResponse;
        }
//		执行更新用户参数
        int count = dao.updateUserAttr(userAttr);
//		如果更新影响的记录为0则执行新建用户参数
        if (count == 0) {
            dao.insertUserAttr(userAttr);
        }
//		反馈AJAX响应
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("参数设置成功！");
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.UPDATE, "设置用户参数，用户内码：" + userAttr.getUser_id());
        return ajaxResponse;
    }

    /**
     * 跳转到设置用户快捷方式页面
     *
     * @param userId 需要被设置用户快捷方式的用户编号
     * @param request 请求对象
     * @param model 反馈对象
     * @return basePath + 'setUserShortcutLimit'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/{userId}/set_user_shortcut_limit", method = RequestMethod.GET)
    public String setUserShortcutLimit(@PathVariable String userId, HttpServletRequest request, Model model) throws Exception {
//		将用户编号设置到反馈对象
        model.addAttribute("userId", userId);
//		获取用户的快捷方式菜单
        List<Map<String, Object>> shortcutLimitList = dao.getShortcutLimitList(userId);
        model.addAttribute("shortcutLimitList", JSONArray.fromObject(shortcutLimitList).toString());
//		获取用户对象传递到前台
        User user = dao.getUserById(userId);
        model.addAttribute("user", user);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "跳转到设置用户快捷方式页面，用户内码：" + userId);
        return basePath + "setUserShortcutLimit";
    }

    /**
     * 执行用户快捷方式设置
     *
     * @param request 请求对象
     * @param userId 用户编号
     * @param shortcutLimitIds 快捷方式菜单列表
     * @return AJAX的响应信息
     * @throws Exception
     */
    @RequestMapping(value = "/set_user_shortcut_limit", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse setUserShortcutLimit(HttpServletRequest request, String userId, String shortcutLimitIds) throws Exception {
//		定义AJAX响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
//		更新用户快捷方式菜单
        dao.updateUserShortcutLimit(userId, shortcutLimitIds);
//		反馈AJAX响应
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("用户快捷方式设置成功！");
        //用‘URL’字段通知前段页面的跳转路径
        Map map = new HashMap();
        map.put("URL", "core/system/user/");
        ajaxResponse.setExtParam(map);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.UPDATE, "设置用户快捷方式，用户内码：" + userId);
        return ajaxResponse;
    }

    /**
     * 选择用户
     *
     * @param type 选择类别
     * @param request 请求对象
     * @param model 反馈对象
     * @return basePath + 'selectUser'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/select/{type}", method = RequestMethod.GET)
    public String selectUser(@PathVariable String type, HttpServletRequest request, Model model) throws Exception {
//		传递类型到前台
        model.addAttribute("type", type);
//		获取所有用户列表映射为树对象传递到前台
        List<Map<String, Object>> userList = dao.getSelectUserDataByDept();
        for(Map obj:userList) {
            String objType = (String)obj.get("type");
            if(!"user".equals(objType)) {
                obj.put("nocheck", true);
            }
        }
//		json-lib 日期补丁
        JsonValueProcessor jsonProcessor = new DateJsonValueProcessor();
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(java.sql.Date.class, jsonProcessor);
        model.addAttribute("userTree", JSONArray.fromObject(userList, jsonConfig).toString());
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问选择用户页面，选择类型：" + type);
        return basePath + "selectUser";
    }

    @RequestMapping(value = "/import_user_page", method = RequestMethod.GET)
    public String import_user_page(HttpServletRequest request, Model model) throws Exception {
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问导入用户页面");
        return basePath + "import_user";
    }

    /**
     * 下载导入用户模板
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/getTemplet", method = RequestMethod.GET)
    public void getTemplet(HttpServletRequest request, HttpServletResponse response) throws Exception {

        ExcelUtils.exportExcel(request, response, User.class, "导入用户模板");
    }

    /**
     * 导入用户数据
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/importUserData", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse importUserData(HttpServletRequest request, HttpServletResponse response) throws Exception {  
//      定义AJAX响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        String recordNumberStr = AttributeUtils.getAttributeContent(request.getServletContext(), "exl_import_record_number");
        int recordNumber = 500;
        try {
            recordNumber = Integer.parseInt(recordNumberStr);
        } catch (Exception e) {
            recordNumber = 500;
        }

        String filePath = MyFileUtils.uploadFile(request, "upfile", null);
        if(StringUtils.isNotEmpty(filePath)) {
            List uList = ExcelUtils.readExlData(filePath, null,User.class ,"编号","证件号");
            if(null!=uList && uList.size()>0) {
                String msg = insertUsers(request, uList);
//              执行AJAX响应回执
                ajaxResponse.setSuccess(true);
                ajaxResponse.setSuccessMessage("导入成功！"+msg);  
            }
        } else {
//          执行AJAX响应回执
            ajaxResponse.setError(true);
            ajaxResponse.setErrorMessage("导入失败！");
        }

        return ajaxResponse;
    }
    
    private String insertUsers(HttpServletRequest request, List uList) throws Exception {
        //需要插入的用户数据
        List<User> orgUsers = new ArrayList<User>();
        //需要更新的用户数据
        List<User> oldUsers = new ArrayList<User>();
        
        for(Object obj:uList) {
            User userTemp = (User)obj;

            SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
//	    设置编辑人、编辑时间
            userTemp.setEditor(sessionUser.getUser_id());
            userTemp.setEdit_time(new Date());
            //判断用户编号唯一性，唯一时进行插入操作，否则进行更新操作
            User u = dao.getUserByCode(userTemp.getUser_code());
            if(null != u) {
                userTemp.setUser_id(u.getUser_id());
                userTemp.setValid_type(u.getValid_type());
                userTemp.setPassword(u.getPassword());
                userTemp.setIdentity(u.getIdentity());
                userTemp.setCreator(u.getCreator());
                userTemp.setCreate_time(u.getCreate_time());
                oldUsers.add(userTemp);
            }else {
//              设置id
                userTemp.setUser_id(new GUID().toString());
//              获取并设置初始密码
                String initial_user_password = AttributeUtils.getAttributeContent(request.getServletContext(), "initial_user_password");
                String password = SecurityUtils.getMD5(initial_user_password);
                userTemp.setPassword(password);                
//              默认用户有效、身份为普通用户
                userTemp.setValid_type("1"); 
                userTemp.setIdentity("0");
//              设置创建人、创建时间
                userTemp.setCreator(sessionUser.getUser_id());
                userTemp.setCreate_time(new Date());                 
                orgUsers.add(userTemp);
            }
        }
        
        String msg = "共导入记录"+uList.size()+"条";
        int insertNumber = 0, updateNumber = 0;
        if(orgUsers.size() > 0) {
            //插入数据表
            int[]okNumber = dao.batchInsertUsers(orgUsers);
            //插入用户附属信息数据
            List<UserAttr> ua = getUAttrList(orgUsers, request);
            if(ua.size() > 0) {
                dao.batchInsertUserAttr(ua);
            }
            insertNumber = okNumber.length;
            msg += ",其中新增记录"+insertNumber+"条";
        }
        if(oldUsers.size() > 0) {
            //更新数据
            int[] okNumber = dao.batchUpdateUsrs(oldUsers);
            updateNumber = okNumber.length;
            msg += ",更新记录"+updateNumber+"条";
        }
        msg += "。";
        
        return msg;
    }

    private List<UserAttr> getUAttrList(List<User> uList, HttpServletRequest request) {
        List<UserAttr> UAList = new ArrayList<UserAttr>();
        
        if(null != uList && uList.size() > 0) {
            for(User user:uList) {
                UserAttr userAttr = new UserAttr();
                userAttr.setUser_id(user.getUser_id());
                userAttr.setSkin_info(AttributeUtils.getAttributeContent(request.getServletContext(), "default_skin_info"));
                userAttr.setIs_show_shortcut(AttributeUtils.getAttributeContent(request.getServletContext(), "default_is_show_shortcut"));
                userAttr.setIs_background_float(AttributeUtils.getAttributeContent(request.getServletContext(), "default_is_background_float"));
                userAttr.setBackground_float_speed(Integer.parseInt(AttributeUtils.getAttributeContent(request.getServletContext(), "default_background_float_speed")));
                
                UAList.add(userAttr);
            }
        }
        
        return UAList;
    }
    
    /**
     * 同步用户数据到权限库
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/powerSynchronization", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse powerSynchronization(HttpServletRequest request, HttpServletResponse response) throws Exception {
        AjaxResponse ajaxResp = new AjaxResponse();
        try {
            List<User> ulist = dao.getUserList();
            if(null != ulist && ulist.size() > 0) {
                //更新和插入记录数
                int freshNumber = 0, insertNumber = 0;
                for(User user:ulist) {
                    String userType = "wzgl";
                    if(StringUtils.isNotEmpty(user.getIdentity())) {
                        userType += "-" + CodeTableUtils.getValue(CONFIG.SERVLET_CONTEXT, "user_identity", user.getIdentity());
                    }
                    if(!secUserDao.queryUserByLoginName(user.getUser_code())) {
                        initUser(user, userType, request);
                        insertNumber++;
                    }else {
                        initUpdateUser(user, userType, request);
                        freshNumber++;
                    }
                }
                ajaxResp.setSuccess(true);
                ajaxResp.setSuccessMessage("同步成功！新增数据"+insertNumber+"条，更新数据"+freshNumber+"条。");
            }else {
                ajaxResp.setSuccess(true);
                ajaxResp.setSuccessMessage("没有用户，未进行同步操作！");
            }
        }catch(Exception e) {
            e.printStackTrace();
            ajaxResp.setError(true);
            ajaxResp.setErrorMessage("同步失败");
        }
        return ajaxResp;
    }
    
    public void initUser(User user, String code, HttpServletRequest request) throws Exception{
        Map map = new HashMap();
        map.put("id", user.getUser_id()); 
        map.put("name", user.getUser_name());
        map.put("loginname", user.getUser_code());
        String initial_user_password = AttributeUtils.getAttributeContent(request.getServletContext(), "initial_user_password");
        if(StringUtils.isEmpty(initial_user_password)) {
            initial_user_password = "123456";
        }
        map.put("password", SecurityUtils.getMD5(initial_user_password+"{loginname}"));
        map.put("isactive", 1);
        map.put("note", user.getRemark());
        map.put("editman", user.getEditor());
        map.put("edittime", new Date());
        map.put("code", code);
        map.put("phone", user.getPhone());
        map.put("email", user.getEmail());
        secUserDao.insertUser(map);
    }
    
    public void initUpdateUser(User user, String code, HttpServletRequest request) throws Exception{
        Map map = new HashMap();
        map.put("id", user.getUser_id());
        map.put("name", user.getUser_name());
        map.put("loginname", user.getUser_code());
        map.put("isactive", 1);
        map.put("note", user.getRemark());
        map.put("editman", user.getEditor());
        map.put("edittime", new Date());
        map.put("code", code);
        map.put("phone", user.getPhone());
        map.put("email", user.getEmail());
        secUserDao.updateUser(map);
    }
}
