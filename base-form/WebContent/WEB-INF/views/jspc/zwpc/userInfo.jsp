<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
    <head>
        <title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 查看</title>
        <base target="_self" />
        <%@ include file="/resources/include/plugin.jsp"%>
        <%@ include file="/resources/include/extra.jsp"%>
        <style type="text/css">
            html, body{background:none;}
            .form-group{ height: 20px;margin-top: 23px;font-size: 14px}
            .container { width: 850px;}
            .form-control {width: 85%;}
            .col-xs-5 {width: 80%;}
            .form-control {width: 90%;}
            .reply{border-bottom: 1px solid #ccc; width: 90%; display: block;height: 27px;line-height: 27px; disabled:true}
        </style>
    </head>
    <body>
        <div class="container">
            <div class="row clearfix">
                <div class="col-xs-7 column" >
                    <div class="form-group">
                        <label class="col-xs-2 control-label text-right">姓名：</label>
                        <div class="col-xs-5">
                            <span class="reply">张三</span>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-2 control-label text-right">出生年月：</label>
                        <div class="col-xs-5">
                            <span class="reply">1978年8月23日</span>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-2 control-label text-right">Email：</label>
                        <div class="col-xs-5">
                            <span class="reply">52544565@qq.com</span>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-2 control-label text-right">工作年限：</label>
                        <div class="col-xs-5">
                            <span class="reply">15年</span>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-2 control-label text-right">居住地：</label>
                        <div class="col-xs-5">
                            <span class="reply">北京市海淀区东大街</span>
                        </div>
                    </div>


                    <div class="form-group">
                        <label class="col-xs-2 control-label text-right">毕业院校：</label>
                        <div class="col-xs-5" style="">
                            <!--                                <input type="text" disabled class="form-control" id="user_school_info_area" />-->
                            <span class="reply">中国传媒大学</span>
                        </div>
                    </div>


                    <div class="form-group">
                        <label class="col-xs-2 control-label text-right">所属团队：</label>
                        <div class="col-xs-5">
                            <select class="form-control" id="user_gread_info_area" name="user_team_info_area" style="font-size: 14px;">
                                <option value="">请选择</option>
                                <option value="0">未来之星</option>
                                <option value="1">明日骨干</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-2 control-label text-right">教师资质：</label>
                        <div class="col-xs-5">
                            <select class="form-control" id="user_gread_info_area" name="user_teach_info_area" style="font-size: 14px;">
                                <option value="">请选择</option>
                                <option value="0">省级</option>
                                <option value="1">市级</option>
                            </select>
                        </div>
                    </div>

                </div>
                <div class="col-xs-4 column text-center">
                    <a href="index.html"><img src="${pageContext.request.contextPath }/image/name.jpg" alt="" style="border:3px solid;width:147px;height:206px;margin-top: 48px;margin-left: 42px;"></a>
                </div>
            </div>
        </div>
    </body>
</html>