package com.apms.bs.aircraft.interceptor;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;
import smartx.framework.metadata.vo.Pub_Templet_1VO;
import smartx.publics.form.bs.service.FormInterceptor;

public class AirFlylogViewBefore  implements FormInterceptor {

	protected Logger logger = NovaLogger.getLogger(this.getClass());

	@Override
	public void doSomething(Map<String, Object> arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doSomething(List<Map<String, Object>> arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doSomething(Pub_Templet_1VO templetVO, Map<String, Object> dataValue)throws Exception {
		// TODO Auto-generated method stub
		double FIAIR = new Double(dataValue.get("FIAIR_h").toString());
		double FIBLOCK = new Double(dataValue.get("FIBLOCK_h").toString());
		
		double VDFCAIR = new Double(dataValue.get("VDFCAIR_h").toString());
		double VDFCBLOCK = new Double(dataValue.get("VDFCBLOCK_h").toString());
		
		dataValue.put("FIAIR", FIAIR*60);
		dataValue.put("FIBLOCK", FIBLOCK*60);
		dataValue.put("VDFCAIR", VDFCAIR*60);
		dataValue.put("VDFCBLOCK", VDFCBLOCK*60);
		
		
	}

	@Override
	public void doSomething(Pub_Templet_1VO arg0, List<Map<String, Object>> arg1)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
