<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 系统管理</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
	<script type="text/javascript">
	var gridColumns = [
		{id:'operation', title:'操作', columnClass:'text-center', columnStyle:'width:80px;', hideType:'xs', extra:false, 'export':false, print:false, advanceQuery:false, resolution:function(value, record, column, grid, dataNo, columnNo){
			var content = '';
			content += '<button type="button" class="btn btn-xs btn-icon btn-primary" title="编辑" limit="core/system/system/-/edit" onclick="editSystemSingle(\''+record.system_id+'\')"><i class="fa fa-edit"></i></button>';
			content += '&nbsp;&nbsp;';
			content += '<button type="button" class="btn btn-xs btn-icon btn-danger" title="删除" limit="core/system/system/delete" onclick="deleteSystemSingle(\''+record.system_id+'\')"><i class="fa fa-trash-o"></i></button>';
			return content;
		}},
		{id:'system_code', title:'系统编号', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq' },
		{id:'system_name', title:'系统名称', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'lk' },
		{id:'remark', title:'备注', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'lk' },
		{id:'creator_name', title:'创建人', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'lk' },
		{id:'create_time', title:'创建时间', type:'date', format:'yyyy-MM-dd hh:mm:ss', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'range' },
		{id:'editor_name', title:'编辑人', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'lk' },
		{id:'edit_time', title:'编辑时间', type:'date', format:'yyyy-MM-dd hh:mm:ss', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'range' }
	];
	var gridOption = {
		loadURL : '${pageContext.request.contextPath }/core/system/system/list',
		functionCode : 'CORE_SYSTEM',
		check:true,
		exportFileName : '系统列表',
		columns : gridColumns,
		onRowDblClick : function(value, record, column, grid, dataNo, columnNo, cell, row, extraCell, e){
			editSystemSingle(record.system_id);
		},
		onGridComplete : function(value, record, column, grid, dataNo, columnNo, cell, row, extraCell, e){
			var urls = '';
			urls += 'core/system/system/-/edit,';
			urls += 'core/system/system/delete';
			sw.initGridButtonLimit(urls, '${applicationScope.__SYSTEM_ATTRIBUTE__.show_button_when_no_limit.content }');
		}
	};
	var grid = $.fn.dlshouwen.grid.init(gridOption);;
	//数据加载
	$(function(){
		grid.load();
	});
	//跳转添加
	function addSystem(){
		var url='${pageContext.request.contextPath }/core/system/system/add';
		sw.showProcessBar();
		window.location.href = url;
	}
	//删除操作
	function deleteSystem(){
		var recordObj = grid.getCheckedRecords();
		if(recordObj.length==0){
			sw.toast('请选择要删除的系统！', 'warning', 3000);
			return false;
		}
		if(!confirm('确定删除选中系统吗？')){
			return;
		}
		var systemIds = '';
		for(var i=0;i<recordObj.length;i++){
			systemIds += recordObj[i].system_id + ((i!=recordObj.length-1)?',':'');
		}
		var url = '${pageContext.request.contextPath }/core/system/system/delete';
		var params = new Object();
		params.systemIds = systemIds;
		sw.showProcessBar();
		sw.ajaxSubmitRefreshGrid(url, params, grid);
	}
	//删除操作
	function deleteSystemSingle(systemId){
		if(!confirm('确定删除选中系统吗？')){
			return;
		}
		var url = '${pageContext.request.contextPath }/core/system/system/delete';
		var params = new Object();
		params.systemIds = systemId;
		sw.showProcessBar();
		sw.ajaxSubmitRefreshGrid(url, params, grid);
	}
	//编辑跳转
	function editSystem(){
		var recordObj = grid.getCheckedRecords();
		if(recordObj.length==0){
			sw.toast('请选择要编辑的系统！', 'warning', 3000);
			return false;
		}
		var systemId = recordObj[0].system_id;
		var url = '${pageContext.request.contextPath }/core/system/system/'+systemId+'/edit';
		sw.showProcessBar();
		window.location.href = url;
	}
	//编辑跳转
	function editSystemSingle(systemId){
		var url = '${pageContext.request.contextPath }/core/system/system/'+systemId+'/edit';
		sw.showProcessBar();
		window.location.href = url;
	}
	//初始化绑定方法
	$(function(){
		$('#add_system').bind('click', addSystem);
		$('#edit_system').bind('click', editSystem);
		$('#delete_system').bind('click', deleteSystem);
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
			<li><a href="javascript:void(0);"><i class="fa fa-gears"></i>基础管理</a></li>
			<li class="active"><a href="javascript:void(0);"><i class="fa fa-delicious"></i>系统管理</a></li>
		</ul>
		<div class="panel panel-default">
			<div class="panel-heading"><i class="fa fa-delicious"></i>系统管理</div>
			<div class="panel-operation">
				<div class="btn-group">
					<sw:button accesskey="A" id="add_system" value="新建 (A)" cssClass="btn btn-primary" limit="core/system/system/add" icon="plus" />
					<sw:button accesskey="M" id="edit_system" value="编辑 (M)" cssClass="btn btn-primary" limit="core/system/system/-/edit" icon="edit" />
					<sw:button accesskey="W" id="delete_system" value="删除 (W)" cssClass="btn btn-danger" limit="core/system/system/delete" icon="trash-o" />
				</div>
			</div>
			<div id="grid_container" class="dlshouwen-grid-container"></div>
			<div id="grid_toolbar_container" class="dlshouwen-grid-toolbar-container"></div>
		</div>
		<div class="clearfix"></div>
	</div>
</body>
</html>