package com.nettyframe.seril.userdefined;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * Created by Admin on 2019/4/9.
 */
@Slf4j
public class NettyServer {
    public void bind() throws Exception{
            EventLoopGroup e = new NioEventLoopGroup();
            EventLoopGroup worker = new NioEventLoopGroup();
            ServerBootstrap b = new ServerBootstrap();
            b.group(e,worker)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,100)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,10000)//io超时
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyMessageDecoder(1024*1024,4,4))
                                    .addLast("NettyMessageEncoder",new NettyMessageEncoder())
                                    .addLast("readTimeoutHandler",new ReadTimeoutHandler(50))
                                    .addLast("LoginAuthReqHandler",new LoginAuthRespHandler())
                                    .addLast("HeartBeatReqHandler",new HeartBeatRespHandler());
                        }
                    });

            ChannelFuture future = b.bind(83).sync();
//            future.channel().closeFuture().sync();
            future.awaitUninterruptibly(10, TimeUnit.MINUTES);
            log.info("server init success！");
    }

    public static void main(String[] args)throws Exception {
        new NettyServer().bind();
    }

}
