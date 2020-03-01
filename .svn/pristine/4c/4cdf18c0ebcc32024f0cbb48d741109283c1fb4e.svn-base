package com.apms.bs.file;


import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.apms.ApmsConst;
import com.apms.bs.util.DateUtil;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.utils.FileUtil;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;
import smartx.framework.common.vo.SimpleHashVO;
import smartx.publics.file.FileConstant;
/**
 *@author zzy
 *@date Dec 12, 2012
 **/
public class FdimuFileService {
	
	protected Logger logger = NovaLogger.getLogger(this.getClass());
	
	private static FdimuFileService fileService = null;
	
	public static FdimuFileService getInstance(){
		if(fileService != null)
			return fileService;
		
		fileService = new FdimuFileService();
		
		return fileService;
	}
	
	/**
	 * 附件文件存储到数据库中
	 * @param bytes
	 * @param fileName
	 * @param tableName
	 * @param originId
	 * @param uploadUser
	 * @throws Exception
	 */
	public void swFileUpload(byte[] bytes,String fileName,String tableName,String originId,String uploadUser) throws Exception{
		
		String dirPath = FileConstant.RootPath+ ApmsConst.FDIMUFILE_DIR;
		FileUtil.createDirIfNotExists(dirPath);
		
		String filePath = FileUtil.createFile(dirPath, fileName);
		File f = new File(filePath);
		//如果文件存在，则先删除
		if(f.exists())
			f.delete();
		
		FileUtils.writeByteArrayToFile(new File(filePath), bytes);
		
		logger.debug("文件【" + fileName + "】已上传成功，开始记录到表中!");
		
		CommDMO dmo = new CommDMO();
		String insertSql = "insert into b_fileinfo(id,origin_id,origin_table,filename,downloadurl,update_man,update_date)"
				+ " values(s_b_fileinfo.nextval,?,?,?,?,?,sysdate)";
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, originId,tableName,fileName,ApmsConst.FDIMUFILE_DIR +"/"+fileName,uploadUser);
		
		dmo.commit(ApmsConst.DS_APMS);
		dmo.releaseContext(ApmsConst.DS_APMS);
		
		logger.info("**************文件"+ fileName + "记录插入成功！");
	}
	
	public void deleteFileUpload(String id,String url,String filename) throws Exception{
		
		String dirPath = FileConstant.RootPath+ ApmsConst.APUFDIMUFILE_DIR;
		FileUtil.createDirIfNotExists(dirPath);
		String fileName=url.substring(ApmsConst.APUFDIMUFILE_DIR.length()+1);  //用url里面的文件名
		String filePath = FileUtil.createFile(dirPath, fileName);
		File f = new File(filePath);
		//如果文件存在，则先删除
		if(f.exists())
			f.delete();
		
		logger.debug("文件【" + filename + "】已删除成功!");
		
		CommDMO dmo = new CommDMO();
		String insertSql = "delete from  b_fileinfo "
				         + " where id=? ";
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS,insertSql,id);
		
		dmo.commit(ApmsConst.DS_APMS);
		dmo.releaseContext(ApmsConst.DS_APMS);
		
		logger.info("**************文件"+ filename + "记录删除成功！");
	}
	
	//删除负载均衡上传的附件
	public void deleteFileUploadOfAircraftWeight(String id,String url,String filename) throws Exception{
			
			String dirPath = FileConstant.RootPath+ ApmsConst.AIRCRAFTWEIGHTFILE_DIR;
			FileUtil.createDirIfNotExists(dirPath);
			String fileName=url.substring(ApmsConst.AIRCRAFTWEIGHTFILE_DIR.length()+1);  //用url里面的文件名
			String filePath = FileUtil.createFile(dirPath, fileName);
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
			dmo.releaseContext(ApmsConst.DS_APMS);
			
			logger.info("**************文件"+ filename + "记录删除成功！");
		}
	
	
	public void engineDeleteFileUpload(String id,String url,String filename) throws Exception{
		
			String dirPath = FileConstant.RootPath+ ApmsConst.ENGINEFDIMUFILE_DIR;//enginefdimufile
			String fileName=url.substring(ApmsConst.ENGINEFDIMUFILE_DIR.length()+1);  //用url里面的文件名
			FileUtil.createDirIfNotExists(dirPath);
		
			String filePath = FileUtil.createFile(dirPath, fileName);
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
			dmo.releaseContext(ApmsConst.DS_APMS);
		
			logger.info("**************文件"+ filename + "记录删除成功！");
	}
	
	public void aircraftFileUpload(byte[] bytes,String fileName,String tableName,String originId,String uploadUser,String recordTimeVal,String maintenanceReportVal,String commentVal) throws Exception{
		
		String templeStr="";
		int postfix=fileName.lastIndexOf(".");
		templeStr=fileName.substring(0,postfix);
		String filenamestr=templeStr+DateUtil.format(new Date(), "yyyyMMddHHmmss")+fileName.substring(postfix);
		String urlstr=ApmsConst.AIRCRAFTWEIGHTFILE_DIR +"/"+filenamestr;
		
		String dirPath = FileConstant.RootPath+ ApmsConst.AIRCRAFTWEIGHTFILE_DIR;
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date recordTime=df.parse(recordTimeVal);
		//String ds=df.format(d);
		FileUtil.createDirIfNotExists(dirPath);
		String filePath = FileUtil.createFile(dirPath, filenamestr);
		File f = new File(filePath);
		//如果文件存在，则先删除
		if(f.exists())
			f.delete();
		
		FileUtils.writeByteArrayToFile(new File(filePath), bytes);
		
		logger.debug("文件【" + fileName + "】已上传成功，开始记录到表中!");
		
		CommDMO dmo = new CommDMO();
		String insertSql = "insert into b_fileinfo(id,origin_id,origin_table,filename,downloadurl,update_man,filetime,filetype,comments,update_date)"
				+ " values(s_b_fileinfo.nextval,?,?,?,?,?,?,?,?,sysdate)";
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql,originId,tableName,fileName,urlstr,uploadUser,recordTime,maintenanceReportVal,commentVal);
		
		dmo.commit(ApmsConst.DS_APMS);
		dmo.releaseContext(ApmsConst.DS_APMS);
		
		logger.info("**************文件"+ fileName + "记录插入成功！");
	}
	
	public void apuFileUpload(byte[] bytes,String fileName,String tableName,String originId,String uploadUser,String recordTimeVal,String maintenanceReportVal,String commentVal) throws Exception{
			
		String templeStr="";
		int postfix=fileName.lastIndexOf(".");
		templeStr=fileName.substring(0,postfix);
		String filenamestr=templeStr+DateUtil.format(new Date(), "yyyyMMddHHmmss")+fileName.substring(postfix);
		String urlstr=ApmsConst.APUFDIMUFILE_DIR +"/"+filenamestr;
		
		String dirPath = FileConstant.RootPath+ ApmsConst.APUFDIMUFILE_DIR;
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date recordTime=df.parse(recordTimeVal);
		//String ds=df.format(d);
		FileUtil.createDirIfNotExists(dirPath);
		String filePath = FileUtil.createFile(dirPath, filenamestr);
		File f = new File(filePath);
		//如果文件存在，则先删除
		if(f.exists())
			f.delete();
		
		FileUtils.writeByteArrayToFile(new File(filePath), bytes);
		
		logger.debug("文件【" + fileName + "】已上传成功，开始记录到表中!");
		
		
		CommDMO dmo = new CommDMO();
		String insertSql = "insert into b_fileinfo(id,origin_id,origin_table,filename,downloadurl,update_man,filetime,filetype,comments,update_date)"
				+ " values(s_b_fileinfo.nextval,?,?,?,?,?,?,?,?,sysdate)";
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql,originId,tableName,fileName,urlstr,uploadUser,recordTime,maintenanceReportVal,commentVal);
		
		dmo.commit(ApmsConst.DS_APMS);
		dmo.releaseContext(ApmsConst.DS_APMS);
		
		logger.info("**************文件"+ fileName + "记录插入成功！");
		}
	public void engineFileUpload(byte[] bytes,String fileName,String tableName,String originId,String uploadUser,String recordTimeVal,String maintenanceReportVal,String commentVal) throws Exception{
		
		String templeStr="";
		int postfix=fileName.lastIndexOf(".");
		templeStr=fileName.substring(0,postfix);
		String filenamestr=templeStr+DateUtil.format(new Date(), "yyyyMMddHHmmss")+fileName.substring(postfix);
		String urlstr=ApmsConst.ENGINEFDIMUFILE_DIR +"/"+filenamestr;
		
		String dirPath = FileConstant.RootPath+ ApmsConst.ENGINEFDIMUFILE_DIR;
		FileUtil.createDirIfNotExists(dirPath);
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date recordTime=df.parse(recordTimeVal);
		String filePath = FileUtil.createFile(dirPath, filenamestr);
		File f = new File(filePath);
		//如果文件存在，则先删除
		if(f.exists())
			f.delete();
		
		FileUtils.writeByteArrayToFile(new File(filePath), bytes);
		
		logger.debug("文件【" + fileName + "】已上传成功，开始记录到表中!");
		
		
		
		
		CommDMO dmo = new CommDMO();
		String insertSql = "insert into b_fileinfo(id,origin_id,origin_table,filename,downloadurl,update_man,filetime,filetype,comments,update_date)"
				+ " values(s_b_fileinfo.nextval,?,?,?,?,?,?,?,?,sysdate)";
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, originId,tableName,fileName,urlstr,uploadUser,recordTime,maintenanceReportVal,commentVal);
		
		dmo.commit(ApmsConst.DS_APMS);
		dmo.releaseContext(ApmsConst.DS_APMS);
		
		logger.info("**************文件"+ fileName + "记录插入成功！");
	}
	/**
	 * 从数据库中读取文件下载地址(相对路径)
	 * @param fileContentId
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public String swFileDownload(String fileContentId) throws Exception{
		CommDMO dmo = new CommDMO();
		String querySql = "select id,origin_id,filename,downloadurl from b_fileinfo where id = ?";
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, querySql, fileContentId);
		String fileUrl = vos[0].getStringValue("DOWNLOADURL");
		logger.info("查询到文件路径：【"+ fileUrl +"】！");	
		return fileUrl;
	}

	public SimpleHashVO[] selectFileTypeFromDictionary() throws Exception {
		CommDMO dmo = new CommDMO();
		String sql="SELECT VALUECN FROM B_DICTIONARY WHERE CLASSNAME='ATTACHMANAGE' AND ATTRIBUTENAME='FILETYPE'";
		HashVO[] vos=dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql);
		SimpleHashVO[] result = new SimpleHashVO[vos.length];
		for (int i = 0; i < vos.length; i++) {
			SimpleHashVO vo = new SimpleHashVO(vos[i]);
			result[i] = vo;
		}
		return result;
	}
}