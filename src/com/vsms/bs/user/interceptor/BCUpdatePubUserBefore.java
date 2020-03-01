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
import com.apms.bs.util.DateUtil;

/**
 * 服务端拦截器实现类
 */
public class BCUpdatePubUserBefore implements FormInterceptor {

	protected Logger logger = NovaLogger.getLogger(this.getClass());

	/**
	 * 单条数据新增、更新时，使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, Map<String, Object> dataMap) throws Exception {
		CommDMO dmo = new CommDMO();
		String no;
		String accountstatus;
		String telephone;
		String mobile;
		String email;
		String orgid_group;
		String name;
		String loginname;
		
		

		if(dataMap.get("NO")==null){
			no=null;
		}else{
			no = dataMap.get("NO").toString();
		}
		if(dataMap.get("ACCOUNTSTATUS")==null){
			accountstatus=null;
		}else{
			//下拉框
			if(dataMap.get( "ACCOUNTSTATUS" ) instanceof ComBoxItemVO){
				accountstatus = ((ComBoxItemVO)dataMap.get( "ACCOUNTSTATUS" )).getId();
				//dataMap.put( "MTTYPE", dbom.getMtTypeByChartType( type ) );
			}else{
				accountstatus=null;
			}
			
		}
		if(dataMap.get("TELEPHONE")==null){
			telephone=null;
		}else{
			telephone = dataMap.get("TELEPHONE").toString();
		}
		if(dataMap.get("MOBILE")==null){
			mobile=null;
		}else{
			mobile = dataMap.get("MOBILE").toString();
		}
		if(dataMap.get("EMAIL")==null){
			email=null;
		}else{
			email = dataMap.get("EMAIL").toString();
		}
		
		if(dataMap.get("ORGID_GROUP")==null){
			orgid_group=null;
		}else{
			//参照框
			if(dataMap.get( "ORGID_GROUP" ) instanceof RefItemVO){
				orgid_group = ((RefItemVO)dataMap.get( "ORGID_GROUP" )).getId();
			}else{
				orgid_group=null;
			}
		}
		
		name=dataMap.get("NAME").toString();
		loginname = dataMap.get("LOGINNAME").toString();
		String loginname_bak = dataMap.get("LOGINNAME_BAK").toString();
		//更新用户信息
		String updateSql ="update pub_user set loginname=?,name=?,no=?,accountstatus=?,telephone=?,mobile=?,email=?,department=? where loginname=? ";
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateSql,loginname,name,no,accountstatus,telephone,mobile,email,orgid_group,loginname_bak);
		dmo.commit(ApmsConst.DS_APMS);
		logger.debug("插入到pub_user成功！");
	}
	
	/**
	 * 列表数据删除，或列表数据新增、删除时使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, List<Map<String, Object>> dataMapList) throws Exception {
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
	public void doSomething(List<Map<String, Object>> dataMapList) throws Exception {
		CommDMO dmo = new CommDMO();
		
		String updateUser = null; 
		
		for(Map<String, Object> dataMap : dataMapList){
			
			
			String formservice_templetcode = dataMap.get("FORMSERVICE_TEMPLETCODE").toString();
			if("T_B_USER".equalsIgnoreCase(formservice_templetcode)){
				
				String no;
				String accountstatus;
				String telephone;
				String mobile;
				String email;
				String orgid_group;
				String name;
				String loginname;
				String pwd;
				String createdateStr;
				Date createdate;
				String creator;
				
				updateUser = dataMap.get("UPDATEUSER")==null? null : dataMap.get("UPDATEUSER").toString();
				
				if(dataMap.get("NO")==null){
					no=null;
				}else{
					no = dataMap.get("NO").toString();
				}
				if(dataMap.get("ACCOUNTSTATUS")==null){
					accountstatus=null;
				}else{
					//下拉框
					if(dataMap.get( "ACCOUNTSTATUS" ) instanceof ComBoxItemVO){
						accountstatus = ((ComBoxItemVO)dataMap.get( "ACCOUNTSTATUS" )).getId();
						//dataMap.put( "MTTYPE", dbom.getMtTypeByChartType( type ) );
					}else{
						accountstatus=null;
					}
				}
				if(dataMap.get("TELEPHONE")==null){
					telephone=null;
				}else{
					telephone = dataMap.get("TELEPHONE").toString();
				}
				if(dataMap.get("MOBILE")==null){
					mobile=null;
				}else{
					mobile = dataMap.get("MOBILE").toString();
				}
				if(dataMap.get("EMAIL")==null){
					email=null;
				}else{
					email = dataMap.get("EMAIL").toString();
				}
				
				if(dataMap.get("ORGID_GROUP")==null){
					orgid_group=null;
				}else{
					//参照框
					if(dataMap.get( "ORGID_GROUP" ) instanceof RefItemVO){
						orgid_group = ((RefItemVO)dataMap.get( "ORGID_GROUP" )).getId();
					}else{
						orgid_group=null;
					}
				}
				
				name=dataMap.get("NAME").toString();
				loginname = dataMap.get("LOGINNAME").toString();
				createdateStr = dataMap.get("UPDATETIME").toString();
				createdate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(createdateStr);
				creator = dataMap.get("UPDATEUSER").toString();
				String flag = (String)dataMap.get("FORMSERVICE_MODIFYFLAG");
				if("insert".equals(flag)){
					pwd=dataMap.get("PWD").toString();
					String insertSql="insert into pub_user(id,loginname,pwd,name,no,accountstatus,telephone,mobile,email,creator,createdate,department)"
						+" values(s_pub_user.nextval,?,?,?,?,?,?,?,?,?,?,?)";
					dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql,loginname,pwd,name,no,accountstatus,telephone,mobile,email,creator,createdate,orgid_group);
				}else if("update".equals(flag)){
					String loginname_bak = dataMap.get("LOGINNAME_BAK").toString();
					String updateSql="update pub_user set loginname=?,name=?,no=?,accountstatus=?,telephone=?,mobile=?,email=?,department=? where loginname=? ";
					dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateSql, loginname, name,no,accountstatus,telephone,mobile,email,orgid_group,loginname_bak);
				}
			}else if("T_B_USER_SKILL".equalsIgnoreCase(formservice_templetcode)){
				//更新子表数据的更新人，更新时间
				dataMap.put("UPDATETIME", DateUtil.getLongDate(new Date()) );
				dataMap.put("UPDATEUSER", updateUser);
			}else if("T_B_USER_CAPACITY1".equalsIgnoreCase(formservice_templetcode)){
				dataMap.put("UPDATETIME", DateUtil.getLongDate(new Date()) );
				dataMap.put("UPDATEUSER", updateUser);
			}
			
		}
		

	}

}
