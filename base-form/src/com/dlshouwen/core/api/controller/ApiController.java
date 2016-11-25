/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlshouwen.core.api.controller;

import com.dlshouwen.core.base.utils.AttributeUtils;
import com.dlshouwen.core.system.dao.SecUserDao;
import com.dlshouwen.core.system.dao.UserDao;
import com.dlshouwen.core.system.model.User;
import com.dlshouwen.core.team.dao.TeamDao;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sun.misc.BASE64Encoder;

/**
 * 与即时通讯接口互通
 *
 * @author yangtong
 */
@Controller
@RequestMapping("/api")
public class ApiController {

    private SecUserDao secUserDao;

    @Resource(name = "secUserDao")
    public void setSecUserDao(SecUserDao secUserDao) {
        this.secUserDao = secUserDao;
    }
    private TeamDao teamDao;

    @Resource(name = "teamDao")
    public void setTeamDao(TeamDao teamDao) {
        this.teamDao = teamDao;
    }

    private UserDao userDao;

    @Resource(name = "userDao")
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * 登录认证接口
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String loginName = request.getParameter("username");
        String password = request.getParameter("plainPassword");
        JSONObject obj = new JSONObject();
        Boolean flag = false;
        String msg = "";
        try {
            if (loginName != null && loginName.trim().length() > 0 && password != null && password.trim().length() > 0) {
                password = DigestUtils.md5Hex(password + "{loginname}");
                flag = secUserDao.queryUserByLoginNameAndPass(loginName, password);
                if (!flag) {
                    msg = "用户名或密码错误，请重新录入";
                }
            } else {
                msg = "用户名或密码为空，请重新录入";
            }
        } catch (Exception ex) {
            msg = "系统异常,请查看教师评测服务端";
            ex.printStackTrace();
        }
        obj.put("result", flag);
        obj.put("msg", msg);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(obj.toString());
    }

    /**
     * 获取团队信息接口
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/getGroups")
    public void getGoups(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Map<String, Object>> teams = teamDao.getTeamList();
        JSONArray array = new JSONArray();
        if (teams != null && !teams.isEmpty()) {
            for (Map map : teams) {
                JSONObject obj = new JSONObject();
                obj.put("cgroupId", map.get("team_id"));
                obj.put("cgroupName", map.get("team_name"));
                obj.put("cgroupType", 0);
                array.add(obj);
            }
        }
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(array.toString());
    }

    /**
     * 获取所有用户接口
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/getUsers")
    public void getUsers(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<User> users = userDao.getUserList();
        JSONArray array = new JSONArray();
        if (users != null && !users.isEmpty()) {
            for (User user : users) {
                JSONObject obj = new JSONObject();
                obj.put("groupId", user.getTeam_id());
                obj.put("userId", user.getUser_id());
                obj.put("screenName", user.getUser_name());
                obj.put("userName", user.getUser_code());
                obj.put("emailbox", user.getEmail());
                obj.put("mobilephone", user.getPhone());
                obj.put("telephone", user.getPhone());
                if(StringUtils.isNotEmpty(user.getImgpath())) {
                    String ipath = "";
                    try {
                        ipath = user.getImgpath().substring(user.getImgpath().lastIndexOf("/")+1, user.getImgpath().lastIndexOf("."));
                    }catch(Exception e) {
                        ipath = "";
                    }
                    obj.put("avatar", ipath);
                }else {
                    obj.put("avatar", "");
                }
                array.add(obj);
            }
        }
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(array.toString());
    }

    /**
     * 获取单个用户接口
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/getUser")
    public void getUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String loginName = request.getParameter("username");
        JSONObject obj = new JSONObject();
        if (loginName != null && loginName.trim().length() > 0) {
            User user = userDao.getUserByCode(loginName);
            if (user != null) {
                obj.put("userName", user.getUser_code());
                 if(StringUtils.isNotEmpty(user.getImgpath())) {
                    String ipath = "";
                    try {
                        ipath = user.getImgpath().substring(user.getImgpath().lastIndexOf("/")+1, user.getImgpath().lastIndexOf("."));
                    }catch(Exception e) {
                        ipath = "";
                    }
                    obj.put("avatar", ipath);
                }else {
                    obj.put("avatar", "");
                }
                obj.put("emailbox", user.getEmail());
                obj.put("mobilephone", user.getPhone());
                obj.put("telephone", user.getPhone());
                if(user.getImgpath()!=null&&user.getImgpath().trim().length()>0){
                    String recordNumberStr = AttributeUtils.getAttributeContent(request.getServletContext(), "source_webapp_file_postion");
                    obj.put("avatarData", GetImageStr(recordNumberStr+user.getImgpath()));
                }
            }
        }
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(obj.toString());
    }
    /**
     * 图片转base64
     * @param path
     * @return 
     */
    public static String GetImageStr(String path) {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try {
            URL url = new URL(path);
            HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();
            httpUrl.connect();
            in = httpUrl.getInputStream();
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);//返回Base64编码过的字节数组字符串
    }
 
}
