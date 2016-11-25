<%-- 
    Document   : send_success
    Created on : 2015-8-3, 18:34:48
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
            <div class="login_box ggmm_success Email_yfs">
                <div class="passwordPageBox">
                    <h1>验证邮件已发送，请于30分钟内完成验证。若未收到邮件，点击<a href="${pageContext.request.contextPath}/core/base/forget/sendEmail" class="resend">重发邮件</a></h1><br/>
                    <center><a href="${checkUrl}" class="f30" target="_blank">查看邮件</a></center>
                </div>
                <div class="login-footer">
                    ${applicationScope.__SYSTEM_ATTRIBUTE__.login_page_copyright.content } 
                </div>
            </div>
        </div>
    </body>
</html>
