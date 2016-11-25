<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 配置用户</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
	<%
	//通过静态域设置码制映射对象
	CodeTableUtils.createCodeTableJS(application, out, "sex");
	CodeTableUtils.createCodeTableJS(application, out, "card_type");
	CodeTableUtils.createCodeTableJS(application, out, "folk");
	CodeTableUtils.createCodeTableJS(application, out, "degree");
	%>
	<script type="text/javascript">
	//定义返回URL
	var goBackUrl = '${pageContext.request.contextPath }/core/system/role';
	//返回
	function goBack(){
		window.location.href = goBackUrl;
	}
	var gridColumns = [
		{id:'operate', title:'操作', columnClass:'text-center', columnStyle:'width:60px;', hideType:'xs', extra:false, 'export':false, print:false, advanceQuery:false, resolution:function(value, record, column, grid, dataNo, columnNo){
			var content = '<input type="checkbox" name="user_id" value="'+record.user_id+'" '+(record.checked=='1'?'checked="checked"':'')+' />';
			return content;
		}},
		{id:'user_code', title:'用户编号', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq' },
		{id:'user_name', title:'用户名称', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'lk' },
		{id:'dept_name', title:'所属部门', type:'string', columnClass:'text-center', hideType:'xs|sm', fastQuery:true, fastQueryType:'lk' },
		{id:'valid_type', title:'是否有效', type:'string', codeTable:code_table_info['valid_type'], columnClass:'text-center', hideType:'xs', fastQuery:true, fastQueryType:'eq' },
		{id:'sex', title:'性别', type:'string', codeTable:code_table_info['sex'], columnClass:'text-center', hideType:'xs|sm|md', fastQuery:true, fastQueryType:'eq' },
		{id:'phone', title:'联系电话', type:'string', columnClass:'text-center', hideType:'xs|sm|md', fastQuery:true, fastQueryType:'lk' },
		{id:'card_type', title:'证件类型', type:'string', codeTable:code_table_info['card_type'], columnClass:'text-center', hideType:'xs|sm|md|lg', fastQuery:true, fastQueryType:'eq' },
		{id:'card_id', title:'证件号', type:'string', columnClass:'text-center', hideType:'xs|sm|md|lg', fastQuery:true, fastQueryType:'lk' },
		{id:'birthday', title:'出生日期', type:'date', format:'yyyy-MM-dd', otype:'string', oformat:'yyyy-MM-dd hh:mm:ss', columnClass:'text-center', hideType:'xs|sm|md|lg', fastQuery:true, fastQueryType:'range' },
		{id:'work_date', title:'工作日期', type:'date', format:'yyyy-MM-dd', otype:'string', oformat:'yyyy-MM-dd hh:mm:ss', columnClass:'text-center', hideType:'xs|sm|md|lg', fastQuery:true, fastQueryType:'range' },
		{id:'folk', title:'民族', type:'string', codeTable:code_table_info['folk'], columnClass:'text-center', hideType:'xs|sm|md|lg', fastQuery:true, fastQueryType:'eq' },
		{id:'degree', title:'学历', type:'string', codeTable:code_table_info['degree'], columnClass:'text-center', hideType:'xs|sm|md|lg', fastQuery:true, fastQueryType:'eq' },
		{id:'email', title:'E-mail', type:'string', columnClass:'text-center', hideType:'xs|sm|md|lg', fastQuery:true, fastQueryType:'lk' },
		{id:'address', title:'联系地址', type:'string', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'lk' },
		{id:'remark', title:'备注', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'lk' },
		{id:'creator_name', title:'创建人', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'lk' },
		{id:'create_time', title:'创建时间', type:'date', format:'yyyy-MM-dd hh:mm:ss', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'range' },
		{id:'editor_name', title:'编辑人', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'lk' },
		{id:'edit_time', title:'编辑时间', type:'date', format:'yyyy-MM-dd hh:mm:ss', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'range' }
	];
	var gridOption = {
		loadURL : '${pageContext.request.contextPath }/core/system/role/${roleId}/set_user/list',
		functionCode : 'CORE_USER',
		exportFileName : '用户列表',
		columns : gridColumns
	};
	var grid = $.fn.dlshouwen.grid.init(gridOption);;
	//数据加载
	$(function(){
		grid.load();
	});
	//配置用户
	function setUser(){
		if($('input[name=user_id]').length==0){
			sw.toast('没有用户数据！', 'warning', 3000);
			return;
		}
		if(!confirm('确认要配置用户吗？')){
			return;
		}
		var addUserIds = '';
		var deleteUserIds = '';
		$('input[name=user_id]:checked').each(function(i){
			addUserIds += this.value + ((i!=$('input[name=user_id]:checked').length-1)?',':'');
		});
		$('input[name=user_id]:not(:checked)').each(function(i){
			deleteUserIds += this.value + ((i!=$('input[name=user_id]:not(:checked)').length-1)?',':'');
		});
		var url = '${pageContext.request.contextPath }/core/system/role/set_user';
		var params = new Object();
		params.roleId = '${roleId }';
		params.addUserIds = addUserIds;
		params.deleteUserIds = deleteUserIds;
		sw.showProcessBar();
		sw.ajaxSubmitRefreshGrid(url, params, grid);
	}
	//初始化绑定方法
	$(function(){
		$('#checkedAll').bind('click', function(){
			$('input[name=user_id]').attr('checked', 'checked');
		});
		$('#unCheckedAll').bind('click', function(){
			$('input[name=user_id]').removeAttr('checked');
		});
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
			<li><a href="${pageContext.request.contextPath }/core/system/role"><i class="fa fa-lock"></i>角色管理</a></li>
			<li class="active"><a href="javascript:void(0);"><i class="fa fa-user"></i>为 <span class="text-primary">${role.role_name }</span> 配置用户</a></li>
		</ul>
		<div class="panel panel-default">
			<div class="panel-heading"><i class="fa fa-users"></i>为 <span class="text-primary">${role.role_name }</span> 配置用户</div>
			<div class="panel-operation">
				<div class="btn-group">
					<sw:button id="checkedAll" value="全选" cssClass="btn btn-default" icon="check-square-o" />
					<sw:button id="unCheckedAll" value="反选" cssClass="btn btn-default" icon="square-o" />
				</div>
				&nbsp;&nbsp;|&nbsp;&nbsp;
				<div class="btn-group">
					<sw:button accesskey="S" id="set_user" value="保存配置 (S)" cssClass="btn btn-success" limit="core/system/role/-/set_user" icon="save" />
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
