<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
    <head>
        <title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 预览模板</title>
        <base target="_self" />
        <%@ include file="/resources/include/plugin.jsp"%>
        <%@ include file="/resources/include/extra.jsp"%>
        <style type="text/css">
            html, body{background:none;}
        </style>
    </head>
    <body>
        <div style="padding:10px;">
            <pre><c:out value="${content}"/></pre>
        </div>
    </body>
</html>