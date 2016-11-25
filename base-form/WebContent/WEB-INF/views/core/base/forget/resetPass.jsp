<%-- 
    Document   : resetPass
    Created on : 2015-8-3, 18:29:40
    Author     : haohao
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

            <div class="login_box ggmm">
                <div class="passwordPageBox">
                    <h1>更改密码</h1>
                    <div class="conent_box">
                        <ul class="clearfix ul1">
                            <li class="li1">新密码：</li>
                            <li class="li2"><input type="password" class="text" name="newPass"/></li>
                            <li class="li1 mt20">重新输入密码：</li>
                            <li class="li2 mt20"><input type="password" class="text"  name="reNewPass"/></li>
                        </ul>
                        <a href="#" class="submit" onclick="reNew()">确定</a>
                    </div>
                </div>
                <div class="login-footer">
                    ${applicationScope.__SYSTEM_ATTRIBUTE__.login_page_copyright.content } 
                </div> 
            </div>
        </div>
    </body>
</html>
