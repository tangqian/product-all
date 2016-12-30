package com.ofweek.live.core.modules.sys.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户类型枚举
 */
public enum UserTypeEnum {

    AUDIENCE(1, "观众"), ADMIN(2, "管理员"),
    SPEAKER(3, "主播") {
        @Override
        public boolean isMyPersonalUri(String uri) {
            return uri.startsWith("/speaker") || uri.startsWith("/room");
        }
    },
    WAITER(4, "客服"), VISITOR(5, "游客");

    private static final Map<Integer, UserTypeEnum> codes = new HashMap<Integer, UserTypeEnum>();

    static {
        for (UserTypeEnum typeEnum : UserTypeEnum.values()) {
            codes.put(typeEnum.getCode(), typeEnum);
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
     * 判断当前访问uri是否为独享uri，（即不允许其它类型用户访问）
     *
     * @param uri 当前访问uri
     * @return
     */
    public boolean isMyPersonalUri(String uri) {
        return false;
    }

    /**
     * 根据type获取类型枚举对象
     *
     * @param type
     * @return
     */
    public static UserTypeEnum getEnum(Integer type) {
        return codes.get(type);
    }

    /**
     * 是否是管理员
     *
     * @param type
     * @return
     */
    public static boolean isAdmin(Integer type) {
        return type != null && type == ADMIN.code;
    }

    /**
     * 是否是观众
     *
     * @param type
     * @return
     */
    public static boolean isAudience(Integer type) {
        return type != null && type == AUDIENCE.code;
    }

    /**
     * @param type
     * @return
     */
    public static boolean isSpeaker(Integer type) {
        return type != null && type == SPEAKER.code;
    }

    public static boolean isWaiter(Integer type) {
        return type != null && type == WAITER.code;
    }

    private final int code;
    private final String meaning;


}
