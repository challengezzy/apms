package com.apms.bs;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.NovaLogger;

import com.apms.ApmsConst;
import com.apms.bs.neuralnet.ApuDiagnoseresultData;
import com.apms.bs.neuralnet.ApuTraindata;
import com.apms.bs.neuralnet.EngineTraindata;

/**
 * 神经网络相关服务类
 * @author chenyong
 *
 */
public class NeuralnetFormService {
	protected  Logger logger = NovaLogger.getLogger(this.getClass());
	
	public boolean insertApuTraindata(String msgNo,String dateType,String explain,String user) throws Exception{
		try{
			ApuTraindata apuTraindata = new ApuTraindata();
			return apuTraindata.insertApuTraindata(msgNo, dateType, explain, user);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("插入apu样本数据失败!", e);
			throw e;
		}finally{
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	public void updateApuTraindata(String msgNo,String dateType,String explain,String user) throws Exception{
		try{
			ApuTraindata apuTraindata = new ApuTraindata();
			apuTraindata.updateApuTraindata(msgNo, dateType, explain, user);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("更新apu样本点数据失败!", e);
			throw e;
		}finally{
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	public String apuDiagnoseresultAddTraindata(String msg_no, String datatype_old, String datetype_new,
			String acnum, String dateStr, String asn, String apumodelid, String comments, String user) throws Exception {
		try{
			ApuDiagnoseresultData apuDiagnoseresultData = new ApuDiagnoseresultData();
			return apuDiagnoseresultData.apuDiagnoseresultAddTraindata(msg_no, datatype_old, datetype_new, acnum, dateStr, asn, apumodelid, comments, user);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("诊断数据添加到样本点数据失败!", e);
			throw e;
		}finally{
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	public boolean insertEngineTraindata(String msgNo,String dateType,String explain,String user,
			String position,String rptNo,String engineType) throws Exception{
		try{
			EngineTraindata engineTraindata = new EngineTraindata();
			return engineTraindata.insertEngineTraindata( msgNo, dateType, explain, user,
					 position, rptNo, engineType);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("插入发动机样本数据失败!", e);
			throw e;
		}finally{
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	public void updateEngineTraindata(String msgNo,String dateType,String explain,String user,String position) throws Exception{
		try{
			EngineTraindata engineTraindata = new EngineTraindata();
			engineTraindata.updateEngineTraindata(msgNo, dateType, explain, user,position);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("更新发动机样本点数据失败!", e);
			throw e;
		}finally{
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
}
