<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 网页截图</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
	<style type="text/css">
	html, body{background:none;}
	iframe{display:block;border:0;}
	</style>
	<script type="text/javascript">
	$(function(){
		var screenAttrList = window.opener.screenAttrList;
		for(var i=0; i<screenAttrList.length; i++){
			var screenAttr = screenAttrList[i];
			$('body').append('<iframe id="iframe_'+screenAttr.sequence+'" name="iframe_'+screenAttr.sequence+'" src="'+screenAttr.url+'" width="'+screenAttr.width+'" height="'+screenAttr.height+'"></iframe>');
		}
	});
	</script>
</head>
<body>
</body>
</html>