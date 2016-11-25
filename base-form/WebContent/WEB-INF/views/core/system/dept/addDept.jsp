<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 新增部门</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
	<script type="text/javascript">
	//定义返回URL
	var goBackUrl = '${pageContext.request.contextPath }/core/system/dept';
	//返回
	function goBack(){
		sw.showProcessBar();
		window.location.href = goBackUrl;
	}
	//定义验证对象
	var deptValidator;
	//新增部门
	function addDept(){
		if(!deptValidator.validResult()){
			return;
		}
		if(!confirm('确定新增此部门吗？')){
			return;
		}
		var url = '${pageContext.request.contextPath }/core/system/dept/add';
		var params = sw.getFormParams(document.forms[0]);
		sw.showProcessBar();
		sw.ajaxSubmit(url, params, function(data){
			sw.ajaxSuccessCallback(data, function(){
				deptValidator.reset(true);
			});
		});
	}
	$(function(){
		//初始化表单验证
		deptValidator = $.fn.dlshouwen.validator.init($('#deptForm'));
	});
	//绑定方法
	$(function(){
		$('#add_dept').bind('click', addDept);
	});
	//选择用户
	function selectUser(){
		var url = '${pageContext.request.contextPath }/core/system/user/select/radio';
		sw.openModal({
			id : 'selectUser',
			icon : 'user', 
			title : '选择用户',
			url : url,
			callback : function(users){
				var userName = '';
				var phone = '';
				if(users[0]){
					userName = users[0].user_name;
					phone = users[0].phone;
				}
				$('#principal').val(userName);
				$('#principal_phone').val(phone);
				sw.closeModal('selectUser');
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
			<li><a href="${pageContext.request.contextPath }/core/system/dept"><i class="fa fa-building"></i>部门管理</a></li>
			<li class="active"><a href="javascript:void(0);"><i class="fa fa-plus"></i>新增部门</a></li>
		</ul>
		<div class="panel panel-default">
			<div class="panel-heading"><i class="fa fa-plus"></i>新增部门</div>
			<div class="panel-body">
				<form:form cssClass="form-horizontal" id="deptForm" modelAttribute="dept" method="post">
					<form:hidden path="pre_dept_id" />
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">部门名称：</label>
						<div class="col-sm-4">
							<sw:input path="dept_name" cssClass="form-control" placeholder="请输入部门名称" valid="r|l-l40" validTitle="部门名称" validInfoArea="dept_name_info_area" />
						</div>
						<div class="col-sm-6"><p class="help-block" id="dept_name_info_area"></p></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">联系电话：</label>
						<div class="col-sm-4">
							<sw:input path="phone" cssClass="form-control" placeholder="请输入联系电话" valid="r|l-l20" validTitle="联系电话" validInfoArea="phone_info_area" />
						</div>
						<div class="col-sm-6"><p class="help-block" id="phone_info_area"></p></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">负责人：</label>
						<div class="col-sm-4">
							<div class="input-group">
								<sw:input path="principal" cssClass="form-control" placeholder="请输入负责人"
									valid="r|l-l40" validTitle="负责人" validInfoArea="principal_info_area" ondblclick="selectUser();" />
								<span class="input-group-addon" onclick="selectUser();"><i class="fa fa-user"></i></span>
							</div>
						</div>
						<div class="col-sm-6"><p class="help-block" id="principal_info_area"></p></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">负责人电话：</label>
						<div class="col-sm-4">
							<sw:input path="principal_phone" cssClass="form-control" placeholder="请输入负责人电话" valid="r|l-l20" validTitle="负责人电话" validInfoArea="principal_phone_info_area" />
						</div>
						<div class="col-sm-6"><p class="help-block" id="principal_phone_info_area"></p></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">备注：</label>
						<div class="col-sm-4">
							<sw:textarea path="remark" cssClass="form-control" placeholder="请输入备注" valid="l-l200" validTitle="备注" validInfoArea="remark_info_area" />
						</div>
						<div class="col-sm-6"><p class="help-block" id="remark_info_area"></p></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">排序号：</label>
						<div class="col-sm-4">
							<sw:input path="sort" cssClass="form-control" placeholder="请输入排序号" valid="r|integer" validTitle="排序号" validInfoArea="sort_info_area" />
						</div>
						<div class="col-sm-6"><p class="help-block" id="sort_info_area"></p></div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<sw:button accesskey="S" id="add_dept" cssClass="btn btn-success" value="保存 (S)" icon="save" limit="core/system/dept/add" />
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
