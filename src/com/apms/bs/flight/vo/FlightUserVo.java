package com.apms.bs.flight.vo;

/**
 * 航班中的人员信息
 * @author jerry
 * @date Apr 3, 2016
 */
public class FlightUserVo {

	private String username;
	private String post;//职责 放行、勤务、维修、接机、出港

	public FlightUserVo(){
	}
	
	public FlightUserVo(String post,String username) {
		super();
		this.post = post;
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}
	
}
