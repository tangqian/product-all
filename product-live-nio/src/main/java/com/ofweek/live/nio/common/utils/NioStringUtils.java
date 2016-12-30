package com.ofweek.live.nio.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author tangqian
 *
 */
public class NioStringUtils {

	private static final Pattern EMOJI = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
			Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

	public static String filterEmoji(String source) {
		if (source != null) {
			Matcher emojiMatcher = EMOJI.matcher(source);
			if (emojiMatcher.find()) {
				source = emojiMatcher.replaceAll("*");
				return source;
			}
			return source;
		}
		return source;
	}
}
