package com.apms.bs.dataprase.impl2;

import java.util.Date;

import com.apms.bs.dataprase.impl.A27DataParseImpl_A320;
import com.apms.bs.dataprase.vo.AcarsDfdA27Vo_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsAL_ARVo_A27_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsCF_CGVo_A27_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsCL_CRVo_A27_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsDL_DRVo_A27_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsELVo_A27_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsERVo_A27_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsML_NRVo_A27_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsPPVo_A27_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsZ4_Z5Vo_A27_A320;
import com.apms.bs.util.StringUtil;

public class A27DataParseImpl_A320_REP extends A27DataParseImpl_A320 {

	public AcarsDfdA27Vo_A320 praseA27Data(String gStr,String transdate) throws Exception{
		AcarsDfdA27Vo_A320 a27vo = new AcarsDfdA27Vo_A320();
		String trans_date = transdate;
		String tmpStr = gStr.replaceAll("[\n\r]", " ");
		
		String[] lines = StringUtil.splitString2Array(tmpStr, "/", true);
		
		boolean isRep = true;
		logger.debug("报文每行数据如下：");
		Date alTime = null;
//		String alday = null;
		Date arTime = null;
//		String arday = null;
		for(int i=0; i<lines.length; i++){
			String line = lines[i].trim();
			//logger.debug("第"+i+"行:" + line);
			
			if(line.startsWith("PP")){				
				a27vo.setPp(new AcarsPPVo_A27_A320(line, isRep));
			}else if(line.startsWith("Z4")){					
				a27vo.setZ4(new AcarsZ4_Z5Vo_A27_A320(line, trans_date, isRep));
			}else if(line.startsWith("Z5")){				
				a27vo.setZ5(new AcarsZ4_Z5Vo_A27_A320(line, trans_date, isRep));
			}else if(line.startsWith("AL")){				
				a27vo.setAl(new AcarsAL_ARVo_A27_A320(line, trans_date, isRep));
				alTime = a27vo.getAl().getTime();
//				alday = a27vo.getAl().getYymmdd();
			}else if(line.startsWith("AR")){				
				a27vo.setAr(new AcarsAL_ARVo_A27_A320(line, trans_date, isRep));
				arTime = a27vo.getAr().getTime();
//				arday = a27vo.getAr().getYymmdd();
			}else if(line.startsWith("ML")){				
				a27vo.setMl(new AcarsML_NRVo_A27_A320(line, isRep));
			}else if(line.startsWith("DL")){		
				a27vo.setDl(new AcarsDL_DRVo_A27_A320(line,alTime,trans_date));
			}else if(line.startsWith("NR")){				
				a27vo.setNr(new AcarsML_NRVo_A27_A320(line, isRep));
			}else if(line.startsWith("DR")){		
				a27vo.setDr(new AcarsDL_DRVo_A27_A320(line,arTime,trans_date));
			}else if(line.startsWith("CL")){				
				a27vo.setCl(new AcarsCL_CRVo_A27_A320(line, isRep));
			}else if(line.startsWith("CR")){				
				a27vo.setCr(new AcarsCL_CRVo_A27_A320(line, isRep));
			}else if(line.startsWith("EL")){				
				a27vo.setEl(new AcarsELVo_A27_A320(line, isRep));
			}else if(line.startsWith("ER")){				
				a27vo.setEr(new AcarsERVo_A27_A320(line, isRep));
			}else if(line.startsWith("CF")){				
				a27vo.setCf(new AcarsCF_CGVo_A27_A320(line));
			}else if(line.startsWith("CG")){				
				a27vo.setCg(new AcarsCF_CGVo_A27_A320(line));
			}
		}
	
		return a27vo;
		
	}
}
