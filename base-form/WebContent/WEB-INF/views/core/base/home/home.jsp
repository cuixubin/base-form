<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<head>
    <title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 首页</title>
    <base target="_self" />
    <%@ include file="/resources/include/plugin.jsp"%>
    <%@ include file="/resources/include/extra.jsp"%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/css/home/home.css" media="all" />
    <script src="${pageContext.request.contextPath }/resources/js/hq/jquery.SuperSlide.2.1.1.js"></script>
    <script type="text/javascript" src="https://cdn.goeasy.io/goeasy.js"></script>
    <script>
        $(function () {
            jQuery(".slideBox").slide({mainCell: ".bd ul", autoPlay: false, interTime: 5000});
            $('.textbox').css('width', $('.newslist').width() - 150 + 'px');
            $(window).resize(function () {
                $('.textbox').css('width', $('.newslist').width() - 150 + 'px');
            });
            $(".newslist ul li").each(function (index) {
                if (index > 4) {
                    $(this).hide();
                }
                ;
            });
        });
        
        function toTdjsArticleDetail(articleId, channelId, teamId) {
            var paramss = "teamId="+teamId+"&channelId="+channelId+"&articleId="+articleId;
            var tdjsUrl = "${applicationScope.__SYSTEM_ATTRIBUTE__.tdjs_front_web_url.content }/article/detail?"+paramss;
            $('#tdjsForm').attr('action',tdjsUrl);
            $('#thearticleId').val(articleId);
            $('#thechannelId').val(channelId);
            $('#theteamId').val(teamId);
            $("#tdjsForm").attr("target","_blank"); 
            $('#tdjsForm').submit();
        }
    </script>
</head>
<body>
    <jsp:include page="/core/base/layout/header" />
    <jsp:include page="/core/base/layout/left" />
    <jsp:include page="/core/base/layout/shortcut" />
    <div class="main-container container-fluid">
        <form action="" method="post" id="tdjsForm">
            <input type="hidden" name="articleId" id="thearticleId">
            <input type="hidden" name="channelId" id="thechannelId">
            <input type="hidden" name="teamId" id="theteamId">
        </form>
        <div class="row view-list">
            <c:forEach items="${shortcutList}" var="s" varStatus="index"> 
                <c:choose>
                    <c:when test="${index.index <= 5 }">
                        <div class="col-md-2 col-sm-4 col-xs-6 view-libox">
                            <a href="${pageContext.request.contextPath }/${s.url}" style="color: #fff; text-decoration: none;">
                                <div class="row view-li">
                                    <div class="col-md-6 col-xs-6 img-box">
                                        <i class="fa fa-${s.icon_fa}" style="font-size: 25px;"></i>
                                    </div>

                                    <div class="col-md-6 col-xs-6 text-box">
                                        <h4>${s.limit_name}</h4> 
                                        <span>${index.index+1}</span>
                                    </div>

                                </div>
                            </a>
                        </div>
                    </c:when>
                </c:choose>
            </c:forEach> 
        </div>
<!--        <div class="row">
            <div class="col-md-7 col-sm-12">
                <div class="slideBox">
                    <div class="bd">
                        <h1>最新新闻</h1>
                        <ul>
                        <c:forEach items="${wzglNews}" var="w" varStatus="stu">
                            <li>
                                <a href="${applicationScope.__SYSTEM_ATTRIBUTE__.wzgl_front_web_url.content }/html/content_${w.article_id}.html" target="_blank">
                                <img src="${applicationScope.__SYSTEM_ATTRIBUTE__.source_webapp_file_postion.content }${w.album_coverpath}" />
                                </a>
                                <div class="text"><p>${w.title}</p></div>
                            </li>
                        </c:forEach>
                        </ul>
                        <a class="prev" href="javascript:void(0)"></a>
                        <a class="next" href="javascript:void(0)"></a>
                    </div> 
                </div>
            </div>
            <div class="col-md-5 col-sm-12">
                <div class="newslist">
                    <h1>团队建设</h1>
                    <ul>
                        <c:forEach items="${news}" var="n">
                            <c:choose>
                                <c:when test="${n.album_coverpath !=null}">
                                    <li>    
                                        <img src="${applicationScope.__SYSTEM_ATTRIBUTE__.source_webapp_file_postion.content }${n.album_coverpath}" />
                                        <div class="textbox">
                                            <h4><a href="javascript:void(0);" onclick="toTdjsArticleDetail('${n.article_id}','${n.channel_id}','${n.team_id}')">${n.title}</a></h4>
                                            <p>${n.tabloid}</p>
                                        </div>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li>   
                                        <img src="${pageContext.request.contextPath }/image/none.png" />
                                        <div class="textbox">
                                            <h4><a href="javascript:void(0);" onclick="toTdjsArticleDetail('${n.article_id}','${n.channel_id}','${n.team_id}')">${n.title}</a></h4>
                                            <p>${n.tabloid}</p>
                                        </div>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>-->
    </div>
</body>
</html>