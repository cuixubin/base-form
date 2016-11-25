<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 全局监控</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
	<style type="text/css">
	.table>thead>tr>th, .table>tbody>tr>th, .table>tfoot>tr>th, .table>thead>tr>td, .table>tbody>tr>td, .table>tfoot>tr>td{border-top:0;vertical-align:middle;}
	
	.dataContentOutter{border:1px solid #d0d0d0;padding:2px;margin-right:4px;margin-bottom:4px;}
	.dataSearch{margin-bottom:2px;border:1px solid #d0d0d0;}
	.dataContent{background:#111;padding:3px;overflow:auto;}
	
	.monitoringText{font-size:10px;line-height:14px;}
	
	.dataContentText{margin-bottom:3px;overflow:hidden;cursor:pointer;}
	
	.greenText{color:#00ff00;}
	.yellowText{color:#ffff00;}
	.pinkText{color:#ff00ff;}
	.redText{color:#ff0000;}
	.whiteText{color:#ffffff;}
	
	.table{margin-bottom:10px;}
	</style>
	<script type="text/javascript">
	//自适应
	function resizeMain(){
		var w_height = jQuery(window).height();
		$('#operationPanel').height(w_height-365);
		$('#sqlPanel').height(w_height-80);
	};
	//设置默认的秒数和缓冲区大小
	var loginDefaultRefreshTime = 10000;
	var loginDefaultOverflow = 15;
	var operationDefaultRefreshTime = 5000;
	var operationDefaultOverflow = 15;
	var sqlDefaultRefreshTime = 5000;
	var sqlDefaultOverflow = 30;
	var initTime = new Date().format('yyyy-MM-dd hh:mm:ss S');
	$(function(){
		//设置初始化参数
		$('#login_fresh_time').val(loginDefaultRefreshTime);
		$('#operation_fresh_time').val(operationDefaultRefreshTime);
		$('#sql_fresh_time').val(sqlDefaultRefreshTime);
		$('#login_overflow').val(loginDefaultOverflow);
		$('#operation_overflow').val(operationDefaultOverflow);
		$('#sql_overflow').val(sqlDefaultOverflow);
		//触发监控
		getData('login');
		getData('operation');
		getData('sql');
	});
	//获取数据
	function getData(type, lastTime){
		lastTime = (lastTime==null||lastTime=='')?initTime:lastTime;
		var url = '';
		if(type=='login'){
			url = path + '/core/tools/monitoring/login';
		}
		if(type=='operation'){
			url = path + '/core/tools/monitoring/operation';
		}
		if(type=='sql'){
			url = path + '/core/tools/monitoring/sql';
		}
		var params = new Object();
		params.lastTime = lastTime;
		sw.ajaxSubmit(url, params, function(data){
			addData(type, data);
			var refresh_time = $('#'+type+'_fresh_time').val();
			if(isNaN(refresh_time)){
				$('#'+type+'_fresh_time').val(eval(type+'DefaultRefreshTime'));
				refresh_time = eval(type+'DefaultRefreshTime');
			}
			setTimeout(function(){
				getData(type, data.lastTime);
			}, refresh_time);
		});
	}
	//将数据放置在页面中
	function addData(type, data){
		if(type=='login'){
			var login_overflow = $('#login_overflow').val();
			if(isNaN(login_overflow)){
				$('#login_overflow').val(loginDefaultOverflow);
				login_overflow = loginDefaultOverflow;
			}
			var loginLogList = data.loginLogList;
			for(var i=loginLogList.length-1; i>=0; i--){
				var loginLog = loginLogList[i];
				var result = '';
				result += '<div class="dataContentText" onclick="viewDetailLoginLog(\''+loginLog.log_id+'\');">';
				result += '<span class="yellowText">['+new Date(loginLog.donate_time).format('yyyy-MM-dd hh:mm:ss')+']</span> ';
				result += '<span class="whiteText">用户</span> <span class="greenText">'+loginLog.login_user_name+'</span> ';
				result += (loginLog.data_type=='login'?'<span class="greenText">登录系统</span>':'<span class="redText">登出系统</span>');
				result += '<span class="whiteText">，</span>';
				if(loginLog.data_type=='login'){
					result += '<span class="whiteText">登录IP：</span><span class="greenText">'+loginLog.ip+'</span>';
				}else{
					result += '<span class="whiteText">登出方式：</span><span class="greenText">'+loginLog.logout_type_name+'</span>';
				}
				result += '</div>';
				$('#loginPanel').append(result);
			}
			var allLength = $('#loginPanel div').length;
			if(allLength>login_overflow){
				$('#loginPanel div:lt('+(allLength-login_overflow)+')').remove();
			}
		}
		if(type=='operation'){
			var operation_overflow = $('#operation_overflow').val();
			if(isNaN(operation_overflow)){
				$('#operation_overflow').val(operationDefaultOverflow);
				operation_overflow = operationDefaultOverflow;
			}
			var operationLogList = data.operationLogList;
			for(var i=operationLogList.length-1; i>=0; i--){
				var operationLog = operationLogList[i];
				var result = '';
				result += '<div class="dataContentText" onclick="viewDetailOperationLog(\''+operationLog.log_id+'\');">';
				result += '<span class="yellowText">['+new Date(operationLog.response_start).format('yyyy-MM-dd hh:mm:ss')+']</span> ';
				result += '<span class="whiteText">用户</span> <span class="greenText">'+operationLog.operator_name+'</span> ';
				result += '<span class="pinkText">'+operationLog.operation_url+'</span> ';
				result += '<span class="pinkText">'+operationLog.operation_detail+'</span>';
				result += '</div>';
				$('#operationPanel').append(result);
			}
			var allLength = $('#operationPanel div').length;
			if(allLength>operation_overflow){
				$('#operationPanel div:lt('+(allLength-operation_overflow)+')').remove();
			}
		}
		if(type=='sql'){
			var sql_overflow = $('#sql_overflow').val();
			if(isNaN(sql_overflow)){
				$('#sql_overflow').val(dataDefaultOverflow);
				sql_overflow = dataDefaultOverflow;
			}
			var sqlLogList = data.sqlLogList;
			for(var i=sqlLogList.length-1; i>=0; i--){
				var sqlLog = sqlLogList[i];
				var result = '';
				result += '<div class="dataContentText" onclick="viewDetailSqlLog(\''+sqlLog.log_id+'\');">';
				result += '<span class="yellowText">['+new Date(sqlLog.start_time).format('yyyy-MM-dd hh:mm:ss')+']</span> ';
				result += '<span class="whiteText">'+sqlLog.operation_sql+'</span>';
				result += '</div>';
				$('#sqlPanel').append(result);
			}
			var allLength = $('#sqlPanel div').length;
			if(allLength>sql_overflow){
				$('#sqlPanel div:lt('+(allLength-sql_overflow)+')').remove();
			}
		}
	}
	//查看Login日志
	function viewDetailLoginLog(logId){
		var url = '${pageContext.request.contextPath }/core/log/login_log/'+logId+'/view';
		sw.openModal({
			id : 'viewDetailLoginLog',
			icon : 'table',
			title : '查看登录日志',
			url : url,
			height : 400
		});
	}
	//查看Operation日志
	function viewDetailOperationLog(logId){
		var url = '${pageContext.request.contextPath }/core/log/operation_log/'+logId+'/view';
		sw.openModal({
			id : 'viewDetailOperationLog',
			icon : 'table',
			title : '查看操作日志',
			url : url,
			height : 400
		});
	}
	//查看Sql日志
	function viewDetailSqlLog(logId){
		var url = '${pageContext.request.contextPath }/core/log/sql_log/'+logId+'/view';
		sw.openModal({
			id : 'viewDetailSqlLog',
			icon : 'table',
			title : '查看SQL日志',
			url : url,
			height : 400
		});
	}
	</script>
</head>
<body>
	<jsp:include page="/core/base/layout/header" />
	<jsp:include page="/core/base/layout/left" />
	<jsp:include page="/core/base/layout/shortcut" />
	<div class="main-container">
		<ul class="breadcrumb">
			<li><a href="${pageContext.request.contextPath }/core/base/home"><i class="fa fa-home"></i>首页</a></li>
			<li><a href="javascript:void(0);"><i class="fa fa-puzzle-piece"></i>系统工具</a></li>
			<li class="active"><a href="javascript:void(0);"><i class="fa fa-terminal"></i>全局监控</a></li>
		</ul>
		<div class="panel panel-default">
			<div class="panel-heading"><i class="fa fa-terminal"></i>全局监控</div>
			<div class="panel-body">
				<i class="fa fa-warning"></i>
				&nbsp;&nbsp;
				此系统监控的 <code>时效性</code> 同系统设置中各日志的闸口时间有关，闸口时间越短时效性越高，但服务器压力会随之增加。
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading"><i class="fa fa-user"></i>登录监控</div>
			<div class="panel-body">
				<table class="table">
					<tr>
						<th>Fresh Time(ms):</th>
						<td>
							<input id="login_fresh_time" name="login_fresh_time" class="monitoringText" />
						</td>
						<th>Overflow(line):</th>
						<td>
							<input id="login_overflow" name="login_overflow" class="monitoringText" />
						</td>
					</tr>
				</table>
				<div id="loginPanel" class="dataContent" style="height:240px;"></div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading"><i class="fa fa-edit"></i>操作监控</div>
			<div class="panel-body">
				<table class="table">
					<tr>
						<th>Fresh Time(ms):</th>
						<td>
							<input id="operation_fresh_time" name="operation_fresh_time" class="monitoringText" />
						</td>
						<th>Overflow(line):</th>
						<td>
							<input id="operation_overflow" name="operation_overflow" class="monitoringText" />
						</td>
					</tr>
				</table>
				<div id="operationPanel" class="dataContent" style="height:240px;"></div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading"><i class="fa fa-code"></i>SQL监控</div>
			<div class="panel-body">
				<table class="table">
					<tr>
						<th>Fresh Time(ms):</th>
						<td>
							<input id="sql_fresh_time" name="sql_fresh_time" class="monitoringText" />
						</td>
						<th>Overflow(line):</th>
						<td>
							<input id="sql_overflow" name="sql_overflow" class="monitoringText" />
						</td>
					</tr>
				</table>
				<div id="sqlPanel" class="dataContent" style="height:240px;"></div>
			</div>
		</div>
		<div class="clearfix"></div>
	</div>
</body>
</html>