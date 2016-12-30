package com.ofweek.live.nio.handlers.user.dto;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;


public class LoginRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 登录类型 1：正常 2：游客
	 */
	private Integer loginType;

	private String userId;

	//对应Signature，即签名
	private String ak;
	private String nonce;
	private String roomId;
	private Integer mode;
	/**
	 * 客户端IP地址
	 */
	private String clientIp;

	public Integer getLoginType() {
		return loginType;
	}

	public void setLoginType(Integer loginType) {
		this.loginType = loginType;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public Integer getMode() {
		return mode;
	}

	public void setMode(Integer mode) {
		this.mode = mode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAk() {
		return ak;
	}

	public void setAk(String ak) {
		this.ak = ak;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
