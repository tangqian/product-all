package com.ofweek.live.core.common.utils;

/**
 * @author tangqian
 *
 */
public class Validate {
	public static void checkStringNotEmpty(String value, String errorMessage) {
		if (value == null || value.trim().isEmpty()) {
			throw new IllegalArgumentException(errorMessage);
		}
	}

	public static void checkNotNull(Object value, String errorMessage) {
		if (value == null) {
			throw new IllegalArgumentException(errorMessage);
		}
	}

	public static void checkIsTrue(boolean condition, String errorMessage) {
		if (!condition) {
			throw new IllegalArgumentException(errorMessage);
		}
	}
}
