<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 查看SQL日志</title>
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
			<th style="width:100px;">日志编号：</th>
			<td>${sqlLog.log_id }</td>
		</tr>
		<tr>
			<th>调用来源：</th>
			<td>${sqlLog.call_source }</td>
		</tr>
		<tr>
			<th>行号：</th>
			<td>${sqlLog.line_no }</td>
		</tr>
		<tr>
			<th>操作sql：</th>
			<td><pre class="brush:sql;">${sqlLog.operation_sql }</pre></td>
		</tr>
		<tr>
			<th>参数：</th>
			<td>${sqlLog.params }</td>
		</tr>
		<tr>
			<th>执行结果：</th>
			<td>
				<c:forEach items="${applicationScope.__CODE_TABLE__.call_result }" var="item">
					<c:if test="${item.key== sqlLog.call_result}">${item.value }</c:if>
				</c:forEach>
			</td>
		</tr>
		<tr>
			<th>执行类别：</th>
			<td>${sqlLog.execute_type }</td>
		</tr>
		<tr>
			<th>错误原因：</th>
			<td><pre class="brush:java;">${sqlLog.error_reason }</pre></td>
		</tr>
		<tr>
			<th>结果类别：</th>
			<td><pre class="brush:java;">${sqlLog.result_type }</pre></td>
		</tr>
		<tr>
			<th>执行开始时间：</th>
			<td><fmt:formatDate value="${sqlLog.start_time }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		<tr>
			<th>执行结束时间：</th>
			<td><fmt:formatDate value="${sqlLog.end_time }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		<tr>
			<th>耗时：</th>
			<td>${sqlLog.cost }</td>
		</tr>
		<tr>
			<th>操作人：</th>
			<td>${sqlLog.operator }</td>
		</tr>
		<tr>
			<th>操作人姓名：</th>
			<td>${sqlLog.operator_name }</td>
		</tr>
		<tr>
			<th>操作人部门代码：</th>
			<td>${sqlLog.operator_dept_id }</td>
		</tr>
		<tr>
			<th>操作人部门名称：</th>
			<td>${sqlLog.operator_dept_name }</td>
		</tr>
		<tr>
			<th>操作机ip：</th>
			<td>${sqlLog.ip }</td>
		</tr>
	</table>
</body>
</html>