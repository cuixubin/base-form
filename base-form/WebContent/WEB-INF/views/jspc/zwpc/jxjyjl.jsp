<%-- 
    Document   : 继续教育经历单元页
    Created on : 2016-7-26, 10:23:47
    Author     : cuixubin
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<script type="text/javascript">
   
</script>
<div class="col-xs-12 column mybgstyle" id="section7"  style="margin-top:10px;">
    <div class="col-sm-12 itemTitle">
        <div class="col-sm-12 text-left">
            <a name="JXJYJL_INDEX"><span>继续教育经历</span></a>
        </div>
    </div>
    <div class="col-sm-12" >
        <div class="col-sm-12 itemDescription">学习经历</div>
        <div class="col-xs-12">
            <ul class="item-table-list-ul" id="JXJYJL_ID"></ul>

            <div  class="addItemButton">
                <c:if test="${editalble == null && UVO.expreview.status != 0 && UVO.expreview.status != 1}">
                <table class="table  table-responsive item-table-list" width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td class="text-right">
                            <button type="button" class="btn btn-primary  btn-md" onclick="addItem('${UVO.expreview.id}', 'JXJYJL')">添加</button>
                        </td>
                    </tr>
                </table>
                </c:if>
            </div>
        </div>
    </div>
</div>
