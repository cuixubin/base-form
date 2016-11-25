<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 配置角色</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
	<script type="text/javascript">
	//定义返回URL
	var goBackUrl = '${pageContext.request.contextPath }/core/system/user';
	//返回
	function goBack(){
		sw.showProcessBar();
		window.location.href = goBackUrl;
	}
	var gridColumns = [
		{id:'operate', title:'操作', columnClass:'text-center', columnStyle:'width:60px;', hideType:'xs', extra:false, 'export':false, print:false, advanceQuery:false, resolution:function(value, record, column, grid, dataNo, columnNo){
			var content = '<input type="checkbox" name="role_id" value="'+record.role_id+'" '+(record.checked=='1'?'checked="checked"':'')+' />';
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
	var gridOption={
		loadURL : '${pageContext.request.contextPath }/core/system/user/${userId}/set_role/list',
		functionCode : 'CORE_ROLE',
		exportFileName : '角色列表',
		columns : gridColumns
	};
	var grid = $.fn.dlshouwen.grid.init(gridOption);;
	//数据加载
	$(function(){
		grid.load();
	});
	//配置角色
	function setRole(){
		if($('input[name=role_id]').length==0){
			sw.toast('没有角色数据！');
			return;
		}
		if(!confirm('确认要配置角色吗？')){
			return;
		}
		var addRoleIds = '';
		var deleteRoleIds = '';
		$('input[name=role_id]:checked').each(function(i){
			addRoleIds += this.value + ((i!=$('input[name=role_id]:checked').length-1)?',':'');
		});
		$('input[name=role_id]:not(:checked)').each(function(i){
			deleteRoleIds += this.value + ((i!=$('input[name=role_id]:not(:checked)').length-1)?',':'');
		});
		var url = '${pageContext.request.contextPath }/core/system/user/set_role';
		var params = new Object();
		params.userId = '${userId }';
		params.addRoleIds = addRoleIds;
		params.deleteRoleIds = deleteRoleIds;
		sw.showProcessBar();
		sw.ajaxSubmitRefreshGrid(url, params, grid);
	}
	//初始化绑定方法
	$(function(){
		$('#checkedAll').bind('click', function(){
			$('input[name=role_id]').attr('checked', 'checked');
		});
		$('#unCheckedAll').bind('click', function(){
			$('input[name=role_id]').removeAttr('checked');
		});
		$('#set_role').bind('click', setRole);
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
			<li><a href="${pageContext.request.contextPath }/core/system/user"><i class="fa fa-user"></i>用户管理</a></li>
			<li class="active"><a href="javascript:void(0);"><i class="fa fa-users"></i>为 <span class="text-primary">${user.user_name }</span> 配置角色</a></li>
		</ul>
		<div class="panel panel-default">
			<div class="panel-heading"><i class="fa fa-users"></i>为 <span class="text-primary">${user.user_name }</span> 配置角色</div>
			<div class="panel-operation">
				<div class="btn-group">
					<sw:button accesskey="A" id="checkedAll" value="全选 (A)" cssClass="btn btn-default" icon="check-square-o" />
					<sw:button accesskey="C" id="unCheckedAll" value="反选 (C)" cssClass="btn btn-default" icon="square-o" />
				</div>
				&nbsp;&nbsp;|&nbsp;&nbsp;
				<div class="btn-group">
					<sw:button accesskey="S" id="set_role" value="保存配置 (S)" cssClass="btn btn-success" limit="core/system/user/-/set_role" icon="save" />
					<button accesskey="B" type="button" class="btn btn-default" onclick="goBack();">
						<i class="fa fa-arrow-circle-left"></i>返回 (B)
					</button>
				</div>
			</div>
			<div id="grid_container" class="dlshouwen-grid-container"></div>
			<div id="grid_toolbar_container" class="dlshouwen-grid-toolbar-container"></div>
		</div>
		<div class="clearfix"></div>
	</div>
</body>
</html>
