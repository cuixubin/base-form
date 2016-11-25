<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 任务查看</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
	<script type="text/javascript">
	var gridColumns = [
		{id:'message', title:'任务简述', fastQuery:true, fastQueryType:'lk' }
	];
	var gridOption = {
		loadURL : '${pageContext.request.contextPath }/core/task/view_task/list',
		loadAll : true,
		pageSize : 2,
		functionCode : 'CORE_VIEW_TASK',
		exportFileName : '任务列表',
		columns : gridColumns,
		onRowDblClick : function(value, record, column, grid, dataNo, columnNo, cell, row, extraCell, e){
			sw.showProcessBar();
			window.location.href = path+'/'+record.url;
		}
	};
	var grid = $.fn.dlshouwen.grid.init(gridOption);;
	//数据加载
	$(function(){
		grid.load();
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
			<li><a href="javascript:void(0);"><i class="fa fa-tachometer"></i>任务管理</a></li>
			<li class="active"><a href="javascript:void(0);"><i class="fa fa-eye"></i>任务查看</a></li>
		</ul>
		<div class="panel panel-default">
			<div class="panel-heading"><i class="fa fa-eye"></i>任务查看</div>
			<div id="grid_container" class="dlshouwen-grid-container"></div>
			<div id="grid_toolbar_container" class="dlshouwen-grid-toolbar-container"></div>
		</div>
		<div class="clearfix"></div>
	</div>
</body>
</html>