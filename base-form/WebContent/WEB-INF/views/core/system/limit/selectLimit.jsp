<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 选择功能</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
	<script type="text/javascript">
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
		check:{
			enable:true,
			radioType:'all',
			chkStyle:'${type}'
		},
		data: {
			key: {
				url:'not_url :)'
			},
			simpleData: {
				enable: true,
				idKey: 'id',
				pIdKey: 'pid'
			}
		}
	};
	var limitTree_treeNodes = ${limitTree };
	limitTree_treeNodes = limitTree_treeNodes==null?[]:limitTree_treeNodes;
	//初始化加载树
	$(function(){
		limitTree = $.fn.zTree.init($("#limitTree"), setting, limitTree_treeNodes);
	});
	//回调方法
	var modalCallback;
	//确定
	function sure(){
		var nodes = limitTree.getCheckedNodes();
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
<body style="background:none;">
	<div style="border-bottom:1px solid #ddd;padding:8px;">
		<sw:button accesskey="S" id="sure" value="确定 (S)" cssClass="btn btn-success" icon="save" />
		<sw:button accesskey="C" id="clear" value="清空 (C)" cssClass="btn btn-danger" icon="trash-o" />
	</div>
	<div id="limitTreeDiv" style="overflow:auto;">
		<ul id="limitTree" class="ztree"></ul>
	</div>
</body>
</html>