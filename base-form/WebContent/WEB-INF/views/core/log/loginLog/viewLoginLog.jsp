<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 查看登录日志</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
	<style type="text/css">
	html, body{background:none;}
	.table th{text-align:right;font-weight:normal;}
	</style>
</head>
<body>
	<table class="table table-bordered">
		<tr>
			<th style="width:120px;">日志编号：</th>
			<td>${loginLog.log_id }</td>
		</tr>
		<tr>
			<th>SESSION编号：</th>
			<td>${loginLog.session_id }</td>
		</tr>
		<tr>
			<th>登录用户编号：</th>
			<td>${loginLog.login_user_id }</td>
		</tr>
		<tr>
			<th>登录用户：</th>
			<td>${loginLog.login_user_name }</td>
		</tr>
		<tr>
			<th>登录用户部门代码：</th>
			<td>${loginLog.login_user_dept_id }</td>
		</tr>
		<tr>
			<th>登录用户部门名称：</th>
			<td>${loginLog.login_user_dept_name }</td>
		</tr>
		<tr>
			<th>登录时间：</th>
			<td><fmt:formatDate value="${loginLog.login_time }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		<tr>
			<th>操作机IP：</th>
			<td>${loginLog.ip }</td>
		</tr>
		<tr>
			<th>登录状态：</th>
			<td>
				<c:forEach items="${applicationScope.__CODE_TABLE__.login_status }" var="item">
					<c:if test="${item.key== loginLog.login_status}">${item.value }</c:if>
				</c:forEach>
			</td>
		</tr>
		<tr>
			<th>是否登出：</th>
			<td>
				<c:forEach items="${applicationScope.__CODE_TABLE__.zero_one }" var="item">
					<c:if test="${item.key== loginLog.is_logout}">${item.value }</c:if>
				</c:forEach>
			</td>
		</tr>
		<tr>
			<th>登出状态：</th>
			<td>
				<c:forEach items="${applicationScope.__CODE_TABLE__.logout_type }" var="item">
					<c:if test="${item.key== loginLog.logout_type}">${item.value }</c:if>
				</c:forEach>
			</td>
		</tr>
		<tr>
			<th>登出时间：</th>
			<td><fmt:formatDate value="${loginLog.logout_time }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
	</table>
</body>
</html>