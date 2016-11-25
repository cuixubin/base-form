<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sw" uri="http://core.dlshouwen.com/tags/all" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">
//初始化方法
    $(function () {
        //Tooltip初始化
        $('[data-toggle="tooltip"]').tooltip({trigger: 'hover'});
        //菜单切换事件绑定
        $('.navbar-menu-toggle').click(function () {
            if (!$('.navbar').hasClass('navbar-xs')) {
                $('.navbar').addClass('navbar-xs')
                $('.left-sider').addClass('hidden');
                $('.left-sider-xs').removeClass('hidden');
                $('.main-container').addClass('main-container-xs')
            } else {
                $('.navbar').removeClass('navbar-xs')
                $('.left-sider').removeClass('hidden');
                $('.left-sider-xs').addClass('hidden');
                $('.main-container').removeClass('main-container-xs')
            }
        });
        //公告轮播事件绑定
        setInterval(loopNotice, 3000);
        //当前时间事件绑定
        showHeaderTime();
        setInterval(showHeaderTime, 1000);
        //任务调度
        systemTaskListener();
    });
//公告轮播
    var nowNoticeTop = 0;
    function loopNotice() {
        var noticeHeight = 26;
        var noticeCount = $('.header-notice-container>ul>li').length;
        nowNoticeTop -= noticeHeight;
        if (nowNoticeTop == noticeCount * noticeHeight * -1)
            nowNoticeTop = 0;
        $('.header-notice-container>ul').animate({top: nowNoticeTop});
    }
//显示当前时间
    function showHeaderTime() {
        $('#header_time_container').html(new Date().format('yyyy-MM-dd hh:mm:ss'));
    }
//更换皮肤
    function changeSkin(skinId) {
        var url = '${pageContext.request.contextPath }/core/base/assist/change_skin';
        var params = new Object();
        params.skinId = skinId;
        sw.ajaxSubmit(url, params, function (data) {
            $('#__skin__').attr('href', '${pageContext.request.contextPath }/core/base/assist/skin');
            $('#show_select_skin img').attr('src', data.data.thumbnail);
        });
    }
//退出系统
    function logout() {
        if (!confirm("确定要退出吗？请确保您的数据已经全部保存。")) {
            return;
        }
        var url = '${pageContext.request.contextPath }/core/base/logout';
        var params = new Object();
        sw.ajaxSubmit(url, params, function () {
            $.cookie('root', null, {path: "/"});
            $.cookie('son', null, {path: "/"});
//            parent.location.href = '${pageContext.request.contextPath }/core/base/login';
            parent.location.href = '${applicationScope.__SYSTEM_ATTRIBUTE__.cas_login_page_system.content }';
        });
    }
//设定皮肤
    function setUserSkinInfo(skinInfo) {
        var url = '${pageContext.request.contextPath }/core/base/skin/set_user_skin_info';
        var params = new Object();
        params.skinInfo = skinInfo;
        sw.ajaxSubmit(url, params, function () {
            $('#skin_css').attr('href', '${pageContext.request.contextPath }/core/base/skin');
        });
    }
//查看跳转
    function viewAnnouncementSingle(announcementId) {
        var url = '${pageContext.request.contextPath }/core/announcement/view_announcement/' + announcementId + '/view';
        sw.openModal({
            id: 'viewAnnouncement',
            icon: 'bell',
            title: '查看公告',
            url: url,
            modalType: 'lg'
        });
    }
//处理浮动背景
    $(function () {
        //定义是否执行浮动、移动速度
        var isBackgroundFloat = '${sessionScope.__SESSION_USER__.userAttr.is_background_float }';
        var backgroundFloatSpeed = '${sessionScope.__SESSION_USER__.userAttr.background_float_speed }';
        backgroundFloat(isBackgroundFloat, backgroundFloatSpeed);
    });
</script>
<header class="navbar">
    <div class="navbar-header">
        <button class="navbar-toggle" type="button" data-toggle="collapse" data-target=".bs-navbar-collapse">
            <i class="fa fa-list"></i>
        </button>
        <a href="javascript:;" class="navbar-brand">${applicationScope.__SYSTEM_ATTRIBUTE__.system_title.content }</a>
        <a href="javascript:;" class="navbar-menu-toggle hidden-xs"><i class="fa fa-list"></i></a>
    </div>
    <nav class="collapse navbar-collapse bs-navbar-collapse" role="navigation">
        <ul class="nav navbar-nav navbar-icon">
            <li>
                <a href="${pageContext.request.contextPath }/core/base/home" data-toggle="tooltip" data-placement="bottom" title="首页">
                    <i class="fa fa-home"></i><span class="navbar-icon-text hidden-lg hidden-md hidden-sm">首页</span>
                </a>
            </li>
            <!--
            <li>
                <a href="javascript:;" data-toggle="dropdown" class="dropdown-toggle">
                    <i class="fa fa-warning"></i>
                    <span id="header_task_count_container" class="noft noft-success">0</span>
                    <span class="navbar-icon-text hidden-lg hidden-md hidden-sm">查看待办任务</span>
                    <i class="fa fa-caret-down fa-right hidden-lg hidden-md hidden-sm"></i>
                </a>
                <ul class="dropdown-menu dropdown-task dropdown-wrap">
                    <li id="header_task_nothing"><a href="javascript:;">当前无待办任务...</a></li>
                </ul>
            </li>
            <li>
                <a href="javascript:;" data-toggle="dropdown" class="dropdown-toggle">
                    <i class="fa fa-volume-up"></i>
                    <c:if test="${fn:length(announcementList)>0 }">
                        <span class="noft noft-danger">${fn:length(announcementList) }</span>
                    </c:if>
                    <c:if test="${fn:length(announcementList)<=0 }">
                        <span class="noft noft-success">${fn:length(announcementList) }</span>
                    </c:if>
                    <span class="navbar-icon-text hidden-lg hidden-md hidden-sm">查看公告</span>
                    <i class="fa fa-caret-down fa-right hidden-lg hidden-md hidden-sm"></i>
                </a>
                <ul class="dropdown-menu dropdown-menu-announcement dropdown-wrap">
                    <c:if test="${fn:length(announcementList)>0 }">
                        <c:forEach items="${announcementList }" var="announcement">
                            <li><a href="javascript:;" onclick="viewAnnouncementSingle('${announcement.announcement_id}')">${announcement.title }</a></li>
                            </c:forEach>
                        </c:if>
                        <c:if test="${fn:length(announcementList)<=0 }">
                        <li><a href="javascript:;">当前无公告信息...</a></li>
                        </c:if>
                </ul>
            </li>
            -->
        </ul>
        <div class="navbar-header-info hidden-xs hidden-sm">
            <ul>
                <li class="hidden-xs hidden-sm hidden-md">
                    <a href="javascript:;">
                        <i class="fa fa-clock-o"></i><span id="header_time_container"></span>
                    </a>
                </li>
                <li class="header-notice-container">
                    <ul>
                        <c:if test="${fn:length(announcementList)>0 }">
                            <c:forEach items="${announcementList }" var="announcement">
                                <li><a href="javascript:;" onclick="viewAnnouncementSingle('${announcement.announcement_id}')"><i class="fa fa-volume-up"></i>${announcement.title }</a></li>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${fn:length(announcementList)<=0 }">
                            <li><a href="javascript:;"><i class="fa fa-volume-up"></i>当前无公告信息...</a></li>
                            </c:if>
                    </ul>
                </li>
            
            </ul>
        </div>
        <ul class="nav navbar-nav navbar-right">
            <li>
                <a href="javascript:;" data-toggle="dropdown" class="dropdown-toggle">
                    <i class="fa fa-user"></i>您好，${sessionScope.__SESSION_USER__.user_name }<i class="fa fa-caret-down fa-right"></i>
                </a>
                <ul class="dropdown-menu dropdown-wrap">
                    <li><a href="${pageContext.request.contextPath }/core/base/assist/set_user"><i class="fa fa-file-text-o"></i>信息设置</b></a></li>
                    <!--<li><a href="${pageContext.request.contextPath }/core/base/assist/set_user_attr"><i class="fa fa-gears"></i>参数设置</b></a></li>-->
                    <!--<li><a href="${pageContext.request.contextPath }/core/base/assist/change_password"><i class="fa fa-key"></i>编辑密码</b></a></li>-->
                    <li><a href="${pageContext.request.contextPath }/core/base/assist/set_user_shortcut_limit"><i class="fa fa-magic"></i>设置快捷方式</b></a></li>
                </ul>
            </li>
            <li>
                <a href="javascript:;" data-toggle="dropdown">
                    <i class="fa fa-magic"></i>皮肤<i class="fa fa-caret-down fa-right"></i>
                </a>
                <ul class="dropdown-menu dropdown-menu-skin">
                    <c:forEach items="${skinList }" var="skin">
                        <li><a href="javascript:;" onclick="setUserSkinInfo('${skin}');"><img src="${pageContext.request.contextPath }/${skinRootPath }/small/${skin}" /></a>
                            </c:forEach>
                </ul>
            </li>
            <li>
                <a href="javascript:;" onclick="logout();"><i class="fa fa-sign-out"></i>退出</a>
            </li>
        </ul>
    </nav>
</header>