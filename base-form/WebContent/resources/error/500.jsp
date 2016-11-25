<%@ page language="java" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.system_title.content }</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/syntaxHighLighter/scripts/shCore.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/syntaxHighLighter/scripts/shBrushBash.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/syntaxHighLighter/scripts/shBrushSql.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/plugin/syntaxHighLighter/styles/shCore.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/plugin/syntaxHighLighter/styles/shThemeDefault.css" />
	<style type="text/css">
	.error-panel{margin:10px;margin-bottom:0px;padding:5px;border:1px solid #ddd;}
	</style>
	<script type="text/javascript">
	//初始化
	$(function(){
		SyntaxHighlighter.config.clipboardSwf = '${pageContext.request.contextPath }/resources/plugin/syntaxHighLighter/scripts/clipboard.swf';
		SyntaxHighlighter.all();
	});
	//重置任务调度方法，本页面不进行任务调度
	var systemTaskListener = function(){};
	</script>
</head>
<body style="background:none;">
	<div class="error-panel">
		<pre class="brush:sql;"><%
		if(exception==null){
			out.println("未知的错误。");
		}else{
			out.println(exception.getMessage());
		}
		%></pre>
	</div>
	<div class="error-panel">
		<pre class="brush:sql;"><%
		if(exception==null){
			out.println("未知的错误。");
		}else{
			StackTraceElement[] stackTraceElement = exception.getStackTrace();
			for(int i=0; i<stackTraceElement.length; i++){
				out.println(stackTraceElement[i].toString());
			}
		}
		%></pre>
	</div>
</body>
</html>