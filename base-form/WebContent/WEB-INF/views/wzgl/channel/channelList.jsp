<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 栏目管理</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
        <%
	//通过静态域设置码制映射对象
            CodeTableUtils.createCodeTableJS(application, out, "channel_type");
            CodeTableUtils.createCodeTableJS(application, out, "open_close");
	%>
	<style type="text/css">
	.dept-info{margin-bottom:0px;}
	.dept-info th{font-weight:normal;text-align:right;width:100px;}
	.table>thead>tr>th, .table>tbody>tr>th, .table>tfoot>tr>th, .table>thead>tr>td, .table>tbody>tr>td, .table>tfoot>tr>td{padding:4px 8px;}
	</style>
	<script type="text/javascript">
	//定义树
	var channelTree;
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
				$('#'+treeNode.tId+'_span').prepend('<i class="fa fa-navicon"></i>&nbsp;&nbsp;');
			}
		},
		data: {
			key : {
				name : 'channel_name'
			},
			simpleData: {
				enable: true,
				idKey: 'channel_id',
				pIdKey: 'channel_parent'
			}
		},
		callback: {
			onClick: function(event, treeId, treeNode){
				showDeptInfo(treeNode.channel_id);
			}
		}
	};
	var channelTree_treeNodes = ${channelTree };
	channelTree_treeNodes = channelTree_treeNodes==null?[]:channelTree_treeNodes;
	//初始化加载树
	$(function(){
		channelTree = $.fn.zTree.init($("#channel_tree_container"), setting, channelTree_treeNodes);
	});
	//新建栏目
	function addChannel(){
		var nodes = channelTree.getSelectedNodes();
		if(nodes==null||nodes.length==0){
			addTopChannel();
			return;
		}
		if(nodes.length>1){
			sw.toast('只能选择一个父节点。', 'warning', 3000);
			return;
		}
		var channelId = nodes[0].channel_id;
		var url='${pageContext.request.contextPath }/wzgl/channel/channel/'+channelId+'/add';
		sw.showProcessBar();
		window.location.href = url;
	}
	//新建顶级栏目
	function addTopChannel(){
		var url='${pageContext.request.contextPath }/wzgl/channel/channel/top/add';
		sw.showProcessBar();
		window.location.href = url;
	}
	//编辑栏目
	function editChannel(){
		var nodes = channelTree.getSelectedNodes();
		if(nodes==null||nodes.length==0){
			sw.toast('请选择要编辑的栏目！', 'warning', 3000);
			return;
		}
		if(nodes.length>1){
			sw.toast('编辑只能选择一个栏目。', 'warning', 3000);
			return;
		}
		var channelId = nodes[0].channel_id;
		var url='${pageContext.request.contextPath }/wzgl/channel/channel/'+channelId+'/edit';
		sw.showProcessBar();
		window.location.href = url;
	}
	//删除栏目
	function deleteChannel(){
		var nodes = channelTree.getSelectedNodes();
		if(nodes==null||nodes.length==0){
			sw.toast('请选择要删除的栏目！', 'warning', 3000);
			return;
		}
		if(!confirm('确定删除选中栏目？')){
			return;
		}
		var channelIds = '';
		for(var i=0; i<nodes.length; i++){
			channelIds += nodes[i].channel_id + ((i!=nodes.length-1)?',':'');
		}
		var url='${pageContext.request.contextPath }/wzgl/channel/channel/delete';
		var params = new Object();
		params.channelIds = channelIds;
		sw.showProcessBar();
		sw.ajaxSubmit(url, params, function(data){
			sw.ajaxSuccessCallback(data, function(){
				//处理动态删除
				var deleteChannelIds = data.data;
				for(var i=0; i<deleteChannelIds.split(',').length; i++){
					var deleteChannelId = deleteChannelIds.split(',')[i];
					var node = channelTree.getNodeByParam('channel_id', deleteChannelId);
					channelTree.removeNode(node);
					if(deleteChannelId!=''&&nowShowChannelId==deleteChannelId){
						$('#channel_info').html('');
					}
				}
			});
		});
	}
	//绑定方法
	$(function(){
		$('#add_channel').bind('click', addChannel);
		$('#add_top_channel').bind('click', addTopChannel);
		$('#edit_channel').bind('click', editChannel);
		$('#delete_channel').bind('click', deleteChannel);
	});
	//显示栏目信息
	var nowShowChannelId = '';
	function showDeptInfo(channelId){
		nowShowChannelId = channelId;
		var url='${pageContext.request.contextPath }/wzgl/channel/channel/info';
		var params = new Object();
		params.channelId = channelId;
		sw.showProcessBar();
		sw.ajaxSubmit(url, params, function(channel){
			var content = '';
			content += '<table class="table table-bordered table-hover table-responsive dept-info">';
                        content += '	<tr><th width: 120px;>栏目标识：</th><td>'+channel.channel_id+'</td><tr>';
			content += '	<tr><th width: 120px;>栏目名称：</th><td>'+channel.channel_name+'</td><tr>';
			content += '	<tr><th>栏目排序：</th><td>'+channel.channel_seq+'</td><tr>';
			content += '	<tr><th>栏目创建人：</th><td>'+channel.creator_name+'</td><tr>';
			content += '	<tr><th>栏目状态：</th><td>'+code_table_info['open_close'][channel.channel_state]+'</td><tr>';
			content += '	<tr><th>创建时间：</th><td>'+new Date(channel.channel_createDate).format('yyyy-MM-dd hh:mm:ss')+'</td><tr>';
			content += '	<tr><th>栏目类型：</th><td>'+code_table_info['channel_type'][channel.channel_type]+'</td><tr>';
                        content += '	<tr><th>栏目修改人：</th><td>'+channel.editor_name+'</td><tr>';
                         content += '	<tr><th>修改时间：</th><td>'+new Date(channel.channel_updateDate).format('yyyy-MM-dd hh:mm:ss')+'</td><tr>';
			content += '	<tr><th>栏目路径：</th><td>'+channel.channel_url+'</td><tr>';
			content += '	<tr><th>栏目描述：</th><td>'+channel.channel_description+'</td><tr>';
			content += '</tr>';
			$('#channel_info').html(content);
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
			<li><a href="${pageContext.request.contextPath }/core/base/home"><i class="fa fa-home"></i>网站管理</a></li>
			<li><a href="javascript:void(0);"><i class="fa fa-book"></i>基础管理</a></li>
			<li class="active"><a href="javascript:void(0);"><i class="fa fa-navicon"></i>栏目管理</a></li>
		</ul>
		<div class="row">
			<div class="col-lg-4 col-md-4 col-sm-4">
				<div class="panel panel-default">
					<div class="panel-heading"><i class="fa fa-cubes"></i>栏目树</div>
					<div class="panel-operation text-right">
						<div class="btn-group">
							<sw:button accesskey="A" id="add_channel" value="新建 (A)" cssClass="btn btn-xs btn-primary" limit="wzgl/channel/channel/add" icon="plus" />
							<sw:button accesskey="T" id="add_top_channel" value="新建顶级 (T)" cssClass="btn btn-xs btn-primary" limit="wzgl/channel/channel/add" icon="plus" />
							<sw:button accesskey="M" id="edit_channel" value="编辑 (M)" cssClass="btn btn-xs btn-primary" limit="wzgl/channel/channel/-/edit" icon="edit" />
							<sw:button accesskey="W" id="delete_channel" value="删除 (W)" cssClass="btn btn-xs btn-danger" limit="wzgl/channel/channel/delete" icon="trash-o" />
						</div>
					</div>
					<div class="panel-body">
						<ul id="channel_tree_container" class="ztree"></ul>
					</div>
				</div>
			</div>
			<div class="col-lg-8 col-md-8 col-sm-8">
				<div class="panel panel-default">
					<div class="panel-heading"><i class="fa fa-table"></i>栏目详情</div>
					<div class="panel-body" style="border-bottom:1px solid #ddd;">
						<i class="fa fa-exclamation-circle"></i>
						&nbsp;&nbsp;
						当您点击某一个 <code>栏目</code> 时这里将显示该栏目的详细信息。
					</div>
					<div class="panel-body" id="channel_info"></div>
				</div>
			</div>
		</div>
		<div class="clearfix"></div>
	</div>
</body>
</html>