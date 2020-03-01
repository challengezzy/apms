package com.apms.bs.sysconfig;

import org.simpleframework.xml.Attribute;

public class EmailConfVo {
	
	@Attribute
	private String host;//主机地址
	
	@Attribute
	private String user;//用户
	
	@Attribute
	private String password;//密码
	
	@Attribute
	private String sender;//发件人

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}
	
}
