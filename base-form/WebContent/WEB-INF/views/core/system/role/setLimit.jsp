<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 配置功能</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
	<script type="text/javascript">
	//定义返回URL
	var goBackUrl = '${pageContext.request.contextPath }/core/system/role';
	//返回
	function goBack(){
		window.location.href = goBackUrl;
	}
	//定义树
	var limitTree;
	var setting = {
		view: {
			expandSpeed:'',
			showIcon : false,
			showLine : false,
			addDiyDom : function(treeId, treeNode){
				$('#'+treeNode.tId+'_span').prepend('<i class="fa fa-'+treeNode.icon_fa+'"></i>&nbsp;&nbsp;');
			}
		},
		check: {
			enable: true,
			chkStyle: 'checkbox',
			chkboxType: { "Y": "p", "N": "s" }
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
		}
	};
	var limitTree_treeNodes = ${limitTree };
	limitTree_treeNodes = limitTree_treeNodes==null?[]:limitTree_treeNodes;
	//初始化加载树
	$(function(){
		limitTree = $.fn.zTree.init($("#limitTree"), setting, limitTree_treeNodes);
		limitTree.expandAll(true);
	});
	//配置功能
	function setLimit(){
		var nodes = limitTree.getCheckedNodes(true);
		if(!confirm('确定配置功能？')){
			return;
		}
		var limitIds = '';
		for(var i=0; i<nodes.length; i++){
			limitIds += nodes[i].limit_id + ((i!=nodes.length-1)?',':'');
		}
		var url='${pageContext.request.contextPath }/core/system/role/set_limit';
		var params = new Object();
		params.roleId = '${roleId }';
		params.limitIds = limitIds;
		sw.showProcessBar();
		sw.ajaxSubmit(url, params, function(data){
			sw.ajaxSuccessCallback(data);
		});
	}
	//绑定方法
	$(function(){
		$('#checkedAll').bind('click', function(){
			limitTree.checkAllNodes(true);
		});
		$('#unCheckedAll').bind('click', function(){
			limitTree.checkAllNodes(false);
		});
		$('#set_limit').bind('click', setLimit);
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
			<li class="active"><a href="javascript:void(0);"><i class="fa fa-cubes"></i>为 <span class="text-primary">[${role.role_name }]</span> 配置功能</a></li>
		</ul>
		<div class="panel panel-default">
			<div class="panel-heading"><i class="fa fa-cubes"></i>为 <span class="text-primary">[${role.role_name }]</span> 配置功能</div>
			<div class="panel-operation text-right">
				<div class="btn-group">
					<sw:button accesskey="A" id="checkedAll" value="全选 (A)" cssClass="btn btn-default" icon="check-square-o" />
					<sw:button accesskey="C" id="unCheckedAll" value="反选 (C)" cssClass="btn btn-default" icon="square-o" />
				</div>
				&nbsp;&nbsp;|&nbsp;&nbsp;
				<div class="btn-group">
					<sw:button accesskey="S" id="set_limit" value="保存 (S)" cssClass="btn btn-success" limit="core/system/role/-/set_limit" icon="save" />
					<button accesskey="B" type="button" class="btn btn-default" onclick="goBack();">
						<i class="fa fa-arrow-circle-left"></i>返回 (B)
					</button>
				</div>
			</div>
			<div class="panel-body">
				<ul id="limitTree" class="ztree"></ul>
			</div>
		</div>
		<div class="clearfix"></div>
	</div>
</body>
</html>
