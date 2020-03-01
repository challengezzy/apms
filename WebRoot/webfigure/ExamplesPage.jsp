<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Calculation Services</title>

        <%
            String sizeStr = request.getParameter("size");
            int size = 5;
            boolean sizeSet = false;
            if(sizeStr!=null && sizeStr.length()>0)
            {
                size = Integer.parseInt(sizeStr);
                sizeSet = true;
            }
        %>

        <link rel="Stylesheet" type="text/css" media=all href="../StyleSheet.css" />
        <link href="StyleSheet.css" rel="stylesheet" type="text/css" />
    </head>

    <body>
        <form method="get">
            <div style="text-align: center">
                <table width="760" cellpadding="0" cellspacing="0">
                    <tr>
                        <td><img src="header_bg.jpg" alt="Header Image Not Found" width="779" height="72" /></td>
                    </tr>
                </table>
                <br />

                <h1> Calculation Services</h1>

                Calculate Magic Square
                <br>
                Size:
                <input type="text" name="size" size="8" value="<%=size%>" >
                <br>
                <input type="submit" value="Calculate">
                <br>
                <br />
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
                    objXHR.open('GET','MagicSquare?size=<%=size%>',false);
                    objXHR.send(null);
                    document.writeln(objXHR.responseText);
                </script>
                <br>
            </div>
        </form>
    </body>
</html>

