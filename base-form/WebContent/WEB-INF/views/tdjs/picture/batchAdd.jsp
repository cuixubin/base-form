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
        <title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 添加</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/js/wzgl/uploadPicture/webuploader.css" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/js/wzgl/uploadPicture/style.css" />
    <script type="text/javascript">
        var contextPath = '${pageContext.request.contextPath }';
        var closeWinId = "albumId";
        
    </script>
    </head>
        
    <body>
        <div class="row">
            <div class="text-center">
                 <div class="col-lg-12 text-center" >
                    <!--代码-->
                    <div id="uploader">
                        <div class="queueList">
                            <div id="dndArea" class="placeholder">
                                <div id="filePicker"></div>
                                <p>或将照片拖到这里，单次最多可选300张</p>
                            </div>
                        </div>
                        <div class="statusBar" style="display:none;">
                            <div class="progress">
                                <span class="text">0%</span>
                                <span class="percentage"></span>
                            </div><div class="info"></div>
                            <div class="btns">
                                <div id="filePicker2"></div><div class="uploadBtn">开始上传</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
      <script>
        var uploadUrl="${pageContext.request.contextPath }/tdjs/tdjsPicture/picture/${albumId}/ajaxMultipartAdd";
    </script>  
     <script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugin/jquery/jquery.min_1.9.1.js"></script> 
     <script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/wzgl/uploadPicture/webuploader.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/wzgl/uploadPicture/upload.js"></script> 
    </body>
</html>
