<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/include/header.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <title>w文章评审意见</title>
	<%@ include file="/resources/include/extra.jsp"%>
        <script type="text/javascript">
            function getNote(){
                var notes = document.getElementsByName("note")[0].value;
                return notes;
            }
        </script>
    </head>
    <body>
           
        <table>
            <tr>
                <td>文章评审意见:</td>
            </tr>
            <tr>
                <td>
                    <textarea name="note" placeholder="确认前请填写评审意见" rows="13" cols="45"></textarea>
                </td>
            </tr>
        </table>
        
    </body>
</html>
