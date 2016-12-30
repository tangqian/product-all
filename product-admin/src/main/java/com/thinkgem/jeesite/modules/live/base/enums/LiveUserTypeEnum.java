package com.thinkgem.jeesite.modules.live.base.enums;

/**
 * 直播用户类型枚举
 * 
 * @author fengjingwei
 *
 */
public enum LiveUserTypeEnum {

	AUDIENCE(1, "观众"), ADMIN(2, "管理员"), SPEAKER(3, "主播"), WAITER(4, "客服");

	private LiveUserTypeEnum(int code, String meaning) {
		this.code = code;
		this.meaning = meaning;
	}

	public int getCode() {
		return code;
	}

	public String getMeaning() {
		return meaning;
	}

	public static boolean isAudience(Integer status) {
		return status != null && status == AUDIENCE.getCode();
	}

	public static boolean isAdmin(Integer status) {
		return status != null && status == ADMIN.getCode();
	}

	public static boolean isSpeaker(Integer status) {
		return status != null && status == SPEAKER.getCode();
	}

	public static boolean isWaiter(Integer status) {
		return status != null && status == WAITER.getCode();
	}

	private final int code;
	private final String meaning;

}
