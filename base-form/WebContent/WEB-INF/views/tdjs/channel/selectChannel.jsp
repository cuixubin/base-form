<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 选择功能</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
	<style type="text/css">
	html, body{background:none;}
	.operation-container{text-align:right;border-bottom:1px solid #ddd;padding:8px;}
	</style>
	<script type="text/javascript">
	//定义树
	var channelTree;
	var setting = {
		view: {
			expandSpeed:'',
			showIcon : false,
			showLine : false,
			addDiyDom : function(treeId, treeNode){
				$('#'+treeNode.tId+'_span').prepend('<i class="fa fa-navicon"></i>&nbsp;&nbsp;');
			}
		},
		check:{
			enable:true,
			radioType:'all',
			chkStyle:'${type}'
		},
		data: {
			key: {
				name : 'channel_name'
			},
			simpleData: {
				enable: true,
				idKey: 'channel_id',
				pIdKey: 'channel_parent'
			}
		}
	};
	var channelTree_treeNodes = ${channelTree };
	channelTree_treeNodes = channelTree_treeNodes==null?[]:channelTree_treeNodes;
	//初始化加载树
	$(function(){
		channelTree = $.fn.zTree.init($("#channelTree"), setting, channelTree_treeNodes);
	});
	//回调方法
	var modalCallback;
	//确定
	function sure(){
		var nodes = channelTree.getCheckedNodes();
		modalCallback(nodes);
	}
	//清空
	function clear(){
		modalCallback([]);
	}
	//绑定方法
	$(function(){
		$('#sure').bind('click', sure);
		$('#clear').bind('click', clear);
	});
	</script>
</head>
<body>
	<div class="operation-container">
		<sw:button accesskey="S" id="sure" value="确定 (S)" cssClass="btn btn-success" icon="save" />
		<sw:button accesskey="C" id="clear" value="清空 (C)" cssClass="btn btn-danger" icon="trash-o" />
	</div>
	<div id="teamTreeDiv" style="overflow:auto;">
		<ul id="channelTree" class="ztree"></ul>
	</div>
</body>
</html>