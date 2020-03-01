package com.apms.bs.dataprase.vo.a25a26a27;

import java.util.Date;

import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.DateUtil;
import com.apms.bs.util.StringUtil;

public class AcarsDL_DRVo_A27_A320 extends AcarsLineDataVo{
	private Date qdt0;
	private Date qdt1; 
	private Date qdt2; 
	private Date qdt3; 
	private Date qdt4;
		
	public AcarsDL_DRVo_A27_A320(String str,Date zTime,String transdate) throws Exception{
		originalStr = str;
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 5);

		if (columns[0].contains("---") ||  columns[0].contains("XXX")){
			qdt0 = null;
		}else if (zTime==null){
			qdt0 = null;
		}else{		
			int qdt = Integer.valueOf(columns[0].replace("S", ""));
			qdt0 = DateUtil.moveSecond(zTime, qdt);
		}
		qdt1 = getQdt(columns[1],transdate);
		qdt2 = getQdt(columns[2],transdate);
		qdt3 = getQdt(columns[3],transdate);
		qdt4 = getQdt(columns[4],transdate);
		
	}
	
	
	private Date getQdt(String hhmmss, String transdate){
		Date newDat = null;
		if (hhmmss.contains("000000")){
			newDat = null;
		}else {
			try{
				String newhhmmss = hhmmss.substring(0, 2) + ":" + hhmmss.substring(2, 4) + ":" + hhmmss.substring(4, 6);
				String newday = transdate.substring(0,10) + " " +newhhmmss;
				newDat = DateUtil.StringToDate(newday, "yyyy-MM-dd HH:mm:ss");			
			}catch (Exception e) {
				logger.debug("date_utc转换为日期异常！");
			}
		}
		
		return newDat;
	}
	public Date getQdt0() {
		return qdt0;
	}
	public void setQdt0(Date qdt0) {
		this.qdt0 = qdt0;
	}
	public Date getQdt1() {
		return qdt1;
	}
	public void setQdt1(Date qdt1) {
		this.qdt1 = qdt1;
	}
	public Date getQdt2() {
		return qdt2;
	}
	public void setQdt2(Date qdt2) {
		this.qdt2 = qdt2;
	}
	public Date getQdt3() {
		return qdt3;
	}
	public void setQdt3(Date qdt3) {
		this.qdt3 = qdt3;
	}
	public Date getQdt4() {
		return qdt4;
	}
	public void setQdt4(Date qdt4) {
		this.qdt4 = qdt4;
	}


}
