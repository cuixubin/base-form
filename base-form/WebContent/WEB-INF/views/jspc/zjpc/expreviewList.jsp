<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
    <head>
        <title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 专家评审</title>
        <base target="_self" />
        <%@ include file="/resources/include/plugin.jsp"%>
        <%@ include file="/resources/include/extra.jsp"%>
        <%            //通过静态域设置码制映射对象
            CodeTableUtils.createCodeTableJS(application, out, "review_status_zj");
            CodeTableUtils.createCodeTableJS(application, out, "jszz");
        %>
        <script type="text/javascript">
            var gridColumns = [
                {id: 'operation', title: '操作', columnClass: 'text-center', columnStyle: 'width:140px;', hideType: 'xs', extra: false, 'export': false, print: false, advanceQuery: false, resolution: function (value, record, column, grid, dataNo, columnNo) {
                        var content = '';
                        content += '<button type="button" class="btn btn-xs btn-icon btn-primary" title="评审" limit="jspc/zjpc/zjpc/review" onclick="reviewUserSingle(\'' + record.id + '\')"><i class="fa fa-edit"></i></button>';
//                        content += '&nbsp;&nbsp;';
//                        content += '<button type="button" class="btn btn-xs btn-icon btn-danger" title="删除" limit="jspc/zjpc/zjpc/delete" onclick="deleteUserSingle(\'' + record.id + '\')"><i class="fa fa-trash-o"></i></button>';
                        return content;
                    }},
                {id: 'id', title: '编号', type: 'string',columnClass: 'text-center', fastQuery: true, fastQueryType: 'lk'},
                {id: 'user_name', title: '姓名', type: 'string', columnClass: 'text-center', hideType: 'xs|sm', fastQuery: true, fastQueryType: 'lk'},
                {id: 'qualified', title: '资质意向', type: 'string', codeTable: code_table_info['jszz'], columnClass: 'text-center', hideType: 'xs|sm|md', fastQuery: true, fastQueryType: 'eq', resolution: function (value, record, column, grid, dataNo, columnNo) {
                        return  code_table_info['jszz'][value];
                    }},
                {id: 'status', title: '状态', type: 'string', codeTable: code_table_info['review_status_zj'], columnClass: 'text-center', fastQuery: true, fastQueryType: 'eq', resolution: function (value, record, column, grid, dataNo, columnNo) {
                        if (value == 0) {
                            return '<span class="label label-warning">' + code_table_info['review_status_zj'][value] + '</span>';
                        } else if (value == 1) {
                            return '<span class="label label-success">' + code_table_info['review_status_zj'][value] + '</span>';
                        } else if (value == 2) {
                            return '<span class="label label-danger">' + code_table_info['review_status_zj'][value] + '</span>';
                        } else {
                            return '<span class="label label-default">' + code_table_info['review_status_zj'][value] + '</span>'
                        }

                    }},
                {id: 'review_name', title: '评审人', type: 'string', columnClass: 'text-center', hideType: 'xs|sm|md|lg', fastQuery: true, fastQueryType: 'lk'},
                {id: 'create_date', title: '创建时间', type: 'date', format: 'yyyy-MM-dd hh:mm:ss', columnClass: 'text-center', hideType: 'xs|sm|md', fastQuery: true, fastQueryType: 'range'},
                {id: 'review_date', title: '评审时间', type: 'date', format: 'yyyy-MM-dd hh:mm:ss', columnClass: 'text-center', hideType: 'xs|sm|md|lg', fastQuery: true, fastQueryType: 'range'},
                {id: 'note', title: '评审意见', type: 'string', columnClass: 'text-center', hideType: 'xs|sm|md|lg', fastQuery: true, fastQueryType: 'lk'}
            ];
            var gridOption = {
                loadURL: '${pageContext.request.contextPath }/jspc/zjpc/zjpc/list',
                functionCode: 'JSPC_EXPREVIEW',
                check: true,
                exportFileName: '专家评测',
                columns: gridColumns,
                onRowDblClick: function (value, record, column, grid, dataNo, columnNo, cell, row, extraCell, e) {
                    reviewUserSingle(record.id);
                },
                onGridComplete: function (value, record, column, grid, dataNo, columnNo, cell, row, extraCell, e) {
                    var urls = '';
//                    urls += 'wzgl/article/article/-/edit,';
                    urls += 'jspc/zjpc/zjpc/review';
                    sw.initGridButtonLimit(urls, '${applicationScope.__SYSTEM_ATTRIBUTE__.show_button_when_no_limit.content }');
                }
            };
            var grid = $.fn.dlshouwen.grid.init(gridOption);
            ;
            //数据加载
            $(function () {
                grid.load();
            });

            //编辑跳转
            function reviewUserSingle(id) {
                var url = '${pageContext.request.contextPath }/jspc/zjpc/zjpc/' + id + '/edit';
                sw.showProcessBar();
                window.location.href = url;
            }

            //编辑跳转
            function reviewUser() {
                var recordObj = grid.getCheckedRecords();
                if (recordObj.length == 0) {
                    sw.toast('请选择评审信息！', 'warning', 3000);
                    return false;
                }
                var id = recordObj[0].id;
                var url = '${pageContext.request.contextPath }/jspc/zjpc/zjpc/' + id + '/edit';
                sw.showProcessBar();
                window.location.href = url;
            }





            //初始化绑定方法
            $(function () {
                $('#expert_review').bind('click', reviewUser);
                //$('#delete_review').bind('click', deleteArticle);
            }
            );
        </script>
    </head>
    <body>
        <jsp:include page="/core/base/layout/header" />
        <jsp:include page="/core/base/layout/left" />
        <jsp:include page="/core/base/layout/shortcut" />
        <div class="main-container">
            <ul class="breadcrumb">
                <li><a href="${pageContext.request.contextPath }/core/base/home"><i class="fa fa-home"></i>首页</a></li>
                <li><a href="javascript:void(0);"><i class="fa fa-graduation-cap"></i>教师评测</a></li>
                <li class="active"><a href="${pageContext.request.contextPath }/jspc/zjpc/zjpc"><i class="fa fa-legal"></i>专家评测</a></li>
            </ul>
            <div class="panel panel-default">
                <div class="panel-heading"><i class="fa fa-bell"></i>专家评测</div>
                <div class="panel-operation">
                    <div class="btn-group">
                        <sw:button accesskey="M" id="expert_review" value="评审 (M)" cssClass="btn btn-primary" limit="jspc/zjpc/zjpc/review" icon="edit" />
                        <%--<sw:button accesskey="W" id="delete_review" value="删除 (W)" cssClass="btn btn-danger" limit="wzgl/article/article/delete" icon="trash-o" />--%>
                    </div>
                </div>
                <div id="grid_container" class="dlshouwen-grid-container"></div>
                <div id="grid_toolbar_container" class="dlshouwen-grid-toolbar-container"></div>
            </div>
            <div class="clearfix"></div>
        </div>
    </body>
</html>