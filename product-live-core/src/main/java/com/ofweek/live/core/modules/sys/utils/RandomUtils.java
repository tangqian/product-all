package com.ofweek.live.core.modules.sys.utils;

import java.util.Random;

public class RandomUtils {

	private static final String BASE = "abcdefghijklmnopqrstuvwxyz0123456789";

	private static final Random RANDOM = new Random(System.currentTimeMillis());

	public static String getRandomString(int length) { // length表示生成字符串的长度
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = RANDOM.nextInt(BASE.length());
			sb.append(BASE.charAt(number));
		}
		return sb.toString();
	}

	/** This class should not be instantiated. */
	private RandomUtils() {
	}
}
