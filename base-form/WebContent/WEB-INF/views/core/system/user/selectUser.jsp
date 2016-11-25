<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 选择用户</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
	<style type="text/css">
	html, body{background:none;}
	.operation-container{text-align:right;border-bottom:1px solid #ddd;padding:8px;}
	</style>
	<script type="text/javascript">
	//定义树
	var userTree;
	var setting = {
		view: {
			expandSpeed:'',
			showLine:false,
			showIcon:false,
			nameIsHTML:true,
			addDiyDom : function(treeId, treeNode){
				if(treeNode.type=='dept'){
					$('#'+treeNode.tId+'_span').prepend('<i class="fa fa-building"></i>&nbsp;&nbsp;');
				}else{
					$('#'+treeNode.tId+'_span').prepend('<i class="fa fa-user"></i>&nbsp;&nbsp;');
				}
			}
		},
		check:{
			enable:true,
			radioType:'all',
			chkStyle:'${type}'
		},
		data: {
			key: {
				name : 'name'
			},
			simpleData: {
				enable: true,
				idKey: 'id',
				pIdKey: 'pid'
			}
		}
	};
	var userTree_treeNodes = ${userTree };
	userTree_treeNodes = userTree_treeNodes==null?[]:userTree_treeNodes;
	//初始化加载树
	$(function(){
		userTree = $.fn.zTree.init($("#userTree"), setting, userTree_treeNodes);
	});
	//回调方法
	var modalCallback;
	//确定
	function sure(){
		var nodes = userTree.getCheckedNodes();
		var returnNodes = [];
		for(var i=0; i<nodes.length; i++){
			var node = nodes[i];
			if(node.type=='user'){
				returnNodes.push(node);
			}
		}
		modalCallback(returnNodes);
	}
	//清空
	function clear(){
		modalCallback([]);
	}
	//绑定方法
	$(function(){
		$('#checkedAll').bind('click', function(){
			userTree.checkAllNodes(true);
		});
		$('#unCheckedAll').bind('click', function(){
			userTree.checkAllNodes(false);
		});
		$('#sure').bind('click', sure);
		$('#clear').bind('click', clear);
	});
	</script>
</head>
<body>
	<div class="operation-container">
		<sw:button accesskey="A" id="checkedAll" value="全选 (A)" cssClass="btn btn-default" icon="check-square-o" />
		<sw:button accesskey="C" id="unCheckedAll" value="反选 (C)" cssClass="btn btn-default" icon="square-o" />
		<sw:button accesskey="S" id="sure" value="确定 (S)" cssClass="btn btn-success" icon="save" />
		<sw:button accesskey="Z" id="clear" value="清空 (Z)" cssClass="btn btn-warning" icon="trash-o" />
	</div>
	<div id="userTreeDiv" style="overflow:auto;">
		<ul id="userTree" class="ztree"></ul>
	</div>
</body>
</html>