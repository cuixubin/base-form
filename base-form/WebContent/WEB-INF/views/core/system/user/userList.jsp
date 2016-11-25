<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<head>
    <title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 用户管理</title>
    <base target="_self" />
    <%@ include file="/resources/include/plugin.jsp"%>
    <%@ include file="/resources/include/extra.jsp"%>
    <%            //通过静态域设置码制映射对象
        CodeTableUtils.createCodeTableJS(application, out, "valid_type");
        CodeTableUtils.createCodeTableJS(application, out, "user_identity");
        CodeTableUtils.createCodeTableJS(application, out, "sex");
        CodeTableUtils.createCodeTableJS(application, out, "card_type");
        CodeTableUtils.createCodeTableJS(application, out, "folk");
        CodeTableUtils.createCodeTableJS(application, out, "degree");
        CodeTableUtils.createCodeTableJS(application, out, "jszz");
    %>
    <script type="text/javascript">
        var gridColumns = [
            {id: 'operation', title: '操作', columnClass: 'text-center', columnStyle: 'width:200px;', hideType: 'xs', extra: false, 'export': false, print: false, advanceQuery: false, resolution: function (value, record, column, grid, dataNo, columnNo) {
                    var content = '';
                    content += '<button type="button" class="btn btn-xs btn-icon btn-primary" title="编辑" limit="core/system/user/-/edit" onclick="editUserSingle(\'' + record.user_id + '\')"><i class="fa fa-edit"></i></button>';
                    content += '&nbsp;&nbsp;';
                    content += '<button type="button" class="btn btn-xs btn-icon btn-danger" title="删除" limit="core/system/user/delete" onclick="deleteUserSingle(\'' + record.user_id + '\')"><i class="fa fa-trash-o"></i></button>';
                    content += '&nbsp;&nbsp;';
                    content += '<button type="button" class="btn btn-xs btn-icon btn-default" title="配置角色" limit="core/system/user/-/set_role" onclick="setRoleSingle(\'' + record.user_id + '\')"><i class="fa fa-users"></i></button>';
                    content += '&nbsp;&nbsp;';
                    content += '<button type="button" class="btn btn-xs btn-icon btn-default" title="配置用户参数" limit="core/system/user/-/set_user_attr" onclick="setUserAttrSingle(\'' + record.user_id + '\')"><i class="fa fa-gears"></i></button>';
                    content += '&nbsp;&nbsp;';
                    content += '<button type="button" class="btn btn-xs btn-icon btn-default" title="配置快捷方式" limit="core/system/user/-/set_user_shortcut_limit" onclick="setUserShortcutLimitSingle(\'' + record.user_id + '\')"><i class="fa fa-magic"></i></button>';
                    content += '&nbsp;&nbsp;';
                    content += '<button type="button" class="btn btn-xs btn-icon btn-default" title="重置密码" limit="core/system/user/reset_password" onclick="resetPasswordSingle(\'' + record.user_id + '\')"><i class="fa fa-key"></i></button>';
                    return content;
                }},
            {id: 'user_code', title: '用户编号', type: 'string', columnClass: 'text-center', hideType: '', fastQuery: true, fastQueryType: 'eq'},
            {id: 'user_name', title: '用户名称', type: 'string', columnClass: 'text-center', hideType: '', fastQuery: true, fastQueryType: 'lk'},
            {id: 'dept_name', title: '所属部门', type: 'string', columnClass: 'text-center', hideType: 'xs|sm', fastQuery: true, fastQueryType: 'lk'},
            {id: 'valid_type', title: '是否有效', type: 'string', codeTable: code_table_info['valid_type'], columnClass: 'text-center', hideType: 'xs', fastQuery: true, fastQueryType: 'eq'},
            {id: 'identity', title: '用户身份', type: 'string', codeTable: code_table_info['user_identity'], columnClass: 'text-center', hideType: 'xs|sm|md', fastQuery: true, fastQueryType: 'eq'},
            {id: 'sex', title: '性别', type: 'string', codeTable: code_table_info['sex'], columnClass: 'text-center', hideType: 'xs|sm|md', fastQuery: true, fastQueryType: 'eq'},
            {id: 'phone', title: '联系电话', type: 'string', columnClass: 'text-center', hideType: 'xs|sm|md', fastQuery: true, fastQueryType: 'lk'},
            {id: 'card_type', title: '证件类型', type: 'string', codeTable: code_table_info['card_type'], columnClass: 'text-center', hideType: 'xs|sm|md|lg', fastQuery: true, fastQueryType: 'eq'},
            {id: 'card_id', title: '证件号', type: 'string', columnClass: 'text-center', hideType: 'xs|sm|md|lg', fastQuery: true, fastQueryType: 'lk'},
            {id: 'birthday', title: '出生日期', type: 'date', format: 'yyyy-MM-dd', otype: 'string', oformat: 'yyyy-MM-dd hh:mm:ss', columnClass: 'text-center', hideType: 'xs|sm|md|lg', fastQuery: true, fastQueryType: 'range'},
            {id: 'work_date', title: '工作日期', type: 'date', format: 'yyyy-MM-dd', otype: 'string', oformat: 'yyyy-MM-dd hh:mm:ss', columnClass: 'text-center', hideType: 'xs|sm|md|lg', fastQuery: true, fastQueryType: 'range'},
            {id: 'folk', title: '民族', type: 'string', codeTable: code_table_info['folk'], columnClass: 'text-center', hideType: 'xs|sm|md|lg', fastQuery: true, fastQueryType: 'eq'},
            {id: 'degree', title: '学历', type: 'string', codeTable: code_table_info['degree'], columnClass: 'text-center', hideType: 'xs|sm|md|lg', fastQuery: true, fastQueryType: 'eq'},
            {id: 'qualified', title: '教师资质', type: 'string', codeTable: code_table_info['jszz'], columnClass: 'text-center', hideType: 'xs|sm|md|lg', fastQuery: true, fastQueryType: 'eq'},
            {id: 'graduateSchool', title: '毕业院校', type: 'string', columnClass: 'text-center', hideType: 'xs|sm|md|lg', fastQuery: true, fastQueryType: 'eq'},
            {id: 'email', title: 'E-mail', type: 'string', columnClass: 'text-center', hideType: 'xs|sm|md|lg', fastQuery: true, fastQueryType: 'lk'},
            {id: 'address', title: '联系地址', type: 'string', hideType: 'lg|md|sm|xs', fastQuery: true, fastQueryType: 'lk'},
            {id: 'remark', title: '备注', type: 'string', columnClass: 'text-center', hideType: 'lg|md|sm|xs', fastQuery: true, fastQueryType: 'lk'},
            {id: 'creator_name', title: '创建人', type: 'string', columnClass: 'text-center', hideType: 'lg|md|sm|xs', fastQuery: true, fastQueryType: 'lk'},
            {id: 'create_time', title: '创建时间', type: 'date', format: 'yyyy-MM-dd hh:mm:ss', columnClass: 'text-center', hideType: 'lg|md|sm|xs', fastQuery: true, fastQueryType: 'range'},
            {id: 'editor_name', title: '编辑人', type: 'string', columnClass: 'text-center', hideType: 'lg|md|sm|xs', fastQuery: true, fastQueryType: 'lk'},
            {id: 'edit_time', title: '编辑时间', type: 'date', format: 'yyyy-MM-dd hh:mm:ss', columnClass: 'text-center', hideType: 'lg|md|sm|xs', fastQuery: true, fastQueryType: 'range'}
        ];
        var gridOption = {
            loadURL: '${pageContext.request.contextPath }/core/system/user/list',
            functionCode: 'CORE_USER',
            check: true,
            exportFileName: '用户列表',
            columns: gridColumns,
            onRowDblClick: function (value, record, column, grid, dataNo, columnNo, cell, row, extraCell, e) {
                editUserSingle(record.user_id);
            },
            onGridComplete: function (value, record, column, grid, dataNo, columnNo, cell, row, extraCell, e) {
                var urls = '';
                urls += 'core/system/user/-/edit,';
                urls += 'core/system/user/delete,';
                urls += 'core/system/user/-/set_role,';
                urls += 'core/system/user/-/set_user_attr,';
                urls += 'core/system/user/-/set_user_shortcut_limit,';
                urls += 'core/system/user/reset_password';
                sw.initGridButtonLimit(urls, '${applicationScope.__SYSTEM_ATTRIBUTE__.show_button_when_no_limit.content }');
            }
        };
        var grid = $.fn.dlshouwen.grid.init(gridOption);
        ;
        //数据加载
        $(function () {
            grid.load();
        });
        //跳转添加
        function addUser() {
            var url = '${pageContext.request.contextPath }/core/system/user/add';
            sw.showProcessBar();
            window.location.href = url;
        }
        //删除操作
        function deleteUser() {
            var recordObj = grid.getCheckedRecords();
            if (recordObj.length == 0) {
                sw.toast('请选择要删除的用户！', 'warning', 3000);
                return false;
            }
            if (!confirm('确定删除选中用户吗？')) {
                return;
            }
            var userIds = '';
            for (var i = 0; i < recordObj.length; i++) {
                userIds += recordObj[i].user_id + ((i != recordObj.length - 1) ? ',' : '');
            }
            var url = '${pageContext.request.contextPath }/core/system/user/delete';
            var params = new Object();
            params.userIds = userIds;
            sw.showProcessBar();
            sw.ajaxSubmitRefreshGrid(url, params, grid);
        }
        //删除操作
        function deleteUserSingle(userId) {
            if (!confirm('确定删除选中用户吗？')) {
                return;
            }
            var url = '${pageContext.request.contextPath }/core/system/user/delete';
            var params = new Object();
            params.userIds = userId;
            sw.showProcessBar();
            sw.ajaxSubmitRefreshGrid(url, params, grid);
        }
        //编辑跳转
        function editUser() {
            var recordObj = grid.getCheckedRecords();
            if (recordObj.length == 0) {
                sw.toast('请选择要编辑的用户！', 'warning', 3000);
                return false;
            }
            var userId = recordObj[0].user_id;
            var url = '${pageContext.request.contextPath }/core/system/user/' + userId + '/edit';
            sw.showProcessBar();
            window.location.href = url;
        }
        //编辑跳转
        function editUserSingle(userId) {
            var url = '${pageContext.request.contextPath }/core/system/user/' + userId + '/edit';
            sw.showProcessBar();
            window.location.href = url;
        }
        //配置角色
        function setRole() {
            var recordObj = grid.getCheckedRecords();
            if (recordObj.length == 0) {
                sw.toast('请选择要配置角色的用户！', 'warning', 3000);
                return false;
            }
            var userId = recordObj[0].user_id;
            var url = '${pageContext.request.contextPath }/core/system/user/' + userId + '/set_role';
            sw.showProcessBar();
            window.location.href = url;
        }
        //配置角色
        function setRoleSingle(userId) {
            var url = '${pageContext.request.contextPath }/core/system/user/' + userId + '/set_role';
            sw.showProcessBar();
            window.location.href = url;
        }
        //配置用户参数
        function setUserAttr() {
            var recordObj = grid.getCheckedRecords();
            if (recordObj.length == 0) {
                sw.toast('请选择要配置参数的用户！', 'warning', 3000);
                return false;
            }
            var userId = recordObj[0].user_id;
            var url = '${pageContext.request.contextPath }/core/system/user/' + userId + '/set_user_attr';
            sw.showProcessBar();
            window.location.href = url;
        }
        //配置用户参数
        function setUserAttrSingle(userId) {
            var url = '${pageContext.request.contextPath }/core/system/user/' + userId + '/set_user_attr';
            sw.showProcessBar();
            window.location.href = url;
        }
        //配置快捷方式
        function setUserShortcutLimit() {
            var recordObj = grid.getCheckedRecords();
            if (recordObj.length == 0) {
                sw.toast('请选择要配置快捷方式的用户！', 'warning', 3000);
                return false;
            }
            var userId = recordObj[0].user_id;
            var url = '${pageContext.request.contextPath }/core/system/user/' + userId + '/set_user_shortcut_limit';
            sw.showProcessBar();
            window.location.href = url;
        }
        //配置快捷方式
        function setUserShortcutLimitSingle(userId) {
            var url = '${pageContext.request.contextPath }/core/system/user/' + userId + '/set_user_shortcut_limit';
            sw.showProcessBar();
            window.location.href = url;
        }
        //重置密码
        function resetPassword() {
            var recordObj = grid.getCheckedRecords();
            if (recordObj.length == 0) {
                sw.toast('请选择要重置密码的用户！', 'warning', 3000);
                return false;
            }
            if (recordObj.length > 1) {
                sw.toast('每次只能重置一位用户！', 'warning', 3000);
                return false;
            }
            if (!confirm('确认要重置密码吗？')) {
                return;
            }
            var userId = recordObj[0].user_id;
            url = '${pageContext.request.contextPath }/core/system/user/reset_password';
            var params = new Object();
            params.userId = userId;
            sw.showProcessBar();
            sw.ajaxSubmitRefreshGrid(url, params, grid);
        }
        //重置密码
        function resetPasswordSingle(userId) {
            if (!confirm('确认要重置密码吗？')) {
                return;
            }
            url = '${pageContext.request.contextPath }/core/system/user/reset_password';
            var params = new Object();
            params.userId = userId;
            sw.showProcessBar();
            sw.ajaxSubmitRefreshGrid(url, params, grid);
        }
        function import_download() {
            var url = '${pageContext.request.contextPath }/core/system/user/import_user_page';
            sw.openModal({
                id: 'import_user',
                icon: 'download',
                title: '导入用户',
                url: url,
                showCloseButton:false,
                showConfirmButton:true,
                callback: function () {
                    var isOk = document.getElementById("import_userFrame").contentWindow.sendForm();
                    if(isOk) {
                        sw.closeModal('import_user');
                    }
                }
            });
        }
        function powerSet() {
            if (!confirm('确认要将用户数据同步到权限系统吗？')) {
                return;
            }
            url = '${pageContext.request.contextPath }/core/system/user/powerSynchronization';
            var params;
            sw.showProcessBar();
            sw.ajaxSubmit(url, params, function(data){
                sw.ajaxSuccessCallback(data, function(){
                });
            });
        }
        function refreshUserList() {
            window.location.reload();
        }
        //初始化绑定方法
        $(function () {
            $('#add_user').bind('click', addUser);
            $('#edit_user').bind('click', editUser);
            $('#delete_user').bind('click', deleteUser);
            $('#set_role').bind('click', setRole);
            $('#set_user_attr').bind('click', setUserAttr);
            $('#set_user_shortcut_limit').bind('click', setUserShortcutLimit);
            $('#reset_password').bind('click', resetPassword);
            $('#import_user').bind('click', import_download);
            $('#powerSynchronization').bind('click', powerSet);
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
            <li><a href="javascript:void(0);"><i class="fa fa-gears"></i>基础管理</a></li>
            <li class="active"><a href="javascript:void(0);"><i class="fa fa-user"></i>用户管理</a></li>
        </ul>
        <div class="panel panel-default">
            <div class="panel-heading"><i class="fa fa-user"></i>用户管理</div>
            <div class="panel-operation">
                <div class="btn-group">
                    <sw:button accesskey="A" id="add_user" value="新建 (A)" cssClass="btn btn-primary" limit="core/system/user/add" icon="plus" />
                    <sw:button accesskey="M" id="edit_user" value="编辑 (M)" cssClass="btn btn-primary" limit="core/system/user/-/edit" icon="edit" />
                    <sw:button accesskey="W" id="delete_user" value="删除 (W)" cssClass="btn btn-danger" limit="core/system/user/delete" icon="trash-o" />
                    <sw:button accesskey="R" id="set_role" value="配置角色 (R)" cssClass="btn btn-default" limit="core/system/user/-/set_role" icon="users" />
                    <sw:button accesskey="T" id="set_user_attr" value="配置用户参数 (T)" cssClass="btn btn-default" limit="core/system/user/-/set_user_attr" icon="gears" />
                    <sw:button accesskey="L" id="set_user_shortcut_limit" value="配置快捷方式 (L)" cssClass="btn btn-default" limit="core/system/user/-/set_user_shortcut_limit" icon="magic" />
                    <sw:button accesskey="P" id="reset_password" value="重置密码 (P)" cssClass="btn btn-default" limit="core/system/user/reset_password" icon="key" />
                    <sw:button accesskey="I" id="import_user" value="导入用户 (I)" cssClass="btn btn-default" limit="core/system/user/import_user" icon="download" />
                    <sw:button accesskey="S" id="powerSynchronization" value="同步权限 (S)" cssClass="btn btn-default" limit="core/system/user/powerSynchronization" icon="exchange" />
                </div>
            </div>
            <div id="grid_container" class="dlshouwen-grid-container"></div>
            <div id="grid_toolbar_container" class="dlshouwen-grid-toolbar-container"></div>
        </div>
        <div class="clearfix"></div>
    </div>
</body>
</html>