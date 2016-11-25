<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 设置个人参数</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
	<style type="text/css">
	ul.skin{float:left;padding:0;list-style-type:none;}
	ul.skin>li{float:left;padding:0;margin-right:10px;}
	ul.skin>li>a{padding:2px;border:1px solid white;float:left;}
	ul.skin>li>a:hover{border:1px solid #d0d0d0;}
	ul.skin>li.active>a{border:1px solid #aaaaaa;padding:2px;}
	</style>
	<script type="text/javascript">
	//定义验证对象
	var userAttrValidator;
	//执行保存用户参数信息
	function setUserAttr(){
		if(!userAttrValidator.validResult()){
			return;
		}
		if(!confirm('确定保存用户参数吗？')){
			return;
		}
		var url = '${pageContext.request.contextPath }/core/base/assist/set_user_attr';
		var params = sw.getFormParams(document.forms[0]);
		params.skin_info = $('ul.skin>li.active').attr('skin_info');
		sw.showProcessBar();
		sw.ajaxSubmit(url, params, function(data){
			sw.ajaxSuccessCallback(data, function(){
				userAttrValidator.reset(true);
			});
			$('#skin_css').attr('href', '${pageContext.request.contextPath }/core/base/skin');
		});
	}
	//选择皮肤
	function selectSkin(skinInfo){
		$('ul.skin>li.active').removeClass('active');
		$('ul.skin>li').each(function(){
			if($(this).attr('skin_info')==skinInfo){
				$(this).addClass('active');
				return false;
			}
		});
	}
	//初始化皮肤选择
	$(function(){
		$('ul.skin>li').each(function(){
			if($(this).attr('skin_info')=='${userAttr.skin_info}'){
				$(this).addClass('active');
				return false;
			}
		});
		userAttrValidator = $.fn.dlshouwen.validator.init($('#userAttrForm'));
	});
	//初始化表单验证
	$(function(){
		userAttrValidator = $.fn.dlshouwen.validator.init($('#userAttrForm'));
	});
	//绑定方法
	$(function(){
		$('#set_user_attr').bind('click', setUserAttr);
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
			<li class="active"><a href="javascript:;"><i class="fa fa-gears"></i>参数设置</a></li>
		</ul>
		<div class="panel panel-default">
			<div class="panel-heading"><i class="fa fa-gears"></i>设置个人参数</div>
			<div class="panel-body">
				<form:form cssClass="form-horizontal" id="userAttrForm" modelAttribute="userAttr" method="post">
					<form:hidden path="user_id" />
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">使用皮肤：</label>
						<div class="col-sm-4">
							<ul class="skin">
								<c:forEach items="${skinList }" var="skin">
									<li skin_info="${skin}"><a href="javascript:;" onclick="selectSkin('${skin}');"><img src="${pageContext.request.contextPath }/${skinRootPath }/small/${skin}" /></a>
								</c:forEach>
							</ul>
						</div>
						<div class="col-sm-6"><p class="help-block" id="user_code_info_area"></p></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">显示快捷菜单：</label>
						<div class="col-sm-4">
							<sw:radiobuttons path="is_show_shortcut" items="${applicationScope.__CODE_TABLE__.zero_one }" 
								valid="r" validTitle="有效标识" validInfoArea="is_show_shortcut_info_area" />
						</div>
						<div class="col-sm-6"><p class="help-block" id="is_show_shortcut_info_area"></p></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">背景漂浮：</label>
						<div class="col-sm-4">
							<sw:radiobuttons path="is_background_float" items="${applicationScope.__CODE_TABLE__.zero_one }" 
								valid="r" validTitle="背景漂浮" validInfoArea="is_background_float_info_area" />
						</div>
						<div class="col-sm-6"><p class="help-block" id="is_background_float_info_area"></p></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">背景漂浮速度：</label>
						<div class="col-sm-4">
							<sw:input path="background_float_speed" cssClass="form-control" placeholder="请输入背景漂浮速度" 
								valid="r|integer" validTitle="背景漂浮速度" validInfoArea="background_float_speed_info_area" />
						</div>
						<div class="col-sm-6"><p class="help-block" id="background_float_speed_info_area"></p></div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<button type="button" class="btn btn-success" id="set_user_attr" accesskey="S"><i class="fa fa-save"></i>保存 (S)</button>
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<div class="clearfix"></div>
	</div>
</body>
</html>
