<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
    <head>
        <title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 新增标签</title>
        <base target="_self" />
        <%@ include file="/resources/include/plugin.jsp"%>
        <%@ include file="/resources/include/extra.jsp"%>
        <style>
            #tag_url_area{width:650px;height:380px}
            #tag_styledescription_area{height:150px}
            #add_tag{color: #333;background-color: #e6e6e6;border-color: #adadad;}
        </style>
        <script type="text/javascript">
            //定义返回URL
            var goBackUrl = '${pageContext.request.contextPath }/wzgl/tag/tag';
            //返回
            function goBack() {
                sw.showProcessBar();
                window.location.href = goBackUrl;
            }
            //定义验证对象
            var tagValidator;
            //新增标签
            function addTag() {
                if (!tagValidator.validResult()) {
                    return;
                }
                if (!confirm('确定新增此标签吗？')) {
                    return;
                }
                var url = '${pageContext.request.contextPath }/wzgl/tag/tag/add';
                var params = sw.getFormParams(document.forms[0]);
                sw.showProcessBar();
                sw.ajaxSubmit(url, params, function (data) {
                    sw.ajaxSuccessCallback(data, function () {
                        tagValidator.reset(true);
                    });
                });
            }
            //初始化表单验证
            $(function () {
                tagValidator = $.fn.dlshouwen.validator.init($('#tagForm'));
            });
            //绑定方法
            $(function () {
                $('#add_tag').bind('click', addTag);
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
                <li><a href="${pageContext.request.contextPath }/wzgl/tag/tag"><i class="fa fa-tag"></i>标签管理</a></li>
                <li class="active"><a href="javascript:void(0);"><i class="fa fa-plus"></i>新增标签</a></li>
            </ul>
            <form:form id="tagForm" modelAttribute="tag" method="post">
                <div class="panel panel-default">
                    <div class="panel-heading"><i class="fa fa-plus"></i>新增标签</div>
                    <div class="panel-body">
                        <div class="form-horizontal">
                            <div class="form-group">
                                <label class="col-sm-2 control-label text-right">标签名称：</label>
                                <div class="col-sm-4">
                                    <sw:input path="tag_name" cssClass="form-control" placeholder="请输入标签名称" valid="r|l-l60" validTitle="标签名称" validInfoArea="tag_name_info_area" />
                                    <input style="display:none"/> 
                                </div>
                                <div class="col-sm-6"><p class="help-block" id="tag_name_info_area"></p></div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label text-right">标签状态：</label>
                                <div class="col-sm-4">
                                    <sw:radiobuttons path="tag_state" items="${applicationScope.__CODE_TABLE__.open_close }" 
                                                     valid="r" validTitle="标签状态" validInfoArea="tag_state_info_area" />
                                </div>
                                <div class="col-sm-6"><p class="help-block" id="tag_state_info_area"></p></div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label text-right">标签格式说明：</label>
                                <div class="col-sm-4">
                                    <sw:textarea path="tag_styledescription" cssClass="form-control"  placeholder="请输入标签格式说明" valid="r"  validTitle="标签格式说明" validInfoArea="tag_styledescription_info_area" id="tag_styledescription_area"  />
                                </div>
                                <div class="col-sm-6"><p class="help-block" id="tag_styledescription_info_area"></p></div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label text-right">标签内容：</label>
                                <div class="col-sm-7">
                                    <%--<sw:input path="tag_url" cssClass="form-control" placeholder="请输入标签名称" valid="r|l-l60" validTitle="标签名称" validInfoArea="tag_url_info_area" />--%>
                                    <sw:textarea path="tag_url" cssClass="form-control" placeholder="请输入标签内容" valid="r" validTitle="标签内容"  validInfoArea="tag_url_info_area" id="tag_url_area"  />
                                </div>
                                <div class="col-sm-3"><p class="help-block" id="tag_url_info_area"></p></div>
                            </div>


                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <button accesskey="C" type="button" class="btn btn-default" onclick="" style="background-color: #5cb85c;border-color: #4cae4c;">
                                        <i class="fa fa-eye" ></i>预览 (C)
                                    </button>
                                    <sw:button accesskey="S" id="add_tag" cssClass="btn btn-success" value="保存 (S)" icon="save" limit="wzgl/tag/tag/add" />
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
