<%-- 
    Document   : forget_Phone_step2
    Created on : 2015-8-5, 14:20:39
    Author     : sunxingwu
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
                    <h1>根据手机找回密码</h1>
                    <div class="conent_box">
                        <form id="getBackPasswordForm" action="" method="post">
                            <ul class="clearfix">
                                <li class="li1">用户名:</li>
                                <li class="li2"><input type="text" id="loginname" name="loginname" class="text"/></li>
                                <li class="li1 mt10">手机号:</li>
                                <li class="li2 mt10"><input type="text" id="phone"  name="phone" class="text" /></li>
                                <li class="mt10 li4">
                                    <a href="#"  id="free">免费获取验证码</a>
                                    <a href="#" id="free1" style="display:none;background:grey"></a>
                                </li>
                                <li class="li5 mt10"></li>
                                <li class="li1 mt10">验证码:</li>
                                <li class="li2 mt10">
                                    <input type="text" id="verifyNum"  name="verifyNum" class="text"/>
                                    <span id="verifyErr"></span>
                                </li>
                            </ul>
                        </form>
                        <a href="#" id="save" class="submit">下一步</a>
                    </div>
                </div>
                <div class="login-footer">
                    ${applicationScope.__SYSTEM_ATTRIBUTE__.login_page_copyright.content } 
                </div>
            </div>
        </div>
    </body>
</html>
