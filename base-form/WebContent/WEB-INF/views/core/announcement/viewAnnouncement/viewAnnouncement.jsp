<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 查看公告</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
	<style type="text/css">
	html, body{background:none;}
	</style>
</head>
<body>
	<div style="padding:10px;">
		<div style="text-align:center;font-weight:bold;font-size:16px;line-height:32px;">
			${announcement.title }
		</div>
		<div style="text-align:center;font-weight:normal;font-size:12px;line-height:18px;color:#999;">
			发布人：${announcement.creator_name }
			&nbsp;&nbsp;
			发布时间：${announcement.create_time }
		</div>
		<div style="margin-top:10px;font-weight:normal;font-size:12px;line-height:18px;color:#222;">
			${announcement.content }<br />
		</div>
	</div>
</body>
</html>