package com.dlshouwen.core.base.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dlshouwen.core.base.config.CONFIG;
import com.dlshouwen.core.base.dao.AssistDao;
import com.dlshouwen.core.base.extra.unique.utils.UniqueUtils;
import com.dlshouwen.core.base.http.FileUploadClient;
import com.dlshouwen.core.base.model.AjaxResponse;
import com.dlshouwen.core.base.model.OperationType;
import com.dlshouwen.core.base.model.SessionUser;
import com.dlshouwen.core.base.utils.LogUtils;
import com.dlshouwen.core.base.utils.SecurityUtils;
import com.dlshouwen.core.system.dao.UserDao;
import com.dlshouwen.core.system.model.Limit;
import com.dlshouwen.core.system.model.User;
import com.dlshouwen.core.system.model.UserAttr;
import java.io.FileOutputStream;
import java.io.InputStream;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 附属功能
 * @author 大连首闻科技有限公司
 * @version 2013-7-16 18:59:36
 */
@Controller
@RequestMapping("/core/base/assist")
public class AssistController {
	
	private String basePath = "core/base/assist/";
	
	private AssistDao dao;
        
        private UserDao userDao;
	
	@Resource(name="assistDao")
	public void setDao(AssistDao dao) {
		this.dao = dao;
	}
        
        @Resource(name="userDao")
        public void setUserDao(UserDao userDao) {
            this.userDao = userDao;
        }
        
        
	
	/**
	 * 跳转到设置用户信息页面
	 * @param request 请求对象
	 * @param model 反馈对象
	 * @return basePath + 'setUserInfo'
	 * @throws Exception
	 */
	@RequestMapping(value="/set_user", method=RequestMethod.GET)
	public String goSetUserPage(HttpServletRequest request, Model model) throws Exception {
//		获取用户编号
		SessionUser sessionUser = (SessionUser)request.getSession().getAttribute(CONFIG.SESSION_USER);
		String userId = sessionUser.getUser_id();
//		获取用户详细信息传递到前台
		User user = userDao.getUserById(userId);
		model.addAttribute("user", user);
//		记录操作日志
		LogUtils.updateOperationLog(request, OperationType.VISIT, "访问设置用户信息页面");
		return basePath + "setUser";
	}
	
	/**
	 * 执行设置用户信息
	 * @param user 被编辑的用户
	 * @param bindingResult 绑定的错误信息
	 * @param request 请求对象
	 * @return AJAX响应回执
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="/set_user", method=RequestMethod.POST)
	@ResponseBody
	public void setUser(@Valid User user, BindingResult bindingResult, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
//		定义AJAX响应回执
		AjaxResponse ajaxResponse = new AjaxResponse();
//		获取登录用户
		SessionUser sessionUser = (SessionUser)request.getSession().getAttribute(CONFIG.SESSION_USER);
//		设置用户编号
		user.setUser_id(sessionUser.getUser_id());
//		校验唯一
		if(!UniqueUtils.unique(dao, ajaxResponse, "用户编号已经被使用，请重新填写。", "USER_CODE_UNIQUE_FOR_EDIT", user.getUser_code(), user.getUser_id())){
//			记录操作日志
			LogUtils.updateOperationLog(request, OperationType.UPDATE, "设置用户（失败：用户编号已被使用），用户内码："+user.getUser_id()+"，用户编号："+user.getUser_code()+"，用户名称："+user.getUser_name());
			response.setContentType("text/html;charset=utf-8");
                        JSONObject obj = JSONObject.fromObject(ajaxResponse);
                        response.getWriter().write(obj.toString());
		}
//		如果发现错误则拼接错误信息执行回执
		if(bindingResult.hasErrors()){
			ajaxResponse.bindingResultHandler(bindingResult);
//			记录操作日志
			LogUtils.updateOperationLog(request, OperationType.UPDATE, "设置用户（失败：数据验证错误，详细信息："+AjaxResponse.getBindingResultMessage(bindingResult)+"）");
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
                    if(null != jobj && jobj.getString("responseMessage").equals("OK")) {
                        path = jobj.getString("fpath");
                    }
                }
                
                /*
                if(null != multipartFile && StringUtils.isNotEmpty(multipartFile.getOriginalFilename())) {
                    String fileName = multipartFile.getOriginalFilename();
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
                */
                if(null != path) {
                    user.setImgpath(path);
                }
                
//		设置编辑人、编辑时间
		user.setEditor(sessionUser.getUser_id());
		user.setEdit_time(new Date());
//		执行更新用户
		userDao.updateUser(user);
//		反馈AJAX响应
		ajaxResponse.setSuccess(true);
		ajaxResponse.setSuccessMessage("设置成功！");
//		记录操作日志
		LogUtils.updateOperationLog(request, OperationType.UPDATE, "设置用户，用户内码："+user.getUser_id()+"，用户编号："+user.getUser_code()+"，用户名称："+user.getUser_name());
		response.setContentType("text/html;charset=utf-8");
                JSONObject obj = JSONObject.fromObject(ajaxResponse);
                response.getWriter().write(obj.toString());
	}
	
	/**
	 * 跳转到参数设置页面
	 * @param request 请求对象
	 * @param model 反馈对象
	 * @return basePath + 'setUserAttr'
	 * @throws Exception
	 */
	@RequestMapping(value="/set_user_attr", method=RequestMethod.GET)
	public String goSetUserAttrPage(HttpServletRequest request, Model model) throws Exception {
//		获取用户编号
		SessionUser sessionUser = (SessionUser)request.getSession().getAttribute(CONFIG.SESSION_USER);
		String userId = sessionUser.getUser_id();
//		获取皮肤列表传递到前台
		List<String> skinList = new ArrayList<String>();
		File skinFolder = new File(request.getSession().getServletContext().getRealPath("/")+"/" + CONFIG.SKIN_ROOT_PATH);
		for(File skin : skinFolder.listFiles()){
			if(!skin.isDirectory())
				skinList.add(skin.getName());
		}
		model.addAttribute("skinList", skinList);
//		获取根目录传递到前台
		model.addAttribute("skinRootPath", CONFIG.SKIN_ROOT_PATH);
//		获取用户参数信息传递到前台
		UserAttr userAttr = dao.getUserAttrById(userId);
		model.addAttribute("userAttr", userAttr);
//		记录操作日志
		LogUtils.updateOperationLog(request, OperationType.VISIT, "访问个人参数设置页面");
		return basePath + "setUserAttr";
	}
	
	/**
	 * 执行参数设置
	 * @param request 请求对象
	 * @param response 响应对象
	 * @param userAttr 用户参数对象
	 * @return AJAX的响应信息
	 * @throws Exception
	 */
	@RequestMapping(value="/set_user_attr", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse setUserAttr(HttpServletRequest request, HttpServletResponse response, 
			@Valid UserAttr userAttr, BindingResult bindingResult) throws Exception {
//		定义AJAX响应回执
		AjaxResponse ajaxResponse = new AjaxResponse();
//		如果发现错误则拼接错误信息执行回执
		if(bindingResult.hasErrors()){
			ajaxResponse.bindingResultHandler(bindingResult);
//			记录操作日志
			LogUtils.updateOperationLog(request, OperationType.UPDATE, "设置用户参数（失败：数据验证错误，详细信息："+AjaxResponse.getBindingResultMessage(bindingResult)+"）");
			return ajaxResponse;
		}
//		获取用户编号
		SessionUser sessionUser = (SessionUser)request.getSession().getAttribute(CONFIG.SESSION_USER);
		String userId = sessionUser.getUser_id();
//		设置用户参数的用户编号为当前用户
		userAttr.setUser_id(userId);
//		执行更新用户参数
		int count = dao.updateUserAttr(userAttr);
//		如果更新影响的记录为0则执行新建用户参数
		if(count==0){
			dao.insertUserAttr(userAttr);
		}
//		更新SessionUser信息
		sessionUser.setUserAttr(userAttr);
		request.getSession().setAttribute(CONFIG.SESSION_USER, sessionUser);
//		设置皮肤缓存
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("skin_info")) {
					cookie.setValue(URLEncoder.encode(userAttr.getSkin_info(), "utf-8"));
					cookie.setPath("/");
					try{
						cookie.setMaxAge(60 * 60 * 24 * CONFIG.SYSTEM_LOGIN_USER_SKIN_INFO_COOKIE_AVAILABLE_DAY);
					}catch(Exception e){
						cookie.setMaxAge(60 * 60 * 24 * 30);
					}
					response.addCookie(cookie);
					break;
				}
				if (cookie.getName().equals("is_background_float")) {
					cookie.setValue(URLEncoder.encode(userAttr.getIs_background_float(), "utf-8"));
					cookie.setPath("/");
					try{
						cookie.setMaxAge(60 * 60 * 24 * CONFIG.SYSTEM_IS_BACKGROUND_FLOAT_COOKIE_AVAILABLE_DAY);
					}catch(Exception e){
						cookie.setMaxAge(60 * 60 * 24 * 30);
					}
					response.addCookie(cookie);
					break;
				}
				if (cookie.getName().equals("background_float_speed")) {
					cookie.setValue(URLEncoder.encode(String.valueOf(sessionUser.getUserAttr().getBackground_float_speed()), "utf-8"));
					cookie.setPath("/");
					try{
						cookie.setMaxAge(60 * 60 * 24 * CONFIG.SYSTEM_BACKGROUND_FLOAT_SPEED_COOKIE_AVAILABLE_DAY);
					}catch(Exception e){
						cookie.setMaxAge(60 * 60 * 24 * 30);
					}
					response.addCookie(cookie);
					break;
				}
			}
		}
//		反馈AJAX响应
		ajaxResponse.setSuccess(true);
		ajaxResponse.setSuccessMessage("参数设置成功！");
//		记录操作日志
		LogUtils.updateOperationLog(request, OperationType.UPDATE, "设置用户参数，用户内码："+userAttr.getUser_id());
		return ajaxResponse;
	}
	
	/**
	 * 跳转到用户快捷方式设置页面
	 * @param request 请求对象
	 * @param model 反馈对象
	 * @return basePath + 'setUserShortcutLimit'
	 * @throws Exception
	 */
	@RequestMapping(value="/set_user_shortcut_limit", method=RequestMethod.GET)
	public String goSetUserShortcutLimitPage(HttpServletRequest request, Model model) throws Exception {
//		获取用户编号
		SessionUser sessionUser = (SessionUser)request.getSession().getAttribute(CONFIG.SESSION_USER);
		String userId = sessionUser.getUser_id();
//		获取用户的快捷方式菜单
		List<Map<String, Object>> shortcutLimitList = dao.getShortcutLimitList(userId);
		model.addAttribute("shortcutLimitList", JSONArray.fromObject(shortcutLimitList).toString());
//		记录操作日志
		LogUtils.updateOperationLog(request, OperationType.VISIT, "访问用户快捷方式设置页面");
		return basePath + "setUserShortcutLimit";
	}
	
	/**
	 * 执行用户快捷方式设置
	 * @param request 请求对象
	 * @param shortcutLimitIds 快捷方式菜单列表
	 * @return AJAX的响应信息
	 * @throws Exception
	 */
	@RequestMapping(value="/set_user_shortcut_limit", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse setUserShortcutLimit(HttpServletRequest request, String shortcutLimitIds) throws Exception {
//		定义AJAX响应回执
		AjaxResponse ajaxResponse = new AjaxResponse();
//		获取用户编号
		SessionUser sessionUser = (SessionUser)request.getSession().getAttribute(CONFIG.SESSION_USER);
		String userId = sessionUser.getUser_id();
//		更新用户快捷方式菜单
		dao.updateUserShortcutLimit(userId, shortcutLimitIds);
//		更新sessionUser的快捷方式信息
		List<Limit> shortcutList = dao.getShortcutList(userId);
		sessionUser.setShortcutList(shortcutList);
		request.getSession().setAttribute(CONFIG.SESSION_USER, sessionUser);
//		反馈AJAX响应
		ajaxResponse.setSuccess(true);
		ajaxResponse.setSuccessMessage("用户快捷方式设置成功！");
//		记录操作日志
		LogUtils.updateOperationLog(request, OperationType.UPDATE, "设置用户快捷方式，用户内码："+userId);
		return ajaxResponse;
	}
	
	/**
	 * 跳转到编辑密码页面
	 * @param request 请求对象
	 * @param model 反馈对象
	 * @return basePath + 'changePassword'
	 * @throws Exception
	 */
	@RequestMapping(value="/change_password", method=RequestMethod.GET)
	public String goChangePasswordPage(HttpServletRequest request, Model model) throws Exception {
//		记录操作日志
		LogUtils.updateOperationLog(request, OperationType.VISIT, "访问编辑密码页面");
		return basePath + "changePassword";
	}
	
	/**
	 * 执行编辑密码
	 * @param request 请求对象
	 * @param old_password 原密码
	 * @param new_password 新密码
	 * @return AJAX响应对象
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="/change_password", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse changePassword(HttpServletRequest request, String oldPassword, String newPassword) throws Exception {
//		定义AJAX响应信息
		AjaxResponse ajaxResponse = new AjaxResponse();
//		获取用户编号
		SessionUser sessionUser = (SessionUser)request.getSession().getAttribute(CONFIG.SESSION_USER);
		String userId = sessionUser.getUser_id();
//		如果用户编号为demo，则禁止修改
		if("demo".equals(userId)){
			ajaxResponse.setError(true);
			ajaxResponse.setErrorMessage("示例用户不允许修改密码。");
//			记录操作日志
			LogUtils.updateOperationLog(request, OperationType.UPDATE, "示例用户不允许修改密码。");
			return ajaxResponse;
		}
//		判断原密码是否为空
		if("".equals(oldPassword.trim())){
			ajaxResponse.setWarning(true);
			ajaxResponse.setWarningMessage("请输入原密码。");
//			记录操作日志
			LogUtils.updateOperationLog(request, OperationType.UPDATE, "编辑密码（失败：未输入原密码）");
			return ajaxResponse;
		}
//		判断新密码是否为空
		if("".equals(newPassword.trim())){
			ajaxResponse.setWarning(true);
			ajaxResponse.setWarningMessage("请输入新密码。");
//			记录操作日志
			LogUtils.updateOperationLog(request, OperationType.UPDATE, "编辑密码（失败：未输入新密码）");
			return ajaxResponse;
		}
//		密码转义密文
		oldPassword = SecurityUtils.getMD5(oldPassword);
		newPassword = SecurityUtils.getMD5(newPassword);
//		获取是否匹配原密码
		boolean isOldPassword = dao.isOldPassword(userId, oldPassword);
//		如果不匹配则直接弹出错误
		if(!isOldPassword){
			ajaxResponse.setError(true);
			ajaxResponse.setErrorMessage("您输入的原始密码有误，请重新输入。");
//			记录操作日志
			LogUtils.updateOperationLog(request, OperationType.UPDATE, "编辑密码（失败：原始密码输入错误）");
			return ajaxResponse;
		}
//		更新密码
		dao.changePassword(userId, newPassword);
//		反馈成功信息
		ajaxResponse.setSuccess(true);
		ajaxResponse.setSuccessMessage("密码编辑成功。");
//		记录操作日志
		LogUtils.updateOperationLog(request, OperationType.UPDATE, "编辑密码");
		return ajaxResponse;
	}
	
}
