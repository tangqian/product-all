package com.ofweek.live.nio.common.dto;

public class NioBaseResponse {

	/**
	 * 0表示成功，其它值代码失败
	 */
	private int code;

	private String message;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
