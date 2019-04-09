package com.netty.aio;

import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Admin on 2019/4/9.
 */
@Slf4j
public class Server {
    private ExecutorService executorService;
    private AsynchronousChannelGroup asynchronousChannelGroup;
    public AsynchronousServerSocketChannel asynchronousServerSocketChannel;

    public Server(int port) throws Exception{
        executorService = Executors.newCachedThreadPool();
        asynchronousChannelGroup = AsynchronousChannelGroup.withCachedThreadPool(executorService,1);
        asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open(asynchronousChannelGroup);
        asynchronousServerSocketChannel.bind(new InetSocketAddress(port),1024);
        asynchronousServerSocketChannel.accept(this,new ServerCompletionHandler());
        log.info("server init success!");
        //一直阻塞 不让服务器停止
        Thread.sleep(Integer.MAX_VALUE);
    }

    public static void main(String[] args)throws Exception {
        new Server(8765);
    }



}
