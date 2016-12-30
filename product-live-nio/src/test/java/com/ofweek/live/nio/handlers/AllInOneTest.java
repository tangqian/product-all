package com.ofweek.live.nio.handlers;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

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

/**
 * @author tangqian
 *
 */
public class AllInOneTest {

	//public static final String HOST = "14.17.70.152";//线上环境
	//public static final String HOST = "192.168.2.229";// 测试环境
	//public static final String HOST = "192.168.2.233";//预上线环境
	public static final String HOST = "127.0.0.1";//本机

	public static final int PORT = 9081;
	//public static final int PORT = 80;

	private static final int mode = 2;

	private static final int roomId = 3;

	private static final int userId = 1;

	private static final String ak = "B88362D7D3DF99BC67B613279BEB2AE465CA5D83";

	private static final String LOGING_REQUEST;

	private static final Charset UTF8 = Charset.forName("utf-8");

	static {
		StringBuilder sb = new StringBuilder();
			sb.append("10100{'body':{'nonce':'www','roomId':").append(roomId)
				.append(",'userId':").append(userId)
				.append(",'ak':'").append(ak)
					.append("','loginType':").append(1)
					.append(",'mode':").append(mode).append("},'msgId':1}");
		LOGING_REQUEST = sb.toString();
	}

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
		handler = new ClientHandler(LOGING_REQUEST);
	}

	@Test
	public void UserBlacklistTest() {
		handler = new ClientHandler(LOGING_REQUEST);
		handler.setSendOther("10110{'body': {'userId': 8, 'reason': '坏人'}}");
	}

	@Test
	public void UserOnlineTest() {
		handler = new ClientHandler(LOGING_REQUEST);
		handler.setSendOther("10120{'body': {'start': 1, 'size': 0}}");
	}

	@Test
	public void RoomInfoTest() {
		handler = new ClientHandler(LOGING_REQUEST);
		handler.setSendOther("10300{}");
	}

	@Test
	public void RoomWaiterTest() {
		handler = new ClientHandler(LOGING_REQUEST);
		handler.setSendOther("10301{}");
	}

	@Test
	public void RoomStatusStartTest() {
		handler = new ClientHandler(LOGING_REQUEST);
		handler.setSendOther("10302{}");
	}

	@Test
	public void RoomStatusOverTest() {
		handler = new ClientHandler(LOGING_REQUEST);
		handler.setSendOther("10303{}");
	}

	@Test
	public void RoomContacterTest() {
		handler = new ClientHandler(LOGING_REQUEST);
		handler.setSendOther("10304{}");
	}

	@Test
	public void RoomDataTest() {
		handler = new ClientHandler(LOGING_REQUEST);
		handler.setSendOther("10310{}");
	}

	@Test
	public void RoomPptTest() {
		handler = new ClientHandler(LOGING_REQUEST);
		handler.setSendOther("10320{}");
	}

	@Test
	public void RoomPptDetailTest() {
		handler = new ClientHandler(LOGING_REQUEST);
		handler.setSendOther("10321{'body':{'id':'54'}}");
	}

	@Test
	public void RoomVideoTest() {
		handler = new ClientHandler(LOGING_REQUEST);
		handler.setSendOther("10330{}");
	}

	@Test
	public void RoomVideoForReviewTest() {
		handler = new ClientHandler(LOGING_REQUEST);
		handler.setSendOther("10331{}");
	}

	@Test
	public void RoomRichTextTest() {
		handler = new ClientHandler(LOGING_REQUEST);
		handler.setSendOther("10340{'body': {'start': 0, 'size': 10}}");
	}

	@Test
	public void RoomRichTextPublishTest() {
		handler = new ClientHandler(LOGING_REQUEST);
		handler.setSendOther("10341{'body': {'content': '魂牵梦萦魂牵梦萦11111'}}");
	}

	@Test
	public void RoomRichTextRemoveTest() {
		handler = new ClientHandler(LOGING_REQUEST);
		handler.setSendOther("10342{'body': {'id': 23}}");
	}

	@Test
	public void ChatRoomHistoryTest() {
		handler = new ClientHandler(LOGING_REQUEST);
		handler.setSendOther("10201{'body': {'start': 0, 'size': 10}}");
	}

	@Test
	public void LiveStartTest() {
		handler = new ClientHandler(LOGING_REQUEST);
		handler.setSendOther("10802{}");
		handler.setSendLast("10800{'body': {'type': 'camera_live', 'pptId': 10}}");
	}

	@Test
	public void LiveGetStreamTest() {
		handler = new ClientHandler(LOGING_REQUEST);
		handler.setSendOther("10803{}");
	}

	@Test
	public void LiveStopTest() {
		handler = new ClientHandler(LOGING_REQUEST);
		handler.setSendOther("10801{}");
	}

	@Test
	public void LivePptTest() {
		handler = new ClientHandler(LOGING_REQUEST);
		handler.setSendOther("10810{'body': {'page': 3}}");
	}

	@Test
	public void LiveVodStartTest() {
		handler = new ClientHandler(LOGING_REQUEST);
		handler.setSendOther("10820{'body': {'vodName': 'upload/booth/vedio/2016-01/15523.mp4'}}");
	}

	@Test
	public void LiveVodStopTest() {
		handler = new ClientHandler(LOGING_REQUEST);
		handler.setSendOther("10821{}");
	}


	@org.junit.After
	public void finish(){
		bootstrap.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			public void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast("lengthFieldDecoder", new LengthFieldBasedFrameDecoder(409600000, 0, 4, 0, 4));
				ch.pipeline().addLast("lengthFieldEncoder", new LengthFieldPrepender(4));
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
