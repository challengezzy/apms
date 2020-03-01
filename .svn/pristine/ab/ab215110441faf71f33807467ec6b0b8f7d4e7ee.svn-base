package com.apms.bs.datatask;

import org.dom4j.Element;

import smartx.framework.common.bs.CommDMO;
import smartx.publics.datatask.DataTaskExecThread;
import smartx.publics.datatask.DataTaskExecuteIFC;

import com.apms.ApmsConst;
import com.apms.bs.datacompute.A01CfmDataComputeService;
import com.apms.bs.datacompute.A01IaevDataComputeService;
import com.apms.bs.datacompute.A04CfmDataComputeService;
import com.apms.bs.datacompute.A04IaevDataComputeService;
import com.apms.bs.datacompute.A49IaevDataComputeService;
import com.apms.bs.datacompute.DataComputeService;
import com.apms.vo.ApmsVarConst;

/**
 * 报文数据计算，指定报文类型
 *@date Nov 28, 2012
 **/
public class DTReportComputeTaskByFilter implements DataTaskExecuteIFC{

	/**
	 * 报文内容解析执行
	 * @param task 定义该报文的参数XML
	 * @param mainThread 执行任务的主线程
	 * @throws Exception
	 */
	public void dataTaskExec(Element task,DataTaskExecThread mainThread) throws Exception {
		
		String rptno = task.elementText("REPORTNO");
		String acnum = task.elementText("ACNUM");
		String EngineType = task.elementText("ENGINETYPE");
		
		if("".equals(rptno))
			rptno = null;
		
		if("".equals(acnum))
			acnum = null;
		if("".equals(EngineType))
			EngineType = null;
		mainThread.logTaskRun("开始执行【"+rptno+"】报文计算任务....");
		
		DataComputeService service = DataComputeService.getInstance();
		A01CfmDataComputeService A01CfmService = A01CfmDataComputeService.getInstance();
		A01IaevDataComputeService  A01IaevService = A01IaevDataComputeService.getInstance();
		
		A04CfmDataComputeService A04CfmService = A04CfmDataComputeService.getInstance();
		A04IaevDataComputeService  A04IaevService = A04IaevDataComputeService.getInstance();
		
		int num =0;
		
		try{
			//执行报文数据计算
			if(ApmsVarConst.RPTNO_A13.equalsIgnoreCase(rptno) || ApmsVarConst.RPTNO_R13.equalsIgnoreCase(rptno)){
				num += service.computeA13Data( acnum );
				
			}else if(ApmsVarConst.RPTNO_A21.equalsIgnoreCase(rptno)){
				num += service.computeA21Data( acnum );
				
			}else if(ApmsVarConst.RPTNO_A23.equalsIgnoreCase(rptno)){
				num += service.computeA23Data( acnum );
				
			}else if(ApmsVarConst.RPTNO_A25.equalsIgnoreCase(rptno)){
				num += service.computeA25Data( acnum );
				
			}else if(ApmsVarConst.RPTNO_A27.equalsIgnoreCase(rptno)){
				num += service.computeA27Data( acnum );
				
			}else if(ApmsVarConst.RPTNO_A34.equalsIgnoreCase(rptno)){
				num += service.computeA34Data( acnum );
				
			}else if(ApmsVarConst.RPTNO_A24.equalsIgnoreCase(rptno)){
				num += service.computeA24Data( acnum );
				
			}else if(ApmsVarConst.RPTNO_A28.equalsIgnoreCase(rptno)){
				num += service.computeA28Data( acnum );
			}else if(ApmsVarConst.RPTNO_A38.equalsIgnoreCase(rptno)){
				num += service.computeA38Data( acnum );
			}else if(ApmsVarConst.RPTNO_A40.equalsIgnoreCase(rptno)){
				num += service.computeA40Data( acnum );
			}else if(ApmsVarConst.RPTNO_A01.equalsIgnoreCase(rptno)){
				if(ApmsVarConst.RPTNO_A01CFM.equalsIgnoreCase(EngineType)){
					num += A01CfmService.computeA01Data(acnum);
				}else if(ApmsVarConst.RPTNO_A01IAE.equalsIgnoreCase(EngineType)){
					num += A01IaevService.computeA01Data(acnum);
				}
			}else if(ApmsVarConst.RPTNO_A04.equalsIgnoreCase(rptno)){
				if(ApmsVarConst.RPTNO_A04CFM.equalsIgnoreCase(EngineType)){
					num += A04CfmService.computeA04Data(acnum);
				}else if(ApmsVarConst.RPTNO_A04IAE.equalsIgnoreCase(EngineType)){
					num += A04IaevService.computeA04Data(acnum);
				}
			}else if(ApmsVarConst.RPTNO_A49.equalsIgnoreCase(rptno)){
				//a49报文计算
				A49IaevDataComputeService.getInstance().computeA49Data(acnum);
			}
			
		}catch (Exception e) {
			mainThread.logTaskRun("【"+rptno+"】报文计算中发生异常。" + e.getMessage());
		}finally{
			//释放数据库连接
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
		
				
		mainThread.logTaskRun("【"+rptno+"】报文计算任务执行完成！，共计算【"+num+"】条！");
		
	}

}