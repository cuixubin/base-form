<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 编辑角色</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
	<script type="text/javascript">
	//定义返回URL
	var goBackUrl = '${pageContext.request.contextPath }/core/system/role';
	//返回
	function goBack(){
		sw.showProcessBar();
		window.location.href = goBackUrl;
	}
	//定义验证对象
	var roleValidator;
	//编辑角色
	function editRole(){
		if(!roleValidator.validResult()){
			return;
		}
		if(!confirm('确定编辑此角色吗？')){
			return;
		}
		var url = '${pageContext.request.contextPath }/core/system/role/edit';
		var params = sw.getFormParams(document.forms[0]);
		sw.showProcessBar();
		sw.ajaxSubmit(url, params, function(data){
			sw.ajaxSuccessCallback(data, function(){
				roleValidator.reset(true);
			});
		});
	}
	//初始化表单验证
	$(function(){
		roleValidator = $.fn.dlshouwen.validator.init($('#roleForm'));
	});
	//绑定方法
	$(function(){
		$('#edit_role').bind('click', editRole);
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
			<li><a href="javascript:void(0);"><i class="fa fa-gears"></i>基础管理</a></li>
			<li><a href="${pageContext.request.contextPath }/core/system/role"><i class="fa fa-lock"></i>角色管理</a></li>
			<li class="active"><a href="javascript:void(0);"><i class="fa fa-edit"></i>编辑角色</a></li>
		</ul>
		<div class="panel panel-default">
			<div class="panel-heading"><i class="fa fa-edit"></i>编辑角色</div>
			<div class="panel-body">
				<form:form cssClass="form-horizontal" id="roleForm" modelAttribute="role" method="post">
					<form:hidden path="role_id" />
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">角色名称：</label>
						<div class="col-sm-4"><sw:input path="role_name" cssClass="form-control" placeholder="请输入角色名称" valid="r|l-l40" validTitle="角色名称" validInfoArea="role_name_info_area" /></div>
						<div class="col-sm-6"><p class="help-block" id="role_name_info_area"></p></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">备注：</label>
						<div class="col-sm-4">
							<sw:textarea path="remark" cssClass="form-control" placeholder="请输入备注" valid="l-l200" validTitle="备注" validInfoArea="remark_info_area"/>
						</div>
						<div class="col-sm-6"><p class="help-block" id="remark_info_area"></p></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">所属系统：</label>
						<div class="col-sm-4">
							<sw:select path="system_id" items="${systemList }" itemLabel="system_name" itemValue="system_id" emptyText="请选择..." 
									cssClass="form-control" valid="r" validTitle="所属系统" validInfoArea="system_id_info_area" />
						</div>
						<div class="col-sm-6"><p class="help-block" id="system_id_info_area"></p></div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<sw:button accesskey="S" id="edit_role" cssClass="btn btn-success" value="保存 (S)" icon="save" limit="core/system/role/-/edit" />
							<button accesskey="B" type="button" class="btn btn-default" onclick="goBack();">
								<i class="fa fa-arrow-circle-left"></i>返回 (B)
							</button>
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<div class="clearfix"></div>
	</div>
</body>
</html>
