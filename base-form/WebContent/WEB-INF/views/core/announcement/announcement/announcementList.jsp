<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 公告管理</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
	<%
	//通过静态域设置码制映射对象
	CodeTableUtils.createCodeTableJS(application, out, "open_close");
	%>
	<script type="text/javascript">
	var gridColumns = [
		{id:'operation', title:'操作', columnClass:'text-center', columnStyle:'width:100px;', hideType:'xs', extra:false, 'export':false, print:false, advanceQuery:false, resolution:function(value, record, column, grid, dataNo, columnNo){
			var content = '';
			content += '<button type="button" class="btn btn-xs btn-icon btn-primary" title="编辑" limit="core/announcement/announcement/-/edit" onclick="editAnnouncementSingle(\''+record.announcement_id+'\')"><i class="fa fa-edit"></i></button>';
			content += '&nbsp;&nbsp;';
			if(record.status=='1'){
				content += '<button type="button" class="btn btn-xs btn-icon btn-warning" title="禁用" limit="core/announcement/announcement/close" onclick="closeAnnouncementSingle(\''+record.announcement_id+'\')"><i class="fa fa-minus"></i></button>';
			}else{
				content += '<button type="button" class="btn btn-xs btn-icon btn-success" title="启用" limit="core/announcement/announcement/open" onclick="openAnnouncementSingle(\''+record.announcement_id+'\')"><i class="fa fa-check"></i></button>';
			}
			content += '&nbsp;&nbsp;';
			content += '<button type="button" class="btn btn-xs btn-icon btn-danger" title="删除" limit="core/announcement/announcement/delete" onclick="deleteAnnouncementSingle(\''+record.announcement_id+'\')"><i class="fa fa-trash-o"></i></button>';
			return content;
		}},
		{id:'title', title:'公告主题', type:'string', fastQuery:true, fastQueryType:'lk' },
		{id:'status', title:'状态', type:'string', codeTable:code_table_info['open_close'], columnClass:'text-center', fastQuery:true, fastQueryType:'eq', resolution:function(value, record, column, grid, dataNo, columnNo){
			return value=='0'?('<span class="label label-danger">'+code_table_info['open_close'][value]+'</span>'):('<span class="label label-success">'+code_table_info['open_close'][value]+'</span>');
		}},
		{id:'creator_name', title:'创建人', type:'string', columnClass:'text-center', hideType:'xs|sm|md|lg', fastQuery:true, fastQueryType:'lk' },
		{id:'create_time', title:'创建时间', type:'date', format:'yyyy-MM-dd hh:mm:ss', columnClass:'text-center', hideType:'xs|sm|md|lg', fastQuery:true, fastQueryType:'range' },
		{id:'editor_name', title:'编辑人', type:'string', columnClass:'text-center', hideType:'xs|sm|md|lg', fastQuery:true, fastQueryType:'lk' },
		{id:'edit_time', title:'编辑时间', type:'date', format:'yyyy-MM-dd hh:mm:ss', columnClass:'text-center', hideType:'xs|sm|md|lg', fastQuery:true, fastQueryType:'range' }
	];
	var gridOption = {
		loadURL : '${pageContext.request.contextPath }/core/announcement/announcement/list',
		functionCode : 'CORE_ANNOUNCEMENT',
		check:true,
		exportFileName : '公告列表',
		columns : gridColumns,
		onRowDblClick : function(value, record, column, grid, dataNo, columnNo, cell, row, extraCell, e){
			editAnnouncementSingle(record.announcement_id);
		},
		onGridComplete : function(value, record, column, grid, dataNo, columnNo, cell, row, extraCell, e){
			var urls = '';
			urls += 'core/announcement/announcement/-/edit,';
			urls += 'core/announcement/announcement/open,';
			urls += 'core/announcement/announcement/close,';
			urls += 'core/announcement/announcement/delete';
			sw.initGridButtonLimit(urls, '${applicationScope.__SYSTEM_ATTRIBUTE__.show_button_when_no_limit.content }');
		}
	};
	var grid = $.fn.dlshouwen.grid.init(gridOption);;
	//数据加载
	$(function(){
		grid.load();
	});
	//跳转添加
	function addAnnouncement(){
		var url = '${pageContext.request.contextPath }/core/announcement/announcement/add';
		sw.showProcessBar();
		window.location.href = url;
	}
	//启用操作
	function openAnnouncement(){
		var recordObj = grid.getCheckedRecords();
		if(recordObj.length==0){
			sw.toast('请选择要启用的公告！', 'warning', 3000);
			return false;
		}
		if(!confirm('确定启用选中公告吗？')){
			return;
		}
		var announcementIds = '';
		for(var i=0;i<recordObj.length;i++){
			announcementIds += recordObj[i].announcement_id + ((i!=recordObj.length-1)?',':'');
		}
		var url = '${pageContext.request.contextPath }/core/announcement/announcement/open';
		var params = new Object();
		params.announcementIds = announcementIds;
		sw.showProcessBar();
		sw.ajaxSubmitRefreshGrid(url, params, grid);
	}
	//启用操作
	function openAnnouncementSingle(announcementId){
		if(!confirm('确定启用此公告吗？')){
			return;
		}
		var url = '${pageContext.request.contextPath }/core/announcement/announcement/open';
		var params = new Object();
		params.announcementIds = announcementId;
		sw.showProcessBar();
		sw.ajaxSubmitRefreshGrid(url, params, grid);
	}
	//禁用操作
	function closeAnnouncement(){
		var recordObj = grid.getCheckedRecords();
		if(recordObj.length==0){
			sw.toast('请选择要禁用的公告！', 'warning', 3000);
			return false;
		}
		if(!confirm('确定禁用选中公告吗？')){
			return;
		}
		var announcementIds = '';
		for(var i=0;i<recordObj.length;i++){
			announcementIds += recordObj[i].announcement_id + ((i!=recordObj.length-1)?',':'');
		}
		var url = '${pageContext.request.contextPath }/core/announcement/announcement/close';
		var params = new Object();
		params.announcementIds = announcementIds;
		sw.showProcessBar();
		sw.ajaxSubmitRefreshGrid(url, params, grid);
	}
	//禁用操作
	function closeAnnouncementSingle(announcementId){
		if(!confirm('确定禁用此公告吗？')){
			return;
		}
		var url = '${pageContext.request.contextPath }/core/announcement/announcement/close';
		var params = new Object();
		params.announcementIds = announcementId;
		sw.showProcessBar();
		sw.ajaxSubmitRefreshGrid(url, params, grid);
	}
	//删除操作
	function deleteAnnouncement(){
		var recordObj = grid.getCheckedRecords();
		if(recordObj.length==0){
			sw.toast('请选择要删除的公告！', 'warning', 3000);
			return false;
		}
		if(!confirm('确定删除选中公告吗？')){
			return;
		}
		var announcementIds = '';
		for(var i=0;i<recordObj.length;i++){
			announcementIds += recordObj[i].announcement_id + ((i!=recordObj.length-1)?',':'');
		}
		var url = '${pageContext.request.contextPath }/core/announcement/announcement/delete';
		var params = new Object();
		params.announcementIds = announcementIds;
		sw.showProcessBar();
		sw.ajaxSubmitRefreshGrid(url, params, grid);
	}
	//删除操作
	function deleteAnnouncementSingle(announcementId){
		if(!confirm('确定删除此公告吗？')){
			return;
		}
		var url = '${pageContext.request.contextPath }/core/announcement/announcement/delete';
		var params = new Object();
		params.announcementIds = announcementId;
		sw.showProcessBar();
		sw.ajaxSubmitRefreshGrid(url, params, grid);
	}
	//编辑跳转
	function editAnnouncement(){
		var recordObj = grid.getCheckedRecords();
		if(recordObj.length==0){
			sw.toast('请选择要编辑的公告！', 'warning', 3000);
			return false;
		}
		var announcementId = recordObj[0].announcement_id;
		var url = '${pageContext.request.contextPath }/core/announcement/announcement/'+announcementId+'/edit';
		sw.showProcessBar();
		window.location.href = url;
	}
	//编辑跳转
	function editAnnouncementSingle(announcementId){
		var url = '${pageContext.request.contextPath }/core/announcement/announcement/'+announcementId+'/edit';
		sw.showProcessBar();
		window.location.href = url;
	}
	//初始化绑定方法
	$(function(){
		$('#add_announcement').bind('click', addAnnouncement);
		$('#edit_announcement').bind('click', editAnnouncement);
		$('#open_announcement').bind('click', openAnnouncement);
		$('#close_announcement').bind('click', closeAnnouncement);
		$('#delete_announcement').bind('click', deleteAnnouncement);
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
			<li><a href="javascript:void(0);"><i class="fa fa-bell"></i>公告平台</a></li>
			<li class="active"><a href="javascript:void(0);"><i class="fa fa-bell"></i>公告管理</a></li>
		</ul>
		<div class="panel panel-default">
			<div class="panel-heading"><i class="fa fa-bell"></i>公告管理</div>
			<div class="panel-operation">
				<div class="btn-group">
					<sw:button accesskey="A" id="add_announcement" value="新建 (A)" cssClass="btn btn-primary" limit="core/announcement/announcement/add" icon="plus" />
					<sw:button accesskey="M" id="edit_announcement" value="编辑 (M)" cssClass="btn btn-primary" limit="core/announcement/announcement/-/edit" icon="edit" />
					<sw:button accesskey="O" id="open_announcement" value="启用 (O)" cssClass="btn btn-success" limit="core/announcement/announcement/open" icon="check" />
					<sw:button accesskey="C" id="close_announcement" value="禁用 (C)" cssClass="btn btn-warning" limit="core/announcement/announcement/close" icon="minus" />
					<sw:button accesskey="W" id="delete_announcement" value="删除 (W)" cssClass="btn btn-danger" limit="core/announcement/announcement/delete" icon="trash-o" />
				</div>
			</div>
			<div id="grid_container" class="dlshouwen-grid-container"></div>
			<div id="grid_toolbar_container" class="dlshouwen-grid-toolbar-container"></div>
		</div>
		<div class="clearfix"></div>
	</div>
</body>
</html>