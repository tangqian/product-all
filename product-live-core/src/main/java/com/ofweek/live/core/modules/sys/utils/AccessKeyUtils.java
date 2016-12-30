package com.ofweek.live.core.modules.sys.utils;

import java.io.UnsupportedEncodingException;

import com.ofweek.live.core.common.security.Digests;
import com.ofweek.live.core.common.utils.StringUtils;

/**
 * @author tangqian
 *
 */
public class AccessKeyUtils {

	
    public static String encode(String text, String nonce, String token) {
    	byte[] bytes = null;
		try {
			bytes = Digests.sha1(text.getBytes("UTF-8"), token.getBytes());
			bytes = Digests.sha1(bytes, nonce.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
		}
    	return StringUtils.toHex(bytes);
    }

	public static void main(String[] args) {
		System.out.println(encode("64", "www", "ofweek"));
	}

}
