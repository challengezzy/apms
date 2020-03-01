<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="smartx.framework.common.bs.NovaDBConnection"%>
<%@page import="smartx.framework.common.bs.CommDMO"%>
<%@ page import="net.sf.jasperreports.engine.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.sql.*"%>
<%
	//报表编译之后生成的.jasper文件的存放位置
	File reportFile = new File(this.getServletContext().getRealPath("/ireport/check_result1.jasper"));

	//"SQLSTR"是报表中定义的一个参数名称,其类型为String 型
	Map<String,Object> parameters = new HashMap<String,Object>();
	parameters.put("QUERYSQL", "SELECT * FROM DQC_CHK_TJ_RESULT_REGION t WHERE  t.check_day_str = '20111123' and check_item = '12'");
	CommDMO dmo = new CommDMO();
 	NovaDBConnection novaConn = dmo.getConn("datasource_dqc");
 	Connection conn = novaConn.getConn();

	byte[] bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), parameters, conn);

	response.setContentType("application/pdf");
	response.setContentLength(bytes.length);

	ServletOutputStream outStream = response.getOutputStream();
	outStream.write(bytes, 0, bytes.length);
	outStream.flush();
	outStream.close();
	out.clear();
	out = pageContext.pushBody();
%>
