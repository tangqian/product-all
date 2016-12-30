package com.thinkgem.jeesite.modules.live.base.utils;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * 已压缩过图片集合缓存类
 * 
 * @author tangq
 */
public class CompressImageUtils {

	public enum CompressTypeEnum {
		LARGE_IMAGE("_l", "大图"), MIDDLE_IMAGE("_m", "中图"), SMALL_IMAGE("_s", "小图");

		private static final Set<String> subfixs = Sets.newHashSet();

		static {
			for (CompressTypeEnum typeEnum : CompressTypeEnum.values()) {
				subfixs.add(typeEnum.getSubfix());
			}
		}

		CompressTypeEnum(String subfix, String meaning) {
			this.subfix = subfix;
			this.meaning = meaning;
		}

		public String getSubfix() {
			return subfix;
		}
		
		public Map<Integer, String> getCacheMap() {
			return compressedMap;
		}

		public static Set<String> getSubfixs() {
			return Collections.unmodifiableSet(subfixs);
		}

		private final String subfix;
		@SuppressWarnings("unused")
		private final String meaning;
		
		private final Map<Integer, String> compressedMap = Maps.newHashMap();
	}

	public static void put(Integer key, String uri, CompressTypeEnum typeEnum) {
		typeEnum.getCacheMap().put(key, uri);
	}

	public static String get(Integer key, CompressTypeEnum typeEnum) {
		return typeEnum.getCacheMap().get(key);
	}

	public static boolean containsKey(Integer key, CompressTypeEnum typeEnum) {
		return typeEnum.getCacheMap().containsKey(key);
	}

	public static boolean containsValue(String uri, CompressTypeEnum typeEnum) {
		return typeEnum.getCacheMap().containsValue(uri);
	}

	/**
	 * 获取原始图片uri
	 * 
	 * @param imageUri
	 * @return
	 */
	public static String getPureUri(String imageUri) {
		if (StringUtils.isNotBlank(imageUri)) {
			for (String subfix : CompressTypeEnum.getSubfixs()) {
				imageUri = imageUri.replace(subfix, "");
			}
		}
		return imageUri;
	}

	/**
	 * 是否是缩略图
	 * 
	 * @param imageUri
	 * @return
	 */
	public static boolean isThumbnailImage(String imageUri) {
		boolean result = false;
		for (String subfix : CompressTypeEnum.getSubfixs()) {
			if (imageUri.contains(subfix)) {
				result = true;
				break;
			}
		}
		return result;
	}

	/**
	 * 获取压缩后的图片uri
	 * 
	 * @param srcUri
	 * @return
	 */
	public static String getCompressUri(String srcUri, CompressTypeEnum typeEnum) {
		return combineUri(srcUri, typeEnum.getSubfix());
	}

	/**
	 * 组合uri
	 * 
	 * @param srcUri
	 * @param subfix
	 * @return
	 */
	private static String combineUri(String srcUri, String subfix) {
		int pos = srcUri.lastIndexOf('.');
		return srcUri.substring(0, pos) + subfix + srcUri.substring(pos);
	}
}
