<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 编辑码表类别</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
	<script type="text/javascript">
	//定义返回URL
	var goBackUrl = '${pageContext.request.contextPath }/core/system/code_table';
	//返回
	function goBack(){
		sw.showProcessBar();
		window.location.href = goBackUrl;
	}
	//定义验证对象
	var codeTableTypeValidator;
	//编辑码表类别
	function editCodeTableType(){
		if(!codeTableTypeValidator.validResult()){
			return;
		}
		if(!confirm('确定编辑此码表类别吗？')){
			return;
		}
		var url = '${pageContext.request.contextPath }/core/system/code_table/code_table_type/edit';
		var params = sw.getFormParams(document.forms[0]);
		sw.showProcessBar();
		sw.ajaxSubmit(url, params, function(data){
			sw.ajaxSuccessCallback(data, function(){
				codeTableTypeValidator.reset(true);
			});
		});
	}
	//初始化表单验证
	$(function(){
		codeTableTypeValidator = $.fn.dlshouwen.validator.init($('#codeTableTypeForm'));
	});
	//绑定方法
	$(function(){
		$('#edit_code_table_type').bind('click', editCodeTableType);
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
			<li><a href="${pageContext.request.contextPath }/core/system/code_table"><i class="fa fa-table"></i>码表类型管理</a></li>
			<li class="active"><a href="javascript:void(0);"><i class="fa fa-edit"></i>编辑码表类型</a></li>
		</ul>
		<div class="panel panel-default">
			<div class="panel-heading"><i class="fa fa-edit"></i>编辑码表类型</div>
			<div class="panel-body">
				<form:form cssClass="form-horizontal" id="codeTableTypeForm" modelAttribute="codeTableType" method="post">
					<input type="hidden" id="oldType" name="oldType" value="${oldType }" />
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">码表类别编号：</label>
						<div class="col-sm-4">
							<sw:input path="c_type" cssClass="form-control" placeholder="请输入码表类别编号" valid="r|english_number|l-l20" 
								validTitle="码表类别编号" validInfoArea="c_type_info_area" validUnique="CODE_TABLE_C_TYPE_UNIQUE_FOR_EDIT|oldType" />
						</div>
						<div class="col-sm-6"><p class="help-block" id="c_type_info_area"></p></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">码表类别名称：</label>
						<div class="col-sm-4"><sw:input path="c_info" cssClass="form-control" placeholder="请输入码表类别名称" valid="r|l-l40" validTitle="码表类别名称" validInfoArea="c_info_info_area" /></div>
						<div class="col-sm-6"><p class="help-block" id="c_info_info_area"></p></div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<sw:button accesskey="S" id="edit_code_table_type" cssClass="btn btn-success" value="保存 (S)" icon="save" limit="core/system/code_table/code_table_type/-/edit" />
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
