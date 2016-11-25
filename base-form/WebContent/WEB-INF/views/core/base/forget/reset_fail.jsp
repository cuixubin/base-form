<%-- 
    Document   : reset_success
    Created on : 2015-8-3, 18:39:13
    Author     : haohao
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
        <div class="login_box ggmm_failure">
            <div class="passwordPageBox">
                <h1>修改密码失败</h1><br/>
                <h1>3秒后返回登录界面</h1>
            </div>
            <div class="login-footer">
                ${applicationScope.__SYSTEM_ATTRIBUTE__.login_page_copyright.content } 
            </div>
        </div>
    </div>

</body>
</html>
