package com.thinkgem.jeesite.modules.live.base.utils;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class FastJsonUtils {

	private final static SerializerFeature[] serializerFeature;

	static {
		List<SerializerFeature> lists = new ArrayList<SerializerFeature>();
		lists.add(SerializerFeature.WriteMapNullValue);
		lists.add(SerializerFeature.QuoteFieldNames);
		serializerFeature = (SerializerFeature[]) lists.toArray(new SerializerFeature[1]);
	}

	public static <T> T parseObject(String text, Class<T> type) throws Exception {
		return JSON.parseObject(text, type);
	}
	
	public static JSONObject parseObject(String text) throws Exception {
		return JSON.parseObject(text);
	}

	public static String toJSONString(Object value) {
		return JSON.toJSONString(value, serializerFeature);
	}

}
