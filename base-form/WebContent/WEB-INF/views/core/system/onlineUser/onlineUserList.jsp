<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 在线用户管理</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
	<%
	//通过静态域设置码制映射对象
	CodeTableUtils.createCodeTableJS(application, out, "login_status");
	CodeTableUtils.createCodeTableJS(application, out, "zero_one");
	CodeTableUtils.createCodeTableJS(application, out, "logout_type");
	%>
	<script type="text/javascript">
	var gridColumns = [
		{id:'operation', title:'操作', columnClass:'text-center', columnStyle:'width:60px;', hideType:'xs', extra:false, 'export':false, print:false, advanceQuery:false, resolution:function(value, record, column, grid, dataNo, columnNo){
			var content = '';
			content += '<button type="button" class="btn btn-xs btn-icon btn-danger" title="强制下线" limit="core/system/online_user/force" onclick="forceUserOfflineSingle(\''+record.log_id+'\')"><i class="fa fa-flash"></i></button>';
			return content;
		}},
		{id:'log_id', title:'登录编号', type:'string', columnClass:'text-center', hideType:'xs|sm|md|lg', fastQuery:true, fastQueryType:'eq' },
		{id:'session_id', title:'SESSION ID', type:'string', columnClass:'text-center', hideType:'xs|sm|md|lg', fastQuery:true, fastQueryType:'eq' },
		{id:'login_user_name', title:'登录人姓名', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'lk' },
		{id:'login_time', title:'登录时间', type:'date', format:'yyyy-MM-dd hh:mm:ss', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'range' },
		{id:'ip', title:'IP地址', type:'string', columnClass:'text-center', hideType:'xs|sm', fastQuery:true, fastQueryType:'lk' },
		{id:'login_status', title:'登录状态', type:'string', codeTable:code_table_info['login_status'], columnClass:'text-center', hideType:'xs|sm', fastQuery:true, fastQueryType:'eq' },
		{id:'is_logout', title:'是否登出', type:'string', codeTable:code_table_info['zero_one'], columnClass:'text-center', hideType:'xs|sm|md|lg', fastQuery:true, fastQueryType:'eq' }
	];
	var gridOption={
		loadURL : '${pageContext.request.contextPath }/core/system/online_user/list',
		functionCode : 'CORE_ONLINE_USER',
		check:true,
		exportFileName : '在线用户列表',
		columns : gridColumns,
		onRowDblClick : function(value, record, column, grid, dataNo, columnNo, cell, row, extraCell, e){
			forceUserOfflineSingle(record.log_id);
		},
		onGridComplete : function(value, record, column, grid, dataNo, columnNo, cell, row, extraCell, e){
			var urls = '';
			urls += 'core/system/online_user/force';
			sw.initGridButtonLimit(urls, '${applicationScope.__SYSTEM_ATTRIBUTE__.show_button_when_no_limit.content }');
		}
	};
	var grid = $.fn.dlshouwen.grid.init(gridOption);
	//数据加载
	$(function(){
		grid.load();
	});
	//强制下线
	function forceUserOffline(){
		var recordObj = grid.getCheckedRecords();
		if(recordObj.length==0){
			sw.toast('请选择要强制下线的用户！', 'warning', 3000);
			return false;
		}
		if(!confirm('确定强制下线选中在线用户吗？')){
			return;
		}
		var logIds = '';
		for(var i=0;i<recordObj.length;i++){
			logIds += recordObj[i].log_id + ((i!=recordObj.length-1)?',':'');
		}
		var url = '${pageContext.request.contextPath }/core/system/online_user/force';
		var params = new Object();
		params.logIds = logIds;
		sw.showProcessBar();
		sw.ajaxSubmitRefreshGrid(url, params, grid);
	}
	//强制下线
	function forceUserOfflineSingle(logId){
		if(!confirm('确定强制下线此在线用户吗？')){
			return;
		}
		var url = '${pageContext.request.contextPath }/core/system/online_user/force';
		var params = new Object();
		params.logIds = logId;
		sw.showProcessBar();
		sw.ajaxSubmitRefreshGrid(url, params, grid);
	}
	//初始化绑定方法
	$(function(){
		$('#force_user_offline').bind('click', forceUserOffline);
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
			<li class="active"><a href="javascript:void(0);"><i class="fa fa-male"></i>在线用户管理</a></li>
		</ul>
		<div class="panel panel-default">
			<div class="panel-heading"><i class="fa fa-male"></i>在线用户管理</div>
			<div class="panel-operation">
				<div class="btn-group">
					<sw:button accesskey="Q" id="force_user_offline" value="强制下线 (Q)" cssClass="btn btn-danger" limit="core/system/online_user/force" icon="flash" />
				</div>
			</div>
			<div id="grid_container" class="dlshouwen-grid-container"></div>
			<div id="grid_toolbar_container" class="dlshouwen-grid-toolbar-container"></div>
		</div>
		<div class="clearfix"></div>
	</div>
</body>
</html>