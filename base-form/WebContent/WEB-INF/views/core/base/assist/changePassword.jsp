<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 编辑密码</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
	<script type="text/javascript">
	//定义验证对象
	var changePasswordValidator;
	//修改密码
	function changePassword(){
		if(!changePasswordValidator.validResult()){
			return;
		}
		if(!confirm('确定修改此密码吗？')){
			return;
		}
		var url = '${pageContext.request.contextPath }/core/base/assist/change_password';
		var params = sw.getFormParams(document.forms[0]);
		sw.showProcessBar();
		sw.ajaxSubmit(url, params, function(data){
			sw.ajaxSuccessCallback(data, function(){
				changePasswordValidator.reset(true);
			});
		});
	}
	//初始化表单验证
	$(function(){
		changePasswordValidator = $.fn.dlshouwen.validator.init($('#changePasswordForm'));
	});
	$(function(){
		$('#change_password').bind('click', changePassword);
	});
	</script>
</head>
<body>
	<jsp:include page="/core/base/layout/header" />
	<jsp:include page="/core/base/layout/left" />
	<jsp:include page="/core/base/layout/shortcut" />
	<div class="main-container">
		<ul class="breadcrumb">
			<li><a href="${pageContext.request.contextPath }/core/base/home"><i class="fa fa-home"></i>首页</a></li>
			<li class="active"><a href="javascript:void(0);"><i class="fa fa-key"></i>修改密码</a></li>
		</ul>
		<div class="panel panel-default">
			<div class="panel-heading"><i class="fa fa-key"></i>修改密码</div>
			<div class="panel-body">
				<form class="form-horizontal" id="changePasswordForm" name="changePasswordForm" action="/assist/change_password" >
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">原始密码：</label>
						<div class="col-sm-4">
							<input type="password" id="oldPassword" name="oldPassword" class="form-control" placeholder="请输入原始密码" 
								valid="r|l-l20" validTitle="原密码" validInfoArea="oldPassword_info_area" />
						</div>
						<div class="col-sm-6"><p class="help-block" id="oldPassword_info_area"></p></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">新密码：</label>
						<div class="col-sm-4">
							<input type="password" id="newPassword" name="newPassword" class="form-control" placeholder="请输入新密码" 
								valid="r|l-l20" validTitle="新密码" validInfoArea="newPassword_info_area" />
						</div>
						<div class="col-sm-6"><p class="help-block" id="newPassword_info_area"></p></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">重复新密码：</label>
						<div class="col-sm-4">
							<input type="password" id="newPassword2" name="newPassword2" class="form-control" placeholder="请输入重复新密码" 
								valid="r|l-l20" validTitle="重复新密码" validInfoArea="newPassword2_info_area" validPassword="newPassword" />
						</div>
						<div class="col-sm-6"><p class="help-block" id="newPassword2_info_area"></p></div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<sw:button accesskey="S" id="change_password" cssClass="btn btn-success" value="保存 (S)" icon="save" />
						</div>
					</div>
				</form>
			</div>
		</div>
		<div class="clearfix"></div>
	</div>
</body>
</html>
