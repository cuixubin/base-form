<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 编辑码表</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
	<script type="text/javascript">
	//定义返回URL
	var goBackUrl = '${pageContext.request.contextPath }/core/system/code_table/${c_type}/code_table';
	//返回
	function goBack(){
		sw.showProcessBar();
		window.location.href = goBackUrl;
	}
	//定义验证对象
	var codeTableValidator;
	//编辑码表
	function editCodeTable(){
		if(!codeTableValidator.validResult()){
			return;
		}
		if(!confirm('确定编辑此码表吗？')){
			return;
		}
		var url = '${pageContext.request.contextPath }/core/system/code_table/code_table/edit';
		var params = sw.getFormParams(document.forms[0]);
		sw.showProcessBar();
		sw.ajaxSubmit(url, params, function(data){
			sw.ajaxSuccessCallback(data, function(){
				codeTableValidator.reset(true);
			});
		});
	}
	//初始化表单验证
	$(function(){
		codeTableValidator = $.fn.dlshouwen.validator.init($('#codeTableForm'));
	});
	//绑定方法
	$(function(){
		$('#edit_code_table').bind('click', editCodeTable);
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
			<li><a href="${pageContext.request.contextPath }/core/system/code_table/${c_type}/code_table"><i class="fa fa-table"></i>码表管理 [ ${codeTableType.c_type } - ${codeTableType.c_info } ]</a></li>
			<li class="active"><a href="javascript:void(0);"><i class="fa fa-edit"></i>编辑码表</a></li>
		</ul>
		<div class="panel panel-default">
			<div class="panel-heading"><i class="fa fa-edit"></i>编辑码表</div>
			<div class="panel-body">
				<form:form cssClass="form-horizontal" id="codeTableForm" modelAttribute="codeTable" method="post">
					<form:hidden path="c_type" />
					<input type="hidden" id="oldKey" name="oldKey" value="${oldKey }" />
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">码表编号：</label>
						<div class="col-sm-4">
							<sw:input path="c_key" cssClass="form-control" placeholder="请输入码表编号" valid="r|l-l20" 
								validTitle="码表编号" validInfoArea="c_key_info_area" validUnique="CODE_TABLE_C_KEY_UNIQUE_FOR_EDIT|oldKey|c_type" />
						</div>
						<div class="col-sm-6"><p class="help-block" id="c_key_info_area"></p></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">码表名称：</label>
						<div class="col-sm-4"><sw:input path="c_value" cssClass="form-control" placeholder="请输入码表名称" valid="r|l-l200" validTitle="码表名称" validInfoArea="c_value_info_area" /></div>
						<div class="col-sm-6"><p class="help-block" id="c_value_info_area"></p></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">排序号：</label>
						<div class="col-sm-4"><sw:input path="sort" cssClass="form-control" placeholder="请输入排序号" valid="r|integer" validTitle="排序号" validInfoArea="sort_info_area" /></div>
						<div class="col-sm-6"><p class="help-block" id="sort_info_area"></p></div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<sw:button accesskey="S" id="edit_code_table" cssClass="btn btn-success" value="保存 (S)" icon="save" limit="core/system/code_table/-/code_table/-/edit" />
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
