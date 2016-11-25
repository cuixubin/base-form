<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 任务管理</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
	<%
	//通过静态域设置码制映射对象
	CodeTableUtils.createCodeTableJS(application, out, "open_close");
	CodeTableUtils.createCodeTableJS(application, out, "zero_one");
	%>
	<script type="text/javascript">
	var gridColumns = [
		{id:'operation', title:'操作', columnClass:'text-center', columnStyle:'width:100px;', hideType:'xs', extra:false, 'export':false, print:false, advanceQuery:false, resolution:function(value, record, column, grid, dataNo, columnNo){
			var content = '';
			content += '<button type="button" class="btn btn-xs btn-icon btn-primary" title="编辑" limit="core/task/task/-/edit" onclick="editTaskSingle(\''+record.task_id+'\')"><i class="fa fa-edit"></i></button>';
			content += '&nbsp;&nbsp;';
			if(record.status=='1'){
				content += '<button type="button" class="btn btn-xs btn-icon btn-warning" title="禁用" limit="core/task/task/close" onclick="closeTaskSingle(\''+record.task_id+'\')"><i class="fa fa-minus"></i></button>';
			}else{
				content += '<button type="button" class="btn btn-xs btn-icon btn-success" title="启用" limit="core/task/task/open" onclick="openTaskSingle(\''+record.task_id+'\')"><i class="fa fa-check"></i></button>';
			}
			content += '&nbsp;&nbsp;';
			content += '<button type="button" class="btn btn-xs btn-icon btn-danger" title="删除" limit="core/task/task/delete" onclick="deleteTaskSingle(\''+record.task_id+'\')"><i class="fa fa-trash-o"></i></button>';
			return content;
		}},
		{id:'task_name', title:'任务名称', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'lk' },
		{id:'status', title:'启用状态', type:'string', codeTable:code_table_info['open_close'], columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq' },
		{id:'is_timing', title:'定时提醒', type:'string', codeTable:code_table_info['zero_one'], columnClass:'text-center', hideType:'xs', fastQuery:true, fastQueryType:'eq' },
		{id:'timing_time', title:'提醒时间', type:'date', format:'yyyy-MM-dd hh:mm:ss', columnClass:'text-center', hideType:'xs|sm', fastQuery:true, fastQueryType:'range' },
		{id:'time_space', title:'提示时间间隔', type:'number', columnClass:'text-center', hideType:'xs|sm', fastQuery:true, fastQueryType:'range' },
		{id:'is_never_overdue', title:'从不过期', type:'string', codeTable:code_table_info['zero_one'], columnClass:'text-center', hideType:'xs|sm', fastQuery:true, fastQueryType:'eq' },
		{id:'overdue_time', title:'过期时间', type:'date', format:'yyyy-MM-dd hh:mm:ss', hideType:'lg|md|sm|xs', columnClass:'text-center', hideType:'xs|sm', fastQuery:true, fastQueryType:'range' },
		{id:'message', title:'信息提示模板', type:'string', columnClass:'text-center', hideType:'xs|sm|md|lg', fastQuery:true, fastQueryType:'lk' },
		{id:'limit_name', title:'响应功能点', type:'string', columnClass:'text-center', hideType:'xs|sm|md|lg', fastQuery:true, fastQueryType:'lk' },
		{id:'detonate_sql', title:'触发脚本', type:'string', columnClass:'text-center', hideType:'xs|sm|md|lg', fastQuery:true, fastQueryType:'lk' },
		{id:'is_all_user', title:'全用户触发', type:'string', codeTable:code_table_info['zero_one'], columnClass:'text-center', hideType:'xs|sm|md', fastQuery:true, fastQueryType:'eq' },
		{id:'remark', title:'备注', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'lk' },
		{id:'creator_name', title:'创建人', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'lk' },
		{id:'create_time', title:'创建时间', type:'date', format:'yyyy-MM-dd hh:mm:ss', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'range' },
		{id:'editor_name', title:'编辑人', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'lk' },
		{id:'edit_time', title:'编辑时间', type:'date', format:'yyyy-MM-dd hh:mm:ss', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'range' }
	];
	var gridOption = {
		loadURL : '${pageContext.request.contextPath }/core/task/task/list',
		functionCode : 'CORE_TASK',
		check:true,
		exportFileName : '任务列表',
		columns : gridColumns,
		onRowDblClick : function(value, record, column, grid, dataNo, columnNo, cell, row, extraCell, e){
			editTaskSingle(record.task_id);
		},
		onGridComplete : function(value, record, column, grid, dataNo, columnNo, cell, row, extraCell, e){
			var urls = '';
			urls += 'core/task/task/-/edit,';
			urls += 'core/task/task/open,';
			urls += 'core/task/task/close,';
			urls += 'core/task/task/delete';
			sw.initGridButtonLimit(urls, '${applicationScope.__SYSTEM_ATTRIBUTE__.show_button_when_no_limit.content }');
		}
	};
	var grid = $.fn.dlshouwen.grid.init(gridOption);;
	//数据加载
	$(function(){
		grid.load();
	});
	//跳转添加
	function addTask(){
		var url='${pageContext.request.contextPath }/core/task/task/add';
		sw.showProcessBar();
		window.location.href = url;
	}
	//启用操作
	function openTask(){
		var recordObj = grid.getCheckedRecords();
		if(recordObj.length==0){
			sw.toast('请选择要启用的任务！', 'warning', 3000);
			return false;
		}
		if(!confirm('确定启用选中任务吗？')){
			return;
		}
		var taskIds = '';
		for(var i=0;i<recordObj.length;i++){
			taskIds += recordObj[i].task_id + ((i!=recordObj.length-1)?',':'');
		}
		var url = '${pageContext.request.contextPath }/core/task/task/open';
		var params = new Object();
		params.taskIds = taskIds;
		sw.showProcessBar();
		sw.ajaxSubmitRefreshGrid(url, params, grid);
	}
	//启用操作
	function openTaskSingle(taskId){
		if(!confirm('确定启用此任务吗？')){
			return;
		}
		var url = '${pageContext.request.contextPath }/core/task/task/open';
		var params = new Object();
		params.taskIds = taskId;
		sw.showProcessBar();
		sw.ajaxSubmitRefreshGrid(url, params, grid);
	}
	//禁用操作
	function closeTask(){
		var recordObj = grid.getCheckedRecords();
		if(recordObj.length==0){
			sw.toast('请选择要禁用的任务！', 'warning', 3000);
			return false;
		}
		if(!confirm('确定禁用选中任务吗？')){
			return;
		}
		var taskIds = '';
		for(var i=0;i<recordObj.length;i++){
			taskIds += recordObj[i].task_id + ((i!=recordObj.length-1)?',':'');
		}
		var url = '${pageContext.request.contextPath }/core/task/task/close';
		var params = new Object();
		params.taskIds = taskIds;
		sw.showProcessBar();
		sw.ajaxSubmitRefreshGrid(url, params, grid);
	}
	//禁用操作
	function closeTaskSingle(taskId){
		if(!confirm('确定禁用此任务吗？')){
			return;
		}
		var url = '${pageContext.request.contextPath }/core/task/task/close';
		var params = new Object();
		params.taskIds = taskId;
		sw.showProcessBar();
		sw.ajaxSubmitRefreshGrid(url, params, grid);
	}
	//删除操作
	function deleteTask(){
		var recordObj = grid.getCheckedRecords();
		if(recordObj.length==0){
			sw.toast('请选择要删除的任务！', 'warning', 3000);
			return false;
		}
		if(!confirm('确定删除选中任务吗？')){
			return;
		}
		var taskIds = '';
		for(var i=0;i<recordObj.length;i++){
			taskIds += recordObj[i].task_id + ((i!=recordObj.length-1)?',':'');
		}
		var url = '${pageContext.request.contextPath }/core/task/task/delete';
		var params = new Object();
		params.taskIds = taskIds;
		sw.showProcessBar();
		sw.ajaxSubmitRefreshGrid(url, params, grid);
	}
	//删除操作
	function deleteTaskSingle(taskId){
		if(!confirm('确定删除此任务吗？')){
			return;
		}
		var url = '${pageContext.request.contextPath }/core/task/task/delete';
		var params = new Object();
		params.taskIds = taskId;
		sw.showProcessBar();
		sw.ajaxSubmitRefreshGrid(url, params, grid);
	}
	//编辑跳转
	function editTask(){
		var recordObj = grid.getCheckedRecords();
		if(recordObj.length==0){
			sw.toast('请选择要编辑的任务！', 'warning', 3000);
			return false;
		}
		var taskId = recordObj[0].task_id;
		var url = '${pageContext.request.contextPath }/core/task/task/'+taskId+'/edit';
		sw.showProcessBar();
		window.location.href = url;
	}
	//编辑跳转
	function editTaskSingle(taskId){
		var url = '${pageContext.request.contextPath }/core/task/task/'+taskId+'/edit';
		sw.showProcessBar();
		window.location.href = url;
	}
	//初始化绑定方法
	$(function(){
		$('#add_task').bind('click', addTask);
		$('#edit_task').bind('click', editTask);
		$('#open_task').bind('click', openTask);
		$('#close_task').bind('click', closeTask);
		$('#delete_task').bind('click', deleteTask);
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
			<li><a href="javascript:void(0);"><i class="fa fa-tachometer"></i>任务管理</a></li>
			<li class="active"><a href="javascript:void(0);"><i class="fa fa-tachometer"></i>任务管理</a></li>
		</ul>
		<div class="panel panel-default">
			<div class="panel-heading"><i class="fa fa-tachometer"></i>任务管理</div>
			<div class="panel-operation">
				<div class="btn-group">
					<sw:button accesskey="A" id="add_task" value="新建 (A)" cssClass="btn btn-primary" limit="core/task/task/add" icon="plus" />
					<sw:button accesskey="M" id="edit_task" value="编辑 (M)" cssClass="btn btn-primary" limit="core/task/task/-/edit" icon="edit" />
					<sw:button accesskey="O" id="open_task" value="启用 (O)" cssClass="btn btn-success" limit="core/task/task/open" icon="check" />
					<sw:button accesskey="C" id="close_task" value="禁用 (C)" cssClass="btn btn-warning" limit="core/task/task/close" icon="minus" />
					<sw:button accesskey="W" id="delete_task" value="删除 (W)" cssClass="btn btn-danger" limit="core/task/task/delete" icon="trash-o" />
				</div>
			</div>
			<div id="grid_container" class="dlshouwen-grid-container"></div>
			<div id="grid_toolbar_container" class="dlshouwen-grid-toolbar-container"></div>
		</div>
		<div class="clearfix"></div>
	</div>
</body>
</html>