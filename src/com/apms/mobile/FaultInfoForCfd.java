package com.apms.mobile;

public class FaultInfoForCfd {
	
	private String acnum;
	private String flightno;
	private String reporttime;
	private String atano;
	private String fault_content;
	public String getAcnum() {
		return acnum;
	}
	public void setAcnum(String acnum) {
		this.acnum = acnum;
	}
	public String getFlightno() {
		return flightno;
	}
	public void setFlightno(String flightno) {
		this.flightno = flightno;
	}
	public String getReporttime() {
		return reporttime;
	}
	public void setReporttime(String reporttime) {
		this.reporttime = reporttime;
	}
	public String getAtano() {
		return atano;
	}
	public void setAtano(String atano) {
		this.atano = atano;
	}
	private String fault_source;
	public String getFault_content() {
		return fault_content;
	}
	public void setFault_content(String fault_content) {
		this.fault_content = fault_content;
	}
	public String getFault_source() {
		return fault_source;
	}
	public void setFault_source(String fault_source) {
		this.fault_source = fault_source;
	}
	
}
