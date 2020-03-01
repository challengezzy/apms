package com.apms.bs.dataprase.vo.a19a21a24;

import smartx.framework.common.bs.CommDMO;

import com.apms.ApmsConst;
import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsHeadVo;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsSxTxVo_A19_A320 extends AcarsLineDataVo{
	private String row_str;
	private Float p3;
	private Float t3;
	private Integer tw;
	private Integer tp;
	private Integer tpo;
	private Integer pd;
	private Integer alt;
	private Integer ps;
	
	public AcarsSxTxVo_A19_A320(String str, String SxTx) throws Exception{
		
		row_str = SxTx;
		originalStr = str;
		
		String tempStr = str.substring(2).trim();
		String[] columns;
		columns = StringUtil.splitString2Array(tempStr, ",", true);	
		checkFieldsNum(originalStr,columns, 8);
		
		p3 = ReportParseUtil.strToFloatWithIntPostion(columns[0], 3, "p3");
		t3 = ReportParseUtil.strToFloatWithIntPostion(columns[1], 3, "t3");
		tw = ReportParseUtil.strToInteger(columns[2], "tw");
		tp = ReportParseUtil.strToInteger(columns[3], "tp");
		tpo = ReportParseUtil.strToInteger(columns[4], "tpo");
		pd = ReportParseUtil.strToInteger(columns[5], "pd");
		alt = ReportParseUtil.strToInteger(columns[6], "alt");
		ps = ReportParseUtil.strToInteger(columns[7], "ps");
		
	}
	public void insertToTable(String msgno,AcarsHeadVo headVo,int i) throws Exception{
		CommDMO dmo = new CommDMO();
		String insertSqlst = "insert into a_dfd_a19_list_st_row(ID,MSG_NO,ROW_STR,ROW_NUM,"
			+ "P3,T3,TW,TP,TPO,PD,ALT,PS,"
			+ "UPDATE_DATE)" 
			+ " values(S_A_DFD_HEAD.nextval," 
			+ "?,?,?,?,?,?,?,?,?,?," 
			+ "?,sysdate)";
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSqlst, msgno,getRow_str(),i+1,
				getP3(),getT3(),getTw(),getTp(),getTpo(),getPd(),getAlt(),getPs());
		
	}
	public String getRow_str() {
		return row_str;
	}
	public void setRow_str(String row_str) {
		this.row_str = row_str;
	}
	public Float getP3() {
		return p3;
	}
	public void setP3(Float p3) {
		this.p3 = p3;
	}
	public Float getT3() {
		return t3;
	}
	public Integer getTw() {
		return tw;
	}
	public void setTw(Integer tw) {
		this.tw = tw;
	}
	public Integer getTp() {
		return tp;
	}
	public void setTp(Integer tp) {
		this.tp = tp;
	}
	public Integer getTpo() {
		return tpo;
	}
	public void setTpo(Integer tpo) {
		this.tpo = tpo;
	}
	public Integer getPd() {
		return pd;
	}
	public void setPd(Integer pd) {
		this.pd = pd;
	}
	public Integer getAlt() {
		return alt;
	}
	public void setAlt(Integer alt) {
		this.alt = alt;
	}
	public Integer getPs() {
		return ps;
	}
	public void setPs(Integer ps) {
		this.ps = ps;
	}
	public void setT3(Float t3) {
		this.t3 = t3;
	}
	
}
