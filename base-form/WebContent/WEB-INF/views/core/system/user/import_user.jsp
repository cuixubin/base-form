<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 导入用户</title>
        <base target="_self" />
        <%@ include file="/resources/include/plugin.jsp"%>
        <%@ include file="/resources/include/extra.jsp"%>
        <style type="text/css">
            html, body{background:none;}
            .mustfill{color:red;}
        </style>
        <script>
            function sendForm() {
                if($('#upfile').val() == null || $('#upfile').val() == '') {
                    alert("请先选择要导入的文件");
                    return false;
                }
                //判断当前字符串是否以str结束  
                if (typeof String.prototype.endsWith != 'function') {  
                    String.prototype.endsWith = function (str){  
                        return this.slice(-str.length) == str;  
                    };  
                } 
                if(!$('#upfile').val().endsWith("xls")){
                    alert("请选择97-2003版本的Excel文件（后缀名为.xls）");
                    return false;
                }
                var url = '${pageContext.request.contextPath }/core/system/user/importUserData';
                var params = $("#data_form").serializeArray();
                sw.showProcessBar();
                sw.ajaxSubmitForForm(url, params, "upfile", function (data) {
                    sw.ajaxSuccessCallback(data, function () {
                        window.parent.refreshUserList();
                        return true;
                    });
                });
                return true;
            }
        </script>
    </head>
    <body>
        <form:form cssClass="form-horizontal" id="data_form" method="post" enctype="multipart/form-data">
        <div style="margin-left: 20px;margin-top:20px;margin-right: 20px">
            <div class="form-group">
                <label class="col-xs-2 control-label text-right"><span class="mustfill">*</span>选择文件：</label>
                <div class="col-xs-10">
                    <input id="upfile" name="upfile" type="file" />
                </div>
            </div>
            <div  class="form-group">
                <h2  class="col-xs-12" style="text-align:center;color:red;margin-top: 20px;">温馨提示</h2>
                <h4  class="col-xs-12" style="line-height: 2; font-weight: 500;">
                    1. 请先下载<a href="${pageContext.request.contextPath }/core/system/user/getTemplet" 
                            style="font-size:15px; font-weight: bold;">导入模板</a>
                            ，按照模板给定字段填写用户信息；</br>
                            2. 模板中<span class="mustfill">编号、姓名、证件号、电话</span>和<span class="mustfill">邮箱</span>字段为必填；</br>
                    3. <span class="mustfill">编号为用户的登录账号，不允许存在重复</span>(可用教职工号或证件号作为编号)；</br>
                    4. 日期字段的数据请按以下两种格式填写：2016-10-10或2016/10/10；</br>
                    5. 模板允许导入的记录数默认为<span class="mustfill">500条</span>，该数值可在系统管理的参数管理中修改；</br>
                    6. 建议您使用2007版Excel进行数据编辑，如未安装请在此<a href="${applicationScope.__SYSTEM_ATTRIBUTE__.source_webapp_file_postion.content }/office/MicrosoftOffice2007.ZIP" 
                            style="font-size:15px; font-weight: bold;">下载</a>。
                </h4>
            </div>
        </div>
        </form:form>
    </body>
</html>
