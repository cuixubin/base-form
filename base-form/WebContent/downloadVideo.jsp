<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="video/x-flv; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import ="java.util.Date" %>
<%@page import ="java.util.Map" %>
<%@page import ="java.lang.String" %>
<%@page import ="java.util.HashMap" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="video/x-flv; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
            String path = (String) request.getParameter("path");
            String fileName = (String) request.getParameter("fileName");
            if(null == fileName || "" == fileName) {
                fileName = new Date().getTime() + "";
                fileName += path.substring(path.lastIndexOf("."));
            }
            File f = new File(path);
            FileInputStream filein = new FileInputStream(f);
            byte[] b = new byte[1024];
            if(fileName!=null){
	    	fileName = new String(fileName.getBytes("ISO-8859-1"),"utf-8");
	    	response.addHeader("Content-Disposition", "attachment;filename="+fileName);
	    }
            try {
                //d.writeImg(id, response.getOutputStream());
                int len = filein.read(b);
                
                while(len>0){
                    response.getOutputStream().write(b, 0, len);
                    len = filein.read(b);
                }                
                response.getOutputStream().flush();
                response.getOutputStream().close();

            } catch (Exception e) {
                System.out.println(e.getStackTrace()+e.getMessage());
            } finally {
                filein.close();
                out.clear();
                out = pageContext.pushBody();
            }
    %>
</body>
</html>