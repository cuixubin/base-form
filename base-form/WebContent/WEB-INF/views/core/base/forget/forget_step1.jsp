<%-- 
    Document   : forget_step1
    Created on : 2015-8-3, 11:51:40
    Author     : yangtong
--%>

<%@page import="com.dlshouwen.core.base.config.CONFIG"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 密码找回</title>
        <%@ include file="common.jsp"%>
    </head>
    <body>
        <a href="${pageContext.request.contextPath}<%=CONFIG.FIRST_PAGE_PATH%>" class="re_btn">返回登录</a>
        <div class="login_main passwordPage">
            <div class="login_box">
                <div class="passwordPageBox">
                    <h1>激活用户可以通过以下两种方式设置密码</h1>
                    <div class="btn_box">
                        <a href="${pageContext.request.contextPath}/core/base/forget/toEmail" class="yxzh"></a>
                        <a href="${pageContext.request.contextPath}/core/base/forget/toPhone" class="sjzh"></a>
                    </div>
                </div>
                <div class="login-footer">
                    ${applicationScope.__SYSTEM_ATTRIBUTE__.login_page_copyright.content } 
                </div> 
            </div>
        </div>
    </body>
</html>
