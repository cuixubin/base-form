<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 标签管理</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
	<%
	//通过静态域设置码制映射对象
	CodeTableUtils.createCodeTableJS(application, out, "open_close");
	%>
	<script type="text/javascript">
	var gridColumns = [
		{id:'operation', title:'操作', columnClass:'text-center', columnStyle:'width:150px;', hideType:'xs', extra:false, 'export':false, print:false, advanceQuery:false, resolution:function(value, record, column, grid, dataNo, columnNo){
			var content = '';
                        content += '<button type="button" class="btn btn-xs btn-icon btn-default" title="预览" limit=" " onclick=""><i class="fa fa-eye"></i></button>';
			content += '&nbsp;&nbsp;';
			content += '<button type="button" class="btn btn-xs btn-icon btn-primary" title="编辑" limit="wzgl/tag/tag/-/edit/-/edit" onclick="editTagSingle(\''+record.tag_id+'\')"><i class="fa fa-edit"></i></button>';
			content += '&nbsp;&nbsp;';
			content += '<button type="button" class="btn btn-xs btn-icon btn-danger" title="删除" limit="wzgl/tag/tag/-/edit/delete" onclick="deleteTagSingle(\''+record.tag_id+'\')"><i class="fa fa-trash-o"></i></button>';
			return content;
		}},
		{id:'tag_name', title:'标签主题', type:'string', columnClass:'text-center', hideType:'',fastQuery:true, fastQueryType:'lk' },
                {id:'tag_styledescription', title:'标签类型说明', columnClass:'text-center', hideType:'',fastQuery:true, fastQueryType:'lk' },
		{id:'tag_addtime', title:'创建时间', type:'date', format:'yyyy-MM-dd hh:mm:ss', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'range' },
		{id:'tag_state', title:'标签状态', type:'string', codeTable:code_table_info['open_close'], columnClass:'text-center', fastQuery:true, fastQueryType:'eq', resolution:function(value, record, column, grid, dataNo, columnNo){
			return value=='0'?('<span class="label label-danger">'+code_table_info['open_close'][value]+'</span>'):('<span class="label label-success">'+code_table_info['open_close'][value]+'</span>');
		}},
                {id:'tag_url', title:'标签内容路径', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'lk' },
                {id:'tag_adduser', title:'创建人', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'lk' },
		{id:'tag_edituser', title:'编辑人', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'lk' },
		{id:'tag_edittime', title:'编辑时间', type:'date', format:'yyyy-MM-dd hh:mm:ss', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'range' }
	];
	var gridOption = {
		loadURL : '${pageContext.request.contextPath }/wzgl/tag/tag/list',
		functionCode : 'WZGL_TAG',
		check:true,
		exportFileName : '标签列表',
		columns : gridColumns,
		onRowDblClick : function(value, record, column, grid, dataNo, columnNo, cell, row, extraCell, e){
			editTagSingle(record.tag_id);
		},
		onGridComplete : function(value, record, column, grid, dataNo, columnNo, cell, row, extraCell, e){
			var urls = '';
			urls += 'wzgl/tag/tag/-/edit,';
			urls += 'wzgl/tag/tag/open,';
			urls += 'wzgl/tag/tag/close,';
			urls += 'wzgl/tag/tag/delete';
			sw.initGridButtonLimit(urls, '${applicationScope.__SYSTEM_ATTRIBUTE__.show_button_when_no_limit.content }');
		}
	};
	var grid = $.fn.dlshouwen.grid.init(gridOption);
	//数据加载
	$(function(){
		grid.load();
	});
	//跳转添加
	function addTag(){
		var url = '${pageContext.request.contextPath }/wzgl/tag/tag/add';
		sw.showProcessBar();
		window.location.href = url;
	}
	//启用操作
	function openTag(){
		var recordObj = grid.getCheckedRecords();
		if(recordObj.length==0){
			sw.toast('请选择要启用的标签！', 'warning', 3000);
			return false;
		}
		if(!confirm('确定启用选中标签吗？')){
			return;
		}
		var tagIds = '';
		for(var i=0;i<recordObj.length;i++){
			tagIds += recordObj[i].tag_id + ((i!=recordObj.length-1)?',':'');
		}
		var url = '${pageContext.request.contextPath }/wzgl/tag/tag/open';
		var params = new Object();
		params.tagIds = tagIds;
		sw.showProcessBar();
		sw.ajaxSubmitRefreshGrid(url, params, grid);
	}
	//启用操作
	function openTagSingle(tagId){
		if(!confirm('确定启用此标签吗？')){
			return;
		}
		var url = '${pageContext.request.contextPath }/wzgl/tag/tag/open';
		var params = new Object();
		params.tagIds = tagId;
		sw.showProcessBar();
		sw.ajaxSubmitRefreshGrid(url, params, grid);
	}
	//禁用操作
	function closeTag(){
		var recordObj = grid.getCheckedRecords();
		if(recordObj.length==0){
			sw.toast('请选择要禁用的标签！', 'warning', 3000);
			return false;
		}
		if(!confirm('确定禁用选中标签吗？')){
			return;
		}
		var tagIds = '';
		for(var i=0;i<recordObj.length;i++){
			tagIds += recordObj[i].tag_id + ((i!=recordObj.length-1)?',':'');
		}
		var url = '${pageContext.request.contextPath }/wzgl/tag/tag/close';
		var params = new Object();
		params.tagIds = tagIds;
		sw.showProcessBar();
		sw.ajaxSubmitRefreshGrid(url, params, grid);
	}
	//禁用操作
	function closeTagSingle(tagId){
		if(!confirm('确定禁用此标签吗？')){
			return;
		}
		var url = '${pageContext.request.contextPath }/wzgl/tag/tag/close';
		var params = new Object();
		params.tagIds = tagId;
		sw.showProcessBar();
		sw.ajaxSubmitRefreshGrid(url, params, grid);
	}
	//删除操作
	function deleteTag(){
		var recordObj = grid.getCheckedRecords();
		if(recordObj.length==0){
			sw.toast('请选择要删除的标签！', 'warning', 3000);
			return false;
		}
		if(!confirm('确定删除选中标签吗？')){
			return;
		}
		var tagIds = '';
		for(var i=0;i<recordObj.length;i++){
			tagIds += recordObj[i].tag_id + ((i!=recordObj.length-1)?',':'');
		}
		var url = '${pageContext.request.contextPath }/wzgl/tag/tag/delete';
		var params = new Object();
		params.tagIds = tagIds;
		sw.showProcessBar();
		sw.ajaxSubmitRefreshGrid(url, params, grid);
	}
	//删除操作
	function deleteTagSingle(tagId){
		if(!confirm('确定删除此标签吗？')){
			return;
		}
		var url = '${pageContext.request.contextPath }/wzgl/tag/tag/delete';
		var params = new Object();
		params.tagIds = tagId;
		sw.showProcessBar();
		sw.ajaxSubmitRefreshGrid(url, params, grid);
	}
	//编辑跳转
	function editTag(){
		var recordObj = grid.getCheckedRecords();
		if(recordObj.length==0){
			sw.toast('请选择要编辑的标签！', 'warning', 3000);
			return false;
		}
		var tagId = recordObj[0].tag_id;
		var url = '${pageContext.request.contextPath }/wzgl/tag/tag/'+tagId+'/edit';
		sw.showProcessBar();
		window.location.href = url;
	}
	//编辑跳转
	function editTagSingle(tagId){
		var url = '${pageContext.request.contextPath }/wzgl/tag/tag/'+tagId+'/edit';
		sw.showProcessBar();
		window.location.href = url;
	}
	//初始化绑定方法
	$(function(){
		$('#add_tag').bind('click', addTag);
		$('#edit_tag').bind('click', editTag);
		$('#open_tag').bind('click', openTag);
		$('#close_tag').bind('click', closeTag);
		$('#delete_tag').bind('click', deleteTag);
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
			<li class="active"><a href="javascript:void(0);"><i class="fa fa-tag"></i>标签管理</a></li>
		</ul>
		<div class="panel panel-default">
			<div class="panel-heading"><i class="fa fa-bell"></i>标签管理</div>
			<div class="panel-operation">
				<div class="btn-group">
					<sw:button accesskey="A" id="add_tag" value="新建 (A)" cssClass="btn btn-primary" limit="wzgl/tag/tag/add" icon="plus" />
					<sw:button accesskey="M" id="edit_tag" value="编辑 (M)" cssClass="btn btn-primary" limit="wzgl/tag/tag/-/edit" icon="edit" />
					<sw:button accesskey="O" id="open_tag" value="启用 (O)" cssClass="btn btn-success" limit="wzgl/tag/tag/open" icon="check" />
					<sw:button accesskey="C" id="close_tag" value="禁用 (C)" cssClass="btn btn-warning" limit="wzgl/tag/tag/close" icon="minus" />
                                        <sw:button accesskey="W" id="delete_tag" value="删除 (W)" cssClass="btn btn-danger" limit="wzgl/tag/tag/delete" icon="trash-o" />
				</div>
			</div>
			<div id="grid_container" class="dlshouwen-grid-container"></div>
			<div id="grid_toolbar_container" class="dlshouwen-grid-toolbar-container"></div>
		</div>
		<div class="clearfix"></div>
	</div>
</body>
</html>