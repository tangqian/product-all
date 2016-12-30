package com.ofweek.live.core.modules.sys.enums;

/**
 * 性别枚举
 */
public enum SexEnum {

    SECRET(0, "保密"), MAN(1, "男"), WOMAN(2, "女");

    SexEnum(int code, String meaning) {
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
     * 获取性别
     *
     * @param sex
     * @return
     */
    public static SexEnum fromSmartlifein(Integer sex) {
        SexEnum sexEnum = SECRET;
        if (sex != null) {
            if (sex == 0) {
                sexEnum = MAN;
            } else if (sex == 1) {
                sexEnum = WOMAN;
            }
        }
        return sexEnum;
    }

    public static int toSmartlifein(Integer sex) {
        if (sex != null && sex == WOMAN.code)
            return 1;
        return 0;
    }

    private final int code;
    private final String meaning;

}
