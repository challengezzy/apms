package com.apms.bs.dataprase.impl;

import java.util.Date;
import smartx.framework.common.vo.HashVO;
import com.apms.bs.dataprase.ReportHeadParseClass;
import com.apms.bs.dataprase.vo.AcarsHeadVo;
import com.apms.bs.util.StringUtil;

public class ReportHeadParseImpl_A320 extends ReportHeadParseClass {

	@Override
	public AcarsHeadVo parseHeadData(HashVO acarsVo, String msgno, String content, Date trans_time, Date trans_time_full) throws Exception {
		AcarsHeadVo head = new AcarsHeadVo();
		head.setTransdate(trans_time);
		String[] lines;
		String rptno = acarsVo.getStringValue("RPTNO");
		boolean isA34 = true; // 判断是否存在A34报文
		boolean othercaseA34 = false; // 判断是否A34报文DMU=I----
		String code = null;

		if (rptno.equals("A38")) {
			
			String lineStr = StringUtil.replaceAll(content.trim(), "\r\n", "");
			lines = StringUtil.splitString2Array(lineStr, "/", true);
			if(lines.length<=6){
				lines = StringUtil.splitString2Array(content.trim(), "\r\n", true);
			}
			for (int i = 0; i < lines.length; i++) {
				String line = lines[i].trim();
				if (line.startsWith("CC")) {
					head.setCC_320(line);
				} else if (line.startsWith("C0")) {
					head.setC0_320(line);
				} else if (line.startsWith("C1")) {
					head.setC1_320(line);
				} else if (line.startsWith("CE")) {
					head.setCE_320(line);
				}
			}
		} else {
			String tmpStr = content.replaceAll("[\n\r]", " ");
			lines = StringUtil.splitString2Array(tmpStr, "/", true);

			// 检查A34报文CE行是否存在DMU=I----的情况。
			// 如果是，othercaseA34为true不进行常规head解析，只采集必要参数
			if (rptno.equals("A34")) {
				for (int i = 0; i < lines.length; i++) {
					String line = lines[i].trim();
					// 采集code参数
					if (line.startsWith("C1")) {
						String tempStr1 = line.substring(2);
						if (tempStr1.startsWith(",")) {
							tempStr1 = line.substring(3);
						}
						String columns[];
						columns = StringUtil.splitString2Array(tempStr1, ",", true);
						code = columns[2];
					}
					if (line.startsWith("CE")) {
						String tempStr1 = line.substring(2);
						String columns[];
						columns = StringUtil.splitString2Array(tempStr1, ",", true);
						String dmu = columns[6];
						if (dmu.contains("I---")) {
							othercaseA34 = true;
							String acnum = acarsVo.getStringValue("AC_ID");
							head.setAcid(acnum);
							head.setCode(code);
							head.setDateUtc(trans_time_full);
							head.setDmu(dmu);
						}
					}
				}
			}

			// 如果不存在DMU=I-----情况，othercaseA34为false，进行常规head解析
			if (othercaseA34 == false) {
				for (int i = 0; i < lines.length; i++) {
					String line = lines[i].trim();
					// logger.debug("第"+i+"行:" + line);

					if (line.startsWith("CC") || line.indexOf("CCB-") > 0 ) {
						if(line.startsWith("CC")){
							head.setCC_320(line);
						}else{
							line = line.substring(line.indexOf("CCB-"));
							head.setCC_320(line);
						}

					} else if (line.startsWith("C1")) {
						head.setC1_320(line);
					} else if (line.startsWith("C0")) {
						head.setC0_320(line);

					} else if (line.startsWith("CE")) {
						head.setCE_320(line);
						isA34 = false;
						break;

					} else if (line.startsWith("EC") && isA34 == true) {
						head.setEC_320(line);
					}
				}
			}
		}

		return head;
	}

}
