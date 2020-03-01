package com.apms.bs.datatask;

import org.apache.log4j.Logger;
import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.NovaLogger;
import com.apms.ApmsConst;

/**
 * 数据任务日志工具类
 * 
 * @author jack
 * @date Wed 7, 2014
 */
public class DTLogUtil {

	Logger logger = NovaLogger.getLogger(this.getClass());
	private CommDMO dmo = new CommDMO();

	/**
	 * 删除数据任务日志
	 * 
	 * @param day
	 *            删除数据任务日志的天数
	 * @throws Exception
	 */
	public void clearDataTaskLog(int day) throws Exception {
		String sql = "DELETE FROM PUB_DATATASK A WHERE A.ENDTIME<=SYSDATE-"
				+ day;
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
		logger.debug("删除数据任务日志成功");
	}

}
