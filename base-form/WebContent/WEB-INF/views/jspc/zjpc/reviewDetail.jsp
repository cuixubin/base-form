<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>

<html>
<head>
    <title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } -自我评测</title>
    <base target="_self" />
    <%@ include file="/resources/include/plugin.jsp"%>
    <%@ include file="/resources/include/extra.jsp"%>
    <%
        //通过静态域设置码制映射对象
        CodeTableUtils.createCodeTableJS(application, out, "jszz");//教师资质
        CodeTableUtils.createCodeTableJS(application, out, "ryjb");//荣誉级别
        CodeTableUtils.createCodeTableJS(application, out, "hjdj");//获奖等级
        CodeTableUtils.createCodeTableJS(application, out, "review_status");//评审状态
    %>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/css/jspc/fatherstyle.css" />
    
    <script>
        var editable = false; 
        $(function(){
            var expId = "${UVO.expreview.id}";
            var url = thePath + '/jspc/zwpc/zwpc/'+ expId +'/getAllItems';
            var params;
            sw.ajaxSubmit(url, params, function(data){
                sw.ajaxSuccessCallback(data, function(){
                    if(data.extParam != null) {
                        var itemsData = data.extParam;
                        for (var key in itemsData) {  
                            $("#"+key+"_ID").append(itemsData[key]);
                        }  
                    }
                });
            });
            
        });
        
        function submitStatus(expId,status){
            
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
//                                  alert(status);
//                                  alert(expId);

                                var url = '${pageContext.request.contextPath }/jspc/zjpc/zjpc/ajaxReview';
                                var params = new Object();
                                params.status = status;
                                params.id = expId;
                                params.notes = notes;
                                sw.showProcessBar();
                                sw.ajaxSubmit(url, params, function (data) {
                                    sw.ajaxSuccessCallback(data, function () {
                                        sw.closeModal('viewReviewSingle');
                                        sw.closeModal('viewReviewSingle1Inner');
                                        window.location.reload();
                                    });
                                });
                            }
                        });
            
        }
        
    </script>
    
    <script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/jspc/zwpc/application.js"></script>
</head>
<body>
    <jsp:include page="/core/base/layout/header" />
    <jsp:include page="/core/base/layout/left" />
    <jsp:include page="/core/base/layout/shortcut" />
    
    <div class="main-container">
        <ul class="breadcrumb">
                <li><a href="${pageContext.request.contextPath }/core/base/home"><i class="fa fa-home"></i>首页</a></li>
                <li><a href="javascript:void(0);"><i class="fa fa-graduation-cap"></i>教师评测</a></li>
                <li class="active"><a href="${pageContext.request.contextPath }/jspc/zjpc/zjpc"><i class="fa fa-legal"></i>专家评测</a></li>
                <li class="active"><a href="javascript:void(0);"><i class="fa fa-edit"></i>专家评测</a></li>
        </ul>

        <div class="panel-body">
            <span style="font-size: 20px;">我的专业发展</span>
        </div>
            
        <div class="panel panel-default" style="border: none">
            <div class="panel-body">
            <!-- 基本信息 -->
            <c:import url="../zwpc/jbxx.jsp" />
            
            <!-- 教书育人 -->
            <c:import url="../zwpc/jsyr.jsp" />
            
            <!--课程教学-->
            <c:import url="../zwpc/kcjx.jsp" />
            
            <!--教育教学研究-->
            <c:import url="../zwpc/jyjxyj.jsp" />
            
            <!--影响力-->
            <c:import url="../zwpc/yxl.jsp" />
            
            <!--教育教学经历-->
            <c:import url="../zwpc/jyjxjl.jsp" />
            
            <!--继续教育经历-->
            <c:import url="../zwpc/jxjyjl.jsp" />
            
            <!--专业技术职称-->
            <c:import url="../zwpc/zyjszc.jsp" />
            
            <!--其他-->
            <c:import url="../zwpc/qt.jsp" />

            <!---------------评审按钮 --------------->
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-5">
                        <sw:button accesskey="S" id="edit_expreview" cssClass="btn btn-success" onclick="submitStatus('${UVO.expreview.id}','1')" value="通过 (S)" icon="save" limit="jspc/zjpc/zjpc/ajaxReview" />
                        <button accesskey="B" type="button" class="btn btn-default" onclick="submitStatus('${UVO.expreview.id}','2')">
                            <i class="fa fa-arrow-circle-left"></i>拒绝 (B)
                        </button>
                    </div>
                </div>
            </div>         
        </div>
    </div>
    <!-- 右侧-->
    <div class="rightNav">
        <ul class="nav-wrap">
            <li><a href="#section1" class="on" style="right: -110px;" title="基本信息"><em><i class="fa fa-male"></i></em>基本信息</a></li>
            <li><a href="#section2" style="right: -110px;" title="教书育人"><em><i class="fa fa-pencil"></i></em>教书育人</a></li>
            <li><a href="#section3" style="right: -110px;" title="课程教学"><em><i class="fa fa-book"></i></em>课程教学</a></li>
            <li><a href="#section4" style="right: -110px;" title="教育教学研究"><em><i class="fa fa-flask"></i></em>教育教学研究</a></li>
            <li><a href="#section5" style="right: -110px;" title="影响力"><em><i class="fa fa-volume-up"></i></em>影响力</a></li>
            <li><a href="#section6" style="right: -110px;" title="教育教学经历"><em><i class="fa fa-superscript"></i></em>教育教学经历</a></li>
            <li><a href="#section7" style="right: -110px;" title="继续教育经历"><em><i class="fa fa-graduation-cap"></i></em>继续教育经历</a></li>
            <li><a href="#section8" style="right: -110px;" title="专业技术职称"><em><i class="fa fa-bookmark"></i></em>专业技术职称</a></li>
            <li><a href="#section9" style="right: -110px;" title="其它"><em><i class="fa fa-align-center"></i></em>其它</a></li>
        </ul>
    </div>  
    <script>
        $(function(){
            $('.nav-wrap').navScroll({
                mobileDropdown: true,
                scrollSpy: true
            });
        })
    </script>
</body>
</html>