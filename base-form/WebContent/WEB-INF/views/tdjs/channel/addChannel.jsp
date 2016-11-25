<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
    <head>
        <title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 新增栏目</title>
        <base target="_self" />
        <%@ include file="/resources/include/plugin.jsp"%>
        <%@ include file="/resources/include/extra.jsp"%>
        <script type="text/javascript">
            //定义返回URL
            var goBackUrl = '${pageContext.request.contextPath }/tdjs/tdjsChannel/channel';
            //返回
            function goBack() {
                sw.showProcessBar();
                window.location.href = goBackUrl;
            }
            //定义验证对象
            var channelValidator;
            //新增栏目
            function addChannel() {
                if (!channelValidator.validResult()) {
                    return;
                }
                if (!confirm('确定新增此栏目吗？')) {
                    return;
                }
                var url = '${pageContext.request.contextPath }/tdjs/tdjsChannel/channel/add';
                var params = sw.getFormParams(document.forms[0]);
                sw.showProcessBar();
                sw.ajaxSubmit(url, params, function (data) {
                    sw.ajaxSuccessCallback(data, function () {
                        channelValidator.reset(true);
                    });
                });
            }

            function selectTeam(){
                var url = '${pageContext.request.contextPath}/core/team/selectOneTeam';
                sw.openModal({
                    id: 'selectTeam',
                    icon: 'group',
                    title: '选择团队',
                    url: url,
                    callback: function (teams) {
                        var teamId = '';
                        var teamName = '';
                        if (teams[0]) {
                            teamId = teams[0].team_id;
                            teamName = teams[0].team_name;
                        }
                        $('#team_id').val(teamId);
                        $('#team_name').val(teamName);
                        sw.closeModal('selectTeam');
                    }
                });

            }


            //初始化表单验证
            $(function () {
                channelValidator = $.fn.dlshouwen.validator.init($('#channelForm'));
            });
            //绑定方法
            $(function () {
                $('#add_channel').bind('click', addChannel);
                //栏目的默认开启状态  开启
                $('#channel_state').attr('checked', true);
            });
        </script>
    </head>
    <body>
        <jsp:include page="/core/base/layout/header" />
        <jsp:include page="/core/base/layout/left" />
        <jsp:include page="/core/base/layout/shortcut" />
        <div class="main-container">
            <ul class="breadcrumb">
                <li><a href="${pageContext.request.contextPath }/core/base/home"><i class="fa fa-home"></i>首页</a></li>
                <li><a href="javascript:void(0);"><i class="fa fa-group"></i>团队建设</a></li>
                <li><a href="javascript:void(0);"><i class="fa fa-book"></i>基础管理</a></li>
                <li><a href="${pageContext.request.contextPath }/tdjs/tdjsChannel/channel"><i class="fa fa-navicon"></i>栏目管理</a></li>
                <li class="active"><a href="javascript:void(0);"><i class="fa fa-group"></i>新增栏目</a></li>
            </ul>
            <div class="panel panel-default">
                <div class="panel-heading"><i class="fa fa-plus"></i>新增栏目</div>
                <div class="panel-body">
                    <form:form id="channelForm" cssClass="form-horizontal" modelAttribute="channel" method="post">
                        <form:hidden path="channel_parent" />


                        <div class="form-group">
                            <label class="col-sm-2 control-label text-right">栏目名称：</label>
                            <div class="col-sm-4">
                                <sw:input path="channel_name" cssClass="form-control" placeholder="请输入栏目名称" valid="r|l-l40" validTitle="栏目名称" validInfoArea="channel_name_info_area"/>
                            </div>
                            <div class="col-sm-6"><p class="help-block" id="channel_name_info_area"></p></div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label text-right">所属团队：</label>
                            <div class="col-sm-4">
                                <div class="input-group">
                                    <form:hidden path="team_id"/>
                                    <sw:input path="team_name" cssClass="form-control" readonly="true" 
                                              valid="r" validTitle="所属团队" validInfoArea="team_id_info_area" onclick="selectTeam('team_id', 'team_name');"/>
                                    <span class="input-group-addon" onclick="selectTeam('team_id', 'team_name');"><i class="fa fa-group"></i></span>
                                </div>
                            </div>
                            <div class="col-sm-3"><p class="help-block" id="team_id_info_area"></p></div>
                        </div>


                        <div class="form-group">
                            <label class="col-sm-2 control-label text-right">栏目类型：</label>
                            <div class="col-sm-4">
                                <sw:select path="channel_type" items="${applicationScope.__CODE_TABLE__.channel_type }" 
                                           valid="r" cssClass="form-control" validTitle="栏目类型" validInfoArea="channel_type_info_area" />
                            </div>
                            <div class="col-sm-6"><p class="help-block" id="channel_type_info_area"></p></div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label text-right">栏目排序：</label>
                            <div class="col-sm-4">
                                <sw:input path="channel_seq" cssClass="form-control" placeholder="请输入栏目排序（数字输入）" valid="|number" validTitle="栏目排序" validInfoArea="channel_seq_info_area"/>
                            </div>
                            <div class="col-sm-6"><p class="help-block" id="channel_seq_info_area"></p></div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label text-right">栏目状态：</label>
                            <div class="col-sm-4">
                                <sw:radiobuttons path="channel_state" items="${applicationScope.__CODE_TABLE__.open_close }" 
                                                 valid="r" validTitle="栏目状态" validInfoArea="channel_state_info_area" />
                            </div>
                            <div class="col-sm-6"><p class="help-block" id="channel_state_info_area"></p></div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label text-right">栏目路径：</label>
                            <div class="col-sm-4">
                                <sw:input path="channel_url" cssClass="form-control" placeholder="请输入栏目的路径" valid="|l-l200" validTitle="栏目路径" validInfoArea="channel_url_info_area"/>
                            </div>
                            <div class="col-sm-6"><p class="help-block" id="channel_url_info_area"></p></div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label text-right">栏目描述：</label>
                            <div class="col-sm-4">
                                <sw:textarea path="channel_description" cssClass="form-control" placeholder="请输入栏目描述" valid="l-l2000" validTitle="栏目描述" validInfoArea="channel_description_info_area" />
                            </div>
                            <div class="col-sm-6"><p class="help-block" id="channel_description_info_area"></p></div>
                        </div>

                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <sw:button accesskey="S" id="add_channel" cssClass="btn btn-success" value="保存 (S)" icon="save" limit="tdjs/tdjsChannel/channel/add" />
                                <button accesskey="B" type="button" class="btn btn-default" onclick="goBack();">
                                    <i class="fa fa-arrow-circle-left"></i>返回 (B)
                                </button>
                            </div>
                        </div>
                    </form:form>
                </div>
            </div>
            <div class="clearfix"></div>
        </div>
    </body>
</html>
