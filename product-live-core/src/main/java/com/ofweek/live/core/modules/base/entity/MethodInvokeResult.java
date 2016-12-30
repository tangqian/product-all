package com.ofweek.live.core.modules.base.entity;

/**
 * 方法调用结果传输类
 * @author tangqian
 *
 */
public class MethodInvokeResult<T> {

	private boolean success;

	private String msg;
	
	private T data;
	
	public MethodInvokeResult() {
		success = false;
	}

	public MethodInvokeResult(boolean success) {
		this.success = success;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public MethodInvokeResult setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
