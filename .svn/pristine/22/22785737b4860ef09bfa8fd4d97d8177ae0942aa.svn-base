package com.apms.bs.datacompute;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.apms.ApmsConst;
import com.apms.bs.datacompute.vo.FieldComputeVo;
import com.apms.bs.sysconfig.ApmsSysParamConfService;
import com.apms.bs.sysconfig.RegressionVarVo;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

/**
 * 报文数据计算缓存服务类
 * 
 * @author jerry
 * @date Nov 7, 2014
 */
public class DataComputeCacheService {

	private Logger logger = NovaLogger.getLogger(this);
	private CommDMO dmo = new CommDMO();
	
	private RegressionVarVo regVar;//回归计算配置变量
	
	private int pointsN;//N点数目
	
	private int maxHisNum = 30;

	/** 监控对象和该对象的规则列表 key=(acnum_rptno_fname), 已计算对象列表 */
	private Map<String, ArrayList<FieldComputeVo>> fieldvoMap = new ConcurrentHashMap<String, ArrayList<FieldComputeVo>>();

	private static DataComputeCacheService fieldCacheService;

	private DataComputeCacheService() throws Exception{
		regVar = ApmsSysParamConfService.getInstance().getRegressionVar();
		
		pointsN = regVar.getNumberOfPoints();
		
		maxHisNum = pointsN*2+10;
	}

	public static DataComputeCacheService getInstance() throws Exception {
		if (fieldCacheService != null)
			return fieldCacheService;
		else
			fieldCacheService = new DataComputeCacheService();

		return fieldCacheService;
	}
	
	public void addFieldVoToCache(FieldComputeVo fieldvo) throws Exception{
		
		String key = getFieldVoMapKey(fieldvo.getAcnum(), fieldvo.getRptno(), fieldvo.getF_name());
		ArrayList<FieldComputeVo> hisList = fieldvoMap.get(key);
		//没有该飞机+报文相关字段的缓存数据
		if(hisList == null){
			logger.debug("添加第一个历史数据节点acnum["+fieldvo.getAcnum()+"],rptno["+fieldvo.getRptno()+"],fname["+fieldvo.getF_name()+"]到缓存!");
			hisList = new ArrayList<FieldComputeVo>(maxHisNum);
			hisList.add(0, fieldvo);
		}else{
			hisList.add(0, fieldvo);
		}
		
		//超出最大储存数，则移除最后一个节点(即时间在最前面的节点)
		if(hisList.size() > maxHisNum){
			hisList.remove(hisList.size()-1);
		}
		
	}
	
	/**
	 * 查询当前计算节点的N点均计算历史数据
	 * @param fieldVo
	 * @param changeMsgno
	 * @return
	 * @throws Exception
	 */
	public ArrayList<FieldComputeVo> getFieldRollNVoHis(String acnum,String rptno,String fname,String curMsgno,String changeMsgno) throws Exception{
		String key = getFieldVoMapKey(acnum, rptno, fname);
		ArrayList<FieldComputeVo> hisList = fieldvoMap.get(key);
		
		//没有该飞机+报文相关字段的缓存数据
		if(hisList == null){
			logger.debug("计算历史数据acnum["+acnum+"],rptno["+rptno+"],fname["+fname+"]没有缓存过，查询历史数据并缓存!");
			
			hisList = new ArrayList<FieldComputeVo>(maxHisNum);
			StringBuilder sb = new StringBuilder("SELECT /*+RULE*/ ID, MSG_NO, RPTNO, ACNUM, F_NAME, F_VALUE, F_VALUE_ROLL_N, N_VALUE FROM ( ");
			sb.append("SELECT ID, MSG_NO, RPTNO, ACNUM, F_NAME, F_VALUE, F_VALUE_ROLL_N, N_VALUE ");
			sb.append(" FROM A_DFD_FIELD_NROLL WHERE ACNUM=? AND RPTNO=? AND F_NAME=? AND MSG_NO>? ORDER BY MSG_NO DESC ");
			sb.append(" ) WHERE ROWNUM <= " + (maxHisNum) );//考虑飘点情况,多取N条数据
			HashVO[] vos =dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(), acnum,rptno,fname,changeMsgno);
			
			for(int i=0;i<vos.length;i++){
				//HashVo 转换化 FieldComputeVo 对象
				String msgno = vos[i].getStringValue("MSG_NO");
				Double fvalue = vos[i].getDoubleValue("F_VALUE");
				FieldComputeVo fieldvo = new FieldComputeVo(rptno, msgno, acnum, fname, fvalue);
				fieldvo.setF_value_roll5(vos[i].getDoubleValue("F_VALUE_ROLL_N"));
				
				hisList.add(fieldvo);
			}
			
			fieldvoMap.put(key, hisList);
		}
		
		return hisList;
		
	}
	
	/**
	 * 查询当前计算节点的计算历史数据
	 * @param fieldVo
	 * @param changeMsgno
	 * @return
	 * @throws Exception
	 */
	public ArrayList<FieldComputeVo> getFieldComputedVoHis(String acnum,String rptno,String fname,String curMsgno,String changeMsgno) throws Exception{
		String key = getFieldVoMapKey(acnum, rptno, fname);
		ArrayList<FieldComputeVo> hisList = fieldvoMap.get(key);
		
		//没有该飞机+报文相关字段的缓存数据
		if(hisList == null){
			logger.debug("计算历史数据acnum["+acnum+"],rptno["+rptno+"],fname["+fname+"]没有缓存过，查询历史数据并缓存!");
			
			hisList = new ArrayList<FieldComputeVo>(maxHisNum);
			//进行初始化操作
			HashVO[] vos = getFieldComputeHisVos(acnum, rptno, fname, changeMsgno);
			for(int i=0;i<vos.length;i++){
				//HashVo 转换化 FieldComputeVo 对象
				FieldComputeVo fieldvo = hashvoToFieldVo(vos[i]);
				hisList.add(fieldvo);
			}
			
			fieldvoMap.put(key, hisList);
		}
		
		return hisList;
		
	}
	
	/**
	 * 当前节点是changepoint,清空缓存数据
	 * @param acnum
	 * @param rptno
	 * @param fname
	 */
	public void clearCacheByChangePoint(String acnum,String rptno,String fname){
		String key = getFieldVoMapKey(acnum, rptno, fname);
		ArrayList<FieldComputeVo> hisList = fieldvoMap.get(key);
		
		if(hisList == null){
			//本来也没缓存，不用操作
		}else{
			hisList = null; //清空缓存数据
			logger.debug("到更换点，清空缓存数据acnum["+acnum+"],rptno["+rptno+"],fname["+fname+"]!");
		}
		
	}
	
	private String getFieldVoMapKey(String acnum,String rptno,String fname){
		String key = acnum+"_" +rptno+ "_" + fname;
		return key;
	}
	
	/**
	 * 查询当前计算节点的最近3N个历史数据,此处不进行changpoint的判断，在changepoint点时，对应的计算节点应自动清空
	 * @param fieldVo
	 * @param changeMsgno
	 * @return
	 * @throws Exception
	 */
	private HashVO[] getFieldComputeHisVos(String acnum,String rptno,String fname,String changeMsgno) throws Exception{
		String cols = "id, msg_no, rptno, acnum, f_name, f_value, f_value_roll5, v_avg, v_std, v_out, v_pointtype" +
				", v_tsmp_x_avg, v_tsmp_y_avg, v_tsmp, v_tsmp_sign, v_tsmp_alter, x_fieldname, x_value, v_k, v_b" +
				", v_k_avg, v_k_std, v_k_out, v_k_pointtype, v_k_tsmp_x_avg, v_k_tsmp_y_avg, v_k_tsmp, v_k_tsmp_sign" +
				", v_k_tsmp_alter, x_on_estlimitvalue, deta_x_on_estlimitvalue, up_value, down_value, isoverflow, yy";
		
		StringBuilder sb = new StringBuilder("SELECT /*+RULE*/ "+cols+" FROM ( ");
		//最好再加个日期过滤，如12个月内，以提高查询速度
		sb.append("SELECT "+cols+" FROM A_DFD_FIELD_COMPUTE WHERE ACNUM=? AND RPTNO=? AND F_NAME=? AND MSG_NO>? ORDER BY MSG_NO DESC");
		sb.append(" ) WHERE ROWNUM <= " + (maxHisNum) );//考虑飘点情况,多取N条数据
		
		HashVO[] vos =dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(), acnum,rptno,fname,changeMsgno);
		
		return vos;
	}
	
	private FieldComputeVo hashvoToFieldVo(HashVO hashvo){
		String msgno = hashvo.getStringValue("MSG_NO");
		String rptno = hashvo.getStringValue("RPTNO");
		String acnum = hashvo.getStringValue("ACNUM");
		String fname = hashvo.getStringValue("F_NAME");
		Double fvalue = hashvo.getDoubleValue("F_VALUE");
		FieldComputeVo fieldvo = new FieldComputeVo(rptno, msgno, acnum, fname, fvalue);
		
		//F_VALUE_ROLL5 NUMBER(18,3)  Y 字段值，5点均 
		fieldvo.setF_value_roll5(hashvo.getDoubleValue("F_VALUE_ROLL5"));
		//V_AVG                   NUMBER(18,3)  Y                N点均值 
		fieldvo.setV_avg(hashvo.getDoubleValue("V_AVG"));
		//V_STD                   NUMBER(18,3)  Y                N点STD 
		fieldvo.setV_std(hashvo.getDoubleValue("V_STD"));
		//V_OUT                   NUMBER(2)     Y                N点 2个正负STD的飘点告警         
		fieldvo.setV_out(hashvo.getIntegerValue("V_OUT"));
		//V_POINTTYPE             NUMBER(2)     Y                点类型0-普通点(实点)  1-飘点, 2--不良数据 
		fieldvo.setV_pointtype(hashvo.getIntegerValue("V_POINTTYPE"));
		
		//V_TSMP_X_AVG            NUMBER(18,3)  Y                0~N点的数据均值        
		fieldvo.setV_tsmp_x_avg(hashvo.getDoubleValue("V_TSMP_X_AVG"));
		//V_TSMP_Y_AVG            NUMBER(18,3)  Y                N+1~2N点的数据均值 
		fieldvo.setV_tsmp_y_avg(hashvo.getDoubleValue("V_TSMP_Y_AVG"));
		//V_TSMP                  NUMBER(2)     Y                数据独立样本T检验超限结果       
		fieldvo.setV_tsmp(hashvo.getIntegerValue("V_TSMP"));
		//V_TSMP_SIGN             NUMBER(18,3)  Y                独立样本T检验显著性     
		fieldvo.setV_tsmp_sign(hashvo.getDoubleValue("V_TSMP_SIGN"));
		//V_TSMP_ALTER            NUMBER(2)     Y                STA_V1_TSMP=1时 两组数据有明显差异 SIGN在置信区间并且两组数据平均值异常一定百分比，如30% 
		fieldvo.setV_tsmp_alter(hashvo.getIntegerValue("V_TSMP_ALTER"));
		
		//X_FIELDNAME             VARCHAR2(63)  Y                一元回归时，回归变量名       
		fieldvo.setX_fieldname(hashvo.getStringValue("X_FIELDNAME"));
		//X_VALUE                 NUMBER(18,3)  Y                回归变量值             
		fieldvo.setX_value(hashvo.getDoubleValue("X_VALUE"));
		
		//V_K                     NUMBER(30,18) Y                N点基于另一个字段值（如AHRS ）得到一元回归的 K值
		fieldvo.setV_k(hashvo.getDoubleValue("V_K"));
		//V_B                     NUMBER(30,18) Y                STA_V1基于AHRS 得到一元回归的 B值                      
		fieldvo.setV_b(hashvo.getDoubleValue("V_B"));
		
		//V_K_AVG                 NUMBER(30,18) Y                N点 V_K的均值            
		fieldvo.setV_k_avg(hashvo.getDoubleValue("V_K_AVG"));
		//V_K_STD                 NUMBER(30,18) Y                N点 V_K的标准方差   
		fieldvo.setV_k_std(hashvo.getDoubleValue("V_K_STD"));                                     
		//V_K_OUT                 NUMBER(2)     Y                N点 2个正负STD的飘点告警
		fieldvo.setV_k_out(hashvo.getIntegerValue("V_K_OUT"));                                 
		
		//V_K_POINTTYPE           NUMBER(2)     Y                0-普通点(实点)  1-飘点,  2--不良数据  
		fieldvo.setV_k_pointtype(hashvo.getIntegerValue("V_K_POINTTYPE"));
		//V_K_TSMP_X_AVG          NUMBER(30,18) Y                0~N点的数据均值      
		fieldvo.setV_k_tsmp_x_avg(hashvo.getDoubleValue("V_K_TSMP_X_AVG"));
		//V_K_TSMP_Y_AVG          NUMBER(30,18) Y                N+1~2N点的数据均值     
		fieldvo.setV_k_tsmp_y_avg(hashvo.getDoubleValue("V_K_TSMP_Y_AVG"));
		//V_K_TSMP                NUMBER(2)     Y                独立样本T检验结果
		fieldvo.setV_k_tsmp(hashvo.getIntegerValue("V_K_TSMP"));

		//V_K_TSMP_SIGN           NUMBER(18,3)  Y                独立样本T检验显著性      
		fieldvo.setV_k_tsmp_sign(hashvo.getDoubleValue("V_K_TSMP_SIGN"));
		//V_K_TSMP_ALTER          NUMBER(2)     Y                样本T检验告警                  
		fieldvo.setV_k_tsmp_alter(hashvo.getIntegerValue("V_K_TSMP_ALTER"));
		//X_ON_ESTLIMITVALUE      NUMBER(24,10) Y                计算字段到达门限值时，自变量值(如时间)    
		fieldvo.setX_on_estlimitvalue(hashvo.getDoubleValue("X_ON_ESTLIMITVALUE"));
		//DETA_X_ON_ESTLIMITVALUE NUMBER(24,10) Y  计算字段到达门限值时，剩余自变量值(如时间) 
		fieldvo.setDeta_x_on_estlimitvalue(hashvo.getDoubleValue("DETA_X_ON_ESTLIMITVALUE"));
		
		return fieldvo;
	}

}
