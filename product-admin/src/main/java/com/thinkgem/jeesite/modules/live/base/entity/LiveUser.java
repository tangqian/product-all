package com.thinkgem.jeesite.modules.live.base.entity;

import com.thinkgem.jeesite.modules.live.base.persistence.ExDataEntity;

/**
 * @version 1.0
 * @author tangqian
 */
public class LiveUser extends ExDataEntity<LiveUser> {

	private static final long serialVersionUID = 1L;

	private String account;

	private String email;

	private String password;

	private Integer type;

	public LiveUser() {
		
	}
	
	public LiveUser(String id) {
		this.id = id;
	}
	
	public void setAccount(String value) {
		this.account = value;
	}

	public String getAccount() {
		return this.account;
	}

	public void setEmail(String value) {
		this.email = value;
	}

	public String getEmail() {
		return this.email;
	}

	public void setPassword(String value) {
		this.password = value;
	}

	public String getPassword() {
		return this.password;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
