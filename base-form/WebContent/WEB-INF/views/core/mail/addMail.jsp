<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 新增邮件</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
	<%-- UEditor --%>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath }/resources/plugin/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath }/resources/plugin/ueditor/ueditor.all.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath }/resources/plugin/ueditor/lang/zh-cn/zh-cn.js"></script>
	<style type="text/css">
	body{background:none;}
	</style>
	<script type="text/javascript">
	//自适应高度
	var ue;
	$(function(){
		ue = UE.getEditor('content',{initialFrameWidth: null});
		ue.addListener( 'ready', function( editor ) {
			resizeMain();
		});
		$(window).bind('resize', resizeMain);
	});
	function resizeMain(){
		var w_height = $(window).height()-315;
		ue.setHeight(w_height);
	};
	//选择用户
	function selectUsers(){
		var url='${pageContext.request.contextPath }/core/system/user/select/checkbox';
		sw.openModal({
			id : 'selectUsers',
			icon : 'check', 
			title : '选择用户',
			url : url,
			height : 320, 
			callback : function(users){
				sw.closeModal('selectUsers');
				if(users&&users.length>0){
					var userIds = '';
					var userNames = '';
					for(var i=0; i<users.length; i++){
						var user = users[i];
						userIds += user.user_id + ((i!=users.length-1)?',':'');
						userNames += user.user_name + ((i!=users.length-1)?',':'');
					}
					$('#receivers').val(userIds);
					$('#receiver_names').val(userNames);
				}else{
					$('#receivers').val('');
					$('#receiver_names').val('');
				}
			}
		});
	}
	//保存草稿
	function addDraftMessage(){
		var url = '${pageContext.request.contextPath }/core/mail/draft/save';
		var params = sw.getFormParams(document.forms[0]);
		params.content = ue.getContent();
		sw.showProcessBar();
		sw.ajaxSubmit(url, params, function(data){
			sw.ajaxSuccessCallback(data);
		});
	}
	//发送
	function sendMessage(){
		if(!confirm('确定发送此邮件吗？')){
			return;
		}
		var url = '${pageContext.request.contextPath }/core/mail/send';
		var params = sw.getFormParams(document.forms[0]);
		params.content = ue.getContent();
		sw.showProcessBar();
		sw.ajaxSubmit(url, params, function(data){
			sw.ajaxSuccessCallback(data);
		});
	}
	//绑定方法
	$(function(){
		$('#add_draft_message').bind('click', addDraftMessage);
		$('#send_message').bind('click', sendMessage);
	});
	</script>
</head>
<body>
	<form action="/mail/draft/save" id="mailForm" enctype="multipart/form-data">
		<input type="hidden" id="mail_id" name="mail_id" value="${mail.mail_id }" />
		<div class="panel panel-default">
			<div class="panel-heading"><i class="fa fa-envelope-o"></i>邮件信息</div>
			<div class="panel-body">
				<div class="form-horizontal">
					<div class="form-group">
						<label class="col-xs-2 control-label text-right">收件人：</label>
						<div class="col-xs-10">
							<input type="hidden" id="receivers" name="receivers" />
							<div class="input-group">
								<input type="text" id="receiver_names" name="receiver_names" class="form-control" placeholder="请输入收件人" 
									valid="r" readonly="readonly" ondblclick="selectUsers();" />
								<span class="input-group-addon" onclick="selectUsers();"><i class="fa fa-users"></i></span>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-2 control-label text-right">主题：</label>
						<div class="col-xs-10">
							<input type="text" id="title" name="title" class="form-control" placeholder="请输入主题" valid="l-l200" />
						</div>
					</div>
					<div class="form-group">
						<div class="col-xs-offset-2 col-xs-10">
							<sw:button id="add_draft_message" accesskey="A" cssClass="btn btn-success" value="保存草稿 (A)" icon="save" />
							<sw:button id="send_message" accesskey="S" cssClass="btn btn-success" value="发送邮件 (S)" icon="paper-plane-o" />
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading"><i class="fa fa-file-text-o"></i>邮件内容</div>
			<div class="panel-body" style="padding:0px;">
				<textarea id="content" name="content"></textarea>
			</div>
		</div>
	</form>
	<div class="clearfix"></div>
</body>
</html>
