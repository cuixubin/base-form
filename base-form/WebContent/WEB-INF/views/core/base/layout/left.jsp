<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sw" uri="http://core.dlshouwen.com/tags/all" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">
    var contentL = '';
    var contentS = '';
    function interator(limitList, limit) {
        var flag = false;
        for (var j = 0; j < limitList.length; j++) {
            var limit2 = limitList[j];
            if (limit2.pre_limit_id == limit.limit_id && limit2.limit_type == '1') {
                flag = true;
                break;
            }
        }
        if (flag) {
            contentL += '<li>';
            contentL += '	<a id="' + limit.limit_id + '" href="javascript:;"><i class="fa fa-' + limit.icon_fa + '"></i>' + limit.limit_name + '</a>';
            contentL += '	<ul>';
            contentS += '<li>';
            contentS += '	<a href="javascript:;" data-toggle="tooltip" data-placement="right" title="' + limit.limit_name + '"><i class="fa fa-' + limit.icon_fa + '"></i></a>';
            contentS += '	<ul>';
            for (var j = 0; j < limitList.length; j++) {
                var limit2 = limitList[j];
                if (limit2.pre_limit_id == limit.limit_id && limit2.limit_type == '1') {
                    interator(limitList, limit2);
                }
            }
            contentL += '	</ul>';
            contentL += '</li>';
            contentS += '	</ul>';
            contentS += '</li>';
        } else {
            contentL += '<li>';
            contentL += '	<a id="' + limit.limit_id + '" onclick=toPage("' + limit.pre_limit_id + '","' + limit.limit_id + '","' + limit.url + '","' + limit.ifAsynch + '") href="javascript:;"><i class="fa fa-' + limit.icon_fa + '"></i>' + limit.limit_name + '</a>';
            contentL += '</li>';
            contentS += '<li>';
            contentS += '	<a href="javascript:;" onclick=toPage("' + limit.pre_limit_id + '","' + limit.limit_id + '","' + limit.url + '","' + limit.ifAsynch + '") data-toggle="tooltip" data-placement="right" title="' + limit.limit_name + '"><i class="fa fa-' + limit.icon_fa + '"></i></a>';
            contentS += '</li>';
            return;
        }
    }
//初始化方法
    $(function () {
        //加载菜单
        var limitList = ${limitList };
        limitList = limitList ? limitList : [];
        contentL += '<ul>';
        contentS += '<ul>';
        for (var i = 0; i < limitList.length; i++) {
            var limit = limitList[i];
            if (limit.pre_limit_id == 'top' && limit.limit_type == '1') {
                interator(limitList, limit);
            }
        }
        contentL += '</ul>';
        contentS += '</ul>';
        $('.left-sider').html(contentL);
        $('.left-sider-xs').html(contentS);
        //Tooltip初始化
        $('[data-toggle="tooltip"]').tooltip({trigger: 'hover'});
        //标准左侧菜单事件绑定
        $('.left-sider>ul>li>ul>li>a').click(function () {
            if ($(this).next('ul').hasClass('active')) {
                $('.left-sider>ul>li>ul>li>ul.active').prev('a').children('.fa-right').removeClass('fa-caret-down').addClass('fa-caret-right');
                $('.left-sider>ul>li>ul>li>ul.active').slideUp(100);
                $('.left-sider>ul>li>ul>li>ul.active').removeClass('active');
            } else {
                $('.left-sider>ul>li>ul>li>ul.active').prev('a').children('.fa-right').removeClass('fa-caret-down').addClass('fa-caret-right');
                $('.left-sider>ul>li>ul>li>ul.active').slideUp(100);
                $('.left-sider>ul>li>ul>li>ul.active').removeClass('active');
                $(this).children('.fa-right').removeClass('fa-caret-right').addClass('fa-caret-down');
                $(this).next('ul').slideDown(100);
                $(this).next('ul').addClass('active');
            }
        });
        //简化版左侧菜单事件绑定
        $('.left-sider-xs>ul>li>ul>li>a').click(function () {
            if ($(this).nextAll('ul').hasClass('active')) {
                $('.left-sider-xs>ul>li>ul>li>ul.active').slideUp(100);
                $('.left-sider-xs>ul>li>ul>li>ul.active').removeClass('active');
            } else {
                $('.left-sider-xs>ul>li>ul>li>ul.active').slideUp(100);
                $('.left-sider-xs>ul>li>ul>li>ul.active').removeClass('active');
                $(this).nextAll('ul').slideDown(100);
                $(this).nextAll('ul').addClass('active');
            }
        });
        var root = $.cookie("root");
        if (root !== '') {
            var son = $.cookie("son");
            $("#" + root).trigger("click");
            $("#" + son).css({"background-color": "gray","opacity": "0.6"});
        }
    });
    function toPage(root, son, url, isAjax) {
        $.cookie('root', null,{ path: "/"}); 
        $.cookie('son', null,{ path: "/"}); 
        $.cookie('root', root,{ path: "/"}); 
        $.cookie('son', son,{ path: "/"}); 
        if(isAjax == "0") {
            window.location.href = "${pageContext.request.contextPath }/" + url;
        }else if(isAjax == "1") {
            var url = "${pageContext.request.contextPath }/" + url;
            var params;
            sw.ajaxSubmit(url, params, function(data){
                sw.ajaxSuccessCallback(data, function(){
                });
            });
        }
    }
</script>
<div class="left-sider-xs hidden">
    <%-- <ul>
            <c:forEach items="${limitList }" var="limit">
                    <c:if test="${limit.pre_limit_id == 'top' && limit.limit_type == '1' }">
                            <li>
                                    <a href="javascript:;" data-toggle="tooltip" data-placement="right" title="${limit.limit_name }"><i class="fa fa-${limit.icon_fa }"></i></a>
                                    <ul>
                                            <c:forEach items="${limitList }" var="limit_level_2">
                                                    <c:if test="${limit_level_2.pre_limit_id == limit.limit_id && limit_level_2.limit_type == '1' }">
                                                            <li>
                                                                    <a href="javascript:;" data-toggle="tooltip" data-placement="right" title="${limit_level_2.limit_name }"><i class="fa fa-${limit_level_2.icon_fa }"></i></a>
                                                                    <ul>
                                                                            <c:forEach items="${limitList }" var="limit_level_3">
                                                                                    <c:if test="${limit_level_3.pre_limit_id == limit_level_2.limit_id && limit_level_3.limit_type == '1' }">
                                                                                            <li>
                                                                                                    <a href="${pageContext.request.contextPath }/${limit_level_3.url }" data-toggle="tooltip" data-placement="right" title="${limit_level_3.limit_name }"><i class="fa fa-${limit_level_3.icon_fa }"></i></a>
                                                                                            </li>
                                                                                    </c:if>
                                                                            </c:forEach>
                                                                    </ul>
                                                            </li>
                                                    </c:if>
                                            </c:forEach>
                                    </ul>
                            </li>
                    </c:if>
            </c:forEach>
    </ul> --%>
</div>
<div class="left-sider">
    <%-- <ul>
            <c:forEach items="${limitList }" var="limit">
                    <c:if test="${limit.pre_limit_id == 'top' && limit.limit_type == '1' }">
                            <li>
                                    <a href="javascript:;"><i class="fa fa-${limit.icon_fa }"></i>${limit.limit_name }</a>
                                    <ul>
                                            <c:forEach items="${limitList }" var="limit_level_2">
                                                    <c:if test="${limit_level_2.pre_limit_id == limit.limit_id && limit_level_2.limit_type == '1' }">
                                                            <li>
                                                                    <a href="javascript:;"><i class="fa fa-${limit_level_2.icon_fa }"></i>${limit_level_2.limit_name }</a>
                                                                    <ul>
                                                                            <c:forEach items="${limitList }" var="limit_level_3">
                                                                                    <c:if test="${limit_level_3.pre_limit_id == limit_level_2.limit_id && limit_level_3.limit_type == '1' }">
                                                                                            <li>
                                                                                                    <a href="${pageContext.request.contextPath }/${limit_level_3.url }"><i class="fa fa-${limit_level_3.icon_fa }"></i>${limit_level_3.limit_name }</a>
                                                                                            </li>
                                                                                    </c:if>
                                                                            </c:forEach>
                                                                    </ul>
                                                            </li>
                                                    </c:if>
                                            </c:forEach>
                                    </ul>
                            </li>
                    </c:if>
            </c:forEach>
    </ul> --%>
</div>