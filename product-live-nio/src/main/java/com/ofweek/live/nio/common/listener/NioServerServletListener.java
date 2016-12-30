package com.ofweek.live.nio.common.listener;

import com.ofweek.live.nio.common.netty.codec.PolicyHandler;
import com.ofweek.live.nio.common.netty.server.LiveNioServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author tangq
 */
public class NioServerServletListener implements ServletContextListener {

    private Channel serverChannel;

    private static final Logger logger = LoggerFactory.getLogger(NioServerServletListener.class);

    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            new Thread(() -> {
                EventLoopGroup bossGroup = new NioEventLoopGroup();
                try {
                    ServerBootstrap b = new ServerBootstrap();
                    b.group(bossGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_REUSEADDR, true)
                            .childHandler(new ChannelInitializer<SocketChannel>() {
                                @Override
                                protected void initChannel(SocketChannel ch) throws Exception {
                                    ch.pipeline().addLast(new LineBasedFrameDecoder(128));
                                    ch.pipeline().addLast(new StringDecoder());
                                    ch.pipeline().addLast(new StringEncoder());
                                    ch.pipeline().addLast(new PolicyHandler());
                                }
                            });
                    serverChannel = b.bind(843).sync().channel();
                    logger.info("开启843端口成功...");
                    serverChannel.closeFuture().sync();
                } catch (Exception e) {
                    logger.error("开启843端口失败", e);
                } finally {
                    bossGroup.shutdownGracefully();
                }
            }).start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    LiveNioServer.getInstance().run();
                }
            }).start();
            logger.info("start nio server success");
        } catch (Exception e) {
            logger.error("start nio server failure.", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        try {
            serverChannel.close();
            LiveNioServer.getInstance().shutdown();
            event.getServletContext().log("shutdown nio server success");
        } catch (Exception e) {
            event.getServletContext().log("shutdown nio server failure.", e);
        }
    }

}
