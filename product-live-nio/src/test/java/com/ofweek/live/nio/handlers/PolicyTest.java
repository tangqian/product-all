package com.ofweek.live.nio.handlers;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.Charset;

/**
 * @author tangqian
 *
 */
public class PolicyTest {

	//public static final String HOST = "14.17.70.150";//线上环境
	public static final String HOST = "192.168.2.229";// 测试环境
	//public static final String HOST = "192.168.2.233";//预上线环境
	//public static final String HOST = "127.0.0.1";//本机

	public static final int PORT = 843;

	private static final Charset UTF8 = Charset.forName("utf-8");

	private Bootstrap bootstrap;

	private ClientHandler handler;

	@Before
	public void init() {
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		bootstrap = new Bootstrap();
		bootstrap.group(workerGroup);
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
	}
	
	@Test
	public void LoginTest() {
		handler = new ClientHandler(null);
	}

	@org.junit.After
	public void finish(){
		bootstrap.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			public void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast("stringDecode", new StringDecoder(UTF8));
				ch.pipeline().addLast("stringEncode", new StringEncoder(UTF8));
				ch.pipeline().addLast("handler", handler);
			}
		});
		ChannelFuture f = null;
		try {
			f = bootstrap.connect(HOST, PORT).sync();
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
