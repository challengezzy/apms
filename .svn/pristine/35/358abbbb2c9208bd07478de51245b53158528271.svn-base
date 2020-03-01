package com.apms.bs.system;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.BootServlet;
import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.bs.NovaSessionFactory;
import smartx.framework.common.ui.NovaClientEnvironment;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;
import smartx.framework.common.vo.NovaRemoteException;
import smartx.system.common.constant.CommonSysConst;
import smartx.system.common.constant.LogConst;
import smartx.system.log.bs.LogWriter;
import smartx.system.login.bs.SystemLoginServiceImpl;
import smartx.system.login.ui.Md5Digest;
import smartx.system.login.ui.SystemLoginServiceIFC;
import smartx.system.login.vo.LoginInfoVO;
import flex.messaging.FlexContext;

public class LoginService {
	
	 protected static String SYSTEM_DEFAULT_USER_ID = "-1";
	 private Logger logger=NovaLogger.getLogger(SystemLoginServiceImpl.class);
	
	
	/**
     * 登录并返回登录信息
     * 
     */
    public LoginInfoVO login(String _usercode, String _pwd) throws Exception {
        return login(_usercode, _pwd, null, false);
    }

    /**
     * 登录并返回登录信息
     */
    public LoginInfoVO login(String _usercode, String _pwd, String _adminpwd) throws Exception {
        return login(_usercode, _pwd, _adminpwd, true);
    }
    
    /**
     * 登录实现
     * @param _usercode
     * @param _pwd
     * @param _adminpwd
     * @param isAdmin
     * @return
     * @throws Exception
     */
    protected LoginInfoVO login(String _usercode, String _pwd, String _adminpwd, boolean isAdmin) throws Exception {
    	CommDMO dmo = new CommDMO();
        LoginInfoVO loginInfo = new LoginInfoVO();
        loginInfo.setAdmin(isAdmin);
        
        //Flex客户端的怎样信息
        HttpServletRequest request = FlexContext.getHttpRequest();
        String clientIp = request.getRemoteAddr();
        logger.debug("客户端IP："+clientIp);
        try {
        	if (isSystemDefaultUser(isAdmin, _usercode, _pwd, _adminpwd, loginInfo)) {
                new LogWriter().writeLog(LogConst.OPERATION_SUPERLOGIN,null);
                return loginInfo;
            }
            String str_newpasswd = modifyPasswd(_usercode, _pwd); //加密密码
            String str_newadminpasswd = modifyPasswd(_usercode, _adminpwd); //加密密码
            String _sql ="SELECT * FROM PUB_USER WHERE LOGINNAME = '" + _usercode + "' AND PWD='" + str_newpasswd.trim() + "'";
            if (isAdmin) {
            	
            	_sql = _sql + " AND ADMINPWD='" + str_newadminpasswd.trim() + "'";
            }
             HashVO[] queryResult = execQuery(_sql);
	        if (queryResult.length <= 0) {
	            if (isAdmin) {
	                loginInfo.setLoginStatus(SystemLoginServiceIFC.ADMINUSER_ERROR_TYPE);
	            } else {
	                loginInfo.setLoginStatus(SystemLoginServiceIFC.USER_ERROR_TYPE);
	            }
	            new LogWriter().writeLog(LogConst.OPERATION_LOGIN_FAILED,null);
	        } else {
	        	
	        	String ipAddress = queryResult[0].getStringValue("EXTENDATTRIBUTE20");
	            if(ipAddress != null && !ipAddress.trim().equals("")){
	            	if(!this.isAuthorizedIP(clientIp,ipAddress)){
	            		 loginInfo.setLoginStatus(SystemLoginServiceIFC.USER_IP_UNAUTHORIZED);
	            		 return loginInfo;
	            	}
	            }
	            if (isAdmin) {
	                loginInfo.setLoginStatus(SystemLoginServiceIFC.ADMINUSER_LOGINOK_TYPE);
	                new LogWriter().writeLog(LogConst.OPERATION_ADMINLOGIN,null);
	            } else {
	                loginInfo.setLoginStatus(SystemLoginServiceIFC.USER_LOGINOK_TYPE);
	                new LogWriter().writeLog(LogConst.OPERATION_LOGIN,null);
	            }
	            setLoginInfo(loginInfo, queryResult);
	            dmo.commit(CommonSysConst.DATASOURCE_USERMGMT);
	        }
        } catch (NovaRemoteException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
     		dmo.releaseContext(CommonSysConst.DATASOURCE_USERMGMT);
        }
        return loginInfo;
    }
    
    private void setLoginInfo(LoginInfoVO loginInfo, HashVO[] queryResult) {
        loginInfo.setId(queryResult[0].getStringValue("ID")); //
        loginInfo.setCode(queryResult[0].getStringValue("LOGINNAME"));
        loginInfo.setName(queryResult[0].getStringValue("NAME")); //
        loginInfo.setLoginName(queryResult[0].getStringValue("LOGINNAME")); //
        loginInfo.setRegionCode(queryResult[0].getStringValue("REGIONCODE")); //
        loginInfo.setLoginTime(new Date(System.currentTimeMillis()));
        loginInfo.setDbReadWriteAuth(queryResult[0].getStringValue("EXTENDATTRIBUTE1"));
        loginInfo.setDispatchtype(queryResult[0].getStringValue("DISPATCHTYPE"));
        System.out.println("----------------"+queryResult[0].getStringValue("EXTENDATTRIBUTE5")+"--------------------");
        //add by zhangzz 20121016 start 添加黑名单用户项
        loginInfo.setIsBlackUser(queryResult[0].getBooleanValue("EXTENDATTRIBUTE4"));
        //add by zhangzz 20121016 end
        if (queryResult[0].getLognValue("LOGINCOUNT") != null) {
            loginInfo.setUserLoginCount(queryResult[0].getLognValue("LOGINCOUNT").longValue());
        }
        if (queryResult[0].getLognValue("LOGINTIMECOUNT") != null) {
            loginInfo.setTotalLoginCount(queryResult[0].getLognValue("LOGINTIMECOUNT").longValue());
        }
        if (queryResult[0].getIntegerValue("ACCOUNTSTATUS") != null &&
            queryResult[0].getIntegerValue("ACCOUNTSTATUS").intValue() ==
            CommonSysConst.PUB_USER_ACCOUNTSTATUS_DISABLED) {
            loginInfo.setLoginStatus(SystemLoginServiceIFC.USER_ACCOUTSTATUS_DISABLED);
            return;
        }
        Date nowDate = new Date(System.currentTimeMillis());
        if (queryResult[0].getDateValue("EXPDATE") != null && nowDate.after(queryResult[0].getDateValue("EXPDATE"))) {
            loginInfo.setLoginStatus(SystemLoginServiceIFC.USER_ACCOUT_EXPDATE);
            return;
        }
        if (queryResult[0].getDateValue("PWDEXPDATE") != null && nowDate.after(queryResult[0].getDateValue("PWDEXPDATE"))) {
            loginInfo.setLoginStatus(SystemLoginServiceIFC.USER_PWD_EXPDATE);
            return;
        }
        String _sqlWorkPosition = "SELECT * FROM PUB_USER_WORKPOSITION WHERE USERID = " +
            queryResult[0].getStringValue("ID");
        try {
            HashVO[] voWorkPosition = execQuery(_sqlWorkPosition);
            if (voWorkPosition.length > 0) {
                Vector workPositionID = new Vector();
                for (int i = 0; i < voWorkPosition.length; i++) {
                    workPositionID.add(voWorkPosition[i].getStringValue("WORKPOSITIONID"));
                }
                loginInfo.setWorkPositionID(workPositionID);
            }
        } catch (Exception ex) {
        }

        updateUserInfo(queryResult);
    }
    
    private void updateUserInfo(HashVO[] queryResult) {
        long id = queryResult[0].getLognValue("ID").longValue();
        long loginCount = 0;
        if (queryResult[0].getLognValue("LOGINCOUNT") != null) {
            loginCount = queryResult[0].getLognValue("LOGINCOUNT").longValue();
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String _sql = "UPDATE PUB_USER SET ISONLINE=" + CommonSysConst.GLOBAL_BOOLEAN_TRUE +
                ",LOGINCOUNT=" + (loginCount + 1) +
                ",LOGINTIME=TO_DATE('" + format.format(new Date(System.currentTimeMillis())) +
                "','YYYY-MM-DD HH24:MI:SS')" +
                " WHERE ID=" + id;
            new CommDMO(false).executeUpdateByDS(CommonSysConst.DATASOURCE_USERMGMT,_sql);
        } catch (Exception e) {
            logger.error("发生异常：",e);
        }
    }
    
    /**
     * 检索用户相关信息
     * @param sql
     * @return
     * @throws Exception
     */
    protected HashVO[] execQuery(String sql) throws Exception {
        return (new CommDMO()).getHashVoArrayByDS(CommonSysConst.DATASOURCE_USERMGMT, sql);
    }

    /**
     * 更新用户相关信息
     * @param sql
     * @throws Exception
     */
    protected void execUpdate(String sql) throws Exception {
        (new CommDMO()).executeUpdateByDS(CommonSysConst.DATASOURCE_USERMGMT, sql);
    }
	
    
    /**
     * 重置密码（MD5）
     * 
     */
    public boolean resetPwd(String _userLoginName, String _pwd) throws Exception {
        Md5Digest digest = new Md5Digest();
        String newPwd = digest.generatePasswd(_userLoginName, _pwd);
        String _sql = "UPDATE PUB_USER SET PWD='" + newPwd +
            "' WHERE LOGINNAME='" + _userLoginName + "'";
        (new CommDMO()).executeUpdateByDS(CommonSysConst.DATASOURCE_USERMGMT, _sql);
        return true;
    }
    
    /**
     * 用户退出
     * @param _userID String
     */
    public void logout(String _userID) {
        try {
        	//Flex客户端的怎样信息
            HttpServletRequest request = FlexContext.getHttpRequest();
            String clientIp = request.getRemoteAddr();
            
            NovaClientEnvironment clientEnv=NovaSessionFactory.getInstance().getClientEnv(Thread.currentThread());
            
            if(clientEnv == null)
            	clientEnv = NovaClientEnvironment.getInstance();
            
            if(clientEnv.get("SYS_LOGINUSER_LOGINNAME")==null){
            	clientEnv.put("SYS_LOGINUSER_LOGINNAME",_userID);
            }
            clientEnv.put("CLIENTIP", clientIp);
            
            String _sql = "SELECT ID,LOGINTIME,LOGINTIMECOUNT FROM PUB_USER WHERE ID = " + _userID;
            HashVO[] queryResult = execQuery(_sql);
            if (queryResult.length > 0) {
                Date loginTime = queryResult[0].getDateValue("LOGINTIME");
                Date nowTime = new Date(System.currentTimeMillis());
                long timeStanding = nowTime.getTime() - loginTime.getTime();
                long onlineTime = timeStanding / 1000;
                if (queryResult[0].getLognValue("LOGINTIMECOUNT") != null) {
                    onlineTime = onlineTime + queryResult[0].getLognValue("LOGINTIMECOUNT").longValue();
                }
                _sql = "UPDATE PUB_USER SET ISONLINE=" + CommonSysConst.GLOBAL_BOOLEAN_FALSE +
                    ",LOGINTIMECOUNT=" + onlineTime + " WHERE ID=" + _userID;
                new CommDMO(false).executeUpdateByDS(CommonSysConst.DATASOURCE_USERMGMT, _sql);
            }
            new LogWriter().writeLog(LogConst.OPERATION_EXIT,null);
        } catch (Exception e) {
        	logger.error("发生异常：",e);
        }
    }
    
    /**
     * 加密用户密码,即用加密算法进行处理...
     */
    public String modifyPasswd(String _userid, String _pwd) {
        Md5Digest digest = new Md5Digest();
        String newpassword = digest.generatePasswd(_userid, _pwd);
        return newpassword;
//        return _pwd; //不加密
    }
    
	private boolean isSystemDefaultUser(boolean isAdmin, String _usercode, String _pwd, String _adminpwd, LoginInfoVO loginInfo) {
		boolean result = false;
		if (isAdmin) {
			if (_usercode.equals(BootServlet.sysDefaultUserName) && _pwd.equals(BootServlet.sysDefaultUserPwd) && _adminpwd.equals(BootServlet.sysDefaultUserAdminPwd)) {
				loginInfo.setLoginStatus(SystemLoginServiceIFC.ADMINUSER_LOGINOK_TYPE);
				result = true;
			}
		} else {
			if (_usercode.equals(BootServlet.sysDefaultUserName) && _pwd.equals(BootServlet.sysDefaultUserPwd)) {
				loginInfo.setLoginStatus(SystemLoginServiceIFC.USER_LOGINOK_TYPE);
				result = true;
			}
		}
		if (result) {
			loginInfo.setId(SYSTEM_DEFAULT_USER_ID); //
			loginInfo.setName(_usercode); //
			loginInfo.setLoginName(_usercode); //
			loginInfo.setLoginTime(new Date());
		}
		return result;
	}
	
	private boolean isAuthorizedIP(String clientIP,String ipaddress){
    	boolean flag = false;
    	try{
    		String[] ipArray = ipaddress.trim().split(",");
    		clientIP = clientIP.replaceAll("\\.", ";");
    		if(ipArray != null ){
    			for(String ip : ipArray){
    				ip = ip.replaceAll("\\.", ";");
    				ip = ip.replaceAll("\\*", "[0-9]{1,3}");
    				if(clientIP.matches(ip)){
    					flag = true;
    					break;
    				}
    			}
    		}
    		
    	}catch(Exception e){
    		logger.debug("",e);
    		flag = false;
    	}
    	return flag;
    }
	
	/**
	 * 用户登录，返回用户登录的对象信息
	 * @param _usercode
	 * @param _pwd
	 * @return
	 * @throws Exception
	 */
	public LoginUserVo doLogin(String loginname, String pwd) throws Exception{
		CommDMO dmo = new CommDMO();
		LoginUserVo loginvo = new LoginUserVo();
		loginvo.setLoginname(loginname);

		try {
			String str_newpasswd = modifyPasswd(loginname, pwd); // 加密密码
			String loginSql = "SELECT 1 FROM PUB_USER WHERE LOGINNAME = '" + loginname + "' AND PWD='" + str_newpasswd.trim() + "'";
			HashVO[] validVos = dmo.getHashVoArrayByDS(CommonSysConst.DATASOURCE_USERMGMT, loginSql);
			if(validVos.length <= 0) {
				loginvo.setLoginStatus(2);//登录失败
				logger.info("用户["+loginname+"]登录失败!");
			} else {
				loginvo.setLoginStatus(1);//登录成功
				//查询用户的登录属性信息
				String sql = "select id,loginname,name,leadertype,isfieldstaff,mobile,email";
				sql += ",orgid_dep,orgid_base,orgid_line,orgid_section,orgid_group,airportid";
				sql += ",(select a.name from b_airport a where a.id=u.airportid ) airportname";
				sql += ",(select a.code_3 from b_airport a where a.id=u.airportid ) airportcode";
				sql += ",(select name from b_organization o where o.id=u.orgid_dep) orgname_dep";
				sql += ",(select name from b_organization o where o.id=u.orgid_base) orgname_base";
				sql += ",(select name from b_organization o where o.id=u.orgid_line) orgname_line";
				sql += ",(select name from b_organization o where o.id=u.orgid_section) orgname_section";
				sql += ",(select name from b_organization o where o.id=u.orgid_group) orgname_group";
				sql += "";
				sql += " from b_user u where u.loginname = '"+ loginname +"'";
				
				HashVO[] vos = dmo.getHashVoArrayByDS(CommonSysConst.DATASOURCE_USERMGMT, sql);
				HashVO vo = vos[0];
				
				loginvo.setId(vo.getStringValue("id"));
				loginvo.setLoginname(vo.getStringValue("loginname"));
				loginvo.setName(vo.getStringValue("name"));
				loginvo.setLeadertype(vo.getIntegerValue("leadertype"));
				loginvo.setIsfieldstaff(vo.getBooleanValue("isfieldstaff"));
				loginvo.setMobile(vo.getStringValue("mobile"));
				loginvo.setEmail(vo.getStringValue("email"));
				loginvo.setAirportId(vo.getStringValue("airportid"));
				loginvo.setAirportName(vo.getStringValue("airportname"));
				loginvo.setAirportCode(vo.getStringValue("airportcode"));
				loginvo.setOrgid_dep(vo.getStringValue("orgid_dep"));
				loginvo.setOrgid_base(vo.getStringValue("orgid_base"));
				loginvo.setOrgid_line(vo.getStringValue("orgid_line"));
				loginvo.setOrgid_section(vo.getStringValue("orgid_section"));
				loginvo.setOrgid_group(vo.getStringValue("orgid_group"));
				loginvo.setOrgname_dep(vo.getStringValue("orgid_dep"));
				loginvo.setOrgname_base(vo.getStringValue("orgid_base"));
				loginvo.setOrgname_line(vo.getStringValue("orgid_line"));
				loginvo.setOrgname_section(vo.getStringValue("orgid_section"));
				loginvo.setOrgname_group(vo.getStringValue("orgid_group"));
				
				dmo.commit(CommonSysConst.DATASOURCE_USERMGMT);
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			dmo.releaseContext(CommonSysConst.DATASOURCE_USERMGMT);
		}
		return loginvo;
	}

}
