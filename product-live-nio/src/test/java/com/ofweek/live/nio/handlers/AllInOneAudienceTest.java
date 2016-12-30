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
public class AllInOneAudienceTest {

	//public static final String HOST = "14.17.70.150";//线上环境
	public static final String HOST = "192.168.2.229";// 测试环境
	//public static final String HOST = "192.168.2.233";//预上线环境
	//public static final String HOST = "127.0.0.1";//本机

	public static final int PORT = 9081;
	//public static final int PORT = 80;

	private static final int mode = 2;

	private static final int roomId = 8;

	private static final int userId = 8;
	private static final String ak = "1E81F80BBB7222B636BA8692F9ED58A6C641CF83";

	//private static final int userId = 98;
	//private static final String ak = "AF09F98379B5FD670F386E2D3F1A639FE4F59BF4";

	//private static final int userId = 28;
	//private static final String ak = "7F359D51423F3E1E981FDD7E19D9E01E8D8A8405";

	//private static final int userId = 64;
	//private static final String ak = "92921AC48461874DC306AE335721ACFA3036C306";

	//private static final int userId = 131;
	//private static final String ak = "2A4D2E41D0F0F5EE20328C19F13451CF943B9FD6";

//	private static final int userId = 50;
//	private static final String ak = "605595AC443777EF798CB6664F8E90D81BAEB1E4";

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
	public void ChatRoomTest() {
		handler = new ClientHandler(LOGING_REQUEST);
		handler.setSendOther("10200{'body': {'content': '测试聊天内容'}}");
	}

	@Test
	public void ChatRoomHistoryTest() {
		handler = new ClientHandler(LOGING_REQUEST);
		handler.setSendOther("10201{'body': {'start': 0, 'size': 10}}");
	}

	@Test
	public void AudienceRegisterTest() {
		handler = new ClientHandler(LOGING_REQUEST);
		handler.setSendOther("10105{}");
	}

	@Test
	public void AudienceUpdateTest() {
		handler = new ClientHandler(LOGING_REQUEST);
		handler.setSendOther("10106{'body': {'name':'唐乾1','company':'好视科技1','job':'保密1','department':'技术部1','telephone':'188142266781'}}");
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
