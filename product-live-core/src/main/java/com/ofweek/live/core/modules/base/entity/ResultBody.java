package com.ofweek.live.core.modules.base.entity;

import java.io.Serializable;

public class ResultBody<T> implements Serializable {

	private static final long serialVersionUID = -4666615071577805431L;

	private boolean status = true;
	private T data;
	private String errorCode = "";
	private String errorMsg = "";

	public ResultBody() {

	}

	public ResultBody(T data) {
		this.data = data;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
