<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 功能管理</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
	<%
	//通过静态域设置码制映射对象
	CodeTableUtils.createCodeTableJS(application, out, "zero_one");
	CodeTableUtils.createCodeTableJS(application, out, "limit_type");
	%>
	<style type="text/css">
	.limit-info{margin-bottom:0px;}
	.limit-info th{font-weight:normal;text-align:right;width:100px;}
	.table>thead>tr>th, .table>tbody>tr>th, .table>tfoot>tr>th, .table>thead>tr>td, .table>tbody>tr>td, .table>tfoot>tr>td{padding:4px 8px;}
	</style>
	<script type="text/javascript">
	//定义树
	var limitTree;
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
				$('#'+treeNode.tId+'_span').prepend('<i class="fa fa-'+treeNode.icon_fa+'"></i>&nbsp;&nbsp;');
			}
		},
		data: {
			key : {
				name : 'limit_name'
			},
			simpleData: {
				enable: true,
				idKey: 'limit_id',
				pIdKey: 'pre_limit_id'
			}
		},
		callback: {
			onClick: function(event, treeId, treeNode){
				showLimitInfo(treeNode.limit_id);
			},
			beforeDrop : function(treeId, treeNodes, targetNode, moveType) {
				if(!confirm('您确定执行此拖拽功能吗？\n\n注意：如果所属系统不同，将会自动更新本功能为其上级功能的所属系统。')){
					return false;
				}
				var limitIds = '';
				for(var i=0; i<treeNodes.length; i++){
					limitIds += treeNodes[i].limit_id + ((i!=treeNodes.length-1)?',':'');
				}
				var targetLimitId = targetNode.limit_id;
				var result = false;
				var url = '${pageContext.request.contextPath }/core/system/limit/drag';
				var params = new Object();
				params.limitIds = limitIds;
				params.targetLimitId = targetLimitId;
				params.moveType = moveType;
				sw.showProcessBar();
				sw.ajaxSubmit(url, params, function(data){
					sw.ajaxSuccessCallback(data, function(){
						result = true;
						//处理动态更新
						for(var i=0; i<limitIds.split(',').length; i++){
							var limitId = limitIds.split(',')[i];
							if(nowShowLimitId==limitId){
								showLimitInfo(limitId);
								break;
							}
						}
					});
				}, null, false);
				return result;
			}
		}
	};
	var limitTree_treeNodes = ${limitTree };
	limitTree_treeNodes = limitTree_treeNodes==null?[]:limitTree_treeNodes;
	//初始化加载树
	$(function(){
		limitTree = $.fn.zTree.init($("#limit_tree_container"), setting, limitTree_treeNodes);
	});
	//新建功能
	function addLimit(){
		var nodes = limitTree.getSelectedNodes();
		if(nodes==null||nodes.length==0){
			addTopLimit();
			return;
		}
		if(nodes.length>1){
			sw.toast('只能选择一个父节点。', 'warning', 3000);
			return;
		}
		var limitId = nodes[0].limit_id;
		var url='${pageContext.request.contextPath }/core/system/limit/'+limitId+'/add';
		sw.showProcessBar();
		window.location.href = url;
	}
	//新建顶级功能
	function addTopLimit(){
		var url='${pageContext.request.contextPath }/core/system/limit/top/add';
		sw.showProcessBar();
		window.location.href = url;
	}
	//编辑功能
	function editLimit(){
		var nodes = limitTree.getSelectedNodes();
		if(nodes==null||nodes.length==0){
			sw.toast('请选择要编辑的功能！', 'warning', 3000);
			return;
		}
		if(nodes.length>1){
			sw.toast('编辑只能选择一个功能。', 'warning', 3000);
			return;
		}
		var limitId = nodes[0].limit_id;
		var url='${pageContext.request.contextPath }/core/system/limit/'+limitId+'/edit';
		sw.showProcessBar();
		window.location.href = url;
	}
	//删除功能
	function deleteLimit(){
		var nodes = limitTree.getSelectedNodes();
		if(nodes==null||nodes.length==0){
			sw.toast('请选择要删除的功能！', 'warning', 3000);
			return;
		}
		if(!confirm('确定删除选中功能？')){
			return;
		}
		var limitIds = '';
		for(var i=0; i<nodes.length; i++){
			limitIds += nodes[i].limit_id + ((i!=nodes.length-1)?',':'');
		}
		var url='${pageContext.request.contextPath }/core/system/limit/delete';
		var params = new Object();
		params.limitIds = limitIds;
		sw.showProcessBar();
		sw.ajaxSubmit(url, params, function(data){
			sw.ajaxSuccessCallback(data, function(){
				//处理动态删除
				var deleteLimitIds = data.data;
				for(var i=0; i<deleteLimitIds.split(',').length; i++){
					var deleteLimitId = deleteLimitIds.split(',')[i];
					var node = limitTree.getNodeByParam('limit_id', deleteLimitId);
					limitTree.removeNode(node);
					if(deleteLimitId!=''&&nowShowLimitId==deleteLimitId){
						$('#limit_info').html('');
					}
				}
			});
		});
	}
	//绑定方法
	$(function(){
		$('#add_limit').bind('click', addLimit);
		$('#add_top_limit').bind('click', addTopLimit);
		$('#edit_limit').bind('click', editLimit);
		$('#delete_limit').bind('click', deleteLimit);
	});
	//显示功能信息
	var nowShowLimitId = '';
	function showLimitInfo(limitId){
		nowShowLimitId = limitId;
		var url='${pageContext.request.contextPath }/core/system/limit/info';
		var params = new Object();
		params.limitId = limitId;
		sw.showProcessBar();
		sw.ajaxSubmit(url, params, function(limit){
			var content = '';
			content += '<table class="table table-bordered table-hover table-responsive limit-info">';
			content += '	<tr><th>所属系统：</th><td>'+limit.system_name+'</td><tr>';
			content += '	<tr><th>功能名称：</th><td>'+limit.limit_name+'</td><tr>';
			content += '	<tr><th>功能类型：</th><td>'+code_table_info['limit_type'][limit.limit_type]+'</td><tr>';
			content += '	<tr><th>是否异步：</th><td>'+code_table_info['zero_one'][limit.ifAsynch]+'</td><tr>';
			content += '	<tr><th>功能图标：</th><td><i class="fa fa-'+limit.icon_fa+'"></i>&nbsp;&nbsp;[ '+limit.icon_fa+' ]</td><tr>';
			content += '	<tr><th>功能链接：</th><td>'+limit.url+'</td><tr>';
			content += '	<tr><th>排序号：</th><td>'+limit.sort+'</td><tr>';
			content += '	<tr><th>备注：</th><td>'+limit.remark+'</td><tr>';
			content += '	<tr><th>创建人：</th><td>'+limit.creator_name+'</td><tr>';
			content += '	<tr><th>创建时间：</th><td>'+new Date(limit.create_time).format('yyyy-MM-dd hh:mm:ss')+'</td><tr>';
			content += '	<tr><th>编辑人：</th><td>'+limit.editor_name+'</td><tr>';
			content += '	<tr><th>编辑时间：</th><td>'+new Date(limit.edit_time).format('yyyy-MM-dd hh:mm:ss')+'</td><tr>';
			content += '</tr>';
			$('#limit_info').html(content);
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
			<li class="active"><a href="javascript:void(0);"><i class="fa fa-cubes"></i>功能管理</a></li>
		</ul>
		<div class="row">
			<div class="col-lg-4 col-md-4 col-sm-4">
				<div class="panel panel-default">
					<div class="panel-heading"><i class="fa fa-cubes"></i>功能树</div>
					<div class="panel-operation text-right">
						<div class="btn-group">
							<sw:button accesskey="A" id="add_limit" value="新建 (A)" cssClass="btn btn-xs btn-primary" limit="core/system/limit/add" icon="plus" />
							<sw:button accesskey="T" id="add_top_limit" value="新建顶级 (T)" cssClass="btn btn-xs btn-primary" limit="core/system/limit/add" icon="plus" />
							<sw:button accesskey="M" id="edit_limit" value="编辑 (M)" cssClass="btn btn-xs btn-primary" limit="core/system/limit/-/edit" icon="edit" />
							<sw:button accesskey="W" id="delete_limit" value="删除 (W)" cssClass="btn btn-xs btn-danger" limit="core/system/limit/delete" icon="trash-o" />
						</div>
					</div>
					<div class="panel-body">
						<ul id="limit_tree_container" class="ztree"></ul>
					</div>
				</div>
			</div>
			<div class="col-lg-8 col-md-8 col-sm-8">
				<div class="panel panel-default">
					<div class="panel-heading"><i class="fa fa-table"></i>功能详情</div>
					<div class="panel-body" style="border-bottom:1px solid #ddd;">
						<i class="fa fa-exclamation-circle"></i>
						&nbsp;&nbsp;
						当您点击某一个 <code>功能</code> 时这里将显示该功能的详细信息。
					</div>
					<div class="panel-body" id="limit_info"></div>
				</div>
			</div>
		</div>
		<div class="clearfix"></div>
	</div>
</body>
</html>