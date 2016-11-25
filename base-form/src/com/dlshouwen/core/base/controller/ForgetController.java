package com.dlshouwen.core.base.controller;

import com.dlshouwen.core.base.dao.ForgetDao;
import com.dlshouwen.core.base.utils.SecurityUtils;
import com.dlshouwen.core.base.utils.SendEmail;
import com.dlshouwen.core.base.utils.SendSMS;
import com.dlshouwen.core.system.model.User;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Random;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsDateJsonBeanProcessor;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 登录
 *
 * @author 大连首闻科技有限公司
 * @version 2013-7-15 13:47:22
 */
@Controller
@RequestMapping("/core/base/forget")
public class ForgetController {

    private static final Logger logger = Logger.getLogger(ForgetController.class);
    /**
     * 功能根路径
     */
    private String basePath = "core/base/forget/";

    /**
     * 数据操作对象
     */
    private ForgetDao dao;

    /**
     * 注入数据操作对象
     *
     * @param dao 数据操作对象
     */
    @Resource(name = "forgetDao")
    public void setDao(ForgetDao dao) {
        this.dao = dao;
    }

    /**
     * 跳转忘记密码首页
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String toIndex() throws Exception {
        return basePath + "forget_step1";
    }

    /**
     * 验证email
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/verifyEmail", method = RequestMethod.POST)
    public void verifyEmail(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String msg = "{\"msg\":\"fail\"}";
        String loginname = request.getParameter("loginname");
        String email = request.getParameter("email");
        User account = dao.findByLoginNameAndEmail(loginname, email);
        if (account != null) {
            msg = "{\"msg\":\"success\"}";
            addCookie(request, response, "loginname", loginname, 1800);
            addCookie(request, response, "email", email, 1800);
        }
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.getWriter().write(msg);
    }

    /**
     * 发送邮件
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/sendEmail", method = RequestMethod.GET)
    public String sendEmail(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String loginname = getCookie(request, "loginname");
        String email = getCookie(request, "email");
        if (loginname == null || email == null || loginname.trim().length() == 0 || email.trim().length() == 0) {
            return basePath + "forget_email";
        }
        try {
            SendEmail.sendEmailXHD(loginname, email, request.getContextPath() + "/core/base/forget/toResetPass");

            request.getSession().getServletContext().setAttribute("loginname", loginname);
            String postfix = email.split("@")[1];
            String checkUrl = "http://mail." + postfix;
            request.setAttribute("checkUrl", checkUrl);
        } catch (Exception e) {
            logger.info("邮箱发送失败，请查看邮箱配置，附上：" + e.getMessage());
        }

        return basePath + "send_success";
    }

    /**
     * 发送验证码
     *
     * @param request
     * @param response
     * @throws java.io.IOException
     */
    @RequestMapping(value = "/sendRegexNum", method = RequestMethod.POST)
    public void sendRegexNum(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String loginName = request.getParameter("loginName");
        String phoneNum = request.getParameter("phoneNum").trim();
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        try {
            User account = dao.findByNameAndPhone(loginName, phoneNum);
            if (account == null) {
                response.getWriter().write("N");
                return;
            }
            String num = getAuthCode(loginName, request, response);
            System.out.println("本次验证码为：" + num);
            String mes = "尊敬的用户：您正在通过密保手机找回登陆密码，验证码为:" + num
                    + "，有效时间60秒,请勿泄露。";
            SendSMS.sendSMS(phoneNum, mes, "");
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonBeanProcessor(java.sql.Date.class, new JsDateJsonBeanProcessor());
            JSONObject obj = JSONObject.fromObject(account, jsonConfig);
            response.getWriter().write(obj.toString());
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("error");
        }
    }

    /**
     * 验证码验证
     *
     * @param request
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(value = "/toResetPassword", method = RequestMethod.POST)
    public void toResetPassword(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws Exception {
        String loginname = request.getParameter("loginname");
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        if ((loginname != null) && (loginname.equals(new String(loginname.getBytes("iso8859-1"), "iso8859-1")))) {
            loginname = new String(loginname.getBytes("ISO-8859-1"), "utf-8");
        }
        String type = request.getParameter("type");

        String phoneCookieContent = getCookie(request, "authCode" + loginname);

        String verifyNumCode = request.getParameter("verifyNum");
        String verifyNum = DigestUtils.md5Hex("{" + verifyNumCode + "}");

        request.setAttribute("loginname", loginname);
        String error = "";
        if ((type.equals("phone")) && (phoneCookieContent != null)) {

            if ((verifyNum != null) && (verifyNum.trim().length() > 0)) {
                if (!verifyNum.equals(phoneCookieContent)) {
                    error = "{error:'验证码输入不正确'}";
                } else {
                    addCookie(request, response, loginname + "phone", "true", -1);
                    deleteCookie(request, response, "authCode" + loginname);
                }
            } else {
                error = "{error:'请输入验证码'}";
            }
        } else if (phoneCookieContent == null) {
            error = "{error:'验证码过期,请重新获取'}";
        }
        response.getWriter().write(error);
    }

    /**
     * 异常跳转页面
     *
     * @param request
     * @param response
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/toErrorPage", method = RequestMethod.GET)
    public String toErrorPage(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws Exception {
        return basePath + "forget_step1";
    }

    /**
     * 跳转修改密码页面
     *
     * @param request
     * @param response
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/toChangePass", method = RequestMethod.POST)
    public String toChangePass(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forgetPwdFlag = getCookie(request, "forgetPwdFlag");
        if (forgetPwdFlag != null && forgetPwdFlag.trim().length() > 0) {
            String loginname = request.getParameter("loginname");
            addCookie(request, response, "resetLoginname", loginname, -1);
            return basePath + "resetPass";
        } else {
            return basePath + "forget_step1";
        }
    }

    /**
     * 重置密码
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/resetPass", method = RequestMethod.POST)
    public void resetPass(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String msg = "{\"msg\":\"success\"}";
        String loginname = getCookie(request, "resetLoginname");
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        if (loginname == null) {
            msg = "{\"msg\":\"重置失败\"}";
            response.getWriter().write(msg);
            return;
        }
        User account = dao.findByLoginName(loginname);
        String newPass = request.getParameter("newPass").trim();
        String pw =SecurityUtils.getMD5(newPass);
        String old = account.getPassword();
        if (pw.equals(old)) {
            msg = "{\"msg\":\"密码没有更改,请重新设置密码\"}";
            response.getWriter().write(msg);
            return;
        }
        try {
            account.setPassword(pw);
            dao.updateUserPass(account);
        } catch (Exception e) {
            msg = "{\"msg\":\"重置失败\"}";
        }
        request.getSession().getServletContext().setAttribute("successLoginname", loginname);
        response.getWriter().write(msg);
    }

    /**
     * 重置成功页面
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/toResetPassSuccessPage", method = RequestMethod.GET)
    public String toResetPassSuccessPage(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String loginname = (String) request.getSession().getServletContext().getAttribute("successLoginname");
        if (loginname == null || loginname.trim().length() == 0) {
            return basePath + "forget_step1";
        }
        request.getSession().getServletContext().removeAttribute("successLoginname");
        return basePath + "reset_success";
    }

    /**
     * 跳转到email页面
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/toEmail", method = RequestMethod.GET)
    public String toEmail(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return basePath + "forget_email";
    }

    /**
     * 跳转重置密码页面
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/toResetPass", method = RequestMethod.GET)
    public String toResetPass(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String loginname = (String) request.getSession().getServletContext().getAttribute("loginname");
        if (loginname != null) {
            request.getSession().getServletContext().removeAttribute("loginname");
        }
        if (loginname == null || loginname.trim().length() == 0) {
            return basePath + "forget_step1";
        }
        addCookie(request, response, "resetLoginname", loginname, -1);
        return basePath + "resetPass";
    }

    /**
     * 跳转手机登录页面
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/toPhone", method = RequestMethod.GET)
    public String toPhone(HttpServletRequest request, HttpServletResponse response) {
        try {
            addCookie(request, response, "forgetPwdFlag", "xcyg", -1);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
        }
        return basePath + "forget_Phone_step2";
    }

    protected void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, int expiry) throws Exception {
        if ((name != null) && (value != null)) {
            if (response != null) {
                value = URLEncoder.encode(value, "UTF-8");
                String contextPath = request.getContextPath();
                Cookie cookie = new Cookie(name, value);
                cookie.setPath(contextPath.length() > 0 ? contextPath : "/");
                cookie.setMaxAge(expiry);
                response.addCookie(cookie);
            }
        }
    }

    protected String getCookie(HttpServletRequest request, String name) throws UnsupportedEncodingException {
        if (name != null) {
            if (request != null) {
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if ((cookie != null) && (name.equals(cookie.getName()))) {
                            String value = cookie.getValue();
                            value = URLDecoder.decode(value, "UTF-8");
                            return value;
                        }
                    }
                }
            }
        }
        return null;
    }

    protected void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name)
            throws Exception {
        if (name != null) {
            addCookie(request, response, name, "", 0);
        }
    }

    /**
     * 获取手机验证码
     *
     * @param cusName
     * @param request
     * @param response
     * @return
     */
    private String getAuthCode(String cusName, HttpServletRequest request, HttpServletResponse response) {

        char[] codeSequence = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        Random random = new Random();
        StringBuilder randomCode = new StringBuilder();
        int codeCount = 6;
        for (int i = 0; i < codeCount; i++) {
            String strRand = String.valueOf(codeSequence[random.nextInt(10)]);

            randomCode.append(strRand);
        }
        try {
            //将数据存入cookie中
            String cookieValue = DigestUtils.md5Hex("{" + randomCode.toString() + "}");
            addCookie(request, response, "authCode" + cusName, cookieValue, 60);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
        }
        return randomCode.toString();
    }
}
