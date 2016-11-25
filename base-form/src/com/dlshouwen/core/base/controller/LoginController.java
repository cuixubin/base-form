package com.dlshouwen.core.base.controller;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dlshouwen.core.base.config.CONFIG;
import com.dlshouwen.core.base.dao.LoginDao;
import com.dlshouwen.core.base.model.LoginUser;
import com.dlshouwen.core.base.model.OperationType;
import com.dlshouwen.core.base.model.SessionUser;
import com.dlshouwen.core.base.utils.AttributeUtils;
import com.dlshouwen.core.base.utils.DateUtils;
import com.dlshouwen.core.base.utils.GUID;
import com.dlshouwen.core.base.utils.LogUtils;
import com.dlshouwen.core.base.utils.SecurityUtils;
import com.dlshouwen.core.log.model.LoginLog;
import com.dlshouwen.core.system.model.Limit;
import com.dlshouwen.core.system.model.User;
import com.dlshouwen.core.system.model.UserAttr;
import org.jasig.cas.client.util.AssertionHolder;

/**
 * 登录
 * @author 大连首闻科技有限公司
 * @version 2013-7-15 13:47:22
 */
@Controller
@RequestMapping("/core/base")
public class LoginController {
	
	/** 功能根路径 */
	private String basePath = "core/base/login/";
	
	/** 数据操作对象 */
	private LoginDao dao;
	
	/**
	 * 注入数据操作对象
	 * @param dao 数据操作对象
	 */
	@Resource(name="loginDao")
	public void setDao(LoginDao dao) {
		this.dao = dao;
	}
	
	/**
	 * 跳转到登录页面
	 * @param loginUser 登录对象
	 * @param request 请求对象
	 * @param model 反馈对象
	 * @return basePath + 'index'
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(@ModelAttribute("loginUser") LoginUser loginUser, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
                Object obj = request.getSession().getAttribute(CONFIG.SESSION_USER);
                if(obj!=null){
                    request.getRequestDispatcher("home").forward(request, response);
                }
//		根据缓存获取登录用户名、皮肤、是(1)否(0)背景漂浮、背景漂浮速度
		String userCode = "";
		String skinInfo = "";
		String isBackgroundFloat = "0";
		int backgroundFloatSpeed = 5;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for(int i = 0 ;i < cookies.length ; i ++) {
				if (cookies[i].getName().equals("user_code")) {
					userCode = URLDecoder.decode(cookies[i].getValue(), "utf-8");
					userCode = userCode!=null?userCode.trim():"";
				}
				if (cookies[i].getName().equals("skin_info")) {
					skinInfo = URLDecoder.decode(cookies[i].getValue(), "utf-8");
					skinInfo = skinInfo!=null?skinInfo.trim():"";
				}
				if (cookies[i].getName().equals("is_background_float")) {
					isBackgroundFloat = URLDecoder.decode(cookies[i].getValue(), "utf-8");
                                        //如果缓存中没有IsBackgroundFloat信息，则默认置0，不浮动
					isBackgroundFloat = isBackgroundFloat!=null?isBackgroundFloat.trim():"0";
				}
				if (cookies[i].getName().equals("background_float_speed")) {
					backgroundFloatSpeed = Integer.parseInt(URLDecoder.decode(cookies[i].getValue(), "utf-8"));
					backgroundFloatSpeed = backgroundFloatSpeed<=0?5:backgroundFloatSpeed;
				}
			}
		}
//		参数传递到前台
		loginUser.setUser_code(userCode);
//		处理为样式
		StringBuffer style = new StringBuffer();
		if(!"".equals(skinInfo)){
			style.append("body{background-image:url('").append(request.getContextPath())
				.append("/").append(CONFIG.SKIN_ROOT_PATH).append("/").append(skinInfo).append("');background-size:100% 100%}");
		}
		model.addAttribute("style", style);
//		背景浮动传递
		model.addAttribute("isBackgroundFloat", isBackgroundFloat);
		model.addAttribute("backgroundFloatSpeed", backgroundFloatSpeed);
//		记录操作日志
		LogUtils.updateOperationLog(request, OperationType.VISIT, "访问登录页面");
		return basePath + "index";
	}

	/**
	 * 执行登录
	 * @param loginUser 登录用户
	 * @param bindingResult 验证的错误信息
	 * @return basePath + 'index' - forward
	 * @throws Exception
	 */
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(@Validated LoginUser loginUser, BindingResult bindingResult, 
			HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
//		如果有错误则直接返回原页面
		if(bindingResult.hasErrors()){
//			记录操作日志
			LogUtils.updateOperationLog(request, OperationType.LOGIN, "执行登录操作（失败：验证信息发生错误），登录用户："+loginUser.getUser_code());
			return login(loginUser, request,response, model);
		}
		/**
		 * 获取用户数据并验证用户名密码是否正确
		 */
//		获取用户名密码
		String userCode = loginUser.getUser_code();
		String password = loginUser.getPassword();
//		获取登录ID
		String loginId = new GUID().toString();
//		定义日志文档
		LoginLog loginLog = new LoginLog();
//		设置日志的LogId、SessionId、登录时间、IP地址、状态、是否登出
		loginLog.setLog_id(loginId);
		HttpSession session = request.getSession();
		loginLog.setSession_id(session.getId());
		loginLog.setLogin_time(new Timestamp(System.currentTimeMillis()));
		loginLog.setIp(request.getRemoteHost());
		loginLog.setLogin_status("1");
		loginLog.setIs_logout("0");
//		设置日志登录用户名
		loginLog.setLogin_user_id(userCode);
		loginLog.setLogin_user_name(userCode);
//		设置日志登录用户部门编号、登录用户部门名称
		loginLog.setLogin_user_dept_id("-");
		loginLog.setLogin_user_dept_name("未知");
//		密码加密
		password = SecurityUtils.getMD5(password);
//		获取用户信息
		User user = dao.getUserInfo(userCode);
//		如果用户为空则用户名不存在，返回并提示
		if(user==null){
			bindingResult.addError(new ObjectError("userNotExist", "登录错误：用户不存在，请您核实用户名是否正确"));
//			记录日志 - 2 - 用户不存在
			loginLog.setLogin_status("2");
			loginLog.setIs_logout("1");
			LogUtils.insertLoginLog(dao, loginLog);
//			记录操作日志
			LogUtils.updateOperationLog(request, OperationType.LOGIN, "执行登录操作（失败：用户不存在），登录用户："+userCode);
			return login(loginUser, request, response, model);
		}
//		如果密码不正确则返回并提示
		if(!password.equals(user.getPassword())){
			bindingResult.addError(new ObjectError("wrongPassword", "登录错误：密码错误，请您核实您的账户密码"));
//			记录日志 - 3 - 密码错误
			loginLog.setLogin_status("3");
			loginLog.setIs_logout("1");
			LogUtils.insertLoginLog(dao, loginLog);
//			记录操作日志
			LogUtils.updateOperationLog(request, OperationType.LOGIN, "执行登录操作（失败：密码错误），登录用户："+userCode);
			return login(loginUser, request, response, model);
		}
//		如果用户无效则返回并提示
		if(!"1".equals(user.getValid_type())){
			bindingResult.addError(new ObjectError("wrongPassword", "登录错误：当前用户无效，请联系系统管理员"));
//			记录日志 - 4 - 用户无效
			loginLog.setLogin_status("4");
			loginLog.setIs_logout("1");
			LogUtils.insertLoginLog(dao, loginLog);
//			记录操作日志
			LogUtils.updateOperationLog(request, OperationType.LOGIN, "执行登录操作（失败：用户无效），登录用户："+userCode);
			return login(loginUser, request, response, model);
		}
//		设置日志中的登录用户信息
		loginLog.setLogin_user_id(user.getUser_id());
		loginLog.setLogin_user_name(user.getUser_name());
//		设置日志中的登录用户部门信息
		loginLog.setLogin_user_dept_id(user.getDept_id());
		loginLog.setLogin_user_dept_name(user.getDept_name());
		/**
		 * 单一登录的限制
		 */
//		获取是否限制用户单一登录
		String online_login_user_single = AttributeUtils.getAttributeContent(request.getServletContext(), "online_login_user_single");
		if("1".equals(online_login_user_single)){
//			查询当前用户的在线数量
			int alreadyLoginUserCount = dao.getUserLoginCount(user.getUser_id());
//			如果用户登录数量大于0则提示在线不允许登录
			if(alreadyLoginUserCount>0){
				bindingResult.addError(new ObjectError("userAlreadyLogin", "当前用户已在线，系统禁止同时登录。"));
//				记录日志 - 5 - 用户已在线
				loginLog.setLogin_status("5");
				loginLog.setIs_logout("1");
				LogUtils.insertLoginLog(dao, loginLog);
//				记录操作日志
				LogUtils.updateOperationLog(request, OperationType.LOGIN, "执行登录操作（失败：用户已在线），登录用户："+userCode);
				return login(loginUser, request, response, model);
			}
		}
		/**
		 * 同一IP的限制
		 */
//		获取是否限制同一IP登录
		String online_login_ip_single = AttributeUtils.getAttributeContent(request.getServletContext(), "online_login_ip_single");
		if("1".equals(online_login_ip_single)){
//			获取当前IP的
			String ip = request.getRemoteHost();
//			查询当前IP的在线数量
			int alreadyLoginIpCount = dao.getIpLoginCount(ip);
//			如果登录的IP大于0则不允许登录
			if(alreadyLoginIpCount>0){
				bindingResult.addError(new ObjectError("ipAlreadyLogin", "当前操作机已有在线用户，系统禁止同一台操作机同时登录多个。"));
//				记录日志 - 6 - IP已在线
				loginLog.setLogin_status("6");
				loginLog.setIs_logout("1");
				LogUtils.insertLoginLog(dao, loginLog);
//				记录操作日志
				LogUtils.updateOperationLog(request, OperationType.LOGIN, "执行登录操作（失败：IP已在线），登录用户："+userCode);
				return login(loginUser, request, response, model);
			}
		}
		/**
		 * 将登录用户传递到session中
		 */
//		映射登录用户到Object实体对象
		SessionUser sessionUser = new SessionUser();
//		设置登录ID
		sessionUser.setLogin_id(loginId);
//		获取用户参数对象
		UserAttr userAttr = dao.getUserAttrById(user.getUser_id());
		sessionUser.setUserAttr(userAttr);
//		获取快捷方式信息
		List<Limit> shortcutList = dao.getShortcutList(user.getUser_id());
		sessionUser.setShortcutList(shortcutList);
//		获取用户所有功能对象
		List<Limit> limitList = dao.getLimitList(user.getUser_id());
		sessionUser.setLimitList(limitList);
//		获取用户的所有权限对象，用于权限判断
		List<Map<String, Object>> limitInfoList = dao.getLimitInfoList(user.getUser_id());
//		将用户的权限以Map的形式传递到Session中
		Map<String, String> limitInfo = new HashMap<String, String>();
		for(Map<String, Object> tempLimitInfo : limitInfoList){
			limitInfo.put(MapUtils.getString(tempLimitInfo, "url"), MapUtils.getString(tempLimitInfo, "is_has_limit"));
		}
		sessionUser.setLimitInfo(limitInfo);
//		映射用户信息到实体对象
		LoginController.mappingSessionUser(sessionUser, user, request);
//		用户对象传递到Session
		request.getSession().setAttribute(CONFIG.SESSION_USER, sessionUser);
		/**
		 * 记录缓存，保存用户名
		 */
//		登录用户名记录到cookie(默认有效期为30天)
		Cookie cookie = new Cookie("user_code", URLEncoder.encode(userCode, "utf-8"));
		try{
			cookie.setMaxAge(60 * 60 * 24 * CONFIG.SYSTEM_LOGIN_USER_COOKIE_AVAILABLE_DAY);
		}catch(Exception e){
			cookie.setMaxAge(60 * 60 * 24 * 30);
		}
		response.addCookie(cookie);
//		设置皮肤缓存
		Cookie skinCookie = new Cookie("skin_info", URLEncoder.encode(sessionUser.getUserAttr().getSkin_info(), "utf-8"));
		skinCookie.setPath("/");
		try{
			skinCookie.setMaxAge(60 * 60 * 24 * CONFIG.SYSTEM_LOGIN_USER_SKIN_INFO_COOKIE_AVAILABLE_DAY);
		}catch(Exception e){
			skinCookie.setMaxAge(60 * 60 * 24 * 30);
		}
		response.addCookie(skinCookie);
//		设置是否背景浮动缓存
		Cookie isBackgroundFloatCookie = new Cookie("is_background_float", URLEncoder.encode(sessionUser.getUserAttr().getIs_background_float(), "utf-8"));
		isBackgroundFloatCookie.setPath("/");
		try{
			isBackgroundFloatCookie.setMaxAge(60 * 60 * 24 * CONFIG.SYSTEM_IS_BACKGROUND_FLOAT_COOKIE_AVAILABLE_DAY);
		}catch(Exception e){
			isBackgroundFloatCookie.setMaxAge(60 * 60 * 24 * 30);
		}
		response.addCookie(isBackgroundFloatCookie);
//		设置背景浮动速度缓存
		Cookie backgroundFloatSpeedCookie = new Cookie("background_float_speed", URLEncoder.encode(String.valueOf(sessionUser.getUserAttr().getBackground_float_speed()), "utf-8"));
		backgroundFloatSpeedCookie.setPath("/");
		try{
			backgroundFloatSpeedCookie.setMaxAge(60 * 60 * 24 * CONFIG.SYSTEM_BACKGROUND_FLOAT_SPEED_COOKIE_AVAILABLE_DAY);
		}catch(Exception e){
			backgroundFloatSpeedCookie.setMaxAge(60 * 60 * 24 * 30);
		}
		response.addCookie(backgroundFloatSpeedCookie);
//		返回到系统主页面
		model.addAttribute("url", "core/base/home");
//		记录日志 - 1 - 登录成功
		LogUtils.insertLoginLog(dao, loginLog);
//		记录操作日志
		LogUtils.updateOperationLog(request, OperationType.LOGIN, "执行登录操作，登录用户："+user.getUser_name()+"("+userCode+")，登录正常页面");
		return basePath + "forward";
	}
	
	/**
	 * 登出系统
	 * @param request 请求对象
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="/logout", method=RequestMethod.POST)
	@ResponseBody
	public void logout(HttpServletRequest request) throws Exception {
//		获取SessionUser信息
		SessionUser sessionUser = (SessionUser)request.getSession().getAttribute(CONFIG.SESSION_USER);
//		记录操作日志
		LogUtils.updateOperationLog(request, OperationType.LOGOUT, "登出系统");
		if(sessionUser==null){
			return;
		}
//		获取日志
		LoginLog loginLog = new LoginLog();
//		设置LogId、登出时间、是否登出、登出类别-1
		loginLog.setLog_id(sessionUser.getLogin_id());
		loginLog.setIs_logout("1");
		loginLog.setLogout_type("1");
		loginLog.setLogout_time(new Timestamp(System.currentTimeMillis()));
//		记录日志
		LogUtils.updateLoginLog(dao, loginLog);
//		清空用户的Session信息
		request.getSession().removeAttribute(CONFIG.SESSION_USER);
	}
	
//	*********************************************************************
//	LoginController.class 内部方法
//	*********************************************************************
	
	/**
	 * 映射登录用户到SessionUser对象
	 * @param sessionUser 被映射的SessionUser对象
	 * @param user 用户对象
	 * @param request 请求内容
	 */
	public static void mappingSessionUser(SessionUser sessionUser, User user, HttpServletRequest request){
//		获取sessionID映射到对象中
		sessionUser.setSession_id(request.getSession().getId());
//		获取当前登录IP地址映射到对象中
		sessionUser.setIp(request.getRemoteHost());
//		设置登录时间
		sessionUser.setLogin_time(DateUtils.getNowTime());
//		其他信息映射到对象中
		sessionUser.setDept_id(user.getDept_id());
		sessionUser.setDept_name(user.getDept_name());
		sessionUser.setAddress(user.getAddress());
		sessionUser.setBirthday(user.getBirthday());
		sessionUser.setCard_id(user.getCard_id());
		sessionUser.setCard_type(user.getCard_type());
		sessionUser.setCreate_time(user.getCreate_time());
		sessionUser.setDegree(user.getDegree());
		sessionUser.setEmail(user.getEmail());
		sessionUser.setFolk(user.getFolk());
		sessionUser.setLogin_time(DateUtils.getNowTime());
		sessionUser.setPassword(user.getPassword());
		sessionUser.setPhone(user.getPhone());
		sessionUser.setRemark(user.getRemark());
		sessionUser.setSex(user.getSex());
		sessionUser.setUser_code(user.getUser_code());
		sessionUser.setUser_id(user.getUser_id());
		sessionUser.setUser_name(user.getUser_name());
		sessionUser.setWork_date(user.getWork_date());
	}
	
        
        /**
        * 单点登录方式
        *
        * @param loginUser
        * @param request
        * @param model
        * @return
        * @throws Exception
        */
        @RequestMapping(value = "/loginCas", method = RequestMethod.GET)
        public String loginForCas(@ModelAttribute("loginUser") LoginUser loginUser,HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
            Object obj = request.getSession().getAttribute(CONFIG.SESSION_USER);
            if(obj!=null){
                request.getRequestDispatcher("home").forward(request, response);
            }
   //		salt代表所属系统 从权限系统获得 其值为权限系统user的code
            Map<String, Object> casProperty = AssertionHolder.getAssertion().getPrincipal().getAttributes();
            if (casProperty != null && casProperty.get("salt").toString().indexOf("wzgl")>-1) {
                String userCode = (String) casProperty.get("loginname");
                String skinInfo = "";
                String isBackgroundFloat = "0";
                int backgroundFloatSpeed = 5;
                if (!initUser(userCode,request, response, model)) {     //初始化用户信息
                    return basePath + "403";
                }
    //		处理为样式
                StringBuffer style = new StringBuffer();
                if (!"".equals(skinInfo)) {
                    style.append("body{background-image:url('").append(request.getContextPath())
                            .append("/").append(CONFIG.SKIN_ROOT_PATH).append("/").append(skinInfo).append("');background-size:100% 100%}");
                }
                model.addAttribute("style", style);
    //		背景浮动传递
                model.addAttribute("isBackgroundFloat", isBackgroundFloat);
                model.addAttribute("backgroundFloatSpeed", backgroundFloatSpeed);
    //		记录操作日志
                LogUtils.updateOperationLog(request, OperationType.VISIT, "访问登录页面");
                loginUser.setUser_code(userCode);
                return basePath + "forward";
            }
            return basePath + "403";
        }

        private boolean initUser(String userCode,HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
            /**
             * 获取用户数据并验证用户名密码是否正确
             */
    //		获取登录ID
            String loginId = new GUID().toString();
    //		定义日志文档
            LoginLog loginLog = new LoginLog();
    //		设置日志的LogId、SessionId、登录时间、IP地址、状态、是否登出
            loginLog.setLog_id(loginId);
            HttpSession session = request.getSession();
            loginLog.setSession_id(session.getId());
            loginLog.setLogin_time(new Timestamp(System.currentTimeMillis()));
            loginLog.setIp(request.getRemoteHost());
            loginLog.setLogin_status("1");
            loginLog.setIs_logout("0");
    //		设置日志登录用户名
            loginLog.setLogin_user_id(userCode);
            loginLog.setLogin_user_name(userCode);
    //		设置日志登录用户部门编号、登录用户部门名称
            loginLog.setLogin_user_dept_id("-");
            loginLog.setLogin_user_dept_name("未知");
    //		获取用户信息
            User user = dao.getUserInfo(userCode);
    //		如果用户为空则用户名不存在，返回并提示
            if (user == null) {
    //			记录日志 - 2 - 用户不存在
                loginLog.setLogin_status("2");
                loginLog.setIs_logout("1");
                LogUtils.insertLoginLog(dao, loginLog);
    //			记录操作日志
                LogUtils.updateOperationLog(request, OperationType.LOGIN, "执行登录操作（失败：用户不存在），登录用户：" + userCode);
                return false;
            }
    //		如果用户无效则返回并提示
            if (!"1".equals(user.getValid_type())) {
    //			记录日志 - 4 - 用户无效
                loginLog.setLogin_status("4");
                loginLog.setIs_logout("1");
                LogUtils.insertLoginLog(dao, loginLog);
    //			记录操作日志
                LogUtils.updateOperationLog(request, OperationType.LOGIN, "执行登录操作（失败：用户无效），登录用户：" + userCode);
                return false;
            }
    //		设置日志中的登录用户信息
            loginLog.setLogin_user_id(user.getUser_id());
            loginLog.setLogin_user_name(user.getUser_name());
    //		设置日志中的登录用户部门信息
            loginLog.setLogin_user_dept_id(user.getDept_id());
            loginLog.setLogin_user_dept_name(user.getDept_name());
            /**
             * 单一登录的限制
             */
    //		获取是否限制用户单一登录
            String online_login_user_single = AttributeUtils.getAttributeContent(CONFIG.SERVLET_CONTEXT, "online_login_user_single");
            if ("1".equals(online_login_user_single)) {
    //			查询当前用户的在线数量
                int alreadyLoginUserCount = dao.getUserLoginCount(user.getUser_id());
    //			如果用户登录数量大于0则提示在线不允许登录
                if (alreadyLoginUserCount > 0) {
    //				记录日志 - 5 - 用户已在线
                    loginLog.setLogin_status("5");
                    loginLog.setIs_logout("1");
                    LogUtils.insertLoginLog(dao, loginLog);
    //				记录操作日志
                    LogUtils.updateOperationLog(request, OperationType.LOGIN, "执行登录操作（失败：用户已在线），登录用户：" + userCode);
                    return false;
                }
            }
            /**
             * 同一IP的限制
             */
    //		获取是否限制同一IP登录
            String online_login_ip_single = AttributeUtils.getAttributeContent(CONFIG.SERVLET_CONTEXT, "online_login_ip_single");
            if ("1".equals(online_login_ip_single)) {
    //			获取当前IP的
                String ip = request.getRemoteHost();
    //			查询当前IP的在线数量
                int alreadyLoginIpCount = dao.getIpLoginCount(ip);
    //			如果登录的IP大于0则不允许登录
                if (alreadyLoginIpCount > 0) {
    //				记录日志 - 6 - IP已在线
                    loginLog.setLogin_status("6");
                    loginLog.setIs_logout("1");
                    LogUtils.insertLoginLog(dao, loginLog);
    //				记录操作日志
                    LogUtils.updateOperationLog(request, OperationType.LOGIN, "执行登录操作（失败：IP已在线），登录用户：" + userCode);
                    return false;
                }
            }
            /**
             * 将登录用户传递到session中
             */
    //		映射登录用户到Object实体对象
            SessionUser sessionUser = new SessionUser();
    //		设置登录ID
            sessionUser.setLogin_id(loginId);
    //		获取用户参数对象
            UserAttr userAttr = dao.getUserAttrById(user.getUser_id());
            sessionUser.setUserAttr(userAttr);
    //		获取快捷方式信息
            List<Limit> shortcutList = dao.getShortcutList(user.getUser_id());
            sessionUser.setShortcutList(shortcutList);
    //		获取用户所有功能对象
            List<Limit> limitList = dao.getLimitList(user.getUser_id());
            sessionUser.setLimitList(limitList);
    //		获取用户的所有权限对象，用于权限判断
            List<Map<String, Object>> limitInfoList = dao.getLimitInfoList(user.getUser_id());
    //		将用户的权限以Map的形式传递到Session中
            Map<String, String> limitInfo = new HashMap<String, String>();
            for (Map<String, Object> tempLimitInfo : limitInfoList) {
                limitInfo.put(MapUtils.getString(tempLimitInfo, "url"), MapUtils.getString(tempLimitInfo, "is_has_limit"));
            }
            sessionUser.setLimitInfo(limitInfo);
    //		映射用户信息到实体对象
            LoginController.mappingSessionUser(sessionUser, user, request);
    //		用户对象传递到Session
            request.getSession().setAttribute(CONFIG.SESSION_USER, sessionUser);
            /**
             * 记录缓存，保存用户名
             */
    //		登录用户名记录到cookie(默认有效期为30天)
            Cookie cookie = new Cookie("user_code", URLEncoder.encode(userCode, "utf-8"));
            try {
                cookie.setMaxAge(60 * 60 * 24 * CONFIG.SYSTEM_LOGIN_USER_COOKIE_AVAILABLE_DAY);
            } catch (Exception e) {
                cookie.setMaxAge(60 * 60 * 24 * 30);
            }
            response.addCookie(cookie);
    //		设置皮肤缓存
            Cookie skinCookie = new Cookie("skin_info", URLEncoder.encode(sessionUser.getUserAttr().getSkin_info(), "utf-8"));
            skinCookie.setPath("/");
            try {
                skinCookie.setMaxAge(60 * 60 * 24 * CONFIG.SYSTEM_LOGIN_USER_SKIN_INFO_COOKIE_AVAILABLE_DAY);
            } catch (Exception e) {
                skinCookie.setMaxAge(60 * 60 * 24 * 30);
            }
            response.addCookie(skinCookie);
    //		设置是否背景浮动缓存
            Cookie isBackgroundFloatCookie = new Cookie("is_background_float", URLEncoder.encode(sessionUser.getUserAttr().getIs_background_float(), "utf-8"));
            isBackgroundFloatCookie.setPath("/");
            try {
                isBackgroundFloatCookie.setMaxAge(60 * 60 * 24 * CONFIG.SYSTEM_IS_BACKGROUND_FLOAT_COOKIE_AVAILABLE_DAY);
            } catch (Exception e) {
                isBackgroundFloatCookie.setMaxAge(60 * 60 * 24 * 30);
            }
            response.addCookie(isBackgroundFloatCookie);
    //		设置背景浮动速度缓存
            Cookie backgroundFloatSpeedCookie = new Cookie("background_float_speed", URLEncoder.encode(String.valueOf(sessionUser.getUserAttr().getBackground_float_speed()), "utf-8"));
            backgroundFloatSpeedCookie.setPath("/");
            try {
                backgroundFloatSpeedCookie.setMaxAge(60 * 60 * 24 * CONFIG.SYSTEM_BACKGROUND_FLOAT_SPEED_COOKIE_AVAILABLE_DAY);
            } catch (Exception e) {
                backgroundFloatSpeedCookie.setMaxAge(60 * 60 * 24 * 30);
            }
            response.addCookie(backgroundFloatSpeedCookie);
    //		返回到系统主页面
            model.addAttribute("url", "core/base/home");
    //		记录日志 - 1 - 登录成功
            LogUtils.insertLoginLog(dao, loginLog);
    //		记录操作日志
            LogUtils.updateOperationLog(request, OperationType.LOGIN, "执行登录操作，登录用户：" + user.getUser_name() + "(" + userCode + ")，登录正常页面");
    //              设置code 供前台显示
            return true;
        }
}
