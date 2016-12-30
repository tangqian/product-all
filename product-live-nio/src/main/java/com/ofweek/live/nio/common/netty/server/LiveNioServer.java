package com.ofweek.live.nio.common.netty.server;

import com.ofweek.live.core.common.config.LiveEnv;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ofweek.live.nio.common.netty.codec.DispatchHandler;
import com.ofweek.live.nio.common.netty.codec.InitialDemuxHandler;
import com.ofweek.live.nio.common.netty.codec.JsonBaseCodec;

public class LiveNioServer {

	private final static Logger logger = LoggerFactory.getLogger(LiveNioServer.class);

	private static final LiveNioServer INSTANCE = new LiveNioServer();

	private Channel serverChannel;

	private int[] ports;

	private LiveNioServer() {
	}

	public static final LiveNioServer getInstance() {
		return INSTANCE;
	}

	public void run() {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		try {
			initPorts();
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_REUSEADDR, true)
					.childHandler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast("initialDemuxHandler", new InitialDemuxHandler());
							ch.pipeline().addLast("jsonBaseCodec", new JsonBaseCodec());
							ch.pipeline().addLast("dispatchHandler", new DispatchHandler());
						}
					});

			for (int port : ports) {
				serverChannel = b.bind(port).sync().channel();
			}
			logger.info("服务端开启等待客户端连接 ... ...");
			logger.info("Listen on port:{}", Arrays.toString(ports));
			serverChannel.closeFuture().sync();
		} catch (Exception e) {
			logger.error("nio消息服务启动失败", e);
		} finally {
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}

	public void shutdown() {
		serverChannel.close();
	}

	private void initPorts() {
		if (ports == null) {
			String[] portsStr = LiveEnv.getNioPort().split(",");
			int[] tmpPorts = new int[portsStr.length];
			int index = 0;
			for (String portStr : portsStr) {
				tmpPorts[index++] = Integer.parseInt(portStr);
			}
			ports = tmpPorts;
		}
	}

	public static void main(String[] args) {
		LiveNioServer.getInstance().ports = new int[] { 9081, 110 };
		new Thread(new Runnable() {

			@Override
			public void run() {
				LiveNioServer.getInstance().run();
			}
		}).start();

		try {
			Thread.sleep(1000000);
		} catch (InterruptedException e) {
		}
	}

}
