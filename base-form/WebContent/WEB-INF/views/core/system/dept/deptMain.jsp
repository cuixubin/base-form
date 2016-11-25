<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 部门管理</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
	<style type="text/css">
	.dept-info{margin-bottom:0px;}
	.dept-info th{font-weight:normal;text-align:right;width:100px;}
	.table>thead>tr>th, .table>tbody>tr>th, .table>tfoot>tr>th, .table>thead>tr>td, .table>tbody>tr>td, .table>tfoot>tr>td{padding:4px 8px;}
	</style>
	<script type="text/javascript">
	//定义树
	var deptTree;
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
				$('#'+treeNode.tId+'_span').prepend('<i class="fa fa-building"></i>&nbsp;&nbsp;');
			}
		},
		data: {
			key : {
				name : 'dept_name'
			},
			simpleData: {
				enable: true,
				idKey: 'dept_id',
				pIdKey: 'pre_dept_id'
			}
		},
		callback: {
			onClick: function(event, treeId, treeNode){
				showDeptInfo(treeNode.dept_id);
			}
		}
	};
	var deptTree_treeNodes = ${deptTree };
	deptTree_treeNodes = deptTree_treeNodes==null?[]:deptTree_treeNodes;
	//初始化加载树
	$(function(){
		deptTree = $.fn.zTree.init($("#dept_tree_container"), setting, deptTree_treeNodes);
	});
	//新建部门
	function addDept(){
		var nodes = deptTree.getSelectedNodes();
		if(nodes==null||nodes.length==0){
			addTopDept();
			return;
		}
		if(nodes.length>1){
			sw.toast('只能选择一个父节点。', 'warning', 3000);
			return;
		}
		var deptId = nodes[0].dept_id;
		var url='${pageContext.request.contextPath }/core/system/dept/'+deptId+'/add';
		sw.showProcessBar();
		window.location.href = url;
	}
	//新建顶级部门
	function addTopDept(){
		var url='${pageContext.request.contextPath }/core/system/dept/top/add';
		sw.showProcessBar();
		window.location.href = url;
	}
	//编辑部门
	function editDept(){
		var nodes = deptTree.getSelectedNodes();
		if(nodes==null||nodes.length==0){
			sw.toast('请选择要编辑的部门！', 'warning', 3000);
			return;
		}
		if(nodes.length>1){
			sw.toast('编辑只能选择一个部门。', 'warning', 3000);
			return;
		}
		var deptId = nodes[0].dept_id;
		var url='${pageContext.request.contextPath }/core/system/dept/'+deptId+'/edit';
		sw.showProcessBar();
		window.location.href = url;
	}
	//删除部门
	function deleteDept(){
		var nodes = deptTree.getSelectedNodes();
		if(nodes==null||nodes.length==0){
			sw.toast('请选择要删除的部门！', 'warning', 3000);
			return;
		}
		if(!confirm('确定删除选中部门？')){
			return;
		}
		var deptIds = '';
		for(var i=0; i<nodes.length; i++){
			deptIds += nodes[i].dept_id + ((i!=nodes.length-1)?',':'');
		}
		var url='${pageContext.request.contextPath }/core/system/dept/delete';
		var params = new Object();
		params.deptIds = deptIds;
		sw.showProcessBar();
		sw.ajaxSubmit(url, params, function(data){
			sw.ajaxSuccessCallback(data, function(){
				//处理动态删除
				var deleteDeptIds = data.data;
				for(var i=0; i<deleteDeptIds.split(',').length; i++){
					var deleteDeptId = deleteDeptIds.split(',')[i];
					var node = deptTree.getNodeByParam('dept_id', deleteDeptId);
					deptTree.removeNode(node);
					if(deleteDeptId!=''&&nowShowDeptId==deleteDeptId){
						$('#dept_info').html('');
					}
				}
			});
		});
	}
	//绑定方法
	$(function(){
		$('#add_dept').bind('click', addDept);
		$('#add_top_dept').bind('click', addTopDept);
		$('#edit_dept').bind('click', editDept);
		$('#delete_dept').bind('click', deleteDept);
	});
	//显示部门信息
	var nowShowDeptId = '';
	function showDeptInfo(deptId){
		nowShowDeptId = deptId;
		var url='${pageContext.request.contextPath }/core/system/dept/info';
		var params = new Object();
		params.deptId = deptId;
		sw.showProcessBar();
		sw.ajaxSubmit(url, params, function(dept){
			var content = '';
			content += '<table class="table table-bordered table-hover table-responsive dept-info">';
			content += '	<tr><th>部门名称：</th><td>'+dept.dept_name+'</td><tr>';
			content += '	<tr><th>联系电话：</th><td>'+dept.phone+'</td><tr>';
			content += '	<tr><th>负责人：</th><td>'+dept.principal+'</td><tr>';
			content += '	<tr><th>负责人电话：</th><td>'+dept.principal_phone+'</td><tr>';
			content += '	<tr><th>排序号：</th><td>'+dept.sort+'</td><tr>';
			content += '	<tr><th>备注：</th><td>'+dept.remark+'</td><tr>';
			content += '	<tr><th>创建人：</th><td>'+dept.creator_name+'</td><tr>';
			content += '	<tr><th>创建时间：</th><td>'+new Date(dept.create_time).format('yyyy-MM-dd hh:mm:ss')+'</td><tr>';
			content += '	<tr><th>编辑人：</th><td>'+dept.editor_name+'</td><tr>';
			content += '	<tr><th>编辑时间：</th><td>'+new Date(dept.edit_time).format('yyyy-MM-dd hh:mm:ss')+'</td><tr>';
			content += '</tr>';
			$('#dept_info').html(content);
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
			<li class="active"><a href="javascript:void(0);"><i class="fa fa-building"></i>部门管理</a></li>
		</ul>
		<div class="row">
			<div class="col-lg-4 col-md-4 col-sm-4">
				<div class="panel panel-default">
					<div class="panel-heading"><i class="fa fa-cubes"></i>部门树</div>
					<div class="panel-operation text-right">
						<div class="btn-group">
							<sw:button accesskey="A" id="add_dept" value="新建 (A)" cssClass="btn btn-xs btn-primary" limit="core/system/dept/add" icon="plus" />
							<sw:button accesskey="T" id="add_top_dept" value="新建顶级 (T)" cssClass="btn btn-xs btn-primary" limit="core/system/dept/add" icon="plus" />
							<sw:button accesskey="M" id="edit_dept" value="编辑 (M)" cssClass="btn btn-xs btn-primary" limit="core/system/dept/-/edit" icon="edit" />
							<sw:button accesskey="W" id="delete_dept" value="删除 (W)" cssClass="btn btn-xs btn-danger" limit="core/system/dept/delete" icon="trash-o" />
						</div>
					</div>
					<div class="panel-body">
						<ul id="dept_tree_container" class="ztree"></ul>
					</div>
				</div>
			</div>
			<div class="col-lg-8 col-md-8 col-sm-8">
				<div class="panel panel-default">
					<div class="panel-heading"><i class="fa fa-table"></i>部门详情</div>
					<div class="panel-body" style="border-bottom:1px solid #ddd;">
						<i class="fa fa-exclamation-circle"></i>
						&nbsp;&nbsp;
						当您点击某一个 <code>部门</code> 时这里将显示该部门的详细信息。
					</div>
					<div class="panel-body" id="dept_info"></div>
				</div>
			</div>
		</div>
		<div class="clearfix"></div>
	</div>
</body>
</html>