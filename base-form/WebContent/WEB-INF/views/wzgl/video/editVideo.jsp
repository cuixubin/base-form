<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
    <head>
        <title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 编辑视频</title>
        <base target="_self" />
        <%@ include file="/resources/include/plugin.jsp"%>
        <%@ include file="/resources/include/extra.jsp"%>
        <script type="text/javascript">
            //定义返回URL
            var goBackUrl = '${pageContext.request.contextPath }/wzgl/video/video';
            //返回
            function goBack() {
                sw.showProcessBar();
                window.location.href = goBackUrl;
            }
            //定义验证对象
            var videoValidator;
            //新增视频
            function editVideo() {
                if (!videoValidator.validResult()) {
                    return;
                }
                var url = '${pageContext.request.contextPath }/wzgl/video/video/edit';
                var params = sw.getFormParams(document.forms[0]);
                sw.showProcessBar();
                sw.ajaxSubmit(url, params, function (data) {
                    sw.ajaxSuccessCallback(data, function () {
                        videoValidator.reset(true);
                    });
                });
            }
            //初始化表单验证
            $(function () {
                videoValidator = $.fn.dlshouwen.validator.init($('#videoForm'));
                $('#edit_video').bind('click', editVideo);
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
                <li><a href="javascript:void(0);"><i class="fa fa-book"></i>基础管理</a></li>
                <li><a href="${pageContext.request.contextPath }/wzgl/video/video"><i class="fa fa-video-camera"></i>视频管理</a></li>
                <li class="active"><a href="javascript:void(0);"><i class="fa fa-plus"></i>编辑视频</a></li>
            </ul>
            <div class="panel panel-default">
                <div class="panel-heading"><i class="fa fa-plus"></i>编辑视频</div>
                <div class="panel-body">
                    <form:form cssClass="form-horizontal" id="videoForm" modelAttribute="video" method="post">
                        <form:hidden path="vd_id" />

                        <div class="form-group">
                            <label class="col-sm-2 control-label text-right">视频名称：</label>
                            <div class="col-sm-4">
                                <sw:input path="vd_name" cssClass="form-control" placeholder="请输入视频名称" valid="r|l-l60" validTitle="视频名称" validInfoArea="video_name_info_area" />
                            </div>
                            <div class="col-sm-6"><p class="help-block" id="video_name_info_area"></p></div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label text-right">是否显示：</label>
                            <div class="col-sm-4">
                                <sw:radiobuttons path="vd_isdisplay" items="${applicationScope.__CODE_TABLE__.zero_one }" 
                                                 valid="r" validTitle="视频状态" validInfoArea="vd_isdisplay_info_area" />
                            </div>
                            <div class="col-sm-6"><p class="help-block" id="vd_isdisplay_info_area"></p></div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label text-right">视频排序：</label>
                            <div class="col-sm-4">
                                <sw:input path="vd_order" cssClass="form-control" placeholder="请输入视屏排序(请录入数字)" valid="r|integer" validTitle="视频排序" validInfoArea="video_order_info_area" />
                            </div>
                            <div class="col-sm-6"><p class="help-block" id="video_order_info_area"></p></div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label text-right">视频描述：</label>
                            <div class="col-sm-7">
                                <sw:textarea path="vd_description" cssClass="form-control" placeholder="请输入视频描述" valid="r" validTitle="视频描述"  validInfoArea="vd_description_info_area" id="vd_description_area"  />
                            </div>
                            <div class="col-sm-3"><p class="help-block" id="vd_description_info_area"></p></div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <sw:button accesskey="S" id="edit_video" cssClass="btn btn-success" value="保存 (S)" icon="save" limit="wzgl/video/video/add" />
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
