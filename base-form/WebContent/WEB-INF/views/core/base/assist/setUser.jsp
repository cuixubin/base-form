<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<head>
	<title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 设置个人信息</title>
	<base target="_self" />
	<%@ include file="/resources/include/plugin.jsp"%>
	<%@ include file="/resources/include/extra.jsp"%>
        <style>
            .picImg img{border:2px solid gray;width:146px;height:207px;}
            .imgSetA{margin-left: 10px; margin-top: 5px;}
        </style>
	<script type="text/javascript">
	//定义验证对象
	var userValidator;
	//执行保存用户信息
	function setUser(){
		if(!userValidator.validResult()){
			return;
		}
		if(!confirm('确定保存用户信息吗？')){
			return;
		}
		var url = '${pageContext.request.contextPath }/core/base/assist/set_user';
		var params = $("#userForm").serializeArray();
                params.push({"name": "imgpath", "value": $("input[name=picture]").val()});
		sw.showProcessBar();
		sw.ajaxSubmitForForm(url, params, "picture", function(data){
			sw.ajaxSuccessCallback(data, function(){
				userValidator.reset(true);
			});
		});
	}
	//初始化表单验证
	$(function(){
		userValidator = $.fn.dlshouwen.validator.init($('#userForm'));
	});
	//绑定方法
	$(function(){
		$('#set_user').bind('click', setUser);
	        var ipath = '${user.imgpath}';
                if(null == ipath || '' == ipath) {
                    $("#picImg").html('<img src="${pageContext.request.contextPath }/image/default_user_img.jpg" alt="个人照片">');
                }else {
//                    $("#picImg").html('<img src="${pageContext.request.contextPath }/downloadImage.jsp?path=${user.imgpath}" alt="个人照片">');
                    $("#picImg").html('<img src="${applicationScope.__SYSTEM_ATTRIBUTE__.source_webapp_file_postion.content }${user.imgpath}" alt="个人照片">');
                }
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
                var sFilter = 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image,src="';
                file.select();
                div.focus();//file.blur();  
                var src = document.selection.createRange().text;
                div.innerHTML = '<img id=imghead>';
                var img = document.getElementById('imghead');
                img.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
                var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
                status = ('rect:' + rect.top + ',' + rect.left + ',' + MAXWIDTH + ',' + MAXHEIGHT);
                div.innerHTML = "<div id=divhead style='width:" + MAXWIDTH + "px;height:" + MAXHEIGHT + "px;margin-top:" + rect.top + "px;margin-left:125px;" + sFilter + encodeURI(src) + "\"'></div>";
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
	</script>
</head>
<body>
	<jsp:include page="/core/base/layout/header" />
	<jsp:include page="/core/base/layout/left" />
	<jsp:include page="/core/base/layout/shortcut" />
	<div class="main-container">
		<ul class="breadcrumb">
			<li><a href="${pageContext.request.contextPath }/core/base/home"><i class="fa fa-home"></i>首页</a></li>
			<li class="active"><a href="javascript:;"><i class="fa fa-file-text-o"></i>信息设置</a></li>
		</ul>
		<div class="panel panel-default">
			<div class="panel-heading"><i class="fa fa-file-text-o"></i>设置个人信息</div>
			<div class="panel-body">
			<form:form cssClass="form-horizontal" id="userForm" modelAttribute="user" method="post">
                            <form:hidden path="user_id" />
                            <div class="form-group"> 
                            <div class="col-sm-9">
                                <div class="form-group">
                                        <label class="col-sm-2 control-label text-right">用户编号：</label>
                                        <div class="col-sm-4">
                                                <sw:input path="user_code" cssClass="form-control" placeholder="请输入用户编号" valid="r|english_number|l-l20" 
                                                        validTitle="用户编号" validInfoArea="user_code_info_area" validUnique="USER_CODE_UNIQUE_FOR_EDIT|user_id"/>
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
                                        <label class="col-sm-2 control-label text-right">所属部门：</label>
                                        <div class="col-sm-4">
                                                <sw:input path="dept_name" cssClass="form-control" disabled="true" 
                                                        valid="" validTitle="所属部门" validInfoArea="dept_id_info_area" />
                                        </div>
                                        <div class="col-sm-3"></div>
                                </div>
                                <div class="form-group">
                                        <label class="col-sm-2 control-label text-right">所属团队：</label>
                                        <div class="col-sm-4">
                                                <sw:input path="team_name" cssClass="form-control" disabled="true" 
                                                        valid="" validTitle="所属团队" validInfoArea="team_id_info_area" />
                                        </div>
                                        <div class="col-sm-3"></div>
                                </div>
                                <div class="form-group">
                                        <label class="col-sm-2 control-label text-right">性别：</label>
                                        <div class="col-sm-4">
                                                <sw:radiobuttons path="sex" items="${applicationScope.__CODE_TABLE__.sex }" 
                                                        valid="r" validTitle="性别" validInfoArea="sex_info_area" />
                                        </div>
                                        <div class="col-sm-3"><p class="help-block" id="sex_info_area"></p></div>
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
                                                <sw:input path="card_id" cssClass="form-control" placeholder="请输入证件号码" valid="r|l-l30" validTitle="证件号码" validInfoArea="card_id_info_area" />
                                        </div>
                                        <div class="col-sm-3"><p class="help-block" id="card_id_info_area"></p></div>
                                </div>
                                <div class="form-group">
                                        <label class="col-sm-2 control-label text-right">出生日期：</label>
                                        <div class="col-sm-4">
                                                <div class="input-group">
                                                        <sw:input path="birthday" cssClass="form-control" placeholder="请输入出生日期" valid="" validTitle="出生日期" validInfoArea="birthday_info_area" onclick="WdatePicker();" />
                                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                                </div>
                                        </div>
                                        <div class="col-sm-3"><p class="help-block" id="birthday_info_area"></p></div>
                                </div>
                                <div class="form-group">
                                        <label class="col-sm-2 control-label text-right">工作日期：</label>
                                        <div class="col-sm-4">
                                                <div class="input-group">
                                                        <sw:input path="work_date" cssClass="form-control" placeholder="请输入工作日期" valid="" validTitle="工作日期" validInfoArea="work_date_info_area" onclick="WdatePicker();" />
                                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                                </div>
                                        </div>
                                        <div class="col-sm-3"><p class="help-block" id="work_date_info_area"></p></div>
                                </div>
                                <div class="form-group">
                                        <label class="col-sm-2 control-label text-right">民族：</label>
                                        <div class="col-sm-4">
                                                <sw:select path="folk" items="${applicationScope.__CODE_TABLE__.folk }" 
                                                        emptyText="请选择..." valid="" cssClass="form-control" validTitle="民族" validInfoArea="folk_info_area" />
                                        </div>
                                        <div class="col-sm-3"><p class="help-block" id="folk_info_area"></p></div>
                                </div>
                                <div class="form-group">
                                        <label class="col-sm-2 control-label text-right">学历：</label>
                                        <div class="col-sm-4">
                                                <sw:select path="degree" items="${applicationScope.__CODE_TABLE__.degree }" 
                                                        emptyText="请选择..." valid="" cssClass="form-control" validTitle="学历" validInfoArea="degree_info_area" />
                                        </div>
                                        <div class="col-sm-3"><p class="help-block" id="degree_info_area"></p></div>
                                </div>
                                <div class="form-group">
                                        <label class="col-sm-2 control-label text-right">教师资质：</label>
                                        <div class="col-sm-4">
                                            <sw:select path="qualified" disabled="true" items="${applicationScope.__CODE_TABLE__.jszz }"  
                                                emptyText="请选择..." valid="" cssClass="form-control" validTitle="教师资质" validInfoArea="qualified_info_area" />
                                        </div>
                                        <div class="col-sm-3"></div>
                                </div>
                                <div class="form-group">
                                        <label class="col-sm-2 control-label text-right">毕业院校：</label>
                                        <div class="col-sm-4">
                                                <sw:input path="graduateSchool" cssClass="form-control" placeholder="请输入毕业院校信息" valid="r|l-40" validTitle="毕业院校" validInfoArea="grdSchool_info_area" />
                                        </div>
                                        <div class="col-sm-3"><p class="help-block" id="grdSchool_info_area"></p></div>
                                </div>
                                <div class="form-group">
                                        <label class="col-sm-2 control-label text-right">联系电话：</label>
                                        <div class="col-sm-4">
                                                <sw:input path="phone" cssClass="form-control" placeholder="请输入联系电话" valid="phone|l-l30" validTitle="联系电话" validInfoArea="phone_info_area" />
                                        </div>
                                        <div class="col-sm-3"><p class="help-block" id="phone_info_area"></p></div>
                                </div>
                                <div class="form-group">
                                        <label class="col-sm-2 control-label text-right">E-mail：</label>
                                        <div class="col-sm-4">
                                                <sw:input path="email" cssClass="form-control" placeholder="请输入E-mail" valid="email|l-l30" validTitle="E-mail" validInfoArea="email_info_area" />
                                        </div>
                                        <div class="col-sm-3"><p class="help-block" id="email_info_area"></p></div>
                                </div>
                                <div class="form-group">
                                        <label class="col-sm-2 control-label text-right">联系地址：</label>
                                        <div class="col-sm-4">
                                                <sw:textarea path="address" cssClass="form-control" placeholder="请输入联系地址" valid="l-l200" validTitle="联系地址" validInfoArea="address_info_area" />
                                        </div>
                                        <div class="col-sm-3"><p class="help-block" id="address_info_area"></p></div>
                                </div>
                                <div class="form-group">
                                        <label class="col-sm-2 control-label text-right">备注：</label>
                                        <div class="col-sm-4">
                                                <sw:textarea path="remark" cssClass="form-control" placeholder="请输入备注" valid="l-l200" validTitle="备注" validInfoArea="remark_info_area" />
                                        </div>
                                        <div class="col-sm-3"><p class="help-block" id="remark_info_area"></p></div>
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
                                        <button type="button" class="btn btn-success" id="set_user" accesskey="S"><i class="fa fa-save"></i>保存 (S)</button>
                                </div>
                            </div>
                        </form:form>
			</div>
		</div>
		<div class="clearfix"></div>
	</div>
</body>
</html>
