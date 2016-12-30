package com.ofweek.live.core.modules.sys.enums;

/**
 * 审核相关状态枚举
 */
public enum AuditStatusEnum {

	AUDITING(0, "待审核"), PASS(3, "审核通过"), NOPASS(4, "审核不通过");

	AuditStatusEnum(int code, String meaning) {
		this.code = code;
		this.meaning = meaning;
	}

	public int getCode() {
		return code;
	}

	public String getMeaning() {
		return meaning;
	}

	/**
	 * 状态是否审核通过
	 * 
	 * @param status
	 * @return
	 */
	public static boolean isPass(Integer status) {
		return status != null && status == PASS.code;
	}

	/**
	 * 状态是否已审核，包括审核通过和审核未通过
	 * 
	 * @param status
	 * @return
	 */
	public static boolean isAudited(Integer status) {
		return status != null && (status == PASS.code || status == NOPASS.code);
	}

	public static String getHtml(Integer status) {
		String ret = "<span class='wait-pass'>待审核</span>";
		if (status != null) {
			if (status == PASS.code){
				ret = "<span class='pass'>审核通过</span>";
			}else if (status == NOPASS.code){
				ret = "<span class='not-pass'>审核未通过</span>";
			}
		}
		return  ret;
	}

	private final int code;
	private final String meaning;

}
