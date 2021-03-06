<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>

<html>
<head>
    <title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } 评测详情</title>
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
    <style>
        .item-table-list-box:hover{border:1px solid #ffdab6;background-color:#fff9f3;}
    </style>
    
    <script>
    var editable = ${UVO.expreview.status !=0 && UVO.expreview.status !=1};   
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
            <li><a href="${pageContext.request.contextPath }/jspc/zwpc/zwpc/listPage"><i class="fa fa-eye"></i>评测查看</a></li>
            <li class="active"><a href="javascript:void(0);"><i class="fa fa-edit"></i>评测详情</a></li>
        </ul>

        <div class="panel-body">
            <span style="font-size: 20px;">我的专业发展</span>
        </div>
            
        <div class="panel panel-default" style="border: none">
            <div class="panel-body">
            <!-- 基本信息 -->
            <c:import url="jbxx.jsp" />
            
            <!-- 教书育人 -->
            <c:import url="jsyr.jsp" />
            
            <!--课程教学-->
            <c:import url="kcjx.jsp" />
            
            <!--教育教学研究-->
            <c:import url="jyjxyj.jsp" />
            
            <!--影响力-->
            <c:import url="yxl.jsp" />
            
            <!--教育教学经历-->
            <c:import url="jyjxjl.jsp" />
            
            <!--继续教育经历-->
            <c:import url="jxjyjl.jsp" />
            
            <!--专业技术职称-->
            <c:import url="zyjszc.jsp" />
            
            <!--其他-->
            <c:import url="qt.jsp" />

            <!---------------提交按钮 --------------->
            <div class="col-xs-12" style="margin-top:10px; height: 40px">
                <c:if test="${UVO.expreview.status != 0 && UVO.expreview.status != 1}">
                    <div class=" text-center button">
                        <button  type="button" class="btn btn-primary btn-lg text-center" 
                                onclick="submitPs('${UVO.expreview.id}','${UVO.expreview.status}')" id="buttonSend">提交评审</button>
                    </div>
                </c:if>
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