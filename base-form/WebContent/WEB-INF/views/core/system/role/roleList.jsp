<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 角色管理</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
	<script type="text/javascript">
	var gridColumns = [
		{id:'operation', title:'操作', columnClass:'text-center', columnStyle:'width:140px;', hideType:'xs', extra:false, 'export':false, print:false, advanceQuery:false, resolution:function(value, record, column, grid, dataNo, columnNo){
			var content = '';
			content += '<button type="button" class="btn btn-xs btn-icon btn-primary" title="编辑" limit="core/system/role/-/edit" onclick="editRoleSingle(\''+record.role_id+'\')"><i class="fa fa-edit"></i></button>';
			content += '&nbsp;&nbsp;';
			content += '<button type="button" class="btn btn-xs btn-icon btn-danger" title="删除" limit="core/system/role/delete" onclick="deleteRoleSingle(\''+record.role_id+'\')"><i class="fa fa-trash-o"></i></button>';
			content += '&nbsp;&nbsp;';
			content += '<button type="button" class="btn btn-xs btn-icon btn-default" title="配置功能" limit="core/system/role/-/set_limit" onclick="setLimitSingle(\''+record.role_id+'\')"><i class="fa fa-cubes"></i></button>';
			content += '&nbsp;&nbsp;';
			content += '<button type="button" class="btn btn-xs btn-icon btn-default" title="配置用户" limit="core/system/role/-/set_user" onclick="setUserSingle(\''+record.role_id+'\')"><i class="fa fa-user"></i></button>';
			return content;
		}},
		{id:'role_name', title:'角色名称', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'lk' },
		{id:'system_name', title:'所属系统', type:'string', columnClass:'text-center', hideType:'xs', fastQuery:true, fastQueryType:'lk' },
		{id:'remark', title:'备注', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq' },
		{id:'creator_name', title:'创建人', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'lk' },
		{id:'create_time', title:'创建时间', type:'date', format:'yyyy-MM-dd hh:mm:ss', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'range' },
		{id:'editor_name', title:'编辑人', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'lk' },
		{id:'edit_time', title:'编辑时间', type:'date', format:'yyyy-MM-dd hh:mm:ss', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'range' }
	];
	var gridOption = {
		loadURL : '${pageContext.request.contextPath }/core/system/role/list',
		functionCode : 'CORE_ROLE',
		check : true,
		exportFileName : '角色列表',
		columns : gridColumns,
		onRowDblClick : function(value, record, column, grid, dataNo, columnNo, cell, row, extraCell, e){
			editRoleSingle(record.role_id);
		},
		onGridComplete : function(value, record, column, grid, dataNo, columnNo, cell, row, extraCell, e){
			var urls = '';
			urls += 'core/system/role/-/edit,';
			urls += 'core/system/role/delete,';
			urls += 'core/system/role/-/set_limit,';
			urls += 'core/system/role/-/set_user';
			sw.initGridButtonLimit(urls, '${applicationScope.__SYSTEM_ATTRIBUTE__.show_button_when_no_limit.content }');
		}
	};
	var grid = $.fn.dlshouwen.grid.init(gridOption);;
	//数据加载
	$(function(){
		grid.load();
	});
	//跳转添加
	function addRole(){
		var url='${pageContext.request.contextPath }/core/system/role/add';
		sw.showProcessBar();
		window.location.href = url;
	}
	//删除操作
	function deleteRole(){
		var recordObj = grid.getCheckedRecords();
		if(recordObj.length==0){
			sw.toast('请选择要删除的角色！', 'warning', 3000);
			return false;
		}
		if(!confirm('确定删除选中角色吗？')){
			return;
		}
		var roleIds = '';
		for(var i=0;i<recordObj.length;i++){
			roleIds += recordObj[i].role_id + ((i!=recordObj.length-1)?',':'');
		}
		var url = '${pageContext.request.contextPath }/core/system/role/delete';
		var params = new Object();
		params.roleIds = roleIds;
		sw.showProcessBar();
		sw.ajaxSubmitRefreshGrid(url, params, grid);
	}
	//删除操作
	function deleteRoleSingle(roleId){
		if(!confirm('确定删除此角色吗？')){
			return;
		}
		var url = '${pageContext.request.contextPath }/core/system/role/delete';
		var params = new Object();
		params.roleIds = roleId;
		sw.showProcessBar();
		sw.ajaxSubmitRefreshGrid(url, params, grid);
	}
	//编辑跳转
	function editRole(){
		var recordObj = grid.getCheckedRecords();
		if(recordObj.length==0){
			sw.toast('请选择要编辑的角色！', 'warning', 3000);
			return false;
		}
		var roleId = recordObj[0].role_id;
		var url = '${pageContext.request.contextPath }/core/system/role/'+roleId+'/edit';
		sw.showProcessBar();
		window.location.href = url;
	}
	//编辑跳转
	function editRoleSingle(roleId){
		var url = '${pageContext.request.contextPath }/core/system/role/'+roleId+'/edit';
		sw.showProcessBar();
		window.location.href = url;
	}
	//配置功能
	function setLimit(){
		var recordObj = grid.getCheckedRecords();
		if(recordObj.length==0){
			sw.toast('请选择要配置功能的角色！', 'warning', 3000);
			return false;
		}
		var roleId = recordObj[0].role_id;
		var url = '${pageContext.request.contextPath }/core/system/role/'+roleId+'/set_limit';
		sw.showProcessBar();
		window.location.href = url;
	}
	//配置功能
	function setLimitSingle(roleId){
		var url = '${pageContext.request.contextPath }/core/system/role/'+roleId+'/set_limit';
		sw.showProcessBar();
		window.location.href = url;
	}
	//配置用户
	function setUser(){
		var recordObj = grid.getCheckedRecords();
		if(recordObj.length==0){
			sw.toast('请选择要配置用户的角色！');
			return false;
		}
		var roleId = recordObj[0].role_id;
		var url = '${pageContext.request.contextPath }/core/system/role/'+roleId+'/set_user';
		sw.showProcessBar();
		window.location.href = url;
	}
	//配置用户
	function setUserSingle(roleId){
		var url = '${pageContext.request.contextPath }/core/system/role/'+roleId+'/set_user';
		sw.showProcessBar();
		window.location.href = url;
	}
	//初始化绑定方法
	$(function(){
		$('#add_role').bind('click', addRole);
		$('#edit_role').bind('click', editRole);
		$('#delete_role').bind('click', deleteRole);
		$('#set_limit').bind('click', setLimit);
		$('#set_user').bind('click', setUser);
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
			<li class="active"><a href="javascript:void(0);"><i class="fa fa-lock"></i>角色管理</a></li>
		</ul>
		<div class="panel panel-default">
			<div class="panel-heading"><i class="fa fa-users"></i>角色管理</div>
			<div class="panel-operation">
				<div class="btn-group">
					<sw:button accesskey="A" id="add_role" value="新建 (A)" cssClass="btn btn-primary" limit="core/system/role/add" icon="plus" />
					<sw:button accesskey="M" id="edit_role" value="编辑 (M)" cssClass="btn btn-primary" limit="core/system/role/-/edit" icon="edit" />
					<sw:button accesskey="W" id="delete_role" value="删除 (W)" cssClass="btn btn-danger" limit="core/system/role/delete" icon="trash-o" />
					<sw:button accesskey="L" id="set_limit" value="配置功能 (L)" cssClass="btn btn-default" limit="core/system/role/-/set_limit" icon="cubes" />
					<sw:button accesskey="U" id="set_user" value="配置用户 (U)" cssClass="btn btn-default" limit="core/system/role/-/set_user" icon="user" />
				</div>
			</div>
			<div id="grid_container" class="dlshouwen-grid-container"></div>
			<div id="grid_toolbar_container" class="dlshouwen-grid-toolbar-container"></div>
		</div>
		<div class="clearfix"></div>
	</div>
</body>
</html>