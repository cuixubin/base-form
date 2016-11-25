<%-- 
    Document   : forget_step1
    Created on : 2015-8-3, 11:51:40
    Author     : haohao
--%>

<%@page import="com.dlshouwen.core.base.config.CONFIG"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                    <h1>根据Email找回密码</h1>
                    <div class="conent_box">
                        <ul class="clearfix">
                            <li class="li1">用户名：</li>
                            <li class="li2"><input type="text" class="text" name="loginname"/></li>
                            <li class="li1 mt20">邮箱：</li>
                            <li class="li2 mt20"><input type="text" class="text" name="email" /></li>
                        </ul>
                        <p>请输入在个人资料中填写的邮箱地址</p>
                        <a href="#" id="submit1" class="submit" onclick="ajaxVerify()">找回</a>
                        <label id="submit2" class="submit" style="display: none">找回中...</label>
                    </div>
                </div>
                <div class="login-footer">
                    ${applicationScope.__SYSTEM_ATTRIBUTE__.login_page_copyright.content } 
                </div>  
            </div>
        </div>
    </body>
</html>
