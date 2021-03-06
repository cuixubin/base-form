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
        %>
        <script type="text/javascript">
            var gridColumns = [
                {id: 'operation', title: '操作', columnClass: 'text-center', columnStyle: 'width:190px;', hideType: 'xs', extra: false, 'export': false, print: false, advanceQuery: false, resolution: function (value, record, column, grid, dataNo, columnNo) {
                        var content = '';
                        //预览
                        content += '<button type="button" class="btn btn-xs btn-icon btn-primary" title="预览" limit="wzgl/article/article/check" onclick="checkArticleSingle(\'' + record.article_id + '\')"><i class="fa fa-eye"></i></button>';
                        content += '&nbsp;&nbsp;';
                        //编辑
                        content += '<button type="button" class="btn btn-xs btn-icon btn-primary" title="编辑" limit="wzgl/article/article/-/edit" onclick="editArticleSingle(\'' + record.article_id + '\')"><i class="fa fa-edit"></i></button>';
                        content += '&nbsp;&nbsp;';
                        //禁用-启用
                        if (record.status == '1') {
                            content += '<button type="button" class="btn btn-xs btn-icon btn-warning" title="禁用" limit="wzgl/article/article/close" onclick="closeArticleSingle(\'' + record.article_id + '\')"><i class="fa fa-minus"></i></button>';
                        } else {
                            content += '<button type="button" class="btn btn-xs btn-icon btn-success" title="启用" limit="wzgl/article/article/open" onclick="openArticleSingle(\'' + record.article_id + '\')"><i class="fa fa-check"></i></button>';
                        }
                        content += '&nbsp;&nbsp;';
                        //删除
                        content += '<button type="button" class="btn btn-xs btn-icon btn-danger" title="删除" limit="wzgl/article/article/delete" onclick="deleteArticleSingle(\'' + record.article_id + '\')"><i class="fa fa-trash-o"></i></button>';
                        content += '&nbsp;&nbsp;';
                         
                        //打开相册
                        content += '<button type="button" class="btn btn-xs btn-icon btn-default" title="打开文章相册" limit="wzgl/article/article-/openArtAlbum" onclick="openArtAlbumSingle(\'' + record.article_id + '\')"><i class="fa fa-file-photo-o"></i></button>';
                        content += '&nbsp;&nbsp;';
                        //编辑相册
                        content += '<button type="button" class="btn btn-xs btn-icon btn-default" title="编辑文章相册" limit="wzgl/article/article-/editArtAlbum" onclick="editArtAlbumSingle(\'' + record.article_id + '\')"><i class="fa fa-pencil"></i></button>';
                        content += '&nbsp;&nbsp;';

                        return content;
                    }},
                {id: 'channel_id', title: '所属栏目', type: 'string',codeTable:${channelList}, columnClass: 'text-center', hideType: 'xs|sm|md|lg', fastQuery: true, fastQueryType: 'eq'},
                 
                {id: 'title', title: '文章标题', type: 'string', fastQuery: true, fastQueryType: 'lk'},
                {id: 'publisher', title: '发布人', type: 'string', columnClass: 'text-center', hideType: 'xs|sm', fastQuery: true, fastQueryType: 'lk'},
                {id: 'publish_time', title: '发布时间', type: 'date', format: 'yyyy-MM-dd hh:mm:ss', columnClass: 'text-center', hideType: 'xs|sm|md', fastQuery: true, fastQueryType: 'lk'},
                {id: 'status', title: '状态', type: 'string', codeTable: code_table_info['open_close'], columnClass: 'text-center', fastQuery: true, fastQueryType: 'eq', resolution: function (value, record, column, grid, dataNo, columnNo) {
                        return value == '0' ? ('<span class="label label-danger">' + code_table_info['open_close'][value] + '</span>') : ('<span class="label label-success">' + code_table_info['open_close'][value] + '</span>');
                    }},
                {id: 'creator_name', title: '创建人', type: 'string', columnClass: 'text-center', hideType: 'xs|sm|md|lg', fastQuery: true, fastQueryType: 'lk'},
                {id: 'create_time', title: '创建时间', type: 'date', format: 'yyyy-MM-dd hh:mm:ss', columnClass: 'text-center', hideType: 'xs|sm|md|lg', fastQuery: true, fastQueryType: 'range'},
                {id: 'topset', title: '是否置顶', type: 'string', codeTable: code_table_info['zero_one'], columnClass: 'text-center', hideType: 'xs|sm|md|lg', fastQuery: true, fastQueryType: 'eq', resolution: function (value, record, column, grid, dataNo, columnNo) {
                        return code_table_info['zero_one'][value];
                    }},
                {id: 'provenance', title: '信息来源', type: 'string', columnClass: 'text-center', hideType: 'xs|sm|md|lg', fastQuery: true, fastQueryType: 'range'}
            ];
            var gridOption = {
                loadURL: '${pageContext.request.contextPath }/wzgl/article/article/list',
                functionCode: 'WZGL_ARTICLE',
                check: true,
                exportFileName: '文章列表',
                columns: gridColumns,
                onRowDblClick: function (value, record, column, grid, dataNo, columnNo, cell, row, extraCell, e) {
                    editArticleSingle(record.article_id);
                },
                onGridComplete: function (value, record, column, grid, dataNo, columnNo, cell, row, extraCell, e) {
                    var urls = '';
                    urls += 'wzgl/article/article/-/edit,';
                    urls += 'wzgl/article/article/open,';
                    urls += 'wzgl/article/article/close,';
                    urls += 'wzgl/article/article/delete';
                    sw.initGridButtonLimit(urls, '${applicationScope.__SYSTEM_ATTRIBUTE__.show_button_when_no_limit.content }');
                }
            };
            var grid = $.fn.dlshouwen.grid.init(gridOption);
            ;
            //数据加载
            $(function () {
                grid.load();
            });
            //跳转添加
            function addArticle() {
                var url = '${pageContext.request.contextPath }/wzgl/article/article/add';
                sw.showProcessBar();
                window.location.href = url;
            }
            //启用操作
            function openArticle() {
                var recordObj = grid.getCheckedRecords();
                if (recordObj.length == 0) {
                    sw.toast('请选择要启用的文章！', 'warning', 3000);
                    return false;
                }
                if (!confirm('确定启用选中文章吗？')) {
                    return;
                }
                var articleIds = '';
                for (var i = 0; i < recordObj.length; i++) {
                    articleIds += recordObj[i].article_id + ((i != recordObj.length - 1) ? ',' : '');
                }
                var url = '${pageContext.request.contextPath }/wzgl/article/article/open';
                var params = new Object();
                params.articleIds = articleIds;
                sw.showProcessBar();
                sw.ajaxSubmitRefreshGrid(url, params, grid);
            }
            //启用操作
            function openArticleSingle(articleId) {
                if (!confirm('确定启用此文章吗？')) {
                    return;
                }
                var url = '${pageContext.request.contextPath }/wzgl/article/article/open';
                var params = new Object();
                params.articleIds = articleId;
                sw.showProcessBar();
                sw.ajaxSubmitRefreshGrid(url, params, grid);
            }
            //禁用操作
            function closeArticle() {
                var recordObj = grid.getCheckedRecords();
                if (recordObj.length == 0) {
                    sw.toast('请选择要禁用的文章！', 'warning', 3000);
                    return false;
                }
                if (!confirm('确定禁用选中文章吗？')) {
                    return;
                }
                var articleIds = '';
                for (var i = 0; i < recordObj.length; i++) {
                    articleIds += recordObj[i].article_id + ((i != recordObj.length - 1) ? ',' : '');
                }
                var url = '${pageContext.request.contextPath }/wzgl/article/article/close';
                var params = new Object();
                params.articleIds = articleIds;
                sw.showProcessBar();
                sw.ajaxSubmitRefreshGrid(url, params, grid);
            }
            //禁用操作
            function closeArticleSingle(articleId) {
                if (!confirm('确定禁用此文章吗？')) {
                    return;
                }
                var url = '${pageContext.request.contextPath }/wzgl/article/article/close';
                var params = new Object();
                params.articleIds = articleId;
                sw.showProcessBar();
                sw.ajaxSubmitRefreshGrid(url, params, grid);
            }
            //删除操作
            function deleteArticle() {
                var recordObj = grid.getCheckedRecords();
                if (recordObj.length == 0) {
                    sw.toast('请选择要删除的文章！', 'warning', 3000);
                    return false;
                }
                if (!confirm('确定删除选中文章吗？')) {
                    return;
                }
                var articleIds = '';
                for (var i = 0; i < recordObj.length; i++) {
                    articleIds += recordObj[i].article_id + ((i != recordObj.length - 1) ? ',' : '');
                }
                var url = '${pageContext.request.contextPath }/wzgl/article/article/delete';
                var params = new Object();
                params.articleIds = articleIds;
                sw.showProcessBar();
                sw.ajaxSubmitRefreshGrid(url, params, grid);
            }
            //删除操作
            function deleteArticleSingle(articleId) {
                if (!confirm('确定删除此文章吗？')) {
                    return;
                }
                var url = '${pageContext.request.contextPath }/wzgl/article/article/delete';
                var params = new Object();
                params.articleIds = articleId;
                sw.showProcessBar();
                sw.ajaxSubmitRefreshGrid(url, params, grid);
            }
            //编辑跳转
            function editArticle() {
                var recordObj = grid.getCheckedRecords();
                if (recordObj.length == 0) {
                    sw.toast('请选择要编辑的文章！', 'warning', 3000);
                    return false;
                }
                var articleId = recordObj[0].article_id;
                var url = '${pageContext.request.contextPath }/wzgl/article/article/' + articleId + '/edit';
                sw.showProcessBar();
                window.location.href = url;
            }
            //编辑跳转
            function editArticleSingle(articleId) {
                var url = '${pageContext.request.contextPath }/wzgl/article/article/' + articleId + '/edit';
                sw.showProcessBar();
                window.location.href = url;
            }

           //打开文章相册
            function openArtAlbumSingle(articleId) {
                var url = '${pageContext.request.contextPath}/wzgl/article/article/' + articleId + '/openArtAlubm';
                sw.showProcessBar();
                window.location.href = url;
            }
            //编辑相册
            function editArtAlbumSingle(articleId) {
                var url = '${pageContext.request.contextPath}/wzgl/article/article/' + articleId + '/editArtAlubm';
                sw.showProcessBar();
                window.location.href = url;
            }
            
             //打开文章相册
              function openArtAlbum() {
                var recordObj = grid.getCheckedRecords();
                if (recordObj.length == 0) {
                    sw.toast('请选择要打开的文章相册！', 'warning', 3000);
                    return false;
                }
                var articleId = recordObj[0].article_id;
                var url = '${pageContext.request.contextPath}/wzgl/article/article/' + articleId + '/openArtAlubm';
                sw.showProcessBar();
                window.location.href = url;
            }
            
             //打开文章相册
              function editArtAlbum() {
                var recordObj = grid.getCheckedRecords();
                if (recordObj.length == 0) {
                    sw.toast('请选择要编辑的文章相册！', 'warning', 3000);
                    return false;
                }
                var articleId = recordObj[0].article_id;
                 var url = '${pageContext.request.contextPath}/wzgl/article/article/' + articleId + '/editArtAlubm';
                sw.showProcessBar();
                window.location.href = url;
            }

             //查看文章
            function checkArticleSingle(articleId) {
                var url = '${pageContext.request.contextPath }/wzgl/article/article/' + articleId + '/check';
                window.open(url);
            }



            //初始化绑定方法
            $(function () {
                $('#add_article').bind('click', addArticle);
                $('#edit_article').bind('click', editArticle);
                $('#open_article').bind('click', openArticle);
                $('#close_article').bind('click', closeArticle);
                $('#delete_article').bind('click', deleteArticle);
                $('#open_artAlubm').bind('click', openArtAlbum);
                $('#edit_artAlbum').bind('click', editArtAlbum)
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
                <li><a href="javascript:void(0);"><i class="fa fa-book"></i>基础管理</a></li>
                <li class="active"><a href="javascript:void(0);"><i class="fa fa-file-o"></i>文章管理</a></li>
            </ul>
            <div class="panel panel-default">
                <div class="panel-heading"><i class="fa fa-bell"></i>文章管理</div>
                <div class="panel-operation">
                    <div class="btn-group">
                        <sw:button accesskey="A" id="add_article" value="新建 (A)" cssClass="btn btn-primary" limit="wzgl/article/article/add" icon="plus" />
                        <sw:button accesskey="M" id="edit_article" value="编辑 (M)" cssClass="btn btn-primary" limit="wzgl/article/article/-/edit" icon="edit" />
                        <sw:button accesskey="O" id="open_article" value="启用 (O)" cssClass="btn btn-success" limit="wzgl/article/article/open" icon="check" />
                        <sw:button accesskey="C" id="close_article" value="禁用 (C)" cssClass="btn btn-warning" limit="wzgl/article/article/close" icon="minus" />
                        <sw:button accesskey="W" id="delete_article" value="删除 (W)" cssClass="btn btn-danger" limit="wzgl/article/article/delete" icon="trash-o" />
                        <sw:button accesskey="P" id="open_artAlubm" value="查看文章相册 (P)" cssClass="btn btn-primary" limit="wzgl/article/article/-/openArtAlbum" icon="eye" />
                        <sw:button accesskey="E" id="edit_artAlbum" value="编辑文章相册 (E)" cssClass="btn btn-primary" limit="wzgl/article/article/-/editArtAlbum" icon="pencil" />
                    </div>
                </div>
                <div id="grid_container" class="dlshouwen-grid-container"></div>
                <div id="grid_toolbar_container" class="dlshouwen-grid-toolbar-container"></div>
            </div>
            <div class="clearfix"></div>
        </div>
    </body>
</html>