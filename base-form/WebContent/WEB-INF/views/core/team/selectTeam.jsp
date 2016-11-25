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
            var teamTree;
            var setting = {
                view: {
                    expandSpeed: '',
                    showIcon: false,
                    showLine: false,
                    addDiyDom: function (treeId, treeNode) {
                        $('#' + treeNode.tId + '_span').prepend('<i class="fa fa-group"></i>&nbsp;&nbsp;');
                    }
                },
                check: {
                    enable: true,
                    radioType: 'all',
                    chkStyle: '${type}'
                },
                data: {
                    key: {
                        name: 'team_name'
                    },
                    simpleData: {
                        enable: true,
                        idKey: 'team_id',
                        pIdKey: 'pre_team_id'
                    }
                }
            };
            var teamTree_treeNodes = ${teamTree };
            teamTree_treeNodes = teamTree_treeNodes == null ? [] : teamTree_treeNodes;
            //初始化加载树
            $(function () {
                teamTree = $.fn.zTree.init($("#teamTree"), setting, teamTree_treeNodes);
                teamTree.expandAll(true);
            });
            //回调方法
            var modalCallback;
            //确定
            function sure() {
                var nodes = teamTree.getCheckedNodes();
                modalCallback(nodes);
            }
            //清空
            function clear() {
                modalCallback([]);
            }
            //绑定方法
            $(function () {
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
            <ul id="teamTree" class="ztree"></ul>
        </div>
    </body>
</html>