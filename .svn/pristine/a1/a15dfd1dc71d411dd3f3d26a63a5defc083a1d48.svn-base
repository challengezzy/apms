<%@page import="smartx.framework.common.bs.NovaDBConnection"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@ page contentType="text/html;charset=GB2312"%>
<%@ page import="net.sf.jasperreports.engine.JasperRunManager" %>
<%@ page import="smartx.framework.common.bs.CommDMO" %>
<%@ page import="java.io.*" %>
<%@ page import="java.sql.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
</head>
<body>
<%
 	//报表编译之后生成的.jasper文件的存放位置
 	File reportFile = new File(this.getServletContext().getRealPath("/ireport/check_result1.jasper"));
 
 	//String url="jdbc:mysql://localhost:3306/db";
	// Class.forName("com.mysql.jdbc.Driver");
 	Map<String,Object> parameters = new HashMap<String,Object>();
 	//"SQLSTR"是报表中定义的参数名称,其类型为String 型
 	//设置SQLSTR参数的内容,根据需要赋值sql语句
 	parameters.put("QUERYSQL", "SELECT * FROM DQC_CHK_TJ_RESULT_REGION t WHERE  t.check_day_str = '20111123' and check_item = '12'");
 	
 	CommDMO dmo = new CommDMO();
 	NovaDBConnection novaConn = dmo.getConn("datasource_dqc");
 	Connection conn = novaConn.getConn();
 	//Connection conn = DriverManager.getConnection(url,"username", "password");
 	//JasperRunManager.runReportToHtmlFile(reportFile.getPath(),parameters,conn);
 	JasperRunManager.runReportToHtmlFile(reportFile.getPath(),parameters,conn); 	
 	response.sendRedirect("check_result1.html");
%>
</body>
</html>



