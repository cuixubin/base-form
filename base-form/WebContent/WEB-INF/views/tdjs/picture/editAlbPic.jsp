<%-- 
    Document   : batchAdd
    Created on : 2016-8-2, 11:16:05
    Author     : admin
--%>

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 预览</title>
        <script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/jquery/jquery.min.js"></script>
        <script type="text/javascript">
            function messageTip(message) {
                alert(message);
            }
            function initBind() {
                $("#${pic.picture_id}").keydown(function (event) {
                    if (event.keyCode == 13) {
                        saveData(this.id,this.value)
                    }
                });
            }
            function saveData(picId, pic_description) {
                var url = '${pageContext.request.contextPath }/tdjs/tdjsPicture/picture/editDescription';
                $.ajax({
                    type: "post",
                    url: url,
                    data: {picId:picId,pic_description:pic_description},
                    success: function (data) {
                        if(data.success == true){
                            alert(data.successMessage);
                        }
                    },
                    error: function (e, e1) {
                        alert("修改描述不成功");
                    }
                });
            }
            $(function () {
                initBind();
            })
        </script>
    </head>

    <body>
        <div class="row">
            <div class="text-center" style="text-align: center;">
                <img src="${applicationScope.__SYSTEM_ATTRIBUTE__.source_webapp_file_postion.content }${pic.path}" style="height:340px"/>
                <div id="picEval">
                    图片描述: 
                    <input id="${pic.picture_id}" type="text" value="${pic.description}" style="border: hidden;height:19px; width: 750px;">
                </div>
            </div>
        </div>
    </body>
</html>
