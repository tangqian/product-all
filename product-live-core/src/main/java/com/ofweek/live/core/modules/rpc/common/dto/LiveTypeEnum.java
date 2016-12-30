package com.ofweek.live.core.modules.rpc.common.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tangqian on 2016/9/19.
 */
public enum LiveTypeEnum {

    LIVE(1, "直播"), EXPO(2, "展会"), WEBINAR(2, "研讨会");

    LiveTypeEnum(int code, String meaning) {
        this.code = code;
        this.meaning = meaning;
    }

    private static final Map<Integer, LiveTypeEnum> codeEnums = new HashMap<>();

    static {
        for (LiveTypeEnum statusEnum : LiveTypeEnum.values()) {
            codeEnums.put(statusEnum.getCode(), statusEnum);
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
    public static LiveTypeEnum getEnum(Integer code) {
        return codeEnums.get(code);
    }

    private final int code;
    private final String meaning;
}
