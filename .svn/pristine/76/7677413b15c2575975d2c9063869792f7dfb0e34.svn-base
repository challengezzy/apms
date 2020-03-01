package com.apms.bs.dataprase.impl2;

import com.apms.bs.dataprase.impl.A38DataParseImpl;
import com.apms.bs.dataprase.vo.AcarsDfdA38Vo_A320;
import com.apms.bs.dataprase.vo.a38.AcarsE1Vo_A38;
import com.apms.bs.dataprase.vo.a38.AcarsN1N2Vo_A38;
import com.apms.bs.util.StringUtil;

/**
 * A23报文解析实现类
 * @author zzy
 *
 */
public class A38DataParseImpl_REP extends A38DataParseImpl{
	
	/**
	 * 报文解析
	 * @param graphStr 报文内容
	 * @param transdate 报文传送日期 YYYY-MM-DD
	 * @return
	 * @throws Exception
	 */
	protected AcarsDfdA38Vo_A320 praseA38Data(String graphStr,String transdate) throws Exception{
		AcarsDfdA38Vo_A320 a38vo = new AcarsDfdA38Vo_A320();
		String[] lines  = StringUtil.splitString2Array(graphStr, "\r\n", false);
		logger.debug("报文每行数据如下：");
		for(int i=0; i<lines.length; i++){
			String line = lines[i].trim();
			//logger.debug("第"+i+"行:" + line);
			if(line.startsWith("E1")){
				a38vo.setE1(new AcarsE1Vo_A38(line));
				asn=a38vo.getE1().getAsn();
			}else if(line.startsWith("N1")){
				a38vo.setN1(new AcarsN1N2Vo_A38(line,true));
				
			}else if(line.startsWith("N2")){
				a38vo.setN2(new AcarsN1N2Vo_A38(line,true));
				
			}else if(line.startsWith("T1")){
				
				String ct5atp_t1Str=line.substring(2).trim();
				if(ct5atp_t1Str.contains("---")||ct5atp_t1Str.contains("XXX")){
					ct5atp_t1=null;
				}else{
					ct5atp_t1=ct5atp_t1Str;
					if(ct5atp_t1.endsWith("/")){
						ct5atp_t1=ct5atp_t1.substring(0, ct5atp_t1.length()-1);
					}
				}
			}else if(line.startsWith("T2")){
				String ct5atp_t2Str=line.substring(2).trim();
				if(ct5atp_t2Str.contains("---")||ct5atp_t2Str.contains("XXX")){
					ct5atp_t2=null;
				}else{
					ct5atp_t2=ct5atp_t2Str;
					if(ct5atp_t2.endsWith("/")){
						ct5atp_t2=ct5atp_t2.substring(0, ct5atp_t2.length()-1);
					}
				};
			}
		}
		
		return a38vo;
	}
}
