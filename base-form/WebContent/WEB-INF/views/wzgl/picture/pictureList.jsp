<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 大图管理</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
	<%
	//通过静态域设置码制映射对象
            CodeTableUtils.createCodeTableJS(application, out, "picture_flag");
            CodeTableUtils.createCodeTableJS(application, out, "picture_show");
            CodeTableUtils.createCodeTableJS(application, out, "album_flag");
	%>
	<script type="text/javascript">
	var gridColumns = [
		{id:'operation', title:'操作', columnClass:'text-center', columnStyle:'width:140px;', hideType:'xs', extra:false, 'export':false, print:false, advanceQuery:false, resolution:function(value, record, column, grid, dataNo, columnNo){
			var content = '';
//                        content += '<button type="button" class="btn btn-xs btn-icon btn-default" title="查看" onclick="viewArticleSingle(\''+record.picture_id+'\')"><i class="fa fa-eye"></i></button>';
//			content += '&nbsp;&nbsp;';
			content += '<button type="button" class="btn btn-xs btn-icon btn-primary" title="编辑" limit="wzgl/picture/picture/-/edit" onclick="editPictureSingle(\''+record.picture_id+'\')"><i class="fa fa-edit"></i></button>';
			content += '&nbsp;&nbsp;';
			if(record.show=='1'){
				content += '<button type="button" class="btn btn-xs btn-icon btn-warning" title="隐藏"  limit="wzgl/picture/picture/hide" onclick="hidePictureSingle(\''+record.picture_id+'\')"><i class="fa fa-minus"></i></button>';
			}else{
				content += '<button type="button" class="btn btn-xs btn-icon btn-success" title="显示" limit="wzgl/picture/picture/show" onclick="showPictureSingle(\''+record.picture_id+'\')"><i class="fa fa-check"></i></button>';
			}
			content += '&nbsp;&nbsp;';
			content += '<button type="button" class="btn btn-xs btn-icon btn-danger" title="删除" limit="wzgl/picture/picture/delete" onclick="deletePictureSingle(\''+record.picture_id+'\')"><i class="fa fa-trash-o"></i></button>';
			return content;
		}},
		{id:'picture_name', title:'图片名称', type:'string', fastQuery:true, fastQueryType:'lk' },
		{id:'user_name', title:'创建人', type:'string', columnClass:'text-center', hideType:'xs|sm', fastQuery:true, fastQueryType:'lk' },
		{id:'create_time', title:'创建时间', type:'date', format:'yyyy-MM-dd hh:mm:ss', columnClass:'text-center', hideType:'xs|sm|md', fastQuery:true, fastQueryType:'lk' },
		{id:'flag', title:'类型', type:'string', codeTable:code_table_info['picture_flag'], columnClass:'text-center', fastQuery:true, fastQueryType:'eq', resolution:function(value, record, column, grid, dataNo, columnNo){
                    return code_table_info['picture_flag'][value];
		}},
                {id:'show', title:'状态', type:'string', codeTable:code_table_info['picture_show'],columnClass:'text-center', hideType:'xs|sm', fastQuery:true, fastQueryType:'eq', resolution:function(value, record, column, grid, dataNo, columnNo){
                        return value=='0'?('<span class="label label-danger">'+code_table_info['picture_show'][value]+'</span>'):('<span class="label label-success">'+code_table_info['picture_show'][value]+'</span>');
                }},
//                {id:'album_id', title:'所属相册', type:'string', codeTable:code_table_info['album_flag'],columnClass:'text-center', hideType:'xs|sm|md|lg', fastQuery:true, fastQueryType:'eq', resolution:function(value, record, column, grid, dataNo, columnNo){
//                        return code_table_info['album_flag'][value];
//                }},
		{id:'description', title:'图片描述', type:'string', columnClass:'text-center', hideType:'xs|sm|md|lg', fastQuery:true, fastQueryType:'lk' },
		{id:'update_time', title:'更新时间', type:'date', format:'yyyy-MM-dd hh:mm:ss', columnClass:'text-center', hideType:'xs|sm|md|lg', fastQuery:true, fastQueryType:'range' },
		
		{id:'order', title:'排序号', type:'string', columnClass:'text-center', hideType:'xs|sm|md|lg', fastQuery:true, fastQueryType:'range' }
	];
	var gridOption = {
		loadURL : '${pageContext.request.contextPath }/wzgl/picture/picture/list',
		functionCode : 'WZGL_PICTURE',
		check:true,
		exportFileName : '图片列表',
		columns : gridColumns,
		onRowDblClick : function(value, record, column, grid, dataNo, columnNo, cell, row, extraCell, e){
			editPictureSingle(record.picture_id);
		},
		onGridComplete : function(value, record, column, grid, dataNo, columnNo, cell, row, extraCell, e){
			var urls = '';
			urls += 'wzgl/picture/picture/-/edit,';
			urls += 'wzgl/picture/picture/show,';
			urls += 'wzgl/picture/picture/hide,';
			urls += 'wzgl/picture/picture/delete';
			sw.initGridButtonLimit(urls, '${applicationScope.__SYSTEM_ATTRIBUTE__.show_button_when_no_limit.content }');
		}
	};
	var grid = $.fn.dlshouwen.grid.init(gridOption);;
	//数据加载
	$(function(){
		grid.load();
	});
	//跳转添加
	function addPicture(){
		var url = '${pageContext.request.contextPath }/wzgl/picture/picture/add';
		sw.showProcessBar();
		window.location.href = url;
	}
	//展示操作
	function showPicture(){
		var recordObj = grid.getCheckedRecords();
		if(recordObj.length==0){
			sw.toast('请选择要显示的图片！', 'warning', 3000);
			return false;
		}
		if(!confirm('确定显示选中的图片吗？')){
			return;
		}
		var pictureIds = '';
		for(var i=0;i<recordObj.length;i++){
			pictureIds += recordObj[i].picture_id + ((i!=recordObj.length-1)?',':'');
		}
		var url = '${pageContext.request.contextPath }/wzgl/picture/picture/show';
		var params = new Object();
		params.pictureIds = pictureIds;
		sw.showProcessBar();
		sw.ajaxSubmitRefreshGrid(url, params, grid);
	}
	//展示操作
	function showPictureSingle(pictureId){
		if(!confirm('确定显示此图片吗？')){
			return;
		}
		var url = '${pageContext.request.contextPath }/wzgl/picture/picture/show';
		var params = new Object();
		params.pictureIds = pictureId;
		sw.showProcessBar();
		sw.ajaxSubmitRefreshGrid(url, params, grid);
	}
	//隐藏操作
	function hidePicture(){
		var recordObj = grid.getCheckedRecords();
		if(recordObj.length==0){
			sw.toast('请选择要隐藏的图片！', 'warning', 3000);
			return false;
		}
		if(!confirm('确定隐藏选中的图片吗？')){
			return;
		}
		var pictureIds = '';
		for(var i=0;i<recordObj.length;i++){
			pictureIds += recordObj[i].picture_id + ((i!=recordObj.length-1)?',':'');
		}
		var url = '${pageContext.request.contextPath }/wzgl/picture/picture/hide';
		var params = new Object();
		params.pictureIds = pictureIds;
		sw.showProcessBar();
		sw.ajaxSubmitRefreshGrid(url, params, grid);
	}
	//隐藏操作
	function hidePictureSingle(pictureId){
		if(!confirm('确定隐藏此图片吗？')){
			return;
		}
		var url = '${pageContext.request.contextPath }/wzgl/picture/picture/hide';
		var params = new Object();
		params.pictureIds = pictureId;
		sw.showProcessBar();
		sw.ajaxSubmitRefreshGrid(url, params, grid);
	}
	//删除操作
	function deletePicture(){
		var recordObj = grid.getCheckedRecords();
		if(recordObj.length==0){
			sw.toast('请选择要删除的图片！', 'warning', 3000);
			return false;
		}
		if(!confirm('确定删除选中图片吗？')){
			return;
		}
		var pictureIds = '';
		for(var i=0;i<recordObj.length;i++){
			pictureIds += recordObj[i].picture_id + ((i!=recordObj.length-1)?',':'');
		}
		var url = '${pageContext.request.contextPath }/wzgl/picture/picture/delete';
		var params = new Object();
		params.pictureIds = pictureIds;
		sw.showProcessBar();
		sw.ajaxSubmitRefreshGrid(url, params, grid);
	}
	//删除操作
	function deletePictureSingle(pictureId){
		if(!confirm('确定删除此图片吗？')){
			return;
		}
		var url = '${pageContext.request.contextPath }/wzgl/picture/picture/delete';
		var params = new Object();
		params.pictureIds = pictureId;
		sw.showProcessBar();
		sw.ajaxSubmitRefreshGrid(url, params, grid);
	}
	//编辑跳转
	function editPicture(){
		var recordObj = grid.getCheckedRecords();
		if(recordObj.length==0){
			sw.toast('请选择要编辑的图片！', 'warning', 3000);
			return false;
		}
		var pictureId = recordObj[0].picture_id;
		var url = '${pageContext.request.contextPath }/wzgl/picture/picture/'+pictureId+'/edit';
		sw.showProcessBar();
		window.location.href = url;
	}
	//编辑跳转
	function editPictureSingle(pictureId){
		var url = '${pageContext.request.contextPath }/wzgl/picture/picture/'+pictureId+'/edit';
		sw.showProcessBar();
		window.location.href = url;
	}
	//初始化绑定方法
	$(function(){
		$('#add_picture').bind('click', addPicture);
		$('#edit_picture').bind('click', editPicture);
		$('#show_picture').bind('click', showPicture);
		$('#hide_picture').bind('click', hidePicture);
		$('#delete_picture').bind('click', deletePicture);
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
			<li class="active"><a href="javascript:void(0);"><i class="fa fa-file-image-o"></i>大图管理</a></li>
		</ul>
		<div class="panel panel-default">
			<div class="panel-heading"><i class="fa fa-bell"></i>大图管理</div>
			<div class="panel-operation">
				<div class="btn-group">
					<sw:button accesskey="A" id="add_picture" value="新建 (A)" cssClass="btn btn-primary" limit="wzgl/picture/picture/add" icon="plus" />
					<sw:button accesskey="M" id="edit_picture" value="编辑 (M)" cssClass="btn btn-primary" limit="wzgl/picture/picture/-/edit"  icon="edit" />
                                        <sw:button accesskey="O" id="show_picture" value="显示 (O)" cssClass="btn btn-success" limit="wzgl/picture/picture/show" icon="check" />
					<sw:button accesskey="C" id="hide_picture" value="隐藏 (C)" cssClass="btn btn-warning" limit="wzgl/picture/picture/hide" icon="minus" />
					<sw:button accesskey="W" id="delete_picture" value="删除 (W)" cssClass="btn btn-danger" limit="wzgl/picture/picture/delete" icon="trash-o" />
				</div>
			</div>
			<div id="grid_container" class="dlshouwen-grid-container"></div>
			<div id="grid_toolbar_container" class="dlshouwen-grid-toolbar-container"></div>
		</div>
		<div class="clearfix"></div>
	</div>
</body>
</html>