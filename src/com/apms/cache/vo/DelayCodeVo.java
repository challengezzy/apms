package com.apms.cache.vo;

public class DelayCodeVo {
	
	private String id ;
	private String serial_no ; 
	private String catalog ; //延误类别
	private String delaycode ; //
	private String delaydesc ; //延误说明
	private String sortseq ;
	private boolean innerflag ; //是否内部原因
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSerial_no() {
		return serial_no;
	}
	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}
	public String getCatalog() {
		return catalog;
	}
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
	public String getDelaycode() {
		return delaycode;
	}
	public void setDelaycode(String delaycode) {
		this.delaycode = delaycode;
	}
	public String getDelaydesc() {
		return delaydesc;
	}
	public void setDelaydesc(String delaydesc) {
		this.delaydesc = delaydesc;
	}
	public String getSortseq() {
		return sortseq;
	}
	public void setSortseq(String sortseq) {
		this.sortseq = sortseq;
	}
	public boolean isInnerflag() {
		return innerflag;
	}
	public void setInnerflag(boolean innerflag) {
		this.innerflag = innerflag;
	}
	
}
