<%-- 
    Document   : 教书育人单元页
    Created on : 2016-7-26, 10:23:47
    Author     : cuixubin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<script type="text/javascript">

</script>
<div class="col-xs-12 column mybgstyle" id="section2"  style="margin-top:10px;">
    <div class="col-sm-12 itemTitle">
        <div class="col-sm-12 text-left">
            <a name="JSYR_INDEX"><span>教书育人</span></a>
        </div>
    </div>
    <div class="col-sm-12" >
        <div class="col-sm-12 itemDescription">与教书育人、班主任相关的业绩成果及荣誉称号</div>
        <div class="col-xs-12">
            <ul class="item-table-list-ul" id="JSYR_ID">
<!--                <li class="item-table-list-li">
                    <div class="item-table-list-box">
                        <i class="fa fa-edit" style="display:none;" onclick="add()"></i>
                        <i class="fa fa-times" style="display:none;" onclick="delete()"></i>
                        <table class="table  table-responsive item-table-list" width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td class="first_td">2011/9-2012/7</td>
                                <td>北京市骨干教师</td>
                                <td>市级</td>
                                <td><a href="javascript:void(0);" class="table-tc-fj-open" onclick="fujian()" >附件</a></td>
                            </tr>
                        </table>
                    </div>
                </li>-->
            </ul>

            <div  class="addItemButton">
                
                <c:if test="${editalble == null && UVO.expreview.status != 0 && UVO.expreview.status != 1}">
                <table class="table  table-responsive item-table-list" width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td class="text-right">
                            <button type="button" class="btn btn-primary  btn-md" onclick="addItem('${UVO.expreview.id}', 'JSYR')">添加</button>
                        </td>
                    </tr>
                </table>
                </c:if>
            </div>

        </div>
    </div>
</div>
