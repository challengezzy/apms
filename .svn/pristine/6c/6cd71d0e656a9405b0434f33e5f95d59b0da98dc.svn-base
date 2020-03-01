package com.apms.bs.dataprase.vo.a23;

import java.util.Date;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.DateUtil;
import com.apms.bs.util.StringUtil;

/**
 * 氧气温度对象
 * @author zzy
 *
 */
public class AcarsSTempVo extends AcarsLineDataVo{
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;//原始报文，如S20248,0055,1758,DEC03,235212
	private String ckpt_t;//客舱温度
	private String sat;//静温
	private String press;//氧气压力
	private String date_utc;//采样时间
	
	private Date dateUtc;
	
	/**
	 * 修正采样时间数据，跨天的情况，日期+1
	 */
	public void correctDateUtc(){
		if(dateUtc != null){
			logger.debug("跨天采样时间数据修正！");
			Date newDt = DateUtil.moveDay(dateUtc, 1);
			setDateUtc(newDt);
		}
	}
	
	public void correctNumberData(AcarsSTempVo s7){
		if(ckpt_t == null)
			ckpt_t = s7.getCkpt_t();
		
		if(sat == null)
			sat = s7.getSat();
		
		if(press == null)
			press = s7.getPress();
		
	}
	
	
	public void setDateUtc(Date dateUtc) {
		this.dateUtc = dateUtc;
		date_utc = DateUtil.getLongDate(dateUtc);
	}

	
	public Date getDateUtc() {
		return dateUtc;
	}

	/**
	 * S系统温度原始字符串
	 * @param str
	 * @throws Exception 
	 */
	public AcarsSTempVo(String str,String transdate) throws Exception{
		originalStr = str;
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true);
		checkFieldsNum(originalStr,columns, 5);
		if( !columns[0].contains("--") && !columns[0].contains("XX") ){
			try{
			ckpt_t = new Integer(columns[0].substring(0, 3)).toString() +"."+ columns[0].substring(3, 4);
			}
			catch(NumberFormatException e)
			{
				throw new NumberFormatException(columns[0].substring(0, 3)+"无法转换成Integer类型");
			}
		}
		
		if( !columns[1].contains("--") && !columns[1].contains("XX") ){
			sat = columns[1].substring(0, 3) +"."+ columns[1].substring(3, 4);
			sat = sat.replace("- ", "");
		}
		
		if( !columns[2].contains("--") && !columns[2].contains("XX") ){
			press = columns[2];
		}
		
		try{
			date_utc = transdate.substring(0, 10) +" "+ columns[4].substring(0,2)+":"
			+ columns[4].substring(2,4)+":" + columns[4].substring(4,6);
			dateUtc = DateUtil.StringToDate(date_utc, "yyyy-MM-dd HH:mm:ss");
		}catch (Exception e) {
			logger.debug("date_utc转换为日期异常！");
		}
	}

	public String getCkpt_t() {
		return ckpt_t;
	}

	public void setCkpt_t(String ckpt_t) {
		this.ckpt_t = ckpt_t;
	}

	public String getSat() {
		return sat;
	}

	public void setSat(String sat) {
		this.sat = sat;
	}

	public String getPress() {
		return press;
	}

	public void setPress(String press) {
		this.press = press;
	}

	public String getDate_utc() {
		return date_utc;
	}

	public void setDate_utc(String date_utc) {
		this.date_utc = date_utc;
	}

	public String getOriginalStr() {
		return originalStr;
	}
	
}
