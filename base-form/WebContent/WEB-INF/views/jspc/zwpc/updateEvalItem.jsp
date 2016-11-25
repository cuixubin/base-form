<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
    <head>
        <title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 编辑</title>
        <base target="_self" />
        <%@ include file="/resources/include/plugin.jsp"%>
        <%@ include file="/resources/include/extra.jsp"%>
        <style type="text/css">
            html, body{background:none;}
            .form-group{ height: 35px;margin-top: 23px;}
            .container { width: 850px;}
            .form-control {width: 85%;}
            .col-xs-5 {width: 80%;}
            .form-control {width: 90%;}
            .valid{margin-top: 6px;color: #AFA8A8;}
        </style>
        <script type="text/javascript">
        //验证器
        var itemValidator;
        $(function(){
            itemValidator = $.fn.dlshouwen.validator.init($('#evalItem_form'));
	});
        //提交数据方法（在父级页面触发）
        function updateOneItem(){
            if(!itemValidator.validResult()){
                return false;
            }
            var url = '${pageContext.request.contextPath }/jspc/zwpc/zwpc/updateEvalItem';
            var params = $("#evalItem_form").serializeArray();
            params.push({"name": "attach", "value": $("input[name=upfile]").val()});
            sw.showProcessBar();
            sw.ajaxSubmitForForm(url, params, "upfile", function (data) {
                sw.ajaxSuccessCallback(data, function () {
                    window.parent.refreshItems(window.parent.tempExpId, window.parent.tempItemType);
                    return true;
                });
            });
            return true;
        }
        </script>
    </head>
    <body>
        <div class="container">
            <div class="row clearfix">
                <form:form cssClass="form-horizontal" id="evalItem_form" modelAttribute="evalItem" method="post" enctype="multipart/form-data">
                    <form:hidden path="evalResult_id" />
                    <form:hidden path="evalItem_id" />
                    <form:hidden path="type" />
                    <div class="col-xs-7 column" style="border-right:1px solid #ccc;">
                        <div class="form-group">
                            <label class="col-xs-2 control-label text-right" id ="starttime">开始时间：</label>
                            <div class="col-xs-5">
                                <sw:input path="beginDate" cssClass="form-control" placeholder="请输入开始日期"
                                valid="r" validTitle="开始日期" validInfoArea="beginDate_info_area" onclick="WdatePicker();" />
                                <div class="help-block" id="beginDate_info_area"></div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-xs-2 control-label text-right">结束时间：</label>
                            <div class="col-xs-5">
                                <sw:input path="endDate" cssClass="form-control" placeholder="请输入结束日期，不填写表示至今" 
                                valid="" validTitle="结束日期" validInfoArea="endDate_info_area" onclick="WdatePicker();" />
                                <div class="help-block" id="endDate_info_area"></div>
                            </div>
                        </div>
                                
                        <div class="form-group">
                            <label class="col-xs-2 control-label text-right">荣誉名称：</label>
                            <div class="col-xs-5">
                                <sw:input path="name" cssClass="form-control" placeholder="请输入荣誉名称" 
                                valid="r|5-40" validTitle="荣誉名称" validInfoArea="name_info_area" />
                                <div class="help-block" id="name_info_area"></div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-xs-2 control-label text-right">级别：</label>
                            <div class="col-xs-5">
                                <sw:select path="level" items="${applicationScope.__CODE_TABLE__.ryjb }" 
                                emptyText="请选择..." valid="r" cssClass="form-control" validTitle="级别" validInfoArea="level_info_area" />
                                <div class="help-block" id="level_info_area"></div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-xs-2 control-label text-right">等级：</label>
                            <div class="col-xs-5">
                                <sw:select path="grade" items="${applicationScope.__CODE_TABLE__.hjdj }" 
                                emptyText="请选择..." cssClass="form-control" validTitle="等级" validInfoArea="grade_info_area" />
                                <div class="help-block" id="grade_info_area"></div>
                            </div>
                        </div>
                                
                        <div class="form-group">
                            <label class="col-xs-2 control-label text-right">文件：</label>
                            <div class="col-xs-5">
                                <input id="upfile" name="upfile" type="file" />
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label class="col-xs-2 control-label text-right">详情描述：</label>
                            <div class="col-xs-5">
                                <sw:textarea path="description" rows="7" cssClass="form-control" placeholder="在此输入详情信息"
                                valid="" validTitle="详情" validInfoArea="detail_info_area" />
                                <!--<div class="help-block" id="detail_info_area"></div>-->
                            </div>
                        </div>

                    </div>
                    <div class="col-xs-4 column">
                        <h3>注意事项</h3>
                        <h4 style="margin-left:3px;line-height: 2;">
                            1.必须填写开始日期；</br>
                            2.获奖荣誉必须超过5个字符；</br>
                            3.必须上传相关文件（多个文件可打包压缩后上传）。
                        </h4>
                    </div>
                </form:form>
            </div>
        </div>
    </body>
</html>