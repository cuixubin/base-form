<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.io.File"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 选择图片</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
	<style type="text/css">
	html, body{background:none;}
	.container{width:100%;}
	</style>
	<script type="text/javascript">
	var isSelect = '${isSelect }';
	var previewPath = '${previewPath }';
	var rootPath = '${rootPath }';
	var uploadPicPattern = '${upload_pic_pattern }';
	var uploadPicMaxSize = '${upload_pic_max_size }';
	var sessionId = '${pageContext.session.id }';
	var userId = '${sessionScope.__SESSION_USER__.user_id }';
	</script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/swfUpload/swfupload.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/swfUpload/swfupload.queue.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/swfUpload/fileprogress.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/core/server/image/image.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/core/server/image/handlers.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/core/server/image/upload.min.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/css/core/server/image/layout.min.css" />
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-lg-6 col-md-6">
				<div class="panel panel-default">
					<div class="panel-heading"><i class="fa fa-image"></i>图片服务器</div>
					<div class="panel-operation">
						<div class="btn-group">
							<sw:button accesskey="B" value="返回 (B)" cssClass="btn btn-default" icon="reply" onclick="goBack();" />
							<sw:button accesskey="R" value="刷新 (R)" cssClass="btn btn-default" icon="refresh" onclick="refresh();" />
						</div>
						&nbsp;&nbsp;|&nbsp;&nbsp;
						<div class="btn-group">
							<sw:button accesskey="T" value="显示设置 (T)" cssClass="btn btn-primary" icon="wrench" onclick="$('#showTypeModal').modal('show');" />
							<sw:button accesskey="O" value="文件操作 (O)" cssClass="btn btn-primary" icon="folder" onclick="$('#fileOperationModal').modal('show');" />
							<sw:button accesskey="S" value="文件查询 (S)" cssClass="btn btn-primary" icon="search" onclick="$('#searchModal').modal('show');" />
						</div>
						&nbsp;&nbsp;|&nbsp;&nbsp;
						<div class="btn-group">
							<sw:button accesskey="X" value="选择 (X)" cssClass="btn btn-success" icon="save" onclick="selectImage();" />
							<sw:button accesskey="Z" value="清空 (Z)" cssClass="btn btn-danger" icon="trash-o" onclick="clearImage();" />
						</div>
					</div>
					<div class="panel-operation">
						<input type="text" id="path_container" name="path_container" class="form-control" readonly="readonly" value="\" />
					</div>
					<div class="panel-body" id="show_images_container" style="padding:8px;min-height:320px;padding-bottom:0;"></div>
				</div>
			</div>
			<div class="col-lg-6 col-md-6">
				<div class="panel panel-default">
					<div class="panel-heading"><i class="fa fa-upload"></i>图片上传</div>
					<div class="panel-operation">
						<sw:button value="选择文件" id="button_placeholder" cssClass="btn btn-primary" icon="search" />
						<sw:button accesskey="U" value="开始上传 (U)" id="start_upload" cssClass="btn btn-success" icon="upload" onclick="uploader.startUpload();" />
						<sw:button accesskey="C" value="取消上传 (C)" id="cancel_upload" cssClass="btn btn-danger" icon="sign-out" onclick="cancelQueue(uploader);" disabled="disabled" />
					</div>
					<form id="form" action="/image/upload" method="post" enctype="multipart/form-data">
						<div class="modal-body fieldset flash" id="upload_container" style="min-height:363px;"></div>
					</form>
				</div>
			</div>
		</div>
		<div class="clearfix"></div>
	</div>
	<div class="modal fade" id="showTypeModal" tabindex="-1" role="dialog" aria-labelledby="showTypeModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h5 class="modal-title" id="showTypeLabel"><i class="fa fa-wrench"></i>显示设置</h5>
				</div>
				<div class="modal-body form-horizontal">
					<div class="form-group">
						<label class="col-sm-4 control-label text-right">展现形式：</label>
						<div class="col-sm-8">
							<select id="show_type" name="show_type" class="form-control" onchange="fakeRefresh();">
								<option value="0">列表</option>
								<option value="1" selected="selected">文件</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label text-right">排序类型：</label>
						<div class="col-sm-8">
							<select id="order" name="order" class="form-control" onchange="fakeRefresh();">
								<option value="0" selected="selected">名称</option>
								<option value="1">大小</option>
								<option value="2">时间</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label text-right">排序方式：</label>
						<div class="col-sm-8">
							<select id="order_type" name="order_type" class="form-control" onchange="fakeRefresh();">
								<option value="0" selected="selected">升序</option>
								<option value="1">降序</option>
							</select>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">
						<i class="fa fa-times"></i>关闭
					</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="fileOperationModal" tabindex="-1" role="dialog" aria-labelledby="fileOperationModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h5 class="modal-title" id="fileOperationLabel"><i class="fa fa-folder"></i>文件操作</h5>
				</div>
				<div class="modal-body form-horizontal">
					<div class="form-group">
						<label class="col-sm-3 control-label text-right">文件名称：</label>
						<div class="col-sm-6">
							<input type="text" id="file_name" name="file_name" class="form-control" placeholder="请输入文件名称" />
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<sw:button value="新建文件夹" cssClass="btn btn-primary" icon="plus" onclick="addFolder();" />
					<sw:button value="文件重命名" cssClass="btn btn-warning" icon="edit" onclick="modifyFileName();" />
					<sw:button value="删除文件" cssClass="btn btn-danger" icon="trash-o" onclick="deleteFile();" />
					<button type="button" class="btn btn-default" data-dismiss="modal">
						<i class="fa fa-times"></i>关闭
					</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="searchModal" tabindex="-1" role="dialog" aria-labelledby="searchModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h5 class="modal-title" id="searchLabel"><i class="fa fa-search"></i>文件查询</h5>
				</div>
				<div class="modal-body form-horizontal">
					<div class="form-group">
						<label class="col-sm-3 control-label text-right">文件名称：</label>
						<div class="col-sm-6">
							<input type="text" id="search_content" name="search_content" class="form-control" placeholder="请输入文件名称" />
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<sw:button value="查询" cssClass="btn btn-primary" icon="search" onclick="fakeRefresh();" />
					<button type="button" class="btn btn-default" data-dismiss="modal">
						<i class="fa fa-times"></i>关闭
					</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
