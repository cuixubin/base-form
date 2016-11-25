<%-- 
    Document   : 
    Created on : 2013-8-26, 15:08:59
    Author     : fred
--%>

<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="java.io.File"%>
<%@page contentType="image/jpeg" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="image/jpeg; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            String path = (String) request.getParameter("path");
            File f = new File(path);
            FileInputStream filein = new FileInputStream(f);
            byte[] b = new byte[1024];
            
            try {
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
