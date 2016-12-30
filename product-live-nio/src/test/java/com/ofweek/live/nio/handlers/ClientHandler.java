package com.ofweek.live.nio.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 *
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    private String sendOther;

    private String sendLast;

    private String values;

    public ClientHandler(String values) {
        this.values = values;
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        if (values != null)
            ctx.writeAndFlush(values);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("我收到消息" + msg.toString());
        if (sendOther != null) {
            ctx.writeAndFlush(sendOther);
            sendOther = null;
        } else {
            if (sendLast != null) {
                ctx.writeAndFlush(sendLast);
                sendLast = null;
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    public void setSendOther(String sendOther) {
        this.sendOther = sendOther;
    }

    public void setSendLast(String sendLast) {
        this.sendLast = sendLast;
    }

}