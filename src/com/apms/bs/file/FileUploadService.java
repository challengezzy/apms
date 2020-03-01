package com.apms.bs.file;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.utils.FileUtil;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;
import smartx.framework.common.vo.SimpleHashVO;
import smartx.publics.file.FileConstant;

import com.apms.ApmsConst;
import com.apms.bs.util.DateUtil;

public class FileUploadService {
	
protected Logger logger = NovaLogger.getLogger(this.getClass());
	
	private static FileUploadService fileUploadService = null;
	
	public static FileUploadService getInstance(){
		if(fileUploadService != null)
			return fileUploadService;
		
		fileUploadService = new FileUploadService();
		
		return fileUploadService;
	}
	
	
	/**
	 * 附件上传通用
	 * @param bytes
	 * @param fileName
	 * @param downloadurl
	 * @param tableName
	 * @param originId
	 * @param uploadUser
	 * @param recordTimeVal
	 * @param maintenanceReportVal
	 * @param commentVal
	 * @throws Exception
	 */
	public void fileUpload(byte[] bytes,String fileName,String downloadurl,String tableName,String originId,String uploadUser,String recordTimeVal,String maintenanceReportVal,String commentVal) throws Exception{
		
		String templeStr="";
		int postfix=fileName.lastIndexOf(".");
		templeStr=fileName.substring(0,postfix);
		String filenamestr=templeStr+DateUtil.format(new Date(), "yyyyMMddHHmmss")+fileName.substring(postfix);
		String downLoadUrl =downloadurl+"/"+filenamestr;
		
		String dirPath = FileConstant.RootPath+downloadurl;

		FileUtil.createDirIfNotExists(dirPath);
		Date recordTime = DateUtil.StringToDate(recordTimeVal, "yyyy-MM-dd hh:mm:ss");
		String filePath = FileUtil.createFile(dirPath, filenamestr);
		File f = new File(filePath);
		//如果文件存在，则先删除
		if(f.exists())
			f.delete();
		
		FileUtils.writeByteArrayToFile(new File(filePath), bytes);
		
		logger.debug("文件【" + fileName + "】已上传成功，开始记录到表中!");
		
		
		
		
		CommDMO dmo = new CommDMO();
		String insertSql = "insert into b_fileinfo(id,origin_id,origin_table,filename,downloadurl,update_man,filetime,filetype," +
				"comments,update_date)"
				+ " values(s_b_fileinfo.nextval,?,?,?,?,?,?,?,?,sysdate)";
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, originId,tableName,fileName,downLoadUrl,uploadUser,recordTime,maintenanceReportVal,commentVal);
		
		dmo.commit(ApmsConst.DS_APMS);
		
		logger.info("**************文件"+ fileName + "记录插入成功！");
	}
	/**
	 * 附件删除通用
	 * @param id
	 * @param downloadurl
	 * @param filename
	 * @throws Exception
	 */
	public void fileDelete(String id,String downloadurl,String filename) throws Exception{
		FileUtil.createDirIfNotExists(downloadurl);
		String filePath = FileUtil.createFile(downloadurl, filename);
		File f = new File(filePath);
		//如果文件存在，则先删除
		if(f.exists())
			f.delete();
	
		logger.debug("文件【" + filename + "】已删除成功!");
	
		CommDMO dmo = new CommDMO();
		String insertSql = "delete from  b_fileinfo "
			         	+ " where id=? ";
	
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, id);
	
		dmo.commit(ApmsConst.DS_APMS);
	
		logger.info("**************文件"+ filename + "记录删除成功！");
	}
}
