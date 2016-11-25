<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 设置默认快捷方式</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
	<style type="text/css">
	html, body{background:none;}
	</style>
	<script type="text/javascript">
	//设置快捷方式
	var shortcutLimitTree;
	var shortcutLimitTreeSetting = {
		view: {
			expandSpeed:100,
			showIcon : false,
			showLine : true,
			addDiyDom : function(treeId, treeNode){
				$('#'+treeNode.tId+'_span').prepend('<i class="fa fa-'+treeNode.icon_fa+'"></i>&nbsp;&nbsp;');
			}
		},
		check:{
			enable:true,
			chkStyle:'checkbox',
			chkboxType: { "Y" : "", "N" : "" }
		},
		data: {
			key : {
				name : 'limit_name',
				url : 'path_for_link'
			},
			simpleData: {
				enable: true,
				idKey: 'limit_id',
				pIdKey: 'pre_limit_id'
			}
		}
	};
	//获取所有功能列表
	var shortcutLimitList = ${shortcutLimitList };
	shortcutLimitList = shortcutLimitList==null?[]:shortcutLimitList;
	//初始化加载树
	$(function(){
		//加载树
		shortcutLimitTree = $.fn.zTree.init($("#shortcut_limit_tree_container"), shortcutLimitTreeSetting, shortcutLimitList);
		shortcutLimitTree.expandAll(true);
	});
	$(function(){
		$('#checkedAll').bind('click', function(){
			shortcutLimitTree.checkAllNodes(true);
		});
		$('#unCheckedAll').bind('click', function(){
			shortcutLimitTree.checkAllNodes(false);
		});
		$('#set_default_shortcut_limit').bind('click', setDefaultShortcutLimit);
	});
	//设置快捷方式
	function setDefaultShortcutLimit(){
		var nodes = shortcutLimitTree.getCheckedNodes(true);
		if(!confirm('确定配置默认快捷方式？')){
			return;
		}
		var shortcutLimitIds = '';
		for(var i=0; i<nodes.length; i++){
			shortcutLimitIds += nodes[i].limit_id + ((i!=nodes.length-1)?',':'');
		}
		var url='${pageContext.request.contextPath }/core/system/attr/set_default_shortcut_limit';
		var params = new Object();
		params.userId = '${userId }';
		params.shortcutLimitIds = shortcutLimitIds;
		sw.showProcessBar();
		sw.ajaxSubmit(url, params, function(data){
			sw.ajaxSuccessCallback(data);
		});
	}
	</script>
</head>
<body>
	<div class="panel panel-default">
		<div class="panel-operation text-right">
			<div class="btn-group">
				<sw:button id="checkedAll" value="全选" cssClass="btn btn-default" icon="check-square-o" />
				<sw:button id="unCheckedAll" value="反选" cssClass="btn btn-default" icon="square-o" />
			</div>
			&nbsp;&nbsp;|&nbsp;&nbsp;
			<div class="btn-group">
				<sw:button accesskey="S" id="set_default_shortcut_limit" value="保存 (S)" cssClass="btn btn-success" limit="core/system/attr/set_default_shortcut_limit" icon="save" />
			</div>
		</div>
		<div class="panel-body">
			<ul id="shortcut_limit_tree_container" class="ztree"></ul>
		</div>
	</div>
	<div class="clearfix"></div>
</body>
</html>
