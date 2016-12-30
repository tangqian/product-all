package com.ofweek.live.nio.common.handler;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息msgNo->handler的映射辅助类
 */
public abstract class HandlerMapperUtils {

	private static final Map<Integer, Handleable> msgNoHandlers = new HashMap<>();

	public static void addHandler(Handleable handler) {
		msgNoHandlers.put(handler.msgNo(), handler);
	}

	public static Handleable getHandler(int msgNo) {
		return msgNoHandlers.get(msgNo);
	}
}
