<%-- 
    Document   : reset_success
    Created on : 2015-8-4, 14:57:43
    Author     : sunxingwu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 密码找回</title>
    <%@ include file="common.jsp"%>
    <script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/forget/js/resetAfter.js"></script>
</head>
<body>
    <div class="login_main passwordPage">
        <div class="login_box ggmm_success Email_yfs">
            <div class="passwordPageBox">
                <h1>您好，您的密码已经修改成功,3秒后返回登录界面！</h1>
            </div>
            <div class="login-footer">
                ${applicationScope.__SYSTEM_ATTRIBUTE__.login_page_copyright.content } 
            </div> 
        </div>
    </div>
</body>
</html>
