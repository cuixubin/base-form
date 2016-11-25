<%-- 
    Document   : 基本信息单元
    Created on : 2016-7-26, 9:27:00
    Author     : cuixubin
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<style>
    .picImg img{border:2px solid gray;width:146px;height:207px;}
    .imgSetA{margin-left: 10px; margin-top: 5px;}
</style>
<script type="text/javascript">
    function initUser() {
        var keyNow = '${UVO.user.qualified}';
        var valNow = '';
        if (keyNow !== null && keyNow !== '') {
            valNow = code_table_info['jszz'][keyNow];
        }
        $('#jszzInfo').html(valNow);

        var keyHope = ${UVO.expreview.qualified};
        var valHope = '';
        if (keyHope !== null && keyHope !== '') {
            valHope = code_table_info['jszz'][keyHope];
        }
        $('#sqzzInfo').html(valHope);

        //评审信息处理
        var keyStatus = ${UVO.expreview.status};
        var varStatus = '';
        if (keyStatus == 0) {
            varStatus = '<span class="label label-warning">' + code_table_info['review_status'][keyStatus] + '</span>';
        } else if (keyStatus == 1) {
            varStatus = '<span class="label label-success">' + code_table_info['review_status'][keyStatus] + '</span>';
        } else if (keyStatus == 2) {
            varStatus = '<span class="label label-danger">' + code_table_info['review_status'][keyStatus] + '</span>';
        } else if (keyStatus == 3) {
            varStatus = '<span class="label label-default">' + code_table_info['review_status'][keyStatus] + '</span>'
        }
        $('#status').html(varStatus);

        var passStatus = '';
        var note = '${UVO.expreview.note}';
        if (keyStatus == '1' || keyStatus == '2') {
            if (note == null || note == "") {
                passStatus += '<div class="form-group">'
                passStatus += '<div class="col-sm-9 control-label text-left">评测意见：<span>无</span> </div>'
                passStatus += '</div>'
            } else {
                passStatus += '<div class="form-group">'
                passStatus += '<div class="col-sm-9 control-label text-left">评测意见：<span>' + note + '</span> </div>'
                passStatus += '</div>'
            }

            passStatus += '<div class="form-group">'
            passStatus += '<div class="col-sm-9 control-label text-left">评测专家：<span>' + '${UVO.expreview.review_name}' + '</span> </div>'
            passStatus += '</div>'

            var createTime = '${UVO.expreview.review_date}';
            createTime = createTime.substring(0, 19);
            passStatus += '<div class="form-group">'
            passStatus += '<div class="col-sm-9 control-label text-left">最近评测时间：<span>' + createTime + '</span> </div>'
            passStatus += '</div>'
        }
        $('#passStatus').html(passStatus);

        //未提交申请时不显示评审信息
        if (keyStatus == 4) {
            $('#expStatu').hide();
            $('#passStatus').hide();
        }

        //加载用户照片
        var ipath = '${UVO.user.imgpath}';
        if (null == ipath || '' == ipath) {
            $("#picImg").html('<img src="${pageContext.request.contextPath }/image/default_user_img.jpg" alt="个人照片">');
        } else {
            $("#picImg").html('<img src="${applicationScope.__SYSTEM_ATTRIBUTE__.source_webapp_file_postion.content }${UVO.user.imgpath}" alt="个人照片">');
        }
    }
    $(function () {
        //基本信息中教师资质和申请资质从码表获取
        if ('${UVO.user}' != '') {
            initUser();
        }
    });
</script>
<!DOCTYPE html>
<div class="col-sm-12 mybgstyle" id="section1">
    <div class="col-sm-12 itemTitle">
        <div class="col-sm-6 text-left">
            <a name="JBXX_INDEX"><span>基本信息</span></a>
        </div>
        <div class="col-sm-6 text-right">
            <a href="${applicationScope.__SYSTEM_ATTRIBUTE__.source_webapp_file_postion.content }/basefiles/standard.zip" style="color:orange"><span>评测标准下载</span></a>
        </div>
    </div>
    <hr style="margin-bottom: 30px;">

    <c:choose>
        <c:when test="${UVO.user!=null }">
            <div class="col-sm-9 control-label text-left itemContent">
                <div class="form-group" >
                    <div class="col-sm-9 control-label text-left">姓名：<span>${UVO.user.user_name}</span> </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-9 control-label text-left">出生日期：<span>${UVO.user.birthday}</span></div>
                </div>

                <div class="form-group">
                    <div class="col-sm-9 control-label text-left">Email：<span>${UVO.user.email}</span></div>
                </div>

                <div class="form-group">
                    <div class="col-sm-9 control-label text-left">教师资质：<span id="jszzInfo"></span>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-9 control-label text-left">工作年限：<span>${UVO.workYears}</span></div>
                </div>

                <div class="form-group">
                    <div class="col-sm-9 control-label text-left">居住地：<span>${UVO.user.address}</span> </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-9 control-label text-left">毕业院校：<span>${UVO.user.graduateSchool}</span> </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-9 control-label text-left">所属团队：<span>${UVO.user.team_name}</span> </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-9 control-label text-left">我的意愿：<span id="sqzzInfo"></span> </div>
                </div>

                <div class="form-group" id="expStatu">
                    <div class="col-sm-9 control-label text-left">评审状态：<span id="status"></span> </div>
                </div>
                <div id="passStatus">

                </div>
            </div>
            <!--图片 -->
            <div class="col-sm-3 userImg text-left">
                <div class="picImg" id="picImg"></div>
            </div>
        </c:when>
        <c:otherwise>
            <h3 class="text-center" style="margin-bottom:30px">该用户已被管理员删除</h3>
        </c:otherwise>
    </c:choose>   

</div>
