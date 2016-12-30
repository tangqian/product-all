package com.ofweek.live.core.modules.base.entity;

import java.io.Serializable;

public class Result<T> implements Serializable {

	private static final long serialVersionUID = 3532721356478596835L;

	private ResultBody<T> result = new ResultBody<T>();

	public ResultBody<T> getResult() {
		return result;
	}

	public void setResult(ResultBody<T> result) {
		this.result = result;
	}

	public Result() {

	}

	public void setData(T data) {
		this.result.setData(data);
	}

	public void setStatus(boolean status) {
		this.result.setStatus(status);
	}

	public void setErrorCode(String errorCode) {
		this.result.setErrorCode(errorCode);
	}

	public void setErrorMsg(String errorMsg) {
		this.result.setErrorMsg(errorMsg);
	}

}
