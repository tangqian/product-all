package com.ofweek.live.nio.common.netty.codec;

import com.ofweek.live.nio.handlers.user.SocketCloseHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import com.ofweek.live.nio.common.dto.BaseMessage;
import com.ofweek.live.nio.common.handler.Handleable;
import com.ofweek.live.nio.common.handler.HandlerMapperUtils;

import java.net.Socket;

public final class DispatchHandler extends SimpleChannelInboundHandler<BaseMessage> {
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		Handleable handler = HandlerMapperUtils.getHandler(SocketCloseHandler.CLOSE_MSG);
		handler.process(ctx, null);
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, BaseMessage msg) throws Exception {
		Handleable handler = HandlerMapperUtils.getHandler(msg.getMsgNo());
		if(handler != null){
			handler.process(ctx, msg);
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}

}
