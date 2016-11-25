<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sw" uri="http://core.dlshouwen.com/tags/all" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:if test="${is_show_shortcut=='1' }">
	<%-- jquery rotate --%>
	<script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/rotate/jQueryRotateCompressed.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/rotate/jquery.easing.min.js"></script>
	<script type="text/javascript">
	var shortcutList = ${shortcutList };
	shortcutList = shortcutList==null?[]:shortcutList;
	var bottom = '${bottom }';
	</script>
	<style type="text/css">
	.shortcuts{position:fixed;bottom:0px;left:0px;z-index:1090;background:red;}
	.shortcut{position:absolute;background:white;width:28px;height:28px;border-radius:100%;z-index:2;cursor:pointer;
		text-align:center;line-height:28px;color:white;background:#000;font-size:16px;left:10px;bottom:10px;opacity:0.8;
		-webkit-box-shadow: 0 0 6px #000;
		-moz-box-shadow: 0 0 6px #000;
		-o-box-shadow: 0 0 6px #000;
		box-shadow: 0 0 6px #000;
	}
	.shortcut a:link, .shortcut a:hover, .shortcut a:focus, .shortcut a:visited{color:#fff;text-decoration:none;}
	.shortcut a>i{margin-right:0;}
	</style>
	<script type="text/javascript">
	//定义半径、展开速度
	var r = 90;
	var speed = 300;
	//初始化快捷方式
	$(function(){
		$('#shortcuts').css('bottom', bottom+'px');
		if(shortcutList==null||shortcutList.length==0){
			return;
		}
		var angle = 90/(shortcutList.length+1);
		for(var i=0; i<shortcutList.length; i++){
			var shortcut = shortcutList[i];
			var content = '';
			var x = Math.sin(angle*(i+1)*2*Math.PI/360)*r-20/2;
			var y = Math.cos(angle*(i+1)*2*Math.PI/360)*r-20/2;
			content += '<div id="shortcut_'+i+'" title="'+shortcut.limit_name+'" class="shortcut" x="'+Math.round(x)+'" y="'+Math.round(y)+'" style="z-index:1;display:none;">';
			content += '	<a href="'+path+'/'+shortcut.url+'">';
			content += '		<i class="fa fa-'+shortcut.icon_fa+'"></i>';
			content += '	</a>';
			content += '</div>';
			$('#shortcuts').append(content);
		}
	});
	//显隐快捷方式
	function showHideShortcuts(){
		var isShow = $('#shortcut-base').attr('isshow');
		if(isShow==0){
			$('#shortcut-base').attr('isshow', '1');
			$('#shortcut-base').rotate({
				angle:0,
				animateTo:360,
				duration:speed
			});
			$('div[id*="shortcut_"]').each(function(){
				$(this).show();
				$(this).animate({
					left:$(this).attr('x')+'px',
					bottom:$(this).attr('y')+'px'
				}, speed);
			});
		}else{
			$('#shortcut-base').attr('isshow', '0');
			$('#shortcut-base').rotate({
				angle:0,
				animateTo:360,
				duration:speed
			});
			$('div[id*="shortcut_"]').each(function(){
				$(this).show();
				$(this).animate({
					left:'10px',
					bottom:'10px'
				}, speed, function(){
					$(this).hide();
				});
			});
		}
	}
	</script>
	<div class="shortcuts" id="shortcuts">
		<div id="shortcut-base" class="shortcut" isshow="0" onclick="showHideShortcuts();">
			<i class="fa fa-chevron-right"></i>
		</div>
	</div>
</c:if>