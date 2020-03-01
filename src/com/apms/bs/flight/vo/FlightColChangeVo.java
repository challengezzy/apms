package com.apms.bs.flight.vo;

import java.util.ArrayList;
import java.util.Date;

import com.apms.bs.util.DateUtil;

import smartx.framework.common.vo.HashVO;

public class FlightColChangeVo {
	
	private String item ;//列说明
	private String oldColumn; //源表字段列名
	private String newColumn; //目标表字段列名
	
	private int dataType = 0;//数据类型 0-字符 1-日期
	
	private Object oldValue; //旧值
	private Object newValue; //新值
	
	private String oldValStr; //旧值
	private String newValStr;
	
	private String dateFormat = "MM-dd HH:mm";
	
	private boolean isChanged = false;//值是否有变化
	
	public FlightColChangeVo(){
		
	}
	
	/**
	 * 新旧表列名相同，字段为字符串
	 * @param newvo
	 * @param oldvo
	 * @param item
	 * @param newCol
	 */
	public FlightColChangeVo(HashVO newvo,HashVO oldvo,String item,String newCol){
		initValue(newvo, oldvo, item, newCol, newCol, 0);
	}
	
	/**
	 * 新旧表列名不同，字段为字符串
	 * @param newvo
	 * @param oldvo
	 * @param item
	 * @param newCol
	 * @param oldCol
	 */
	public FlightColChangeVo(HashVO newvo,HashVO oldvo,String item,String newCol,String oldCol){
		initValue(newvo, oldvo, item, newCol, oldCol, 0);
	}
	
	/**
	 * 新旧表列名相同，字段为日期
	 * @param newvo
	 * @param oldvo
	 * @param item
	 * @param newCol
	 * @param dataType 1-日期
	 */
	public FlightColChangeVo(HashVO newvo,HashVO oldvo,String item,String newCol,int dataType){
		initValue(newvo, oldvo, item, newCol, newCol, dataType);
	}
	
	/**
	 * 
	 * @param newvo
	 * @param oldvo
	 * @param item
	 * @param newCol
	 * @param oldCol
	 * @param dataType 字段类型
	 */
	public FlightColChangeVo(HashVO newvo,HashVO oldvo,String item,String newCol,String oldCol,int dataType){
		initValue(newvo, oldvo, item, oldCol, newCol, dataType);
	}
	
	public void initValue(HashVO newvo,HashVO oldvo,String item,String newCol,String oldCol,int dataType){
		this.item = item;
		this.newColumn = newCol;
		this.oldColumn = oldCol;
		this.dataType = dataType;
		
		if(dataType == 1){
			// 日期字符串采用短格式 MM-dd HH:mm
			newValue = newvo.getDateValue(newColumn);
			oldValue = oldvo.getDateValue(oldColumn);
			
			if(newValue != null){
				newValStr = DateUtil.getDateStr( (Date)newValue, dateFormat);
			}
			
			if(oldValue != null){
				oldValStr = DateUtil.getDateStr( (Date)oldValue, dateFormat);
			}
			
		}else{
			newValStr = newvo.getStringValue(newColumn);
			oldValStr = oldvo.getStringValue(oldColumn);
			newValue = newValStr;
			oldValue = oldValStr;
		}
		
		//判断值是否相等，只需要判断字符串是否相等
		if( !strEqual(newValStr, oldValStr) ){
			isChanged = true;
		}
		
	}
	
	/**
	 * 生成update语句中的set字句
	 * @return
	 */
	public String getColSetStr(){
		if( isChanged ){
			return " , "+oldColumn+"=?";
		}else{
			return "";
		}
	}
	
	/**
	 * 生成update语句中?点位符的值
	 * @param paramValList
	 */
	public void setParamValList(ArrayList<Object> paramValList){
		if(isChanged){
			paramValList.add(newValue);
		}
	}
	
	/**
	 * 生成变动详细信息
	 * @return
	 */
	public String getChgDesc(){
		String desc = "";
		if(isChanged){
			if(oldValStr == null){
				desc = ", " + item + ": [空]->["+newValStr+"]";
			}else{
				desc = ", " + item + ": ["+oldValStr+"]->["+newValStr+"]";
			}
		}
		return desc;
	}
	
	
	/** 生成日期的变动信息，保持简洁,日期格式为 MM-DD HH:MI:SS */
	public String getChgTimeDesc(){
		String desc = "";
		if(isChanged){
			if(oldValStr == null){
				desc = ", " + item + ": [空]->["+newValStr+"]";
			}else{
				desc = ", " + item + ": ["+oldValStr+"]->["+newValStr+"]";
			}
		}
		return desc;
	}
	
	private boolean strEqual(String str1,String str2){
		if(str1 == null && str2 == null){
			return true;
		}else if(str1 == null && str2 != null) {
			return false;
		}else if(str1 != null && str2 == null) {
			return false;
		}else if(str1.equals(str2)){
			return true;
		}else{
			return false;
		}
	}
	
	public String getOldColumn() {
		return oldColumn;
	}

	public void setOldColumn(String oldColumn) {
		this.oldColumn = oldColumn;
	}

	public String getNewColumn() {
		return newColumn;
	}

	public void setNewColumn(String newColumn) {
		this.newColumn = newColumn;
	}

	public int getDataType() {
		return dataType;
	}
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}
	public Object getOldValue() {
		return oldValue;
	}
	public void setOldValue(Object oldValue) {
		this.oldValue = oldValue;
	}
	public Object getNewValue() {
		return newValue;
	}
	public void setNewValue(Object newValue) {
		this.newValue = newValue;
	}
	public String getOldValStr() {
		return oldValStr;
	}
	public void setOldValStr(String oldValStr) {
		this.oldValStr = oldValStr;
	}
	public String getNewValStr() {
		return newValStr;
	}
	public void setNewValStr(String newValStr) {
		this.newValStr = newValStr;
	}
	public boolean isChanged() {
		return isChanged;
	}
	public void setChanged(boolean isChanged) {
		this.isChanged = isChanged;
	}
	
	/**
	 * 获取两个日期字符串的时间差,单位秒
	 */
	public int getDateDiffSecond(){
		if(newValue == null || oldValue == null){
			return 365*24*60*60;
		}
		
		return DateUtil.dateDiff("second", (Date)newValue, (Date)oldValue );
	}

}
