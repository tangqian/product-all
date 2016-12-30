package com.ofweek.live.core.modules.room.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * socket接入房间模式枚举
 */
public enum NioLoginEnum {

	NORMAL(1, "正常登录模式"), VISITOR(2, "游客访问模式");

	NioLoginEnum(int code, String meaning) {
		this.code = code;
		this.meaning = meaning;
	}

	private static final Map<Integer, NioLoginEnum> codeLoginEnums = new HashMap<Integer, NioLoginEnum>();

	static {
		for (NioLoginEnum statusEnum : NioLoginEnum.values()) {
			codeLoginEnums.put(statusEnum.getCode(), statusEnum);
		}
	}

	public int getCode() {
		return code;
	}

	public String getMeaning() {
		return meaning;
	}

	/**
	 * 根据code获取枚举对象
	 * 
	 * @param code
	 * @return
	 */
	public static NioLoginEnum getEnum(Integer code) {
		return codeLoginEnums.get(code);
	}

	public static boolean isVisitorLogin(Integer type) {
		return type != null && type == VISITOR.code;
	}

	private final int code;
	private final String meaning;

}
