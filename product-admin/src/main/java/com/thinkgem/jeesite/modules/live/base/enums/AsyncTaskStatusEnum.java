/**
 * @Date 2015年11月12日 上午11:04:15
 * @author tangq
 * @version V1.0
 * 
 */
package com.thinkgem.jeesite.modules.live.base.enums;

/**
 * 异步任务状态枚举
 */
public enum AsyncTaskStatusEnum {

	READY(0, "待执行"), EXECUTING(1, "执行中"), SUCCESS(2, "执行成功"), FAILURE(3, "执行失败");

	AsyncTaskStatusEnum(int code, String meaning) {
		this.code = code;
		this.meaning = meaning;
	}

	public int getCode() {
		return code;
	}

	public String getMeaning() {
		return meaning;
	}

	private final int code;
	private final String meaning;

}
