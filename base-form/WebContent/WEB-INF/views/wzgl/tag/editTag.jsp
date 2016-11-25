<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 编辑系统</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
        <style>
            #tag_url_area{width:650px;height:380px}
            #tag_styledescription_area{height:150px}
            #edit_tag{color: #333;background-color: #e6e6e6;border-color: #adadad;}
        </style>
	<script type="text/javascript">
	//定义返回URL
	var goBackUrl = '${pageContext.request.contextPath }/wzgl/tag/tag';
	//返回
	function goBack(){
		sw.showProcessBar();
		window.location.href = goBackUrl;
	}
	//定义验证对象
	var tagValidator;
	//新增码表类别
	function editTag(){
		if(!tagValidator.validResult()){
			return;
		}
		if(!confirm('确定编辑此系统吗？')){
			return;
		}
		var url = '${pageContext.request.contextPath }/wzgl/tag/tag/edit';
		var params = sw.getFormParams(document.forms[0]);
		sw.showProcessBar();
		sw.ajaxSubmit(url, params, function(data){
			sw.ajaxSuccessCallback(data, function(){
				tagValidator.reset(true);
			});
		});
	}
	//初始化表单验证
	$(function(){
		tagValidator = $.fn.dlshouwen.validator.init($('#tagForm'));
	});
	//绑定方法
	$(function(){
		$('#edit_tag').bind('click', editTag);
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
			<li><a href="javascript:void(0);"><i class="fa fa-book"></i>基础管理</a></li>
			<li><a href="${pageContext.request.contextPath }/wzgl/tag/tag"><i class="fa fa-tag"></i>标签管理</a></li>
			<li class="active"><a href="javascript:void(0);"><i class="fa fa-edit"></i>编辑标签</a></li>
		</ul>
		<div class="panel panel-default">
			<div class="panel-heading"><i class="fa fa-edit"></i>编辑标签</div>
			<div class="panel-body">
				<form:form cssClass="form-horizontal" id="tagForm" modelAttribute="tag" method="post">
					<form:hidden path="tag_id" />
                                        <form:hidden path="tag_addtime" />
                                        <form:hidden path="tag_adduser" />
                                        <form:hidden path="tag_edittime" />
                                        <form:hidden path="tag_edituser" />
                                        
					
                                        <div class="form-group">
                            <label class="col-sm-2 control-label text-right">标签名称：</label>
                            <div class="col-sm-4"><sw:input path="tag_name" cssClass="form-control" placeholder="请输入标签名称" valid="r|l-l60" validTitle="标签名称" validInfoArea="tag_name_info_area" /></div>
                            <div class="col-sm-6"><p class="help-block" id="tag_name_info_area"></p></div>
                        </div>


                        <div class="form-group">
                            <label class="col-sm-2 control-label text-right">标签状态：</label>
                            <div class="col-sm-4">
                                <sw:radiobuttons path="tag_state" items="${applicationScope.__CODE_TABLE__.open_close }" 
                                                 valid="r" validTitle="标签状态" validInfoArea="tag_state_info_area" />
                            </div>
                            <div class="col-sm-6"><p class="help-block" id="tag_state_info_area"></p></div>
                        </div>

                            <div class="form-group">
                            <label class="col-sm-2 control-label text-right">标签格式说明：</label>
                            <div class="col-sm-4">
                                <sw:textarea path="tag_styledescription" cssClass="form-control"  placeholder="请输入标签格式说明" valid="r"  validTitle="标签格式说明" validInfoArea="tag_styledescription_info_area" id="tag_styledescription_area"  />
                            </div>
                            <div class="col-sm-6"><p class="help-block" id="tag_styledescription_info_area"></p></div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label text-right">标签内容：</label>
                            <div class="col-sm-7">
                                <sw:textarea path="tag_url" cssClass="form-control" placeholder="请输入标签内容" valid="r" validTitle="标签内容"  validInfoArea="tag_url_info_area" id="tag_url_area"  />
                            </div>
                            <div class="col-sm-3"><p class="help-block" id="tag_url_info_area"></p></div>
                        </div>


                       <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <button accesskey="C" type="button" class="btn btn-default" onclick="" style="background-color: #5cb85c;border-color: #4cae4c;">
                                    <i class="fa fa-eye" ></i>预览 (C)
                                </button>
                                <sw:button accesskey="S" id="edit_tag" cssClass="btn btn-success" value="保存 (S)" icon="save" limit="wzgl/tag/tag/-/edit" />
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
