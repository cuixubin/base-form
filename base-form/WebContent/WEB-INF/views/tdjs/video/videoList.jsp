<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
    <head>
        <title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 视频管理</title>
        <base target="_self" />
        <%@ include file="/resources/include/plugin.jsp"%>
        <%@ include file="/resources/include/extra.jsp"%>
        <%            //通过静态域设置码制映射对象
            CodeTableUtils.createCodeTableJS(application, out, "zero_one");  //是否显示
            CodeTableUtils.createCodeTableJS(application, out, "video_status");  //视频状态 1转换中 2已发布
%>
        <script type="text/javascript">
            var gridColumns = [
                {id: 'operation', title: '操作', columnClass: 'text-center', columnStyle: 'width:150px;', hideType: 'xs', extra: false, 'export': false, print: false, advanceQuery: false, resolution: function (value, record, column, grid, dataNo, columnNo) {
                        var content = '';
                        content += '<button type="button" class="btn btn-xs btn-icon btn-default" title="预览" limit=" " onclick="preview(\'' + record.vd_status + '\',\'' + record.vd_id + '\')"><i class="fa fa-eye"></i></button>';
                        content += '&nbsp;&nbsp;';
                        content += '<button type="button" class="btn btn-xs btn-icon btn-primary" title="编辑" limit="tdjs/tdjsVideo/video/-/edit/-/edit" onclick="editVideoSingle(\'' + record.vd_id + '\')"><i class="fa fa-edit"></i></button>';
                        content += '&nbsp;&nbsp;';
                        if (record.vd_isdisplay == '1') {
                            content += '<button type="button" class="btn btn-xs btn-icon btn-warning" title="禁用" limit="tdjs/tdjsVideo/video/close" onclick="closeVideoSingle(\'' + record.vd_id + '\')"><i class="fa fa-minus"></i></button>';
                        } else {
                            content += '<button type="button" class="btn btn-xs btn-icon btn-success" title="启用" limit="tdjs/tdjsVideo/video/open" onclick="openVideoSingle(\'' + record.vd_id + '\')"><i class="fa fa-check"></i></button>';
                        }
                        content += '&nbsp;&nbsp;';
                        content += '<button type="button" class="btn btn-xs btn-icon btn-danger" title="删除" limit="tdjs/tdjsVideo/video/-/edit/delete" onclick="deleteVideoSingle(\'' + record.vd_id + '\')"><i class="fa fa-trash-o"></i></button>';
                        return content;
                    }},
                {id: 'vd_name', title: '视频名称', type: 'string', columnClass: 'text-center', hideType: '', fastQuery: true, fastQueryType: 'lk'},
                {id: 'vd_time', title: '视频长度', columnClass: 'text-center', hideType: '', fastQuery: true, fastQueryType: 'lk'},
                {id: 'vd_uploaddate', title: '创建时间', type: 'date', format: 'yyyy-MM-dd hh:mm:ss', columnClass: 'text-center', hideType: '', fastQuery: true, fastQueryType: 'range'},
                {id: 'team_name', title: '所属团队', type: 'string', columnClass: 'text-center', hideType: 'lg|md|sm|xs', fastQuery: true, fastQueryType: 'lk'},
                {id: 'vd_uploaduser', title: '创建人', type: 'string', columnClass: 'text-center', hideType: 'lg|md|sm|xs', fastQuery: true, fastQueryType: 'lk'},
                {id: 'vd_updateuser', title: '编辑人', type: 'string', columnClass: 'text-center', hideType: 'lg|md|sm|xs', fastQuery: true, fastQueryType: 'lk'},
                {id: 'vd_updatedate', title: '编辑时间', type: 'date', format: 'yyyy-MM-dd hh:mm:ss', columnClass: 'text-center', hideType: 'lg|md|sm|xs', fastQuery: true, fastQueryType: 'range'},
                {id: 'vd_isdisplay', title: '是否显示', type: 'string', codeTable: code_table_info['zero_one'], columnClass: 'text-center', fastQuery: true, fastQueryType: 'eq', resolution: function (value, record, column, grid, dataNo, columnNo) {
                        if (value == '1') {
                            return '<span class="label label-success">' + code_table_info['zero_one'][value] + '</span>';
                        } else if (value == '0') {
                            return '<span class="label label-danger">' + code_table_info['zero_one'][value] + '</span>';
                        }
                        return '';
                    }}
            ];
            var gridOption = {
                loadURL: '${pageContext.request.contextPath }/tdjs/tdjsVideo/video/list',
                functionCode: 'WZGL_VIDEO',
                check: true,
                exportFileName: '视频列表',
                columns: gridColumns,
                onRowDblClick: function (value, record, column, grid, dataNo, columnNo, cell, row, extraCell, e) {
                    editVideoSingle(record.vd_id);
                },
                onGridComplete: function (value, record, column, grid, dataNo, columnNo, cell, row, extraCell, e) {
                    var urls = '';
                    urls += 'tdjs/tdjsVideo/video/-/edit,';
                    urls += 'tdjs/tdjsVideo/video/open,';
                    urls += 'tdjs/tdjsVideo/video/close,';
                    urls += 'tdjs/tdjsVideo/video/delete';
                    sw.initGridButtonLimit(urls, '${applicationScope.__SYSTEM_ATTRIBUTE__.show_button_when_no_limit.content }');
                }
            };
            var grid = $.fn.dlshouwen.grid.init(gridOption);
            //数据加载
            $(function () {
                grid.load();
            });
            //跳转添加
            function addVideo() {
                var url = '${pageContext.request.contextPath }/tdjs/tdjsVideo/video/add';
                sw.showProcessBar();
                window.location.href = url;
            }
            //启用操作
            function openVideo() {
                var recordObj = grid.getCheckedRecords();
                if (recordObj.length == 0) {
                    sw.toast('请选择要启用的视频！', 'warning', 3000);
                    return false;
                }
                if (!confirm('确定启用选中视频吗？')) {
                    return;
                }
                var videoIds = '';
                for (var i = 0; i < recordObj.length; i++) {
                    videoIds += recordObj[i].vd_id + ((i != recordObj.length - 1) ? ',' : '');
                }
                var url = '${pageContext.request.contextPath }/tdjs/tdjsVideo/video/open';
                var params = new Object();
                params.videoIds = videoIds;
                sw.showProcessBar();
                sw.ajaxSubmitRefreshGrid(url, params, grid);
            }
            //启用操作
            function openVideoSingle(videoId) {
                if (!confirm('确定启用此视频吗？')) {
                    return;
                }
                var url = '${pageContext.request.contextPath }/tdjs/tdjsVideo/video/open';
                var params = new Object();
                params.videoIds = videoId;
                sw.showProcessBar();
                sw.ajaxSubmitRefreshGrid(url, params, grid);
            }
            //禁用操作
            function closeVideo() {
                var recordObj = grid.getCheckedRecords();
                if (recordObj.length == 0) {
                    sw.toast('请选择要禁用的视频！', 'warning', 3000);
                    return false;
                }
                if (!confirm('确定禁用选中视频吗？')) {
                    return;
                }
                var videoIds = '';
                for (var i = 0; i < recordObj.length; i++) {
                    videoIds += recordObj[i].vd_id + ((i != recordObj.length - 1) ? ',' : '');
                }
                var url = '${pageContext.request.contextPath }/tdjs/tdjsVideo/video/close';
                var params = new Object();
                params.videoIds = videoIds;
                sw.showProcessBar();
                sw.ajaxSubmitRefreshGrid(url, params, grid);
            }
            //禁用操作
            function closeVideoSingle(videoId) {
                if (!confirm('确定禁用此视频吗？')) {
                    return;
                }
                var url = '${pageContext.request.contextPath }/tdjs/tdjsVideo/video/close';
                var params = new Object();
                params.videoIds = videoId;
                sw.showProcessBar();
                sw.ajaxSubmitRefreshGrid(url, params, grid);
            }
            //删除操作
            function deleteVideo() {
                var recordObj = grid.getCheckedRecords();
                if (recordObj.length == 0) {
                    sw.toast('请选择要删除的视频！', 'warning', 3000);
                    return false;
                }
                if (!confirm('确定删除选中视频吗？')) {
                    return;
                }
                var videoIds = '';
                for (var i = 0; i < recordObj.length; i++) {
                    videoIds += recordObj[i].vd_id + ((i != recordObj.length - 1) ? ',' : '');
                }
                var url = '${pageContext.request.contextPath }/tdjs/tdjsVideo/video/delete';
                var params = new Object();
                params.videoIds = videoIds;
                sw.showProcessBar();
                sw.ajaxSubmitRefreshGrid(url, params, grid);
            }
            //删除操作
            function deleteVideoSingle(videoId) {
                if (!confirm('确定删除此视频吗？')) {
                    return;
                }
                var url = '${pageContext.request.contextPath }/tdjs/tdjsVideo/video/delete';
                var params = new Object();
                params.videoIds = videoId;
                sw.showProcessBar();
                sw.ajaxSubmitRefreshGrid(url, params, grid);
            }
            //编辑跳转
            function editVideo() {
                var recordObj = grid.getCheckedRecords();
                if (recordObj.length == 0) {
                    sw.toast('请选择要编辑的视频！', 'warning', 3000);
                    return false;
                }
                var videoId = recordObj[0].vd_id;
                var url = '${pageContext.request.contextPath }/tdjs/tdjsVideo/video/' + videoId + '/edit';
                sw.showProcessBar();
                window.location.href = url;
            }
            //编辑跳转
            function editVideoSingle(videoId) {
                var url = '${pageContext.request.contextPath }/tdjs/tdjsVideo/video/' + videoId + '/edit';
                sw.showProcessBar();
                window.location.href = url;
            }
            function openVideoSingle(vd_id) {
                if (!confirm('确定启用此视频吗？')) {
                    return;
                }
                var url = '${pageContext.request.contextPath }/tdjs/tdjsVideo/video/open';
                var params = new Object();
                params.videoIds = vd_id;
                sw.showProcessBar();
                sw.ajaxSubmitRefreshGrid(url, params, grid);
            }
            function closeVideoSingle(vd_id) {
                if (!confirm('确定禁用此视频吗？')) {
                    return;
                }
                var url = '${pageContext.request.contextPath }/tdjs/tdjsVideo/video/close';
                var params = new Object();
                params.videoIds = vd_id;
                sw.showProcessBar();
                sw.ajaxSubmitRefreshGrid(url, params, grid);
            }
            function preview(vd_status,vd_id) {
//                if (vd_status != '2') {
//                    sw.alert("当前视频无法预览,仅能预览状态为已发布的视频.");
//                    return;
//                }
                var url = '${pageContext.request.contextPath }/tdjs/tdjsVideo/video/preview?videoId='+vd_id;
                sw.openModal({
                    id: 'videoPreview',
                    icon: 'video',
                    title: '视频预览',
                    url: url,
                    modalType: 'sw',
                    height:470,
                    showCloseButton: true,
                    showExpreviewButton: false
                });
            }
            //初始化绑定方法
            $(function () {
                $('#add_video').bind('click', addVideo);
                $('#edit_video').bind('click', editVideo);
                $('#open_video').bind('click', openVideo);
                $('#close_video').bind('click', closeVideo);
                $('#delete_video').bind('click', deleteVideo);
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
                <li class="active"><a href="javascript:void(0);"><i class="fa fa-video-camera"></i>视频管理</a></li>
            </ul>
            <div class="panel panel-default">
                <div class="panel-heading"><i class="fa fa-bell"></i>视频管理</div>
                <div class="panel-operation">
                    <div class="btn-group">
                        <sw:button accesskey="A" id="add_video" value="新建 (A)" cssClass="btn btn-primary" limit="wzgl/video/video/add" icon="plus" />
                        <sw:button accesskey="M" id="edit_video" value="编辑 (M)" cssClass="btn btn-primary" limit="wzgl/video/video/-/edit" icon="edit" />
                        <sw:button accesskey="O" id="open_video" value="启用 (O)" cssClass="btn btn-success" limit="wzgl/video/video/open" icon="check" />
                        <sw:button accesskey="C" id="close_video" value="禁用 (C)" cssClass="btn btn-warning" limit="wzgl/video/video/close" icon="minus" />
                        <sw:button accesskey="W" id="delete_video" value="删除 (W)" cssClass="btn btn-danger" limit="wzgl/video/video/delete" icon="trash-o" />
                    </div>
                </div>
                <div id="grid_container" class="dlshouwen-grid-container"></div>
                <div id="grid_toolbar_container" class="dlshouwen-grid-toolbar-container"></div>
            </div>
            <div class="clearfix"></div>
        </div>
    </body>
</html>