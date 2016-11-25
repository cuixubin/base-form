<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 新增参数</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
	<script type="text/javascript">
	//定义返回URL
	var goBackUrl = '${pageContext.request.contextPath }/core/system/attr';
	//返回
	function goBack(){
		sw.showProcessBar();
		window.location.href = goBackUrl;
	}
	//定义验证对象
	var attrValidator;
	//新增参数
	function addAttr(){
		if(!attrValidator.validResult()){
			return;
		}
		if(!confirm('确定新增此参数吗？')){
			return;
		}
		var url = '${pageContext.request.contextPath }/core/system/attr/add';
		var params = sw.getFormParams(document.forms[0]);
		sw.showProcessBar();
		sw.ajaxSubmit(url, params, function(data){
			sw.ajaxSuccessCallback(data, function(){
				attrValidator.reset(true);
			});
		});
	}
	//初始化表单验证
	$(function(){
		attrValidator = $.fn.dlshouwen.validator.init($('#attrForm'));
	});
	//绑定方法
	$(function(){
		$('#add_attr').bind('click', addAttr);
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
			<li><a href="javascript:void(0);"><i class="fa fa-gears"></i>系统管理</a></li>
			<li><a href="${pageContext.request.contextPath }/core/system/attr"><i class="fa fa-wrench"></i>参数管理</a></li>
			<li class="active"><a href="javascript:void(0);"><i class="fa fa-plus"></i>新增参数</a></li>
		</ul>
		<div class="panel panel-default">
			<div class="panel-heading"><i class="fa fa-plus"></i>新增参数</div>
			<div class="panel-body">
				<form:form cssClass="form-horizontal" id="attrForm" modelAttribute="attr" method="post">
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">参数编号：</label>
						<div class="col-sm-4">
							<sw:input path="attr_code" cssClass="form-control" placeholder="请输入参数编号" valid="r|english_number|l-l80" 
								validTitle="参数编号" validInfoArea="attr_code_info_area" validUnique="ATTR_CODE_UNIQUE_FOR_ADD" />
						</div>
						<div class="col-sm-6"><p class="help-block" id="attr_code_info_area"></p></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">参数类型：</label>
						<div class="col-sm-4">
							<sw:select path="type" cssClass="form-control" items="${applicationScope.__CODE_TABLE__.attr_type }" 
								valid="r" validTitle="参数类型" validInfoArea="type_info_area" />
						</div>
						<div class="col-sm-6"><p class="help-block" id="type_info_area"></p></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">参数名称：</label>
						<div class="col-sm-4"><sw:input path="attr_name" cssClass="form-control" placeholder="请输入参数名称" valid="r|l-l40" validTitle="参数名称" validInfoArea="attr_name_info_area" /></div>
						<div class="col-sm-6"><p class="help-block" id="attr_name_info_area"></p></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">顺序号：</label>
						<div class="col-sm-4"><sw:input path="sort" cssClass="form-control" placeholder="请输入顺序号" valid="r|integer" validTitle="顺序号" validInfoArea="sort_info_area" /></div>
						<div class="col-sm-6"><p class="help-block" id="sort_info_area"></p></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">内容：</label>
						<div class="col-sm-4">
							<sw:textarea path="content" cssClass="form-control" placeholder="请输入内容" valid="r" validTitle="内容" validInfoArea="content_info_area" />
						</div>
						<div class="col-sm-6"><p class="help-block" id="content_info_area"></p></div>
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
							<sw:button accesskey="S" id="add_attr" cssClass="btn btn-success" value="保存 (S)" icon="save" limit="core/system/attr/add" />
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
