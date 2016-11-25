<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>

<html>
<head>
    <title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } -专家评测</title>
    <base target="_self" />
    <%@ include file="/resources/include/plugin.jsp"%>
    <%@ include file="/resources/include/extra.jsp"%>
    <%
        //通过静态域设置码制映射对象
        CodeTableUtils.createCodeTableJS(application, out, "jszz");//教师资质
        CodeTableUtils.createCodeTableJS(application, out, "ryjb");//荣誉等级
        CodeTableUtils.createCodeTableJS(application, out, "review_status");//评审状态
    %>
    <script type="text/javascript">
        
        function submit(){
            
                        sw.openModal({
                            id: 'viewReviewSingle1Inner',
                            icon: 'bell',
                            title: '评审意见',
                            url: '${pageContext.request.contextPath }/jspc/zjpc/zjpc/note',
                            modalType: 'sm',
                            showCloseButton: false,
                            showConfirmButton: true,
                            width: '30%',
                            height: '250px',
                            callback: function () {
                                var notes = "";
                                notes = document.getElementById("viewReviewSingle1InnerFrame").contentWindow.getNote();
//                                alert(notes);
//                                alert(id);
                                //默认未通过
                                var status = '2';
                                //通过评审
                                if (pass == 'passModel') {
                                    status = '1';
                                }

                                var url = '${pageContext.request.contextPath }/jspc/zjpc/zjpc/ajaxReview';
                                var params = new Object();
                                params.status = status;
                                params.id = id;
                                params.notes = notes;
                                sw.showProcessBar();
                                sw.ajaxSubmit(url, params, function (data) {
                                    sw.ajaxSuccessCallback(data, function () {
                                        sw.closeModal('viewReviewSingle');
                                        sw.closeModal('viewReviewSingle1Inner');
                                        window.location.reload();
//                                        window.location.href = '${pageContext.request.contextPath }/jspc/zjpc/zjpc';
                                    });
                                });
//                                sw.ajaxSubmitRefreshGrid(url, params, grid);
                            }
                        });
            
        }
        
        //初始化表单验证
            $(function () {
                $('#edit_expreview').bind('click', submit);
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
                <li><a href="javascript:void(0);"><i class="fa fa-bell"></i>专家评测</a></li>
                <li><a href="${pageContext.request.contextPath }/jspc/zjpc/zjpc"><i class="fa fa-bell"></i>专家评测</a></li>
                <li class="active"><a href="javascript:void(0);"><i class="fa fa-plus"></i>专家评测</a></li>
            </ul>
            <form:form id="expreviewForm" modelAttribute="expreview" method="post">
                <form:hidden path="id" />
                <div class="panel panel-default">
                    <div class="panel-heading"><i class="fa fa-plus"></i>专家评测</div>
                    <div class="panel-body">
                        <div class="form-horizontal">
                            

                        
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <sw:button accesskey="S" id="edit_expreview" cssClass="btn btn-success" value="通过 (S)" icon="save" limit="jspc/zjpc/zjpc/ajaxReview" />
                                    <button accesskey="B" type="button" class="btn btn-default" onclick="submit();">
                                        <i class="fa fa-arrow-circle-left"></i>拒绝 (B)
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
