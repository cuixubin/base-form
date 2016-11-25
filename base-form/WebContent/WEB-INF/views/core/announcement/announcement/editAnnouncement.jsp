<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 编辑公告</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
	<%-- UEditor --%>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath }/resources/plugin/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath }/resources/plugin/ueditor/ueditor.all.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath }/resources/plugin/ueditor/lang/zh-cn/zh-cn.js"></script>
	<script type="text/javascript">
	//定义返回URL
	var goBackUrl = '${pageContext.request.contextPath }/core/announcement/announcement';
	//返回
	function goBack(){
		sw.showProcessBar();
		window.location.href = goBackUrl;
	}
	//定义验证对象
	var announcementValidator;
	//编辑公告
	function editAnnouncement(){
		if(!announcementValidator.validResult()){
			return;
		}
		if(!confirm('确定编辑此公告吗？')){
			return;
		}
		var url = '${pageContext.request.contextPath }/core/announcement/announcement/edit';
		var params = sw.getFormParams(document.forms[0]);
		params.content = ue.getContent();
		sw.showProcessBar();
		sw.ajaxSubmit(url, params, function(data){
			sw.ajaxSuccessCallback(data, function(){
				announcementValidator.reset(true);
			});
		});
	}
	//初始化表单验证
	$(function(){
		announcementValidator = $.fn.dlshouwen.validator.init($('#announcementForm'));
	});
	//绑定方法
	$(function(){
		$('#edit_announcement').bind('click', editAnnouncement);
	});
	//初始化UEditor
	var ue;
	$(function(){
		$('#content').height('400px');
		ue = UE.getEditor('content');
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
			<li><a href="javascript:void(0);"><i class="fa fa-bell"></i>公告管理</a></li>
			<li><a href="${pageContext.request.contextPath }/core/announcement/announcement"><i class="fa fa-bell"></i>公告管理</a></li>
			<li class="active"><a href="javascript:void(0);"><i class="fa fa-edit"></i>编辑公告</a></li>
		</ul>
		<form:form id="announcementForm" modelAttribute="announcement" method="post">
			<form:hidden path="announcement_id" />
			<div class="panel panel-default">
				<div class="panel-heading"><i class="fa fa-edit"></i>编辑公告</div>
				<div class="panel-body">
					<div class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-2 control-label text-right">公告主题：</label>
							<div class="col-sm-4">
								<sw:input path="title" cssClass="form-control" placeholder="请输入公告主题" valid="r|l-l200" validTitle="公告主题" validInfoArea="title_info_area" />
							</div>
							<div class="col-sm-6"><p class="help-block" id="title_info_area"></p></div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label text-right">状态：</label>
							<div class="col-sm-4">
								<sw:radiobuttons path="status" items="${applicationScope.__CODE_TABLE__.open_close }" 
									valid="r" validTitle="状态" validInfoArea="status_info_area" />
							</div>
							<div class="col-sm-6"><p class="help-block" id="status_info_area"></p></div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<sw:button accesskey="S" id="edit_announcement" cssClass="btn btn-success" value="保存 (S)" icon="save" limit="core/announcement/announcement/-/edit" />
								<button accesskey="B" type="button" class="btn btn-default" onclick="goBack();">
									<i class="fa fa-arrow-circle-left"></i>返回 (B)
								</button>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading"><i class="fa fa-pencil"></i>公告内容</div>
				<div class="panel-body" style="padding:0;">
					<sw:textarea path="content" />
				</div>
			</div>
		</form:form>
		<div class="clearfix"></div>
	</div>
</body>
</html>
