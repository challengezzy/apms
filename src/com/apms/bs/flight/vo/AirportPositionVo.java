package com.apms.bs.flight.vo;

public class AirportPositionVo {
	
	private String id; //id
	private String airportid   ; //机场id
	private String code   ; //编号
	private String terminalno  ; //航站楼编号(t1/t2)
	private String isbridge; //是否靠桥,非靠桥机位需要摆渡车
	private String linkcode    ; //机场+机位  
	private String positiondesc; //位置描述   
	private String longitude   ; //经度  
	private String latitude    ; //纬度
	
	private String x; //x坐标 
	private String y; //y坐标 
	private String rotation; //旋转角度
	
	private String workladdernum;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAirportid() {
		return airportid;
	}
	public void setAirportid(String airportid) {
		this.airportid = airportid;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getTerminalno() {
		return terminalno;
	}
	public void setTerminalno(String terminalno) {
		this.terminalno = terminalno;
	}
	public String getIsbridge() {
		return isbridge;
	}
	public void setIsbridge(String isbridge) {
		this.isbridge = isbridge;
	}
	public String getLinkcode() {
		return linkcode;
	}
	public void setLinkcode(String linkcode) {
		this.linkcode = linkcode;
	}
	public String getPositiondesc() {
		return positiondesc;
	}
	public void setPositiondesc(String positiondesc) {
		this.positiondesc = positiondesc;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	public String getY() {
		return y;
	}
	public void setY(String y) {
		this.y = y;
	}
	public String getRotation() {
		return rotation;
	}
	public void setRotation(String rotation) {
		this.rotation = rotation;
	}
	public String getWorkladdernum() {
		return workladdernum;
	}
	public void setWorkladdernum(String workladdernum) {
		this.workladdernum = workladdernum;
	}
	
}
