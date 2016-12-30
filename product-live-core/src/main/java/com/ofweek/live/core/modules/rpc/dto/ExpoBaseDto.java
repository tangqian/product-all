package com.ofweek.live.core.modules.rpc.dto;

/**
 * 展会网http接口基本响应对象
 * @author tangqian
 *
 */
public class ExpoBaseDto<T> {

	/**
	 * 0:成功;1:失败
	 */
	private int status;

	private String msg;
	
	private T data;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
