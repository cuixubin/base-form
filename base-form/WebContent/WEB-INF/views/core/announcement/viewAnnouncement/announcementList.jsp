<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 查看公告</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
	<script type="text/javascript">
	var gridColumns = [
		{id:'operation', title:'操作', columnClass:'text-center', columnStyle:'width:50px;', hideType:'xs', extra:false, 'export':false, print:false, advanceQuery:false, resolution:function(value, record, column, grid, dataNo, columnNo){
			var content = '';
			content += '<button type="button" class="btn btn-xs btn-icon btn-default" title="查看" onclick="viewAnnouncementSingle(\''+record.announcement_id+'\')"><i class="fa fa-eye"></i></button>';
			return content;
		}},
		{id:'title', title:'公告主题', type:'string', fastQuery:true, fastQueryType:'lk' },
		{id:'create_time', title:'发布时间', type:'date', format:'yyyy-MM-dd hh:mm:ss', columnClass:'text-center', hideType:'xs', fastQuery:true, fastQueryType:'range' }
	];
	var gridOption = {
		loadURL : '${pageContext.request.contextPath }/core/announcement/view_announcement/list',
		functionCode : 'CORE_VIEW_ANNOUNCEMENT',
		check:true,
		exportFileName : '公告列表',
		columns : gridColumns,
		onRowDblClick : function(value, record, column, grid, dataNo, columnNo, cell, row, extraCell, e){
			viewAnnouncementSingle(record.announcement_id);
		}
	};
	var grid = $.fn.dlshouwen.grid.init(gridOption);;
	//数据加载
	$(function(){
		grid.load();
	});
	//查看跳转
	function viewAnnouncement(){
		var recordObj = grid.getCheckedRecords();
		if(recordObj.length==0){
			sw.toast('请选择要查看的公告！', 'warning', 3000);
			return false;
		}
		var announcementId = recordObj[0].announcement_id;
		var url = '${pageContext.request.contextPath }/core/announcement/view_announcement/'+announcementId+'/view';
		sw.openModal({
			id : 'viewAnnouncement',
			icon : 'bell', 
			title : '查看公告',
			url : url,
			modalType : 'lg'
		});
	}
	//查看跳转
	function viewAnnouncementSingle(announcementId){
		var url = '${pageContext.request.contextPath }/core/announcement/view_announcement/'+announcementId+'/view';
		sw.openModal({
			id : 'viewAnnouncement',
			icon : 'bell', 
			title : '查看公告',
			url : url,
			modalType : 'lg'
		});
	}
	//初始化绑定方法
	$(function(){
		$('#view_announcement').bind('click', viewAnnouncement);
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
			<li><a href="javascript:void(0);"><i class="fa fa-bell"></i>公告平台</a></li>
			<li class="active"><a href="javascript:void(0);"><i class="fa fa-eye"></i>公告查看</a></li>
		</ul>
		<div class="panel panel-default">
			<div class="panel-heading"><i class="fa fa-eye"></i>公告查看</div>
			<div class="panel-operation">
				<div class="btn-group">
					<sw:button accesskey="V" id="view_announcement" value="查看 (V)" cssClass="btn btn-default" icon="eye" />
				</div>
			</div>
			<div id="grid_container" class="dlshouwen-grid-container"></div>
			<div id="grid_toolbar_container" class="dlshouwen-grid-toolbar-container"></div>
		</div>
		<div class="clearfix"></div>
	</div>
</body>
</html>