<%@ page contentType="text/html;charset=UTF-8"%>

<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<meta name="renderer" content="webkit"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">

<%-- favicon --%>
<link rel="shortcut icon" href="${pageContext.request.contextPath }/resources/images/favicon.ico">

<%-- jQuery --%>
   <%--  <script type="text/javascript" src="${pageContext.request.contextPath }/resources/webuploader/examples/image-upload/jquery.js"></script>--%>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/jquery/jquery.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/jquery/jquery.cookie.js"></script>

<%-- bootstrap --%>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/plugin/bootstrap/css/bootstrap.min.css" />
<!--[if lt IE 9]>
	<script src="${pageContext.request.contextPath }/resources/plugin/bootstrap/plugins/ie/html5shiv.js"></script>
	<script src="${pageContext.request.contextPath }/resources/plugin/bootstrap/plugins/ie/respond.js"></script>
<![endif]-->
<!--[if lt IE 8]>
	<script src="${pageContext.request.contextPath }/resources/plugin/bootstrap/plugins/ie/json2.js"></script>
<![endif]-->

<%-- html5media 处理不同浏览器下视频播放问题 --%>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/media/html5media.js"></script>

<%-- echarts --%>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/echarts/esl.js"></script>

<%-- font-awesome --%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/plugin/fontAwesome/css/font-awesome.min.css" media="all" />

<%-- icon --%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/icon/icon.css" media="all" />

<%-- toast --%>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/toast/jquery.toast.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/plugin/toast/jquery.toast.css" />

<%-- datePicker --%>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/datePicker/WdatePicker.js" defer="defer"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/plugin/datePicker/skin/WdatePicker.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/plugin/datePicker/skin/default/datepicker.css" />

<%-- grid --%>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/grid/dlshouwen.grid.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/grid/i18n/en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/grid/i18n/zh-cn.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/grid/i18n/zh-tw.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/plugin/grid/dlshouwen.grid.min.css" />

<%-- colorPicker --%>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/jsColor/jscolor.js"></script>

<%-- processBar --%>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/processBar/jquery.processBar.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/plugin/processBar/jquery.processBar.css" />

<%-- task --%>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/task/jquery.task.js"></script>

<%-- validator --%>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/validator/dlshouwen.validator.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/validator/i18n/en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/validator/i18n/zh-cn.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/validator/i18n/zh-tw.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/plugin/validator/dlshouwen.validator.min.css" />

<%-- validator --%>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/window/jquery.window.js"></script>

<%-- zTree --%>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/zTree/jquery.ztree.all-3.5.min.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/plugin/zTree/zTreeStyle.css" />

<%-- framework urils method js --%>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/utils.min.js"></script>
<%-- framework base method js --%>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/dlshouwen.js"></script>

<%-- global css --%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/css/global.min.css" media="all" />

<%-- icon css --%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/icon/icon.css" media="all" />

<%-- skin css --%>
<link rel="stylesheet" id="skin_css" type="text/css" href="${pageContext.request.contextPath }/core/base/skin" media="all" />

<%-- toTop --%>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/toTop/jquery.toTop.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/plugin/toTop/jquery.toTop.css" />
<div id="back-to-top" class="back-to-top" title="回到顶部"><i class="fa fa-arrow-circle-up"></i></div>

<%-- 右侧滑动 --%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/css/default.css" media="all" />
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/jspc/nav.js"></script>
<%-- 上传图片 --%>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/ajaxfileupload/ajaxfileupload.js"></script>
<%-- 文章批量上传图片 --%>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/zyUpload/zyFile.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/zyUpload/zyUpload.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/plugin/zyUpload/zyUpload.css" media="all" />
<%--图片拖拽--%>


<%-- 多图片上传--%>
