<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 查看邮件</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
	<style type="text/css">
	body{background:none;}

	.table{margin-bottom:0px;}
	.table th{text-align:right;}
	.table>thead>tr>th, .table>tbody>tr>th, .table>tfoot>tr>th, .table>thead>tr>td, .table>tbody>tr>td, .table>tfoot>tr>td{padding:8px;}
	</style>
</head>
<body>
	<div class="panel panel-default">
		<div class="panel-heading"><i class="fa fa-envelope-o"></i>邮件信息</div>
		<div class="panel-body" style="padding:0px;">
			<table class="table">
				<tr>
					<th style="width:100px;">发件人：</th>
					<td>${messageInfo.sender_name }</td>
				</tr>
				<tr>
					<th>收件人：</th>
					<td>${messageInfo.receiver_name }</td>
				</tr>
				<tr>
					<th>主题：</th>
					<td>${messageInfo.title }</td>
				</tr>
			</table>
		</div>
	</div>
	<div class="panel panel-default">
		<div class="panel-heading"><i class="fa fa-file-text-o"></i>邮件内容</div>
		<div class="panel-body">
			${messageInfo.content }
		</div>
	</div>
</body>
</html>