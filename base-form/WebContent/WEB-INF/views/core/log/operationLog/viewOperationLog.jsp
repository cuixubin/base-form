<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 查看操作日志</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
	<script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/syntaxHighLighter/scripts/shCore.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/syntaxHighLighter/scripts/shBrushBash.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/syntaxHighLighter/scripts/shBrushSql.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/syntaxHighLighter/scripts/shBrushJava.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/plugin/syntaxHighLighter/styles/shCore.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/plugin/syntaxHighLighter/styles/shThemeDefault.css" />
	<style type="text/css">
	html, body{background:none;}
	.table th{text-align:right;font-weight:normal;}
	</style>
	<script type="text/javascript">
	//语法着色
	$(function(){
		SyntaxHighlighter.config.clipboardSwf = '${pageContext.request.contextPath }/resources/plugin/syntaxHighLighter/scripts/clipboard.swf';
		SyntaxHighlighter.all();
	});
	</script>
</head>
<body>
	<table class="table table-bordered">
		<tr>
			<th style="width:120px;">日志内码：</th>
			<td>${operationLog.log_id }</td>
		</tr>
		<tr>
			<th>操作地址：</th>
			<td>${operationLog.operation_url }</td>
		</tr>
		<tr>
			<th>操作类别：</th>
			<td>
				<c:forEach items="${applicationScope.__CODE_TABLE__.operation_type }" var="item">
					<c:if test="${item.key== operationLog.operation_type}">${item.value }</c:if>
				</c:forEach>
			</td>
		</tr>
		<tr>
			<th>操作结果：</th>
			<td>
				<c:forEach items="${applicationScope.__CODE_TABLE__.operation_result }" var="item">
					<c:if test="${item.key== operationLog.operation_result}">${item.value }</c:if>
				</c:forEach>
			</td>
		</tr>
		<tr>
			<th>错误原因：</th>
			<td><pre class="brush:java;">${operationLog.error_reason }</pre></td>
		</tr>
		<tr>
			<th>操作说明：</th>
			<td>${operationLog.operation_detail }</td>
		</tr>
		<tr>
			<th>响应开始时间：</th>
			<td><fmt:formatDate value="${operationLog.response_start }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		<tr>
			<th>响应结束时间：</th>
			<td><fmt:formatDate value="${operationLog.response_end }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		<tr>
			<th>耗时：</th>
			<td>${operationLog.cost }</td>
		</tr>
		<tr>
			<th>操作人：</th>
			<td>${operationLog.operator }</td>
		</tr>
		<tr>
			<th>操作人姓名：</th>
			<td>${operationLog.operator_name }</td>
		</tr>
		<tr>
			<th>操作人部门代码：</th>
			<td>${operationLog.operator_dept_id }</td>
		</tr>
		<tr>
			<th>操作人部门名称：</th>
			<td>${operationLog.operator_dept_name }</td>
		</tr>
		<tr>
			<th>操作机ip：</th>
			<td>${operationLog.ip }</td>
		</tr>
	</table>
</body>
</html>