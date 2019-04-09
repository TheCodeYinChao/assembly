package com.nettyframe.first;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;

/**
 * Created by Admin on 2019/4/9.
 */
public class TimeClient {

    public TimeClient(String ip,int port) {
        EventLoopGroup e = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(e).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new TimeClientHandler());
                        }
                    });
            ChannelFuture sync = b.connect(ip, port).sync();
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } finally {
            e.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new TimeClient("127.0.0.1",82);
    }
}
