<%@ include file="/resources/include/header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 登录</title>
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
	<%-- toast --%>
	<script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/toast/jquery.toast.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/plugin/toast/jquery.toast.css" />
	<%-- framework urils method js --%>
	<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/utils.min.js"></script>
	<%-- login css --%>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/css/login.min.css" media="all" />
	<!--[if lt IE 9]>
		<script src="${pageContext.request.contextPath }/resources/plugin/bootstrap/plugins/ie/html5shiv.js"></script>
		<script src="${pageContext.request.contextPath }/resources/plugin/bootstrap/plugins/ie/respond.js"></script>
	<![endif]-->
	<!--[if lt IE 8]>
		<script src="${pageContext.request.contextPath }/resources/plugin/bootstrap/plugins/ie/json2.js"></script>
	<![endif]-->
	<style type="text/css">${style }</style>
	<script type="text/javascript">
	//取消选中
	document.onselectstart=function(){
		return false;
	};
	//用户登录
	function doLogin(){
		document.forms[0].submit();
	}
	//处理回车提交
	function checkKey(e) {
		var theEvent = window.event || e;
		var code = theEvent.keyCode || theEvent.which;
		if(13 == code) {
			code = 0;
			doLogin();
		}
	}
	document.onkeydown=checkKey;
	//初始提示错误信息
	$(function(){
		var errorMessage = $('#login_error_message').html();
		if(errorMessage.trim()!=''){
			openToast(errorMessage, 'warning', 3000);
		}
	});
	$(function(){
		//绑定登录方法
		$('#doLogin').click(doLogin);
		//判断用户名是否存在，如果存在就定位到password
		var user_code = $('#user_code').val();
		if(user_code==''){
			$('#user_code').focus();
		}else{
			$('#password').focus();
		}
	});
	//处理浮动背景
	$(function(){
		//定义是否执行浮动、移动速度
		var isBackgroundFloat = '${isBackgroundFloat }';
		var backgroundFloatSpeed = '${backgroundFloatSpeed }';
		backgroundFloat(isBackgroundFloat, backgroundFloatSpeed);
	});
	</script>
</head>
<body>
	<!--[if lte IE 9]>
	<div class="text-warning fade in mb_0 browser-version-low">
		<button data-dismiss="alert" class="close" type="button">×</button>
		<strong>您正在使用低版本浏览器，</strong> 在本页面的显示效果可能有差异。建议您升级到以下浏览器：
		<a href="http://www.google.cn/intl/zh-CN/chrome/" target="_blank">Chrome<span class="text-success">（推荐）</span></a> /
		<a href="http://www.mozilla.org/" target="_blank">Firefox</a> /
		<a href="http://www.apple.com.cn/safari/" target="_blank">Safari</a> /
		<a href="http://www.opera.com/" target="_blank">Opera</a> /
		<a href="http://windows.microsoft.com/zh-cn/internet-explorer/download-ie" target="_blank">更高版本的 Internet Explorer</a>
	</div>
	<![endif]-->
	<div class="login-panel panel">
		<div class="login-form">
			<form:form modelAttribute="loginUser" method="post">
				<legend>${applicationScope.__SYSTEM_ATTRIBUTE__.login_page_system_title.content }</legend>
				<div class="form-group">
					<label>用户名：</label>
					<sw:input path="user_code" cssClass="form-control" placeholder="请输入您的用户名" />
				</div>
				<div class="form-group">
					<label>密码：</label>
					<sw:password path="password" cssClass="form-control" placeholder="请输入您的密码" />
				</div>
				<div class="footer">
					<button accesskey="L" type="button" class="btn btn-primary" id="doLogin">
						<i class="fa fa-user"></i>登录 (L)
					</button>
				</div>
				<div id="login_error_message"><form:errors path="*" /></div>
			</form:form>
                                <div style="margin-left: 82%"><a href="${pageContext.request.contextPath }/core/base/forget/index">忘记密码</a></div>
		</div>
	</div>
	<div class="login-footer">
		${applicationScope.__SYSTEM_ATTRIBUTE__.login_page_copyright.content } 
	</div>
</body>
</html>