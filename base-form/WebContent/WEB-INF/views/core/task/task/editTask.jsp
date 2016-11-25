<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 编辑任务</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
	<style type="text/css">
	.template{display:none;}
	.table{margin-bottom:0px;}
	.table th, .table td{text-align:center;}
	.table>thead>tr>th, .table>tbody>tr>th, .table>tfoot>tr>th, .table>thead>tr>td, .table>tbody>tr>td, .table>tfoot>tr>td{padding:4px 8px;}
	</style>
	<script type="text/javascript">
	//定义返回URL
	var goBackUrl = '${pageContext.request.contextPath }/core/task/task';
	//返回
	function goBack(){
		window.location.href = goBackUrl;
	}
	//定义验证对象
	var taskValidator;
	//编辑任务
	function editTask(){
		if(!taskValidator.validResult()){
			return;
		}
		//校验信息模板中的参数个数和参数列表是否能匹配上
		var message = $('#message').val();
		var re = /\{[1-9]{1}[0-9]*\}/g;
		while(arr = re.exec(message)){
			var id = arr.toString().replace('{', '').replace('}', '');
			var attrObj = $('#task_attr_container tr[id*=attr_]')[id-1];
			if(!attrObj){
				sw.toast('参数'+arr+'在参数列表中未找到，请详查。', 'warning', 3000);
				return;
			}else{
				var _id = attrObj.id.split('_')[1];
				var attrSql = $('#attr_sql_'+_id).val();
				if(attrSql==''){
					sw.toast('请录入参数'+arr+'的详细SQL内容。', 'warning', 3000);
					$('#attr_sql_'+_id).focus();
					return;
				}
			}
		}
		//检测SQL列表是否能够匹配上参数，顺带拼接参数
		var taskAttrList = new Array();
		var isPass = true;
		$('#task_attr_container tr[id*=attr_]').each(function(i){
			if(message.indexOf('{'+(i+1)+'}')==-1){
				sw.toast('参数{'+(i+1)+'}在信息提示模板中未找到，请详查。', 'warning', 3000);
				isPass = false;
				return false;
			}
			var id = this.id.split('_')[1];
			var attrSql = $('#attr_sql_'+id).val();
			if(attrSql==''){
				sw.toast('请录入参数{'+(i+1)+'}的详细SQL内容。', 'warning', 3000);
				$('#attr_sql_'+id).focus();
				isPass = false;
				return false;
			}
			var taskAttr = new Object();
			taskAttr.attr_sql = attrSql;
			taskAttr.sort = i;
			taskAttrList.push(taskAttr);
		});
		if(!isPass){
			return;
		}
		//获取用户列表
		var arr = userTree.getCheckedNodes();
		var userIds = '';
		for(var i=0;i<arr.length;i++){
			userIds += arr[i].id + ((i!=arr.length-1)?',':'');
		}
		//获取角色列表
		var arr = roleTree.getCheckedNodes();
		var roleIds = '';
		for(var i=0;i<arr.length;i++){
			roleIds += arr[i].id + ((i!=arr.length-1)?',':'');
		}
		if(!confirm('确定编辑此任务吗？')){
			return;
		}
		var url = '${pageContext.request.contextPath }/core/task/task/edit';
		var task = sw.getFormParams(document.forms[0]);
		task.timing_time = task.timing_time?new String(task.timing_time).toDate():null;
		task.overdue_time = task.overdue_time?new String(task.overdue_time).toDate():null;
		task.taskAttrList = taskAttrList;
		task.userIds = userIds;
		task.roleIds = roleIds;
		sw.ajaxSubmitJSON(url, task, function(data){
			sw.ajaxSuccessCallback(data, function(){
				taskValidator.reset(true);
			});
		});
	}
	//选择权限链接
	function selectLimit(){
		var url='${pageContext.request.contextPath }/core/system/limit/select/radio';
		sw.openModal({
			id : 'selectLimit',
			icon : 'check', 
			title : '选择功能',
			url : url,
			modalType : 'sm',
			callback : function(limits){
				sw.closeModal('selectLimit');
				if(limits&&limits.length>0){
					$('#limit_id').val(limits[0].limit_id);
					$('#limit_name').val(limits[0].limit_name);
				}else{
					$('#limit_id').val('');
					$('#limit_name').val('');
				}
			}
		});
	}
	//新建一行
	var maxId = 0;
	function insertTaskAttr(type, id){
		maxId++;
		var content = '';
		content += '<tr id="attr_'+maxId+'">';
		content += '	<td id="task_sequence_'+maxId+'">'+maxId+'</td>';
		content += '	<td>';
		content += '		'+$('#attr_sql_template').html().replaceAll('replaceId', maxId);
		content += '	</td>';
		content += '	<td>';
		content += '		'+$('#attroperation_template').html().replaceAll('replaceId', maxId);
		content += '	</td>';
		content += '</tr>';
		if(type=='before'){
			jQuery('#attr_'+id).before(content);
			resetSequence();
		}else if(type=='after'){
			jQuery('#attr_'+id).after(content);
			resetSequence();
		}else{
			jQuery('#attrTable').append(content);
			resetSequence();
		}
	}
	//重排编号
	function resetSequence(){
		var i=1;
		$('#task_attr_container tr[id*="attr_"]').each(function(){
			var id = this.id.split('_')[1];
			$('#task_sequence_'+id).html(i);
			i++;
		});
	}
	//上移
	function upTaskAttr(id){
		jQuery('#task_attr_container #attr_'+id).prev('#task_attr_container tr[id*=attr_]').before(jQuery('#task_attr_container #attr_'+id));
		resetSequence();
	}
	//下移
	function downTaskAttr(id){
		jQuery('#task_attr_container #attr_'+id).next('#task_attr_container tr[id*=attr_]').after(jQuery('#task_attr_container #attr_'+id));
		resetSequence();
	}
	//删除
	function deleteTaskAttr(id){
		jQuery('#attr_'+id).remove();
		resetSequence();
	}
	$(function(){
		//初始化表单验证
		taskValidator = $.fn.dlshouwen.validator.init($('#taskForm'));
		//处理复合验证
		isTimingChange();
		$('input[name=is_timing]').bind('change', isTimingChange);
		isNeverOverdueChange();
		$('input[name=is_never_overdue]').bind('change', isNeverOverdueChange);
	});
	//树加载
	var userTree;
	var roleTree;
	var userSetting = {
		view: {
			expandSpeed:'',
			showIcon : false,
			showLine:false,
			addDiyDom : function(treeId, treeNode){
				if(treeNode.id=='top'){
					$('#'+treeNode.tId+'_span').prepend('<i class="fa fa-list"></i>&nbsp;&nbsp;');
				}else{
					$('#'+treeNode.tId+'_span').prepend('<i class="fa fa-user"></i>&nbsp;&nbsp;');
				}
			}
		},
		check: {
			enable: true,
			chkStyle: 'checkbox'
		},
		data: {
			simpleData: {
				enable: true,
				idKey: 'id',
				pIdKey: 'pid'
			}
		}
	};
	var roleSetting = {
		view: {
			expandSpeed:'',
			showIcon : false,
			showLine:false,
			addDiyDom : function(treeId, treeNode){
				if(treeNode.id=='top'){
					$('#'+treeNode.tId+'_span').prepend('<i class="fa fa-list"></i>&nbsp;&nbsp;');
				}else{
					$('#'+treeNode.tId+'_span').prepend('<i class="fa fa-users"></i>&nbsp;&nbsp;');
				}
			}
		},
		check: {
			enable: true,
			chkStyle: 'checkbox'
		},
		data: {
			simpleData: {
				enable: true,
				idKey: 'id',
				pIdKey: 'pid'
			}
		}
	};
	var userTree_treeNodes = ${userTree };
	userTree_treeNodes = userTree_treeNodes==null?[]:userTree_treeNodes;
	var userTreeTopNode = new Object();
	userTreeTopNode.id = 'top';
	userTreeTopNode.pid = '_top';
	userTreeTopNode.name = '用户列表';
	userTree_treeNodes.push(userTreeTopNode);
	var roleTree_treeNodes = ${roleTree };
	roleTree_treeNodes = roleTree_treeNodes==null?[]:roleTree_treeNodes;
	var roleTreeTopNode = new Object();
	roleTreeTopNode.id = 'top';
	roleTreeTopNode.pid = '_top';
	roleTreeTopNode.name = '角色列表';
	roleTree_treeNodes.push(roleTreeTopNode);
	//初始化加载树
	$(function(){
		userTree = $.fn.zTree.init($("#userTree"),userSetting, userTree_treeNodes);
		userTree.expandAll(true);
		roleTree = $.fn.zTree.init($("#roleTree"), roleSetting, roleTree_treeNodes);
		roleTree.expandAll(true);
	});
	//绑定方法
	$(function(){
		$('#edit_task').bind('click', editTask);
		$('#insert').bind('click', function(){
			insertTaskAttr('inner');
		});
	});
	//进入启用：调用各种变更方法及加入默认启用时间为当前
	$(function(){
		//加载参数
		var taskAttrJSON = ${taskAttrList };
		taskAttrJSON = taskAttrJSON==null?[]:taskAttrJSON;
		for(var i=0; i<taskAttrJSON.length; i++){
			insertTaskAttr('inner');
			$('#attr_sql_'+maxId).val(taskAttrJSON[i].attr_sql);
		}
	});
	//定时提醒变更
	function isTimingChange(){
		//获取值
		var is_timing = $('input[name=is_timing]:checked').val();
		//隐藏所有
		$('.is_timing_multiple_valid').hide();
		//处理初始化验证
		$('#timing_time').attr('valid', '');
		//判断符合处理
		if(is_timing=='0'){
			$('#timing_time').attr('valid', '');
			$('#time_space').attr('valid', 'r|integer|l-l10');
			$('.multiple_valid_timing_time').hide();
			$('.multiple_valid_time_space').show();
		}
		if(is_timing=='1'){
			$('#timing_time').attr('valid', 'r');
			$('#time_space').attr('valid', 'integer|l-l10');
			$('.multiple_valid_timing_time').show();
			$('.multiple_valid_time_space').hide();
		}
		taskValidator = $.fn.dlshouwen.validator.init($('#taskForm'));
	}
	//从不过期变更
	function isNeverOverdueChange(){
		//获取值
		var is_never_overdue = $('input[name=is_never_overdue]:checked').val();
		//隐藏所有
		$('.is_never_overdue_multiple_valid').hide();
		//处理初始化验证
		$('#overdue_time').attr('valid', '');
		//判断符合处理
		if(is_never_overdue=='0'){
			$('#overdue_time').attr('valid', 'r');
			$('.multiple_valid_overdue_time').show();
		}
		if(is_never_overdue=='1'){
			
		}
		taskValidator = $.fn.dlshouwen.validator.init($('#taskForm'));
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
			<li><a href="javascript:void(0);"><i class="fa fa-tachometer"></i>任务管理</a></li>
			<li><a href="${pageContext.request.contextPath }/core/task/task"><i class="fa fa-tachometer"></i>任务管理</a></li>
			<li class="active"><a href="javascript:void(0);"><i class="fa fa-edit"></i>编辑任务</a></li>
		</ul>
		<div class="panel panel-default">
			<div class="panel-heading"><i class="fa fa-edit"></i>编辑任务</div>
			<div class="panel-body">
				<form:form cssClass="form-horizontal" id="taskForm" modelAttribute="task" method="post">
					<form:hidden path="task_id"/>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">任务名称：</label>
						<div class="col-sm-4">
							<sw:input path="task_name" cssClass="form-control" placeholder="请输入任务名称" valid="r|l-l40" validTitle="任务名称" validInfoArea="task_name_info_area" />
						</div>
						<div class="col-sm-6"><p class="help-block" id="task_name_info_area"></p></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">启动状态：</label>
						<div class="col-sm-4">
							<sw:radiobuttons path="status" items="${applicationScope.__CODE_TABLE__.open_close }" valid="r" validTitle="启动状态" validInfoArea="status_info_area" />
						</div>
						<div class="col-sm-6"><p class="help-block" id="status_info_area"></p></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">定时提醒：</label>
						<div class="col-sm-4">
							<sw:radiobuttons path="is_timing" items="${applicationScope.__CODE_TABLE__.zero_one }" valid="r" validTitle="定时提醒" validInfoArea="is_timing_info_area" />
						</div>
						<div class="col-sm-6"><p class="help-block" id="is_timing_info_area"></p></div>
					</div>
					<div class="form-group is_timing_multiple_valid multiple_valid_timing_time">
						<label class="col-sm-2 control-label text-right">提醒时间：</label>
						<div class="col-sm-4">
							<div class="input-group">
								<sw:input path="timing_time" cssClass="form-control" placeholder="请输入提醒时间" valid="r" validTitle="提醒时间" 
									validInfoArea="timing_time_info_area" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" />
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>
						<div class="col-sm-6"><p class="help-block" id="timing_time_info_area"></p></div>
					</div>
					<div class="form-group is_timing_multiple_valid multiple_valid_time_space">
						<label class="col-sm-2 control-label text-right">提示间隔：</label>
						<div class="col-sm-4">
							<sw:input path="time_space" cssClass="form-control" placeholder="请输入提示间隔"
								valid="r|integer|l-l10" validTitle="提示间隔" validInfoArea="time_space_info_area" />
						</div>
						<div class="col-sm-6"><p class="help-block" id="time_space_info_area"></p></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">从不过期：</label>
						<div class="col-sm-4">
							<sw:radiobuttons path="is_never_overdue" items="${applicationScope.__CODE_TABLE__.zero_one }" valid="r" validTitle="从不过期" validInfoArea="is_never_overdue_info_area" />
						</div>
						<div class="col-sm-6"><p class="help-block" id="is_never_overdue_info_area"></p></div>
					</div>
					<div class="form-group is_never_overdue_multiple_valid multiple_valid_overdue_time">
						<label class="col-sm-2 control-label text-right">过期时间：</label>
						<div class="col-sm-4">
							<div class="input-group">
								<sw:input path="overdue_time" cssClass="form-control" placeholder="请输入过期时间" valid="r" validTitle="过期时间" 
									validInfoArea="overdue_time_info_area" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" />
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>
						<div class="col-sm-6"><p class="help-block" id="overdue_time_info_area"></p></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">信息提示模板：</label>
						<div class="col-sm-4">
							<sw:input path="message" cssClass="form-control" placeholder="请输入信息提示模板" valid="r|l-l2000" validTitle="信息提示模板" 
								validInfoArea="message_info_area" />
						</div>
						<div class="col-sm-6"><p class="help-block" id="message_info_area"></p></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">响应功能点：</label>
						<div class="col-sm-4">
							<form:hidden path="limit_id" />
							<div class="input-group">
								<sw:input path="limit_name" cssClass="form-control" placeholder="请输入响应功能点" valid="r" validTitle="响应功能点" validInfoArea="limit_name_info_area"
									readonly="true" onclick="selectLimit();" />
								<span class="input-group-addon" onclick="selectLimit();"><i class="fa fa-cubes"></i></span>
							</div>
						</div>
						<div class="col-sm-6"><p class="help-block" id="limit_name_info_area"></p></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">触发脚本：</label>
						<div class="col-sm-4">
							<sw:input path="detonate_sql" cssClass="form-control" placeholder="请输入触发脚本" valid="r|l-l2000" validTitle="触发脚本" 
								validInfoArea="detonate_sql_info_area" />
						</div>
						<div class="col-sm-6"><p class="help-block" id="detonate_sql_info_area"></p></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">全用户触发：</label>
						<div class="col-sm-4">
							<sw:radiobuttons path="is_all_user" items="${applicationScope.__CODE_TABLE__.zero_one }" valid="r" validTitle="全用户触发" validInfoArea="is_all_user_info_area" />
						</div>
						<div class="col-sm-6"><p class="help-block" id="is_all_user_info_area"></p></div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<sw:button accesskey="S" id="edit_task" cssClass="btn btn-success" value="保存 (S)" icon="save" limit="core/task/task/-/edit" />
							<sw:button accesskey="A" id="insert" cssClass="btn btn-primary" value="添加参数 (A)" icon="plus" />
							<button accesskey="B" type="button" class="btn btn-default" onclick="goBack();">
								<i class="fa fa-arrow-circle-left"></i>返回 (B)
							</button>
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading"><i class="fa fa-table"></i>参数列表</div>
			<div class="panel-body" id="task_attr_container" style="padding:0px;">
				<table id="attrTable" class="table table-bordered table-hover table-responsive">
					<tr>
						<th style="width:80px;">序号</th>
						<th>参数SQL</th>
						<th style="width:200px;">操作</th>
					</tr>
				</table>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-6">
				<div class="panel panel-default">
					<div class="panel-heading"><i class="fa fa-table"></i>触发用户</div>
					<div class="panel-body" style="padding:0px;">
						<ul id="userTree" class="ztree"></ul>
					</div>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="panel panel-default">
					<div class="panel-heading"><i class="fa fa-table"></i>触发角色</div>
					<div class="panel-body" style="padding:0px;">
						<ul id="roleTree" class="ztree"></ul>
					</div>
				</div>
			</div>
		</div>
		<div class="clearfix"></div>
	</div>
	<div class="template" id="attr_sql_template">
		<input type="text" id="attr_sql_replaceId" name="attr_sql_replaceId" class="form-control" placeholder="请输入参数SQL" />
	</div>
	<div class="template" id="attroperation_template">
		<sw:button cssClass="btn btn-primary btn-icon" title="上方插入" icon="hand-o-up" onclick="insertTaskAttr('before', 'replaceId');" />
		<sw:button cssClass="btn btn-primary btn-icon" title="下方插入" icon="hand-o-down" onclick="insertTaskAttr('after', 'replaceId');" />
		<sw:button cssClass="btn btn-warning btn-icon" title="上移" icon="arrow-up" onclick="upTaskAttr('replaceId');" />
		<sw:button cssClass="btn btn-warning btn-icon" title="下移" icon="arrow-down" onclick="downTaskAttr('replaceId');" />
		<sw:button cssClass="btn btn-danger btn-icon" title="删除" icon="trash-o" onclick="deleteTaskAttr('replaceId');" />
	</div>
</body>
</html>
