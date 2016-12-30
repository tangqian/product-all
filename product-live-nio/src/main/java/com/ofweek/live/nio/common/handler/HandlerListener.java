package com.ofweek.live.nio.common.handler;

import io.netty.channel.Channel;

public interface HandlerListener {
	
	void notice(Channel channel);
	
}
