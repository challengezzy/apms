package com.apms.bs.flight.interceptor;

import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import com.apms.ApmsConst;
import com.apms.bs.remind.RemindManageService;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;
import smartx.framework.metadata.vo.Pub_Templet_1VO;
import smartx.publics.form.bs.service.FormInterceptor;

/**
 * 服务端拦截器实现类
 */
public class UpdateFaultInfoAfter implements FormInterceptor {

	protected Logger logger = NovaLogger.getLogger(this.getClass());

	/**
	 * 单条数据新增、更新时，使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, Map<String, Object> dataValue) throws Exception {
		CommDMO dmo = new CommDMO();
		double versionNum;
		String querySql="select max(VERSIONNO) VERSIONNO from W_DEFECTREMIND_VERSION where DEFECTREMINDID="+dataValue.get("ID").toString();
		HashVO[] vos= dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, querySql);
		if(vos[0].getDoubleValue("VERSIONNO")==null){
			versionNum=1;
		}else{
			versionNum=vos[0].getIntegerValue("VERSIONNO")+1;
		}
		String defectremindid=dataValue.get("ID").toString();
		String reminddesc=null;
		if(dataValue.get("REMINDDESC")!=null){
			reminddesc=dataValue.get("REMINDDESC").toString();
		}
		String comments=null;
		if(dataValue.get("COMMENTS")!=null){
			comments=dataValue.get("COMMENTS").toString();
		}
		String updateuser=dataValue.get("UPDATEUSER").toString();
		
		//String querySql="SELECT VERSIONNO FROM W_DEFECTREMIND_VERSION WHERE DEFECTREMINDID="+dataValue.get(key)；
		String insertSql="INSERT INTO W_DEFECTREMIND_VERSION VALUES(S_W_DEFECTREMIND_VERSION.NEXTVAL,"+defectremindid+","+versionNum+",'"+reminddesc+"',";
		if(comments==null){
			insertSql=insertSql+"null,";
		}else{
			insertSql=insertSql+"'"+comments+"',";
		}
		insertSql=insertSql+"to_date('"+dataValue.get("UPDATETIME")+"','YYYY-MM-DD HH24:MI:SS'),'"+updateuser+"')";
		//新增故障提醒信息时记录下该故障信息的版本
		if (dataValue.get("RDMINDDESC_OLD") == null) {
			if (dataValue.get("REMINDDESC") != null) {
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql);
				// dmo.commit(ApmsConst.DS_APMS);
			}
		} else {
			if (reminddesc == null) {
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql);
			} else {
				if (!dataValue.get("RDMINDDESC_OLD").toString().equals(reminddesc)) {
					dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql);
					// dmo.commit(ApmsConst.DS_APMS);
				}
			}
		}
		
		//logger.debug("故障信息更新后置拦截器执行完成！生成实例信息["+numAdd+"]条");
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
