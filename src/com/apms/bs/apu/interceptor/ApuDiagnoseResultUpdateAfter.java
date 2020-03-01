package com.apms.bs.apu.interceptor;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.NovaLogger;

import smartx.framework.metadata.vo.Pub_Templet_1VO;
import smartx.publics.form.bs.service.FormInterceptor;

import com.apms.ApmsConst;

public class ApuDiagnoseResultUpdateAfter  implements FormInterceptor {
	protected Logger logger = NovaLogger.getLogger(this.getClass());
	private CommDMO dmo = new CommDMO(); 
	private static final int willDo = 0;  //未处理
	private static final int notDo = 9;  //不用处理

	/**
	 * 单条数据新增、更新时，使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, Map<String, Object> dataValue) throws Exception {
		
		String msg_no =  dataValue.get("MSG_NO").toString();
		String datatype_old = dataValue.get("DATATYPE_OLD").toString();
		String datetype_new = dataValue.get("DATATYPE").toString();
		
		if(datatype_old != datetype_new){
			if("正常".equals(datetype_new)){
				StringBuilder sb = new StringBuilder("update  L_APU_DIAGNOSERESULT t set t.ISALERTED =" + notDo + "where t.msg_no ="+msg_no);
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, sb.toString());
				dmo.commit(ApmsConst.DS_APMS);
			}else{
				StringBuilder sb = new StringBuilder("update  L_APU_DIAGNOSERESULT t set t.ISALERTED =" + willDo + "where t.msg_no ="+msg_no);
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, sb.toString());
				dmo.commit(ApmsConst.DS_APMS);
			}

		}
		
		logger.debug("apu诊断数据修改前置拦截器执行完成！");
	
	}
	
	/**
	 * 列表数据删除，或列表数据新增、删除时使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, List<Map<String, Object>> dataValueList) throws Exception {
		
	}

	/**
	 * 不常用 ，待查证
	 */
	public void doSomething(Map<String, Object> map) throws Exception {
		
	}

	/**
	 * 待查证
	 */
	public void doSomething(List<Map<String, Object>> dataValueList) throws Exception {

	}

}
