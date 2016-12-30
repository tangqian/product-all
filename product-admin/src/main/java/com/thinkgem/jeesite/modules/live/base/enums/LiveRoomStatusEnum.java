package com.thinkgem.jeesite.modules.live.base.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public enum LiveRoomStatusEnum {

	CREATED(0, "待举办"), HOLDING(2, "进行中"), END(4, "已结束");

	LiveRoomStatusEnum(int code, String meaning) {
		this.code = code;
		this.meaning = meaning;
	}

	private static final Map<Integer, LiveRoomStatusEnum> CODE2ENUMMAP = new HashMap<Integer, LiveRoomStatusEnum>();
	
	private static final List<LiveRoomStatusEnum> OPTIONS = new ArrayList<LiveRoomStatusEnum>();

	static {
		for (LiveRoomStatusEnum statusEnum : LiveRoomStatusEnum.values()) {
			CODE2ENUMMAP.put(statusEnum.code, statusEnum);
		}
		OPTIONS.add(CREATED);
		OPTIONS.add(HOLDING);
		OPTIONS.add(END);
	}

	public int getCode() {
		return code;
	}

	public String getMeaning() {
		return meaning;
	}

	public static LiveRoomStatusEnum getEnum(Integer code) {
		return CODE2ENUMMAP.get(code);
	}
	
	public static List<LiveRoomStatusEnum> getOptions() {
		return OPTIONS;
	}	

	private final int code;
	private final String meaning;

}
