package com.apms.bs.neuralnet;

import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;


import smartx.framework.common.vo.NovaLogger;

public class NeuralNetTrainService {
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	
	/**
	 * 执行神经网络bat文件任务
	 * @param batFile
	 * @throws Exception
	 */
	public void neuralNetBatExec(String batFile) throws Exception{
		
		// 统一规定，目录为 d:/neuralnet/apu/
		//String batFile = "D:/neuralnet/apu/train_apu.bat";
		try {
			long begin = System.currentTimeMillis();
			runBat(batFile);
            long end = System.currentTimeMillis();
			logger.debug("调用APU样本训练bat执行命令成功,耗时" + (end - begin) / 1000 + " s. batFile=" + batFile);
		} catch (IOException e) {
			logger.error("调用APU样本训练bat执行命令失败,batFile=" + batFile);
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 执行指定的bat文件， bat文件结尾需要有exit退出窗口
	 * @param batFile
	 * @throws Exception
	 */
	public void runBat(String batFile) throws Exception{
		Runtime rt = Runtime.getRuntime();
		try {
			Process ps = rt.exec("cmd.exe /c start " + batFile); // 加了/c参数后它将运行/c后面的命令,不加参数的话,它只执行CMD命令
			InputStream in = ps.getInputStream();
			int c;
            while ((c = in.read()) != -1) {
                System.out.print(c);// 如果你不需要看输出，这行可以注销掉
            }
            in.close();
            ps.waitFor();
			ps.destroy();
            
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("执行bat文件失败!",e);
		}
	}
	
	
	public static void main(String[] args) throws Exception{
		NeuralNetTrainService service = new NeuralNetTrainService();
		String batFile = "D:/neuralnet/apu/train_apu.bat";
		service.neuralNetBatExec(batFile);

	}

}
