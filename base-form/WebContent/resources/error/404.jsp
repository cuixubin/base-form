<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.system_title.content }</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<style type="text/css">
	#message_info{
		line-height:20px;
		position:fixed;
		_position:absolute;
		top:50%;
		left:50%;
		width:400px;
		margin-top:-50px;
		margin-left:-200px;
		text-align:center;
		color:#666;
		background:rgba(255, 255, 255, 0.9);
		padding:10px 20px;
		border-radius:3px;
		-webkit-box-shadow: 0 0 6px rgba(0, 0, 0, 1);
		-moz-box-shadow: 0 0 6px rgba(0, 0, 0, 1);
		-o-box-shadow: 0 0 6px rgba(0, 0, 0, 1);
		box-shadow: 0 0 6px rgba(0, 0, 0, 1);
	}
	#message_info>i{margin-right:10px;}
	</style>
	<script type="text/javascript">
	//重置任务调度方法，本页面不进行任务调度
	var systemTaskListener = function(){};
	</script>
</head>
<body>
<div id="message_info">
	<i class="fa fa-warning"></i>页面未找到或正在建设中。<br />
</div>
</body>
</html>