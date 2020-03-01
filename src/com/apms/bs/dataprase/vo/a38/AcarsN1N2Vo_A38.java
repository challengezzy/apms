package com.apms.bs.dataprase.vo.a38;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsN1N2Vo_A38 extends AcarsLineDataVo{
	
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	
	private String ct5atp;
	private String cptatp;
	private String cwfatp;
	private String igvatp;
	private String bdtmax;
	
	/**
	 * n1/n2行报文内容解析
	 * @param str n1/n2行内容 
	 * @param isRep 是否rep新报文
	 * @throws Exception
	 */
	public AcarsN1N2Vo_A38(String str,boolean isRep) throws Exception{
		originalStr = str;
		//logger.debug("ExNx content: " + originalStr);
		String[] columns;
		String tempStr = str.substring(2).trim();
		if(tempStr.endsWith("/")){
			tempStr=tempStr.substring(0, tempStr.length()-1);
			columns= StringUtil.splitString2Array(tempStr, ",", true); 
			checkFieldsNum(originalStr,columns, 5);
			ct5atp = ReportParseUtil.getNumberStr(columns[0]);
			
			if(isRep){//REP报文自带小数点
				cptatp = columns[1];
				cwfatp = columns[2];
				igvatp = columns[3];
			}else{
				cptatp = ReportParseUtil.strToStrWithIntPostion(columns[1], 1);
				cwfatp = ReportParseUtil.strToStrWithIntPostion(columns[2], 3);
				igvatp = ReportParseUtil.strToStrWithIntPostion(columns[3], 3);
			}
			
			bdtmax = ReportParseUtil.getNumberStr(columns[4]);
			if(bdtmax.endsWith("/")){
				bdtmax=bdtmax.substring(0, bdtmax.length()-1);
			}
		}else{
			
			columns = StringUtil.splitString2Array(tempStr, " ", false); 
			checkFieldsNum(originalStr,columns, 8);
			ct5atp = ReportParseUtil.getNumberStr(columns[0]);
			
			if(isRep){
				cptatp = columns[2];
				cwfatp = columns[5];
				igvatp = columns[6];
			}else{
				cptatp = ReportParseUtil.strToStrWithIntPostion(columns[2], 1);
				cwfatp = ReportParseUtil.strToStrWithIntPostion(columns[5], 3);
				igvatp = ReportParseUtil.strToStrWithIntPostion(columns[6], 3);
			}
			
			bdtmax = ReportParseUtil.getNumberStr(columns[7]);
		}
	
	}

	public String getCt5atp() {
		return ct5atp;
	}

	public void setCt5atp(String ct5atp) {
		this.ct5atp = ct5atp;
	}

	public String getCptatp() {
		return cptatp;
	}

	public void setCptatp(String cptatp) {
		this.cptatp = cptatp;
	}

	public String getCwfatp() {
		return cwfatp;
	}

	public void setCwfatp(String cwfatp) {
		this.cwfatp = cwfatp;
	}

	public String getIgvatp() {
		return igvatp;
	}

	public void setIgvatp(String igvatp) {
		this.igvatp = igvatp;
	}

	public String getBdtmax() {
		return bdtmax;
	}

	public void setBdtmax(String bdtmax) {
		this.bdtmax = bdtmax;
	}


}
