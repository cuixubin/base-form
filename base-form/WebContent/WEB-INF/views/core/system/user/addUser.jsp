<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 新增用户</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
        <style>
            .picImg img{border:2px solid gray;width:146px;height:207px;}
            .imgSetA{margin-left: 10px; margin-top: 5px;}
        </style>
	<script type="text/javascript">
	//定义返回URL
	var goBackUrl = '${pageContext.request.contextPath }/core/system/user';
	//返回
	function goBack(){
		sw.showProcessBar();
		window.location.href = goBackUrl;
	}
	//新增
	var userValidator;
	function addUser(){
		if(!userValidator.validResult()){
			return;
		}
		if(!confirm('确定添加此用户吗？')){
			return;
		}
		var url = '${pageContext.request.contextPath }/core/system/user/add';
                var params = $("#userForm").serializeArray();
                params.push({"name": "imgpath", "value": $("input[name=picture]").val()});
		sw.showProcessBar();
		sw.ajaxSubmitForForm(url, params, "picture", function(data){
			sw.ajaxSuccessCallback(data, function(){
				userValidator.reset(true);
			});
		});
	}
	$(function(){
		userValidator = $.fn.dlshouwen.validator.init($('#userForm'));
	});
	$(function(){
		$('#add_user').bind('click', addUser);
                $("#picImg").html('<img src="${pageContext.request.contextPath }/image/default_user_img.jpg" alt="个人照片">');
	});
        //验证图片  
        function preview(file) {
            if (/.+\.(jpg|JPG|jpeg|JPEG|bmp|BMP|png|PNG|gif|GIF)/g.test(document.getElementById('picture').value)) {
                previewImage(146, 207, file, "picImg");

            } else {
                alert('请选择正确的图片格式（jpg,jpeg,bmp,png,gif）');
                document.getElementById('picture').value = '';
                return false;
            }
        }
        //预览图片
        function previewImage(MAXWIDTH, MAXHEIGHT, file, divId) {
            var div = document.getElementById(divId);
            if (file.files && file.files[0])
            {
                div.innerHTML = '<img id=' + divId + 'imghead >';
                var img = document.getElementById(divId + 'imghead');
                img.onload = function () {
                    var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
                    img.width = rect.width;
                    img.height = rect.height;
                    img.style.marginLeft = '0px';
                    img.style.marginTop = '5px';
                }
                var reader = new FileReader();
                reader.onload = function (evt) {
                    img.src = evt.target.result;
                }
                reader.readAsDataURL(file.files[0]);
            } else
            {
                file.select();
                    div.focus();//file.blur();  
                    var src = document.selection.createRange().text;
                    div.innerHTML = "<div id=divhead style=\"FILTER:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);width:160px; height:160px\"></div>";
                    var newPreview = document.getElementById("divhead");            
                    newPreview.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = src;  
            }
        }
        /*
        * 设置图片大小宽高
        */
        function clacImgZoomParam(maxWidth, maxHeight, width, height) {
            var param = {top: 0, left: 0, width: width, height: height};
            if (width > maxWidth || height > maxHeight)
            {
                rateWidth = width / maxWidth;
                rateHeight = height / maxHeight;

                if (rateWidth > rateHeight)
                {
                    param.width = maxWidth;
                    param.height = Math.round(height / rateWidth);
                } else
                {
                    param.width = Math.round(width / rateHeight);
                    param.height = maxHeight;
               }
           }

           param.left = Math.round((maxWidth - param.width) / 2);
           param.top = Math.round((maxHeight - param.height) / 2);
           return param;
        }
	//选择机构
	function selectDept(){
		var url = '${pageContext.request.contextPath }/core/system/dept/select/radio';
		sw.openModal({
			id : 'selectDept',
			icon : 'building', 
			title : '选择机构',
			url : url,
			callback : function(depts){
				var deptId = '';
				var deptName = '';
				if(depts[0]){
					deptId = depts[0].dept_id;
					deptName = depts[0].dept_name;
				}
				$('#dept_id').val(deptId);
				$('#dept_name').val(deptName);
				sw.closeModal('selectDept');
			}
		});
	}
        
	//选择团队
	function selectTeam(){
		var url = '${pageContext.request.contextPath }/core/team/select/radio';
		sw.openModal({
			id : 'selectTeam',
			icon : 'building', 
			title : '选择团队',
			url : url,
			callback : function(teams){
				var teamId = '';
				var teamName = '';
				if(teams[0]){
					teamId = teams[0].team_id;
					teamName = teams[0].team_name;
				}
				$('#team_id').val(teamId);
				$('#team_name').val(teamName);
				sw.closeModal('selectTeam');
			}
		});
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
			<li><a href="javascript:void(0);"><i class="fa fa-gears"></i>基础管理</a></li>
			<li><a href="${pageContext.request.contextPath }/core/system/user"><i class="fa fa-user"></i>用户管理</a></li>
			<li class="active"><a href="javascript:void(0);"><i class="fa fa-plus"></i>新增用户</a></li>
		</ul>
		<div class="panel panel-default">
			<div class="panel-heading"><i class="fa fa-plus"></i>新增用户</div>
			<div class="panel-body">
				<form:form cssClass="form-horizontal" id="userForm" modelAttribute="user" method="post">
                                    <div class="form-group"> 
                                    <div class="col-sm-9">
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">用户编号：</label>
						<div class="col-sm-4">
							<sw:input path="user_code" cssClass="form-control" placeholder="请输入用户编号" valid="r|english_number|3-l20" 
								validTitle="用户编号" validInfoArea="user_code_info_area" validUnique="USER_CODE_UNIQUE_FOR_ADD" />
						</div>
						<div class="col-sm-3"><p class="help-block" id="user_code_info_area"></p></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">用户名称：</label>
						<div class="col-sm-4">
							<sw:input path="user_name" cssClass="form-control" placeholder="请输入用户名称" 
								valid="r|l-l40" validTitle="用户名称" validInfoArea="user_name_info_area" />
						</div>
						<div class="col-sm-3"><p class="help-block" id="user_name_info_area"></p></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">有效标识：</label>
						<div class="col-sm-4">
							<sw:radiobuttons path="valid_type" items="${applicationScope.__CODE_TABLE__.valid_type }" 
								valid=""  validInfoArea="valid_type_info_area" />
						</div>
						<div class="col-sm-3"></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">用户身份：</label>
						<div class="col-sm-4">
							<sw:radiobuttons path="identity" items="${applicationScope.__CODE_TABLE__.user_identity }" 
								valid=""  validInfoArea="identity_info_area" />
						</div>
						<div class="col-sm-3"></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">所属部门：</label>
						<div class="col-sm-4">
							<div class="input-group">
								<form:hidden path="dept_id"/>
								<sw:input path="dept_name" cssClass="form-control" readonly="true" 
									valid="" validInfoArea="dept_id_info_area" onclick="selectDept('dept_id', 'dept_name');"/>
								<span class="input-group-addon" onclick="selectDept('dept_id', 'dept_name');"><i class="fa fa-building"></i></span>
							</div>
						</div>
						<div class="col-sm-3"></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">所属团队：</label>
						<div class="col-sm-4">
							<div class="input-group">
								<form:hidden path="team_id"/>
								<sw:input path="team_name" cssClass="form-control" readonly="true" 
									valid="" validInfoArea="team_id_info_area" onclick="selectTeam('team_id', 'team_name');"/>
								<span class="input-group-addon" onclick="selectTeam('team_id', 'team_name');"><i class="fa fa-group"></i></span>
							</div>
						</div>
						<div class="col-sm-3"></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">性别：</label>
						<div class="col-sm-4">
							<sw:radiobuttons path="sex" items="${applicationScope.__CODE_TABLE__.sex }" 
								valid="" validInfoArea="sex_info_area" />
						</div>
						<div class="col-sm-3"></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">证件类型：</label>
						<div class="col-sm-4">
							<sw:select path="card_type" items="${applicationScope.__CODE_TABLE__.card_type }" 
								emptyText="请选择..." cssClass="form-control" valid="r" validTitle="证件类型" validInfoArea="card_type_info_area" />
						</div>
						<div class="col-sm-3"><p class="help-block" id="card_type_info_area"></p></div>
					</div>
                                        
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">证件号码：</label>
						<div class="col-sm-4">
							<sw:input path="card_id" cssClass="form-control" placeholder="请输入证件号码" valid="r|english_number|l-l30" validTitle="证件号码" validInfoArea="card_id_info_area" />
						</div>
						<div class="col-sm-3"><p class="help-block" id="card_id_info_area"></p></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">出生日期：</label>
						<div class="col-sm-4">
							<div class="input-group">
								<sw:input path="birthday" cssClass="form-control" placeholder="请输入出生日期" valid="" validInfoArea="birthday_info_area" onclick="WdatePicker();" />
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>
						<div class="col-sm-3"></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">工作日期：</label>
						<div class="col-sm-4">
							<div class="input-group">
								<sw:input path="work_date" cssClass="form-control" placeholder="请输入工作日期" valid="" validInfoArea="work_date_info_area" onclick="WdatePicker();" />
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>
						<div class="col-sm-3"></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">民族：</label>
						<div class="col-sm-4">
							<sw:select path="folk" items="${applicationScope.__CODE_TABLE__.folk }" 
								emptyText="请选择..." valid="" cssClass="form-control" validInfoArea="folk_info_area" />
						</div>
						<div class="col-sm-3"></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">学历：</label>
						<div class="col-sm-4">
							<sw:select path="degree" items="${applicationScope.__CODE_TABLE__.degree }" 
								emptyText="请选择..." valid="" cssClass="form-control" validInfoArea="degree_info_area" />
						</div>
						<div class="col-sm-3"></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">教师资质：</label>
						<div class="col-sm-4">
							<sw:select path="qualified" items="${applicationScope.__CODE_TABLE__.jszz }" 
								emptyText="请选择..." valid="" cssClass="form-control" validInfoArea="qualified_info_area" />
						</div>
						<div class="col-sm-3"></div>
					</div>
                                        <div class="form-group">
						<label class="col-sm-2 control-label text-right">毕业院校：</label>
						<div class="col-sm-4">
							<sw:input path="graduateSchool" cssClass="form-control" placeholder="请输入毕业院校信息" valid="l-40" validInfoArea="grdSchool_info_area" />
						</div>
						<div class="col-sm-3"></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">联系电话：</label>
						<div class="col-sm-4">
							<sw:input path="phone" cssClass="form-control" placeholder="请输入联系电话" valid="r|phone|5-30" validTitle="联系电话" validInfoArea="phone_info_area" />
						</div>
						<div class="col-sm-3"><p class="help-block" id="phone_info_area"></p></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">E-mail：</label>
						<div class="col-sm-4">
							<sw:input path="email" cssClass="form-control" placeholder="请输入E-mail" valid="r|email|6-30" validTitle="E-mail" validInfoArea="email_info_area" />
						</div>
						<div class="col-sm-3"><p class="help-block" id="email_info_area"></p></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">联系地址：</label>
						<div class="col-sm-4">
							<sw:textarea path="address" cssClass="form-control" placeholder="请输入联系地址" valid="l-200" validInfoArea="address_info_area" />
						</div>
						<div class="col-sm-3"></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">备注：</label>
						<div class="col-sm-4">
							<sw:textarea path="remark" cssClass="form-control" placeholder="请输入备注" valid="l-l200" validInfoArea="remark_info_area" />
						</div>
						<div class="col-sm-3"></div>
					</div>
                                    </div>
                                    <!-- 个人照片 -->          
                                    <div class="col-sm-3">
                                        <div class="imgContent">
                                            <div class="picImg" id="picImg">
                                                
                                            </div>
                                            <div class="imgSetA">
                                                <input id="picture" name="picture" type="file" onchange="preview(this)"/>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    </div>
                                    <div class="form-group">
                                            <div class="col-sm-offset-2 col-sm-10">
                                                    <sw:button accesskey="S" id="add_user" cssClass="btn btn-success" value="保存 (S)" icon="save" limit="core/system/user/add" />
                                                    <button accesskey="B" type="button" class="btn btn-default" onclick="goBack();">
                                                            <i class="fa fa-arrow-circle-left"></i>返回 (B)
                                                    </button>
                                            </div>
                                    </div>
				</form:form>
			</div>
		</div>
		<div class="clearfix"></div>
	</div>
</body>
</html>
