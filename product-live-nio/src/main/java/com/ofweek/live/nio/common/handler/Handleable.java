package com.ofweek.live.nio.common.handler;

import io.netty.channel.ChannelHandlerContext;

import com.ofweek.live.nio.common.dto.BaseMessage;

/**
 * 消息处理接口
 */
public interface Handleable {

	/**
	 * 初始化，主动将自己加入HandlerMapperUtils辅助类中
	 */
	void init();
	
	int msgNo();

	/**
	 * 处理请求消息
	 * @param ctx
	 * @param request 请求消息
	 */
	public void process(ChannelHandlerContext ctx, BaseMessage request);
}
