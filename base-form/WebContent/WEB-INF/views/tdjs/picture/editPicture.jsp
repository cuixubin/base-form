<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
    <head>
        <title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 编辑图片</title>
        <base target="_self" />
        <%@ include file="/resources/include/plugin.jsp"%>
        <%@ include file="/resources/include/extra.jsp"%>

        <script type="text/javascript">
            //定义返回URL
            var goBackUrl = '${pageContext.request.contextPath }/tdjs/tdjsPicture/picture';
            //返回
            function goBack() {
                sw.showProcessBar();
                window.location.href = goBackUrl;
            }

            //编辑图片
            function editPicture() {
                if (!pictureValidator.validResult()) {
                    return;
                }
                if (!confirm('确定编辑此图片吗？')) {
                    return;
                }
                var url = '${pageContext.request.contextPath }/tdjs/tdjsPicture/picture/ajaxEdit';
                var params = $("#pictureForm").serializeArray();
                if ($("input[name=picture]").val() !== "") {
                    params.push({"name": "path", "value": $("input[name=picture]").val()});
                } else {
                    params.push({"name": "path", "value": "${picture.path}"});
                }
                sw.showProcessBar();
                sw.ajaxSubmitForForm(url, params, "picture", function (data) {
                    sw.ajaxSuccessCallback(data, function () {
                        pictureValidator.reset(true);
                    });
                });

            }


            //初始化表单验证
            $(function () {
                pictureValidator = $.fn.dlshouwen.validator.init($('#pictureForm'));
                $('#edit_picture').bind('click', editPicture);

                if ('${picture.path}' != '') {
                    $("#picImg").html("<img  src=${applicationScope.__SYSTEM_ATTRIBUTE__.source_webapp_file_postion.content }${picture.path}" + " alt=''  height='150px' width='200px' style='margin-top:5px;filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale');-moz-background-size:100% 100%;background-size:100% 100%;'/>");
                } else {
                    $("#picImg").html("<img  src=${pageContext.request.contextPath }/resources/images/no_picture.jpg" + " alt=''  height='150px' width='200px' style='margin-top:5px;filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale');-moz-background-size:100% 100%;background-size:100% 100%;'/>");
                }
            });

            //验证图片
            function preview(file) {
                if (/.+\.(jpg|JPG|jpeg|JPEG|bmp|BMP|png|PNG|gif|GIF)/g.test(document.getElementById('picture').value)) {
                    previewImage(200, 163, file, "picImg");
                } else {
                    alert('请选择正确的图片格式（jpg,jpeg,bmp,png,gif）');
                    return false;
                }
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

            function selectTeam(){
                var tempTeamId = "";
                if($('#team_id').val() != null && $('#team_id').val() != "") {
                    tempTeamId = $('#team_id').val();
                }
                var url = '${pageContext.request.contextPath}/core/team/selectOneTeam?teamId=' + tempTeamId;
                sw.openModal({
                    id: 'selectTeam',
                    icon: 'building',
                    title: '选择团队',
                    url: url,
                    callback: function (teams) {
                        var teamId = '';
                        var teamName = '';
                        if (teams[0]) {
                            teamId = teams[0].team_id;
                            teamName = teams[0].team_name;
                        }
                        $('#team_id').val(teamId);
                        $('#team_name').val(teamName);
                        $('#team_name').trigger("blur");
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
                <li><a href="javascript:void(0);"><i class="fa fa-group"></i>团队建设</a></li>
                <li><a href="javascript:void(0);"><i class="fa fa-book"></i>基础管理</a></li>
                <li><a href="${pageContext.request.contextPath }/tdjs/tdjsPicture/picture"><i class="fa fa-file-photo-o"></i>大图管理</a></li>
                <li class="active"><a href="javascript:void(0);"><i class="fa fa-group"></i>编辑图片</a></li>
            </ul>
            <form:form id="pictureForm" modelAttribute="picture" method="post">
                <form:hidden path="picture_id" />
                <div class="panel panel-default">
                    <div class="panel-heading"><i class="fa fa-plus"></i>编辑图片</div>
                    <div class="panel-body">
                        <div class="form-horizontal">
                            <div class="form-group">
                                <label class="col-sm-2 control-label text-right">图片名称：</label>
                                <div class="col-sm-3">
                                    <sw:input path="picture_name" cssClass="form-control" placeholder="请输入图片名称" valid="r|l-200" validTitle="图片名称" validInfoArea="picture_name_info_area" />
                                </div>
                                <div class="col-sm-7"><p class="help-block" id="picture_name_info_area"></p></div>
                            </div>

                            <div class="form-group">

                                <label class="col-sm-2 control-label text-right">类型：</label>
                                <div class="col-sm-3">
                                    <sw:select path="flag" items="${applicationScope.__CODE_TABLE__.picture_flag }" 
                                   cssClass="form-control" validInfoArea="flag_info_area" />
                                </div>
                                <div class="col-sm-7"><p class="help-block" id="flag_info_area"></p></div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label text-right">状态：</label>
                                <div class="col-sm-3">
                                    <sw:radiobuttons path="show" items="${applicationScope.__CODE_TABLE__.picture_show }" 
                                   validInfoArea="show_info_area" />
                                </div>
                                <div class="col-sm-7"><p class="help-block" id="show_info_area"></p></div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label text-right">所属团队：</label>
                                <div class="col-sm-3">
                                    <div class="input-group" id="teamBtn">
                                        <form:hidden path="team_id"/>
                                        <sw:input path="team_name" cssClass="form-control"  
                                                  valid="r" validTitle="所属团队" validInfoArea="team_id_info_area" onclick="selectTeam('team_id', 'team_name');"/>
                                        <span class="input-group-addon" onclick="selectTeam('team_id', 'team_name');"><i class="fa fa-group"></i></span>
                                    </div>
                                </div>
                                <div class="col-sm-7"><p class="help-block" id="team_id_info_area"></p></div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label text-right">排序号：</label>
                                <div class="col-sm-3">
                                    <sw:input path="order" cssClass="form-control" placeholder="请输入排序号" valid="r|integer|l-200" validTitle="排序号" validInfoArea="order_info_area" />
                                </div>
                                <div class="col-sm-7"><p class="help-block" id="order_info_area"></p></div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label text-right">图片描述：</label>
                                <div class="col-sm-6">
                                    <sw:textarea path="description" cssClass="form-control" placeholder="请输入图片描述（200字以内）" valid="l-l200" validTitle="图片描述" validInfoArea="description_info_area" />
                                </div>
                                <div class="col-sm-4"><p class="help-block" id="description_info_area"></p></div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label text-right">图片：</label>
                                <div class="col-sm-6">
                                    <div id="picImg"> </div>
                                </div>
                            </div>        



                            <div class="form-group">
                                <label class="col-sm-2 control-label text-right">上传图片：</label>
                                <div class="col-sm-6">
                                    <input id="picture" name="picture" type="file" onchange="preview(this)"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <sw:button accesskey="S" id="edit_picture" cssClass="btn btn-success" value="保存 (S)" icon="save" limit="tdjs/tdjsPicture/picture/-/edit" />
                                    <button accesskey="B" type="button" class="btn btn-default" onclick="goBack();">
                                        <i class="fa fa-arrow-circle-left"></i>返回 (B)
                                    </button>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>

            </form:form>
            <div class="clearfix"></div>
        </div>
    </body>
</html>
