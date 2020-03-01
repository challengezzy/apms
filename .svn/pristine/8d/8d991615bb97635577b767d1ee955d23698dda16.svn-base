package com.apms.bs.dataprase.impl;

import java.util.Date;

import smartx.framework.common.vo.HashVO;

import com.apms.bs.dataprase.ReportHeadParseClass;
import com.apms.bs.dataprase.vo.AcarsHeadVo;
import com.apms.bs.util.StringUtil;

public class ReportHeadParseImpl_A340 extends ReportHeadParseClass{

	@Override
	public AcarsHeadVo parseHeadData(HashVO acarsVo, String msgno, String content, Date trans_time,Date trans_time_full) throws Exception {
		AcarsHeadVo head = new AcarsHeadVo();
		head.setTransdate(trans_time);
		String[] lines;
		
		
//			String tmpStr = content.replaceAll("[\n\r]", " ");
		String tmpStr = content;
		lines = StringUtil.splitString2Array(tmpStr, "\n", true);
		
		
		logger.debug("报文每行数据如下：");
		head.setC1_340(lines[4]);
		head.setC2_340(lines[5]);
		head.setC3_340(lines[6]);
	
		
		return head;
	}

}
