<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 网页截图</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
	<style type="text/css">
	.template{display:none;}
	
	#left_container{float:left;width:60%;box-sizing:border-box;}
	#right_container{float:right;width:40%;box-sizing:border-box;}
	
	#screen_attr_list_container{overflow:auto;}
	
	#screenAttrList{margin:0;}
	#screenAttrList tr>th, #screenAttrList tr>td{text-align:center;}
	#screenAttrList .form-control{width:100%;padding:1px 3px;height:22px;}
	</style>
	<script type="text/javascript">
	var isSuccess = '${isSuccess }';
	var errorMessage = '${errorMessage }';
	var screenAttrList;
	try{
		screenAttrList = $.parseJSON('${screenAttrList}');
	}catch(e){
		screenAttrList = null;
	}
	$(function(){
		//默认新增一个屏幕参数
		insertScreenAttr();
		//判断提示信息
		if(isSuccess&&isSuccess=='false'){
			sw.toast(errorMessage, 'warning', 3000);
		}
		//如果成功则进行加载
		if(isSuccess&&isSuccess=='true'){
			clearScreenAttr();
			nowScreenAttrId = 0;
			for(var i=0; i<screenAttrList.length; i++){
				var screenAttr = screenAttrList[i];
				insertScreenAttr();
				$('#screen_attr_name_'+nowScreenAttrId).val(screenAttr.name);
				$('#screen_attr_url_'+nowScreenAttrId).val(screenAttr.url);
				$('#screen_attr_width_'+nowScreenAttrId).val(screenAttr.width);
				$('#screen_attr_height_'+nowScreenAttrId).val(screenAttr.height);
			}
		}
	});
	//定义当前屏幕编号
	var nowScreenAttrId = 0;
	//新增一个屏幕参数
	function insertScreenAttr(){
		nowScreenAttrId++;
		var content = '';
		content += '<tr id="screen_attr_'+nowScreenAttrId+'">';
		content += '	<td id="screen_attr_sequence_'+nowScreenAttrId+'" class="screen-attr-sequence"></td>';
		content += '	<td class="screen-attr-operation">'+$('#screen_attr_operation_template').html().replaceAll('replaceId', nowScreenAttrId)+'</td>';
		content += '	<td>'+$('#screen_attr_name_template').html().replaceAll('replaceId', nowScreenAttrId)+'</td>';
		content += '	<td>'+$('#screen_attr_url_template').html().replaceAll('replaceId', nowScreenAttrId)+'</td>';
		content += '	<td>'+$('#screen_attr_width_template').html().replaceAll('replaceId', nowScreenAttrId)+'</td>';
		content += '	<td>'+$('#screen_attr_height_template').html().replaceAll('replaceId', nowScreenAttrId)+'</td>';
		content += '</tr>';
		$('#screenAttrList').append(content);
		resetScreenAttrSequence();
	}
	//上移屏幕
	function upScreenAttr(screenId){
		$('#screenAttrList #screen_attr_'+screenId).prev('#screenAttrList tr[id*=screen_attr_]').before($('#screenAttrList #screen_attr_'+screenId));
		resetScreenAttrSequence();
	}
	//下移屏幕
	function downScreenAttr(screenId){
		$('#screenAttrList #screen_attr_'+screenId).next('#screenAttrList tr[id*=screen_attr_]').after($('#screenAttrList #screen_attr_'+screenId));
		resetScreenAttrSequence();
	}
	//删除屏幕
	function deleteScreenAttr(screenId){
		$('#screenAttrList #screen_attr_'+screenId).remove();
		resetScreenAttrSequence();
	}
	//清空屏幕
	function clearScreenAttr(){
		$('#screenAttrList tr[id*=screen_attr_]').remove();
	}
	//重排编号
	function resetScreenAttrSequence(){
		$('#screenAttrList tr[id*=screen_attr_]').each(function(i){
			var screenId = this.id.split('_')[2];
			$('#screen_attr_sequence_'+screenId).html(i+1);
		});
	}
	//导出配置
	function exportScreenAttr(){
		if($('#screenAttrList tr[id*=screen_attr_]').length<=0){
			sw.toast('您未配置任何内容。', 'warning', 3000);
			return;
		}
		var params = '';
		var isPass = true;
		$('#screenAttrList tr[id*=screen_attr_]').each(function(){
			var screenId = this.id.split('_')[2];
			var sequence = $('#screen_attr_sequence_'+screenId).html();
			var name = $('#screen_attr_name_'+screenId).val();
			var url = $('#screen_attr_url_'+screenId).val();
			var width = $('#screen_attr_width_'+screenId).val();
			var height = $('#screen_attr_height_'+screenId).val();
			if(!name||name==''){
				sw.toast('序号为'+sequence+'的名称未填写，请填写。', 'warning', 3000);
				$('#screen_attr_name_'+screenId).focus();
				isPass = false;
				return false;
			}
			if(!url||url==''){
				sw.toast('序号为'+sequence+'的地址未填写，请填写。', 'warning', 3000);
				$('#screen_attr_url_'+screenId).focus();
				isPass = false;
				return false;
			}
			if(!width||width==''){
				sw.toast('序号为'+sequence+'的宽度未填写，请填写。', 'warning', 3000);
				$('#screen_attr_width_'+screenId).focus();
				isPass = false;
				return false;
			}
			if(!height||height==''){
				sw.toast('序号为'+sequence+'的高度未填写，请填写。', 'warning', 3000);
				$('#screen_attr_height_'+screenId).focus();
				isPass = false;
				return false;
			}
			params += sequence+'\t'+name+'\t'+url+'\t'+width+'\t'+height+'\n';
		});
		if(!isPass){
			return;
		}
		$('#exportScreenAttrParams').val(params);
		document.forms['exportScreenAttrForm'].submit();
	}
	//导入配置
	function importScreenAttr(){
		var fileUrl = $('#importScreenAttrFile').val();
		if(!fileUrl||fileUrl==null||fileUrl==''){
			sw.toast('请选择需要导入的配置文件。', 'warning', 3000);
			return;
		}
		document.forms['importScreenAttrForm'].submit();
	}
	//获取高度
	function generatePageHeight(){
		if($('#screenAttrList tr[id*=screen_attr_]').length<=0){
			sw.toast('您未配置任何内容。', 'warning', 3000);
			return;
		}
		screenAttrList = new Array();
		var isPass = true;
		$('#screenAttrList tr[id*=screen_attr_]').each(function(){
			var screenId = this.id.split('_')[2];
			var sequence = $('#screen_attr_sequence_'+screenId).html();
			var name = $('#screen_attr_name_'+screenId).val();
			var url = $('#screen_attr_url_'+screenId).val();
			var width = $('#screen_attr_width_'+screenId).val();
			var height = $('#screen_attr_height_'+screenId).val();
			if(!name||name==''){
				sw.toast('序号为'+sequence+'的名称未填写，请填写。', 'warning', 3000);
				$('#screen_attr_name_'+screenId).focus();
				isPass = false;
				return false;
			}
			if(!url||url==''){
				sw.toast('序号为'+sequence+'的地址未填写，请填写。', 'warning', 3000);
				$('#screen_attr_url_'+screenId).focus();
				isPass = false;
				return false;
			}
			if(!width||width==''){
				sw.toast('序号为'+sequence+'的宽度未填写，请填写。', 'warning', 3000);
				$('#screen_attr_width_'+screenId).focus();
				isPass = false;
				return false;
			}
			if(!height||height==''){
				sw.toast('序号为'+sequence+'的高度未填写，请填写。', 'warning', 3000);
				$('#screen_attr_height_'+screenId).focus();
				isPass = false;
				return false;
			}
			var screenAttr = new Object();
			screenAttr.screenId = screenId;
			screenAttr.sequence = sequence;
			screenAttr.name = name;
			screenAttr.url = url;
			screenAttr.width = width;
			screenAttr.height = height;
			screenAttrList.push(screenAttr);
		});
		if(!isPass){
			return;
		}
		sw.showProcessBar('正在计算高度，请稍后...');
		generatePageHeightIterator(0);
	}
	function generatePageHeightIterator(i){
		if(!screenAttrList[i]){
			sw.hideProcessBar();
			return;
		}
		sw.hideProcessBar();
		sw.showProcessBar('正在计算第 '+(i+1)+' 个页面的高度，请稍后...');
		var screenAttr = screenAttrList[i];
		$('#heightFrame').attr('width', screenAttr.width+'px');
		$('#heightFrame').attr('height', screenAttr.height+'px');
		$('#heightFrame').attr('src', screenAttr.url);
		var iframe = document.getElementById("heightFrame");
		iframe.onload = iframe.onreadystatechange = function(){
			setTimeout(function(){
				if (!iframe.readyState || iframe.readyState == "complete") {
					$('#screen_attr_height_'+screenAttr.screenId).val(reinitIframe('heightFrame', screenAttr.height));
					generatePageHeightIterator(i+1);
				}
			}, 5000);
		};
	}
	//重设Iframe高度
	function reinitIframe(iframeId, minHeight) {
		var isOpera = false, isFireFox = false, isChrome = false, isSafari = false, isIE = false;
		var browserVersion = window.navigator.userAgent.toUpperCase();
		isOpera = browserVersion.indexOf("OPERA") > -1 ? true : false;
		isFireFox = browserVersion.indexOf("FIREFOX") > -1 ? true : false;
		isChrome = browserVersion.indexOf("CHROME") > -1 ? true : false;
		isSafari = browserVersion.indexOf("SAFARI") > -1 ? true : false;
		if (!!window.ActiveXObject || "ActiveXObject" in window)
			isIE = true;
	    try {
			var iframe = document.getElementById(iframeId);
			var bHeight = 0;
			if (isChrome == false && isSafari == false)
				bHeight = iframe.contentWindow.document.body.scrollHeight;
			var dHeight = 0;
			if (isFireFox == true)
				dHeight = iframe.contentWindow.document.documentElement.offsetHeight + 2;
			else if (isIE == false && isOpera == false)
				dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
			else if (isIE == true && ! -[1, ] == false) { }
			else
				bHeight += 3;
			var height = Math.max(bHeight, dHeight);
			if (height < minHeight) height = minHeight;
			iframe.style.height = height + "px";
			return height;
	    } catch (ex) {
	    	return minHeight;
	    }
	}
	//生成页面
	function generateScreenPage(){
		if($('#screenAttrList tr[id*=screen_attr_]').length<=0){
			sw.toast('您未配置任何内容。', 'warning', 3000);
			return;
		}
		screenAttrList = new Array();
		var isPass = true;
		$('#screenAttrList tr[id*=screen_attr_]').each(function(){
			var screenId = this.id.split('_')[2];
			var sequence = $('#screen_attr_sequence_'+screenId).html();
			var name = $('#screen_attr_name_'+screenId).val();
			var url = $('#screen_attr_url_'+screenId).val();
			var width = $('#screen_attr_width_'+screenId).val();
			var height = $('#screen_attr_height_'+screenId).val();
			if(!name||name==''){
				sw.toast('序号为'+sequence+'的名称未填写，请填写。', 'warning', 3000);
				$('#screen_attr_name_'+screenId).focus();
				isPass = false;
				return false;
			}
			if(!url||url==''){
				sw.toast('序号为'+sequence+'的地址未填写，请填写。', 'warning', 3000);
				$('#screen_attr_url_'+screenId).focus();
				isPass = false;
				return false;
			}
			if(!width||width==''){
				sw.toast('序号为'+sequence+'的宽度未填写，请填写。', 'warning', 3000);
				$('#screen_attr_width_'+screenId).focus();
				isPass = false;
				return false;
			}
			if(!height||height==''){
				sw.toast('序号为'+sequence+'的高度未填写，请填写。', 'warning', 3000);
				$('#screen_attr_height_'+screenId).focus();
				isPass = false;
				return false;
			}
			var screenAttr = new Object();
			screenAttr.sequence = sequence;
			screenAttr.name = name;
			screenAttr.url = url;
			screenAttr.width = width;
			screenAttr.height = height;
			screenAttrList.push(screenAttr);
		});
		if(!isPass){
			return;
		}
		window.open('${pageContext.request.contextPath }/core/tools/screenshot/generate');
	}
	//生成图片
	function generatePicture(){
		if($('#screenAttrList tr[id*=screen_attr_]').length<=0){
			sw.toast('您未配置任何内容。', 'warning', 3000);
			return;
		}
		screenAttrList = new Array();
		var isPass = true;
		$('#screenAttrList tr[id*=screen_attr_]').each(function(){
			var screenId = this.id.split('_')[2];
			var sequence = $('#screen_attr_sequence_'+screenId).html();
			var name = $('#screen_attr_name_'+screenId).val();
			var url = $('#screen_attr_url_'+screenId).val();
			var width = $('#screen_attr_width_'+screenId).val();
			var height = $('#screen_attr_height_'+screenId).val();
			if(!name||name==''){
				sw.toast('序号为'+sequence+'的名称未填写，请填写。', 'warning', 3000);
				$('#screen_attr_name_'+screenId).focus();
				isPass = false;
				return false;
			}
			if(!url||url==''){
				sw.toast('序号为'+sequence+'的地址未填写，请填写。', 'warning', 3000);
				$('#screen_attr_url_'+screenId).focus();
				isPass = false;
				return false;
			}
			if(!width||width==''){
				sw.toast('序号为'+sequence+'的宽度未填写，请填写。', 'warning', 3000);
				$('#screen_attr_width_'+screenId).focus();
				isPass = false;
				return false;
			}
			if(!height||height==''){
				sw.toast('序号为'+sequence+'的高度未填写，请填写。', 'warning', 3000);
				$('#screen_attr_height_'+screenId).focus();
				isPass = false;
				return false;
			}
			var screenAttr = new Object();
			screenAttr.sequence = sequence;
			screenAttr.name = name;
			screenAttr.url = url;
			screenAttr.width = width;
			screenAttr.height = height;
			screenAttrList.push(screenAttr);
		});
		if(!isPass){
			return;
		}
		var fileUrl = $('#pictureFile').val();
		if(!fileUrl||fileUrl==null||fileUrl==''){
			sw.toast('请选择需要裁剪的图片。', 'warning', 3000);
			return;
		}
		if(!fileUrl.toLowerCase().endsWith('.png')){
			sw.toast('图片仅支持png格式。', 'warning', 3000);
			return;
		}
		$('#pictureParams').val(JSON.stringify(screenAttrList));
		document.forms['generatePictureForm'].submit();
	}
	//加载Session页面
	function loadSessionPage(){
		var sessionUrl = $('#session_url').val();
		$('#sessionFrame').attr('src', sessionUrl);
	}
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
			<li class="active"><a href="javascript:void(0);"><i class="fa fa-camera"></i>网页截图</a></li>
		</ul>
		<div class="row">
			<div class="col-md-6">
				<div class="panel panel-default">
					<div class="panel-heading"><i class="fa fa-gear"></i>截图参数配置</div>
					<div class="panel-operation">
						<div class="btn-group">
							<sw:button accesskey="X" value="导出配置 (X)" cssClass="btn btn-xs btn-default" icon="level-up" onclick="exportScreenAttr();" />
							<sw:button accesskey="I" value="导入配置 (I)" cssClass="btn btn-xs btn-default" icon="level-down" onclick="importScreenAttr();" />
							<sw:button accesskey="H" value="获取高度 (H)" cssClass="btn btn-xs btn-default" icon="square" onclick="generatePageHeight();" />
							<sw:button accesskey="P" value="生成页面 (P)" cssClass="btn btn-xs btn-default" icon="mail-forward" onclick="generateScreenPage();" />
							<sw:button accesskey="A" value="新增一行 (A)" cssClass="btn btn-xs btn-primary" icon="plus" onclick="insertScreenAttr();" />
							<sw:button accesskey="C" value="清空全部 (C)" cssClass="btn btn-xs btn-danger" icon="trash-o" onclick="clearScreenAttr();" />
						</div>
					</div>
					<div id="export_screen_attr_container" class="panel-operation" style="display:none;">
						<form id="exportScreenAttrForm" action="${pageContext.request.contextPath }/core/tools/screenshot/attr/export" method="post">
							<textarea id="exportScreenAttrParams" name="exportScreenAttrParams"></textarea>
						</form>
					</div>
					<div id="import_screen_attr_container" class="panel-operation">
						<form id="importScreenAttrForm" action="${pageContext.request.contextPath }/core/tools/screenshot/attr/import" method="post" enctype="multipart/form-data">
							<input id="importScreenAttrFile" name="importScreenAttrFile" type="file" style="line-height:18px;" />
						</form>
					</div>
					<div id="screen_attr_list_container" class="panel-body" style="height:400px;">
						<table id="screenAttrList" class="table table-bordered table-striped table-hover" style="width:800px;">
							<tr>
								<th style="width:40px;">序号</th>
								<th style="width:90px;">操作</th>
								<th style="width:120px;">文件名</th>
								<th>地址</th>
								<th style="width:80px;">宽度</th>
								<th style="width:80px;">高度</th>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="panel panel-default">
					<div class="panel-heading"><i class="fa fa-file-zip-o"></i>生成图片</div>
					<div class="panel-operation">
						<div class="btn-group">
							<sw:button accesskey="G" value="导入图片并生成文件包 (G)" cssClass="btn btn-xs btn-success" icon="download" onclick="generatePicture();" />
						</div>
					</div>
					<div id="import_picture_container" class="panel-body">
						<form id="generatePictureForm" action="${pageContext.request.contextPath }/core/tools/screenshot/picture/generate" method="post" enctype="multipart/form-data">
							<input id="pictureParams" name="pictureParams" type="hidden" />
							<input id="pictureFile" name="pictureFile" type="file" style="line-height:18px;" />
						</form>
					</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading"><i class="fa fa-user"></i>页面SESSION授权</div>
					<div class="panel-operation">
							<div class="input-group">
								<input id="session_url" name="session_url" class="form-control" value="about:blank" />
								<span accesskey="L" class="input-group-addon" onclick="loadSessionPage();"><i class="fa fa-arrow-right"></i>加载 (L)</span>
							</div>
					</div>
					<div class="panel-body" style="padding:0;">
						<iframe id="sessionFrame" name="sessionFrame" src="about:blank" style="float:left;height:315px;width:100%;border:0;overflow:auto;"></iframe>
					</div>
				</div>
			</div>
		</div>
		<div class="clearfix"></div>
	</div>
	<div id="height_frame_container" style="position:absolute;top:-10000px;left:-10000px;">
		<iframe id="heightFrame" name="heightFrame" src="about:blank"></iframe>
	</div>
	<div class="template" id="screen_attr_name_template">
		<input type="text" class="form-control" id="screen_attr_name_replaceId" name="screen_attr_name_replaceId" />
	</div>
	<div class="template" id="screen_attr_url_template">
		<input type="text" class="form-control" id="screen_attr_url_replaceId" name="screen_attr_url_replaceId" />
	</div>
	<div class="template" id="screen_attr_width_template">
		<input type="text" class="form-control" id="screen_attr_width_replaceId" name="screen_attr_width_replaceId" />
	</div>
	<div class="template" id="screen_attr_height_template">
		<input type="text" class="form-control" id="screen_attr_height_replaceId" name="screen_attr_height_replaceId" />
	</div>
	<div class="template" id="screen_attr_operation_template">
		<button class="btn btn-xs btn-icon btn-default" onclick="upScreenAttr('replaceId');"><i class="fa fa-chevron-circle-up"></i></button>
		<button class="btn btn-xs btn-icon btn-default" onclick="downScreenAttr('replaceId');"><i class="fa fa-chevron-circle-down"></i></button>
		<button class="btn btn-xs btn-icon btn-default" onclick="deleteScreenAttr('replaceId');"><i class="fa fa-times"></i></button>
	</div>
</body>
</html>