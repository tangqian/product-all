/**
 * @Date 2016年3月23日 下午1:59:24
 * @author tangq
 * @version V1.0
 * 
 */
package com.thinkgem.jeesite.modules.live.base.utils;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

import com.thinkgem.jeesite.common.security.Digests;
import com.thinkgem.jeesite.common.utils.StringUtils;

/** 
 *  
 */
public class CommonUtils {

	private final static char[] HEX_CHARS = "0123456789ABCDEF".toCharArray();

	public static boolean isNullReference(String id) {
		return StringUtils.isEmpty(id) || id.equals("-1");
	}

	public static String encode(String input, String token, String nonce) {
		byte[] inputs, output = null;
		try {
			inputs = input.getBytes("UTF-8");
			byte[] salt = token.getBytes();
			inputs = Digests.sha1(inputs, salt);

			salt = nonce.getBytes("UTF-8");
			output = Digests.sha1(inputs, salt);
			return bytesToHexString(output, output.length);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
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
