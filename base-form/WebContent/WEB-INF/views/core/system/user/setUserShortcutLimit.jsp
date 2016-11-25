<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 设置用户快捷方式</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
	<script type="text/javascript">
	//定义返回URL
	var goBackUrl = '${pageContext.request.contextPath }/core/system/user';
	//返回
	function goBack(){
		sw.showProcessBar();
		window.location.href = goBackUrl;
	}
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
		$('#set_user_shortcut_limit').bind('click', setUserShortcutLimit);
	});
	//设置用户快捷方式
	function setUserShortcutLimit(){
		var nodes = shortcutLimitTree.getCheckedNodes(true);
		if(!confirm('确定设置个人快捷方式？')){
			return;
		}
		var shortcutLimitIds = '';
		for(var i=0; i<nodes.length; i++){
			shortcutLimitIds += nodes[i].limit_id + ((i!=nodes.length-1)?',':'');
		}
		var url='${pageContext.request.contextPath }/core/system/user/set_user_shortcut_limit';
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
	<jsp:include page="/core/base/layout/header" />
	<jsp:include page="/core/base/layout/left" />
	<jsp:include page="/core/base/layout/shortcut" />
	<div class="main-container">
		<ul class="breadcrumb">
			<li><a href="${pageContext.request.contextPath }/core/base/home"><i class="fa fa-home"></i>首页</a></li>
			<li><a href="javascript:void(0);"><i class="fa fa-gears"></i>基础管理</a></li>
			<li><a href="${pageContext.request.contextPath }/core/system/user"><i class="fa fa-user"></i>用户管理</a></li>
			<li class="active"><a href="javascript:void(0);"><i class="fa fa-magic"></i>为 <span class="text-primary">${user.user_name }</span> 设置快捷方式</a></li>
		</ul>
		<div class="panel panel-default">
			<div class="panel-heading"><i class="fa fa-magic"></i>设置用户快捷方式</div>
			<div class="panel-operation text-right">
				<div class="btn-group">
					<sw:button accesskey="A" id="checkedAll" value="全选 (A)" cssClass="btn btn-default" icon="check-square-o" />
					<sw:button accesskey="C" id="unCheckedAll" value="反选 (C)" cssClass="btn btn-default" icon="square-o" />
				</div>
				&nbsp;&nbsp;|&nbsp;&nbsp;
				<div class="btn-group">
					<sw:button accesskey="S" id="set_user_shortcut_limit" value="保存 (S)" cssClass="btn btn-success" limit="core/system/attr/set_user_shortcut_limit" icon="save" />
					<button accesskey="B" type="button" class="btn btn-default" onclick="goBack();">
						<i class="fa fa-arrow-circle-left"></i>返回 (B)
					</button>
				</div>
			</div>
			<div class="panel-body">
				<ul id="shortcut_limit_tree_container" class="ztree"></ul>
			</div>
		</div>
		<div class="clearfix"></div>
	</div>
</body>
</html>
