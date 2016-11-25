<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 操作日志查询</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
	<%
	//通过静态域设置码制映射对象
	CodeTableUtils.createCodeTableJS(application, out, "operation_type");
	CodeTableUtils.createCodeTableJS(application, out, "operation_result");
	%>
	<style type="text/css">
	.chart{position:relative;}
	.chart-container{height:500px;padding:0;}
	</style>
	<script type="text/javascript">
	var gridColumns = [
		{id:'operation', title:'操作', columnClass:'text-center', columnStyle:'width:50px;', hideType:'xs', extra:false, 'export':false, print:false, advanceQuery:false, resolution:function(value, record, column, grid, dataNo, columnNo){
			var content = '';
			content += '<button type="button" class="btn btn-xs btn-icon btn-default" title="查看" onclick="viewOperationLogSingle(\''+record.log_id+'\')"><i class="fa fa-table"></i></button>';
			return content;
		}},
		{id:'operation_url', title:'操作地址', type:'string', hideType:'', columnStyle:'word-break:break-all;', fastQuery:true, fastQueryType:'lk' },
		{id:'operation_type', title:'操作类别', type:'string', codeTable:code_table_info['operation_type'], columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq' },
		{id:'operation_result', title:'操作结果', type:'string', codeTable:code_table_info['operation_result'], columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq' },
		{id:'error_reason', title:'错误原因', type:'string', hideType:'xs|sm|md|lg', fastQuery:true, fastQueryType:'lk', resolution:function(value, record, column, grid, dataNo, columnNo){
			return htmlEncode(value);
		}},
		{id:'operation_detail', title:'操作说明', type:'string', hideType:'xs|sm|md|lg', fastQuery:true, fastQueryType:'lk' },
		{id:'response_start', title:'响应开始时间', type:'date', format:'yyyy-MM-dd hh:mm:ss', hideType:'xs|sm|md|lg', columnClass:'text-center', fastQuery:true, fastQueryType:'range' },
		{id:'response_start', title:'响应结束时间', type:'date', format:'yyyy-MM-dd hh:mm:ss', hideType:'xs|sm|md|lg', columnClass:'text-center', fastQuery:true, fastQueryType:'range' },
		{id:'cost', title:'耗时', type:'number', columnClass:'text-center', hideType:'xs|sm|md|lg', fastQuery:true, fastQueryType:'range' },
		{id:'operator', title:'操作人', type:'string', columnClass:'text-center', hideType:'xs|sm|md|lg', fastQuery:true, fastQueryType:'eq' },
		{id:'operator_name', title:'操作人名称', type:'string', columnClass:'text-center', hideType:'xs|sm|md|lg', fastQuery:true, fastQueryType:'lk' },
		{id:'operator_dept_id', title:'操作人部门代码', type:'string', columnClass:'text-center', hideType:'xs|sm|md|lg', fastQuery:true, fastQueryType:'eq' },
		{id:'operator_dept_name', title:'操作人部门名称', type:'string', columnClass:'text-center', hideType:'xs|sm|md|lg', fastQuery:true, fastQueryType:'lk' },
		{id:'ip', title:'操作机IP地址', type:'string', columnClass:'text-center', hideType:'xs|sm|md|lg', fastQuery:true, fastQueryType:'lk' }
	];
	var gridOption = {
		loadURL : '${pageContext.request.contextPath }/core/log/operation_log/list',
		functionCode : 'CORE_OPERATION_LOG',
		check:true,
		exportFileName : '操作日志列表',
		columns : gridColumns,
		onRowDblClick : function(value, record, column, grid, dataNo, columnNo, cell, row, extraCell, e){
			viewOperationLogSingle(record.log_id);
		},
		onGridComplete : function(value, record, column, grid, dataNo, columnNo, cell, row, extraCell, e){
			constructChart();
		}
	};
	var grid = $.fn.dlshouwen.grid.init(gridOption);;
	//数据加载
	$(function(){
		grid.load();
	});
	//查看
	function viewOperationLog(){
		var recordObj = grid.getCheckedRecords();
		if(recordObj.length==0){
			sw.toast('请选择要查看的操作日志！', 'warning', 3000);
			return false;
		}
		var logId = recordObj[0].log_id;
		var url = '${pageContext.request.contextPath }/core/log/operation_log/'+logId+'/view';
		sw.openModal({
			id : 'viewOperationLog',
			icon : 'table', 
			title : '查看操作日志',
			url : url
		});
	}
	//查看
	function viewOperationLogSingle(logId){
		var url = '${pageContext.request.contextPath }/core/log/operation_log/'+logId+'/view';
		sw.openModal({
			id : 'viewOperationLog',
			icon : 'table', 
			title : '查看操作日志',
			url : url
		});
	}
	//设置需要的图表基础路径
	require.config({
		paths:{ 
			echarts:'${pageContext.request.contextPath }/resources/plugin/echarts/echarts',
			'echarts/chart/bar' : '${pageContext.request.contextPath }/resources/plugin/echarts/echarts-map',
			'echarts/chart/line': '${pageContext.request.contextPath }/resources/plugin/echarts/echarts-map',
			'echarts/chart/map' : '${pageContext.request.contextPath }/resources/plugin/echarts/echarts-map'
		}
	});
	//定义参数：图表对象、图表事件对象、图表数据对象、图表列信息、图表显示数据信息、饼图数据信息
	var chart, eventChart, chartData, chartCategories, chartSeriesDatas, pieChartSeriesDatas;
	//定义参数：图表标题、图表副标题、值坐标名称、列坐标数据名称、值坐标数据名称
	var chartTitle = '业务日志统计报表';
	var chartSubTitle = '';
	var valueAxisTitle = '访问数量';
	var categoryField = 'category_field_name';
	var valueField = 'operation_count';
	//获得横坐标列表数据
	function getCategories(__chartData__){
		chartCategories = new Array();
		for(var i=0; i<__chartData__.length; i++){
			chartCategories.push(__chartData__[i][categoryField]);
		}
	}
	//获得显示内容数据
	function getSeriesData(__chartData__){
		chartSeriesDatas = new Array();
		var chartSeriesData = new Object();
		chartSeriesData.name = valueAxisTitle;
		chartSeriesData.type = 'bar';
		chartSeriesData.data = new Array();
		for(var i=0; i<__chartData__.length; i++){
			chartSeriesData.data.push(__chartData__[i][valueField]);
		}
		//处理线图
		var chart_type = $('#chart_type').val();
		if(chart_type=='line'){
			chartSeriesData.type = 'line';
		}
		chartSeriesDatas.push(chartSeriesData);
	}
	//获得饼状图图表数据
	function getPieChartData(__chartData__){
		pieChartSeriesDatas = new Array();
		var pieChartSeriesData = new Object();
		pieChartSeriesData.name = valueAxisTitle;
		pieChartSeriesData.type = 'pie';
		pieChartSeriesData.radius = '55%';
		pieChartSeriesData.center = ['50%', '60%'];
		pieChartSeriesData.data = new Array();
		for(var i=0; i<__chartData__.length; i++){
			var obj = new Object();
			obj.name = __chartData__[i][categoryField];
			obj.value = __chartData__[i][valueField];
			pieChartSeriesData.data.push(obj);
		}
		pieChartSeriesDatas.push(pieChartSeriesData);
	}
	//构建图表
	function constructChart(){
		chartSubTitle = '统计时间：'+new Date().format('yyyy-MM-dd hh:mm:ss S');
		var url = '${pageContext.request.contextPath }/core/log/operation_log/chart/data';
		var params = new Object();
		params.data_type = $('#data_type').val();
		params.chart_type = $('#chart_type').val();
		params.fastQueryParameters = grid.fastQueryParameters==null?'{}':JSON.stringify(grid.fastQueryParameters);
		params.advanceQueryConditions = grid.advanceQueryParameter.advanceQueryConditions==null?'[]':JSON.stringify(grid.advanceQueryParameter.advanceQueryConditions);
		params.advanceQuerySorts = grid.advanceQueryParameter.advanceQuerySorts==null?'[]':JSON.stringify(grid.advanceQueryParameter.advanceQuerySorts);
		sw.showProcessBar();
		sw.ajaxSubmit(url, params, function(_chartData){
			$('#countConfigModal').modal('hide');
			chartData = _chartData;
			getCategories(chartData);
			getSeriesData(chartData);
			chart = eventChart.init(document.getElementById('chart_container'));
			var chartOption = {
				title : { text: chartTitle, subtext: chartSubTitle, x:'center' },
				tooltip : { trigger: 'axis' },
				legend: false,
				toolbox: {
					show : true,
					padding : [5, 32, 5, 5],
					feature : {
						mark : {show: true},
						dataView : {show: false, readOnly: false},
						restore : {show: true},
						saveAsImage : {show: true}
					}
				},
				calculable : true,
				xAxis : [{type : 'category', data : chartCategories }],
				yAxis : [{type : 'value', splitArea : {show : true} }],
				series : chartSeriesDatas
			};
			if(params.chart_type=='bar'){
				var temp = chartOption.xAxis;
				chartOption.xAxis = chartOption.yAxis;
				chartOption.yAxis = temp;
			}
			if(params.chart_type=='pie'){
				chartOption.tooltip = { trigger: 'item', formatter: "{a} <br/>{b} : {c} ({d}%)" };
				chartOption.legend = { orient : 'vertical', x : 'left', data:chartCategories};
				chartOption.xAxis = null;
				chartOption.yAxis = null;
				getPieChartData(chartData);
				chartOption.series = pieChartSeriesDatas;
			}
			chart.setOption(chartOption);
			sw.hideProcessBar();
		});
	}
	//统计设置
	function countConfig(){
		$('#countConfigModal').modal('show');
	}
	//清空日志
	function clearOperationLog(){
		if(!confirm('确认要清空当前查询的所有操作日志吗？')){
			return;
		}
		url = '${pageContext.request.contextPath }/core/log/operation_log/clear';
		var params = new Object();
		params.fastQueryParameters = grid.fastQueryParameters==null?'{}':JSON.stringify(grid.fastQueryParameters);
		params.advanceQueryConditions = grid.advanceQueryParameter.advanceQueryConditions==null?'[]':JSON.stringify(grid.advanceQueryParameter.advanceQueryConditions);
		params.advanceQuerySorts = grid.advanceQueryParameter.advanceQuerySorts==null?'[]':JSON.stringify(grid.advanceQueryParameter.advanceQuerySorts);
		sw.showProcessBar();
		sw.ajaxSubmitRefreshGrid(url, params, grid);
	}
	//初始化绑定方法
	$(function(){
		$('#view_operation_log').bind('click', viewOperationLog);
		$('#clear_operation_log').bind('click', clearOperationLog);
		$('#count_config').bind('click', countConfig);
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
			<li><a href="javascript:void(0);"><i class="fa fa-table"></i>日志查询</a></li>
			<li class="active"><a href="javascript:void(0);"><i class="fa fa-table"></i>操作日志查询</a></li>
		</ul>
		<div class="row">
			<div class="col-md-6">
				<div class="panel panel-default">
					<div class="panel-heading"><i class="fa fa-table"></i>操作日志查询</div>
					<div class="panel-operation">
						<div class="btn-group">
							<sw:button accesskey="V" id="view_operation_log" value="查看 (V)" cssClass="btn btn-primary" icon="table" />
							<sw:button accesskey="C" id="clear_operation_log" value="清空日志 (C)" cssClass="btn btn-danger" limit="core/log/operation_log/clear" icon="trash-o" />
						</div>
					</div>
					<div id="grid_container" class="dlshouwen-grid-container"></div>
					<div id="grid_toolbar_container" class="dlshouwen-grid-toolbar-container"></div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="panel panel-default">
					<div class="panel-heading"><i class="fa fa-bar-chart-o"></i>操作日志统计</div>
					<div class="panel-operation">
						<div class="btn-group">
							<sw:button accesskey="S" id="count_config" value="统计设置 (S)" cssClass="btn btn-default" icon="gear" />
						</div>
					</div>
					<div class="panel-body chart">
						<div id="chart_container" class="chart-container"></div>
					</div>
				</div>
			</div>
		</div>
		<div class="clearfix"></div>
	</div>
	<div class="modal fade" id="countConfigModal" tabindex="-1" role="dialog" aria-labelledby="countConfigModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h5 class="modal-title" id="countConfigLabel"><i class="fa fa-gear"></i>统计设置</h5>
				</div>
				<div class="modal-body form-horizontal">
					<div class="form-group">
						<label class="col-sm-3 control-label text-right">图表类型：</label>
						<div class="col-sm-4">
							<select id="chart_type" name="chart_type" class="form-control">
								<option value="bar">条图</option>
								<option value="column" selected="selected">柱状图</option>
								<option value="line">线图</option>
								<option value="pie">饼状图</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label text-right">统计类型：</label>
						<div class="col-sm-4">
							<select id="data_type" name="data_type" class="form-control">
								<option value="visit_url">按地址</option>
								<option value="status">按访问状态</option>
								<option value="operator" selected="selected">按访问用户</option>
								<option value="cost">按访问耗时</option>
								<option value="ip">按访问IP</option>
								<option value="date">按日</option>
								<option value="month">按月</option>
								<option value="year">按年</option>
							</select>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">
						<i class="fa fa-times"></i>关闭
					</button>
					<button type="button" class="btn btn-primary" onclick="constructChart();">
						<i class="fa fa-search"></i>查询
					</button>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	//动态加载echarts然后在回调函数中开始使用
	require(
		[
			'echarts',
			'echarts/chart/bar',
			'echarts/chart/line',
			'echarts/chart/map'
		],
		function (ec) {
			eventChart = ec;
		}
	);
	</script>
</body>
</html>