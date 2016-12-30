package com.ofweek.live.core.modules.base.enums;

public enum PageEnum {

	OFFSET(0), PAGESIZE(15);

	PageEnum(Integer code) {
		this.code = code;
	}

	public Integer getCode() {
		return code;
	}

	private final Integer code;

	public static Integer setPageOffset(Integer offset, Integer pageSize) {
		if (offset == null) {
			offset = OFFSET.code;
		} else {
			offset = (offset - 1) * pageSize;
		}
		return offset;
	}

	public static Integer setPageSize(Integer... objects) {
		if (objects.length == OFFSET.code) {
			return PAGESIZE.code;
		}
		return objects[OFFSET.code];
	}

}
