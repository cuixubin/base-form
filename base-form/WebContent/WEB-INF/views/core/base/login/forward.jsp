<%@ include file="/resources/include/header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 跳转中...</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
        <meta name="renderer" content="webkit"/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<link rel="shortcut icon" href="${pageContext.request.contextPath }/resources/images/favicon.ico">
	<base target="_self" />
	<%-- jQuery --%>
	<script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/jquery/jquery.min.js"></script>
	<%-- bootstrap --%>
	<script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/bootstrap/js/bootstrap.min.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/plugin/bootstrap/css/bootstrap.min.css" />
	<%-- font-awesome --%>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/plugin/fontAwesome/css/font-awesome.min.css" media="all" />
	<script type="text/javascript">
	function openSystemWindow(){
		window.location.href = '${pageContext.request.contextPath }/${url }';
	}
	</script>
	<style type="text/css">
	body, html{
		background:white;
	}
	#forward_loading{
		position:absolute;
		top:40%;
		left:50%;
		margin-left:-120px;
		color:#666;
		width:240px;
		padding-left:24px;
		line-height:18px;
	};
	</style>
</head>
<body onload="openSystemWindow();">
	<div id="forward_loading">
		<i class="fa fa-spinner fa-spin"></i>&nbsp;&nbsp;正在跳转到系统功能页面，请稍等...
	</div>
</body>
</html>