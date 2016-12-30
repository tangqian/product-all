/**
 * @Date 2015年11月12日 上午11:04:15
 * @author tangq
 * @version V1.0
 * 
 */
package com.thinkgem.jeesite.modules.live.base.enums;

import java.util.HashMap;
import java.util.Map;


/**
 * 用户类型枚举
 */
public enum UserTypeEnum {

	AUDIENCE(1, "观众"), ADMIN(2, "管理员"), EXHIBITOR(3, "参展商"), WAITER(4, "客服"), VISITOR(5, "游客");
	
	private static final Map<Integer, UserTypeEnum> CODE2ENUMMAP = new HashMap<Integer, UserTypeEnum>();

	static {
		for (UserTypeEnum typeEnum : UserTypeEnum.values()) {
			CODE2ENUMMAP.put(typeEnum.getCode(), typeEnum);
		}
	}

	UserTypeEnum(int code, String meaning) {
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
	 * 根据code获取枚举对象
	 * 
	 * @param code
	 * @return
	 */
	public static UserTypeEnum getEnum(Integer code) {
		return CODE2ENUMMAP.get(code);
	}

	private final int code;
	private final String meaning;


}
