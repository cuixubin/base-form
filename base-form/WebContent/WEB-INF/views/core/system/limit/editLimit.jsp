<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 编辑功能</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
	<script type="text/javascript">
	//定义返回URL
	var goBackUrl = '${pageContext.request.contextPath }/core/system/limit';
	//返回
	function goBack(){
		sw.showProcessBar();
		window.location.href = goBackUrl;
	}
	//定义验证对象
	var limitValidator;
	//编辑功能
	function editLimit(){
		if(!limitValidator.validResult()){
			return;
		}
		if(!confirm('确定编辑此功能吗？')){
			return;
		}
		var url = '${pageContext.request.contextPath }/core/system/limit/edit';
		var params = sw.getFormParams(document.forms[0]);
		sw.showProcessBar();
		sw.ajaxSubmit(url, params, function(data){
			sw.ajaxSuccessCallback(data, function(){
				limitValidator.reset(true);
			});
		});
	}
	$(function(){
		//初始化表单验证
		limitValidator = $.fn.dlshouwen.validator.init($('#limitForm'));
	});
	//绑定方法
	$(function(){
		$('#edit_limit').bind('click', editLimit);
	});
	//选择图标
	function selectFontAwesome(){
		var url = '${pageContext.request.contextPath }/resources/plugin/fontAwesome/jsp/selectFontAwesome.jsp';
		sw.openModal({
			id : 'selectFontAwesome',
			icon : 'image', 
			title : '选择图标',
			url : url,
			modalType : 'lg',
			callback : function(icon){
				$('#icon_fa').val(icon);
				closeModal('selectFontAwesome');
			}
		});
	}
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
			<li><a href="${pageContext.request.contextPath }/core/system/limit"><i class="fa fa-cubes"></i>功能管理</a></li>
			<li class="active"><a href="javascript:void(0);"><i class="fa fa-edit"></i>编辑功能</a></li>
		</ul>
		<div class="panel panel-default">
			<div class="panel-heading"><i class="fa fa-edit"></i>编辑功能</div>
			<div class="panel-body">
				<form:form cssClass="form-horizontal" id="limitForm" modelAttribute="limit" method="post">
					<form:hidden path="limit_id" />
					<form:hidden path="pre_limit_id" />
					<form:hidden path="sort" />
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">所属系统：</label>
						<div class="col-sm-4">
							<c:if test="${limit.pre_limit_id == 'top' }">
								<sw:select path="system_id" items="${systemList }" itemLabel="system_name" itemValue="system_id" emptyText="请选择..." 
									cssClass="form-control" valid="r" validTitle="所属系统" validInfoArea="system_id_info_area" />
							</c:if>
							<c:if test="${limit.pre_limit_id != 'top' }">
								<sw:select path="system_id" items="${systemList }" disabled="true" itemLabel="system_name" itemValue="system_id" emptyText="请选择..." 
									cssClass="form-control" valid="r" validTitle="所属系统" validInfoArea="system_id_info_area" />
							</c:if>
						</div>
						<div class="col-sm-6"><p class="help-block" id="system_id_info_area"></p></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">功能名称：</label>
						<div class="col-sm-4">
							<sw:input path="limit_name" cssClass="form-control" placeholder="请输入功能名称" valid="r|l-l40" validTitle="功能名称" validInfoArea="limit_name_info_area" />
						</div>
						<div class="col-sm-6"><p class="help-block" id="limit_name_info_area"></p></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">功能类型：</label>
						<div class="col-sm-4">
							<sw:radiobuttons path="limit_type" items="${applicationScope.__CODE_TABLE__.limit_type }" 
								valid="r" validTitle="功能类型" validInfoArea="limit_type_info_area" />
						</div>
						<div class="col-sm-6"><p class="help-block" id="limit_type_info_area"></p></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">是否异步：</label>
						<div class="col-sm-4">
							<sw:radiobuttons path="ifAsynch" items="${applicationScope.__CODE_TABLE__.zero_one }" 
								valid="r" validTitle="是否异步" validInfoArea="ifAsynch_info_area" />
						</div>
						<div class="col-sm-6"><p class="help-block" id="ifAsynch_info_area"></p></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">URL地址：</label>
						<div class="col-sm-4">
							<sw:input path="url" cssClass="form-control" placeholder="请输入URL地址" valid="r|l-l120" validTitle="URL地址" validInfoArea="url_info_area" />
						</div>
						<div class="col-sm-6"><p class="help-block" id="url_info_area"></p></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">图标：</label>
						<div class="col-sm-4">
							<div class="input-group">
								<sw:input path="icon_fa" cssClass="form-control" placeholder="请输入图标" 
									valid="l-l60" validTitle="图标 " validInfoArea="icon_fa_info_area" ondblclick="selectFontAwesome();" />
								<span class="input-group-addon" onclick="selectFontAwesome();"><i class="fa fa-image"></i></span>
							</div>
						</div>
						<div class="col-sm-6"><p class="help-block" id="icon_fa_info_area"></p></div>
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
							<sw:button accesskey="S" id="edit_limit" cssClass="btn btn-success" value="保存 (S)" icon="save" limit="core/system/limit/-/edit" />
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
