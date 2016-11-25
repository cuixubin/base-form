<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 团队管理</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
	<style type="text/css">
	.team-info{margin-bottom:0px;}
	.team-info th{font-weight:normal;text-align:right;width:100px;}
	.table>thead>tr>th, .table>tbody>tr>th, .table>tfoot>tr>th, .table>thead>tr>td, .table>tbody>tr>td, .table>tfoot>tr>td{padding:4px 8px;}
	</style>
	<script type="text/javascript">
	//定义树
	var teamTree;
	var setting = {
		edit: {
			enable: true,
			showRemoveBtn: false,
			showRenameBtn: false
		},
		view: {
			expandSpeed:'',
			showIcon : false,
			showLine : false,
			addDiyDom : function(treeId, treeNode){
				$('#'+treeNode.tId+'_span').prepend('<i class="fa fa-group"></i>&nbsp;&nbsp;');
			}
		},
		data: {
			key : {
				name : 'team_name'
			},
			simpleData: {
				enable: true,
				idKey: 'team_id',
				pIdKey: 'pre_team_id'
			}
		},
		callback: {
			onClick: function(event, treeId, treeNode){
				showTeamInfo(treeNode.team_id);
			}
		}
	};
	var teamTree_treeNodes = ${teamTree };
	teamTree_treeNodes = teamTree_treeNodes==null?[]:teamTree_treeNodes;
	//初始化加载树
	$(function(){
		teamTree = $.fn.zTree.init($("#team_tree_container"), setting, teamTree_treeNodes);
                teamTree.expandAll(true); 
	});
	//新建团队
	function addTeam(){
		var nodes = teamTree.getSelectedNodes();
		if(nodes==null||nodes.length==0){
			addTopTeam();
			return;
		}
		if(nodes.length>1){
			sw.toast('只能选择一个父节点。', 'warning', 3000);
			return;
		}
		var teamId = nodes[0].team_id;
		var url='${pageContext.request.contextPath }/core/team/'+teamId+'/add';
		sw.showProcessBar();
		window.location.href = url;
	}
	//新建顶级团队
	function addTopTeam(){
		var url='${pageContext.request.contextPath }/core/team/top/add';
		sw.showProcessBar();
		window.location.href = url;
	}
	//编辑团队
	function editTeam(){
		var nodes = teamTree.getSelectedNodes();
		if(nodes==null||nodes.length==0){
			sw.toast('请选择要编辑的团队！', 'warning', 3000);
			return;
		}
		if(nodes.length>1){
			sw.toast('编辑只能选择一个团队。', 'warning', 3000);
			return;
		}
		var teamId = nodes[0].team_id;
		var url='${pageContext.request.contextPath }/core/team/'+teamId+'/edit';
		sw.showProcessBar();
		window.location.href = url;
	}
	//删除团队
	function deleteTeam(){
		var nodes = teamTree.getSelectedNodes();
		if(nodes==null||nodes.length==0){
			sw.toast('请选择要删除的团队！', 'warning', 3000);
			return;
		}
		if(!confirm('确定删除选中团队？')){
			return;
		}
		var teamIds = '';
		for(var i=0; i<nodes.length; i++){
			teamIds += nodes[i].team_id + ((i!=nodes.length-1)?',':'');
		}
		var url='${pageContext.request.contextPath }/core/team/delete';
		var params = new Object();
		params.teamIds = teamIds;
		sw.showProcessBar();
		sw.ajaxSubmit(url, params, function(data){
			sw.ajaxSuccessCallback(data, function(){
				//处理动态删除
				var deleteTeamIds = data.data;
				for(var i=0; i<deleteTeamIds.split(',').length; i++){
					var deleteTeamId = deleteTeamIds.split(',')[i];
					var node = teamTree.getNodeByParam('team_id', deleteTeamId);
					teamTree.removeNode(node);
					if(deleteTeamId!=''&&nowShowTeamId==deleteTeamId){
						$('#team_info').html('');
					}
				}
			});
		});
	}
	//绑定方法
	$(function(){
		$('#add_team').bind('click', addTeam);
		$('#add_top_team').bind('click', addTopTeam);
		$('#edit_team').bind('click', editTeam);
		$('#delete_team').bind('click', deleteTeam);
	});
	//显示团队信息
	var nowShowTeamId = '';
	function showTeamInfo(teamId){
		nowShowTeamId = teamId;
		var url='${pageContext.request.contextPath }/core/team/info';
		var params = new Object();
		params.teamId = teamId;
		sw.showProcessBar();
		sw.ajaxSubmit(url, params, function(team){
			var content = '';
			content += '<table class="table table-bordered table-hover table-responsive team-info">';
			content += '	<tr><th>团队名称：</th><td>'+team.team_name+'</td><tr>';
			content += '	<tr><th>负责人：</th><td>'+team.principal+'</td><tr>';
			content += '	<tr><th>负责人电话：</th><td>'+team.principal_phone+'</td><tr>';
			content += '	<tr><th>排序号：</th><td>'+team.sort+'</td><tr>';
			content += '	<tr><th>备注：</th><td>'+team.remark+'</td><tr>';
			content += '	<tr><th>创建人：</th><td>'+team.creator_name+'</td><tr>';
			content += '	<tr><th>创建时间：</th><td>'+new Date(team.create_time).format('yyyy-MM-dd hh:mm:ss')+'</td><tr>';
			content += '	<tr><th>编辑人：</th><td>'+team.editor_name+'</td><tr>';
			content += '	<tr><th>编辑时间：</th><td>'+new Date(team.edit_time).format('yyyy-MM-dd hh:mm:ss')+'</td><tr>';
			content += '</tr>';
			$('#team_info').html(content);
			sw.hideProcessBar();
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
			<li><a href="javascript:void(0);"><i class="fa fa-gears"></i>基础管理</a></li>
			<li class="active"><a href="javascript:void(0);"><i class="fa fa-group"></i>团队管理</a></li>
		</ul>
		<div class="row">
			<div class="col-lg-4 col-md-4 col-sm-4">
				<div class="panel panel-default">
					<div class="panel-heading"><i class="fa fa-cubes"></i>团队树</div>
					<div class="panel-operation text-right">
						<div class="btn-group">
							<sw:button accesskey="A" id="add_team" value="新建 (A)" cssClass="btn btn-xs btn-primary" limit="core/team/add" icon="plus" />
							<sw:button accesskey="T" id="add_top_team" value="新建顶级 (T)" cssClass="btn btn-xs btn-primary" limit="core/team/add" icon="plus" />
							<sw:button accesskey="M" id="edit_team" value="编辑 (M)" cssClass="btn btn-xs btn-primary" limit="core/team/-/edit" icon="edit" />
							<sw:button accesskey="W" id="delete_team" value="删除 (W)" cssClass="btn btn-xs btn-danger" limit="core/team/delete" icon="trash-o" />
						</div>
					</div>
					<div class="panel-body">
						<ul id="team_tree_container" class="ztree"></ul>
					</div>
				</div>
			</div>
			<div class="col-lg-8 col-md-8 col-sm-8">
				<div class="panel panel-default">
					<div class="panel-heading"><i class="fa fa-table"></i>团队详情</div>
					<div class="panel-body" style="border-bottom:1px solid #ddd;">
						<i class="fa fa-exclamation-circle"></i>
						&nbsp;&nbsp;
						当您点击某一个 <code>团队</code> 时这里将显示该团队的详细信息。
					</div>
					<div class="panel-body" id="team_info"></div>
				</div>
			</div>
		</div>
		<div class="clearfix"></div>
	</div>
</body>
</html>