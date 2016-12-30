package com.ofweek.live.core.modules.sys.enums;

public enum RecycleTypeEnum {

	DATA(256, "资料"), VIDEO(257, "视频"), SPEECH(258, "PPT");

	RecycleTypeEnum(int code, String meaning) {
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
