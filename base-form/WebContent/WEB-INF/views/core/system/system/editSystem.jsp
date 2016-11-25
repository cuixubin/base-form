<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 编辑系统</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
	<script type="text/javascript">
	//定义返回URL
	var goBackUrl = '${pageContext.request.contextPath }/core/system/system';
	//返回
	function goBack(){
		sw.showProcessBar();
		window.location.href = goBackUrl;
	}
	//定义验证对象
	var systemValidator;
	//新增码表类别
	function editSystem(){
		if(!systemValidator.validResult()){
			return;
		}
		if(!confirm('确定编辑此系统吗？')){
			return;
		}
		var url = '${pageContext.request.contextPath }/core/system/system/edit';
		var params = sw.getFormParams(document.forms[0]);
		sw.showProcessBar();
		sw.ajaxSubmit(url, params, function(data){
			sw.ajaxSuccessCallback(data, function(){
				systemValidator.reset(true);
			});
		});
	}
	//初始化表单验证
	$(function(){
		systemValidator = $.fn.dlshouwen.validator.init($('#systemForm'));
	});
	//绑定方法
	$(function(){
		$('#edit_system').bind('click', editSystem);
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
			<li><a href="${pageContext.request.contextPath }/core/system/system"><i class="fa fa-delicious"></i>系统管理</a></li>
			<li class="active"><a href="javascript:void(0);"><i class="fa fa-edit"></i>编辑系统</a></li>
		</ul>
		<div class="panel panel-default">
			<div class="panel-heading"><i class="fa fa-edit"></i>编辑系统</div>
			<div class="panel-body">
				<form:form cssClass="form-horizontal" id="systemForm" modelAttribute="system" method="post">
					<form:hidden path="system_id" />
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">系统编号：</label>
						<div class="col-sm-4">
							<sw:input path="system_code" cssClass="form-control" placeholder="请输入系统编号" valid="r|english_number|l-l20" 
								validTitle="系统编号" validInfoArea="system_code_info_area" validUnique="SYSTEM_CODE_UNIQUE_FOR_EDIT|system_id" />
						</div>
						<div class="col-sm-6"><p class="help-block" id="system_code_info_area"></p></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">系统名称：</label>
						<div class="col-sm-4"><sw:input path="system_name" cssClass="form-control" placeholder="请输入系统名称" valid="r|l-l40" validTitle="系统名称" validInfoArea="system_name_info_area" /></div>
						<div class="col-sm-6"><p class="help-block" id="system_name_info_area"></p></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">备注：</label>
						<div class="col-sm-4">
							<sw:textarea path="remark" cssClass="form-control" placeholder="请输入备注" valid="l-l200" validTitle="备注" validInfoArea="remark_info_area" />
						</div>
						<div class="col-sm-6"><p class="help-block" id="remark_info_area"></p></div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<sw:button accesskey="S" id="edit_system" cssClass="btn btn-success" value="保存 (S)" icon="save" limit="core/system/system/-/edit" />
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
