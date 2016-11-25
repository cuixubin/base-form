<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
    <head>
        <title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } -评测申请</title>
        <base target="_self" />
        <%@ include file="/resources/include/plugin.jsp"%>
        <%@ include file="/resources/include/extra.jsp"%>
        <style>
            .sm_contact{text-align: left;font-size: 15px;padding: 17px;line-height: 2em;}    
            .center_content{margin-left: 2em;}
        </style>
        <script type="text/javascript">
        var temv;
        var myValidator;
        $(function(){
            $('#start_apply').bind('click', startApply);
            myValidator = $.fn.dlshouwen.validator.init($('#startForm'));
	});
        function startApply() {
            if(!myValidator.validResult()){
                return;
            }
            var url = '${pageContext.request.contextPath }/jspc/zwpc/zwpc/'+temv+'/application';
            sw.showProcessBar();
            window.location.href = url;
        }
        function selCheck() {
            if($('#myCheck').is(':checked')) {
                $('#start_apply').removeAttr("disabled");
            }else {
                $('#start_apply').attr("disabled", "disabled");
            }
        }    
        function setValue(obj) {
            temv = obj.value;
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
                <li><a href="javascript:void(0);"><i class="fa fa-graduation-cap"></i>教师评测</a></li>
                <li class="active"><a href="javascript:void(0);"><i class="fa fa-male"></i>自我评测</a></li>
            </ul>

            <div class="panel panel-default">
                <!--<div class="panel-heading"><i class="fa fa-navicon"></i><h3>免责声明</h3></div>-->
                <div class="panel-operation">
                    <h3 style="text-align: center">免责声明</h3>
                    <div class="sm_contact"><p style="text-indent: 2em">访问者在接受本网站服务之前，请务必仔细阅读本条款并同意本声明。
                        访问者访问本网站的行为以及通过各类方式利用本网站的行为，都将被视作是对本声明全部内容的无异议的认可;如有异议，
                        请立即跟本网站协商，并取得本网站的书面同意意见。<br/>
                        第一条访问者在从事与本网站相关的所有行为(包括但不限于访问浏览、利用、转载、宣传介绍)时，
                        必须以善意且谨慎的态度行事;访问者不得故意或者过失的损害或者弱化本网站的各类合法权利与利益，
                        不得利用本网站以任何方式直接或者间接的从事违反中国法律、国际公约以及社会公德的行为，且访问者应当恪守下述承诺：<br/>
                        </p>
                        <div class="center_content">
                        1、传输和利用信息符合中国法律、国际公约的规定、符合公序良俗;<br/>
                        2、不将本网站以及与之相关的网络服务用作非法用途以及非正当用途;<br/>
                        3、不干扰和扰乱本网站以及与之相关的网络服务;<br/>
                        4、遵守与本网站以及与之相关的网络服务的协议、规定、程序和惯例等。<br/>
                        </div>
                        </div>
                    
                    <!--复选框开始 -->
                    <div class="text-left" style="font-size: 15px;padding: 17px">
                        <input type="checkbox" name="checkbox" id="myCheck" value="0" onclick="selCheck()" class="check_confirm" /> 我已阅读并同意以上声明！
                    </div>  
                    <!--中间空白 -->
                    <p style="height: 88px;" ></p>
                     <!-- 下拉框-->   
                    <div class="text-center" style="font-size: 15px">
                      <form:form class="form-horizontal" id="startForm" modelAttribute="expreview" method="post">
                            <div class="form-group">
                                <label class="col-sm-4 control-label text-right">我的意愿：</label>
                                <div class="col-sm-4 select_confirm" > 
                                    <sw:select path="qualified" items="${applicationScope.__CODE_TABLE__.jszz }"
					emptyText="请选择..." onChange="setValue(this)" valid="r" cssClass="form-control" validTitle="所要教师资质" validInfoArea="jszz_info_area" />
                                </div>
                                <div class="col-sm-4 text-left"><p class="help-block" id="jszz_info_area"></p></div>
                            </div>
                            <div class="form-group" style=" margin-top: 30px; margin-bottom: 20px;">
                                <div class="col-sm-12 text-center">
                                    <sw:button accesskey="S" id="start_apply" disabled="disabled" cssClass="btn btn-success" value="开始填写" limit="jspc/zwpc/zwpc/application" />
                                </div>
                            </div>
                      </form:form>
                    </div>
                </div>
            </div>
        </div>      
    </body>
</html>