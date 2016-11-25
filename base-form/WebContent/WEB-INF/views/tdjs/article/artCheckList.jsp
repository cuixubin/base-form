<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
    <head>
        <title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 文章管理</title>
        <base target="_self" />
        <%@ include file="/resources/include/plugin.jsp"%>
        <%@ include file="/resources/include/extra.jsp"%>
        <%            //通过静态域设置码制映射对象
            CodeTableUtils.createCodeTableJS(application, out, "open_close");
            CodeTableUtils.createCodeTableJS(application, out, "zero_one");
            CodeTableUtils.createCodeTableJS(application, out, "articleType");
            CodeTableUtils.createCodeTableJS(application, out, "article_limit");
        %>
        <script type="text/javascript">
            var gridColumns = [
                {id: 'operation', title: '操作', columnClass: 'text-center', columnStyle: 'width:130px;', hideType: 'xs', extra: false, 'export': false, print: false, advanceQuery: false, resolution: function (value, record, column, grid, dataNo, columnNo) {
                        var content = '';
                        //编辑
                        content += '<button type="button" class="btn btn-xs btn-icon btn-primary" title="预览" limit="tdjs/tdjsArticle/article/-/check" onclick="checkArticleSingle(\'' + record.article_id + '\')"><i class="fa fa-eye"></i></button>';
                        content += '&nbsp;&nbsp;';

                        //通过
                        content += '<button type="button" class="btn btn-xs btn-icon btn-success" title="通过" limit="tdjs/tdjsArticle/article/-/check" onclick="checkArt(\'' + record.article_id + '\',1)"><i class="fa fa-check"></i></button>';
                        content += '&nbsp;&nbsp;';

                        //拒绝
                        content += '<button type="button" class="btn btn-xs btn-icon btn-warning" title="拒绝" limit="tdjs/tdjsArticle/article/-/check" onclick="checkArt(\'' + record.article_id + '\',2)"><i class="fa fa-minus"></i></button>';
                        content += '&nbsp;&nbsp;';
                        return content;
                    }},
                {id: 'title', title: '文章标题', type: 'string', columnClass: 'text-center', hideType: 'xs|sm', fastQuery: true, fastQueryType: 'lk'},
                {id: 'editor_name', title: '发布人', type: 'string', columnClass: 'text-center', hideType: 'xs|sm', fastQuery: true, fastQueryType: 'lk'},
                {id: 'publish_time', title: '发布时间', type: 'date', format: 'yyyy-MM-dd hh:mm:ss', columnClass: 'text-center', hideType: 'xs|sm|md', fastQuery: true, fastQueryType: 'lk'},
                {id: 'team_name', title: '所属团队', type: 'string', columnClass: 'text-center', hideType: 'xs|sm', fastQuery: true, fastQueryType: 'lk'},
                {id: 'check_comment', title: '评审意见', type: 'string', columnClass: 'text-center', hideType: 'xs|sm|md|lg', fastQuery: true, fastQueryType: 'lk'},
                {id: 'article_limit', title: '文章权限', type: 'string', codeTable: code_table_info['article_limit'], hideType: 'xs|sm', columnClass: 'text-center', fastQuery: true, fastQueryType: 'eq', resolution: function (value, record, column, grid, dataNo, columnNo) {
                    return value == '2' ? ('<span class="label label-warning">' + code_table_info['article_limit'][value] + '</span>') : ('<span class="label label-success">' + code_table_info['article_limit'][value] + '</span>');
                }},
                {id: 'status', title: '状态', type: 'string', codeTable: code_table_info['open_close'], hideType: 'xs|sm', columnClass: 'text-center', fastQuery: true, fastQueryType: 'eq', resolution: function (value, record, column, grid, dataNo, columnNo) {
                    return value == '0' ? ('<span class="label label-danger">' + code_table_info['open_close'][value] + '</span>') : ('<span class="label label-success">' + code_table_info['open_close'][value] + '</span>');
                }},
                {id: 'tabloid', title: '文章摘要', type: 'string', columnClass: 'text-center', hideType: 'xs|sm|md|lg', fastQuery: true, fastQueryType: 'lk'},
                {id: 'article_type', title: '审批状态', type: 'string', codeTable: code_table_info['articleType'], columnClass: 'text-center', fastQuery: true, fastQueryType: 'eq', resolution: function (value, record, column, grid, dataNo, columnNo) {
                        if (value == 1) {
                            return '<span class="label label-success">' + code_table_info['articleType'][value] + '</span>';
                        } else if (value == 2) {
                            return '<span class="label label-info">' + code_table_info['articleType'][value] + '</span>';
                        } else if (value == 3) {
                            return '<span class="label label-warning">' + code_table_info['articleType'][value] + '</span>';
                        } else if (value == 4) {
                            return '<span class="label label-danger">' + code_table_info['articleType'][value] + '</span>';
                        }
                        return "";

                    }},
                {id: 'creator_name', title: '创建人', type: 'string', columnClass: 'text-center', hideType: 'xs|sm|md|lg', fastQuery: true, fastQueryType: 'lk'},
                {id: 'create_time', title: '创建时间', type: 'date', format: 'yyyy-MM-dd hh:mm:ss', columnClass: 'text-center', hideType: 'xs|sm|md|lg', fastQuery: true, fastQueryType: 'range'},
                {id: 'reviewer', title: '审核人', type: 'string', columnClass: 'text-center', hideType: 'xs|sm|md|lg', fastQuery: true, fastQueryType: 'lk'},
                {id: 'review_time', title: '审核时间', type: 'date', format: 'yyyy-MM-dd hh:mm:ss', columnClass: 'text-center', hideType: 'xs|sm|md|lg', fastQuery: true, fastQueryType: 'range'},
                {id: 'topset', title: '是否置顶', type: 'string', codeTable: code_table_info['zero_one'], columnClass: 'text-center', hideType: 'xs|sm|md|lg', fastQuery: true, fastQueryType: 'eq', resolution: function (value, record, column, grid, dataNo, columnNo) {
                        return code_table_info['zero_one'][value];
                    }},
                {id: 'provenance', title: '信息来源', type: 'string', columnClass: 'text-center', hideType: 'xs|sm|md|lg', fastQuery: true, fastQueryType: 'range'}
            ];
            var gridOption = {
                loadURL: '${pageContext.request.contextPath }/tdjs/tdjsArticle/articleCheck/list',
                functionCode: 'WZGL_ARTICLE',
                check: true,
                exportFileName: '文章列表',
                columns: gridColumns,
                onRowDblClick: function (value, record, column, grid, dataNo, columnNo, cell, row, extraCell, e) {
                    checkArticleSingle(record.article_id);
                },
                onGridComplete: function (value, record, column, grid, dataNo, columnNo, cell, row, extraCell, e) {
                    var urls = '';
                    urls += 'tdjs/tdjsArticle/article/-/check,';
                    urls += 'tdjs/tdjsArticle/article/open,';
                    urls += 'tdjs/tdjsArticle/article/close,';
                    urls += 'tdjs/tdjsArticle/article/delete';
                    sw.initGridButtonLimit(urls, '${applicationScope.__SYSTEM_ATTRIBUTE__.show_button_when_no_limit.content }');
                }
            };
            var grid = $.fn.dlshouwen.grid.init(gridOption);
            ;
            //数据加载
            $(function () {
                grid.load();
            });


            //评审
            function checkArt(articleId, flag) {

                var url = '${pageContext.request.contextPath}/tdjs/tdjsArticle/articleCheck/note';
                sw.openModal({
                    id: 'checkArt',
                    icon: 'bell',
                    title: '评审意见',
                    url: url,
                    modalType: 'sm',
                    showCloseButton: false,
                    showConfirmButton: true,
                    width: '30%',
                    height: '250px',
                    callback: function () {
                        var check_comment = "";
                        check_comment = document.getElementById("checkArtFrame").contentWindow.getNote();
                        var url = '${pageContext.request.contextPath}/tdjs/tdjsArticle/articleCheck/' + articleId + '/pass';
                        if (flag != 1) {
                            url = '${pageContext.request.contextPath}/tdjs/tdjsArticle/articleCheck/' + articleId + '/refuse';
                        }
                        var params = new Object();
                        params.check_comment = check_comment;
                        sw.showProcessBar();
                        sw.ajaxSubmit(url, params, function (data) {
                            sw.ajaxSuccessCallback(data, function () {
                                sw.closeModal('check');
                                sw.closeModal('checkArt');
                                grid.reload(true);
                            });
                        });

                    }


                });

            }


            //预览文章（单条）
            function checkArticleSingle(articleId) {
                var url = '${pageContext.request.contextPath }/tdjs/tdjsArticle/articleCheck/' + articleId + '/check';
                sw.showProcessBar();
//                window.location.href = url;
                window.open(url);
            }

            //批量通过文章
            function batchSetUseArticle() {
                var recordObj = grid.getCheckedRecords();
                if (recordObj.length == 0) {
                    sw.toast('请选择要审批的文章！', 'warning', 3000);
                    return false;
                }
                var articleIds = '';
                for (var i = 0; i < recordObj.length; i++) {
                    articleIds += recordObj[i].article_id + ((i != recordObj.length - 1) ? ',' : '');
                }
                checkArt(articleIds, 1);
            }
            //批量拒绝文章
            function batchSetUnuseArticle() {
                var recordObj = grid.getCheckedRecords();
                if (recordObj.length == 0) {
                    sw.toast('请选择要审批的文章！', 'warning', 3000);
                    return false;
                }
                var articleIds = '';
                for (var i = 0; i < recordObj.length; i++) {
                    articleIds += recordObj[i].article_id + ((i != recordObj.length - 1) ? ',' : '');
                }
                checkArt(articleIds, 2);
            }


            //初始化绑定方法
            $(function () {
                $('#use_article').bind('click', batchSetUseArticle);
                $('#unuse_article').bind('click', batchSetUnuseArticle);
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
                <li><a href="javascript:void(0);"><i class="fa fa-group"></i>团队建设</a></li>
                <li><a href="javascript:void(0);"><i class="fa fa-book"></i>基础管理</a></li>
                <li class="active"><a href="javascript:void(0);"><i class="fa fa-file-o"></i>文章审批</a></li>
            </ul>
            <div class="panel panel-default">
                <div class="panel-heading"><i class="fa fa-bell"></i>文章审批</div>
                <div class="panel-operation">
                    <div class="btn-group">
                        <sw:button accesskey="O" id="use_article" value="通过(O)" cssClass="btn btn-success" limit="tdjs/tdjsArticle/article/-/check" icon="check" />
                        <sw:button accesskey="F" id="unuse_article" value="拒绝(F)" cssClass="btn btn-warning" limit="tdjs/tdjsArticle/article/-/check" icon="minus" />
                    </div>
                </div>
                <div id="grid_container" class="dlshouwen-grid-container"></div>
                <div id="grid_toolbar_container" class="dlshouwen-grid-toolbar-container"></div>
            </div>
            <div class="clearfix"></div>
        </div>
    </body>
</html>