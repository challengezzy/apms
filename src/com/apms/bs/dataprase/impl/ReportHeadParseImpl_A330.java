package com.apms.bs.dataprase.impl;

import java.util.Date;

import smartx.framework.common.vo.HashVO;

import com.apms.bs.dataprase.ReportHeadParseClass;
import com.apms.bs.dataprase.vo.AcarsHeadVo;
import com.apms.bs.util.StringUtil;

public class ReportHeadParseImpl_A330 extends ReportHeadParseClass{

	@Override
	public AcarsHeadVo parseHeadData(HashVO acarsVo, String msgno, String content, Date trans_time,Date trans_time_full) throws Exception {
		AcarsHeadVo head = new AcarsHeadVo();
		head.setTransdate(trans_time);
		String[] lines;
		
		String tmpStr = content;			
		lines = StringUtil.splitString2Array(tmpStr, "\n", true);
		
		
		//logger.debug("报文每行数据如下：");
		for(int i=0; i<lines.length; i++){
			String line = lines[i].trim();
			//logger.debug("第"+i+"行:" + line);
			
			if(line.startsWith("C1")){
				head.setC1_330(line);
				
			}else if(line.startsWith("C2")){
				head.setC2_330(line);
				
			}else if(line.startsWith("C3")){
				head.setC3_330(line);
				
			}
		}
		
		return head;
	}

}
