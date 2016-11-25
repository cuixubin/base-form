<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 参数管理</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
	<%
	//通过静态域设置码制映射对象
	CodeTableUtils.createCodeTableJS(application, out, "attr_type");
	%>
	<script type="text/javascript">
	var gridColumns = [
		{id:'operation', title:'操作', columnClass:'text-center', columnStyle:'width:80px;', hideType:'xs', extra:false, 'export':false, print:false, advanceQuery:false, resolution:function(value, record, column, grid, dataNo, columnNo){
			var content = '';
			content += '<button type="button" class="btn btn-xs btn-icon btn-primary" title="编辑" limit="core/system/attr/-/edit" onclick="editAttrSingle(\''+record.attr_id+'\', \''+record.type+'\')"><i class="fa fa-edit"></i></button>';
			content += '&nbsp;&nbsp;';
			content += '<button type="button" class="btn btn-xs btn-icon btn-danger" title="删除" limit="core/system/attr/delete" onclick="deleteAttrSingle(\''+record.attr_id+'\', \''+record.type+'\')"><i class="fa fa-trash-o"></i></button>';
			return content;
		}},
		{id:'attr_code', title:'参数编号', type:'string', columnClass:'text-center', hideType:'xs|sm', fastQuery:true, fastQueryType:'eq' },
		{id:'attr_name', title:'参数名称', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'lk' },
		{id:'type', title:'参数类别', type:'string', codeTable:code_table_info['attr_type'], columnClass:'text-center', hideType:'xs', fastQuery:true, fastQueryType:'eq' },
		{id:'content', title:'内容', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'lk' },
		{id:'remark', title:'备注', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'lk' }
	];
	var gridOption = {
		loadURL : '${pageContext.request.contextPath }/core/system/attr/list',
		functionCode : 'CORE_ATTR',
		check:true,
		exportFileName : '参数列表',
		columns : gridColumns,
		onRowDblClick : function(value, record, column, grid, dataNo, columnNo, cell, row, extraCell, e){
			editAttrSingle(record.attr_id, record.type);
		},
		onGridComplete : function(value, record, column, grid, dataNo, columnNo, cell, row, extraCell, e){
			var urls = '';
			urls += 'core/system/attr/-/edit,';
			urls += 'core/system/attr/delete';
			sw.initGridButtonLimit(urls, '${applicationScope.__SYSTEM_ATTRIBUTE__.show_button_when_no_limit.content }');
		}
	};
	var grid = $.fn.dlshouwen.grid.init(gridOption);
	//数据加载
	$(function(){
		grid.load();
	});
	//跳转添加
	function addAttr(){
		var url='${pageContext.request.contextPath }/core/system/attr/add';
		sw.showProcessBar();
		window.location.href = url;
	}
	//删除操作
	function deleteAttr(){
		var recordObj = grid.getCheckedRecords();
		if(recordObj.length==0){
			sw.toast('请选择要删除的参数！', 'warning', 3000);
			return false;
		}
		if(!confirm('确定删除选中参数吗？')){
			return;
		}
		var attrIds = '';
		for(var i=0;i<recordObj.length;i++){
			attrIds += recordObj[i].attr_id + ((i!=recordObj.length-1)?',':'');
		}
		var url = '${pageContext.request.contextPath }/core/system/attr/delete';
		var params = new Object();
		params.attrIds = attrIds;
		sw.showProcessBar();
		sw.ajaxSubmitRefreshGrid(url, params, grid);
	}
	//删除操作
	function deleteAttrSingle(attrId, type){
		if(type=='1'){
			sw.toast('系统内置的参数不允许删除！', 'warning', 3000);
			return false;
		}
		if(!confirm('确定删除此参数吗？')){
			return;
		}
		var url = '${pageContext.request.contextPath }/core/system/attr/delete';
		var params = new Object();
		params.attrIds = attrId;
		sw.showProcessBar();
		sw.ajaxSubmitRefreshGrid(url, params, grid);
	}
	//编辑跳转
	function editAttr(){
		var recordObj = grid.getCheckedRecords();
		if(recordObj.length==0){
			sw.toast('请选择要编辑的参数！', 'warning', 3000);
			return false;
		}
		var attrId = recordObj[0].attr_id;
		var url = '${pageContext.request.contextPath }/core/system/attr/'+attrId+'/edit';
		sw.showProcessBar();
		window.location.href = url;
	}
	//编辑跳转
	function editAttrSingle(attrId, type){
		var url = '${pageContext.request.contextPath }/core/system/attr/'+attrId+'/edit';
		sw.showProcessBar();
		window.location.href = url;
	}
	//配置默认快捷方式
	function setDefaultShortcutLimit(){
		var url = '${pageContext.request.contextPath }/core/system/attr/set_default_shortcut_limit';
		sw.openModal({
			id : 'setDefaultShortcutLimit',
			icon : 'magic', 
			title : '配置默认快捷方式',
			url : url
		});
	}
	//初始化绑定方法
	$(function(){
		$('#add_attr').bind('click', addAttr);
		$('#edit_attr').bind('click', editAttr);
		$('#delete_attr').bind('click', deleteAttr);
		$('#set_default_shortcut_limit').bind('click', setDefaultShortcutLimit);
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
			<li class="active"><a href="javascript:void(0);"><i class="fa fa-wrench"></i>参数管理</a></li>
		</ul>
		<div class="panel panel-default">
			<div class="panel-heading"><i class="fa fa-wrench"></i>参数管理</div>
			<div class="panel-operation">
				<div class="btn-group">
					<sw:button accesskey="A" id="add_attr" value="新建 (A)" cssClass="btn btn-primary" limit="core/system/attr/add" icon="plus" />
					<sw:button accesskey="M" id="edit_attr" value="编辑 (M)" cssClass="btn btn-primary" limit="core/system/attr/-/edit" icon="edit" />
					<sw:button accesskey="W" id="delete_attr" value="删除 (W)" cssClass="btn btn-danger" limit="core/system/attr/delete" icon="trash-o" />
					<sw:button accesskey="S" id="set_default_shortcut_limit" value="配置默认快捷方式 (S)" cssClass="btn btn-default" limit="core/system/attr/set_default_shortcut_limit" icon="magic" />
				</div>
			</div>
			<div id="grid_container" class="dlshouwen-grid-container"></div>
			<div id="grid_toolbar_container" class="dlshouwen-grid-toolbar-container"></div>
		</div>
		<div class="clearfix"></div>
	</div>
</body>
</html>