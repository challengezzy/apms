package com.apms.cache.vo;

public class AirPortVo {
	
	private String id;
	
	private String code_3;//机场三字码
	private String code_4;//
	private String regionId;//
	private String name;//
	private String nameEn;//
	private boolean plateau;//是否高原机场
	private boolean antiice;//是否除冰
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode_3() {
		return code_3;
	}
	public void setCode_3(String code_3) {
		this.code_3 = code_3;
	}
	public String getCode_4() {
		return code_4;
	}
	public void setCode_4(String code_4) {
		this.code_4 = code_4;
	}
	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNameEn() {
		return nameEn;
	}
	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}
	public boolean isPlateau() {
		return plateau;
	}
	public void setPlateau(boolean plateau) {
		this.plateau = plateau;
	}
	public boolean isAntiice() {
		return antiice;
	}
	public void setAntiice(boolean antiice) {
		this.antiice = antiice;
	}
	
	

}
