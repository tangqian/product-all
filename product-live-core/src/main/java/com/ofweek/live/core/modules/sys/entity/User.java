package com.ofweek.live.core.modules.sys.entity;

import com.ofweek.live.core.common.persistence.DataEntity;

/**
 * 用户Entity
 * @author tangqian
 * 
 */
public class User extends DataEntity<User> {

	private static final long serialVersionUID = 1L;

	private String account;

	private String email;

	private String password;

	//用户类型(1:观众,2:管理员,3:主播,4:客服)
	private Integer type;
	
	//用户状态。0:正常,1禁用
	private Integer status;

	public User() {
		
	}
	
	public User(String id) {
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

	/**
	 * 用户类型(1:观众,2:管理员,3:主播,4:客服)
	 * @return
	 */
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public void preInsert() {
		super.preInsert();
		status = 0;
	}
	
	private String newPassword;

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}