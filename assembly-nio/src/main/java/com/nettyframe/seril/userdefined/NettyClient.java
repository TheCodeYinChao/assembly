package com.nettyframe.seril.userdefined;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Admin on 2019/4/9.
 */
public class NettyClient {
   private ScheduledExecutorService executor =  Executors.newScheduledThreadPool(1);
   EventLoopGroup e = new NioEventLoopGroup();
   public void connet(String ip,int port)throws Exception{
       try {
           Bootstrap b = new Bootstrap();
           b.group(e).channel(NioSocketChannel.class)
                   .option(ChannelOption.TCP_NODELAY,true)
                   .handler(new ChannelInitializer<SocketChannel>() {
                       @Override
                       protected void initChannel(SocketChannel socketChannel) throws Exception {
                           socketChannel.pipeline().addLast(new NettyMessageDecoder(1024*1024,4,4))
                                   .addLast("NettyMessageEncoder",new NettyMessageEncoder())
                                   .addLast("readTimeoutHandler",new ReadTimeoutHandler(50))
                                   .addLast("LoginAuthReqHandler",new LoginAuthReqHandler())
                                   .addLast("HeartBeatReqHandler",new HeartBeatReqHandler());
                       }
                   });
           ChannelFuture sync = b.connect(ip, port).sync();
           sync.channel().closeFuture().sync();
       }finally {
           executor.execute(new Runnable() {
               @Override
               public void run() {
                   try {
                       Thread.sleep(5000);
                       connet(ip,port);
                   } catch (Exception e1) {
                       e1.printStackTrace();
                   }
               }
           });
   }
   }

    public static void main(String[] args)throws Exception {
        new NettyClient().connet("127.0.0.1",83);
    }

}
