<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 码表类别管理</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
	<script type="text/javascript">
	var gridColumns = [
		{id:'operation', title:'操作', columnClass:'text-center', columnStyle:'width:100px;', hideType:'xs', extra:false, 'export':false, print:false, advanceQuery:false, resolution:function(value, record, column, grid, dataNo, columnNo){
			var content = '';
			content += '<button type="button" class="btn btn-xs btn-icon btn-primary" title="编辑" limit="core/system/code_table/code_table_type/-/edit" onclick="editCodeTableTypeSingle(\''+record.c_type+'\')"><i class="fa fa-edit"></i></button>';
			content += '&nbsp;&nbsp;';
			content += '<button type="button" class="btn btn-xs btn-icon btn-danger" title="删除" limit="core/system/code_table/code_table_type/delete" onclick="deleteCodeTableTypeSingle(\''+record.c_type+'\')"><i class="fa fa-trash-o"></i></button>';
			content += '&nbsp;&nbsp;';
			content += '<button type="button" class="btn btn-xs btn-icon btn-default" title="查看码表" onclick="goCodeTablePage(\''+record.c_type+'\')"><i class="fa fa-list"></i></button>';
			return content;
		}},
		{id:'c_type', title:'码表类别编号', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq' },
		{id:'c_info', title:'码表类别名称', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'lk' },
		{id:'ct_count', title:'码表数量', type:'number', columnClass:'text-center', hideType:'xs', fastQuery:true, fastQueryType:'range' }
	];
	var gridOption = {
		loadURL : '${pageContext.request.contextPath }/core/system/code_table/code_table_type/list',
		functionCode : 'CORE_CODE_TABLE_TYPE',
		check:true,
		exportFileName : '码表类别列表',
		columns : gridColumns,
		onRowDblClick : function(value, record, column, grid, dataNo, columnNo, cell, row, extraCell, e){
			goCodeTablePage(record.c_type);
		},
		onGridComplete : function(value, record, column, grid, dataNo, columnNo, cell, row, extraCell, e){
			var urls = '';
			urls += 'core/system/code_table/code_table_type/-/edit,';
			urls += 'core/system/code_table/code_table_type/delete';
			sw.initGridButtonLimit(urls, '${applicationScope.__SYSTEM_ATTRIBUTE__.show_button_when_no_limit.content }');
		}
	};
	var grid = $.fn.dlshouwen.grid.init(gridOption);;
	//数据加载
	$(function(){
		grid.load();
	});
	//跳转码表页面
	function addCodeTableType(){
		var url='${pageContext.request.contextPath }/core/system/code_table/code_table_type/add';
		sw.showProcessBar();
		window.location.href = url;
	}
	//跳转添加
	function goCodeTablePage(c_type){
		var url='${pageContext.request.contextPath }/core/system/code_table/'+c_type+'/code_table';
		sw.showProcessBar();
		window.location.href = url;
	}
	//删除操作
	function deleteCodeTableType(){
		var recordObj = grid.getCheckedRecords();
		if(recordObj.length==0){
			sw.toast('请选择要删除的码表类别！', 'warning', 3000);
			return false;
		}
		if(!confirm('确定删除此码表类别吗？')){
			return;
		}
		var c_types = '';
		for(var i=0;i<recordObj.length;i++){
			c_types += recordObj[i].c_type + ((i!=recordObj.length-1)?',':'');
		}
		var url = '${pageContext.request.contextPath }/core/system/code_table/code_table_type/delete';
		var params = new Object();
		params.c_types = c_types;
		sw.showProcessBar();
		sw.ajaxSubmitRefreshGrid(url, params, grid);
	}
	//删除操作
	function deleteCodeTableTypeSingle(c_type){
		if(!confirm('确定删除此码表类别吗？')){
			return;
		}
		var url = '${pageContext.request.contextPath }/core/system/code_table/code_table_type/delete';
		var params = new Object();
		params.c_types = c_type;
		sw.showProcessBar();
		sw.ajaxSubmitRefreshGrid(url, params, grid);
	}
	//编辑跳转
	function editCodeTableType(){
		var recordObj = grid.getCheckedRecords();
		if(recordObj.length==0){
			sw.toast('请选择要编辑的码表类别！', 'warning', 3000);
			return false;
		}
		var code_table_typeId = recordObj[0].code_table_type_id;
		var url = '${pageContext.request.contextPath }/core/system/code_table/code_table_type/'+code_table_typeId+'/edit';
		sw.showProcessBar();
		sw.updateSigmaGrid(url, '480px', '140px', grid);
	}
	//编辑跳转
	function editCodeTableType(){
		var recordObj = grid.getCheckedRecords();
		if(recordObj.length==0){
			sw.toast('请选择要编辑的码表类别！', 'warning', 3000);
			return false;
		}
		var c_type = recordObj[0].c_type;
		var url = '${pageContext.request.contextPath }/core/system/code_table/code_table_type/'+c_type+'/edit';
		sw.showProcessBar();
		window.location.href = url;
	}
	//编辑跳转
	function editCodeTableTypeSingle(c_type){
		var url = '${pageContext.request.contextPath }/core/system/code_table/code_table_type/'+c_type+'/edit';
		sw.showProcessBar();
		window.location.href = url;
	}
	//初始化绑定方法
	$(function(){
		$('#add_code_table_type').bind('click', addCodeTableType);
		$('#edit_code_table_type').bind('click', editCodeTableType);
		$('#delete_code_table_type').bind('click', deleteCodeTableType);
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
			<li class="active"><a href="javascript:void(0);"><i class="fa fa-table"></i>码表类型管理</a></li>
		</ul>
		<div class="panel panel-default">
			<div class="panel-heading"><i class="fa fa-table"></i>码表类型列表</div>
			<div class="panel-operation">
				<div class="btn-group">
					<sw:button accesskey="A" id="add_code_table_type" value="新建 (A)" cssClass="btn btn-primary" limit="core/system/code_table/code_table_type/add" icon="plus" />
					<sw:button accesskey="M" id="edit_code_table_type" value="编辑 (M)" cssClass="btn btn-primary" limit="core/system/code_table/code_table_type/-/edit" icon="edit" />
					<sw:button accesskey="W" id="delete_code_table_type" value="删除 (W)" cssClass="btn btn-danger" limit="core/system/code_table/code_table_type/delete" icon="trash-o" />
				</div>
			</div>
			<div id="grid_container" class="dlshouwen-grid-container"></div>
			<div id="grid_toolbar_container" class="dlshouwen-grid-toolbar-container"></div>
		</div>
		<div class="clearfix"></div>
	</div>
</body>
</html>