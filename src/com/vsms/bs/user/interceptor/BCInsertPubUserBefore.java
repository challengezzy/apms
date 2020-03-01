package com.vsms.bs.user.interceptor;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.NovaLogger;
import smartx.framework.metadata.vo.ComBoxItemVO;
import smartx.framework.metadata.vo.Pub_Templet_1VO;
import smartx.framework.metadata.vo.RefItemVO;
import smartx.publics.form.bs.service.FormInterceptor;
import com.apms.ApmsConst;

/**
 * 服务端拦截器实现类
 */
public class BCInsertPubUserBefore implements FormInterceptor {

	protected Logger logger = NovaLogger.getLogger(this.getClass());

	/**
	 * 单条数据新增、更新时，使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, Map<String, Object> dataValue) throws Exception {
		CommDMO dmo = new CommDMO();
		String no;
		String accountstatus;
		String telephone;
		String mobile;
		String email;
		String orgid_group;
		
		if(dataValue.get("NO")==null){
			no=null;
		}else{
			no = dataValue.get("NO").toString();
		}
		if(dataValue.get("ACCOUNTSTATUS")==null){
			accountstatus=null;
		}else{
			//下拉框
			if(dataValue.get( "ACCOUNTSTATUS" ) instanceof ComBoxItemVO){
				accountstatus = ((ComBoxItemVO)dataValue.get( "ACCOUNTSTATUS" )).getId();
				//dataValue.put( "MTTYPE", dbom.getMtTypeByChartType( type ) );
			}else{
				accountstatus=null;
			}
		}
		if(dataValue.get("TELEPHONE")==null){
			telephone=null;
		}else{
			telephone = dataValue.get("TELEPHONE").toString();
		}
		if(dataValue.get("MOBILE")==null){
			mobile=null;
		}else{
			mobile = dataValue.get("MOBILE").toString();
		}
		if(dataValue.get("EMAIL")==null){
			email=null;
		}else{
			email = dataValue.get("EMAIL").toString();
		}
		
		if(dataValue.get("ORGID_GROUP")==null){
			orgid_group=null;
		}else{
			//参照框
			if(dataValue.get( "ORGID_GROUP" ) instanceof RefItemVO){
				orgid_group = ((RefItemVO)dataValue.get( "ORGID_GROUP" )).getId();
			}else{
				orgid_group=null;
			}
		}
		
		String name=dataValue.get("NAME").toString();
		String loginname=dataValue.get("LOGINNAME").toString();
		String pwd=dataValue.get("PWD").toString();
		String createdateStr = dataValue.get("UPDATETIME").toString();
		Date createdate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(createdateStr);
		String creator = dataValue.get("UPDATEUSER").toString();
		//插入用户信息
		String insertSql ="insert into pub_user(id,loginname,pwd,name,no,accountstatus,telephone,mobile,email,creator,createdate,department)"
			+" values(s_pub_user.nextval,?,?,?,?,?,?,?,?,?,?,?)";
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql,loginname,pwd,name,no,accountstatus,telephone,mobile,email,creator,createdate,orgid_group);
		dmo.commit(ApmsConst.DS_APMS);
		logger.debug("插入到pub_user成功！");
	}
	
	/**
	 * 列表数据删除，或列表数据新增、删除时使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, List<Map<String, Object>> dataValueList) throws Exception {
		System.out.println("xxxxxxxxxxxxxxxxxxxxxx2");
	}

	/**
	 * 不常用 ，待查证
	 */
	public void doSomething(Map<String, Object> map) throws Exception {
		System.out.println("xxxxxxxxxxxxxxxxxxxxxx3");
	}

	/**
	 * 风格模板09的主表数据更新
	 */
	public void doSomething(List<Map<String, Object>> dataValueList) throws Exception {
		System.out.println("xxxxxxxxxxxxxxxxxxxxxx4");

	}

}
