<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 打开相册</title>
        <base target="_self" />
        <%@ include file="/resources/include/plugin.jsp"%>
        <%@ include file="/resources/include/extra.jsp"%>
        <style>
             .page-loading{  
                width:180px;  
                height:66px;  
                position: fixed;  
                top:50%;  
                left:50%;  
                line-height:56px;  
                color:blorde;  
                padding-left:70px;  
                background: #CCCCCC url(${pageContext.request.contextPath }/image/loading.gif) no-repeat 5px 50%;  
                opacity: 0.7;  
                z-index:9999;  
                -moz-border-radius:20px;  
                -webkit-border-radius:20px;  
                border-radius:20px;  
                filter:progid:DXImageTransform.Microsoft.Alpha(opacity=70);  
                margin:-28px 0 0 -80px;
            }  
            * {margin:0; padding:0;list-style: none}
            #ul1 {width:100%;height: auto; position:relative; margin:10px auto;}
            #ul1 li {width:200px;float:left; list-style:none; margin:10px;padding-top:5px;padding-bottom:5px;height:110px;}
            #ul1 li:hover{ border-color: #9a9fa4; box-shadow: 0 0 6px 0 rgba(0, 0, 0, 0.85);}
            #ul1 .active{ border:1px dashed red;}
            .a_Img{position:absolute;right:5px;top:5px;z-index:999;}
            .out_Div{position:relative; text-align:center}
        </style>
        <script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/zyUpload/tuozhuai.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/zyUpload/move.js"></script>
        <script>
             window.onload = function () {
                //移除openAlbum.jsp中的拖拽层
                $("div #loading").remove();
                $("div .zhezhao").remove();
            };
            //设置ul1的高度
            $(function () {
                var l = ($('#ul1 li').length);
                m = Math.floor(($('#ul1').width() - 20) / 220);
                w = Math.ceil(l / m) * 135
                $('#ul1').css('height', w + 'px');
            })

            //上传保存 按钮触发的事件 弹出页面
            function addPicture(album_id) {
                if (album_id !== null && 　album_id !== "") {
                    var url = '${pageContext.request.contextPath }/wzgl/picture/picture/' + album_id + '/batchAdd';
                    sw.openModal({
                        id: 'albumId',
                        icon: 'bell',
                        title: "添加",
                        url: url,
                        modalType: 'lg',
                        showCloseButton: true,
                        //关闭模态的回调方法
                        closeCallback: function () {

                        }
                    });
                }
            }
            ;
            //
            function editPic(pictureId, albumId, albCoverPath) {
                //删除图片
                if (pictureId != null && pictureId != "") {
                    var url = '${pageContext.request.contextPath }/wzgl/picture/picture/' + pictureId + '/editAlbPic';
                    sw.openModal({
                        id: 'editAlbPic',
                        icon: 'eye',
                        title: '预览',
                        url: url,
                        modalType: 'lg',
                        showCloseButton: false,
                        showCoverButton: true,
                        callback: function () {
                            if ((this.id).indexOf("deleteModel") != -1) {
                                alert("您确定要删除此图片吗？");
                                var url = '${pageContext.request.contextPath }/wzgl/picture/picture/delete';
                                $.ajax({
                                    type: "POST",
                                    url: url,
                                    data: {pictureIds: pictureId},
                                    success: function (data) {
                                        window.location.reload();
                                    },
                                    error: function (e, e1) {
                                        document.getElementById("editAlbPicFrame").contentWindow.messageTip(e1);
                                    }
                                });
                            }
                            ;
                            //设为封面
                            if ((this.id).indexOf("coverModel") != -1) {
                                var url = '${pageContext.request.contextPath }/wzgl/album/album/updateAlbCover';
                                $.ajax({
                                    type: "post",
                                    url: url,
                                    data: {albumId: albumId, albCoverPath: albCoverPath},
                                    dataType: "json",
                                    success: function (data) {
                                        if (data.success == true) {
                                            alert(data.successMessage);
                                        }
                                    }, error: function (e1, e2) {
                                    }
                                });
                            }
                            ;

                        }
                    });
                }
            }

            
            //图片排序
            function savePicOrder() {
                var myIds = $("li[name=theli]");
                var ids = "";
                var position = "";
                if (undefined !== myIds && "" !== myIds) {
                    for (var i = 0; i < myIds.length; i++) {
                        ids += myIds[i].id + ",";
                        position += myIds[i].index + ",";
                    }
                }
                var url = '${pageContext.request.contextPath }/wzgl/picture/picture/updatePicOrder';
                $.ajax({
                    type: "post",
                    url: url,
                    data: {picIds: ids, order: position},
                    success: function (data) {
                        alert("图片排序成功!");
                    },
                    error: function (e1, e2) {
                    }
                });
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
                <li><a href="javascript:void(0);"><i class="fa fa-book"></i>基础管理</a></li>
                <li class="active"><a href="${pageContext.request.contextPath }/wzgl/article/article"><i class="fa fa-file-o"></i>文章管理</a></li>
                <li class="active"><a href="javascript:void(0);"><i class="fa fa-plus"></i>打开文章相册</a></li>
            </ul>
            <div class="panel panel-default">
                <div class="panel-heading"><i class="fa fa-plus"></i>打开相册</div>
                <div class="panel-body">
                    <div class="row" >
                        <!--加载中 -->
                        <div id="loading" class="page-loading" > 页面正在加载中........</div>  
                        <div class="row" style="position:relative;" >
                        <!-- 遮照层-->
                        <div class="zhezhao" style="position:absolute;background:#F2F2F2;width: 100%;height:100%;z-index: 1000;"></div>
                        <!--第一张图片 -->
                        <ul id="ul1">    
                            <c:forEach items="${pic}" var="pic">
                                <li class="col-lg-3 col-sm-4 col-xs-6" name="theli" id="${pic.picture_id}">
                                    <div class="out_Div" >
                                        <a class="a_Img">
                                            <img src="${pageContext.request.contextPath }/image/openAlbum.png" onclick="editPic('${pic.picture_id}', '${album.album_id}', '${pic.path}')" style="cursor:pointer" /></a>
                                        <a href="javascript:void(0)" style="text-align:center;width:190px;height:100px;display:block;background-color:#ececec;">
                                            <img src="${applicationScope.__SYSTEM_ATTRIBUTE__.source_webapp_file_postion.content }${pic.path}" class="img-responsive" style="height: 100px;display:inline-block;" >
                                        </a>
                                    </div>
                                </li>
                            </c:forEach>

                        </ul>
                    </div>
                    <div class="row">
                        <!--提交按钮 -->
                        <div class="text-center">
                            <sw:button accesskey="S" id="add_picture" cssClass="btn btn-success" value="上传 (S)" icon="save" limit="wzgl/album/picture/batchAdd" onclick="addPicture('${album.album_id}')"/>
                            <sw:button accesskey="C" id="sort_picture" cssClass="btn btn-success" value="确认排序 (C)" icon="sort" limit="wzgl/picture/picture/updatePicOrder" onclick="savePicOrder()"/>
                        </div>
                    </div>
                </div>
                <div class="clearfix"></div>
            </div>
    </body>
</html>
