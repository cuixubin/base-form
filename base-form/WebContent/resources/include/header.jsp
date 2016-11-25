<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.dlshouwen.core.base.model.*"%>
<%@ page import="com.dlshouwen.core.base.utils.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sw" uri="http://core.dlshouwen.com/tags/all" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<%
//定义基本路径
String path = request.getContextPath();

response.setHeader("Pragma","No-cache"); 
response.setHeader("Cache-Control","no-cache"); 
response.setDateHeader("Expires", 0); 
response.flushBuffer();
%>
<script type="text/javascript">
//定义基本路径
var path = '${pageContext.request.contextPath }';
//预定义CodeTable信息
var code_table_info = new Object();
</script>