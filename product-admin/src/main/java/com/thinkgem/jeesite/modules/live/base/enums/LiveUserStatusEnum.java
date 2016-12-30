package com.thinkgem.jeesite.modules.live.base.enums;

/**
 * 直播用户状态枚举
 * 
 * @author fengjingwei
 *
 */
public enum LiveUserStatusEnum {

	NORMAL(0, "正常"), DISABLE(1, "禁用");

	private LiveUserStatusEnum(int code, String meaning) {
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
