package com.ofweek.live.core.modules.room.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 聊天类型枚举
 */
public enum ChatTypeEnum {

	P2P(1, "私聊"), GROUP(2, "群聊");

	ChatTypeEnum(int code, String meaning) {
		this.code = code;
		this.meaning = meaning;
	}

	private static final Map<Integer, ChatTypeEnum> codeEnums = new HashMap<>();

	static {
		for (ChatTypeEnum statusEnum : ChatTypeEnum.values()) {
			codeEnums.put(statusEnum.getCode(), statusEnum);
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
	public static ChatTypeEnum getEnum(Integer code) {
		return codeEnums.get(code);
	}

	private final int code;
	private final String meaning;

}
