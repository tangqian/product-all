package com.ofweek.live.core.modules.base.enums;

public enum ResultBodyEnum {

	OFWEEK_LIVE_ERROR("500", "获取首页直播列表失败."), 
	OFWEEK_LIVE_BANNER_ERROR("500", "获取首页直播Banner图列表失败.");

	ResultBodyEnum(String code, String meaning) {
		this.code = code;
		this.meaning = meaning;
	}

	public String getCode() {
		return code;
	}

	public String getMeaning() {
		return meaning;
	}

	private final String code;
	private final String meaning;

}
