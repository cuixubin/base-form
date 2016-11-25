<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
    <head>
        <title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 新增团队</title>
        <base target="_self" />
        <%@ include file="/resources/include/plugin.jsp"%>
        <%@ include file="/resources/include/extra.jsp"%>
        <script type="text/javascript">
            //定义返回URL
            var goBackUrl = '${pageContext.request.contextPath }/core/team';
            //返回
            function goBack() {
                sw.showProcessBar();
                window.location.href = goBackUrl;
            }
            //定义验证对象
            var teamValidator;
            //新增团队
            function addTeam() {
                if (!teamValidator.validResult()) {
                    return;
                }
                if (!confirm('确定新增此团队吗？')) {
                    return;
                }
                
                var url = '${pageContext.request.contextPath }/core/team/add';
                var params = $("#teamForm").serializeArray();
                params.push({"name": "team_path", "value": $("input[name=picture]").val()});
                sw.showProcessBar();
                sw.ajaxSubmitForForm(url, params,"picture", function (data) {
                    sw.ajaxSuccessCallback(data, function () {
                        teamValidator.reset(true);
                    });
                });
            }
            
            
            $(function () {
                //初始化表单验证
                teamValidator = $.fn.dlshouwen.validator.init($('#teamForm'));
            });
            //绑定方法
            $(function () {
                $('#add_team').bind('click', addTeam);
                $("#picImg").html("<img  src=${pageContext.request.contextPath }/resources/images/no_picture.jpg" + " alt=''  height='150px' width='200px' style='margin-top:5px;filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale');-moz-background-size:100% 100%;background-size:100% 100%;'/>");
            });
            //选择用户
            function selectUser() {
                var url = '${pageContext.request.contextPath }/core/system/user/select/radio';
                sw.openModal({
                    id: 'selectUser',
                    icon: 'user',
                    title: '选择用户',
                    url: url,
                    callback: function (users) {
                        var userName = '';
                        var phone = '';
                        if (users[0]) {
                            userName = users[0].user_name;
                            phone = users[0].phone;
                        }
                        $('#principal').val(userName);
                        $('#principal_phone').val(phone);
                        sw.closeModal('selectUser');
                    }
                });
            }
            //预览图片
            function previewImage(MAXWIDTH, MAXHEIGHT, file, divId) {
                var div = document.getElementById(divId);
                if (file.files && file.files[0]) {
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
                } else {
                   file.select();
                    div.focus();//file.blur();  
                    var src = document.selection.createRange().text;
                    div.innerHTML = "<div id=divhead style=\"FILTER:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);width:160px; height:160px\"></div>";
                    var newPreview = document.getElementById("divhead");            
                    newPreview.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = src;  
                }
            }
             function preview(file) {
                if (/.+\.(jpg|JPG|jpeg|JPEG|bmp|BMP|png|PNG|gif|GIF)/g.test(document.getElementById('picture').value)) {
                    previewImage(200, 163, file, "picImg");
                } else {
                    alert('请选择正确的图片格式（jpg,jpeg,bmp,png,gif）');
                    return false;
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
                <li><a href="javascript:void(0);"><i class="fa fa-gears"></i>基础管理</a></li>
                <li><a href="${pageContext.request.contextPath }/core/team"><i class="fa fa-group"></i>团队管理</a></li>
                <li class="active"><a href="javascript:void(0);"><i class="fa fa-plus"></i>新增团队</a></li>
            </ul>
            <div class="panel panel-default">
                <div class="panel-heading"><i class="fa fa-plus"></i>新增团队</div>
                <div class="panel-body">
                    <form:form cssClass="form-horizontal" id="teamForm" modelAttribute="team" method="post">
                        <form:hidden path="pre_team_id" />
                        <div class="form-group">
                            <label class="col-sm-2 control-label text-right">团队名称：</label>
                            <div class="col-sm-4">
                                <sw:input path="team_name" cssClass="form-control" placeholder="请输入团队名称" valid="r|l-l40" validTitle="团队名称" validInfoArea="team_name_info_area" />
                            </div>
                            <div class="col-sm-6"><p class="help-block" id="team_name_info_area"></p></div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label text-right">负责人：</label>
                            <div class="col-sm-4">
                                <div class="input-group">
                                    <sw:input path="principal" cssClass="form-control" placeholder="请输入负责人"
                                              valid="l-l40" validTitle="负责人" validInfoArea="principal_info_area" ondblclick="selectUser();" />
                                    <span class="input-group-addon" onclick="selectUser();"><i class="fa fa-user"></i></span>
                                </div>
                            </div>
                            <div class="col-sm-6"><p class="help-block" id="principal_info_area"></p></div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label text-right">负责人电话：</label>
                            <div class="col-sm-4">
                                <sw:input path="principal_phone" cssClass="form-control" placeholder="请输入负责人电话" valid="mobile" validTitle="负责人电话" validInfoArea="principal_phone_info_area" />
                            </div>
                            <div class="col-sm-6"><p class="help-block" id="principal_phone_info_area"></p></div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label text-right">备注：</label>
                            <div class="col-sm-4">
                                <sw:textarea path="remark" cssClass="form-control" placeholder="请输入备注" valid="l-l200" validTitle="备注" validInfoArea="remark_info_area" />
                            </div>
                            <div class="col-sm-6"><p class="help-block" id="remark_info_area"></p></div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label text-right">排序号：</label>
                            <div class="col-sm-4">
                                <sw:input path="sort" cssClass="form-control" placeholder="请输入排序号" valid="integer" validTitle="排序号" validInfoArea="sort_info_area" />
                            </div>
                            <div class="col-sm-6"><p class="help-block" id="sort_info_area"></p></div>
                        </div>
                            
                        <div class="form-group">
                            <label class="col-sm-2 control-label text-right">团队栏目封面：</label>
                            <div class="col-sm-6">
                                <div id="picImg"> </div>
                            </div>
                        </div> 
                        <div class="form-group">
                            <label class="col-sm-2 control-label text-right">上传文件：</label>
                            <div class="col-sm-7">
                                <input id="picture" name="picture" type="file" onchange="preview(this)"/>
                            </div>
                        </div>      



                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <sw:button accesskey="S" id="add_team" cssClass="btn btn-success" value="保存 (S)" icon="save" limit="core/team/add" />
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
