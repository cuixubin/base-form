<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 码表管理</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
	<script type="text/javascript">
	var gridColumns = [
		{id:'operation', title:'操作', columnClass:'text-center', columnStyle:'width:80px;', hideType:'xs', extra:false, 'export':false, print:false, advanceQuery:false, resolution:function(value, record, column, grid, dataNo, columnNo){
			var content = '';
			content += '<button type="button" class="btn btn-xs btn-icon btn-primary" title="编辑" limit="core/system/code_table/-/code_table/-/edit" onclick="editCodeTableSingle(\''+record.c_key+'\')"><i class="fa fa-edit"></i></button>';
			content += '&nbsp;&nbsp;';
			content += '<button type="button" class="btn btn-xs btn-icon btn-danger" title="删除" limit="core/system/code_table/-/code_table/delete" onclick="deleteCodeTableSingle(\''+record.c_key+'\')"><i class="fa fa-trash-o"></i></button>';
			return content;
		}},
		{id:'c_key', title:'码表编号', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq' },
		{id:'c_value', title:'码表名称', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'lk' },
		{id:'sort', title:'排序号', type:'number', columnClass:'text-center', hideType:'xs', fastQuery:true, fastQueryType:'range' }
	];
	var gridOption = {
		loadURL : '${pageContext.request.contextPath }/core/system/code_table/${c_type}/code_table/list',
		functionCode : 'CORE_CODE_TABLE',
		check:true,
		exportFileName : '码表列表',
		columns : gridColumns,
		onRowDblClick : function(value, record, column, grid, dataNo, columnNo, cell, row, extraCell, e){
			editCodeTableSingle(record.c_key);
		},
		onGridComplete : function(value, record, column, grid, dataNo, columnNo, cell, row, extraCell, e){
			var urls = '';
			urls += 'core/system/code_table/-/code_table/-/edit,';
			urls += 'core/system/code_table/-/code_table/delete';
			sw.initGridButtonLimit(urls, '${applicationScope.__SYSTEM_ATTRIBUTE__.show_button_when_no_limit.content }');
		}
	};
	var grid = $.fn.dlshouwen.grid.init(gridOption);;
	//数据加载
	$(function(){
		grid.load();
	});
	//跳转添加
	function addCodeTable(){
		var url='${pageContext.request.contextPath }/core/system/code_table/${c_type}/code_table/add';
		sw.showProcessBar();
		window.location.href = url;
	}
	//删除操作
	function deleteCodeTable(){
		var recordObj = grid.getCheckedRecords();
		if(recordObj.length==0){
			sw.toast('请选择要删除的码表！', 'warning', 3000);
			return false;
		}
		if(!confirm('确定删除选中码表吗？')){
			return;
		}
		var c_keys = '';
		for(var i=0;i<recordObj.length;i++){
			c_keys += recordObj[i].c_key + ((i!=recordObj.length-1)?',':'');
		}
		var url = '${pageContext.request.contextPath }/core/system/code_table/${c_type}/code_table/delete';
		var params = new Object();
		params.c_keys = c_keys;
		sw.showProcessBar();
		sw.ajaxSubmitRefreshGrid(url, params, grid);
	}
	//删除操作
	function deleteCodeTableSingle(c_key){
		if(!confirm('确定删除选中码表吗？')){
			return;
		}
		var url = '${pageContext.request.contextPath }/core/system/code_table/${c_type}/code_table/delete';
		var params = new Object();
		params.c_keys = c_key;
		sw.showProcessBar();
		sw.ajaxSubmitRefreshGrid(url, params, grid);
	}
	//编辑跳转
	function editCodeTable(){
		var recordObj = grid.getCheckedRecords();
		if(recordObj.length==0){
			sw.toast('请选择要编辑的码表！', 'warning', 3000);
			return false;
		}
		var c_key = recordObj[0].c_key;
		var url = '${pageContext.request.contextPath }/core/system/code_table/${c_type}/code_table/'+c_key+'/edit';
		sw.showProcessBar();
		window.location.href = url;
	}
	//编辑跳转
	function editCodeTableSingle(c_key){
		var url = '${pageContext.request.contextPath }/core/system/code_table/${c_type}/code_table/'+c_key+'/edit';
		sw.showProcessBar();
		window.location.href = url;
	}
	//初始化绑定方法
	$(function(){
		$('#add_code_table').bind('click', addCodeTable);
		$('#edit_code_table').bind('click', editCodeTable);
		$('#delete_code_table').bind('click', deleteCodeTable);
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
			<li><a href="${pageContext.request.contextPath }/core/system/code_table"><i class="fa fa-table"></i>码表类型管理</a></li>
			<li class="active"><a href="javascript:void(0);"><i class="fa fa-table"></i>码表管理 [ ${codeTableType.c_type } - ${codeTableType.c_info } ]</a></li>
		</ul>
		<div class="panel panel-default">
			<div class="panel-heading"><i class="fa fa-table"></i>码表列表</div>
			<div class="panel-operation">
				<div class="btn-group">
					<sw:button accesskey="A" id="add_code_table" value="新建 (A)" cssClass="btn btn-primary" limit="core/system/code_table/-/code_table/add" icon="plus" />
					<sw:button accesskey="M" id="edit_code_table" value="编辑 (M)" cssClass="btn btn-primary" limit="core/system/code_table/-/code_table/-/edit" icon="edit" />
					<sw:button accesskey="W" id="delete_code_table" value="删除 (W)" cssClass="btn btn-danger" limit="core/system/code_table/-/code_table/delete" icon="trash-o" />
				</div>
			</div>
			<div id="grid_container" class="dlshouwen-grid-container"></div>
			<div id="grid_toolbar_container" class="dlshouwen-grid-toolbar-container"></div>
		</div>
		<div class="clearfix"></div>
	</div>
</body>
</html>