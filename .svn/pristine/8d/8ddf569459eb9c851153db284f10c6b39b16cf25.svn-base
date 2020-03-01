<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>抖动波形图</title>
        <%
            String acnum = request.getParameter("acnum");
            String groupid = request.getParameter("groupid");
            String dim = request.getParameter("dim");
            String date_utc = request.getParameter("date_utc");
        %>

        <link rel="Stylesheet" type="text/css" media=all href="../StyleSheet.css" />
        <link href="StyleSheet.css" rel="stylesheet" type="text/css" />
    </head>

    <body>
    	<table width="100%" cellpadding="0" cellspacing="0">
    	   <tr>
    	     <td>
    	     	机号：<%=acnum%> ; 	报文时间：<%=date_utc %>
    	     </td>
    	   </tr>
           <tr>
              <td>
              <form method="get">
            <div style="text-align: center">
                <script type="text/javascript">
                    try
                    {
                        var objXHR = new XMLHttpRequest();
                    }
                    catch (e)
                    {
                        try
                        {
                            var objXHR = new ActiveXObject('Msxml2.XMLHTTP');
                        }
                        catch (e)
                        {
                            try
                            {
                                var objXHR = new ActiveXObject('Microsoft.XMLHTTP');
                            }
                            catch (e)
                            {
                                document.write('XMLHttpRequest not supported');
                            }
                        }
                    }
                    objXHR.open('GET','VibrationFigure?acnum=<%=acnum%>&groupid=<%=groupid%>&dim=Z',false);
                    objXHR.send(null);
                    document.writeln(objXHR.responseText);
                </script>
                <br>
            </div>
        </form>
              </td>
           </tr>
        </table>
    </body>
</html>