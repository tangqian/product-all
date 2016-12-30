package com.thinkgem.jeesite.modules.sys.utils;

import java.util.Locale;

public class CryptUtils {
    private final static char[] HEX_CHARS = "0123456789ABCDEF".toCharArray();
    
    public static String bytesToHexString(byte[] b) {
        return bytesToHexString(b, b.length);
    }
    
    /**
     * bytes转换成十六进制字符串
     * 
     * @param b
     *            byte[] byte数组
     * @param iLen
     *            int 取前N位处理 N=iLen
     * @return String 每个Byte值之间空格分隔
     */
    public static String bytesToHexString(byte[] b, int iLen) {
        StringBuilder sb = new StringBuilder();
        for (int n = 0; n < iLen; n++) {
            sb.append(HEX_CHARS[(b[n] & 0xFF) >> 4]);
            sb.append(HEX_CHARS[b[n] & 0x0F]);
        }
        return sb.toString().trim().toUpperCase(Locale.US);
    }
}
