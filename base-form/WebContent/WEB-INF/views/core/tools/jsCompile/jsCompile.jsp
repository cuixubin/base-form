<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - JS编译器</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
	<%-- JS Compile --%>
	<script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/jsCompile/my.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/jsCompile/Words.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/jsCompile/jsformat.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/jsCompile/Packer.js"></script>
	<style type="text/css">
	textarea{width:100%;border:1px solid #d0d0d0;height:240px;background:#fff;}
	</style>
	<script type="text/javascript">
	$(function(){
		$('#do').click(function(){
			var packer = new Packer;
			var v = packer.pack($('#source').val(), true, true);
			$('#compile').val(v);
		});
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
			<li><a href="javascript:void(0);"><i class="fa fa-puzzle-piece"></i>系统工具</a></li>
			<li class="active"><a href="javascript:void(0);"><i class="fa fa-shield"></i>JS编译器</a></li>
		</ul>
		<div class="panel panel-default">
			<div class="panel-heading"><i class="fa fa-shield"></i>JS编译器</div>
			<div class="panel-operation">
				<div class="btn-group">
					<button accesskey="C" type="button" class="btn btn-primary" id="do"><i class="fa fa-arrow-right"></i>compile (C)</button>
				</div>
			</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-md-6">
						<textarea id="source"></textarea>
					</div>
					<div class="col-md-6">
						<textarea id="compile"></textarea>
					</div>
				</div>
			</div>
		</div>
		<div class="clearfix"></div>
	</div>
</body>
</html>