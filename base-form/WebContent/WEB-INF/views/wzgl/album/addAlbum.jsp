<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
    <head>
        <title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 新增相册</title>
        <base target="_self" />
        <%@ include file="/resources/include/plugin.jsp"%>
        <%@ include file="/resources/include/extra.jsp"%>
        <script type="text/javascript">
            //定义返回URL
            var goBackUrl = '${pageContext.request.contextPath }/wzgl/album/album';
            //返回
            function goBack() {
                sw.showProcessBar();
                window.location.href = goBackUrl;
            }
            //定义验证对象
            var albumValidator;
            //新增相册
            function addAlbum() {
                if (!albumValidator.validResult()) {
                    return;
                }
                if (!confirm('确定新增此相册吗？')) {
                    return;
                }
                var url = '${pageContext.request.contextPath }/wzgl/album/album/add';
                var params = $("#albumForm").serializeArray();
                params.push({"name": "album_coverpath", "value": $("input[name=picture]").val()});
                sw.showProcessBar();
                sw.ajaxSubmitForForm(url, params, "picture", function (data) {
                    sw.ajaxSuccessCallback(data, function () {
                        articleValidator.reset(true);
                    });
                });
            }
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
            //初始化表单验证
            $(function () {
                albumValidator = $.fn.dlshouwen.validator.init($('#albumForm'));
                $('#add_album').bind('click', addAlbum);
                $("#picImg").html("<img  src=${pageContext.request.contextPath }/resources/images/no_picture.jpg" + " alt=''  height='150px' width='200px' style='margin-top:5px;filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale');-moz-background-size:100% 100%;background-size:100% 100%;'/>");
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
                <li><a href="javascript:void(0);"><i class="fa fa-gears"></i>基础管理</a></li>
                <li><a href="${pageContext.request.contextPath }/wzgl/album/album"><i class="fa fa-delicious"></i>相册管理</a></li>
                <li class="active"><a href="javascript:void(0);"><i class="fa fa-plus"></i>新增相册</a></li>
            </ul>
            <div class="panel panel-default">
                <div class="panel-heading"><i class="fa fa-plus"></i>新增相册</div>
                <div class="panel-body">
                    <form:form cssClass="form-horizontal" id="albumForm" modelAttribute="album" method="post">
                        <form:hidden path="album_id" />

                        <div class="form-group">
                            <label class="col-sm-2 control-label text-right">名称：</label>
                            <div class="col-sm-4">
                                <sw:input path="album_name" cssClass="form-control" placeholder="请输入相册名称" valid="r|l-l60" validTitle="相册名称" validInfoArea="album_name_info_area" />
                            </div>
                            <div class="col-sm-6"><p class="help-block" id="album_name_info_area"></p></div>
                        </div>


                        <div class="form-group">
                            <label class="col-sm-2 control-label text-right">类型：</label>
                            <div class="col-sm-4">
                                <sw:select path="album_flag" disabled="true" items="${applicationScope.__CODE_TABLE__.album_flag }" 
                                          cssClass="form-control" validTitle="相册类型" validInfoArea="album_flag_info_area" />
                            </div>
                            <div class="col-sm-6"><p class="help-block" id="album_flag_info_area"></p></div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label text-right">描述：</label>
                            <div class="col-sm-7">
                                <sw:textarea path="album_description" cssClass="form-control" placeholder="请输入相册描述" valid="r" validTitle="相册描述"  validInfoArea="album_description_info_area" id="album_description_area"  />
                            </div>
                            <div class="col-sm-3"><p class="help-block" id="album_description_info_area"></p></div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label text-right">封面：</label>
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
                                <sw:button accesskey="S" id="add_album" cssClass="btn btn-success" value="保存 (S)" icon="save" limit="wzgl/album/album/add" />
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
