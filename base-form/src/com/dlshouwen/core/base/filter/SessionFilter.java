package com.dlshouwen.core.base.filter;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.dlshouwen.core.base.config.CONFIG;
import com.dlshouwen.core.base.dao.BaseDao;
import com.dlshouwen.core.base.model.OperationResult;
import com.dlshouwen.core.base.model.OperationType;
import com.dlshouwen.core.base.model.ProjectSession;
import com.dlshouwen.core.base.model.SessionUser;
import com.dlshouwen.core.base.utils.DateUtils;
import com.dlshouwen.core.base.utils.GUID;
import com.dlshouwen.core.base.utils.LimitUtils;
import com.dlshouwen.core.base.utils.LogUtils;
import com.dlshouwen.core.log.model.OperationLog;
import com.dlshouwen.core.base.local.ProjectSessionThreadLocal;
import com.dlshouwen.core.base.local.SessionUserThreadLocal;

/**
 * 检查Session权限
 *
 * @author 大连首闻科技有限公司
 * @version 2013-7-12 8:56:36
 */
public class SessionFilter implements Filter {

    /**
     * 登录用户
     */
    private SessionUser sessionUser = null;

    /**
     * 系统会话
     */
    private ProjectSession projectSession = null;

    public SessionUser getLoginUser() {
        return sessionUser;
    }

    public void setLoginUser(SessionUser sessionUser) {
        this.sessionUser = sessionUser;
    }

    public ProjectSession getProjectSession() {
        return projectSession;
    }

    public void setProjectSession(ProjectSession projectSession) {
        this.projectSession = projectSession;
    }

    /**
     * Servlet初始化方法
     */
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * 执行Session过滤
     * <p>
     * 将处理权限的过滤，并记录操作日志
     *
     * @param request 请求对象
     * @param response 响应对象
     * @param chain 过滤对象
     * @throws IOException, ServletException 抛出IO异常、Servlet异常
     */
    public void doFilter(ServletRequest req, ServletResponse resp,
            FilterChain filterChain) throws IOException, ServletException {
//		获取Http参数
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
//		判断是否是Ajax请求
        boolean isAjaxRequest = false;;
        String requestHeader = request.getHeader("__REQUEST_TYPE__");
        if (requestHeader != null) {
            if (requestHeader.trim().equalsIgnoreCase("AJAX_REQUEST")
                    || requestHeader.trim().equalsIgnoreCase("AJAX_REQUESTFORM")) {
                isAjaxRequest = true;
            }
        }
//		获取访问的真实路径
        String path = request.getRequestURI().replaceFirst(request.getContextPath(), "");
        if (path.startsWith("/")) {
            path = path.replaceFirst("/", "");
        }
//		获取访问会话
        ProjectSession projectSession = new ProjectSession();
        projectSession.setSession_id(request.getSession().getId());
        projectSession.setIp(request.getRemoteHost());
        this.setProjectSession(projectSession);
//		设置projectSessionThreadLocal
        ProjectSessionThreadLocal.setSession(projectSession);
//		获取登录用户
        this.setLoginUser((SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER));
//		设置SessionUserThreadLocal
        SessionUserThreadLocal.setUser(this.getLoginUser());
//		首页、资源、上传不走验证
        if ("".equals(path) || path.startsWith("resource")
                || path.startsWith("core/server/image/upload") || path.startsWith("core/server/file/upload")
                || path.equals("core/base/skin") || path.equals("api/login")
                || path.equals("api/getGroups") || path.equals("api/getUsers")
                || path.equals("api/getUser")) {
            filterChain.doFilter(request, response);
            return;
        }
//		获取Spring的配置
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(CONFIG.SERVLET_CONTEXT);
//		获取数据源
        DataSource dataSource = context.getBean(CONFIG.LOGIN_LOG_DATA_SOURCE, DataSource.class);
//		定义BaseDao对象
        BaseDao dao = new BaseDao();
        dao.setDataSource(dataSource);
//		初始化操作日志：日志编号、访问地址、默认类别（未知）、操作结果（默认成功）、操作信息、响应开始时间、操作人、操作人姓名、操作人IP
        OperationLog operationLog = new OperationLog();
        operationLog.setLog_id(new GUID().toString());
        operationLog.setOperation_url(path);
        operationLog.setOperation_type(OperationType.UNKNOW);
        operationLog.setOperation_result(OperationResult.SUCCESS);
        operationLog.setOperation_detail("-");
        operationLog.setResponse_start(new Timestamp(System.currentTimeMillis()));
        operationLog.setOperator(sessionUser == null ? "-" : sessionUser.getUser_id());
        operationLog.setOperator_name(sessionUser == null ? "未知" : sessionUser.getUser_name());
        operationLog.setOperator_dept_id(sessionUser == null ? "-" : sessionUser.getDept_id());
        operationLog.setOperator_dept_name(sessionUser == null ? "未知" : (sessionUser.getDept_name() == null ? "" : sessionUser.getDept_name()));
        operationLog.setIp(request.getRemoteHost());
//		获取耗时开始
        Long startCost = System.currentTimeMillis();
//		处理登录信息
        if (path.startsWith("core/base/login") || path.startsWith("core/base/forget")) {
            filterChain.doFilter(request, response);
//			记录Session失效错误、响应时间、耗时
            operationLog.setOperation_result(OperationResult.SESSION_INVALID);
            operationLog.setResponse_end(new Timestamp(System.currentTimeMillis()));
            operationLog.setCost((int) (System.currentTimeMillis() - startCost));
            try {
                LogUtils.insertOperationLog(dao, operationLog);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
//		如果登录用户为空，则强制返回
        if (sessionUser == null) {
//			记录Session失效错误、响应时间、耗时
            operationLog.setOperation_result(OperationResult.SESSION_INVALID);
            operationLog.setResponse_end(new Timestamp(System.currentTimeMillis()));
            operationLog.setCost((int) (System.currentTimeMillis() - startCost));
            try {
                LogUtils.insertOperationLog(dao, operationLog);
            } catch (Exception e) {
                e.printStackTrace();
            }
//			如果是AJAX请求则返回提示数据
            if (isAjaxRequest) {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html;charset=UTF-8");
                try {
                    response.getWriter().write("__AJAX_REQUEST_SESSION_OUT__");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            } else {
//				定向到session失效页面
                response.sendRedirect(request.getContextPath() + CONFIG.SESSION_INVALID_PAGE);
                return;
            }
        }
        /**
         * 检查用户是否有访问权限
         */
        Map<String, String> limitInfo = sessionUser.getLimitInfo();
        boolean isHasLimit = false;
        try {
            isHasLimit = LimitUtils.checkLimit(path, limitInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!isHasLimit) {
//			记录无权访问、响应时间、耗时
            operationLog.setOperation_result(OperationResult.NO_LIMIT);
            operationLog.setResponse_end(new Timestamp(System.currentTimeMillis()));
            operationLog.setCost((int) (System.currentTimeMillis() - startCost));
            try {
                LogUtils.insertOperationLog(dao, operationLog);
            } catch (Exception e) {
                e.printStackTrace();
            }
//			如果是AJAX请求则返回提示数据
            if (isAjaxRequest) {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html;charset=UTF-8");
                try {
                    response.getWriter().write("__AJAX_REQUEST_NO_LIMIT__");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            } else {
//				定向到无权访问页面
                response.sendRedirect(request.getContextPath() + CONFIG.NO_LIMIT_PAGE);
                return;
            }
        }
        /**
         * 检验用户是否已被强制下线
         */
        JdbcTemplate jt = new JdbcTemplate(dataSource);
        StringBuffer sql = new StringBuffer();
        sql.append("select l.* from core_login_log l where l.log_id=?");
        Map<String, Object> loginLogInfo = null;
        try {
            loginLogInfo = jt.queryForMap(sql.toString(), sessionUser.getLogin_id());
        } catch (EmptyResultDataAccessException e) {
            loginLogInfo = null;
        }
        if (loginLogInfo == null || "4".equals(MapUtils.getString(loginLogInfo, "logout_type"))) {
//			记录强制下线、响应时间、耗时
            operationLog.setOperation_result(OperationResult.FORCE_OUTLINE);
            operationLog.setResponse_end(new Timestamp(System.currentTimeMillis()));
            operationLog.setCost((int) (System.currentTimeMillis() - startCost));
            try {
                LogUtils.insertOperationLog(dao, operationLog);
            } catch (Exception e) {
                e.printStackTrace();
            }
//			如果是AJAX请求则返回提示数据
            if (isAjaxRequest) {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html;charset=UTF-8");
                try {
                    response.getWriter().write("__AJAX_REQUEST_FORCE_OUTLINE__");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            } else {
//				定向到强制下线页面
                response.sendRedirect(request.getContextPath() + CONFIG.FORCE_OUTLINE_PAGE);
                return;
            }
        }
//		放行
        request.setAttribute(CONFIG.OPERATION_LOG, operationLog);
//		定义开始时间
        long start = System.currentTimeMillis();
//		定义日志对象
        Logger logger = Logger.getLogger(SessionFilter.class);
//		记录开始日志
        StringBuffer log = new StringBuffer();
        if (!path.startsWith("core/layout") && !path.startsWith("core/home")) {
            log.append("访问开始：");
            log.append("访问地址-").append(path).append(";");
            log.append("访问时间-").append(DateUtils.getNowTimeHaveMS()).append(";");
            if (sessionUser.getDept_name() != null) {
                log.append("访问用户所属部门-").append(sessionUser.getDept_name().trim()).append(";");
            }
            log.append("访问用户-").append(sessionUser.getUser_name()).append("(").append(sessionUser.getUser_id()).append(")");
            logger.info(log);
        }
        try {
            filterChain.doFilter(request, response);
//			记录响应时间、耗时
            operationLog = (OperationLog) request.getAttribute(CONFIG.OPERATION_LOG);
            operationLog.setResponse_end(new Timestamp(System.currentTimeMillis()));
            operationLog.setCost((int) (System.currentTimeMillis() - startCost));
//			全局监控不记录日志
            if (!path.startsWith("core/tools/monitoring")) {
                LogUtils.insertOperationLog(dao, operationLog);
            }
        } catch (Exception e) {
            e.printStackTrace();
//			记录响应错误、响应时间、耗时
            operationLog = (OperationLog) request.getAttribute(CONFIG.OPERATION_LOG);
            operationLog.setOperation_result(OperationResult.ERROR);
            operationLog.setError_reason(e.getMessage());
            operationLog.setResponse_end(new Timestamp(System.currentTimeMillis()));
            operationLog.setCost((int) (System.currentTimeMillis() - startCost));
            try {
                LogUtils.insertOperationLog(dao, operationLog);
            } catch (Exception _e) {
                _e.printStackTrace();
            }
//			定向到错误页面
            response.sendRedirect(request.getContextPath() + "/resources/error/500.jsp");
        } finally {
//			定义结束时间
            long end = System.currentTimeMillis();
//			记录结束日志
            if (!path.startsWith("core/layout") && !path.startsWith("core/home")) {
                log = new StringBuffer();
                log.append("访问开始：");
                log.append("访问地址-").append(path).append(";");
                log.append("访问时间-").append(DateUtils.getNowTimeHaveMS()).append(";");
                if (sessionUser.getDept_name() != null) {
                    log.append("访问用户所属部门-").append(sessionUser.getDept_name().trim()).append(";");
                }
                log.append("访问用户-").append(sessionUser.getUser_name()).append("(").append(sessionUser.getUser_id()).append(");");
                log.append("响应耗时-").append(end - start).append("ms").append("");
                logger.info(log);
            }
        }
        return;
    }

    /**
     * Servlet销毁
     */
    public void destroy() {
    }

}
