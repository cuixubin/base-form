package com.dlshouwen.core.team.controller;

import com.dlshouwen.core.base.config.CONFIG;
import com.dlshouwen.core.base.http.FileUploadClient;
import com.dlshouwen.core.base.model.AjaxResponse;
import com.dlshouwen.core.base.model.OperationType;
import com.dlshouwen.core.base.model.SessionUser;
import com.dlshouwen.core.base.utils.GUID;
import com.dlshouwen.core.base.utils.LogUtils;
import com.dlshouwen.core.system.dao.UserDao;
import com.dlshouwen.core.system.model.User;
import com.dlshouwen.core.team.dao.TeamDao;
import com.dlshouwen.core.team.model.Team;
import java.util.Date;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 团队管理
 *
 * @author cui
 */
@Controller
@RequestMapping("/core/team")
public class TeamController {

    /**
     * 功能根路径：抽取出跟路径，便于程序中直接调用
     */
    private String basePath = "core/team/";

    /**
     * 数据操作对象：定义数据操作对象，该对象通过setDao方法的Resource注解进行对象注入
     */
    private TeamDao dao;

    private UserDao userDao;

    /**
     * 注入数据操作对象
     *
     * @param dao 数据操作对象
     */
    @Resource(name = "teamDao")
    public void setDao(TeamDao dao) {
        this.dao = dao;
    }

    @Resource(name = "userDao")
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * 跳转到团队主页面
     *
     * @param request 请求对象
     * @param model 反馈对象
     * @return basePath + 'teamMain'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String goDeptMainPage(HttpServletRequest request, Model model) throws Exception {
        //		获取团队列表传递到前台
        List<Map<String, Object>> teamList = dao.getTeamList();
        model.addAttribute("teamTree", JSONArray.fromObject(teamList).toString());
        //		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问团队管理页面");
        return basePath + "teamMain";
    }

    /**
     * 获取某一团队信息
     *
     * @param teamId 团队编号
     * @param request 请求对象
     * @param model 反馈对象
     * @return 团队信息
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    @ResponseBody
    public Team viewDept(String teamId, HttpServletRequest request, Model model) throws Exception {
//		获取部门信息
        Team team = dao.getTeamById(teamId);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "获取部门信息，部门编号：" + team.getTeam_id() + "，部门名称：" + team.getTeam_name());
        return team;
    }

    /**
     * 跳转到新增团队页面
     *
     * @param preTeamId 上级团队编号
     * @param request 请求对象
     * @param model 反馈对象
     * @return basePath + 'addTeam'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/{preTeamId}/add", method = RequestMethod.GET)
    public String addTeam(@PathVariable String preTeamId, HttpServletRequest request, Model model) throws Exception {
//		定义新的团队对象
        Team team = new Team();
//		设置上级编号
        team.setPre_team_id(preTeamId);
//		传递新的团队到前台
        model.addAttribute("team", team);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问新增部门页面，上级部门编号：" + preTeamId);
        return basePath + "addTeam";
    }

    /**
     * 执行新增团队
     *
     * @param team 部门对象
     * @param bindingResult 绑定结果
     * @param request 请求对象
     * @return ajax响应内容
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public void addTeam(@Valid Team team, BindingResult bindingResult,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
//		定义AJAX响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
//		如果发现错误则拼接错误信息执行回执
        if (bindingResult.hasErrors()) {
            ajaxResponse.bindingResultHandler(bindingResult);
            LogUtils.updateOperationLog(request, OperationType.INSERT, "新增相册（新增失败，相册名称：" + team.getTeam_name() + "，错误信息：" + AjaxResponse.getBindingResultMessage(bindingResult) + "）");
            response.setContentType("text/html;charset=utf-8");
            JSONObject obj = JSONObject.fromObject(ajaxResponse);
            response.getWriter().write(obj.toString());
            return;
        }
        //团队图片
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartRequest.getFile("picture");
        String fileName = multipartFile.getOriginalFilename();
        String path = null;
        if (null != multipartFile && StringUtils.isNotEmpty(multipartFile.getOriginalFilename())) {
            JSONObject jobj = FileUploadClient.upFile(request, fileName, multipartFile.getInputStream());
            if (jobj.getString("responseMessage").equals("OK")) {
                path = jobj.getString("fpath");
            }
        }
        if (null != path) {
            team.setTeam_path(path);
        }

//		设置团队编号、创建人、创建时间、编辑人、编辑时间
        team.setTeam_id(new GUID().toString());
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        team.setCreator(sessionUser.getUser_id());
        team.setCreate_time(new Date());
        team.setEditor(sessionUser.getUser_id());
        team.setEdit_time(new Date());

//		执行新增团队
        dao.insertTeam(team);
//		反馈成功信息
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("团队新增成功！");
        ajaxResponse.setData(team);

        //用‘URL’字段通知前段页面的跳转路径
        Map map = new HashMap();
        map.put("URL", basePath);
        ajaxResponse.setExtParam(map);
        response.setContentType("text/html;charset=utf-8");
        JSONObject obj = JSONObject.fromObject(ajaxResponse);
        response.getWriter().write(obj.toString());

//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.INSERT, "新增团队，团队编号：" + team.getTeam_id() + "，团队名称：" + team.getTeam_name() + "）");
    }

    /**
     * 跳转到编辑团队页面
     *
     * @param teamId 部门编号
     * @param request 请求对象
     * @param model 反馈对象
     * @return base + 'editDept'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/{teamId}/edit", method = RequestMethod.GET)
    public String editTeam(@PathVariable String teamId, HttpServletRequest request, Model model) throws Exception {
//		获取部门信息
        Team team = dao.getTeamById(teamId);
//		设置到反馈对象中
        model.addAttribute("team", team);
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问编辑团队页面，团队编号：" + team.getTeam_id() + "，团队名称：" + team.getTeam_name());
//		执行跳转到编辑页面
        return basePath + "editTeam";
    }

    /**
     * 执行编辑团队
     *
     * @param team 部门对象
     * @param bindingResult 绑定对象
     * @param request 请求对象
     * @return ajax响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public void editTeam(@Valid Team team, BindingResult bindingResult,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
//		定义AJAX响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
//		如果发现错误则拼接错误信息执行回执
        if (bindingResult.hasErrors()) {
            ajaxResponse.bindingResultHandler(bindingResult);
//			记录操作日志
            LogUtils.updateOperationLog(request, OperationType.UPDATE, "编辑团队（失败：数据验证错误，团队编号：" + team.getTeam_id() + "，团队名称：" + team.getTeam_name() + "）");
            response.setContentType("text/html;charset=utf-8");
            JSONObject obj = JSONObject.fromObject(ajaxResponse);
            response.getWriter().write(obj.toString());
            return;
        }
//		设置编辑人、编辑时间
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        team.setEditor(sessionUser.getUser_id());
        team.setEdit_time(new Date());
        //团队图片
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartRequest.getFile("picture");
        String fileName = multipartFile.getOriginalFilename();
        String path = null;
        if (null != multipartFile && StringUtils.isNotEmpty(multipartFile.getOriginalFilename())) {
            JSONObject jobj = FileUploadClient.upFile(request, fileName, multipartFile.getInputStream());
            if (jobj.getString("responseMessage").equals("OK")) {
                path = jobj.getString("fpath");
            }
        }
        if (null != path) {
            team.setTeam_path(path);
        }
//		执行编辑部门
        dao.updateTeam(team);
//		反馈成功信息
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage("部门编辑成功！");
        ajaxResponse.setData(team);
        //用‘URL’字段通知前段页面的跳转路径
        Map map = new HashMap();
        map.put("URL", basePath);
        ajaxResponse.setExtParam(map);

//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.UPDATE, "编辑部门，部门编号：" + team.getTeam_id() + "，团队名称：" + team.getTeam_name() + "）");
        response.setContentType("text/html;charset=utf-8");
        JSONObject obj = JSONObject.fromObject(ajaxResponse);
        response.getWriter().write(obj.toString());
    }

    /**
     * 执行删除团队
     *
     * @param teamId 团队编号
     * @param request 请求对象
     * @return ajax响应回执
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse deleteTeam(String teamIds, HttpServletRequest request) throws Exception {
//		定义最终需要删除的部门列表
        StringBuffer deleteTeamIds = new StringBuffer();
//		定义各个数量
        int totalCount = teamIds.split(",").length;
        int hasTeamCount = 0;
        int hasUserCount = 0;
//		删除成功、失败的纪录编号
        StringBuffer successIds = new StringBuffer();
        StringBuffer failureIds = new StringBuffer();
//		遍历判断
        for (String teamId : teamIds.split(",")) {
//			是否包含子项
            boolean isHaveSubTeam = dao.getSubTeamCount(teamId) > 0 ? true : false;
            if (isHaveSubTeam) {
                hasTeamCount++;
                failureIds.append(teamId).append(",");
                continue;
            }
//			部门是否包含人员
            boolean isHaveUser = dao.getTeamIsHaveUser(teamId);
            if (isHaveUser) {
                hasUserCount++;
                failureIds.append(teamId).append(",");
                continue;
            }
            successIds.append(teamId).append(",");
//			拼接删除部门编号
            deleteTeamIds.append(teamId).append(",");
        }
        if (deleteTeamIds.length() > 0) {
            deleteTeamIds.deleteCharAt(deleteTeamIds.length() - 1);
//			执行删除部门
            dao.deleteTeam(deleteTeamIds.toString());
        }
//		处理响应信息
        StringBuffer message = new StringBuffer();
        message.append("本次共执行").append(totalCount).append("条记录，其中：<br /><br />");
        message.append("　　成功删除团队").append(totalCount - hasTeamCount - hasUserCount).append("条。<br />");
        if (hasTeamCount > 0) {
            message.append("　　因为包含子部门无法删除的记录共").append(hasTeamCount).append("条。<br />");
        }
        if (hasUserCount > 0) {
            message.append("　　因为包含用户无法删除的记录共").append(hasUserCount).append("条。<br />");
        }
//		执行响应回执
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setSuccessMessage(message.toString());
        ajaxResponse.setData(deleteTeamIds.toString().replaceAll("'", ""));

//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.DELETE,
                "删除部门，成功删除团队：" + successIds.toString() + "；因为包含子部门无法删除的团队：" + failureIds.toString());
        return ajaxResponse;
    }

    /**
     * 选择团队
     *
     * @param type 选择类别
     * @param request 请求对象
     * @param model 反馈对象
     * @return basePath + 'selectTeam'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/select/{type}", method = RequestMethod.GET)
    public String selectTeam(@PathVariable String type,
            HttpServletRequest request, Model model) throws Exception {
        String teamId = request.getParameter("teamId");
//		传递选择类别到前台
        model.addAttribute("type", type);
//		获取团队树传递到前台
        List<Map<String, Object>> teamList = dao.getTeamList();
        if (teamId != null && teamId.trim().length() > 0) {
            for (Map obj : teamList) {
                String teamId1 = (String) obj.get("team_id");
                if (teamId1.equals(teamId)) {
                    obj.put("checked", "true");
                    break;
                }
            }
        }
        model.addAttribute("teamTree", JSONArray.fromObject(teamList));
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问选择团队页面");
        return basePath + "selectTeam";
    }

    /**
     * 选择根据条件团队
     *
     * @param type 选择类别
     * @param request 请求对象
     * @param model 反馈对象
     * @return basePath + 'selectTeam'
     * @throws Exception 抛出全部异常
     */
    @RequestMapping(value = "/selectOneTeam", method = RequestMethod.GET)
    public String selectTeam2(HttpServletRequest request, Model model) throws Exception {
        String teamId = request.getParameter("teamId");
//		传递选择类别到前台
        model.addAttribute("type", "radio");
//		获取团队树传递到前台
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
        User user = userDao.getUserById(sessionUser.getUser_id());
        List<Map<String, Object>> teamList = dao.getTeamList(user.getIdentity(), user.getTeam_id());
        if (teamId != null && teamId.trim().length() > 0) {
            for (Map obj : teamList) {
                String teamId1 = (String) obj.get("team_id");
                if (teamId1.equals(teamId)) {
                    obj.put("checked", "true");
                    break;
                }
            }
        }
        model.addAttribute("teamTree", JSONArray.fromObject(teamList));
//		记录操作日志
        LogUtils.updateOperationLog(request, OperationType.VISIT, "访问选择团队页面");
        return basePath + "selectTeam";
    }
}
