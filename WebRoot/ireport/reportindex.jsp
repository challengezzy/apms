<%@page import="java.net.URLDecoder"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="smartx.framework.common.utils.StringUtil"%>
<%@page import="smartx.framework.common.vo.DMOConst"%>
<%@page import="smartx.framework.common.vo.HashVO"%>
<%@page import="smartx.framework.common.bs.CommDMO"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.io.*" %>
<%@page contentType="text/html;charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% 
 	String reportCode = request.getParameter("code");// StringUtil.transCharSet(request.getParameter("code"),"ISO8859-1");
	String param1 = request.getParameter("param1");// StringUtil.transCharSet(request.getParameter("param1"),"ISO8859-1");
	String param2 = request.getParameter("param2");// StringUtil.transCharSet(request.getParameter("param2"),"ISO8859-1");
	String param3 = request.getParameter("param3");// StringUtil.transCharSet(request.getParameter("param3"),"ISO8859-1");
	String param4 = request.getParameter("param4");// StringUtil.transCharSet(request.getParameter("param4"),"ISO8859-1");
	String param5 = request.getParameter("param5");// StringUtil.transCharSet(request.getParameter("param5"),"ISO8859-1");

	String reportType = "html";
	
	String paramCondition = "";
	if(param1 != null && !"".equals(param1))
		paramCondition = paramCondition + " and param1='" + param1+"'";
	if(param2 != null && !"".equals(param2))
		paramCondition = paramCondition + " and param2='" + param2+"'";
	if(param3 != null && !"".equals(param3))
		paramCondition = paramCondition + " and param3='" + param3+"'";
	if(param4 != null && !"".equals(param4))
		paramCondition = paramCondition + " and param4='" + param4+"'";
	if(param5 != null && !"".equals(param5))
		paramCondition = paramCondition + " and param5='" + param5+"'";
	
	System.out.println("********************paramCondition= *****"+paramCondition+ "*******************************");
	//这里从报表模板中读出参数，并进行初始化
	String querySql = "SELECT CREATEDATE_STR,REPORTNAME,NAME FROM PUB_REPORT_INSTANCE WHERE TYPE='html' AND CODE = '"+reportCode+"'" 
					+ paramCondition + " ORDER BY CREATEDATE_STR DESC";
	
	CommDMO dmo = new CommDMO();
	HashVO[] reportIns = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT,querySql);
	String reportTitle = "数据质量检查报表查看";
	if(reportIns.length > 0)
		 reportTitle = reportIns[0].getStringValue("NAME");
	
	
 	//response.sendRedirect("export/"+reportName+"."+reportType);
 	//Map<String,String> reportMap = new HashMap<String,String>();
 	//for(int i=0;i<reportIns.length;i++){
 	//	reportIns[i].getStringValue("CREATEDATE_STR");
 		//reportMap.put(reportIns[i].getStringValue("CREATEDATE_STR"),reportIns[i].getStringValue("REPORTNAME"));
 	//}
 	
	%>
<html>
<head>
	<title><%=reportTitle %></title>
</head>
<body onload="init()">

	<center>
	<form action=/cgi-bin/post-query method=POST>
		<select id="dateSelect" onchange="onBatchDateChanged()" >
			<%for(int i=0;i<reportIns.length;i++){
				//tomcat中设置conf/server.xml 中 connector :URIEncoding="UTF-8"/ 
				//System.out.println("********************"+reportIns[i].getStringValue("REPORTNAME")+ "*******************************");
				//String reportname = URLEncoder.encode(reportIns[i].getStringValue("REPORTNAME"),"UTF-8");
				String reportname = reportIns[i].getStringValue("REPORTNAME");
				//System.out.println("********************"+reportname+ "*******************************");
				//reportname=reportname.replaceAll("[+]","%20");//替换"+"为"%20"
				//System.out.println("********************"+reportname+ "*******************************");
			%>
        		<option value="<%=  reportname %>" >
        			<%=reportIns[i].getStringValue("CREATEDATE_STR") %></option>
      		<%} %>
		</select>&nbsp;
		<select id="typeSelect" onchange="onBatchDateChanged()">
			<option value="html">html</option>
			<option value="xls">xls</option>
			<option value="pdf">pdf</option>
		</select>&nbsp;
		<font color="red" >选择报表日期和类型查看报表内容</font>
	</form>
	</center>
	<% if(reportIns.length > 0) {%>
	<iframe id="reportContentFrame" frameborder="0" width="100%" src="<%="export/" + reportIns[0].getStringValue("REPORTNAME")+".html" %>"></iframe>
	<% }else{ %>
	<iframe id="reportContentFrame" frameborder="0" width="100%" src="aboutblank.html"></iframe>
	<%} %>
	<script type="text/javascript">
		function onBatchDateChanged(){
			var dateSel = document.getElementById("dateSelect");
			var reportName = dateSel.options[dateSel.selectedIndex].value;
			var typeSel = document.getElementById("typeSelect");
			var type = typeSel.options[typeSel.selectedIndex].value;
						
			//alert(reportName + "." + type);
			changeReport(reportName,type)
			
		}
		
		function changeReport(reportName,type){
			var name = "export/" + reportName + "." + type;
			//alert(reportName + "." + type);
			document.getElementById("reportContentFrame").src = name;
		}
		
		function functionTest(){
			alert("show functionTest");
		}
		
		function init(){
		
			//alert( window.screen.availHeight );
			document.getElementById("reportContentFrame").height = window.screen.availHeight - 130;
		}
	</script>
</body>	
</html>



