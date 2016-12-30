package com.ofweek.live.core.modules.room.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 房间状态枚举
 */
public enum RoomStatusEnum {

    UPCOMING(0, "待举办"), HOLDING(2, "进行中"), OVER(4, "已结束");

    RoomStatusEnum(int code, String meaning) {
        this.code = code;
        this.meaning = meaning;
    }

    private static final Map<Integer, RoomStatusEnum> CODE2ENUMMAP = new HashMap<>();

    static {
        for (RoomStatusEnum statusEnum : RoomStatusEnum.values()) {
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
    public static RoomStatusEnum getEnum(Integer code) {
        return CODE2ENUMMAP.get(code);
    }

    public static boolean isOver(Integer status) {
        return status != null && status == OVER.code;
    }

    public static boolean isHolding(Integer status) {
        return status != null && status == HOLDING.code;
    }

    public static boolean isUpcoming(Integer status) {
        return status != null && status == UPCOMING.code;
    }

    private final int code;
    private final String meaning;

}
