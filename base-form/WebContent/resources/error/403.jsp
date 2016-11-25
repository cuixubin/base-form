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
	function goHistory(){
		history.go(-1);
	}
	var i = 6;
	function autoBackToNormal(){
		i--;
		if(i==0){
			goHistory();
		}else{
			$('#showTime').html(i);
		}
		setTimeout(autoBackToNormal, 1000);
	}
	$(function(){
		$('#showTime').html(i);
		autoBackToNormal();
	});
	//重置任务调度方法，本页面不进行任务调度
	var systemTaskListener = function(){};
	</script>
</head>
<body>
<div id="message_info">
	<i class="fa fa-warning"></i>我们拒绝为您处理本次请求，请您谅解。<br />
	我们将于 <span class="text-danger" id="showTime">1</span> 秒后返回至上一界面，或者您可以 <a href="javascript:void(0);" target="_self" onclick="goHistory();">点此返回</a> 。
</div>
</body>
</html>