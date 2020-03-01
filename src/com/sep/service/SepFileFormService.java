package com.sep.service;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.DMOConst;
import smartx.framework.common.vo.NovaLogger;
import smartx.publics.file.FileServletURI;

import com.sep.file.SepFileService;

/**
 * SEP项目中的文件上传下载服务类
 * @author jerry
 * @date Aug 14, 2015
 */
public class SepFileFormService {
	
	protected Logger logger = NovaLogger.getLogger(this.getClass());
	
	//培训材料文件上传入口
	public void trainingMaterialUploadFile(String id, byte[] bytes,String filename, int uploadStatus)
			throws Exception {
		try {
			SepFileService fileService = SepFileService.getInstance();
			fileService.trainingMaterialFileInsert(id, bytes, filename, uploadStatus);
			logger.info("附件【" + filename + "】上传成功！");
		} catch (Exception e) {
			logger.error("上传并存储附件文件失败!", e);
			throw e;
		}
	}
	

	//培训材料文件下载入口
	public String trainingMaterialFileDownload(String fileContentId,String fileName) throws Exception{
		try{
			SepFileService fileService = SepFileService.getInstance();
			fileService.trainingMaterialFileDownload(fileContentId, fileName);
			logger.info("附件【" + fileName + "】生成文件成功！");
			
			FileServletURI fsu = new FileServletURI();
			String downLoadUri = fsu.getDownLoadURI(fileName,fileName);
			
			return downLoadUri;
		}catch (Exception e) {
			logger.error("下载附件文件失败!",e);
			throw e;
		}finally{
			new CommDMO().releaseContext(DMOConst.DS_DEFAULT);
		}
	}
	
	
	//风险点关键点文件上传入口
	public void riskAndKeyPointUploadFile(String cardId, String keyId, byte[] bytes,String filename, String uploadUser)
			throws Exception {
		try {
			SepFileService fileService = SepFileService.getInstance();
			fileService.riskAndKeyPointFileInsert(cardId, keyId, bytes, filename, uploadUser);
			logger.info("附件【" + filename + "】上传成功！");
		} catch (Exception e) {
			logger.error("上传并存储附件文件失败!", e);
			throw e;
		}
	}
	
	
	//风险点关键点文件下载入口
	public String riskAndKeyPointFileDownload(String fileContentId,String fileName) throws Exception{
		try{
			SepFileService fileService = SepFileService.getInstance();
			fileService.riskAndKeyPointFileDownload(fileContentId, fileName);
			logger.info("附件【" + fileName + "】生成文件成功！");
			
			FileServletURI fsu = new FileServletURI();
			String downLoadUri = fsu.getDownLoadURI(fileName,fileName);
			
			return downLoadUri;
		}catch (Exception e) {
			logger.error("下载附件文件失败!",e);
			throw e;
		}finally{
			new CommDMO().releaseContext(DMOConst.DS_DEFAULT);
		}
	}
}
