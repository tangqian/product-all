package com.ofweek.live.nio.common.netty;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelMatcher;

/**
 * @author tangqian
 *
 */
public class RoomChannelMatcher implements ChannelMatcher {
	
	private Channel excludeChannel;
	
	public RoomChannelMatcher(Channel excludeChannel){
		this.excludeChannel = excludeChannel;
	}

	@Override
	public boolean matches(Channel channel) {
		return excludeChannel == channel ? false : true;
	}
}
