package com.ofweek.live.core.modules.room.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 房间进入模式枚举
 */
public enum RoomModeEnum {

	PREVIEW(1, "预览"), RELEASE(2, "正式");

	RoomModeEnum(int code, String meaning) {
		this.code = code;
		this.meaning = meaning;
	}

	private static final Map<Integer, RoomModeEnum> CODE2ENUMMAP = new HashMap<>();

	static {
		for (RoomModeEnum statusEnum : RoomModeEnum.values()) {
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
	public static RoomModeEnum getEnum(Integer code) {
		return CODE2ENUMMAP.get(code);
	}
	
	public static boolean isPreview(Integer mode){
		return mode != null && mode == PREVIEW.code;
	}
	
	public static boolean isPreview(RoomModeEnum mode){
		return mode != null && mode == PREVIEW;
	}

	private final int code;
	private final String meaning;

}
