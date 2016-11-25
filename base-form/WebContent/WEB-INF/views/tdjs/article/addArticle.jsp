<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
    <head>
        <title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 新增文章</title>
        <base target="_self" />
        <%@ include file="/resources/include/articlePlugin.jsp"%>
        <%@ include file="/resources/include/extra.jsp"%>
        <script type="text/javascript">
            //定义全局的团队id
            var tempTeamId = "";
            var contextPath = '${pageContext.request.contextPath }';
            //定义返回URL
            var goBackUrl = '${pageContext.request.contextPath }/tdjs/tdjsArticle/article';
            //返回
            function goBack() {
                sw.showProcessBar();
                window.location.href = goBackUrl;
            }
            //绑定方法
            $(function () {
                $('#add_article').bind('click', addArticle);
                //文章的默认开启状态  开启
                $('#status2').attr('checked', true);
                //文章默认 不置顶
                $('#topset1').attr('checked', true);
            });
            //定义验证对象
            var articleValidator;
            //新增文章
            function addArticle() {
                if (!articleValidator.validResult()) {
                    return;
                }
                if (!confirm('确定新增此文章吗？')) {
                    return;
                }
                var url = '${pageContext.request.contextPath }/tdjs/tdjsArticle/article/add';
                var params = sw.getFormParams(document.forms[0]);
                //判断文章摘要里边是否为空
                if (params.tabloid == null || params.tabloid == '') {
                    var tabloid = "" + ue.getContentTxt();
                    if (tabloid.length > 200) {
                        tabloid = tabloid.substring(0, 200);
                    }
                    params.tabloid = tabloid;
                }
                //文章内容
                params.content = ue.getContent();
                sw.showProcessBar();
                sw.ajaxSubmit(url, params, function (data) {
                    sw.ajaxSuccessCallback(data, function () {
                        $(".uploadBtn").trigger("click");
                    });
                });
            }

            //存为草稿
            function goDraft() {
                if (!articleValidator.validResult()) {
                    return;
                }
                if (!confirm('确定新增此文章吗？')) {
                    return;
                }
                var url = '${pageContext.request.contextPath }/tdjs/tdjsArticle/article/saveDraft';
                var params = sw.getFormParams(document.forms[0]);
                params.content = ue.getContent();
                sw.showProcessBar();
                sw.ajaxSubmit(url, params, function (data) {
                    sw.ajaxSuccessCallback(data, function () {
                        $(".uploadBtn").trigger("click");
                    });
                });
            }



            function initUE() {
                ue = UE.getEditor('content', {
                    //这里可以选择自己需要的工具按钮名称,此处仅选择如下五个
                    toolbars: [['undo', 'redo', '|',
                            'bold', 'italic', 'underline', 'formatmatch', 'strikethrough', '|',
                            'forecolor', 'backcolor',
                            'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
                            'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|',
                            'simpleupload', 'insertvideo', 'fontfamily', //字体
                            'fontsize', '|', 'attachment', 'link', 'unlink', '|',
                            'highlightcode', 'horizontal', 'date', 'spechars', '|', 'autotypeset',
                            'searchreplace', 'mathassist', 'wordimage', 'source', 'fullscreen'
                        ]],
                    //focus时自动清空初始化时的内容
                    autoClearinitialContent: false,
                    //关闭字数统计
                    wordCount: false,
                    //关闭elementPath
                    elementPathEnabled: false,
                    sourceEditorFirst: false,
                    enterTag: ''
                });

                var article_id = '${article.article_id}';
                //对编辑器的操作最好在编辑器ready之后再做
                UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
                UE.Editor.prototype.getActionUrl = function (action) {
                    var theUrl = contextPath + '/uploadPic?articleId=' + article_id;
                    if (action === 'uploadimage') {
                        return theUrl;
                    } else if (action === 'uploadfile') {
                        return theUrl + '&isFile=file';
                    } else if (action === 'uploadvideo') {
                        return theUrl + '&isVideo=video';
                    } else {
                        return this._bkGetActionUrl.call(this, action);
                    }
                };
            }
            //初始化UEditor
            var ue;
            $(function () {
                articleValidator = $.fn.dlshouwen.validator.init($('#articleForm'));
                $('#content').height('400px');
                initUE();
            });

            //选择栏目
            function selectChannel() {
                //如果teamId为空的话，给他一个默认的假数据
                if (tempTeamId == "") {
                    tempTeamId = "1";
                }
                var url = '${pageContext.request.contextPath }/tdjs/tdjsChannel/channel/' + tempTeamId + '/selectChannel';
                sw.openModal({
                    id: '',
                    icon: 'navicon',
                    title: '选择栏目',
                    url: url,
                    callback: function (channels) {
                        var channelId = '';
                        var channelName = '';
                        if (channels[0]) {
                            channelId = channels[0].channel_id;
                            channelName = channels[0].channel_name;
                        }
                        $('#channel_id').val(channelId);
                        $('#channel_name').val(channelName);
                        $('#channel_name').trigger("blur");
                        sw.closeModal('');
                    }
                });
            }
            //所属团队
            function selectTeam() {
                var url = '${pageContext.request.contextPath}/core/team/selectOneTeam';
                sw.openModal({
                    id: 'selectTeam',
                    icon: 'building',
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
                        $('#team_name').trigger("blur");
                        tempTeamId = teamId;
                        sw.closeModal('selectTeam');
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
                <li><a href="javascript:void(0);"><i class="fa fa-group"></i>团队建设</a></li>
                <li><a href="javascript:void(0);"><i class="fa fa-bell"></i>基础管理</a></li>
                <li><a href="${pageContext.request.contextPath }/tdjs/tdjsArticle/article"><i class="fa fa-bell"></i>文章管理</a></li>
                <li class="active"><a href="javascript:void(0);"><i class="fa fa-plus"></i>新增文章</a></li>
            </ul>
            <form:form id="articleForm" modelAttribute="article" method="post">
                <form:hidden path="article_id" />
                <form:hidden path="reviewer" />
                <form:hidden path="review_time" />
                <div class="panel panel-default">
                    <div class="panel-heading"><i class="fa fa-plus"></i>新增文章</div>
                    <div class="panel-body">
                        <div class="form-horizontal">
                            <div class="form-group">
                                <label class="col-sm-2 control-label text-right">标题：</label>
                                <div class="col-sm-6">
                                    <sw:input path="title" cssClass="form-control" placeholder="请输入文章标题" valid="r|l-200" validTitle="文章标题" validInfoArea="title_info_area" />
                                </div>
                                <div class="col-sm-4"><p class="help-block" id="title_info_area"></p></div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label text-right">状态：</label>
                                <div class="col-sm-2">
                                    <sw:radiobuttons path="status" items="${applicationScope.__CODE_TABLE__.open_close }"
                                                     validInfoArea="status_info_area" />
                                </div>


                                <label class="col-sm-2 control-label text-right">置顶：</label>
                                <div class="col-sm-2">
                                    <sw:radiobuttons path="topset" items="${applicationScope.__CODE_TABLE__.zero_one }" 
                                                     validInfoArea="topset_info_area" />
                                </div>
                                <div class="col-sm-5"><p class="help-block" id="topset_info_area"></p></div>
                            </div>     

                            <div class="form-group">
                                <label class="col-sm-2 control-label text-right">文章权限：</label>
                                <div class="col-sm-2">
                                    <sw:radiobuttons path="article_limit" items="${applicationScope.__CODE_TABLE__.article_limit }"
                                                     validInfoArea="article_limit_info_area" />
                                </div>
                            </div>


                            <div class="form-group">
                                <label class="col-sm-2 control-label text-right">摘要：</label>
                                <div class="col-sm-6">
                                    <sw:textarea path="tabloid" cssClass="form-control" placeholder="输入文章摘要（200字以内）" validInfoArea="tabloid_info_area" />
                                </div>
                                <div class="col-sm-4"><p class="help-block" id="tabloid_info_area"></p></div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label text-right">所属团队：</label>
                                <div class="col-sm-3">
                                    <div class="input-group" id="teamBtn">
                                        <form:hidden path="team_id"/>
                                        <sw:input path="team_name" cssClass="form-control"  
                                                  valid="r" validTitle="所属团队" validInfoArea="team_id_info_area" onclick="selectTeam('team_id', 'team_name');"/>
                                        <span class="input-group-addon" onclick="selectTeam('team_id', 'team_name');"><i class="fa fa-group"></i></span>
                                    </div>
                                </div>
                                <div class="col-sm-7"><p class="help-block" id="team_id_info_area"></p></div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label text-right">所属栏目：</label>
                                <div class="col-sm-3">
                                    <div class="input-group">
                                        <form:hidden path="channel_id"/>
                                        <sw:input path="channel_name" cssClass="form-control" readonly="true" placeholder="请选择所属栏目"
                                                  valid="r" validTitle="所属栏目" validInfoArea="channel_id_info_area" onclick="selectChannel('channel_id', 'channel_name');"/>
                                        <span class="input-group-addon" onclick="selectChannel('channel_id', 'channel_name');"><i class="fa fa-navicon"></i></span>
                                    </div>
                                </div>
                                <div class="col-sm-7"><p class="help-block" id="channel_id_info_area"></p></div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label text-right">发布日期：</label>
                                <div class="col-sm-3">
                                    <div class="input-group">
                                        <sw:input path="publish_time" cssClass="form-control" placeholder="请输入发布日期" readonly="true" 
                                                  validTitle="发布日期" validInfoArea="publish_time_info_area" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" />
                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                    </div>
                                </div>
                                <div class="col-sm-7"><p class="help-block" id="publish_time_info_area"></p></div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label text-right">发布人：</label>
                                <div class="col-sm-3">
                                    <sw:input path="publisher" cssClass="form-control" placeholder="请填写发布人" valid="r" validTitle="发布人" validInfoArea="publisher_info_area" />
                                </div>
                                <div class="col-sm-7"><p class="help-block" id="publisher_info_area"></p></div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label text-right">文章来源：</label>
                                <div class="col-sm-6">
                                    <sw:input path="provenance" cssClass="form-control" placeholder="文章出处" validInfoArea="provenance_info_area" />
                                </div>
                                <div class="col-sm-4"><p class="help-block" id="provenance_info_area"></p></div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label text-right">使用模板：</label>
                                <div class="col-sm-3">
                                    <div class="input-group">
                                        <sw:input path="templet" cssClass="form-control" placeholder="请选择模板"
                                                  validInfoArea="templet_info_area" ondblclick="selectTemplet();" />
                                        <span class="input-group-addon" onclick="selectTemplet();"><i class="fa fa-building"></i></span>
                                    </div>
                                </div>
                                <div class="col-sm-7"><p class="help-block" id="channel_info_area"></p></div>
                            </div>        

                            <div class="form-group">
                                <label class="col-sm-2 control-label text-right">上传图片：</label>
                                <div class="col-sm-3">
                                    <div class="input-group">
                                        <div id="uploader">
                                            <div class="queueList" style="width:600px">
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
                                                    <div id="filePicker2"></div><div class="uploadBtn" style="display: none">开始上传</div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>     
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <sw:button accesskey="S" id="add_article" cssClass="btn btn-success" value="保存 (S)" icon="save" limit="tdjs/tdjsArticle/article/add" />
                                    <button accesskey="B" type="button" class="btn btn-danger" onclick="goDraft();">
                                        <i class="fa fa-drupal"></i>存为草稿 (C)
                                    </button>
                                    <button accesskey="B" type="button" class="btn btn-default" onclick="goBack();">
                                        <i class="fa fa-arrow-circle-left"></i>返回 (B)
                                    </button>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>

                <div class="panel panel-default" style="margin-top:10px;">
                    <div class="panel-heading"><i class="fa fa-pencil"></i>文章内容</div>
                    <div class="panel-body" style="padding:0;">
                        <sw:textarea path="content" />
                    </div>
                </div>
            </form:form>
            <div class="clearfix"></div>
        </div>
        <script>
            var uploadUrl = "${pageContext.request.contextPath }/tdjs/tdjsPicture/picture/${article.article_id}/ajaxMultipartAdd?flag=article";
        </script>  
        <script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/wzgl/uploadPicture/webuploader.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/wzgl/uploadPicture/uploadForArticle.js"></script> 
    </body>
</html>
