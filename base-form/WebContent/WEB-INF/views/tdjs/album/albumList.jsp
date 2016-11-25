<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
    <head>
        <title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 相册管理</title>
        <base target="_self" />
        <%@ include file="/resources/include/plugin.jsp"%>
        <%@ include file="/resources/include/extra.jsp"%>
        <%            //通过静态域设置码制映射对象
            CodeTableUtils.createCodeTableJS(application, out, "album_flag");  //视频状态 1转换中 2已发布
%>
        <script type="text/javascript">
            var gridColumns = [
                {id: 'operation', title: '操作', columnClass: 'text-center', columnStyle: 'width:150px;', hideType: 'xs', extra: false, 'export': false, print: false, advanceQuery: false, resolution: function (value, record, column, grid, dataNo, columnNo) {
                        var content = '';
                        content += '<button type="button" class="btn btn-xs btn-icon btn-primary" title="编辑" limit="tdjs/tdjsAlbum/album/-/edit" onclick="editAlbumSingle(\'' + record.album_id + '\')"><i class="fa fa-edit"></i></button>';
                        content += '&nbsp;&nbsp;';
                        content += '<button type="button" class="btn btn-xs btn-icon btn-danger" title="删除" limit="tdjs/tdjsAlbum/album/delete" onclick="deleteAlbumSingle(\'' + record.album_id + '\')"><i class="fa fa-trash-o"></i></button>';
                        content += '&nbsp;&nbsp;';
                        content += '<button type="button" class="btn btn-xs btn-icon btn-default" title="打开相册" limit="tdjs/tdjsAlbum/album/-/openAlbum" onclick="openAlbumSingle(\'' + record.album_id + '\')"><i class="fa fa-eye"></i></button>';
                        return content;
                    }},
                {id: 'album_name', title: '相册名称', type: 'string', columnClass: 'text-center', hideType: '', fastQuery: true, fastQueryType: 'lk'},
                {id: 'album_description', title: '相册描述', columnClass: 'text-center', hideType: '', fastQuery: true, fastQueryType: 'lk'},
                {id: 'album_createdate', title: '创建时间', type: 'date', format: 'yyyy-MM-dd hh:mm:ss', columnClass: 'text-center', hideType: '', fastQuery: true, fastQueryType: 'range'},
                {id: 'album_createuser', title: '创建人', type: 'string', columnClass: 'text-center', hideType: 'lg|md|sm|xs', fastQuery: true, fastQueryType: 'lk'},
                {id: 'album_updatedate', title: '编辑时间', type: 'date', format: 'yyyy-MM-dd hh:mm:ss', columnClass: 'text-center', hideType: 'lg|md|sm|xs', fastQuery: true, fastQueryType: 'range'},
                {id: 'album_flag', title: '相册类型', type: 'string', codeTable: code_table_info['album_flag'], columnClass: 'text-center', fastQuery: true, fastQueryType: 'eq', resolution: function (value) {
                        if (value !== '') {
                            return '<span class="label label-success">' + code_table_info['album_flag'][value] + '</span>';
                        }
                        return '';
                    }}
            ];
            var gridOption = {
                loadURL: '${pageContext.request.contextPath }/tdjs/tdjsAlbum/album/list',
                functionCode: 'WZGL_ALBUM',
                check: true,
                exportFileName: '相册列表',
                columns: gridColumns,
                onRowDblClick: function (value, record, column, grid, dataNo, columnNo, cell, row, extraCell, e) {
                    editAlbumSingle(record.album_id);
                },
                onGridComplete: function (value, record, column, grid, dataNo, columnNo, cell, row, extraCell, e) {
                    var urls = '';
                    urls += 'tdjs/tdjsAlbum/album/-/edit,';
                    urls += 'tdjs/tdjsAlbum/album/delete';
                    sw.initGridButtonLimit(urls, '${applicationScope.__SYSTEM_ATTRIBUTE__.show_button_when_no_limit.content }');
                }
            };
            var grid = $.fn.dlshouwen.grid.init(gridOption);
            //数据加载
            $(function () {
                grid.load();
            });
            //跳转添加
            function addAlbum() {
                var url = '${pageContext.request.contextPath }/tdjs/tdjsAlbum/album/add';
                sw.showProcessBar();
                window.location.href = url;
            }

            //删除操作
            function deleteAlbum() {
                var recordObj = grid.getCheckedRecords();
                if (recordObj.length == 0) {
                    sw.toast('请选择要删除的相册！', 'warning', 3000);
                    return false;
                }
                if (!confirm('确定删除选中相册吗？')) {
                    return;
                }
                var albumIds = '';
                for (var i = 0; i < recordObj.length; i++) {
                    albumIds += recordObj[i].album_id + ((i != recordObj.length - 1) ? ',' : '');
                }
                var url = '${pageContext.request.contextPath }/tdjs/tdjsAlbum/album/delete';
                var params = new Object();
                params.albumIds = albumIds;
                sw.showProcessBar();
                sw.ajaxSubmitRefreshGrid(url, params, grid);
            }
            //删除操作
            function deleteAlbumSingle(albumId) {
                if (!confirm('确定删除此相册吗？')) {
                    return;
                }
                var url = '${pageContext.request.contextPath }/tdjs/tdjsAlbum/album/delete';
                var params = new Object();
                params.albumIds = albumId;
                sw.showProcessBar();
                sw.ajaxSubmitRefreshGrid(url, params, grid);
            }
            //编辑跳转
            function editAlbum() {
                var recordObj = grid.getCheckedRecords();
                if (recordObj.length == 0) {
                    sw.toast('请选择要编辑的相册！', 'warning', 3000);
                    return false;
                }
                var albumId = recordObj[0].album_id;
                var url = '${pageContext.request.contextPath }/tdjs/tdjsAlbum/album/' + albumId + '/edit';
                sw.showProcessBar();
                window.location.href = url;
            }
            //编辑跳转
            function editAlbumSingle(albumId) {
                var url = '${pageContext.request.contextPath }/tdjs/tdjsAlbum/album/' + albumId + '/edit';
                sw.showProcessBar();
                window.location.href = url;
            }
            function openAlbumSingle(album_id) {
                var url = '${pageContext.request.contextPath }/tdjs/tdjsAlbum/album/' + album_id + '/openAlbum';
                sw.showProcessBar();
                window.location.href = url;
            }
            //打开相册跳转
            function openAlbum() {
                var recordObj = grid.getCheckedRecords();
                if (recordObj.length == 0) {
                    sw.toast('请选择要打开的相册！', 'warning', 3000);
                    return false;
                }
                var albumId = recordObj[0].album_id;
                var url = '${pageContext.request.contextPath }/tdjs/tdjsAlbum/album/' + albumId + '/openAlbum';
                sw.showProcessBar();
                window.location.href = url;
            }
            //初始化绑定方法
            $(function () {
                $('#add_album').bind('click', addAlbum);
                $('#edit_album').bind('click', editAlbum);
                $('#delete_album').bind('click', deleteAlbum);
                $('#open_album').bind('click', openAlbum);
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
                <li><a href="javascript:void(0);"><i class="fa fa-bell"></i>基础管理</a></li>
                <li class="active"><a href="javascript:void(0);"><i class="fa fa-bell"></i>相册管理</a></li>
            </ul>
            <div class="panel panel-default">
                <div class="panel-heading"><i class="fa fa-bell"></i>相册管理</div>
                <div class="panel-operation">
                    <div class="btn-group">
                        <sw:button accesskey="A" id="add_album" value="新建 (A)" cssClass="btn btn-primary" limit="tdjs/tdjsAlbum/album/add" icon="plus" />
                        <sw:button accesskey="M" id="edit_album" value="编辑 (M)" cssClass="btn btn-primary" limit="tdjs/tdjsAlbum/album/-/edit" icon="edit" />
                        <sw:button accesskey="W" id="delete_album" value="删除 (W)" cssClass="btn btn-danger" limit="tdjs/tdjsAlbum/album/delete" icon="trash-o" />
                        <sw:button accesskey="O" id="open_album" value="打开相册 (O)" cssClass="btn btn-primary" limit="tdjs/tdjsAlbum/album/-/preview" icon="file-image-o" />
                    </div>
                </div>
                <div id="grid_container" class="dlshouwen-grid-container"></div>
                <div id="grid_toolbar_container" class="dlshouwen-grid-toolbar-container"></div>
            </div>
            <div class="clearfix"></div>
        </div>
    </body>
</html>