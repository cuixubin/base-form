<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 草稿箱</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
	<%
	//通过静态域设置码制映射对象
	CodeTableUtils.createCodeTableJS(application, out, "mail_send_status");
	CodeTableUtils.createCodeTableJS(application, out, "mail_receive_status");
	CodeTableUtils.createCodeTableJS(application, out, "mail_delete_from");
	%>
	<script type="text/javascript">
	var gridColumns = [
		{id:'operation', title:'操作', columnClass:'text-center', columnStyle:'width:100px;', hideType:'xs', extra:false, 'export':false, print:false, advanceQuery:false, resolution:function(value, record, column, grid, dataNo, columnNo){
			var content = '';
			content += '<button type="button" class="btn btn-xs btn-icon btn-default" title="查看" onclick="viewMailSingle(\''+record.mail_id+'\')"><i class="fa fa-eye"></i></button>';
			content += '&nbsp;&nbsp;';
			content += '<button type="button" class="btn btn-xs btn-icon btn-primary" title="编辑" onclick="editDraftMailSingle(\''+record.mail_id+'\')"><i class="fa fa-edit"></i></button>';
			content += '&nbsp;&nbsp;';
			content += '<button type="button" class="btn btn-xs btn-icon btn-danger" title="删除" onclick="deleteMailForReceiveSingle(\''+record.mail_id+'\')"><i class="fa fa-trash-o"></i></button>';
			return content;
		}},
		{id:'title', title:'邮件主题', type:'string', hideType:'', columnClass:'text-center', fastQuery:true, fastQueryType:'lk' },
		{id:'receiver_name', title:'收件人', type:'string', columnClass:'text-center', hideType:'xs|sm', fastQuery:true, fastQueryType:'lk', resolution:function(value, record, column, grid, dataNo, columnNo){
			if(record.receiver_count==null||record.receiver_count=='0')
				return '未指定';
			else if(record.receiver_count=='1')
				return record.receiver_name;
			else
				return record.receiver_name + ' 等 ' + record.receiver_count + ' 人';
		}},
		{id:'create_time', title:'创建时间', type:'date', format:'yyyy-MM-dd hh:mm:ss', hideType:'xs', columnClass:'text-center', fastQuery:true, fastQueryType:'range' }
	];
	var gridOption={
		loadURL : '${pageContext.request.contextPath }/core/mail/draft/list',
		functionCode : 'CORE_MAIL_DRAFT',
		check:true,
		exportFileName : '草稿箱列表',
		columns : gridColumns,
		onRowDblClick : function(value, record, column, grid, dataNo, columnNo, cell, row, extraCell, e){
			viewMailSingle(record.mail_id);
		}
	};
	var grid = $.fn.dlshouwen.grid.init(gridOption);;
	//数据加载
	$(function(){
		grid.load();
	});
	//添加跳转
	function addMail(){
		var url='${pageContext.request.contextPath }/core/mail/add';
		sw.updateGrid({
			id : 'addMail',
			icon : 'plus',
			title : '新增邮件',
			url : url,
			height : 640,
			modalType : 'lg'
		}, grid);
	}
	//查看跳转
	function viewMail(){
		var recordObj = grid.getCheckedRecords();
		if(recordObj.length==0){
			sw.toast('请选择要查看的邮件！', 'warning', 3000);
			return false;
		}
		if(recordObj.length>1){
			sw.toast('只能选择一个要查看的邮件！', 'warning', 3000);
			return false;
		}
		var mailId = recordObj[0].mail_id;
		var url='${pageContext.request.contextPath }/core/mail/'+mailId+'/view';
		sw.updateGrid({
			id : 'viewMail',
			icon : 'envelope-o',
			title : '查看邮件',
			url : url,
			height : 640,
			modalType : 'lg'
		}, grid);
	}
	//查看跳转
	function viewMailSingle(mailId){
		var url='${pageContext.request.contextPath }/core/mail/'+mailId+'/view?receiverRead=true';
		sw.updateGrid({
			id : 'viewMail',
			icon : 'envelope-o',
			title : '查看邮件',
			url : url,
			height : 640,
			modalType : 'lg'
		}, grid);
	}
	//编辑草稿
	function editDraftMail(){
		var recordObj = grid.getCheckedRecords();
		if(recordObj.length==0){
			sw.toast('请选择要编辑的草稿！', 'warning', 3000);
			return false;
		}
		if(recordObj.length>1){
			sw.toast('只能选择一个要编辑的草稿！', 'warning', 3000);
			return false;
		}
		var mailId = recordObj[0].mail_id;
		var url='${pageContext.request.contextPath }/core/mail/draft/'+mailId+'/edit';
		sw.updateGrid({
			id : 'editDraft',
			icon : 'edit',
			title : '编辑草稿',
			url : url,
			height : 640,
			modalType : 'lg'
		}, grid);
	}
	//编辑草稿
	function editDraftMailSingle(mailId){
		var url='${pageContext.request.contextPath }/core/mail/draft/'+mailId+'/edit';
		sw.updateGrid({
			id : 'editDraft',
			icon : 'edit',
			title : '编辑草稿',
			url : url,
			height : 640,
			modalType : 'lg'
		}, grid);
	}
	//删除操作
	function deleteMailForDraft(){
		var recordObj = grid.getCheckedRecords();
		if(recordObj.length==0){
			sw.toast('请选择要删除的邮件！', 'warning', 3000);
			return false;
		}
		var mailIds = '';
		for(var i=0;i<recordObj.length;i++){
			mailIds += recordObj[i].mail_id+((i!=recordObj.length-1)?',':'');
		}
		if(!confirm('确认要删除当前邮件吗？')){
			return;
		}
		url = '${pageContext.request.contextPath }/core/mail/delete/draft';
		var params = new Object();
		params.mailIds = mailIds;
		sw.showProcessBar();
		sw.ajaxSubmitRefreshGrid(url, params, grid);
	}
	//删除操作
	function deleteMailForDraftSingle(mailId){
		if(!confirm('确认要删除此邮件吗？')){
			return;
		}
		url = '${pageContext.request.contextPath }/core/mail/delete/draft';
		var params = new Object();
		params.mailIds = mailId;
		sw.showProcessBar();
		sw.ajaxSubmitRefreshGrid(url, params, grid);
	}
	//初始化绑定方法
	$(function(){
		$('#add_mail').bind('click', addMail);
		$('#view_mail').bind('click', viewMail);
		$('#edit_draft_mail').bind('click', editDraftMail);
		$('#delete_mail_for_draft').bind('click', deleteMailForDraft);
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
			<li><a href="javascript:void(0);"><i class="fa fa-envelope-o"></i>邮件管理</a></li>
			<li class="active"><a href="javascript:void(0);"><i class="fa fa-edit"></i>草稿箱</a></li>
		</ul>
		<div class="panel panel-default">
			<div class="panel-heading"><i class="fa fa-edit"></i>草稿箱</div>
			<div class="panel-operation">
				<div class="btn-group">
					<sw:button accesskey="A" id="add_mail" value="新建 (A)" cssClass="btn btn-primary" icon="plus" />
					<sw:button accesskey="V" id="view_mail" value="查看 (V)" cssClass="btn btn-default" icon="eye" />
					<sw:button accesskey="M" id="edit_draft_mail" value="编辑 (M)" cssClass="btn btn-primary" icon="edit" />
					<sw:button accesskey="W" id="delete_mail_for_draft" value="删除 (W)" cssClass="btn btn-danger" icon="trash-o" />
				</div>
			</div>
			<div id="grid_container" class="dlshouwen-grid-container"></div>
			<div id="grid_toolbar_container" class="dlshouwen-grid-toolbar-container"></div>
		</div>
		<div class="clearfix"></div>
	</div>
</html>