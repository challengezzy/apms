package com.apms.sample;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.apms.ApmsConst;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

/**
 *@author zzy
 *@date Jul 23, 2012
 **/
public class DbAccessSample {
	
	private Logger logger = NovaLogger.getLogger(this.getClass());	
	private CommDMO dmo = new CommDMO();
	
	public void jdbcTest() throws Exception{
		logger.info("SQLSERVER查询测试1...");
		String querySql = "select * from logintable";//查询SQL,可能直接在PLSQL中执行
		
		//执行查询语句，指定数据源、查询SQL, 返回HashVO对象数组
		HashVO[] vos = dmo.getHashVoArrayByDS("datasource_apmsold", querySql); 
		for(int i=0;i<vos.length;i++){
			HashVO vo = vos[i];
			//对查询到的数据进行处理，如打印出来
			String userid = vo.getStringValue("user_id");//用位置索引 查询属性值
			String poste = vo.getStringValue("poste");//用属性名查询属性值
			
			logger.info("查询到的数据["+ i +"]userid=" + userid + ", poste="+poste);
		}
	}
	
	
	/**
	 * 从数据库中读取数据示例1
	 * @throws Exception
	 */
	public void readData() throws Exception{
		
		logger.info("数据查询测试1...");
		String querySql = "SELECT TABLE_NAME TABLENAME,COMMENTS FROM USER_TAB_COMMENTS WHERE ROWNUM <5";//查询SQL,可能直接在PLSQL中执行
		
		//执行查询语句，指定数据源、查询SQL, 返回HashVO对象数组
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_DEFAULT, querySql); 
		for(int i=0;i<vos.length;i++){
			HashVO vo = vos[i];
			//对查询到的数据进行处理，如打印出来
			String tableName = vo.getStringValue(0);//用位置索引 查询属性值
			String comments = vo.getStringValue("COMMENTS");//用属性名查询属性值
			
			logger.info("查询到的数据["+ i +"]taleName=" + tableName + ", comments="+comments);
		}
	}
	
	/**
	 * 从数据库中读取数据示例2
	 * @throws Exception
	 */
	public void readDataUseParam() throws Exception{
		
		logger.info("数据查询测试2...");
		
		//查询SQL,使用绑定变量，传入参数， "?"变绑定变量,可以有多个
		String querySql =" select id,name,appmodule,seq from pub_menu where isflex='Y' and  appmodule=? and name=?";
		
		String module = "SMARTX";
		String menuName = "菜单管理";
		
		//执行查询语句，指定数据源、查询SQL,绑定变量参数
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_DEFAULT, querySql,module,menuName); 
		for(int i=0;i<vos.length;i++){
			HashVO vo = vos[i];
			//对查询到的数据进行处理，如打印出来
			String name = vo.getStringValue(1);//用位置索引 查询属性值
			String appmodule = vo.getStringValue("APPMODULE");//用属性名查询属性值
			
			logger.info("查询到的数据["+ i +"] name=" + name + ", appmodule="+appmodule);
		}
		
	}
	
	public void queryLogData(String datasource,String querySql) throws Exception{

		//执行查询语句，指定数据源、查询SQL,绑定变量参数
		HashVO[] vos = dmo.getHashVoArrayByDS(datasource, querySql); 
		for(int i=0;i<vos.length;i++){
			HashVO vo = vos[i];
			//对查询到的数据进行处理，如打印出来
			String col1Value = vo.getStringValue(0);//用位置索引 查询属性值
			logger.info("查询到的数据["+ i +"] 第一个参数：" + col1Value);
		}
		
	}
	
	/**
	 * 新增数据
	 * @throws Exception
	 */
	public void insertData() throws Exception{
		logger.info("数据插入测试...");
		
		String insertSql = "insert into pub_log(id,time,source,operation) values(s_pub_log.nextval,sysdate,?,?)";
		String source = "testuser";
		String operation = "数据库测试";
		//执行SQL,参数：数据源、操作SQL,和N(0或多个)变量参数
		dmo.executeUpdateByDS(ApmsConst.DS_DEFAULT,insertSql, source,operation);
		
		dmo.executeUpdateByDS(ApmsConst.DS_DEFAULT,insertSql, source, "第二条测试记录");
		//数据更新后，执行提交
		dmo.commit(ApmsConst.DS_DEFAULT);
		
		logger.info("数据插入成功");
		
	}
	
	/**
	 * 更新数据
	 * @throws Exception
	 */
	public void updateData() throws Exception{
		logger.info("数据更新测试...");
		
		String updateSql1 = "update pub_log set operation='更新测试1' where source = 'testuser'";
		String updateSql2 = "update pub_log set operation='更新测试2' where source = ?";
		String source = "testuser";
		//执行SQL,参数：数据源、操作SQL,和N(0或多个)变量参数
		dmo.executeUpdateByDS(ApmsConst.DS_DEFAULT,updateSql1);
		
		dmo.executeUpdateByDS(ApmsConst.DS_DEFAULT,updateSql2, source);
		//数据更新后，执行提交
		dmo.commit(ApmsConst.DS_DEFAULT);
		
		logger.info("数据更新成功");
	}
	
	/**
	 * 删除数据
	 * @throws Exception
	 */
	public void deleteData() throws Exception{
		logger.info("数据删除测试...");
		
		String delSql1 = "delete pub_log where source = 'testuser'";
		String delSql2 = "delete pub_log where source = ?";
		String source = "testuser";
		
		//以下两个操作结果相同，均是删除source为testuser的记录，一个不使用变量，一个使用绑定变量
		dmo.executeUpdateByDS(ApmsConst.DS_DEFAULT,delSql1);		
		dmo.executeUpdateByDS(ApmsConst.DS_DEFAULT,delSql2, source);
		//数据更新后，执行提交
		dmo.commit(ApmsConst.DS_DEFAULT);
		
		logger.info("数据删除成功");
	}
	
}
