package com.apms.bs.sysconfig;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

import com.apms.ApmsConst;
import com.apms.bs.util.XmlUtil;
import com.apms.vo.SysParamConfVo;

/**
 * APMS系统参数缓存、配置类
 * @author jerry
 * @date Mar 25, 2013
 */
public class ApmsSysParamConfService {
	
	private Logger logger = NovaLogger.getLogger(this.getClass());	
	private static ApmsSysParamConfService paramConf;
	
	private String sysParamMtcode = "MT_APMS_SYSPARAMCONFIG";//系统配置元数据编码
	
	private SysParamConfVo confVo;
	
	private ApmsSysParamConfService() throws Exception{
		//初始化，
		initParamConifg();
	}
	
	/**
	 * 单例类，只初始化一次
	 * @return
	 * @throws Exception 
	 */
	public static ApmsSysParamConfService getInstance() throws Exception{
		if(paramConf == null){
			try{
				paramConf = new ApmsSysParamConfService();
			}catch (Exception e) {
				e.printStackTrace();
				throw new Exception("ApmsSysParamConfig类初始化配置失败！");
			}
		}
		
		return paramConf;
	}
	
	/**
	 * 刷新缓存
	 * @throws Exception
	 */
	public void refreshCache() throws Exception{
		//TODO 缓存刷新
	}
	
	
	
	public SysParamConfVo getConfVo() {
		return confVo;
	}
	
	public RegressionVarVo getRegressionVar() {
		return confVo.getRegressionVar();
	}

	//初始化配置
	private void initParamConifg() throws Exception{
		
		CommDMO dmo = new CommDMO();
		String sql = "select ID,NAME,CODE,CONTENT from pub_metadata_templet mt where mt.code = ?";
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_DEFAULT,sql,sysParamMtcode);
		if(vos.length > 0){
			String content = vos[0].getStringValue("CONTENT");
			//转换成SysParamConfVo对象
			confVo = XmlUtil.getObjectByXml(SysParamConfVo.class, content, false);
			logger.info("系统参数配置初始化缓存成功！");
		}else{
			throw new Exception("未找到系统参数配置["+sysParamMtcode+"]对应的元数据!");
		}
	}
	

}
