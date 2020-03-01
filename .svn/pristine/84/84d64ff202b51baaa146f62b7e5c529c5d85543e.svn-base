package com.sep.file;

import java.io.File;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.utils.FileUtil;
import smartx.framework.common.vo.DMOConst;
import smartx.framework.common.vo.NovaLogger;
import smartx.publics.file.FileConstant;
import smartx.publics.form.vo.InitMetaDataConst;

public class SepFileService {
	
protected Logger logger = NovaLogger.getLogger(this.getClass());
	
	private static SepFileService fileService = null;
	
	public static SepFileService getInstance(){
		if(fileService != null)
			return fileService;
		
		fileService = new SepFileService();
		
		return fileService;
	}
	
	
	/**
	 * 培训资料文件上传 
	 * 修改表 U_JOBCARD_MATERIAL 字段  MATERIALFILE
	 * @param id
	 * @param bytes
	 * @param filename
	 * @param uploadStatus
	 * @throws Exception
	 */                                   
	public void  trainingMaterialFileInsert(String id, byte[] bytes,String filename, int uploadStatus) throws Exception{
		logger.debug("文件【" + filename + "】存储到表U_JOBCARD_MATERIAL 中!");
		
		CommDMO dmo = new CommDMO();
		String updateSql = "UPDATE U_JOBCARD_MATERIAL SET MATERIALFILE=?,FILENAME=?,ISWRITEFILE=? WHERE ID=?";
		
		dmo.executeUpdateByDS(DMOConst.DS_DEFAULT, updateSql, bytes,filename,uploadStatus,id);
		
		dmo.commit(DMOConst.DS_DEFAULT);	
		dmo.releaseContext(DMOConst.DS_DEFAULT);
		
		logger.info("**************文件"+ filename + "存储成功！！u_jobcard_material.id= 【"+id+"】***************");
	}
	
	
	/**
	 * 培训资料文件下载
	 * 从表 U_JOBCARD_MATERIAL 提取字段 MATERIALFILE 生成文件
	 * @param fileContentId
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public String trainingMaterialFileDownload(String fileContentId,String fileName) throws Exception{
		String filePath = InitMetaDataConst.RootPath + FileConstant.DOWNLOAD_DIR + "/" + fileName ;
		//创建下载目录
		FileUtil.createDirIfNotExists(InitMetaDataConst.RootPath + FileConstant.DOWNLOAD_DIR);
		
		CommDMO dmo = new CommDMO();
		String querySql = "select MATERIALFILE from U_JOBCARD_MATERIAL where id=" + fileContentId;
		byte[] bytes = dmo.readBlobDataByDS(DMOConst.DS_DEFAULT, querySql);		
		File f = FileUtil.getFileFromBytes(bytes, filePath);	
		dmo.commit(DMOConst.DS_DEFAULT);
		dmo.releaseContext(DMOConst.DS_DEFAULT);
		logger.info("文件【"+ f.getName() +"】生成成功");
		
		return fileName;
	}
	
	
	
	/**
	 * 风险点关键点文件上传
	 * 插入记录至表  U_JOBCARD_KEYPOINT_MET
	 * @param cardId
	 * @param keyId
	 * @param bytes
	 * @param filename
	 * @param uploadUser
	 * @throws Exception
	 */
	public void riskAndKeyPointFileInsert(String cardId, String keyId, byte[] bytes,String filename, String uploadUser) throws Exception{
		logger.debug("文件【" + filename + "】存储到表U_JOBCARD_KEYPOINT_MET中!");
		int dot = filename.lastIndexOf('.'); 
		String fileEndName="";
        if ((dot >-1) && (dot < (filename.length() - 1))) { 
        	fileEndName =  filename.substring(dot + 1); 
        } 
		CommDMO dmo = new CommDMO();
		String insertSql = "INSERT INTO U_JOBCARD_KEYPOINT_MET VALUES (S_U_JOBCARD_KEYPOINT_MET.NEXTVAL,?,?,?,?,?,?,sysdate,?)";
		
		dmo.executeUpdateByDS(DMOConst.DS_DEFAULT, insertSql, cardId, keyId, filename,1,fileEndName,bytes,uploadUser);
		
		dmo.commit(DMOConst.DS_DEFAULT);	
		dmo.releaseContext(DMOConst.DS_DEFAULT);
		
		logger.info("**************文件"+ filename + "存储成功！！***************");
	}
	


	/**
	 * 风险点关键点文件下载
	 * 从表  U_JOBCARD_KEYPOINT_MET 提取字段  MATERIALFILE 生成文件
	 * @param fileContentId
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public String riskAndKeyPointFileDownload(String fileContentId,String fileName) throws Exception{
		String filePath = InitMetaDataConst.RootPath + FileConstant.DOWNLOAD_DIR + "/" + fileName ;
		//创建下载目录
		FileUtil.createDirIfNotExists(InitMetaDataConst.RootPath + FileConstant.DOWNLOAD_DIR);
		
		CommDMO dmo = new CommDMO();
		String querySql = "select MATERIALFILE from U_JOBCARD_KEYPOINT_MET where id=" + fileContentId;
		byte[] bytes = dmo.readBlobDataByDS(DMOConst.DS_DEFAULT, querySql);		
		File f = FileUtil.getFileFromBytes(bytes, filePath);	
		dmo.commit(DMOConst.DS_DEFAULT);
		dmo.releaseContext(DMOConst.DS_DEFAULT);
		logger.info("文件【"+ f.getName() +"】生成成功");
		
		return fileName;
	}
}
