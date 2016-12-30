package com.ofweek.live.web.api.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tangqian
 *
 */
public enum CodeEnum {

	SUCCESS(0, "成功"),

	CLIENT_PARAM_ILLEGAL(400, "参数不符合规范"),

	SERVER_ERROR(500, "服务端错误");

	private int code;

	private String meaning;

	private static final Map<Integer, CodeEnum> CODES = new HashMap<>();

	static {
		for (CodeEnum statusEnum : CodeEnum.values()) {
			CODES.put(statusEnum.code(), statusEnum);
		}
	}

	/**
	 * <默认构造函数>
	 */
	CodeEnum(int code, String meaning) {
		this.code = code;
		this.meaning = meaning;
	}

	/**
	 * @return 返回 code
	 */
	public int code() {
		return code;
	}

	/**
	 * @return 返回 meaning
	 */
	public String getMeaning() {
		return meaning;
	}

	public static CodeEnum getEnum(Integer code) {
		return CODES.get(code);
	}
}
