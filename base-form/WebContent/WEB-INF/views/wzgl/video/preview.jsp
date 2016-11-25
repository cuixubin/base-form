<%--
 *XCYG TECHNOLOGY CO., LTD. PROPRIETARY INFORMATION
 * 
 * This software is supplied under the terms of a license agreement or
 * nondisclosure agreement with Mylab Healthcare Technology Co., Ltd. and may not 
 * be copied or disclosed except in accordance with the terms of that agreement.
 * 
 * Copyright (c) 2015-present XCYG Technology Co., Ltd. All Rights Reserved.
 * 
 * 版权所有 (c) 新晨阳光科技（北京）有限公司
 * 

    Document   : uploadPage
    Created on : 2015-8-17, 14:18:58
    Author     : yangtong
--%>

<%@page contentType="text/html" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href='${pageContext.request.contextPath }/resources/plugin/ueditor/third-party/video-js/video-js.min.css' rel='stylesheet' type='text/css' />
        <script src="${pageContext.request.contextPath }/resources/plugin/ueditor/third-party/video-js/video.js"></script>
        <title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 视频预览</title>
       
    </head>
    <body>
        <video id="sample_video" preload="none" style="margin-left: 2px"  class="video-js vjs-default-skin vjs-big-play-centered" data-setup='{ "controls": true, "autoplay": false, "preload": "none", "poster": "${applicationScope.__SYSTEM_ATTRIBUTE__.source_webapp_file_postion.content }${imagePath}", "width": 550, "height": 450 }'>
            <source src="${applicationScope.__SYSTEM_ATTRIBUTE__.source_webapp_file_postion.content }${videoPath}" type='video/flv' />
            <!-- 如果浏览器不兼容HTML5则使用flash播放 -->
            <object id="sample_video" class="vjs-flash-fallback" width="852"
                    height="480" type="application/x-shockwave-flash"
                    data="${pageContext.request.contextPath}/js/video-js/video-js.swf">
                <param name="movie"
                       value="${pageContext.request.contextPath}/js/video-js/video-js.swf" />
                <param name="allowfullscreen" value="true" />
                <param name="flashvars" 
                       value='config={"playlist":["${applicationScope.__SYSTEM_ATTRIBUTE__.source_webapp_file_postion.content }${imagePath}", 
                       {"url": "${applicationScope.__SYSTEM_ATTRIBUTE__.source_webapp_file_postion.content }${videoPath}",
                       "autoPlay":false,"autoBuffering":true}]}' />
                <!-- 视频图片. -->
                <img src="${applicationScope.__SYSTEM_ATTRIBUTE__.source_webapp_file_postion.content }${imagePath}" width="852"
                     height="480" alt="Poster Image"
                     title="No video playback capabilities." />
            </object>
        </video>
    </body>
</html>