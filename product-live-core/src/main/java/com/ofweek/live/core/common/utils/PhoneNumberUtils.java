package com.ofweek.live.core.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tangqian on 2016/11/3.
 */
public class PhoneNumberUtils {

    private final static String REGEX_MOBILEPHONE = "^1[3,5,8]\\d{9}$";

    //用于匹配固定电话号码
    private final static String REGEX_FIXEDPHONE = "^(\\d{3,4}-?)?\\d{7,9}$";

    private static Pattern PATTERN_MOBILEPHONE;
    private static Pattern PATTERN_FIXEDPHONE;


    static {
        PATTERN_FIXEDPHONE = Pattern.compile(REGEX_FIXEDPHONE);
        PATTERN_MOBILEPHONE = Pattern.compile(REGEX_MOBILEPHONE);
    }

    /**
     * 判断是否为手机号码
     *
     * @param number 手机号码
     * @return
     */
    public static boolean isCellPhone(String number) {
        Matcher match = PATTERN_MOBILEPHONE.matcher(number);
        return match.matches();
    }

    /**
     * 判断是否为固定电话号码
     *
     * @param number 固定电话号码
     * @return
     */
    public static boolean isFixedPhone(String number) {
        Matcher match = PATTERN_FIXEDPHONE.matcher(number);
        return match.matches();
    }

    public static void main(String[] args) {
    }
}
