package com.apms.bs.dataprase.vo.a19a21a24;

import smartx.framework.common.bs.CommDMO;

import com.apms.ApmsConst;
import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsAcwVo;
import com.apms.bs.dataprase.vo.AcarsHeadVo;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;
import com.apms.vo.ApmsVarConst;

public class AcarsXxVo_A19_A320 extends AcarsLineDataVo{
	private String pcsw;
	private Integer vscb;
	private Integer pdc;
	private Integer vf;
	private Integer vw;
	private Integer va;
	private Integer ovp;
	private Integer cpc;
	private String row_str;
	
	public AcarsXxVo_A19_A320(String str, String Xx) throws Exception{
		row_str = Xx;
		originalStr = str;
		String tempStr = str.substring(2).trim();
		for(int i=0;i<6;i++)
			tempStr=tempStr.replaceAll("  ", " ");
		String[] columns;
		columns = StringUtil.splitString2Array(tempStr, ",", true);	
		checkFieldsNum(originalStr,columns, 8);
		pcsw = columns[0];
		vscb = ReportParseUtil.strToInteger(columns[1], "vscb");
		pdc = ReportParseUtil.strToInteger(columns[2], "pdc");
		vf = ReportParseUtil.strToInteger(columns[3], "vf");
		vw = ReportParseUtil.strToInteger(columns[4], "vw");
		va = ReportParseUtil.strToInteger(columns[5], "va");
		ovp = ReportParseUtil.strToInteger(columns[6], "ovp");
		
		if (columns[7].contains("-")|columns[7].contains("X"))
		{ 
			cpc=null;
		}else{
			cpc = ReportParseUtil.strToInteger(columns[7],"cpc");
		}
	}
	
	public void insertToTable(String msgno,AcarsHeadVo headVo,int i, String acmodel,String rptno) throws Exception{
		CommDMO dmo = new CommDMO();
		String insertSqlx = "insert into a_dfd_a19_list_x_row(ID,MSG_NO,ROW_STR,ROW_NUM,"
			+ "PCSW,VSCB,PDC,VF,VW,VA,OVP,CPC,"
			+ "UPDATE_DATE)" 
			+ " values(S_A_DFD_HEAD.nextval," 
			+ "?,?,?,?,?,?,?,?,?,?," 
			+ "?,sysdate)";
				
		AcarsAcwVo pcsw1 = new AcarsAcwVo(ApmsVarConst.CW_PCSW, getPcsw());

		pcsw1.insertToTable(msgno,  headVo.getAcid(),acmodel, rptno, row_str);
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSqlx, msgno,getRow_str(),i+1,
				getPcsw(),getVscb(),getPdc(),getVf(),getVw(),getVa(),getOvp(),getCpc());
		
	}

	public String getPcsw() {
		return pcsw;
	}

	public void setPcsw(String pcsw) {
		this.pcsw = pcsw;
	}

	public Integer getVscb() {
		return vscb;
	}

	public void setVscb(Integer vscb) {
		this.vscb = vscb;
	}

	public Integer getPdc() {
		return pdc;
	}

	public void setPdc(Integer pdc) {
		this.pdc = pdc;
	}

	public Integer getVf() {
		return vf;
	}

	public void setVf(Integer vf) {
		this.vf = vf;
	}

	public Integer getVw() {
		return vw;
	}

	public void setVw(Integer vw) {
		this.vw = vw;
	}

	public Integer getVa() {
		return va;
	}

	public void setVa(Integer va) {
		this.va = va;
	}

	public Integer getOvp() {
		return ovp;
	}

	public void setOvp(Integer ovp) {
		this.ovp = ovp;
	}

	public Integer getCpc() {
		return cpc;
	}

	public void setCpc(Integer cpc) {
		this.cpc = cpc;
	}

	public String getRow_str() {
		return row_str;
	}

	public void setRow_str(String row_str) {
		this.row_str = row_str;
	}
	

}
