package com.ofweek.live.core.modules.sys.enums;

public enum FileTypeEnum {

	PICTURE(0, "图片"), FILE(1, "文件");

	FileTypeEnum(int code, String meaning) {
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
