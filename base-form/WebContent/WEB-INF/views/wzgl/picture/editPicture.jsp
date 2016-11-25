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
            var goBackUrl = '${pageContext.request.contextPath }/wzgl/picture/picture';
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
                var url = '${pageContext.request.contextPath }/wzgl/picture/picture/ajaxEdit';
                var params = $("#pictureForm").serializeArray();
                if ($("input[name=picture]").val() !== "") {
                    params.push({"name": "path", "value": $("input[name=picture]").val()});
                }else{
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
        </script>
    </head>
    <body>
        <jsp:include page="/core/base/layout/header" />
        <jsp:include page="/core/base/layout/left" />
        <jsp:include page="/core/base/layout/shortcut" />
        <div class="main-container">
            <ul class="breadcrumb">
                <li><a href="${pageContext.request.contextPath }/core/base/home"><i class="fa fa-home"></i>首页</a></li>
                <li><a href="javascript:void(0);"><i class="fa fa-bell"></i>基础管理</a></li>
                <li><a href="${pageContext.request.contextPath }/wzgl/picture/picture"><i class="fa fa-file-image-o"></i>大图管理</a></li>
                <li class="active"><a href="javascript:void(0);"><i class="fa fa-plus"></i>编辑图片</a></li>
            </ul>
            <form:form id="pictureForm" modelAttribute="picture" method="post">
                <form:hidden path="picture_id" />
                <div class="panel panel-default">
                    <div class="panel-heading"><i class="fa fa-plus"></i>编辑图片</div>
                    <div class="panel-body">
                        <div class="form-horizontal">
                            <div class="form-group">
                                <label class="col-sm-2 control-label text-right">名称：</label>
                                <div class="col-sm-6">
                                    <sw:input path="picture_name" cssClass="form-control" placeholder="请输入图片名称" valid="r|l-200" validTitle="图片名称" validInfoArea="picture_name_info_area" />
                                </div>
                                <div class="col-sm-4"><p class="help-block" id="picture_name_info_area"></p></div>
                            </div>

                            <div class="form-group">
                                <%--
                                <!--                                <label class="col-sm-2 control-label text-right">所属相册：</label>
                                                                <div class="col-sm-2">
                                <sw:select path="album_id" items="${applicationScope.__CODE_TABLE__.album_flag }" 
                                           validInfoArea="flag_info_area" />
                            </div>-->
                            --%>
                                <label class="col-sm-2 control-label text-right">类型：</label>
                                <div class="col-sm-2">
                                    <sw:select path="flag" items="${applicationScope.__CODE_TABLE__.picture_flag }" 
                                   cssClass="form-control" validInfoArea="flag_info_area" />
                                </div>
                                <div class="col-sm-8"><p class="help-block" id="flag_info_area"></p></div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label text-right">状态：</label>
                                <div class="col-sm-2">
                                    <sw:radiobuttons path="show" items="${applicationScope.__CODE_TABLE__.picture_show }" 
                                                     validInfoArea="show_info_area" />
                                </div>
                            </div>


                            <div class="form-group">
                                <label class="col-sm-2 control-label text-right">排序：</label>
                                <div class="col-sm-2">
                                    <sw:input path="order" cssClass="form-control" placeholder="请输入排序号" valid="r|integer|l-200" validTitle="排序号" validInfoArea="order_info_area" />
                                </div>
                                <div class="col-sm-8"><p class="help-block" id="order_info_area"></p></div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label text-right">描述：</label>
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
                                    <sw:button accesskey="S" id="edit_picture" cssClass="btn btn-success" value="保存 (S)" icon="save" limit="wzgl/picture/picture/-/edit" />
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
