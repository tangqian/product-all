package com.ofweek.live.nio.common.utils;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author tangqian
 *
 */
public class NioFastJsonUtils {
	
	private final static SerializerFeature[] serializerFeature;

	static {
		List<SerializerFeature> lists = new ArrayList<>();
		lists.add(SerializerFeature.QuoteFieldNames);
		lists.add(SerializerFeature.DisableCircularReferenceDetect);
		serializerFeature = (SerializerFeature[]) lists.toArray(new SerializerFeature[1]);
	}

	public static <T> T parseObject(String text, Class<T> type) throws Exception {
		return JSON.parseObject(text, type);
	}

	public static String toJSONString(Object value) {
		return JSON.toJSONString(value, serializerFeature);
	}

}
