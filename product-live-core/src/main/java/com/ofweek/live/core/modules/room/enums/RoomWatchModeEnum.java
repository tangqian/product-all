package com.ofweek.live.core.modules.room.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 房间观看模式枚举
 */
public enum RoomWatchModeEnum {

	VISITOR(0, "游客"), USER(1, "会员");

	RoomWatchModeEnum(int code, String meaning) {
		this.code = code;
		this.meaning = meaning;
	}

	private static final Map<Integer, RoomWatchModeEnum> CODE2ENUMMAP = new HashMap<>();

	static {
		for (RoomWatchModeEnum statusEnum : RoomWatchModeEnum.values()) {
			CODE2ENUMMAP.put(statusEnum.getCode(), statusEnum);
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
	public static RoomWatchModeEnum getEnum(Integer code) {
		return CODE2ENUMMAP.get(code);
	}
	
	public static boolean requireUser(Integer mode){
		return mode != null && mode == USER.code;
	}

	private final int code;
	private final String meaning;

}
