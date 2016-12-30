package com.ofweek.live.core.modules.sys.enums;

/**
 * 用户状态枚举
 * 
 */
public enum UserStatusEnum {

	NORMAL(0, "正常"), DISABLE(1, "禁用");

	private UserStatusEnum(int code, String meaning) {
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
