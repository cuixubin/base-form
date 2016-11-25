<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<html>
    <head>
        <title>${applicationScope.__SYSTEM_ATTRIBUTE__.page_title.content } - 专家评审</title>
        <base target="_self" />
        <%@ include file="/resources/include/plugin.jsp"%>
        <%@ include file="/resources/include/extra.jsp"%>
        <%  
            //通过静态域设置码制映射对象
            CodeTableUtils.createCodeTableJS(application, out, "review_status");
            CodeTableUtils.createCodeTableJS(application, out, "jszz");
        %>
        <script type="text/javascript">
        var gridColumns = [
            {id: 'operation', title: '操作', columnClass: 'text-center', columnStyle: 'width:140px;', hideType: 'xs', extra: false, 'export': false, print: false, advanceQuery: false, resolution: function (value, record, column, grid, dataNo, columnNo) {
                    var content = '';
                    //待评审状态和通过状态不可编辑
                    if(record.status == 0 || record.status == 1) {
                        content += '<button type="button" class="btn btn-xs btn-icon btn-primary" title="查看" limit="jspc/zwpc/zwpc/checkResult" onclick="checkResult(\'' + record.id + '\')"><i class="fa fa-eye"></i></button>';
                    }else {
                        content += '<button type="button" class="btn btn-xs btn-icon btn-primary" title="编辑" limit="jspc/zwpc/zwpc/editItems" onclick="editItems(\'' + record.id + '\')"><i class="fa fa-edit"></i></button>';
//                        content += '&nbsp;&nbsp;';
//                        content += '<button type="button" class="btn btn-xs btn-icon btn-danger" title="删除" limit="jspc/zwpc/zwpc/delete" onclick="deleteApplySingle(\'' + record.id + '\')"><i class="fa fa-trash-o"></i></button>';
                    }
                    return content;
                }},
            {id: 'qualified', title: '资质意向', type: 'string', codeTable: code_table_info['jszz'], columnClass: 'text-center', hideType: 'xs|sm|md', fastQuery: true, fastQueryType: 'eq', resolution: function (value, record, column, grid, dataNo, columnNo) {
                    return  code_table_info['jszz'][value] ;
                }},
            {id: 'create_date', title: '创建时间', type: 'date', format: 'yyyy-MM-dd hh:mm:ss', columnClass: 'text-center', hideType: 'xs|sm|md', fastQuery: true, fastQueryType: 'range'},
            {id: 'status', title: '评审状态', type: 'string', codeTable: code_table_info['review_status'], columnClass: 'text-center', fastQuery: true, fastQueryType: 'eq', resolution: function (value, record, column, grid, dataNo, columnNo) {
                   if(value == 0){
                       return '<span class="label label-warning">' + code_table_info['review_status'][value] + '</span>' ;
                   }else if(value == 1){
                       return '<span class="label label-success">' + code_table_info['review_status'][value] + '</span>' ;
                   } else if(value == 2){
                       return '<span class="label label-danger">' + code_table_info['review_status'][value] + '</span>' ;
                   } else{
                       return '<span class="label label-default">' + code_table_info['review_status'][value] + '</span>'
                   }   

                }},
            {id: 'review_name', title: '评审人', type: 'string', columnClass: 'text-center', hideType: 'xs|sm|md|lg', fastQuery: true, fastQueryType: 'lk'},
            {id: 'review_date', title: '评审时间', type: 'date', format: 'yyyy-MM-dd hh:mm:ss', columnClass: 'text-center', hideType: 'xs|sm|md|lg', fastQuery: true, fastQueryType: 'range'},
            {id: 'note', title: '评审意见', type: 'string', columnClass: 'text-center', hideType: 'xs|sm|md|lg', fastQuery: true, fastQueryType: 'lk'}
        ];
        
        var gridOption = {
            loadURL: '${pageContext.request.contextPath }/jspc/zwpc/zwpc/list',
            functionCode: 'JSPC_SELFVIEW',
            check: true,
            exportFileName: '自我评测',
            columns: gridColumns,
            onRowDblClick: function (value, record, column, grid, dataNo, columnNo, cell, row, extraCell, e) {
                checkResult(record.id);
            },
            onGridComplete: function (value, record, column, grid, dataNo, columnNo, cell, row, extraCell, e) {
                var urls = '';
                urls += 'jspc/zwpc/zwpc/list';
                sw.initGridButtonLimit(urls, '${applicationScope.__SYSTEM_ATTRIBUTE__.show_button_when_no_limit.content }');
            }
        };
        
        var grid = $.fn.dlshouwen.grid.init(gridOption);
        //数据加载
        $(function () {
            grid.load();
            $('#delete_applications').bind('click', deleteApply);
        });
        //编辑详情 跳转
	function editItems(expId){
            var url = '${pageContext.request.contextPath }/jspc/zwpc/zwpc/'+expId+'/toDetailPage';
            sw.showProcessBar();
            window.location.href = url;
	}
            
        //查看详情 跳转
	function checkResult(expId){
            var url = '${pageContext.request.contextPath }/jspc/zwpc/zwpc/'+expId+'/toResultPage';
            sw.showProcessBar();
            window.location.href = url;
	}
        
        //批量删除操作
	function deleteApply(){
            var recordObj = grid.getCheckedRecords();
            if(recordObj.length==0){
                    sw.toast('请选择要删除的申请！', 'warning', 3000);
                    return false;
            }
            if(!confirm('确定删除选中的申请吗？')){
                    return;
            }
            var applyIds = '';
            for(var i=0;i<recordObj.length;i++){
                    applyIds += recordObj[i].id + ((i!=recordObj.length-1)?',':'');
            }
            var url = '${pageContext.request.contextPath }/jspc/zwpc/zwpc/deleteApply';
            var params = new Object();
            params.applyIds = applyIds;
            sw.showProcessBar();
            sw.ajaxSubmitRefreshGrid(url, params, grid);
	}
        
	//单个删除操作
	function deleteApplySingle(applyIds){
            if(!confirm('确定删除此申请吗？')){
                    return;
            }
            var url = '${pageContext.request.contextPath }/jspc/zwpc/zwpc/deleteApply';
            var params = new Object();
            params.applyIds = applyIds;
            sw.showProcessBar();
            sw.ajaxSubmitRefreshGrid(url, params, grid);
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
                <li class="active"><a href="javascript:void(0);"><i class="fa fa-eye"></i>评测查看</a></li>
            </ul>
            <div class="panel panel-default">
                <div class="panel-heading"><i class="fa fa-bell"></i>评测详情</div>
                <!--
                <div class="panel-operation">
                    <div class="btn-group">
                    <%--<sw:button accesskey="W" id="delete_applications" value="删除 (W)" cssClass="btn btn-danger" limit="jspc/zwpc/zwpc/deleteApply" icon="trash-o" />--%>
                    </div>
                </div>
                -->    
                <div id="grid_container" class="dlshouwen-grid-container"></div>
                <div id="grid_toolbar_container" class="dlshouwen-grid-toolbar-container"></div>
            </div>
            <div class="clearfix"></div>
        </div>
    </body>
</html>